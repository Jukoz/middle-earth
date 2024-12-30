package net.jukoz.me.registries;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.models.HotMetalsModel;
import net.jukoz.me.datageneration.content.models.SimpleDyeableItemModel;
import net.jukoz.me.datageneration.content.tags.LeavesSets;
import net.jukoz.me.datageneration.content.tags.Saplings;
import net.jukoz.me.item.*;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.recipe.ModTags;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.random.Random;

public class ModRegistries {

    public static void registerFlammableBlocks() {
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
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.OAK_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BIRCH_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.SPRUCE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.DARK_OAK_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.ACACIA_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.JUNGLE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.MANGROVE_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.CHERRY_TABLE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_STOOL, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_BENCH, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_CHAIR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.BAMBOO_TABLE, 5, 20);
    }

    public static void registerAgingCopperBlocks() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.COPPER_BARS, ModBlocks.WAXED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WAXED_EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.WAXED_WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_COPPER_BARS, ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.CUT_COPPER_WALL, ModBlocks.EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.OXIDIZED_CUT_COPPER_WALL);

        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.CUT_COPPER_WALL, ModBlocks.WAXED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxableBlockPair(ModBlocks.OXIDIZED_CUT_COPPER_WALL, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL);
        
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.THATCH.block(), OtherBlockSets.WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.THATCH.slab(), OtherBlockSets.WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.THATCH.verticalSlab(), OtherBlockSets.WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.THATCH.stairs(), OtherBlockSets.WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.THATCH.wall(), OtherBlockSets.WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.WEATHERED_THATCH.block(), OtherBlockSets.AGED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.WEATHERED_THATCH.slab(), OtherBlockSets.AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.WEATHERED_THATCH.verticalSlab(), OtherBlockSets.AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.WEATHERED_THATCH.stairs(), OtherBlockSets.AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.WEATHERED_THATCH.wall(), OtherBlockSets.AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.AGED_THATCH.block(), OtherBlockSets.OLD_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.AGED_THATCH.slab(), OtherBlockSets.OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.AGED_THATCH.verticalSlab(), OtherBlockSets.OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.AGED_THATCH.stairs(), OtherBlockSets.OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.AGED_THATCH.wall(), OtherBlockSets.OLD_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.OLD_THATCH.block(), OtherBlockSets.ROTTEN_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.OLD_THATCH.slab(), OtherBlockSets.ROTTEN_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.OLD_THATCH.verticalSlab(), OtherBlockSets.ROTTEN_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.OLD_THATCH.stairs(), OtherBlockSets.ROTTEN_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(OtherBlockSets.OLD_THATCH.wall(), OtherBlockSets.ROTTEN_THATCH.wall());

        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.THATCH.block(), OtherBlockSets.WAXED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.THATCH.slab(), OtherBlockSets.WAXED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.THATCH.verticalSlab(), OtherBlockSets.WAXED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.THATCH.stairs(), OtherBlockSets.WAXED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.THATCH.wall(), OtherBlockSets.WAXED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.WEATHERED_THATCH.block(), OtherBlockSets.WAXED_WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.WEATHERED_THATCH.slab(), OtherBlockSets.WAXED_WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.WEATHERED_THATCH.verticalSlab(), OtherBlockSets.WAXED_WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.WEATHERED_THATCH.stairs(), OtherBlockSets.WAXED_WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.WEATHERED_THATCH.wall(), OtherBlockSets.WAXED_WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.AGED_THATCH.block(), OtherBlockSets.WAXED_AGED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.AGED_THATCH.slab(), OtherBlockSets.WAXED_AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.AGED_THATCH.verticalSlab(), OtherBlockSets.WAXED_AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.AGED_THATCH.stairs(), OtherBlockSets.WAXED_AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.AGED_THATCH.wall(), OtherBlockSets.WAXED_AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.OLD_THATCH.block(), OtherBlockSets.WAXED_OLD_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.OLD_THATCH.slab(), OtherBlockSets.WAXED_OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.OLD_THATCH.verticalSlab(), OtherBlockSets.WAXED_OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.OLD_THATCH.stairs(), OtherBlockSets.WAXED_OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.OLD_THATCH.wall(), OtherBlockSets.WAXED_OLD_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.ROTTEN_THATCH.block(), OtherBlockSets.WAXED_ROTTEN_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.ROTTEN_THATCH.slab(), OtherBlockSets.WAXED_ROTTEN_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.ROTTEN_THATCH.verticalSlab(), OtherBlockSets.WAXED_ROTTEN_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.ROTTEN_THATCH.stairs(), OtherBlockSets.WAXED_ROTTEN_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OtherBlockSets.ROTTEN_THATCH.wall(), OtherBlockSets.WAXED_ROTTEN_THATCH.wall());
    }

    public static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModNatureBlocks.MIRKWOOD_ROOTS, 300);
        registry.add(ModNatureBlocks.GREEN_SHRUB, 100);
        registry.add(ModNatureBlocks.SMALL_DRY_SHRUB, 100);
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

        registry.add(ModWeaponItems.GONDORIAN_BOW, 300);
        registry.add(ModWeaponItems.GONDORIAN_LONGBOW, 400);
        registry.add(ModWeaponItems.GONDORIAN_NOBLE_LONGBOW, 400);

        registry.add(ModWeaponItems.ROHIRRIC_BOW, 300);
        registry.add(ModWeaponItems.ROHIRRIC_NOBLE_BOW, 300);
        registry.add(ModWeaponItems.ROHIRRIC_LONGBOW, 400);

        registry.add(ModWeaponItems.LORIEN_BOW, 300);
        registry.add(ModWeaponItems.LORIEN_LONGBOW, 400);
        registry.add(ModWeaponItems.LORIEN_NOBLE_LONGBOW, 400);

        registry.add(ModWeaponItems.EREBOR_BOW, 300);
        registry.add(ModWeaponItems.EREBOR_NOBLE_BOW, 300);
        registry.add(ModWeaponItems.EREBOR_CROSSBOW, 400);
        registry.add(ModWeaponItems.EREBOR_NOBLE_CROSSBOW, 400);

        registry.add(ModWeaponItems.GUNDABAD_BOW, 300);
        registry.add(ModWeaponItems.GUNDABAD_CROSSBOW, 400);

        registry.add(ModWeaponItems.WOODEN_DAGGER, 150);

        registry.add(ModDecorativeBlocks.OAK_STOOL, 300);
        registry.add(ModDecorativeBlocks.OAK_BENCH, 300);
        registry.add(ModDecorativeBlocks.OAK_CHAIR, 300);
        registry.add(ModDecorativeBlocks.OAK_TABLE, 300);

        registry.add(ModDecorativeBlocks.BIRCH_STOOL, 300);
        registry.add(ModDecorativeBlocks.BIRCH_BENCH, 300);
        registry.add(ModDecorativeBlocks.BIRCH_CHAIR, 300);
        registry.add(ModDecorativeBlocks.BIRCH_TABLE, 300);

        registry.add(ModDecorativeBlocks.SPRUCE_STOOL, 300);
        registry.add(ModDecorativeBlocks.SPRUCE_BENCH, 300);
        registry.add(ModDecorativeBlocks.SPRUCE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.SPRUCE_TABLE, 300);

        registry.add(ModDecorativeBlocks.DARK_OAK_STOOL, 300);
        registry.add(ModDecorativeBlocks.DARK_OAK_BENCH, 300);
        registry.add(ModDecorativeBlocks.DARK_OAK_CHAIR, 300);
        registry.add(ModDecorativeBlocks.DARK_OAK_TABLE, 300);

        registry.add(ModDecorativeBlocks.ACACIA_STOOL, 300);
        registry.add(ModDecorativeBlocks.ACACIA_BENCH, 300);
        registry.add(ModDecorativeBlocks.ACACIA_CHAIR, 300);
        registry.add(ModDecorativeBlocks.ACACIA_TABLE, 300);

        registry.add(ModDecorativeBlocks.JUNGLE_STOOL, 300);
        registry.add(ModDecorativeBlocks.JUNGLE_BENCH, 300);
        registry.add(ModDecorativeBlocks.JUNGLE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.JUNGLE_TABLE, 300);

        registry.add(ModDecorativeBlocks.MANGROVE_STOOL, 300);
        registry.add(ModDecorativeBlocks.MANGROVE_BENCH, 300);
        registry.add(ModDecorativeBlocks.MANGROVE_CHAIR, 300);
        registry.add(ModDecorativeBlocks.MANGROVE_TABLE, 300);

        registry.add(ModDecorativeBlocks.CHERRY_STOOL, 300);
        registry.add(ModDecorativeBlocks.CHERRY_BENCH, 300);
        registry.add(ModDecorativeBlocks.CHERRY_CHAIR, 300);
        registry.add(ModDecorativeBlocks.CHERRY_TABLE, 300);

        registry.add(ModDecorativeBlocks.BAMBOO_STOOL, 300);
        registry.add(ModDecorativeBlocks.BAMBOO_BENCH, 300);
        registry.add(ModDecorativeBlocks.BAMBOO_CHAIR, 300);
        registry.add(ModDecorativeBlocks.BAMBOO_TABLE, 300);
    }

    public static void registerComposterBlocks() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(MushroomBlockSets.GRAY_MUSHROOM.stem(), 0.50f);
        registry.add(MushroomBlockSets.DARK_MUSHROOM.stem(), 0.50f);

        registry.add(ModNatureBlocks.TAN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.GREEN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.SMALL_DRY_SHRUB, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_SHRUB, 0.10f);
        registry.add(ModNatureBlocks.MORGUL_IVY, 0.40f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_BLOCK, 0.65f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS, 0.35f);
        registry.add(ModNatureBlocks.MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.FOREST_MOSS_BLOCK, 0.65f);

        registry.add(ModNatureBlocks.MALLOS, 0.65f);
        registry.add(ModNatureBlocks.ELANOR, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_FLOWER, 0.65f);
        registry.add(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, 0.65f);

        registry.add(ModNatureBlocks.LIGHT_BLUE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.MAGENTA_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.ORANGE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.PINK_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.PURPLE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.RED_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.WHITE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_FLOWERS, 0.65f);

        registry.add(ModNatureBlocks.BROWN_GRASS, 0.30f);
        registry.add(ModNatureBlocks.DYING_GRASS, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_GRASS, 0.10f);
        registry.add(ModNatureBlocks.GRIM_GRASS, 0.30f);
        registry.add(ModNatureBlocks.TEMPERATE_GRASS, 0.30f);
        registry.add(ModNatureBlocks.DRY_GRASS, 0.30f);
        registry.add(ModNatureBlocks.GRASS_TUFT, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_TUFT, 0.10f);
        registry.add(ModNatureBlocks.WHEATGRASS, 0.30f);
        registry.add(ModNatureBlocks.WILD_GRASS, 0.30f);
        registry.add(ModNatureBlocks.WILDERGRASS, 0.30f);
        registry.add(ModNatureBlocks.BEACH_GRASS, 0.30f);
        registry.add(ModNatureBlocks.COASTAL_PANIC_GRASS, 0.30f);
        registry.add(ModNatureBlocks.SEDUM, 0.30f);
        registry.add(ModNatureBlocks.YELLOW_SEDUM, 0.30f);
        registry.add(ModNatureBlocks.BRACKEN, 0.30f);
        registry.add(ModNatureBlocks.DEAD_RUSHES, 0.30f);
        registry.add(ModNatureBlocks.FALSE_OATGRASS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_CATTAILS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_BULRUSH, 0.30f);
        registry.add(ModNatureBlocks.HEATHER, 0.50f);
        registry.add(ModNatureBlocks.RED_HEATHER, 0.50f);
        registry.add(ModNatureBlocks.DEAD_HEATHER, 0.30f);
        registry.add(ModNatureBlocks.DRY_HEATHER, 0.30f);
        registry.add(ModNatureBlocks.HEATH, 0.30f);
        registry.add(ModNatureBlocks.GIANT_HOROKAKA, 0.65f);
        registry.add(ModNatureBlocks.HOROKAKA, 0.50f);

        registry.add(ModNatureBlocks.SHRIVELED_SHRUB, 0.30f);

        registry.add(ModNatureBlocks.SCORCHED_GRASS, 0.10f);
        registry.add(ModNatureBlocks.SCORCHED_TUFT, 0.10f);
        registry.add(ModNatureBlocks.SCORCHED_SHRUB, 0.10f);

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

        registry.add(ModNatureBlocks.BERRY_HOLLY_LEAVES, 0.4F);

        registry.add(ModNatureBlocks.DRY_LARCH_LEAVES, 0.2F);

        registry.add(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, 0.4F);

        registry.add(ModNatureBlocks.FALLEN_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.FALLEN_MALLORN_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, 0.3F);

        registry.add(ModNatureBlocks.DRY_PINE_LEAVES, 0.2F);
        registry.add(ModNatureBlocks.PINE_BRANCHES, 0.2F);

        registry.add(ModNatureBlocks.MAPLE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.ORANGE_MAPLE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.RED_MAPLE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.YELLOW_MAPLE_LEAVES, 0.3F);


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

    public static final CauldronBehavior CLEAN_CUSTOM_DYEABLE_ITEM = (state, world, pos, player, hand, stack) -> {
        if (!stack.isIn(ModTags.DYEABLE)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!stack.contains(ModDataComponentTypes.DYE_DATA)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!world.isClient) {
            CustomDyeableDataComponent dyeableDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

            stack.set(ModDataComponentTypes.DYE_DATA,
                     new CustomDyeableDataComponent(CustomDyeableDataComponent.DEFAULT_COLOR));
            player.incrementStat(Stats.CLEAN_ARMOR);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ItemActionResult.success(world.isClient);
    };

    public static final CauldronBehavior COOL_DOWN_METAL = (state, world, pos, player, hand, stack) -> {
        Random random = world.getRandom();
        int smokeAmount = random.nextInt(9) + 4;
        int bigSmokeAmount = random.nextInt(3) + 2;


        if (!stack.contains(ModDataComponentTypes.TEMPERATURE_DATA)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!world.isClient) {
            ItemStack originalStack = stack.copy();
            originalStack.setCount(1);
            originalStack.remove(ModDataComponentTypes.TEMPERATURE_DATA);
            stack.decrement(1);
            player.giveItemStack(originalStack);

            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else {
            for (int i = 0; i < bigSmokeAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.9f,
                        pos.getZ()+ random.nextDouble(),
                        0.0f,
                        0.03f + random.nextDouble() * 0.08,
                        0.0f);
            }
            for (int i = 0; i < smokeAmount; i++) {
                world.addParticle(ParticleTypes.SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.8f,
                        pos.getZ() + random.nextDouble(),
                        0.0f,
                        0.00f + random.nextDouble() * 0.08,
                        0.0f);
            }
        }
        return ItemActionResult.success(world.isClient);
    };

    public static void registerCauldronBehaviour() {
        SimpleDyeableItemModel.items.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, CLEAN_CUSTOM_DYEABLE_ITEM);
        });

        HotMetalsModel.items.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.ingots.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggets.forEach(item -> {
            CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(item, COOL_DOWN_METAL);
        });
    }
}
