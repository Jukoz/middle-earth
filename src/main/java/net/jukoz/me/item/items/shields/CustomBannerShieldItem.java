package net.jukoz.me.item.items.shields;

import net.jukoz.me.item.utils.ModShieldTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;

public class CustomBannerShieldItem extends CustomShieldItem{
    public CustomBannerShieldItem(ModShieldTypes type, ModFactions faction) {
        super(type, faction);
    }

    public CustomBannerShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(type, subFaction);
    }
}
