package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomSwordWeaponItem extends SwordItem {
    public final ModFactions faction;
    public final ModSubFactions subFaction;

    public final ModWeaponTypes type;

    public CustomSwordWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, ModWeaponTypes.SWORD.attack, ModWeaponTypes.SWORD.attackSpeed)));
        this.faction = ModFactions.NONE;
        this.subFaction = null;
        this.type = ModWeaponTypes.SWORD;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial,  ModFactions faction) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, ModWeaponTypes.SWORD.attack, ModWeaponTypes.SWORD.attackSpeed)));
        this.faction = faction;
        this.subFaction = null;
        this.type = ModWeaponTypes.SWORD;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiersSword(toolMaterial, ModWeaponTypes.SWORD.attack, ModWeaponTypes.SWORD.attackSpeed)));
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        this.type = ModWeaponTypes.SWORD;
    }

    public static AttributeModifiersComponent createAttributeModifiersSword(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
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
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name));
            }
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
