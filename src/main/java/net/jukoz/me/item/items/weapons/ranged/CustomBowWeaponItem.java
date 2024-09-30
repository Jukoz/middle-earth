package net.jukoz.me.item.items.weapons.ranged;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Predicate;

public class CustomBowWeaponItem extends BowItem {
    private final ModFactions faction;
    private final ModSubFactions subFaction;

    public CustomBowWeaponItem(Settings settings) {
        super(settings);
        this.faction = null;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(ModFactions faction, Settings settings) {
        super(settings);
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(ModSubFactions subFaction, Settings settings) {
        super(settings);
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
