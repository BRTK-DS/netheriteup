package me.jheyvu.netheriteup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AnvilEnchantListener implements Listener {

    private final NetheriteUpPlugin plugin;

    public AnvilEnchantListener(NetheriteUpPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean isUpgradeableNetherite(ItemStack item) {
        if (item == null) return false;
        Material t = item.getType();
        return t.name().startsWith("NETHERITE_");
    }

    @EventHandler
    public void onPrepare(PrepareAnvilEvent e) {
        AnvilInventory inv = e.getInventory();
        ItemStack left = inv.getItem(0);
        ItemStack right = inv.getItem(1);

        if (!isUpgradeableNetherite(left)) return;
        if (right == null || right.getType().isAir()) return;

        int level = ItemData.getLevel(plugin, left);
        boolean allowConflicts = Progression.conflictsUnlocked(level);

        // Obsługujemy: książka z enchantami -> item
        if (right.getType() == Material.ENCHANTED_BOOK && right.getItemMeta() instanceof EnchantmentStorageMeta bookMeta) {
            Map<Enchantment, Integer> merged = new HashMap<>(left.getEnchantments());
            Map<Enchantment, Integer> adding = bookMeta.getStoredEnchants();

            for (Map.Entry<Enchantment, Integer> en : adding.entrySet()) {
                Enchantment ench = en.getKey();
                int addLvl = en.getValue();

                // konflikt?
                if (!allowConflicts && conflictsWithAny(ench, merged.keySet())) {
                    continue; // po prostu pomijamy ten enchant
                }

                int current = merged.getOrDefault(ench, 0);

                // vanilla-like łączenie poziomów
                int newLvl;
                if (current == addLvl) newLvl = current + 1;
                else newLvl = Math.max(current, addLvl);

                // cap z levela itemu
                int cap = Progression.capFor(ench, level);
                newLvl = Math.min(newLvl, cap);

                merged.put(ench, newLvl);
            }

            // Sloty enchantów
            int maxSlots = Progression.maxSlots(left.getType(), level);
            if (merged.size() > maxSlots) {
                e.setResult(null);
                return;
            }

            // Tworzymy wynik: klon lewego itemu + enchanty + zachowaj PDC
            ItemStack result = left.clone();

            // usuń stare ench
            for (Enchantment ench : result.getEnchantments().keySet().toArray(new Enchantment[0])) {
                result.removeEnchantment(ench);
            }

            // dodaj nowe (unsafe żeby przyjął wyższe poziomy)
            for (Map.Entry<Enchantment, Integer> en : merged.entrySet()) {
                result.addUnsafeEnchantment(en.getKey(), en.getValue());
            }

            // rename z kowadła (jeśli gracz wpisał nazwę)
            String rename = inv.getRenameText();
            if (rename != null && !rename.isBlank()) {
                ItemMeta meta = result.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(rename);
                    result.setItemMeta(meta);
                }
            }

            // odśwież lore hover
            LoreUpdater.apply(plugin, result);

            // ustaw wynik
            e.setResult(result);

            // prościutki koszt (żeby było vanilla-ish)
            inv.setRepairCost(Math.min(39, 1 + merged.size()));
            return;
        }

        // Na razie: inne przypadki zostawiamy vanilla
    }

    private boolean conflictsWithAny(Enchantment ench, Set<Enchantment> existing) {
        for (Enchantment other : existing) {
            if (ench.conflictsWith(other) || other.conflictsWith(ench)) return true;
        }
        return false;
    }
}
