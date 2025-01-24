package net.jukoz.me.datageneration.content.loot_tables;

import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class LeavesDrops {

    public record LeavesDrop(Block block, Block drop) {}
    public static List<LeavesDrop> blocks = new ArrayList<>() {
        {
            add(new LeavesDrop(ModNatureBlocks.LEBETHRON_LEAVES, ModNatureBlocks.LEBETHRON_SAPLING));

            add(new LeavesDrop(ModNatureBlocks.BERRY_HOLLY_LEAVES, ModNatureBlocks.HOLLY_SAPLING));

            add(new LeavesDrop(ModNatureBlocks.DRY_LARCH_LEAVES, ModNatureBlocks.LARCH_SAPLING));

            add(new LeavesDrop(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, ModNatureBlocks.MALLORN_SAPLING));

            add(new LeavesDrop(ModNatureBlocks.DRY_PINE_LEAVES, ModNatureBlocks.PINE_SAPLING));
        }
    };
}
