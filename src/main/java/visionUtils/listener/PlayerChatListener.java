package visionUtils.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String[] words = event.getMessage().split(" ");
        String newMessage = "";
        for (String word : words) {
            if (word.startsWith("@")) {

                Player player = Bukkit.getPlayer(word.substring(1));

                if (player != null)
                    player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 5, 0.5f);

                word = ChatColor.RED + word.substring(1);
            }

            newMessage += word + " ";
        }

        event.setMessage(newMessage);
    }
}
