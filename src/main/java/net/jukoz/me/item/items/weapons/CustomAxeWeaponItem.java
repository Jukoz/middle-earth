package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomAxeWeaponItem extends AxeItem {
    private final MutableText faction;
    private final MutableText subFaction;

    private final ModWeaponTypes type;

    public CustomAxeWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersAxe(toolMaterial, ModWeaponTypes.AXE.attack, ModWeaponTypes.AXE.attackSpeed)));
        this.faction = Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic");
        this.subFaction = null;
        this.type = ModWeaponTypes.AXE;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial,  MutableText faction) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersAxe(toolMaterial, ModWeaponTypes.AXE.attack, ModWeaponTypes.AXE.attackSpeed)));
        this.faction = faction;
        this.subFaction = null;
        this.type = ModWeaponTypes.AXE;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial,  MutableText faction, MutableText subFaction) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersAxe(toolMaterial, ModWeaponTypes.AXE.attack, ModWeaponTypes.AXE.attackSpeed)));
        this.faction = faction;
        this.subFaction = subFaction;
        this.type = ModWeaponTypes.AXE;
    }

    public static AttributeModifiersComponent createAttributeModifiersAxe(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                        baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
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
        if(Registries.ITEM.getId(stack.getItem()).getPath().contains("_noble") || Registries.ITEM.getId(stack.getItem()).getPath().contains("_elite")){
            return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
