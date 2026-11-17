package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

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
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollSaddleFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_platform.png");
    private final CaveTrollSaddleModel model;

    public CaveTrollSaddleFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);

        model = new CaveTrollSaddleModel(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, TEXTURE, itemStack.hasGlint(), light);
        }
    }
}
