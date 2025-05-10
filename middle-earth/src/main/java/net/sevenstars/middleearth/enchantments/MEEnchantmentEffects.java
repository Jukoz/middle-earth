package net.sevenstars.middleearth.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.enchantments.effects.MiningEnchantmentEffect;

public class MEEnchantmentEffects {
    public static final RegistryKey<Enchantment> MINING_ENCHANT = of("mining_enchant");
    public static MapCodec<MiningEnchantmentEffect> MINING_EFFECT = register("mining_effect", MiningEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering EnchantmentEffects for " + MiddleEarth.MOD_ID);
    }
}
