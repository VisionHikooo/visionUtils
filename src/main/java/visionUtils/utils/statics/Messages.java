package visionUtils.utils.statics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import visionUtils.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Messages {

    public enum PlaceHolder {
        SPIELER("%Spielername%"),
        ENTITY("%Entity%");

        public String s;

        private PlaceHolder(String s) {
            this.s = s;
        }
    }

    /*
    * Placeholders
    *
    * %Spielername% - Ingamename des Spielers
    * */


    /*
    * Default Messages
    * */

    public static String noPermission = ChatColor.RED + "Du hast nicht die benötigten Berechtigungen";
    public static String onlyPlayer = ChatColor.RED + "Das kann nur ein Spieler tun!";

    public static String joinMessage = "Hallo, %Spielername%";
    public static String quitMessage = "Auf Wiedersehen, %Spielername%";


    /*
    * Death Messages
    * */

    private static HashMap<EntityDamageEvent.DamageCause, List<String>> deathMessages;
    private static String defaultDeathMessage = "%Spielername% ist gestorben";

    public static String getDeathMessage(EntityDamageEvent.DamageCause cause, String playerName) {

        if (!deathMessages.containsKey(cause))
            return defaultDeathMessage;

        List<String> list = deathMessages.get(cause);

        if (list.isEmpty())
            return defaultDeathMessage;

        return list.get(new Random().nextInt(list.size())).replace("%Spielername%", ChatColor.GOLD + playerName + ChatColor.WHITE);
    }

    /*
    * General
    * */

    public static void loadMessages() {
        // Lade die Messages aus der yml
        deathMessages = Plugin.getFileManager().getDeathMessages();
        joinMessage = Plugin.getFileManager().getConfig().getString("joinMessage");
        quitMessage = Plugin.getFileManager().getConfig().getString("quitMessage");
    }
}
