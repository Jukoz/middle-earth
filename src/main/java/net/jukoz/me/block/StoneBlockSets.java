package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.VerticalSlabBlock;
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

    public static SimpleBlockSet ASHEN_COBBLESTONE = registerBrickSet("ashen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet ASHEN_STONE = registerBrickSet("ashen_stone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet ASHEN_BRICKS = registerBrickSet("ashen_bricks", ASHEN_COBBLESTONE.base.getHardness(), ASHEN_COBBLESTONE.base.getBlastResistance(), ASHEN_STONE.base);
    public static SimpleBlockSet ASHEN_TILES = registerBrickSet("ashen_tiles", ASHEN_BRICKS.base.getHardness(), ASHEN_BRICKS.base.getBlastResistance(), ASHEN_BRICKS.base);

    public static SimpleBlockSet GONLUIN = registerBrickSet("gonluin", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_GONLUIN = registerBrickSet("cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GONLUIN.base);
    public static SimpleBlockSet MOSSY_COBBLED_GONLUIN = registerBrickSet("mossy_cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GONLUIN.base);
    public static SimpleBlockSet POLISHED_GONLUIN = registerBrickSet("polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), GONLUIN.base);
    public static SimpleBlockSet MOSSY_POLISHED_GONLUIN = registerBrickSet("mossy_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet CRACKED_POLISHED_GONLUIN = registerBrickSet("cracked_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet GONLUIN_BRICKS = registerBrickSet("gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet MOSSY_GONLUIN_BRICKS = registerBrickSet("mossy_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet CRACKED_GONLUIN_BRICKS = registerBrickSet("cracked_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet GONLUIN_TILES = registerBrickSet("gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet MOSSY_GONLUIN_TILES = registerBrickSet("mossy_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);
    public static SimpleBlockSet CRACKED_GONLUIN_TILES = registerBrickSet("cracked_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);

    public static SimpleBlockSet COBBLED_CALCITE = registerBrickSet("cobbled_calcite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.CALCITE);
    public static SimpleBlockSet MOSSY_COBBLED_CALCITE = registerBrickSet("mossy_cobbled_calcite", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), COBBLED_CALCITE.base);
    public static SimpleBlockSet POLISHED_CALCITE = registerBrickSet("polished_calcite", Blocks.CALCITE.getHardness(), Blocks.CALCITE.getBlastResistance(), Blocks.CALCITE);
    public static SimpleBlockSet MOSSY_POLISHED_CALCITE = registerBrickSet("mossy_polished_calcite", POLISHED_CALCITE.base.getHardness(), POLISHED_CALCITE.base.getBlastResistance(), POLISHED_CALCITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_CALCITE = registerBrickSet("cracked_polished_calcite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, POLISHED_CALCITE.base);
    public static SimpleBlockSet CALCITE_BRICKS = registerBrickSet("calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), POLISHED_CALCITE.base);
    public static SimpleBlockSet MOSSY_CALCITE_BRICKS = registerBrickSet("mossy_calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_CALCITE_BRICKS = registerBrickSet("cracked_calcite_bricks", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet CALCITE_TILES = registerBrickSet("calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_CALCITE_TILES = registerBrickSet("mossy_calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_TILES.base);
    public static SimpleBlockSet CRACKED_CALCITE_TILES = registerBrickSet("cracked_calcite_tiles", COBBLED_CALCITE.base.getHardness(), COBBLED_CALCITE.base.getBlastResistance(), CALCITE_TILES.base);

    public static SimpleBlockSet DOLOMITE = registerBrickSet("dolomite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_DOLOMITE = registerBrickSet("cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE.base);
    public static SimpleBlockSet MOSSY_COBBLED_DOLOMITE = registerBrickSet("mossy_cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE.base);
    public static SimpleBlockSet POLISHED_DOLOMITE = registerBrickSet("polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), DOLOMITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_DOLOMITE = registerBrickSet("mossy_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_DOLOMITE = registerBrickSet("cracked_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet DOLOMITE_BRICKS = registerBrickSet("dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet MOSSY_DOLOMITE_BRICKS = registerBrickSet("mossy_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_DOLOMITE_BRICKS = registerBrickSet("cracked_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet DOLOMITE_TILES = registerBrickSet("dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_DOLOMITE_TILES = registerBrickSet("mossy_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);
    public static SimpleBlockSet CRACKED_DOLOMITE_TILES = registerBrickSet("cracked_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);

    public static SimpleBlockSet FROZEN_STONE = registerBrickSet("frozen_stone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet FROZEN_COBBLESTONE = registerBrickSet("frozen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, FROZEN_STONE.base);
    public static SimpleBlockSet MOSSY_FROZEN_COBBLESTONE = registerBrickSet("mossy_frozen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, FROZEN_STONE.base);
    public static SimpleBlockSet POLISHED_FROZEN_STONE = registerBrickSet("polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), FROZEN_STONE.base);
    public static SimpleBlockSet MOSSY_POLISHED_FROZEN_STONE = registerBrickSet("mossy_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    public static SimpleBlockSet CRACKED_POLISHED_FROZEN_STONE = registerBrickSet("cracked_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    public static SimpleBlockSet FROZEN_BRICKS = registerBrickSet("frozen_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    public static SimpleBlockSet MOSSY_FROZEN_STONE_BRICKS = registerBrickSet("mossy_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    public static SimpleBlockSet CRACKED_FROZEN_STONE_BRICKS = registerBrickSet("cracked_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    public static SimpleBlockSet FROZEN_TILES = registerBrickSet("frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    public static SimpleBlockSet MOSSY_FROZEN_TILES = registerBrickSet("mossy_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);
    public static SimpleBlockSet CRACKED_FROZEN_TILES = registerBrickSet("cracked_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);

    public static SimpleBlockSet LIMESTONE = registerBrickSet("limestone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_LIMESTONE = registerBrickSet("cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, LIMESTONE.base);
    public static SimpleBlockSet MOSSY_COBBLED_LIMESTONE = registerBrickSet("mossy_cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, LIMESTONE.base);
    public static SimpleBlockSet POLISHED_LIMESTONE = registerBrickSet("polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), LIMESTONE.base);
    public static SimpleBlockSet MOSSY_POLISHED_LIMESTONE = registerBrickSet("mossy_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet CRACKED_POLISHED_LIMESTONE = registerBrickSet("cracked_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet LIMESTONE_BRICKS = registerBrickSet("limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_BRICKS = registerBrickSet("mossy_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_BRICKS = registerBrickSet("cracked_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet LIMESTONE_TILES = registerBrickSet("limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_TILES = registerBrickSet("mossy_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_TILES = registerBrickSet("cracked_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);

    public static SimpleBlockSet STONE_TILES = registerBrickSet("stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.STONE);
    public static SimpleBlockSet MOSSY_STONE_TILES = registerBrickSet("mossy_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet CRACKED_STONE_TILES = registerBrickSet("cracked_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet MOSSY_SMOOTH_STONE = registerBrickSet("mossy_smooth_stone", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.SMOOTH_STONE);
    public static SimpleBlockSet CRACKED_SMOOTH_STONE = registerBrickSet("cracked_smooth_stone", Blocks.SMOOTH_STONE.getHardness(), Blocks.SMOOTH_STONE.getBlastResistance(), Blocks.SMOOTH_STONE);

    public static SimpleBlockSet MOSSY_DEEPSLATE_BRICKS = registerBrickSet("mossy_deepslate_bricks", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_BRICKS);
    public static SimpleBlockSet MOSSY_POLISHED_DEEPSLATE = registerBrickSet("mossy_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet CRACKED_POLISHED_DEEPSLATE = registerBrickSet("cracked_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet MOSSY_DEEPSLATE_TILES = registerBrickSet("mossy_deepslate_tiles", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_TILES);

    public static SimpleBlockSet COBBLED_ANDESITE = registerBrickSet("cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.ANDESITE);
    public static SimpleBlockSet MOSSY_COBBLED_ANDESITE = registerBrickSet("mossy_cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.ANDESITE);
    public static SimpleBlockSet MOSSY_POLISHED_ANDESITE = registerBrickSet("mossy_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet CRACKED_POLISHED_ANDESITE = registerBrickSet("cracked_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet ANDESITE_BRICKS = registerBrickSet("andesite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet MOSSY_ANDESITE_BRICKS = registerBrickSet("mossy_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_ANDESITE_BRICKS = registerBrickSet("cracked_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet ANDESITE_TILES = registerBrickSet("andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_ANDESITE_TILES = registerBrickSet("mossy_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);
    public static SimpleBlockSet CRACKED_ANDESITE_TILES = registerBrickSet("cracked_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);

    public static SimpleBlockSet MOSSY_POLISHED_BASALT = registerBrickSet("mossy_polished_basalt", Blocks.BASALT.getHardness(), Blocks.BASALT.getBlastResistance(), Blocks.POLISHED_BASALT);
    public static SimpleBlockSet CRACKED_POLISHED_BASALT = registerBrickSet("cracked_polished_basalt", Blocks.BASALT.getHardness(), Blocks.BASALT.getBlastResistance(), Blocks.POLISHED_BASALT);
    public static SimpleBlockSet BASALT_BRICKS = registerBrickSet("basalt_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.SMOOTH_BASALT);
    public static SimpleBlockSet MOSSY_BASALT_BRICKS = registerBrickSet("mossy_basalt_bricks", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet CRACKED_BASALT_BRICKS = registerBrickSet("cracked_basalt_bricks", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet BASALT_TILES = registerBrickSet("basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet MOSSY_BASALT_TILES = registerBrickSet("mossy_basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_TILES.base);
    public static SimpleBlockSet CRACKED_BASALT_TILES = registerBrickSet("cracked_basalt_tiles", BASALT_BRICKS.base.getHardness(), BASALT_BRICKS.base.getBlastResistance(), BASALT_TILES.base);

    public static SimpleBlockSet COBBLED_GRANITE = registerBrickSet("cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.GRANITE);
    public static SimpleBlockSet MOSSY_COBBLED_GRANITE = registerBrickSet("mossy_cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.GRANITE);
    public static SimpleBlockSet MOSSY_POLISHED_GRANITE = registerBrickSet("mossy_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet CRACKED_POLISHED_GRANITE = registerBrickSet("cracked_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet GRANITE_BRICKS = registerBrickSet("granite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet MOSSY_GRANITE_BRICKS = registerBrickSet("mossy_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_GRANITE_BRICKS = registerBrickSet("cracked_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet GRANITE_TILES = registerBrickSet("granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_GRANITE_TILES = registerBrickSet("mossy_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);
    public static SimpleBlockSet CRACKED_GRANITE_TILES = registerBrickSet("cracked_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);

    public static SimpleBlockSet COBBLED_DIORITE = registerBrickSet("cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_COBBLED_DIORITE = registerBrickSet("mossy_cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_POLISHED_DIORITE = registerBrickSet("mossy_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet CRACKED_POLISHED_DIORITE = registerBrickSet("cracked_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet DIORITE_BRICKS = registerBrickSet("diorite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet MOSSY_DIORITE_BRICKS = registerBrickSet("mossy_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_DIORITE_BRICKS = registerBrickSet("cracked_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet DIORITE_TILES = registerBrickSet("diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_DIORITE_TILES = registerBrickSet("mossy_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);
    public static SimpleBlockSet CRACKED_DIORITE_TILES = registerBrickSet("cracked_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);

    public static SimpleBlockSet QUARTZITE = registerBrickSet("quartzite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_QUARTZITE = registerBrickSet("cobbled_quartzite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, QUARTZITE.base);
    public static SimpleBlockSet MOSSY_COBBLED_QUARTZITE = registerBrickSet("mossy_cobbled_quartzite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, QUARTZITE.base);
    public static SimpleBlockSet POLISHED_QUARTZITE = registerBrickSet("polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), QUARTZITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_QUARTZITE = registerBrickSet("mossy_polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_QUARTZITE = registerBrickSet("cracked_polished_quartzite", QUARTZITE.base.getHardness(), QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    public static SimpleBlockSet QUARTZITE_BRICKS = registerBrickSet("quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), POLISHED_QUARTZITE.base);
    public static SimpleBlockSet MOSSY_QUARTZITE_BRICKS = registerBrickSet("mossy_quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_QUARTZITE_BRICKS = registerBrickSet("cracked_quartzite_bricks", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    public static SimpleBlockSet QUARTZITE_TILES = registerBrickSet("quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_QUARTZITE_TILES = registerBrickSet("mossy_quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_TILES.base);
    public static SimpleBlockSet CRACKED_QUARTZITE_TILES = registerBrickSet("cracked_quartzite_tiles", COBBLED_QUARTZITE.base.getHardness(), COBBLED_QUARTZITE.base.getBlastResistance(), QUARTZITE_TILES.base);

    public static SimpleBlockSet JADEITE = registerBrickSet("jadeite", STONE_HARDNESS + 1F, STONE_BLAST_RESISTANCE + 0.5F, null);
    public static SimpleBlockSet COBBLED_JADEITE = registerBrickSet("cobbled_jadeite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, JADEITE.base);
    public static SimpleBlockSet POLISHED_JADEITE = registerBrickSet("polished_jadeite", JADEITE.base.getHardness(), JADEITE.base.getBlastResistance(), JADEITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_JADEITE = registerBrickSet("cracked_polished_jadeite", JADEITE.base.getHardness(), JADEITE.base.getBlastResistance(), POLISHED_JADEITE.base);
    public static SimpleBlockSet JADEITE_BRICKS = registerBrickSet("jadeite_bricks", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), POLISHED_JADEITE.base);
    public static SimpleBlockSet CRACKED_JADEITE_BRICKS = registerBrickSet("cracked_jadeite_bricks", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_BRICKS.base);
    public static SimpleBlockSet JADEITE_TILES = registerBrickSet("jadeite_tiles", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_JADEITE_TILES = registerBrickSet("cracked_jadeite_tiles", COBBLED_JADEITE.base.getHardness(), COBBLED_JADEITE.base.getBlastResistance(), JADEITE_TILES.base);

    public static SimpleBlockSet DIFTOMIN = registerBrickSet("diftomin", DIFTOMIN_HARDNESS, DIFTOMIN_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_DIFTOMIN = registerBrickSet("cobbled_diftomin", DIFTOMIN_BRICKS_HARDNESS, DIFTOMIN_BRICKS_BLAST_RESISTANCE, DIFTOMIN.base);
    public static SimpleBlockSet MOSSY_COBBLED_DIFTOMIN = registerBrickSet("mossy_cobbled_diftomin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DIFTOMIN.base);
    public static SimpleBlockSet POLISHED_DIFTOMIN = registerBrickSet("polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), DIFTOMIN.base);
    public static SimpleBlockSet MOSSY_POLISHED_DIFTOMIN = registerBrickSet("mossy_polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    public static SimpleBlockSet CRACKED_POLISHED_DIFTOMIN = registerBrickSet("cracked_polished_diftomin", DIFTOMIN.base.getHardness(), DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    public static SimpleBlockSet DIFTOMIN_BRICKS = registerBrickSet("diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), POLISHED_DIFTOMIN.base);
    public static SimpleBlockSet MOSSY_DIFTOMIN_BRICKS = registerBrickSet("mossy_diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    public static SimpleBlockSet CRACKED_DIFTOMIN_BRICKS = registerBrickSet("cracked_diftomin_bricks", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    public static SimpleBlockSet DIFTOMIN_TILES = registerBrickSet("diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_BRICKS.base);
    public static SimpleBlockSet MOSSY_DIFTOMIN_TILES = registerBrickSet("mossy_diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_TILES.base);
    public static SimpleBlockSet CRACKED_DIFTOMIN_TILES = registerBrickSet("cracked_diftomin_tiles", COBBLED_DIFTOMIN.base.getHardness(), COBBLED_DIFTOMIN.base.getBlastResistance(), DIFTOMIN_TILES.base);

    public static SimpleBlockSet EPMOSTO = registerBrickSet("epmosto", EPMOSTO_HARDNESS, EPMOSTO_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_EPMOSTO = registerBrickSet("cobbled_epmosto", EPMOSTO_BRICKS_HARDNESS, EPMOSTO_BRICKS_BLAST_RESISTANCE, EPMOSTO.base);
    public static SimpleBlockSet MOSSY_COBBLED_EPMOSTO = registerBrickSet("mossy_cobbled_epmosto", EPMOSTO_BRICKS_HARDNESS, EPMOSTO_BRICKS_BLAST_RESISTANCE, EPMOSTO.base);
    public static SimpleBlockSet POLISHED_EPMOSTO = registerBrickSet("polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), EPMOSTO.base);
    public static SimpleBlockSet MOSSY_POLISHED_EPMOSTO = registerBrickSet("mossy_polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    public static SimpleBlockSet CRACKED_POLISHED_EPMOSTO = registerBrickSet("cracked_polished_epmosto", EPMOSTO.base.getHardness(), EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    public static SimpleBlockSet EPMOSTO_BRICKS = registerBrickSet("epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), POLISHED_EPMOSTO.base);
    public static SimpleBlockSet MOSSY_EPMOSTO_BRICKS = registerBrickSet("mossy_epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    public static SimpleBlockSet CRACKED_EPMOSTO_BRICKS = registerBrickSet("cracked_epmosto_bricks", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    public static SimpleBlockSet EPMOSTO_TILES = registerBrickSet("epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_BRICKS.base);
    public static SimpleBlockSet MOSSY_EPMOSTO_TILES = registerBrickSet("mossy_epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_TILES.base);
    public static SimpleBlockSet CRACKED_EPMOSTO_TILES = registerBrickSet("cracked_epmosto_tiles", COBBLED_EPMOSTO.base.getHardness(), COBBLED_EPMOSTO.base.getBlastResistance(), EPMOSTO_TILES.base);



    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {

            ASHEN_STONE,
            ASHEN_BRICKS,
            ASHEN_TILES,

            GONLUIN,
            COBBLED_GONLUIN,
            POLISHED_GONLUIN,
            GONLUIN_BRICKS,
            GONLUIN_TILES,

            FROZEN_COBBLESTONE,
            FROZEN_STONE,
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

            DOLOMITE,
            DOLOMITE_BRICKS,
            DOLOMITE_TILES,
            POLISHED_DOLOMITE,

            COBBLED_LIMESTONE,
            MOSSY_COBBLED_LIMESTONE,
            LIMESTONE,
            LIMESTONE_BRICKS,
            CRACKED_LIMESTONE_BRICKS,
            MOSSY_LIMESTONE_BRICKS,
            LIMESTONE_TILES,
            CRACKED_LIMESTONE_TILES,
            POLISHED_LIMESTONE,

            STONE_TILES,
            MOSSY_STONE_TILES,
            CRACKED_STONE_TILES,
            MOSSY_SMOOTH_STONE,
            CRACKED_SMOOTH_STONE,

            MOSSY_DEEPSLATE_BRICKS,
            MOSSY_POLISHED_DEEPSLATE,
            CRACKED_POLISHED_DEEPSLATE,
            MOSSY_DEEPSLATE_TILES,

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
            MOSSY_BASALT_TILES,
            CRACKED_BASALT_TILES,

            QUARTZITE,
            QUARTZITE_BRICKS,
            QUARTZITE_TILES,

            JADEITE,
            POLISHED_JADEITE,
            CRACKED_POLISHED_JADEITE,
            JADEITE_BRICKS,
            CRACKED_JADEITE_BRICKS,
            JADEITE_TILES,
            CRACKED_JADEITE_TILES,

            COBBLED_DIFTOMIN,
            DIFTOMIN,
            POLISHED_DIFTOMIN,
            DIFTOMIN_BRICKS,
            DIFTOMIN_TILES,

            COBBLED_EPMOSTO,
            EPMOSTO,
            POLISHED_EPMOSTO,
            EPMOSTO_BRICKS,
            EPMOSTO_TILES,
    };

    public record SimpleBlockSet(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall) {
    }

    private static SimpleBlockSet registerBrickSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerBlock(name, new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(hardness, blastResistance).requiresTool()));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(hardness, blastResistance).requiresTool()));

        Block verticalSlab = ModBlocks.registerBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(hardness, blastResistance).requiresTool()));

        Block wall = ModBlocks.registerBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()));

        return new SimpleBlockSet(source, base, slab, verticalSlab, stairs, wall);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering SimpleBlockSets for " + MiddleEarth.MOD_ID);
    }
}
