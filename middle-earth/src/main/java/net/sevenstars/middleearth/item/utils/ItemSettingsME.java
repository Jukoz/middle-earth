package net.sevenstars.middleearth.item.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.BlockTagsME;
import net.sevenstars.middleearth.utils.EntityTypeTagsME;

import java.util.List;

public interface ItemSettingsME {

    /**
     * Middle-earth mod custom Settings for weapons
     */

    Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    static Item.Settings createWeaponSettings(ToolMaterial material, Item.Settings settings, WeaponTypesME type){
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);

        return settings.maxDamage(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .attributeModifiers(createWeaponAttibutes(material, type.attack, type.attackSpeed, type.attackRange))
                .component(DataComponentTypes.TOOL, new ToolComponent(List.of(
                        ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(BlockTagsME.COBWEBS), 15.0F),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                        ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false))
                .component(DataComponentTypes.WEAPON, new WeaponComponent(1));
    }

    static AttributeModifiersComponent createWeaponAttibutes(ToolMaterial material, float attackDamage, float attackSpeed, float attackRange){
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(attackDamage + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID, (double)attackRange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    static Item.Settings goatArmor(ArmorMaterial material) {
        RegistryEntryLookup<EntityType<?>> registryEntryLookup = Registries.createEntryLookup(Registries.ENTITY_TYPE);
        return new Item.Settings().attributeModifiers(material.createAttributeModifiers(EquipmentType.BODY))
                .component(
                        DataComponentTypes.EQUIPPABLE,
                        EquippableComponent.builder(EquipmentSlot.BODY)
                                .equipSound(SoundEvents.ENTITY_HORSE_ARMOR)
                                .model(material.assetId())
                                .allowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_GOAT_ARMOR))
                                .damageOnHurt(false)
                                .canBeSheared(true)
                                .shearingSound(SoundEvents.ITEM_HORSE_ARMOR_UNEQUIP)
                                .build()
                )
                .maxCount(1);
    }

    static Item.Settings wargArmor(ArmorMaterial material) {
        RegistryEntryLookup<EntityType<?>> registryEntryLookup = Registries.createEntryLookup(Registries.ENTITY_TYPE);
        return new Item.Settings().attributeModifiers(material.createAttributeModifiers(EquipmentType.BODY))
                .component(
                        DataComponentTypes.EQUIPPABLE,
                        EquippableComponent.builder(EquipmentSlot.BODY)
                                .equipSound(SoundEvents.ENTITY_HORSE_ARMOR)
                                .model(material.assetId())
                                .allowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_WARG_ARMOR))
                                .damageOnHurt(false)
                                .canBeSheared(true)
                                .shearingSound(SoundEvents.ITEM_HORSE_ARMOR_UNEQUIP)
                                .build()
                )
                .maxCount(1);
    }

    static Item.Settings greatHornArmor(ArmorMaterial material) {
        RegistryEntryLookup<EntityType<?>> registryEntryLookup = Registries.createEntryLookup(Registries.ENTITY_TYPE);
        return new Item.Settings().attributeModifiers(material.createAttributeModifiers(EquipmentType.BODY))
                .component(
                        DataComponentTypes.EQUIPPABLE,
                        EquippableComponent.builder(EquipmentSlot.BODY)
                                .equipSound(SoundEvents.ENTITY_HORSE_ARMOR)
                                .model(material.assetId())
                                .allowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_GREAT_HORN_ARMOR))
                                .damageOnHurt(false)
                                .build()
                )
                .maxCount(1);
    }
}
