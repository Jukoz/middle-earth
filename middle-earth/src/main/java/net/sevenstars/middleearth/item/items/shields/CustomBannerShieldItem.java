package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;

public class CustomBannerShieldItem extends CustomShieldItem {
    public CustomBannerShieldItem(ModShieldTypes type, ModFactions faction, Item.Settings settings) {
        super(type, faction, settings);
    }

    public CustomBannerShieldItem(ModShieldTypes type, ModSubFactions subFaction, Item.Settings settings) {
        super(type, subFaction, settings);
    }
}
