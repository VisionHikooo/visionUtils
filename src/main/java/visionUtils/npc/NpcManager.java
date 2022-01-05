package visionUtils.npc;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NpcManager {

    private List<VisionNPC> npcs;

    public NpcManager() {
        npcs = new ArrayList<>();

        // Load NPCs
        loadNPCs();
    }

    public void showAllNPCs(Player player) {
        npcs.forEach(npc -> {
            npc.showNPC(player);
        });
    }

    public VisionNPC createNPC(Player player, String name) {
        VisionNPC npc = new VisionNPC(player, name);
        npcs.add(npc);
        return npc;
    }

    public VisionNPC createNPC(Player player) {
        return createNPC(player, player.getDisplayName());
    }

    public void clearNPCs() {
        npcs.forEach(visionNPC -> {
            visionNPC.despawn();
        });

        npcs = new ArrayList<>();
    }

    /*
    * Data
    * */

    public List<VisionNPC> getNpcs() {
        return npcs;
    }

    public void loadNPCs() {

    }

    public void safeNPCs() {

    }

}
