package net.sevenstars.middleearth.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.content.factions.FactionFreePeople;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class FactionRegistryME extends DynamicRegistriesAPI<Faction> {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesHH.FACTION;

    public final static RegistryKey<Faction> GONDOR  = of(FACTION_KEY, MiddleEarth.id("gondor"));
    public final static RegistryKey<Faction> ROHAN  = of(FACTION_KEY, MiddleEarth.id("rohan"));
    public final static RegistryKey<Faction> DALE  = of(FACTION_KEY, MiddleEarth.id("dale"));
    public final static RegistryKey<Faction> LONGBEARDS  = of(FACTION_KEY, MiddleEarth.id("longbeards"));
    public final static RegistryKey<Faction> LONGBEARDS_EREBOR  = of(FACTION_KEY, MiddleEarth.id("longbeards.erebor"));
    public final static RegistryKey<Faction> LOTHLORIEN  = of(FACTION_KEY, MiddleEarth.id("lothlorien"));
    public final static RegistryKey<Faction> WOODLAND_REALM  = of(FACTION_KEY, MiddleEarth.id("woodland_realm"));
    public final static RegistryKey<Faction> MORDOR  = of(FACTION_KEY, MiddleEarth.id("mordor"));
    public final static RegistryKey<Faction> HOBGOBLIN_TRIBES  = of(FACTION_KEY, MiddleEarth.id("hobgoblin_tribes"));
    public final static RegistryKey<Faction> HOBGOBLIN_TRIBES_GUNDABAD  = of(FACTION_KEY, MiddleEarth.id("hobgoblin_tribes.gundabad"));
    public final static RegistryKey<Faction> GOBLIN_TOWN  = of(FACTION_KEY, MiddleEarth.id("goblin_town"));
    public final static RegistryKey<Faction> MORIA  = of(FACTION_KEY, MiddleEarth.id("moria"));
    public final static RegistryKey<Faction> ISENGARD  = of(FACTION_KEY, MiddleEarth.id("isengard"));
    public final static RegistryKey<Faction> SHIRE  = of(FACTION_KEY, MiddleEarth.id("shire"));
    public final static RegistryKey<Faction> BRIGAND  = of(FACTION_KEY, MiddleEarth.id("brigand"));
    public final static RegistryKey<Faction> WILD_GOBLINS  = of(FACTION_KEY, MiddleEarth.id("wild_goblins"));

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> registryEntryLookup = context.getRegistryLookup(FACTION_KEY);

        Map<RegistryKey<Faction>, Faction> registryMap = new HashMap<>();
        registryMap.putAll(FactionFreePeople.fetch());

        MiddleEarth.LOGGER.logDebugMsg(registryMap.toString());
        for(Map.Entry<RegistryKey<Faction>, Faction> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }

    /*
      // [LANG datagen]
        TranslationEntries.factionEntries.add(registryKey.getValue().getPath());
        /*
        SpawnDataHandler spawnDataHandler = element.getSpawnData();
        if(spawnDataHandler != null && spawnDataHandler.getAllSpawnIdentifiers() != null)
            for(Identifier spawnId : spawnDataHandler.getAllSpawnIdentifiers())
                TranslationEntries.spawnEntries.add(spawnId.getPath());
         */
}
