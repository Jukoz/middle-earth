package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.EquipmentTooltipME;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HelmetAttachmentItem extends Item implements EquipmentTooltipME {
    public ModFactions faction;
    public ModSubFactions subFaction;

    public HelmetAttachmentItem(Item.Settings settings, ExtendedArmorMaterial armorMaterial, ModFactions faction) {
        super(settings.armor(armorMaterial.material(), EquipmentType.HELMET).maxCount(1));

        this.faction = faction;
        this.subFaction = null;
    }

    public HelmetAttachmentItem(Item.Settings settings, ExtendedArmorMaterial armorMaterial, ModSubFactions subFaction) {
        super(settings.armor(armorMaterial.material(), EquipmentType.HELMET).maxCount(1));

        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        DyedColorComponent dyeDataComponent = stack.get(DataComponentTypes.DYED_COLOR);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").append(": " + String.format(EquipmentTooltipME.COLOR_PREFIX, (0xFFFFFF & DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (helmetAttachmentDataComponent != null) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + helmetAttachmentDataComponent.helmetAttachment().getName()).formatted(Formatting.GRAY));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        appendBaseTooltip(textConsumer, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    public static void toggleHelmetAttachmentState(ServerPlayerEntity player, ItemStack stack){
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (helmetAttachmentDataComponent != null){
            if (helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null) {
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null){
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
