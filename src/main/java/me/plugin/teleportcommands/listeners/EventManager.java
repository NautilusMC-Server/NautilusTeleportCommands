package me.plugin.teleportcommands.listeners;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventManager implements Listener {

    private TeleportCommands plugin;

    public EventManager(TeleportCommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.d().checkPlayer(e.getPlayer());
    }

}
