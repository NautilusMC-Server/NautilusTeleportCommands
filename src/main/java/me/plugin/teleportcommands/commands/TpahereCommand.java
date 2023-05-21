package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TpahereCommand extends CommandStem {
    public TpahereCommand(TeleportCommands plugin) {
        super(plugin, "tpahere", "Send tpahere request", "");
    }

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
                    p.sendMessage(ChatColor.GRAY + "You can't tp yourself to yourself :p");
                    return true;
                }
                plugin.tpa().sendRequestTPA(p, to, true);
                return true;
            }
        }
        p.sendMessage(ChatColor.GRAY + "Player " + args[0] + " is not online.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return execute(commandSender, strings);
    }
}
