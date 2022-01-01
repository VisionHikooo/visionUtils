package visionUtils.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import visionUtils.listener.events.SpawnerBreakEvent;

public class SpawnerBreakListener implements Listener {

    @EventHandler
    public void onSpawnerBreak(SpawnerBreakEvent event) {
        if (!(event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")))
            return;



        if (!(event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)))
            return;

        CreatureSpawner creatureSpawner = (CreatureSpawner) event.getSpawner().getState();
        EntityType type = creatureSpawner.getSpawnedType();

        ItemStack spawnerItem = new ItemStack(creatureSpawner.getType());
        BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();

        CreatureSpawner creatureSpawner2 = (CreatureSpawner) meta.getBlockState();
        creatureSpawner2.setSpawnedType(type);
        meta.setBlockState(creatureSpawner2);
        spawnerItem.setItemMeta(meta);

        event.getPlayer().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Du hast einen Spawner abgebaut!");
        event.getSpawner().getWorld().dropItemNaturally(event.getSpawner().getLocation(), spawnerItem);
    }
}
