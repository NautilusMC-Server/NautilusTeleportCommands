package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HomeCommand extends CommandStem {
    public HomeCommand(TeleportCommands plugin) {
        super(plugin, "home", "Teleports to specified home", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length < 1) {
            p.sendMessage(ChatColor.GRAY + "Please provide name of home: /home [home]");
            HashMap<String, Location> homes = plugin.d().homesList(p);
            p.sendMessage(ChatColor.DARK_AQUA + "-- Homes: --"); //todo maybe do all the output code in one place (grimacing at this rn)
            for(String name : homes.keySet()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&f" + name + "&b: &7(" + Math.round(homes.get(name).getX()) + ", " +
                                Math.round(homes.get(name).getZ()) + "), " + homes.get(name).getWorld().getName()));
            }
            return true;
        }
        String name = args[0];
        if (!plugin.d().hasHome(p,name)) {
            p.sendMessage(ChatColor.GRAY + "Home " + name + " doesn't exist!");
            return true;
        }
        Location to = plugin.d().getHomeLocation(p, name);
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

}
