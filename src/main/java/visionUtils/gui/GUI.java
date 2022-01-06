package visionUtils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public abstract class GUI implements InventoryHolder {

    private Inventory inventory;
    private HashMap<Integer, InventoryClickAction> clickable;

    public GUI(int length, String title) {
        inventory = Bukkit.createInventory(this, length, title);
        clickable = defineClickActions();
    }

    public Inventory getInventory() {
        return inventory;
    }

    protected abstract HashMap<Integer, InventoryClickAction> defineClickActions();

    public boolean hasClick(int slot) {
        return clickable.containsKey(slot);
    }

    public void onClick(int slot, Player player, ClickType type) {
        clickable.get(slot).onClick(player, type);
    }

    public int getSize() {
        return inventory.getSize();
    }

    public void addClick(int slot, InventoryClickAction action) {
        clickable.put(slot, action);
    }
}
