package visionUtils.listener.events;

import net.minecraft.world.EnumHand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import visionUtils.npc.VisionNPC;

public class InteractNpcEvent extends Event implements Cancellable {

    public enum NpcInteractType{
        ATTACK,
        INTERACT,
        INTERACT_AT
    }

    private Player PLAYER;
    private VisionNPC NPC;
    private NpcInteractType type;
    private EnumHand hand;
    private boolean isCanncelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public InteractNpcEvent(Player PLAYER, VisionNPC NPC, NpcInteractType type, EnumHand hand) {
        this.PLAYER = PLAYER;
        this.NPC = NPC;
        this.hand = hand;
        this.type = type;
        isCanncelled = false;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCanncelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCanncelled = cancel;
    }

    public Player getPlayer() {
        return PLAYER;
    }

    public VisionNPC getNPC() {
        return NPC;
    }

    public NpcInteractType getType() {
        return type;
    }

    public EnumHand getHand() {
        return hand;
    }
}
