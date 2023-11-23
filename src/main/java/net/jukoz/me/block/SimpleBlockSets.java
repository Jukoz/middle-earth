package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.minecraft.block.*;

public class SimpleBlockSets {
    public static final float STONE_STRENGTH = 1.5f;
    public static final float COBBLE_STRENGTH = 1.65f;
    public static final float BRICKS_STRENGTH = 1.75f;

    public static SimpleBlockSet ASHEN_ROCK = registerBrickSet("ashen_rock", STONE_STRENGTH, null);
    public static SimpleBlockSet ASHEN_BRICKS = registerBrickSet("ashen_bricks", BRICKS_STRENGTH, ASHEN_ROCK.base);

    public static SimpleBlockSet GONLUIN = registerBrickSet("gonluin", STONE_STRENGTH, null);
    public static SimpleBlockSet SMALL_GONLUIN_BRICKS = registerBrickSet("small_gonluin_bricks", BRICKS_STRENGTH, GONLUIN.base);

    public static SimpleBlockSet CALCITE_BRICKS = registerBrickSet("calcite_bricks", BRICKS_STRENGTH, Blocks.CALCITE);

    public static SimpleBlockSet DOLOMITE = registerBrickSet("dolomite", STONE_STRENGTH, null);
    public static SimpleBlockSet DOLOMITE_BRICKS = registerBrickSet("dolomite_bricks", STONE_STRENGTH, DOLOMITE.base);
    public static SimpleBlockSet POLISHED_DOLOMITE = registerBrickSet("polished_dolomite", STONE_STRENGTH, DOLOMITE_BRICKS.base);

    public static SimpleBlockSet FROZEN_GONLUIN_COBBLESTONE = registerBrickSet("frozen_gonluin_cobblestone", COBBLE_STRENGTH, null);
    public static SimpleBlockSet FROZEN_GONLUIN = registerBrickSet("frozen_gonluin", STONE_STRENGTH, null);
    public static SimpleBlockSet FROZEN_GONLUIN_BRICKS = registerBrickSet("frozen_gonluin_bricks", STONE_STRENGTH, FROZEN_GONLUIN.base);
    public static SimpleBlockSet POLISHED_FROZEN_GONLUIN = registerBrickSet("polished_frozen_gonluin", STONE_STRENGTH, FROZEN_GONLUIN_BRICKS.base);

    public static SimpleBlockSet LIMESTONE = registerBrickSet("limestone", STONE_STRENGTH, null);
    public static SimpleBlockSet LIMESTONE_BRICKS = registerBrickSet("limestone_bricks", STONE_STRENGTH, LIMESTONE.base);
    public static SimpleBlockSet LIMESTONE_CRACKED_BRICKS = registerBrickSet("limestone_cracked_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet LIMESTONE_MOSSY_BRICKS = registerBrickSet("limestone_mossy_bricks", STONE_STRENGTH, null);

    public static SimpleBlockSet ANDESITE_BRICKS = registerBrickSet("andesite_bricks", STONE_STRENGTH, Blocks.POLISHED_ANDESITE);
    public static SimpleBlockSet MOSSY_ANDESITE_BRICKS = registerBrickSet("mossy_andesite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet CRACKED_ANDESITE_BRICKS = registerBrickSet("cracked_andesite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet SMALL_ANDESITE_BRICKS = registerBrickSet("small_andesite_bricks", STONE_STRENGTH, ANDESITE_BRICKS.base);

    public static SimpleBlockSet GRANITE_BRICKS = registerBrickSet("granite_bricks", STONE_STRENGTH, Blocks.POLISHED_GRANITE);
    public static SimpleBlockSet MOSSY_GRANITE_BRICKS = registerBrickSet("mossy_granite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet CRACKED_GRANITE_BRICKS = registerBrickSet("cracked_granite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet SMALL_GRANITE_BRICKS = registerBrickSet("small_granite_bricks", STONE_STRENGTH, GRANITE_BRICKS.base);

    public static SimpleBlockSet DIORITE_BRICKS = registerBrickSet("diorite_bricks", STONE_STRENGTH, Blocks.POLISHED_DIORITE);
    public static SimpleBlockSet MOSSY_DIORITE_BRICKS = registerBrickSet("mossy_diorite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet CRACKED_DIORITE_BRICKS = registerBrickSet("cracked_diorite_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet SMALL_DIORITE_BRICKS = registerBrickSet("small_diorite_bricks", STONE_STRENGTH, DIORITE_BRICKS.base);

    public static SimpleBlockSet QUARTZITE = registerBrickSet("quartzite", STONE_STRENGTH, null);

    public static SimpleBlockSet JADEITE = registerBrickSet("jadeite", STONE_STRENGTH, null);
    public static SimpleBlockSet POLISHED_JADEITE = registerBrickSet("polished_jadeite", STONE_STRENGTH, JADEITE.base);
    public static SimpleBlockSet JADEITE_BRICKS = registerBrickSet("jadeite_bricks", STONE_STRENGTH, POLISHED_JADEITE.base);
    public static SimpleBlockSet SMALL_JADEITE_BRICKS = registerBrickSet("small_jadeite_bricks", STONE_STRENGTH, JADEITE_BRICKS.base);


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            ASHEN_BRICKS,
            ASHEN_ROCK,
            GONLUIN,
            SMALL_GONLUIN_BRICKS,
            CALCITE_BRICKS,
            DOLOMITE,
            DOLOMITE_BRICKS,
            FROZEN_GONLUIN_COBBLESTONE,
            FROZEN_GONLUIN,
            FROZEN_GONLUIN_BRICKS,
            LIMESTONE,
            LIMESTONE_BRICKS,
            LIMESTONE_CRACKED_BRICKS,
            LIMESTONE_MOSSY_BRICKS,
            POLISHED_FROZEN_GONLUIN,
            POLISHED_DOLOMITE,
            ANDESITE_BRICKS,
            MOSSY_ANDESITE_BRICKS,
            CRACKED_ANDESITE_BRICKS,
            SMALL_ANDESITE_BRICKS,
            GRANITE_BRICKS,
            MOSSY_GRANITE_BRICKS,
            CRACKED_GRANITE_BRICKS,
            SMALL_GRANITE_BRICKS,
            DIORITE_BRICKS,
            MOSSY_DIORITE_BRICKS,
            CRACKED_DIORITE_BRICKS,
            SMALL_DIORITE_BRICKS,
            QUARTZITE,
            JADEITE,
            POLISHED_JADEITE,
            JADEITE_BRICKS,
            SMALL_JADEITE_BRICKS,
    };

    public record SimpleBlockSet(Block source, Block base, Block slab, Block stairs, Block wall) {
    }

    private static SimpleBlockSet registerBrickSet(String name, float strength, Block source) {

        Block base = ModBlocks.registerBlock(name, new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(strength).requiresTool()));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(strength, ModBlocks.SLAB_RESISTANCE).requiresTool()));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(base.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(strength).requiresTool()));

        Block wall = ModBlocks.registerBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(base).strength(strength).requiresTool()));

        return new SimpleBlockSet(source, base, slab, stairs, wall);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering SimpleBlockSets for " + MiddleEarth.MOD_ID);
    }
}
