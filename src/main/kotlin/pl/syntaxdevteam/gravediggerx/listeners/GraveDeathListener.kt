package pl.syntaxdevteam.gravediggerx.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import pl.syntaxdevteam.gravediggerx.GraveDiggerX

class GraveDeathListener(private val plugin: GraveDiggerX) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity

        val env = player.world.environment
        val enabledInWorld = when (env) {
            org.bukkit.World.Environment.NORMAL -> plugin.config.getBoolean("graves.worlds.overworld", true)
            org.bukkit.World.Environment.NETHER -> plugin.config.getBoolean("graves.worlds.nether", true)
            org.bukkit.World.Environment.THE_END -> plugin.config.getBoolean("graves.worlds.end", true)
            else -> true
        }
        if (!enabledInWorld) {
            event.keepInventory = false
            event.drops.clear()
            for (item in player.inventory.contents) {
                if (item != null && item.type != Material.AIR) {
                    event.drops.add(item.clone())
                }
            }

            // wyczyść eq
            player.inventory.clear()
            player.inventory.armorContents = null
            player.inventory.setItemInOffHand(ItemStack(Material.AIR))

            return
        }

        //
        // 2️⃣ WORLD ENABLED → tworzenie grobu
        //
        val playerItems = mutableMapOf<Int, ItemStack>()

        // Sloty regularne
        for (i in 0..35) {
            player.inventory.getItem(i)?.let { playerItems[i] = it.clone() }
        }

        // Armor + offhand (zawsze osobno mapowane)
        player.inventory.helmet?.let { playerItems[36] = it.clone() }
        player.inventory.chestplate?.let { playerItems[37] = it.clone() }
        player.inventory.leggings?.let { playerItems[38] = it.clone() }
        player.inventory.boots?.let { playerItems[39] = it.clone() }
        player.inventory.itemInOffHand?.let { playerItems[40] = it.clone() }

        val hasRealItems = playerItems.values.any { it.type != Material.AIR && it.amount > 0 }
        if (!hasRealItems) return

        val totalXP = player.totalExperience

        val grave = plugin.graveManager.createGraveAndGetIt(player, playerItems, totalXP)
            ?: return

        // XP → do grobu
        player.totalExperience = 0
        player.exp = 0f
        player.level = 0

        // brak dropów
        event.drops.clear()
        event.keepInventory = false
        event.droppedExp = 0

        // komunikat
        val message = plugin.messageHandler.stringMessageToComponent(
            "graves",
            "created-grave",
            mapOf(
                "x" to player.location.blockX.toString(),
                "y" to player.location.blockY.toString(),
                "z" to player.location.blockZ.toString()
            )
        )
        player.sendMessage(message)
    }
}
