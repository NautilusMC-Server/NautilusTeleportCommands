package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class HomesCommand extends CommandStem {
    public HomesCommand(TeleportCommands plugin) {
        super(plugin, "homes", "List homes", "");
    }

    public boolean execute(CommandSender sender, String[] strings) {
        if (!(sender instanceof Player)) { //todo allow viewing of other players' homes with argument
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        HashMap<String, Location> homes = plugin.d().homesList(p);
        if (homes.size() == 0) {
            p.sendMessage(ChatColor.GRAY + "You have don't have any homes!");
            p.sendMessage(ChatColor.GRAY + "\"/home set <name>\" to create a home");
            return true;
        }
        p.sendMessage(ChatColor.DARK_AQUA + "-- Homes: --");
        for(String name : homes.keySet()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&f" + name + "&b: &7(" + Math.round(homes.get(name).getX()) + ", " +
                            Math.round(homes.get(name).getZ()) + "), " + homes.get(name).getWorld().getName()));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return execute(commandSender, strings);
    }
}
