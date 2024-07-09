package net.jukoz.me.registries;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.tags.LeavesSets;
import net.jukoz.me.datageneration.content.tags.Saplings;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.block.cauldron.CauldronBehavior;

public class ModRegistries {

    public static void registerFlammableBlocks(){
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_STAIRS, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.WOOD_PILE, 5, 5);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_TABLE, 5, 20);
    }

    public static void registerAgingCopperBlocks(){
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.THATCH.block(), RoofBlockSets.WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.THATCH.slab(), RoofBlockSets.WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.THATCH.verticalSlab(), RoofBlockSets.WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.THATCH.stairs(), RoofBlockSets.WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.THATCH.wall(), RoofBlockSets.WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.WEATHERED_THATCH.block(), RoofBlockSets.AGED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.WEATHERED_THATCH.slab(), RoofBlockSets.AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.WEATHERED_THATCH.verticalSlab(), RoofBlockSets.AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.WEATHERED_THATCH.stairs(), RoofBlockSets.AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.WEATHERED_THATCH.wall(), RoofBlockSets.AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.AGED_THATCH.block(), RoofBlockSets.OLD_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.AGED_THATCH.slab(), RoofBlockSets.OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.AGED_THATCH.verticalSlab(), RoofBlockSets.OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.AGED_THATCH.stairs(), RoofBlockSets.OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.AGED_THATCH.wall(), RoofBlockSets.OLD_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.OLD_THATCH.block(), RoofBlockSets.ROTTEN_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.OLD_THATCH.slab(), RoofBlockSets.ROTTEN_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.OLD_THATCH.verticalSlab(), RoofBlockSets.ROTTEN_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.OLD_THATCH.stairs(), RoofBlockSets.ROTTEN_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(RoofBlockSets.OLD_THATCH.wall(), RoofBlockSets.ROTTEN_THATCH.wall());

        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.THATCH.block(), RoofBlockSets.WAXED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.THATCH.slab(), RoofBlockSets.WAXED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.THATCH.verticalSlab(), RoofBlockSets.WAXED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.THATCH.stairs(), RoofBlockSets.WAXED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.THATCH.wall(), RoofBlockSets.WAXED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.WEATHERED_THATCH.block(), RoofBlockSets.WAXED_WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.WEATHERED_THATCH.slab(), RoofBlockSets.WAXED_WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.WEATHERED_THATCH.verticalSlab(), RoofBlockSets.WAXED_WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.WEATHERED_THATCH.stairs(), RoofBlockSets.WAXED_WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.WEATHERED_THATCH.wall(), RoofBlockSets.WAXED_WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.AGED_THATCH.block(), RoofBlockSets.WAXED_AGED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.AGED_THATCH.slab(), RoofBlockSets.WAXED_AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.AGED_THATCH.verticalSlab(), RoofBlockSets.WAXED_AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.AGED_THATCH.stairs(), RoofBlockSets.WAXED_AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.AGED_THATCH.wall(), RoofBlockSets.WAXED_AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.OLD_THATCH.block(), RoofBlockSets.WAXED_OLD_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.OLD_THATCH.slab(), RoofBlockSets.WAXED_OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.OLD_THATCH.verticalSlab(), RoofBlockSets.WAXED_OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.OLD_THATCH.stairs(), RoofBlockSets.WAXED_OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.OLD_THATCH.wall(), RoofBlockSets.WAXED_OLD_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.ROTTEN_THATCH.block(), RoofBlockSets.WAXED_ROTTEN_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.ROTTEN_THATCH.slab(), RoofBlockSets.WAXED_ROTTEN_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.ROTTEN_THATCH.verticalSlab(), RoofBlockSets.WAXED_ROTTEN_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.ROTTEN_THATCH.stairs(), RoofBlockSets.WAXED_ROTTEN_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(RoofBlockSets.ROTTEN_THATCH.wall(), RoofBlockSets.WAXED_ROTTEN_THATCH.wall());
    }

    public static void registerFuels(){
        FuelRegistry registry =  FuelRegistry.INSTANCE;

        registry.add(ModNatureBlocks.MIRKWOOD_ROOTS, 300);
        registry.add(ModNatureBlocks.GREEN_SHRUB, 100);
        registry.add(ModNatureBlocks.TAN_SHRUB, 100);

        registry.add(ModDecorativeBlocks.ROPE, 150);
        registry.add(ModDecorativeBlocks.WOOD_PILE, 200);

        registry.add(ModBlocks.WHITE_WOOL_SLAB, 50);
        registry.add(ModBlocks.ORANGE_WOOL_SLAB, 50);
        registry.add(ModBlocks.MAGENTA_WOOL_SLAB, 50);
        registry.add(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 50);
        registry.add(ModBlocks.YELLOW_WOOL_SLAB, 50);
        registry.add(ModBlocks.LIME_WOOL_SLAB, 50);
        registry.add(ModBlocks.PINK_WOOL_SLAB, 50);
        registry.add(ModBlocks.GRAY_WOOL_SLAB, 50);
        registry.add(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 50);
        registry.add(ModBlocks.CYAN_WOOL_SLAB, 50);
        registry.add(ModBlocks.PURPLE_WOOL_SLAB, 50);
        registry.add(ModBlocks.BLUE_WOOL_SLAB, 50);
        registry.add(ModBlocks.BROWN_WOOL_SLAB, 50);
        registry.add(ModBlocks.GREEN_WOOL_SLAB, 50);
        registry.add(ModBlocks.RED_WOOL_SLAB, 50);
        registry.add(ModBlocks.BLACK_WOOL_SLAB, 50);

        registry.add(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.RED_WOOL_VERTICAL_SLAB, 50);
        registry.add(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 50);

        registry.add(ModBlocks.WHITE_WOOL_STAIRS, 100);
        registry.add(ModBlocks.ORANGE_WOOL_STAIRS, 100);
        registry.add(ModBlocks.MAGENTA_WOOL_STAIRS, 100);
        registry.add(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 100);
        registry.add(ModBlocks.YELLOW_WOOL_STAIRS, 100);
        registry.add(ModBlocks.LIME_WOOL_STAIRS, 100);
        registry.add(ModBlocks.PINK_WOOL_STAIRS, 100);
        registry.add(ModBlocks.GRAY_WOOL_STAIRS, 100);
        registry.add(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 100);
        registry.add(ModBlocks.CYAN_WOOL_STAIRS, 100);
        registry.add(ModBlocks.PURPLE_WOOL_STAIRS, 100);
        registry.add(ModBlocks.BLUE_WOOL_STAIRS, 100);
        registry.add(ModBlocks.BROWN_WOOL_STAIRS, 100);
        registry.add(ModBlocks.GREEN_WOOL_STAIRS, 100);
        registry.add(ModBlocks.RED_WOOL_STAIRS, 100);
        registry.add(ModBlocks.BLACK_WOOL_STAIRS, 100);

        registry.add(ModWeaponItems.ROHIRRIC_BOW, 300);
        registry.add(ModWeaponItems.LORIEN_BOW, 300);
        registry.add(ModWeaponItems.GONDOR_BOW, 300);
        registry.add(ModWeaponItems.UMBAR_BOW, 300);
        registry.add(ModWeaponItems.DALISH_BOW, 300);

        registry.add(ModWeaponItems.WOODEN_DAGGER, 150);
        registry.add(ModWeaponItems.DUNLAND_CLUB, 300);

        registry.add(ModDecorativeBlocks.OAK_STOOL, 300);
        registry.add(ModDecorativeBlocks.OAK_CHAIR, 300);
        registry.add(ModDecorativeBlocks.OAK_TABLE, 300);

        registry.add(ModDecorativeBlocks.BIRCH_STOOL, 300);
        registry.add(ModDecorativeBlocks.BIRCH_CHAIR, 300);
        registry.add(ModDecorativeBlocks.BIRCH_TABLE, 300);

        registry.add(ModDecorativeBlocks.SPRUCE_STOOL, 300);
        registry.add(ModDecorativeBlocks.SPRUCE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.SPRUCE_TABLE, 300);

        registry.add(ModDecorativeBlocks.DARK_OAK_STOOL, 300);
        registry.add(ModDecorativeBlocks.DARK_OAK_CHAIR, 300);
        registry.add(ModDecorativeBlocks.DARK_OAK_TABLE, 300);

        registry.add(ModDecorativeBlocks.ACACIA_STOOL, 300);
        registry.add(ModDecorativeBlocks.ACACIA_CHAIR, 300);
        registry.add(ModDecorativeBlocks.ACACIA_TABLE, 300);

        registry.add(ModDecorativeBlocks.JUNGLE_STOOL, 300);
        registry.add(ModDecorativeBlocks.JUNGLE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.JUNGLE_TABLE, 300);

        registry.add(ModDecorativeBlocks.MANGROVE_STOOL, 300);
        registry.add(ModDecorativeBlocks.MANGROVE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.MANGROVE_TABLE, 300);

        registry.add(ModDecorativeBlocks.CHERRY_STOOL, 300);
        registry.add(ModDecorativeBlocks.CHERRY_CHAIR, 300);
        registry.add(ModDecorativeBlocks.CHERRY_TABLE, 300);

        registry.add(ModDecorativeBlocks.BAMBOO_STOOL, 300);
        registry.add(ModDecorativeBlocks.BAMBOO_CHAIR, 300);
        registry.add(ModDecorativeBlocks.BAMBOO_TABLE, 300);
    }

    public static void registerComposterBlocks(){
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(MushroomBlockSets.GRAY_MUSHROOM.stem(), 0.50f);
        registry.add(MushroomBlockSets.DARK_MUSHROOM.stem(), 0.50f);

        registry.add(ModNatureBlocks.TAN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.GREEN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.MORDOR_LICHEN, 0.30f);
        registry.add(ModNatureBlocks.MORGUL_IVY, 0.40f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_BLOCK, 0.65f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS, 0.35f);
        registry.add(ModNatureBlocks.MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.FOREST_MOSS_BLOCK, 0.65f);

        registry.add(ModNatureBlocks.MALLOS, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_FLOWER, 0.65f);
        registry.add(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, 0.65f);

        registry.add(ModNatureBlocks.BROWN_GRASS, 0.30f);
        registry.add(ModNatureBlocks.DYING_GRASS, 0.30f);
        registry.add(ModNatureBlocks.DRY_GRASS, 0.30f);
        registry.add(ModNatureBlocks.GRASS_TUFT, 0.30f);
        registry.add(ModNatureBlocks.WHEATGRASS, 0.30f);
        registry.add(ModNatureBlocks.WILD_GRASS, 0.30f);
        registry.add(ModNatureBlocks.WILDERGRASS, 0.30f);
        registry.add(ModNatureBlocks.BEACH_GRASS, 0.30f);
        registry.add(ModNatureBlocks.COASTAL_PANIC_GRASS, 0.30f);
        registry.add(ModNatureBlocks.SEDUM, 0.30f);
        registry.add(ModNatureBlocks.YELLOW_SEDUM, 0.30f);
        registry.add(ModNatureBlocks.SHORT_CATTAILS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_BULRUSH, 0.30f);
        registry.add(ModNatureBlocks.HEATHER, 0.50f);
        registry.add(ModNatureBlocks.RED_HEATHER, 0.50f);
        registry.add(ModNatureBlocks.DEAD_HEATHER, 0.30f);
        registry.add(ModNatureBlocks.HEATHER_BUSH, 0.65f);
        registry.add(ModNatureBlocks.GIANT_HOROKAKA, 0.65f);
        registry.add(ModNatureBlocks.HOROKAKA, 0.50f);

        registry.add(ModNatureBlocks.SHRIVELED_SHRUB, 0.30f);

        registry.add(ModNatureBlocks.BROWN_BOLETE, 0.65f);
        registry.add(ModNatureBlocks.CAVE_AMANITA, 0.65f);
        registry.add(ModNatureBlocks.DEEP_FIRECAP, 0.65f);
        registry.add(ModNatureBlocks.GHOSTSHROOM, 0.65f);
        registry.add(ModNatureBlocks.MORSEL, 0.65f);
        registry.add(ModNatureBlocks.SKY_FIRECAP, 0.65f);
        registry.add(ModNatureBlocks.TRUMPET_SHROOM, 0.65f);
        registry.add(ModNatureBlocks.TALL_TRUMPET_SHROOM, 0.85f);
        registry.add(ModNatureBlocks.TUBESHRROM, 0.65f);
        registry.add(ModNatureBlocks.TALL_TRUMPET_SHROOM, 0.85f);
        registry.add(ModNatureBlocks.VIOLET_CAPS, 0.65f);
        registry.add(ModNatureBlocks.WHITE_MUSHROOM, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_AMANITA, 0.65f);

        registry.add(ModNatureBlocks.BROWN_BOLETE_TILLER, 0.40f);
        registry.add(ModNatureBlocks.CAVE_AMANITA_TILLER, 0.40f);
        registry.add(ModNatureBlocks.DEEP_FIRECAP_TILLER, 0.40f);
        registry.add(ModNatureBlocks.GHOSTSHROOM_TILLER, 0.40f);
        registry.add(ModNatureBlocks.MORSEL_TILLER, 0.40f);
        registry.add(ModNatureBlocks.SKY_FIRECAP_TILLER, 0.40f);
        registry.add(ModNatureBlocks.VIOLET_CAPS_TILLER, 0.40f);
        registry.add(ModNatureBlocks.WHITE_MUSHROOM_TILLER, 0.40f);
        registry.add(ModNatureBlocks.YELLOW_AMANITA_TILLER, 0.40f);

        registry.add(ModNatureBlocks.BROWN_BOLETE_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.CAVE_AMANITA_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.DEEP_FIRECAP_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.SKY_FIRECAP_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.YELLOW_AMANITA_BLOCK, 0.85F);

        Saplings.saplings.forEach(sapling -> {
            registry.add(sapling, 0.3F);
        });
        LeavesSets.blocks.forEach(block -> {
            registry.add(block, 0.3F);
        });
        registry.add(ModNatureBlocks.LEBETHRON_LEAVES, 0.3F);

        registry.add(ModNatureBlocks.DRY_PINE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.PINE_BRANCHES, 0.3F);

        registry.add(ModNatureBlocks.MAPLE_LEAVES, 0.3F);


        registry.add(ModNatureBlocks.WILD_PIPEWEED, 0.5F);
        registry.add(ModNatureBlocks.WILD_FLAX, 0.5F);
        registry.add(ModNatureBlocks.WILD_TOMATO, 0.5F);
        registry.add(ModNatureBlocks.WILD_BELL_PEPPER, 0.5F);
        registry.add(ModNatureBlocks.WILD_CUCUMBER, 0.5F);
        registry.add(ModNatureBlocks.WILD_GARLIC, 0.5F);
        registry.add(ModNatureBlocks.WILD_ONION, 0.5F);
        registry.add(ModNatureBlocks.WILD_LETTUCE, 0.5F);
        registry.add(ModNatureBlocks.WILD_LEEK, 0.5F);
        registry.add(ModNatureBlocks.WILD_POTATO, 0.5F);
        registry.add(ModNatureBlocks.WILD_CARROT, 0.5F);
        registry.add(ModNatureBlocks.WILD_BEETROOT, 0.5F);

        registry.add(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, 0.3F);
        registry.add(ModNatureBlocks.MIRKWOOD_ROOTS, 0.3F);
        registry.add(ModNatureBlocks.MIRKWOOD_SPIDER_EGG, 0.8F);

        registry.add(ModFoodItems.LEMBAS, 1.0F);
        registry.add(ModFoodItems.MAGGOTY_BREAD, 0.8F);
        registry.add(ModFoodItems.TOUGH_BERRIES, 0.3F);
        registry.add(ModFoodItems.STRAWBERRY, 0.5F);
        registry.add(ModFoodItems.TOMATO, 0.5F);
        registry.add(ModFoodItems.BELL_PEPPER, 0.5F);
        registry.add(ModFoodItems.CUCUMBER, 0.5F);
        registry.add(ModFoodItems.GARLIC, 0.5F);
        registry.add(ModFoodItems.LEEK, 0.5F);
        registry.add(ModFoodItems.LETTUCE, 0.5F);
        registry.add(ModFoodItems.ONION, 0.5F);

        registry.add(ModFoodItems.BERRY_PIE, 1.0F);
        registry.add(ModFoodItems.VEGETABLE_SKEWER, 1.0F);
        registry.add(ModFoodItems.VEGETABLE_SOUP, 1.0F);

        registry.add(ModResourceItems.STRAW, 0.3F);

        registry.add(ModBlocks.STRAW_BLOCK, 0.5F);
        registry.add(ModBlocks.STRAW_STAIRS, 0.5F);
        registry.add(ModBlocks.STRAW_SLAB, 0.5F);
        registry.add(ModBlocks.STRAW_VERTICAL_SLAB, 0.5F);
        registry.add(ModBlocks.STRAW_WALL, 0.5F);

        registry.add(ModResourceItems.REEDS, 0.3F);

        registry.add(ModBlocks.REED_BLOCK, 0.5F);
        registry.add(ModBlocks.REED_STAIRS, 0.5F);
        registry.add(ModBlocks.REED_SLAB, 0.5F);
        registry.add(ModBlocks.REED_VERTICAL_SLAB, 0.5F);
        registry.add(ModBlocks.REED_WALL, 0.5F);

        registry.add(ModResourceItems.FLAX, 0.3F);
        registry.add(ModResourceItems.PIPEWEED, 0.3F);
        registry.add(ModResourceItems.PINECONE, 0.3F);

        registry.add(ModResourceItems.BELL_PEPPER_SEEDS, 0.3F);
        registry.add(ModResourceItems.CUCUMBER_SEEDS, 0.3F);
        registry.add(ModResourceItems.FLAX_SEEDS, 0.3F);
        registry.add(ModResourceItems.LETTUCE_SEEDS, 0.3F);
        registry.add(ModResourceItems.TOMATO_SEEDS, 0.3F);
        registry.add(ModResourceItems.PIPEWEED_SEEDS, 0.3F);
    }

    public static void registerCauldronBehaviour(){
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ModEquipmentItems.GAMBESON, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ModEquipmentItems.CLOAK, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ModEquipmentItems.CLOAK_HOOD, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(ModEquipmentItems.TUNIC_CLOAK, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }
}
