package visionUtils.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private List<Material> crops = Arrays.asList(Material.WHEAT, Material.BEETROOTS, Material.POTATOES,
            Material.CARROTS, Material.COCOA_BEANS, Material.NETHER_WART);
    private List<Material> seeds = Arrays.asList(Material.WHEAT_SEEDS, Material.BEETROOT_SEEDS, Material.POTATOES,
            Material.CARROTS, Material.COCOA_BEANS, Material.NETHER_WART);

    @EventHandler
    public void onBlockBreak(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand().equals(EquipmentSlot.HAND)) {

            if (crops.contains(event.getClickedBlock().getType())) {

                Ageable crop = (Ageable) event.getClickedBlock().getBlockData();

                if (crop.getAge() == crop.getMaximumAge()) {
                    Collection<ItemStack> drops = event.getClickedBlock().getDrops();
                    drops.stream().map(drop ->  {
                        if (seeds.contains(drop.getType())) {
                            if (drop.getAmount() == 1)
                                return new ItemStack(Material.AIR);
                            return new ItemStack(drop.getType(), drop.getAmount());
                        }
                        return drop;
                    }).forEach(
                            itemStack -> {
                                if (itemStack.getType() != Material.AIR)
                                    event.getPlayer().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), itemStack);
                            }
                    );

                    crop.setAge(0);
                    event.getClickedBlock().setBlockData(crop);
                }
            }
        } else if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.FARMLAND) {
                event.setCancelled(true);
                Block crop = event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getLocation().add(0,1,0));
                Ageable ageable = (Ageable) crop.getBlockData();

                ageable.setAge(0);
                crop.setBlockData(ageable);
                event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_ROOTED_DIRT_PLACE, 3, 0.5f);
            }
        }
    }

}
