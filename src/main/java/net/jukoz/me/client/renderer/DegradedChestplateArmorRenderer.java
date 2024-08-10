package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.items.CustomChestplateItem;
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

public class DegradedChestplateArmorRenderer implements ArmorRenderer {

    private CustomChestplateModel<LivingEntity> customChestplateModel;
    private CloakCapeModel<LivingEntity> capeModel;
    private ChestplateAddonModel<LivingEntity> chestplateModel;


    public DegradedChestplateArmorRenderer() {
    }

    public DegradedChestplateArmorRenderer(ChestplateAddonModel<LivingEntity> chestplateModel) {
        this.chestplateModel = chestplateModel;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.customChestplateModel = new CustomChestplateModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_CHESTPLATE));
        this.capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));

        CustomChestplateItem item = (CustomChestplateItem)stack.getItem();
        boolean dyeable = false;
        String texture;

        if (slot == EquipmentSlot.CHEST) {
            contextModel.copyBipedStateTo(customChestplateModel);
            customChestplateModel.setVisible(false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;

            if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
            }

            if((double)stack.getDamage()/(double)stack.getMaxDamage() > 0.5){
                texture = "textures/models/armor/degraded_" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            } else {
                texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            }

            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customChestplateModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.chestplateModel != null) {
                contextModel.copyBipedStateTo(this.chestplateModel);
                this.chestplateModel.setVisible(false);
                this.chestplateModel.body.visible = true;
                this.chestplateModel.rightArm.visible = true;
                this.chestplateModel.leftArm.visible = true;
                ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.chestplateModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_addition.png")), dyeable);
            }

            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
            if (capeDataComponent != null) {
                if (capeDataComponent.enabled()) {
                    contextModel.copyBipedStateTo(capeModel);
                    capeModel.setVisible(false);
                    capeModel.body.visible = true;
                    capeModel.rightArm.visible = true;
                    capeModel.leftArm.visible = true;
                    capeModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(), (float) entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), contextModel.head.yaw, contextModel.head.roll);
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/" + capeDataComponent.cape() + ".png"), false);
                }
            }
        }
    }
}
