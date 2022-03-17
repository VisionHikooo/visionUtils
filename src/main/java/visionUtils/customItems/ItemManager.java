package visionUtils.customItems;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import visionUtils.customItems.items.FlyingSoup;
import visionUtils.customItems.items.ShieldBowChestplate;

import java.util.HashMap;

public class ItemManager {

    private HashMap<Custom_Item,visionItem> custom_items = new HashMap<>();

    public ItemManager() {
        registerItems();

        for (visionItem item : custom_items.values()) {
            item.registerEvent();

            if (item.getRecipe() == null)
                continue;

            for (Recipe recipe : item.getRecipe())
                Bukkit.addRecipe(recipe);
        }
    }

    public void registerItems() {
        custom_items.put(Custom_Item.FLYING_SOUP, new FlyingSoup());
        custom_items.put(Custom_Item.SHIELDBOW_CHESTPLATE, new ShieldBowChestplate());
    }

    public ItemStack getItem(Custom_Item item) {
        return custom_items.get(item).build();
    }
}
