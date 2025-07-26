package net.sevenstars.middleearth.block.registration;

import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLESTONE_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GRANITE_SET = registerStoneSet(new StoneBlockSetBuilder("granite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.ORANGE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.CALCITE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.DRIPSTONE_BLOCK, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS));

    public static StoneBlockSetBuilder DEEPSLATE_SET = registerStoneSet(new StoneBlockSetBuilder("deepslate",
            DEEPSLATE_HARDNESS, DEEPSLATE_BLAST_RESISTANCE, MapColor.DEEPSLATE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.DEEPSLATE, true, true)
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

    public static StoneBlockSetBuilder TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.TUFF, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.BASALT, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BLACKSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("blackstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LIGHT_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder SLATE_SET = registerStoneSet(new StoneBlockSetBuilder("slate",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder NURGON_SET = registerStoneSet(new StoneBlockSetBuilder("nurgon",
            NURGON_HARDNESS, NURGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder MEDGON_SET = registerStoneSet(new StoneBlockSetBuilder("medgon",
            MEDGON_HARDNESS, MEDGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder GALONN_SET = registerStoneSet(new StoneBlockSetBuilder("galonn",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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

    public static StoneBlockSetBuilder LIMESTONE_SET = registerStoneSet(new StoneBlockSetBuilder("limestone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BROWN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE,
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

    public static StoneBlockSetBuilder ASHENSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("ashenstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS));

    public static StoneBlockSetBuilder PUMICE_SET = registerStoneSet(new StoneBlockSetBuilder("pumice",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS));

    public static StoneBlockSetBuilder KHAGALABAN_SET = registerStoneSet(new StoneBlockSetBuilder("khagalaban",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
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
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder SCHIST_SET = registerStoneSet(new StoneBlockSetBuilder("schist",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LAPIS_BLUE, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder IRONSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("ironstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.DARK_RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder HEMATITE_SET = registerStoneSet(new StoneBlockSetBuilder("hematite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RED, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("green_tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder GILDED_GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("gilded_green_tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS));

    public static StoneBlockSetBuilder JADEITE_SET = registerStoneSet(new StoneBlockSetBuilder("jadeite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.EMERALD_GREEN, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder QUARTZITE_SET = registerStoneSet(new StoneBlockSetBuilder("quartzite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RAW_IRON_PINK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS));

    public static StoneBlockSetBuilder TAN_CLAY = registerStoneSet(new StoneBlockSetBuilder("tan_clay",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RAW_IRON_PINK, NoteBlockInstrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));


    private static StoneBlockSetBuilder registerStoneSet(StoneBlockSetBuilder set) {

        List<ItemStack> itemGroup = ModItemGroups.STONE_BLOCKS_CONTENTS;

        set.existingList.forEach((stoneBlockTypes) -> {
            switch (stoneBlockTypes){
                case BASE_BLOCKS -> set.baseBlocks = BlockSetRegistration.createMainStoneSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                case COBBLED_BLOCKS -> {
                    set.cobblestoneBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix() , set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case COBBLESTONE_BLOCKS -> {
                    set.cobblestoneBlocks = BlockSetRegistration.createRegularSet("cobblestone", set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = BlockSetRegistration.createRegularSet("mossy_cobblestone", set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                    set.brickBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossyBrickBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasCracked) set.crackedBrickBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case TILE_BLOCKS -> {
                    set.tileBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossyTileBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasCracked) set.crackedTileBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case SMOOTH_BLOCKS -> {
                    set.smoothBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossySmoothBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasCracked) set.crackedSmoothBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case POLISHED_BLOCKS -> {
                    set.polishedBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasMossy) set.mossyPolishedBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                    if(set.hasCracked) set.crackedPolishedBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                }
                case POLISHED_BLOCKS_PILLAR -> {
                    set.polishedBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup);
                    if(set.hasMossy) set.mossyPolishedBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup);
                    if(set.hasCracked) set.crackedPolishedBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup);
                }
                case BRICKWORK_BLOCKS -> set.brickworkBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                case OLD_BLOCKS -> set.oldBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                case OLD_BLOCKS_PILLAR -> set.oldBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup);
                case PILLAR_BLOCKS -> {
                    set.pillarBlocks = BlockSetRegistration.createStonePillarSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if(set.hasMossy) set.mossyPillarBlocks = BlockSetRegistration.createStonePillarSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if(set.hasCracked) set.crackedPillarBlocks = BlockSetRegistration.createStonePillarSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                }
                case CHISELED_BLOCKS -> {
                    if (set.existingList.contains(StoneBlockTypes.BASE_BLOCKS)) set.chiseledBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.BRICK_BLOCKS)) set.chiseledBricksBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_bricks", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS)) set.chiseledPolishedBlocks = BlockSetRegistration.createStoneChiseledSet("polished_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.TILE_BLOCKS)) set.chiseledTilesBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_tiles", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.SMOOTH_BLOCKS)) set.chiseledSmoothBlocks = BlockSetRegistration.createStoneChiseledSet("smooth_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                }
                case CARVED_WINDOW -> set.carvedWindows = BlockSetRegistration.createCarvedWindowSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
            }
        });

        stoneSetsList.add(set);

        return set;
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Stone Block Sets for " + MiddleEarth.MOD_ID);
    }
}
