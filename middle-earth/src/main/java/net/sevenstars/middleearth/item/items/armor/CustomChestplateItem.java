package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;

public class CustomChestplateItem extends ArmorItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, Type.CHESTPLATE, settings.stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        super.inventoryTick(stack, level, entity, slotId, selected);
        if (!(level instanceof ServerLevel world)) {
            return;
        }
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        if(backAttachmentDataComponent != null) {
            int id = backAttachmentDataComponent.getBackAttachment().getId();
            if(id == BackAttachmentsME.MANTLE_OF_YAVANNA.getId()) {
                MantleOfYavannaItem.applyBackAttachmentColor(stack, world, entity, backAttachmentDataComponent);
            }
        }
    }
}
