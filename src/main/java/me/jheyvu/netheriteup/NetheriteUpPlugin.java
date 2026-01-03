package me.jheyvu.netheriteup;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class NetheriteUpPlugin extends JavaPlugin {

    public NamespacedKey KEY_LEVEL;
    public NamespacedKey KEY_XP;

    @Override
    public void onEnable() {
        KEY_LEVEL = new NamespacedKey(this, "level");
        KEY_XP = new NamespacedKey(this, "xp");

        getServer().getPluginManager().registerEvents(new XpListeners(this), this);
        getServer().getPluginManager().registerEvents(new XpOrbListener(this), this);
        getServer().getPluginManager().registerEvents(new AnvilEnchantListener(this), this);

        getLogger().info("NetheriteUp enabled!");
    }
}
