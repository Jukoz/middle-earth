package net.sevenstars.middleearth.datageneration.content.loot_tables;

import net.sevenstars.middleearth.features.farming.FarmingBlocks;

import net.sevenstars.middleearth.features.farming.block.crop.BellpepperCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.CucumberCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.FlaxCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.GarlicCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.LeekCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.LettuceCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.OnionCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.PipeweedCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.TomatoCropBlock;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;

import java.util.ArrayList;
import java.util.List;

public class CropDrops {
    public static List<CropDrop> crops = new ArrayList<>() {
        {
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.TOMATO_CROP).properties(StatePredicate.Builder.create().exactMatch(TomatoCropBlock.AGE, 3)),
                    FarmingBlocks.TOMATO_CROP, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.BELL_PEPPER_CROP).properties(StatePredicate.Builder.create().exactMatch(BellpepperCropBlock.AGE, 4)),
                    FarmingBlocks.BELL_PEPPER_CROP, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.CUCUMBER_CROP).properties(StatePredicate.Builder.create().exactMatch(CucumberCropBlock.AGE, 3)),
                    FarmingBlocks.CUCUMBER_CROP, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.FLAX_CROP).properties(StatePredicate.Builder.create().exactMatch(FlaxCropBlock.AGE, 3)),
                    FarmingBlocks.FLAX_CROP, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.GARLIC_CROP).properties(StatePredicate.Builder.create().exactMatch(GarlicCropBlock.AGE, 3)),
                    FarmingBlocks.GARLIC_CROP, FoodItemsME.GARLIC, FoodItemsME.GARLIC));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.LEEK_CROP).properties(StatePredicate.Builder.create().exactMatch(LeekCropBlock.AGE, 3)),
                    FarmingBlocks.LEEK_CROP, FoodItemsME.LEEK, FoodItemsME.LEEK));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.LETTUCE_CROP).properties(StatePredicate.Builder.create().exactMatch(LettuceCropBlock.AGE, 3)),
                    FarmingBlocks.LETTUCE_CROP, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.ONION_CROP).properties(StatePredicate.Builder.create().exactMatch(OnionCropBlock.AGE, 3)),
                    FarmingBlocks.ONION_CROP, FoodItemsME.ONION, FoodItemsME.ONION));
            add(new CropDrop(BlockStatePropertyLootCondition.builder(FarmingBlocks.PIPEWEED_CROP).properties(StatePredicate.Builder.create().exactMatch(PipeweedCropBlock.AGE, 3)),
                    FarmingBlocks.PIPEWEED_CROP, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS));
        }
    };

    public static List<CropDrop> wild_crops = new ArrayList<>() {
        {
            add(new CropDrop(null, NatureBlockRegistryME.WILD_PIPEWEED, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_FLAX, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_TOMATO, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_BELL_PEPPER, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_CUCUMBER, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_LEEK, FoodItemsME.LEEK, FoodItemsME.LEEK));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_LETTUCE, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_GARLIC, FoodItemsME.GARLIC, FoodItemsME.GARLIC));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_ONION, FoodItemsME.ONION, FoodItemsME.ONION));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_POTATO, Items.POTATO, Items.POTATO));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_CARROT, Items.CARROT, Items.CARROT));
            add(new CropDrop(null, NatureBlockRegistryME.WILD_BEETROOT, Items.BEETROOT, Items.BEETROOT_SEEDS));
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
