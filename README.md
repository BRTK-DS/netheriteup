<div align="center">

# 🔥 NetheriteUp

**Vanilla-style Item Leveling for Paper 1.21.x**

[![Java Version](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://jdk.java.net/21/)
[![Platform](https://img.shields.io/badge/Platform-Paper%201.21-A4C639?style=for-the-badge&logo=paper&logoColor=white)](https://papermc.io/)
[![License](https://img.shields.io/github/license/BRTK-DS/netheriteup?style=for-the-badge&color=blue)](LICENSE)

---

<p align="left">
<b>NetheriteUp</b> to plugin, który wprowadza system levelowania netheritowych przedmiotów, zachowując klimat czystego Minecrafta. Żadnych GUI, żadnych dziwnych komend dla graczy – wszystko opiera się na mechanice kowadła, kopaniu i zbieraniu doświadczenia.
</p>

</div>

## ✨ Główne Cechy

* **⚔️ Levelowanie Ekwipunku:** Każde narzędzie, miecz i element zbroi z netheritu ma swój własny poziom i pasek XP.
* **🔓 Przełamywanie Limitów:** Odblokuj enchanty niedostępne w zwykłej grze, np. **Efficiency VIII**, **Sharpness VII** czy **Protection VI**.
* **⚡ Mieszanie Konfliktów:** Na wysokich poziomach możesz łączyć enchanty, które normalnie się wykluczają (np. *Protection* + *Fire Protection*).
* **🛠️ Vanilla Feel:** Wszystkie informacje widoczne są w opisie przedmiotu (lore), a ulepszanie odbywa się w kowadle.

---

## 🎮 Jak to działa?

### 1. Zdobywanie XP Przedmiotu
Twój sprzęt zdobywa doświadczenie w trakcie używania:
* **⛏️ Narzędzia:** Otrzymują XP za kopanie wartościowych bloków (5 XP/blok).
* **⚔️🛡️ Miecze i Zbroje:** Absorbują XP bezpośrednio z orbów doświadczenia (XP Orbs), które zbierasz postacią.

### 2. Ulepszanie Enchantów
System działa automatycznie lub przez kowadło:
* **Auto-Unlock:** Jeśli masz na przedmiocie maksymalny vanilla enchant (np. Fortune III) i wbijesz odpowiedni poziom itemu, plugin **automatycznie** podniesie go na wyższy tier (np. na Fortune IV).
* **Kowadło:** Możesz łączyć itemy z książkami (Enchanted Books). Jeśli Twój item ma odpowiedni poziom, kowadło pozwoli na nałożenie wyższego enchantu.

### 3. Łączenie Konfliktów
Normalnie nie możesz mieć *Protection* i *Blast Protection* na jednym napierśniku. W NetheriteUp, po osiągnięciu **30 poziomu przedmiotu**, kowadło pozwoli Ci połączyć te enchanty!

---

## 📈 Progresja i Limity

Poniższe tabele przedstawiają wymagane poziomy przedmiotu, aby odblokować potężniejsze enchanty.
*Enchanty niewymienione poniżej (np. Unbreaking, Mending) pozostają na poziomach Vanilla dla zachowania balansu.*

### 🛡️ Zbroja (Armor)

| Enchant | Vanilla Max | Tier 1 (Wymagany Lvl) | Tier 2 (Wymagany Lvl) |
| :--- | :---: | :---: | :---: |
| **Protection** | IV | **V** (Lvl 30) | **VI** (Lvl 55) |
| **Fire/Blast/Proj. Protection** | IV | **V** (Lvl 40) | — |
| **Feather Falling** | IV | **V** (Lvl 35) | — |
| **Swift Sneak** | III | **IV** (Lvl 50) | — |
| *Mieszanie konfliktów* | ❌ | ✅ **Od Lvl 30** | — |

### ⚔️ Miecz (Sword)

| Enchant | Vanilla Max | Tier 1 (Wymagany Lvl) | Tier 2 (Wymagany Lvl) |
| :--- | :---: | :---: | :---: |
| **Sharpness / Smite / Bane** | V | **VI** (Lvl 35) | **VII** (Lvl 60) |
| **Sweeping Edge** | III | **IV** (Lvl 45) | — |
| **Fire Aspect** | II | **III** (Lvl 50) | — |

### ⛏️ Narzędzia (Tools)

| Enchant | Vanilla Max | Tier 1 (Wymagany Lvl) | Tier 2 (Wymagany Lvl) | Tier 3 (Wymagany Lvl) |
| :--- | :---: | :---: | :---: | :---: |
| **Efficiency** | V | **VI** (Lvl 25) | **VII** (Lvl 55) | **VIII** (Lvl 85) |
| **Fortune** | III | **IV** (Lvl 70) | — | — |

---

## 💻 Komendy Administratora

Plugin nie wymaga komend do działania dla graczy. Dostępna jest komenda administracyjna do testów i zarządzania:

```bash
/nup setlevel <poziom>