package net.sevenstars.middleearth.utils;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.sevenstars.middleearth.item.ModFoodItems;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class LootModifiers {

    private static final Identifier HORSE_LOOT_TABLE_IDENTIFIER = Identifier.of("minecraft", "entities/horse");
    private static final Identifier GOAT_LOOT_TABLE_IDENTIFIER = Identifier.of("minecraft", "entities/goat");

    public static final Identifier FISHING_LOOT_TABLE_IDENTIFIER = Identifier.of("me", "gameplay/fishing");
    public static final RegistryKey<LootTable> FISHING_LOOT_TABLE =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, FISHING_LOOT_TABLE_IDENTIFIER);

    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(HORSE_LOOT_TABLE_IDENTIFIER.equals(key.getValue()) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModFoodItems.RAW_HORSE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(pool);
            }

            if(GOAT_LOOT_TABLE_IDENTIFIER.equals(key.getValue()) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModResourceItems.FUR))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(pool);
            }
        });
    }
}
