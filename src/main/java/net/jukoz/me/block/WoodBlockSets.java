package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.WoodChairBlock;
import net.jukoz.me.block.special.StoolBlock;
import net.jukoz.me.block.special.TableBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

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
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block table, Block chair) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength, boolean hasLeaves) {
        Block leaves = null;
        if(hasLeaves) {
            leaves = ModNatureBlocks.registerBlock(name + "_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), false);
        }


        Block log = ModBlocks.registerWoodBlock(name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block wood = ModBlocks.registerWoodBlock(name + "_wood", new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block woodWall = ModBlocks.registerWoodBlock(name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)));


        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)));

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD)));

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD), WoodType.OAK));

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true));

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.OAK));

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()));

        Block table = ModBlocks.registerBlock(name + "_table", new TableBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()));

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(log.asItem().getDefaultStack());

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new SimpleBlockSet(leaves, log, wood, woodWall, planks, slab, verticalSlab, stairs, fence, gate,
                pressurePlate, button, door, trapdoor, stool, table, chair);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
