package visionUtils.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import visionUtils.gui.GUI;
import visionUtils.listener.events.GuiClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player))
            return;

        if (event.getInventory().getHolder() instanceof GUI) {
            GUI gui = (GUI) event.getInventory().getHolder();
            if (event.getRawSlot() > gui.getSize()-1)
                return;

            event.setCancelled(true);
            if (event.getCurrentItem() == null)
                return;
            Bukkit.getPluginManager().callEvent(new GuiClickEvent(gui, (Player) event.getWhoClicked(), event.getSlot(), event.getClick()));
        }
    }
}
