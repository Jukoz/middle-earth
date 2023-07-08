package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.minecraft.block.*;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 1.25f;

    public static SimpleBlockSet MALLORN = registerWoodSet("mallorn", WOOD_STRENGTH);


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            MALLORN,
    };

    public record SimpleBlockSet(Block log, Block logWall, Block wood, Block woodWall, Block leaves,
                                 Block plank, Block plankSlab, Block plankStair, Block plankFence, Block plankGate,
                                 Block pressurePlate, Block button) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength) {
        Block wood = ModBlocks.registerBlock(name + "_wood", new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
                .strength(strength)));

        Block woodWall = ModBlocks.registerBlock(name + "_wood_wall", new Block(FabricBlockSettings.copyOf(wood)
                .strength(strength)));

        Block log = ModBlocks.registerBlock(name + "_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)
                .strength(strength)));

        //Block logWall = ModBlocks.registerBlock(name + "_wall",  new WallBlock(AbstractBlock.Settings.copy(log).strength(strength)));


        return new SimpleBlockSet(log, null, wood, woodWall, null,null, null, null, null, null, null, null);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
