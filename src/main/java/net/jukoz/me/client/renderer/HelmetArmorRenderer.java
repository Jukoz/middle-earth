package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.CustomHelmetItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HelmetArmorRenderer implements ArmorRenderer {

    private CustomHelmetModel<LivingEntity> customHelmetModel;
    private CloakHoodModel<LivingEntity> hoodModel;

    public HelmetArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.customHelmetModel = new CustomHelmetModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_HELMET));
        this.hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        CustomHelmetItem item = (CustomHelmetItem)stack.getItem();
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyBipedStateTo(customHelmetModel);
            customHelmetModel.setVisible(false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            if(stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customHelmetModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);

            if (item.additionModel != null) {
                contextModel.copyBipedStateTo(item.additionModel);
                item.additionModel.setVisible(false);
                item.additionModel.head.visible = true;
                item.additionModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), contextModel.head.yaw, contextModel.head.pitch);
                if(texture.contains("_helmet.png")){
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, item.additionModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
                } else {
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, item.additionModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null) {
                if (hoodDataComponent.enabled()) {
                    contextModel.copyBipedStateTo(hoodModel);
                    hoodModel.setVisible(false);
                    hoodModel.hat.visible = true;
                    if(hoodDataComponent.down()){
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, hoodModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.hood().toLowerCase() + "_down.png"), false);
                    } else {
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, hoodModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.hood().toLowerCase() + ".png"), false);
                    }
                }
            }
        }
    }

}
