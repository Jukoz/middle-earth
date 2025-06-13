package net.sevenstars.middleearth.block.utils;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.StoneChairBlock;
import net.sevenstars.middleearth.block.special.StoneTableBlock;
import net.sevenstars.middleearth.block.special.StoolBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

import java.util.function.Function;

public class StoneBlockSetCreation {

    public static BlockRecordTypes.BaseStoneSet createMainStoneSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, Block source) {
        Block base;
        if (source != null){
            base = source;
        } else {
            base = getVanillaOrNew(name, Block::new,
                    AbstractBlock.Settings.create().strength(hardness, blastResistance)
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).requiresTool(),false);
        }

        Block slab = getVanillaOrNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base),false);

        Block verticalSlab = getVanillaOrNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base),false);

        Block stairs = getVanillaOrNew(name + "_stairs", (settings) ->  new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base),false);

        Block wall = getVanillaOrNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base),false);

        Block pressurePlate = getVanillaOrNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).noCollision(),false);

        Block button = getVanillaOrNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.STONE, 20, settings), AbstractBlock.Settings.copy(base).noCollision(),false);

        Block trapdoor = getVanillaOrNew(name + "_trapdoor", (settings) -> new TrapdoorBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).nonOpaque(),false);

        Block rocks = getVanillaOrNew(name + "_rocks", RocksBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(),false);

        Block stool = getVanillaOrNew(name + "_stool", StoolBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(),false);

        Block table = getVanillaOrNew(name + "_table", StoneTableBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(),false);

        Block chair = getVanillaOrNew(name + "_chair", StoneChairBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(),false);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new BlockRecordTypes.BaseStoneSet(source, base, slab, verticalSlab, stairs, wall, pressurePlate, button, trapdoor, stool, table, chair, rocks);
    }

    public static BlockRecordTypes.RegularSet createStoneSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean pillar) {
        Block base;
        if(pillar){
            base = getVanillaOrNew(name, PillarBlock::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(),false);
        }else{
            base = getVanillaOrNew(name, Block::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(),false);
        }

        Block slab = getVanillaOrNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        Block verticalSlab = getVanillaOrNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        Block stairs = getVanillaOrNew(name + "_stairs", (settings) -> new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        Block wall = getVanillaOrNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.PillarSet createStonePillarSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block base = getVanillaOrNew(name, PillarBlock::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(),false);

        Block verticalSlab = getVanillaOrNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        Block wall = getVanillaOrNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(),false);

        return new BlockRecordTypes.PillarSet(base, verticalSlab, wall);
    }

    public static BlockRecordTypes.PillarSet createStoneChiseledSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup) {

        Block block = getVanillaOrNew("chiseled_" + name, PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(),false);

        Block verticalSlab = getVanillaOrNew("chiseled_" + name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool(),false);

        Block wall = getVanillaOrNew("chiseled_" + name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool(),false);

        return new BlockRecordTypes.PillarSet(block, verticalSlab, wall);
    }

    private static Block getVanillaOrNew(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean drop){
        Block block;
        if (Registries.BLOCK.get(Identifier.ofVanilla(path)) == Blocks.AIR){
            block = ModBlocks.registerStoneBlock(path, factory, settings, drop);
        } else {
            block = Registries.BLOCK.get(Identifier.ofVanilla(path));
        }
        return block;
    }
}