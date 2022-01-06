package visionUtils.listener.events;

import net.minecraft.world.EnumHand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import visionUtils.npc.Corp;

public class InteractCorpEvent extends InteractNpcEvent {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Corp corp;

    public InteractCorpEvent(Player player, Corp corp, NpcInteractType type, EnumHand hand) {
        super(player, corp, type, hand);
        this.player = player;
        this.corp = corp;
    }

    public Player getPlayer() {
        return player;
    }

    public Corp getCorp() {
        return corp;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
