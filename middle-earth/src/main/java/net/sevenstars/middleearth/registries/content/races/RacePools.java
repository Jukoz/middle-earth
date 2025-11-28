package net.sevenstars.middleearth.registries.content.races;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityAttributes;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.HashMap;
import java.util.List;

public class RacePools {
    public final static Race DWARF;
    public final static Race ELF;
    public final static Race HOBBIT;
    public final static Race HUMAN;
    public final static Race ORC;
    public final static Race URUK;

    static {
        DWARF = new Race(Identifier.of(MiddleEarth.MOD_ID, "dwarf"), RaceType.DWARF,
            new AttributePool()
                .addElement(AttributePoolElement.create(ModEntityAttributes.CLIMBING_STRENGTH, 80.0f))
                .addElement(AttributePoolElement.create(ModEntityAttributes.POWDERED_SNOW_IMMUNITY, 1.0f))
                .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.81).withBuffReversed())
                .addElement(AttributePoolElement.create(EntityAttributes.MAX_HEALTH, 22.0))
                .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0)
                    .withModifier(IdentifierUtil.create("dwarven_damage_modifier"), 10.0))
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
            List.of(), List.of());
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
            List.of(), List.of());

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

            List.of(), List.of());

        HUMAN = new Race(Identifier.of(MiddleEarth.MOD_ID, "human"), RaceType.HUMAN,
            new AttributePool(),
            new HashMap<>(){{
                put(EntityCategory.SHARED, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.ATTACK_DAMAGE, 1.0)));
                put(EntityCategory.FEMALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.95, 1.0)));
                put(EntityCategory.MALE, new AttributePool()
                    .addElement(AttributePoolElement.create(EntityAttributes.SCALE, 0.9, 0.98)));
            }},
            List.of(), List.of());
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
            List.of(), List.of());
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
            List.of(), List.of());
    }
}
