package me.jheyvu.netheriteup;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreUpdater {

    private static final String START = ChatColor.DARK_GRAY + "⟦NetheriteUp⟧";
    private static final String END   = ChatColor.DARK_GRAY + "⟦/NetheriteUp⟧";

    public static void apply(NetheriteUpPlugin plugin, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        int level = ItemData.getLevel(plugin, item);
        int xp = ItemData.getXp(plugin, item);

        // Jeśli item jeszcze "nie żyje" (lvl 0 i xp 0), to nie dodawajmy sekcji
        if (level <= 0 && xp <= 0) {
            removeSection(meta);
            item.setItemMeta(meta);
            return;
        }

        int next = Progression.xpToNext(level);

        // Pasek 10 segmentów
        double pct = Math.max(0, Math.min(1.0, xp / (double) Math.max(1, next)));
        int bars = 10;
        int filled = (int) Math.floor(pct * bars);
        if (filled > bars) filled = bars;

        String bar = ChatColor.GREEN + "█".repeat(filled)
                + ChatColor.DARK_GRAY + "░".repeat(bars - filled);

        int percent = (int) Math.round(pct * 100);

        // Sloty enchantów + konflikty
        int maxSlots = Progression.maxSlots(item.getType(), level);
        int used = item.getEnchantments().size();
        boolean conflicts = Progression.conflictsUnlocked(level);

        List<String> section = new ArrayList<>();
        section.add(START);
        section.add(ChatColor.GOLD + "NetheriteUp");
        section.add(ChatColor.YELLOW + "Lvl: " + ChatColor.WHITE + level);
        section.add(ChatColor.YELLOW + "XP: " + ChatColor.WHITE + xp
                + ChatColor.GRAY + "/" + next
                + ChatColor.DARK_GRAY + " [" + bar + ChatColor.DARK_GRAY + "] "
                + ChatColor.GRAY + percent + "%");
        section.add(ChatColor.YELLOW + "Enchants: " + ChatColor.WHITE + used + ChatColor.GRAY + "/" + maxSlots);
        section.add(ChatColor.YELLOW + "Conflicts: " + (conflicts ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF"));
        section.add(END);

        // Nie niszcz innych lore — usuń starą sekcję NetheriteUp i dopisz nową na końcu
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore = removeSection(lore);
        lore.addAll(section);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private static void removeSection(ItemMeta meta) {
        List<String> lore = meta.getLore();
        if (lore == null) return;
        meta.setLore(removeSection(lore));
    }

    private static List<String> removeSection(List<String> lore) {
        List<String> out = new ArrayList<>();
        boolean skipping = false;

        for (String line : lore) {
            if (line != null && line.equals(START)) {
                skipping = true;
                continue;
            }
            if (skipping) {
                if (line != null && line.equals(END)) {
                    skipping = false;
                }
                continue;
            }
            out.add(line);
        }
        return out;
    }
}
