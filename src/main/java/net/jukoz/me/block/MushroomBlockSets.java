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

public class MushroomBlockSets {
    public static final float MUSHROOM_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.1f;

    public static MushroomBlockSets.MushroomBlockSet DARK_MUSHROOM = registerMushroomSet("dark_mushroom", null);
    public static MushroomBlockSets.MushroomBlockSet GRAY_MUSHROOM = registerMushroomSet("gray_mushroom", null);
    public static MushroomBlockSets.MushroomBlockSet MUSHROOM = registerMushroomSet("mushroom", Blocks.MUSHROOM_STEM);


    public record MushroomBlockSet(Block stem, Block stemWall, Block stemFence,
                                 Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs, Block planksFence, Block planksGate,
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block table, Block chair) {
    }

    public static MushroomBlockSets.MushroomBlockSet[] sets = new MushroomBlockSets.MushroomBlockSet[] {
            DARK_MUSHROOM,
            GRAY_MUSHROOM,
            MUSHROOM,
    };

    private static MushroomBlockSets.MushroomBlockSet registerMushroomSet(String name, Block stem) {

        if(stem == null){
            stem = ModBlocks.registerWoodBlock(name + "_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);
        }

        Block stemWall = ModBlocks.registerWoodBlock(name + "_stem_wall", new WallBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block stemFence = ModBlocks.registerWoodBlock(name + "_stem_fence", new FenceBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)),false);

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)),false);

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD), WoodType.OAK),false);

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true),false);

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK),false);

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK),false);

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.OAK),false);

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block table = ModBlocks.registerBlock(name + "_table", new TableBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new MushroomBlockSet(stem, stemWall, stemFence, planks, slab, verticalSlab, stairs, fence, gate, pressurePlate, button, door, trapdoor, stool, table, chair);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering MushroomBlockSets for " + MiddleEarth.MOD_ID);
    }
}
