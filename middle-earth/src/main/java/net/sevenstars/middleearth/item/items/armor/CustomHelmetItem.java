package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomHelmetItem extends ArmorItem {

    public CustomHelmetItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, Type.HELMET, settings.stacksTo(1));
    }

    public static void toggleHoodState(ServerPlayer player, ItemStack stack){
        HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (hoodDataComponent != null){
            if (hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null) {
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.displayClientMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null){
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.displayClientMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
