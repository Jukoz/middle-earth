package net.sevenstars.middleearth.datageneration.providers;

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

        wool.add(Wool.blocks.toArray(new Block[0]));

        leaves.add(LeavesSets.blocks.toArray(new Block[0]));
        mineableHoe.add(MineableHoe.blocks.toArray(new Block[0]));

        swordEfficient.add(LeavesSets.blocks.toArray(new Block[0]));

        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "saplings"))).add(Saplings.saplings.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "doors"))).add(Doors.doors.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "trapdoors"))).add(Trapdoors.trapdoors.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "buttons"))).add(Buttons.buttons.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "fences"))).add(Fences.fences.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "wooden_fences"))).add(Fences.fences.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "fence_gates"))).add(FenceGates.fenceGates.toArray(new Block[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of( "logs"))).add(Logs.logs.toArray(new Block[0]));
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

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
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

        /*for (StoneBlockSets.SimpleBlockSet record : StoneBlockSets.sets) {
            if (Registries.BLOCK.getId(record.base()).getPath().contains("nurgon")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("medgon")){
                needsDiamondTools.add(record.base());
                needsDiamondTools.add(record.slab());
                needsDiamondTools.add(record.verticalSlab());
                needsDiamondTools.add(record.stairs());
                needsDiamondTools.add(record.wall());
            }
        }

        baseStoneOverworld.add(Blocks.CALCITE);

        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if(record.base() != StoneBlockSets.ASHEN_STONE.base()) baseStoneOverworld.add(record.base());
            if (Registries.BLOCK.getId(record.base()).getPath().contains("nurgon")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
                needsIronTools.add(record.stool());
                needsIronTools.add(record.table());
                needsIronTools.add(record.rocks());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("medgon")){
                needsDiamondTools.add(record.base());
                needsDiamondTools.add(record.slab());
                needsDiamondTools.add(record.verticalSlab());
                needsDiamondTools.add(record.stairs());
                needsDiamondTools.add(record.wall());
                needsDiamondTools.add(record.stool());
                needsDiamondTools.add(record.table());
                needsDiamondTools.add(record.rocks());
            }
            snapsGoatHorn.add(record.base());
        }*/

        cobwebs.add(Blocks.COBWEB);
        cobwebs.add(ModNatureBlocks.HANGING_WEBS);
        cobwebs.add(ModNatureBlocks.CORNER_COBWEB);

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

        seat.add(ModDecorativeBlocks.BLUE_CUSHION);
        seat.add(ModDecorativeBlocks.BROWN_CUSHION);
        seat.add(ModDecorativeBlocks.DARK_BLUE_CUSHION);
        seat.add(ModDecorativeBlocks.DARK_BROWN_CUSHION);
        seat.add(ModDecorativeBlocks.DARK_GREEN_CUSHION);
        seat.add(ModDecorativeBlocks.DARK_RED_CUSHION);
        seat.add(ModDecorativeBlocks.GREEN_CUSHION);
        seat.add(ModDecorativeBlocks.RED_CUSHION);

        SimpleLadderModel.ladders.forEach(block -> {
            climbable.add(block.ladder());
        });

        SimpleLadderModel.vanillaLadders.forEach(block -> {
            climbable.add(block.ladder());
        });

        SimplePaneModel.panes.forEach(block -> {
            impermeable.add(block.glass());
        });

        climbable.add(ModDecorativeBlocks.ROPE);
        climbable.add(ModDecorativeBlocks.ROPE_LADDER);
        climbable.add(ModBlocks.NET);
        climbable.add(ModNatureBlocks.MIRKWOOD_VINES);

        needsStoneTools.add(OreRockSets.KHAGALABAN.copper_ore());
        needsStoneTools.add(OreRockSets.KHAGALABAN.coal_ore());
        needsStoneTools.add(OreRockSets.KHAGALABAN.tin_ore());
        
        needsStoneTools.add(OreRockSets.ASHEN.copper_ore());
        needsStoneTools.add(OreRockSets.ASHEN.coal_ore());
        needsStoneTools.add(OreRockSets.ASHEN.tin_ore());

        needsStoneTools.add(OreRockSets.LIMESTONE.copper_ore());
        needsStoneTools.add(OreRockSets.LIMESTONE.coal_ore());
        needsStoneTools.add(OreRockSets.LIMESTONE.tin_ore());

        needsStoneTools.add(OreRockSets.CALCITE.copper_ore());
        needsStoneTools.add(OreRockSets.CALCITE.coal_ore());
        needsStoneTools.add(OreRockSets.CALCITE.tin_ore());

        needsStoneTools.add(OreRockSets.SLATE.copper_ore());
        needsStoneTools.add(OreRockSets.SLATE.coal_ore());
        needsStoneTools.add(OreRockSets.SLATE.tin_ore());

        needsStoneTools.add(OreRockSets.IRONSTONE.copper_ore());
        needsStoneTools.add(OreRockSets.IRONSTONE.coal_ore());
        needsStoneTools.add(OreRockSets.IRONSTONE.tin_ore());

        needsStoneTools.add(OreRockSets.STONE.tin_ore());

        needsStoneTools.add(OreRockSets.DEEPSLATE.tin_ore());
        needsStoneTools.add(OreRockSets.DEEPSLATE.lead_ore());

        needsIronTools.add(OreRockSets.NURGON.tin_ore());
        needsIronTools.add(OreRockSets.NURGON.lead_ore());
        needsIronTools.add(OreRockSets.NURGON.silver_ore());
        needsIronTools.add(OreRockSets.NURGON.gold_ore());
        needsIronTools.add(OreRockSets.NURGON.iron_ore());

        needsDiamondTools.add(OreRockSets.MEDGON.lead_ore());
        needsDiamondTools.add(OreRockSets.MEDGON.silver_ore());
        needsDiamondTools.add(OreRockSets.MEDGON.gold_ore());
        needsDiamondTools.add(OreRockSets.MEDGON.iron_ore());
        needsDiamondTools.add(OreRockSets.MEDGON.mithril_ore());

        needsIronTools.add(ModDecorativeBlocks.TREATED_ANVIL);
        needsIronTools.add(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL);
        needsIronTools.add(ModDecorativeBlocks.ELVEN_TREATED_ANVIL);
        needsIronTools.add(ModDecorativeBlocks.ORCISH_TREATED_ANVIL);

        needsIronTools.add(ModDecorativeBlocks.TORCH_OF_ORTHANC);

        needsDiamondTools.add(ModDecorativeBlocks.REINFORCED_CHEST);
        mineableAxe.add(ModDecorativeBlocks.REINFORCED_CHEST);

        needsDiamondTools.add(ModDecorativeBlocks.FIRE_OF_ORTHANC);

        needsIronTools.add(ModDecorativeBlocks.BIG_BRAZIER);
        needsIronTools.add(ModDecorativeBlocks.GILDED_BIG_BRAZIER);
        needsIronTools.add(ModDecorativeBlocks.SMALL_BRAZIER);
        needsIronTools.add(ModDecorativeBlocks.GILDED_SMALL_BRAZIER);
        needsIronTools.add(ModDecorativeBlocks.FIRE_BOWL);

        needsIronTools.add(ModDecorativeBlocks.CRUDE_ROD);
        needsIronTools.add(ModDecorativeBlocks.TREATED_STEEL_ROD);

        needsIronTools.add(ModBlocks.BRONZE_DOOR);
        needsIronTools.add(ModBlocks.CRUDE_DOOR);
        needsIronTools.add(ModBlocks.TREATED_STEEL_DOOR);
        needsIronTools.add(ModBlocks.BRONZE_TRAPDOOR);
        needsIronTools.add(ModBlocks.CRUDE_TRAPDOOR);
        needsIronTools.add(ModBlocks.TREATED_STEEL_TRAPDOOR);

        needsIronTools.add(ModBlocks.BRONZE_BARS);
        needsIronTools.add(ModBlocks.CRUDE_BARS);
        needsIronTools.add(ModBlocks.TREATED_STEEL_BARS);
        needsIronTools.add(ModBlocks.GILDED_BARS);

        needsIronTools.add(ModDecorativeBlocks.SPIKY_CHAIN);

        needsStoneTools.add(ModDecorativeBlocks.WATERING_CAN);

        mineablePickaxe.add(ModDecorativeBlocks.GOLDEN_CHALICE);

        mineablePickaxe.add(ModDecorativeBlocks.ARKENSTONE);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_ARKENSTONE);

        mineableAxe.add(ModDecorativeBlocks.WOODEN_BUCKET);

        needsStoneTools.add(ModBlocks.BRONZE_BLOCK);
        needsStoneTools.add(ModBlocks.CRUDE_BLOCK);
        needsIronTools.add(ModBlocks.STEEL_BLOCK);
        needsIronTools.add(ModBlocks.KHAZAD_STEEL_BLOCK);
        needsIronTools.add(ModBlocks.EDHEL_STEEL_BLOCK);
        needsIronTools.add(ModBlocks.BURZUM_STEEL_BLOCK);

        mineablePickaxe.add(ModBlocks.STONE_MYCELIUM);
        mineableShovel.add(ModBlocks.ASH_BLOCK);
        mineableShovel.add(ModBlocks.RIVER_SAND);
        mineableShovel.add(ModBlocks.BLACK_SAND);
        mineableShovel.add(ModBlocks.WHITE_SAND);

        mineableShovel.add(ModBlocks.SNOWY_GRASS_BLOCK);
        mineableShovel.add(ModNatureBlocks.OLD_PODZOL);
        mineableShovel.add(ModNatureBlocks.LORIEN_PODZOL);

        mineablePickaxe.add(ModDecorativeBlocks.FIRE_OF_ORTHANC);

        mineablePickaxe.add(ModDecorativeBlocks.DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.TREATED_STEEL_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_TREATED_STEEL_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.CRUDE_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_CRUDE_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.LEAD_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_LEAD_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.CRYSTAL_LAMP);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_CRYSTAL_LAMP);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_SILVER_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.ELVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.WALL_ELVEN_LANTERN);

        mineablePickaxe.add(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER);
        mineablePickaxe.add(ModDecorativeBlocks.COPPER_COIN_PILE);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_COIN_PILE);
        mineablePickaxe.add(ModDecorativeBlocks.GOLD_COIN_PILE);


        mineableShovel.add(ModBlocks.GRASSY_DIRT);
        mineableShovel.add(ModBlocks.GRASSY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.GRASSY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.LOAM_GRASS_BLOCK);
        
        mineableShovel.add(ModBlocks.LOAM);
        mineableShovel.add(ModBlocks.LOAM_SLAB);
        mineableShovel.add(ModBlocks.LOAM_STAIRS);
        
        mineableShovel.add(ModBlocks.GRASSY_LOAM);
        mineableShovel.add(ModBlocks.GRASSY_LOAM_SLAB);
        mineableShovel.add(ModBlocks.GRASSY_LOAM_STAIRS);
        
        mineableShovel.add(ModBlocks.LOAM_PATH);
        mineableShovel.add(ModBlocks.LOAM_FARMLAND);

        mineableShovel.add(ModBlocks.SILT_GRASS_BLOCK);

        mineableShovel.add(ModBlocks.SILT);
        mineableShovel.add(ModBlocks.SILT_SLAB);
        mineableShovel.add(ModBlocks.SILT_STAIRS);
        
        mineableShovel.add(ModBlocks.GRASSY_SILT);
        mineableShovel.add(ModBlocks.GRASSY_SILT_SLAB);
        mineableShovel.add(ModBlocks.GRASSY_SILT_STAIRS);

        mineableShovel.add(ModBlocks.SILT_PATH);
        mineableShovel.add(ModBlocks.SILT_FARMLAND);

        mineableShovel.add(ModBlocks.PEAT_GRASS_BLOCK);
        
        mineableShovel.add(ModBlocks.PEAT);
        mineableShovel.add(ModBlocks.PEAT_SLAB);
        mineableShovel.add(ModBlocks.PEAT_STAIRS);

        mineableShovel.add(ModBlocks.GRASSY_PEAT);
        mineableShovel.add(ModBlocks.GRASSY_PEAT_SLAB);
        mineableShovel.add(ModBlocks.GRASSY_PEAT_STAIRS);
        
        mineableShovel.add(ModBlocks.PEAT_PATH);
        mineableShovel.add(ModBlocks.PEAT_FARMLAND);
        
        mineablePickaxe.add(ModBlocks.PEBBLED_GRASS);
        mineablePickaxe.add(ModBlocks.PEBBLED_GRASS_SLAB);
        mineablePickaxe.add(ModBlocks.PEBBLED_GRASS_STAIRS);
        
        mineableShovel.add(ModBlocks.TURF);
        mineableShovel.add(ModBlocks.TURF_SLAB);
        mineableShovel.add(ModBlocks.TURF_STAIRS);
        mineableShovel.add(ModBlocks.TURF_VERTICAL_SLAB);

        mineableShovel.add(ModBlocks.MIRE);
        mineableShovel.add(ModBlocks.MIRE_SLAB);
        mineableShovel.add(ModBlocks.MIRE_STAIRS);

        mineablePickaxe.add(ModBlocks.EMBERS);

        mineableShovel.add(ModBlocks.ASHEN_SAND);
        mineableShovel.add(ModBlocks.ASHEN_SAND_LAYER);

        mineableShovel.add(ModBlocks.ASHEN_GRAVEL);
        mineableShovel.add(ModBlocks.ASHEN_GRAVEL_LAYER);

        mineablePickaxe.add(ModBlocks.SKELETAL_PILE);
        mineablePickaxe.add(ModBlocks.SKELETAL_PILE_LAYER);

        mineableShovel.add(ModBlocks.WASTE_PILE);
        mineableShovel.add(ModBlocks.WASTE_PILE_LAYER);


        mineableShovel.add(ModBlocks.DRY_DIRT);
        mineableShovel.add(ModBlocks.DRY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.DRY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.FOUL_DIRT);
        mineableShovel.add(ModBlocks.FOUL_DIRT_SLAB);
        mineableShovel.add(ModBlocks.FOUL_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.DIRTY_ROOTS);
        mineableShovel.add(ModBlocks.DIRTY_ROOTS_SLAB);
        mineableShovel.add(ModBlocks.DIRTY_ROOTS_STAIRS);

        mineableShovel.add(ModBlocks.ASHEN_DIRT);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT);
        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.COBBLY_DIRT);
        mineableShovel.add(ModBlocks.COBBLY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COBBLY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.SNOWY_DIRT);
        mineableShovel.add(ModBlocks.SNOWY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.SNOWY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.SNOWY_GRASS_BLOCK);

        mineableShovel.add(ModNatureBlocks.LORIEN_PODZOL);

        mineableAxe.add(ModDecorativeBlocks.WOOD_PILE);
        mineableAxe.add(ModDecorativeBlocks.ARTISAN_TABLE);

        mineablePickaxe.add(ModBlocks.BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.MUD_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.END_STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PURPUR_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        mineablePickaxe.add(ModBlocks.CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.EXPOSED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.WEATHERED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.OXIDIZED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.WAXED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL);
        mineablePickaxe.add(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL);

        mineableShovel.add(ModBlocks.GRAVEL_LAYER);
        mineableShovel.add(ModBlocks.SAND_LAYER);
        mineableShovel.add(ModBlocks.BLACK_SAND_LAYER);
        mineableShovel.add(ModBlocks.WHITE_SAND_LAYER);

        mineableShovel.add(ModBlocks.DIRT_SLAB);
        mineableShovel.add(ModBlocks.DIRT_STAIRS);
        mineableShovel.add(ModBlocks.MOSS_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.MUD_SLAB);
        mineableShovel.add(ModBlocks.MOSS_SLAB);
        mineableShovel.add(ModBlocks.MUD_STAIRS);
        mineableShovel.add(ModBlocks.COARSE_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COARSE_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);

        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);
        
        mineablePickaxe.add(ModBlocks.PACKED_MUD_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_STAIRS);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_WALL);

        mineablePickaxe.add(ModBlocks.QUARTZ_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_QUARTZ);
        mineablePickaxe.add(ModBlocks.SMALL_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.QUARTZ_CLUSTER);
        mineablePickaxe.add(ModBlocks.CITRINE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_CITRINE);
        mineablePickaxe.add(ModBlocks.SMALL_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.CITRINE_CLUSTER);
        mineablePickaxe.add(ModBlocks.RED_AGATE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_RED_AGATE);
        mineablePickaxe.add(ModBlocks.SMALL_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.RED_AGATE_CLUSTER);
        mineablePickaxe.add(ModBlocks.GLOWSTONE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_GLOWSTONE);
        mineablePickaxe.add(ModBlocks.SMALL_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.GLOWSTONE_CLUSTER);
    }
}
