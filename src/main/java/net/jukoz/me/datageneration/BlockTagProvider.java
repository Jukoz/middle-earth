package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var mineablePickaxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "mineable/pickaxe")));
        var mineableAxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "mineable/axe")));
        var mineableShovel = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "mineable/shovel")));
        var mineableHoe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "mineable/hoe")));
        var swordEfficient = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "sword_efficient")));

        var needsStoneTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "needs_stone_tool")));
        var needsIronTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "needs_iron_tool")));
        var needsDiamondTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "needs_diamond_tool")));
        var needsNetheriteTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("fabric", "needs_tool_level_4")));

        var baseStoneOverworld = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "base_stone_overworld")));

        var seat = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat")));
        var table = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "table")));

        var leaves = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "leaves")));

        var cobwebs = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("me", "cobwebs")));

        mineableAxe.add(MineableAxe.blocks.toArray(new Block[0]));
        mineablePickaxe.add(MineablePickaxe.blocks.toArray(new Block[0]));

        leaves.add(LeavesSets.blocks.toArray(new Block[0]));
        mineableHoe.add(MineableHoe.blocks.toArray(new Block[0]));

        swordEfficient.add(LeavesSets.blocks.toArray(new Block[0]));

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "saplings"))).add(Saplings.saplings.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "doors"))).add(Doors.doors.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "trapdoors"))).add(Trapdoors.trapdoors.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "buttons"))).add(Buttons.buttons.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "fences"))).add(Fences.fences.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "wooden_fences"))).add(Fences.fences.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "fence_gates"))).add(FenceGates.fenceGates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "logs"))).add(Logs.logs.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "pressure_plates"))).add(PressurePlates.pressurePlates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "walls"))).add(Walls.walls.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "planks"))).add(Planks.planks.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "crops"))).add(Crops.crops.toArray(new Block[0]));

        //Ores
        TagKey<Block> iron_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "iron_ores"));
        TagKey<Block> gold_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "gold_ores"));
        TagKey<Block> copper_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "copper_ores"));
        TagKey<Block> coal_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "coal_ores"));

        TagKey<Block> tin_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("me", "tin_ores"));
        TagKey<Block> lead_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("me", "lead_ores"));
        TagKey<Block> silver_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("me", "silver_ores"));
        TagKey<Block> mithril_ores = TagKey.of(RegistryKeys.BLOCK, Identifier.of("me", "mithril_ores"));



        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                getOrCreateTagBuilder(coal_ores)
                        .add(set.coal_ore());
            }
            if(set.copper_ore() != null) {
                getOrCreateTagBuilder(copper_ores)
                        .add(set.copper_ore());
            }
            if(set.tin_ore() != null) {
                getOrCreateTagBuilder(tin_ores)
                        .add(set.tin_ore());
            }
            if(set.lead_ore() != null) {
                getOrCreateTagBuilder(lead_ores)
                        .add(set.lead_ore());
            }
            if(set.silver_ore() != null) {
                getOrCreateTagBuilder(silver_ores)
                        .add(set.silver_ore());
            }
            if(set.gold_ore() != null) {
                getOrCreateTagBuilder(gold_ores)
                        .add(set.gold_ore());
            }
            if(set.iron_ore() != null) {
                getOrCreateTagBuilder(iron_ores)
                        .add(set.iron_ore());
            }
            if(set.mithril_ore() != null) {
                getOrCreateTagBuilder(mithril_ores)
                        .add(set.mithril_ore());
            }
        }

        for (StoneBlockSets.SimpleBlockSet record : StoneBlockSets.sets) {
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
            } else {
                needsStoneTools.add(record.base());
                needsStoneTools.add(record.slab());
                needsStoneTools.add(record.verticalSlab());
                needsStoneTools.add(record.stairs());
                needsStoneTools.add(record.wall());
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
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("medgon")){
                needsDiamondTools.add(record.base());
                needsDiamondTools.add(record.slab());
                needsDiamondTools.add(record.verticalSlab());
                needsDiamondTools.add(record.stairs());
                needsDiamondTools.add(record.wall());
                needsDiamondTools.add(record.stool());
                needsDiamondTools.add(record.table());
            } else {
                needsStoneTools.add(record.base());
                needsStoneTools.add(record.slab());
                needsStoneTools.add(record.verticalSlab());
                needsStoneTools.add(record.stairs());
                needsStoneTools.add(record.wall());
                needsStoneTools.add(record.stool());
                needsStoneTools.add(record.table());
            }
        }

        cobwebs.add(Blocks.COBWEB);
        cobwebs.add(ModNatureBlocks.HANGING_COBWEB);
        cobwebs.add(ModNatureBlocks.CORNER_COBWEB);

        SimpleStoneStoolModel.stools.forEach(seat::add);
        SimpleStoneStoolModel.vanillaStools.forEach(block -> {
            seat.add(block.base());
        });
        SimpleStoneChairModel.chairs.forEach(seat::add);
        SimpleStoneChairModel.vanillaChairs.forEach(block -> {
            seat.add(block.base());
        });
        SimpleStoneTableModel.tables.forEach(table::add);
        SimpleStoneTableModel.vanillaTables.forEach(block -> {
            table.add(block.base());
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

        needsStoneTools.add(OreRockSets.GONLUIN.copper_ore());
        needsStoneTools.add(OreRockSets.GONLUIN.tin_ore());

        needsStoneTools.add(OreRockSets.FROZEN.copper_ore());

        needsStoneTools.add(OreRockSets.ASHEN.copper_ore());
        needsStoneTools.add(OreRockSets.ASHEN.tin_ore());

        needsStoneTools.add(OreRockSets.LIMESTONE.copper_ore());
        needsStoneTools.add(OreRockSets.LIMESTONE.tin_ore());

        needsStoneTools.add(OreRockSets.CALCITE.copper_ore());
        needsStoneTools.add(OreRockSets.CALCITE.tin_ore());

        needsStoneTools.add(OreRockSets.STONE.tin_ore());

        needsStoneTools.add(OreRockSets.DEEPSLATE.tin_ore());
        needsStoneTools.add(OreRockSets.DEEPSLATE.lead_ore());

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

        needsDiamondTools.add(ModDecorativeBlocks.REINFORCED_CHEST);

        needsDiamondTools.add(ModDecorativeBlocks.FIRE_OF_ORTHANC);

        needsStoneTools.add(ModBlocks.BRONZE_BLOCK);
        needsStoneTools.add(ModBlocks.SLAG_BLOCK);
        needsIronTools.add(ModBlocks.STEEL_BLOCK);
        needsIronTools.add(ModBlocks.DWARVEN_STEEL_BLOCK);
        needsIronTools.add(ModBlocks.ELVEN_STEEL_BLOCK);
        needsIronTools.add(ModBlocks.URUK_STEEL_BLOCK);

        mineablePickaxe.add(ModBlocks.STONE_MYCELIUM);
        mineableShovel.add(ModBlocks.ASH_BLOCK);
        mineableShovel.add(ModBlocks.RIVER_SAND);
        mineableShovel.add(ModBlocks.BLACK_SAND);
        mineableShovel.add(ModBlocks.WHITE_SAND);

        mineablePickaxe.add(ModDecorativeBlocks.FIRE_OF_ORTHANC);
        mineablePickaxe.add(ModDecorativeBlocks.DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_LANTERN);

        mineableShovel.add(ModBlocks.GRASSY_DIRT);
        mineableShovel.add(ModBlocks.GRASSY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.GRASSY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.TURF);
        mineableShovel.add(ModBlocks.TURF_SLAB);
        mineableShovel.add(ModBlocks.TURF_STAIRS);

        mineableShovel.add(ModBlocks.MIRE);
        mineableShovel.add(ModBlocks.MIRE_SLAB);
        mineableShovel.add(ModBlocks.MIRE_STAIRS);

        mineableShovel.add(ModBlocks.ASHEN_SAND);
        mineableShovel.add(ModBlocks.ASHEN_SAND_LAYER);

        mineableShovel.add(ModBlocks.ASHEN_GRAVEL);
        mineableShovel.add(ModBlocks.ASHEN_GRAVEL_LAYER);

        mineableShovel.add(ModBlocks.DRY_DIRT);
        mineableShovel.add(ModBlocks.DRY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.DRY_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.ASHEN_DIRT);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT);
        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);

        mineableShovel.add(ModBlocks.COBBLY_DIRT);
        mineableShovel.add(ModBlocks.COBBLY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COBBLY_DIRT_STAIRS);

        mineableHoe.add(ModBlocks.REED_BLOCK);
        mineableHoe.add(ModBlocks.REED_SLAB);
        mineableHoe.add(ModBlocks.REED_VERTICAL_SLAB);
        mineableHoe.add(ModBlocks.REED_STAIRS);
        mineableHoe.add(ModBlocks.REED_WALL);
        mineableHoe.add(ModBlocks.STRAW_BLOCK);
        mineableHoe.add(ModBlocks.STRAW_SLAB);
        mineableHoe.add(ModBlocks.STRAW_VERTICAL_SLAB);
        mineableHoe.add(ModBlocks.STRAW_STAIRS);
        mineableHoe.add(ModBlocks.STRAW_WALL);

        mineableAxe.add(ModDecorativeBlocks.WOOD_PILE);
        mineableAxe.add(ModDecorativeBlocks.ARTISAN_TABLE);

        mineablePickaxe.add(ModBlocks.STONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.COBBLESTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_STONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.GRANITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.DIORITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.ANDESITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB);
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
        mineablePickaxe.add(ModBlocks.BLACKSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
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

        mineableAxe.add(ModBlocks.OAK_WOOD_SLAB);
        mineableAxe.add(ModBlocks.SPRUCE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.BIRCH_WOOD_SLAB);
        mineableAxe.add(ModBlocks.JUNGLE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.ACACIA_WOOD_SLAB);
        mineableAxe.add(ModBlocks.DARK_OAK_WOOD_SLAB);
        mineableAxe.add(ModBlocks.MANGROVE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.CHERRY_WOOD_SLAB);

        mineableAxe.add(ModBlocks.STRIPPED_OAK_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_CHERRY_WOOD_SLAB);

        mineableAxe.add(ModBlocks.STRIPPED_OAK_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_SPRUCE_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_BIRCH_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_JUNGLE_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_ACACIA_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_MANGROVE_WOOD_STAIRS);
        mineableAxe.add(ModBlocks.STRIPPED_CHERRY_WOOD_STAIRS);

        mineableAxe.add(ModBlocks.OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.SPRUCE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.BIRCH_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.JUNGLE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.ACACIA_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.MANGROVE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.CHERRY_WOOD_VERTICAL_SLAB);

        mineableAxe.add(ModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB);

        mineableAxe.add(ModBlocks.OAK_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.SPRUCE_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.BIRCH_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.JUNGLE_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.ACACIA_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.DARK_OAK_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.MANGROVE_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.CHERRY_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.BAMBOO_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.CRIMSON_VERTICAL_SLAB);
        mineableAxe.add(ModBlocks.WARPED_VERTICAL_SLAB);

        mineableShovel.add(ModBlocks.GRAVEL_LAYER);
        mineableShovel.add(ModBlocks.SAND_LAYER);
        mineableShovel.add(ModBlocks.BLACK_SAND_LAYER);
        mineableShovel.add(ModBlocks.WHITE_SAND_LAYER);

        mineableShovel.add(ModBlocks.DIRT_SLAB);
        mineableShovel.add(ModBlocks.DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.MUD_SLAB);
        mineableShovel.add(ModBlocks.MUD_STAIRS);
        mineableShovel.add(ModBlocks.COARSE_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COARSE_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);

        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);
        
        mineablePickaxe.add(ModBlocks.PACKED_MUD_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_STAIRS);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_WALL);

        mineablePickaxe.add(ModBlocks.TUFF_VERTICAL_SLAB);

        mineablePickaxe.add(ModBlocks.CALCITE_SLAB);
        mineablePickaxe.add(ModBlocks.CALCITE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CALCITE_STAIRS);
        mineablePickaxe.add(ModBlocks.CALCITE_WALL);

        mineablePickaxe.add(ModBlocks.SMOOTH_BASALT_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_BASALT_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_BASALT_STAIRS);
        mineablePickaxe.add(ModBlocks.SMOOTH_BASALT_WALL);

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

        mineablePickaxe.add(ModDecorativeBlocks.ALLOY_FURNACE);
    }
}
