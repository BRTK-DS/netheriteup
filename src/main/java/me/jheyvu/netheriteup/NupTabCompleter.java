package me.jheyvu.netheriteup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NupTabCompleter implements TabCompleter {

    private static final List<String> SUBCOMMANDS = List.of("setlevel");
    private static final List<String> LEVEL_HINTS = Arrays.asList("0", "5", "10", "30", "60");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filterMatches(args[0], SUBCOMMANDS);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("setlevel")) {
            return filterMatches(args[1], LEVEL_HINTS);
        }
        return Collections.emptyList();
    }

    private List<String> filterMatches(String token, List<String> options) {
        if (token == null || token.isEmpty()) {
            return new ArrayList<>(options);
        }
        List<String> matches = new ArrayList<>();
        String lower = token.toLowerCase();
        for (String option : options) {
            if (option.toLowerCase().startsWith(lower)) {
                matches.add(option);
            }
        }
        return matches;
    }
}
