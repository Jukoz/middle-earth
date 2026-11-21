package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

// Draws the banners, see CaveTrollBannerModel
public class CaveTrollBannerFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    // How visible the banners are to a rider in the over the shoulder view
    private static final float FADED_OPACITY = 0.2f;

    private final CaveTrollBannerModel poles;
    private final CaveTrollBannerModel flags;
    private final SpriteHolder spriteHolder;

    public CaveTrollBannerFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> context, LoadedEntityModels loader, SpriteHolder spriteHolder) {
        super(context);
        this.poles = new CaveTrollBannerModel(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_BANNER), false);
        this.flags = new CaveTrollBannerModel(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_BANNER), true);
        this.spriteHolder = spriteHolder;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(!state.hasBanner) {
            return;
        }

        SpriteIdentifier sprite = ModelBaker.BANNER_BASE;

        if(state.fadeBanner) {
            queue.submitModel(this.poles, state, matrices, sprite.getRenderLayer(RenderLayers::entityTranslucent), light, OverlayTexture.DEFAULT_UV,
                    withOpacity(0xFFFFFFFF, FADED_OPACITY), this.spriteHolder.getSprite(sprite), 0, null);
        }
        else {
            queue.submitModel(this.poles, state, matrices, this.poles.getLayer(sprite.getAtlasId()), light, OverlayTexture.DEFAULT_UV,
                    -1, this.spriteHolder.getSprite(sprite), state.outlineColor, null);
        }

        // Same layering as a vanilla banner, minus the plain base when see-through as it would stack up the opacity
        if(!state.fadeBanner) {
            queue.submitModel(this.flags, state, matrices, sprite.getRenderLayer(RenderLayers::entitySolid), light, OverlayTexture.DEFAULT_UV,
                    -1, this.spriteHolder.getSprite(sprite), state.outlineColor, null);
        }
        renderLayer(matrices, queue, light, state, TexturedRenderLayers.BANNER_BASE, state.bannerColor.getEntityColor());
        for(int i = 0; i < 16 && i < state.bannerPatterns.layers().size(); i++) {
            BannerPatternsComponent.Layer layer = state.bannerPatterns.layers().get(i);
            renderLayer(matrices, queue, light, state, TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()), layer.color().getEntityColor());
        }
    }

    private void renderLayer(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state, SpriteIdentifier sprite, int color) {
        if(state.fadeBanner) {
            queue.submitModel(this.flags, state, matrices, sprite.getRenderLayer(RenderLayers::entityTranslucent), light, OverlayTexture.DEFAULT_UV,
                    withOpacity(color, FADED_OPACITY), this.spriteHolder.getSprite(sprite), 0, null);
        }
        else {
            queue.submitModel(this.flags, state, matrices, sprite.getRenderLayer(RenderLayers::entityNoOutline), light, OverlayTexture.DEFAULT_UV,
                    color, this.spriteHolder.getSprite(sprite), 0, null);
        }
    }

    private static int withOpacity(int color, float opacity) {
        return ((int)(opacity * 255.0f) << 24) | (color & 0xFFFFFF);
    }
}
