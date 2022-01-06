package visionUtils.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    /*
    * Build-Methods
    * */

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreLvl) {
        meta.addEnchant(enchantment, level, ignoreLvl);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public void addNBTTag(String key, NBTBase value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound comp = nmsItem.t();
        comp.a(key, value);
        nmsItem.c(comp);
        meta = CraftItemStack.getItemMeta(nmsItem);
    }

    public void addNBTTag(String key, String value) {
        addNBTTag(key, NBTTagString.a(value));
    }

    public void removeNBTTag(String key) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound comp = nmsItem.t();
        comp.r(key);
        nmsItem.c(comp);
        meta = CraftItemStack.getItemMeta(nmsItem);
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    /*
    * Get-Methods
    * */

    public NBTBase getNBTTag(String key) {
        return getNBTTag(item, key);
    }

    public String getNBTTagString(String key) {
        return getNBTTagString(item, key);
    }

    /*
    * Statics
    * */

    public static NBTBase getNBTTag(ItemStack item, String key) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound comp = nmsItem.t();
            return comp.c(key);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getNBTTagString(ItemStack item, String key) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound comp = nmsItem.t();
            String value = comp.l(key);
            return value.isEmpty() ? null : value;
        } catch (Exception exception) {
            return null;
        }
    }
}
