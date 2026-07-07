package net.sevenstars.middleearth.registries.content.factions;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.pools.*;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;

public class FactionRegistry {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesME.FACTION;

    public final static RegistryKey<Faction> GONDOR = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("gondor"));
    public final static RegistryKey<Faction> ROHAN = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("rohan"));
    public final static RegistryKey<Faction> DALE = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("dale"));
    public final static RegistryKey<Faction> LONGBEARDS = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("longbeards"));
    public final static RegistryKey<Faction> LONGBEARDS_EREBOR = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of(LONGBEARDS.getValue().getPath(), "erebor"));
    public final static RegistryKey<Faction> LOTHLORIEN = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("lothlorien"));
    public final static RegistryKey<Faction> WOODLAND_REALM = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("woodland_realm"));
    public final static RegistryKey<Faction> MORDOR = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("mordor"));
    public final static RegistryKey<Faction> HOBGOBLIN_TRIBES =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("hobgoblin_tribes"));
    public final static RegistryKey<Faction> HOBGOBLIN_TRIBES_GUNDABAD =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of(HOBGOBLIN_TRIBES.getValue().getPath(), "gundabad"));
    public final static RegistryKey<Faction> GOBLIN_TOWN =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("goblin_town"));
    public final static RegistryKey<Faction> MORIA =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("moria"));
    public final static RegistryKey<Faction> ISENGARD = DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("isengard"));
    public final static RegistryKey<Faction> SHIRE =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("shire"));
    public final static RegistryKey<Faction> BRIGAND =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("brigand"));
    public final static RegistryKey<Faction> WILD_GOBLINS =  DynamicRegistriesME.of(FACTION_KEY, MiddleEarth.of("wild_goblins"));

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> registryEntryLookup = context.getRegistryLookup(FACTION_KEY);

        register(context, registryEntryLookup, GONDOR, GondorFactionPool.GONDOR);
        register(context, registryEntryLookup, ROHAN, RohanFactionPool.ROHAN);
        register(context, registryEntryLookup, DALE, DaleFactionPool.DALE);
        register(context, registryEntryLookup, LONGBEARDS, LongbeardsFactionPool.LONGBEARDS);
        register(context, registryEntryLookup, LONGBEARDS_EREBOR, LongbeardsFactionPool.EREBOR);
        register(context, registryEntryLookup, LOTHLORIEN, LothlorienFactionPool.LOTHLORIEN);
        register(context, registryEntryLookup, WOODLAND_REALM, WoodlandRealmFactionPool.WOODLAND_REALM);
        register(context, registryEntryLookup, MORDOR, MordorFactionPool.MORDOR);
        register(context, registryEntryLookup, HOBGOBLIN_TRIBES, HobgoblinTribesFactionPool.HOBGOBLIN_TRIBES);
        register(context, registryEntryLookup, HOBGOBLIN_TRIBES_GUNDABAD, HobgoblinTribesFactionPool.GUNDABAD);
        register(context, registryEntryLookup, GOBLIN_TOWN, GoblinTownFactionPool.GOBLIN_TOWN);
        register(context, registryEntryLookup, MORIA, MoriaFactionPool.MORIA);
        register(context, registryEntryLookup, ISENGARD, IsengardFactionPool.ISENGARD);
        register(context, registryEntryLookup, SHIRE, ShireFactionPool.SHIRE);
        register(context, registryEntryLookup, BRIGAND, BrigandFactionPool.BRIGAND);
        register(context, registryEntryLookup, WILD_GOBLINS, WildGoblinsFactionPool.WILD_GOBLIN);
    }

    private static void register(Registerable<Faction> context, RegistryEntryLookup<Faction> registryEntryLookup, RegistryKey<Faction> registryKey, Faction element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.factionEntries.add(registryKey.getValue().getPath());
        SpawnDataHandler spawnDataHandler = element.getSpawnData();
        if(spawnDataHandler != null && spawnDataHandler.getAllSpawnIdentifiers() != null)
            for(Identifier spawnId : spawnDataHandler.getAllSpawnIdentifiers())
                TranslationEntries.spawnEntries.add(spawnId.getPath());
    }
}
