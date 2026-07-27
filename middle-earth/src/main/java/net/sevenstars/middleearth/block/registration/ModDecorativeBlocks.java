
package net.sevenstars.middleearth.block.registration;

import net.sevenstars.api.registries.RegistrationBridge;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.artefact.arkenstone.ArkenstoneBlock;
import net.sevenstars.middleearth.block.special.artefact.arkenstone.ArkenstoneWallBlock;
import net.sevenstars.middleearth.block.special.artisantable.ArtisanTable;
import net.sevenstars.middleearth.block.special.beds.CustomBedBlock;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import net.sevenstars.middleearth.block.special.candles.CandleHolderBlock;
import net.sevenstars.middleearth.block.special.candles.CandleStickBlock;
import net.sevenstars.middleearth.block.special.candles.CeramicLampBlock;
import net.sevenstars.middleearth.block.special.candles.SkullCandleBlock;
import net.sevenstars.middleearth.block.special.coffers.*;
import net.sevenstars.middleearth.block.special.curtains.CurtainsBlock;
import net.sevenstars.middleearth.block.special.curtains.SmallCurtainsBlock;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.fireBlocks.*;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancBlock;
import net.sevenstars.middleearth.block.special.forge.ForgeBlock;
import net.sevenstars.middleearth.block.special.inscriptiontable.InscriptionTableblock;
import net.sevenstars.middleearth.block.special.plate.PlateBlock;
import net.sevenstars.middleearth.block.special.pots.AmphoraBlock;
import net.sevenstars.middleearth.block.special.pots.FatPotBlock;
import net.sevenstars.middleearth.block.special.pots.JarBlock;
import net.sevenstars.middleearth.block.special.pots.JugBlock;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.block.special.sack.SackBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.dwarvenTreatedAnvil.DwarvenShapingAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.elvenTreatedAnvil.ElvenTreatedAnvilblock;
import net.sevenstars.middleearth.block.special.shapingAnvil.orcishTreatedAnvil.OrcishTreatedAnvilblock;
import net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil.StoneAnvilBlock;
import net.sevenstars.middleearth.block.special.shapingAnvil.treatedAnvil.TreatedAnvilblock;
import net.sevenstars.middleearth.block.special.statues.FlipStatueBlock;
import net.sevenstars.middleearth.block.special.statues.StatueBlock;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlock;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlock;
import net.sevenstars.middleearth.block.special.toggeable_lights.*;
import net.sevenstars.middleearth.block.special.torches.METorchBlock;
import net.sevenstars.middleearth.block.special.torches.MEWallTorchBlock;
import net.sevenstars.middleearth.block.special.torches.OrcSconceBlock;
import net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlock;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.minecraft.world.level.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrialSpawnerBlock;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModDecorativeBlocks {
    private record FlowerPotRegistration(Supplier<? extends Block> content, FlowerPotBlock pot) {
    }

    private static final List<FlowerPotRegistration> FLOWER_POTS = new ArrayList<>();
    private static boolean flowerPotsRegistered;

    public static final Block SILVER_LANTERN = registerBlock("silver_lantern",
            SilverLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));
    public static final Block WALL_SILVER_LANTERN = registerBlock("silver_lantern_wall",
            WallSilverLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block LEAD_LANTERN = registerBlock("lead_lantern",
            LeadLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));
    public static final Block WALL_LEAD_LANTERN = registerBlock("lead_lantern_wall",
            WallLeadLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block CRUDE_LANTERN = registerBlock("crude_lantern",
            CrudeLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f).noOcclusion());
    public static final Block WALL_CRUDE_LANTERN = registerBlock("crude_lantern_wall",
            WallCrudeLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block ELVEN_LANTERN = registerBlock("elven_lantern",
            ElvenLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));
    public static final Block WALL_ELVEN_LANTERN = registerBlock("elven_lantern_wall",
            WallElvenLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block DWARVEN_LANTERN = registerBlock("dwarven_lantern",
            DwarvenLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f).noOcclusion());
    public static final Block WALL_DWARVEN_LANTERN = registerBlock("dwarven_lantern_wall",
            WallDwarvenLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));
    
    public static final Block CRYSTAL_LAMP = registerBlock("crystal_lamp",
            CrystalLampBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f).noOcclusion());
    public static final Block WALL_CRYSTAL_LAMP = registerBlock("crystal_lamp_wall",
            WallCrystalLampBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block TREATED_STEEL_LANTERN = registerBlock("treated_steel_lantern",
            ShireLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f).noOcclusion());
    public static final Block WALL_TREATED_STEEL_LANTERN = registerBlock("treated_steel_lantern_wall",
            WallShireLanternBlock::new, BlockBehaviour.Properties.of().lightLevel(createLightLevelFromLitBlockState(15)).strength(1.0f));

    public static final Block FORGE = registerBlock("forge",
            ForgeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(createLightLevelFromLitBlockState(15)).strength(1.65f).requiresCorrectToolForDrops());

    public static final Block STONE_ANVIL = registerBlock("stone_anvil",
            StoneAnvilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion());

    public static final Block TREATED_ANVIL = registerBlock("treated_anvil",
            TreatedAnvilblock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.65f).requiresCorrectToolForDrops().noOcclusion());
    public static final Block DWARVEN_TREATED_ANVIL = registerBlock("dwarven_treated_anvil",
            DwarvenShapingAnvilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.65f).requiresCorrectToolForDrops().noOcclusion());
    public static final Block ELVEN_TREATED_ANVIL = registerBlock("elven_treated_anvil",
            ElvenTreatedAnvilblock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.65f).requiresCorrectToolForDrops().noOcclusion());
    public static final Block ORCISH_TREATED_ANVIL = registerBlock("orcish_treated_anvil",
            OrcishTreatedAnvilblock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(1.65f).requiresCorrectToolForDrops().noOcclusion());

    public static final Block BELLOWS = registerBlock("bellows",
            BellowsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block ARTISAN_TABLE = registerBlock("artisan_table",
            ArtisanTable::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE).noOcclusion());
    public static final Block ORCISH_ARTISAN_TABLE = registerBlock("orcish_artisan_table",
            ArtisanTable::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE).noOcclusion());
    public static final Block INSCRIPTION_TABLE = registerBlock("inscription_table",
            InscriptionTableblock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE).noOcclusion());

    public static final Block STRUCTURE_MANAGER = registerBlock("structure_manager",
            StructureManagerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block ORC_STRUCTURE_MANAGER = registerBlock("orc_structure_manager",
            StructureManagerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS).noOcclusion());
    public static final Block STRUCTURE_NEST = registerBlock("structure_nest",
            StructureNestBlock::new, BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noCollission().noLootTable().noOcclusion().isValidSpawn(ModBlocks::never).noTerrainParticles().pushReaction(PushReaction.BLOCK));

    public static final Block BRIGAND_TRIAL_SPAWNER = registerBlock("brigand_trial_spawner",
            TrialSpawnerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TRIAL_SPAWNER));
    public static final Block BRIGAND_VAULT = registerBlock("brigand_vault",
            VaultBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VAULT).noOcclusion());
    public static final Block SPIDER_TRIAL_SPAWNER = registerBlock("spider_trial_spawner",
            TrialSpawnerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TRIAL_SPAWNER));
    public static final Block SPIDER_VAULT = registerBlock("spider_vault",
            VaultBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VAULT).noOcclusion());

    public static final Block SMALL_CRATE = registerBlock("small_crate",
            CrateBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block THIN_BARREL = registerBlock("thin_barrel",
            ThinBarrelBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block LARCH_COFFER = registerBlock("larch_coffer",
            LarchCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block PINE_COFFER = registerBlock("pine_coffer",
            PineCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block SPRUCE_COFFER = registerBlock("spruce_coffer",
            SpruceCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block FIR_COFFER = registerBlock("fir_coffer",
            FirCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block BEECH_COFFER = registerBlock("beech_coffer",
            BeechCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block CHESTNUT_COFFER = registerBlock("chestnut_coffer",
            ChestnutCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block OAK_COFFER = registerBlock("oak_coffer",
            OakCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block WILLOW_COFFER = registerBlock("willow_coffer",
            WillowCofferBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final Block REINFORCED_CHEST = registerBlock("reinforced_chest",
            ReinforcedChestBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(5.0f).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops());

    public static final Block SACK = registerBlock("sack",
            SackBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.WOOL).noOcclusion());

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            WoodPileBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(1.0f).noOcclusion());

    public static final Block CANDLESTICK = registerBlockWithItem("candlestick",
            CandleStickBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion());
    public static final Block CERAMIC_LAMP = registerBlockWithItem("ceramic_lamp",
            CeramicLampBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion());
    public static final Block CANDLE_HOLDER = registerBlockWithItem("candle_holder",
            CandleHolderBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE).lightLevel(createLightLevelFromLitBlockState(6)).noOcclusion());
    public static final Block SKULL_CANDLE = registerBlockWithItem("skull_candle",
            SkullCandleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion());
    public static final Block CANDLE_HEAP = registerBlockWithItem("candle_heap",
            CandleHeapBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE).noOcclusion().lightLevel(createLightLevelFromLitBlockState(10)));

    public static final Block STONE_LECTERN = registerBlockWithItem("stone_lectern",
            StoneLecternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block CHISELED_DOLOMITE_BOOKSHELF = registerBlockWithItem("chiseled_dolomite_bookshelf",
            ChiseledBookShelfBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final Block BASALT_STATUE = registerBlock("basalt_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).noOcclusion().requiresCorrectToolForDrops());
    public static final Block CALCITE_STATUE = registerBlock("calcite_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block DEEPSLATE_STATUE = registerBlock("deepslate_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block DIORITE_STATUE = registerBlock("diorite_statue",
            FlipStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block GABBRO_STATUE = registerBlock("gabbro_statue",
            FlipStatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block GALONN_STATUE = registerBlock("galonn_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block KHAGALABAN_STATUE = registerBlock("khagalaban_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block MEDGON_SPIKE = registerBlock("medgon_spike",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().requiresCorrectToolForDrops());
    public static final Block PUMICE_STATUE = registerBlock("pumice_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).noOcclusion().requiresCorrectToolForDrops());
    public static final Block TUFF_STATUE = registerBlock("tuff_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF).noOcclusion().requiresCorrectToolForDrops());
    public static final Block ZIGILABAN_STATUE = registerBlock("zigilaban_statue",
            StatueBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF).noOcclusion().requiresCorrectToolForDrops());

    public static final Block ORCISH_DRUM = registerBlockWithItem("orcish_drum",
            OrcishDrumBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NOTE_BLOCK).noOcclusion());

    public static final Block FIRE_OF_ORTHANC = registerBlock("fire_of_orthanc",
            FireOfOrthancBlock::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.METAL).strength(6f).ignitedByLava().isRedstoneConductor(ModBlocks::never).noOcclusion());
    public static final Block TORCH_OF_ORTHANC = registerBlock("torch_of_orthanc",
            (settings) -> new TorchOfOrthancBlock(settings, ParticleTypes.FLAME), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block CERAMIC_PLATE = registerBlockWithItem("ceramic_plate",
            PlateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE).instabreak());
    public static final Block ROTTEN_PLATE = registerBlockWithItem("rotten_plate",
            PlateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).instabreak());
    public static final Block SILVER_PLATE = registerBlockWithItem("silver_plate",
            PlateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).instabreak());
    public static final Block TAPPER = registerBlock("tapper",
            TapperBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).instabreak().noOcclusion());

    public static final Block WOOD_FRAMED_WINDOW = registerBlockWithItem("wood_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WOOD_FRAMED_WINDOW_PANE = registerBlockWithItem("wood_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block AGED_WOOD_WINDOW = registerBlockWithItem("aged_wood_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.WOOD).ignitedByLava());
    public static final Block AGED_WOOD_WINDOW_PANE = registerBlockWithItem("aged_wood_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).sound(SoundType.WOOD).ignitedByLava());

    public static final Block SIMPLE_OAK_WINDOW = registerBlockWithItem("simple_oak_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.WOOD).ignitedByLava());
    public static final Block SIMPLE_OAK_WINDOW_PANE = registerBlockWithItem("simple_oak_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).sound(SoundType.WOOD).ignitedByLava());

    public static final Block WATTLE_AND_BRICK_WINDOW = registerBlockWithItem("wattle_and_brick_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WATTLE_AND_BRICK_WINDOW_PANE = registerBlockWithItem("wattle_and_brick_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block WATTLE_FRAMED_WINDOW = registerBlockWithItem("wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block DARK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("dark_wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block DARK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("dark_wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block BLACK_WATTLE_FRAMED_WINDOW = registerBlockWithItem("black_wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block BLACK_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("black_wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block GREEN_WATTLE_FRAMED_WINDOW = registerBlockWithItem("green_wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block GREEN_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("green_wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block RED_WATTLE_FRAMED_WINDOW = registerBlockWithItem("red_wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block RED_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("red_wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block WHITE_WATTLE_FRAMED_WINDOW = registerBlockWithItem("white_wattle_framed_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WHITE_WATTLE_FRAMED_WINDOW_PANE = registerBlockWithItem("white_wattle_framed_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block MUD_BRICK_ROUND_WINDOW = registerBlockWithItem("mud_brick_round_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block MUD_BRICK_ROUND_WINDOW_PANE = registerBlockWithItem("mud_brick_round_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block WHITE_DAUB_ROUND_WINDOW = registerBlockWithItem("white_daub_round_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WHITE_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("white_daub_round_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block YELLOW_DAUB_ROUND_WINDOW = registerBlockWithItem("yellow_daub_round_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block YELLOW_DAUB_ROUND_WINDOW_PANE = registerBlockWithItem("yellow_daub_round_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block WHITE_DAUB_HOBBIT_WINDOW = registerBlockWithItem("white_daub_hobbit_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WHITE_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("white_daub_hobbit_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW = registerBlockWithItem("yellow_daub_hobbit_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block YELLOW_DAUB_HOBBIT_WINDOW_PANE = registerBlockWithItem("yellow_daub_hobbit_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block PLASTER_HOBBIT_WINDOW = registerBlockWithItem("plaster_hobbit_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block PLASTER_HOBBIT_WINDOW_PANE = registerBlockWithItem("plaster_hobbit_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block PLASTER_ROUND_WINDOW = registerBlockWithItem("plaster_round_window",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block PLASTER_ROUND_WINDOW_PANE = registerBlockWithItem("plaster_round_window_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block LEAD_GLASS = registerBlockWithItem("lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block BLACK_STAINED_LEAD_GLASS = registerBlockWithItem("black_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("blue_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block BROWN_STAINED_LEAD_GLASS = registerBlockWithItem("brown_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block CYAN_STAINED_LEAD_GLASS = registerBlockWithItem("cyan_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("gray_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block GREEN_STAINED_LEAD_GLASS = registerBlockWithItem("green_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("light_blue_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("light_gray_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block LIME_STAINED_LEAD_GLASS = registerBlockWithItem("lime_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block MAGENTA_STAINED_LEAD_GLASS = registerBlockWithItem("magenta_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block ORANGE_STAINED_LEAD_GLASS = registerBlockWithItem("orange_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block PINK_STAINED_LEAD_GLASS = registerBlockWithItem("pink_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block PURPLE_STAINED_LEAD_GLASS = registerBlockWithItem("purple_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block RED_STAINED_LEAD_GLASS = registerBlockWithItem("red_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block WHITE_STAINED_LEAD_GLASS = registerBlockWithItem("white_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block YELLOW_STAINED_LEAD_GLASS = registerBlockWithItem("yellow_stained_lead_glass",
            TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block LEAD_GLASS_PANE = registerBlockWithItem("lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block BLACK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("black_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("blue_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block BROWN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("brown_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block CYAN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("cyan_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("gray_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block GREEN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("green_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_blue_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_gray_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block LIME_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("lime_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block MAGENTA_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("magenta_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block ORANGE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("orange_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block PINK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("pink_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block PURPLE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("purple_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block RED_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("red_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block WHITE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("white_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block YELLOW_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("yellow_stained_lead_glass_pane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final Block BLUE_CUSHION = registerBlockWithItem("blue_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block BROWN_CUSHION = registerBlockWithItem("brown_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block DARK_BLUE_CUSHION = registerBlockWithItem("dark_blue_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block DARK_BROWN_CUSHION = registerBlockWithItem("dark_brown_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block DARK_GREEN_CUSHION = registerBlockWithItem("dark_green_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block DARK_RED_CUSHION = registerBlockWithItem("dark_red_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block GREEN_CUSHION = registerBlockWithItem("green_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());
    public static final Block RED_CUSHION = registerBlockWithItem("red_cushion",
            CushionBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).noOcclusion());

    public static final Block SMALL_BLACK_CURTAIN = registerBlockWithItem("small_black_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_BLUE_CURTAIN = registerBlockWithItem("small_blue_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_BROWN_CURTAIN = registerBlockWithItem("small_brown_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_BURNT_CURTAIN = registerBlockWithItem("small_burnt_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_DARK_BLUE_CURTAIN = registerBlockWithItem("small_dark_blue_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_DARK_BROWN_CURTAIN = registerBlockWithItem("small_dark_brown_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_DARK_GREEN_CURTAIN = registerBlockWithItem("small_dark_green_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_DARK_RED_CURTAIN = registerBlockWithItem("small_dark_red_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_FANCY_BLUE_CURTAIN = registerBlockWithItem("small_fancy_blue_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_FANCY_GREEN_CURTAIN = registerBlockWithItem("small_fancy_green_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_FANCY_RED_CURTAIN = registerBlockWithItem("small_fancy_red_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_GRAY_CURTAIN = registerBlockWithItem("small_gray_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_GREEN_CURTAIN = registerBlockWithItem("small_green_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_PURPLE_CURTAIN = registerBlockWithItem("small_purple_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_RED_CURTAIN = registerBlockWithItem("small_red_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_ROTTEN_CURTAIN = registerBlockWithItem("small_rotten_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_WHITE_CURTAIN = registerBlockWithItem("small_white_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block SMALL_YELLOW_CURTAIN = registerBlockWithItem("small_yellow_curtain",
            SmallCurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));

    public static final Block BLACK_CURTAIN = registerBlockWithItem("black_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block BLUE_CURTAIN = registerBlockWithItem("blue_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block BROWN_CURTAIN = registerBlockWithItem("brown_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block BURNT_CURTAIN = registerBlockWithItem("burnt_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block DARK_BLUE_CURTAIN = registerBlockWithItem("dark_blue_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block DARK_BROWN_CURTAIN = registerBlockWithItem("dark_brown_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block DARK_GREEN_CURTAIN = registerBlockWithItem("dark_green_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block DARK_RED_CURTAIN = registerBlockWithItem("dark_red_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block FANCY_BLUE_CURTAIN = registerBlockWithItem("fancy_blue_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block FANCY_GREEN_CURTAIN = registerBlockWithItem("fancy_green_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block FANCY_RED_CURTAIN = registerBlockWithItem("fancy_red_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block GRAY_CURTAIN = registerBlockWithItem("gray_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block GREEN_CURTAIN = registerBlockWithItem("green_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block PURPLE_CURTAIN = registerBlockWithItem("purple_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block RED_CURTAIN = registerBlockWithItem("red_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block ROTTEN_CURTAIN = registerBlockWithItem("rotten_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block WHITE_CURTAIN = registerBlockWithItem("white_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block YELLOW_CURTAIN = registerBlockWithItem("yellow_curtain",
            CurtainsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY));

    public static final Block PAPER_SHEET = registerBlockWithItem("paper_sheet",
            PaperSheetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE).noOcclusion());

    public static final Block TREATED_WOOD_STOOL = registerBlockWithItem("treated_wood_stool",
            WoodStoolBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block TREATED_WOOD_BENCH = registerBlockWithItem("treated_wood_bench",
            WoodBenchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block TREATED_WOOD_TABLE = registerBlockWithItem("treated_wood_table",
            WoodTableBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block TREATED_WOOD_CHAIR = registerBlockWithItem("treated_wood_chair",
            WoodChairBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    //endregion

    public static final Block WOODEN_BUCKET = registerBlockWithItem("wooden_bucket",
            WoodenBucketBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block WATERING_CAN = registerBlock("watering_can",
            WateringCanBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops());
    public static final Block POTTED_ASPEN_SAPLING = registerPottedBlock("potted_aspen_sapling",
            () -> ModNatureBlocks.ASPEN_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_BEECH_SAPLING = registerPottedBlock("potted_beech_sapling",
            () -> ModNatureBlocks.BEECH_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_CHESTNUT_SAPLING = registerPottedBlock("potted_chestnut_sapling",
            () -> ModNatureBlocks.CHESTNUT_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_HOLLY_SAPLING = registerPottedBlock("potted_holly_sapling",
            () -> ModNatureBlocks.HOLLY_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_FIR_SAPLING = registerPottedBlock("potted_fir_sapling",
            () -> ModNatureBlocks.FIR_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_LARCH_SAPLING = registerPottedBlock("potted_larch_sapling",
            () -> ModNatureBlocks.LARCH_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_LEBETHRON_SAPLING = registerPottedBlock("potted_lebethron_sapling",
            () -> ModNatureBlocks.LEBETHRON_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_WHITE_LEBETHRON_SAPLING = registerPottedBlock("potted_white_lebethron_sapling",
            () -> ModNatureBlocks.WHITE_LEBETHRON_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_MALLORN_SAPLING = registerPottedBlock("potted_mallorn_sapling",
            () -> ModNatureBlocks.MALLORN_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_MAPLE_SAPLING = registerPottedBlock("potted_maple_sapling",
            () -> ModNatureBlocks.MAPLE_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_SILVER_MAPLE_SAPLING = registerPottedBlock("potted_silver_maple_sapling",
            () -> ModNatureBlocks.SILVER_MAPLE_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_MIRKWOOD_SAPLING = registerPottedBlock("potted_mirkwood_sapling",
            () -> ModNatureBlocks.MIRKWOOD_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_PALM_SAPLING = registerPottedBlock("potted_palm_sapling",
            () -> ModNatureBlocks.PALM_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_WHITE_PALM_SAPLING = registerPottedBlock("potted_white_palm_sapling",
            () -> ModNatureBlocks.WHITE_PALM_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_PINE_SAPLING = registerPottedBlock("potted_pine_sapling",
            () -> ModNatureBlocks.PINE_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_BLACK_PINE_SAPLING = registerPottedBlock("potted_black_pine_sapling",
            () -> ModNatureBlocks.BLACK_PINE_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_WHITE_SPRUCE_SAPLING = registerPottedBlock("potted_white_spruce_sapling",
            () -> ModNatureBlocks.WHITE_SPRUCE_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_WILLOW_SAPLING = registerPottedBlock("potted_willow_sapling",
            () -> ModNatureBlocks.WILLOW_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));

    public static final Block POTTED_GREEN_SHRUB = registerPottedBlock("potted_green_shrub",
            () -> ModNatureBlocks.GREEN_SHRUB, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_ELANOR = registerPottedBlock("potted_elanor",
            () -> ModNatureBlocks.ELANOR, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_MALLOS = registerPottedBlock("potted_mallos",
            () -> ModNatureBlocks.MALLOS, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_NIPHREDIL = registerPottedBlock("potted_niphredil",
            () -> ModNatureBlocks.NIPHREDIL, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_SIMBELMYNE = registerPottedBlock("potted_simbelmyne",
            () -> ModNatureBlocks.SIMBELMYNE, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_YELLOW_FLOWER = registerPottedBlock("potted_yellow_flower",
            () -> ModNatureBlocks.YELLOW_FLOWER, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_YELLOW_TROLLIUS = registerPottedBlock("potted_yellow_trollius",
            () -> ModNatureBlocks.YELLOW_TROLLIUS, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_TAN_SHRUB = registerPottedBlock("potted_tan_shrub",
            () -> ModNatureBlocks.TAN_SHRUB, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_BLUE_GENTIAN = registerPottedBlock("potted_blue_gentian",
            () -> ModNatureBlocks.BLUE_GENTIAN, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_GREEN_JEWEL_CORNFLOWER = registerPottedBlock("potted_green_jewel_cornflower",
            () -> ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_NOBLEWHITE = registerPottedBlock("potted_noblewhite",
            () -> ModNatureBlocks.NOBLEWHITE, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_SCORCHED_SHRUB = registerPottedBlock("potted_scorched_shrub",
            () -> ModNatureBlocks.SCORCHED_SHRUB, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_FROZEN_SHRUB = registerPottedBlock("potted_frozen_shrub",
            () -> ModNatureBlocks.FROZEN_SHRUB, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    
    public static final Block POTTED_CAVE_AMANITA = registerPottedBlock("potted_cave_amanita",
            () -> ModNatureBlocks.CAVE_AMANITA, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_DEEP_FIRECAP = registerPottedBlock("potted_deep_firecap",
            () -> ModNatureBlocks.DEEP_FIRECAP, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_GHOSTSHROOM = registerPottedBlock("potted_ghostshroom",
            () -> ModNatureBlocks.GHOSTSHROOM, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_MORSEL = registerPottedBlock("potted_morsel",
            () -> ModNatureBlocks.MORSEL, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_SKYFIRECAP = registerPottedBlock("potted_sky_firecap",
            () -> ModNatureBlocks.SKY_FIRECAP, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_TRUMPET_SHROOM = registerPottedBlock("potted_trumpet_shroom",
            () -> ModNatureBlocks.TRUMPET_SHROOM, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_TUBESHROOM = registerPottedBlock("potted_tubeshroom",
            () -> ModNatureBlocks.TUBESHRROM, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_VIOLET_CAPS = registerPottedBlock("potted_violet_caps",
            () -> ModNatureBlocks.VIOLET_CAPS, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_WHITE_MUSHROOM = registerPottedBlock("potted_white_mushroom",
            () -> ModNatureBlocks.WHITE_MUSHROOM, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block POTTED_YELLOW_AMANITA = registerPottedBlock("potted_yellow_amanita",
            () -> ModNatureBlocks.YELLOW_AMANITA, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));

    public static final Block BROWN_JUG = registerBlockWithItem("brown_jug",
            JugBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block GRAY_POT = registerBlockWithItem("gray_pot",
            JugBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block LARGE_JUG = registerBlockWithItem("large_jug",
            JugBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());

    public static final Block AMPHORA = registerBlockWithItem("amphora",
            AmphoraBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block BROWN_AMPHORA = registerBlockWithItem("brown_amphora",
            AmphoraBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block GRAY_VASE = registerBlockWithItem("gray_vase",
            AmphoraBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());

    public static final Block BROWN_JAR = registerBlockWithItem("brown_jar",
            JarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block CLAY_JAR = registerBlockWithItem("clay_jar",
            JarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block GRAY_JAR = registerBlockWithItem("gray_jar",
            JarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());

    public static final Block BROWN_FAT_POT = registerBlockWithItem("brown_fat_pot",
            FatPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block FAT_POT = registerBlockWithItem("fat_pot",
            FatPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block GRAY_FAT_POT = registerBlockWithItem("gray_fat_pot",
            FatPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());
    public static final Block POT_OF_GOLD = registerBlockWithItem("pot_of_gold",
            FatPotBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).noOcclusion());

    public static final Block GOLDEN_CHALICE = registerBlockWithItem("golden_chalice",
            ChaliceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion());

    public static final Block COPPER_TREASURE_HEAP_LAYER = registerBlockWithItem("copper_treasure_heap_layer",
            LayersAltBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion());
    public static final Block SILVER_TREASURE_HEAP_LAYER = registerBlockWithItem("silver_treasure_heap_layer",
            LayersAltBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion());
    public static final Block GOLD_TREASURE_HEAP_LAYER = registerBlockWithItem("gold_treasure_heap_layer",
            LayersAltBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion());

    public static final Block COPPER_COIN_PILE = registerBlockWithItem("copper_coin_pile",
            CoinPileBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noCollission().noOcclusion());
    public static final Block SILVER_COIN_PILE = registerBlockWithItem("silver_coin_pile",
            CoinPileBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noCollission().noOcclusion());
    public static final Block GOLD_COIN_PILE = registerBlockWithItem("gold_coin_pile",
            CoinPileBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noCollission().noOcclusion());

    public static final Block CRUDE_ROD = registerBlockWithItem("crude_rod",
            DecorativeRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops());
    public static final Block TREATED_STEEL_ROD = registerBlockWithItem("treated_steel_rod",
            DecorativeRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops());

    public static final Block ROPE = registerBlockWithItem("rope",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).noCollission());

    public static final Block BRONZE_CHAIN = registerBlockWithItem("bronze_chain",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
    public static final Block BRONZE_BROAD_CHAIN = registerBlockWithItem("bronze_broad_chain",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
    public static final Block CRUDE_CHAIN = registerBlockWithItem("crude_chain",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
    public static final Block CRUDE_BROAD_CHAIN = registerBlockWithItem("crude_broad_chain",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));
    public static final Block SPIKY_CHAIN = registerBlockWithItem("spiky_chain",
            ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN));

    public static final Block CHIMNEY = registerBlockWithItem("chimney",
            ChimneyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).requiresCorrectToolForDrops());

    public static final Block BIG_BRAZIER = registerBlockWithItem("big_brazier",
            BrazierBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block SMALL_BRAZIER = registerBlockWithItem("small_brazier",
            SmallBrazierBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block GILDED_BIG_BRAZIER = registerBlockWithItem("gilded_big_brazier",
            GildedBrazierBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block GILDED_SMALL_BRAZIER = registerBlockWithItem("gilded_small_brazier",
            GildedSmallBrazierBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block FIRE_BOWL = registerBlockWithItem("fire_bowl",
            FireBowlBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block BONFIRE = registerBlockWithItem("bonfire",
            BonfireBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block SCONCE = registerBlock("sconce",
            METorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block WALL_SCONCE = registerBlock("wall_sconce",
            MEWallTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block GILDED_SCONCE = registerBlock("gilded_sconce",
            METorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block GILDED_WALL_SCONCE = registerBlock("gilded_wall_sconce",
            MEWallTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block ORCISH_SCONCE = registerBlock("orcish_sconce",
            OrcSconceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block ORCISH_WALL_SCONCE = registerBlock("orcish_wall_sconce",
            MEWallTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(createLightLevelFromLitBlockState(15)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block GROUND_BOOK = registerBlockWithItem("ground_book",
            GroundBookBlock::new, BlockBehaviour.Properties.of().instabreak().noOcclusion().noCollission());
    public static final Block DWARVEN_GROUND_BOOK = registerBlockWithItem("dwarven_ground_book",
            DwarvenGroundBookBlock::new, BlockBehaviour.Properties.of().instabreak().noOcclusion().noCollission());

    public static final Block TREATED_WOOD_LADDER = registerBlockWithItem("treated_wood_ladder",
            ThickLadderBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.LADDER).ignitedByLava());

    public static final Block ROPE_LADDER = registerBlockWithItem("rope_ladder",
            ThickLadderBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).sound(SoundType.WOOL).ignitedByLava());

    public static final Block TALL_BLACK_PINE_DOOR = registerBlock("tall_black_pine_door",
            LargeDoor3x1::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final Block TALL_FIR_DOOR = registerBlock("tall_fir_door",
            LargeDoor3x1::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final Block OAK_STABLE_DOOR = registerBlock("oak_stable_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block REINFORCED_BLACK_PINE_DOOR = registerBlock("reinforced_black_pine_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block REINFORCED_SPRUCE_DOOR = registerBlock("reinforced_spruce_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block SIMPLE_LARCH_GATE = registerBlock("simple_larch_gate",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block RICKETY_SIMPLE_LARCH_DOOR = registerBlock("rickety_simple_larch_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block SPRUCE_STABLE_DOOR = registerBlock("spruce_stable_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block LARGE_STURDY_DOOR = registerBlock("large_sturdy_door",
            LargeDoor5x3::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block LARGE_BEECH_FENCE_GATE = registerBlock("large_beech_fence_gate",
            LargeDoor1x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block LARCH_HOBBIT_DOOR = registerBlock("larch_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block SPRUCE_HOBBIT_DOOR = registerBlock("spruce_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block BLUE_HOBBIT_DOOR = registerBlock("blue_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block GREEN_HOBBIT_DOOR = registerBlock("green_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block LIGHT_BLUE_HOBBIT_DOOR = registerBlock("light_blue_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block RED_HOBBIT_DOOR = registerBlock("red_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());
    public static final Block YELLOW_HOBBIT_DOOR = registerBlock("yellow_hobbit_door",
            LargeDoor2x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block GREAT_GONDORIAN_GATE = registerBlock("great_gondorian_gate",
            LargeDoor10x5::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).noOcclusion());

    public static final Block GREAT_DWARVEN_GATE = registerBlock("great_dwarven_gate",
            LargeDoor5x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).noOcclusion());
    public static final Block HIDDEN_DWARVEN_DOOR = registerBlock("hidden_dwarven_door",
            LargeThickDoor3x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion());
    public static final Block VARNISHED_DWARVEN_DOOR = registerBlock("varnished_dwarven_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).noOcclusion());
    public static final Block RUINED_DWARVEN_DOOR = registerBlock("ruined_dwarven_door",
            LargeDoor4x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion());

    public static final Block GREAT_ELVEN_GATE = registerBlock("great_elven_gate",
            LargeDoor6x2::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion());

    public static final Block GREAT_ORCISH_GATE = registerBlock("great_orcish_gate",
            LargeDoor10x4::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).noOcclusion());

    public static final Block FANCY_BED = registerBlockWithItem("fancy_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_BED));
    public static final Block FUR_BED = registerBlockWithItem("fur_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_BED));
    public static final Block STRAW_BED = registerBlockWithItem("straw_bed",
            (settings) -> new CustomBedBlock(DyeColor.BLACK, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_BED));

    public static final Block ARKENSTONE = registerBlock("arkenstone",
            ArkenstoneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel((state -> 7)).noOcclusion().requiresCorrectToolForDrops());
    public static final Block WALL_ARKENSTONE = registerBlock("wall_arkenstone",
            ArkenstoneWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel((state -> 7)).noOcclusion().requiresCorrectToolForDrops());

    public static final Block REINFORCED_SCAFFOLDING = registerBlock("reinforced_scaffolding",
            ReinforcedScaffoldingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).mapColor(MapColor.PODZOL)
                    .sound(SoundType.WOOD).isValidSpawn(ModBlocks::never).pushReaction(PushReaction.DESTROY).isRedstoneConductor(ModBlocks::never));

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));
        return RegistrationBridge.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name).location(), block);
    }

    private static Block registerPottedBlock(String name, Supplier<? extends Block> content, BlockBehaviour.Properties settings) {
        FlowerPotBlock pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, content, settings);
        FLOWER_POTS.add(new FlowerPotRegistration(content, pot));
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));
        return RegistrationBridge.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name).location(), pot);
    }

    public static synchronized void registerFlowerPots() {
        if (flowerPotsRegistered) {
            return;
        }

        FlowerPotBlock emptyPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        for (FlowerPotRegistration registration : FLOWER_POTS) {
            Block content = registration.content().get();
            if (content == null) {
                throw new IllegalStateException("Flower pot content was not registered");
            }
            emptyPot.addPlant(BuiltInRegistries.BLOCK.getKey(content), registration::pot);
        }
        flowerPotsRegistered = true;
    }

    public static Block registerBlockWithItem(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings);
        ModBlocks.registerBlockItem(name, block);
        ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(block.asItem().getDefaultInstance());
        TranslationEntries.blockEntries.add(block);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));
        return RegistrationBridge.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name).location(), block);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean) state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
