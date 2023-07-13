package net.jesteur.me.datageneration.content.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Planks {
    public static List<Block> planks = new ArrayList<>() {
        {

        }
    };

    public static List<Item> getItemPlanks() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : planks) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
