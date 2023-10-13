package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.item.ModEquipmentItems;
import net.jesteur.me.item.ModRessourceItems;
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

            add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);
            add(ModEquipmentItems.NAZGUL_CLOAK);
            add(ModEquipmentItems.NAZGUL_PANTS);
            add(ModEquipmentItems.NAZGUL_BOOTS);
        }
    };
}
