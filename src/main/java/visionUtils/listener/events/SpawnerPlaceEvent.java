package visionUtils.listener.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class SpawnerPlaceEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Block spawner;
    private ItemStack spawnerItem;

    public SpawnerPlaceEvent(Player player, Block block, ItemStack spawnerItem) {
        this.player = player;
        this.spawner = block;
        this.spawnerItem = spawnerItem;
    }

    public ItemStack getSpawnerItem() {
        return spawnerItem;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getSpawner() {
        return spawner;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
