package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.block.StoneBlockSets;
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

        mineableAxe.add(ModBlocks.STONE_MYCELIUM);

        mineablePickaxe.add(ModDecorativeBlocks.DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_LANTERN);

        mineableShovel.add(ModBlocks.DRY_DIRT);
        mineableShovel.add(ModBlocks.ASHEN_DIRT);

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
    }
}
