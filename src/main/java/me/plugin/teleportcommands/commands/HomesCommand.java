package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand extends CommandStem {
    public HomesCommand(TeleportCommands plugin) {
        super(plugin, "homes", "List homes", "");
    }

    public boolean execute(CommandSender sender, String[] strings) {
        if (!(sender instanceof Player)) { //todo allow viewing of other players' homes with argument
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        Player p = (Player) sender;
        int count = 0;
        for (int i = 0; i < HomeStorage.getHomes().size(); i++) {
            Home home = HomeStorage.getHomes().get(i);
            if (home.getPlayer().equals(p)) {
                count++;
            }
        }
        if (count == 0) {
            p.sendMessage("You have don't have any homes!");
            p.sendMessage("\"/home set <name>\" to create a home");
            return;
        }
        p.sendMessage("Homes:");
        for (int i = 0; i < HomeStorage.getHomes().size(); i++) {
            Home home = HomeStorage.getHomes().get(i);
            if (home.getPlayer().equals(p)) {
                p.sendMessage("-" + home.getName());
            }
        }
    }

}
