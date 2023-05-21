package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public abstract class CommandStem implements CommandExecutor, TabCompleter {

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
