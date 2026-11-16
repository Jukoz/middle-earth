package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends FeatureRenderer<GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornSaddleModel model;
    private final static String PATH = "textures/entities/great_horn/feature/great_horn_saddle";

    public GreatHornSaddleFeatureRenderer(FeatureRendererContext<GreatHornEntityRenderState, GreatHornModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean blueSaddle = state.blueSaddle;
        String suffix = "";
        if(blueSaddle) suffix = "_blue";
        if(!itemStack.isEmpty()) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, Identifier.of(MiddleEarth.MOD_ID, PATH + suffix + ".png"), itemStack.hasGlint(), light);
        }
    }
}
