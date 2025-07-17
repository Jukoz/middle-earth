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

/**
 * Middle-earth mod enchantment effects registry
 * <hr>
 */
public class EnchantmentEffectsME {
    public static final RegistryKey<Enchantment> BANE_OF_GIANTS = of("bane_of_giants");
    public static final RegistryKey<Enchantment> BEHEADING = of("beheading");
    public static final RegistryKey<Enchantment> GROUNDED = of("grounded");
    public static final RegistryKey<Enchantment> HEWING = of("hewing");
    public static final RegistryKey<Enchantment> HIGH_STEP = of("high_step");
    public static final RegistryKey<Enchantment> MINER_REACH = of("miner_reach");
    public static final RegistryKey<Enchantment> STEALTHY_TRAIL = of("stealthy_trail");
    public static final RegistryKey<Enchantment> STRIDE = of("stride");
    public static final RegistryKey<Enchantment> TREE_FELLER = of("tree_feller");
    public static final RegistryKey<Enchantment> VANTAGE = of("vantage");

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
