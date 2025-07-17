package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.OxidizableVerticalSlabBlock;
import net.sevenstars.middleearth.block.special.OxidizableWallBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.minecraft.block.*;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

import java.util.ArrayList;
import java.util.List;

public class GenericBlockSets {

    public static List<GenericBlockSetBuilder> genericSetsList = new ArrayList<>();

    public static GenericBlockSetBuilder MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder MOSSY_MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "mossy_mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CRACKED_MOSSY_MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "cracked_mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "clay_tiling", 1.25F, 4.2F, MapColor.ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BLACK_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "black_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BLUE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "blue_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BROWN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "brown_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CYAN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "cyan_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder GRAY_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "gray_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder GREEN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "green_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_BLUE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "light_blue_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIGHT_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GRAY_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "light_gray_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIGHT_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIME_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "lime_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIME, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder MAGENTA_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "magenta_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_MAGENTA, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder ORANGE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "orange_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder PINK_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "pink_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_PINK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder PURPLE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "purple_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_PURPLE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder RED_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "red_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WHITE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "white_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder YELLOW_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "yellow_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    
    public static GenericBlockSetBuilder CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ModItemGroups.STONE_BLOCKS_CONTENTS));


    /*public static MiscBlockSet TREATED_WOOD = registerMiscSet("treated_wood", null, Blocks.OAK_WOOD, true);
    public static MiscBlockSet TREATED_WOOD_PLANKS = registerMiscSet("treated_wood_planks", null, Blocks.OAK_PLANKS, false);
    public static MiscBlockSet TREATED_WOOD_BEAM = registerMiscSet("treated_wood_beam", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_CARVED_BEAM = registerMiscSet("treated_wood_carved_beam", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_PANELS = registerMiscSet("treated_wood_panels", null, Blocks.OAK_PLANKS, true);
    public static MiscBlockSet TREATED_WOOD_TILING = registerMiscSet("treated_wood_tiling", null, Blocks.OAK_PLANKS, false);

    public static RoofBlockSet GRAY_MUSHROOM_SHINGLES = registerWoodSet("gray_mushroom_shingles", MushroomBlockSets.GRAY_MUSHROOM.planks());
    public static RoofBlockSet DARK_MUSHROOM_SHINGLES = registerWoodSet("dark_mushroom_shingles", MushroomBlockSets.DARK_MUSHROOM.planks());
    public static RoofBlockSet MUSHROOM_SHINGLES = registerWoodSet("mushroom_shingles", MushroomBlockSets.MUSHROOM.planks());

    public static RoofBlockSet WEATHERED_SHINGLES = registerWoodSet("weathered_shingles", null);

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

    /*public static RoofBlockSet[] sets = new RoofBlockSet[] {

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
    }*/

    private static GenericBlockSetBuilder registerBlockSet(GenericBlockSetBuilder set){
        set.blockSet = BlockSetRegistration.createRegularSet(set.setName , set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, set.group);

        genericSetsList.add(set);

        return set;
    }

    /*private static RoofBlockSet registerWoodSet(String name, Block origin) {
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
    }*/

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Generic Block Sets for " + MiddleEarth.MOD_ID);
    }
}
