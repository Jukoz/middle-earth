package net.sevenstars.middleearth.entity.beasts.great_horn.features;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends FeatureRenderer<GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornSaddleModel model;
    private final static String PATH = "textures/entities/great_horn/feature/great_horn_saddle";

    public GreatHornSaddleFeatureRenderer(FeatureRendererContext<GreatHornEntityRenderState, GreatHornModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.getModelPart(ModEntityModelLayers.GREAT_HORN_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean blueSaddle = state.blueSaddle;
        String suffix = "";
        if(blueSaddle) suffix = "_blue";
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers,
                    RenderLayer.getArmorCutoutNoCull(Identifier.of(MiddleEarth.MOD_ID, PATH + suffix + ".png")), itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
