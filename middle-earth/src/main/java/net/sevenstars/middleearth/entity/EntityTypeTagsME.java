package net.sevenstars.middleearth.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> UNGOLIENI = of("ungolieni");
    public static TagKey<EntityType<?>> GAME = of("game");
    // Cattle, pigs, sheep and poultry living wild. Npcs hunt them like game where nobody keeps them
    public static TagKey<EntityType<?>> WILD_LIVESTOCK = of("wild_livestock");
    // Every npc attacks these on sight unless its faction says otherwise. Vanilla monsters are already covered in code
    public static TagKey<EntityType<?>> NPC_ENEMIES = of("npc_enemies");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
