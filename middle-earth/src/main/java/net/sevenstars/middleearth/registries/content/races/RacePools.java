package net.sevenstars.middleearth.registries.content.races;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.HashMap;
import java.util.List;

public class RacePools {
    public static class RaceAttributes {
        public static final Identifier TOTAL_DAMAGE_MODIFIER = MiddleEarth.of("total_damage");
    }

    public final static Race DWARF;
    public final static Race ELF;
    public final static Race HOBBIT;
    public final static Race HUMAN;
    public final static Race ORC;
    public final static Race URUK;
    public final static Race GOBLIN;
    public final static Race SNAGA;

    static {
        DWARF = new Race(MiddleEarth.of("dwarf"), RaceType.DWARF,
            new AttributePool()
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.81))
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.09))
                .addElement(AttributePoolElement.create(EntityAttributes.MINING_EFFICIENCY, 0.2)),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.81))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75))
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.27)));
                put(EntityCategories.MALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.75, 0.81))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 1.05, 1.1)));
                put(EntityCategories.FEMALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.73, 0.79))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 1, 1.03)));
            }},
            List.of(), List.of());

        ELF = new Race(MiddleEarth.of("elf"), RaceType.ELF,
            new AttributePool()
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.06))
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 20.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.25))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.1))
                .addElement(AttributePoolElement.create(EntityAttributesME.POWDERED_SNOW_IMMUNITY, 1.0f))
                .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.75))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_EFFICIENCY, 0.3)),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                    .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0).withModifier(RaceAttributes.TOTAL_DAMAGE_MODIFIER, -0.1))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75))
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.3)));
                put(EntityCategories.MALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.02, 1.06))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.93, 0.97)));
                put(EntityCategories.FEMALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.00, 1.03))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.91, 0.94)));
            }},
            List.of(), List.of());

        HOBBIT = new Race(MiddleEarth.of("hobbit"), RaceType.HOBBIT,
            new AttributePool()
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                    .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0).withModifier(RaceAttributes.TOTAL_DAMAGE_MODIFIER, -0.2))
                .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.1))
                .addElement(AttributePoolElement.create(EntityAttributes.SNEAKING_SPEED, 0.435))
                .addElement(AttributePoolElement.create(EntityAttributes.FALL_DAMAGE_MULTIPLIER, 0.90))
                .addElement(AttributePoolElement.create(EntityAttributesME.DETECTION_RANGE, 0.8))
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.66)),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0).withModifier(RaceAttributes.TOTAL_DAMAGE_MODIFIER, -0.1))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75))
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.3)));
                put(EntityCategories.MALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.63, 0.68))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.95, 1.02)));
                put(EntityCategories.FEMALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.62, 0.66))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.94, 1)));
            }},
            List.of(), List.of());

        HUMAN = new Race(MiddleEarth.of("human"), RaceType.HUMAN,
            new AttributePool(),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.3))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75)));
                put(EntityCategories.FEMALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.95, 1.0))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.97, 1.01)));
                put(EntityCategories.MALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.97, 1.01))
                    .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.96, 1)));
            }},
            List.of(), List.of());

        ORC = new Race(MiddleEarth.of("orc"), RaceType.ORC,
            new AttributePool()
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.79))
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 16.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.75))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.1))
                .addElement(AttributePoolElement.create(EntityAttributes.SNEAKING_SPEED, 0.435)),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75))
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.3)));
                put(EntityCategories.MALE, new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.75, 0.8))
                        .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.95, 1.02)));
            }},
            List.of(), List.of());

        URUK = new Race(MiddleEarth.of("uruk"), RaceType.URUK,
            new AttributePool()
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.0))
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 18.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 3.0))
                .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.105))
                .addElement(AttributePoolElement.create(EntityAttributes.BURNING_TIME, 0.70)),
            new HashMap<>(){{
                put(EntityCategories.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 1.0))
                    .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                    .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.75))
                    .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.27))
                    .addElement(AttributePoolElement.create(EntityAttributes.STEP_HEIGHT, 0.70)));
                put(EntityCategories.MALE, new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.95, 1.01)));
            }},
            List.of(), List.of());


        GOBLIN = new Race(MiddleEarth.of("goblin"), RaceType.GOBLIN,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.75))
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 14.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0).withModifier(RaceAttributes.TOTAL_DAMAGE_MODIFIER, -0.1))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.105))
                        .addElement(AttributePoolElement.create(EntityAttributes.MINING_EFFICIENCY, 0.2))
                        .addElement(AttributePoolElement.create(EntityAttributesME.CLIMBING_STRENGTH, 100)),
                new HashMap<>(){{
                    put(EntityCategories.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 0.9))
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.5))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.315))
                            .addElement(AttributePoolElement.create(EntityAttributesME.CLIMBING_STRENGTH, 80)));
                    put(EntityCategories.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.72, 0.76))
                            .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.96, 1.02)));
                }},
                List.of(), List.of());

        SNAGA = new Race(MiddleEarth.of("snaga"), RaceType.SNAGA,
                new AttributePool()
                        .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.71))
                        .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 12.0))
                        .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0).withModifier(RaceAttributes.TOTAL_DAMAGE_MODIFIER, -0.2))
                        .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 2.5))
                        .addElement(AttributePoolElement.create(EntityAttributes.BLOCK_INTERACTION_RANGE, 5.5))
                        .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.105)),
                new HashMap<>(){{
                    put(EntityCategories.SHARED, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.ENTITY_INTERACTION_RANGE, 1.5))
                            .addElement(AttributePoolElement.create(EntityAttributes.MOVEMENT_SPEED, 0.315)));
                    put(EntityCategories.MALE, new AttributePool()
                            .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.68, 0.72))
                            .addElement(AttributePoolElement.create(EntityAttributesME.WIDTH_SCALE, 0.96, 1.01)));
                }},
                List.of(), List.of());
    }
}
