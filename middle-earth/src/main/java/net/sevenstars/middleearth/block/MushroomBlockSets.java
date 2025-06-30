package net.sevenstars.middleearth.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class MushroomBlockSets {
    public static final float MUSHROOM_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.1f;

    public static MushroomBlockSet DARK_MUSHROOM = registerMushroomSet("dark_mushroom", null);
    public static MushroomBlockSet GRAY_MUSHROOM = registerMushroomSet("gray_mushroom", null);
    public static MushroomBlockSet MUSHROOM = registerMushroomSet("mushroom", Blocks.MUSHROOM_STEM);


    public record MushroomBlockSet(Block stem, Block stemWall, Block stemFence,
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block bench, Block table, Block chair, Block ladder) {
    }

    public static MushroomBlockSet[] sets = new MushroomBlockSet[] {
            DARK_MUSHROOM,
            GRAY_MUSHROOM,
            MUSHROOM,
    };

    private static MushroomBlockSet registerMushroomSet(String name, Block stem) {

        if(stem == null){
            stem = ModBlocks.registerWoodBlock(name + "_stem", MushroomBlock::new,
                    AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.WOOD),false);
        }

        Block stemWall = ModBlocks.registerWoodBlock(name + "_stem_wall", WallBlock::new,
                AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block stemFence = ModBlocks.registerWoodBlock(name + "_stem_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", Block::new,
                AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD),false);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", (settings)-> new StairsBlock(
                planks.getDefaultState(), settings), AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate", (settings) -> new FenceGateBlock(
                WoodType.OAK, settings), AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD),false);

        Block button = ModBlocks.registerWoodBlock(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.OAK, 5, settings), AbstractBlock.Settings.copy(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD).noCollision(),false);

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate", (settings) ->  new PressurePlateBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD).noCollision(),false);

        Block door = ModBlocks.registerWoodBlock(name + "_door", (settings) ->  new DoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", (settings) ->  new TrapdoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block stool = ModBlocks.registerWoodBlock(name + "_stool", WoodStoolBlock::new,
                AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block bench = ModBlocks.registerWoodBlock(name + "_bench", WoodBenchBlock::new,
                AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block table = ModBlocks.registerWoodBlock(name + "_table", WoodTableBlock::new,
                AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block chair = ModBlocks.registerWoodBlock(name + "_chair", WoodChairBlock::new,
                AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.WOOD).nonOpaque(),false);

        Block ladder = ModBlocks.registerWoodBlock(name + "_ladder", ThickLadderBlock::new,
                AbstractBlock.Settings.copy(planks).sounds(BlockSoundGroup.LADDER).nonOpaque(),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(bench.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(ladder.asItem().getDefaultStack());

        return new MushroomBlockSet(stem, stemWall, stemFence, planks, slab, verticalSlab, stairs, fence, gate, pressurePlate, button, door, trapdoor, stool, bench, table, chair, ladder);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering MushroomBlockSets for " + MiddleEarth.MOD_ID);
    }
}
