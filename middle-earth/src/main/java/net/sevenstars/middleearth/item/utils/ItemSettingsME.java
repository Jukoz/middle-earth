package net.sevenstars.middleearth.item.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;

public interface ItemSettingsME {
    ResourceLocation ENTITY_INTERACTION_RANGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity_interaction_range");

    static Item.Properties createWeaponSettings(Tier material, Item.Properties settings, WeaponTypesME type) {
        return settings.durability(material.getUses())
                .attributes(createWeaponAttributes(material, type.attack, type.attackSpeed, type.attackRange))
                .component(DataComponents.TOOL, SwordItem.createToolProperties())
                .component(DataComponentTypesME.WEAPON_ACTIVE, true);
    }

    static ItemAttributeModifiers createWeaponAttributes(
            Tier material, float attackDamage, float attackSpeed, float attackRange
    ) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID,
                                attackDamage + material.getAttackDamageBonus(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                Item.BASE_ATTACK_SPEED_ID,
                                attackSpeed,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(
                                ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                                attackRange,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }
}
