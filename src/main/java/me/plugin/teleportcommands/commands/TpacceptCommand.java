package me.plugin.teleportcommands.commands;

import me.plugin.teleportcommands.TeleportCommands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand extends CommandStem {
    public TpacceptCommand(TeleportCommands plugin) {
        super(plugin, "tpaccept", "Accept tpa request", "tpyes");
    } //TODO add aliases to all in plugin.yml

    protected TeleportCommands plugin;

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        //TODO
        return true;
    }
}
