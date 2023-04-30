package me.plugin.teleportcommands.events;

import me.plugin.teleportcommands.utils.Teleporter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomTeleportEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Location location;
    private Teleporter teleporter;
    boolean cancelled = false;

    public CustomTeleportEvent(Player p, Location l, Teleporter t) {
        player = p;
        location = l;
        teleporter = t;
    }

    public CustomTeleportEvent(boolean isAsync) {
        super(isAsync);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Teleporter getTeleporter() {
        return teleporter;
    }

    public void setTeleporter(Teleporter teleporter) {
        this.teleporter = teleporter;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
