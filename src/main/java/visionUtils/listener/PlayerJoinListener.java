package visionUtils.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import visionUtils.utils.statics.Messages;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Messages.joinMessage.replace("%Spielername%", event.getPlayer().getDisplayName()));
    }
}