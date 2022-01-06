package visionUtils.listener.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import visionUtils.gui.GUI;

public class GuiClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private GUI gui;
    private Player player;
    private ClickType clickType;
    private int slot;

    private boolean isCancelled;

    public GuiClickEvent(GUI gui, Player player, int slot, ClickType clickType) {
        this.gui = gui;
        this.player = player;
        this.slot = slot;
        this.clickType = clickType;

        isCancelled = false;
    }

    public GUI getGui() {
        return gui;
    }

    public Player getPlayer() {
        return player;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public int getSlot() {
        return slot;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }
}
