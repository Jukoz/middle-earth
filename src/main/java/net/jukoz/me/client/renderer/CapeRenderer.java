package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.chest.CloakCapeModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.items.CapeChestplateItem;
import net.jukoz.me.item.items.CustomChestplateItem;
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

import java.awt.*;

public class CapeRenderer implements ArmorRenderer {

    private CloakCapeModel<LivingEntity> capeModel;


    public CapeRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));

        CapeChestplateItem item = (CapeChestplateItem)stack.getItem();
        boolean dyeable = false;

        if (slot == EquipmentSlot.CHEST) {
            if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";

            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

            if (capeDataComponent != null) {
                if (capeDataComponent.enabled()) {
                    contextModel.copyBipedStateTo(capeModel);
                    capeModel.setVisible(false);
                    capeModel.body.visible = true;
                    capeModel.rightArm.visible = true;
                    capeModel.leftArm.visible = true;
                    capeModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(), (float) entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), contextModel.head.yaw, contextModel.head.roll);
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/" + capeDataComponent.cape().toLowerCase() + ".png"), false);
                }
            }
        }
    }
}