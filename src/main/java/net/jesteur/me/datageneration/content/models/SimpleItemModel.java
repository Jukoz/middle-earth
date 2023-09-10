package net.jesteur.me.datageneration.content.models;

import net.jesteur.me.item.ModEquipmentItems;
import net.jesteur.me.item.ModRessourceItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);
            add(ModEquipmentItems.NAZGUL_CLOAK);
        }
    };
}
