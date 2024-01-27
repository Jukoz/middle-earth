package net.jukoz.me.datageneration.content.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Logs {
    public static List<Block> logs = new ArrayList<>() {
        {

        }
    };

    public static List<Item> getItemPlanks() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : logs) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
