package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.CropDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.datageneration.content.models.SimpleBlockModel;
import net.jukoz.me.datageneration.content.tags.MineablePickaxe;
import net.jukoz.me.item.ModRessourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {

    protected BlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : BlockDrops.blocks) {
            addDrop(block);
        }
        for (Block block : LeavesDrops.blocks) {
            addDrop(block, BlockLootTableGenerator.dropsWithShears(block));
        }
        for (CropDrops.CropDrop cd : CropDrops.crops) {
            addDrop(cd.crop_block, cropDrops(cd.crop_block, cd.fruit, cd.seeds, cd.builder));
        }
        for (CropDrops.CropDrop cd : CropDrops.wild_crops) {
            addDrop(cd.crop_block,
                    LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                            .with(ItemEntry.builder(cd.seeds)
                                    .conditionally(RandomChanceLootCondition.builder(0.125f)))
                            .with(ItemEntry.builder(cd.fruit))));
        }

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if (set.coal_ore() != null) {
                addDrop(set.coal_ore(), oreDrops(set.coal_ore(), Items.COAL));
            }
            if (set.copper_ore() != null) {
                addDrop(set.copper_ore(),copperOreDrops(set.copper_ore()));
            }
            if (set.tin_ore() != null) {
                addDrop(set.tin_ore(),oreDrops(set.tin_ore(), ModRessourceItems.RAW_TIN));
            }
            if (set.lead_ore() != null) {
                addDrop(set.lead_ore(),oreDrops(set.lead_ore(), ModRessourceItems.RAW_LEAD));
            }
            if (set.silver_ore() != null) {
                addDrop(set.silver_ore(),oreDrops(set.silver_ore(), ModRessourceItems.RAW_SILVER));
            }
            if (set.gold_ore() != null) {
                addDrop(set.gold_ore(),oreDrops(set.gold_ore(), Items.RAW_GOLD));
            }
            if (set.iron_ore() != null) {
                addDrop(set.iron_ore(),oreDrops(set.iron_ore(), Items.RAW_IRON));
            }
            if (set.mithril_ore() != null) {
                addDrop(set.mithril_ore(),oreDrops(set.mithril_ore(), ModRessourceItems.RAW_MITHRIL));
            }
        }
        addDropWithSilkTouch(ModBlocks.STONE_MYCELIUM);
    }
}
