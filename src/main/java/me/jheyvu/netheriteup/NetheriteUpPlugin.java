package me.jheyvu.netheriteup;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        getCommand("nup").setTabCompleter(new NupTabCompleter());

        getLogger().info("NetheriteUp enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("nup")) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "NetheriteUp " + ChatColor.GRAY + ">> "
                    + ChatColor.YELLOW + "/" + label + " setlevel <poziom>");
            return true;
        }

        if (args[0].equalsIgnoreCase("setlevel")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Tylko gracz moze ustawic poziom narzedzia.");
                return true;
            }

            if (!sender.hasPermission("netheriteup.setlevel")) {
                sender.sendMessage(ChatColor.RED + "Ta komenda jest tylko dla operatorow.");
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Uzycie: /" + label + " setlevel <poziom>");
                return true;
            }

            int level;
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "Podaj liczbe calkowita.");
                return true;
            }

            if (level < 0) {
                sender.sendMessage(ChatColor.RED + "Poziom nie moze byc ujemny.");
                return true;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || item.getType().isAir()) {
                sender.sendMessage(ChatColor.RED + "Musisz trzymac w rece narzedzie/zbroje.");
                return true;
            }

            ItemData.setLevel(this, item, level);
            ItemData.setXp(this, item, 0);
            Progression.applyAutoUnlocks(item, level);
            LoreUpdater.apply(this, item);

            sender.sendMessage(ChatColor.GREEN + "Ustawiono poziom na " + level + ".");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Nieznana komenda NetheriteUp.");
        return true;
    }
}
