package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.datas.RaceType;
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
        DWARF = new Race(Identifier.of(MiddleEarth.MOD_ID, "dwarf"), RaceType.DWARF,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.74);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 22.0);
                    put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.1);
                    put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 2.5);
                    put(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.09);
                    put(EntityAttributes.PLAYER_MINING_EFFICIENCY, 0.15);
                }}), List.of(), List.of());
        ELF = new Race(Identifier.of(MiddleEarth.MOD_ID, "elf"), RaceType.ELF,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.06);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 24.0);
                    put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.9);
                    put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 2.75);
                    put(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.11);
                    put(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER, 0.225);
                }}), List.of(), List.of());
        HOBBIT = new Race(Identifier.of(MiddleEarth.MOD_ID, "hobbit"), RaceType.HOBBIT,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.57);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);
                    put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.8);
                    put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 2.5);
                    put(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.11);
                    put(EntityAttributes.PLAYER_SNEAKING_SPEED, 0.435);
                }}), List.of(), List.of());
        HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), RaceType.HUMAN,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.0); // Basic
                }}), List.of(), List.of());
        ORC = new Race(Identifier.of(MiddleEarth.MOD_ID, "orc"), RaceType.ORC,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 0.71);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 18.0);
                    put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.9);
                    put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 2.5);
                    put(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.11);
                    put(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0);
                }}), List.of(), List.of());
        URUK = new Race(Identifier.of(MiddleEarth.MOD_ID, "uruk"), RaceType.URUK,
                new AttributeData(new HashMap<>(){{
                    put(EntityAttributes.GENERIC_SCALE, 1.0);
                    put(EntityAttributes.GENERIC_MAX_HEALTH, 22.0);
                    put(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.9);
                    put(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 2.75);
                    put(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1);
                    put(EntityAttributes.GENERIC_BURNING_TIME, 0.70);
                }}), List.of(), List.of());
    }
}

