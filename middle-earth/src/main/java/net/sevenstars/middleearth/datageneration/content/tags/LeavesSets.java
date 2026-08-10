package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LeavesSets {
    public static List<Block> leaves = new ArrayList<>() {
        {
            add(ModNatureBlocks.LEBETHRON_LEAVES);

            add(ModNatureBlocks.BERRY_HOLLY_LEAVES);

            add(ModNatureBlocks.DRY_LARCH_LEAVES);

            add(ModNatureBlocks.FLOWERING_MALLORN_LEAVES);

            add(ModNatureBlocks.ORANGE_MAPLE_LEAVES);
            add(ModNatureBlocks.RED_MAPLE_LEAVES);
            add(ModNatureBlocks.YELLOW_MAPLE_LEAVES);

            add(ModNatureBlocks.DRY_PINE_LEAVES);
        }
    };

    public static List<Block> grayscaleLeaves = new ArrayList<>() {
        {

        }
    };

    public static List<Item> getItemLeaves() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : leaves) {
            newList.add(block.asItem());
        }
        for (Block block : grayscaleLeaves) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
