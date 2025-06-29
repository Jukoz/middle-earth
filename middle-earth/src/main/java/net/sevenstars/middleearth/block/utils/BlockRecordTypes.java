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

    public record BaseStoneSet(Block base,
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

    public record PlanksSet(Block base, Block slab, Block verticalSlab, Block stairs, Block fence, Block gate) {
        public static List<Block> getAllBlocks(PlanksSet set){
            List<Block> list = new ArrayList<>();
            list.add(set.base);
            list.add(set.slab);
            list.add(set.verticalSlab);
            list.add(set.stairs);
            list.add(set.fence);
            list.add(set.gate);
            return list;
        }
    }

    public record WoodSet(Block log, Block wood, Block slab, Block verticalSlab, Block stairs, Block wall, Block fence) {
        public static List<Block> getAllBlocks(WoodSet set){
            List<Block> list = new ArrayList<>();
            list.add(set.log);
            list.add(set.wood);
            list.add(set.slab);
            list.add(set.verticalSlab);
            list.add(set.stairs);
            list.add(set.wall);
            list.add(set.fence);
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

    public record WoodRedstoneBlocks(Block door, Block trapdoor, Block pressurePlate, Block button) {
        public static List<Block> getAllBlocks(WoodRedstoneBlocks set){
            List<Block> list = new ArrayList<>();
            list.add(set.door);
            list.add(set.trapdoor);
            list.add(set.pressurePlate);
            list.add(set.button);
            return list;
        }
    }

    public record WoodFurnitureBlocks(Block table, Block chair, Block stool, Block bench, Block ladder) {
        public static List<Block> getAllBlocks(WoodFurnitureBlocks set){
            List<Block> list = new ArrayList<>();
            list.add(set.table);
            list.add(set.chair);
            list.add(set.stool);
            list.add(set.bench);
            list.add(set.ladder);
            return list;
        }
    }
}
