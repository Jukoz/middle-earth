package net.sevenstars.middleearth.enchantments;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.EntityTypeTags;
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
    public static final RegistryKey<Enchantment> STALWART = of("stalwart");
    public static final RegistryKey<Enchantment> STRIDE = of("stride");
    public static final RegistryKey<Enchantment> TREE_FELLER = of("tree_feller");
    public static final RegistryKey<Enchantment> VANTAGE = of("vantage");

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = MiddleEarth.of(path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Enchantment> registryEntryLookup2 = registry.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<Item> registryEntryLookup3 = registry.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<EntityType<?>> registryEntryLookup5 = registry.getRegistryLookup(RegistryKeys.ENTITY_TYPE);

        register(registry, AILMENT_PROTECTION, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 4,
                        Enchantment.leveledCost(18, 8),
                        Enchantment.leveledCost(10, 8), 2,
                        AttributeModifierSlot.ARMOR))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE_SET)));

        register(registry, BANE_OF_GIANTS, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 5, 5,
                        Enchantment.leveledCost(5, 8),
                        Enchantment.leveledCost(25, 8), 2,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE,
                        new AddEnchantmentEffect(EnchantmentLevelBasedValue.linear(2.5F)),
                        EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create().type(EntityTypePredicate
                                        .create(registryEntryLookup5, EntityTypeTags.SENSITIVE_TO_SMITE)))));

        register(registry, CELERITY, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 2, 1,
                        Enchantment.leveledCost(10, 20),
                        Enchantment.leveledCost(40, 20), 4,
                        AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(MiddleEarth.of("enchantment.celerity"),
                                EntityAttributes.ATTACK_SPEED, new EnchantmentLevelBasedValue.Linear(0.2f, 0.2f), EntityAttributeModifier.Operation.ADD_VALUE)));

        register(registry, FIRST_STRIKE, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.WEAPON_ENCHANTABLE), 3, 1,
                                Enchantment.leveledCost(15, 7),
                                Enchantment.leveledCost(30, 15), 3,
                                AttributeModifierSlot.MAINHAND)));

        register(registry, GROUNDED, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 4,
                                Enchantment.leveledCost(5, 8),
                                Enchantment.leveledCost(25, 8), 2,
                                AttributeModifierSlot.ARMOR))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(MiddleEarth.of("enchantment.grounded"),
                                EntityAttributes.KNOCKBACK_RESISTANCE, EnchantmentLevelBasedValue.linear(0.025F),
                                EntityAttributeModifier.Operation.ADD_VALUE)));

        register(registry, HEWING, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 5, 4,
                                Enchantment.leveledCost(18, 8),
                                Enchantment.leveledCost(10, 8), 2,
                                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE_SET)));

        register(registry, TREE_FELLER, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 5, 4,
                                Enchantment.leveledCost(18, 8),
                                Enchantment.leveledCost(10, 8), 2,
                                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE_SET)));

        register(registry, HIGH_STEP, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.FOOT_ARMOR), 1, 3,
                        Enchantment.leveledCost(30, 30),
                        Enchantment.leveledCost(85, 30), 9,
                        AttributeModifierSlot.CHEST)));

        register(registry, MINER_REACH, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 1, 1,
                                Enchantment.leveledCost(18, 15),
                                Enchantment.leveledCost(25, 15), 8,
                                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE_SET)));

        register(registry, STEALTHY_TRAIL, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE), 1, 3,
                        Enchantment.leveledCost(25, 25),
                        Enchantment.leveledCost(75, 25), 8,
                        AttributeModifierSlot.CHEST)));

        register(registry, STRIDE, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE), 1, 3,
                        Enchantment.leveledCost(15, 15),
                        Enchantment.leveledCost(45, 15), 7,
                        AttributeModifierSlot.LEGS)));

        register(registry, STALWART, Enchantment.builder(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 1,
                                Enchantment.leveledCost(10, 8),
                                Enchantment.leveledCost(25, 8), 3,
                                AttributeModifierSlot.ARMOR))
                .exclusiveSet(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(Identifier.ofVanilla("enchantment.stalwart"),
                                EntityAttributes.ARMOR, EnchantmentLevelBasedValue.linear(1F),
                                EntityAttributeModifier.Operation.ADD_VALUE)));

        register(registry, VANTAGE, Enchantment.builder(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 2, 1,
                        Enchantment.leveledCost(10, 20),
                        Enchantment.leveledCost(40, 20), 4,
                        AttributeModifierSlot.MAINHAND))
                .addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES,
                        new AttributeEnchantmentEffect(MiddleEarth.of("enchantment.vantage"),
                                EntityAttributes.ENTITY_INTERACTION_RANGE, new EnchantmentLevelBasedValue.Linear(0.5f, 0.5f), EntityAttributeModifier.Operation.ADD_VALUE)));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }


    public static void registerModEnchantmentEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering EnchantmentEffects for " + MiddleEarth.MOD_ID);
    }
}
