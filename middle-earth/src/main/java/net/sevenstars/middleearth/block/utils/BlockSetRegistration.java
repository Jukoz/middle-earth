package net.sevenstars.middleearth.block.utils;

import net.minecraft.world.level.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.TransparentVerticalSlab;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class BlockSetRegistration {

    public static BlockRecordTypes.RegularSet createRegularSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean pillar, List<ItemStack> group, boolean requiresTool) {
        Block base;

        BlockBehaviour.Properties baseSettings;
        if (requiresTool){
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops();
        } else {
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance);
        }

        if(pillar){
            base = getVanillaOrCreateNew(name, RotatedPillarBlock::new,
                    baseSettings, group);
        }else{
            base = getVanillaOrCreateNew(name, Block::new,
                    baseSettings, group);
        }

        name = name.replaceAll("_bricks", "_brick");
        name = name.replaceAll("_tiles", "_tile");
        name = name.replaceAll("_block", "");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                baseSettings, group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                baseSettings, group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairBlock(
                base.defaultBlockState(), settings), baseSettings, group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                baseSettings, group);

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.SimpleBlocks createSimpleSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean pillar, List<ItemStack> group, boolean requiresTool) {
        Block base;

        BlockBehaviour.Properties baseSettings;
        if (requiresTool){
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops();
        } else {
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance);
        }

        base = getVanillaOrCreateNew(name, Block::new, baseSettings, group);

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                baseSettings, group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                baseSettings, group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairBlock(
                base.defaultBlockState(), settings), baseSettings, group);

        return new BlockRecordTypes.SimpleBlocks(base, slab, verticalSlab, stairs);
    }

    public static BlockRecordTypes.RegularSet createOxidizableSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean pillar, List<ItemStack> group, boolean requiresTool, WeatheringCopper.WeatherState level) {
        Block base;

        BlockBehaviour.Properties baseSettings;
        if (requiresTool){
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops();
        } else {
            baseSettings = BlockBehaviour.Properties.of()
                    .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance);
        }

        if(pillar){
            base = getVanillaOrCreateNew(name, RotatedPillarBlock::new,
                    baseSettings, group);
        }else{
            base = getVanillaOrCreateNew(name, (settings) -> new WeatheringCopperFullBlock(level, settings),
                    baseSettings, group);
        }

        name = name.replaceAll("_bricks", "_brick");
        name = name.replaceAll("_tiles", "_tile");

        Block slab = getVanillaOrCreateNew(name + "_slab",(settings) -> new WeatheringCopperSlabBlock(level, settings),
                baseSettings, group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab",(settings) -> new OxidizableVerticalSlabBlock(level, settings),
                baseSettings, group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new WeatheringCopperStairBlock(
                level, base.defaultBlockState(), settings), baseSettings, group);

        Block wall = getVanillaOrCreateNew(name + "_wall",(settings) -> new OxidizableWallBlock(level, settings),
                baseSettings, group);

        return new BlockRecordTypes.RegularSet(base, slab, verticalSlab, stairs, wall);
    }

    public static BlockRecordTypes.WoodSet createWoodSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block log = getVanillaOrCreateNew(name + "_log", RotatedPillarBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        Block wood = getVanillaOrCreateNew(name + "_wood", RotatedPillarBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_wood_slab", SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_wood_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_wood_stairs", (settings) -> new StairBlock(
                wood.defaultBlockState(), settings), BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_wood_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_wood_fence", FenceBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        BlockDataMapCollector.registerFlammable(log, 5, 5);
        BlockDataMapCollector.registerFlammable(wood, 5, 5);
        BlockDataMapCollector.registerFlammable(slab, 5, 5);
        BlockDataMapCollector.registerFlammable(verticalSlab, 5, 5);
        BlockDataMapCollector.registerFlammable(stairs, 5, 5);
        BlockDataMapCollector.registerFlammable(wall, 5, 5);
        BlockDataMapCollector.registerFlammable(fence, 5, 5);

        BlockDataMapCollector.registerFuel(slab, 150);
        BlockDataMapCollector.registerFuel(stairs, 300);
        BlockDataMapCollector.registerFuel(verticalSlab, 150);
        BlockDataMapCollector.registerFuel(wall, 300);
        BlockDataMapCollector.registerFuel(fence, 300);

        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.WoodSet createStemSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block log = getVanillaOrCreateNew(name + "_stem", RotatedPillarBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        Block wood = getVanillaOrCreateNew(name + "_hyphae", RotatedPillarBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_hyphae_slab", SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_hyphae_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_hyphae_stairs", (settings) -> new StairBlock(
                wood.defaultBlockState(), settings), BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_hyphae_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_hyphae_fence", FenceBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(mapColor).strength(hardness, blastResistance), group);

        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.MushroomStemSet createMushroomStemSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block stem = getVanillaOrCreateNew(name + "_stem", HugeMushroomBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        Block slab = getVanillaOrCreateNew(name + "_stem_slab", SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_stem_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_stem_stairs", (settings) -> new StairBlock(
                stem.defaultBlockState(), settings), BlockBehaviour.Properties.ofFullCopy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block wall = getVanillaOrCreateNew(name + "_stem_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_stem_fence", FenceBlock::new,
                BlockBehaviour.Properties.ofFullCopy(stem).mapColor(mapColor).strength(hardness, blastResistance), group);

        return new BlockRecordTypes.MushroomStemSet(stem, slab, verticalSlab, stairs, wall, fence);
    }

    public static BlockRecordTypes.PlanksSet createPlanksSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block base = getVanillaOrCreateNew(name, Block::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance), group);

        name = name.replaceAll("_planks", "");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance), group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) -> new StairBlock(
                base.defaultBlockState(), settings), BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance), group);

        Block fence = getVanillaOrCreateNew(name + "_fence", FenceBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance), group);

        Block gate = getVanillaOrCreateNew(name + "_fence_gate", (settings) -> new FenceGateBlock(
                WoodType.OAK, settings), BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance), group);

        BlockDataMapCollector.registerFlammable(base, 5, 20);
        BlockDataMapCollector.registerFlammable(slab, 5, 20);
        BlockDataMapCollector.registerFlammable(verticalSlab, 5, 20);
        BlockDataMapCollector.registerFlammable(stairs, 5, 20);
        BlockDataMapCollector.registerFlammable(fence, 5, 20);
        BlockDataMapCollector.registerFlammable(gate, 5, 20);

        BlockDataMapCollector.registerFuel(slab, 150);
        BlockDataMapCollector.registerFuel(verticalSlab, 150);
        BlockDataMapCollector.registerFuel(stairs, 300);
        BlockDataMapCollector.registerFuel(fence, 300);
        BlockDataMapCollector.registerFuel(gate, 300);

        return new BlockRecordTypes.PlanksSet(base, slab, verticalSlab, stairs, fence, gate);
    }

    public static BlockRecordTypes.WoodRedstoneBlocks createWoodRedstoneSet(String name, float hardness, float blastResistance, MapColor mapColor, SoundType soundGroup, Block base, List<ItemStack> group) {

        Block door = getVanillaOrCreateNew(name + "_door", (settings) -> new DoorBlock(
                BlockSetType.OAK, settings), BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(), group);

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapDoorBlock(
                BlockSetType.OAK, settings), BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(), group);

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.OAK, settings), BlockBehaviour.Properties.ofFullCopy(base).strength(0.5f, blastResistance).mapColor(mapColor).sound(soundGroup).noCollission(), group);

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.OAK, 30, settings), BlockBehaviour.Properties.ofFullCopy(base).strength(0.5f, blastResistance).mapColor(mapColor).sound(soundGroup).noCollission().pushReaction(PushReaction.DESTROY), group);

        BlockDataMapCollector.registerFuel(button, 100);
        BlockDataMapCollector.registerFuel(pressurePlate, 300);
        BlockDataMapCollector.registerFuel(door, 200);
        BlockDataMapCollector.registerFuel(trapdoor, 200);

        return new BlockRecordTypes.WoodRedstoneBlocks(door, trapdoor, pressurePlate, button);
    }

    public static BlockRecordTypes.WoodFurnitureBlocks createWoodFurnitureSet(String name, float hardness, float blastResistance, MapColor mapColor, SoundType soundGroup, Block base, List<ItemStack> group) {

        Block table = ModBlocks.registerWoodBlock(name + "_table", WoodTableBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(),false);

        Block chair = ModBlocks.registerWoodBlock(name + "_chair", WoodChairBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(),false);

        Block stool = ModBlocks.registerWoodBlock(name + "_stool", WoodStoolBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(),false);

        Block bench = ModBlocks.registerWoodBlock(name + "_bench", WoodBenchBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).mapColor(mapColor).sound(soundGroup).noOcclusion(),false);

        Block ladder = ModBlocks.registerWoodBlock(name + "_ladder", ThickLadderBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).sound(SoundType.LADDER).noOcclusion(),false);

        BlockDataMapCollector.registerFlammable(stool, 5, 20);
        BlockDataMapCollector.registerFlammable(bench, 5, 20);
        BlockDataMapCollector.registerFlammable(table, 5, 20);
        BlockDataMapCollector.registerFlammable(chair, 5, 20);

        BlockDataMapCollector.registerFuel(table, 300);
        BlockDataMapCollector.registerFuel(chair, 300);
        BlockDataMapCollector.registerFuel(bench, 300);
        BlockDataMapCollector.registerFuel(stool, 300);

        return new BlockRecordTypes.WoodFurnitureBlocks(table, chair, stool, bench, ladder);
    }

    public static BlockRecordTypes.BaseStoneSet createMainStoneSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {
        if (Objects.equals(name, "dripstone"))
        {
            name = "dripstone_block";
        }

        Block base = getVanillaOrCreateNew(name, Block::new,
                BlockBehaviour.Properties.of().strength(hardness, blastResistance)
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).requiresCorrectToolForDrops(), group);

        name = name.replaceAll("dripstone_block", "dripstone");

        Block slab = getVanillaOrCreateNew(name + "_slab", SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base), group);

        Block stairs = getVanillaOrCreateNew(name + "_stairs", (settings) ->  new StairBlock(
                base.defaultBlockState(), settings), BlockBehaviour.Properties.ofFullCopy(base), group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base), group);

        Block pressurePlate = getVanillaOrCreateNew(name + "_pressure_plate", (settings) -> new PressurePlateBlock(
                BlockSetType.STONE, settings), BlockBehaviour.Properties.ofFullCopy(base).noCollission(), group);

        Block button = getVanillaOrCreateNew(name + "_button", (settings) -> new ButtonBlock(
                BlockSetType.STONE, 20, settings), BlockBehaviour.Properties.ofFullCopy(base).noCollission(), group);

        Block trapdoor = getVanillaOrCreateNew(name + "_trapdoor", (settings) -> new TrapDoorBlock(
                BlockSetType.STONE, settings), BlockBehaviour.Properties.ofFullCopy(base).noOcclusion(), group);

        Block rocks = getVanillaOrCreateNew(name + "_rocks", RocksBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).noOcclusion(), group);

        Block stool = getVanillaOrCreateNew(name + "_stool", StoolBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).noOcclusion(), group);

        Block table = getVanillaOrCreateNew(name + "_table", StoneTableBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).noOcclusion(), group);

        Block chair = getVanillaOrCreateNew(name + "_chair", StoneChairBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).noOcclusion(), group);

        ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(stool.asItem().getDefaultInstance());
        ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(table.asItem().getDefaultInstance());
        ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(chair.asItem().getDefaultInstance());

        return new BlockRecordTypes.BaseStoneSet(base, slab, verticalSlab, stairs, wall, pressurePlate, button, trapdoor, stool, table, chair, rocks);
    }

    public static BlockRecordTypes.PillarSet createStonePillarSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block base = getVanillaOrCreateNew(name, RotatedPillarBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        Block wall = getVanillaOrCreateNew(name + "_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(base).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        return new BlockRecordTypes.PillarSet(base, verticalSlab, wall);
    }

    public static BlockRecordTypes.PillarSet createStoneChiseledSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {

        Block block = getVanillaOrCreateNew("chiseled_" + name, RotatedPillarBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        Block verticalSlab = getVanillaOrCreateNew("chiseled_" + name + "_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(block).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        Block wall = getVanillaOrCreateNew("chiseled_" + name + "_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(block).strength(hardness, blastResistance).requiresCorrectToolForDrops(), group);

        return new BlockRecordTypes.PillarSet(block, verticalSlab, wall);
    }

    public static BlockRecordTypes.CarvedWindow createCarvedWindowSet(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, List<ItemStack> group) {
        Block block = getVanillaOrCreateNew(name, TransparentBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor).instrument(instrument).sound(soundGroup).strength(hardness, blastResistance).requiresCorrectToolForDrops()
                        .noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never), group);

        Block verticalSlab = getVanillaOrCreateNew(name + "_vertical_slab", TransparentVerticalSlab::new,
                BlockBehaviour.Properties.ofFullCopy(block).strength(hardness, blastResistance).requiresCorrectToolForDrops()
                        .noOcclusion().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never), group);

        return new BlockRecordTypes.CarvedWindow(block, verticalSlab);
    }

    public static Block getVanillaOrCreateNew(String path, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, List<ItemStack> group){
        if (BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(path)) == Blocks.AIR){
            return ModBlocks.registerBlock(path, factory, settings, false, group);
        } else {
            return BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(path));
        }
    }
}
