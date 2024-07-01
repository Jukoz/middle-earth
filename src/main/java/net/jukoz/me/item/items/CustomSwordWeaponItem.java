package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomSwordWeaponItem extends SwordItem {
    private final MutableText faction;
    private final MutableText subFaction;

    private final ModWeaponTypes type;

    public CustomSwordWeaponItem(ToolMaterial toolMaterial, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = null;
        this.subFaction = null;
        this.type = type;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial,  MutableText faction, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial,  MutableText faction, MutableText subFaction, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = faction;
        this.subFaction = subFaction;
        this.type = type;
    }

    public static AttributeModifiersComponent createAttributeModifiersSword(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.type != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
            }
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
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble") || Registries.ITEM.getId(this).getPath().contains("_elite")){
            return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
