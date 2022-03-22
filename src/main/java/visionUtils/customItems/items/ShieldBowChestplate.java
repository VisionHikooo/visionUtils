package visionUtils.customItems.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import visionUtils.Plugin;
import visionUtils.customItems.VisionItem;
import visionUtils.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.UUID;

public class ShieldBowChestplate extends VisionItem {

    private ArrayList<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        ItemStack chestplate = player.getInventory().getChestplate();

        if (chestplate == null)
            return;

        if (chestplate.getType() != Material.DIAMOND_CHESTPLATE)
            return;

        if (!chestplate.getItemMeta().hasLore() || !chestplate.getItemMeta().getLore().get(0).equals("Shieldbow"))
            return;


        if ((player.getHealth()-event.getDamage())<=5 && !cooldown.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("Der ShieldBow hat sich aktiviert!");
            player.playSound(player.getEyeLocation(), Sound.ENTITY_GHAST_SHOOT, 4, 0.5f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20,3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*20, 3));
            cooldown.add(player.getUniqueId());

            Bukkit.getScheduler().runTaskLater(Plugin.getPlugin(Plugin.class), (task) -> {
                player.sendMessage("Deine Chestplate ist jetzt wieder bereit!");
                cooldown.remove(player.getUniqueId());
            }, Plugin.getFileManager().getConfig().getInt("shield_bow_reset_time")*60*20);
        }

    }

    @Override
    public ItemStack build() {
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE).setLore("Shieldbow")
                .addEnchantment(Enchantment.DIG_SPEED, 1,true).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
    }

    @Override
    public ArrayList<Recipe> setRecipe() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        ShapelessRecipe recipe1 = new ShapelessRecipe(build());
        recipe1.addIngredient(Material.DIAMOND_CHESTPLATE);
        recipe1.addIngredient(Material.DRAGON_BREATH);
        recipe1.addIngredient(Material.GOLDEN_APPLE);

        recipes.add(recipe1);
        return recipes;
    }
}
