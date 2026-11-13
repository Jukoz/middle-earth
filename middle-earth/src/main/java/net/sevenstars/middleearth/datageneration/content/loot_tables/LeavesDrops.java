package net.sevenstars.middleearth.datageneration.content.loot_tables;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LeavesDrops {

    public record LeavesDrop(Block block, Block drop) {}
    public static List<LeavesDrop> blocks = new ArrayList<>() {
        {
            add(new LeavesDrop(WoodBlockSetRegistryME.ASPEN_SET.leaves, NatureBlockRegistryME.ASPEN_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.BEECH_SET.leaves, NatureBlockRegistryME.BEECH_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.LARCH_SET.leaves, NatureBlockRegistryME.LARCH_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.CHESTNUT_SET.leaves, NatureBlockRegistryME.CHESTNUT_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.FIR_SET.leaves, NatureBlockRegistryME.FIR_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.HOLLY_SET.leaves, NatureBlockRegistryME.HOLLY_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.MALLORN_SET.leaves, NatureBlockRegistryME.MALLORN_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.MIRKWOOD_SET.leaves, NatureBlockRegistryME.MIRKWOOD_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.PALM_SET.leaves, NatureBlockRegistryME.PALM_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.PINE_SET.leaves, NatureBlockRegistryME.PINE_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.BLACK_PINE_SET.leaves, NatureBlockRegistryME.BLACK_PINE_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.WHITE_SPRUCE_SET.leaves, NatureBlockRegistryME.WHITE_SPRUCE_SAPLING));
            add(new LeavesDrop(WoodBlockSetRegistryME.WILLOW_SET.leaves, NatureBlockRegistryME.WILLOW_SAPLING));
            add(new LeavesDrop(NatureBlockRegistryME.LEBETHRON_LEAVES, NatureBlockRegistryME.LEBETHRON_SAPLING));
            add(new LeavesDrop(NatureBlockRegistryME.BERRY_HOLLY_LEAVES, NatureBlockRegistryME.HOLLY_SAPLING));
            add(new LeavesDrop(NatureBlockRegistryME.DRY_LARCH_LEAVES, NatureBlockRegistryME.LARCH_SAPLING));
            add(new LeavesDrop(NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES, NatureBlockRegistryME.MALLORN_SAPLING));
            add(new LeavesDrop(NatureBlockRegistryME.DRY_PINE_LEAVES, NatureBlockRegistryME.PINE_SAPLING));
        }
    };
}
