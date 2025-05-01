package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.races.data.RaceTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MiddleEarthRaces {
    public final static String PATH = "races";
    public static final RegistryKey<Registry<Race>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Races for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, Race.CODEC);
    }

    // TODO : for/from Crab : This is only bare bone races, to be fleshed out in 1.6 -> I will take care of that :P
    public final static Race DWARF;
    public final static Race ELF;
    public final static Race HOBBIT;
    public final static Race HUMAN;
    public final static Race ORC;
    public final static Race URUK;

    private static RaceTextureData commonRaceTextureData = new RaceTextureData(new HashMap<>(){{
        put(EntityCategory.MALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.SKIN, List.of(
                                MiddleEarthNpcTexturePatterns.Skin.MUSCULAR,
                                MiddleEarthNpcTexturePatterns.Skin.SLIM
                        ))
                        .withPatterns(NpcTextureType.EAR, List.of(
                                MiddleEarthNpcTexturePatterns.Ear.CUBE,
                                MiddleEarthNpcTexturePatterns.Ear.POINTY
                        ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                    MiddleEarthNpcTexturePatterns.Nose.CUBE,
                                        MiddleEarthNpcTexturePatterns.Nose.LARGE_CUBE,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                MiddleEarthNpcTextureMaterials.Skin.PALE,
                                MiddleEarthNpcTextureMaterials.Skin.TAN,
                                MiddleEarthNpcTextureMaterials.Skin.OLIVE,
                                MiddleEarthNpcTextureMaterials.Skin.NEUTRAL,
                                MiddleEarthNpcTextureMaterials.Skin.GREENISH,
                                MiddleEarthNpcTextureMaterials.Skin.TAN_DESATURATED,
                                MiddleEarthNpcTextureMaterials.Skin.BROWN
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                MiddleEarthNpcTexturePatterns.Eye.COMMON
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                MiddleEarthNpcTextureMaterials.Eye.BLACK,
                                MiddleEarthNpcTextureMaterials.Eye.BLUE,
                                MiddleEarthNpcTextureMaterials.Eye.BROWN,
                                MiddleEarthNpcTextureMaterials.Eye.DARK_GREEN,
                                MiddleEarthNpcTextureMaterials.Eye.GREEN,
                                MiddleEarthNpcTextureMaterials.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, Stream.of(
                                MiddleEarthNpcTexturePatterns.Hair.SHORT,
                                        MiddleEarthNpcTexturePatterns.Hair.UNCUT,
                                        MiddleEarthNpcTexturePatterns.Hair.BALD_SIDES,
                                        MiddleEarthNpcTexturePatterns.Hair.SHARP,
                                        MiddleEarthNpcTexturePatterns.Hair.BOWL,
                                        null)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                MiddleEarthNpcTexturePatterns.Eyebrow.SHORT,
                                MiddleEarthNpcTexturePatterns.Eyebrow.BASIC,
                                MiddleEarthNpcTexturePatterns.Eyebrow.LONG
                        ))
                        .withPatterns(NpcTextureType.BEARD, Stream.of(
                                MiddleEarthNpcTexturePatterns.Beard.SHORT,
                                        MiddleEarthNpcTexturePatterns.Beard.LARGE,
                                        MiddleEarthNpcTexturePatterns.Beard.SINGLE,
                                        MiddleEarthNpcTexturePatterns.Beard.CLEAN,
                                        MiddleEarthNpcTexturePatterns.Beard.UNCLEAN_ORNAMENTED,
                                        MiddleEarthNpcTexturePatterns.Beard.LONG_SINGLE_ORNAMENTED,
                                        MiddleEarthNpcTexturePatterns.Beard.DUAL_ORNAMENTED,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                MiddleEarthNpcTextureMaterials.Hair.BLACK_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.BROWN_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.DARK_BROWN_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.STRAW_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.ORANGE_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.WHITE_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.GRAY_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.BLONDE_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.BLACK_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.BROWN_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.DARK_BROWN_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.STRAW_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.ORANGE_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.WHITE_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.GRAY_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.BLONDE_GOLD,
                                MiddleEarthNpcTextureMaterials.Hair.BLACK_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.BROWN_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.DARK_BROWN_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.STRAW_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.ORANGE_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.WHITE_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.GRAY_COPPER,
                                MiddleEarthNpcTextureMaterials.Hair.BLONDE_COPPER
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                MiddleEarthNpcTexturePatterns.Clothing.FABRIC_SKIRT
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                MiddleEarthNpcTextureMaterials.Clothing.WHITE,
                                MiddleEarthNpcTextureMaterials.Clothing.BROWN
                        ))
        ));
        put(EntityCategory.FEMALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.SKIN, List.of(
                                MiddleEarthNpcTexturePatterns.Skin.FEMALE,
                                MiddleEarthNpcTexturePatterns.Skin.SLIM
                        ))
                        .withPatterns(NpcTextureType.EAR, List.of(
                                MiddleEarthNpcTexturePatterns.Ear.CUBE,
                                MiddleEarthNpcTexturePatterns.Ear.POINTY
                        ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                MiddleEarthNpcTexturePatterns.Nose.CUBE,
                                        MiddleEarthNpcTexturePatterns.Nose.LARGE_CUBE,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                MiddleEarthNpcTextureMaterials.Skin.PALE,
                                MiddleEarthNpcTextureMaterials.Skin.TAN,
                                MiddleEarthNpcTextureMaterials.Skin.OLIVE,
                                MiddleEarthNpcTextureMaterials.Skin.NEUTRAL
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                MiddleEarthNpcTexturePatterns.Eye.COMMON
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                MiddleEarthNpcTextureMaterials.Eye.BLACK,
                                MiddleEarthNpcTextureMaterials.Eye.BLUE,
                                MiddleEarthNpcTextureMaterials.Eye.BROWN,
                                MiddleEarthNpcTextureMaterials.Eye.DARK_GREEN,
                                MiddleEarthNpcTextureMaterials.Eye.GREEN,
                                MiddleEarthNpcTextureMaterials.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, List.of(
                                MiddleEarthNpcTexturePatterns.Hair.LONG,
                                MiddleEarthNpcTexturePatterns.Hair.FLAT_LONG
                        ))
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                MiddleEarthNpcTexturePatterns.Eyebrow.SHORT
                        ))
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                MiddleEarthNpcTextureMaterials.Hair.BLACK_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.BROWN_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.DARK_BROWN_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.STRAW_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.ORANGE_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.WHITE_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.GRAY_BEADS,
                                MiddleEarthNpcTextureMaterials.Hair.BLONDE_BEADS
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                MiddleEarthNpcTexturePatterns.Clothing.FABRIC_SKIRT_WITH_STROPHIUM
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                MiddleEarthNpcTextureMaterials.Clothing.WHITE,
                                MiddleEarthNpcTextureMaterials.Clothing.BROWN
                        ))
        ));
    }});

    public static void bootstrap(Registerable<Race> context) {
        RegistryEntryLookup<Race> raceRegistryEntryLookup = context.getRegistryLookup(KEY);
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
        RegistryKey<Race> newRace = RegistryKey.of(KEY,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<Race>> optionalRace = raceRegistryEntryLookup.getOptional(raceRegistryKey);
        optionalRace.ifPresent(raceReference -> context.register(newRace, race));

        return race;
    }
    private static RegistryKey<Race> of(String name) {
        return RegistryKey.of(KEY, Identifier.of(MiddleEarth.MOD_ID, name));
    }


    static {
        DWARF = new Race(Identifier.of(MiddleEarth.MOD_ID, "dwarf"), RaceType.DWARF,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.81).withBuffReversed())
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.09))
                        .addElement(AttributePoolElement.create(EntityAttributes.MINING_EFFICIENCY, 0.15)),
                new HashMap<>(){{
                    put(EntityCategory.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.81))
                            .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.09))
                            .addElement(AttributePoolElement.create(EntityAttributes.MINING_EFFICIENCY, 0.15)));
                    put(EntityCategory.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.75, 0.81)));
                    put(EntityCategory.FEMALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.71, 0.78)));
                }},
                List.of(), List.of(), commonRaceTextureData);
        ELF = new Race(Identifier.of(MiddleEarth.MOD_ID, "elf"), RaceType.ELF,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.06))
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 20.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.25))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.1))
                        .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.75).withBuffReversed()),
                new HashMap<>(){{
                    put(EntityCategory.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 0.9))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.115))
                            .addElement(AttributePoolElement.create(EntityAttributes.SNEAKING_SPEED, 0.435))
                            .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.75)));
                    put(EntityCategory.MALE, new AttributePool()
                                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.02, 1.06)));
                    put(EntityCategory.FEMALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.00, 1.03)));
                }},
                List.of(), List.of(), commonRaceTextureData);

        HOBBIT = new Race(Identifier.of(MiddleEarth.MOD_ID, "hobbit"), RaceType.HOBBIT,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 0.9))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.115))
                        .addElement(AttributePoolElement.create(EntityAttributes.SNEAKING_SPEED, 0.435))
                        .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.90).withBuffReversed())
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.6).withBuffReversed()),
                new HashMap<>(){{
                    put(EntityCategory.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 0.9))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED,  0.115))
                            .addElement(AttributePoolElement.create(EntityAttributes.SNEAKING_SPEED, 0.435))
                            .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.90)));
                    put(EntityCategory.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.58, 0.62)));
                    put(EntityCategory.FEMALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.55, 0.58)));
                }},

                List.of(), List.of(), commonRaceTextureData);

                HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), RaceType.HUMAN,
                        new AttributePool(),
                        new HashMap<>(){{
                            put(EntityCategory.SHARED, new AttributePool());
                            put(EntityCategory.FEMALE, new AttributePool()
                                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.95, 1.0)));
                            put(EntityCategory.MALE, new AttributePool()
                                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.9, 0.98)));
                        }},
                List.of(), List.of(), commonRaceTextureData);
        ORC = new Race(Identifier.of(MiddleEarth.MOD_ID, "orc"), RaceType.ORC,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.83).withBuffReversed())
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 16.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.11))
                        .addElement(AttributePoolElement.create(EntityAttributes.STEP_HEIGHT, 1.0)),
                new HashMap<>(){{
                    put(EntityCategory.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 16.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.11))
                            .addElement(AttributePoolElement.create(EntityAttributes.STEP_HEIGHT, 1.0)));
                    put(EntityCategory.FEMALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.74, 0.79)));
                    put(EntityCategory.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.78, 0.83)));
                }},
                List.of(), List.of(), commonRaceTextureData);
        URUK = new Race(Identifier.of(MiddleEarth.MOD_ID, "uruk"), RaceType.URUK,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.09))
                        .addElement(AttributePoolElement.create(EntityAttributes.BURNING_TIME, 0.70).withBuffReversed()),
                new HashMap<>(){{
                    put(EntityCategory.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.0))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.09))
                            .addElement(AttributePoolElement.create(EntityAttributes.STEP_HEIGHT, 0.70)));
                    put(EntityCategory.FEMALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.92, 0.98)));
                    put(EntityCategory.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.96, 1.0)));
                }},
                List.of(), List.of(), commonRaceTextureData);
    }
}

