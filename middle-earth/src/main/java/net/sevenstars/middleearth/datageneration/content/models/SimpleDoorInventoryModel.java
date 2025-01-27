package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.item.ModDecorativeItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorInventoryModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ModDecorativeItems.BLUE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.GREEN_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.LIGHT_BLUE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.RED_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.YELLOW_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.LARCH_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.SPRUCE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.TALL_BLACK_PINE_DOOR.asItem());
            add(ModDecorativeItems.OAK_STABLE_DOOR.asItem());
            add(ModDecorativeItems.REINFORCED_SPRUCE_DOOR.asItem());
            add(ModDecorativeItems.REINFORCED_BLACK_PINE_DOOR.asItem());
            add(ModDecorativeItems.SIMPLE_LARCH_GATE.asItem());
            add(ModDecorativeItems.RICKETY_SIMPLE_LARCH_DOOR.asItem());
            add(ModDecorativeItems.SPRUCE_STABLE_DOOR.asItem());
            add(ModDecorativeItems.LARGE_STURDY_DOOR.asItem());
            add(ModDecorativeItems.GREAT_GONDORIAN_GATE.asItem());
            add(ModDecorativeItems.GREAT_DWARVEN_GATE.asItem());
            add(ModDecorativeItems.VARNISHED_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.RUINED_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.HIDDEN_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.GREAT_ELVEN_GATE.asItem());
            add(ModDecorativeItems.GREAT_ORCISH_GATE.asItem());
        }
    };
}
