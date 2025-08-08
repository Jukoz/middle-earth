package net.sevenstars.middleearth.block.utils;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.TransparentVerticalSlab;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class BlockSetRegistration {

    public static BlockRecordTypes.RegularSet createRegularSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean pillar, List<ItemStack> group, boolean requiresTool) {
        Block base;

        AbstractBlock.Settings baseSettings;
        if (requiresTool){
            baseSettings = AbstractBlock.Settings.create()
                    .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool();
        } else {
            baseSettings = AbstractBlock.Settings.create()
                    .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance);
        }

        if(pillar){
            base = getVanillaOrCreateNew(name, PillarBlock::new,
                    baseSettings, group);
        }else{
            base = getVanillaOrCreateNew(name, Block::new,
                    baseSettings, group);
        }

        name = name.replaceAll("_bricks", "_brick");
        name = name.replaceAll("_tiles", "_tile");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                baseSettings, group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                baseSettings, group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairsBlock(
                base.getDefaultState(), settings), baseSettings, group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                baseSettings, group);

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.RegularSet createOxidizableSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean pillar, List<ItemStack> group, boolean requiresTool, Oxidizable.OxidationLevel level) {
        Block base;

        AbstractBlock.Settings baseSettings;
        if (requiresTool){
            baseSettings = AbstractBlock.Settings.create()
                    .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool();
        } else {
            baseSettings = AbstractBlock.Settings.create()
                    .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance);
        }

        if(pillar){
            base = getVanillaOrCreateNew(name, PillarBlock::new,
                    baseSettings, group);
        }else{
            base = getVanillaOrCreateNew(name, (settings) -> new OxidizableBlock(level, settings),
                    baseSettings, group);
        }

        name = name.replaceAll("_bricks", "_brick");
        name = name.replaceAll("_tiles", "_tile");

        Block slab = getVanillaOrCreateNew(name + "_slab",(settings) -> new OxidizableSlabBlock(level, settings),
                baseSettings, group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab",(settings) -> new OxidizableVerticalSlabBlock(level, settings),
                baseSettings, group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new OxidizableStairsBlock(
                level, base.getDefaultState(), settings), baseSettings, group);

        Block wall = getVanillaOrCreateNew(name + "_wall",(settings) -> new OxidizableWallBlock(level, settings),
                baseSettings, group);

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.WoodSet createWoodSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block log = getVanillaOrCreateNew(name + "_log", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        Block wood = getVanillaOrCreateNew(name + "_wood", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_wood_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_wood_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_wood_stairs", (settings) -> new StairsBlock(
                wood.getDefaultState(), settings), AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_wood_wall", WallBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_wood_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        FlammableBlockRegistry.getDefaultInstance().add(log, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(wood, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(fence, 5, 5);

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(slab, 150);
            builder.add(stairs, 300);
            builder.add(verticalSlab, 150);
            builder.add(wall, 300);
            builder.add(fence, 300);
        }));

        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.WoodSet createStemSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block log = getVanillaOrCreateNew(name + "_stem", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        Block wood = getVanillaOrCreateNew(name + "_hyphae", PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_hyphae_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_hyphae_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_hyphae_stairs", (settings) -> new StairsBlock(
                wood.getDefaultState(), settings), AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_hyphae_wall", WallBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_hyphae_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.MushroomStemSet createMushroomStemSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block stem = getVanillaOrCreateNew(name + "_stem", MushroomBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_stem_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_stem_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_stem_stairs", (settings) -> new StairsBlock(
                stem.getDefaultState(), settings), AbstractBlock.Settings.copy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_stem_wall", WallBlock::new,
                AbstractBlock.Settings.copy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_stem_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        return new BlockRecordTypes.MushroomStemSet(stem, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.PlanksSet createPlanksSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block base = getVanillaOrCreateNew(name, Block::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance), group);

        name = name.replaceAll("_planks", "");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_fence", FenceBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance), group);

        Block gate = getVanillaOrCreateNew(name + "_fence_gate", (settings) -> new FenceGateBlock(
                WoodType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance), group);

        FlammableBlockRegistry.getDefaultInstance().add(base, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(fence, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(gate, 5, 20);

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(slab, 150);
            builder.add(verticalSlab, 150);
            builder.add(stairs, 300);
            builder.add(fence, 300);
            builder.add(gate, 300);
        }));

        return new BlockRecordTypes.PlanksSet(base, slab, verticalSlab, stairs, fence, gate);
    }

    public static BlockRecordTypes.WoodRedstoneBlocks createWoodRedstoneSet(String name, float hardness, float blastResistance, MapColor mapColor, BlockSoundGroup soundGroup, Block base, List<ItemStack> group) {

        Block door = getVanillaOrCreateNew(name + "_door", (settings) -> new DoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(), group);

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapdoorBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).mapColor(mapColor).sounds(soundGroup).nonOpaque(), group);

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.OAK, settings), AbstractBlock.Settings.copy(base).strength(0.5f, blastResistance).mapColor(mapColor).sounds(soundGroup).noCollision(), group);

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.OAK, 30, settings), AbstractBlock.Settings.copy(base).strength(0.5f, blastResistance).mapColor(mapColor).sounds(soundGroup).noCollision().pistonBehavior(PistonBehavior.DESTROY), group);

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(button, 100);
            builder.add(pressurePlate, 300);
            builder.add(door, 200);
            builder.add(trapdoor, 200);
        }));

        return new BlockRecordTypes.WoodRedstoneBlocks(door, trapdoor, pressurePlate, button);
    }

    public static BlockRecordTypes.WoodFurnitureBlocks createWoodFurnitureSet(String name, float hardness, float blastResistance, MapColor mapColor, BlockSoundGroup soundGroup, Block base, List<ItemStack> group) {

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

        FlammableBlockRegistry.getDefaultInstance().add(stool, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(bench, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(table, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(chair, 5, 20);

        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(table, 300);
            builder.add(chair, 300);
            builder.add(bench, 300);
            builder.add(stool, 300);
        }));

        return new BlockRecordTypes.WoodFurnitureBlocks(table, chair, stool, bench, ladder);
    }

    public static BlockRecordTypes.BaseStoneSet createMainStoneSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {
        if (Objects.equals(name, "dripstone"))
        {
            name = "dripstone_block";
        }

        Block base = getVanillaOrCreateNew(name, Block::new,
                AbstractBlock.Settings.create().strength(hardness, blastResistance)
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).requiresTool(), group);

        name = name.replaceAll("dripstone_block", "dripstone");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                AbstractBlock.Settings.copy(base), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base), group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) ->  new StairsBlock(
                base.getDefaultState(), settings), AbstractBlock.Settings.copy(base), group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base), group);

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).noCollision(), group);

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.STONE, 20, settings), AbstractBlock.Settings.copy(base).noCollision(), group);

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapdoorBlock(
                BlockSetType.STONE, settings), AbstractBlock.Settings.copy(base).nonOpaque(), group);

        Block rocks = getVanillaOrCreateNew(name + "_rocks", RocksBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(), group);

        Block stool = getVanillaOrCreateNew(name + "_stool", StoolBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(), group);

        Block table = getVanillaOrCreateNew(name + "_table", StoneTableBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(), group);

        Block chair = getVanillaOrCreateNew(name + "_chair", StoneChairBlock::new,
                AbstractBlock.Settings.copy(base).nonOpaque(), group);

        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultStack());
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultStack());

        return new BlockRecordTypes.BaseStoneSet(base, slab, verticalSlab, stairs, wall, pressurePlate, button, trapdoor, stool, table, chair, rocks);
    }

    public static BlockRecordTypes.PillarSet createStonePillarSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block base = getVanillaOrCreateNew(name, PillarBlock::new,
                    AbstractBlock.Settings.create()
                            .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(), group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(base).strength(hardness, blastResistance).requiresTool(), group);

        return new BlockRecordTypes.PillarSet(base, verticalSlab, wall);
    }

    public static BlockRecordTypes.PillarSet createStoneChiseledSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {

        Block block = getVanillaOrCreateNew("chiseled_" + name, PillarBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool(), group);

        Block verticalSlab = getVanillaOrCreateNew("chiseled_" + name + "_vertical_slab", VerticalSlabBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool(), group);

        Block wall = getVanillaOrCreateNew("chiseled_" + name + "_wall", WallBlock::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool(), group);

        return new BlockRecordTypes.PillarSet(block, verticalSlab, wall);
    }

    public static BlockRecordTypes.CarvedWindow createCarvedWindowSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, List<ItemStack> group) {
        Block block = getVanillaOrCreateNew(name, TransparentBlock::new,
                AbstractBlock.Settings.create()
                        .mapColor(mapColor).instrument(instrument).sounds(soundGroup).strength(hardness, blastResistance).requiresTool()
                        .nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", TransparentVerticalSlab::new,
                AbstractBlock.Settings.copy(block).strength(hardness, blastResistance).requiresTool()
                        .nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never), group);

        return new BlockRecordTypes.CarvedWindow(block, verticalSlab);
    }

    public static Block getVanillaOrCreateNew(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, List<ItemStack> group){
        if (Registries.BLOCK.get(Identifier.ofVanilla(path)) == Blocks.AIR){
            return ModBlocks.registerBlock(path, factory, settings, false, group);
        } else {
            return Registries.BLOCK.get(Identifier.ofVanilla(path));
        }
    }
}