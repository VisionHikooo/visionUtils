package visionUtils.commands;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import visionUtils.utils.statics.Messages;

import java.util.UUID;

public class NpcCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.onlyPlayer);
            return true;
        }

        Player player = (Player) sender;

        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

        // Creating the NPC
        MinecraftServer mcServer = entityPlayer.c;
        WorldServer worldServer = entityPlayer.x();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Pisser");
        EntityPlayer npc = new EntityPlayer(mcServer, worldServer, gameProfile);

        npc.e(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        // Spawning the NPC
        PlayerConnection connection = entityPlayer.b;
        // Player Info Packet
        connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        // Spawn Packet
        connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        return true;
    }
}
