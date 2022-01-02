package visionUtils.utils.statics;

import org.bukkit.ChatColor;

public class Messages {

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

    public static String joinMessage = "Hallo, %Spielername";
    public static String quitMessage = "Auf Wiedersehen, %Spielername%";


    /*
    * Death Messages
    * */

    public static void loadMessages() {
        // Lade die Messages aus der yml

    }
}
