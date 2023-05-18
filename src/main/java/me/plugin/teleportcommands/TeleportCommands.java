package me.plugin.teleportcommands;

import me.plugin.teleportcommands.commands.*;
import me.plugin.teleportcommands.listeners.EventManager;
import me.plugin.teleportcommands.listeners.OnCustomTeleport;
import me.plugin.teleportcommands.utils.DataManager;
import me.plugin.teleportcommands.utils.TPA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class TeleportCommands extends JavaPlugin {

    public static Plugin plugin;

    protected String pluginName;
    private DataManager dataManager;
    private TPA tpa;
    private ArrayList<CommandStem> commands;

    public TeleportCommands() {
        this.pluginName = "TeleportCommands";
        commands = new ArrayList<>();
        registerCommands();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        dataManager = new DataManager(this);
        getServer().getPluginManager().registerEvents(new EventManager(this), this);
        getServer().getPluginManager().registerEvents(new OnCustomTeleport(this), this);
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

    protected void registerCommands() {
        commands.add(new DelhomeCommand(this));
        commands.add(new HomeCommand(this));
        commands.add(new HomesCommand(this));
        commands.add(new SethomeCommand(this));
    }

    public DataManager d() {
        return dataManager;
    }

    public TPA tpa() {
        return tpa;
    }
}
