package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;

public class CustomLongswordWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public CustomLongswordWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, ModWeaponTypes.LONGSWORD, settings);
    }

    public CustomLongswordWeaponItem(ToolMaterial toolMaterial, ModFactions faction, Item.Settings settings) {
        super(toolMaterial, faction, ModWeaponTypes.LONGSWORD, settings);
    }

    public CustomLongswordWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction, Item.Settings settings) {
        super(toolMaterial, subFaction, ModWeaponTypes.LONGSWORD, settings);
    }
}
