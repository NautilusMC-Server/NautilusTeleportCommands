package me.plugin.teleportcommands.commands;

import me.kodysimpson.simpapi.command.SubCommand;
import me.plugin.teleportcommands.utils.HomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeDelete extends SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("remove");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Deletes an existing home";
    }

    @Override
    public String getSyntax() {
        return "/home delete <name>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player p = (Player) sender;
        String str = "";
        if (args.length < 2) {
            p.sendMessage("Please provide name for home to delete");
            p.sendMessage(getSyntax());
            return;
        }
        for (int i = 1; i < args.length - 1; i++) {
            str += args[i] + " ";
        }
        str += args[args.length - 1];
        if (HomeStorage.findHome(p, str) == null) {
            p.sendMessage("Home " + str + " doesn't exists!");
            return;
        }
        HomeStorage.deleteHome(p, str);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
