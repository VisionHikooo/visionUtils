package visionUtils.npc;


import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import visionUtils.Plugin;

import java.util.UUID;

public class Corp extends VisionNPC {

    private ItemStack[] contents;
    private long whenDied;
    private UUID whoDied;

    public Corp(Player player, long whenDied, ItemStack[] contents) {
        super(player, "");
        this.changePose(Pose.SLEEPING);
        whoDied = player.getUniqueId();
        this.whenDied = whenDied;
        this.contents = contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public long getWhenDied() {
        return whenDied;
    }

    @Override
    public void despawn() {
        super.despawn();
        Plugin.getCorpManager().getCorps().remove(this);
    }
}
