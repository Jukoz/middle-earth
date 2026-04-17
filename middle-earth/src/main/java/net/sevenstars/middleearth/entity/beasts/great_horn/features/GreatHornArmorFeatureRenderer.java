package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class GreatHornArmorFeatureRenderer extends FeatureRenderer<GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public GreatHornArmorFeatureRenderer(FeatureRendererContext<GreatHornEntityRenderState, GreatHornModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornArmorModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_ARMOR));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.assetId().isEmpty()) {
            model.setAngles(state);
            String path = "textures/entities/great_horn/feature/" + equippableComponent.assetId().get().getValue().getPath() + ".png";
            boolean dyeable = false;
            if (itemStack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers,
                    RenderLayer.getArmorCutoutNoCull(Identifier.of(MiddleEarth.MOD_ID, path)), itemStack.hasGlint());

            if(dyeable){
                int color = DyedColorComponent.getColor(itemStack, DyedColorComponent.DEFAULT_COLOR);
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
                if(DyeablePiecesME.dyeablePieces.get(itemStack.getItem())) {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, itemStack, model,
                            Identifier.of(MiddleEarth.MOD_ID, path.replaceAll(".png", "_overlay.png")));
                }
            } else {
                model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
            }
        }
    }
}
