package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacancelCommand extends CommandStem {
    public TpacancelCommand(TeleportCommands plugin) {
        super(plugin, "tpacancel", "Cancel sent tpa request", "");
    }

    protected TeleportCommands plugin;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        plugin.tpa().cancelRequest(p);
        return true;
    }
}
