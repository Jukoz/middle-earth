package net.sevenstars.middleearth.block.registration;

import net.minecraft.block.enums.NoteBlockInstrument;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.utils.StoneBlockSetCreation;
import net.sevenstars.middleearth.block.utils.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.List;

public class StoneBlockSets {
    public static final float STONE_HARDNESS = Blocks.STONE.getHardness(); //1.5f
    public static final float STONE_BLAST_RESISTANCE = Blocks.STONE.getBlastResistance(); //6.0f

    public static final float DEEPSLATE_HARDNESS = Blocks.DEEPSLATE.getHardness();
    public static final float DEEPSLATE_BLAST_RESISTANCE = Blocks.DEEPSLATE.getBlastResistance();

    public static final float NURGON_HARDNESS = 5.0F;
    public static final float NURGON_BLAST_RESISTANCE = 7.0F;

    public static final float MEDGON_HARDNESS = 7.0F;
    public static final float MEDGON_BLAST_RESISTANCE = 9.0F;

    public static List<StoneBlockSetBuilder> stoneSetsList = new ArrayList<>();

    public static StoneBlockSetBuilder STONE_SET = registerStoneSet(new StoneBlockSetBuilder("stone",
            Blocks.STONE, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLESTONE_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder GRANITE_SET = registerStoneSet(new StoneBlockSetBuilder("granite",
            Blocks.GRANITE, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder DIORITE_SET = registerStoneSet(new StoneBlockSetBuilder("diorite",
            Blocks.DIORITE,STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder ANDESITE_SET = registerStoneSet(new StoneBlockSetBuilder("andesite",
            Blocks.ANDESITE, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder CALCITE_SET = registerStoneSet(new StoneBlockSetBuilder("calcite",
            Blocks.CALCITE, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.CALCITE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder DRIPSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("dripstone",
            Blocks.DRIPSTONE_BLOCK, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.DRIPSTONE_BLOCK, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS));

    public static StoneBlockSetBuilder DEEPSLATE_SET = registerStoneSet(new StoneBlockSetBuilder("deepslate",
            Blocks.DEEPSLATE, DEEPSLATE_HARDNESS, DEEPSLATE_BLAST_RESISTANCE, MapColor.DEEPSLATE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.DEEPSLATE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("tuff",
            Blocks.TUFF, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BASALT_SET = registerStoneSet(new StoneBlockSetBuilder("basalt",
            Blocks.BASALT, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.BASALT, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder BLACKSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("blackstone",
            Blocks.BLACKSTONE, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder DOLOMITE_SET = registerStoneSet(new StoneBlockSetBuilder("dolomite",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LIGHT_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR));

    public static StoneBlockSetBuilder SLATE_SET = registerStoneSet(new StoneBlockSetBuilder("slate",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder NURGON_SET = registerStoneSet(new StoneBlockSetBuilder("nurgon",
            null, NURGON_HARDNESS, NURGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder MEDGON_SET = registerStoneSet(new StoneBlockSetBuilder("medgon",
            null, MEDGON_HARDNESS, MEDGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GNEISS_SET = registerStoneSet(new StoneBlockSetBuilder("gneiss",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder ZIGILABAN_SET = registerStoneSet(new StoneBlockSetBuilder("zigilaban",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder GALONN_SET = registerStoneSet(new StoneBlockSetBuilder("galonn",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR));

    public static StoneBlockSetBuilder LIMESTONE_SET = registerStoneSet(new StoneBlockSetBuilder("limestone",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder IZHERABAN_SET = registerStoneSet(new StoneBlockSetBuilder("izheraban",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GABBRO_SET = registerStoneSet(new StoneBlockSetBuilder("gabbro",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder ASHEN_STONE_SET = registerStoneSet(new StoneBlockSetBuilder("ashen_stone",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS));

    public static StoneBlockSetBuilder PUMICE_SET = registerStoneSet(new StoneBlockSetBuilder("pumice",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS));

    public static StoneBlockSetBuilder KHAGALABAN_SET = registerStoneSet(new StoneBlockSetBuilder("khagalaban",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BLUE_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("blue_tuff",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder SCHIST_SET = registerStoneSet(new StoneBlockSetBuilder("schist",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LAPIS_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder IRONSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("ironstone",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.DARK_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder HEMATITE_SET = registerStoneSet(new StoneBlockSetBuilder("hematite",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("green_tuff",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder GILDED_GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("gilded_green_tuff",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder JADEITE_SET = registerStoneSet(new StoneBlockSetBuilder("jadeite",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.EMERALD_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder QUARTZITE_SET = registerStoneSet(new StoneBlockSetBuilder("quartzite",
            null, STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RAW_IRON_PINK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS));

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

    private static StoneBlockSetBuilder registerStoneSet(StoneBlockSetBuilder set) {

        set.existingList.forEach((stoneBlockTypes) -> {
            switch (stoneBlockTypes){
                case BASE_BLOCKS -> set.baseBlocks = StoneBlockSetCreation.createMainStoneSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, set.source);
                case COBBLED_BLOCKS -> {
                    set.cobblestoneBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix() , set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case COBBLESTONE_BLOCKS -> {
                    set.cobblestoneBlocks = StoneBlockSetCreation.createStoneSet("cobblestone", set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = StoneBlockSetCreation.createStoneSet("mossy_cobblestone", set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                    set.brickBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossyBrickBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasCracked) set.crackedBrickBlocks = StoneBlockSetCreation.createStoneSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case TILE_BLOCKS -> {
                    set.tileBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossyTileBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasCracked) set.crackedTileBlocks = StoneBlockSetCreation.createStoneSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case SMOOTH_BLOCKS -> {
                    set.smoothBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossySmoothBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasCracked) set.crackedSmoothBlocks = StoneBlockSetCreation.createStoneSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case POLISHED_BLOCKS -> {
                    set.polishedBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasMossy) set.mossyPolishedBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                    if(set.hasCracked) set.crackedPolishedBlocks = StoneBlockSetCreation.createStoneSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                }
                case POLISHED_BLOCKS_PILLAR -> {
                    set.polishedBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true);
                    if(set.hasMossy) set.mossyPolishedBlocks = StoneBlockSetCreation.createStoneSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true);
                    if(set.hasCracked) set.crackedPolishedBlocks = StoneBlockSetCreation.createStoneSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true);
                }
                case BRICKWORK_BLOCKS -> set.brickworkBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                case OLD_BLOCKS -> set.oldBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false);
                case OLD_BLOCKS_PILLAR -> set.oldBlocks = StoneBlockSetCreation.createStoneSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true);
                case PILLAR_BLOCKS -> {
                    set.pillarBlocks = StoneBlockSetCreation.createStonePillarSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    if(set.hasMossy) set.mossyPillarBlocks = StoneBlockSetCreation.createStonePillarSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    if(set.hasCracked) set.crackedPillarBlocks = StoneBlockSetCreation.createStonePillarSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                }
                case CHISELED_BLOCKS -> {
                    set.chiseledBlocks = StoneBlockSetCreation.createStoneChiseledSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    set.chiseledBricksBlocks = StoneBlockSetCreation.createStoneChiseledSet(set.setName + "_bricks", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    set.chiseledPolishedBlocks = StoneBlockSetCreation.createStoneChiseledSet("polished_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    set.chiseledTilesBlocks = StoneBlockSetCreation.createStoneChiseledSet(set.setName + "_tiles", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                    set.chiseledSmoothBlocks = StoneBlockSetCreation.createStoneChiseledSet("smooth_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
                }
                case CARVED_WINDOW -> set.carvedWindows = StoneBlockSetCreation.createGlassSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup);
            }
        });

        stoneSetsList.add(set);

        return set;
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Stone Block Sets for " + MiddleEarth.MOD_ID);
    }
}
