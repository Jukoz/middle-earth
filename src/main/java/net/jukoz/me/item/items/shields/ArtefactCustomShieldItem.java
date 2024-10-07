package net.jukoz.me.item.items.shields;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ArtefactCustomShieldItem extends CustomShieldItem {

    public ArtefactCustomShieldItem(ModShieldTypes type, ModFactions faction) {
        super(type, faction);
    }

    public ArtefactCustomShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(type, subFaction);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(!(stack.getDamage() < stack.getMaxDamage() - 1)){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact_broken").formatted(Formatting.ITALIC).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact").formatted(Formatting.AQUA));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shield_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if(stack.getDamage() == stack.getMaxDamage() - 1) {
            return false;
        } else if( stack.getDamage() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
