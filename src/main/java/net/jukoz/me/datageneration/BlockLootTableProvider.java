package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.crop.TomatoCropBlock;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.CropDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;

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
        for(CropDrops.CropDrop cd : CropDrops.blocks) {
            addDrop(cd.crop_block, cropDrops(cd.crop_block, cd.fruit, cd.seeds, cd.builder));
        }
    }
}
