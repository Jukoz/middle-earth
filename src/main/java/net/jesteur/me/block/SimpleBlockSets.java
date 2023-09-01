package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.minecraft.block.*;

public class SimpleBlockSets {
    public static final float STONE_STRENGTH = 1.5f;
    public static final float COBBLE_STRENGTH = 1.65f;
    public static final float BRICKS_STRENGTH = 1.75f;

    public static SimpleBlockSet ASHEN_ROCK = registerBrickSet("ashen_rock", STONE_STRENGTH, null);
    public static SimpleBlockSet ASHEN_BRICKS = registerBrickSet("ashen_bricks", BRICKS_STRENGTH, ASHEN_ROCK.base);
    public static SimpleBlockSet BLUE_ROCK = registerBrickSet("blue_rock", STONE_STRENGTH, null);
    public static SimpleBlockSet BLUE_ROCK_BRICKS = registerBrickSet("blue_rock_bricks", BRICKS_STRENGTH, BLUE_ROCK.base);
    public static SimpleBlockSet CALCITE_BRICKS = registerBrickSet("calcite_bricks", BRICKS_STRENGTH, Blocks.CALCITE);
    public static SimpleBlockSet DOLOMITE = registerBrickSet("dolomite", STONE_STRENGTH, null);
    public static SimpleBlockSet DOLOMITE_BRICKS = registerBrickSet("dolomite_bricks", STONE_STRENGTH, DOLOMITE.base);
    public static SimpleBlockSet FROZEN_BLUE_COBBLESTONE = registerBrickSet("frozen_blue_cobblestone", COBBLE_STRENGTH, null);
    public static SimpleBlockSet FROZEN_BLUE_ROCK = registerBrickSet("frozen_blue_rock", STONE_STRENGTH, null);
    public static SimpleBlockSet FROZEN_BLUE_ROCK_BRICKS = registerBrickSet("frozen_blue_rock_bricks", STONE_STRENGTH, FROZEN_BLUE_ROCK.base);
    public static SimpleBlockSet LIMESTONE = registerBrickSet("limestone", STONE_STRENGTH, null);
    public static SimpleBlockSet LIMESTONE_BRICKS = registerBrickSet("limestone_bricks", STONE_STRENGTH, LIMESTONE.base);
    public static SimpleBlockSet LIMESTONE_CRACKED_BRICKS = registerBrickSet("limestone_cracked_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet LIMESTONE_MOSSY_BRICKS = registerBrickSet("limestone_mossy_bricks", STONE_STRENGTH, null);
    public static SimpleBlockSet POLISHED_FROZEN_BLUE_ROCK = registerBrickSet("polished_frozen_blue_rock", STONE_STRENGTH, FROZEN_BLUE_ROCK_BRICKS.base);
    public static SimpleBlockSet POLISHED_DOLOMITE = registerBrickSet("polished_dolomite", STONE_STRENGTH, DOLOMITE_BRICKS.base);

    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            ASHEN_BRICKS,
            ASHEN_ROCK,
            BLUE_ROCK,
            BLUE_ROCK_BRICKS,
            CALCITE_BRICKS,
            DOLOMITE,
            DOLOMITE_BRICKS,
            FROZEN_BLUE_COBBLESTONE,
            FROZEN_BLUE_ROCK,
            FROZEN_BLUE_ROCK_BRICKS,
            LIMESTONE,
            LIMESTONE_BRICKS,
            LIMESTONE_CRACKED_BRICKS,
            LIMESTONE_MOSSY_BRICKS,
            POLISHED_FROZEN_BLUE_ROCK,
            POLISHED_DOLOMITE
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
