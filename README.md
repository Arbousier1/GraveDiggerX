# GraveDiggerX

GraveDiggerX to zaawansowany plugin dla serwerów Paper, który automatycznie tworzy nagrobki po śmierci gracza. Plugin zabezpiecza wyposażenie, doświadczenie oraz miejsce śmierci gracza, zapewniając czytelne informacje wizualne i łatwe odzyskiwanie utraconych przedmiotów.

## Najważniejsze funkcje
- **Automatyczne nagrobki** – po śmierci gracza jego ekwipunek i doświadczenie trafiają do nagrobka w postaci głowy gracza wraz z hologramem informacyjnym.
- **Interaktywne odzyskiwanie ekwipunku** – właściciel może otworzyć GUI nagrobka lub zebrać zawartość błyskawicznie przytrzymując kucanie i klikając nagrobek.
- **Ochrona miejsca śmierci** – nagrobki są odporne na zniszczenia przez eksplozje, płyny, tłoki, lejek czy moby, a złodziei odrzuca specjalny efekt, dopóki nagrobek nie wygaśnie.
- **Duch opiekun** – nad nagrobkiem pojawia się świecący Allay, który pozostaje do czasu zebrania łupów lub wygaśnięcia nagrobka (można go wyłączyć w konfiguracji).
- **Automatyczne czyszczenie** – nagrobki są usuwane po zdefiniowanym czasie, a właściciel otrzymuje odliczanie w pasku akcji.
- **Trwałe przechowywanie** – dane nagrobków zapisywane są w `data.json`, dzięki czemu nie znikają po restarcie serwera.

## Wymagania i instalacja
1. Wymagany jest serwer Paper w wersji 1.21.10 (lub zgodnej).【F:build.gradle.kts†L21-L47】
2. Pobierz najnowszą wersję GraveDiggerX i umieść plik JAR w katalogu `plugins/`.
3. Uruchom serwer, aby wygenerować pliki konfiguracyjne i językowe.
4. Dostosuj konfigurację zgodnie z potrzebami i zrestartuj serwer lub użyj komendy `/gravediggerx reload`.

## Konfiguracja
Najważniejsze opcje znajdują się w pliku `config.yml`:

| Ścieżka | Opis |
| --- | --- |
| `graves.grave-despawn` | Czas (sekundy) po którym nagrobek zostaje usunięty. |
| `graves.max-per-player` | Limit aktywnych nagrobków na gracza; przekroczenie blokuje tworzenie nowych nagrobków. |
| `graves.protected-effect-cooldown` | Czas odnowienia (ms) efektu odrzutu dla obcych graczy próbujących okraść świeży nagrobek. |
| `graves.protection.*` | Włącza/wyłącza ochronę przed eksplozjami, płynami, mobami, tłokami i lejkowaniem przedmiotów. |
| `spirits.enabled` | Steruje pojawianiem się ducha pilnującego nagrobek. |
| `language` | Wybór pliku językowego (`EN`/`PL`). Możesz dostosować wiadomości w `lang/messages_*.yml`. |
| `update.*` | Automatyczne sprawdzanie i pobieranie aktualizacji (Hangar/GitHub/Modrinth). |
| `stats.enabled` | Włącza zbieranie anonimowych statystyk użycia. |

## Komendy i uprawnienia
Podstawowa komenda pluginu to `/gravediggerx` (skrót `/grx`). Dostępne są następujące podkomendy:

| Komenda | Działanie | Wymagane uprawnienie |
| --- | --- | --- |
| `/gravediggerx help` | Wyświetla listę dostępnych poleceń oraz podstawowe informacje.| `grx.cmd.help` |
| `/gravediggerx reload` | Przeładowuje konfigurację, wiadomości oraz odświeża dane pluginu bez restartu serwera.| `grx.cmd.reload` |
| `/gravediggerx list` | Pokazuje właścicielowi współrzędne wszystkich jego aktywnych nagrobków.| `grx.cmd.list` |
| `/gravediggerx admin list <gracz>` | Wymienia wszystkie nagrobki konkretnego gracza (dla administracji).| `grx.cmd.admin` |

Dodatkowe uprawnienia:

| Uprawnienie | Opis |
| --- | --- |
| `grx.opengrave` | Pozwala właścicielowi otworzyć GUI nagrobka i zebrać przedmioty z przycisku „Collect All”. |
| `grx.owner` | Uprawnienie nadrzędne dające pełny dostęp do wszystkich funkcji pluginu. |
| `grx.*` | Wildcard udostępniający wszystkie uprawnienia pluginu. |

> **Wskazówka:** Operatorzy (OP) mają pełny dostęp do wszystkich funkcji pluginu bez potrzeby nadawania dodatkowych uprawnień.


## Wsparcie i rozwój
Plugin został przygotowany z myślą o serwerach survivalowych, hardcore i RPG. Zgłaszaj sugestie oraz błędy na oficjalnym serwerze Discord lub w repozytorium projektu, abyśmy mogli kontynuować rozwój GraveDiggerX.

