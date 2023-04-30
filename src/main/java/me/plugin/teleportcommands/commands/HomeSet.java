package me.plugin.teleportcommands.commands;

import me.kodysimpson.simpapi.command.SubCommand;
import me.plugin.teleportcommands.utils.HomeStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeSet extends SubCommand {
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("create");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Sets a new home";
    }

    @Override
    public String getSyntax() {
        return "/home set <name>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player p = (Player) sender;
        String str = "";
        p.sendMessage("Creating Home...");
        if (args.length < 2) {
            p.sendMessage("Please provide name for new home");
            p.sendMessage(getSyntax());
            return;
        }
        for (int i = 1; i < args.length - 1; i++) {
            str += args[i] + " ";
        }
        str += args[args.length - 1];
        p.sendMessage(str);
        if (HomeStorage.findHome(p, str) != null) {
            p.sendMessage("Home " + str + " already exists!");
            return;
        }

            HomeStorage.createHome(p, p.getLocation(), str);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return Arrays.asList(args);
    }
}
