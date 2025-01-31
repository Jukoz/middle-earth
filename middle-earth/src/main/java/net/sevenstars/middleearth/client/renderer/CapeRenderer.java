package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.model.equipment.chest.capes.CloakCapeModel;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ModArmorModels;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class CapeRenderer implements ArmorRenderer {

    private ChestplateAddonModel capeModel;

    public CapeRenderer() {
    }

    static void renderDyeableCape(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean chestplate) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (chestplate){
            color =  ColorHelper.fullAlpha(stack.get(ModDataComponentTypes.CAPE_DATA).capeColor());
        } else {
            color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        this.capeModel = new CloakCapeModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));

        if (slot == EquipmentSlot.CHEST) {
            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

            if (capeDataComponent != null) {
                this.capeModel = ModArmorModels.ModCapePairedModels.valueOf(capeDataComponent.cape().getName().toUpperCase()).getModel().getUnarmoredModel();
                contextModel.copyTransforms(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;
                this.capeModel.setAngles(bipedEntityRenderState);

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
}
