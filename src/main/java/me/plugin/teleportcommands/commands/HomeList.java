package me.plugin.teleportcommands.commands;

import me.kodysimpson.simpapi.command.SubCommand;
import me.plugin.teleportcommands.utils.Home;
import me.plugin.teleportcommands.utils.HomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeList extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Lists all of your homes";
    }

    @Override
    public String getSyntax() {
        return "/home list";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return;
        }
        Player p = (Player) commandSender;
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

    @Override
    public List<String> getSubcommandArguments(Player player, String[] strings) {
        return null;
    }
}
