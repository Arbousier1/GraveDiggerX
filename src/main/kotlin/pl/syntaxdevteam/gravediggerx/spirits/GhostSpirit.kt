package pl.syntaxdevteam.gravediggerx.spirits

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.Allay
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import org.bukkit.NamespacedKey
import pl.syntaxdevteam.gravediggerx.GraveDiggerX
import java.util.UUID

class GhostSpirit(
    val plugin: GraveDiggerX,
    val graveOwnerId: UUID,
    val graveLocation: Location,
    val ownerName: String
) {
    var entity: Entity? = null
    var isAlive: Boolean = true
    private var taskId: Int = -1

    fun spawn() {
        val world = graveLocation.world ?: return
        val exactLoc = Location(
            world,
            graveLocation.blockX + 0.5,
            graveLocation.blockY + 2.7,
            graveLocation.blockZ + 0.5
        )

        val ghost = world.spawn(exactLoc, Allay::class.java) { allay ->
            allay.isInvulnerable = true
            allay.isCollidable = false
            allay.canPickupItems = false
            allay.isGlowing = true
            allay.customName(Component.text("Spirit $ownerName"))
            allay.isCustomNameVisible = true
            allay.persistentDataContainer.set(
                NamespacedKey(plugin, "ghost_spirit"),
                PersistentDataType.STRING,
                graveOwnerId.toString()
            )
            allay.setGravity(false)
            allay.setAI(false)
            allay.velocity = org.bukkit.util.Vector(0, 0, 0)
        }

        this.entity = ghost
        taskId = plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, {
            if (!isAlive || entity == null || entity?.isDead == true) {
                if (taskId >= 0) {
                    plugin.server.scheduler.cancelTask(taskId)
                    taskId = -1
                }
                return@scheduleSyncRepeatingTask
            }

            val w = entity!!.world
            val strictLoc = Location(
                w,
                graveLocation.blockX + 0.5,
                graveLocation.blockY + 2.7,
                graveLocation.blockZ + 0.5
            )
            entity!!.velocity = org.bukkit.util.Vector(0, 0, 0)
            entity!!.teleport(strictLoc)
            w.spawnParticle(org.bukkit.Particle.SOUL, strictLoc, 2, 0.2, 0.2, 0.2, 0.05)

            val allay = entity as? Allay ?: return@scheduleSyncRepeatingTask
            val closestPlayer = w.players
                .filter { it.world == w }
                .minByOrNull { it.location.distance(strictLoc) }
            
            if (closestPlayer != null && strictLoc.distance(closestPlayer.location) < 50.0) {
                val lookLoc = strictLoc.clone()
                val direction = closestPlayer.eyeLocation.subtract(strictLoc).toVector().normalize()
                lookLoc.setDirection(direction)
                allay.teleport(lookLoc)
            }
        }, 1L, 1L)
    }

    fun despawn() {
        isAlive = false
        if (taskId >= 0) {
            plugin.server.scheduler.cancelTask(taskId)
            taskId = -1
        }
        if (entity != null) {
            try {
                entity!!.remove()
            } catch (e: Exception) {
            }
        }
        entity = null
    }
}