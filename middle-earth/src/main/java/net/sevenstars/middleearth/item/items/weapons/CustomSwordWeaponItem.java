package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class CustomSwordWeaponItem extends Item {

    public CustomSwordWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(settings.sword(toolMaterial, WeaponTypesME.SWORD.attack, WeaponTypesME.SWORD.attackSpeed)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.SWORD.name)));
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }

}
