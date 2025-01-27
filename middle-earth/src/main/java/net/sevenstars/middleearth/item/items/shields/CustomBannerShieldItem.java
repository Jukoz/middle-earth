package net.sevenstars.middleearth.item.items.shields;

import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;

public class CustomBannerShieldItem extends CustomShieldItem {
    public CustomBannerShieldItem(ModShieldTypes type, ModFactions faction) {
        super(type, faction);
    }

    public CustomBannerShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(type, subFaction);
    }
}
