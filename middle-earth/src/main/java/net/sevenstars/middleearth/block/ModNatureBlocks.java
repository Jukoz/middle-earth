package net.sevenstars.middleearth.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.crop.*;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.saplings.VariantSaplingBlock;
import net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatures;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static net.sevenstars.middleearth.block.WoodBlockSets.LEAVES_STRENGTH;

public class ModNatureBlocks {

    public static final Block MORGUL_IVY = registerBlock("morgul_ivy",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).luminance(GlowLichenBlock.getLuminanceSupplier(5)).burnable(), true);
    public static final Block HANGING_COBWEB = registerBlock("hanging_cobweb",
            HangingCobwebBlock::new, AbstractBlock.Settings.copy(Blocks.COBWEB), true);

    public static final Block CORNER_COBWEB = registerBlock("corner_cobweb",
            CornerCobwebBlock::new, AbstractBlock.Settings.copy(Blocks.COBWEB), true);

    public static final Block CORRUPTED_MOSS_CARPET = registerBlock("corrupted_moss_carpet",
            CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET), true);

    public static final Block CORRUPTED_MOSS_BLOCK = registerBlock("corrupted_moss_block",
            Block::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK), true);

    public static final Block CORRUPTED_MOSS = registerBlock("corrupted_moss",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);

    public static final Block MOSS = registerBlock("moss",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);

    public static final Block FOREST_MOSS = registerBlock("forest_moss",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);

    public static final Block FOREST_MOSS_CARPET = registerBlock("forest_moss_carpet",
            CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET), true);

    public static final Block FOREST_MOSS_BLOCK = registerBlock("forest_moss_block",
            Block::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_BLOCK), true);

    public static final Block OLD_PODZOL = registerBlock("old_podzol",
            Block::new, AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL), true);

    public static final Block LORIEN_PODZOL = registerBlock("lorien_podzol",
            Block::new, AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL), true);

    public static final Block BROWN_GRASS = registerBlock("brown_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DRY_GRASS = registerBlock("dry_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DYING_GRASS = registerBlock("dying_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GRIM_GRASS = registerBlock("grim_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block TEMPERATE_GRASS = registerBlock("temperate_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GRASS_TUFT = registerBlock("grass_tuft",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block HEATHER = registerBlock("heather",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block RED_HEATHER = registerBlock("red_heather",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DEAD_HEATHER = registerBlock("dead_heather",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DRY_HEATHER = registerBlock("dry_heather",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block HEATH = registerBlock("heath",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block WHEATGRASS = registerBlock("wheatgrass",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block WILD_GRASS = registerBlock("wild_grass",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block WILDERGRASS = registerBlock("wildergrass",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block SEDUM = registerBlock("sedum",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block YELLOW_SEDUM = registerBlock("yellow_sedum",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block GREEN_SHRUB = registerBlock("green_shrub",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SMALL_DRY_SHRUB = registerBlock("small_dry_shrub",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BEACH_GRASS = registerBlock("beach_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block COASTAL_PANIC_GRASS = registerBlock("coastal_panic_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block SHRIVELED_SHRUB = registerBlock("shriveled_shrub",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.DEAD_BUSH).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);

    public static final Block DEAD_RUSHES = registerBlock("dead_rushes",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block FALSE_OATGRASS = registerBlock("false_oatgrass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block BRACKEN = registerBlock("bracken",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block MALLOS = registerBlock("mallos",
            (settings) -> new FlowerBlock(StatusEffects.GLOWING, 3, settings), AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);
    public static final Block ELANOR = registerBlock("elanor",
            (settings) -> new FlowerBlock(StatusEffects.GLOWING, 3, settings), AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);
    public static final Block TAN_SHRUB = registerBlock("tan_shrub",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block YELLOW_FLOWER = registerBlock("yellow_flower",
            (settings) -> new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block GREEN_JEWEL_CORNFLOWER = registerBlock("green_jewel_cornflower",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);

    public static final Block LIGHT_BLUE_FLOWERS = registerBlock("light_blue_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block MAGENTA_FLOWERS = registerBlock("magenta_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block ORANGE_FLOWERS = registerBlock("orange_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block PINK_FLOWERS = registerBlock("pink_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block PURPLE_FLOWERS = registerBlock("purple_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block RED_FLOWERS = registerBlock("red_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block WHITE_FLOWERS = registerBlock("white_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block YELLOW_FLOWERS = registerBlock("yellow_flowers",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);

    public static final Block LAVENDER = registerBlock("lavender",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);
    public static final Block YELLOW_TROLLIUS = registerBlock("yellow_trollius",
            (settings) -> new FlowerBlock(StatusEffects.SATURATION, 0, settings), AbstractBlock.Settings.copy(Blocks.DANDELION), true);

    public static final Block HOROKAKA = registerBlock("horokaka",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GIANT_HOROKAKA = registerBlock("giant_horokaka",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.NONE)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block AZALEA_FLOWER_GROWTH = registerBlock("azalea_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block DRY_GROWTH = registerBlock("dry_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block GREEN_GROWTH = registerBlock("green_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block IVY_GROWTH = registerBlock("ivy_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block LILAC_FLOWER_GROWTH = registerBlock("lilac_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block PINK_FLOWER_GROWTH = registerBlock("pink_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block RED_FLOWER_GROWTH = registerBlock("red_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block WHITE_FLOWER_GROWTH = registerBlock("white_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);
    public static final Block YELLOW_FLOWER_GROWTH = registerBlock("yellow_flower_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);

    public static final Block SHORT_CATTAILS = registerBlock("short_cattails",
            WaterloggablePlant::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SHORT_BULRUSH = registerBlock("short_bulrush",
            WaterloggablePlant::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block TALL_CATTAILS = registerBlock("tall_cattails",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), AbstractBlock.Settings.copy(Blocks.TALL_GRASS).breakInstantly(), true);
    public static final Block TALL_BULRUSH = registerBlock("tall_bulrush",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), AbstractBlock.Settings.copy(Blocks.TALL_GRASS).breakInstantly(), true);

    public static final Block SMALL_LILY_PADS = registerBlock("small_lily_pads",
            LilyPadBlock::new, AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block SMALL_FLOWERING_LILY_PADS = registerBlock("small_flowering_lily_pads",
            LilyPadBlock::new, AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block LILY_PADS = registerBlock("lily_pads",
            LilyPadBlock::new, AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block DUCKWEED = registerBlock("duckweed",
            LilyPadBlock::new, AbstractBlock.Settings.copy(Blocks.LILY_PAD).noCollision(), false);

    public static final Block FROZEN_GRASS = registerBlock("frozen_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block FROZEN_TUFT = registerBlock("frozen_tuft",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block FROZEN_SHRUB = registerBlock("frozen_shrub",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block FROZEN_GROWTH = registerBlock("frozen_growth",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).burnable(), true);

    public static final Block STICKY_SNOW = registerBlock("sticky_snow",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.SNOW), true);
    public static final Block STICKY_ICE = registerBlock("sticky_ice",
            GlowLichenBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).replaceable().noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS), true);

    public static final Block SHORT_ICICLES = registerBlock("short_icicles",
            ShortIciclesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS), true);
    public static final Block DROOPING_ICICLES = registerBlock("drooping_icicles",
            DroopingIciclesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).noCollision()
                    .strength(0.2f).sounds(BlockSoundGroup.GLASS), true);

    public static final Block FLOATING_ICE = registerBlock("floating_ice",
            FloatingIceBlock::new, AbstractBlock.Settings.copy(Blocks.ICE), false);

    public static final Block SCORCHED_GRASS = registerBlock("scorched_grass",
            DesertPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SCORCHED_TUFT = registerBlock("scorched_tuft",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SCORCHED_SHRUB = registerBlock("scorched_shrub",
            CustomPlantBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BROWN_BOLETE = registerBlock("brown_bolete",
            (settings) -> new MushroomPlantBlock(MushroomTreeConfiguredFeatures.BROWN_BOLETTE_TREE_KEY, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block CAVE_AMANITA = registerBlock("cave_amanita",
            (settings) -> new MushroomPlantBlock(MushroomTreeConfiguredFeatures.CAVE_AMANITA_TREE_KEY, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block DEEP_FIRECAP = registerBlock("deep_firecap",
            (settings) -> new MushroomPlantBlock(MushroomTreeConfiguredFeatures.DEEP_FIRECAP_TREE_KEY, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block GHOSTSHROOM = registerBlock("ghostshroom",
            (settings) -> new MushroomPlantBlock(null, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4), true);
    public static final Block MORSEL = registerBlock("morsel",
            (settings) -> new MushroomPlantBlock(null, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block SKY_FIRECAP = registerBlock("sky_firecap",
            (settings) -> new MushroomPlantBlock(MushroomTreeConfiguredFeatures.SKY_FIRECAP_TREE_KEY, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TRUMPET_SHROOM = registerBlock("trumpet_shroom",
            (settings) -> new MushroomPlantBlock(null, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TALL_TRUMPET_SHROOM = registerBlock("tall_trumpet_shroom",
            TallMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TUBESHRROM = registerBlock("tubeshroom",
            (settings) -> new MushroomPlantBlock(null, settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4), true);
    public static final Block TALL_TUBESHROOM = registerBlock("tall_tubeshroom",
            TallMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 5), true);
    public static final Block VIOLET_CAPS = registerBlock("violet_caps",
            (settings) -> new MushroomPlantBlock(null, settings),AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            (settings) -> new MushroomPlantBlock(null, settings),AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block YELLOW_AMANITA = registerBlock("yellow_amanita",
            (settings) -> new MushroomPlantBlock(MushroomTreeConfiguredFeatures.YELLOW_AMANITA_TREE_KEY,settings), AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);

    public static final Block BROWN_BOLETE_TILLER = registerBlock("brown_bolete_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block CAVE_AMANITA_TILLER = registerBlock("cave_amanita_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block DEEP_FIRECAP_TILLER = registerBlock("deep_firecap_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block GHOSTSHROOM_TILLER = registerBlock("ghostshroom_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance(state -> 4), true);
    public static final Block MORSEL_TILLER = registerBlock("morsel_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block SKY_FIRECAP_TILLER = registerBlock("sky_firecap_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block VIOLET_CAPS_TILLER = registerBlock("violet_caps_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block WHITE_MUSHROOM_TILLER = registerBlock("white_mushroom_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);
    public static final Block YELLOW_AMANITA_TILLER = registerBlock("yellow_amanita_tiller",
            FlowerbedMushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM), true);

    public static final Block BROWN_BOLETE_BLOCK = registerBlock("brown_bolete_block",
            MushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block CAVE_AMANITA_BLOCK = registerBlock("cave_amanita_block",
            MushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block DEEP_FIRECAP_BLOCK = registerBlock("deep_firecap_block",
            MushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block SKY_FIRECAP_BLOCK = registerBlock("sky_firecap_block",
            MushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block YELLOW_AMANITA_BLOCK = registerBlock("yellow_amanita_block",
            MushroomBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), true);

    public static final Block BEECH_SAPLING = registerSimpleSapling("beech_sapling", ModTreeConfiguredFeatures.BEECH_TREE_KEY);
    public static final Block CHESTNUT_SAPLING = registerSimpleSapling("chestnut_sapling", ModTreeConfiguredFeatures.CHESTNUT_TREE_KEY);
    public static final Block HOLLY_SAPLING = registerSimpleSapling("holly_sapling", ModTreeConfiguredFeatures.HOLLY_TREE_KEY);
    public static final Block FIR_SAPLING = registerSimpleSapling("fir_sapling", ModTreeConfiguredFeatures.FIR_TREE_KEY);
    public static final Block LARCH_SAPLING = registerSimpleSapling("larch_sapling", ModTreeConfiguredFeatures.LARCH_TREE_KEY);
    public static final Block LEBETHRON_SAPLING = registerSimpleSapling("lebethron_sapling", ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY);
    public static final Block WHITE_LEBETHRON_SAPLING = registerSimpleSapling("white_lebethron_sapling", ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY);
    public static final Block MALLORN_SAPLING = registerSimpleSapling("mallorn_sapling", ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY);
    public static final Block MAPLE_SAPLING = registerVariantSapling("maple_sapling",
            List.of(ModTreeConfiguredFeatures.MAPLE_TREE_KEY, ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY));
    public static final Block SILVER_MAPLE_SAPLING = registerVariantSapling("silver_maple_sapling",
            List.of(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY));
    public static final Block MIRKWOOD_SAPLING = registerSimpleSapling("mirkwood_sapling", ModTreeConfiguredFeatures.SMALL_MIRKWOOD_TREE_KEY);
    public static final Block PALM_SAPLING = registerSimpleSapling("palm_sapling", ModTreeConfiguredFeatures.PALM_TREE_KEY);
    public static final Block WHITE_PALM_SAPLING = registerSimpleSapling("white_palm_sapling", ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY);
    public static final Block PINE_SAPLING = registerSimpleSapling("pine_sapling", ModTreeConfiguredFeatures.PINE_TREE_KEY);
    public static final Block BLACK_PINE_SAPLING = registerSimpleSapling("black_pine_sapling", ModTreeConfiguredFeatures.BLACK_PINE_TREE_KEY);
    public static final Block WILLOW_SAPLING = registerSimpleSapling("willow_sapling", ModTreeConfiguredFeatures.WILLOW_TREE_KEY);

    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);

    public static final Block BERRY_HOLLY_LEAVES = registerBlock("berry_holly_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);

    public static final Block DRY_LARCH_LEAVES = registerBlock("dry_larch_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);

    public static final Block FLOWERING_MALLORN_LEAVES = registerBlock("flowering_mallorn_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS), true);

    public static final Block MAPLE_LEAVES = registerBlock("maple_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);
    public static final Block ORANGE_MAPLE_LEAVES = registerBlock("orange_maple_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);
    public static final Block RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);
    public static final Block YELLOW_MAPLE_LEAVES = registerBlock("yellow_maple_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);

    public static final Block DRY_PINE_LEAVES = registerBlock("dry_pine_leaves",
            LeavesBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);
    public static final Block PINE_BRANCHES = registerBlock("pine_branches",
            Block::new, AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), true);

    public static final Block FALLEN_LEAVES = registerBlock("fallen_leaves",
            FallenLeavesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision(), true);
    public static final Block FALLEN_MALLORN_LEAVES = registerBlock("fallen_mallorn_leaves",
            FallenLeavesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision(), true);
    public static final Block FALLEN_MIRKWOOD_LEAVES = registerBlock("fallen_mirkwood_leaves",
            FallenLeavesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.1f).sounds(BlockSoundGroup.MOSS_CARPET).nonOpaque().replaceable().noCollision(), true);

    public static final Block TOUGH_BERRY_BUSH = registerBlock("tough_berry_bush",
            ToughBerryBushBlock::new, AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly()
                    .sounds(BlockSoundGroup.SWEET_BERRY_BUSH), false);
    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            StrawBerryBushBlock::new, AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH), false);

    public static final Block WILD_WHEAT = registerCrossBlock("wild_wheat",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block TALL_WILD_WHEAT = registerBlock("tall_wild_wheat",
            (settings) -> new CustomTallPlantBlock(settings, true), AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_PIPEWEED = registerBlock("wild_pipeweed",
            (settings) -> new CustomTallPlantBlock(settings, true), AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP),true);
    public static final Block WILD_FLAX = registerCrossBlock("wild_flax",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_TOMATO = registerBlock("wild_tomato",
            (settings) -> new CustomTallPlantBlock(settings, true),AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_BELL_PEPPER = registerCrossBlock("wild_bell_pepper",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_CUCUMBER = registerCrossBlock("wild_cucumber",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_GARLIC = registerCrossBlock("wild_garlic",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_ONION = registerCrossBlock("wild_onion",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_LETTUCE = registerCrossBlock("wild_lettuce",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_LEEK = registerCrossBlock("wild_leek",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_POTATO = registerBlock("wild_potato",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_CARROT = registerBlock("wild_carrot",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);
    public static final Block WILD_BEETROOT = registerBlock("wild_beetroot",
            WildCropBlock::new, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).sounds(BlockSoundGroup.CROP), true);

    public static final Block TOMATO_CROP = registerBlock("tomato_crop",
            TomatoCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block BELL_PEPPER_CROP = registerBlock("bell_pepper_crop",
            BellpepperCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block CUCUMBER_CROP = registerBlock("cucumber_crop",
            CucumberCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block FLAX_CROP = registerBlock("flax_crop",
            FlaxCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block GARLIC_CROP = registerBlock("garlic_crop",
            GarlicCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block LEEK_CROP = registerBlock("leek_crop",
            LeekCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block LETTUCE_CROP = registerBlock("lettuce_crop",
            LettuceCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block ONION_CROP = registerBlock("onion_crop",
            OnionCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
    public static final Block PIPEWEED_CROP = registerBlock("pipeweed_crop",
            PipeweedCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block MIRKWOOD_ROOTS = registerBlock("mirkwood_roots",
            MangroveRootsBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_ROOTS), true);
    public static final Block MIRKWOOD_HANGING_ROOTS = registerBlock("mirkwood_hanging_roots",
            HangingRootsBlock::new, AbstractBlock.Settings.copy(Blocks.HANGING_ROOTS), true);

    public static final Block MIRKWOOD_VINES = registerBlock("mirkwood_vines",
            MirkwoodVinesBottomBlock::new, AbstractBlock.Settings.copy(Blocks.WEEPING_VINES_PLANT), true);
    public static final Block MIRKWOOD_VINES_PLANT = registerBlock("mirkwood_vines_plant",
            MirkwoodVinesBlock::new, AbstractBlock.Settings.copy(Blocks.WEEPING_VINES), false);

    public static final Block MIRKWOOD_SPIDER_EGG = registerBlock("mirkwood_spider_egg",
            MirkwoodSpiderEggBlock::new, AbstractBlock.Settings.copy(Blocks.TURTLE_EGG), true);

    public static final Block GLOWWORM_WEBBING = registerBlock("glowworm_webbing",
            GlowWormBottomBlock::new, AbstractBlock.Settings.copy(Blocks.WEEPING_VINES_PLANT).luminance(state -> 6), true);
    public static final Block GLOWWORM_MAIN = registerBlock("glowworm_main",
            GlowWormBlock::new, AbstractBlock.Settings.copy(Blocks.WEEPING_VINES).luminance(state -> 6), false);

    public static Block registerCrossBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean present) {
        Block resultBlock = registerBlock(name, factory, settings, present);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        return resultBlock;
    }

    public static Block registerSimpleSapling(String name, RegistryKey<ConfiguredFeature<?, ?>> treeFeature) {
        SaplingBlock saplingBlock = new SaplingBlock(new SaplingGenerator(name, Optional.empty(), Optional.ofNullable(treeFeature), Optional.empty()),
                AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).registryKey(ModBlocks.keyOfBlock(name)));
        Block resultBlock = Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), saplingBlock);
        registerBlockItem(name, resultBlock);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        Saplings.saplings.add(resultBlock);
        return resultBlock;
    }

    public static Block registerVariantSapling(String name, List<RegistryKey<ConfiguredFeature<?, ?>>> treeFeatures) {
        List<SaplingGenerator> saplingGenerators = new ArrayList<>();
        for(RegistryKey<ConfiguredFeature<?,?>> treeFeature : treeFeatures) {
            saplingGenerators.add(new SaplingGenerator(name, Optional.empty(), Optional.ofNullable(treeFeature),
                            Optional.empty()));
        }

        SaplingBlock saplingBlock = new VariantSaplingBlock(AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).registryKey(ModBlocks.keyOfBlock(name)), saplingGenerators);

        Block resultBlock = Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), saplingBlock);
        registerBlockItem(name, resultBlock);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        return resultBlock;
    }

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean present) {
        Block block = (Block)factory.apply(settings.registryKey(ModBlocks.keyOfBlock(name)));
        if(present) ModNatureBlocks.registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name),
               new BlockItem(block, new Item.Settings().registryKey(ModBlocks.keyOfItem(name))));
        Item.BLOCK_ITEMS.put(block, item);

        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
