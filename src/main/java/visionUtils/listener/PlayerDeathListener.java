package visionUtils.listener;

import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import visionUtils.Plugin;
import visionUtils.npc.VisionNPC;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        // Erstelle Leiche
        VisionNPC corp = Plugin.getNpcManager().createNPC(event.getEntity(), "");
        corp.changePose(Pose.SLEEPING);

        /*
        * CUSTOM DEATH MESSAGES
        * */


    }
}
