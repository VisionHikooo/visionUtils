package visionUtils.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

import java.util.UUID;

public class VisionNPC {

    private EntityPlayer npc;
    private GameProfile gameProfile;

    public VisionNPC(Player player, String name) {

        // Creating the NPC
        createGameProfile(name);
        setSkin(player.getUniqueId());

        createNPC(((CraftPlayer) player).getHandle());
        setLocation(player.getLocation());
    }

    private void createNPC(EntityPlayer entityPlayer) {
        MinecraftServer mcServer = entityPlayer.c;
        WorldServer worldServer = entityPlayer.x();

        if (gameProfile == null)
            gameProfile = new GameProfile(UUID.randomUUID(), "Pisser");

        npc = new EntityPlayer(mcServer, worldServer, gameProfile);
    }

    public void spawnNPC() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            showNPC(player);
        });
    }

    public void showNPC(Player player) {
        PlayerConnection con = ((CraftPlayer) player).getHandle().b;

        con.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        con.a(new PacketPlayOutNamedEntitySpawn(npc));
        con.a(new PacketPlayOutEntityMetadata(npc.getBukkitEntity().getEntityId(),
                npc.ai(), true));
    }

    public void setLocation(Location loc) {
        setLocation(loc.getX(), loc.getY(), loc.getZ());
        spawnNPC();
    }

    public void setLocation(double x, double y, double z) {
        npc.e(x, y, z);
    }

    public void createGameProfile(String name) {
        gameProfile = new GameProfile(UUID.randomUUID(), name);
    }

    public void setSkin(UUID uuid) {
        GameProfile profile = ((CraftPlayer) Bukkit.getPlayer(uuid)).getProfile();
        Property property = (Property) profile.getProperties().get("textures").toArray()[0];
        String signature = property.getSignature();
        String texture = property.getValue();
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
    }

    public void removeFromTab() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ((CraftPlayer) player).getHandle().b.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc));
        });
    }

    public void despawn() {
        int id = npc.getBukkitEntity().getEntityId();

        Bukkit.getOnlinePlayers().forEach(player -> {
            ((CraftPlayer) player).getHandle().b.a(new PacketPlayOutEntityDestroy(id));
        });
    }

    public EntityPose getEntityPose(Pose pose) {
        EntityPose entityPose;
        switch (pose) {
            case STANDING -> entityPose = EntityPose.a;
            case FALL_FLYING -> entityPose = EntityPose.b;
            case SLEEPING -> entityPose = EntityPose.c;
            case SWIMMING -> entityPose = EntityPose.d;
            case SPIN_ATTACK -> entityPose = EntityPose.e;
            case SNEAKING -> entityPose = EntityPose.f;
            case LONG_JUMPING -> entityPose = EntityPose.g;
            default -> entityPose = EntityPose.h;
        }
        return entityPose;
    }

    public void changePose(Pose pose) {
        npc.b(getEntityPose(pose));
        spawnNPC();
    }
}
