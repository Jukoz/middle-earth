package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public class CustomSpearWeaponItem extends ReachWeaponItem {
    public MutableText faction;
    public MutableText subFaction;
    public ModWeaponTypes type;

    public CustomSpearWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, MutableText faction) {
        super(toolMaterial, faction, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, MutableText faction, MutableText subFaction) {
        super(toolMaterial, faction, subFaction, ModWeaponTypes.SPEAR);
    }
}
