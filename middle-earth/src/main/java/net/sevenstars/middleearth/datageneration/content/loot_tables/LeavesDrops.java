package net.sevenstars.middleearth.datageneration.content.loot_tables;

import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LeavesDrops {

    public record LeavesDrop(Block block, Block drop) {}
    public static List<LeavesDrop> blocks = new ArrayList<>() {
        {
            add(new LeavesDrop(WoodBlockSets.ASPEN_SET.leaves, ModNatureBlocks.ASPEN_SAPLING));
            add(new LeavesDrop(WoodBlockSets.BEECH_SET.leaves, ModNatureBlocks.BEECH_SAPLING));
            add(new LeavesDrop(WoodBlockSets.LARCH_SET.leaves, ModNatureBlocks.LARCH_SAPLING));
            add(new LeavesDrop(WoodBlockSets.CHESTNUT_SET.leaves, ModNatureBlocks.CHESTNUT_SAPLING));
            add(new LeavesDrop(WoodBlockSets.FIR_SET.leaves, ModNatureBlocks.FIR_SAPLING));
            add(new LeavesDrop(WoodBlockSets.HOLLY_SET.leaves, ModNatureBlocks.HOLLY_SAPLING));
            add(new LeavesDrop(WoodBlockSets.MALLORN_SET.leaves, ModNatureBlocks.MALLORN_SAPLING));
            add(new LeavesDrop(WoodBlockSets.MIRKWOOD_SET.leaves, ModNatureBlocks.MIRKWOOD_SAPLING));
            add(new LeavesDrop(WoodBlockSets.PALM_SET.leaves, ModNatureBlocks.PALM_SAPLING));
            add(new LeavesDrop(WoodBlockSets.PINE_SET.leaves, ModNatureBlocks.PINE_SAPLING));
            add(new LeavesDrop(WoodBlockSets.BLACK_PINE_SET.leaves, ModNatureBlocks.BLACK_PINE_SAPLING));
            add(new LeavesDrop(WoodBlockSets.WHITE_SPRUCE_SET.leaves, ModNatureBlocks.WHITE_SPRUCE_SAPLING));
            add(new LeavesDrop(WoodBlockSets.WILLOW_SET.leaves, ModNatureBlocks.WILLOW_SAPLING));
            add(new LeavesDrop(ModNatureBlocks.LEBETHRON_LEAVES, ModNatureBlocks.LEBETHRON_SAPLING));
            add(new LeavesDrop(ModNatureBlocks.BERRY_HOLLY_LEAVES, ModNatureBlocks.HOLLY_SAPLING));
            add(new LeavesDrop(ModNatureBlocks.DRY_LARCH_LEAVES, ModNatureBlocks.LARCH_SAPLING));
            add(new LeavesDrop(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, ModNatureBlocks.MALLORN_SAPLING));
            add(new LeavesDrop(ModNatureBlocks.DRY_PINE_LEAVES, ModNatureBlocks.PINE_SAPLING));
        }
    };
}
