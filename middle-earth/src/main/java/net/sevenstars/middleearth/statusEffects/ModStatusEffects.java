package net.sevenstars.middleearth.statusEffects;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;

public class ModStatusEffects {
    public static final Holder<MobEffect> ENSHROUDED = register("enshrouded", new EnshroudedStatusEffect(MobEffectCategory.HARMFUL, 0x35F253));
    public static final Holder<MobEffect> RESTRAINED = register("restrained", new RestrainedStatusEffect(MobEffectCategory.HARMFUL, 0xC3D3D9)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.withDefaultNamespace("effect.weakness"), -5.0, AttributeModifier.Operation.ADD_VALUE));

    private static Holder<MobEffect> register(String id, MobEffect statusEffect) {
        return RegistrationBridge.registerForHolder(
                BuiltInRegistries.MOB_EFFECT,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id),
                statusEffect
        );
    }

    public static void registerStatusEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Status Effects for " + MiddleEarth.MOD_ID);
    }
}
