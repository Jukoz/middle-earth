package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemUtil {

    public static ResourceLocation getIdentifier(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
