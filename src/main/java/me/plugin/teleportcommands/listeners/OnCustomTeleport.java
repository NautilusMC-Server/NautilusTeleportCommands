package me.plugin.teleportcommands.listeners;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import me.plugin.teleportcommands.utils.PlayerBlock;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;

public class OnCustomTeleport implements Listener {
    private static final ArrayList<PlayerBlock> running = new ArrayList<>();
    private static final ArrayList<Player> canceled = new ArrayList<>();
    private final Plugin plugin;

    public OnCustomTeleport(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onCustomTeleport(CustomTeleportEvent e) {
        Player p = e.getPlayer();
        PlayerBlock pb = new PlayerBlock(p);
        if (running.contains(pb)) {
            e.setCancelled(true);
            return;
        }
        canceled.remove(p);
        p.sendMessage(ChatColor.DARK_AQUA + "Sending you elsewhere in 5 seconds...");
        running.add(pb);
        BukkitRunnable checkAfter = new BukkitRunnable() {
            @Override
            public void run() {
                if (canceled.contains(p)) {
                    e.setCancelled(true);
                    canceled.remove(p);
                }
                running.remove(pb);
                this.cancel();
            }
        };
        checkAfter.runTaskLater(plugin,5 * 20);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && !running.isEmpty()) {
            if(canceled.contains((Player)e.getEntity())) return;
            for (PlayerBlock pb : running) {
                if (pb.equals((Player) (e.getEntity()))) {
                    e.getEntity().sendMessage(ChatColor.RED + "You took damage! Teleportation canceled.");
                    canceled.add((Player)e.getEntity());
                    return;
                }
            }
        }
    }
   @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (running.isEmpty() || canceled.contains(e.getPlayer())) {
            return;
        }
        PlayerBlock pb = null;
        for (PlayerBlock playerBlock : running) {
           if (playerBlock.equals(e.getPlayer())) {
               pb = playerBlock;
               break;
           }
        }
        if (pb != null && !pb.getBlock().equals(e.getPlayer().getLocation().getBlock())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You moved! Teleportation canceled.");
            canceled.add(e.getPlayer());
        }
    }
}