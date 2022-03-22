package visionUtils.customItems.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import visionUtils.customItems.VisionItem;
import visionUtils.utils.ItemBuilder;

import java.util.ArrayList;

public class FlyingBow extends VisionItem {
    @Override
    public ItemStack build() {
        return new ItemBuilder(Material.BOW).setLore("Flying Bow").build();
    }

    @Override
    public ArrayList<Recipe> setRecipe() {
        return null;
    }

    @EventHandler
    public void onBowShot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (!event.getBow().getItemMeta().hasLore() || !event.getBow().getItemMeta().getLore().get(0).equals("Flying Bow"))
            return;

        Player player = (Player) event.getEntity();
        event.getProjectile().addPassenger(player);
    }
}
