package net.sevenstars.middleearth.item.items.weapons.ranged;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.utils.RangedWeaponTypesME;

import java.util.function.Predicate;

public class CustomCrossbowWeaponItem extends CrossbowItem {
    public RangedWeaponTypesME type;

    public CustomCrossbowWeaponItem(RangedWeaponTypesME type, Item.Properties settings) {
        super(settings.durability(type.durability));
        this.type = type;
    }

    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ARROW_ONLY;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
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
