package visionUtils.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import visionUtils.Plugin;

public class InteractSignListener implements Listener {

    @EventHandler
    public void onSignInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        String name = event.getClickedBlock().getType().toString();

        if (!name.substring(name.length()-4).equalsIgnoreCase("sign"))
            return;

        Sign sign = (Sign) event.getClickedBlock().getState();

        if (!sign.getLine(0).equalsIgnoreCase("teleport"))
            return;

        // TODO: Abstand berechnen, Level überprüfen und Level abziehen

        try {
            float x = Float.parseFloat(sign.getLine(1));
            float y = Float.parseFloat(sign.getLine(2));
            float z = Float.parseFloat(sign.getLine(3));

            float distance = (float) Math.sqrt(Math.pow(x - sign.getX(), 2)
            + Math.pow(y - sign.getY(), 2) + Math.pow(z - sign.getZ(), 2));

            Bukkit.broadcastMessage("Abstand: " + distance);

            int exp = (int) Math.floor(distance * Plugin.getFileManager().getConfig().getDouble("xp_multiplier"));
            int act_exp = (int) Math.floor(getExpFromLevel(event.getPlayer().getLevel())
                    + event.getPlayer().getExp() * getExpFromLevel(event.getPlayer().getLevel()+1));

            if (act_exp < exp) {
                event.getPlayer().sendMessage("Du hast nicht genug Erfahrungspunkte");
                return;
            }

            event.getPlayer().giveExp(-exp);
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), x, y, z));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public static int getExpFromLevel(int level) {
        if (level > 30) {
            return (int) (4.5 * level * level - 162.5 * level + 2220);
        }
        if (level > 15) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        }
        return level * level + 6 * level;
    }
}
