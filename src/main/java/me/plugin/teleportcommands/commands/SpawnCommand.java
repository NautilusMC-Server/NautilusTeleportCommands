package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends CommandStem {
    public SpawnCommand(TeleportCommands plugin) {
        super(plugin, "spawn", "Teleports to spawn", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        Location to = plugin.getServer().getWorld("world").getSpawnLocation();
        CustomTeleportEvent tpEvent = new CustomTeleportEvent(p, to);
        Bukkit.getPluginManager().callEvent(tpEvent);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!tpEvent.isCancelled()) {
                p.sendMessage(ChatColor.AQUA + "Teleporting to spawn!");
                p.teleport(to);
            }
        }, 5 * 20);
        return true;
    }
}
