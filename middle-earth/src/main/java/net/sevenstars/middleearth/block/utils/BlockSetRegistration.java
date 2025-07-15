package net.sevenstars.middleearth.block.utils;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.TransparentVerticalSlab;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

import java.util.function.Function;

public class BlockSetRegistration {

    public static BlockRecordTypes.RegularSet createRegularSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean pillar) {
        Block base;
        if(pillar){
            base = getVanillaOrCreateNew(name, PillarBlock::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool());
        }else{
            base = getVanillaOrCreateNew(name, Block::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool());
        }

        name = name.replaceAll("_bricks", "_brick");
        name = name.replaceAll("_tiles", "_tile");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.WoodSet createWoodSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block log = getVanillaOrCreateNew(name + "_log", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance));

        Block wood = getVanillaOrCreateNew(name + "_wood", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance));

        Block slab = getVanillaOrCreateNew(name + "_wood_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance));

        Block verticalSlab = getVanillaOrCreateNew(name + "_wood_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance));

        Block stairs = getVanillaOrCreateNew(name + "_wood_stairs", (settings) -> new StairsBlock(
                wood.getDefaultState(), settings), AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance));

        Block wall = getVanillaOrCreateNew(name + "_wood_wall", WallBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance));

        Block fence = getVanillaOrCreateNew(name + "_wood_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance));

        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.PlanksSet createPlanksSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block base = getVanillaOrCreateNew(name, Block::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance));

        name = name.replaceAll("_planks", "");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance));

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance));

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance));

        Block fence = getVanillaOrCreateNew(name + "_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance));

        Block gate = getVanillaOrCreateNew(name + "_fence_gate", (settings) -> new FenceGateBlock(
                WoodType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance));

        return new BlockRecordTypes.PlanksSet(base, slab, verticalSlab, stairs, fence, gate);
    }

    public static BlockRecordTypes.WoodRedstoneBlocks createWoodRedstoneSet(String name, float hardness, float blastResistance, MapColor mapColor, BlockSoundGroup soundGroup, Block base) {

        Block door = getVanillaOrCreateNew(name + "_door", (settings) -> new DoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque());

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapdoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque());

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(0.5f, blastResistance).mapColor(mapColor).sounds(soundGroup).noCollision());

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.OAK, 30, settings), AbstractBlock.Settings.copy(base).strength(0.5f, blastResistance).mapColor(mapColor).sounds(soundGroup).noCollision().pistonBehavior(PistonBehavior.DESTROY));

        return new BlockRecordTypes.WoodRedstoneBlocks(door, trapdoor, pressurePlate, button);
    }

    public static BlockRecordTypes.WoodFurnitureBlocks createWoodFurnitureSet(String name, float hardness, float blastResistance, MapColor mapColor, BlockSoundGroup soundGroup, Block base) {

        Block table = ModBlocks.registerWoodBlock(name + "_table", WoodTableBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(),false);

        Block chair = ModBlocks.registerWoodBlock(name + "_chair", WoodChairBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(),false);

        Block stool = ModBlocks.registerWoodBlock(name + "_stool", WoodStoolBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(),false);

        Block bench = ModBlocks.registerWoodBlock(name + "_bench", WoodBenchBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(),false);

        Block ladder = ModBlocks.registerWoodBlock(name + "_ladder", ThickLadderBlock::new,
                AbstractBlock.Settings.copy(base).sounds(BlockSoundGroup.LADDER).nonOpaque(),false);

        return new BlockRecordTypes.WoodFurnitureBlocks(table, chair, stool, bench, ladder);
    }

    public static BlockRecordTypes.BaseStoneSet createMainStoneSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {
        Block base = getVanillaOrCreateNew(name, Block::new,
                AbstractBlock.Settings.create().strength(hardness, blastResistance)
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).requiresTool());

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base));

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base));

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) ->  new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base));

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base));

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).noCollision());

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.STONE, 20, settings), AbstractBlock.Settings.copy(base).noCollision());

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapdoorBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).nonOpaque());

        Block rocks = getVanillaOrCreateNew(name + "_rocks", RocksBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque());

        Block stool = getVanillaOrCreateNew(name + "_stool", StoolBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque());

        Block table = getVanillaOrCreateNew(name + "_table", StoneTableBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque());

        Block chair = getVanillaOrCreateNew(name + "_chair", StoneChairBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque());

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new BlockRecordTypes.BaseStoneSet(base, slab, verticalSlab, stairs, wall, pressurePlate, button, trapdoor, stool, table, chair, rocks);
    }

    public static BlockRecordTypes.PillarSet createStonePillarSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block base = getVanillaOrCreateNew(name, PillarBlock::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool());

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool());

        return new BlockRecordTypes.PillarSet(base, verticalSlab, wall);
    }

    public static BlockRecordTypes.PillarSet createStoneChiseledSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block block = getVanillaOrCreateNew("chiseled_" + name, PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool());

        Block verticalSlab = getVanillaOrCreateNew("chiseled_" + name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool());

        Block wall = getVanillaOrCreateNew("chiseled_" + name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool());

        return new BlockRecordTypes.PillarSet(block, verticalSlab, wall);
    }

    public static BlockRecordTypes.CarvedWindow createCarvedWindowSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {
        Block block = getVanillaOrCreateNew(name, TransparentBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool()
                        .nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", TransparentVerticalSlab::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool()
                        .nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));

        return new BlockRecordTypes.CarvedWindow(block, verticalSlab);
    }

    public static Block getVanillaOrCreateNew(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        if (Registries.BLOCK.get(Identifier.ofVanilla(path)) == Blocks.AIR){
            return ModBlocks.registerStoneBlock(path, factory, settings, false);
        } else {
            return Registries.BLOCK.get(Identifier.ofVanilla(path));
        }
    }
}