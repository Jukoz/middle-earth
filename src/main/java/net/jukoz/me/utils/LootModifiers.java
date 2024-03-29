package net.jukoz.me.utils;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootModifiers {

    private static final Identifier HORSE_LOOT_TABLE_IDENTIFIER = new Identifier("minecraft", "entities/horse");

    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(HORSE_LOOT_TABLE_IDENTIFIER.equals(id)){
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1f))
                        .with(ItemEntry.builder(ModFoodItems.RAW_HORSE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());


                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
