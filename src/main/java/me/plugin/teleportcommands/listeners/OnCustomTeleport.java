package me.plugin.teleportcommands.listeners;

import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import me.plugin.teleportcommands.utils.Counter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.logging.Level;

public class OnCustomTeleport implements Listener {
    private static ArrayList<Player> running = new ArrayList<>();
    private static ArrayList<Player> canceled = new ArrayList<>();
    private static Plugin plugin;

    public OnCustomTeleport() {}
    public OnCustomTeleport(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onCustomTeleport(CustomTeleportEvent e) {
        Player p = e.getPlayer();
        canceled.remove(p);
        running.add(p);
        Counter counter = new Counter(5);
        p.sendMessage("" + counter.getCount());
        p.sendMessage(TeleportCommands.plugin.toString());
        BukkitRunnable countdown = new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getServer().getLogger().log(Level.INFO, "In Task");
                counter.decrement();
                p.sendMessage("" + counter.getCount());
            }
        };
        countdown.runTask(TeleportCommands.plugin);
        while (counter.getCount() > 0) {
            if (canceled.contains(p)) {
                p.sendMessage("Teleportation Canceled!");
                e.setCancelled(true);
                canceled.remove(p);
                break;
            }
        }
        countdown.cancel();
        running.remove(p);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && running.contains((Player)e.getEntity())) {
            canceled.add((Player)e.getEntity());
        }
    }
}