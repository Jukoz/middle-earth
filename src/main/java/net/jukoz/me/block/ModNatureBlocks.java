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

    public static final Block DRY_GRASS = registerBlock("dry_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block DYING_GRASS = registerBlock("dying_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block GRASS_TUFT = registerBlock("grass_tuft",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block HEATHER = registerBlock("heather",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block HEATHER_BUSH = registerBlock("heather_bush",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block RED_HEATHER = registerBlock("red_heather",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block WHEATGRASS = registerBlock("wheatgrass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block WILD_GRASS = registerBlock("wild_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block WILDERGRASS = registerBlock("wildergrass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);

    public static final Block BEACH_GRASS = registerBlock("beach_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block COASTAL_PANIC_GRASS = registerBlock("coastal_panic_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), false);

    public static final Block HOROKAKA = registerBlock("horokaka",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN)), false);
    public static final Block GIANT_HOROKAKA = registerBlock("giant_horokaka",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN)), false);

    public static final Block BROWN_BOLETE = registerBlock("brown_bolete",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block CAVE_AMANITA = registerBlock("cave_amanita",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block DEEP_FIRECAP = registerBlock("deep_firecap",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block GHOSTSHROOM = registerBlock("ghostshroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block MORSEL = registerBlock("morsel",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block SKY_FIRECAP = registerBlock("sky_firecap",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block TRUMPET_SHROOM = registerBlock("trumpet_shroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block TALL_TRUMPET_SHROOM = registerBlock("tall_trumpet_shroom",
            new TallWildCropBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM)), false);
    public static final Block TUBESHRROM = registerBlock("tubeshroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block TALL_TUBESHROOM = registerBlock("tall_tubeshroom",
            new TallWildCropBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM)), false);
    public static final Block VIOLET_CAPS = registerBlock("violet_caps",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);
    public static final Block YELLOW_AMANITA = registerBlock("yellow_amanita",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM), null), false);

    public static final Block BROWN_BOLETE_TILLER = registerBlock("brown_bolete_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block CAVE_AMANITA_TILLER = registerBlock("cave_amanita_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block DEEP_FIRECAP_TILLER = registerBlock("deep_firecap_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block GHOSTSHROOM_TILLER = registerBlock("ghostshroom_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block MORSEL_TILLER = registerBlock("morsel_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block SKY_FIRECAP_TILLER = registerBlock("sky_firecap_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block VIOLET_CAPS_TILLER = registerBlock("violet_caps_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block WHITE_MUSHROOM_TILLER = registerBlock("white_mushroom_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);
    public static final Block YELLOW_AMANITA_TILLER = registerBlock("yellow_amanita_tiller",
            new FlowerbedBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)), false);

    public static final Block BROWN_BOLETE_BLOCK = registerBlock("brown_bolete_block",
            new MushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)), false);
    public static final Block CAVE_AMANITA_BLOCK = registerBlock("cave_amanita_block",
            new MushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)), false);
    public static final Block DEEP_FIRECAP_BLOCK = registerBlock("deep_firecap_block",
            new MushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)), false);
    public static final Block SKY_FIRECAP_BLOCK = registerBlock("sky_firecap_block",
            new MushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)), false);
    public static final Block YELLOW_AMANITA_BLOCK = registerBlock("yellow_amanita_block",
            new MushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)), false);

    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), false);

    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            new StrawBerryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), true);

    public static final Block WILD_PIPEWEED = registerBlock("wild_pipeweed",
            new TallWildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_FLAX = registerBlock("wild_flax",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_TOMATO = registerBlock("wild_tomato",
            new TallWildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_BELL_PEPPER = registerBlock("wild_bell_pepper",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_CUCUMBER = registerBlock("wild_cucumber",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_GARLIC = registerBlock("wild_garlic",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_ONION = registerBlock("wild_onion",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_LETTUCE = registerBlock("wild_lettuce",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_LEEK = registerBlock("wild_leek",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_POTATO = registerBlock("wild_potato",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_CARROT = registerBlock("wild_carrot",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);
    public static final Block WILD_BEETROOT = registerBlock("wild_beetroot",
            new WildCropBlock(FabricBlockSettings.copyOf(Blocks.GRASS).sounds(BlockSoundGroup.CROP)), false);

    public static final Block MIRKWOOD_ROOTS = registerBlock("mirkwood_roots",
            new MangroveRootsBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_ROOTS)), false);

    public static final Block MIRKWOOD_HANGING_ROOTS = registerBlock("mirkwood_hanging_roots",
            new HangingRootsBlock(FabricBlockSettings.copyOf(Blocks.HANGING_ROOTS)), false);

    public static final Block MIRKWOOD_SPIDER_EGG = registerBlock("mirkwood_spider_egg",
            new MirkwoodSpiderEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)), false);

    public static final Block GLOWWORM_WEBBING = registerBlock("glowworm_webbing",
            new GlowWormBottomBlock(FabricBlockSettings.copyOf(Blocks.WEEPING_VINES_PLANT).luminance(4)), false);
    public static final Block GLOWWORM_MAIN = registerBlock("glowworm_main",
            new GlowWormBlock(FabricBlockSettings.copyOf(Blocks.WEEPING_VINES).luminance(4)), true);

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
