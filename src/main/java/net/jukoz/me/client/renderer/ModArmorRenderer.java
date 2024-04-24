package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.client.model.equipment.chest.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.client.model.equipment.head.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.InnerArmorModel;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.*;

public class ModArmorRenderer implements ArmorRenderer {

    //TODO datagen tags for dyeable -> auto do render of item client class
    //TODO fix item overlay dyeable item
    private  Identifier ARMOR_LAYER_0;
    private  Identifier ARMOR_LAYER_1;
    private  Identifier ARMOR_LAYER_2;
    private  Identifier ARMOR_LAYER_CAPE;
    private  Identifier ARMOR_LAYER_HOOD;

    private  CustomHelmetModel<LivingEntity> helmetModel;
    private  CustomChestplateModel<LivingEntity> chestplateModel;
    private static InnerArmorModel<LivingEntity> innerArmorModel;
    private static ArmorEntityModel<LivingEntity> armorModel;
    private static ArmorEntityModel<LivingEntity> legArmorModel;
    private static CloakCapeModel<LivingEntity> capeModel;
    private static CloakHoodModel<LivingEntity> hoodModel;

    private final boolean hasInnerLayer;
    private final boolean hasVanillaArmorModel;
    private final boolean hasCape;
    private final boolean hasHood;
    private final boolean dyeable;

    public ModArmorRenderer(CustomHelmetModel<LivingEntity> customHelmetModel, CustomChestplateModel<LivingEntity> customChestplateModel, String armorName,
                            boolean hasInnerLayer, boolean hasVanillaArmorModel, boolean hasCape, boolean hasHood, boolean dyeable) {
        helmetModel = customHelmetModel;
        chestplateModel = customChestplateModel;
        ARMOR_LAYER_0 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_0.png");
        ARMOR_LAYER_1 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_1.png");
        ARMOR_LAYER_2 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_layer_2.png");
        ARMOR_LAYER_CAPE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_cape.png");
        ARMOR_LAYER_HOOD = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + armorName + "_hood.png");

        this.hasInnerLayer = hasInnerLayer;
        this.hasVanillaArmorModel = hasVanillaArmorModel;
        this.hasCape = hasCape;
        this.hasHood = hasHood;
        this.dyeable = dyeable;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (innerArmorModel == null) {
            innerArmorModel = new InnerArmorModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.INNER_ARMOR_MODEL_LAYER));
            armorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
            legArmorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
            capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
            hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));
        }

        if (slot == EquipmentSlot.HEAD) {
            if(hasVanillaArmorModel){
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.head.visible = true;
                armorModel.hat.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
            }
            if (helmetModel != null) {
                contextModel.copyBipedStateTo(helmetModel);
                helmetModel.setVisible(false);
                helmetModel.head.visible = true;
                helmetModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.pitch);
                renderArmor(matrices, vertexConsumers, light, stack, helmetModel, helmetModel.HELMET_ADDON_TEXTURE, this.dyeable);
            }
            if(hasHood){
                contextModel.copyBipedStateTo(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, hoodModel, ARMOR_LAYER_HOOD, this.dyeable);
            }
        } else if (slot == EquipmentSlot.CHEST) {
            if(hasInnerLayer) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                innerArmorModel.body.visible = true;
                innerArmorModel.rightArm.visible = true;
                innerArmorModel.leftArm.visible = true;
                innerArmorModel.rightLeg.visible = true;
                innerArmorModel.leftLeg.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0, this.dyeable);
            }

            if(hasVanillaArmorModel) {
                contextModel.copyBipedStateTo(armorModel);
                armorModel.setVisible(false);
                armorModel.body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
            }

            if(this.chestplateModel != null) {
                contextModel.copyBipedStateTo(chestplateModel);
                chestplateModel.setVisible(false);
                chestplateModel.body.visible = true;
                chestplateModel.rightArm.visible = true;
                chestplateModel.leftArm.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, chestplateModel, chestplateModel.CHESTPLATE_ADDON_TEXTURE, this.dyeable);
            }

            if (this.hasCape) {
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.setAngles(entity, entity.limbAnimator.getPos(),entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.roll);
                renderArmor(matrices, vertexConsumers, light, stack, capeModel, ARMOR_LAYER_CAPE, this.dyeable);
            }
        } else if (slot == EquipmentSlot.LEGS) {
            if(hasInnerLayer) {
                contextModel.copyBipedStateTo(innerArmorModel);
                innerArmorModel.setVisible(false);
                renderArmor(matrices, vertexConsumers, light, stack, innerArmorModel, ARMOR_LAYER_0, this.dyeable);
            }
            contextModel.copyBipedStateTo(legArmorModel);
            legArmorModel.setVisible(false);
            legArmorModel.body.visible = true;
            legArmorModel.rightLeg.visible = true;
            legArmorModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, legArmorModel, ARMOR_LAYER_2, this.dyeable);
        } else if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.rightLeg.visible = true;
            armorModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, armorModel, ARMOR_LAYER_1, this.dyeable);
        }
    }


    static void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean dyeable){
        if(dyeable){
            renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            if(!(model == capeModel)){
                renderDyeable(matrices, vertexConsumers, light, stack, model, new Identifier(MiddleEarth.MOD_ID,
                        texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }
    }

    static void renderDyeable(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
        Color rgb = IntToRGB.ex(DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f, 1.0F);
    }
}
