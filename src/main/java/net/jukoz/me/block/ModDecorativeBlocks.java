package net.jukoz.me.block;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.block.special.artisantable.ArtisanTable;
import net.jukoz.me.block.special.beds.CustomBedBlock;
import net.jukoz.me.block.special.bellows.BellowsBlock;
import net.jukoz.me.block.special.doors.*;
import net.jukoz.me.block.special.fireBlocks.*;
import net.jukoz.me.block.special.fire_of_orthanc.FireOfOrthancBlock;
import net.jukoz.me.block.special.forge.ForgeBlock;
import net.jukoz.me.block.special.pots.AmphoraBlock;
import net.jukoz.me.block.special.pots.FatPotBlock;
import net.jukoz.me.block.special.pots.JarBlock;
import net.jukoz.me.block.special.pots.JugBlock;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlock;
import net.jukoz.me.block.special.shapingAnvil.dwarvenTreatedAnvil.DwarvenShapingAnvilBlock;
import net.jukoz.me.block.special.shapingAnvil.elvenTreatedAnvil.ElvenTreatedAnvilblock;
import net.jukoz.me.block.special.shapingAnvil.orcishTreatedAnvil.OrcishTreatedAnvilblock;
import net.jukoz.me.block.special.shapingAnvil.treatedAnvil.TreatedAnvilblock;
import net.jukoz.me.block.special.statues.StatueBlock;
import net.jukoz.me.block.special.toggeable_lights.*;
import net.jukoz.me.block.special.torches.METorchBlock;
import net.jukoz.me.block.special.torches.MEWallTorchBlock;
import net.jukoz.me.block.special.torches.OrcSconceBlock;
import net.jukoz.me.block.special.wood_pile.WoodPileBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class ModDecorativeBlocks {

    public static final Block SILVER_LANTERN = registerBlock("silver_lantern",
            new SilverLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_SILVER_LANTERN = registerBlock("silver_lantern_wall",
            new WallSilverLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block ELVEN_LANTERN = registerBlock("elven_lantern",
            new ElvenLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_ELVEN_LANTERN = registerBlock("elven_lantern_wall",
            new WallElvenLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block DWARVEN_LANTERN = registerBlock("dwarven_lantern",
            new DwarvenLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f).nonOpaque()));
    public static final Block WALL_DWARVEN_LANTERN = registerBlock("dwarven_lantern_wall",
            new WallDwarvenLanternBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    
    public static final Block CRYSTAL_LAMP = registerBlock("crystal_lamp",
            new CrystalLampBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f).nonOpaque()));
    public static final Block WALL_CRYSTAL_LAMP = registerBlock("crystal_lamp_wall",
            new WallCrystalLampBlock(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block FORGE = registerBlock("forge",
            new ForgeBlock(AbstractBlock.Settings.copy(Blocks.BRICKS).luminance(createLightLevelFromLitBlockState(15)).strength(1.65f).requiresTool()));
    public static final Block TREATED_ANVIL = registerBlock("treated_anvil",
            new TreatedAnvilblock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque()));
    public static final Block DWARVEN_TREATED_ANVIL = registerBlock("dwarven_treated_anvil",
            new DwarvenShapingAnvilBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque()));
    public static final Block ELVEN_TREATED_ANVIL = registerBlock("elven_treated_anvil",
            new ElvenTreatedAnvilblock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque()));
    public static final Block ORCISH_TREATED_ANVIL = registerBlock("orcish_treated_anvil",
            new OrcishTreatedAnvilblock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque()));

    public static final Block BELLOWS = registerBlock("bellows",
            new BellowsBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block ARTISAN_TABLE = registerBlock("artisan_table",
            new ArtisanTable(AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).nonOpaque()));

    public static final Block SMALL_CRATE = registerBlock("small_crate",
            new CrateBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block THIN_BARREL = registerBlock("thin_barrel",
            new ThinBarrelBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block REINFORCED_CHEST = registerBlock("reinforced_chest",
            new ReinforcedChestBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool()));

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            new WoodPileBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque()));

    public static final Block CANDLE_HEAP = registerBlockWithItem("candle_heap",
            new CandleHeapBlock(AbstractBlock.Settings.copy(Blocks.CANDLE).nonOpaque().luminance(createLightLevelFromLitBlockState(10))));

    public static final Block CALCITE_STATUE = registerBlockWithItem("calcite_statue",
            new StatueBlock(AbstractBlock.Settings.copy(Blocks.CALCITE).nonOpaque().requiresTool()));
    public static final Block GALONN_STATUE = registerBlockWithItem("galonn_statue",
            new StatueBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool()));
    public static final Block GONLUIN_STATUE = registerBlockWithItem("gonluin_statue",
            new StatueBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool()));
    public static final Block TUFF_STATUE = registerBlockWithItem("tuff_statue",
            new StatueBlock(AbstractBlock.Settings.copy(Blocks.TUFF).nonOpaque().requiresTool()));
    public static final Block MEDGON_SPIKE = registerBlockWithItem("medgon_spike",
            new StatueBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque().requiresTool()));

    public static final Block FIRE_OF_ORTHANC = registerBlock("fire_of_orthanc",
            new FireOfOrthancBlock(AbstractBlock.Settings.create().requiresTool().mapColor(MapColor.BLACK)
                    .sounds(BlockSoundGroup.METAL).strength(6f).burnable().solidBlock(Blocks::never).nonOpaque()));
    public static final Block TORCH_OF_ORTHANC = registerBlock("torch_of_orthanc",
            new TorchOfOrthancBlock(AbstractBlock.Settings.copy(Blocks.TORCH).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool(), ParticleTypes.FLAME));

    public static final Block WOOD_FRAMED_WINDOW = registerBlockWithItem("wood_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WOOD_FRAMED_WINDOW_PANE = registerBlockWithItem("wood_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block WATTLE_AND_BRICK_WINDOW = registerBlockWithItem("wattle_and_brick_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WATTLE_AND_BRICK_WINDOW_PANE = registerBlockWithItem("wattle_and_brick_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block WATTLE_FRAMED_WINDOW = registerBlockWithItem("wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block DARK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("dark_wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block DARK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("dark_wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block BLACK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("black_wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block BLACK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("black_wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block GREEN_WATTLE_FRAMED_WINDOW = registerBlockWithItem("green_wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block GREEN_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("green_wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block RED_WATTLE_FRAMED_WINDOW = registerBlockWithItem("red_wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block RED_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("red_wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block WHITE_WATTLE_FRAMED_WINDOW = registerBlockWithItem("white_wattle_framed_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WHITE_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("white_wattle_framed_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block TUFF_CARVED_WINDOW = registerBlockWithItem("tuff_carved_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block TUFF_CARVED_WINDOW_PANE = registerBlockWithItem("tuff_carved_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block BLACKSTONE_CARVED_WINDOW = registerBlockWithItem("blackstone_carved_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block BLACKSTONE_CARVED_WINDOW_PANE = registerBlockWithItem("blackstone_carved_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block GONLUIN_CARVED_WINDOW = registerBlockWithItem("gonluin_carved_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block GONLUIN_CARVED_WINDOW_PANE = registerBlockWithItem("gonluin_carved_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block MEDGON_CARVED_WINDOW = registerBlockWithItem("medgon_carved_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block MEDGON_CARVED_WINDOW_PANE = registerBlockWithItem("medgon_carved_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block IZHERABAN_CARVED_WINDOW = registerBlockWithItem("izheraban_carved_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block IZHERABAN_CARVED_WINDOW_PANE = registerBlockWithItem("izheraban_carved_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block MUD_BRICK_ROUND_WINDOW = registerBlockWithItem("mud_brick_round_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block MUD_BRICK_ROUND_WINDOW_PANE = registerBlockWithItem("mud_brick_round_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block WHITE_DAUB_ROUND_WINDOW = registerBlockWithItem("white_daub_round_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WHITE_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("white_daub_round_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block YELLOW_DAUB_ROUND_WINDOW = registerBlockWithItem("yellow_daub_round_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block YELLOW_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("yellow_daub_round_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block WHITE_DAUB_HOBBIT_WINDOW = registerBlockWithItem("white_daub_hobbit_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WHITE_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("white_daub_hobbit_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW = registerBlockWithItem("yellow_daub_hobbit_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("yellow_daub_hobbit_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block PLASTER_HOBBIT_WINDOW = registerBlockWithItem("plaster_hobbit_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block PLASTER_HOBBIT_WINDOW_PANE = registerBlockWithItem("plaster_hobbit_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block PLASTER_ROUND_WINDOW = registerBlockWithItem("plaster_round_window",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block PLASTER_ROUND_WINDOW_PANE = registerBlockWithItem("plaster_round_window_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block LEAD_GLASS = registerBlockWithItem("lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block BLACK_STAINED_LEAD_GLASS = registerBlockWithItem("black_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("blue_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block BROWN_STAINED_LEAD_GLASS = registerBlockWithItem("brown_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block CYAN_STAINED_LEAD_GLASS = registerBlockWithItem("cyan_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("gray_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block GREEN_STAINED_LEAD_GLASS = registerBlockWithItem("green_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("light_blue_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("light_gray_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block LIME_STAINED_LEAD_GLASS = registerBlockWithItem("lime_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS = registerBlockWithItem("magenta_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block ORANGE_STAINED_LEAD_GLASS = registerBlockWithItem("orange_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block PINK_STAINED_LEAD_GLASS = registerBlockWithItem("pink_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block PURPLE_STAINED_LEAD_GLASS = registerBlockWithItem("purple_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block RED_STAINED_LEAD_GLASS = registerBlockWithItem("red_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block WHITE_STAINED_LEAD_GLASS = registerBlockWithItem("white_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block YELLOW_STAINED_LEAD_GLASS = registerBlockWithItem("yellow_stained_lead_glass",
            new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block LEAD_GLASS_PANE = registerBlockWithItem("lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block BLACK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("black_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("blue_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block BROWN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("brown_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block CYAN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("cyan_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("gray_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block GREEN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("green_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_blue_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_gray_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block LIME_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("lime_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("magenta_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block ORANGE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("orange_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block PINK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("pink_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block PURPLE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("purple_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block RED_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("red_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block WHITE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("white_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));
    public static final Block YELLOW_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("yellow_stained_lead_glass_pane",
            new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS_PANE)));

    public static final Block BLUE_CUSHION = registerBlockWithItem("blue_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block BROWN_CUSHION = registerBlockWithItem("brown_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block DARK_BLUE_CUSHION = registerBlockWithItem("dark_blue_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block DARK_BROWN_CUSHION = registerBlockWithItem("dark_brown_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block DARK_GREEN_CUSHION = registerBlockWithItem("dark_green_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block DARK_RED_CUSHION = registerBlockWithItem("dark_red_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block GREEN_CUSHION = registerBlockWithItem("green_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));
    public static final Block RED_CUSHION = registerBlockWithItem("red_cushion",
            new CushionBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).nonOpaque()));

    //region VANILLA FURNITURE
    public static final Block STONE_STOOL = registerBlockWithItem("stone_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque()));
    public static final Block STONE_TABLE = registerBlockWithItem("stone_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque()));
    public static final Block STONE_CHAIR = registerBlockWithItem("stone_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque()));

    public static final Block CALCITE_STOOL = registerBlockWithItem("calcite_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque()));
    public static final Block CALCITE_TABLE = registerBlockWithItem("calcite_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque()));
    public static final Block CALCITE_CHAIR = registerBlockWithItem("calcite_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.CALCITE).requiresTool().nonOpaque()));

    public static final Block ANDESITE_STOOL = registerBlockWithItem("andesite_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque()));
    public static final Block ANDESITE_TABLE = registerBlockWithItem("andesite_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque()));
    public static final Block ANDESITE_CHAIR = registerBlockWithItem("andesite_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.ANDESITE).requiresTool().nonOpaque()));

    public static final Block GRANITE_STOOL = registerBlockWithItem("granite_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque()));
    public static final Block GRANITE_TABLE = registerBlockWithItem("granite_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque()));
    public static final Block GRANITE_CHAIR = registerBlockWithItem("granite_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.GRANITE).requiresTool().nonOpaque()));

    public static final Block DIORITE_STOOL = registerBlockWithItem("diorite_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque()));
    public static final Block DIORITE_TABLE = registerBlockWithItem("diorite_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque()));
    public static final Block DIORITE_CHAIR = registerBlockWithItem("diorite_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.DIORITE).requiresTool().nonOpaque()));

    public static final Block DEEPSLATE_STOOL = registerBlockWithItem("deepslate_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque()));
    public static final Block DEEPSLATE_TABLE = registerBlockWithItem("deepslate_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque()));
    public static final Block DEEPSLATE_CHAIR = registerBlockWithItem("deepslate_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool().nonOpaque()));

    public static final Block BLACKSTONE_STOOL = registerBlockWithItem("blackstone_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque()));
    public static final Block BLACKSTONE_TABLE = registerBlockWithItem("blackstone_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque()));
    public static final Block BLACKSTONE_CHAIR = registerBlockWithItem("blackstone_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.BLACKSTONE).requiresTool().nonOpaque()));

    public static final Block BASALT_STOOL = registerBlockWithItem("basalt_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque()));
    public static final Block BASALT_TABLE = registerBlockWithItem("basalt_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque()));
    public static final Block BASALT_CHAIR = registerBlockWithItem("basalt_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.BASALT).requiresTool().nonOpaque()));

    public static final Block TUFF_STOOL = registerBlockWithItem("tuff_stool",
            new StoolBlock(AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque()));
    public static final Block TUFF_TABLE = registerBlockWithItem("tuff_table",
            new StoneTableBlock(AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque()));
    public static final Block TUFF_CHAIR = registerBlockWithItem("tuff_chair",
            new StoneChairBlock(AbstractBlock.Settings.copy(Blocks.TUFF).requiresTool().nonOpaque()));

    public static final Block OAK_STOOL = registerBlockWithItem("oak_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block OAK_BENCH = registerBlockWithItem("oak_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block OAK_TABLE = registerBlockWithItem("oak_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block OAK_CHAIR = registerBlockWithItem("oak_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block SPRUCE_STOOL = registerBlockWithItem("spruce_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block SPRUCE_BENCH = registerBlockWithItem("spruce_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block SPRUCE_TABLE = registerBlockWithItem("spruce_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block SPRUCE_CHAIR = registerBlockWithItem("spruce_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));

    public static final Block BIRCH_STOOL = registerBlockWithItem("birch_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque()));
    public static final Block BIRCH_BENCH = registerBlockWithItem("birch_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block BIRCH_TABLE = registerBlockWithItem("birch_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque()));
    public static final Block BIRCH_CHAIR = registerBlockWithItem("birch_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS).nonOpaque()));

    public static final Block JUNGLE_STOOL = registerBlockWithItem("jungle_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque()));
    public static final Block JUNGLE_BENCH = registerBlockWithItem("jungle_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block JUNGLE_TABLE = registerBlockWithItem("jungle_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque()));
    public static final Block JUNGLE_CHAIR = registerBlockWithItem("jungle_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS).nonOpaque()));

    public static final Block ACACIA_STOOL = registerBlockWithItem("acacia_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque()));
    public static final Block ACACIA_BENCH = registerBlockWithItem("acacia_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block ACACIA_TABLE = registerBlockWithItem("acacia_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque()));
    public static final Block ACACIA_CHAIR = registerBlockWithItem("acacia_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS).nonOpaque().nonOpaque()));

    public static final Block DARK_OAK_STOOL = registerBlockWithItem("dark_oak_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque()));
    public static final Block DARK_OAK_BENCH = registerBlockWithItem("dark_oak_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block DARK_OAK_TABLE = registerBlockWithItem("dark_oak_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque()));
    public static final Block DARK_OAK_CHAIR = registerBlockWithItem("dark_oak_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque()));
    
    public static final Block MANGROVE_STOOL = registerBlockWithItem("mangrove_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque()));
    public static final Block MANGROVE_BENCH = registerBlockWithItem("mangrove_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block MANGROVE_TABLE = registerBlockWithItem("mangrove_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque()));
    public static final Block MANGROVE_CHAIR = registerBlockWithItem("mangrove_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS).nonOpaque()));

    public static final Block CHERRY_STOOL = registerBlockWithItem("cherry_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque()));
    public static final Block CHERRY_BENCH = registerBlockWithItem("cherry_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block CHERRY_TABLE = registerBlockWithItem("cherry_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque()));
    public static final Block CHERRY_CHAIR = registerBlockWithItem("cherry_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque()));

    public static final Block BAMBOO_STOOL = registerBlockWithItem("bamboo_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque()));
    public static final Block BAMBOO_BENCH = registerBlockWithItem("bamboo_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block BAMBOO_TABLE = registerBlockWithItem("bamboo_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque()));
    public static final Block BAMBOO_CHAIR = registerBlockWithItem("bamboo_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque()));

    public static final Block CRIMSON_STOOL = registerBlockWithItem("crimson_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque()));
    public static final Block CRIMSON_BENCH = registerBlockWithItem("crimson_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block CRIMSON_TABLE = registerBlockWithItem("crimson_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque()));
    public static final Block CRIMSON_CHAIR = registerBlockWithItem("crimson_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).nonOpaque()));

    public static final Block WARPED_STOOL = registerBlockWithItem("warped_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque()));
    public static final Block WARPED_BENCH = registerBlockWithItem("warped_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS).nonOpaque()));
    public static final Block WARPED_TABLE = registerBlockWithItem("warped_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque()));
    public static final Block WARPED_CHAIR = registerBlockWithItem("warped_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).nonOpaque()));

    public static final Block TREATED_WOOD_STOOL = registerBlockWithItem("treated_wood_stool",
            new WoodStoolBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block TREATED_WOOD_BENCH = registerBlockWithItem("treated_wood_bench",
            new WoodBenchBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block TREATED_WOOD_TABLE = registerBlockWithItem("treated_wood_table",
            new WoodTableBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final Block TREATED_WOOD_CHAIR = registerBlockWithItem("treated_wood_chair",
            new WoodChairBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    //endregion

    public static final Block WATERING_CAN = registerBlockWithItem("watering_can",
            new WateringCanBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().requiresTool()));
    public static final Block WOODEN_BUCKET = registerBlockWithItem("wooden_bucket",
            new WoodenBucketBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block BROWN_JUG = registerBlockWithItem("brown_jug",
            new JugBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block GRAY_POT = registerBlockWithItem("gray_pot",
            new JugBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block LARGE_JUG = registerBlockWithItem("large_jug",
            new JugBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));

    public static final Block AMPHORA = registerBlockWithItem("amphora",
            new AmphoraBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block BROWN_AMPHORA = registerBlockWithItem("brown_amphora",
            new AmphoraBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block GRAY_VASE = registerBlockWithItem("gray_vase",
            new AmphoraBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));

    public static final Block BROWN_JAR = registerBlockWithItem("brown_jar",
            new JarBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block CLAY_JAR = registerBlockWithItem("clay_jar",
            new JarBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block GRAY_JAR = registerBlockWithItem("gray_jar",
            new JarBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));

    public static final Block BROWN_FAT_POT = registerBlockWithItem("brown_fat_pot",
            new FatPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block FAT_POT = registerBlockWithItem("fat_pot",
            new FatPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block GRAY_FAT_POT = registerBlockWithItem("gray_fat_pot",
            new FatPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));
    public static final Block POT_OF_GOLD = registerBlockWithItem("pot_of_gold",
            new FatPotBlock(AbstractBlock.Settings.copy(Blocks.DECORATED_POT).nonOpaque()));

    public static final Block GOLDEN_CHALICE = registerBlockWithItem("golden_chalice",
            new ChaliceBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque()));

    public static final Block COPPER_TREASURE_HEAP_LAYER = registerBlockWithItem("copper_treasure_heap_layer",
            new LayersAltBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque()));
    public static final Block SILVER_TREASURE_HEAP_LAYER = registerBlockWithItem("silver_treasure_heap_layer",
            new LayersAltBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque()));
    public static final Block GOLD_TREASURE_HEAP_LAYER = registerBlockWithItem("gold_treasure_heap_layer",
            new LayersAltBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).nonOpaque()));

    public static final Block COPPER_COIN_PILE = registerBlockWithItem("copper_coin_pile",
            new CoinPileBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque()));
    public static final Block SILVER_COIN_PILE = registerBlockWithItem("silver_coin_pile",
            new CoinPileBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque()));
    public static final Block GOLD_COIN_PILE = registerBlockWithItem("gold_coin_pile",
            new CoinPileBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK).noCollision().nonOpaque()));

    public static final Block TREATED_STEEL_ROD = registerBlockWithItem("treated_steel_rod",
            new DecorativeRodBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().requiresTool()));

    public static final Block ROPE = registerBlockWithItem("rope",
            new ChainBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).noCollision()));

    public static final Block BRONZE_CHAIN = registerBlockWithItem("bronze_chain",
            new ChainBlock(AbstractBlock.Settings.copy(Blocks.CHAIN)));
    public static final Block BRONZE_BROAD_CHAIN = registerBlockWithItem("bronze_broad_chain",
            new ChainBlock(AbstractBlock.Settings.copy(Blocks.CHAIN)));
    public static final Block SPIKY_CHAIN = registerBlockWithItem("spiky_chain",
            new ChainBlock(AbstractBlock.Settings.copy(Blocks.CHAIN)));

    public static final Block CHIMNEY = registerBlockWithItem("chimney",
            new ChimneyBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS).requiresTool()));

    public static final Block BIG_BRAZIER = registerBlockWithItem("big_brazier",
            new BrazierBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));
    public static final Block SMALL_BRAZIER = registerBlockWithItem("small_brazier",
            new SmallBrazierBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block GILDED_BIG_BRAZIER = registerBlockWithItem("gilded_big_brazier",
            new GildedBrazierBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));
    public static final Block GILDED_SMALL_BRAZIER = registerBlockWithItem("gilded_small_brazier",
            new GildedSmallBrazierBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block FIRE_BOWL = registerBlockWithItem("fire_bowl",
            new FireBowlBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block BONFIRE = registerBlockWithItem("bonfire",
            new BonfireBlock(AbstractBlock.Settings.copy(Blocks.CAMPFIRE).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block SCONCE = registerBlock("sconce",
            new METorchBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));
    public static final Block WALL_SCONCE = registerBlock("wall_sconce",
            new MEWallTorchBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block GILDED_SCONCE = registerBlock("gilded_sconce",
            new METorchBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));
    public static final Block GILDED_WALL_SCONCE = registerBlock("gilded_wall_sconce",
            new MEWallTorchBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block ORCISH_SCONCE = registerBlock("orcish_sconce",
            new OrcSconceBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));
    public static final Block ORCISH_WALL_SCONCE = registerBlock("orcish_wall_sconce",
            new MEWallTorchBlock(AbstractBlock.Settings.copy(Blocks.LANTERN).luminance(createLightLevelFromLitBlockState(15)).nonOpaque().requiresTool()));

    public static final Block GROUND_BOOK = registerBlockWithItem("ground_book",
            new GroundBookBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().noCollision()));
    public static final Block DWARVEN_GROUND_BOOK = registerBlockWithItem("dwarven_ground_book",
            new DwarvenGroundBookBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().noCollision()));

    public static final Block OAK_LADDER = registerBlockWithItem("oak_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block SPRUCE_LADDER = registerBlockWithItem("spruce_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block BIRCH_LADDER = registerBlockWithItem("birch_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block JUNGLE_LADDER = registerBlockWithItem("jungle_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block ACACIA_LADDER = registerBlockWithItem("acacia_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block DARK_OAK_LADDER = registerBlockWithItem("dark_oak_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block MANGROVE_LADDER = registerBlockWithItem("mangrove_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block CHERRY_LADDER = registerBlockWithItem("cherry_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block BAMBOO_LADDER = registerBlockWithItem("bamboo_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_SLAB).sounds(BlockSoundGroup.WOOL).burnable()));
    public static final Block CRIMSON_LADDER = registerBlockWithItem("crimson_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));
    public static final Block WARPED_LADDER = registerBlockWithItem("warped_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.WARPED_SLAB).sounds(BlockSoundGroup.LADDER).burnable()));

    public static final Block TREATED_WOOD_LADDER = registerBlockWithItem("treated_wood_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.LADDER).burnable()));

    public static final Block ROPE_LADDER = registerBlockWithItem("rope_ladder",
            new ThickLadderBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.WOOL).burnable()));

    public static final Block TALL_BLACK_PINE_DOOR = registerDoorBlock("tall_black_pine_door",
            new LargeDoor3x1(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block OAK_STABLE_DOOR = registerDoorBlock("oak_stable_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block REINFORCED_BLACK_PINE_DOOR = registerDoorBlock("reinforced_black_pine_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block REINFORCED_SPRUCE_DOOR = registerDoorBlock("reinforced_spruce_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block SIMPLE_LARCH_GATE = registerDoorBlock("simple_larch_gate",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block RICKETY_SIMPLE_LARCH_DOOR = registerDoorBlock("rickety_simple_larch_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block SPRUCE_STABLE_DOOR = registerDoorBlock("spruce_stable_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block LARGE_STURDY_DOOR = registerDoorBlock("large_sturdy_door",
            new LargeDoor5x3(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block LARCH_HOBBIT_DOOR = registerDoorBlock("larch_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block SPRUCE_HOBBIT_DOOR = registerDoorBlock("spruce_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block BLUE_HOBBIT_DOOR = registerDoorBlock("blue_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block GREEN_HOBBIT_DOOR = registerDoorBlock("green_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block LIGHT_BLUE_HOBBIT_DOOR = registerDoorBlock("light_blue_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block RED_HOBBIT_DOOR = registerDoorBlock("red_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
    public static final Block YELLOW_HOBBIT_DOOR = registerDoorBlock("yellow_hobbit_door",
            new LargeDoor2x2(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block GREAT_GONDORIAN_GATE = registerDoorBlock("great_gondorian_gate",
            new LargeDoor10x5(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)));

    public static final Block GREAT_DWARVEN_GATE = registerDoorBlock("great_dwarven_gate",
            new LargeDoor5x2(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)));
    public static final Block HIDDEN_DWARVEN_DOOR = registerDoorBlock("hidden_dwarven_door",
            new LargeThickDoor3x2(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block VARNISHED_DWARVEN_DOOR = registerDoorBlock("varnished_dwarven_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)));
    public static final Block RUINED_DWARVEN_DOOR = registerDoorBlock("ruined_dwarven_door",
            new LargeDoor4x2(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)));

    public static final Block GREAT_ELVEN_GATE = registerDoorBlock("great_elven_gate",
            new LargeDoor6x2(AbstractBlock.Settings.copy(Blocks.OAK_DOOR)));

    public static final Block GREAT_ORCISH_GATE = registerDoorBlock("great_orcish_gate",
            new LargeDoor10x4(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)));

    public static final Block FANCY_BED = registerBlockWithItem("fancy_bed",
            new CustomBedBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_BED)));
    public static final Block FUR_BED = registerBlockWithItem("fur_bed",
            new CustomBedBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_BED)));
    public static final Block STRAW_BED = registerBlockWithItem("straw_bed",
            new CustomBedBlock(DyeColor.BLACK, AbstractBlock.Settings.copy(Blocks.BLACK_BED)));

    public static Block registerBlock(String name, Block block) {
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static Block registerBlockWithItem(String name, Block block) {
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, name);
        ModBlocks.registerBlockItem(name, block);
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static Block registerDoorBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(MiddleEarth.MOD_ID, name), block);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> {
            return (Boolean) state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    public static void registerModBlocks() {
        LoggerUtil.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
