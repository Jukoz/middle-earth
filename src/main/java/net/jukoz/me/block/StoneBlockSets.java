package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.*;

public class StoneBlockSets {
    public static final float STONE_HARDNESS = Blocks.STONE.getHardness();
    public static final float STONE_BLAST_RESISTANCE = Blocks.STONE.getBlastResistance();
    public static final float COBBLE_HARDNESS = Blocks.COBBLESTONE.getHardness();
    public static final float COBBLE_BLAST_RESISTANCE = Blocks.COBBLESTONE.getBlastResistance();
    public static final float BRICKS_HARDNESS = Blocks.STONE_BRICKS.getHardness();
    public static final float BRICKS_BLASTRESISTANCE = Blocks.STONE_BRICKS.getBlastResistance();

    public static final float DEEPSLATE_HARDNESS = Blocks.DEEPSLATE.getHardness();
    public static final float DEEPSLATE_BLAST_RESISTANCE = Blocks.DEEPSLATE.getBlastResistance();
    public static final float DEEPSLATE_BRICKS_HARDNESS = Blocks.COBBLED_DEEPSLATE.getHardness();
    public static final float DEEPSLATE_BRICKS_BLAST_RESISTANCE = Blocks.COBBLED_DEEPSLATE.getBlastResistance();

    public static final float DIFTOMIN_HARDNESS = 5.0F;
    public static final float DIFTOMIN_BLAST_RESISTANCE = 7.0F;
    public static final float DIFTOMIN_BRICKS_HARDNESS = 4.5F;
    public static final float DIFTOMIN_BRICKS_BLAST_RESISTANCE = 7.0F;

    public static final float EPMOSTO_HARDNESS = 7.0F;
    public static final float EPMOSTO_BLAST_RESISTANCE = 9.0F;
    public static final float EPMOSTO_BRICKS_HARDNESS = 6.5F;
    public static final float EPMOSTO_BRICKS_BLAST_RESISTANCE = 9.0F;

    public static SimpleBlockSetMain ASHEN_STONE = registerMainStoneSet("ashen_stone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet ASHEN_COBBLESTONE = registerStoneSet("ashen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, ASHEN_STONE.base);
    public static SimpleBlockSet ASHEN_BRICKS = registerStoneSet("ashen_bricks", ASHEN_COBBLESTONE.base.getHardness(), ASHEN_COBBLESTONE.base.getBlastResistance(), ASHEN_STONE.base);
    public static SimpleBlockSet ASHEN_TILES = registerStoneSet("ashen_tiles", ASHEN_BRICKS.base.getHardness(), ASHEN_BRICKS.base.getBlastResistance(), ASHEN_BRICKS.base);

