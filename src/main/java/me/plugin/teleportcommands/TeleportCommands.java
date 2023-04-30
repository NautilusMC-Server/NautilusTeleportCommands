package me.plugin.teleportcommands;

import me.kodysimpson.simpapi.command.CommandManager;
import me.plugin.teleportcommands.commands.HomeDelete;
import me.plugin.teleportcommands.commands.HomeSet;
import me.plugin.teleportcommands.commands.HomeTP;
import me.plugin.teleportcommands.listeners.OnCustomTeleport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TeleportCommands extends JavaPlugin {

    public static Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        String url = "jdbc:mysql://u34689_uw6sGHYfrX:6kRkOHl2fpBQWCKRNrvEe3RB@byrd.bloom.host:3306/s34689_NautilusMain";
        plugin = this;
        new OnCustomTeleport(plugin);
        new HomeTP(plugin);
        try {
            System.out.println("register");
            CommandManager.createCoreCommand(this, "home", "Creates homes to teleport to", "/home", null, HomeSet.class, HomeDelete.class, HomeTP.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(new OnCustomTeleport(), this);

        Bukkit.getScheduler().runTask(plugin, () -> {
            getServer().getLogger().log(Level.INFO, "Working");
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}