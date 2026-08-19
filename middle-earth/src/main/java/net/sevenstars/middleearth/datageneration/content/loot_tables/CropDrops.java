package net.sevenstars.middleearth.datageneration.content.loot_tables;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.special.crop.*;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import java.util.ArrayList;
import java.util.List;

public class CropDrops {
    public static List<CropDrop> crops = new ArrayList<>() {
        {
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.TOMATO_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoCropBlock.AGE, 3)),
                    ModNatureBlocks.TOMATO_CROP, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.BELL_PEPPER_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BellpepperCropBlock.AGE, 4)),
                    ModNatureBlocks.BELL_PEPPER_CROP, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.CUCUMBER_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CucumberCropBlock.AGE, 3)),
                    ModNatureBlocks.CUCUMBER_CROP, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.FLAX_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FlaxCropBlock.AGE, 3)),
                    ModNatureBlocks.FLAX_CROP, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.GARLIC_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GarlicCropBlock.AGE, 3)),
                    ModNatureBlocks.GARLIC_CROP, FoodItemsME.GARLIC, FoodItemsME.GARLIC));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.LEEK_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LeekCropBlock.AGE, 3)),
                    ModNatureBlocks.LEEK_CROP, FoodItemsME.LEEK, FoodItemsME.LEEK));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.LETTUCE_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LettuceCropBlock.AGE, 3)),
                    ModNatureBlocks.LETTUCE_CROP, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.ONION_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OnionCropBlock.AGE, 3)),
                    ModNatureBlocks.ONION_CROP, FoodItemsME.ONION, FoodItemsME.ONION));
            add(new CropDrop(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModNatureBlocks.PIPEWEED_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeweedCropBlock.AGE, 3)),
                    ModNatureBlocks.PIPEWEED_CROP, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS));
        }
    };

    public static List<CropDrop> wild_crops = new ArrayList<>() {
        {
            add(new CropDrop(null, ModNatureBlocks.WILD_PIPEWEED, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_FLAX, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_TOMATO, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_BELL_PEPPER, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_CUCUMBER, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_LEEK, FoodItemsME.LEEK, FoodItemsME.LEEK));
            add(new CropDrop(null, ModNatureBlocks.WILD_LETTUCE, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS));
            add(new CropDrop(null, ModNatureBlocks.WILD_GARLIC, FoodItemsME.GARLIC, FoodItemsME.GARLIC));
            add(new CropDrop(null, ModNatureBlocks.WILD_ONION, FoodItemsME.ONION, FoodItemsME.ONION));
            add(new CropDrop(null, ModNatureBlocks.WILD_POTATO, Items.POTATO, Items.POTATO));
            add(new CropDrop(null, ModNatureBlocks.WILD_CARROT, Items.CARROT, Items.CARROT));
            add(new CropDrop(null, ModNatureBlocks.WILD_BEETROOT, Items.BEETROOT, Items.BEETROOT_SEEDS));
        }
    };

    public static class CropDrop {
        public LootItemBlockStatePropertyCondition.Builder builder;
        public Block crop_block;
        public Item fruit;
        public Item seeds;

        public CropDrop(LootItemBlockStatePropertyCondition.Builder builder, Block block, Item fruit, Item seeds) {
            this.builder = builder;
            this.crop_block = block;
            this.fruit = fruit;
            this.seeds = seeds;
        }
    }
}
