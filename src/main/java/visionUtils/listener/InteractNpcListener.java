package visionUtils.listener;

import net.minecraft.world.EnumHand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import visionUtils.gui.ItemShopGUI;
import visionUtils.listener.events.InteractNpcEvent;

public class InteractNpcListener implements Listener {

    @EventHandler
    public void onNpcRightclick(InteractNpcEvent event) {
        if(event.getType() != InteractNpcEvent.NpcInteractType.INTERACT)
            return;

        if (event.getHand() != EnumHand.a)
            return;

        event.getPlayer().openInventory(
                new ItemShopGUI(9*5, event.getPlayer().getDisplayName().toUpperCase()).getInventory()
        );
    }
}
