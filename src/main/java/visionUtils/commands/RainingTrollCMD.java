package visionUtils.commands;

import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import visionUtils.utils.statics.Messages;
import visionUtils.utils.statics.Permissions;

public class RainingTrollCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(Permissions.RAIN)) {
            sender.sendMessage(Messages.noPermission);
            return true;
        }

        if (args.length == 0)
            return false;

        for (String name : args) {

            Player player = Bukkit.getPlayer(name);

            if (player == null) {
                sender.sendMessage(name + " konnte nicht gefunden werden");
                continue;
            }

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            PlayerConnection connection = entityPlayer.b;

            connection.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0.0f));
            sender.sendMessage("Der Command wurde ausgeführt!");
        }




        return true;
    }
}
