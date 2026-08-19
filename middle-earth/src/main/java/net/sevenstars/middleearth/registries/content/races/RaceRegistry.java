package net.sevenstars.middleearth.registries.content.races;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.races.Race;

public class RaceRegistry {
    private static final ResourceKey<Registry<Race>> RACE_KEY = DynamicRegistriesME.RACE;

    public final static ResourceKey<Race> DWARF = DynamicRegistriesME.of(RACE_KEY, RacePools.DWARF.getId());
    public final static ResourceKey<Race> ELF = DynamicRegistriesME.of(RACE_KEY, RacePools.ELF.getId());
    public final static ResourceKey<Race> HOBBIT = DynamicRegistriesME.of(RACE_KEY, RacePools.HOBBIT.getId());
    public final static ResourceKey<Race> HUMAN = DynamicRegistriesME.of(RACE_KEY, RacePools.HUMAN.getId());
    public final static ResourceKey<Race> ORC = DynamicRegistriesME.of(RACE_KEY, RacePools.ORC.getId());
    public final static ResourceKey<Race> URUK = DynamicRegistriesME.of(RACE_KEY, RacePools.URUK.getId());
    public final static ResourceKey<Race> GOBLIN = DynamicRegistriesME.of(RACE_KEY, RacePools.GOBLIN.getId());
    public final static ResourceKey<Race> SNAGA = DynamicRegistriesME.of(RACE_KEY, RacePools.SNAGA.getId());

    public static void bootstrap(BootstrapContext<Race> context) {
        HolderGetter<Race> registryEntryLookup = context.lookup(RACE_KEY);

        register(context, registryEntryLookup, DWARF, RacePools.DWARF);
        register(context, registryEntryLookup, ELF, RacePools.ELF);
        register(context, registryEntryLookup, HOBBIT, RacePools.HOBBIT);
        register(context, registryEntryLookup, HUMAN, RacePools.HUMAN);
        register(context, registryEntryLookup, ORC, RacePools.ORC);
        register(context, registryEntryLookup, URUK, RacePools.URUK);
        register(context, registryEntryLookup, GOBLIN, RacePools.GOBLIN);
        register(context, registryEntryLookup, SNAGA, RacePools.SNAGA);
    }

    private static void register(BootstrapContext<Race> context, HolderGetter<Race> registryEntryLookup, ResourceKey<Race> registryKey, Race element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.raceEntries.add(registryKey.location().getPath());
    }
}
