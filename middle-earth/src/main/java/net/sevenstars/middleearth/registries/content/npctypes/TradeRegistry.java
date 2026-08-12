package net.sevenstars.middleearth.registries.content.npctypes;

import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.Trade;

public class TradeRegistry {
    private static final RegistryKey<Registry<Trade>> TRADE_KEY = DynamicRegistriesME.TRADE;

    private static final int LVL1XP = 1;
    private static final int LVL2XP = 5;
    private static final int LVL3XP = 10;
    private static final int LVL4XP = 15;
    private static final int LVL5XP = 20;
    private static final float PRICE_MULTIPLIER_10 = 0.1f;
    private static final float PRICE_MULTIPLIER_20 = 0.2f;

    public final static RegistryKey<Trade> BUY_COAL         = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_coal"));
    public final static RegistryKey<Trade> BUY_COPPER       = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_copper"));
    public final static RegistryKey<Trade> BUY_TIN          = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_tin"));
    public final static RegistryKey<Trade> BUY_LEAD         = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_lead"));
    public final static RegistryKey<Trade> BUY_IRON         = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_iron"));
    public final static RegistryKey<Trade> BUY_LAPIS        = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_lapis"));
    public final static RegistryKey<Trade> BUY_SILVER       = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_silver"));
    public final static RegistryKey<Trade> BUY_GOLD         = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_gold"));
    //public final static RegistryKey<Trade> BUY_             = DynamicRegistriesME.of(TRADE_KEY, MiddleEarth.of("buy_"));


    public static void bootstrap(Registerable<Trade> context) {
        RegistryEntryLookup<Trade> registryEntryLookup = context.getRegistryLookup(TRADE_KEY);

        register(context, registryEntryLookup, BUY_COAL, new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(2), 2,
                Items.COAL.getDefaultStack().copyWithCount(3), LVL1XP, 1));
        register(context, registryEntryLookup, BUY_COPPER, new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(2), 3,
                Items.RAW_COPPER.getDefaultStack().copyWithCount(2), LVL1XP+1, 1));
        register(context, registryEntryLookup, BUY_TIN , new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(4), 3,
                ResourceItemsME.RAW_TIN.getDefaultStack(), LVL2XP+1, 2));
        register(context, registryEntryLookup, BUY_LEAD, new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(6), 2,
                ResourceItemsME.RAW_LEAD.getDefaultStack(), LVL2XP+2, 2));
        register(context, registryEntryLookup, BUY_LAPIS, new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(8), 2,
                Items.LAPIS_LAZULI.getDefaultStack().copyWithCount(2), LVL3XP+6, 3));
        register(context, registryEntryLookup, BUY_IRON, new Trade(ResourceItemsME.COPPER_COIN.getDefaultStack().copyWithCount(10), 4,
                Items.RAW_IRON.getDefaultStack(), LVL3XP, 3, PRICE_MULTIPLIER_10));
        register(context, registryEntryLookup, BUY_SILVER, new Trade(ResourceItemsME.SILVER_COIN.getDefaultStack().copyWithCount(2), 1,
                ResourceItemsME.RAW_SILVER.getDefaultStack(), LVL4XP+5, 4, PRICE_MULTIPLIER_10));
        register(context, registryEntryLookup, BUY_GOLD, new Trade(ResourceItemsME.SILVER_COIN.getDefaultStack().copyWithCount(3), 1,
                Items.RAW_GOLD.getDefaultStack(), LVL5XP+10, 5, PRICE_MULTIPLIER_20));
    }

    private static void register(Registerable<Trade> context, RegistryEntryLookup<Trade> registryEntryLookup, RegistryKey<Trade> registryKey, Trade element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
    }
}
