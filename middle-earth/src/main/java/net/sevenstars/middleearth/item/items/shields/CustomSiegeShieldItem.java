package net.sevenstars.middleearth.item.items.shields;

import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;

public class CustomSiegeShieldItem extends CustomShieldItem {

    public CustomSiegeShieldItem(ModShieldTypes type, ModFactions faction) {
        super(type, faction);
    }

    public CustomSiegeShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(type, subFaction);
    }
}
