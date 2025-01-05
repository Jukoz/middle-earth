package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.capes.CloakCapeModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.items.armor.CapeChestplateItem;
import net.jukoz.me.item.utils.armor.ModArmorModels;
import net.jukoz.me.item.utils.armor.ModDyeablePieces;
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
import net.minecraft.util.math.ColorHelper;

import java.util.Objects;

public class CapeRenderer implements ArmorRenderer {

    private ChestplateAddonModel<LivingEntity> capeModel;

    public CapeRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));

        if (slot == EquipmentSlot.CHEST) {
            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

            if (capeDataComponent != null) {
                this.capeModel = ModArmorModels.ModCapePairedModels.valueOf(capeDataComponent.cape().getName().toUpperCase()).getModel().getUnarmoredModel();
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;
                this.capeModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(), (float) entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), contextModel.head.yaw, contextModel.head.roll);

                if (ModDyeablePieces.dyeableCapes.containsKey(capeDataComponent.getCape())) {
                    renderDyeableCape(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + ".png"), false);
                    if (ModDyeablePieces.dyeableCapes.get(capeDataComponent.cape())){
                        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + "_overlay.png"));
                    }
                } else {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + ".png"));
                }
            }
        }
    }

    static void renderDyeableCape(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean chestplate) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (chestplate){
            color =  ColorHelper.Argb.fullAlpha(stack.get(ModDataComponentTypes.CAPE_DATA).capeColor());
        } else {
            color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }
}
