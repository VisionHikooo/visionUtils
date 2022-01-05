package visionUtils.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.world.EnumHand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import visionUtils.Plugin;
import visionUtils.listener.events.InteractCorpEvent;
import visionUtils.listener.events.InteractNpcEvent;
import visionUtils.npc.Corp;
import visionUtils.npc.VisionNPC;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {

    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<>();

    public void inject(Player player) {
        channel = ((CraftPlayer) player).getHandle().b.a.k;
        channels.put(player.getUniqueId(), channel);

        if (channel.pipeline().get("PacketInjector") != null)
            return;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
                arg.add(packet);
                readPacket(player, packet);
            }
        });
    }

    public void uninject(Player player) {
        channel = channels.get(player.getUniqueId());
        if (channel.pipeline().get("PacketInjector") != null)
            channel.pipeline().remove("PacketInjector");
    }

    public void readPacket(Player player, Packet<?> packet) {
        int id = (int) getValue(packet, "a"); // Die Entity-ID

        Object obj = getValue(packet, "b");
        Object obj2 = getValue(obj, "a");

        EnumHand hand = null;
        InteractNpcEvent.NpcInteractType type;

        switch (obj.getClass().toString().replace("class net.minecraft.network.protocol.game.PacketPlayInUseEntity", "")) {
            case "$d":
                // Interact
                hand = (EnumHand) obj2;
                type = InteractNpcEvent.NpcInteractType.INTERACT;
                break;
            case "$e":
                // Interact At
                type = InteractNpcEvent.NpcInteractType.INTERACT_AT;
                hand = (EnumHand) obj2;
                break;
            default:
                // Attack
                type = InteractNpcEvent.NpcInteractType.ATTACK;
                hand = null;
                break;
        }

        searchNpcOrCorp(player, id, hand, type);
    }

    private void searchNpcOrCorp(Player player, int id, EnumHand hand, InteractNpcEvent.NpcInteractType type) {
        for (VisionNPC npc : Plugin.getNpcManager().getNpcs())
            if (npc.getNpc().getBukkitEntity().getEntityId() == id) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(Plugin.class), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new InteractNpcEvent(player, npc, type, hand));
                    }
                }, 0);
                return;
            }

        for (Corp corp : Plugin.getCorpManager().getCorps()) {
            if (corp.getNpc().getBukkitEntity().getEntityId() == id) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(Plugin.class), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new InteractCorpEvent(player, corp, type, hand));
                    }
                }, 0);
                return;
            }
        }
    }

    private Object getValue(Object instance, String name) {
        Object result = null;

        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
