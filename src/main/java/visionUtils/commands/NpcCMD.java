package visionUtils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import visionUtils.Plugin;
import visionUtils.utils.statics.Messages;

public class NpcCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.onlyPlayer);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            Plugin.getNpcManager().createNPC(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("clear"))
            Plugin.getNpcManager().clearNPCs();
        else if (args[0].equalsIgnoreCase("sleep"))
            Plugin.getNpcManager().createNPC(player).changePose(Pose.SLEEPING);
        return true;
    }
}
