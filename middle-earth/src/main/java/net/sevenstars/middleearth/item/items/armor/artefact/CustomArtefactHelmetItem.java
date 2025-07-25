package net.sevenstars.middleearth.item.items.armor.artefact;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class CustomArtefactHelmetItem extends CustomHelmetItem {
    public CustomArtefactHelmetItem(ExtendedArmorMaterial material, Settings settings, Faction faction) {
        super(material, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
    }

    //TODO canRepair gone need to find new thing -> component REPAIRABLE

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
