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


    public record MushroomBlockSet(Block stem, Block stemWall,
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
            stem = ModBlocks.registerWoodBlock(name + "_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));
        }

        Block stemWall = ModBlocks.registerWoodBlock(name + "_stem_wall", new WallBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block planks = ModBlocks.registerWoodBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)));

        Block verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block fence = ModBlocks.registerWoodBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block gate = ModBlocks.registerWoodBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD), WoodType.OAK));

        Block button = ModBlocks.registerWoodBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true));

        Block pressurePlate = ModBlocks.registerWoodBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block door = ModBlocks.registerWoodBlock(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block trapdoor = ModBlocks.registerWoodBlock(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.OAK));

        Block stool = ModBlocks.registerBlock(name + "_stool", new StoolBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        Block table = ModBlocks.registerBlock(name + "_table", new TableBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        Block chair = ModBlocks.registerBlock(name + "_chair", new WoodChairBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new MushroomBlockSet(stem, stemWall, planks, slab, verticalSlab, stairs, fence, gate, pressurePlate, button, door, trapdoor, stool, table, chair);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering MushroomBlockSets for " + MiddleEarth.MOD_ID);
    }
}