    public static SimpleBlockSetMain GONLUIN = registerMainStoneSet("gonluin", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_GONLUIN = registerStoneSet("cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GONLUIN.base);
    public static SimpleBlockSet MOSSY_COBBLED_GONLUIN = registerStoneSet("mossy_cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GONLUIN.base);
    public static SimpleBlockSet POLISHED_GONLUIN = registerStoneSet("polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), GONLUIN.base);
    //public static SimpleBlockSet MOSSY_POLISHED_GONLUIN = registerStoneSet("mossy_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    //public static SimpleBlockSet CRACKED_POLISHED_GONLUIN = registerStoneSet("cracked_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet GONLUIN_BRICKS = registerStoneSet("gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    //public static SimpleBlockSet MOSSY_GONLUIN_BRICKS = registerStoneSet("mossy_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet CRACKED_GONLUIN_BRICKS = registerStoneSet("cracked_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet GONLUIN_TILES = registerStoneSet("gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    //public static SimpleBlockSet MOSSY_GONLUIN_TILES = registerStoneSet("mossy_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);
    //public static SimpleBlockSet CRACKED_GONLUIN_TILES = registerStoneSet("cracked_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);

    public static SimpleBlockSet COBBLED_CALCITE = registerStoneSet("cobbled_calcite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.CALCITE);
    public static SimpleBlockSet MOSSY_COBBLED_CALCITE = registerStoneSet("mossy_cobbled_calcite", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), COBBLED_CALCITE.base);
    public static SimpleBlockSet POLISHED_CALCITE = registerStoneSet("polished_calcite", Blocks.CALCITE.getHardness(), Blocks.CALCITE.getBlastResistance(), Blocks.CALCITE);
    public static SimpleBlockSet MOSSY_POLISHED_CALCITE = registerStoneSet("mossy_polished_calcite", POLISHED_CALCITE.base.getHardness(), POLISHED_CALCITE.base.getBlastResistance(), POLISHED_CALCITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_CALCITE = registerStoneSet("cracked_polished_calcite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, POLISHED_CALCITE.base);
    public static SimpleBlockSet CALCITE_BRICKS = registerStoneSet("calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), POLISHED_CALCITE.base);
    public static SimpleBlockSet MOSSY_CALCITE_BRICKS = registerStoneSet("mossy_calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_CALCITE_BRICKS = registerStoneSet("cracked_calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet CALCITE_TILES = registerStoneSet("calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_CALCITE_TILES = registerStoneSet("mossy_calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_TILES.base);
    public static SimpleBlockSet CRACKED_CALCITE_TILES = registerStoneSet("cracked_calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_TILES.base);

    public static SimpleBlockSetMain DOLOMITE = registerMainStoneSet("dolomite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    //public static SimpleBlockSet COBBLED_DOLOMITE = registerStoneSet("cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE.base);
    //public static SimpleBlockSet MOSSY_COBBLED_DOLOMITE = registerStoneSet("mossy_cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE.base);
    public static SimpleBlockSet POLISHED_DOLOMITE = registerStoneSet("polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), DOLOMITE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_DOLOMITE = registerStoneSet("mossy_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_DOLOMITE = registerStoneSet("cracked_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet DOLOMITE_BRICKS = registerStoneSet("dolomite_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_DOLOMITE.base);
    //public static SimpleBlockSet MOSSY_DOLOMITE_BRICKS = registerStoneSet("mossy_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    //public static SimpleBlockSet CRACKED_DOLOMITE_BRICKS = registerStoneSet("cracked_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet DOLOMITE_TILES = registerStoneSet("dolomite_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE_BRICKS.base);
    //public static SimpleBlockSet MOSSY_DOLOMITE_TILES = registerStoneSet("mossy_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);
    //public static SimpleBlockSet CRACKED_DOLOMITE_TILES = registerStoneSet("cracked_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);

    public static SimpleBlockSetMain FROZEN_STONE = registerMainStoneSet("frozen_stone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet FROZEN_COBBLESTONE = registerStoneSet("frozen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, FROZEN_STONE.base);
    //public static SimpleBlockSet MOSSY_FROZEN_COBBLESTONE = registerStoneSet("mossy_frozen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, FROZEN_STONE.base);
    public static SimpleBlockSet POLISHED_FROZEN_STONE = registerStoneSet("polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), FROZEN_STONE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_FROZEN_STONE = registerStoneSet("mossy_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_FROZEN_STONE = registerStoneSet("cracked_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    public static SimpleBlockSet FROZEN_BRICKS = registerStoneSet("frozen_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    //public static SimpleBlockSet MOSSY_FROZEN_STONE_BRICKS = registerStoneSet("mossy_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    //public static SimpleBlockSet CRACKED_FROZEN_STONE_BRICKS = registerStoneSet("cracked_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    public static SimpleBlockSet FROZEN_TILES = registerStoneSet("frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    //public static SimpleBlockSet MOSSY_FROZEN_TILES = registerStoneSet("mossy_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);
    //public static SimpleBlockSet CRACKED_FROZEN_TILES = registerStoneSet("cracked_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);

    public static SimpleBlockSetMain LIMESTONE = registerMainStoneSet("limestone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_LIMESTONE = registerStoneSet("cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, LIMESTONE.base);
    public static SimpleBlockSet MOSSY_COBBLED_LIMESTONE = registerStoneSet("mossy_cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, LIMESTONE.base);
    public static SimpleBlockSet POLISHED_LIMESTONE = registerStoneSet("polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), LIMESTONE.base);
    public static SimpleBlockSet MOSSY_POLISHED_LIMESTONE = registerStoneSet("mossy_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet CRACKED_POLISHED_LIMESTONE = registerStoneSet("cracked_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet LIMESTONE_BRICKS = registerStoneSet("limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_BRICKS = registerStoneSet("mossy_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_BRICKS = registerStoneSet("cracked_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet LIMESTONE_TILES = registerStoneSet("limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_TILES = registerStoneSet("mossy_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_TILES = registerStoneSet("cracked_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);

    public static SimpleBlockSet STONE_TILES = registerStoneSet("stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.STONE);
    public static SimpleBlockSet MOSSY_STONE_TILES = registerStoneSet("mossy_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet CRACKED_STONE_TILES = registerStoneSet("cracked_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet MOSSY_SMOOTH_STONE = registerStoneSet("mossy_smooth_stone", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.SMOOTH_STONE);
    public static SimpleBlockSet CRACKED_SMOOTH_STONE = registerStoneSet("cracked_smooth_stone", Blocks.SMOOTH_STONE.getHardness(), Blocks.SMOOTH_STONE.getBlastResistance(), Blocks.SMOOTH_STONE);

    public static SimpleBlockSet MOSSY_DEEPSLATE_BRICKS = registerStoneSet("mossy_deepslate_bricks", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_BRICKS);
    public static SimpleBlockSet MOSSY_POLISHED_DEEPSLATE = registerStoneSet("mossy_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet CRACKED_POLISHED_DEEPSLATE = registerStoneSet("cracked_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet MOSSY_DEEPSLATE_TILES = registerStoneSet("mossy_deepslate_tiles", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_TILES);

    public static SimpleBlockSetMain BLUE_TUFF = registerMainStoneSet("blue_tuff", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_BLUE_TUFF = registerStoneSet("cobbled_blue_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, BLUE_TUFF.base);
    public static SimpleBlockSet MOSSY_COBBLED_BLUE_TUFF = registerStoneSet("mossy_cobbled_blue_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, BLUE_TUFF.base);
    public static SimpleBlockSet POLISHED_BLUE_TUFF = registerStoneSet("polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF.base);
    //public static SimpleBlockSet MOSSY_POLISHED_BLUE_TUFF = registerStoneSet("mossy_polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    //public static SimpleBlockSet CRACKED_POLISHED_BLUE_TUFF = registerStoneSet("cracked_polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    public static SimpleBlockSet BLUE_TUFF_BRICKS = registerStoneSet("blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    public static SimpleBlockSet MOSSY_BLUE_TUFF_BRICKS = registerStoneSet("mossy_blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    public static SimpleBlockSet CRACKED_BLUE_TUFF_BRICKS = registerStoneSet("cracked_blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    public static SimpleBlockSet BLUE_TUFF_TILES = registerStoneSet("blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    //public static SimpleBlockSet MOSSY_BLUE_TUFF_TILES = registerStoneSet("mossy_blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_TILES.base);
    public static SimpleBlockSet CRACKED_BLUE_TUFF_TILES = registerStoneSet("cracked_blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_TILES.base);

    //public static SimpleBlockSet COBBLED_ANDESITE = registerStoneSet("cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.ANDESITE);
    //public static SimpleBlockSet MOSSY_COBBLED_ANDESITE = registerStoneSet("mossy_cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.ANDESITE);
    public static SimpleBlockSet MOSSY_POLISHED_ANDESITE = registerStoneSet("mossy_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet CRACKED_POLISHED_ANDESITE = registerStoneSet("cracked_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet ANDESITE_BRICKS = registerStoneSet("andesite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet MOSSY_ANDESITE_BRICKS = registerStoneSet("mossy_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_ANDESITE_BRICKS = registerStoneSet("cracked_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet ANDESITE_TILES = registerStoneSet("andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_ANDESITE_TILES = registerStoneSet("mossy_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);
    public static SimpleBlockSet CRACKED_ANDESITE_TILES = registerStoneSet("cracked_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);

    //public static SimpleBlockSet COBBLED_GRANITE = registerStoneSet("cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.GRANITE);
    //public static SimpleBlockSet MOSSY_COBBLED_GRANITE = registerStoneSet("mossy_cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.GRANITE);
    public static SimpleBlockSet MOSSY_POLISHED_GRANITE = registerStoneSet("mossy_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet CRACKED_POLISHED_GRANITE = registerStoneSet("cracked_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet GRANITE_BRICKS = registerStoneSet("granite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet MOSSY_GRANITE_BRICKS = registerStoneSet("mossy_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_GRANITE_BRICKS = registerStoneSet("cracked_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet GRANITE_TILES = registerStoneSet("granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_GRANITE_TILES = registerStoneSet("mossy_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);
    public static SimpleBlockSet CRACKED_GRANITE_TILES = registerStoneSet("cracked_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);

    public static SimpleBlockSet COBBLED_DIORITE = registerStoneSet("cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_COBBLED_DIORITE = registerStoneSet("mossy_cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_POLISHED_DIORITE = registerStoneSet("mossy_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet CRACKED_POLISHED_DIORITE = registerStoneSet("cracked_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet DIORITE_BRICKS = registerStoneSet("diorite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet MOSSY_DIORITE_BRICKS = registerStoneSet("mossy_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_DIORITE_BRICKS = registerStoneSet("cracked_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet DIORITE_TILES = registerStoneSet("diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_DIORITE_TILES = registerStoneSet("mossy_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);
    public static SimpleBlockSet CRACKED_DIORITE_TILES = registerStoneSet("cracked_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);

    //public static SimpleBlockSet MOSSY_POLISHED_BASALT = registerStoneSet("mossy_polished_basalt", Blocks.BASALT.getHardness(), Blocks.BASALT.getBlastResistance(), Blocks.POLISHED_BASALT);
    //public static SimpleBlockSet CRACKED_POLISHED_BASALT = registerStoneSet("cracked_polished_basalt", Blocks.BASALT.getHardness(), Blocks.BASALT.getBlastResistance(), Blocks.POLISHED_BASALT);
    public static SimpleBlockSet BASALT_BRICKS = registerStoneSet("basalt_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.SMOOTH_BASALT);
    public static SimpleBlockSet MOSSY_BASALT_BRICKS = registerStoneSet("mossy_basalt_bricks", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet CRACKED_BASALT_BRICKS = registerStoneSet("cracked_basalt_bricks", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet BASALT_TILES = registerStoneSet("basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    //public static SimpleBlockSet MOSSY_BASALT_TILES = registerStoneSet("mossy_basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_TILES.base);
    //public static SimpleBlockSet CRACKED_BASALT_TILES = registerStoneSet("cracked_basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_TILES.base);

    public static SimpleBlockSetMain QUARTZITE = registerMainStoneSet("quartzite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_QUARTZITE = registerStoneSet("cobbled_quartzite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, QUARTZITE.base);
    //public static SimpleBlockSet MOSSY_COBBLED_QUARTZITE = registerStoneSet("mossy_cobbled_quartzite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, QUARTZITE.base);
    public static SimpleBlockSet POLISHED_QUARTZITE = registerStoneSet("polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), QUARTZITE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_QUARTZITE = registerStoneSet("mossy_polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_QUARTZITE = registerStoneSet("cracked_polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    public static SimpleBlockSet QUARTZITE_BRICKS = registerStoneSet("quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    //public static SimpleBlockSet MOSSY_QUARTZITE_BRICKS = registerStoneSet("mossy_quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    //public static SimpleBlockSet CRACKED_QUARTZITE_BRICKS = registerStoneSet("cracked_quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    public static SimpleBlockSet QUARTZITE_TILES = registerStoneSet("quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    //public static SimpleBlockSet MOSSY_QUARTZITE_TILES = registerStoneSet("mossy_quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_TILES.base);
    //public static SimpleBlockSet CRACKED_QUARTZITE_TILES = registerStoneSet("cracked_quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_TILES.base);

    public static SimpleBlockSetMain JADEITE = registerMainStoneSet("jadeite", STONE_HARDNESS + 1F, STONE_BLAST_RESISTANCE + 0.5F, null);
    public static SimpleBlockSet COBBLED_JADEITE = registerStoneSet("cobbled_jadeite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, JADEITE.base);
    public static SimpleBlockSet POLISHED_JADEITE = registerStoneSet("polished_jadeite", JADEITE.base.getHardness(), JADEITE.base.getBlastResistance(), JADEITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_JADEITE = registerStoneSet("cracked_polished_jadeite", JADEITE.base.getHardness(), JADEITE.base.getBlastResistance(), POLISHED_JADEITE.base);
    public static SimpleBlockSet JADEITE_BRICKS = registerStoneSet("jadeite_bricks", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), POLISHED_JADEITE.base);
    public static SimpleBlockSet CRACKED_JADEITE_BRICKS = registerStoneSet("cracked_jadeite_bricks", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_BRICKS.base);
    public static SimpleBlockSet JADEITE_TILES = registerStoneSet("jadeite_tiles", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_JADEITE_TILES = registerStoneSet("cracked_jadeite_tiles", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_TILES.base);

    public static SimpleBlockSetMain DIFTOMIN = registerMainStoneSet("diftomin", DIFTOMIN_HARDNESS, DIFTOMIN_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_DIFTOMIN = registerStoneSet("cobbled_diftomin", DIFTOMIN_BRICKS_HARDNESS, DIFTOMIN_BRICKS_BLAST_RESISTANCE, DIFTOMIN.base);
    //public static SimpleBlockSet MOSSY_COBBLED_DIFTOMIN = registerStoneSet("mossy_cobbled_diftomin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DIFTOMIN.base);
    public static SimpleBlockSet POLISHED_DIFTOMIN = registerStoneSet("polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), DIFTOMIN.base);
    //public static SimpleBlockSet MOSSY_POLISHED_DIFTOMIN = registerStoneSet("mossy_polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    //public static SimpleBlockSet CRACKED_POLISHED_DIFTOMIN = registerStoneSet("cracked_polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    public static SimpleBlockSet DIFTOMIN_BRICKS = registerStoneSet("diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    //public static SimpleBlockSet MOSSY_DIFTOMIN_BRICKS = registerStoneSet("mossy_diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    //public static SimpleBlockSet CRACKED_DIFTOMIN_BRICKS = registerStoneSet("cracked_diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    public static SimpleBlockSet DIFTOMIN_TILES = registerStoneSet("diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    //public static SimpleBlockSet MOSSY_DIFTOMIN_TILES = registerStoneSet("mossy_diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_TILES.base);
    //public static SimpleBlockSet CRACKED_DIFTOMIN_TILES = registerStoneSet("cracked_diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_TILES.base);

    public static SimpleBlockSetMain EPMOSTO = registerMainStoneSet("epmosto", EPMOSTO_HARDNESS, EPMOSTO_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_EPMOSTO = registerStoneSet("cobbled_epmosto", EPMOSTO_BRICKS_HARDNESS, EPMOSTO_BRICKS_BLAST_RESISTANCE, EPMOSTO.base);
    //public static SimpleBlockSet MOSSY_COBBLED_EPMOSTO = registerStoneSet("mossy_cobbled_epmosto", EPMOSTO_BRICKS_HARDNESS, EPMOSTO_BRICKS_BLAST_RESISTANCE, EPMOSTO.base);
    public static SimpleBlockSet POLISHED_EPMOSTO = registerStoneSet("polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), EPMOSTO.base);
    //public static SimpleBlockSet MOSSY_POLISHED_EPMOSTO = registerStoneSet("mossy_polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    //public static SimpleBlockSet CRACKED_POLISHED_EPMOSTO = registerStoneSet("cracked_polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    public static SimpleBlockSet EPMOSTO_BRICKS = registerStoneSet("epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    //public static SimpleBlockSet MOSSY_EPMOSTO_BRICKS = registerStoneSet("mossy_epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    //public static SimpleBlockSet CRACKED_EPMOSTO_BRICKS = registerStoneSet("cracked_epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    public static SimpleBlockSet EPMOSTO_TILES = registerStoneSet("epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    //public static SimpleBlockSet MOSSY_EPMOSTO_TILES = registerStoneSet("mossy_epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_TILES.base);
    //public static SimpleBlockSet CRACKED_EPMOSTO_TILES = registerStoneSet("cracked_epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_TILES.base);


    public static SimpleBlockSetMain[] setsMain = new SimpleBlockSetMain[] {

            ASHEN_STONE,

            GONLUIN,

            FROZEN_STONE,

            DOLOMITE,

            LIMESTONE,

            BLUE_TUFF,

            QUARTZITE,

            JADEITE,

            DIFTOMIN,

            EPMOSTO,
    };

    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            ASHEN_COBBLESTONE,
            ASHEN_BRICKS,
            ASHEN_TILES,

            COBBLED_GONLUIN,
            MOSSY_COBBLED_GONLUIN,
            POLISHED_GONLUIN,
            GONLUIN_BRICKS,
            CRACKED_GONLUIN_BRICKS,
            GONLUIN_TILES,

            FROZEN_COBBLESTONE,
            FROZEN_BRICKS,
            FROZEN_TILES,
            POLISHED_FROZEN_STONE,

            COBBLED_CALCITE,
            MOSSY_COBBLED_CALCITE,
            POLISHED_CALCITE,
            MOSSY_POLISHED_CALCITE,
            CRACKED_POLISHED_CALCITE,
            CALCITE_BRICKS,
            MOSSY_CALCITE_BRICKS,
            CRACKED_CALCITE_BRICKS,
            CALCITE_TILES,
            MOSSY_CALCITE_TILES,
            CRACKED_CALCITE_TILES,

            DOLOMITE_BRICKS,
            DOLOMITE_TILES,
            POLISHED_DOLOMITE,

            COBBLED_LIMESTONE,
            MOSSY_COBBLED_LIMESTONE,
            LIMESTONE_BRICKS,
            CRACKED_LIMESTONE_BRICKS,
            MOSSY_LIMESTONE_BRICKS,
            LIMESTONE_TILES,
            MOSSY_LIMESTONE_TILES,
            CRACKED_LIMESTONE_TILES,
            POLISHED_LIMESTONE,
            MOSSY_POLISHED_LIMESTONE,
            CRACKED_POLISHED_LIMESTONE,

            STONE_TILES,
            MOSSY_STONE_TILES,
            CRACKED_STONE_TILES,
            MOSSY_SMOOTH_STONE,
            CRACKED_SMOOTH_STONE,

            MOSSY_DEEPSLATE_BRICKS,
            MOSSY_POLISHED_DEEPSLATE,
            CRACKED_POLISHED_DEEPSLATE,
            MOSSY_DEEPSLATE_TILES,

            COBBLED_BLUE_TUFF,
            MOSSY_COBBLED_BLUE_TUFF,
            POLISHED_BLUE_TUFF,
            BLUE_TUFF_BRICKS,
            MOSSY_BLUE_TUFF_BRICKS,
            CRACKED_BLUE_TUFF_BRICKS,
            BLUE_TUFF_TILES,
            CRACKED_BLUE_TUFF_TILES,

            ANDESITE_BRICKS,
            MOSSY_ANDESITE_BRICKS,
            CRACKED_ANDESITE_BRICKS,
            ANDESITE_TILES,
            MOSSY_ANDESITE_TILES,
            CRACKED_ANDESITE_TILES,
            MOSSY_POLISHED_ANDESITE,
            CRACKED_POLISHED_ANDESITE,

            GRANITE_BRICKS,
            MOSSY_GRANITE_BRICKS,
            CRACKED_GRANITE_BRICKS,
            GRANITE_TILES,
            MOSSY_GRANITE_TILES,
            CRACKED_GRANITE_TILES,
            MOSSY_POLISHED_GRANITE,
            CRACKED_POLISHED_GRANITE,

            COBBLED_DIORITE,
            MOSSY_COBBLED_DIORITE,
            DIORITE_BRICKS,
            MOSSY_DIORITE_BRICKS,
            CRACKED_DIORITE_BRICKS,
            DIORITE_TILES,
            MOSSY_DIORITE_TILES,
            CRACKED_DIORITE_TILES,
            MOSSY_POLISHED_DIORITE,
            CRACKED_POLISHED_DIORITE,

            BASALT_BRICKS,
            MOSSY_BASALT_BRICKS,
            CRACKED_BASALT_BRICKS,
            BASALT_TILES,

            QUARTZITE_BRICKS,
            QUARTZITE_TILES,

            POLISHED_JADEITE,
            CRACKED_POLISHED_JADEITE,
            JADEITE_BRICKS,
            CRACKED_JADEITE_BRICKS,
            JADEITE_TILES,
            CRACKED_JADEITE_TILES,

            COBBLED_DIFTOMIN,
            POLISHED_DIFTOMIN,
            DIFTOMIN_BRICKS,
            DIFTOMIN_TILES,

            COBBLED_EPMOSTO,
            POLISHED_EPMOSTO,
            EPMOSTO_BRICKS,
            EPMOSTO_TILES,
    };

    public record SimpleBlockSet(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall) {
    }

    public record SimpleBlockSetMain(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall, Block pressurePlate, Block button, Block stool, Block table, Block chair) {
    }

    private static SimpleBlockSet registerStoneSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerStoneBlock(name, new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(hardness, blastResistance).requiresTool()),false);

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(base).strength(hardness, blastResistance).requiresTool()),false);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), FabricBlockSettings.copyOf(base).strength(hardness, blastResistance).requiresTool()),false);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        return new SimpleBlockSet(source, base, slab, verticalSlab, stairs, wall);
    }

    private static SimpleBlockSetMain registerMainStoneSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerStoneBlock(name, new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(hardness, blastResistance).requiresTool()),false);

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(base).strength(hardness, blastResistance).requiresTool()),false);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), FabricBlockSettings.copyOf(base).strength(hardness, blastResistance).requiresTool()),false);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block pressurePlate = ModBlocks.registerStoneBlock(name + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(), BlockSetType.STONE),false);

        Block button = ModBlocks.registerStoneBlock(name + "_button", new ButtonBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(), BlockSetType.STONE, 20, false),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(FabricBlockSettings.copyOf(base)
                .strength(hardness, blastResistance).requiresTool().nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new StoneTableBlock(FabricBlockSettings.copyOf(base)
                .strength(hardness, blastResistance).requiresTool().nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new StoneChairBlock(FabricBlockSettings.copyOf(base).nonOpaque()),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new SimpleBlockSetMain(source, base, slab, verticalSlab, stairs, wall, pressurePlate, button, stool, table, chair);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering SimpleBlockSets for " + MiddleEarth.MOD_ID);
    }
}
