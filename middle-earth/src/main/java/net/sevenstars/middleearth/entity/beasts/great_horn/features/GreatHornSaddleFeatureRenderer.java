package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends FeatureRenderer<GreatHornEntityRenderState, GreatHornModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_saddle.png");
    private static final Identifier BLUE_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_saddle_blue.png");
    private final GreatHornSaddleModel model;

    public GreatHornSaddleFeatureRenderer(FeatureRendererContext<GreatHornEntityRenderState, GreatHornModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean blueSaddle = state.blueSaddle;
        if(!itemStack.isEmpty()) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, blueSaddle ? BLUE_TEXTURE : TEXTURE, itemStack.hasGlint(), light);
        }
    }
}
