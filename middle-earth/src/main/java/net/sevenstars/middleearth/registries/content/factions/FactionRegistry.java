package net.sevenstars.middleearth.registries.content.factions;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.factions.pools.*;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class FactionRegistry {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesME.FACTION;

    public final static RegistryKey<Faction> GONDOR = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("gondor"));
    public final static RegistryKey<Faction> ROHAN = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("rohan"));
    public final static RegistryKey<Faction> DALE = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("dale"));
    public final static RegistryKey<Faction> LONGBEARDS = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("longbeards"));
    public final static RegistryKey<Faction> LONGBEARDS_EREBOR = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("longbeards.erebor"));
    public final static RegistryKey<Faction> LOTHLORIEN = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("lothlorien"));
    public final static RegistryKey<Faction> MORDOR = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("mordor"));
    public final static RegistryKey<Faction> MISTY_MOUNTAINS_GOBLINS =  DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("misty_mountains_goblins"));
    public final static RegistryKey<Faction> ISENGARD = DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("isengard"));
    public final static RegistryKey<Faction> SHIRE =  DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("shire"));
    public final static RegistryKey<Faction> BRIGAND =  DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("brigand"));
    public final static RegistryKey<Faction> WILD_GOBLINS =  DynamicRegistriesME.of(FACTION_KEY, IdentifierUtil.create("wild_goblins"));

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> registryEntryLookup = context.getRegistryLookup(FACTION_KEY);

        register(context, registryEntryLookup, GONDOR, GondorFactionPool.GONDOR);
        register(context, registryEntryLookup, ROHAN, RohanFactionPool.ROHAN);
        register(context, registryEntryLookup, DALE, DaleFactionPool.DALE);
        register(context, registryEntryLookup, LONGBEARDS, LongbeardsFactionPool.LONGBEARDS);
        register(context, registryEntryLookup, LONGBEARDS_EREBOR, LongbeardsFactionPool.EREBOR);
        register(context, registryEntryLookup, LOTHLORIEN, LothlorienFactionPool.LOTHLORIEN);
        register(context, registryEntryLookup, MORDOR, MordorFactionPool.MORDOR);
        register(context, registryEntryLookup, MISTY_MOUNTAINS_GOBLINS, MistyMountainsGoblinsFactionPool.MISTY_MOUNTAINS_GOBLINS);
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
