package net.jukoz.me.datageneration.content.loot_tables;

import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.crop.TomatoCropBlock;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;

import java.util.ArrayList;
import java.util.List;

public class CropDrops {
    public static List<CropDrop> blocks = new ArrayList<>() {
        {
            add(new CropDrop(BlockStatePropertyLootCondition.builder(ModNatureBlocks.TOMATO_CROP).properties(StatePredicate.Builder.create().exactMatch(TomatoCropBlock.AGE, 3)),
                    ModNatureBlocks.TOMATO_CROP, ModFoodItems.TOMATO, ModFoodItems.TOMATO));
        }
    };

    public static class CropDrop {
        public BlockStatePropertyLootCondition.Builder builder;
        public Block crop_block;
        public Item fruit;
        public Item seeds;

        public CropDrop(BlockStatePropertyLootCondition.Builder builder, Block block, Item fruit, Item seeds) {
            this.builder = builder;
            this.crop_block = block;
            this.fruit = fruit;
            this.seeds = seeds;
        }

    }
}
