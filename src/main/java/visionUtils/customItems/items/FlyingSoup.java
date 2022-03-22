package visionUtils.customItems.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import visionUtils.Plugin;
import visionUtils.customItems.VisionItem;
import visionUtils.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FlyingSoup extends VisionItem {

    private HashMap<UUID, Integer> actUser = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().equals(Material.MUSHROOM_STEW))
            return;
        if (!event.getItem().getItemMeta().getLore().get(0).equals("Flying Soup"))
            return;

        if (actUser.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Bitte warte, bis der derzeitige Effekt abgelaufen ist");
            return;
        }

        int min = Plugin.getFileManager().getConfig().getInt("flying_soup_time");
        if (min == 0) min = 10;

        actUser.put(event.getPlayer().getUniqueId(), min*60);
        event.getPlayer().setAllowFlight(true);
        event.getPlayer().setFlying(true);

        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {

                int time = actUser.get(event.getPlayer().getUniqueId());
                if (time == 0) {
                    event.getPlayer().sendMessage("Die Zeit ist abgelaufen");
                    event.getPlayer().setFlying(false);
                    event.getPlayer().setAllowFlight(false);

                    actUser.remove(event.getPlayer().getUniqueId());
                    this.cancel();
                } else {

                    if (time%60 == 0) {
                        // Bei vollen Minuten
                        event.getPlayer().sendMessage("Der Flugeffekt h\u00e4lt noch " + (time/60) + " Minute" + (time/60==1 ? "" : "n"));
                    } else if (time < 5 || (time <= 20 && time%5==0)) {
                        event.getPlayer().sendMessage("Der Flugeffekt h\u00e4lt noch " + time + " Sekunde" + (time==1 ? "" : "n"));
                    }

                    actUser.put(event.getPlayer().getUniqueId(), time-1);
                }
            }
        };

        run.runTaskTimerAsynchronously(Plugin.getPlugin(Plugin.class), 10,20);
    }

    @Override
    public ItemStack build() {
        return new ItemBuilder(Material.MUSHROOM_STEW).setLore("Flying Soup").build();
    }

    @Override
    public ArrayList<Recipe> setRecipe() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        ShapedRecipe recipe1 = new ShapedRecipe(build());
        recipe1.shape(  " F ",
                        " S ",
                        " D ");
        recipe1.setIngredient('F', Material.FEATHER);
        recipe1.setIngredient('S', Material.MUSHROOM_STEW);
        recipe1.setIngredient('D', Material.DIAMOND);

        recipes.add(recipe1);

        FurnaceRecipe recipe = new FurnaceRecipe(build(), Material.WATER_BUCKET);
        recipe.setInput(Material.WATER_BUCKET);

        recipes.add(recipe);

        return recipes;
    }
}
