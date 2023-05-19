package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand extends CommandStem {
    public TpacceptCommand(TeleportCommands plugin) {
        super(plugin, "tpaccept", "Accept tpa request", "tpyes");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        plugin.tpa().acceptTPA(p);
        return true;
    }
}
