package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class WarpsCommand extends CommandStem {
    public WarpsCommand(TeleportCommands plugin) {
        super(plugin, "warps", "Lists warps", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&d-- &3&lWarps: &r&d--"));
        HashMap<String, Location> map = plugin.d().warpsList();
        for(String s : map.keySet()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&f" + s + "&d: &7(" + Math.round(map.get(s).getX()) + " " + Math.round(map.get(s).getZ())
                            + "), " + map.get(s).getWorld().getName()));
        }
        return true;
    }
}
