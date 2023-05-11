package me.plugin.teleportcommands.listeners;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import me.plugin.teleportcommands.utils.Counter;
import me.plugin.teleportcommands.utils.PlayerBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.logging.Level;

public class OnCustomTeleport implements Listener {
    private static final ArrayList<PlayerBlock> running = new ArrayList<>();
    private static final ArrayList<Player> canceled = new ArrayList<>();
    private static Plugin plugin;

    public OnCustomTeleport() {}
    public OnCustomTeleport(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onCustomTeleport(CustomTeleportEvent e) {
        Player p = e.getPlayer();
        PlayerBlock pb = new PlayerBlock(p);
        if (running.contains(pb)) {
            p.sendMessage("Teleportation already in progress");
            e.setCancelled(true);
            return;
        }
        canceled.remove(p);
        running.add(pb);
        Counter counter = new Counter(5);
        BukkitRunnable countdown = new BukkitRunnable() {
            @Override
            public void run() {
                p.sendMessage("" + counter.getCount());
                counter.decrement();
                p.sendMessage("" + running.contains(p));
                if (canceled.contains(p)) {
                    p.sendMessage("Teleportation Canceled!");
                    e.setCancelled(true);
                    canceled.remove(p);
                    running.remove(pb);
                    this.cancel();
                }
                if (counter.getCount() < 1) {
                    running.remove(pb);
                    this.cancel();
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {p.damage(2);}, 40);
        countdown.runTaskTimer(TeleportCommands.plugin, 0, 20);;
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && !running.isEmpty()) {
            for (PlayerBlock pb : running) {
                if (pb.equals((Player) (e.getEntity()))) {
                    canceled.add((Player)e.getEntity());
                    return;
                }
            }
        }
    }
   @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (running.isEmpty()) {
            return;
        }
        PlayerBlock pb = null;
        for (int i = 0; i < running.size(); i++) {

            if (running.get(i).equals(e.getPlayer())) {
                pb = running.get(i);
                break;
            }
        }
        pb.getPlayer().sendMessage("here");
        if (pb != null && !pb.getBlock().equals(e.getPlayer().getLocation().getBlock())) {
            canceled.add(e.getPlayer());
        }
    }
}