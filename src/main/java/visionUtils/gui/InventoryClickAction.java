package visionUtils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface InventoryClickAction {
    public void onClick(Player player, ClickType type);
}
