package net.sevenstars.middleearth.block.utils;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockRecordTypes {

    public record RegularSet(Block base, Block slab, Block verticalSlab, Block stairs, Block wall) {
        public static List<Block> getAllBlocks(RegularSet set){
            List<Block> list = new ArrayList<>();
            list.add(set.base);
            list.add(set.slab);
            list.add(set.verticalSlab);
            list.add(set.stairs);
            list.add(set.wall);
            return list;
        }
    }

    public record BaseStoneSet(Block source, Block base,
                               Block slab, Block verticalSlab, Block stairs, Block wall,
                               Block pressurePlate, Block button, Block trapdoor,
                               Block stool, Block table, Block chair, Block rocks) {
        public static List<Block> getAllBlocks(BaseStoneSet set){
            List<Block> list = new ArrayList<>();
            list.add(set.base);
            list.add(set.slab);
            list.add(set.verticalSlab);
            list.add(set.stairs);
            list.add(set.wall);
            list.add(set.pressurePlate);
            list.add(set.button);
            list.add(set.trapdoor);
            list.add(set.stool);
            list.add(set.table);
            list.add(set.chair);
            list.add(set.rocks);
            return list;
        }
    }

    public record PillarSet(Block base, Block verticalSlab, Block wall) {
        public static List<Block> getAllBlocks(PillarSet set){
            List<Block> list = new ArrayList<>();
            list.add(set.base);
            list.add(set.verticalSlab);
            list.add(set.wall);
            return list;
        }
    }

    public record CarvedWindow(Block block, Block verticalSlab) {
        public static List<Block> getAllBlocks(CarvedWindow set){
            List<Block> list = new ArrayList<>();
            list.add(set.block);
            list.add(set.verticalSlab);
            return list;
        }
    }
}
