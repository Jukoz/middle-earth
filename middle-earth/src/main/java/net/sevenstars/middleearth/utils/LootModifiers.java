package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public class LootModifiers {

    private static final ResourceLocation HORSE_LOOT_TABLE_IDENTIFIER = ResourceLocation.fromNamespaceAndPath("minecraft", "entities/horse");
    private static final ResourceLocation GOAT_LOOT_TABLE_IDENTIFIER = ResourceLocation.fromNamespaceAndPath("minecraft", "entities/goat");
    private static final String HORSE_POOL_NAME = MiddleEarth.MOD_ID + ":horse_meat";
    private static final String GOAT_POOL_NAME = MiddleEarth.MOD_ID + ":goat_fur";

    public static final ResourceLocation FISHING_LOOT_TABLE_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gameplay/fishing");
    public static final ResourceKey<LootTable> FISHING_LOOT_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, FISHING_LOOT_TABLE_IDENTIFIER);
    private static boolean registered;

    public static synchronized void modifyLootTables() {
        if (registered) {
            return;
        }
        NeoForge.EVENT_BUS.addListener(LootModifiers::modifyLootTable);
        registered = true;
    }

    private static void modifyLootTable(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        if (table.isFrozen()) {
            return;
        }

        if (HORSE_LOOT_TABLE_IDENTIFIER.equals(event.getName()) && table.getPool(HORSE_POOL_NAME) == null) {
            table.addPool(createHorsePool().build());
        } else if (GOAT_LOOT_TABLE_IDENTIFIER.equals(event.getName()) && table.getPool(GOAT_POOL_NAME) == null) {
            table.addPool(createGoatPool().build());
        }
    }

    private static LootPool.Builder createHorsePool() {
        return LootPool.lootPool()
                .name(HORSE_POOL_NAME)
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(1f))
                .add(LootItem.lootTableItem(FoodItemsME.RAW_HORSE))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
    }

    private static LootPool.Builder createGoatPool() {
        return LootPool.lootPool()
                .name(GOAT_POOL_NAME)
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(1f))
                .add(LootItem.lootTableItem(ResourceItemsME.FUR))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
    }
}
