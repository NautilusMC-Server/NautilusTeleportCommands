package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarpCommand extends CommandStem {
    public WarpCommand(TeleportCommands plugin) {
        super(plugin, "warp", "Teleports to specified warp", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length < 1) {
            p.sendMessage(ChatColor.GRAY + "Please provide name of home: /warp [home]");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&d-- &b&lWarps: &r&d--"));
            HashMap<String, Location> map = plugin.d().warpsList();
            for(String s : map.keySet()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&f" + s + "&d: &7(" + Math.round(map.get(s).getX()) + " " + Math.round(map.get(s).getZ())
                        + "), " + map.get(s).getWorld().getName()));
            }
            return true;
        }
        String name = args[0];
        if (!plugin.d().existsWarp(name)) {
            p.sendMessage(ChatColor.GRAY + "Warp " + name + " doesn't exist!");
            return true;
        }
        Location to = plugin.d().getWarpLocation(name);
        CustomTeleportEvent tpEvent = new CustomTeleportEvent(p, to);
        Bukkit.getPluginManager().callEvent(tpEvent);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!tpEvent.isCancelled()) {
                p.sendMessage(ChatColor.AQUA + "Teleporting!");
                p.teleport(to);
            }
        }, 5 * 20);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) return null;

        List<String> out = new ArrayList<>(plugin.d().warpsList().keySet());

        return out.stream().filter(str->str.startsWith(strings[strings.length-1])).toList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return execute(commandSender, strings);
    }
}
