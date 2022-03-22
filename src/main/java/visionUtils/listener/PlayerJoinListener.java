package visionUtils.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import visionUtils.Plugin;
import visionUtils.customItems.Custom_Item;
import visionUtils.utils.PacketReader;
import visionUtils.utils.statics.Messages;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Messages.joinMessage.replace("%Spielername%", ChatColor.GOLD +
                event.getPlayer().getDisplayName() + ChatColor.WHITE));

        event.getPlayer().getInventory().addItem(Plugin.getItemManager().getItem(Custom_Item.FLYING_SOUP));
        event.getPlayer().getInventory().addItem(Plugin.getItemManager().getItem(Custom_Item.SHIELDBOW_CHESTPLATE));
        event.getPlayer().getInventory().addItem(Plugin.getItemManager().getItem(Custom_Item.ENMA));
        event.getPlayer().getInventory().addItem(Plugin.getItemManager().getItem(Custom_Item.FLYING_BOW));
        // Show them all NPCs
        Plugin.getNpcManager().showAllNPCs(event.getPlayer());
        Plugin.getCorpManager().showAllCorps(event.getPlayer());

        // Activate Packet-Listener
        PacketReader reader = new PacketReader();
        reader.inject(event.getPlayer());
    }
}
