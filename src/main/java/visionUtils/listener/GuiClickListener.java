package visionUtils.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import visionUtils.listener.events.GuiClickEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onGuiClick(GuiClickEvent event) {
        event.setCancelled(true);
        if (event.getGui().hasClick(event.getSlot()))
            event.getGui().onClick(event.getSlot(), event.getPlayer(), event.getClickType());
    }
}
