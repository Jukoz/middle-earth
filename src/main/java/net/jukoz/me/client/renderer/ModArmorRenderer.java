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
import net.jukoz.me.item.items.CustomBootsItem;
import net.jukoz.me.item.items.CustomChestplateItem;
import net.jukoz.me.item.items.CustomHelmetItem;
import net.jukoz.me.item.items.CustomLeggingsItem;
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
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.awt.*;

public class ModArmorRenderer implements ArmorRenderer {

    //TODO datagen tags for dyeable -> auto do render of item client class
    //TODO fix item overlay dyeable item
    private CustomHelmetModel<LivingEntity> customHelmetModel;
    private CustomChestplateModel<LivingEntity> customChestplateModel;
    private CustomLeggingsModel<LivingEntity> customLeggingsModel;
    private CustomBootsModel<LivingEntity> customBootsModel;
    private CloakCapeModel<LivingEntity> capeModel;
    private CloakHoodModel<LivingEntity> hoodModel;
    

    public ModArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        customHelmetModel = new CustomHelmetModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_HELMET));
        customChestplateModel = new CustomChestplateModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_CHESTPLATE));
        customLeggingsModel = new CustomLeggingsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_LEGGINGS));
        customBootsModel = new CustomBootsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_BOOTS));
        capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
        hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        if (slot == EquipmentSlot.HEAD) {
            renderHelmet(stack, matrices, vertexConsumers, entity, light, contextModel);
        } else if (slot == EquipmentSlot.CHEST) {
            renderChestplate(stack, matrices, vertexConsumers, entity, light, contextModel);
        } else if (slot == EquipmentSlot.LEGS) {
            renderLeggings(stack, matrices, vertexConsumers, entity, light, contextModel);
        } else if (slot == EquipmentSlot.FEET) {
            renderBoots(stack, matrices, vertexConsumers, entity, light, contextModel);
        }
    }

    void renderHelmet(ItemStack helmet, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light, BipedEntityModel<LivingEntity> contextModel) {
        CustomHelmetItem item = (CustomHelmetItem)helmet.getItem();
        boolean dyeable = false;
        contextModel.copyBipedStateTo(customHelmetModel);
        customHelmetModel.setVisible(false);
        customHelmetModel.head.visible = true;
        customHelmetModel.hat.visible = true;
        customHelmetModel.body.visible = true;
        customHelmetModel.leftArm.visible = true;
        customHelmetModel.rightArm.visible = true;

        if(item.getCustomsList() != null) {
            if (item.getCustomsList().contains(CustomHelmetItem.Customizations.DYEABLE)) {
                dyeable = true;
            }
        }

        String texture = "textures/models/armor/" + Registries.ITEM.getId(helmet.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, helmet, customHelmetModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);

        if (item.additionModel != null) {
            contextModel.copyBipedStateTo(item.additionModel);
            item.additionModel.setVisible(false);
            item.additionModel.head.visible = true;
            item.additionModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.pitch);
            renderArmor(matrices, vertexConsumers, light, helmet, item.additionModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
        }

        if(item.getCustomsList() != null) {
            if (item.getCustomsList().contains(CustomHelmetItem.Customizations.HOOD)) {
                contextModel.copyBipedStateTo(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                renderArmor(matrices, vertexConsumers, light, helmet, hoodModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_hood.png")), dyeable);
            }
        }
    }
    void renderChestplate(ItemStack chestplate, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light, BipedEntityModel<LivingEntity> contextModel) {
        CustomChestplateItem item = (CustomChestplateItem)chestplate.getItem();
        boolean dyeable = false;

        contextModel.copyBipedStateTo(customChestplateModel);
        customChestplateModel.setVisible(false);
        customChestplateModel.body.visible = true;
        customChestplateModel.rightArm.visible = true;
        customChestplateModel.leftArm.visible = true;
        customChestplateModel.rightLeg.visible = true;
        customChestplateModel.leftLeg.visible = true;

        if(item.getCustomsList() != null) {
            if (item.getCustomsList().contains(CustomChestplateItem.Customizations.DYEABLE)) {
                dyeable = true;
            }
        }

        String texture = "textures/models/armor/" + Registries.ITEM.getId(chestplate.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, chestplate, customChestplateModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);

        if (item.additionModel != null) {
            contextModel.copyBipedStateTo(item.additionModel);
            item.additionModel.setVisible(false);
            item.additionModel.body.visible = true;
            item.additionModel.rightArm.visible = true;
            item.additionModel.leftArm.visible = true;
            renderArmor(matrices, vertexConsumers, light, chestplate, item.additionModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_addition.png")), dyeable);
        }

        if(item.getCustomsList() != null){
            if(item.getCustomsList().contains(CustomChestplateItem.Customizations.CAPE)){
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                //capeModel.setAngles(entity, entity.limbAnimator.getPos(),entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.roll);
                renderArmor(matrices, vertexConsumers, light, chestplate, capeModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_cape.png")), dyeable);
            }
        }
    }
    void renderLeggings(ItemStack leggings, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light, BipedEntityModel<LivingEntity> contextModel) {
        CustomLeggingsItem item = (CustomLeggingsItem)leggings.getItem();
        boolean dyeable = false;

        contextModel.copyBipedStateTo(customLeggingsModel);
        customLeggingsModel.setVisible(false);
        customLeggingsModel.body.visible = true;
        customLeggingsModel.rightLeg.visible = true;
        customLeggingsModel.leftLeg.visible = true;

        if(item.getCustomsList() != null) {
            if (item.getCustomsList().contains(CustomLeggingsItem.Customizations.DYEABLE)) {
                dyeable = true;
            }
        }


        String texture = "textures/models/armor/" + Registries.ITEM.getId(leggings.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, leggings, customLeggingsModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);
    }
    void renderBoots(ItemStack boots, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light, BipedEntityModel<LivingEntity> contextModel) {
        CustomBootsItem item = (CustomBootsItem)boots.getItem();
        boolean dyeable = false;
        contextModel.copyBipedStateTo(customBootsModel);
        customBootsModel.setVisible(false);
        customBootsModel.rightLeg.visible = true;
        customBootsModel.leftLeg.visible = true;

        if(item.getCustomsList() != null) {
            if (item.getCustomsList().contains(CustomBootsItem.Customizations.DYEABLE)) {
                dyeable = true;
            }
        }

        String texture = "textures/models/armor/" + Registries.ITEM.getId(boots.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, boots, customBootsModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);

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
