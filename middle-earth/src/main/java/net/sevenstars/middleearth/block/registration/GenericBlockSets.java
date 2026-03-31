package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.block.*;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.OxidizableBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.tags.WoodenSlabs;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

import java.util.ArrayList;
import java.util.List;

public class GenericBlockSets {

    public static List<GenericBlockSetBuilder> genericSetsList = new ArrayList<>();

    public static GenericBlockSetBuilder WHITE_DAUB = registerBlockSet(new GenericBlockSetBuilder(
            "white_daub", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_DAUB = registerBlockSet(new GenericBlockSetBuilder(
            "dark_daub", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder YELLOW_DAUB = registerBlockSet(new GenericBlockSetBuilder(
            "yellow_daub", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder PLASTER = registerBlockSet(new GenericBlockSetBuilder(
            "plaster", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.OFF_WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder STUCCO = registerBlockSet(new GenericBlockSetBuilder(
            "stucco", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.OFF_WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder PACKED_MIRE = registerBlockSet(new GenericBlockSetBuilder(
            "packed_mire", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.PACKED_MUD, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder MIRE_BRICKS = registerBlockSet(new GenericBlockSetBuilder(
            "mire_bricks", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.PACKED_MUD, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder OLD_BRICKS = registerBlockSet(new GenericBlockSetBuilder(
            "old_bricks", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.DULL_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder MOSSY_MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "mossy_mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CRACKED_MIXED_STONES = registerBlockSet(new GenericBlockSetBuilder(
            "cracked_mixed_stones", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder MIXED_STONES_BRICKWORK = registerBlockSet(new GenericBlockSetBuilder(
            "mixed_stones_brickwork", StoneBlockSets.STONE_HARDNESS + 0.5f, StoneBlockSets.STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "clay_tiling", 1.25F, 4.2F, MapColor.ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BLACK_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "black_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BLUE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "blue_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BROWN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "brown_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CYAN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "cyan_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder GRAY_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "gray_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder GREEN_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "green_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_BLUE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "light_blue_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIGHT_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GRAY_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "light_gray_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIGHT_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIME_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "lime_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_LIME, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder MAGENTA_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "magenta_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_MAGENTA, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder ORANGE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "orange_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder PINK_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "pink_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_PINK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder PURPLE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "purple_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_PURPLE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder RED_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "red_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WHITE_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "white_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder YELLOW_CLAY_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "yellow_clay_tiling", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_BLUE_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_blue_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_BROWN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_brown_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    
    public static GenericBlockSetBuilder CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_CYAN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_cyan_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_CYAN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_GRAY_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_gray_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_GREEN_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_green_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_RED_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_red_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder BRIGHT_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "bright_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder DARK_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "dark_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder LIGHT_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "light_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder OFF_YELLOW_ROOF_TILES = registerBlockSet(new GenericBlockSetBuilder(
            "off_yellow_roof_tiles", 1.25F, 4.2F, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true,
            ItemGroupsME.STONE_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder WEATHERED_SHINGLES = registerBlockSet(new GenericBlockSetBuilder(
            "weathered_shingles", 2.0f, 3.0f, MapColor.WHITE_GRAY, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder TREATED_WOOD = registerPillarBlockSet(new GenericBlockSetBuilder(
            "treated_wood", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder TREATED_WOOD_PLANKS = registerBlockSet(new GenericBlockSetBuilder(
            "treated_wood_planks", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder TREATED_WOOD_BEAM = registerPillarBlockSet(new GenericBlockSetBuilder(
            "treated_wood_beam", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder TREATED_WOOD_CARVED_BEAM = registerPillarBlockSet(new GenericBlockSetBuilder(
            "treated_wood_carved_beam", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder TREATED_WOOD_PANELS = registerBlockSet(new GenericBlockSetBuilder(
            "treated_wood_panels", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder TREATED_WOOD_TILING = registerBlockSet(new GenericBlockSetBuilder(
            "treated_wood_tiling", 2.0f, 3.0f, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder AGED_WOOD = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_BOARDS = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_boards", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_CARVING = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_carving", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_PLANKS = registerBlockSet(new GenericBlockSetBuilder(
            "aged_wood_planks", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_BEAM = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_beam", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_FISH_CARVING = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_fish_carving", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_PANELS = registerBlockSet(new GenericBlockSetBuilder(
            "aged_wood_panels", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_SHINGLES = registerBlockSet(new GenericBlockSetBuilder(
            "aged_wood_shingles", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder AGED_WOOD_GILDED_CARVED_PILLAR = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_gilded_carved_pillar", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_GILDED_CARVING = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_gilded_carving", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_GILDED_HORSES = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_gilded_horses", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder AGED_WOOD_GILDED_TRIM = registerPillarBlockSet(new GenericBlockSetBuilder(
            "aged_wood_gilded_trim", 2.0f, 3.0f, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, false,
            ItemGroupsME.WOOD_BLOCKS_CONTENTS));

    public static OxidizableBlockSetBuilder THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.UNAFFECTED));
    public static OxidizableBlockSetBuilder WEATHERED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "weathered_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.EXPOSED));
    public static OxidizableBlockSetBuilder AGED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "aged_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.WEATHERED));
    public static OxidizableBlockSetBuilder OLD_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "old_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.OXIDIZED));
    public static OxidizableBlockSetBuilder ROTTEN_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "rotten_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.OXIDIZED));

    public static GenericBlockSetBuilder WAXED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_WEATHERED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_weathered_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_AGED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_aged_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_OLD_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_old_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_ROTTEN_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_rotten_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));

    public static OxidizableBlockSetBuilder REED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.UNAFFECTED));
    public static OxidizableBlockSetBuilder WEATHERED_REED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "weathered_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.EXPOSED));
    public static OxidizableBlockSetBuilder AGED_REED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "aged_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.WEATHERED));
    public static OxidizableBlockSetBuilder OLD_REED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "old_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.OXIDIZED));
    public static OxidizableBlockSetBuilder ROTTEN_REED_THATCH = registerOxidizableBlockSet(new OxidizableBlockSetBuilder(
            "rotten_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS, Oxidizable.OxidationLevel.OXIDIZED));

    public static GenericBlockSetBuilder WAXED_REED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_WEATHERED_REED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_weathered_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_AGED_REED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_aged_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_OLD_REED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_old_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder WAXED_ROTTEN_REED_THATCH = registerBlockSet(new GenericBlockSetBuilder(
            "waxed_rotten_reed_thatch", 0.5f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder REED = registerPillarBlockSet(new GenericBlockSetBuilder(
            "reed_block", 0.6f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder STRAW = registerPillarBlockSet(new GenericBlockSetBuilder(
            "straw_block", 0.6f, 0.0f, MapColor.PALE_YELLOW, NoteBlockInstrument.BANJO, BlockSoundGroup.GRASS, false,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));

    public static GenericBlockSetBuilder CUT_BRONZE = registerBlockSet(new GenericBlockSetBuilder(
            "cut_bronze", 3.0F, 6.0F, MapColor.ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.COPPER, true,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CUT_CRUDE_PLATES = registerBlockSet(new GenericBlockSetBuilder(
            "cut_crude_plates", 3.0F, 6.0F, MapColor.DIRT_BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.COPPER, true,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CUT_LEAD = registerBlockSet(new GenericBlockSetBuilder(
            "cut_lead", 3.0F, 6.0F, MapColor.GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.COPPER, true,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    public static GenericBlockSetBuilder CUT_SILVER = registerBlockSet(new GenericBlockSetBuilder(
            "cut_silver", 3.0F, 6.0F, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.COPPER, true,
            ItemGroupsME.MISC_BLOCKS_CONTENTS));
    
    private static GenericBlockSetBuilder registerBlockSet(GenericBlockSetBuilder set){
        set.blockSet = BlockSetRegistration.createRegularSet(set.setName , set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, set.group, set.requiresTool);

        genericSetsList.add(set);

        if(set.setName.contains("thatch")) {
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.base(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.slab(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.verticalSlab(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.stairs(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.wall(), 5, 20);
        }

        return set;
    }

    private static GenericBlockSetBuilder registerPillarBlockSet(GenericBlockSetBuilder set){
        set.blockSet = BlockSetRegistration.createRegularSet(set.setName , set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, set.group, set.requiresTool);

        genericSetsList.add(set);

        if(set.setName.contains("wood")){
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.base(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.slab(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.verticalSlab(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.stairs(), 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.wall(), 5, 20);

            FuelRegistryEvents.BUILD.register(((builder, context) -> {
                builder.add(set.blockSet.base(), 300);
                builder.add(set.blockSet.slab(), 150);
                builder.add(set.blockSet.verticalSlab(), 150);
                builder.add(set.blockSet.stairs(), 300);
                builder.add(set.blockSet.wall(), 300);
            }));
        }

        return set;
    }

    private static OxidizableBlockSetBuilder registerOxidizableBlockSet(OxidizableBlockSetBuilder set){
        set.blockSet = BlockSetRegistration.createOxidizableSet(set.setName , set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, set.group, set.requiresTool, set.level);

        genericSetsList.add(set);

        FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.base(), 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.slab(), 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.verticalSlab(), 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.stairs(), 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(set.blockSet.wall(), 5, 20);

        return set;
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Generic Block Sets for " + MiddleEarth.MOD_ID);
    }
}
