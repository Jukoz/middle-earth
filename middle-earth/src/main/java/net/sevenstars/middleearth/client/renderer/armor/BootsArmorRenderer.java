package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;

public class BootsArmorRenderer implements ArmorRenderer {

    private final CustomBootsModel customBootsModel = new CustomBootsModel(CustomBootsModel.getTexturedModelData().bakeRoot());

    public BootsArmorRenderer() {
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.FEET) {
            contextModel.copyPropertiesTo(customBootsModel);
            customBootsModel.setAllVisible(false);
            customBootsModel.rightLeg.visible = true;
            customBootsModel.leftLeg.visible = true;

            if (stack.is(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customBootsModel,
                    RenderResourceCache.armor(stack.getItem()).base(), dyeable);
        }
    }
}
