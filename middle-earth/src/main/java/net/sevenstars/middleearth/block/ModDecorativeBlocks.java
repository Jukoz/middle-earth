package net.sevenstars.middleearth.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.artefact.arkenstone.ArkenstoneBlock;
import net.sevenstars.middleearth.block.special.artefact.arkenstone.ArkenstoneWallBlock;
import net.sevenstars.middleearth.block.special.artisantable.ArtisanTable;
import net.sevenstars.middleearth.block.special.beds.CustomBedBlock;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.fireBlocks.*;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancBlock;
import net.sevenstars.middleearth.block.special.forge.ForgeBlock;
import net.sevenstars.middleearth.block.special.pots.AmphoraBlock;
import net.sevenstars.middleearth.block.special.pots.FatPotBlock;
import net.sevenstars.middleearth.block.special.pots.JarBlock;
import net.sevenstars.middleearth.block.special.pots.JugBlock;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.dwarvenTreatedAnvil.DwarvenShapingAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.elvenTreatedAnvil.ElvenTreatedAnvilblock;
import net.sevenstars.middleearth.block.special.shapingAnvil.orcishTreatedAnvil.OrcishTreatedAnvilblock;
import net.sevenstars.middleearth.block.special.shapingAnvil.treatedAnvil.TreatedAnvilblock;
import net.sevenstars.middleearth.block.special.statues.StatueBlock;
import net.sevenstars.middleearth.block.special.toggeable_lights.*;
import net.sevenstars.middleearth.block.special.torches.METorchBlock;
import net.sevenstars.middleearth.block.special.torches.MEWallTorchBlock;
import net.sevenstars.middleearth.block.special.torches.OrcSconceBlock;
import net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlock;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class ModDecorativeBlocks {

    public static final Block SILVER_LANTERN = registerBlock("silver_lantern",
            SilverLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));
    public static final Block WALL_SILVER_LANTERN = registerBlock("silver_lantern_wall",
            WallSilverLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block ELVEN_LANTERN = registerBlock("elven_lantern",
            ElvenLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));
    public static final Block WALL_ELVEN_LANTERN = registerBlock("elven_lantern_wall",
            WallElvenLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block DWARVEN_LANTERN = registerBlock("dwarven_lantern",
            DwarvenLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f).nonOpaque());
    public static final Block WALL_DWARVEN_LANTERN = registerBlock("dwarven_lantern_wall",
            WallDwarvenLanternBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));
    
    public static final Block CRYSTAL_LAMP = registerBlock("crystal_lamp",
            CrystalLampBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f).nonOpaque());
    public static final Block WALL_CRYSTAL_LAMP = registerBlock("crystal_lamp_wall",
            WallCrystalLampBlock::new, AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block FORGE = registerBlock("forge",
            ForgeBlock::new, AbstractBlock.Settings.copy(Blocks.BRICKS).luminance(createLightLevelFromLitBlockState(15)).strength(1.65f).requiresTool());
    public static final Block TREATED_ANVIL = registerBlock("treated_anvil",
            TreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block DWARVEN_TREATED_ANVIL = registerBlock("dwarven_treated_anvil",
            DwarvenShapingAnvilBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block ELVEN_TREATED_ANVIL = registerBlock("elven_treated_anvil",
            ElvenTreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block ORCISH_TREATED_ANVIL = registerBlock("orcish_treated_anvil",
            OrcishTreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());

    public static final Block BELLOWS = registerBlock("bellows",
            BellowsBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block ARTISAN_TABLE = registerBlock("artisan_table",
            ArtisanTable::new, AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).nonOpaque());

    public static final Block SMALL_CRATE = registerBlock("small_crate",
            CrateBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block THIN_BARREL = registerBlock("thin_barrel",
            ThinBarrelBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());
    public static final Block REINFORCED_CHEST = registerBlock("reinforced_chest",
            ReinforcedChestBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool());

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            WoodPileBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque());

    public static final Block CANDLE_HEAP = registerBlockWithItem("candle_heap",
            CandleHeapBlock::new, AbstractBlock.Settings.copy(Blocks.CANDLE).nonOpaque().luminance(createLightLevelFromLitBlockState(10)));

    public static final Block CALCITE_STATUE = registerBlockWithItem("calcite_statue",
            StatueBlock::new, AbstractBlock.Settings.copy(Blocks.CALCITE).nonOpaque().requiresTool());
    public static final Block GALONN_STATUE = registerBlockWithItem("galonn_statue",
            StatueBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool());
    public static final Block GONLUIN_STATUE = registerBlockWithItem("gonluin_statue",
            StatueBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool());
    public static final Block TUFF_STATUE = registerBlockWithItem("tuff_statue",
            StatueBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF).nonOpaque().requiresTool());
    public static final Block MEDGON_SPIKE = registerBlockWithItem("medgon_spike",
            StatueBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool());

    public static final Block FIRE_OF_ORTHANC = registerBlock("fire_of_orthanc",
            FireOfOrthancBlock::new, AbstractBlock.Settings.create().requiresTool().mapColor(MapColor.BLACK)
                    .sounds(BlockSoundGroup.METAL).strength(6f).burnable().solidBlock(Blocks::never).nonOpaque());
    public static final Block TORCH_OF_ORTHANC = registerBlock("torch_of_orthanc",
            (settings) -> new TorchOfOrthancBlock(settings, ParticleTypes.FLAME), AbstractBlock.Settings.copy(Blocks.TORCH).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block WOOD_FRAMED_WINDOW = registerBlockWithItem("wood_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WOOD_FRAMED_WINDOW_PANE = registerBlockWithItem("wood_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block WATTLE_AND_BRICK_WINDOW = registerBlockWithItem("wattle_and_brick_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WATTLE_AND_BRICK_WINDOW_PANE = registerBlockWithItem("wattle_and_brick_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block WATTLE_FRAMED_WINDOW = registerBlockWithItem("wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block DARK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("dark_wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block DARK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("dark_wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block BLACK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("black_wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block BLACK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("black_wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block GREEN_WATTLE_FRAMED_WINDOW = registerBlockWithItem("green_wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block GREEN_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("green_wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block RED_WATTLE_FRAMED_WINDOW = registerBlockWithItem("red_wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block RED_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("red_wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block WHITE_WATTLE_FRAMED_WINDOW = registerBlockWithItem("white_wattle_framed_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WHITE_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("white_wattle_framed_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block TUFF_CARVED_WINDOW = registerBlockWithItem("tuff_carved_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block TUFF_CARVED_WINDOW_PANE = registerBlockWithItem("tuff_carved_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block BLACKSTONE_CARVED_WINDOW = registerBlockWithItem("blackstone_carved_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block BLACKSTONE_CARVED_WINDOW_PANE = registerBlockWithItem("blackstone_carved_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block GONLUIN_CARVED_WINDOW = registerBlockWithItem("gonluin_carved_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block GONLUIN_CARVED_WINDOW_PANE = registerBlockWithItem("gonluin_carved_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block MEDGON_CARVED_WINDOW = registerBlockWithItem("medgon_carved_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block MEDGON_CARVED_WINDOW_PANE = registerBlockWithItem("medgon_carved_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block IZHERABAN_CARVED_WINDOW = registerBlockWithItem("izheraban_carved_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block IZHERABAN_CARVED_WINDOW_PANE = registerBlockWithItem("izheraban_carved_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block MUD_BRICK_ROUND_WINDOW = registerBlockWithItem("mud_brick_round_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block MUD_BRICK_ROUND_WINDOW_PANE = registerBlockWithItem("mud_brick_round_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block WHITE_DAUB_ROUND_WINDOW = registerBlockWithItem("white_daub_round_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WHITE_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("white_daub_round_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block YELLOW_DAUB_ROUND_WINDOW = registerBlockWithItem("yellow_daub_round_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block YELLOW_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("yellow_daub_round_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block WHITE_DAUB_HOBBIT_WINDOW = registerBlockWithItem("white_daub_hobbit_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WHITE_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("white_daub_hobbit_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW = registerBlockWithItem("yellow_daub_hobbit_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("yellow_daub_hobbit_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block PLASTER_HOBBIT_WINDOW = registerBlockWithItem("plaster_hobbit_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block PLASTER_HOBBIT_WINDOW_PANE = registerBlockWithItem("plaster_hobbit_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block PLASTER_ROUND_WINDOW = registerBlockWithItem("plaster_round_window",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block PLASTER_ROUND_WINDOW_PANE = registerBlockWithItem("plaster_round_window_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block LEAD_GLASS = registerBlockWithItem("lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block BLACK_STAINED_LEAD_GLASS = registerBlockWithItem("black_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("blue_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block BROWN_STAINED_LEAD_GLASS = registerBlockWithItem("brown_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block CYAN_STAINED_LEAD_GLASS = registerBlockWithItem("cyan_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("gray_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block GREEN_STAINED_LEAD_GLASS = registerBlockWithItem("green_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("light_blue_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("light_gray_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block LIME_STAINED_LEAD_GLASS = registerBlockWithItem("lime_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block MAGENTA_STAINED_LEAD_GLASS = registerBlockWithItem("magenta_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block ORANGE_STAINED_LEAD_GLASS = registerBlockWithItem("orange_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block PINK_STAINED_LEAD_GLASS = registerBlockWithItem("pink_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block PURPLE_STAINED_LEAD_GLASS = registerBlockWithItem("purple_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block RED_STAINED_LEAD_GLASS = registerBlockWithItem("red_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block WHITE_STAINED_LEAD_GLASS = registerBlockWithItem("white_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block YELLOW_STAINED_LEAD_GLASS = registerBlockWithItem("yellow_stained_lead_glass",
            TransparentBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS));
    public static final Block LEAD_GLASS_PANE = registerBlockWithItem("lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block BLACK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("black_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("blue_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block BROWN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("brown_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block CYAN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("cyan_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("gray_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block GREEN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("green_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_blue_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_gray_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block LIME_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("lime_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block MAGENTA_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("magenta_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block ORANGE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("orange_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block PINK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("pink_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block PURPLE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("purple_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block RED_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("red_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block WHITE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("white_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));
    public static final Block YELLOW_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("yellow_stained_lead_glass_pane",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.GLASS_PANE));

    public static final Block BLUE_CUSHION = registerBlockWithItem("blue_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block BROWN_CUSHION = registerBlockWithItem("brown_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block DARK_BLUE_CUSHION = registerBlockWithItem("dark_blue_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block DARK_BROWN_CUSHION = registerBlockWithItem("dark_brown_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block DARK_GREEN_CUSHION = registerBlockWithItem("dark_green_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block DARK_RED_CUSHION = registerBlockWithItem("dark_red_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block GREEN_CUSHION = registerBlockWithItem("green_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());
    public static final Block RED_CUSHION = registerBlockWithItem("red_cushion",
            CushionBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque());

    //region VANILLA FURNITURE
    public static final Block STONE_STOOL = registerBlockWithItem("stone_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque());
    public static final Block STONE_TABLE = registerBlockWithItem("stone_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque());
    public static final Block STONE_CHAIR = registerBlockWithItem("stone_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque());

    public static final Block CALCITE_STOOL = registerBlockWithItem("calcite_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque());
    public static final Block CALCITE_TABLE = registerBlockWithItem("calcite_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque());
    public static final Block CALCITE_CHAIR = registerBlockWithItem("calcite_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque());

    public static final Block ANDESITE_STOOL = registerBlockWithItem("andesite_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque());
    public static final Block ANDESITE_TABLE = registerBlockWithItem("andesite_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque());
    public static final Block ANDESITE_CHAIR = registerBlockWithItem("andesite_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque());

    public static final Block GRANITE_STOOL = registerBlockWithItem("granite_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque());
    public static final Block GRANITE_TABLE = registerBlockWithItem("granite_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque());
    public static final Block GRANITE_CHAIR = registerBlockWithItem("granite_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque());

    public static final Block DIORITE_STOOL = registerBlockWithItem("diorite_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque());
    public static final Block DIORITE_TABLE = registerBlockWithItem("diorite_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque());
    public static final Block DIORITE_CHAIR = registerBlockWithItem("diorite_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque());

    public static final Block DEEPSLATE_STOOL = registerBlockWithItem("deepslate_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque());
    public static final Block DEEPSLATE_TABLE = registerBlockWithItem("deepslate_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque());
    public static final Block DEEPSLATE_CHAIR = registerBlockWithItem("deepslate_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque());

    public static final Block BLACKSTONE_STOOL = registerBlockWithItem("blackstone_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque());
    public static final Block BLACKSTONE_TABLE = registerBlockWithItem("blackstone_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque());
    public static final Block BLACKSTONE_CHAIR = registerBlockWithItem("blackstone_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque());

    public static final Block BASALT_STOOL = registerBlockWithItem("basalt_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque());
    public static final Block BASALT_TABLE = registerBlockWithItem("basalt_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque());
    public static final Block BASALT_CHAIR = registerBlockWithItem("basalt_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque());

    public static final Block TUFF_STOOL = registerBlockWithItem("tuff_stool",
            StoolBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque());
    public static final Block TUFF_TABLE = registerBlockWithItem("tuff_table",
            StoneTableBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque());
    public static final Block TUFF_CHAIR = registerBlockWithItem("tuff_chair",
            StoneChairBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque());

    public static final Block OAK_STOOL = registerBlockWithItem("oak_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block OAK_BENCH = registerBlockWithItem("oak_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block OAK_TABLE = registerBlockWithItem("oak_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block OAK_CHAIR = registerBlockWithItem("oak_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());

    public static final Block SPRUCE_STOOL = registerBlockWithItem("spruce_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block SPRUCE_BENCH = registerBlockWithItem("spruce_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block SPRUCE_TABLE = registerBlockWithItem("spruce_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block SPRUCE_CHAIR = registerBlockWithItem("spruce_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());

    public static final Block BIRCH_STOOL = registerBlockWithItem("birch_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque());
    public static final Block BIRCH_BENCH = registerBlockWithItem("birch_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block BIRCH_TABLE = registerBlockWithItem("birch_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque());
    public static final Block BIRCH_CHAIR = registerBlockWithItem("birch_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque());

    public static final Block JUNGLE_STOOL = registerBlockWithItem("jungle_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque());
    public static final Block JUNGLE_BENCH = registerBlockWithItem("jungle_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block JUNGLE_TABLE = registerBlockWithItem("jungle_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque());
    public static final Block JUNGLE_CHAIR = registerBlockWithItem("jungle_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque());

    public static final Block ACACIA_STOOL = registerBlockWithItem("acacia_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque());
    public static final Block ACACIA_BENCH = registerBlockWithItem("acacia_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block ACACIA_TABLE = registerBlockWithItem("acacia_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque());
    public static final Block ACACIA_CHAIR = registerBlockWithItem("acacia_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque().nonOpaque());

    public static final Block DARK_OAK_STOOL = registerBlockWithItem("dark_oak_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque());
    public static final Block DARK_OAK_BENCH = registerBlockWithItem("dark_oak_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block DARK_OAK_TABLE = registerBlockWithItem("dark_oak_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque());
    public static final Block DARK_OAK_CHAIR = registerBlockWithItem("dark_oak_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque());
    
    public static final Block MANGROVE_STOOL = registerBlockWithItem("mangrove_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque());
    public static final Block MANGROVE_BENCH = registerBlockWithItem("mangrove_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block MANGROVE_TABLE = registerBlockWithItem("mangrove_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque());
    public static final Block MANGROVE_CHAIR = registerBlockWithItem("mangrove_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque());

    public static final Block CHERRY_STOOL = registerBlockWithItem("cherry_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque());
    public static final Block CHERRY_BENCH = registerBlockWithItem("cherry_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block CHERRY_TABLE = registerBlockWithItem("cherry_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque());
    public static final Block CHERRY_CHAIR = registerBlockWithItem("cherry_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque());

    public static final Block BAMBOO_STOOL = registerBlockWithItem("bamboo_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque());
    public static final Block BAMBOO_BENCH = registerBlockWithItem("bamboo_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block BAMBOO_TABLE = registerBlockWithItem("bamboo_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque());
    public static final Block BAMBOO_CHAIR = registerBlockWithItem("bamboo_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque());

    public static final Block CRIMSON_STOOL = registerBlockWithItem("crimson_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque());
    public static final Block CRIMSON_BENCH = registerBlockWithItem("crimson_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block CRIMSON_TABLE = registerBlockWithItem("crimson_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque());
    public static final Block CRIMSON_CHAIR = registerBlockWithItem("crimson_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque());

    public static final Block WARPED_STOOL = registerBlockWithItem("warped_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque());
    public static final Block WARPED_BENCH = registerBlockWithItem("warped_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque());
    public static final Block WARPED_TABLE = registerBlockWithItem("warped_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque());
    public static final Block WARPED_CHAIR = registerBlockWithItem("warped_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque());

    public static final Block TREATED_WOOD_STOOL = registerBlockWithItem("treated_wood_stool",
            WoodStoolBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block TREATED_WOOD_BENCH = registerBlockWithItem("treated_wood_bench",
            WoodBenchBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block TREATED_WOOD_TABLE = registerBlockWithItem("treated_wood_table",
            WoodTableBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    public static final Block TREATED_WOOD_CHAIR = registerBlockWithItem("treated_wood_chair",
            WoodChairBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());
    //endregion

    public static final Block WATERING_CAN = registerBlockWithItem("watering_can",
            WateringCanBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().requiresTool());
    public static final Block WOODEN_BUCKET = registerBlockWithItem("wooden_bucket",
            WoodenBucketBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());

    //TODO update all that
/*
    public static final Block POTTED_BEECH_SAPLING      = registerBlock("potted_beech_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.BEECH_SAPLING));
    public static final Block POTTED_CHESTNUT_SAPLING   = registerBlock("potted_chestnut_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.CHESTNUT_SAPLING));
    public static final Block POTTED_HOLLY_SAPLING      = registerBlock("potted_holly_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.HOLLY_SAPLING));
    public static final Block POTTED_FIR_SAPLING        = registerBlock("potted_fir_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.FIR_SAPLING));
    public static final Block POTTED_LARCH_SAPLING      = registerBlock("potted_larch_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.LARCH_SAPLING));
    public static final Block POTTED_LEBETHRON_SAPLING  = registerBlock("potted_lebethron_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.LEBETHRON_SAPLING));
    public static final Block POTTED_WHITE_LEBETHRON_SAPLING = registerBlock("potted_white_lebethron_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.WHITE_LEBETHRON_SAPLING));
    public static final Block POTTED_MALLORN_SAPLING    = registerBlock("potted_mallorn_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.MALLORN_SAPLING));
    public static final Block POTTED_MAPLE_SAPLING      = registerBlock("potted_maple_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.MAPLE_SAPLING));
    public static final Block POTTED_SILVER_MAPLE_SAPLING = registerBlock("potted_silver_maple_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.SILVER_MAPLE_SAPLING));
    public static final Block POTTED_MIRKWOOD_SAPLING   = registerBlock("potted_mirkwood_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.MIRKWOOD_SAPLING));
    public static final Block POTTED_PALM_SAPLING       = registerBlock("potted_palm_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.PALM_SAPLING));
    public static final Block POTTED_WHITE_PALM_SAPLING = registerBlock("potted_white_palm_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.WHITE_PALM_SAPLING));
    public static final Block POTTED_PINE_SAPLING       = registerBlock("potted_pine_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.PINE_SAPLING));
    public static final Block POTTED_BLACK_PINE_SAPLING = registerBlock("potted_black_pine_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.BLACK_PINE_SAPLING));
    public static final Block POTTED_WILLOW_SAPLING     = registerBlock("potted_willow_sapling", Blocks.createFlowerPotBlock(ModNatureBlocks.WILLOW_SAPLING));

    public static final Block POTTED_GREEN_SHRUB     = registerBlock("potted_green_shrub", Blocks.createFlowerPotBlock(ModNatureBlocks.GREEN_SHRUB));
    public static final Block POTTED_MALLOS         = registerBlock("potted_mallos", Blocks.createFlowerPotBlock(ModNatureBlocks.MALLOS));
    public static final Block POTTED_YELLOW_FLOWER     = registerBlock("potted_yellow_flower", Blocks.createFlowerPotBlock(ModNatureBlocks.YELLOW_FLOWER));
    public static final Block POTTED_YELLOW_TROLLIUS     = registerBlock("potted_yellow_trollius", Blocks.createFlowerPotBlock(ModNatureBlocks.YELLOW_TROLLIUS));
    public static final Block POTTED_TAN_SHRUB      = registerBlock("potted_tan_shrub", Blocks.createFlowerPotBlock(ModNatureBlocks.TAN_SHRUB));
    public static final Block POTTED_GREEN_JEWEL_CORNFLOWER     = registerBlock("potted_green_jewel_cornflower", Blocks.createFlowerPotBlock(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER));
    public static final Block POTTED_SCORCHED_SHRUB     = registerBlock("potted_scorched_shrub", Blocks.createFlowerPotBlock(ModNatureBlocks.SCORCHED_SHRUB));
    public static final Block POTTED_FROZEN_SHRUB     = registerBlock("potted_frozen_shrub", Blocks.createFlowerPotBlock(ModNatureBlocks.FROZEN_SHRUB));
    
    public static final Block POTTED_CAVE_AMANITA = registerBlock("potted_cave_amanita", Blocks.createFlowerPotBlock(ModNatureBlocks.CAVE_AMANITA));
    public static final Block POTTED_DEEP_FIRECAP = registerBlock("potted_deep_firecap", Blocks.createFlowerPotBlock(ModNatureBlocks.DEEP_FIRECAP));
    public static final Block POTTED_GHOSTSHROOM = registerBlock("potted_ghostshroom", Blocks.createFlowerPotBlock(ModNatureBlocks.GHOSTSHROOM));
    public static final Block POTTED_MORSEL     = registerBlock("potted_morsel", Blocks.createFlowerPotBlock(ModNatureBlocks.MORSEL));
    public static final Block POTTED_SKYFIRECAP = registerBlock("potted_sky_firecap", Blocks.createFlowerPotBlock(ModNatureBlocks.SKY_FIRECAP));
    public static final Block POTTED_TRUMPET_SHROOM = registerBlock("potted_trumpet_shroom", Blocks.createFlowerPotBlock(ModNatureBlocks.TRUMPET_SHROOM));
    public static final Block POTTED_TUBESHROOM = registerBlock("potted_tubeshroom", Blocks.createFlowerPotBlock(ModNatureBlocks.TUBESHRROM));
    public static final Block POTTED_VIOLET_CAPS = registerBlock("potted_violet_caps", Blocks.createFlowerPotBlock(ModNatureBlocks.VIOLET_CAPS));
    public static final Block POTTED_WHITE_MUSHROOM = registerBlock("potted_white_mushroom", Blocks.createFlowerPotBlock(ModNatureBlocks.WHITE_MUSHROOM));
    public static final Block POTTED_YELLOW_AMANITA = registerBlock("potted_yellow_amanita", Blocks.createFlowerPotBlock(ModNatureBlocks.YELLOW_AMANITA));
*/
    public static final Block BROWN_JUG = registerBlockWithItem("brown_jug",
            JugBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block GRAY_POT = registerBlockWithItem("gray_pot",
            JugBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block LARGE_JUG = registerBlockWithItem("large_jug",
            JugBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());

    public static final Block AMPHORA = registerBlockWithItem("amphora",
            AmphoraBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block BROWN_AMPHORA = registerBlockWithItem("brown_amphora",
            AmphoraBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block GRAY_VASE = registerBlockWithItem("gray_vase",
            AmphoraBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());

    public static final Block BROWN_JAR = registerBlockWithItem("brown_jar",
            JarBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block CLAY_JAR = registerBlockWithItem("clay_jar",
            JarBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block GRAY_JAR = registerBlockWithItem("gray_jar",
            JarBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());

    public static final Block BROWN_FAT_POT = registerBlockWithItem("brown_fat_pot",
            FatPotBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block FAT_POT = registerBlockWithItem("fat_pot",
            FatPotBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block GRAY_FAT_POT = registerBlockWithItem("gray_fat_pot",
            FatPotBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());
    public static final Block POT_OF_GOLD = registerBlockWithItem("pot_of_gold",
            FatPotBlock::new, AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque());

    public static final Block GOLDEN_CHALICE = registerBlockWithItem("golden_chalice",
            ChaliceBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque());

    public static final Block COPPER_TREASURE_HEAP_LAYER = registerBlockWithItem("copper_treasure_heap_layer",
            LayersAltBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque());
    public static final Block SILVER_TREASURE_HEAP_LAYER = registerBlockWithItem("silver_treasure_heap_layer",
            LayersAltBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque());
    public static final Block GOLD_TREASURE_HEAP_LAYER = registerBlockWithItem("gold_treasure_heap_layer",
            LayersAltBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque());

    public static final Block COPPER_COIN_PILE = registerBlockWithItem("copper_coin_pile",
            CoinPileBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque());
    public static final Block SILVER_COIN_PILE = registerBlockWithItem("silver_coin_pile",
            CoinPileBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque());
    public static final Block GOLD_COIN_PILE = registerBlockWithItem("gold_coin_pile",
            CoinPileBlock::new, AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque());

    public static final Block TREATED_STEEL_ROD = registerBlockWithItem("treated_steel_rod",
            DecorativeRodBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().requiresTool());

    public static final Block ROPE = registerBlockWithItem("rope",
            ChainBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).noCollision());

    public static final Block BRONZE_CHAIN = registerBlockWithItem("bronze_chain",
            ChainBlock::new, AbstractBlock.Settings.copy(Blocks.CHAIN));
    public static final Block BRONZE_BROAD_CHAIN = registerBlockWithItem("bronze_broad_chain",
            ChainBlock::new, AbstractBlock.Settings.copy(Blocks.CHAIN));
    public static final Block SPIKY_CHAIN = registerBlockWithItem("spiky_chain",
            ChainBlock::new, AbstractBlock.Settings.copy(Blocks.CHAIN));

    public static final Block CHIMNEY = registerBlockWithItem("chimney",
            ChimneyBlock::new, AbstractBlock.Settings.copy(Blocks.STONE_BRICKS).requiresTool());

    public static final Block BIG_BRAZIER = registerBlockWithItem("big_brazier",
            BrazierBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());
    public static final Block SMALL_BRAZIER = registerBlockWithItem("small_brazier",
            SmallBrazierBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block GILDED_BIG_BRAZIER = registerBlockWithItem("gilded_big_brazier",
            GildedBrazierBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());
    public static final Block GILDED_SMALL_BRAZIER = registerBlockWithItem("gilded_small_brazier",
            GildedSmallBrazierBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block FIRE_BOWL = registerBlockWithItem("fire_bowl",
            FireBowlBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block BONFIRE = registerBlockWithItem("bonfire",
            BonfireBlock::new, AbstractBlock.Settings.copy(Blocks.CAMPFIRE).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block SCONCE = registerBlock("sconce",
            METorchBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());
    public static final Block WALL_SCONCE = registerBlock("wall_sconce",
            MEWallTorchBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block GILDED_SCONCE = registerBlock("gilded_sconce",
            METorchBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());
    public static final Block GILDED_WALL_SCONCE = registerBlock("gilded_wall_sconce",
            MEWallTorchBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block ORCISH_SCONCE = registerBlock("orcish_sconce",
            OrcSconceBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());
    public static final Block ORCISH_WALL_SCONCE = registerBlock("orcish_wall_sconce",
            MEWallTorchBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool());

    public static final Block GROUND_BOOK = registerBlockWithItem("ground_book",
            GroundBookBlock::new, AbstractBlock.Settings.create().breakInstantly().nonOpaque().noCollision());
    public static final Block DWARVEN_GROUND_BOOK = registerBlockWithItem("dwarven_ground_book",
            DwarvenGroundBookBlock::new, AbstractBlock.Settings.create().breakInstantly().nonOpaque().noCollision());

    public static final Block OAK_LADDER = registerBlockWithItem("oak_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block SPRUCE_LADDER = registerBlockWithItem("spruce_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block BIRCH_LADDER = registerBlockWithItem("birch_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block JUNGLE_LADDER = registerBlockWithItem("jungle_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block ACACIA_LADDER = registerBlockWithItem("acacia_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block DARK_OAK_LADDER = registerBlockWithItem("dark_oak_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block MANGROVE_LADDER = registerBlockWithItem("mangrove_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block CHERRY_LADDER = registerBlockWithItem("cherry_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block BAMBOO_LADDER = registerBlockWithItem("bamboo_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_SLAB).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block CRIMSON_LADDER = registerBlockWithItem("crimson_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.LADDER).burnable());
    public static final Block WARPED_LADDER = registerBlockWithItem("warped_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_SLAB).sounds(BlockSoundGroup.LADDER).burnable());

    public static final Block TREATED_WOOD_LADDER = registerBlockWithItem("treated_wood_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.LADDER).burnable());

    public static final Block ROPE_LADDER = registerBlockWithItem("rope_ladder",
            ThickLadderBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.WOOL).burnable());

    public static final Block TALL_BLACK_PINE_DOOR = registerBlock("tall_black_pine_door",
            LargeDoor3x1::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));

    public static final Block OAK_STABLE_DOOR = registerBlock("oak_stable_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block REINFORCED_BLACK_PINE_DOOR = registerBlock("reinforced_black_pine_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block REINFORCED_SPRUCE_DOOR = registerBlock("reinforced_spruce_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block SIMPLE_LARCH_GATE = registerBlock("simple_larch_gate",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block RICKETY_SIMPLE_LARCH_DOOR = registerBlock("rickety_simple_larch_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block SPRUCE_STABLE_DOOR = registerBlock("spruce_stable_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));

    public static final Block LARGE_STURDY_DOOR = registerBlock("large_sturdy_door",
            LargeDoor5x3::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));

    public static final Block LARCH_HOBBIT_DOOR = registerBlock("larch_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block SPRUCE_HOBBIT_DOOR = registerBlock("spruce_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));

    public static final Block BLUE_HOBBIT_DOOR = registerBlock("blue_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block GREEN_HOBBIT_DOOR = registerBlock("green_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block LIGHT_BLUE_HOBBIT_DOOR = registerBlock("light_blue_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block RED_HOBBIT_DOOR = registerBlock("red_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block YELLOW_HOBBIT_DOOR = registerBlock("yellow_hobbit_door",
            LargeDoor2x2::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));

    public static final Block GREAT_GONDORIAN_GATE = registerBlock("great_gondorian_gate",
            LargeDoor10x5::new, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));

    public static final Block GREAT_DWARVEN_GATE = registerBlock("great_dwarven_gate",
            LargeDoor5x2::new, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));
    public static final Block HIDDEN_DWARVEN_DOOR = registerBlock("hidden_dwarven_door",
            LargeThickDoor3x2::new, AbstractBlock.Settings.copy(Blocks.STONE));
    public static final Block VARNISHED_DWARVEN_DOOR = registerBlock("varnished_dwarven_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));
    public static final Block RUINED_DWARVEN_DOOR = registerBlock("ruined_dwarven_door",
            LargeDoor4x2::new, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));

    public static final Block GREAT_ELVEN_GATE = registerBlock("great_elven_gate",
            LargeDoor6x2::new, AbstractBlock.Settings.copy(Blocks.OAK_DOOR));

    public static final Block GREAT_ORCISH_GATE = registerBlock("great_orcish_gate",
            LargeDoor10x4::new, AbstractBlock.Settings.copy(Blocks.IRON_DOOR));

    public static final Block FANCY_BED = registerBlockWithItem("fancy_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), AbstractBlock.Settings.copy(Blocks.BLACK_BED));
    public static final Block FUR_BED = registerBlockWithItem("fur_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), AbstractBlock.Settings.copy(Blocks.BLACK_BED));
    public static final Block STRAW_BED = registerBlockWithItem("straw_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), AbstractBlock.Settings.copy(Blocks.BLACK_BED));

    public static final Block ARKENSTONE = registerBlock("arkenstone",
            ArkenstoneBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).luminance((state -> 7)).nonOpaque().requiresTool());
    public static final Block WALL_ARKENSTONE = registerBlock("wall_arkenstone",
            ArkenstoneWallBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).luminance((state -> 7)).nonOpaque().requiresTool());

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = (Block)factory.apply(settings.registryKey(ModBlocks.keyOfBlock(name)));
        return Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), block);
    }

    public static Block registerBlockWithItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = (Block)factory.apply(settings.registryKey(ModBlocks.keyOfBlock(name)));
        ModBlocks.registerBlockItem(name, block);
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), block);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean) state.get(Properties.LIT) ? litLevel : 0;
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
