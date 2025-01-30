package net.sevenstars.middleearth.item.items.shields;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ArtefactCustomShieldItem extends CustomShieldItem implements MEEquipmentTooltip {

    public ArtefactCustomShieldItem(ModShieldTypes type, ModFactions faction) {
        super(type, faction);
    }

    public ArtefactCustomShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(type, subFaction);
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name));
        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseArtefactTooltip(tooltip, stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
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
