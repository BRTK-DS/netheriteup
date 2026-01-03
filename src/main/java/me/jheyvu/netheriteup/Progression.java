package me.jheyvu.netheriteup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Progression {

    private static final Map<Enchantment, CapRule> CAP_RULES = new HashMap<>();

    static {
        cap(Enchantment.PROTECTION)
                .at(30, 5)
                .at(55, 6);

        cap(Enchantment.FIRE_PROTECTION)
                .at(40, 5);

        cap(Enchantment.BLAST_PROTECTION)
                .at(40, 5);

        cap(Enchantment.PROJECTILE_PROTECTION)
                .at(40, 5);

        cap(Enchantment.FEATHER_FALLING)
                .at(35, 5);

        cap(Enchantment.SWIFT_SNEAK)
                .at(50, 4);

        cap(Enchantment.EFFICIENCY)
                .at(25, 6)
                .at(55, 7)
                .at(85, 8);

        cap(Enchantment.FORTUNE)
                .at(70, 4);

        cap(Enchantment.SHARPNESS)
                .at(35, 6)
                .at(60, 7);

        cap(Enchantment.SMITE)
                .at(35, 6)
                .at(60, 7);

        cap(Enchantment.BANE_OF_ARTHROPODS)
                .at(35, 6)
                .at(60, 7);

        cap(Enchantment.SWEEPING_EDGE)
                .at(45, 4);

        cap(Enchantment.FIRE_ASPECT)
                .at(50, 3);
    }

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

    public static int capFor(Enchantment ench, int level) {
        CapRule rule = CAP_RULES.get(ench);
        if (rule == null) {
            return ench.getMaxLevel();
        }
        return rule.capFor(level);
    }

    public static int autoUnlockLevel(Enchantment ench, int itemLevel, int currentLevel) {
        int vanilla = ench.getMaxLevel();
        if (currentLevel < vanilla) return currentLevel;

        int cap = capFor(ench, itemLevel);
        if (cap > currentLevel) return cap;
        return currentLevel;
    }

    public static boolean applyAutoUnlocks(ItemStack item, int itemLevel) {
        if (item == null) return false;

        boolean changed = false;
        Map<Enchantment, Integer> enchants = new HashMap<>(item.getEnchantments());
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            Enchantment ench = entry.getKey();
            int current = entry.getValue();
            int unlocked = autoUnlockLevel(ench, itemLevel, current);
            if (unlocked > current) {
                item.addUnsafeEnchantment(ench, unlocked);
                changed = true;
            }
        }
        return changed;
    }

    private static CapRule cap(Enchantment enchantment) {
        CapRule rule = new CapRule(enchantment.getMaxLevel());
        CAP_RULES.put(enchantment, rule);
        return rule;
    }

    private static final class CapRule {
        private final NavigableMap<Integer, Integer> caps = new TreeMap<>();

        private CapRule(int base) {
            caps.put(0, base);
        }

        private CapRule at(int requiredLevel, int cap) {
            caps.put(requiredLevel, cap);
            return this;
        }

        private int capFor(int level) {
            Map.Entry<Integer, Integer> entry = caps.floorEntry(level);
            if (entry == null) {
                return caps.firstEntry().getValue();
            }
            return entry.getValue();
        }
    }
}
