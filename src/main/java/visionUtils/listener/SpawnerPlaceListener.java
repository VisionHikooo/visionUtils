package visionUtils.listener;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import visionUtils.listener.events.SpawnerPlaceEvent;

public class SpawnerPlaceListener implements Listener {

    @EventHandler
    public void onSpawnerPlace(SpawnerPlaceEvent event) {
        CreatureSpawner cs = (CreatureSpawner) event.getSpawner().getState();
        try {
            cs.setSpawnedType(EntityType.valueOf(event.getSpawnerItem().getItemMeta().getLore().get(0)));
        } catch (NullPointerException exception) {

        }
        event.getSpawner().getState().update();
    }
}
