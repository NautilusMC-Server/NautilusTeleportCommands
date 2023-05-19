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
    private ArrayList<CommandStem> commands;

    public TeleportCommands() {
        this.pluginName = "TeleportCommands";
        commands = new ArrayList<>();
        registerCommands();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        //data
        dataManager = new DataManager(this);
        tpa = new TPA(this);
        //events
        eventManager = new EventManager(this);
        onCustomTeleport = new OnCustomTeleport(this);
        registerEvents();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        for(CommandStem c : commands){
            if(c.getCommand().equalsIgnoreCase(label) || (c.getAliases() != null && c.getAliases().contains(label))){
                c.execute(sender, args);
            }
        }
        return true;
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(eventManager, this);
        pm.registerEvents(onCustomTeleport, this);
    }

    private void registerCommands() {
        //homes
        commands.add(new DelhomeCommand(this));
        commands.add(new HomeCommand(this));
        commands.add(new HomesCommand(this));
        commands.add(new SethomeCommand(this));
        //warps
        commands.add(new DelwarpCommand(this));
        commands.add(new WarpCommand(this));
        commands.add(new WarpsCommand(this));
        commands.add(new SetwarpCommand(this));
        commands.add(new SpawnCommand(this));
        //tpa
        commands.add(new TpaCommand(this));
        commands.add(new TpahereCommand(this));
        commands.add(new TpacceptCommand(this));
        commands.add(new TpacancelCommand(this));
    }

    public DataManager d() {
        return dataManager;
    }

    public TPA tpa() {
        return tpa;
    }
}
