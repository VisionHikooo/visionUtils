package visionUtils.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VisionItemStack extends ItemStack {

    public VisionItemStack(Material type) {
        super(type);
    }

    public VisionItemStack(Material type, int amount) {
        super(type, amount);
    }

    public VisionItemStack(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public void addNBTTag(String key, NBTBase value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        NBTTagCompound comp = nmsItem.t();
        comp.a(key, value);
        nmsItem.c(comp);
        ItemMeta meta = CraftItemStack.getItemMeta(nmsItem);
        this.setItemMeta(meta);
    }

    public void addNBTTag(String key, String value) {
        addNBTTag(key, NBTTagString.a(value));
    }

    public void removeNBTTag(String key) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        NBTTagCompound comp = nmsItem.t();
        comp.r(key);
        nmsItem.c(comp);
        ItemMeta meta = CraftItemStack.getItemMeta(nmsItem);
        this.setItemMeta(meta);
    }

    public NBTBase getNBTTag(String key) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
            NBTTagCompound comp = nmsItem.t();
            return comp.c(key);
        } catch (Exception exception) {
            return null;
        }
    }

    public String getNBTTagString(String key) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
            NBTTagCompound comp = nmsItem.t();
            String value = comp.l(key);
            return value.isEmpty() ? null : value;
        } catch (Exception exception) {
            return null;
        }
    }
}
