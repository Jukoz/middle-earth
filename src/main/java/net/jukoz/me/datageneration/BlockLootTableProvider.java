package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jukoz.me.block.*;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.jukoz.me.block.special.RocksBlock;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.CropDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.datageneration.content.loot_tables.PotDrops;
import net.jukoz.me.datageneration.content.models.SimplePaneModel;
import net.jukoz.me.datageneration.content.models.SimpleRocksModel;
import net.jukoz.me.datageneration.content.models.TintableCrossModel;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.BooleanProperty;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    protected static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS));
    private final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(this.createSilkTouchCondition());
    private final LootCondition.Builder WITHOUT_SILK_TOUCH_NOR_SHEARS = WITH_SILK_TOUCH_OR_SHEARS.invert();
    private final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f};

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);

        this.registryLookup = registryLookup;
    }

    @Override
    public void generate() {
        for (Block block : BlockDrops.blocks) {
            if (Registries.BLOCK.getId(block).getPath().equals("nurgon")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_NURGON.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("medgon")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_MEDGON.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("gonluin")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_GONLUIN.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("limestone")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_LIMESTONE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("dolomite")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_DOLOMITE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("quartzite")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_QUARTZITE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("jadeite")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_JADEITE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("ashen_stone")) {
                cobbleDrops(block, StoneBlockSets.ASHEN_COBBLESTONE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("ironstone")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_IRONSTONE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("hematite")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_HEMATITE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("gneiss")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_GNEISS.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("izheraban")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_IZHERABAN.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("schist")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_SCHIST.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("galonn")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_GALONN.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("slate")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_SLATE.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("blue_tuff")) {
                cobbleDrops(block, StoneBlockSets.COBBLED_BLUE_TUFF.base());
            } else if (Registries.BLOCK.getId(block).getPath().contains("_door")) {
                addDrop(block, doorDrops(block));
            } else if (Registries.BLOCK.getId(block).getPath().contains("vertical_slab")) {
                addDrop(block, verticalSlabDrops(block));
            } else {
                // TODO : @SlooshyBoi crashes during Datagen
                if (block == null) continue;
                addDrop(block);
            }
        }
        RegistryWrapper.Impl<Enchantment> enchantmentRegistry;

        try {
            enchantmentRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }

        for (LeavesDrops.LeavesDrop drop : LeavesDrops.blocks) {
            if (drop.toString().contains("pine")) {
                addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_DROP_CHANCE)
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS)
                                .with(((LeafEntry.Builder) this.addSurvivesExplosionCondition(drop.drop(), ItemEntry.builder(ModResourceItems.PINECONE)))
                                        .conditionally(TableBonusLootCondition.builder(enchantmentRegistry.getOrThrow(Enchantments.FORTUNE), new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F})))));

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

        for (Block pot : PotDrops.pots) {
            addPottedPlantDrops(pot);
        }

        addDrop(ModNatureBlocks.DEAD_RUSHES, shortPlantDrops(ModNatureBlocks.DEAD_RUSHES));
        addDrop(ModNatureBlocks.FALSE_OATGRASS, shortPlantDrops(ModNatureBlocks.FALSE_OATGRASS));
        addDrop(ModNatureBlocks.BRACKEN, shortPlantDrops(ModNatureBlocks.BRACKEN));

        for (Block block : TintableCrossModel.grassLikeBlocks) {
            addDrop(block, shortPlantDrops(block));
        }
        for (Block block : TintableCrossModel.tintedBlocks) {
            addDropWithSilkTouch(block);
        }

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if (set.coal_ore() != null) {
                addDrop(set.coal_ore(), oreDrops(set.coal_ore(), Items.COAL));
            }
            if (set.copper_ore() != null) {
                addDrop(set.copper_ore(), copperOreDrops(set.copper_ore()));
            }
            if (set.tin_ore() != null) {
                addDrop(set.tin_ore(), oreDrops(set.tin_ore(), ModResourceItems.RAW_TIN));
            }
            if (set.lead_ore() != null) {
                addDrop(set.lead_ore(), oreDrops(set.lead_ore(), ModResourceItems.RAW_LEAD));
            }
            if (set.silver_ore() != null) {
                addDrop(set.silver_ore(), oreDrops(set.silver_ore(), ModResourceItems.RAW_SILVER));
            }
            if (set.gold_ore() != null) {
                addDrop(set.gold_ore(), oreDrops(set.gold_ore(), Items.RAW_GOLD));
            }
            if (set.iron_ore() != null) {
                addDrop(set.iron_ore(), oreDrops(set.iron_ore(), Items.RAW_IRON));
            }
            if (set.mithril_ore() != null) {
                addDrop(set.mithril_ore(), oreDrops(set.mithril_ore(), ModResourceItems.RAW_MITHRIL));
            }
        }

        for (SimplePaneModel.Pane pane : SimplePaneModel.panes){
            addDropWithSilkTouch(pane.pane());
            addDropWithSilkTouch(pane.glass());
        }

        cobbleDrops(ModBlocks.STONE_MYCELIUM, Blocks.COBBLESTONE);

        largeDoorDrop(ModDecorativeBlocks.LARCH_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.BLUE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.GREEN_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.RED_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.YELLOW_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.TALL_BLACK_PINE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.OAK_STABLE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SIMPLE_LARCH_GATE);
        largeDoorDrop(ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SPRUCE_STABLE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.LARGE_STURDY_DOOR);
        largeDoorDrop(ModDecorativeBlocks.GREAT_GONDORIAN_GATE);
        largeDoorDrop(ModDecorativeBlocks.GREAT_DWARVEN_GATE);
        largeDoorDrop(ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.RUINED_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.GREAT_ELVEN_GATE);
        largeDoorDrop(ModDecorativeBlocks.GREAT_ORCISH_GATE);

        cobbleDrops(ModBlocks.SNOWY_GRASS_BLOCK, ModBlocks.DRY_DIRT);

        cobbleDrops(ModNatureBlocks.OLD_PODZOL, Blocks.DIRT);
        cobbleDrops(ModNatureBlocks.LORIEN_PODZOL, Blocks.DIRT);

        for (SimpleRocksModel.Rocks rock : SimpleRocksModel.rocks) {
            rocksDrop(rock.rocks());
        }

        for (SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks) {
            rocksDrop(rock.rocks());
        }
    }

    public void rocksDrop(Block rocksDrop) {
        addDrop(rocksDrop, LootTable.builder()
                .pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(rocksDrop).properties(
                                StatePredicate.Builder.create().exactMatch(RocksBlock.STAGE, 0)))
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(rocksDrop)))
                .pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(rocksDrop).properties(
                                StatePredicate.Builder.create().exactMatch(RocksBlock.STAGE, 1)))
                        .rolls(ConstantLootNumberProvider.create(2.0f))
                        .with(ItemEntry.builder(rocksDrop)))
                .pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(rocksDrop).properties(
                                StatePredicate.Builder.create().exactMatch(RocksBlock.STAGE, 2)))
                        .rolls(ConstantLootNumberProvider.create(3.0f))
                        .with(ItemEntry.builder(rocksDrop)))
                .pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(rocksDrop).properties(
                                StatePredicate.Builder.create().exactMatch(RocksBlock.STAGE, 3)))
                        .rolls(ConstantLootNumberProvider.create(4.0f))
                        .with(ItemEntry.builder(rocksDrop))));
    }

    public LootTable.Builder verticalSlabDrops(Block drop) {
        return LootTable.builder().pool(
                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with((LootPoolEntry.Builder)this.applyExplosionDecay(drop, ItemEntry.builder(drop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(VerticalSlabBlock.DOUBLE, true)))))));
    }

    public void cobbleDrops(Block stoneBlock, Block cobbledBlock) {
        RegistryWrapper.Impl<Enchantment> enchantmentRegistry;

        try {
            enchantmentRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        addDrop(stoneBlock,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().subPredicate(ItemSubPredicateTypes.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(enchantmentRegistry.getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1)))))))
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(stoneBlock)))
                        .pool(LootPool.builder()
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().subPredicate(ItemSubPredicateTypes.ENCHANTMENTS, EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(enchantmentRegistry.getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1)))))).invert())
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(cobbledBlock))));
    }

    public void largeDoorDrop(Block doorblock) {
        addDrop(doorblock, LootTable.builder().pool((LootPool.Builder) this.addSurvivesExplosionCondition(doorblock, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(doorblock).conditionally(BlockStatePropertyLootCondition.builder(doorblock).properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(LargeDoorBlock.PART, 0)))))));
    }

    public LootTable.Builder leavesDrops(Block leaves, Block drop, float... chance) {
        RegistryWrapper.Impl<Enchantment> enchantmentRegistry;

        try {
            enchantmentRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return drops(leaves, this.createWithShearsOrSilkTouchCondition(), ((LeafEntry.Builder) this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop)))
                .conditionally(TableBonusLootCondition.builder(enchantmentRegistry.getOrThrow(Enchantments.FORTUNE), chance))).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS).with((LootPoolEntry.Builder<?>) ((LeafEntry.Builder) this.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))))
                        .conditionally(TableBonusLootCondition.builder(enchantmentRegistry.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE))));
    }
}
