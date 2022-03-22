package visionUtils.customItems.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import visionUtils.customItems.VisionItem;
import visionUtils.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.UUID;

public class Enma extends VisionItem {
    @Override
    public ItemStack build() {
        return new ItemBuilder(Material.DIAMOND_SWORD).setName("ENMA")
                .addModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),"generic.attackDamage", 100.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
                .addModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),"generic.attackSpeed", -0.6, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND))
                .build();
    }

    @Override
    public ArrayList<Recipe> setRecipe() {
        return null;
    }
}
