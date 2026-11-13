package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.datageneration.content.loot_tables.BlockDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.CropDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.LeavesDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.PotDrops;
import net.sevenstars.middleearth.datageneration.content.models.SimplePaneModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleRocksModel;
import net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;
    protected static final float[] SAPLING_COMMON_DROP_CHANCE = new float[]{0.1F, 0.1625F, 0.183333336F, 0.2F};

    public BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);

        this.registryLookup = registryLookup;
    }

    @Override
    public void generate() {

        for (Block block : BlockDrops.blocks) {
            if (Registries.BLOCK.getId(block).getPath().equals("nurgon")) {
                cobbleDrops(block, StoneBlockSetRegistryME.NURGON_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("medgon")) {
                cobbleDrops(block, StoneBlockSetRegistryME.MEDGON_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("khagalaban")) {
                cobbleDrops(block, StoneBlockSetRegistryME.KHAGALABAN_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("limestone")) {
                cobbleDrops(block, StoneBlockSetRegistryME.LIMESTONE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("dolomite")) {
                cobbleDrops(block, StoneBlockSetRegistryME.DOLOMITE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("quartzite")) {
                cobbleDrops(block, StoneBlockSetRegistryME.QUARTZITE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("jadeite")) {
                cobbleDrops(block, StoneBlockSetRegistryME.JADEITE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("ashen_stone")) {
                cobbleDrops(block, StoneBlockSetRegistryME.ASHENSTONE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("ironstone")) {
                cobbleDrops(block, StoneBlockSetRegistryME.IRONSTONE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("hematite")) {
                cobbleDrops(block, StoneBlockSetRegistryME.HEMATITE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("gneiss")) {
                cobbleDrops(block, StoneBlockSetRegistryME.GNEISS_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("izheraban")) {
                cobbleDrops(block, StoneBlockSetRegistryME.IZHERABAN_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("schist")) {
                cobbleDrops(block, StoneBlockSetRegistryME.SCHIST_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("galonn")) {
                cobbleDrops(block, StoneBlockSetRegistryME.GALONN_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("slate")) {
                cobbleDrops(block, StoneBlockSetRegistryME.SLATE_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().equals("blue_tuff")) {
                cobbleDrops(block, StoneBlockSetRegistryME.BLUE_TUFF_SET.cobblestoneBlocks.base());
            } else if (Registries.BLOCK.getId(block).getPath().contains("_door")) {
                addDrop(block, doorDrops(block));
            } else if (Registries.BLOCK.getId(block).getPath().contains("vertical_slab")) {
                addDrop(block, verticalSlabDrops(block));
            } else if (Registries.BLOCK.getId(block).getPath().contains("slab")) {
                addDrop(block, slabDrops(block));
            } else if (Registries.BLOCK.getId(block).getPath().equals("reinforced_scaffolding")) {
                addDrop(block, drops(DecorativeItemsME.REINFORCED_SCAFFOLDING));
            } else {
                // TODO : crashes during Datagen
                if (block == null) continue;
                if(block == Blocks.STONE || block == Blocks.DEEPSLATE) continue;
                addDrop(block);
            }
        }

        for (LeavesDrops.LeavesDrop drop : LeavesDrops.blocks) {
            RegistryWrapper.Impl<Enchantment> impl = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);
            if (drop.toString().contains("pine")) {
                addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_COMMON_DROP_CHANCE).pool(
                        LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(this.createWithoutShearsOrSilkTouchCondition())
                                .with(((LeafEntry.Builder<?>)this.addSurvivesExplosionCondition(drop.block(), ItemEntry.builder(ResourceItemsME.PINECONE)))
                                        .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE),
                                                0.025F, 0.03F, 0.035F, 0.04F, 0.045F)))));
            } else {
                addDrop(drop.block(), this.leavesDrops(drop.block(), drop.drop(), SAPLING_DROP_CHANCE));
            }
        }
        for (Block sapling : Saplings.saplings) {
            addDrop(sapling);
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

        addDrop(NatureBlockRegistryME.CAMPION, shortPlantDrops(NatureBlockRegistryME.CAMPION));
        addDrop(NatureBlockRegistryME.BLUE_BIGLEAF_HYDRANGEA, shortPlantDrops(NatureBlockRegistryME.BLUE_BIGLEAF_HYDRANGEA));
        addDrop(NatureBlockRegistryME.PINK_BIGLEAF_HYDRANGEA, shortPlantDrops(NatureBlockRegistryME.PINK_BIGLEAF_HYDRANGEA));
        addDrop(NatureBlockRegistryME.WHITE_BIGLEAF_HYDRANGEA, shortPlantDrops(NatureBlockRegistryME.WHITE_BIGLEAF_HYDRANGEA));
        addDrop(NatureBlockRegistryME.DEAD_HEATHER_BUSH, shortPlantDrops(NatureBlockRegistryME.DEAD_HEATHER_BUSH));
        addDrop(NatureBlockRegistryME.DRY_HEATHER_BUSH, shortPlantDrops(NatureBlockRegistryME.DRY_HEATHER_BUSH));
        addDrop(NatureBlockRegistryME.DEAD_RUSHES, shortPlantDrops(NatureBlockRegistryME.DEAD_RUSHES));
        addDrop(NatureBlockRegistryME.FALSE_OATGRASS, shortPlantDrops(NatureBlockRegistryME.FALSE_OATGRASS));
        addDrop(NatureBlockRegistryME.HEATHER_BUSH, shortPlantDrops(NatureBlockRegistryME.HEATHER_BUSH));
        addDrop(NatureBlockRegistryME.LARGE_BLUE_FESCUE, shortPlantDrops(NatureBlockRegistryME.LARGE_BLUE_FESCUE));
        addDrop(NatureBlockRegistryME.LARGE_BUSH, shortPlantDrops(NatureBlockRegistryME.LARGE_BUSH));
        addDrop(NatureBlockRegistryME.LARGE_SHRIVELED_SHRUB, shortPlantDrops(NatureBlockRegistryME.LARGE_SHRIVELED_SHRUB));
        addDrop(NatureBlockRegistryME.RED_HEATHER_BUSH, shortPlantDrops(NatureBlockRegistryME.RED_HEATHER_BUSH));
        addDrop(NatureBlockRegistryME.RUSHES, shortPlantDrops(NatureBlockRegistryME.RUSHES));

        addDrop(NatureBlockRegistryME.BRACKEN, shortPlantDrops(NatureBlockRegistryME.BRACKEN));
        addDrop(NatureBlockRegistryME.GIANT_BUTTERBUR, shortPlantDrops(NatureBlockRegistryME.GIANT_BUTTERBUR));

        for (Block block : TintableCrossModel.grassLikeBlocks) {
            addDrop(block, shortPlantDrops(block));
        }
        for (Block block : TintableCrossModel.tintedBlocks) {
            addDropWithSilkTouch(block);
        }

        for (OreStoneSetRegistryME.OreRockSet set : OreStoneSetRegistryME.sets) {
            if (set.coal_ore() != null) {
                addDrop(set.coal_ore(), oreDrops(set.coal_ore(), Items.COAL));
            }
            if (set.copper_ore() != null) {
                addDrop(set.copper_ore(), copperOreDrops(set.copper_ore()));
            }
            if (set.tin_ore() != null) {
                addDrop(set.tin_ore(), oreDrops(set.tin_ore(), ResourceItemsME.RAW_TIN));
            }
            if (set.lead_ore() != null) {
                addDrop(set.lead_ore(), oreDrops(set.lead_ore(), ResourceItemsME.RAW_LEAD));
            }
            if (set.silver_ore() != null) {
                addDrop(set.silver_ore(), oreDrops(set.silver_ore(), ResourceItemsME.RAW_SILVER));
            }
            if (set.gold_ore() != null) {
                addDrop(set.gold_ore(), oreDrops(set.gold_ore(), Items.RAW_GOLD));
            }
            if (set.iron_ore() != null) {
                addDrop(set.iron_ore(), oreDrops(set.iron_ore(), Items.RAW_IRON));
            }
            if (set.mithril_ore() != null) {
                addDrop(set.mithril_ore(), oreDrops(set.mithril_ore(), ResourceItemsME.RAW_MITHRIL));
            }
            if (set.adamant_ore() != null) {
                addDrop(set.adamant_ore(), oreDrops(set.adamant_ore(), ResourceItemsME.ADAMANT));
            }
            if (set.emerald_ore() != null) {
                addDrop(set.emerald_ore(), oreDrops(set.emerald_ore(), Items.EMERALD));
            }
            if (set.ruby_ore() != null) {
                addDrop(set.ruby_ore(), oreDrops(set.ruby_ore(), ResourceItemsME.RUBY));
            }
            if (set.sapphire_ore() != null) {
                addDrop(set.sapphire_ore(), oreDrops(set.sapphire_ore(), ResourceItemsME.SAPPHIRE));
            }
        }

        for (SimplePaneModel.Pane pane : SimplePaneModel.panes){
            addDropWithSilkTouch(pane.pane());
            addDropWithSilkTouch(pane.glass());
        }

        cobbleDrops(BlockRegistryME.STONE_MYCELIUM, Blocks.COBBLESTONE);

        largeDoorDrop(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.SPRUCE_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.BLUE_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.GREEN_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.LIGHT_BLUE_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.RED_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.YELLOW_HOBBIT_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.TALL_BLACK_PINE_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.TALL_FIR_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.OAK_STABLE_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.REINFORCED_SPRUCE_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.REINFORCED_BLACK_PINE_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.SIMPLE_LARCH_GATE);
        largeDoorDrop(DecorativeBlockRegistryME.RICKETY_SIMPLE_LARCH_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.SPRUCE_STABLE_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.LARGE_STURDY_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.LARGE_BEECH_FENCE_GATE);
        largeDoorDrop(DecorativeBlockRegistryME.GREAT_GONDORIAN_GATE);
        largeDoorDrop(DecorativeBlockRegistryME.GREAT_DWARVEN_GATE);
        largeDoorDrop(DecorativeBlockRegistryME.VARNISHED_DWARVEN_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.RUINED_DWARVEN_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.HIDDEN_DWARVEN_DOOR);
        largeDoorDrop(DecorativeBlockRegistryME.GREAT_ELVEN_GATE);
        largeDoorDrop(DecorativeBlockRegistryME.GREAT_ORCISH_GATE);

        cobbleDrops(BlockRegistryME.SNOWY_GRASS_BLOCK, BlockRegistryME.DRY_DIRT);

        cobbleDrops(NatureBlockRegistryME.OLD_PODZOL, Blocks.DIRT);
        cobbleDrops(NatureBlockRegistryME.LORIEN_PODZOL, Blocks.DIRT);

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

    public LootTable.Builder slabDrops(Block drop) {
        return LootTable.builder().pool(
                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(this.applyExplosionDecay(drop, ItemEntry.builder(drop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }
    public LootTable.Builder verticalSlabDrops(Block drop) {
        return LootTable.builder().pool(
                LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(this.applyExplosionDecay(drop, ItemEntry.builder(drop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(StatePredicate.Builder.create().exactMatch(VerticalSlabBlock.DOUBLE, true)))))));
    }

    public void cobbleDrops(Block stoneBlock, Block cobbledBlock) {
        addDrop(stoneBlock, this.dropsWithSilkTouch(stoneBlock, this.applyExplosionDecay(cobbledBlock, ((LeafEntry.Builder<?>)
                ItemEntry.builder(cobbledBlock)))));
    }

    public void largeDoorDrop(Block doorblock) {
        addDrop(doorblock, LootTable.builder().pool(this.addSurvivesExplosionCondition(doorblock, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(doorblock).conditionally(BlockStatePropertyLootCondition.builder(doorblock).properties(StatePredicate.Builder.create().exactMatch(LargeDoorBlock.PART, 0)))))));
    }
}
