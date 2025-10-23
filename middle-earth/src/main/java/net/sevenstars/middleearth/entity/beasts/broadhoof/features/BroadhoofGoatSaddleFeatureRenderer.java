package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

public class BroadhoofGoatSaddleFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private final BroadhoofGoatSaddleModel model;

    public BroadhoofGoatSaddleFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);

        this.model = new BroadhoofGoatSaddleModel(loader.getModelPart(ModEntityModelLayers.BROADHOOF_GOAT_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(Identifier.of(MiddleEarth.MOD_ID, "textures/entities/broadhoof_goat/feature/broadhoof_goat_saddle.png")), itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
