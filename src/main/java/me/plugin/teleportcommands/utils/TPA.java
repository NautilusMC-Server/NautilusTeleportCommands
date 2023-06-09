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
    private HashMap<Player, HashMap<Player, Boolean>> pending; //sending, player and tpahere

    public TPA(TeleportCommands plugin) {
        this.plugin = plugin;
        pending = new HashMap<>();
    }

    public void sendRequestTPA(Player from, Player to, boolean tpahere) {
        from.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&bSent a teleport request to ") + to.getDisplayName());
        for(Player p : pending.keySet()) {
            if(pending.get(p) == null) continue;
            if(pending.get(p).keySet().toArray()[0].equals(to)) {
                pending.put(p, null);
                p.sendMessage(ChatColor.RED + "Your tp request to " + to.getDisplayName() +
                        ChatColor.RED + " was overridden by another player's request.");
            }
        }
        to.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&b" + from.getDisplayName() +
                                (tpahere ? " &bwould like you to teleport to them!" : " &bwould like to teleport to you!")));
        to.sendMessage(ChatColor.RED + "This request will expire in 60 seconds.");
        to.sendMessage(ChatColor.GRAY + "\"/tpaccept\" to accept, just ignore otherwise.");
        HashMap<Player, Boolean> tpProfile = new HashMap<>();
        tpProfile.put(to, tpahere);
        pending.put(from, tpProfile);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(pending.get(from) == null) return;
                if(pending.get(from).containsKey(to))
                    pending.put(from, null);
            }
        }, 60 * 20);
    }

    public void acceptTPA(Player accepting) {
        for(Player player : pending.keySet()) {
            if(pending.get(player) == null) continue;
            if(pending.get(player).containsKey(accepting)) {
                boolean tpahere = pending.get(player).get(accepting);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&b" + accepting.getDisplayName() +
                                " &bhas accepted your teleport request!"));
                accepting.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&bAccepted request to teleport " + (tpahere ? "to " : "") +
                        player.getDisplayName() + (tpahere ? "" : " &bto you")));
                //teleport them!
                if(tpahere) execute(accepting, player);
                else execute(player, accepting);
            }
            pending.put(player, null);
            return;
        }
        accepting.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7No pending teleport requests."));
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
            ((Player)pending.get(p).keySet().toArray()[0]).sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c" + p.getDisplayName() + " &ccanceled their teleport request"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&7Canceled teleport request to ") +
                    ((Player)pending.get(p).keySet().toArray()[0]).getDisplayName());
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
