package net.jukoz.me.item.items;

import net.jukoz.me.item.utils.ModToolMaterials;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ToolItem;

public class SmithingHammerItem extends ToolItem {

    public SmithingHammerItem(Settings settings) {
        super(ModToolMaterials.STEEL, settings.maxCount(1).maxDamage(64).attributeModifiers(createAttributeModifiers(-3.5f)));
    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }
}
