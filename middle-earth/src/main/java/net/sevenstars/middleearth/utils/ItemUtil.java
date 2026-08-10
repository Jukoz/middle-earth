package net.sevenstars.middleearth.utils;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemUtil {

    public static Identifier getIdentifier(Item item){
        return Registries.ITEM.getId(item);
    }
}
