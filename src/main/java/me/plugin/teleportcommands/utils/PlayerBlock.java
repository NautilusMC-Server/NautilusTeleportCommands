package me.plugin.teleportcommands.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerBlock {
    private Player player;
    private Block block;

    public PlayerBlock(Player player) {
        this.player = player;
        block = player.getLocation().getBlock();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Boolean equals(Player player) {
        if (this.player == player) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PlayerBlock{" +
                "player = " + player +
                ", Block = " + block +
                '}';
    }
}
