package visionUtils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import visionUtils.utils.ItemBuilder;

import java.util.HashMap;

public abstract class GUI implements InventoryHolder {

    private Inventory inventory;
    private HashMap<Integer, InventoryClickAction> clickable;

    public GUI(int length, String title) {
        inventory = Bukkit.createInventory(this, length, title);
        clickable = defineClickActions();
        setUpInventory();
    }

    public void setUpInventory() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        }
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
