package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import org.jetbrains.annotations.Nullable;

public class CustomChestplateItem extends ArmorItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        if(backAttachmentDataComponent != null) {
            int id = backAttachmentDataComponent.getBackAttachment().getId();
            if(id == BackAttachmentsME.MANTLE_OF_YAVANNA.getId()) {
                MantleOfYavannaItem.applyBackAttachmentColor(stack, world, entity, backAttachmentDataComponent);
            }
        }
    }
}
