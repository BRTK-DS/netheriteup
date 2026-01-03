# NetheriteUp

NetheriteUp to plugin do **Minecraft Paper 1.21.x**, który dodaje **levelowanie netheritowych itemów** w stylu vanilla.

## Co robi plugin
- Netherite **narzędzia** zdobywają XP za kopanie
- Netherite **miecz i zbroja** zdobywają XP z orbów doświadczenia
- Każdy item ma **własny level i XP**

## Co daje level itemu
- Więcej **slotów na enchanty**
- Możliwość **mieszania konfliktowych enchantów**
  (np. Protection + Fire Protection)
- Wyższe **limity enchantów** (na bazie wartości z Minecraft Wiki – Enchanting)
  - Protection do 6 na netherite zbroi (lvl 30 → 5, lvl 55 → 6)
  - Fire/Blast/Projectile Protection do 5 (lvl 40)
  - Feather Falling do 5 (lvl 35)
  - Swift Sneak do 4 (lvl 50)
  - Sharpness/Smite/Bane do 7 (lvl 35 → 6, lvl 60 → 7)
  - Efficiency do 8 (lvl 25 → 6, lvl 55 → 7, lvl 85 → 8)
  - Fortune do 4 (lvl 70)
  - Sweeping Edge 4 (lvl 45) i Fire Aspect 3 (lvl 50)
  - Reszta kluczowych enchantów (np. Unbreaking, Looting) zostaje na vanilla maxach, żeby nie przegiąć balansu

## Jak to działa
- Wszystko przez **kowadło + enchanted booki**
- Brak GUI, brak modów, czysty vanilla vibe
- Informacje o levelu, XP i enchantach są widoczne **na hoverze itemu**

## Limity enchantów
Poniższe wartości bazują na maksach z Minecraft Wiki i dodają maksymalnie +2/+3 poziomy tylko dla enchantów, które realnie mają sens na późnym etapie gry.

### Netherite zbroja
- **Protection**: 4 vanilla / 5 od lvl 30 / 6 od lvl 55
- **Fire, Blast, Projectile Protection**: 4 vanilla / 5 od lvl 40
- **Feather Falling**: 4 vanilla / 5 od lvl 35
- **Swift Sneak**: 3 vanilla / 4 od lvl 50
- Pozostałe armorowe enchanty (Thorns, Depth Strider, Frost Walker, Soul Speed, Respiration, Aqua Affinity, Unbreaking, Mending, klątwy) zostają na poziomie vanilla

### Netherite miecz
- **Sharpness / Smite / Bane of Arthropods**: 5 vanilla / 6 od lvl 35 / 7 od lvl 60
- **Sweeping Edge**: 3 vanilla / 4 od lvl 45
- **Fire Aspect**: 2 vanilla / 3 od lvl 50
- **Looting, Knockback, Unbreaking, Mending, Curse of Vanishing** bez zmian (max 3 / 2 / 3 / 1 / 1)

### Netherite narzędzia
- **Efficiency**: 5 vanilla / 6 od lvl 25 / 7 od lvl 55 / 8 od lvl 85
- **Fortune**: 3 vanilla / 4 od lvl 70
- **Silk Touch, Unbreaking, Mending, Curse of Vanishing** pozostają na vanilla maxach

## Jak zdobywać wyższe enchanty
1. **Leveluj item** – narzędzia dostają 5 XP za kopanie wartościowych bloków, a miecz + zbroja zbierają XP z orbów (XpListeners i XpOrbListener).
2. **Pilnuj progów** – Progression.capFor wpuszcza wyższe poziomy dopiero po osiągnięciu wymaganego levelu z tabeli powyżej.
3. **Najpierw dobij vanilla** – dopóki item nie ma vanilla maxa (np. Fortune III), każde łączenie zostanie przycięte do tego poziomu.
4. **Auto-unlock** – kiedy tylko item posiada vanilla max i spełnia próg levelowy, plugin sam wbija kolejny tier (np. Fortune III → Fortune IV) bez dodatkowej książki; jeśli osiągniesz próg dopiero później, sprawdzamy to przy każdym zdobyciu XP.
5. **Kombinuj na kowadle** – wrzuć enchanted book + netheritowy item. AnvilEnchantListener scala enchanty vanilla-style, respektuje limit slotów i nowe capy.
6. **Balans** – konflikty można mieszać dopiero od lvl 30, a Unbreaking/Looting nadal kończy się na 3, więc trzeba inwestować w grind, żeby robić naprawdę OP sety.

## Komendy
- `/nup setlevel <poziom>` – tylko dla operatorów; ustawia podany level na trzymanym netheritowym itemie, zeruje bieżące XP i od razu odświeża lore (przydatne do testów).

## Wymagania
- Paper 1.21.x
- Java 21
