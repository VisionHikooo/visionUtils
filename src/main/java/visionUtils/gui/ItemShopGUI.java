package visionUtils.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import visionUtils.utils.ItemBuilder;

import java.util.HashMap;

public class ItemShopGUI extends GUI {

    public ItemShopGUI(int length, String title) {
        super(length, title);
        getInventory().setItem(10, new ItemBuilder(Material.DIAMOND).addEnchantment(Enchantment.BINDING_CURSE, 1, true)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS).build());
    }

    @Override
    protected HashMap<Integer, InventoryClickAction> defineClickActions() {
        HashMap<Integer, InventoryClickAction> clicks = new HashMap<>();


        clicks.put(10, (player, clickType) -> {
            if (clickType == ClickType.LEFT)
                player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_SWORD).setName(ChatColor.GOLD + "Excalibur")
                        .addEnchantment(Enchantment.DAMAGE_ALL, 8, true).setLore("", "Der fickt alles !!!").build());
            else if (clickType == ClickType.RIGHT)
                player.sendMessage(ChatColor.GOLD + "[Käufer] " + ChatColor.WHITE +  "Ich kaufe zurzeit nichts");
            else if (clickType == ClickType.MIDDLE)
                player.teleport(player.getWorld().getSpawnLocation());
        });


        return clicks;
    }
}
