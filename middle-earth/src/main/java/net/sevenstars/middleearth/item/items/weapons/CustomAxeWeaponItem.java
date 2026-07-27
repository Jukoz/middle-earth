package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class CustomAxeWeaponItem extends AxeItem {

    public CustomAxeWeaponItem(Tier toolMaterial, Item.Properties settings) {
        super(toolMaterial, settings.attributes(AxeItem.createAttributes(
                        toolMaterial, WeaponTypesME.AXE.attack, WeaponTypesME.AXE.attackSpeed))
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.AXE.name))
                .component(DataComponentTypesME.WEAPON_ACTIVE, true));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return hasActiveWeaponComponent(stack);
    }

    protected final boolean hasActiveWeaponComponent(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(DataComponentTypesME.WEAPON_ACTIVE));
    }

    protected final void disableWeaponComponent(ItemStack stack) {
        stack.remove(DataComponentTypesME.WEAPON_ACTIVE);
    }

    protected final void damageAfterAttack(ItemStack stack, LivingEntity attacker) {
        if (hasActiveWeaponComponent(stack)) {
            stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if(BuiltInRegistries.ITEM.getKey(this).getPath().contains("_noble")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("_elite")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("uruk_hai")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("heyday")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("numenorean")){
            return Component.translatable(this.getDescriptionId()).withStyle(ChatFormatting.GOLD);
        }
        return super.getName(stack);
    }
}
