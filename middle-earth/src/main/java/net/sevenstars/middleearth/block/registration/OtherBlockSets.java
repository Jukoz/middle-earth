package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.OxidizableVerticalSlabBlock;
import net.sevenstars.middleearth.block.special.OxidizableWallBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.minecraft.block.*;

public class OtherBlockSets {
    public static MiscBlockSet TREATED_WOOD = registerMiscSet("treated_wood", null, Blocks.OAK_WOOD, true);
    public static MiscBlockSet TREATED_WOOD_PLANKS = registerMiscSet("treated_wood_planks", null, Blocks.OAK_PLANKS, false);
    public static MiscBlockSet TREATED_WOOD_BEAM = registerMiscSet("treated_wood_beam", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_CARVED_BEAM = registerMiscSet("treated_wood_carved_beam", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_PANELS = registerMiscSet("treated_wood_panels", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_TILING = registerMiscSet("treated_wood_tiling", null, Blocks.OAK_PLANKS, false);

    public static RoofBlockSet GRAY_MUSHROOM_SHINGLES = registerWoodSet("gray_mushroom_shingles", MushroomBlockSets.GRAY_MUSHROOM.planks());
    public static RoofBlockSet DARK_MUSHROOM_SHINGLES = registerWoodSet("dark_mushroom_shingles", MushroomBlockSets.DARK_MUSHROOM.planks());
    public static RoofBlockSet MUSHROOM_SHINGLES = registerWoodSet("mushroom_shingles", MushroomBlockSets.MUSHROOM.planks());

    public static RoofBlockSet WEATHERED_SHINGLES = registerWoodSet("weathered_shingles", null);

    public static RoofBlockSet CLAY_TILING = registerClaySet("clay_tiling", Blocks.TERRACOTTA);

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

    public static RoofBlockSet BLUE_ROOF_TILES = registerClaySet("blue_roof_tiles", null);
    public static RoofBlockSet BRIGHT_BLUE_ROOF_TILES = registerClaySet("bright_blue_roof_tiles", null);
    public static RoofBlockSet DARK_BLUE_ROOF_TILES = registerClaySet("dark_blue_roof_tiles", null);
    public static RoofBlockSet LIGHT_BLUE_ROOF_TILES = registerClaySet("light_blue_roof_tiles", null);
    public static RoofBlockSet OFF_BLUE_ROOF_TILES = registerClaySet("off_blue_roof_tiles", null);

    public static RoofBlockSet BROWN_ROOF_TILES = registerClaySet("brown_roof_tiles", null);
    public static RoofBlockSet DARK_BROWN_ROOF_TILES = registerClaySet("dark_brown_roof_tiles", null);
    public static RoofBlockSet OFF_BROWN_ROOF_TILES = registerClaySet("off_brown_roof_tiles", null);
    
    public static RoofBlockSet CYAN_ROOF_TILES = registerClaySet("cyan_roof_tiles", null);
    public static RoofBlockSet BRIGHT_CYAN_ROOF_TILES = registerClaySet("bright_cyan_roof_tiles", null);
    public static RoofBlockSet DARK_CYAN_ROOF_TILES = registerClaySet("dark_cyan_roof_tiles", null);
    public static RoofBlockSet LIGHT_CYAN_ROOF_TILES = registerClaySet("light_cyan_roof_tiles", null);
    public static RoofBlockSet OFF_CYAN_ROOF_TILES = registerClaySet("off_cyan_roof_tiles", null);

    public static RoofBlockSet GRAY_ROOF_TILES = registerClaySet("gray_roof_tiles", null);
    public static RoofBlockSet DARK_GRAY_ROOF_TILES = registerClaySet("dark_gray_roof_tiles", null);
    public static RoofBlockSet LIGHT_GRAY_ROOF_TILES = registerClaySet("light_gray_roof_tiles", null);
    public static RoofBlockSet OFF_GRAY_ROOF_TILES = registerClaySet("off_gray_roof_tiles", null);

    public static RoofBlockSet GREEN_ROOF_TILES = registerClaySet("green_roof_tiles", null);
    public static RoofBlockSet BRIGHT_GREEN_ROOF_TILES = registerClaySet("bright_green_roof_tiles", null);
    public static RoofBlockSet DARK_GREEN_ROOF_TILES = registerClaySet("dark_green_roof_tiles", null);
    public static RoofBlockSet LIGHT_GREEN_ROOF_TILES = registerClaySet("light_green_roof_tiles", null);
    public static RoofBlockSet OFF_GREEN_ROOF_TILES = registerClaySet("off_green_roof_tiles", null);

    public static RoofBlockSet RED_ROOF_TILES = registerClaySet("red_roof_tiles", null);
    public static RoofBlockSet BRIGHT_RED_ROOF_TILES = registerClaySet("bright_red_roof_tiles", null);
    public static RoofBlockSet DARK_RED_ROOF_TILES = registerClaySet("dark_red_roof_tiles", null);
    public static RoofBlockSet LIGHT_RED_ROOF_TILES = registerClaySet("light_red_roof_tiles", null);
    public static RoofBlockSet OFF_RED_ROOF_TILES = registerClaySet("off_red_roof_tiles", null);

    public static RoofBlockSet YELLOW_ROOF_TILES = registerClaySet("yellow_roof_tiles", null);
    public static RoofBlockSet BRIGHT_YELLOW_ROOF_TILES = registerClaySet("bright_yellow_roof_tiles", null);
    public static RoofBlockSet DARK_YELLOW_ROOF_TILES = registerClaySet("dark_yellow_roof_tiles", null);
    public static RoofBlockSet LIGHT_YELLOW_ROOF_TILES = registerClaySet("light_yellow_roof_tiles", null);
    public static RoofBlockSet OFF_YELLOW_ROOF_TILES = registerClaySet("off_yellow_roof_tiles", null);

    public static RoofBlockSet THATCH = registerThatchSet("thatch", Oxidizable.OxidationLevel.UNAFFECTED);
    public static RoofBlockSet WEATHERED_THATCH = registerThatchSet("weathered_thatch", Oxidizable.OxidationLevel.EXPOSED);
    public static RoofBlockSet AGED_THATCH = registerThatchSet("aged_thatch", Oxidizable.OxidationLevel.WEATHERED);
    public static RoofBlockSet OLD_THATCH = registerThatchSet("old_thatch", Oxidizable.OxidationLevel.OXIDIZED);
    public static RoofBlockSet ROTTEN_THATCH = registerThatchSet("rotten_thatch", Oxidizable.OxidationLevel.OXIDIZED);

    public static RoofBlockSet WAXED_THATCH = registerWaxedThatchSet("waxed_thatch");
    public static RoofBlockSet WAXED_WEATHERED_THATCH = registerWaxedThatchSet("waxed_weathered_thatch");
    public static RoofBlockSet WAXED_AGED_THATCH = registerWaxedThatchSet("waxed_aged_thatch");
    public static RoofBlockSet WAXED_OLD_THATCH = registerWaxedThatchSet("waxed_old_thatch");
    public static RoofBlockSet WAXED_ROTTEN_THATCH = registerWaxedThatchSet("waxed_rotten_thatch");

    //TODO move to different registration
/*
    public static SimpleBlockSet MIXED_STONES = registerStoneSet("mixed_stones", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet MOSSY_MIXED_STONES = registerStoneSet("mossy_mixed_stones", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, MIXED_STONES.base);
    public static SimpleBlockSet CRACKED_MIXED_STONES = registerStoneSet("cracked_mixed_stones", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, MIXED_STONES.base);

    public static SimpleBlockSet PACKED_MIRE = registerStoneSet("packed_mire",STONE_HARDNESS, STONE_BLAST_RESISTANCE, ModBlocks.MIRE);
    public static SimpleBlockSet MIRE_BRICKS = registerStoneSet("mire_bricks", STONE_HARDNESS, STONE_BLAST_RESISTANCE, PACKED_MIRE.base);

    public static SimpleBlockSet PLASTER = registerStoneSet("plaster", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);

    public static SimpleBlockSet TAN_CLAY_BRICKS = registerStoneSet("tan_clay_bricks", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet TAN_CLAY_TILES = registerStoneSet("tan_clay_tiles", STONE_HARDNESS, STONE_BLAST_RESISTANCE, TAN_CLAY_BRICKS.base);

    public static SimpleBlockSet TAN_CLAY_BRICKWORK = registerStoneSet("tan_clay_brickwork", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);

    public static SimpleBlockSet WHITE_DAUB = registerStoneSet("white_daub", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);
    public static SimpleBlockSet DARK_DAUB = registerStoneSet("dark_daub", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);
    public static SimpleBlockSet YELLOW_DAUB = registerStoneSet("yellow_daub", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet STUCCO = registerStoneSet("stucco", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);
    public static SimpleBlockSet MIXED_STONES_BRICKWORK = registerStoneSet("mixed_stones_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_BRICKS = registerStoneSet("old_bricks", Blocks.BRICKS.getHardness(), Blocks.BRICKS.getBlastResistance(), null);
*/

    public static RoofBlockSet[] sets = new RoofBlockSet[] {

            THATCH,
            WEATHERED_THATCH,
            AGED_THATCH,
            OLD_THATCH,
            ROTTEN_THATCH,

            WAXED_THATCH,
            WAXED_WEATHERED_THATCH,
            WAXED_AGED_THATCH,
            WAXED_OLD_THATCH,
            WAXED_ROTTEN_THATCH,

            /*OAK_SHINGLES,
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
            BEECH_ROOFING,
            LARCH_SHINGLES,
            LARCH_ROOFING,
            BLACK_LEBETHRON_SHINGLES,
            BLACK_LEBETHRON_ROOFING,
            WHITE_LEBETHRON_SHINGLES,
            WHITE_LEBETHRON_ROOFING,
            CHESTNUT_SHINGLES,
            CHESTNUT_ROOFING,
            FIR_SHINGLES,
            FIR_ROOFING,
            HOLLY_SHINGLES,
            HOLLY_ROOFING,
            MALLORN_SHINGLES,
            MALLORN_ROOFING,
            MAPLE_SHINGLES,
            MAPLE_ROOFING,
            SILVER_MAPLE_SHINGLES,
            SILVER_MAPLE_ROOFING,
            MIRKWOOD_SHINGLES,
            MIRKWOOD_ROOFING,
            PALM_SHINGLES,
            PALM_ROOFING,
            WHITE_PALM_SHINGLES,
            WHITE_PALM_ROOFING,
            PINE_SHINGLES,
            PINE_ROOFING,
            BLACK_PINE_SHINGLES,
            BLACK_PINE_ROOFING,
            WILLOW_SHINGLES,
            WILLOW_ROOFING,

            ROTTEN_SHINGLES,
            ROTTEN_ROOFING,
            SCORCHED_SHINGLES,
            SCORCHED_ROOFING,*/
            
            DARK_MUSHROOM_SHINGLES,
            GRAY_MUSHROOM_SHINGLES,
            MUSHROOM_SHINGLES,

            WEATHERED_SHINGLES,

            CLAY_TILING,

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

            BLUE_ROOF_TILES,
            BRIGHT_BLUE_ROOF_TILES,
            DARK_BLUE_ROOF_TILES,
            LIGHT_BLUE_ROOF_TILES,
            OFF_BLUE_ROOF_TILES,

            BROWN_ROOF_TILES,
            DARK_BROWN_ROOF_TILES,
            OFF_BROWN_ROOF_TILES,

            CYAN_ROOF_TILES,
            BRIGHT_CYAN_ROOF_TILES,
            DARK_CYAN_ROOF_TILES,
            LIGHT_CYAN_ROOF_TILES,
            OFF_CYAN_ROOF_TILES,

            GRAY_ROOF_TILES,
            DARK_GRAY_ROOF_TILES,
            LIGHT_GRAY_ROOF_TILES,
            OFF_GRAY_ROOF_TILES,

            GREEN_ROOF_TILES,
            BRIGHT_GREEN_ROOF_TILES,
            DARK_GREEN_ROOF_TILES,
            LIGHT_GREEN_ROOF_TILES,
            OFF_GREEN_ROOF_TILES,

            RED_ROOF_TILES,
            BRIGHT_RED_ROOF_TILES,
            DARK_RED_ROOF_TILES,
            LIGHT_RED_ROOF_TILES,
            OFF_RED_ROOF_TILES,

            YELLOW_ROOF_TILES,
            BRIGHT_YELLOW_ROOF_TILES,
            DARK_YELLOW_ROOF_TILES,
            LIGHT_YELLOW_ROOF_TILES,
            OFF_YELLOW_ROOF_TILES,
    };
    public static MiscBlockSet[] specialWoodSets = new MiscBlockSet[] {
            TREATED_WOOD,
            TREATED_WOOD_PLANKS,
            TREATED_WOOD_BEAM,
            TREATED_WOOD_CARVED_BEAM,
            TREATED_WOOD_PANELS,
            TREATED_WOOD_TILING,
    };

    public record RoofBlockSet(Block block, Block slab, Block verticalSlab, Block stairs, Block wall, Block origin) {
    }

    public record MiscBlockSet(Block block, Block slab, Block verticalSlab, Block stairs, Block wall, Block origin) {
    }

    private static RoofBlockSet registerWoodSet(String name, Block origin) {
        Block block;

        if (origin == null) {
            block = ModBlocks.registerWoodBlock(name, Block::new,
                    AbstractBlock.Settings.copy(Blocks.OAK_PLANKS),true);
        }else {
            block = ModBlocks.registerWoodBlock(name, Block::new,
                    AbstractBlock.Settings.copy(origin),false);
        }

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", (settings) -> new StairsBlock(
                block.getDefaultState(), settings), AbstractBlock.Settings.copy(block),true);

        Block wall = ModBlocks.registerWoodBlock(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block),true);

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 20);

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(block, 300);
            builder.add(slab, 150);
            builder.add(verticalSlab, 150);
            builder.add(stairs, 300);
            builder.add(wall, 300);
        }));

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerClaySet(String name, Block origin) {
        Block block = null;

        if (origin == null) {
            block = ModBlocks.registerStoneBlock(name, Block::new,
                    AbstractBlock.Settings.copy(Blocks.TERRACOTTA).requiresTool(),true);
        }else {
            block = ModBlocks.registerStoneBlock(name, Block::new,
                    AbstractBlock.Settings.copy(origin).requiresTool(),true);
        }

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(block).requiresTool(),true);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block).requiresTool(),true);

        Block finalBlock = block;
        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", (settings) -> new StairsBlock(
                finalBlock.getDefaultState(), settings), AbstractBlock.Settings.copy(block).requiresTool(),true);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block).requiresTool(),true);


        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerThatchSet(String name, Oxidizable.OxidationLevel level) {

        Block block = ModBlocks.registerMiscBlock(name, (settings) -> new OxidizableBlock(
                level, settings), AbstractBlock.Settings.copy(Blocks.HAY_BLOCK),true);

        Block slab = ModBlocks.registerMiscBlock(name + "_slab", (settings) -> new OxidizableSlabBlock(
                level, settings), AbstractBlock.Settings.copy(block),true);

        Block verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", (settings) -> new OxidizableVerticalSlabBlock(
                level, settings), AbstractBlock.Settings.copy(block),true);

        Block stairs = ModBlocks.registerMiscBlock(name + "_stairs", (settings) -> new OxidizableStairsBlock(
                level, block.getDefaultState(), settings), AbstractBlock.Settings.copy(block),true);

        Block wall = ModBlocks.registerMiscBlock(name + "_wall", (settings) -> new OxidizableWallBlock(
                level, settings), AbstractBlock.Settings.copy(block),true);


        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);


        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    private static RoofBlockSet registerWaxedThatchSet(String name) {

        Block block = ModBlocks.registerMiscBlock(name, Block::new,
                AbstractBlock.Settings.copy(Blocks.HAY_BLOCK),true);

        Block slab = ModBlocks.registerMiscBlock(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block stairs = ModBlocks.registerMiscBlock(name + "_stairs", (settings) -> new StairsBlock(
                block.getDefaultState(), settings), AbstractBlock.Settings.copy(block),true);

        Block wall = ModBlocks.registerMiscBlock(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block),true);

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    private static MiscBlockSet registerMiscSet(String name, Block origin, Block copy, boolean isPillar) {
        Block block;
        if (isPillar){
            block = ModBlocks.registerMiscBlock(name, PillarBlock::new,
                    AbstractBlock.Settings.copy(copy),true);
        } else {
            block = ModBlocks.registerMiscBlock(name, Block::new,
                    AbstractBlock.Settings.copy(copy),true);
        }

        Block slab = ModBlocks.registerMiscBlock(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block),true);

        Block stairs = ModBlocks.registerMiscBlock(name + "_stairs", (settings) ->  new StairsBlock(
                block.getDefaultState(), settings), AbstractBlock.Settings.copy(block),true);

        Block wall = ModBlocks.registerMiscBlock(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block),false);

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);

        return new MiscBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
