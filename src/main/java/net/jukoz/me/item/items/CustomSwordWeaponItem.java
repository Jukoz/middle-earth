package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.WeaponTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomSwordWeaponItem extends SwordItem {
    private final MutableText faction;
    private final MutableText subFaction;

    private final WeaponTypes type;

    public CustomSwordWeaponItem(ToolMaterial toolMaterial, WeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = null;
        this.subFaction = null;
        this.type = type;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial,  MutableText faction, WeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public CustomSwordWeaponItem(ToolMaterial toolMaterial,  MutableText faction, MutableText subFaction, WeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed)));
        this.faction = faction;
        this.subFaction = subFaction;
        this.type = type;
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
}
