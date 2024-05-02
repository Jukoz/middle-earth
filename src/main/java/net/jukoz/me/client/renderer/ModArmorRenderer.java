package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomBootsModel;
import net.jukoz.me.client.model.equipment.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.CustomLeggingsModel;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.client.model.equipment.head.HelmetAddonModel;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
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
    private  String ARMOR_TEXTURE;

    private HelmetAddonModel<LivingEntity> helmetModel;
    private ChestplateAddonModel<LivingEntity> chestplateModel;
    private static CustomHelmetModel<LivingEntity> customHelmetModel;
    private static CustomChestplateModel<LivingEntity> customChestplateModel;
    private static CustomLeggingsModel<LivingEntity> customLeggingsModel;
    private static CustomBootsModel<LivingEntity> customBootsModel;
    private static CloakCapeModel<LivingEntity> capeModel;
    private static CloakHoodModel<LivingEntity> hoodModel;
    
    private final boolean hasCape;
    private final boolean hasHood;
    private final boolean dyeable;

    public ModArmorRenderer(HelmetAddonModel<LivingEntity> helmetAddonModel, ChestplateAddonModel<LivingEntity> customChestplateModel, String armorName,
                            boolean hasCape, boolean hasHood, boolean dyeable) {

        helmetModel = helmetAddonModel;
        chestplateModel = customChestplateModel;
        ARMOR_TEXTURE = "textures/models/armor/" + armorName + ".png";

        this.hasCape = hasCape;
        this.hasHood = hasHood;
        this.dyeable = dyeable;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (customHelmetModel == null) {
            customHelmetModel = new CustomHelmetModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_HELMET));
            customChestplateModel = new CustomChestplateModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_CHESTPLATE));
            customLeggingsModel = new CustomLeggingsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_LEGGINGS));
            customBootsModel = new CustomBootsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_BOOTS));
            capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
            hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));
        }

        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyBipedStateTo(customHelmetModel);
            customHelmetModel.setVisible(false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            renderArmor(matrices, vertexConsumers, light, stack, customHelmetModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_helmet.png")), this.dyeable);
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
                renderArmor(matrices, vertexConsumers, light, stack, hoodModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_hood.png")), this.dyeable);
            }

        } else if (slot == EquipmentSlot.CHEST) {
            contextModel.copyBipedStateTo(customChestplateModel);
            customChestplateModel.setVisible(false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, customChestplateModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_chestplate.png")), this.dyeable);
            if(this.chestplateModel != null) {
                contextModel.copyBipedStateTo(chestplateModel);
                chestplateModel.setVisible(false);
                chestplateModel.body.visible = true;
                chestplateModel.rightArm.visible = true;
                chestplateModel.leftArm.visible = true;
                renderArmor(matrices, vertexConsumers, light, stack, chestplateModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_addon.png")), this.dyeable);
            }

            if (this.hasCape) {
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.setAngles(entity, entity.limbAnimator.getPos(),entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.roll);
                renderArmor(matrices, vertexConsumers, light, stack, capeModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_cape.png")), this.dyeable);
            }

        } else if (slot == EquipmentSlot.LEGS) {
            contextModel.copyBipedStateTo(customLeggingsModel);
            customLeggingsModel.setVisible(false);
            customLeggingsModel.body.visible = true;
            customLeggingsModel.rightLeg.visible = true;
            customLeggingsModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, customLeggingsModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_leggings.png")), this.dyeable);
        } else if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(customBootsModel);
            customBootsModel.setVisible(false);
            customBootsModel.rightLeg.visible = true;
            customBootsModel.leftLeg.visible = true;
            renderArmor(matrices, vertexConsumers, light, stack, customBootsModel, new Identifier(MiddleEarth.MOD_ID, ARMOR_TEXTURE.replaceAll(".png", "_boots.png")), this.dyeable);
        }
    }

    static void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean dyeable){
        if(dyeable){
            /*renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            if(!(model == capeModel)){
                renderDyeable(matrices, vertexConsumers, light, stack, model, new Identifier(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
            }*/
            renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new Identifier(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }
    }

    static void renderDyeable(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
        Color rgb = IntToRGB.ex(DyedColorComponent.getColor(stack, 12135725));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f, 1.0F);
    }
}
