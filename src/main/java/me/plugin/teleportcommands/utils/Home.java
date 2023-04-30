package me.plugin.teleportcommands.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Home extends Teleporter{
    private String id;

    public Home(Player player, Location location, String name) {
        super(player, location, name);
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getName() {
        return name;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return Objects.equals(player, home.player) && Objects.equals(location, home.location) && Objects.equals(name, home.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, location, name);
    }
}
