package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends CommandStem {
    public TpaCommand(TeleportCommands plugin) {
        super(plugin, "tpa", "Send tpa request", "");
    }

    protected TeleportCommands plugin;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        for(Player to : Bukkit.getOnlinePlayers()) {
            if(to.getName().equalsIgnoreCase(args[0])) {
                if(to.equals(p)) {
                    p.sendMessage(ChatColor.GRAY + "You can't tp to yourself :p");
                    return true;
                }
                plugin.tpa().sendRequestTPA(p, to, false);
                return true;
            }
        }
        p.sendMessage(ChatColor.GRAY + "Player " + args[0] + " is not online.");
        return true;
    }
}
