package net.sevenstars.middleearth.registries.content.races;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.races.Race;

public class RaceRegistry {
    private static final RegistryKey<Registry<Race>> RACE_KEY = DynamicRegistriesME.RACE;

    public final static RegistryKey<Race> DWARF = DynamicRegistriesME.of(RACE_KEY, RacePools.DWARF.getId());
    public final static RegistryKey<Race> ELF = DynamicRegistriesME.of(RACE_KEY, RacePools.ELF.getId());
    public final static RegistryKey<Race> HOBBIT = DynamicRegistriesME.of(RACE_KEY, RacePools.HOBBIT.getId());
    public final static RegistryKey<Race> HUMAN = DynamicRegistriesME.of(RACE_KEY, RacePools.HUMAN.getId());
    public final static RegistryKey<Race> ORC = DynamicRegistriesME.of(RACE_KEY, RacePools.ORC.getId());
    public final static RegistryKey<Race> URUK = DynamicRegistriesME.of(RACE_KEY, RacePools.URUK.getId());
    public final static RegistryKey<Race> GOBLIN = DynamicRegistriesME.of(RACE_KEY, RacePools.GOBLIN.getId());
    public final static RegistryKey<Race> SNAGA = DynamicRegistriesME.of(RACE_KEY, RacePools.SNAGA.getId());

    public static void bootstrap(Registerable<Race> context) {
        RegistryEntryLookup<Race> registryEntryLookup = context.getRegistryLookup(RACE_KEY);

        register(context, registryEntryLookup, DWARF, RacePools.DWARF);
        register(context, registryEntryLookup, ELF, RacePools.ELF);
        register(context, registryEntryLookup, HOBBIT, RacePools.HOBBIT);
        register(context, registryEntryLookup, HUMAN, RacePools.HUMAN);
        register(context, registryEntryLookup, ORC, RacePools.ORC);
        register(context, registryEntryLookup, URUK, RacePools.URUK);
        register(context, registryEntryLookup, GOBLIN, RacePools.GOBLIN);
        register(context, registryEntryLookup, SNAGA, RacePools.SNAGA);
    }

    private static void register(Registerable<Race> context, RegistryEntryLookup<Race> registryEntryLookup, RegistryKey<Race> registryKey, Race element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.raceEntries.add(registryKey.getValue().getPath());
    }
}
