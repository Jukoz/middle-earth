package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.CropDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.item.ModRessourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.loot.condition.RandomChanceLootCondition;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    protected static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create()
            .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
    protected static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS));
    private static final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(WITH_SILK_TOUCH);
    private static final LootCondition.Builder WITHOUT_SILK_TOUCH_NOR_SHEARS = WITH_SILK_TOUCH_OR_SHEARS.invert();
    private static final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f};

    protected BlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : BlockDrops.blocks) {
            addDrop(block);
        }
        for (LeavesDrops.LeavesDrop drop : LeavesDrops.blocks) {
            addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_DROP_CHANCE));
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


    }

    public LootTable.Builder leavesDrops(Block leaves, Block drop, float ... chance) {
        return BlockLootTableGenerator.dropsWithSilkTouchOrShears(leaves, ((LeafEntry.Builder)this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop)))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chance))).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with((LootPoolEntry.Builder<?>)((LeafEntry.Builder)this.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, LEAVES_STICK_DROP_CHANCE))));
    }
}
