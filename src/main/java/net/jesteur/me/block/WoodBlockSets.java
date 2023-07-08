package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 1.25f;

    public static SimpleBlockSet MALLORN = registerWoodSet("mallorn", WOOD_STRENGTH);


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            MALLORN,
    };

    public record SimpleBlockSet(Block leaves, Block log, Block wood, Block woodWall,
                                 Block planks, Block planksSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength) {
        Block leaves = ModBlocks.registerBlock(name + "_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(strength)));

        // TODO : Fix the log top texture
        Block log = ModBlocks.registerBlock(name + "_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(strength)));

        Block wood = ModBlocks.registerBlock(name + "_wood", new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(strength)));

        Block woodWall = ModBlocks.registerBlock(name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength)));

        Block planks = ModBlocks.registerBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(strength)));

        Block planksSlab = ModBlocks.registerBlock(name + "_planks_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(strength, ModBlocks.SLAB_RESISTANCE)));

        Block planksStairs = ModBlocks.registerBlock(name + "_planks_stairs", new StairsBlock(planks.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(strength)));

        // TODO : fence, gate, pressurePlate, button

        //Block planksFence = ModBlocks.registerBlock(name + "planks_fence", new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).strength(strength)));

        //Block planksGate = ModBlocks.registerBlock(name + "planks_gate", new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)));


        return new SimpleBlockSet(leaves, log, wood, woodWall, planks, planksSlab, planksStairs, null, null, null, null);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }

    private static PillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(AbstractBlock.Settings.create().mapColor((state) -> { return state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor; }));
    }
}
