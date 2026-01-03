package me.jheyvu.netheriteup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Progression {

    public static int xpToNext(int level) {
        return 150 + (level * 25) + (level * level * 4);
    }

    // +1 slot co 10 lvl, max +5
    public static int extraEnchantSlots(int level) {
        return Math.min(5, level / 10);
    }

    // Od jakiego lvl pozwalamy mieszać konflikty (prot + fireprot itd.)
    public static boolean conflictsUnlocked(int level) {
        return level >= 30;
    }

    // Bazowe "sloty" per item (vanilla vibe, ale jednak limitujemy)
    public static int baseSlots(Material type) {
        // Armor zwykle ma 4–5 enchantów sensownie, tool/sword podobnie
        return switch (type) {
            case NETHERITE_HELMET, NETHERITE_BOOTS -> 5;
            case NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS -> 6;
            case NETHERITE_SWORD -> 6;
            case NETHERITE_PICKAXE, NETHERITE_AXE, NETHERITE_SHOVEL, NETHERITE_HOE -> 6;
            default -> 6;
        };
    }

    public static int maxSlots(Material type, int level) {
        return baseSlots(type) + extraEnchantSlots(level);
    }

    // Podnosimy "cap" tylko dla wybranych (reszta jak vanilla)
    public static int capFor(Enchantment ench, int level) {
        int vanillaMax = ench.getMaxLevel();

        // Protection (vanilla 4) -> do 7
        if (ench == Enchantment.PROTECTION) {
            if (level >= 60) return 7;
            if (level >= 40) return 6;
            if (level >= 20) return 5;
            return 4;
        }

        // Efficiency (vanilla 5) -> do 9
        if (ench == Enchantment.EFFICIENCY) {
            if (level >= 90) return 9;
            if (level >= 75) return 8;
            if (level >= 50) return 7;
            if (level >= 25) return 6;
            return 5;
        }

        return vanillaMax;
    }
}
