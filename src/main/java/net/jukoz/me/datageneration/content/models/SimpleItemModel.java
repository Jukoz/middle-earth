package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModRessourceItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ModRessourceItems.WATER_PHIAL);

            add(ModRessourceItems.BRONZE_INGOT);
            add(ModRessourceItems.BRONZE_NUGGET);
            add(ModRessourceItems.ORC_STEEL);
            add(ModRessourceItems.ORC_STEEL_NUGGET);

            add(ModFoodItems.TOMATO);
            add(ModFoodItems.BELL_PEPPER);
            add(ModFoodItems.CUCUMBER);
            add(ModFoodItems.LEEK);
            add(ModFoodItems.LETTUCE);
            add(ModRessourceItems.FLAX);
            add(ModRessourceItems.PIPEWEED);

            add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);
            add(ModEquipmentItems.NAZGUL_CLOAK);
            add(ModEquipmentItems.NAZGUL_PANTS);
            add(ModEquipmentItems.NAZGUL_BOOTS);

            add(ModFoodItems.RAW_HORSE);
            add(ModFoodItems.COOKED_HORSE);

            add(ModEquipmentItems.STEEL_TROLL_ARMOR);
            add(ModEquipmentItems.BEAST_CHAINS);
        }
    };
}
