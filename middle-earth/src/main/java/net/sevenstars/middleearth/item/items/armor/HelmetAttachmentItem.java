package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class HelmetAttachmentItem extends Item{

    public HelmetAttachmentItem(Item.Settings settings, ExtendedArmorMaterial armorMaterial) {
        super(settings.armor(armorMaterial.material(), EquipmentType.HELMET).maxCount(1));
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
