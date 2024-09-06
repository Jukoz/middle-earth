package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.data.AttributeData;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.*;

public class MiddleEarthRaces {
    public final static String PATH = "races";
    public static final RegistryKey<Registry<Race>> RACE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        DynamicRegistries.registerSynced(RACE_KEY, Race.CODEC);
    }

    // CRAB : TODO : This is only bare bone races, to be fleshed out in 1.6 -> I will take care of that :P
    public final static Race DWARF;
    public final static Race ELF;
    public final static Race HOBBIT;
    public final static Race HUMAN;
    public final static Race ORC;
    public final static Race URUK;

    public static void bootstrap(Registerable<Race> context) {
        RegistryEntryLookup<Race> raceRegistryEntryLookup = context.getRegistryLookup(RACE_KEY);
        // Registering all races
        register(context, raceRegistryEntryLookup, DWARF);
        register(context, raceRegistryEntryLookup, ELF);
        register(context, raceRegistryEntryLookup, HOBBIT);
        register(context, raceRegistryEntryLookup, HUMAN);
        register(context, raceRegistryEntryLookup, ORC);
        register(context, raceRegistryEntryLookup, URUK);
    }

    private static Race register(Registerable<Race> context, RegistryEntryLookup<Race> raceRegistryEntryLookup, Race race) {
        RegistryKey<Race> raceRegistryKey = of(race.getId().getPath());
        String name = raceRegistryKey.getValue().getPath();
        RegistryKey<Race> newRace = RegistryKey.of(RACE_KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Race>> optionalRace = raceRegistryEntryLookup.getOptional(raceRegistryKey);
        optionalRace.ifPresent(raceReference -> context.register(newRace, race));

        return race;
    }
    private static RegistryKey<Race> of(String name) {
        return RegistryKey.of(RACE_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }


    static {
        DWARF = new Race(Identifier.of(MiddleEarth.MOD_ID, "dwarf"), "dwarf",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.7);
                }}), List.of(), List.of());
        ELF = new Race(Identifier.of(MiddleEarth.MOD_ID, "elf"), "elf",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.02);
                }}), List.of(), List.of());
        HOBBIT = new Race(Identifier.of(MiddleEarth.MOD_ID, "hobbit"), "hobbit",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.5);
                }}), List.of(), List.of());
        HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), "human",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.0);
                }}), List.of(), List.of());
        ORC = new Race(Identifier.of(MiddleEarth.MOD_ID, "orc"), "orc",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.65);
                }}), List.of(), List.of());
        URUK = new Race(Identifier.of(MiddleEarth.MOD_ID, "uruk"), "uruk",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.98);
                }}), List.of(), List.of());
    }
}

