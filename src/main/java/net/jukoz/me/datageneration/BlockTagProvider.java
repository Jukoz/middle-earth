package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.block.Block;
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
        var mineablePickaxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/pickaxe")));
        var mineableAxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/axe")));
        var mineableShovel = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/shovel")));
        var mineableHoe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/hoe")));

        var needsStoneTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "needs_stone_tool")));
        var needsIronTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "needs_iron_tool")));
        var needsDiamondTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "needs_diamond_tool")));
        var needsNetheriteTools = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")));

        var seat = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "seat")));
        var table = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "table")));


        mineableAxe.add(MineableAxe.blocks.toArray(new Block[0]));
        mineablePickaxe.add(MineablePickaxe.blocks.toArray(new Block[0]));

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "doors"))).add(Doors.doors.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "trapdoors"))).add(Trapdoors.trapdoors.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "buttons"))).add(Buttons.buttons.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "fences"))).add(Fences.fences.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "fence_gates"))).add(FenceGates.fenceGates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "logs"))).add(Logs.logs.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "pressure_plates"))).add(PressurePlates.pressurePlates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "walls"))).add(Walls.walls.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "planks"))).add(Planks.planks.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "crops"))).add(Crops.crops.toArray(new Block[0]));

        //Ores
        TagKey<Block> iron_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "iron_ores"));
        TagKey<Block> gold_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "gold_ores"));
        TagKey<Block> copper_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "copper_ores"));
        TagKey<Block> coal_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "coal_ores"));

        TagKey<Block> tin_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("me", "tin_ores"));
        TagKey<Block> lead_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("me", "lead_ores"));
        TagKey<Block> silver_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("me", "silver_ores"));
        TagKey<Block> mithril_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("me", "mihtril_pres"));

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
            if (Registries.BLOCK.getId(record.base()).getPath().contains("diftomin")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("epmosto")){
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

        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if (Registries.BLOCK.getId(record.base()).getPath().contains("diftomin")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
                needsIronTools.add(record.stool());
                needsIronTools.add(record.table());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("epmosto")){
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

        for (SimpleStoneChairModel.VanillaChair block : SimpleStoneChairModel.vanillaChairs) {

        }

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
        SimpleWoodChairModel.chairs.forEach(seat::add);
        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            seat.add(block.base());
        });
        SimpleWoodTableModel.tables.forEach(table::add);
        SimpleWoodTableModel.vanillaTables.forEach(block -> {
            table.add(block.base());
        });

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

        needsIronTools.add(OreRockSets.DIFTOMIN.tin_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.lead_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.silver_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.gold_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.iron_ore());

        needsDiamondTools.add(OreRockSets.EPMOSTO.lead_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.silver_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.gold_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.iron_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.mithril_ore());

        mineablePickaxe.add(ModBlocks.STONE_MYCELIUM);
        mineableShovel.add(ModBlocks.ASH_BLOCK);
        mineableShovel.add(ModBlocks.RIVER_SAND);

        mineablePickaxe.add(ModDecorativeBlocks.DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_LANTERN);

        mineableShovel.add(ModBlocks.DRY_DIRT);
        mineableShovel.add(ModBlocks.DRY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.DRY_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ASHEN_DIRT);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_STAIRS);

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

        mineableShovel.add(ModBlocks.DIRT_SLAB);
        mineableShovel.add(ModBlocks.DIRT_STAIRS);
        mineableShovel.add(ModBlocks.MUD_SLAB);
        mineableShovel.add(ModBlocks.MUD_STAIRS);
        mineableShovel.add(ModBlocks.COARSE_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COARSE_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);

        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);
    }
}
