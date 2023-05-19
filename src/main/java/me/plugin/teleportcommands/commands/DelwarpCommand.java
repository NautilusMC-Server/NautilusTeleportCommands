package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelwarpCommand extends CommandStem {
    public DelwarpCommand(TeleportCommands plugin) {
        super(plugin, "delwarp", "Delete warp", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        if(!sender.hasPermission("group.staff")) {
            sender.sendMessage(ChatColor.GRAY + "You do not have permission to use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.GRAY + "Please provide name for warp to delete: /delwarp [warp name]");
            return true;
        }
        plugin.d().deleteWarp(p, args[0]);
        return true;
    }
}
