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
        }
    };
}
