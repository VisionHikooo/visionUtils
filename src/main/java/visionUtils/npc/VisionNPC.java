package visionUtils.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import visionUtils.Plugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class VisionNPC {

    private EntityPlayer npc;
    private GameProfile gameProfile;
    private UUID uuid;

    private String skinPlayerName;

    public VisionNPC(Player player, String name) {

        // Creating the NPC
        createGameProfile(name);
        skinPlayerName = player.getDisplayName();
        setSkin(player.getUniqueId());

        createNPC(((CraftPlayer) player).getHandle());
        setLocation(player.getLocation());
    }

    public VisionNPC(JsonObject json) {
        uuid = UUID.fromString(json.get("uuid").getAsString());
        String name = json.get("name").getAsString();

        skinPlayerName = json.get("skinPlayerName").getAsString();

        JsonObject loc = json.getAsJsonObject("location");
        Location location = new Location(
                Bukkit.getWorld(UUID.fromString(loc.get("world").getAsString())),
                loc.get("x").getAsDouble(),
                loc.get("y").getAsDouble(),
                loc.get("z").getAsDouble(),
                loc.get("pitch").getAsFloat(),
                loc.get("yaw").getAsFloat());
        createGameProfile(name);

        MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

        if(!skinPlayerName.equalsIgnoreCase("null"))
            setSkin(skinPlayerName);

        createNPC(server, world);
        setLocation(location);
    }

    private void createNPC(EntityPlayer entityPlayer) {
        MinecraftServer mcServer = entityPlayer.c;
        WorldServer worldServer = entityPlayer.x();

        npc = new EntityPlayer(mcServer, worldServer, gameProfile);
    }

    private void createNPC(MinecraftServer mcServer, WorldServer world) {
        npc = new EntityPlayer(mcServer, world, gameProfile);
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

        Bukkit.getScheduler().runTaskLater(Plugin.getPlugin(Plugin.class), this::removeFromTab, 20);
    }

    public void setLocation(Location loc) {
        setLocation(loc.getX(), loc.getY(), loc.getZ());
        spawnNPC();
    }

    public void setLocation(double x, double y, double z) {
        npc.e(x, y, z);
    }

    public void createGameProfile(String name) {
        if (uuid == null)
            uuid = UUID.randomUUID();

        gameProfile = new GameProfile(uuid, name);
    }

    public void setSkin(UUID uuid) {
        GameProfile profile = ((CraftPlayer) Bukkit.getPlayer(uuid)).getProfile();
        Property property = (Property) profile.getProperties().get("textures").toArray()[0];
        String signature = property.getSignature();
        String texture = property.getValue();
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
    }

    public void setSkin(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = JsonParser.parseReader(reader).getAsJsonObject().get("id").getAsString();
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject object = JsonParser.parseReader(reader2).getAsJsonObject();
            JsonObject property = object.get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();

            gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public EntityPlayer getNpc() {
        return npc;
    }

    protected GameProfile getGameProfile() {
        return gameProfile;
    }

    public UUID getUuid() {
        return uuid;
    }

    public JsonObject getJsonObject() {
        JsonObject json = new JsonObject();

        json.addProperty("uuid", uuid.toString());
        json.addProperty("name", gameProfile.getName());

        // Location
        JsonObject location = new JsonObject();
        Location loc = npc.getBukkitEntity().getLocation();
        location.addProperty("world", npc.getBukkitEntity().getWorld().getUID().toString());
        location.addProperty("x", loc.getX());
        location.addProperty("y", loc.getY());
        location.addProperty("z", loc.getZ());
        location.addProperty("pitch", loc.getPitch());
        location.addProperty("yaw", loc.getYaw());

        json.add("location", location);

        // Skin betreffend.
        if (skinPlayerName != null)
            json.addProperty("skinPlayerName", skinPlayerName);
        else
            json.addProperty("skinPlayerName", "null");


        return json;
    }

}
