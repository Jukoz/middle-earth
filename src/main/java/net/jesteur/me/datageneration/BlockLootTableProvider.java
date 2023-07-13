package net.jesteur.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jesteur.me.datageneration.content.loot_tables.BlockDrops;
import net.jesteur.me.datageneration.content.loot_tables.LeavesDrops;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {

    protected BlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : BlockDrops.blocks) {
            addDrop(block);
        }
        for (Block block : LeavesDrops.blocks) {
            addDrop(block, BlockLootTableGenerator.dropsWithShears(block));
        }
    }
}
