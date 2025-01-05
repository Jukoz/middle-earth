package net.jukoz.me.block;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class MushroomBlockSets {
    public static final float MUSHROOM_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.1f;

    public static MushroomBlockSets.MushroomBlockSet DARK_MUSHROOM = registerMushroomSet("dark_mushroom", null);
    public static MushroomBlockSets.MushroomBlockSet GRAY_MUSHROOM = registerMushroomSet("gray_mushroom", null);
    public static MushroomBlockSets.MushroomBlockSet MUSHROOM = registerMushroomSet("mushroom", Blocks.MUSHROOM_STEM);


    public record MushroomBlockSet(Block stem, Block stemWall, Block stemFence,
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block bench, Block table, Block chair, Block ladder) {
    }

    public static MushroomBlockSets.MushroomBlockSet[] sets = new MushroomBlockSets.MushroomBlockSet[] {
            DARK_MUSHROOM,
            GRAY_MUSHROOM,
            MUSHROOM,
    };

    private static MushroomBlockSets.MushroomBlockSet registerMushroomSet(String name, Block stem) {

        if(stem == null){
            stem = ModBlocks.registerWoodBlock(name + "_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.WOOD)),false);
        }

        Block stemWall = ModBlocks.registerWoodBlock(name + "_stem_wall", new WallBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block stemFence = ModBlocks.registerWoodBlock(name + "_stem_fence", new FenceBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(AbstractBlock.Settings.copy(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(AbstractBlock.Settings.copy(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(WoodType.OAK, AbstractBlock.Settings.copy(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(BlockSetType.OAK, 5, AbstractBlock.Settings.copy(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD).noCollision()),false);

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(BlockSetType.OAK,
                AbstractBlock.Settings.copy(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD).noCollision()),false);

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new WoodStoolBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block bench = ModBlocks.registerBlock(name + "_bench", new WoodBenchBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new WoodTableBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block ladder = ModBlocks.registerBlock(name + "_ladder", new ThickLadderBlock(AbstractBlock.Settings.copy(planks)
                .sounds(BlockSoundGroup.LADDER).nonOpaque()),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(bench.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(ladder.asItem().getDefaultStack());

        return new MushroomBlockSet(stem, stemWall, stemFence, planks, slab, verticalSlab, stairs, fence, gate, pressurePlate, button, door, trapdoor, stool, bench, table, chair, ladder);
    }

    public static void registerModBlockSets() {
        LoggerUtil.logDebugMsg("Registering MushroomBlockSets for " + MiddleEarth.MOD_ID);
    }
}
