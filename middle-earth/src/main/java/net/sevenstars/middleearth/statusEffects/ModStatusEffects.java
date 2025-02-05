package net.sevenstars.middleearth.statusEffects;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> HALLUCINATION = register("hallucination", new HallucinationStatusEffect(StatusEffectCategory.HARMFUL, 0x006666));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MiddleEarth.MOD_ID, id), statusEffect);
    }

    public static void registerStatusEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Status Effects for " + MiddleEarth.MOD_ID);
    }
}
