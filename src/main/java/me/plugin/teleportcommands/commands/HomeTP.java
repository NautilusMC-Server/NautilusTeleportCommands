package me.plugin.teleportcommands.commands;

import me.kodysimpson.simpapi.command.SubCommand;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import me.plugin.teleportcommands.utils.Home;
import me.plugin.teleportcommands.utils.HomeStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class HomeTP extends SubCommand {
    private static Plugin plugin;
    public HomeTP() {}
    public HomeTP(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Teleports to specified home";
    }

    @Override
    public String getSyntax() {
        return "/home tp <name>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player p = (Player) sender;
        String str = "";
        if (args.length < 2) {
            p.sendMessage("Please provide name for home");
            p.sendMessage(getSyntax());
            return;
        }
        for (int i = 1; i < args.length - 1; i++) {
            str += args[i] + " ";
        }
        str += args[args.length - 1];
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

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
