package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataPreset;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Middle-earth mod race registry<br>
 * To fetch a race during runtime, use : {@link net.sevenstars.middleearth.resources.datas.races.RaceLookup#getRace(World, Identifier)}<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class RacesME {
    public final static String PATH = "races";
    public static final RegistryKey<Registry<Race>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

    public static void register(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Races for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(KEY, Race.CODEC);
    }

    // TODO : for/from Crab : This is only bare bone races, to be fleshed out in 1.0b -> I will take care of that :P
    public final static Race DWARF;
    public final static Race ELF;
    public final static Race HOBBIT;
    public final static Race HUMAN;
    public final static Race ORC;
    public final static Race URUK;

    private static NpcTextureData commonNpcTextureData = new NpcTextureData(new HashMap<>(){{
        put(EntityCategory.MALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.BODY, List.of(
                                NpcTexturePatternsME.Body.MUSCULAR,
                                NpcTexturePatternsME.Body.FEMALE,
                                NpcTexturePatternsME.Body.SLIM
                        ))
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                NpcTexturePatternsME.Head.MALE,
                                NpcTexturePatternsME.Head.FEMALE,
                                NpcTexturePatternsME.Head.GOBLIN_SMALL_WISE,
                                NpcTexturePatternsME.Head.URUK_DUMB,
                                NpcTexturePatternsME.Head.URUK_TALL_DUMB
                        ))
                        .withPatterns(NpcTextureType.SCAR,
                                Stream.of(
                                        NpcTexturePatternsME.Scar.EYE_RIGHT,
                                        null)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EAR, List.of(
                                NpcTexturePatternsME.Ear.CUBE,
                                NpcTexturePatternsME.Ear.FLAT_POINTY,
                                NpcTexturePatternsME.Ear.ANGLED_POINTY,
                                NpcTexturePatternsME.Ear.FLAT_SMALL,
                                NpcTexturePatternsME.Ear.FLAT_ROUND
                                ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                    NpcTexturePatternsME.Nose.CUBE,
                                        NpcTexturePatternsME.Nose.LARGE_CUBE_CENTER,
                                        NpcTexturePatternsME.Nose.LARGE_CUBE_HIGH,
                                        NpcTexturePatternsME.Nose.VILLAGER)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                NpcTextureMaterialsME.Skin.PALE,
                                NpcTextureMaterialsME.Skin.OLIVE,
                                NpcTextureMaterialsME.Skin.DEFAULT,
                                NpcTextureMaterialsME.Skin.GREENISH,
                                NpcTextureMaterialsME.Skin.BROWN,
                                NpcTextureMaterialsME.Skin.TAN,
                                NpcTextureMaterialsME.Skin.TAN_DESATURATED
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                NpcTexturePatternsME.Eye.COMMON,
                                NpcTexturePatternsME.Eye.SMALL_WIDE,
                                NpcTexturePatternsME.Eye.SMALL_HIGH_WIDE,
                                NpcTexturePatternsME.Eye.SMALL
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                NpcTextureMaterialsME.Eye.BLACK,
                                NpcTextureMaterialsME.Eye.BLUE,
                                NpcTextureMaterialsME.Eye.BROWN,
                                NpcTextureMaterialsME.Eye.DARK_GREEN,
                                NpcTextureMaterialsME.Eye.BLIND_LEFT_GREEN,
                                NpcTextureMaterialsME.Eye.GREEN,
                                NpcTextureMaterialsME.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, Stream.of(
                                NpcTexturePatternsME.Hair.SHORT,
                                        NpcTexturePatternsME.Hair.UNCUT,
                                        NpcTexturePatternsME.Hair.BALD_SIDES,
                                        NpcTexturePatternsME.Hair.SHARP,
                                        NpcTexturePatternsME.Hair.BOWL,
                                        NpcTexturePatternsME.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                        NpcTexturePatternsME.Hair.BALD_SMALL_DREADLOCKS,
                                        NpcTexturePatternsME.Hair.DIRTY_MOP,
                                        NpcTexturePatternsME.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                        NpcTexturePatternsME.Hair.TOP_BALDING,
                                        NpcTexturePatternsME.Hair.SIDE_BALDING)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                NpcTexturePatternsME.Eyebrow.SHORT,
                                NpcTexturePatternsME.Eyebrow.BASIC,
                                NpcTexturePatternsME.Eyebrow.LONG,
                                NpcTexturePatternsME.Eyebrow.UNI
                                ))
                        .withPatterns(NpcTextureType.BEARD, Stream.of(
                                NpcTexturePatternsME.Beard.SHORT,
                                        NpcTexturePatternsME.Beard.LARGE,
                                        NpcTexturePatternsME.Beard.SINGLE,
                                        NpcTexturePatternsME.Beard.CLEAN,
                                        NpcTexturePatternsME.Beard.UNCLEAN_ORNAMENTED,
                                        NpcTexturePatternsME.Beard.LONG_SINGLE_ORNAMENTED,
                                        NpcTexturePatternsME.Beard.DUAL_ORNAMENTED,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                NpcTextureMaterialsME.Hair.BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.STRAW_BEADS,
                                NpcTextureMaterialsME.Hair.GINGER_BEADS,
                                NpcTextureMaterialsME.Hair.WHITE_BEADS,
                                NpcTextureMaterialsME.Hair.GRAY_BEADS,
                                NpcTextureMaterialsME.Hair.BLONDE_BEADS,
                                NpcTextureMaterialsME.Hair.BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.STRAW_GOLD,
                                NpcTextureMaterialsME.Hair.GINGER_GOLD,
                                NpcTextureMaterialsME.Hair.WHITE_GOLD,
                                NpcTextureMaterialsME.Hair.GRAY_GOLD,
                                NpcTextureMaterialsME.Hair.BLONDE_GOLD,
                                NpcTextureMaterialsME.Hair.BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.STRAW_COPPER,
                                NpcTextureMaterialsME.Hair.GINGER_COPPER,
                                NpcTextureMaterialsME.Hair.WHITE_COPPER,
                                NpcTextureMaterialsME.Hair.GRAY_COPPER,
                                NpcTextureMaterialsME.Hair.BLONDE_COPPER,
                                NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                                NpcTextureMaterialsME.Hair.WHITE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLONDE_ALMANDINE
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                NpcTexturePatternsME.Clothing.ROBE,
                                NpcTexturePatternsME.Clothing.FULL_TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT,
                                NpcTexturePatternsME.Clothing.TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                NpcTextureMaterialsME.Clothing.WHITE,
                                NpcTextureMaterialsME.Clothing.BROWN,
                                NpcTextureMaterialsME.Clothing.GRAY,
                                NpcTextureMaterialsME.Clothing.ROT_GREEN
                        ))
        ));
        put(EntityCategory.FEMALE, List.of(
                new NpcTextureDataPreset()
                        .withPatterns(NpcTextureType.BODY, List.of(
                                NpcTexturePatternsME.Body.MUSCULAR,
                                NpcTexturePatternsME.Body.FEMALE,
                                NpcTexturePatternsME.Body.SLIM
                        ))
                        .withPatterns(NpcTextureType.HEAD, List.of(
                                NpcTexturePatternsME.Head.MALE,
                                NpcTexturePatternsME.Head.FEMALE,
                                NpcTexturePatternsME.Head.GOBLIN_SMALL_WISE,
                                NpcTexturePatternsME.Head.URUK_DUMB,
                                NpcTexturePatternsME.Head.URUK_TALL_DUMB
                        ))
                        .withPatterns(NpcTextureType.SCAR,
                                Stream.of(
                                                NpcTexturePatternsME.Scar.EYE_RIGHT,
                                                null)
                                        .toList()
                        )
                        .withPatterns(NpcTextureType.EAR, List.of(
                                NpcTexturePatternsME.Ear.CUBE,
                                NpcTexturePatternsME.Ear.FLAT_POINTY,
                                NpcTexturePatternsME.Ear.ANGLED_POINTY,
                                NpcTexturePatternsME.Ear.FLAT_SMALL,
                                NpcTexturePatternsME.Ear.FLAT_ROUND
                        ))
                        .withPatterns(NpcTextureType.NOSE, Stream.of(
                                        NpcTexturePatternsME.Nose.CUBE,
                                        NpcTexturePatternsME.Nose.LARGE_CUBE_CENTER,
                                        NpcTexturePatternsME.Nose.LARGE_CUBE_HIGH,
                                        NpcTexturePatternsME.Nose.VILLAGER)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.SKIN, List.of(
                                NpcTextureMaterialsME.Skin.PALE,
                                NpcTextureMaterialsME.Skin.OLIVE,
                                NpcTextureMaterialsME.Skin.DEFAULT,
                                NpcTextureMaterialsME.Skin.GREENISH,
                                NpcTextureMaterialsME.Skin.BROWN,
                                NpcTextureMaterialsME.Skin.TAN,
                                NpcTextureMaterialsME.Skin.TAN_DESATURATED
                        ))
                        .withPatterns(NpcTextureType.EYE, List.of(
                                NpcTexturePatternsME.Eye.COMMON,
                                NpcTexturePatternsME.Eye.SMALL_WIDE,
                                NpcTexturePatternsME.Eye.SMALL_HIGH_WIDE,
                                NpcTexturePatternsME.Eye.SMALL
                        ))
                        .withMaterials(NpcTextureType.EYE, List.of(
                                NpcTextureMaterialsME.Eye.BLACK,
                                NpcTextureMaterialsME.Eye.BLUE,
                                NpcTextureMaterialsME.Eye.BROWN,
                                NpcTextureMaterialsME.Eye.DARK_GREEN,
                                NpcTextureMaterialsME.Eye.BLIND_LEFT_GREEN,
                                NpcTextureMaterialsME.Eye.GREEN,
                                NpcTextureMaterialsME.Eye.NAVY
                        ))
                        .withEmissiveEyes(false)
                        .withPatterns(NpcTextureType.HAIR, Stream.of(
                                        NpcTexturePatternsME.Hair.SHORT,
                                        NpcTexturePatternsME.Hair.UNCUT,
                                        NpcTexturePatternsME.Hair.BALD_SIDES,
                                        NpcTexturePatternsME.Hair.SHARP,
                                        NpcTexturePatternsME.Hair.BOWL,
                                        NpcTexturePatternsME.Hair.BALD_DREADLOCKS_ORNAMENTED,
                                        NpcTexturePatternsME.Hair.BALD_SMALL_DREADLOCKS,
                                        NpcTexturePatternsME.Hair.DIRTY_MOP,
                                        NpcTexturePatternsME.Hair.PONYTAIL_SHORT_ORNAMENTED,
                                        NpcTexturePatternsME.Hair.TOP_BALDING,
                                        NpcTexturePatternsME.Hair.SIDE_BALDING)
                                .toList()
                        )
                        .withPatterns(NpcTextureType.EYEBROW, List.of(
                                NpcTexturePatternsME.Eyebrow.SHORT,
                                NpcTexturePatternsME.Eyebrow.BASIC,
                                NpcTexturePatternsME.Eyebrow.LONG,
                                NpcTexturePatternsME.Eyebrow.UNI
                        ))
                        .withPatterns(NpcTextureType.BEARD, Stream.of(
                                        NpcTexturePatternsME.Beard.SHORT,
                                        NpcTexturePatternsME.Beard.LARGE,
                                        NpcTexturePatternsME.Beard.SINGLE,
                                        NpcTexturePatternsME.Beard.CLEAN,
                                        NpcTexturePatternsME.Beard.UNCLEAN_ORNAMENTED,
                                        NpcTexturePatternsME.Beard.LONG_SINGLE_ORNAMENTED,
                                        NpcTexturePatternsME.Beard.DUAL_ORNAMENTED,
                                        null)
                                .toList()
                        )
                        .withMaterials(NpcTextureType.HAIR, List.of(
                                NpcTextureMaterialsME.Hair.BLACK_BEADS,
                                NpcTextureMaterialsME.Hair.BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_BEADS,
                                NpcTextureMaterialsME.Hair.STRAW_BEADS,
                                NpcTextureMaterialsME.Hair.GINGER_BEADS,
                                NpcTextureMaterialsME.Hair.WHITE_BEADS,
                                NpcTextureMaterialsME.Hair.GRAY_BEADS,
                                NpcTextureMaterialsME.Hair.BLONDE_BEADS,
                                NpcTextureMaterialsME.Hair.BLACK_GOLD,
                                NpcTextureMaterialsME.Hair.BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_GOLD,
                                NpcTextureMaterialsME.Hair.STRAW_GOLD,
                                NpcTextureMaterialsME.Hair.GINGER_GOLD,
                                NpcTextureMaterialsME.Hair.WHITE_GOLD,
                                NpcTextureMaterialsME.Hair.GRAY_GOLD,
                                NpcTextureMaterialsME.Hair.BLONDE_GOLD,
                                NpcTextureMaterialsME.Hair.BLACK_COPPER,
                                NpcTextureMaterialsME.Hair.BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_COPPER,
                                NpcTextureMaterialsME.Hair.STRAW_COPPER,
                                NpcTextureMaterialsME.Hair.GINGER_COPPER,
                                NpcTextureMaterialsME.Hair.WHITE_COPPER,
                                NpcTextureMaterialsME.Hair.GRAY_COPPER,
                                NpcTextureMaterialsME.Hair.BLONDE_COPPER,
                                NpcTextureMaterialsME.Hair.BLACK_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.DARK_BROWN_ALMANDINE,
                                NpcTextureMaterialsME.Hair.STRAW_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GINGER_ALMANDINE,
                                NpcTextureMaterialsME.Hair.WHITE_ALMANDINE,
                                NpcTextureMaterialsME.Hair.GRAY_ALMANDINE,
                                NpcTextureMaterialsME.Hair.BLONDE_ALMANDINE
                        ))
                        .withPatterns(NpcTextureType.CLOTHING, List.of(
                                NpcTexturePatternsME.Clothing.ROBE,
                                NpcTexturePatternsME.Clothing.FULL_TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT,
                                NpcTexturePatternsME.Clothing.TOGA,
                                NpcTexturePatternsME.Clothing.SKIRT_WITH_STROPHIUM
                        ))
                        .withMaterials(NpcTextureType.CLOTHING, List.of(
                                NpcTextureMaterialsME.Clothing.WHITE,
                                NpcTextureMaterialsME.Clothing.BROWN,
                                NpcTextureMaterialsME.Clothing.GRAY,
                                NpcTextureMaterialsME.Clothing.ROT_GREEN
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
                List.of(), List.of(), commonNpcTextureData);
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
                List.of(), List.of(), commonNpcTextureData);

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

                List.of(), List.of(), commonNpcTextureData);

                HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), RaceType.HUMAN,
                        new AttributePool(),
                        new HashMap<>(){{
                            put(EntityCategory.SHARED, new AttributePool());
                            put(EntityCategory.FEMALE, new AttributePool()
                                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.95, 1.0)));
                            put(EntityCategory.MALE, new AttributePool()
                                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.9, 0.98)));
                        }},
                List.of(), List.of(), commonNpcTextureData);
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
                List.of(), List.of(), commonNpcTextureData);
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
                List.of(), List.of(), commonNpcTextureData);
    }
}

