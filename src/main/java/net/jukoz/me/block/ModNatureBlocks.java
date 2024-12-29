package net.jukoz.me.block;

import net.jukoz.me.datageneration.content.models.TintableCrossModel;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.features.tree.ModTreeConfiguredFeatures;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.crop.*;
import net.jukoz.me.block.special.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.world.features.tree.MushroomTreeConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Optional;

import static net.jukoz.me.block.WoodBlockSets.LEAVES_STRENGTH;

public class ModNatureBlocks {

    public static final Block MORGUL_IVY = registerBlock("morgul_ivy",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).luminance(GlowLichenBlock.getLuminanceSupplier(5)).burnable()), true);
    public static final Block HANGING_COBWEB = registerBlock("hanging_cobweb",
            new HangingCobwebBlock(AbstractBlock.Settings.copy(Blocks.COBWEB)), true);

    public static final Block CORNER_COBWEB = registerBlock("corner_cobweb",
            new CornerCobwebBlock(AbstractBlock.Settings.copy(Blocks.COBWEB)), true);

    public static final Block CORRUPTED_MOSS_CARPET = registerBlock("corrupted_moss_carpet",
            new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET)), true);

    public static final Block CORRUPTED_MOSS_BLOCK = registerBlock("corrupted_moss_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK)), true);

    public static final Block CORRUPTED_MOSS = registerBlock("corrupted_moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);

    public static final Block MOSS = registerBlock("moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);

    public static final Block FOREST_MOSS = registerBlock("forest_moss",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);

    public static final Block FOREST_MOSS_CARPET = registerBlock("forest_moss_carpet",
            new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET)), true);

    public static final Block FOREST_MOSS_BLOCK = registerBlock("forest_moss_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK)), true);

    public static final Block OLD_PODZOL = registerBlock("old_podzol",
            new SnowyBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)), true);

    public static final Block LORIEN_PODZOL = registerBlock("lorien_podzol",
            new SnowyBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)), true);

    public static final Block BROWN_GRASS = registerBlock("brown_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block DRY_GRASS = registerBlock("dry_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block DYING_GRASS = registerBlock("dying_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block GRIM_GRASS = registerBlock("grim_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block TEMPERATE_GRASS = registerBlock("temperate_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block GRASS_TUFT = registerBlock("grass_tuft",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block HEATHER = registerBlock("heather",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block RED_HEATHER = registerBlock("red_heather",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block DEAD_HEATHER = registerBlock("dead_heather",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block DRY_HEATHER = registerBlock("dry_heather",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block HEATH = registerBlock("heath",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block WHEATGRASS = registerBlock("wheatgrass",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block WILD_GRASS = registerBlock("wild_grass",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block WILDERGRASS = registerBlock("wildergrass",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block SEDUM = registerBlock("sedum",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block YELLOW_SEDUM = registerBlock("yellow_sedum",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block GREEN_SHRUB = registerBlock("green_shrub",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block SMALL_DRY_SHRUB = registerBlock("small_dry_shrub",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block BEACH_GRASS = registerBlock("beach_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block COASTAL_PANIC_GRASS = registerBlock("coastal_panic_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block SHRIVELED_SHRUB = registerBlock("shriveled_shrub",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN)), true);

    public static final Block DEAD_RUSHES = registerBlock("dead_rushes",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN)), true);
    public static final Block FALSE_OATGRASS = registerBlock("false_oatgrass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN)), true);
    public static final Block BRACKEN = registerBlock("bracken",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block MALLOS = registerBlock("mallos",
            new FlowerBlock(StatusEffects.GLOWING, 3, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN)), true);
    public static final Block ELANOR = registerBlock("elanor",
            new FlowerBlock(StatusEffects.GLOWING, 3, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN)), true);
    public static final Block TAN_SHRUB = registerBlock("tan_shrub",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block YELLOW_FLOWER = registerBlock("yellow_flower",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block GREEN_JEWEL_CORNFLOWER = registerBlock("green_jewel_cornflower",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);

    public static final Block LIGHT_BLUE_FLOWERS = registerBlock("light_blue_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block MAGENTA_FLOWERS = registerBlock("magenta_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block ORANGE_FLOWERS = registerBlock("orange_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block PINK_FLOWERS = registerBlock("pink_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block PURPLE_FLOWERS = registerBlock("purple_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block RED_FLOWERS = registerBlock("red_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block WHITE_FLOWERS = registerBlock("white_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);
    public static final Block YELLOW_FLOWERS = registerBlock("yellow_flowers",
            new FlowerBlock(StatusEffects.SATURATION, 0, AbstractBlock.Settings.copy(Blocks.DANDELION)), true);

    public static final Block HOROKAKA = registerBlock("horokaka",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block GIANT_HOROKAKA = registerBlock("giant_horokaka",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block AZALEA_FLOWER_GROWTH = registerBlock("azalea_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block DRY_GROWTH = registerBlock("dry_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block GREEN_GROWTH = registerBlock("green_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block IVY_GROWTH = registerBlock("ivy_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block LILAC_FLOWER_GROWTH = registerBlock("lilac_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block PINK_FLOWER_GROWTH = registerBlock("pink_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block RED_FLOWER_GROWTH = registerBlock("red_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block WHITE_FLOWER_GROWTH = registerBlock("white_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);
    public static final Block YELLOW_FLOWER_GROWTH = registerBlock("yellow_flower_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);

    public static final Block SHORT_CATTAILS = registerBlock("short_cattails",
            new WaterloggablePlant(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block SHORT_BULRUSH = registerBlock("short_bulrush",
            new WaterloggablePlant(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block TALL_CATTAILS = registerBlock("tall_cattails",
            new CustomWaterloggableTallPlantBlock(AbstractBlock.Settings.copy(Blocks.TALL_GRASS).breakInstantly(), false), true);
    public static final Block TALL_BULRUSH = registerBlock("tall_bulrush",
            new CustomWaterloggableTallPlantBlock(AbstractBlock.Settings.copy(Blocks.TALL_GRASS).breakInstantly(), false), true);

    public static final Block SMALL_LILY_PADS = registerBlock("small_lily_pads",
            new LilyPadBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision()), false);
    public static final Block SMALL_FLOWERING_LILY_PADS = registerBlock("small_flowering_lily_pads",
            new LilyPadBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision()), false);
    public static final Block LILY_PADS = registerBlock("lily_pads",
            new LilyPadBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision()), false);
    public static final Block DUCKWEED = registerBlock("duckweed",
            new LilyPadBlock(AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision()), false);

    public static final Block FROZEN_GRASS = registerBlock("frozen_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block FROZEN_TUFT = registerBlock("frozen_tuft",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block FROZEN_SHRUB = registerBlock("frozen_shrub",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block FROZEN_GROWTH = registerBlock("frozen_growth",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable()), true);

    public static final Block STICKY_SNOW = registerBlock("sticky_snow",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.SNOW)), true);
    public static final Block STICKY_ICE = registerBlock("sticky_ice",
            new GlowLichenBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).replaceable().noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS)), true);

    public static final Block SHORT_ICICLES = registerBlock("short_icicles",
            new ShortIciclesBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS)), true);
    public static final Block DROOPING_ICICLES = registerBlock("drooping_icicles",
            new DroopingIciclesBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS)), true);

    public static final Block FLOATING_ICE = registerBlock("floating_ice",
            new FloatingIceBlock(AbstractBlock.Settings.copy(Blocks.ICE)), false);

    public static final Block SCORCHED_GRASS = registerBlock("scorched_grass",
            new DesertPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block SCORCHED_TUFT = registerBlock("scorched_tuft",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);
    public static final Block SCORCHED_SHRUB = registerBlock("scorched_shrub",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN)), true);

    public static final Block BROWN_BOLETE = registerBlock("brown_bolete",
            new MushroomPlantBlock(MushroomTreeConfiguredFeatures.BROWN_BOLETTE_TREE_KEY, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block CAVE_AMANITA = registerBlock("cave_amanita",
            new MushroomPlantBlock(MushroomTreeConfiguredFeatures.CAVE_AMANITA_TREE_KEY, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block DEEP_FIRECAP = registerBlock("deep_firecap",
            new MushroomPlantBlock(MushroomTreeConfiguredFeatures.DEEP_FIRECAP_TREE_KEY, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block GHOSTSHROOM = registerBlock("ghostshroom",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4)), true);
    public static final Block MORSEL = registerBlock("morsel",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block SKY_FIRECAP = registerBlock("sky_firecap",
            new MushroomPlantBlock(MushroomTreeConfiguredFeatures.SKY_FIRECAP_TREE_KEY, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block TRUMPET_SHROOM = registerBlock("trumpet_shroom",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block TALL_TRUMPET_SHROOM = registerBlock("tall_trumpet_shroom",
            new TallMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block TUBESHRROM = registerBlock("tubeshroom",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4)), true);
    public static final Block TALL_TUBESHROOM = registerBlock("tall_tubeshroom",
            new TallMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 5)), true);
    public static final Block VIOLET_CAPS = registerBlock("violet_caps",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            new MushroomPlantBlock(null, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block YELLOW_AMANITA = registerBlock("yellow_amanita",
            new MushroomPlantBlock(MushroomTreeConfiguredFeatures.YELLOW_AMANITA_TREE_KEY, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);

    public static final Block BROWN_BOLETE_TILLER = registerBlock("brown_bolete_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block CAVE_AMANITA_TILLER = registerBlock("cave_amanita_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block DEEP_FIRECAP_TILLER = registerBlock("deep_firecap_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block GHOSTSHROOM_TILLER = registerBlock("ghostshroom_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4)), true);
    public static final Block MORSEL_TILLER = registerBlock("morsel_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block SKY_FIRECAP_TILLER = registerBlock("sky_firecap_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block VIOLET_CAPS_TILLER = registerBlock("violet_caps_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block WHITE_MUSHROOM_TILLER = registerBlock("white_mushroom_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);
    public static final Block YELLOW_AMANITA_TILLER = registerBlock("yellow_amanita_tiller",
            new FlowerbedMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), true);

    public static final Block BROWN_BOLETE_BLOCK = registerBlock("brown_bolete_block",
            new MushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK)), true);
    public static final Block CAVE_AMANITA_BLOCK = registerBlock("cave_amanita_block",
            new MushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK)), true);
    public static final Block DEEP_FIRECAP_BLOCK = registerBlock("deep_firecap_block",
            new MushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK)), true);
    public static final Block SKY_FIRECAP_BLOCK = registerBlock("sky_firecap_block",
            new MushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK)), true);
    public static final Block YELLOW_AMANITA_BLOCK = registerBlock("yellow_amanita_block",
            new MushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK)), true);

    public static final Block BEECH_SAPLING = registerSimpleSapling("beech_sapling", ModTreeConfiguredFeatures.BEECH_TREE_KEY);
    public static final Block CHESTNUT_SAPLING = registerSimpleSapling("chestnut_sapling", ModTreeConfiguredFeatures.CHESTNUT_TREE_KEY);
    public static final Block HOLLY_SAPLING = registerSimpleSapling("holly_sapling", ModTreeConfiguredFeatures.HOLLY_TREE_KEY);
    public static final Block LARCH_SAPLING = registerSimpleSapling("larch_sapling", ModTreeConfiguredFeatures.LARCH_TREE_KEY);
    public static final Block LEBETHRON_SAPLING = registerSimpleSapling("lebethron_sapling", ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY);
    public static final Block WHITE_LEBETHRON_SAPLING = registerSimpleSapling("white_lebethron_sapling", ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY);
    public static final Block MALLORN_SAPLING = registerSimpleSapling("mallorn_sapling", ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY);
    public static final Block MAPLE_SAPLING = registerSimpleSapling("maple_sapling", ModTreeConfiguredFeatures.MAPLE_TREE_KEY);
    public static final Block SILVER_MAPLE_SAPLING = registerSimpleSapling("silver_maple_sapling", ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY);
    public static final Block MIRKWOOD_SAPLING = registerSimpleSapling("mirkwood_sapling", ModTreeConfiguredFeatures.SMALL_MIRKWOOD_TREE_KEY);
    public static final Block PALM_SAPLING = registerSimpleSapling("palm_sapling", ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY);
    public static final Block WHITE_PALM_SAPLING = registerSimpleSapling("white_palm_sapling", ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY);
    public static final Block PINE_SAPLING = registerSimpleSapling("pine_sapling", ModTreeConfiguredFeatures.PINE_TREE_KEY);
    public static final Block BLACK_PINE_SAPLING = registerSimpleSapling("black_pine_sapling", ModTreeConfiguredFeatures.PINE_TREE_KEY);
    public static final Block WILLOW_SAPLING = registerSimpleSapling("willow_sapling", ModTreeConfiguredFeatures.WILLOW_TREE_KEY);

    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block BERRY_HOLLY_LEAVES = registerBlock("berry_holly_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block DRY_LARCH_LEAVES = registerBlock("dry_larch_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block FLOWERING_MALLORN_LEAVES = registerBlock("flowering_mallorn_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block MAPLE_LEAVES = registerBlock("maple_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);
    public static final Block ORANGE_MAPLE_LEAVES = registerBlock("orange_maple_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);
    public static final Block RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);
    public static final Block YELLOW_MAPLE_LEAVES = registerBlock("yellow_maple_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block DRY_PINE_LEAVES = registerBlock("dry_pine_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);
    public static final Block PINE_BRANCHES = registerBlock("pine_branches",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), true);

    public static final Block FALLEN_LEAVES = registerBlock("fallen_leaves",
            new FallenLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision()), true);
    public static final Block FALLEN_MALLORN_LEAVES = registerBlock("fallen_mallorn_leaves",
            new FallenLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision()), true);
    public static final Block FALLEN_MIRKWOOD_LEAVES = registerBlock("fallen_mirkwood_leaves",
            new FallenLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision()), true);

    public static final Block TOUGH_BERRY_BUSH = registerBlock("tough_berry_bush",
            new ToughBerryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), false);
    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            new StrawBerryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), false);

    public static final Block WILD_PIPEWEED = registerBlock("wild_pipeweed",
            new CustomTallPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true), true);
    public static final Block WILD_FLAX = registerCrossBlock("wild_flax",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_TOMATO = registerBlock("wild_tomato",
            new CustomTallPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true), true);
    public static final Block WILD_BELL_PEPPER = registerCrossBlock("wild_bell_pepper",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_CUCUMBER = registerCrossBlock("wild_cucumber",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_GARLIC = registerCrossBlock("wild_garlic",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_ONION = registerCrossBlock("wild_onion",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_LETTUCE = registerCrossBlock("wild_lettuce",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_LEEK = registerCrossBlock("wild_leek",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_POTATO = registerBlock("wild_potato",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_CARROT = registerBlock("wild_carrot",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);
    public static final Block WILD_BEETROOT = registerBlock("wild_beetroot",
            new WildCropBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP)), true);

    public static final Block TOMATO_CROP = registerBlock("tomato_crop",
            new TomatoCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block BELL_PEPPER_CROP = registerBlock("bell_pepper_crop",
            new BellpepperCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block CUCUMBER_CROP = registerBlock("cucumber_crop",
            new CucumberCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block FLAX_CROP = registerBlock("flax_crop",
            new FlaxCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)), false);
    public static final Block GARLIC_CROP = registerBlock("garlic_crop",
            new GarlicCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block LEEK_CROP = registerBlock("leek_crop",
            new LeekCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block LETTUCE_CROP = registerBlock("lettuce_crop",
            new LettuceCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block ONION_CROP = registerBlock("onion_crop",
            new OnionCropBlock(AbstractBlock.Settings.copy(Blocks.POTATOES)), false);
    public static final Block PIPEWEED_CROP = registerBlock("pipeweed_crop",
            new PipeweedCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)), false);

    public static final Block MIRKWOOD_ROOTS = registerBlock("mirkwood_roots",
            new MangroveRootsBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_ROOTS)), true);
    public static final Block MIRKWOOD_HANGING_ROOTS = registerBlock("mirkwood_hanging_roots",
            new HangingRootsBlock(AbstractBlock.Settings.copy(Blocks.HANGING_ROOTS)), true);

    public static final Block MIRKWOOD_VINES = registerCrossBlock("mirkwood_vines",
            new MirkwoodVinesBottomBlock(AbstractBlock.Settings.copy(Blocks.WEEPING_VINES_PLANT)), true);
    public static final Block MIRKWOOD_VINES_PLANT = registerCrossBlock("mirkwood_vines_plant",
            new MirkwoodVinesBlock(AbstractBlock.Settings.copy(Blocks.WEEPING_VINES)), false);

    public static final Block MIRKWOOD_SPIDER_EGG = registerBlock("mirkwood_spider_egg",
            new MirkwoodSpiderEggBlock(AbstractBlock.Settings.copy(Blocks.TURTLE_EGG)), true);

    public static final Block GLOWWORM_WEBBING = registerCrossBlock("glowworm_webbing",
            new GlowWormBottomBlock(AbstractBlock.Settings.copy(Blocks.WEEPING_VINES_PLANT).luminance(state -> 6)), true);
    public static final Block GLOWWORM_MAIN = registerCrossBlock("glowworm_main",
            new GlowWormBlock(AbstractBlock.Settings.copy(Blocks.WEEPING_VINES).luminance(state -> 6)), false);

    public static Block registerCrossBlock(String name, Block block, boolean present) {
        Block resultBlock = registerBlock(name, block, present);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        return resultBlock;
    }

    public static Block registerSimpleSapling(String name, RegistryKey<ConfiguredFeature<?, ?>> treeFeature) {
        SaplingBlock saplingBlock = new SaplingBlock(new SaplingGenerator(name, Optional.empty(), Optional.ofNullable(treeFeature), Optional.empty()),
                AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
        Block resultBlock = registerBlock(name, saplingBlock, true);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        return resultBlock;
    }

    public static Block registerBlock(String name, Block block, boolean present) {
        if(present) ModNatureBlocks.registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MiddleEarth.MOD_ID, name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        Item.BLOCK_ITEMS.put(block, item);

        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
    }

    public static void registerModBlocks() {
        LoggerUtil.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
