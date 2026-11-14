package net.sevenstars.middleearth.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> UNGOLIENI = of("ungolieni");
    public static TagKey<EntityType<?>> GAME = of("game");
    // Cattle, pigs, sheep and fowl out in the wild, hunted like game where nobody keeps them
    public static TagKey<EntityType<?>> WILD_LIVESTOCK = of("wild_livestock");
    // Attacked on sight by every npc, unless its faction says otherwise. Vanilla monsters are already, in code
    public static TagKey<EntityType<?>> NPC_ENEMIES = of("npc_enemies");
    // The beasts that serve the Shadow, which its armies count as their own
    public static TagKey<EntityType<?>> SHADOW_BEASTS = of("shadow_beasts");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
