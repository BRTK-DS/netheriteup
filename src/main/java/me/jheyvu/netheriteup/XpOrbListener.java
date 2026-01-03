package me.jheyvu.netheriteup;

import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class XpOrbListener implements Listener {

    private final NetheriteUpPlugin plugin;

    public XpOrbListener(NetheriteUpPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean isNetheriteArmor(ItemStack item) {
        if (item == null) return false;
        Material t = item.getType();
        return t == Material.NETHERITE_HELMET
                || t == Material.NETHERITE_CHESTPLATE
                || t == Material.NETHERITE_LEGGINGS
                || t == Material.NETHERITE_BOOTS;
    }

    private boolean isNetheriteSword(ItemStack item) {
        return item != null && item.getType() == Material.NETHERITE_SWORD;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPickupXp(PlayerPickupExperienceEvent e) {
        ExperienceOrb orb = e.getExperienceOrb();
        int gained = orb.getExperience();
        if (gained <= 0) return;

        List<ItemStack> targets = new ArrayList<>(5);

        // 1) Zbroja (na graczu)
        ItemStack[] armor = e.getPlayer().getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (isNetheriteArmor(piece)) targets.add(piece);
        }

        // 2) Miecz (w głównej ręce)
        ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
        if (isNetheriteSword(mainHand)) targets.add(mainHand);

        if (targets.isEmpty()) return;

        // Rozdziel XP równo między wszystkie cele
        int per = Math.max(1, gained / targets.size());
        int remainder = gained - (per * targets.size());

        for (int i = 0; i < targets.size(); i++) {
            int add = per + (i == 0 ? remainder : 0); // reszta do pierwszego
            addXpToItem(targets.get(i), add);
        }
    }

    private void addXpToItem(ItemStack item, int addXp) {
        if (addXp <= 0) return;

        int level = ItemData.getLevel(plugin, item);
        int xp = ItemData.getXp(plugin, item);

        xp += addXp;

        while (xp >= Progression.xpToNext(level)) {
            xp -= Progression.xpToNext(level);
            level++;
        }

        ItemData.setLevel(plugin, item, level);
        ItemData.setXp(plugin, item, xp);

        // Hover pasek
        LoreUpdater.apply(plugin, item);
    }
}
