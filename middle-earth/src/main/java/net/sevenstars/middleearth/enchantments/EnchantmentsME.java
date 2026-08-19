package net.sevenstars.middleearth.enchantments;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ItemTagsME;

/**
 * Middle-earth mod enchantment effects registry
 * <hr>
 */
public class EnchantmentsME {
    public static final ResourceKey<Enchantment> AILMENT_PROTECTION = of("ailment_protection");
    public static final ResourceKey<Enchantment> AULE_BLESSING = of("aule_blessing");
    public static final ResourceKey<Enchantment> BANE_OF_GIANTS = of("bane_of_giants");
    public static final ResourceKey<Enchantment> BEHEADING = of("beheading");
    public static final ResourceKey<Enchantment> CELERITY = of("celerity");
    public static final ResourceKey<Enchantment> FIRST_STRIKE = of("first_strike");
    public static final ResourceKey<Enchantment> GROUNDED = of("grounded");
    public static final ResourceKey<Enchantment> HEWING = of("hewing");
    public static final ResourceKey<Enchantment> HIGH_STEP = of("high_step");
    public static final ResourceKey<Enchantment> MINER_REACH = of("miner_reach");
    public static final ResourceKey<Enchantment> STEALTHY_TRAIL = of("stealthy_trail");
    public static final ResourceKey<Enchantment> STALWART = of("stalwart");
    public static final ResourceKey<Enchantment> STRIDE = of("stride");
    public static final ResourceKey<Enchantment> TREE_FELLER = of("tree_feller");
    public static final ResourceKey<Enchantment> VANTAGE = of("vantage");
    private static final TagKey<Enchantment> SMITHING_HAMMER_EXCLUSIVE = TagKey.create(
            Registries.ENCHANTMENT,
            MiddleEarth.of("exclusive_set/smithing_hammer")
    );

    private static ResourceKey<Enchantment> of(String path) {
        ResourceLocation id = MiddleEarth.of(path);
        return ResourceKey.create(Registries.ENCHANTMENT, id);
    }

    public static void bootstrap(BootstrapContext<Enchantment> registry) {
        HolderGetter<Enchantment> registryEntryLookup2 = registry.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> registryEntryLookup3 = registry.lookup(Registries.ITEM);
        register(registry, AILMENT_PROTECTION, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 4,
                        Enchantment.dynamicCost(18, 8),
                        Enchantment.dynamicCost(10, 8), 2,
                        EquipmentSlotGroup.ARMOR))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE)));

        register(registry, AULE_BLESSING, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTagsME.SMITHING_HAMMER_ENCHANTABLE), 10, 3,
                        Enchantment.dynamicCost(1, 5),
                        Enchantment.dynamicCost(1, 12), 3,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(registryEntryLookup2.getOrThrow(SMITHING_HAMMER_EXCLUSIVE)));

        register(registry, BANE_OF_GIANTS, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 5, 5,
                        Enchantment.dynamicCost(5, 8),
                        Enchantment.dynamicCost(25, 8), 2,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(2.5F)),
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().entityType(EntityTypePredicate
                                        .of(EntityTypeTags.SENSITIVE_TO_SMITE)))));

        register(registry, CELERITY, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTagsME.CELERITY_ENCHANTABLE), 2, 1,
                        Enchantment.dynamicCost(10, 20),
                        Enchantment.dynamicCost(40, 20), 4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(MiddleEarth.of("enchantment.celerity"),
                                Attributes.ATTACK_SPEED, new LevelBasedValue.Linear(0.2f, 0.2f), AttributeModifier.Operation.ADD_VALUE)));

        register(registry, FIRST_STRIKE, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.WEAPON_ENCHANTABLE), 3, 1,
                                Enchantment.dynamicCost(15, 7),
                                Enchantment.dynamicCost(30, 15), 3,
                                EquipmentSlotGroup.MAINHAND)));

        register(registry, GROUNDED, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 4,
                                Enchantment.dynamicCost(5, 8),
                                Enchantment.dynamicCost(25, 8), 2,
                                EquipmentSlotGroup.ARMOR))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(MiddleEarth.of("enchantment.grounded"),
                                Attributes.KNOCKBACK_RESISTANCE, LevelBasedValue.perLevel(0.025F),
                                AttributeModifier.Operation.ADD_VALUE)));

        register(registry, HEWING, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 5, 4,
                                Enchantment.dynamicCost(18, 8),
                                Enchantment.dynamicCost(10, 8), 2,
                                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)));

        register(registry, TREE_FELLER, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 5, 4,
                                Enchantment.dynamicCost(18, 8),
                                Enchantment.dynamicCost(10, 8), 2,
                                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)));

        register(registry, HIGH_STEP, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.FOOT_ARMOR), 1, 3,
                        Enchantment.dynamicCost(30, 30),
                        Enchantment.dynamicCost(85, 30), 9,
                        EquipmentSlotGroup.CHEST)));

        register(registry, MINER_REACH, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.MINING_ENCHANTABLE), 1, 1,
                                Enchantment.dynamicCost(18, 15),
                                Enchantment.dynamicCost(25, 15), 8,
                                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.MINING_EXCLUSIVE)));

        register(registry, STEALTHY_TRAIL, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE), 1, 3,
                        Enchantment.dynamicCost(25, 25),
                        Enchantment.dynamicCost(75, 25), 8,
                        EquipmentSlotGroup.CHEST)));

        register(registry, STRIDE, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE), 1, 3,
                        Enchantment.dynamicCost(15, 15),
                        Enchantment.dynamicCost(45, 15), 7,
                        EquipmentSlotGroup.LEGS)));

        register(registry, STALWART, Enchantment.enchantment(
                        Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                registryEntryLookup3.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 5, 1,
                                Enchantment.dynamicCost(10, 8),
                                Enchantment.dynamicCost(25, 8), 3,
                                EquipmentSlotGroup.ARMOR))
                .exclusiveWith(registryEntryLookup2.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(ResourceLocation.withDefaultNamespace("enchantment.stalwart"),
                                Attributes.ARMOR, LevelBasedValue.perLevel(1F),
                                AttributeModifier.Operation.ADD_VALUE)));

        register(registry, VANTAGE, Enchantment.enchantment(
                Enchantment.definition(registryEntryLookup3.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 2, 1,
                        Enchantment.dynamicCost(10, 20),
                        Enchantment.dynamicCost(40, 20), 4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(MiddleEarth.of("enchantment.vantage"),
                                Attributes.ENTITY_INTERACTION_RANGE, new LevelBasedValue.Linear(0.5f, 0.5f), AttributeModifier.Operation.ADD_VALUE)));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }


    public static void registerModEnchantmentEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering EnchantmentEffects for " + MiddleEarth.MOD_ID);
    }
}
