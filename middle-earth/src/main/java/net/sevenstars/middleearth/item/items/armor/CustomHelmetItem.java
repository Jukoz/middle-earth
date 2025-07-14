package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.EquipmentTooltipME;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
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

public class CustomHelmetItem extends Item implements EquipmentTooltipME {

    public ModFactions faction;
    public ModSubFactions subFaction;

    private ExtendedArmorMaterial material;

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(settings.armor(material.material(), EquipmentType.HELMET).maxCount(1));


        this.material = material;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(settings.armor(material.material(), EquipmentType.HELMET).maxCount(1));

        this.material = material;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));

        return list;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(DataComponentTypesME.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").append(": " + String.format(EquipmentTooltipME.COLOR_PREFIX, (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (hoodDataComponent != null) {
            if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(hoodDataComponent.helmetAttachment())){
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.helmetAttachment().getName())
                        .append(" (")
                        .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color")
                                .append(": " + String.format("#%06X", (0xFFFFFF & HelmetAttachmentDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR))))
                                .append(")")).formatted(Formatting.GRAY));
            } else {
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.helmetAttachment().getName()).formatted(Formatting.GRAY));
            }
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        appendBaseTooltip(textConsumer, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    public static void toggleHoodState(ServerPlayerEntity player, ItemStack stack){
        HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (hoodDataComponent != null){
            if (hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null) {
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null){
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
