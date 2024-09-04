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

public class Races {
    public final static String PATH = "races";
    public static final RegistryKey<Registry<Race>> RACE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        DynamicRegistries.registerSynced(RACE_KEY, Race.CODEC);
    }

    public final static Race HUMAN;
    public final static Race DWARF;

    public static void bootstrap(Registerable<Race> context) {
        RegistryEntryLookup<Race> raceRegistryEntryLookup = context.getRegistryLookup(RACE_KEY);
        // Registering all races
        register(context, raceRegistryEntryLookup, of("human"), HUMAN);
        register(context, raceRegistryEntryLookup, of("dwarf"), DWARF);
    }

    private static RegistryKey<Race> of(String name) {
        return RegistryKey.of(RACE_KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static Race register(Registerable<Race> context, RegistryEntryLookup<Race> raceRegistryEntryLookup,
                                 RegistryKey<Race> raceRegistryKey, Race race) {
        String name = raceRegistryKey.getValue().getPath();
        RegistryKey<Race> newRace = RegistryKey.of(RACE_KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Race>> optionalRace = raceRegistryEntryLookup.getOptional(raceRegistryKey);
        optionalRace.ifPresent(raceReference -> context.register(newRace, race));

        return race;
    }

    static {
        DWARF = new Race(Identifier.of(MiddleEarth.MOD_ID, "dwarf"), "dwarf",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.5);
                    put(EntityAttributes.GENERIC_ATTACK_SPEED, 50.0);
                }}), List.of(), List.of());

        HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), "human",
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.0);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 50.0);
                }}), List.of(), List.of());
    }
}

