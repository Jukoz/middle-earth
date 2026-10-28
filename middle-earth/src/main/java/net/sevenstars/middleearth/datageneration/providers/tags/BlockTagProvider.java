package net.sevenstars.middleearth.datageneration.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.*;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var mineablePickaxe = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "mineable/pickaxe")));
        var mineableAxe = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "mineable/axe")));
        var mineableShovel = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("mineable/shovel")));
        var mineableHoe = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("mineable/hoe")));
        var swordEfficient = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("sword_efficient")));

        var needsStoneTools = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("needs_stone_tool")));
        var needsIronTools = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("needs_iron_tool")));
        var needsDiamondTools = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("needs_diamond_tool")));
        var needsNetheriteTools = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("fabric", "needs_tool_level_4")));

        var baseStoneOverworld = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("base_stone_overworld")));

        var climbable = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("climbable")));
        var impermeable = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("impermeable")));

        var seat = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat")));
        var table = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "table")));

        var leaves = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("leaves")));

        var wool = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("wool")));

        var snapsGoatHorn = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("snaps_goat_horn")));

        var cobwebs = valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "cobwebs")));

        mineableAxe.add(MineableAxe.blocks.toArray(new Block[0]));
        mineablePickaxe.add(MineablePickaxe.blocks.toArray(new Block[0]));
        mineableHoe.add(MineableHoe.blocks.toArray(new Block[0]));
        mineableShovel.add(MineableShovel.blocks.toArray(new Block[0]));

        wool.add(Wool.blocks.toArray(new Block[0]));

        leaves.add(LeavesSets.leaves.toArray(new Block[0]));

        swordEfficient.add(LeavesSets.leaves.toArray(new Block[0]));

        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "saplings"))).add(Saplings.saplings.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "doors"))).add(Doors.doors.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "trapdoors"))).add(Trapdoors.trapdoors.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "stone_buttons"))).add(Buttons.stoneButtons.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "wooden_buttons"))).add(Buttons.woodButtons.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "fences"))).add(Fences.fences.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "wooden_fences"))).add(Fences.fences.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "fence_gates"))).add(FenceGates.fenceGates.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "leaves"))).add(LeavesSets.leaves.toArray(new Block[0])).add(LeavesSets.grayscaleLeaves.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "logs"))).add(Logs.logs.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "logs_that_burn"))).add(Logs.logs.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "pressure_plates"))).add(PressurePlates.pressurePlates.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "walls"))).add(Walls.walls.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "planks"))).add(Planks.planks.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "crops"))).add(Crops.crops.toArray(new Block[0]));

        //Ores
        TagKey<Block> iron_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of( "iron_ores"));
        TagKey<Block> gold_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of( "gold_ores"));
        TagKey<Block> copper_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of( "copper_ores"));
        TagKey<Block> coal_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of( "coal_ores"));

        TagKey<Block> tin_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "tin_ores"));
        TagKey<Block> lead_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "lead_ores"));
        TagKey<Block> silver_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "silver_ores"));
        TagKey<Block> mithril_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "mithril_ores"));

        for (OreStoneSetRegistryME.OreRockSet set : OreStoneSetRegistryME.sets) {
            if(set.coal_ore() != null) {
                valueLookupBuilder(coal_ores)
                        .add(set.coal_ore());
            }
            if(set.copper_ore() != null) {
                valueLookupBuilder(copper_ores)
                        .add(set.copper_ore());
            }
            if(set.tin_ore() != null) {
                valueLookupBuilder(tin_ores)
                        .add(set.tin_ore());
            }
            if(set.lead_ore() != null) {
                valueLookupBuilder(lead_ores)
                        .add(set.lead_ore());
            }
            if(set.silver_ore() != null) {
                valueLookupBuilder(silver_ores)
                        .add(set.silver_ore());
            }
            if(set.gold_ore() != null) {
                valueLookupBuilder(gold_ores)
                        .add(set.gold_ore());
            }
            if(set.iron_ore() != null) {
                valueLookupBuilder(iron_ores)
                        .add(set.iron_ore());
            }
            if(set.mithril_ore() != null) {
                valueLookupBuilder(mithril_ores)
                        .add(set.mithril_ore());
            }
        }

        BlockRecordTypes.BaseStoneSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.baseBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.cobblestoneBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossyCobblestoneBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.brickBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossyBrickBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.crackedBrickBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.tileBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossyTileBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.crackedTileBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.smoothBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossySmoothBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.crackedSmoothBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.polishedBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossyPolishedBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.crackedPolishedBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.pillarBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.mossyPillarBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.crackedPillarBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.chiseledBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.chiseledBricksBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.chiseledPolishedBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.chiseledSmoothBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.chiseledTilesBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.brickworkBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.oldBlocks).forEach(needsIronTools::add);
        BlockRecordTypes.CarvedWindow.getAllBlocks(StoneBlockSetRegistryME.NURGON_SET.carvedWindows).forEach(needsIronTools::add);

        BlockRecordTypes.BaseStoneSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.baseBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.cobblestoneBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossyCobblestoneBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.brickBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossyBrickBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.crackedBrickBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.tileBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossyTileBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.crackedTileBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.smoothBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossySmoothBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.crackedSmoothBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.polishedBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossyPolishedBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.crackedPolishedBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.pillarBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.mossyPillarBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.crackedPillarBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.chiseledBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.chiseledBricksBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.chiseledPolishedBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.chiseledSmoothBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.chiseledTilesBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.brickworkBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.oldBlocks).forEach(needsDiamondTools::add);
        BlockRecordTypes.CarvedWindow.getAllBlocks(StoneBlockSetRegistryME.MEDGON_SET.carvedWindows).forEach(needsDiamondTools::add);

        baseStoneOverworld.add(Blocks.CALCITE);
        baseStoneOverworld.add(MineablePickaxe.baseStoneOverworld.toArray(new Block[0]));

        cobwebs.add(Blocks.COBWEB);
        cobwebs.add(NatureBlockRegistryME.HANGING_WEBS);
        cobwebs.add(NatureBlockRegistryME.WEBBING);
        cobwebs.add(NatureBlockRegistryME.CORNER_COBWEB);

        SimpleStoneStoolModel.stools.forEach(block -> {
            seat.add(block.stool());
        });
        SimpleStoneChairModel.chairs.forEach(block -> {
            seat.add(block.chair());
        });
        SimpleStoneTableModel.tables.forEach(block -> {
            table.add(block.table());
        });
        SimpleWoodStoolModel.stools.forEach(seat::add);
        SimpleWoodStoolModel.vanillaStools.forEach(block -> {
            seat.add(block.base());
        });
        SimpleWoodBenchModel.benchs.forEach(seat::add);
        SimpleWoodBenchModel.vanillaBenchs.forEach(block -> {
            seat.add(block.base());
        });
        SimpleWoodChairModel.chairs.forEach(seat::add);
        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            seat.add(block.base());
        });
        SimpleWoodTableModel.tables.forEach(table::add);
        SimpleWoodTableModel.vanillaTables.forEach(block -> {
            table.add(block.base());
        });

        seat.add(DecorativeBlockRegistryME.BLUE_CUSHION);
        seat.add(DecorativeBlockRegistryME.BROWN_CUSHION);
        seat.add(DecorativeBlockRegistryME.DARK_BLUE_CUSHION);
        seat.add(DecorativeBlockRegistryME.DARK_BROWN_CUSHION);
        seat.add(DecorativeBlockRegistryME.DARK_GREEN_CUSHION);
        seat.add(DecorativeBlockRegistryME.DARK_RED_CUSHION);
        seat.add(DecorativeBlockRegistryME.GREEN_CUSHION);
        seat.add(DecorativeBlockRegistryME.RED_CUSHION);

        SimpleLadderModel.ladders.forEach(block -> {
            climbable.add(block.ladder());
        });

        SimpleLadderModel.vanillaLadders.forEach(block -> {
            climbable.add(block.ladder());
        });

        SimplePaneModel.panes.forEach(block -> {
            impermeable.add(block.glass());
        });

        climbable.add(DecorativeBlockRegistryME.ROPE);
        climbable.add(DecorativeBlockRegistryME.ROPE_LADDER);
        climbable.add(BlockRegistryME.NET);
        climbable.add(NatureBlockRegistryME.MIRKWOOD_VINES);
        climbable.add(DecorativeBlockRegistryME.REINFORCED_SCAFFOLDING);

        needsStoneTools.add(OreStoneSetRegistryME.KHAGALABAN.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.KHAGALABAN.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.KHAGALABAN.tin_ore());
        
        needsStoneTools.add(OreStoneSetRegistryME.ASHEN.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.ASHEN.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.ASHEN.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.LIMESTONE.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.LIMESTONE.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.LIMESTONE.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.CALCITE.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.CALCITE.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.CALCITE.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.SLATE.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.SLATE.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.SLATE.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.IRONSTONE.copper_ore());
        needsStoneTools.add(OreStoneSetRegistryME.IRONSTONE.coal_ore());
        needsStoneTools.add(OreStoneSetRegistryME.IRONSTONE.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.STONE.tin_ore());

        needsStoneTools.add(OreStoneSetRegistryME.DEEPSLATE.tin_ore());
        needsStoneTools.add(OreStoneSetRegistryME.DEEPSLATE.lead_ore());

        needsIronTools.add(OreStoneSetRegistryME.NURGON.tin_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.lead_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.silver_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.gold_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.iron_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.sapphire_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.emerald_ore());
        needsIronTools.add(OreStoneSetRegistryME.NURGON.ruby_ore());

        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.lead_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.silver_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.gold_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.iron_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.emerald_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.ruby_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.sapphire_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.adamant_ore());
        needsDiamondTools.add(OreStoneSetRegistryME.MEDGON.mithril_ore());

        needsStoneTools.add(DecorativeBlockRegistryME.STONE_ANVIL);
        needsIronTools.add(DecorativeBlockRegistryME.TREATED_ANVIL);
        needsIronTools.add(DecorativeBlockRegistryME.DWARVEN_TREATED_ANVIL);
        needsIronTools.add(DecorativeBlockRegistryME.ELVEN_TREATED_ANVIL);
        needsIronTools.add(DecorativeBlockRegistryME.ORCISH_TREATED_ANVIL);

        needsIronTools.add(DecorativeBlockRegistryME.TORCH_OF_ORTHANC);

        needsIronTools.add(DecorativeBlockRegistryME.REINFORCED_CHEST);
        mineableAxe.add(DecorativeBlockRegistryME.REINFORCED_CHEST);

        needsDiamondTools.add(DecorativeBlockRegistryME.FIRE_OF_ORTHANC);

        needsIronTools.add(DecorativeBlockRegistryME.BIG_BRAZIER);
        needsIronTools.add(DecorativeBlockRegistryME.GILDED_BIG_BRAZIER);
        needsIronTools.add(DecorativeBlockRegistryME.SMALL_BRAZIER);
        needsIronTools.add(DecorativeBlockRegistryME.GILDED_SMALL_BRAZIER);
        needsIronTools.add(DecorativeBlockRegistryME.FIRE_BOWL);

        needsStoneTools.add(DecorativeBlockRegistryME.CRUDE_ROD);
        needsStoneTools.add(DecorativeBlockRegistryME.TREATED_STEEL_ROD);

        needsStoneTools.add(BlockRegistryME.BRONZE_DOOR);
        needsStoneTools.add(BlockRegistryME.CRUDE_DOOR);
        needsIronTools.add(BlockRegistryME.TREATED_STEEL_DOOR);
        needsStoneTools.add(BlockRegistryME.BRONZE_TRAPDOOR);
        needsStoneTools.add(BlockRegistryME.CRUDE_TRAPDOOR);
        needsIronTools.add(BlockRegistryME.TREATED_STEEL_TRAPDOOR);
        needsIronTools.add(BlockRegistryME.BURZUM_SPIKES);

        needsStoneTools.add(BlockRegistryME.BRONZE_BARS);
        needsStoneTools.add(BlockRegistryME.CRUDE_BARS);
        needsIronTools.add(BlockRegistryME.TREATED_STEEL_BARS);
        needsIronTools.add(BlockRegistryME.BURZUM_BARS);
        needsIronTools.add(BlockRegistryME.GILDED_BARS);

        needsStoneTools.add(DecorativeBlockRegistryME.SPIKY_CHAIN);

        needsStoneTools.add(DecorativeBlockRegistryME.WATERING_CAN);

        mineablePickaxe.add(DecorativeBlockRegistryME.GOLDEN_CHALICE);

        mineablePickaxe.add(DecorativeBlockRegistryME.ARKENSTONE);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_ARKENSTONE);

        mineableAxe.add(DecorativeBlockRegistryME.WOODEN_BUCKET);

        needsStoneTools.add(BlockRegistryME.BRONZE_BLOCK);
        needsStoneTools.add(BlockRegistryME.CRUDE_BLOCK);
        needsIronTools.add(BlockRegistryME.STEEL_BLOCK);
        needsIronTools.add(BlockRegistryME.KHAZAD_STEEL_BLOCK);
        needsIronTools.add(BlockRegistryME.EDHEL_STEEL_BLOCK);
        needsIronTools.add(BlockRegistryME.BURZUM_STEEL_BLOCK);

        needsIronTools.add(BlockRegistryME.ADAMANT_BLOCK);
        needsIronTools.add(BlockRegistryME.RUBY_BLOCK);
        needsIronTools.add(BlockRegistryME.SAPPHIRE_BLOCK);

        mineablePickaxe.add(BlockRegistryME.STONE_MYCELIUM);

        mineablePickaxe.add(DecorativeBlockRegistryME.FIRE_OF_ORTHANC);

        mineablePickaxe.add(DecorativeBlockRegistryME.DWARVEN_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_DWARVEN_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.TREATED_STEEL_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_TREATED_STEEL_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.CRUDE_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_CRUDE_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.LEAD_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_LEAD_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.CRYSTAL_LAMP);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_CRYSTAL_LAMP);
        mineablePickaxe.add(DecorativeBlockRegistryME.SILVER_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_SILVER_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.ELVEN_LANTERN);
        mineablePickaxe.add(DecorativeBlockRegistryME.WALL_ELVEN_LANTERN);

        mineablePickaxe.add(DecorativeBlockRegistryME.COPPER_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(DecorativeBlockRegistryME.SILVER_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(DecorativeBlockRegistryME.GOLD_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(DecorativeBlockRegistryME.COPPER_COIN_PILE);
        mineablePickaxe.add(DecorativeBlockRegistryME.SILVER_COIN_PILE);
        mineablePickaxe.add(DecorativeBlockRegistryME.GOLD_COIN_PILE);
        
        mineablePickaxe.add(BlockRegistryME.PEBBLED_GRASS);
        mineablePickaxe.add(BlockRegistryME.PEBBLED_GRASS_SLAB);
        mineablePickaxe.add(BlockRegistryME.PEBBLED_GRASS_STAIRS);

        mineablePickaxe.add(BlockRegistryME.SKELETAL_PILE);
        mineablePickaxe.add(BlockRegistryME.SKELETAL_PILE_LAYER);

        mineablePickaxe.add(BlockRegistryME.EMBERS);

        mineableAxe.add(DecorativeBlockRegistryME.WOOD_PILE);
        mineableAxe.add(DecorativeBlockRegistryME.ARTISAN_TABLE);
        mineableAxe.add(DecorativeBlockRegistryME.ORCISH_ARTISAN_TABLE);
        mineableAxe.add(DecorativeBlockRegistryME.INSCRIPTION_TABLE);

        mineablePickaxe.add(BlockRegistryME.BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.MUD_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.SMOOTH_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.CUT_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.CUT_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.PRISMARINE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.DARK_PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.RED_NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.END_STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.PURPUR_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.SMOOTH_QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.WAXED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        mineablePickaxe.add(BlockRegistryME.CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.EXPOSED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.WEATHERED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.OXIDIZED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.WAXED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_WALL);
        mineablePickaxe.add(BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_WALL);

        mineableShovel.add(BlockRegistryME.GRAVEL_LAYER);
        mineableShovel.add(BlockRegistryME.SAND_LAYER);
        mineableShovel.add(BlockRegistryME.BLACK_SAND_LAYER);
        mineableShovel.add(BlockRegistryME.WHITE_SAND_LAYER);

        mineableShovel.add(BlockRegistryME.DIRT_SLAB);
        mineableShovel.add(BlockRegistryME.DIRT_STAIRS);
        mineableShovel.add(BlockRegistryME.MOSS_STAIRS);
        mineableShovel.add(BlockRegistryME.ROOTED_DIRT_STAIRS);
        mineableShovel.add(BlockRegistryME.MUD_SLAB);
        mineableShovel.add(BlockRegistryME.MOSS_SLAB);
        mineableShovel.add(BlockRegistryME.MUD_STAIRS);
        mineableShovel.add(BlockRegistryME.COARSE_DIRT_SLAB);
        mineableShovel.add(BlockRegistryME.COARSE_DIRT_STAIRS);
        mineableShovel.add(BlockRegistryME.ROOTED_DIRT_SLAB);

        mineableShovel.add(BlockRegistryME.ROOTED_DIRT_SLAB);
        
        mineablePickaxe.add(BlockRegistryME.PACKED_MUD_SLAB);
        mineablePickaxe.add(BlockRegistryME.PACKED_MUD_VERTICAL_SLAB);
        mineablePickaxe.add(BlockRegistryME.PACKED_MUD_STAIRS);
        mineablePickaxe.add(BlockRegistryME.PACKED_MUD_WALL);

        mineablePickaxe.add(BlockRegistryME.QUARTZ_BLOCK);
        mineablePickaxe.add(BlockRegistryME.BUDDING_QUARTZ);
        mineablePickaxe.add(BlockRegistryME.SMALL_QUARTZ_BUD);
        mineablePickaxe.add(BlockRegistryME.MEDIUM_QUARTZ_BUD);
        mineablePickaxe.add(BlockRegistryME.LARGE_QUARTZ_BUD);
        mineablePickaxe.add(BlockRegistryME.QUARTZ_CLUSTER);
        mineablePickaxe.add(BlockRegistryME.CITRINE_BLOCK);
        mineablePickaxe.add(BlockRegistryME.BUDDING_CITRINE);
        mineablePickaxe.add(BlockRegistryME.SMALL_CITRINE_BUD);
        mineablePickaxe.add(BlockRegistryME.MEDIUM_CITRINE_BUD);
        mineablePickaxe.add(BlockRegistryME.LARGE_CITRINE_BUD);
        mineablePickaxe.add(BlockRegistryME.CITRINE_CLUSTER);
        mineablePickaxe.add(BlockRegistryME.RED_AGATE_BLOCK);
        mineablePickaxe.add(BlockRegistryME.BUDDING_RED_AGATE);
        mineablePickaxe.add(BlockRegistryME.SMALL_RED_AGATE_BUD);
        mineablePickaxe.add(BlockRegistryME.MEDIUM_RED_AGATE_BUD);
        mineablePickaxe.add(BlockRegistryME.LARGE_RED_AGATE_BUD);
        mineablePickaxe.add(BlockRegistryME.RED_AGATE_CLUSTER);
        mineablePickaxe.add(BlockRegistryME.GLOWSTONE_BLOCK);
        mineablePickaxe.add(BlockRegistryME.BUDDING_GLOWSTONE);
        mineablePickaxe.add(BlockRegistryME.SMALL_GLOWSTONE_BUD);
        mineablePickaxe.add(BlockRegistryME.MEDIUM_GLOWSTONE_BUD);
        mineablePickaxe.add(BlockRegistryME.LARGE_GLOWSTONE_BUD);
        mineablePickaxe.add(BlockRegistryME.GLOWSTONE_CLUSTER);
    }
}
