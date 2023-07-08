package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.minecraft.block.*;

public class SimpleBlockSets {
    public static final float STONE_STRENGTH = 1.5f;
    public static final float BRICKS_STRENGTH = 1.75f;

    public static SimpleBlockSet ASHEN_BRICKS = registerBrickSet("ashen_bricks", BRICKS_STRENGTH);
    public static SimpleBlockSet ASHEN_ROCK = registerBrickSet("ashen_rock", STONE_STRENGTH);
    public static SimpleBlockSet BLUE_ROCK = registerBrickSet("blue_rock", STONE_STRENGTH);
    public static SimpleBlockSet BLUE_ROCK_BRICKS = registerBrickSet("blue_rock_bricks", BRICKS_STRENGTH);
    public static SimpleBlockSet CALCITE_BRICKS = registerBrickSet("calcite_bricks", BRICKS_STRENGTH);


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            ASHEN_BRICKS,
            ASHEN_ROCK,
            BLUE_ROCK,
            BLUE_ROCK_BRICKS,
            CALCITE_BRICKS,
    };

    public record SimpleBlockSet(Block base, Block slab, Block stairs, Block wall) {
    }

    private static SimpleBlockSet registerBrickSet(String name, float strength) {

        Block bricks = ModBlocks.registerBlock(name, new Block(FabricBlockSettings.copyOf(Blocks.STONE)
                .strength(strength).requiresTool()));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(strength, ModBlocks.SLAB_RESISTANCE).requiresTool()));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(bricks.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(strength).requiresTool()));

        Block wall = ModBlocks.registerBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(bricks)
                .strength(strength).requiresTool()));


        return new SimpleBlockSet(bricks, slab, stairs, wall);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering SimpleBlockSets for " + MiddleEarth.MOD_ID);
    }
}
