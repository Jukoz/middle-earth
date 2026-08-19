package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;

public class LeggingsArmorRenderer implements ArmorRenderer {

    private final CustomLeggingsModel customLeggingsModel = new CustomLeggingsModel(CustomLeggingsModel.getTexturedModelData().bakeRoot());

    public LeggingsArmorRenderer() {
    }


    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.LEGS) {
            contextModel.copyPropertiesTo(customLeggingsModel);
            customLeggingsModel.setAllVisible(false);
            customLeggingsModel.body.visible = true;
            customLeggingsModel.rightLeg.visible = true;
            customLeggingsModel.leftLeg.visible = true;

            if (stack.is(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customLeggingsModel,
                    RenderResourceCache.armor(stack.getItem()).base(), dyeable);
        }
    }
}
