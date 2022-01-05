package visionUtils.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import visionUtils.Plugin;
import visionUtils.utils.PacketReader;
import visionUtils.utils.statics.Messages;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Messages.joinMessage.replace("%Spielername%", event.getPlayer().getDisplayName()));

        // Show them all NPCs
        Plugin.getNpcManager().showAllNPCs(event.getPlayer());
        Plugin.getCorpManager().showAllCorps(event.getPlayer());

        PacketReader reader = new PacketReader();
        reader.inject(event.getPlayer());
    }
}
