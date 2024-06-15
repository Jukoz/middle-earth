package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ReachWeaponItem extends SwordItem {
    private final float rangeDistance;
    private final MutableText faction;
    private final MutableText subFaction;
    public ReachWeaponItem(ToolMaterial toolMaterial, float rangeDistance, Settings settings) {
        super(toolMaterial, settings);
        this.rangeDistance = rangeDistance;
        this.faction = null;
        this.subFaction = null;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, float rangeDistance, MutableText faction, Settings settings) {
        super(toolMaterial, settings);
        this.rangeDistance = rangeDistance;
        this.faction = faction;
        this.subFaction = null;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, float rangeDistance, MutableText faction, MutableText subFaction, Settings settings) {
        super(toolMaterial, settings);
        this.rangeDistance = rangeDistance;
        this.faction = faction;
        this.subFaction = subFaction;
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed, float rangeDistance) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        (float)baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(JavelinItem.ENTITY_INTERACTION_RANGE_MODIFIER_ID, "Weapon modifier",
                        rangeDistance, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".reach").append(Float.toString(rangeDistance)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".blocks_range")).formatted(Formatting.DARK_GREEN));

        super.appendTooltip(stack, context, tooltip, type);
    }

    public float getRangeDistance() {
        return rangeDistance;
    }
}
