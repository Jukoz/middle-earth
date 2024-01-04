package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.StoolBlock;
import net.jukoz.me.block.special.TableBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
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
                                 Block pressurePlate, Block button, Block door, Block trapdoor, Block stool, Block table) {
    }

    public static MushroomBlockSets.MushroomBlockSet[] sets = new MushroomBlockSets.MushroomBlockSet[] {
            DARK_MUSHROOM,
            GRAY_MUSHROOM,
            MUSHROOM,
    };

    private static MushroomBlockSets.MushroomBlockSet registerMushroomSet(String name, Block stem) {

        if(stem == null){
            stem = ModBlocks.registerBlock(name + "_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));
        }

        Block stemWall = ModBlocks.registerBlock(name + "_stem_wall", new WallBlock(AbstractBlock.Settings.copy(stem).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block planks = ModBlocks.registerBlock(name + "_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH, ModBlocks.SLAB_RESISTANCE).sounds(BlockSoundGroup.WOOD)));

        Block verticalSlab = ModBlocks.registerBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(planks.getDefaultState(),
                FabricBlockSettings.copyOf(planks).strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block fence = ModBlocks.registerBlock(name + "_fence", new FenceBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD)));

        Block gate = ModBlocks.registerBlock(name + "_fence_gate",  new FenceGateBlock(FabricBlockSettings.copyOf(planks)
                .strength(MushroomBlockSets.MUSHROOM_STRENGTH).sounds(BlockSoundGroup.WOOD), WoodType.OAK));

        Block button = ModBlocks.registerBlock(name + "_button",  new ButtonBlock(FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK, 5, true));

        Block pressurePlate = ModBlocks.registerBlock(name + "_pressure_plate",  new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                FabricBlockSettings.copyOf(planks).strength(PLATE_BUTTON_STRENGTH).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block door = ModBlocks.registerBlock(name + "_door", new DoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD), BlockSetType.OAK));

        Block trapdoor = ModBlocks.registerBlock(name + "_trapdoor", new TrapdoorBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque(), BlockSetType.OAK));

        Block stool = ModBlocks.registerBlock(name + "_wood_stool", new StoolBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        Block table = ModBlocks.registerBlock(name + "_wood_table", new TableBlock(FabricBlockSettings.copyOf(planks)
                .sounds(BlockSoundGroup.WOOD).nonOpaque()));

        return new MushroomBlockSet(stem, stemWall, planks, slab, verticalSlab, stairs, fence, gate, pressurePlate, button, door, trapdoor, stool, table);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering MushroomBlockSets for " + MiddleEarth.MOD_ID);
    }
}
