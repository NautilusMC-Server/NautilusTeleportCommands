package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            p.sendMessage("Please provide name of home: /home [home]");
            //todo print homes list
            return true;
        }
        String name = args[0];
        if (HomeStorage.findHome(p, str) == null) {
            p.sendMessage("Home " + str + " doesn't exist!");
            return;
        }
        Home home = HomeStorage.findHome(p, str);
        CustomTeleportEvent tpEvent = new CustomTeleportEvent(p, home.getLocation(), home);
        Bukkit.getPluginManager().callEvent(tpEvent);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!tpEvent.isCancelled()) {
                p.teleport(home.getLocation());
            }
        }, 5 * 20);
    }

}
