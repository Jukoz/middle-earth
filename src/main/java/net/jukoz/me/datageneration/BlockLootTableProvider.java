package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.CropDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.datageneration.content.models.SimpleFanModel;
import net.jukoz.me.datageneration.content.models.TintableCrossModel;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.Registries;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    protected static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS));
    private static final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(WITH_SILK_TOUCH);
    private static final LootCondition.Builder WITHOUT_SILK_TOUCH_NOR_SHEARS = WITH_SILK_TOUCH_OR_SHEARS.invert();
    private static final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f};

    protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for (Block block : BlockDrops.blocks) {
            if(Registries.BLOCK.getId(block).getPath().equals("diftomin")){
                cobbleDrops(block, StoneBlockSets.COBBLED_DIFTOMIN.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("epmosto")){
                cobbleDrops(block, StoneBlockSets.COBBLED_EPMOSTO.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("gonluin")){
                cobbleDrops(block, StoneBlockSets.COBBLED_GONLUIN.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("limestone")){
                cobbleDrops(block, StoneBlockSets.COBBLED_LIMESTONE.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("dolomite")){
                cobbleDrops(block, StoneBlockSets.COBBLED_DOLOMITE.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("quartzite")){
                cobbleDrops(block, StoneBlockSets.COBBLED_QUARTZITE.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("frozen_stone")){
                cobbleDrops(block, StoneBlockSets.FROZEN_COBBLESTONE.base());
            }else if(Registries.BLOCK.getId(block).getPath().equals("ashen_stone")){
                cobbleDrops(block, StoneBlockSets.ASHEN_COBBLESTONE.base());
            }else if(Registries.BLOCK.getId(block).getPath().contains("_door")){
                addDrop(block, doorDrops(block));
            } else {
                addDrop(block);
            }
        }

        for (LeavesDrops.LeavesDrop drop : LeavesDrops.blocks) {
            if(drop.toString().contains("pine")){
                addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_DROP_CHANCE)
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS)
                                .with(((LeafEntry.Builder)this.addSurvivesExplosionCondition(drop.drop(), ItemEntry.builder(ModResourceItems.PINECONE)))
                                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F})))));

            } else {
                addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_DROP_CHANCE));
            }
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

        for(Block block : TintableCrossModel.grassLikeBlocks) {
            addDrop(block, shortPlantDrops(block));
        }
        for(Block block : TintableCrossModel.tintedBlocks) {
            addDropWithSilkTouch(block);
        }

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if (set.coal_ore() != null) {
                addDrop(set.coal_ore(), oreDrops(set.coal_ore(), Items.COAL));
            }
            if (set.copper_ore() != null) {
                addDrop(set.copper_ore(),copperOreDrops(set.copper_ore()));
            }
            if (set.tin_ore() != null) {
                addDrop(set.tin_ore(),oreDrops(set.tin_ore(), ModResourceItems.RAW_TIN));
            }
            if (set.lead_ore() != null) {
                addDrop(set.lead_ore(),oreDrops(set.lead_ore(), ModResourceItems.RAW_LEAD));
            }
            if (set.silver_ore() != null) {
                addDrop(set.silver_ore(),oreDrops(set.silver_ore(), ModResourceItems.RAW_SILVER));
            }
            if (set.gold_ore() != null) {
                addDrop(set.gold_ore(),oreDrops(set.gold_ore(), Items.RAW_GOLD));
            }
            if (set.iron_ore() != null) {
                addDrop(set.iron_ore(),oreDrops(set.iron_ore(), Items.RAW_IRON));
            }
            if (set.mithril_ore() != null) {
                addDrop(set.mithril_ore(),oreDrops(set.mithril_ore(), ModResourceItems.RAW_MITHRIL));
            }
        }

        cobbleDrops(ModBlocks.STONE_MYCELIUM, Blocks.COBBLESTONE);
    }

    public void cobbleDrops(Block stoneBlock, Block cobbledBlock){
        addDrop(stoneBlock,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .conditionally(WITH_SILK_TOUCH)
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(stoneBlock)))
                        .pool(LootPool.builder()
                                .conditionally(WITHOUT_SILK_TOUCH)
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(cobbledBlock))));
    }

    public LootTable.Builder leavesDrops(Block leaves, Block drop, float ... chance) {
        return BlockLootTableGenerator.dropsWithSilkTouchOrShears(leaves, ((LeafEntry.Builder)this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop)))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chance))).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with((LootPoolEntry.Builder<?>)((LeafEntry.Builder)this.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, LEAVES_STICK_DROP_CHANCE))));
    }
}
