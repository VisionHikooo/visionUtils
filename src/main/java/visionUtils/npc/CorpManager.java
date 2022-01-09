package visionUtils.npc;

import com.google.gson.JsonObject;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import visionUtils.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CorpManager {

    private List<Corp> corps = new ArrayList<>();

    public void spawnCorp(Player player, long whenDied, ItemStack[] content) {
        corps.add(new Corp(player, whenDied, content));
    }

    public List<Corp> getCorps() {
        return corps;
    }

    public Corp getCorp(EntityPlayer npc) {
        for (Corp corp : corps)
            if (corp.getNpc() == npc)
                return corp;
        return null;
    }

    public void showAllCorps(Player player) {
        corps.forEach(npc -> {
            npc.showNPC(player);
        });
    }

    public void addCorp(JsonObject object) {
        if (corps != null)
            corps.add(new Corp(object));
    }
}
