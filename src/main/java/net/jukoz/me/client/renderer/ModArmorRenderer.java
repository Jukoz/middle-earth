package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomBootsModel;
import net.jukoz.me.client.model.equipment.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.CustomLeggingsModel;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ModArmorRenderer implements ArmorRenderer {

    //TODO datagen tags for dyeable -> auto do render of item client class
    //TODO fix item overlay dyeable item
    //TODO dyeable specify if overlay
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
        this.customHelmetModel = new CustomHelmetModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_HELMET));
        this.customChestplateModel = new CustomChestplateModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_CHESTPLATE));
        this.customLeggingsModel = new CustomLeggingsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_LEGGINGS));
        this.customBootsModel = new CustomBootsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_BOOTS));
        this.capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));
        this.hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

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

    void renderHelmet(@NotNull ItemStack helmet, MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light, BipedEntityModel<LivingEntity> contextModel) {
        CustomHelmetItem item = (CustomHelmetItem)helmet.getItem();
        boolean dyeable = false;
        contextModel.copyBipedStateTo(customHelmetModel);
        customHelmetModel.setVisible(false);
        customHelmetModel.head.visible = true;
        customHelmetModel.hat.visible = true;
        customHelmetModel.body.visible = true;
        customHelmetModel.leftArm.visible = true;
        customHelmetModel.rightArm.visible = true;

        if(helmet.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
        }

        String texture = "textures/models/armor/" + Registries.ITEM.getId(helmet.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, helmet, customHelmetModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);

        if (item.additionModel != null) {
            contextModel.copyBipedStateTo(item.additionModel);
            item.additionModel.setVisible(false);
            item.additionModel.head.visible = true;
            item.additionModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.pitch);
            if(texture.contains("_helmet")){
                renderArmor(matrices, vertexConsumers, light, helmet, item.additionModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
            } else {
                renderArmor(matrices, vertexConsumers, light, helmet, item.additionModel, new Identifier(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
            }
        }

        HoodDataComponent hoodDataComponent = helmet.get(ModDataComponentTypes.HOOD_DATA);

        if(hoodDataComponent != null) {
            if (hoodDataComponent.enabled()) {
                contextModel.copyBipedStateTo(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                if(hoodDataComponent.down()){
                    renderArmor(matrices, vertexConsumers, light, helmet, hoodModel, new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.target() + "_down.png"), false);
                } else {
                    renderArmor(matrices, vertexConsumers, light, helmet, hoodModel, new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.target() + ".png"), false);
                }
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

        if(chestplate.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
            dyeable = true;
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

        CapeDataComponent capeDataComponent = chestplate.get(ModDataComponentTypes.CAPE_DATA);
        if(capeDataComponent != null) {
            if (capeDataComponent.enabled()) {
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.setAngles(entity, entity.limbAnimator.getPos(),entity.limbAnimator.getSpeed(),(float)entity.age + MinecraftClient.getInstance().getTickDelta(), contextModel.head.yaw, contextModel.head.roll);
                renderArmor(matrices, vertexConsumers, light, chestplate, capeModel, new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + capeDataComponent.target() + ".png"), false);
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

        if(leggings.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
            dyeable = true;
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

        if(boots.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
            dyeable = true;
        }

        String texture = "textures/models/armor/" + Registries.ITEM.getId(boots.getItem()).getPath() + ".png";
        renderArmor(matrices, vertexConsumers, light, boots, customBootsModel, new Identifier(MiddleEarth.MOD_ID, texture), dyeable);

    }

    static void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean dyeable){
        if(dyeable){
            renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            if(CustomDyeableDataComponent.getOverlay(stack)) {
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new Identifier(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }
    }

    static void renderDyeable(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
        Color rgb = IntToRGB.ex(CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f, 1.0F);
    }
}
