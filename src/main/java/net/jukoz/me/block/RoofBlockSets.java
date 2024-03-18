package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.OxidizableVerticalSlabBlock;
import net.jukoz.me.block.special.OxidizableWallBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
import net.minecraft.block.*;

public class RoofBlockSets {

    public static RoofBlockSet OAK_SHINGLES = registerWoodSet("oak_shingles", Blocks.OAK_PLANKS);
    public static RoofBlockSet SPRUCE_SHINGLES = registerWoodSet("spruce_shingles", Blocks.SPRUCE_PLANKS);
    public static RoofBlockSet BIRCH_SHINGLES = registerWoodSet("birch_shingles", Blocks.BIRCH_PLANKS);
    public static RoofBlockSet JUNGLE_SHINGLES = registerWoodSet("jungle_shingles", Blocks.JUNGLE_PLANKS);
    public static RoofBlockSet ACACIA_SHINGLES = registerWoodSet("acacia_shingles", Blocks.ACACIA_PLANKS);
    public static RoofBlockSet DARK_OAK_SHINGLES = registerWoodSet("dark_oak_shingles", Blocks.ACACIA_PLANKS);
    public static RoofBlockSet MANGROVE_SHINGLES = registerWoodSet("mangrove_shingles", Blocks.MANGROVE_PLANKS);
    public static RoofBlockSet CHERRY_SHINGLES = registerWoodSet("cherry_shingles", Blocks.CHERRY_PLANKS);
    public static RoofBlockSet BAMBOO_SHINGLES = registerWoodSet("bamboo_shingles", Blocks.BAMBOO_PLANKS);
    public static RoofBlockSet CRIMSON_SHINGLES = registerWoodSet("crimson_shingles", Blocks.CRIMSON_PLANKS);
    public static RoofBlockSet WARPED_SHINGLES = registerWoodSet("warped_shingles", Blocks.WARPED_PLANKS);

    public static RoofBlockSet BEECH_SHINGLES = registerWoodSet("beech_shingles", WoodBlockSets.BEECH.planks());
    public static RoofBlockSet LARCH_SHINGLES = registerWoodSet("larch_shingles", WoodBlockSets.LARCH.planks());
    public static RoofBlockSet BLACK_LEBETHRON_SHINGLES = registerWoodSet("black_lebethron_shingles", WoodBlockSets.BLACK_LEBETHRON.planks());
    public static RoofBlockSet WHITE_LEBETHRON_SHINGLES = registerWoodSet("white_lebethron_shingles", WoodBlockSets.WHITE_LEBETHRON.planks());
    public static RoofBlockSet MALLORN_SHINGLES = registerWoodSet("mallorn_shingles", WoodBlockSets.MALLORN.planks());
    public static RoofBlockSet MAPLE_SHINGLES = registerWoodSet("maple_shingles", WoodBlockSets.MAPLE.planks());
    public static RoofBlockSet MIRKWOOD_SHINGLES = registerWoodSet("mirkwood_shingles", WoodBlockSets.MIRKWOOD.planks());
    public static RoofBlockSet PALM_SHINGLES = registerWoodSet("palm_shingles", WoodBlockSets.PALM.planks());
    public static RoofBlockSet PINE_SHINGLES = registerWoodSet("pine_shingles", WoodBlockSets.PINE.planks());
    public static RoofBlockSet WILLOW_SHINGLES = registerWoodSet("willow_shingles", WoodBlockSets.WILLOW.planks());
    public static RoofBlockSet GRAY_MUSHROOM_SHINGLES = registerWoodSet("gray_mushroom_shingles", MushroomBlockSets.GRAY_MUSHROOM.planks());
    public static RoofBlockSet DARK_MUSHROOM_SHINGLES = registerWoodSet("dark_mushroom_shingles", MushroomBlockSets.DARK_MUSHROOM.planks());
    public static RoofBlockSet MUSHROOM_SHINGLES = registerWoodSet("mushroom_shingles", MushroomBlockSets.MUSHROOM.planks());

    public static RoofBlockSet CHARRED_SHINGLES = registerWoodSet("charred_shingles", null);
    public static RoofBlockSet WEATHERED_SHINGLES = registerWoodSet("weathered_shingles", null);

    public static RoofBlockSet BRICK_CLAY_TILING = registerClaySet("brick_clay_tiling", Blocks.BRICKS);

