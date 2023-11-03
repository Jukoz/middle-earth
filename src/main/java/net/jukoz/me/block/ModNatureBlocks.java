package net.jukoz.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.crop.*;
import net.jukoz.me.block.special.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import static net.jukoz.me.block.WoodBlockSets.LEAVES_STRENGTH;

public class ModNatureBlocks {
    public static final Block TOUGH_BERRY_BUSH = registerBlock("tough_berry_bush",
            new ToughBerryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), true);
    public static final Block MORDOR_LICHEN = registerBlock("mordor_lichen",
            new MordorPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GRAY)), false);
    public static final Block MORDOR_LICHEN_FAN = registerBlock("mordor_lichen_fan",
            new MordorPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ)
                    .mapColor(DyeColor.GRAY)), false);
    public static final Block MORGUL_IVY = registerBlock("morgul_ivy",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).luminance(GlowLichenBlock.getLuminanceSupplier(5)).burnable()), false);
    public static final Block HANGING_COBWEB = registerBlock("hanging_cobweb",
            new HangingCobwebBlock(FabricBlockSettings.copyOf(Blocks.COBWEB).drops(new Identifier(MiddleEarth.MOD_ID, "hanging_cobweb")).strength(2)), false);

    public static final Block CORNER_COBWEB = registerBlock("corner_cobweb",
            new CornerCobwebBlock(FabricBlockSettings.copyOf(Blocks.COBWEB).drops(new Identifier(MiddleEarth.MOD_ID, "corner_cobweb")).strength(2)), false);

    public static final Block CORRUPTED_MOSS_CARPET = registerBlock("corrupted_moss_carpet",
            new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET)), false);

    public static final Block CORRUPTED_MOSS_BLOCK = registerBlock("corrupted_moss_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK)), false);

    public static final Block CORRUPTED_MOSS = registerBlock("corrupted_moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), false);

    public static final Block MOSS = registerBlock("moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), false);

    public static final Block FOREST_MOSS = registerBlock("forest_moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), false);

    public static final Block FOREST_MOSS_CARPET = registerBlock("forest_moss_carpet",
            new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET)), false);

    public static final Block FOREST_MOSS_BLOCK = registerBlock("forest_moss_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK)), false);

    public static final Block MIRKWOOD_PODZOL = registerBlock("mirkwood_podzol",
            new SnowyBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)), false);

    public static final Block BROWN_GRASS = registerBlock("brown_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block GREEN_SHRUB = registerBlock("green_shrub",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block TAN_SHRUB = registerBlock("tan_shrub",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block YELLOW_FLOWER = registerBlock("yellow_flower",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 0, FabricBlockSettings.copyOf(Blocks.DANDELION)), false);

    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);

    public static final Block WHITE_MUSHROOM_TILLER = registerBlock("white_mushroom_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);

    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), false);

    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            new StrawBerryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), true);

    public static final Block MIRKWOOD_ROOTS = registerBlock("mirkwood_roots",
            new MangroveRootsBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_ROOTS)), false);

    public static final Block MIRKWOOD_HANGING_ROOTS = registerBlock("mirkwood_hanging_roots",
            new HangingRootsBlock(FabricBlockSettings.copyOf(Blocks.HANGING_ROOTS)), false);

    public static final Block MIRKWOOD_SPIDER_EGG = registerBlock("mirkwood_spider_egg",
            new MirkwoodSpiderEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)), false);

    public static final Block TOMATO_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "tomato_crop"),
            new TomatoCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block BELL_PEPPER_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "bell_pepper_crop"),
            new BellpepperCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block CUCUMBER_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "cucumber_crop"),
            new CucumberCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block FLAX_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "flax_crop"),
            new FlaxCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block GARLIC_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "garlic_crop"),
            new GarlicCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block LEEK_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "leek_crop"),
            new LeekCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block LETTUCE_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "lettuce_crop"),
            new LettuceCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block ONION_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "onion_crop"),
            new OnionCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block PIPEWEED_CROP = Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, "pipeweed_crop"),
            new PipeweedCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));


    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) ModNatureBlocks.registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
