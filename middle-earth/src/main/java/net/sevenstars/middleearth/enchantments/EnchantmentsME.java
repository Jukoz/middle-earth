package net.sevenstars.middleearth.enchantments;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.enchantment.effect.value.SetEnchantmentEffect;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * Middle-earth mod enchantment effects registry
 * <hr>
 */
public class EnchantmentsME {
    public static final RegistryKey<Enchantment> AILMENT_PROTECTION = of("ailment_protection");
    public static final RegistryKey<Enchantment> BANE_OF_GIANTS = of("bane_of_giants");
    public static final RegistryKey<Enchantment> BEHEADING = of("beheading");
    public static final RegistryKey<Enchantment> CELERITY = of("celerity");
    public static final RegistryKey<Enchantment> FIRST_STRIKE = of("first_strike");
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

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> registryEntryLookup3 = registry.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> registryEntryLookup2 = registry.getRegistryLookup(RegistryKeys.ENCHANTMENT);

        register(registry, AILMENT_PROTECTION, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 4,
                        Enchantment.leveledCost(18, 8),
                        Enchantment.leveledCost(10, 8), 2,
                        AttributeModifierSlot.ARMOR))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE_SET)));

        register(registry, HEWING, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE), 10, 3,
                                Enchantment.leveledCost(1, 15),
                                Enchantment.leveledCost(1, 10), 5,
                                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE_SET)));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }


    public static void registerModEnchantmentEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering EnchantmentEffects for " + MiddleEarth.MOD_ID);
    }
}
