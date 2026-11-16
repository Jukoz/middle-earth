package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

/** Draws the banner rig, which hangs off the harness platform bone, see {@link CaveTrollBannerModel}. */
public class CaveTrollBannerFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private final CaveTrollBannerModel model;

    public CaveTrollBannerFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.model = new CaveTrollBannerModel(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_BANNER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(!state.hasBanner) {
            return;
        }

        this.model.setAngles(state);
        SpriteIdentifier sprite = ModelBaker.BANNER_BASE;

        this.model.showPoles();
        VertexConsumer poleConsumer = sprite.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(sprite.getAtlasId()), false, false));
        this.model.render(matrices, poleConsumer, light, OverlayTexture.DEFAULT_UV);

        // The same layering as the vanilla banner: a plain base, the dyed base, then each pattern in its colour
        this.model.showFlags();
        this.model.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, false, false), light, OverlayTexture.DEFAULT_UV);
        renderLayer(matrices, vertexConsumers, light, TexturedRenderLayers.BANNER_BASE, state.bannerColor.getEntityColor());
        for(int i = 0; i < 16 && i < state.bannerPatterns.layers().size(); i++) {
            BannerPatternsComponent.Layer layer = state.bannerPatterns.layers().get(i);
            renderLayer(matrices, vertexConsumers, light, TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()), layer.color().getEntityColor());
        }
    }

    private void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SpriteIdentifier sprite, int color) {
        this.model.render(matrices, sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, OverlayTexture.DEFAULT_UV, color);
    }
}
