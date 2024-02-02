package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.mixin.client.rendering.ArmorFeatureRendererMixin;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CloakCapeModel;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.InnerArmorModel;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.*;

public class ModArmorRenderer implements ArmorRenderer {
    private  Identifier ARMOR_LAYER_0;
    private  Identifier ARMOR_LAYER_1;
    private  Identifier ARMOR_LAYER_2;
    private  Identifier ARMOR_LAYER_CAPE;

    private  CustomHelmetModel<LivingEntity> helmetModel;
    private static InnerArmorModel<LivingEntity> innerArmorModel;
    private static ArmorEntityModel<LivingEntity> armorModel;
    private static ArmorEntityModel<LivingEntity> legArmorModel;
    private static CloakCapeModel<LivingEntity> capeModel;

    private final boolean hasCape;
    private final boolean dyeable;

    public ModArmorRenderer(CustomHelmetModel<LivingEntity> customHelmetModel, String armorName, boolean hasCape, boolean dyeable) {
        helmetModel = customHelmetModel;
        ARMOR_LAYER_0 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_0.png");
        ARMOR_LAYER_1 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_1.png");
        ARMOR_LAYER_2 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_2.png");
        ARMOR_LAYER_CAPE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_cape.png");

        this.hasCape = hasCape;
        this.dyeable = dyeable;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (innerArmorModel == null) {
            innerArmorModel = new InnerArmorModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.INNER_ARMOR_MODEL_LAYER));
            armorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
            legArmorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
            capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
        }

        if(!dyeable) {
            if (slot == EquipmentSlot.HEAD) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.head.visible = true;
                armorModel.hat.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);
                if (helmetModel != null) {
                    contextModel.copyBipedStateTo(helmetModel);
                    helmetModel.setVisible(false);
                    helmetModel.head.visible = true;
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, helmetModel, helmetModel.HELMET_ADDON_TEXTURE);
                    //helmetModel.setAngles(entity, 0,0,(float)entity.age + tickDelta, contextModel.head.yaw, contextModel.head.roll);
                }
            } else if (slot == EquipmentSlot.CHEST) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                innerArmorModel.body.visible = true;
                innerArmorModel.rightArm.visible = true;
                innerArmorModel.leftArm.visible = true;
                innerArmorModel.rightLeg.visible = true;
                innerArmorModel.leftLeg.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0);

                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);

                if (this.hasCape) {
                    contextModel.copyBipedStateTo(capeModel);
                    capeModel.setVisible(false);
                    capeModel.body.visible = true;
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, capeModel, ARMOR_LAYER_CAPE);
                    capeModel.setAngles(entity,0 ,0 ,0 , contextModel.head.yaw, contextModel.head.roll);
                }
            } else if (slot == EquipmentSlot.LEGS) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0);
                contextModel.copyBipedStateTo(legArmorModel);
                legArmorModel.setVisible(false);
                legArmorModel.body.visible = true;
                legArmorModel.rightLeg.visible = true;
                legArmorModel.leftLeg.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, legArmorModel, ARMOR_LAYER_2);
            } else if (slot == EquipmentSlot.FEET) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.rightLeg.visible = true;
                armorModel.leftLeg.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);
            }
        } else {
            if (slot == EquipmentSlot.HEAD) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.head.visible = true;
                armorModel.hat.visible = true;
                renderDyeable(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);
                if (helmetModel != null) {
                    contextModel.copyBipedStateTo(helmetModel);
                    helmetModel.setVisible(false);
                    helmetModel.head.visible = true;
                    renderDyeable(matrices, vertexConsumers, light, stack, helmetModel, helmetModel.HELMET_ADDON_TEXTURE);
                    //helmetModel.setAngles(entity,0 ,0 ,0 , contextModel.head.yaw, contextModel.head.roll);
                }
            } else if (slot == EquipmentSlot.CHEST) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                innerArmorModel.body.visible = true;
                innerArmorModel.rightArm.visible = true;
                innerArmorModel.leftArm.visible = true;
                innerArmorModel.rightLeg.visible = true;
                innerArmorModel.leftLeg.visible = true;
                renderDyeable(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0);

                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;
                renderDyeable(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);

                if (this.hasCape) {
                    contextModel.copyBipedStateTo(capeModel);
                    capeModel.setVisible(false);
                    capeModel.body.visible = true;
                    renderDyeable(matrices, vertexConsumers, light, stack, capeModel, ARMOR_LAYER_CAPE);
                }
            } else if (slot == EquipmentSlot.LEGS) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                renderDyeable(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0);
                contextModel.copyBipedStateTo(legArmorModel);
                legArmorModel.setVisible(false);
                legArmorModel.body.visible = true;
                legArmorModel.rightLeg.visible = true;
                legArmorModel.leftLeg.visible = true;
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, legArmorModel, ARMOR_LAYER_2);
            } else if (slot == EquipmentSlot.FEET) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.rightLeg.visible = true;
                armorModel.leftLeg.visible = true;
                renderDyeable(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1);
            }
        }
    }

    static void renderDyeable(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
        Color rgb = IntToRGB.ex(((DyeableItem)stack.getItem()).getColor(stack));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f, 1.0F);
    }
}