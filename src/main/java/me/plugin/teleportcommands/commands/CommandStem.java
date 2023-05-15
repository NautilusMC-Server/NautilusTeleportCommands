package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class CommandStem {

    protected TeleportCommands plugin;
    protected String command, description;
    protected List<String> aliases;

    public CommandStem(TeleportCommands plugin, String command, String description, String... aliases){
        this.plugin = plugin;
        this.command = command;
        this.description = description;
        if(aliases!=null) this.aliases = Arrays.asList(aliases);
    }

    public abstract boolean execute(CommandSender sender, String[] args);

    public String getCommand() {
        return command;
    }

    public List<String> getAliases() {
        return aliases;
    }

}
