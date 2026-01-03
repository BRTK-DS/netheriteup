package me.jheyvu.netheriteup;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemData {

    public static int getLevel(NetheriteUpPlugin plugin, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.getOrDefault(plugin.KEY_LEVEL, PersistentDataType.INTEGER, 0);
    }

    public static int getXp(NetheriteUpPlugin plugin, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.getOrDefault(plugin.KEY_XP, PersistentDataType.INTEGER, 0);
    }

    public static void setLevel(NetheriteUpPlugin plugin, ItemStack item, int level) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(plugin.KEY_LEVEL, PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);
    }

    public static void setXp(NetheriteUpPlugin plugin, ItemStack item, int xp) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(plugin.KEY_XP, PersistentDataType.INTEGER, xp);
        item.setItemMeta(meta);
    }
}
