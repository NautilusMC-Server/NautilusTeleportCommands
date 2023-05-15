package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeCommand extends CommandStem {
    public DelhomeCommand(TeleportCommands plugin) {
        super(plugin, "delhome", "Show color and formatting codes", "removehome");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.GRAY + "Please provide name for home to delete: /delhome [home name]");
            return true;
        }
        plugin.d().deleteHome(p, args[0]);
        return true;
    }

}
