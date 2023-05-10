package me.plugin.teleportcommands.utils;
import me.plugin.teleportcommands.TeleportCommands;
import me.plugin.teleportcommands.events.CustomTeleportEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class TPA extends Teleporter{
    private static ArrayList<TPA> pending = new ArrayList<>();
    private Player receiver;

    public TPA() {}
    public TPA(Player player, Player receiver, Location location, String name) {
        super(player, location, name);
        this.receiver = receiver;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void sendRequestTPA() {
        receiver.sendMessage(player.getDisplayName() + " would like to teleport you you" );
        receiver.sendMessage( "-\"/tpa accept\" to accept");
        receiver.sendMessage( "-\"/tpa deny\" to deny");
        for (int i = 0; i < pending.size(); i++) {
            if (player.equals(pending.get(i).getPlayer())) {
                pending.remove(i);
            }
        }
        pending.add(this);
    }

    public static void acceptTPA(Player p) {
        TPA tpa = null;
        for (int i = 0; i < pending.size(); i++) {
            if (p.equals(pending.get(i).getReceiver())) {
                tpa = pending.get(i);
                break;
            }
        }
        if (tpa == null) {
            p.sendMessage(ChatColor.RED + "Teleport request no longer valid");
        }
        tpa.execute();
    }

    public void execute() {
        CustomTeleportEvent event = new CustomTeleportEvent(player, receiver.getLocation(), this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(TeleportCommands.plugin, () -> {

        }, 20 * 5);
    }
}
