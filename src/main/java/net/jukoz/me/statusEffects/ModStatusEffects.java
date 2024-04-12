package net.jukoz.me.statusEffects;

import net.jukoz.me.MiddleEarth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> HALLUCINATION;

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(id), statusEffect);
    }

    static {
        HALLUCINATION = register("hallucination", new HallucinationStatusEffect(StatusEffectCategory.HARMFUL, 0x006666));
    }

}
