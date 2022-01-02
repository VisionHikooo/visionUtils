package visionUtils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import visionUtils.Plugin;
import visionUtils.utils.statics.Messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SkipNightCMD implements CommandExecutor {

    private HashMap<UUID, List<UUID>> worlds = new HashMap<>();

    /**
     * / Skip
     * */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.onlyPlayer);
            return true;
        }

        Player player = (Player) sender;

        // Wenn für die Welt gerade keine Abstimmung läuft
        if (!worlds.containsKey(player.getWorld().getUID())) {
            worlds.put(player.getWorld().getUID(), new ArrayList<>());

            Bukkit.broadcastMessage("Es wurde eine Abstimmung zum Skippen der Nacht in " + ChatColor.GOLD + player.getWorld().getName() + ChatColor.WHITE + " gestartet!");
            Bukkit.getScheduler().runTaskLaterAsynchronously(Plugin.getPlugin(Plugin.class), task -> {
                if (!worlds.containsKey(player.getWorld().getUID()))
                    return;

                if (!checkVotes(player.getWorld().getUID()))
                    Bukkit.broadcastMessage("Die Abstimmung wurde wegen zu wenigen Stimmen abgebrochen!");

                worlds.remove(player.getWorld().getUID());
            }, 20*60);
        }

        worlds.get(player.getWorld().getUID()).add(player.getUniqueId());
        Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.WHITE + " hat abgestimmt. [" +
                worlds.get(player.getWorld().getUID()).size() + "/" + player.getWorld().getPlayers().size() + "]");
        checkVotes(player.getWorld().getUID());
        return true;
    }

    private boolean checkVotes(UUID world) {

        if (!worlds.containsKey(world))
            return false;

        World playerWorld = Bukkit.getWorld(world);

        if ((worlds.get(world).size()*100) / playerWorld.getPlayers().size() >= 50) {
            Bukkit.broadcastMessage("Die Nacht wurde in " + ChatColor.GOLD + playerWorld.getName() + ChatColor.WHITE + " geskipped!");
            playerWorld.setTime(0);
            worlds.remove(world);
            return true;
        }

       return false;
    }
}
