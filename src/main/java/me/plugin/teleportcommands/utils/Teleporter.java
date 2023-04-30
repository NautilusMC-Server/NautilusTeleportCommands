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
}
