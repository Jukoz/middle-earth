package net.jukoz.me.block;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

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

    public static final float NURGON_HARDNESS = 5.0F;
    public static final float NURGON_BLAST_RESISTANCE = 7.0F;
    public static final float NURGON_BRICKS_HARDNESS = 4.5F;
    public static final float NURGON_BRICKS_BLAST_RESISTANCE = 7.0F;

    public static final float MEDGON_HARDNESS = 7.0F;
    public static final float MEDGON_BLAST_RESISTANCE = 9.0F;
    public static final float MEDGON_BRICKS_HARDNESS = 6.5F;
    public static final float MEDGON_BRICKS_BLAST_RESISTANCE = 9.0F;

    public static SimpleBlockSetMain ASHEN_STONE = registerMainStoneSet("ashen_stone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet ASHEN_COBBLESTONE = registerStoneSet("ashen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, ASHEN_STONE.base);
    public static SimpleBlockSet ASHEN_BRICKS = registerStoneSet("ashen_bricks", ASHEN_COBBLESTONE.base.getHardness(), ASHEN_COBBLESTONE.base.getBlastResistance(), ASHEN_STONE.base);
    public static SimpleBlockSet ASHEN_TILES = registerStoneSet("ashen_tiles", ASHEN_BRICKS.base.getHardness(), ASHEN_BRICKS.base.getBlastResistance(), ASHEN_BRICKS.base);
    public static SimpleBlockSet POLISHED_ASHEN_STONE = registerStoneSet("polished_ashen_stone", ASHEN_STONE.base.getHardness(), ASHEN_STONE.base.getBlastResistance(), ASHEN_STONE.base);

    public static SimpleBlockSetMain PUMICE = registerMainStoneSet("pumice", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);

    public static SimpleBlockSetMain GONLUIN = registerMainStoneSet("gonluin", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_GONLUIN = registerStoneSet("cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GONLUIN.base);
    public static SimpleBlockSet MOSSY_COBBLED_GONLUIN = registerStoneSet("mossy_cobbled_gonluin", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GONLUIN.base);
    public static SimpleBlockSet POLISHED_GONLUIN = registerStoneSet("polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), GONLUIN.base);
    public static SimpleBlockSet MOSSY_POLISHED_GONLUIN = registerStoneSet("mossy_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet CRACKED_POLISHED_GONLUIN = registerStoneSet("cracked_polished_gonluin", GONLUIN.base.getHardness(), GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet GONLUIN_BRICKS = registerStoneSet("gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), POLISHED_GONLUIN.base);
    public static SimpleBlockSet MOSSY_GONLUIN_BRICKS = registerStoneSet("mossy_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet CRACKED_GONLUIN_BRICKS = registerStoneSet("cracked_gonluin_bricks", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet GONLUIN_TILES = registerStoneSet("gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_BRICKS.base);
    public static SimpleBlockSet MOSSY_GONLUIN_TILES = registerStoneSet("mossy_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);
    public static SimpleBlockSet CRACKED_GONLUIN_TILES = registerStoneSet("cracked_gonluin_tiles", COBBLED_GONLUIN.base.getHardness(), COBBLED_GONLUIN.base.getBlastResistance(), GONLUIN_TILES.base);

    public static SimpleBlockSet SMOOTH_CALCITE = registerStoneSet("smooth_calcite", Blocks.CALCITE.getHardness(), Blocks.CALCITE.getBlastResistance(), Blocks.CALCITE);
    public static SimpleBlockSet MOSSY_SMOOTH_CALCITE = registerStoneSet("mossy_smooth_calcite", SMOOTH_CALCITE.base.getHardness(), SMOOTH_CALCITE.base.getBlastResistance(), SMOOTH_CALCITE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_CALCITE = registerStoneSet("cracked_smooth_calcite", SMOOTH_CALCITE.base.getHardness(), SMOOTH_CALCITE.base.getBlastResistance(), SMOOTH_CALCITE.base);
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

    public static SimpleBlockSet CALCITE_BRICKWORK = registerStoneSet("calcite_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_CALCITE = registerStoneSet("old_calcite", Blocks.CALCITE.getHardness(), Blocks.CALCITE.getBlastResistance(), COBBLED_CALCITE.base);

    public static SimpleBlockSetMain DOLOMITE = registerMainStoneSet("dolomite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_DOLOMITE = registerStoneSet("smooth_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), DOLOMITE.base);
    public static SimpleBlockSet MOSSY_SMOOTH_DOLOMITE = registerStoneSet("mossy_smooth_dolomite", SMOOTH_DOLOMITE.base.getHardness(), SMOOTH_DOLOMITE.base.getBlastResistance(), SMOOTH_DOLOMITE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_DOLOMITE = registerStoneSet("cracked_smooth_dolomite", SMOOTH_DOLOMITE.base.getHardness(), SMOOTH_DOLOMITE.base.getBlastResistance(), SMOOTH_DOLOMITE.base);
    public static SimpleBlockSet COBBLED_DOLOMITE = registerStoneSet("cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE.base);
    public static SimpleBlockSet MOSSY_COBBLED_DOLOMITE = registerStoneSet("mossy_cobbled_dolomite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_DOLOMITE.base);
    public static SimpleBlockSet POLISHED_DOLOMITE = registerStoneSet("polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), DOLOMITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_DOLOMITE = registerStoneSet("mossy_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet CRACKED_POLISHED_DOLOMITE = registerStoneSet("cracked_polished_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), POLISHED_DOLOMITE.base);
    public static SimpleBlockSet DOLOMITE_BRICKS = registerStoneSet("dolomite_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_DOLOMITE.base);
    public static SimpleBlockSet MOSSY_DOLOMITE_BRICKS = registerStoneSet("mossy_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_DOLOMITE_BRICKS = registerStoneSet("cracked_dolomite_bricks", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_BRICKS.base);
    public static SimpleBlockSet DOLOMITE_TILES = registerStoneSet("dolomite_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, DOLOMITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_DOLOMITE_TILES = registerStoneSet("mossy_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);
    public static SimpleBlockSet CRACKED_DOLOMITE_TILES = registerStoneSet("cracked_dolomite_tiles", COBBLED_DOLOMITE.base.getHardness(), COBBLED_DOLOMITE.base.getBlastResistance(), DOLOMITE_TILES.base);
    
    public static SimpleBlockSet DOLOMITE_BRICKWORK = registerStoneSet("dolomite_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimplePillarBlockSet OLD_DOLOMITE = registerStonePillarSet("old_dolomite", DOLOMITE.base.getHardness(), DOLOMITE.base.getBlastResistance(), COBBLED_DOLOMITE.base);
    
    public static SimpleBlockSetMain IRONSTONE = registerMainStoneSet("ironstone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_IRONSTONE = registerStoneSet("smooth_ironstone", IRONSTONE.base.getHardness(), IRONSTONE.base.getBlastResistance(), IRONSTONE.base);
    public static SimpleBlockSet COBBLED_IRONSTONE = registerStoneSet("cobbled_ironstone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, IRONSTONE.base);
    //public static SimpleBlockSet MOSSY_COBBLED_IRONSTONE = registerStoneSet("mossy_cobbled_ironstone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_IRONSTONE.base);
    public static SimpleBlockSet POLISHED_IRONSTONE = registerStoneSet("polished_ironstone", IRONSTONE.base.getHardness(), IRONSTONE.base.getBlastResistance(), IRONSTONE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_IRONSTONE = registerStoneSet("mossy_polished_ironstone", IRONSTONE.base.getHardness(), IRONSTONE.base.getBlastResistance(), POLISHED_IRONSTONE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_IRONSTONE = registerStoneSet("cracked_polished_ironstone", IRONSTONE.base.getHardness(), IRONSTONE.base.getBlastResistance(), POLISHED_IRONSTONE.base);
    public static SimpleBlockSet IRONSTONE_BRICKS = registerStoneSet("ironstone_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_IRONSTONE.base);
    //public static SimpleBlockSet MOSSY_IRONSTONE_BRICKS = registerStoneSet("mossy_ironstone_bricks", COBBLED_IRONSTONE.base.getHardness(), COBBLED_IRONSTONE.base.getBlastResistance(), IRONSTONE_BRICKS.base);
    //public static SimpleBlockSet CRACKED_IRONSTONE_BRICKS = registerStoneSet("cracked_ironstone_bricks", COBBLED_IRONSTONE.base.getHardness(), COBBLED_IRONSTONE.base.getBlastResistance(), IRONSTONE_BRICKS.base);
    public static SimpleBlockSet IRONSTONE_TILES = registerStoneSet("ironstone_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, IRONSTONE_BRICKS.base);
    //public static SimpleBlockSet MOSSY_IRONSTONE_TILES = registerStoneSet("mossy_ironstone_tiles", COBBLED_IRONSTONE.base.getHardness(), COBBLED_IRONSTONE.base.getBlastResistance(), IRONSTONE_TILES.base);
    //public static SimpleBlockSet CRACKED_IRONSTONE_TILES = registerStoneSet("cracked_ironstone_tiles", COBBLED_IRONSTONE.base.getHardness(), COBBLED_IRONSTONE.base.getBlastResistance(), IRONSTONE_TILES.base);

    public static SimpleBlockSetMain HEMATITE = registerMainStoneSet("hematite", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    //public static SimpleBlockSet SMOOTH_HEMATITE = registerStoneSet("smooth_hematite", HEMATITE.base.getHardness(), HEMATITE.base.getBlastResistance(), HEMATITE.base);
    public static SimpleBlockSet COBBLED_HEMATITE = registerStoneSet("cobbled_hematite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, HEMATITE.base);
    //public static SimpleBlockSet MOSSY_COBBLED_HEMATITE = registerStoneSet("mossy_cobbled_hematite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_HEMATITE.base);
    public static SimpleBlockSet POLISHED_HEMATITE = registerStoneSet("polished_hematite", HEMATITE.base.getHardness(), HEMATITE.base.getBlastResistance(), HEMATITE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_HEMATITE = registerStoneSet("mossy_polished_hematite", HEMATITE.base.getHardness(), HEMATITE.base.getBlastResistance(), POLISHED_HEMATITE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_HEMATITE = registerStoneSet("cracked_polished_hematite", HEMATITE.base.getHardness(), HEMATITE.base.getBlastResistance(), POLISHED_HEMATITE.base);
    public static SimpleBlockSet HEMATITE_BRICKS = registerStoneSet("hematite_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_HEMATITE.base);
    //public static SimpleBlockSet MOSSY_HEMATITE_BRICKS = registerStoneSet("mossy_hematite_bricks", COBBLED_HEMATITE.base.getHardness(), COBBLED_HEMATITE.base.getBlastResistance(), HEMATITE_BRICKS.base);
    //public static SimpleBlockSet CRACKED_HEMATITE_BRICKS = registerStoneSet("cracked_hematite_bricks", COBBLED_HEMATITE.base.getHardness(), COBBLED_HEMATITE.base.getBlastResistance(), HEMATITE_BRICKS.base);
    public static SimpleBlockSet HEMATITE_TILES = registerStoneSet("hematite_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, HEMATITE_BRICKS.base);
    //public static SimpleBlockSet MOSSY_HEMATITE_TILES = registerStoneSet("mossy_hematite_tiles", COBBLED_HEMATITE.base.getHardness(), COBBLED_HEMATITE.base.getBlastResistance(), HEMATITE_TILES.base);
    //public static SimpleBlockSet CRACKED_HEMATITE_TILES = registerStoneSet("cracked_hematite_tiles", COBBLED_HEMATITE.base.getHardness(), COBBLED_HEMATITE.base.getBlastResistance(), HEMATITE_TILES.base);

    public static SimpleBlockSet HEMATITE_BRICKWORK = registerStoneSet("hematite_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);
    
    public static SimpleBlockSetMain GNEISS = registerMainStoneSet("gneiss", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_GNEISS = registerStoneSet("smooth_gneiss", GNEISS.base.getHardness(), GNEISS.base.getBlastResistance(), GNEISS.base);
    public static SimpleBlockSet COBBLED_GNEISS = registerStoneSet("cobbled_gneiss", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GNEISS.base);
    //public static SimpleBlockSet MOSSY_COBBLED_GNEISS = registerStoneSet("mossy_cobbled_gneiss", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GNEISS.base);
    public static SimpleBlockSet POLISHED_GNEISS = registerStoneSet("polished_gneiss", GNEISS.base.getHardness(), GNEISS.base.getBlastResistance(), GNEISS.base);
    //public static SimpleBlockSet MOSSY_POLISHED_GNEISS = registerStoneSet("mossy_polished_gneiss", GNEISS.base.getHardness(), GNEISS.base.getBlastResistance(), POLISHED_GNEISS.base);
    //public static SimpleBlockSet CRACKED_POLISHED_GNEISS = registerStoneSet("cracked_polished_gneiss", GNEISS.base.getHardness(), GNEISS.base.getBlastResistance(), POLISHED_GNEISS.base);
    public static SimpleBlockSet GNEISS_BRICKS = registerStoneSet("gneiss_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_GNEISS.base);
    //public static SimpleBlockSet MOSSY_GNEISS_BRICKS = registerStoneSet("mossy_gneiss_bricks", COBBLED_GNEISS.base.getHardness(), COBBLED_GNEISS.base.getBlastResistance(), GNEISS_BRICKS.base);
    //public static SimpleBlockSet CRACKED_GNEISS_BRICKS = registerStoneSet("cracked_gneiss_bricks", COBBLED_GNEISS.base.getHardness(), COBBLED_GNEISS.base.getBlastResistance(), GNEISS_BRICKS.base);
    public static SimpleBlockSet GNEISS_TILES = registerStoneSet("gneiss_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GNEISS_BRICKS.base);
    public static SimpleBlockSet MOSSY_GNEISS_TILES = registerStoneSet("mossy_gneiss_tiles", COBBLED_GNEISS.base.getHardness(), COBBLED_GNEISS.base.getBlastResistance(), GNEISS_TILES.base);
    //public static SimpleBlockSet CRACKED_GNEISS_TILES = registerStoneSet("cracked_gneiss_tiles", COBBLED_GNEISS.base.getHardness(), COBBLED_GNEISS.base.getBlastResistance(), GNEISS_TILES.base);

    public static SimpleBlockSet GNEISS_BRICKWORK = registerStoneSet("gneiss_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSetMain ZIGILABAN = registerMainStoneSet("zigilaban", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    //public static SimpleBlockSet SMOOTH_ZIGILABAN = registerStoneSet("smooth_zigilaban", ZIGILABAN.base.getHardness(), ZIGILABAN.base.getBlastResistance(), ZIGILABAN.base);
    //public static SimpleBlockSet COBBLED_ZIGILABAN = registerStoneSet("cobbled_zigilaban", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, ZIGILABAN.base);
    //public static SimpleBlockSet MOSSY_COBBLED_ZIGILABAN = registerStoneSet("mossy_cobbled_zigilaban", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_ZIGILABAN.base);
    public static SimpleBlockSet POLISHED_ZIGILABAN = registerStoneSet("polished_zigilaban", ZIGILABAN.base.getHardness(), ZIGILABAN.base.getBlastResistance(), ZIGILABAN.base);
    //public static SimpleBlockSet MOSSY_POLISHED_ZIGILABAN = registerStoneSet("mossy_polished_zigilaban", ZIGILABAN.base.getHardness(), ZIGILABAN.base.getBlastResistance(), POLISHED_ZIGILABAN.base);
    //public static SimpleBlockSet CRACKED_POLISHED_ZIGILABAN = registerStoneSet("cracked_polished_zigilaban", ZIGILABAN.base.getHardness(), ZIGILABAN.base.getBlastResistance(), POLISHED_ZIGILABAN.base);
    public static SimpleBlockSet ZIGILABAN_BRICKS = registerStoneSet("zigilaban_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_ZIGILABAN.base);
    //public static SimpleBlockSet MOSSY_ZIGILABAN_BRICKS = registerStoneSet("mossy_zigilaban_bricks", COBBLED_ZIGILABAN.base.getHardness(), COBBLED_ZIGILABAN.base.getBlastResistance(), ZIGILABAN_BRICKS.base);
    //public static SimpleBlockSet CRACKED_ZIGILABAN_BRICKS = registerStoneSet("cracked_zigilaban_bricks", COBBLED_ZIGILABAN.base.getHardness(), COBBLED_ZIGILABAN.base.getBlastResistance(), ZIGILABAN_BRICKS.base);
    public static SimpleBlockSet ZIGILABAN_TILES = registerStoneSet("zigilaban_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, ZIGILABAN_BRICKS.base);
    //public static SimpleBlockSet MOSSY_ZIGILABAN_TILES = registerStoneSet("mossy_zigilaban_tiles", COBBLED_ZIGILABAN.base.getHardness(), COBBLED_ZIGILABAN.base.getBlastResistance(), ZIGILABAN_TILES.base);
    //public static SimpleBlockSet CRACKED_ZIGILABAN_TILES = registerStoneSet("cracked_zigilaban_tiles", COBBLED_ZIGILABAN.base.getHardness(), COBBLED_ZIGILABAN.base.getBlastResistance(), ZIGILABAN_TILES.base);

    public static SimpleBlockSetMain IZHERABAN = registerMainStoneSet("izheraban", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_IZHERABAN = registerStoneSet("smooth_izheraban", IZHERABAN.base.getHardness(), IZHERABAN.base.getBlastResistance(), IZHERABAN.base);
    public static SimpleBlockSet MOSSY_SMOOTH_IZHERABAN = registerStoneSet("mossy_smooth_izheraban", SMOOTH_IZHERABAN.base.getHardness(), SMOOTH_IZHERABAN.base.getBlastResistance(), SMOOTH_IZHERABAN.base);
    public static SimpleBlockSet CRACKED_SMOOTH_IZHERABAN = registerStoneSet("cracked_smooth_izheraban", SMOOTH_IZHERABAN.base.getHardness(), SMOOTH_IZHERABAN.base.getBlastResistance(), SMOOTH_IZHERABAN.base);
    public static SimpleBlockSet COBBLED_IZHERABAN = registerStoneSet("cobbled_izheraban", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, IZHERABAN.base);
    public static SimpleBlockSet MOSSY_COBBLED_IZHERABAN = registerStoneSet("mossy_cobbled_izheraban", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_IZHERABAN.base);
    public static SimpleBlockSet POLISHED_IZHERABAN = registerStoneSet("polished_izheraban", IZHERABAN.base.getHardness(), IZHERABAN.base.getBlastResistance(), IZHERABAN.base);
    public static SimpleBlockSet MOSSY_POLISHED_IZHERABAN = registerStoneSet("mossy_polished_izheraban", IZHERABAN.base.getHardness(), IZHERABAN.base.getBlastResistance(), POLISHED_IZHERABAN.base);
    public static SimpleBlockSet CRACKED_POLISHED_IZHERABAN = registerStoneSet("cracked_polished_izheraban", IZHERABAN.base.getHardness(), IZHERABAN.base.getBlastResistance(), POLISHED_IZHERABAN.base);
    public static SimpleBlockSet IZHERABAN_BRICKS = registerStoneSet("izheraban_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_IZHERABAN.base);
    public static SimpleBlockSet MOSSY_IZHERABAN_BRICKS = registerStoneSet("mossy_izheraban_bricks", COBBLED_IZHERABAN.base.getHardness(), COBBLED_IZHERABAN.base.getBlastResistance(), IZHERABAN_BRICKS.base);
    public static SimpleBlockSet CRACKED_IZHERABAN_BRICKS = registerStoneSet("cracked_izheraban_bricks", COBBLED_IZHERABAN.base.getHardness(), COBBLED_IZHERABAN.base.getBlastResistance(), IZHERABAN_BRICKS.base);
    public static SimpleBlockSet IZHERABAN_TILES = registerStoneSet("izheraban_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, IZHERABAN_BRICKS.base);
    public static SimpleBlockSet MOSSY_IZHERABAN_TILES = registerStoneSet("mossy_izheraban_tiles", COBBLED_IZHERABAN.base.getHardness(), COBBLED_IZHERABAN.base.getBlastResistance(), IZHERABAN_TILES.base);
    public static SimpleBlockSet CRACKED_IZHERABAN_TILES = registerStoneSet("cracked_izheraban_tiles", COBBLED_IZHERABAN.base.getHardness(), COBBLED_IZHERABAN.base.getBlastResistance(), IZHERABAN_TILES.base);

    public static SimpleBlockSet IZHERABAN_BRICKWORK = registerStoneSet("izheraban_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimplePillarBlockSet OLD_IZHERABAN = registerStonePillarSet("old_izheraban", IZHERABAN.base.getHardness(), IZHERABAN.base.getBlastResistance(), COBBLED_IZHERABAN.base);

    public static SimpleBlockSetMain SCHIST = registerMainStoneSet("schist", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_SCHIST = registerStoneSet("cobbled_schist", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, SCHIST.base);
    //public static SimpleBlockSet MOSSY_FROZEN_COBBLESTONE = registerStoneSet("mossy_frozen_cobblestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, FROZEN_STONE.base);
    public static SimpleBlockSet POLISHED_SCHIST = registerStoneSet("polished_schist", SCHIST.base.getHardness(), SCHIST.base.getBlastResistance(), SCHIST.base);
    //public static SimpleBlockSet MOSSY_POLISHED_FROZEN_STONE = registerStoneSet("mossy_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_FROZEN_STONE = registerStoneSet("cracked_polished_frozen_stone", FROZEN_STONE.base.getHardness(), FROZEN_STONE.base.getBlastResistance(), POLISHED_FROZEN_STONE.base);
    public static SimpleBlockSet SCHIST_BRICKS = registerStoneSet("schist_bricks", COBBLED_SCHIST.base.getHardness(), COBBLED_SCHIST.base.getBlastResistance(), POLISHED_SCHIST.base);
    //public static SimpleBlockSet MOSSY_FROZEN_STONE_BRICKS = registerStoneSet("mossy_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    //public static SimpleBlockSet CRACKED_FROZEN_STONE_BRICKS = registerStoneSet("cracked_frozen_stone_bricks", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_BRICKS.base);
    public static SimpleBlockSet SCHIST_TILES = registerStoneSet("schist_tiles", COBBLED_SCHIST.base.getHardness(), COBBLED_SCHIST.base.getBlastResistance(), SCHIST_BRICKS.base);
    //public static SimpleBlockSet MOSSY_FROZEN_TILES = registerStoneSet("mossy_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);
    //public static SimpleBlockSet CRACKED_FROZEN_TILES = registerStoneSet("cracked_frozen_tiles", FROZEN_COBBLESTONE.base.getHardness(), FROZEN_COBBLESTONE.base.getBlastResistance(), FROZEN_TILES.base);

    public static SimpleBlockSetMain LIMESTONE = registerMainStoneSet("limestone", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_LIMESTONE = registerStoneSet("smooth_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), LIMESTONE.base);
    public static SimpleBlockSet MOSSY_SMOOTH_LIMESTONE = registerStoneSet("mossy_smooth_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), SMOOTH_LIMESTONE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_LIMESTONE = registerStoneSet("cracked_smooth_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), SMOOTH_LIMESTONE.base);
    public static SimpleBlockSet COBBLED_LIMESTONE = registerStoneSet("cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, LIMESTONE.base);
    public static SimpleBlockSet MOSSY_COBBLED_LIMESTONE = registerStoneSet("mossy_cobbled_limestone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_LIMESTONE.base);
    public static SimpleBlockSet POLISHED_LIMESTONE = registerStoneSet("polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), LIMESTONE.base);
    public static SimpleBlockSet MOSSY_POLISHED_LIMESTONE = registerStoneSet("mossy_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet CRACKED_POLISHED_LIMESTONE = registerStoneSet("cracked_polished_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet LIMESTONE_BRICKS = registerStoneSet("limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), POLISHED_LIMESTONE.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_BRICKS = registerStoneSet("mossy_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_BRICKS = registerStoneSet("cracked_limestone_bricks", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet LIMESTONE_TILES = registerStoneSet("limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_BRICKS.base);
    public static SimpleBlockSet MOSSY_LIMESTONE_TILES = registerStoneSet("mossy_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);
    public static SimpleBlockSet CRACKED_LIMESTONE_TILES = registerStoneSet("cracked_limestone_tiles", COBBLED_LIMESTONE.base.getHardness(), COBBLED_LIMESTONE.base.getBlastResistance(), LIMESTONE_TILES.base);

    public static SimpleBlockSet LIMESTONE_BRICKWORK = registerStoneSet("limestone_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimplePillarBlockSet OLD_LIMESTONE = registerStonePillarSet("old_limestone", LIMESTONE.base.getHardness(), LIMESTONE.base.getBlastResistance(), COBBLED_LIMESTONE.base);

    public static SimpleBlockSetMain GALONN = registerMainStoneSet("galonn", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_GALONN = registerStoneSet("smooth_galonn", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), GALONN.base);
    public static SimpleBlockSet MOSSY_SMOOTH_GALONN = registerStoneSet("mossy_smooth_galonn", SMOOTH_GALONN.base.getHardness(), SMOOTH_GALONN.base.getBlastResistance(), SMOOTH_GALONN.base);
    public static SimpleBlockSet CRACKED_SMOOTH_GALONN = registerStoneSet("cracked_smooth_galonn", SMOOTH_GALONN.base.getHardness(), SMOOTH_GALONN.base.getBlastResistance(), SMOOTH_GALONN.base);
    public static SimpleBlockSet COBBLED_GALONN = registerStoneSet("cobbled_galonn", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GALONN.base);
    public static SimpleBlockSet MOSSY_COBBLED_GALONN = registerStoneSet("mossy_cobbled_galonn", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GALONN.base);
    public static SimpleBlockSet POLISHED_GALONN = registerStoneSet("polished_galonn", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), GALONN.base);
    public static SimpleBlockSet MOSSY_POLISHED_GALONN = registerStoneSet("mossy_polished_galonn", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), POLISHED_GALONN.base);
    public static SimpleBlockSet CRACKED_POLISHED_GALONN = registerStoneSet("cracked_polished_galonn", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), POLISHED_GALONN.base);
    public static SimpleBlockSet GALONN_BRICKS = registerStoneSet("galonn_bricks", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), POLISHED_GALONN.base);
    public static SimpleBlockSet MOSSY_GALONN_BRICKS = registerStoneSet("mossy_galonn_bricks", COBBLED_GALONN.base.getHardness(), COBBLED_GALONN.base.getBlastResistance(), GALONN_BRICKS.base);
    public static SimpleBlockSet CRACKED_GALONN_BRICKS = registerStoneSet("cracked_galonn_bricks", COBBLED_GALONN.base.getHardness(), COBBLED_GALONN.base.getBlastResistance(), GALONN_BRICKS.base);
    public static SimpleBlockSet GALONN_TILES = registerStoneSet("galonn_tiles", COBBLED_GALONN.base.getHardness(), COBBLED_GALONN.base.getBlastResistance(), GALONN_BRICKS.base);
    public static SimpleBlockSet MOSSY_GALONN_TILES = registerStoneSet("mossy_galonn_tiles", COBBLED_GALONN.base.getHardness(), COBBLED_GALONN.base.getBlastResistance(), GALONN_TILES.base);
    public static SimpleBlockSet CRACKED_GALONN_TILES = registerStoneSet("cracked_galonn_tiles", COBBLED_GALONN.base.getHardness(), COBBLED_GALONN.base.getBlastResistance(), GALONN_TILES.base);

    public static SimpleBlockSet GALONN_BRICKWORK = registerStoneSet("galonn_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimplePillarBlockSet OLD_GALONN = registerStonePillarSet("old_galonn", GALONN.base.getHardness(), GALONN.base.getBlastResistance(), COBBLED_GALONN.base);

    public static SimpleBlockSetMain GABBRO = registerMainStoneSet("gabbro", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    //public static SimpleBlockSet COBBLED_GABBRO = registerStoneSet("cobbled_gabbro", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GABBRO.base);
    //public static SimpleBlockSet MOSSY_COBBLED_GABBRO = registerStoneSet("mossy_cobbled_gabbro", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GABBRO.base);
    public static SimpleBlockSet POLISHED_GABBRO = registerStoneSet("polished_gabbro", GABBRO.base.getHardness(), GABBRO.base.getBlastResistance(), GABBRO.base);
    //public static SimpleBlockSet MOSSY_POLISHED_GABBRO = registerStoneSet("mossy_polished_gabbro", GABBRO.base.getHardness(), GABBRO.base.getBlastResistance(), POLISHED_GABBRO.base);
    //public static SimpleBlockSet CRACKED_POLISHED_GABBRO = registerStoneSet("cracked_polished_gabbro", GABBRO.base.getHardness(), GABBRO.base.getBlastResistance(), POLISHED_GABBRO.base);
    public static SimpleBlockSet GABBRO_BRICKS = registerStoneSet("gabbro_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, POLISHED_GABBRO.base);
    //public static SimpleBlockSet MOSSY_GABBRO_BRICKS = registerStoneSet("mossy_gabbro_bricks", COBBLED_GABBRO.base.getHardness(), COBBLED_GABBRO.base.getBlastResistance(), GABBRO_BRICKS.base);
    //public static SimpleBlockSet CRACKED_GABBRO_BRICKS = registerStoneSet("cracked_gabbro_bricks", COBBLED_GABBRO.base.getHardness(), COBBLED_GABBRO.base.getBlastResistance(), GABBRO_BRICKS.base);
    //public static SimpleBlockSet GABBRO_TILES = registerStoneSet("gabbro_tiles", COBBLED_GABBRO.base.getHardness(), COBBLED_GABBRO.base.getBlastResistance(), GABBRO_BRICKS.base);
    //public static SimpleBlockSet MOSSY_GABBRO_TILES = registerStoneSet("mossy_gabbro_tiles", COBBLED_GABBRO.base.getHardness(), COBBLED_GABBRO.base.getBlastResistance(), GABBRO_TILES.base);
    //public static SimpleBlockSet CRACKED_GABBRO_TILES = registerStoneSet("cracked_gabbro_tiles", COBBLED_GABBRO.base.getHardness(), COBBLED_GABBRO.base.getBlastResistance(), GABBRO_TILES.base);

    public static SimpleBlockSet GABBRO_BRICKWORK = registerStoneSet("gabbro_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);
    
    public static SimpleBlockSetMain SLATE = registerMainStoneSet("slate", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_SLATE = registerStoneSet("cobbled_slate", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, SLATE.base);
    //public static SimpleBlockSet MOSSY_COBBLED_SLATE = registerStoneSet("mossy_cobbled_slate", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_SLATE.base);
    public static SimpleBlockSet POLISHED_SLATE = registerStoneSet("polished_slate", SLATE.base.getHardness(), SLATE.base.getBlastResistance(), SLATE.base);
    //public static SimpleBlockSet MOSSY_POLISHED_SLATE = registerStoneSet("mossy_polished_slate", SLATE.base.getHardness(), SLATE.base.getBlastResistance(), POLISHED_SLATE.base);
    //public static SimpleBlockSet CRACKED_POLISHED_SLATE = registerStoneSet("cracked_polished_slate", SLATE.base.getHardness(), SLATE.base.getBlastResistance(), POLISHED_SLATE.base);
    public static SimpleBlockSet SLATE_BRICKS = registerStoneSet("slate_bricks", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), POLISHED_SLATE.base);
    //public static SimpleBlockSet MOSSY_SLATE_BRICKS = registerStoneSet("mossy_slate_bricks", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), SLATE_BRICKS.base);
    //public static SimpleBlockSet CRACKED_SLATE_BRICKS = registerStoneSet("cracked_slate_bricks", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), SLATE_BRICKS.base);
    //public static SimpleBlockSet SLATE_TILES = registerStoneSet("slate_tiles", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), SLATE_BRICKS.base);
    //public static SimpleBlockSet MOSSY_SLATE_TILES = registerStoneSet("mossy_slate_tiles", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), SLATE_TILES.base);
    //public static SimpleBlockSet CRACKED_SLATE_TILES = registerStoneSet("cracked_slate_tiles", COBBLED_SLATE.base.getHardness(), COBBLED_SLATE.base.getBlastResistance(), SLATE_TILES.base);
    
    public static SimpleBlockSet POLISHED_STONE = registerStoneSet("polished_stone", Blocks.STONE.getHardness(), Blocks.STONE.getBlastResistance(), Blocks.STONE);
    public static SimpleBlockSet MOSSY_POLISHED_STONE = registerStoneSet("mossy_polished_stone", Blocks.STONE.getHardness(), Blocks.STONE.getBlastResistance(), POLISHED_STONE.base);
    public static SimpleBlockSet CRACKED_POLISHED_STONE = registerStoneSet("cracked_polished_stone", Blocks.STONE.getHardness(), Blocks.STONE.getBlastResistance(), POLISHED_STONE.base);
    public static SimpleBlockSet STONE_TILES = registerStoneSet("stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.STONE_BRICKS);
    public static SimpleBlockSet MOSSY_STONE_TILES = registerStoneSet("mossy_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet CRACKED_STONE_TILES = registerStoneSet("cracked_stone_tiles", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), STONE_TILES.base);
    public static SimpleBlockSet MOSSY_SMOOTH_STONE = registerStoneSet("mossy_smooth_stone", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.SMOOTH_STONE);
    public static SimpleBlockSet CRACKED_SMOOTH_STONE = registerStoneSet("cracked_smooth_stone", Blocks.SMOOTH_STONE.getHardness(), Blocks.SMOOTH_STONE.getBlastResistance(), Blocks.SMOOTH_STONE);
    
    public static SimpleBlockSet STONE_BRICKWORK = registerStoneSet("stone_brickwork", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), null);
    
    public static SimpleBlockSet OLD_STONE = registerStoneSet("old_stone", Blocks.STONE.getHardness(), Blocks.STONE.getBlastResistance(), Blocks.COBBLESTONE);

    public static SimpleBlockSet MOSSY_COBBLED_DEEPSLATE = registerStoneSet("mossy_cobbled_deepslate", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.COBBLED_DEEPSLATE);
    public static SimpleBlockSet SMOOTH_DEEPSLATE = registerStoneSet("smooth_deepslate", Blocks.DEEPSLATE.getHardness(), Blocks.DEEPSLATE.getBlastResistance(), Blocks.DEEPSLATE);
    public static SimpleBlockSet MOSSY_SMOOTH_DEEPSLATE = registerStoneSet("mossy_smooth_deepslate", SMOOTH_DEEPSLATE.base.getHardness(), SMOOTH_DEEPSLATE.base.getBlastResistance(), SMOOTH_DEEPSLATE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_DEEPSLATE = registerStoneSet("cracked_smooth_deepslate", SMOOTH_DEEPSLATE.base.getHardness(), SMOOTH_DEEPSLATE.base.getBlastResistance(), SMOOTH_DEEPSLATE.base);
    public static SimpleBlockSet MOSSY_DEEPSLATE_BRICKS = registerStoneSet("mossy_deepslate_bricks", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_BRICKS);
    public static SimpleBlockSet MOSSY_POLISHED_DEEPSLATE = registerStoneSet("mossy_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet CRACKED_POLISHED_DEEPSLATE = registerStoneSet("cracked_polished_deepslate", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.POLISHED_DEEPSLATE);
    public static SimpleBlockSet MOSSY_DEEPSLATE_TILES = registerStoneSet("mossy_deepslate_tiles", Blocks.DEEPSLATE_BRICKS.getHardness(), Blocks.DEEPSLATE_BRICKS.getBlastResistance(), Blocks.DEEPSLATE_TILES);

    public static SimpleBlockSet DEEPSLATE_BRICKWORK = registerStoneSet("deepslate_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_DEEPSLATE = registerStoneSet("old_deepslate", Blocks.DEEPSLATE.getHardness(), Blocks.DEEPSLATE.getBlastResistance(), Blocks.COBBLED_DEEPSLATE);

    public static SimpleBlockSetMain BLUE_TUFF = registerMainStoneSet("blue_tuff", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_BLUE_TUFF = registerStoneSet("cobbled_blue_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, BLUE_TUFF.base);
    public static SimpleBlockSet MOSSY_COBBLED_BLUE_TUFF = registerStoneSet("mossy_cobbled_blue_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_BLUE_TUFF.base);
    public static SimpleBlockSet POLISHED_BLUE_TUFF = registerStoneSet("polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF.base);
    public static SimpleBlockSet MOSSY_POLISHED_BLUE_TUFF = registerStoneSet("mossy_polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    public static SimpleBlockSet CRACKED_POLISHED_BLUE_TUFF = registerStoneSet("cracked_polished_blue_tuff", BLUE_TUFF.base.getHardness(), BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    public static SimpleBlockSet BLUE_TUFF_BRICKS = registerStoneSet("blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), POLISHED_BLUE_TUFF.base);
    public static SimpleBlockSet MOSSY_BLUE_TUFF_BRICKS = registerStoneSet("mossy_blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    public static SimpleBlockSet CRACKED_BLUE_TUFF_BRICKS = registerStoneSet("cracked_blue_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    public static SimpleBlockSet BLUE_TUFF_TILES = registerStoneSet("blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_BRICKS.base);
    public static SimpleBlockSet MOSSY_BLUE_TUFF_TILES = registerStoneSet("mossy_blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_TILES.base);
    public static SimpleBlockSet CRACKED_BLUE_TUFF_TILES = registerStoneSet("cracked_blue_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), BLUE_TUFF_TILES.base);

    public static SimpleBlockSetMain GREEN_TUFF = registerMainStoneSet("green_tuff", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSetMain GILDED_GREEN_TUFF = registerMainStoneSet("gilded_green_tuff", STONE_HARDNESS, STONE_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_GREEN_TUFF = registerStoneSet("smooth_green_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GREEN_TUFF.base);
    public static SimpleBlockSet CRACKED_SMOOTH_GREEN_TUFF = registerStoneSet("cracked_smooth_green_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, SMOOTH_GREEN_TUFF.base);
    //public static SimpleBlockSet COBBLED_GREEN_TUFF = registerStoneSet("cobbled_green_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, GREEN_TUFF.base);
    //public static SimpleBlockSet MOSSY_COBBLED_GREEN_TUFF = registerStoneSet("mossy_cobbled_green_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GREEN_TUFF.base);
    public static SimpleBlockSet POLISHED_GREEN_TUFF = registerStoneSet("polished_green_tuff", GREEN_TUFF.base.getHardness(), GREEN_TUFF.base.getBlastResistance(), GREEN_TUFF.base);
    //public static SimpleBlockSet MOSSY_POLISHED_GREEN_TUFF = registerStoneSet("mossy_polished_green_tuff", GREEN_TUFF.base.getHardness(), GREEN_TUFF.base.getBlastResistance(), POLISHED_GREEN_TUFF.base);
    public static SimpleBlockSet CRACKED_POLISHED_GREEN_TUFF = registerStoneSet("cracked_polished_green_tuff", GREEN_TUFF.base.getHardness(), GREEN_TUFF.base.getBlastResistance(), POLISHED_GREEN_TUFF.base);
    public static SimpleBlockSet GREEN_TUFF_BRICKS = registerStoneSet("green_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), POLISHED_GREEN_TUFF.base);
    //public static SimpleBlockSet MOSSY_GREEN_TUFF_BRICKS = registerStoneSet("mossy_green_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), GREEN_TUFF_BRICKS.base);
    public static SimpleBlockSet CRACKED_GREEN_TUFF_BRICKS = registerStoneSet("cracked_green_tuff_bricks", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), GREEN_TUFF_BRICKS.base);
    public static SimpleBlockSet GREEN_TUFF_TILES = registerStoneSet("green_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), GREEN_TUFF_BRICKS.base);
    //public static SimpleBlockSet MOSSY_GREEN_TUFF_TILES = registerStoneSet("mossy_green_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), GREEN_TUFF_TILES.base);
    public static SimpleBlockSet CRACKED_GREEN_TUFF_TILES = registerStoneSet("cracked_green_tuff_tiles", COBBLED_BLUE_TUFF.base.getHardness(), COBBLED_BLUE_TUFF.base.getBlastResistance(), GREEN_TUFF_TILES.base);

    public static SimpleBlockSet SMOOTH_ANDESITE = registerStoneSet("smooth_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.ANDESITE);
    public static SimpleBlockSet MOSSY_SMOOTH_ANDESITE = registerStoneSet("mossy_smooth_andesite", SMOOTH_ANDESITE.base.getHardness(), SMOOTH_ANDESITE.base.getBlastResistance(), SMOOTH_ANDESITE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_ANDESITE = registerStoneSet("cracked_smooth_andesite", SMOOTH_ANDESITE.base.getHardness(), SMOOTH_ANDESITE.base.getBlastResistance(), SMOOTH_ANDESITE.base);
    public static SimpleBlockSet COBBLED_ANDESITE = registerStoneSet("cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.ANDESITE);
    public static SimpleBlockSet MOSSY_COBBLED_ANDESITE = registerStoneSet("mossy_cobbled_andesite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_ANDESITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_ANDESITE = registerStoneSet("mossy_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet CRACKED_POLISHED_ANDESITE = registerStoneSet("cracked_polished_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet ANDESITE_BRICKS = registerStoneSet("andesite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet MOSSY_ANDESITE_BRICKS = registerStoneSet("mossy_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_ANDESITE_BRICKS = registerStoneSet("cracked_andesite_bricks", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet ANDESITE_TILES = registerStoneSet("andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_ANDESITE_TILES = registerStoneSet("mossy_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);
    public static SimpleBlockSet CRACKED_ANDESITE_TILES = registerStoneSet("cracked_andesite_tiles", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), ANDESITE_TILES.base);

    public static SimpleBlockSet ANDESITE_BRICKWORK = registerStoneSet("andesite_brickwork", ANDESITE_BRICKS.base.getHardness(), ANDESITE_BRICKS.base.getBlastResistance(), null);
    
    public static SimpleBlockSet OLD_ANDESITE = registerStoneSet("old_andesite", Blocks.ANDESITE.getHardness(), Blocks.ANDESITE.getBlastResistance(), COBBLED_ANDESITE.base);

    public static SimpleBlockSet SMOOTH_GRANITE = registerStoneSet("smooth_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.GRANITE);
    public static SimpleBlockSet MOSSY_SMOOTH_GRANITE = registerStoneSet("mossy_smooth_granite", SMOOTH_GRANITE.base.getHardness(), SMOOTH_GRANITE.base.getBlastResistance(), SMOOTH_GRANITE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_GRANITE = registerStoneSet("cracked_smooth_granite", SMOOTH_GRANITE.base.getHardness(), SMOOTH_GRANITE.base.getBlastResistance(), SMOOTH_GRANITE.base);
    public static SimpleBlockSet COBBLED_GRANITE = registerStoneSet("cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.GRANITE);
    public static SimpleBlockSet MOSSY_COBBLED_GRANITE = registerStoneSet("mossy_cobbled_granite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_GRANITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_GRANITE = registerStoneSet("mossy_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet CRACKED_POLISHED_GRANITE = registerStoneSet("cracked_polished_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet GRANITE_BRICKS = registerStoneSet("granite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet MOSSY_GRANITE_BRICKS = registerStoneSet("mossy_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_GRANITE_BRICKS = registerStoneSet("cracked_granite_bricks", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet GRANITE_TILES = registerStoneSet("granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_GRANITE_TILES = registerStoneSet("mossy_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);
    public static SimpleBlockSet CRACKED_GRANITE_TILES = registerStoneSet("cracked_granite_tiles", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), GRANITE_TILES.base);
    
    public static SimpleBlockSet GRANITE_BRICKWORK = registerStoneSet("granite_brickwork", GRANITE_BRICKS.base.getHardness(), GRANITE_BRICKS.base.getBlastResistance(), null);

    public static SimpleBlockSet OLD_GRANITE = registerStoneSet("old_granite", Blocks.GRANITE.getHardness(), Blocks.GRANITE.getBlastResistance(), COBBLED_GRANITE.base);
    
    public static SimpleBlockSet SMOOTH_DIORITE = registerStoneSet("smooth_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_SMOOTH_DIORITE = registerStoneSet("mossy_smooth_diorite", SMOOTH_DIORITE.base.getHardness(), SMOOTH_DIORITE.base.getBlastResistance(), SMOOTH_DIORITE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_DIORITE = registerStoneSet("cracked_smooth_diorite", SMOOTH_DIORITE.base.getHardness(), SMOOTH_DIORITE.base.getBlastResistance(), SMOOTH_DIORITE.base);
    public static SimpleBlockSet COBBLED_DIORITE = registerStoneSet("cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.DIORITE);
    public static SimpleBlockSet MOSSY_COBBLED_DIORITE = registerStoneSet("mossy_cobbled_diorite", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_DIORITE.base);
    public static SimpleBlockSet MOSSY_POLISHED_DIORITE = registerStoneSet("mossy_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet CRACKED_POLISHED_DIORITE = registerStoneSet("cracked_polished_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet DIORITE_BRICKS = registerStoneSet("diorite_bricks", Blocks.STONE_BRICKS.getHardness(), Blocks.STONE_BRICKS.getBlastResistance(), Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet MOSSY_DIORITE_BRICKS = registerStoneSet("mossy_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet CRACKED_DIORITE_BRICKS = registerStoneSet("cracked_diorite_bricks", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet DIORITE_TILES = registerStoneSet("diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_BRICKS.base);
    public static SimpleBlockSet MOSSY_DIORITE_TILES = registerStoneSet("mossy_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);
    public static SimpleBlockSet CRACKED_DIORITE_TILES = registerStoneSet("cracked_diorite_tiles", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), DIORITE_TILES.base);

    public static SimpleBlockSet DIORITE_BRICKWORK = registerStoneSet("diorite_brickwork", DIORITE_BRICKS.base.getHardness(), DIORITE_BRICKS.base.getBlastResistance(), null);

    public static SimpleBlockSet OLD_DIORITE = registerStoneSet("old_diorite", Blocks.DIORITE.getHardness(), Blocks.DIORITE.getBlastResistance(), COBBLED_DIORITE.base);
    
    public static SimpleBlockSet SMOOTH_TUFF = registerStoneSet("smooth_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), Blocks.TUFF);
    public static SimpleBlockSet MOSSY_SMOOTH_TUFF = registerStoneSet("mossy_smooth_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), SMOOTH_TUFF.base);
    public static SimpleBlockSet CRACKED_SMOOTH_TUFF = registerStoneSet("cracked_smooth_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), SMOOTH_TUFF.base);
    public static SimpleBlockSet COBBLED_TUFF = registerStoneSet("cobbled_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.TUFF);
    public static SimpleBlockSet MOSSY_COBBLED_TUFF = registerStoneSet("mossy_cobbled_tuff", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_TUFF.base);
    public static SimpleBlockSet MOSSY_POLISHED_TUFF = registerStoneSet("mossy_polished_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), Blocks.POLISHED_TUFF);
    public static SimpleBlockSet CRACKED_POLISHED_TUFF = registerStoneSet("cracked_polished_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), Blocks.POLISHED_TUFF);
    public static SimpleBlockSet MOSSY_TUFF_BRICKS = registerStoneSet("mossy_tuff_bricks", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.TUFF_BRICKS);
    public static SimpleBlockSet CRACKED_TUFF_BRICKS = registerStoneSet("cracked_tuff_bricks", COBBLE_HARDNESS, Blocks.TUFF_BRICKS.getBlastResistance(), Blocks.TUFF_BRICKS);
    public static SimpleBlockSet TUFF_TILES = registerStoneSet("tuff_tiles", Blocks.TUFF_BRICKS.getHardness(), Blocks.TUFF_BRICKS.getBlastResistance(), Blocks.TUFF_BRICKS);
    public static SimpleBlockSet MOSSY_TUFF_TILES = registerStoneSet("mossy_tuff_tiles", Blocks.TUFF_BRICKS.getHardness(), Blocks.TUFF_BRICKS.getBlastResistance(), TUFF_TILES.base);
    public static SimpleBlockSet CRACKED_TUFF_TILES = registerStoneSet("cracked_tuff_tiles", Blocks.TUFF_BRICKS.getHardness(), Blocks.TUFF_BRICKS.getBlastResistance(), TUFF_TILES.base);

    public static SimpleBlockSet TUFF_BRICKWORK = registerStoneSet("tuff_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_TUFF = registerStoneSet("old_tuff", Blocks.TUFF.getHardness(), Blocks.TUFF.getBlastResistance(), COBBLED_TUFF.base);

    public static SimpleBlockSet SMOOTH_BLACKSTONE = registerStoneSet("smooth_blackstone", Blocks.BLACKSTONE.getHardness(), Blocks.BLACKSTONE.getBlastResistance(), Blocks.BLACKSTONE);
    public static SimpleBlockSet MOSSY_SMOOTH_BLACKSTONE = registerStoneSet("mossy_smooth_blackstone", SMOOTH_BLACKSTONE.base.getHardness(), SMOOTH_BLACKSTONE.base.getBlastResistance(), SMOOTH_BLACKSTONE.base);
    public static SimpleBlockSet CRACKED_SMOOTH_BLACKSTONE = registerStoneSet("cracked_smooth_blackstone", SMOOTH_BLACKSTONE.base.getHardness(), SMOOTH_BLACKSTONE.base.getBlastResistance(), SMOOTH_BLACKSTONE.base);
    public static SimpleBlockSet COBBLED_BLACKSTONE = registerStoneSet("cobbled_blackstone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.BLACKSTONE);
    public static SimpleBlockSet MOSSY_COBBLED_BLACKSTONE = registerStoneSet("mossy_cobbled_blackstone", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, COBBLED_BLACKSTONE.base);
    public static SimpleBlockSet MOSSY_POLISHED_BLACKSTONE = registerStoneSet("mossy_polished_blackstone", Blocks.BLACKSTONE.getHardness(), Blocks.BLACKSTONE.getBlastResistance(), Blocks.POLISHED_BLACKSTONE);
    public static SimpleBlockSet CRACKED_POLISHED_BLACKSTONE = registerStoneSet("cracked_polished_blackstone", Blocks.BLACKSTONE.getHardness(), Blocks.BLACKSTONE.getBlastResistance(), Blocks.POLISHED_BLACKSTONE);
    public static SimpleBlockSet MOSSY_POLISHED_BLACKSTONE_BRICKS = registerStoneSet("mossy_polished_blackstone_bricks", COBBLED_BLACKSTONE.base.getHardness(), COBBLED_BLACKSTONE.base.getBlastResistance(), Blocks.POLISHED_BLACKSTONE_BRICKS);
    public static SimpleBlockSet BLACKSTONE_TILES = registerStoneSet("blackstone_tiles", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.POLISHED_BLACKSTONE_BRICKS);
    public static SimpleBlockSet MOSSY_BLACKSTONE_TILES = registerStoneSet("mossy_blackstone_tiles", COBBLED_BLACKSTONE.base.getHardness(), COBBLED_BLACKSTONE.base.getBlastResistance(), BLACKSTONE_TILES.base);
    public static SimpleBlockSet CRACKED_BLACKSTONE_TILES = registerStoneSet("cracked_blackstone_tiles", COBBLED_BLACKSTONE.base.getHardness(), COBBLED_BLACKSTONE.base.getBlastResistance(), BLACKSTONE_TILES.base);

    public static SimpleBlockSet BLACKSTONE_BRICKWORK = registerStoneSet("blackstone_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_BLACKSTONE = registerStoneSet("old_blackstone", Blocks.BLACKSTONE.getHardness(), Blocks.BLACKSTONE.getBlastResistance(), COBBLED_BLACKSTONE.base);

    public static SimpleBlockSet MOSSY_SMOOTH_BASALT = registerStoneSet("mossy_smooth_basalt", Blocks.SMOOTH_BASALT.getHardness(), Blocks.SMOOTH_BASALT.getBlastResistance(), Blocks.SMOOTH_BASALT);
    public static SimpleBlockSet CRACKED_SMOOTH_BASALT = registerStoneSet("cracked_smooth_basalt", Blocks.SMOOTH_BASALT.getHardness(), Blocks.SMOOTH_BASALT.getBlastResistance(), Blocks.SMOOTH_BASALT);
    public static SimpleBlockSet COBBLED_BASALT = registerStoneSet("cobbled_basalt", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, Blocks.BASALT);
    public static SimpleBlockSet MOSSY_COBBLED_BASALT = registerStoneSet("mossy_cobbled_basalt", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), COBBLED_BASALT.base);
    public static SimplePillarBlockSet MOSSY_POLISHED_BASALT = registerStonePillarSet("mossy_polished_basalt", Blocks.POLISHED_BASALT.getHardness(), Blocks.POLISHED_BASALT.getBlastResistance(), Blocks.POLISHED_BASALT);
    public static SimplePillarBlockSet CRACKED_POLISHED_BASALT = registerStonePillarSet("cracked_polished_basalt", STONE_HARDNESS, STONE_BLAST_RESISTANCE, Blocks.POLISHED_BASALT);
    public static SimpleBlockSet BASALT_BRICKS = registerStoneSet("basalt_bricks", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), Blocks.POLISHED_BASALT);
    public static SimpleBlockSet MOSSY_BASALT_BRICKS = registerStoneSet("mossy_basalt_bricks", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet CRACKED_BASALT_BRICKS = registerStoneSet("cracked_basalt_bricks", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet BASALT_TILES = registerStoneSet("basalt_tiles", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), BASALT_BRICKS.base);
    public static SimpleBlockSet MOSSY_BASALT_TILES = registerStoneSet("mossy_basalt_tiles", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), BASALT_TILES.base);
    public static SimpleBlockSet CRACKED_BASALT_TILES = registerStoneSet("cracked_basalt_tiles", COBBLED_BASALT.base.getHardness(), COBBLED_BASALT.base.getBlastResistance(), BASALT_TILES.base);

    public static SimpleBlockSet BASALT_BRICKWORK = registerStoneSet("basalt_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_BASALT = registerStoneSet("old_basalt", Blocks.BASALT.getHardness(), Blocks.BASALT.getBlastResistance(), COBBLED_BASALT.base);

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

    public static SimpleBlockSetMain NURGON = registerMainStoneSet("nurgon", NURGON_HARDNESS, NURGON_BLAST_RESISTANCE, null);
    public static SimpleBlockSet COBBLED_NURGON = registerStoneSet("cobbled_nurgon", NURGON_BRICKS_HARDNESS, NURGON_BRICKS_BLAST_RESISTANCE, NURGON.base);
    //public static SimpleBlockSet MOSSY_COBBLED_NURGON = registerStoneSet("mossy_cobbled_nurgon", COBBLE_HARDNESS, COBBLE_BLAST_RESISTANCE, NURGON.base);
    public static SimpleBlockSet POLISHED_NURGON = registerStoneSet("polished_nurgon", NURGON.base.getHardness(), NURGON.base.getBlastResistance(), NURGON.base);
    //public static SimpleBlockSet MOSSY_POLISHED_NURGON = registerStoneSet("mossy_polished_nurgon", NURGON.base.getHardness(), NURGON.base.getBlastResistance(), POLISHED_NURGON.base);
    //public static SimpleBlockSet CRACKED_POLISHED_NURGON = registerStoneSet("cracked_polished_nurgon", NURGON.base.getHardness(), NURGON.base.getBlastResistance(), POLISHED_NURGON.base);
    public static SimpleBlockSet NURGON_BRICKS = registerStoneSet("nurgon_bricks", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), POLISHED_NURGON.base);
    //public static SimpleBlockSet MOSSY_NURGON_BRICKS = registerStoneSet("mossy_nurgon_bricks", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), NURGON_BRICKS.base);
    //public static SimpleBlockSet CRACKED_NURGON_BRICKS = registerStoneSet("cracked_nurgon_bricks", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), NURGON_BRICKS.base);
    public static SimpleBlockSet NURGON_TILES = registerStoneSet("nurgon_tiles", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), NURGON_BRICKS.base);
    //public static SimpleBlockSet MOSSY_NURGON_TILES = registerStoneSet("mossy_nurgon_tiles", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), NURGON_TILES.base);
    //public static SimpleBlockSet CRACKED_NURGON_TILES = registerStoneSet("cracked_nurgon_tiles", COBBLED_NURGON.base.getHardness(), COBBLED_NURGON.base.getBlastResistance(), NURGON_TILES.base);
    
    public static SimpleBlockSetMain MEDGON = registerMainStoneSet("medgon", MEDGON_HARDNESS, MEDGON_BLAST_RESISTANCE, null);
    public static SimpleBlockSet SMOOTH_MEDGON = registerStoneSet("smooth_medgon", MEDGON.base.getHardness(), MEDGON.base.getBlastResistance(), MEDGON.base);
    public static SimpleBlockSet MOSSY_SMOOTH_MEDGON = registerStoneSet("mossy_smooth_medgon", SMOOTH_MEDGON.base.getHardness(), SMOOTH_MEDGON.base.getBlastResistance(), SMOOTH_MEDGON.base);
    public static SimpleBlockSet CRACKED_SMOOTH_MEDGON = registerStoneSet("cracked_smooth_medgon", SMOOTH_MEDGON.base.getHardness(), SMOOTH_MEDGON.base.getBlastResistance(), SMOOTH_MEDGON.base);
    public static SimpleBlockSet COBBLED_MEDGON = registerStoneSet("cobbled_medgon", MEDGON_BRICKS_HARDNESS, MEDGON_BRICKS_BLAST_RESISTANCE, MEDGON.base);
    public static SimpleBlockSet MOSSY_COBBLED_MEDGON = registerStoneSet("mossy_cobbled_medgon", MEDGON_BRICKS_HARDNESS, MEDGON_BRICKS_BLAST_RESISTANCE, COBBLED_MEDGON.base);
    public static SimpleBlockSet POLISHED_MEDGON = registerStoneSet("polished_medgon", MEDGON.base.getHardness(), MEDGON.base.getBlastResistance(), MEDGON.base);
    public static SimpleBlockSet MOSSY_POLISHED_MEDGON = registerStoneSet("mossy_polished_medgon", MEDGON.base.getHardness(), MEDGON.base.getBlastResistance(), POLISHED_MEDGON.base);
    public static SimpleBlockSet CRACKED_POLISHED_MEDGON = registerStoneSet("cracked_polished_medgon", MEDGON.base.getHardness(), MEDGON.base.getBlastResistance(), POLISHED_MEDGON.base);
    public static SimpleBlockSet MEDGON_BRICKS = registerStoneSet("medgon_bricks", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), POLISHED_MEDGON.base);
    public static SimpleBlockSet MOSSY_MEDGON_BRICKS = registerStoneSet("mossy_medgon_bricks", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), MEDGON_BRICKS.base);
    public static SimpleBlockSet CRACKED_MEDGON_BRICKS = registerStoneSet("cracked_medgon_bricks", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), MEDGON_BRICKS.base);
    public static SimpleBlockSet MEDGON_TILES = registerStoneSet("medgon_tiles", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), MEDGON_BRICKS.base);
    public static SimpleBlockSet MOSSY_MEDGON_TILES = registerStoneSet("mossy_medgon_tiles", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), MEDGON_TILES.base);
    public static SimpleBlockSet CRACKED_MEDGON_TILES = registerStoneSet("cracked_medgon_tiles", COBBLED_MEDGON.base.getHardness(), COBBLED_MEDGON.base.getBlastResistance(), MEDGON_TILES.base);

    public static SimpleBlockSet MEDGON_BRICKWORK = registerStoneSet("medgon_brickwork", Blocks.PACKED_MUD.getHardness(), Blocks.PACKED_MUD.getBlastResistance(), null);

    public static SimpleBlockSet OLD_MEDGON = registerStoneSet("old_medgon", MEDGON.base.getHardness(), MEDGON.base.getBlastResistance(), COBBLED_MEDGON.base);

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

    public static SimpleBlockSetMain[] setsMain = new SimpleBlockSetMain[] {

            ASHEN_STONE,

            PUMICE,

            GONLUIN,

            SCHIST,

            DOLOMITE,
            
            IRONSTONE,
            
            HEMATITE,
            
            GNEISS,

            ZIGILABAN,
            
            IZHERABAN,

            LIMESTONE,
            
            GALONN,
            
            GABBRO,
            
            SLATE,

            BLUE_TUFF,
            
            GREEN_TUFF,

            GILDED_GREEN_TUFF,

            QUARTZITE,

            JADEITE,

            NURGON,

            MEDGON,
    };

    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {

            PACKED_MIRE,
            MIRE_BRICKS,

            PLASTER,

            TAN_CLAY_BRICKS,
            TAN_CLAY_TILES,

            TAN_CLAY_BRICKWORK,

            WHITE_DAUB,
            DARK_DAUB,
            YELLOW_DAUB,

            STUCCO,
            MIXED_STONES_BRICKWORK,

            OLD_BRICKS,

            ASHEN_COBBLESTONE,
            ASHEN_BRICKS,
            ASHEN_TILES,
            POLISHED_ASHEN_STONE,

            COBBLED_GONLUIN,
            MOSSY_COBBLED_GONLUIN,
            POLISHED_GONLUIN,
            MOSSY_POLISHED_GONLUIN,
            CRACKED_POLISHED_GONLUIN,
            GONLUIN_BRICKS,
            MOSSY_GONLUIN_BRICKS,
            CRACKED_GONLUIN_BRICKS,
            GONLUIN_TILES,
            MOSSY_GONLUIN_TILES,
            CRACKED_GONLUIN_TILES,

            COBBLED_SCHIST,
            POLISHED_SCHIST,
            SCHIST_BRICKS,
            SCHIST_TILES,

            SMOOTH_CALCITE,
            MOSSY_SMOOTH_CALCITE,
            CRACKED_SMOOTH_CALCITE,
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
            
            OLD_CALCITE,
            
            CALCITE_BRICKWORK,

            SMOOTH_DOLOMITE,
            MOSSY_SMOOTH_DOLOMITE,
            CRACKED_SMOOTH_DOLOMITE,
            COBBLED_DOLOMITE,
            MOSSY_COBBLED_DOLOMITE,
            DOLOMITE_BRICKS,
            MOSSY_DOLOMITE_BRICKS,
            CRACKED_DOLOMITE_BRICKS,
            DOLOMITE_TILES,
            MOSSY_DOLOMITE_TILES,
            CRACKED_DOLOMITE_TILES,
            POLISHED_DOLOMITE,
            MOSSY_POLISHED_DOLOMITE,
            CRACKED_POLISHED_DOLOMITE,

            DOLOMITE_BRICKWORK,


            SMOOTH_IRONSTONE,
            COBBLED_IRONSTONE,
            IRONSTONE_BRICKS,
            IRONSTONE_TILES,
            POLISHED_IRONSTONE,

            COBBLED_HEMATITE,
            HEMATITE_BRICKS,
            HEMATITE_TILES,
            POLISHED_HEMATITE,

            HEMATITE_BRICKWORK,

            SMOOTH_GNEISS,
            COBBLED_GNEISS,
            GNEISS_BRICKS,
            GNEISS_TILES,
            MOSSY_GNEISS_TILES,
            POLISHED_GNEISS,

            GNEISS_BRICKWORK,

            ZIGILABAN_BRICKS,
            ZIGILABAN_TILES,
            POLISHED_ZIGILABAN,

            SMOOTH_IZHERABAN,
            MOSSY_SMOOTH_IZHERABAN,
            CRACKED_SMOOTH_IZHERABAN,
            COBBLED_IZHERABAN,
            MOSSY_COBBLED_IZHERABAN,
            IZHERABAN_BRICKS,
            MOSSY_IZHERABAN_BRICKS,
            CRACKED_IZHERABAN_BRICKS,
            IZHERABAN_TILES,
            MOSSY_IZHERABAN_TILES,
            CRACKED_IZHERABAN_TILES,
            POLISHED_IZHERABAN,
            MOSSY_POLISHED_IZHERABAN,
            CRACKED_POLISHED_IZHERABAN,

            IZHERABAN_BRICKWORK,

            SMOOTH_LIMESTONE,
            MOSSY_SMOOTH_LIMESTONE,
            CRACKED_SMOOTH_LIMESTONE,
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

            LIMESTONE_BRICKWORK,

            SMOOTH_GALONN,
            MOSSY_SMOOTH_GALONN,
            CRACKED_SMOOTH_GALONN,
            COBBLED_GALONN,
            MOSSY_COBBLED_GALONN,
            GALONN_BRICKS,
            CRACKED_GALONN_BRICKS,
            MOSSY_GALONN_BRICKS,
            GALONN_TILES,
            MOSSY_GALONN_TILES,
            CRACKED_GALONN_TILES,
            POLISHED_GALONN,
            MOSSY_POLISHED_GALONN,
            CRACKED_POLISHED_GALONN,

            GALONN_BRICKWORK,

            //COBBLED_GABBRO,
            //MOSSY_COBBLED_GABBRO,
            GABBRO_BRICKS,
            //CRACKED_GABBRO_BRICKS,
            //MOSSY_GABBRO_BRICKS,
            //GABBRO_TILES,
            //MOSSY_GABBRO_TILES,
            //CRACKED_GABBRO_TILES,
            POLISHED_GABBRO,
            //MOSSY_POLISHED_GABBRO,
            //CRACKED_POLISHED_GABBRO,

            GABBRO_BRICKWORK,

            COBBLED_SLATE,
            //MOSSY_COBBLED_SLATE,
            SLATE_BRICKS,
            //CRACKED_SLATE_BRICKS,
            //MOSSY_SLATE_BRICKS,
            //SLATE_TILES,
            //MOSSY_SLATE_TILES,
            //CRACKED_SLATE_TILES,
            POLISHED_SLATE,
            //MOSSY_POLISHED_SLATE,
            //CRACKED_POLISHED_SLATE,

            POLISHED_STONE,
            MOSSY_POLISHED_STONE,
            CRACKED_POLISHED_STONE,
            STONE_TILES,
            MOSSY_STONE_TILES,
            CRACKED_STONE_TILES,
            MOSSY_SMOOTH_STONE,
            CRACKED_SMOOTH_STONE,

            STONE_BRICKWORK,

            OLD_STONE,

            MOSSY_COBBLED_DEEPSLATE,
            SMOOTH_DEEPSLATE,
            MOSSY_SMOOTH_DEEPSLATE,
            CRACKED_SMOOTH_DEEPSLATE,
            MOSSY_DEEPSLATE_BRICKS,
            MOSSY_POLISHED_DEEPSLATE,
            CRACKED_POLISHED_DEEPSLATE,
            MOSSY_DEEPSLATE_TILES,

            OLD_DEEPSLATE,

            DEEPSLATE_BRICKWORK,

            COBBLED_BLUE_TUFF,
            MOSSY_COBBLED_BLUE_TUFF,
            POLISHED_BLUE_TUFF,
            MOSSY_POLISHED_BLUE_TUFF,
            CRACKED_POLISHED_BLUE_TUFF,
            BLUE_TUFF_BRICKS,
            MOSSY_BLUE_TUFF_BRICKS,
            CRACKED_BLUE_TUFF_BRICKS,
            BLUE_TUFF_TILES,
            MOSSY_BLUE_TUFF_TILES,
            CRACKED_BLUE_TUFF_TILES,

            SMOOTH_GREEN_TUFF,
            CRACKED_SMOOTH_GREEN_TUFF,
            //COBBLED_GREEN_TUFF,
            //MOSSY_COBBLED_GREEN_TUFF,
            POLISHED_GREEN_TUFF,
            //MOSSY_POLISHED_GREEN_TUFF,
            CRACKED_POLISHED_GREEN_TUFF,
            GREEN_TUFF_BRICKS,
            //MOSSY_GREEN_TUFF_BRICKS,
            CRACKED_GREEN_TUFF_BRICKS,
            GREEN_TUFF_TILES,
            //MOSSY_GREEN_TUFF_TILES,
            CRACKED_GREEN_TUFF_TILES,

            SMOOTH_ANDESITE,
            MOSSY_SMOOTH_ANDESITE,
            CRACKED_SMOOTH_ANDESITE,
            COBBLED_ANDESITE,
            MOSSY_COBBLED_ANDESITE,
            ANDESITE_BRICKS,
            MOSSY_ANDESITE_BRICKS,
            CRACKED_ANDESITE_BRICKS,
            ANDESITE_TILES,
            MOSSY_ANDESITE_TILES,
            CRACKED_ANDESITE_TILES,
            MOSSY_POLISHED_ANDESITE,
            CRACKED_POLISHED_ANDESITE,

            ANDESITE_BRICKWORK,
            
            OLD_ANDESITE,

            SMOOTH_GRANITE,
            MOSSY_SMOOTH_GRANITE,
            CRACKED_SMOOTH_GRANITE,
            COBBLED_GRANITE,
            MOSSY_COBBLED_GRANITE,
            GRANITE_BRICKS,
            MOSSY_GRANITE_BRICKS,
            CRACKED_GRANITE_BRICKS,
            GRANITE_TILES,
            MOSSY_GRANITE_TILES,
            CRACKED_GRANITE_TILES,
            MOSSY_POLISHED_GRANITE,
            CRACKED_POLISHED_GRANITE,

            GRANITE_BRICKWORK,

            OLD_GRANITE,

            SMOOTH_DIORITE,
            MOSSY_SMOOTH_DIORITE,
            CRACKED_SMOOTH_DIORITE,
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

            DIORITE_BRICKWORK,

            OLD_DIORITE,

            SMOOTH_TUFF,
            MOSSY_SMOOTH_TUFF,
            CRACKED_SMOOTH_TUFF,
            COBBLED_TUFF,
            MOSSY_COBBLED_TUFF,
            MOSSY_TUFF_BRICKS,
            CRACKED_TUFF_BRICKS,
            TUFF_TILES,
            MOSSY_TUFF_TILES,
            CRACKED_TUFF_TILES,
            MOSSY_POLISHED_TUFF,
            CRACKED_POLISHED_TUFF,

            OLD_TUFF,

            TUFF_BRICKWORK,

            SMOOTH_BLACKSTONE,
            MOSSY_SMOOTH_BLACKSTONE,
            CRACKED_SMOOTH_BLACKSTONE,
            COBBLED_BLACKSTONE,
            MOSSY_COBBLED_BLACKSTONE,
            MOSSY_POLISHED_BLACKSTONE_BRICKS,
            BLACKSTONE_TILES,
            MOSSY_BLACKSTONE_TILES,
            CRACKED_BLACKSTONE_TILES,
            MOSSY_POLISHED_BLACKSTONE,
            CRACKED_POLISHED_BLACKSTONE,

            OLD_BLACKSTONE,

            BLACKSTONE_BRICKWORK,

            MOSSY_SMOOTH_BASALT,
            CRACKED_SMOOTH_BASALT,
            COBBLED_BASALT,
            MOSSY_COBBLED_BASALT,
            BASALT_BRICKS,
            MOSSY_BASALT_BRICKS,
            CRACKED_BASALT_BRICKS,
            BASALT_TILES,
            MOSSY_BASALT_TILES,
            CRACKED_BASALT_TILES,

            OLD_BASALT,

            BASALT_BRICKWORK,

            COBBLED_QUARTZITE,
            POLISHED_QUARTZITE,
            QUARTZITE_BRICKS,
            QUARTZITE_TILES,

            COBBLED_JADEITE,
            POLISHED_JADEITE,
            CRACKED_POLISHED_JADEITE,
            JADEITE_BRICKS,
            CRACKED_JADEITE_BRICKS,
            JADEITE_TILES,
            CRACKED_JADEITE_TILES,

            MIXED_STONES,
            MOSSY_MIXED_STONES,
            CRACKED_MIXED_STONES,

            COBBLED_NURGON,
            POLISHED_NURGON,
            NURGON_BRICKS,
            NURGON_TILES,

            SMOOTH_MEDGON,
            MOSSY_SMOOTH_MEDGON,
            CRACKED_SMOOTH_MEDGON,
            COBBLED_MEDGON,
            MOSSY_COBBLED_MEDGON,
            POLISHED_MEDGON,
            MOSSY_POLISHED_MEDGON,
            CRACKED_POLISHED_MEDGON,
            MEDGON_BRICKS,
            MOSSY_MEDGON_BRICKS,
            CRACKED_MEDGON_BRICKS,
            MEDGON_TILES,
            MOSSY_MEDGON_TILES,
            CRACKED_MEDGON_TILES,

            OLD_MEDGON,

            MEDGON_BRICKWORK,
    };

    public static SimplePillarBlockSet[] pillarSets = new SimplePillarBlockSet[] {
            MOSSY_POLISHED_BASALT,
            CRACKED_POLISHED_BASALT,
            OLD_IZHERABAN,
            OLD_LIMESTONE,
            OLD_GALONN,
            OLD_DOLOMITE

    };

    public record SimpleBlockSet(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall) {
    }

    public record SimplePillarBlockSet(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall) {
    }

    public record SimpleBlockSetMain(Block source, Block base, Block slab, Block verticalSlab, Block stairs, Block wall, Block pressurePlate, Block button, Block trapdoor, Block stool, Block table, Block chair, Block rocks) {
    }

    private static SimpleBlockSet registerStoneSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerStoneBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(hardness, blastResistance).requiresTool()),false);

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        return new SimpleBlockSet(source, base, slab, verticalSlab, stairs, wall);
    }

    private static SimplePillarBlockSet registerStonePillarSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerStoneBlock(name, new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(hardness, blastResistance).requiresTool()),false);

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        return new SimplePillarBlockSet(source, base, slab, verticalSlab, stairs, wall);
    }

    private static SimpleBlockSetMain registerMainStoneSet(String name, float hardness, float blastResistance, Block source) {

        Block base = ModBlocks.registerStoneBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(hardness, blastResistance).requiresTool()),false);

        Block slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool()),false);

        Block pressurePlate = ModBlocks.registerStoneBlock(name + "_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool().noCollision()),false);

        Block button = ModBlocks.registerStoneBlock(name + "_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool().noCollision()),false);

        Block trapdoor = ModBlocks.registerStoneBlock(name + "_trapdoor", new TrapdoorBlock(BlockSetType.STONE, AbstractBlock.Settings.copy(base)
                .strength(hardness, blastResistance).sounds(BlockSoundGroup.STONE).nonOpaque()),false);

        Block rocks = ModBlocks.registerStoneBlock(name + "_rocks", new RocksBlock(AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool().nonOpaque()),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(AbstractBlock.Settings.copy(base)
                .strength(hardness, blastResistance).requiresTool().nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new StoneTableBlock(AbstractBlock.Settings.copy(base)
                .strength(hardness, blastResistance).requiresTool().nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new StoneChairBlock(AbstractBlock.Settings.copy(base).nonOpaque()),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new SimpleBlockSetMain(source, base, slab, verticalSlab, stairs, wall, pressurePlate, button, trapdoor, stool, table, chair, rocks);
    }

    public static void registerModBlockSets() {
        LoggerUtil.logDebugMsg("Registering SimpleBlockSets for " + MiddleEarth.MOD_ID);
    }
}
