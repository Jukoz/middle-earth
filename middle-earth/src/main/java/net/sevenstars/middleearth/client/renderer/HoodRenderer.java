package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.CloakHoodModel;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
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

public class HoodRenderer implements ArmorRenderer {

    private CustomHelmetModel customHelmetModel;
    private HelmetAddonModel hoodModel;

    public HoodRenderer() {
    }


    static void renderDyeableHood(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean helmet) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (helmet){
            color =  ColorHelper.fullAlpha(stack.get(ModDataComponentTypes.HOOD_DATA).hoodColor());
        } else {
            color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        this.hoodModel = new CloakHoodModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        if (slot == EquipmentSlot.HEAD) {
            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null) {
                Identifier texture;
                if (hoodDataComponent.down()){
                    texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + "_down.png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getUnarmoredDownModel();
                } else {
                    texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + ".png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getUnarmoredModel();
                }
                contextModel.copyTransforms(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                if (ModDyeablePieces.dyeableHoods.containsKey(hoodDataComponent.getHood())) {
                    renderDyeableHood(matrices, vertexConsumers, light, stack, hoodModel, texture, false);
                    if (ModDyeablePieces.dyeableHoods.get(hoodDataComponent.hood())){
                        ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, hoodModel, Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, hoodModel, texture);
                }
            }
        }
    }
}

