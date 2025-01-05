package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;
import net.jukoz.me.client.model.equipment.head.hoods.CloakHoodModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.armor.HoodHelmetItem;
import net.jukoz.me.item.utils.armor.ModArmorModels;
import net.jukoz.me.item.utils.armor.ModDyeablePieces;
import net.jukoz.me.recipe.ModTags;
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

    private CustomHelmetModel<LivingEntity> customHelmetModel;
    private HelmetAddonModel<LivingEntity> hoodModel;

    public HoodRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

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
                contextModel.copyBipedStateTo(hoodModel);
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

    static void renderDyeableHood(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean helmet) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (helmet){
            color =  ColorHelper.Argb.fullAlpha(stack.get(ModDataComponentTypes.HOOD_DATA).hoodColor());
        } else {
            color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }
}

