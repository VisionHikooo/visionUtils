package visionUtils.customItems;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import visionUtils.Plugin;

import java.util.ArrayList;

public abstract class VisionItem implements Listener {

    public VisionItem() {
        recipe = setRecipe();
    }

    private ArrayList<Recipe> recipe;

    public abstract ItemStack build();
    public abstract ArrayList<Recipe> setRecipe();


    public void registerEvent() {
        Bukkit.getPluginManager().registerEvents(this, Plugin.getPlugin(Plugin.class));
    }

    public ArrayList<Recipe> getRecipe() {
        return recipe;
    }
}
