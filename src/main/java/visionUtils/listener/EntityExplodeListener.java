package visionUtils.listener;

import net.minecraft.server.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitTask;
import visionUtils.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityExplodeListener implements Listener {

    List<BlockState> states = new ArrayList<>();
    private static BukkitTask timer = null;

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() != EntityType.CREEPER)
            return;

        for (Block b : event.blockList()) {
            states.add(b.getState());
            b.getState().update(true);
        }
        event.setYield(0);

        Random random = new Random();

        timer = Bukkit.getServer().getScheduler().runTaskTimer(
                Plugin.getPlugin(Plugin.class), new Runnable() {
            @Override
            public void run() {
                if (states.isEmpty())
                    Bukkit.getServer().getScheduler().cancelTask(timer.getTaskId());
                else {
                    BlockState pick = states.get(random.nextInt(states.size()));
                    pick.update(true);
                    pick.getWorld().playSound(pick.getLocation(), Sound.BLOCK_STONE_PLACE, 5, 0.2f);
                    states.remove(pick);
                }
            }
        }, 20*5,1);
    }
}