    public static RoofBlockSet BLACK_CLAY_TILING = registerClaySet("black_clay_tiling", Blocks.BLACK_TERRACOTTA);
    public static RoofBlockSet BLUE_CLAY_TILING = registerClaySet("blue_clay_tiling", Blocks.BLUE_TERRACOTTA);
    public static RoofBlockSet BROWN_CLAY_TILING = registerClaySet("brown_clay_tiling", Blocks.BROWN_TERRACOTTA);
    public static RoofBlockSet CYAN_CLAY_TILING = registerClaySet("cyan_clay_tiling", Blocks.CYAN_TERRACOTTA);
    public static RoofBlockSet GRAY_CLAY_TILING = registerClaySet("gray_clay_tiling", Blocks.GRAY_TERRACOTTA);
    public static RoofBlockSet GREEN_CLAY_TILING = registerClaySet("green_clay_tiling", Blocks.GREEN_TERRACOTTA);
    public static RoofBlockSet LIGHT_BLUE_CLAY_TILING = registerClaySet("light_blue_clay_tiling", Blocks.LIGHT_BLUE_TERRACOTTA);
    public static RoofBlockSet LIGHT_GRAY_CLAY_TILING = registerClaySet("light_gray_clay_tiling", Blocks.LIGHT_GRAY_TERRACOTTA);
    public static RoofBlockSet LIME_TILING = registerClaySet("lime_clay_tiling", Blocks.LIME_TERRACOTTA);
    public static RoofBlockSet MAGENTA_CLAY_TILING = registerClaySet("magenta_clay_tiling", Blocks.MAGENTA_TERRACOTTA);
    public static RoofBlockSet ORANGE_CLAY_TILING = registerClaySet("orange_clay_tiling", Blocks.ORANGE_TERRACOTTA);
    public static RoofBlockSet PINK_CLAY_TILING = registerClaySet("pink_clay_tiling", Blocks.PINK_TERRACOTTA);
    public static RoofBlockSet PURPLE_CLAY_TILING = registerClaySet("purple_clay_tiling", Blocks.PURPLE_TERRACOTTA);
    public static RoofBlockSet RED_CLAY_TILING = registerClaySet("red_clay_tiling", Blocks.RED_TERRACOTTA);
    public static RoofBlockSet WHITE_CLAY_TILING = registerClaySet("white_clay_tiling", Blocks.WHITE_TERRACOTTA);
    public static RoofBlockSet YELLOW_CLAY_TILING = registerClaySet("yellow_clay_tiling", Blocks.YELLOW_TERRACOTTA);

    public static RoofBlockSet THATCH = registerThatchSet("thatch", Oxidizable.OxidationLevel.UNAFFECTED);
    public static RoofBlockSet WEATHERED_THATCH = registerThatchSet("weathered_thatch", Oxidizable.OxidationLevel.EXPOSED);
    public static RoofBlockSet AGED_THATCH = registerThatchSet("aged_thatch", Oxidizable.OxidationLevel.WEATHERED);
    public static RoofBlockSet OLD_THATCH = registerThatchSet("old_thatch", Oxidizable.OxidationLevel.OXIDIZED);

    public static RoofBlockSet WAXED_THATCH = registerWaxedThatchSet("waxed_thatch");
    public static RoofBlockSet WAXED_WEATHERED_THATCH = registerWaxedThatchSet("waxed_weathered_thatch");
    public static RoofBlockSet WAXED_AGED_THATCH = registerWaxedThatchSet("waxed_aged_thatch");
    public static RoofBlockSet WAXED_OLD_THATCH = registerWaxedThatchSet("waxed_old_thatch");

    public static RoofBlockSet[] sets = new RoofBlockSet[] {
            THATCH,
            WEATHERED_THATCH,
            AGED_THATCH,
            OLD_THATCH,

            WAXED_THATCH,
            WAXED_WEATHERED_THATCH,
            WAXED_AGED_THATCH,
            WAXED_OLD_THATCH,

            OAK_SHINGLES,
            SPRUCE_SHINGLES,
            BIRCH_SHINGLES,
            JUNGLE_SHINGLES,
            ACACIA_SHINGLES,
            DARK_OAK_SHINGLES,
            MANGROVE_SHINGLES,
            CHERRY_SHINGLES,
            BAMBOO_SHINGLES,
            CRIMSON_SHINGLES,
            WARPED_SHINGLES,

            BEECH_SHINGLES,
            LARCH_SHINGLES,
            BLACK_LEBETHRON_SHINGLES,
            WHITE_LEBETHRON_SHINGLES,
            MALLORN_SHINGLES,
            MAPLE_SHINGLES,
            MIRKWOOD_SHINGLES,
            PALM_SHINGLES,
            PINE_SHINGLES,
            WILLOW_SHINGLES,
            DARK_MUSHROOM_SHINGLES,
            GRAY_MUSHROOM_SHINGLES,
            MUSHROOM_SHINGLES,

            CHARRED_SHINGLES,
            WEATHERED_SHINGLES,

            BRICK_CLAY_TILING,

            BLACK_CLAY_TILING,
            BLUE_CLAY_TILING,
            BROWN_CLAY_TILING,
            CYAN_CLAY_TILING,
            GRAY_CLAY_TILING,
            GREEN_CLAY_TILING,
            LIGHT_BLUE_CLAY_TILING,
            LIGHT_GRAY_CLAY_TILING,
            LIME_TILING,
            MAGENTA_CLAY_TILING,
            ORANGE_CLAY_TILING,
            PINK_CLAY_TILING,
            PURPLE_CLAY_TILING,
            RED_CLAY_TILING,
            WHITE_CLAY_TILING,
            YELLOW_CLAY_TILING,
    };

