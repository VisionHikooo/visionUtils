package visionUtils.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import visionUtils.utils.PacketReader;
import visionUtils.utils.statics.Messages;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(Messages.quitMessage.replace("%Spielername%", ChatColor.GOLD +
                event.getPlayer().getDisplayName() + ChatColor.WHITE));

        PacketReader reader = new PacketReader();
        reader.uninject(event.getPlayer());
    }
}
