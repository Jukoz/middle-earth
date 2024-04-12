package net.jukoz.me.statusEffects;

import net.jukoz.me.MiddleEarth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final StatusEffect HALLUCINATION = new Hallucination();

    public static void registerStatusEffects(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MiddleEarth.MOD_ID, "hallucination"), HALLUCINATION).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
