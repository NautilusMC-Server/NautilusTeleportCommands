package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand extends CommandStem {
    public SethomeCommand(TeleportCommands plugin) {
        super(plugin, "sethome", "Set homes", "createhome");
    }

    protected TeleportCommands plugin;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.GRAY + "Please provide name for new home: /sethome [name]");
            return true;
        }
        plugin.d().addHome(p, args[0]);
        return true;
    }
}
