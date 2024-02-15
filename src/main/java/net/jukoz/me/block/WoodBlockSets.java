package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.WoodChairBlock;
import net.jukoz.me.block.special.StoolBlock;
import net.jukoz.me.block.special.TableBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.block.special.ModLeavesBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.5f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static SimpleBlockSet BEECH = registerWoodSet("beech", WOOD_STRENGTH, true, ModNatureBlocks.BEECH_SAPLING, true, false);
    public static SimpleBlockSet LARCH = registerWoodSet("larch", WOOD_STRENGTH, true, ModNatureBlocks.LARCH_SAPLING, true, false);
    public static SimpleBlockSet BLACK_LEBETHRON = registerWoodSet("black_lebethron", WOOD_STRENGTH, false, null, true, false);
    public static SimpleBlockSet WHITE_LEBETHRON = registerWoodSet("white_lebethron", WOOD_STRENGTH, false, null, true, false);
    public static SimpleBlockSet MALLORN = registerWoodSet("mallorn", WOOD_STRENGTH, true, ModNatureBlocks.MALLORN_SAPLING, false, true);
    public static SimpleBlockSet MAPLE = registerWoodSet("maple", WOOD_STRENGTH, true, ModNatureBlocks.MAPLE_SAPLING, false, false);
    public static SimpleBlockSet MIRKWOOD = registerWoodSet("mirkwood", WOOD_STRENGTH, true, ModNatureBlocks.MIRKWOOD_SAPLING, true, true);
    public static SimpleBlockSet PALM = registerWoodSet("palm", WOOD_STRENGTH, true, ModNatureBlocks.PALM_SAPLING,  true,  true);
    public static SimpleBlockSet WHITE_PALM = registerWoodSet("white_palm", WOOD_STRENGTH, false, null, true, true);
    public static SimpleBlockSet PINE = registerWoodSet("pine", WOOD_STRENGTH, true, ModNatureBlocks.PINE_SAPLING, true, false);
    public static SimpleBlockSet WILLOW = registerWoodSet("willow", WOOD_STRENGTH, true, ModNatureBlocks.WILLOW_SAPLING, true, false);

    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            BEECH,
            LARCH,
            BLACK_LEBETHRON,
            WHITE_LEBETHRON,
            MALLORN,
            MAPLE,
            MIRKWOOD,
            PALM,
            WHITE_PALM,
            PINE,
            WILLOW,
    };

    public record SimpleBlockSet(Block leaves, Block log, Block wood, Block woodWall, Block woodFence,
                                 Block strippedLog, Block strippedWood, Block strippedWoodWall, Block strippedWoodFence,
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block table, Block chair, Block sapling) {
    }

    private static SimpleBlockSet registerWoodSet(String name, float strength, boolean hasLeaves, Block sapling, boolean castShadow, boolean range) {
        Block leaves = null;
        if(hasLeaves) {
            if(range) {
                leaves = ModNatureBlocks.registerBlock(name + "_leaves", new ModLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN)
                        .strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never)
                        .blockVision(ModBlocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(ModBlocks::never), castShadow), true);
            } else {
                leaves = ModNatureBlocks.registerBlock(name + "_leaves", new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN)
                        .strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never)
                        .blockVision(ModBlocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(ModBlocks::never)), true);
            }
        }


        Block log = ModBlocks.registerWoodBlock(name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block wood = ModBlocks.registerWoodBlock(name + "_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block woodWall = ModBlocks.registerWoodBlock(name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block woodFence = ModBlocks.registerWoodBlock(name + "_wood_fence", new FenceBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedLog = ModBlocks.registerWoodBlock("stripped_" + name + "_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedWood = ModBlocks.registerWoodBlock("stripped_" + name + "_wood", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedWoodWall = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_wall", new WallBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block strippedWoodFence = ModBlocks.registerWoodBlock("stripped_" + name + "_wood_fence", new FenceBlock(AbstractBlock.Settings.copy(wood).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD)),false);

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD), WoodType.OAK),false);

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true),false);

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK),false);

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK),false);

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.OAK),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new TableBlock(FabricBlockSettings.copyOf(planks)
                .strength(strength).sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(log.asItem().getDefaultStack());

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new SimpleBlockSet(leaves, log, wood, woodWall, woodFence,
                strippedLog, strippedWood, strippedWoodWall, strippedWoodFence,
                planks, slab, verticalSlab, stairs, fence, gate,
                pressurePlate, button, door, trapdoor, stool, table, chair, sapling);
    }


    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
