package visionUtils.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import visionUtils.listener.events.SpawnerPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getBlockPlaced().getType() == Material.SPAWNER)
            Bukkit.getPluginManager().callEvent(new SpawnerPlaceEvent(event.getPlayer(), event.getBlockPlaced(), event.getItemInHand()));
    }
}
