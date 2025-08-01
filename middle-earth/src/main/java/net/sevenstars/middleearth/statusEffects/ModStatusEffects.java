package net.sevenstars.middleearth.statusEffects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> ENSHROUDED = register("enshrouded", new EnshroudedStatusEffect(StatusEffectCategory.HARMFUL, 0x35F253));
    public static final RegistryEntry<StatusEffect> HALLUCINATION = register("hallucination", new HallucinationStatusEffect(StatusEffectCategory.HARMFUL, 0x006666));
    public static final RegistryEntry<StatusEffect> RESTRAINED = register("restrained", new RestrainedStatusEffect(StatusEffectCategory.HARMFUL, 0xC3D3D9)
            .addAttributeModifier(EntityAttributes.ATTACK_DAMAGE, Identifier.ofVanilla("effect.weakness"), -5.0, EntityAttributeModifier.Operation.ADD_VALUE));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MiddleEarth.MOD_ID, id), statusEffect);
    }

    public static void registerStatusEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Status Effects for " + MiddleEarth.MOD_ID);
    }
}
