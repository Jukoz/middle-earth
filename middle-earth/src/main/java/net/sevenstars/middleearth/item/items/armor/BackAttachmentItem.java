package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.EquipmentTooltipME;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BackAttachmentItem extends Item implements EquipmentTooltipME {
    public ModFactions faction;
    public ModSubFactions subFaction;

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material, ModFactions faction) {
        super(settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));

        this.faction = faction;
        this.subFaction = null;
    }

    public BackAttachmentItem(Settings settings, ExtendedArmorMaterial material, ModSubFactions subFaction) {
        super(settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));

        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(DataComponentTypesME.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyed").append(String.format(EquipmentTooltipME.COLOR_PREFIX, (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (backAttachmentDataComponent != null) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + backAttachmentDataComponent.backAttachment().getName()).formatted(Formatting.GRAY));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        appendBaseTooltip(textConsumer, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
