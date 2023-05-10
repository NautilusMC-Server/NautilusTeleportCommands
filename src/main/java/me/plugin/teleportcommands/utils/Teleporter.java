package me.plugin.teleportcommands.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Teleporter {
    protected Location location;
    protected Player player;
    protected String name;

    public Teleporter() {}

    public Teleporter(Player player, Location location, String name) {
        this.location = location;
        this.player = player;
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }
}
