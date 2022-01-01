package visionUtils.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import visionUtils.listener.events.SpawnerBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Falls es sich um einen Spawner handelt, wird das SpawnerBreakEvent aufgerufen
        if (event.getBlock().getType() == Material.SPAWNER)
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerBreakEvent(event.getPlayer(), event.getBlock()));
    }
}
