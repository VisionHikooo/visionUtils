package visionUtils.listener;

import net.minecraft.world.EnumHand;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import visionUtils.listener.events.InteractCorpEvent;
import visionUtils.listener.events.InteractNpcEvent;

import java.util.Arrays;

public class InteractCorpListener implements Listener {

    @EventHandler
    public void onRightclickCorp(InteractCorpEvent event) {

        if (event.getCorp().getContents() == null)
            return;
        if (event.getType() != InteractNpcEvent.NpcInteractType.INTERACT)
            return;
        if (event.getHand() != EnumHand.a)
            return;

        Location loc = event.getCorp().getNpc().getBukkitEntity().getLocation();

        Arrays.stream(event.getCorp().getContents()).forEach(itemStack -> {
            event.getPlayer().getWorld().dropItemNaturally(loc, itemStack);
        });

        event.getCorp().despawn();
    }
}
