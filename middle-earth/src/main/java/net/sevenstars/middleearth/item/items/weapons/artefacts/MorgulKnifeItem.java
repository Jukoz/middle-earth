package net.sevenstars.middleearth.item.items.weapons.artefacts;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class MorgulKnifeItem extends ArtefactCustomDaggerWeaponItem {

    public MorgulKnifeItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() < stack.getMaxDamage() - 1){
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 6000));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 6000));
        }
        if (stack.getDamage() == stack.getMaxDamage() - 1){
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder()
                    .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                            0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                            -3.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                            0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .build());
            stack.remove(DataComponentTypes.WEAPON);
        }
    }
}