    public record RoofBlockSet(Block block, Block slab, Block verticalSlab, Block stairs, Block wall, Block origin) {
    }

    private static RoofBlockSet registerWoodSet(String name, Block origin) {
        Block block = null;

        if (origin == null) {
            block = ModBlocks.registerWoodBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)),true);
        }else {
            block = ModBlocks.registerWoodBlock(name, new Block(AbstractBlock.Settings.copy(origin)),false);
        }

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)),true);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block)),true);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                FabricBlockSettings.copyOf(block)),true);

        Block wall = ModBlocks.registerWoodBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block)),true);

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 20);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerClaySet(String name, Block origin) {
        Block block = null;

        if (origin == null) {
            block = ModBlocks.registerStoneBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.TERRACOTTA).requiresTool()),true);
        }else {
            block = ModBlocks.registerStoneBlock(name, new Block(AbstractBlock.Settings.copy(origin).requiresTool()),true);
        }

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block).requiresTool()),true);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block).requiresTool()),true);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                FabricBlockSettings.copyOf(block).requiresTool()),true);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block).requiresTool()),true);


        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerThatchSet(String name, Oxidizable.OxidationLevel level) {

        Block block = ModBlocks.registerMiscBlock(name, new OxidizableBlock(level, AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),true);

        Block slab = ModBlocks.registerMiscBlock(name + "_slab", new OxidizableSlabBlock(level, FabricBlockSettings.copyOf(block)),true);

        Block verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", new OxidizableVerticalSlabBlock(level, AbstractBlock.Settings.copy(block)),true);

        Block stairs = ModBlocks.registerMiscBlock(name + "_stairs", new OxidizableStairsBlock(level, block.getDefaultState(),
                FabricBlockSettings.copyOf(block)),true);

        Block wall = ModBlocks.registerMiscBlock(name + "_wall", new OxidizableWallBlock(level, AbstractBlock.Settings.copy(block)),true);


        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);


        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    private static RoofBlockSet registerWaxedThatchSet(String name) {

        Block block = ModBlocks.registerMiscBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),true);

        Block slab = ModBlocks.registerMiscBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)),true);

        Block verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block)),true);

        Block stairs = ModBlocks.registerMiscBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                FabricBlockSettings.copyOf(block)),true);

        Block wall = ModBlocks.registerMiscBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block)),true);

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    public static void registerAgingThatchBlocks(){
        OxidizableBlocksRegistry.registerOxidizableBlockPair(THATCH.block(), WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(THATCH.slab(), WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(THATCH.verticalSlab(), WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(THATCH.stairs(), WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(THATCH.wall(), WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_THATCH.block(), AGED_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_THATCH.slab(), AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_THATCH.verticalSlab(), AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_THATCH.stairs(), AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_THATCH.wall(), AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(AGED_THATCH.block(), OLD_THATCH.block());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(AGED_THATCH.slab(), OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(AGED_THATCH.verticalSlab(), OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(AGED_THATCH.stairs(), OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerOxidizableBlockPair(AGED_THATCH.wall(), OLD_THATCH.wall());

        OxidizableBlocksRegistry.registerWaxableBlockPair(THATCH.block(), WAXED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(THATCH.slab(), WAXED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(THATCH.verticalSlab(), WAXED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(THATCH.stairs(), WAXED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(THATCH.wall(), WAXED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_THATCH.block(), WAXED_WEATHERED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_THATCH.slab(), WAXED_WEATHERED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_THATCH.verticalSlab(), WAXED_WEATHERED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_THATCH.stairs(), WAXED_WEATHERED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_THATCH.wall(), WAXED_WEATHERED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(AGED_THATCH.block(), WAXED_AGED_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(AGED_THATCH.slab(), WAXED_AGED_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(AGED_THATCH.verticalSlab(), WAXED_AGED_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(AGED_THATCH.stairs(), WAXED_AGED_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(AGED_THATCH.wall(), WAXED_AGED_THATCH.wall());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OLD_THATCH.block(), WAXED_OLD_THATCH.block());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OLD_THATCH.slab(), WAXED_OLD_THATCH.slab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OLD_THATCH.verticalSlab(), WAXED_OLD_THATCH.verticalSlab());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OLD_THATCH.stairs(), WAXED_OLD_THATCH.stairs());
        OxidizableBlocksRegistry.registerWaxableBlockPair(OLD_THATCH.wall(), WAXED_OLD_THATCH.wall());
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
