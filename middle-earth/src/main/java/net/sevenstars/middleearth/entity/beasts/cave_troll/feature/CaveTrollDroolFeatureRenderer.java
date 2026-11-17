package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollDroolFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_green_drooling.png");
    public CaveTrollDroolFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(state.tameness < 50 && state.isTame) {
            RenderUtil.renderCutoutTexture(this.getContextModel(), state, matrices, queue, TEXTURE, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
