package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.EquipmentTooltipME;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomChestplateItem extends ArmorItem implements EquipmentTooltipME {

    public ModFactions faction;
    public ModSubFactions subFaction;


    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(material, settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));

        this.faction = faction;
        this.subFaction = null;
    }

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(material, settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));

        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.getMaterial().tier().toString().toLowerCase()));

        return list;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        BackAttachmentDataComponent capeDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        DyedColorComponent dyeDataComponent = stack.get(DataComponentTypes.DYED_COLOR);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").formatted(Formatting.GRAY).append(": " + String.format(EquipmentTooltipME.COLOR_PREFIX, (0xFFFFFF & DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (capeDataComponent != null) {
            if (DyeablePiecesME.dyeableBackAttachments.containsKey(capeDataComponent.backAttachment())){
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.backAttachment().getName())
                        .append(" (")
                        .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color")
                                .append(": " + String.format("#%06X", (0xFFFFFF & BackAttachmentDataComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR))))
                                .append(")")).formatted(Formatting.GRAY));
            } else {
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + capeDataComponent.backAttachment().getName()).formatted(Formatting.GRAY));
            }
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        appendBaseTooltip(textConsumer, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
