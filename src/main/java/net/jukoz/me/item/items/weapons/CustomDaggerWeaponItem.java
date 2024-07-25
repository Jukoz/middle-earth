package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, ModWeaponTypes.DAGGER);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, MutableText faction) {
        super(toolMaterial, faction, ModWeaponTypes.DAGGER);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, MutableText faction, MutableText subFaction) {
        super(toolMaterial, faction, subFaction, ModWeaponTypes.DAGGER);
    }
}
