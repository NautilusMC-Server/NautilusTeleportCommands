package me.plugin.teleportcommands;

import me.plugin.teleportcommands.commands.*;
import me.plugin.teleportcommands.listeners.EventManager;
import me.plugin.teleportcommands.listeners.OnCustomTeleport;
import me.plugin.teleportcommands.utils.DataManager;
import me.plugin.teleportcommands.utils.TPA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class TeleportCommands extends JavaPlugin {

    private String pluginName;
    private DataManager dataManager;
    private TPA tpa;
    private EventManager eventManager;
    private OnCustomTeleport onCustomTeleport;

    public TeleportCommands() {
        this.pluginName = "TeleportCommands";
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        //commands
        registerCommands();
        //data
        dataManager = new DataManager(this);
        tpa = new TPA(this);
        //events
        eventManager = new EventManager(this);
        onCustomTeleport = new OnCustomTeleport(this);
        registerEvents();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(eventManager, this);
        pm.registerEvents(onCustomTeleport, this);
    }

    private void registerCommands() {
        //homes
        this.getCommand("delhome").setExecutor(new DelhomeCommand(this));
        this.getCommand("home").setExecutor(new HomeCommand(this));
        this.getCommand("homes").setExecutor(new HomesCommand(this));
        this.getCommand("sethome").setExecutor(new SethomeCommand(this));
        //warps
        this.getCommand("delwarp").setExecutor(new DelwarpCommand(this));
        this.getCommand("warp").setExecutor(new WarpCommand(this));
        this.getCommand("warps").setExecutor(new WarpsCommand(this));
        this.getCommand("setwarp").setExecutor(new SetwarpCommand(this));
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        //tpa
        this.getCommand("tpa").setExecutor(new TpaCommand(this));
        this.getCommand("tpahere").setExecutor(new TpahereCommand(this));
        this.getCommand("tpaccept").setExecutor(new TpacceptCommand(this));
        this.getCommand("tpacancel").setExecutor(new TpacancelCommand(this));
    }

    public DataManager d() {
        return dataManager;
    }

    public TPA tpa() {
        return tpa;
    }
}
