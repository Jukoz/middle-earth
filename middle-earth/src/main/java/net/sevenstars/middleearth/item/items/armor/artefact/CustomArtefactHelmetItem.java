package net.sevenstars.middleearth.item.items.armor.artefact;

import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CustomArtefactHelmetItem extends CustomHelmetItem {
    public CustomArtefactHelmetItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(material, settings, faction);
    }

    public CustomArtefactHelmetItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(material, settings, subFaction);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseArtefactTooltip(tooltip, stack);
    }

    //TODO canRepair gone need to find new thing

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
