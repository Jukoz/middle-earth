package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.pools.*;

import java.util.Optional;

/**
 * Middle-earth mod faction registry<br>
 * To fetch a faction during runtime, use : {@link net.sevenstars.middleearth.resources.datas.factions.FactionLookup#getFactionById}<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class FactionsME {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public final static RegistryKey<Faction> GONDOR = of("gondor");
    public final static RegistryKey<Faction> ROHAN = of("rohan");
    public final static RegistryKey<Faction> DALE = of("dale");
    public final static RegistryKey<Faction> LONGBEARDS = of("longbeards");
    public final static RegistryKey<Faction> LONGBEARDS_EREBOR = of("longbeards.erebor");
    public final static RegistryKey<Faction> LOTHLORIEN = of("lothlorien");
    public final static RegistryKey<Faction> MORDOR = of("mordor");
    public final static RegistryKey<Faction> MISTY_MOUNTAINS_GOBLINS = of("misty_mountains_goblins");
    public final static RegistryKey<Faction> ISENGARD = of("isengard");
    public final static RegistryKey<Faction> SHIRE = of("shire");
    public final static RegistryKey<Faction> BRIGAND = of("brigand");

    private static RegistryKey<Faction> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Factions for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, Faction.CODEC);
    }

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> factionRegistryEntryLookup = context.getRegistryLookup(KEY);
        register(context, factionRegistryEntryLookup, GONDOR, GondorFactionPool.GONDOR);
        register(context, factionRegistryEntryLookup, ROHAN, RohanFactionPool.ROHAN);
        register(context, factionRegistryEntryLookup, DALE, DaleFactionPool.DALE);
        register(context, factionRegistryEntryLookup, LONGBEARDS, LongbeardsFactionPool.LONGBEARDS);
        register(context, factionRegistryEntryLookup, LONGBEARDS_EREBOR, LongbeardsFactionPool.EREBOR);
        register(context, factionRegistryEntryLookup, LOTHLORIEN, LothlorienFactionPool.LOTHLORIEN);
        register(context, factionRegistryEntryLookup, MORDOR, MordorFactionPool.MORDOR);
        register(context, factionRegistryEntryLookup, MISTY_MOUNTAINS_GOBLINS, MistyMountainsGoblinsFactionPool.MISTY_MOUNTAINS_GOBLINS);
        register(context, factionRegistryEntryLookup, ISENGARD, IsengardFactionPool.ISENGARD);
        register(context, factionRegistryEntryLookup, SHIRE, ShireFactionPool.SHIRE);
        register(context, factionRegistryEntryLookup, BRIGAND, BrigandFactionPool.BRIGAND);
    }

    private static Faction register(Registerable<Faction> context, RegistryEntryLookup<Faction> factionRegistryEntryLookup, RegistryKey<Faction> key, Faction faction) {
        Optional<RegistryEntry.Reference<Faction>> optionalFaction = factionRegistryEntryLookup.getOptional(key);
        optionalFaction.ifPresent(biomeReference -> context.register(key, faction));

        TranslationEntries.factionEntries.add(faction.getName());
        if (faction.getSpawnData() != null){
            faction.getSpawnData().getSpawnList().forEach(spawnData -> {
                TranslationEntries.spawnEntries.add(spawnData.getIdentifier().getPath());
            });
        }

        return faction;
    }

    public static String getName(RegistryKey<Faction> factionRegistryKey) {
        return factionRegistryKey.getValue().getPath();
    }
}

