package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModDecorativeItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorInventoryModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ModDecorativeItems.BLUE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.GREEN_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.RED_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.YELLOW_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.LARCH_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.SPRUCE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.REINFORCED_SPRUCE_DOOR.asItem());
            add(ModDecorativeItems.GREAT_DWARVEN_GATE.asItem());
            add(ModDecorativeItems.VARNISHED_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.GREAT_ELVEN_GATE.asItem());
            add(ModDecorativeItems.GREAT_ORCISH_GATE.asItem());
        }
    };
}
