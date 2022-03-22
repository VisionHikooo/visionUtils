package visionUtils.customItems.items;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import visionUtils.customItems.VisionItem;

import java.util.ArrayList;

public class InvisibilityCloak extends VisionItem {
    @Override
    public ItemStack build() {
        return null;
    }

    @Override
    public ArrayList<Recipe> setRecipe() {
        return null;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        // Spieler unsichtbar machen, wenn er sneakt.
    }
}
