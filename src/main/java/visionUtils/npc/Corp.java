package visionUtils.npc;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import visionUtils.Plugin;

import java.lang.reflect.Type;
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

    public Corp(JsonObject object) {
        super(object);

        whenDied = object.get("whenDied").getAsLong();
        whoDied = UUID.fromString(object.get("whoDied").getAsString());

        Gson gson = new Gson();
        gson.fromJson(object.get("contents").toString(), ItemStack[].class);
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

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = super.getJsonObject();

        json.addProperty("whenDied", whenDied);
        json.addProperty("whoDied", whoDied.toString());

        Gson gson = new Gson();
        json.addProperty("contents", gson.toJson(contents));
        return json;
    }
}
