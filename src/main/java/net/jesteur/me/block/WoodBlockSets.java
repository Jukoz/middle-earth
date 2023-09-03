package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.5f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static SimpleBlockSet BEECH = registerWoodSet("beech", WOOD_STRENGTH, true);
    public static SimpleBlockSet LARCH = registerWoodSet("larch", WOOD_STRENGTH, true);
    public static SimpleBlockSet BLACK_LEBETHRON = registerWoodSet("black_lebethron", WOOD_STRENGTH, false);
    public static SimpleBlockSet WHITE_LEBETHRON = registerWoodSet("white_lebethron", WOOD_STRENGTH, false);
    public static SimpleBlockSet MALLORN = registerWoodSet("mallorn", WOOD_STRENGTH, true);
    public static SimpleBlockSet MAPLE = registerWoodSet("maple", WOOD_STRENGTH, true);
    public static SimpleBlockSet MIRKWOOD = registerWoodSet("mirkwood", WOOD_STRENGTH, false);
    public static SimpleBlockSet PALM = registerWoodSet("palm", WOOD_STRENGTH, true);
    public static SimpleBlockSet PINE = registerWoodSet("pine", WOOD_STRENGTH, true);
    public static SimpleBlockSet WILLOW = registerWoodSet("willow", WOOD_STRENGTH, true);


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            BEECH,
            LARCH,
            BLACK_LEBETHRON,
            WHITE_LEBETHRON,
            MALLORN,
            MAPLE,
            MIRKWOOD,
            PALM,
            PINE,
            WILLOW,
    };

    public record SimpleBlockSet(Block leaves, Block log, Block wood, Block woodWall,
                                 Block planks, Block planksSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength, boolean hasLeaves) {
        Block leaves = null;
        if(hasLeaves) {
            leaves = ModNatureBlocks.registerBlock(name + "_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), false);
        }
        Block log = ModBlocks.registerBlock(name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block wood = ModBlocks.registerBlock(name + "_wood", new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block woodWall = ModBlocks.registerBlock(name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block planks = ModBlocks.registerBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block fence = ModBlocks.registerBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block gate = ModBlocks.registerBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD), WoodType.OAK));

        Block button = ModBlocks.registerBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true));

        Block pressurePlate = ModBlocks.registerBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        return new SimpleBlockSet(leaves, log, wood, woodWall, planks, slab, stairs, fence, gate, pressurePlate, button);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
