package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.CloakHoodModel;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ModArmorModels;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
import net.sevenstars.middleearth.recipe.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class HelmetArmorRenderer implements ArmorRenderer {

    private CustomHelmetModel customHelmetModel;
    private HelmetAddonModel hoodModel;
    private HelmetAddonModel helmetModel;

    public HelmetArmorRenderer() {
    }

    public HelmetArmorRenderer(HelmetAddonModel helmetModel) {
        this.helmetModel = helmetModel;
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        this.customHelmetModel = new CustomHelmetModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_HELMET));
        this.hoodModel = new CloakHoodModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyTransforms(customHelmetModel);
            customHelmetModel.setVisible(false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            if(stack.isIn(ModTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customHelmetModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.helmetModel != null) {
                contextModel.copyTransforms(this.helmetModel);
                this.helmetModel.setVisible(false);
                this.helmetModel.head.visible = true;
                this.helmetModel.setAngles(bipedEntityRenderState);
                if(texture.contains("_helmet.png")){
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
                } else {
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null) {
                Identifier textureHood;
                if (hoodDataComponent.down()){
                    textureHood = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + "_down.png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getArmoredDownModel();
                } else {
                    textureHood = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + ".png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getArmoredModel();
                }
                contextModel.copyTransforms(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                if (ModDyeablePieces.dyeableHoods.containsKey(hoodDataComponent.getHood())) {
                    HoodRenderer.renderDyeableHood(matrices, vertexConsumers, light, stack, hoodModel, textureHood, true);
                    if (ModDyeablePieces.dyeableHoods.get(hoodDataComponent.hood())){
                        ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, hoodModel, Identifier.of(MiddleEarth.MOD_ID, textureHood.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, hoodModel, textureHood);
                }
            }
        }
    }
}
