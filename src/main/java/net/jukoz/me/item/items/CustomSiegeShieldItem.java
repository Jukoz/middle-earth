package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class CustomSiegeShieldItem extends CustomShieldItem {

    public CustomSiegeShieldItem(ModShieldTypes type, MutableText faction) {
        super(type, faction);
    }

    public CustomSiegeShieldItem(ModShieldTypes type, MutableText faction, MutableText subFaction) {
        super(type, faction, subFaction);
    }
}
