package me.jheyvu.netheriteup;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class XpListeners implements Listener {

    private final NetheriteUpPlugin plugin;

    public XpListeners(NetheriteUpPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean isNetheriteTool(ItemStack item) {
        if (item == null) return false;
        Material t = item.getType();
        return t == Material.NETHERITE_PICKAXE
                || t == Material.NETHERITE_AXE
                || t == Material.NETHERITE_SHOVEL
                || t == Material.NETHERITE_HOE
                || t == Material.NETHERITE_SWORD;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        ItemStack held = e.getPlayer().getInventory().getItemInMainHand();
        if (!isNetheriteTool(held)) return;

        Block b = e.getBlock();
        Material type = b.getType();

        // szybka anty-farma na start
        if (type == Material.AIR) return;
        if (type == Material.DIRT || type == Material.NETHERRACK) return;

        int level = ItemData.getLevel(plugin, held);
        int xp = ItemData.getXp(plugin, held);

        xp += 5;

        while (xp >= Progression.xpToNext(level)) {
            xp -= Progression.xpToNext(level);
            level++;
        }

        ItemData.setLevel(plugin, held, level);
        ItemData.setXp(plugin, held, xp);

        Progression.applyAutoUnlocks(held, level);
        LoreUpdater.apply(plugin, held);
    }
}
