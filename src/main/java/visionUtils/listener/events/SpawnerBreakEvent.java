package visionUtils.listener.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Block block;

    public SpawnerBreakEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getSpawner() {
        return block;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
