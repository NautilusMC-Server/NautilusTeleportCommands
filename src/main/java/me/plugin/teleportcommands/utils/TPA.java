package me.plugin.teleportcommands.utils;
import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class TPA {
    private TeleportCommands plugin;
    private HashMap<Player, Player> pending; //sending, receiving

    public TPA(TeleportCommands plugin) {
        this.plugin = plugin;
        pending = new HashMap<>();
    }

    public void sendRequestTPA(Player from, Player to, boolean tpahere) {
        from.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&3Sent a teleport request to ") + to.displayName());
        to.sendMessage(from.displayName() +
                ChatColor.translateAlternateColorCodes('&',
                        (tpahere ? " &3would like you to teleport to them!" : " &3Would like to teleport to you!")));
        to.sendMessage(ChatColor.RED + "This request will expire in 60 seconds.");
        to.sendMessage(ChatColor.GRAY + "\"/tpaccept\" to accept, just ignore otherwise.");
        pending.put(from, to);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(pending.get(from).equals(to))
                    pending.put(from, null);
            }
        }, 60 * 20);
    }

    public void acceptTPA(Player accepting, boolean tpahere) {
        for(Player player : pending.keySet()) {
            if(pending.get(player).equals(accepting)) {
                player.sendMessage(accepting.displayName() +
                        ChatColor.translateAlternateColorCodes('&',
                                " &3has accepted your teleport request!"));
                accepting.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&3Accepted request to teleport " + (tpahere ? "" : "to ")) +
                        player.displayName() + (tpahere ? " to you" : ""));
                //teleport them!
                if(tpahere) execute(player, accepting);
                else execute(accepting, player);
            }
            pending.put(player, null);
        }
        accepting.sendMessage(ChatColor.GRAY + "No pending teleport requests.");
    }

    public void execute(Player from, Player to) {
        CustomTeleportEvent tpEvent = new CustomTeleportEvent(from, to.getLocation());
        Bukkit.getPluginManager().callEvent(tpEvent);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!tpEvent.isCancelled()) {
                from.teleport(to);
            }
        }, 5 * 20);
    }

    public void cancelRequest(Player p) {
        if(pending.get(p) != null) {
            pending.get(p).sendMessage(p.displayName() +
                    ChatColor.translateAlternateColorCodes('&',
                            " &ccanceled their teleport request"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7Canceled teleport request to ") +
                    p.displayName());
            pending.put(p, null);
        }
        else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7No pending teleport requests."));
        }
    }

    public void removeFromList(Player p) {
        pending.remove(p);
        for(Player player : pending.keySet()) {
            if(pending.get(player).equals(p)) {
                pending.put(player, null);
            }
        }
    }
}
