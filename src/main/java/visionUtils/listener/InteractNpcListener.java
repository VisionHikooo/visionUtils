package visionUtils.listener;

import net.minecraft.world.EnumHand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import visionUtils.listener.events.InteractNpcEvent;

public class InteractNpcListener implements Listener {

    @EventHandler
    public void onNpcRightclick(InteractNpcEvent event) {
        if(event.getType() != InteractNpcEvent.NpcInteractType.INTERACT)
            return;

        if (event.getHand() != EnumHand.a)
            return;

        event.getPlayer().openInventory(Bukkit.createInventory(null, 5*9));
    }
}
