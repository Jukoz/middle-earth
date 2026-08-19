package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import java.util.ArrayList;
import java.util.List;

public class Logs {
    public static List<Block> logs = new ArrayList<>() {
        {
            add(ModNatureBlocks.PINE_BRANCHES);
        }
    };

    public static List<Item> getItemLogs() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : logs) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
