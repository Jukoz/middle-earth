package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
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
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

import java.util.function.Function;

// Draws the banners, see CaveTrollBannerModel
public class CaveTrollBannerFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    // How visible the banners are to a rider in the over the shoulder view
    private static final float FADED_OPACITY = 0.2f;

    private final CaveTrollBannerModel poles;
    private final CaveTrollBannerModel flags;
    private final SpriteHolder spriteHolder;

    public CaveTrollBannerFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> context, LoadedEntityModels loader, SpriteHolder spriteHolder) {
        super(context);
        this.poles = CaveTrollBannerModel.poles(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_BANNER));
        this.flags = CaveTrollBannerModel.flags(loader.getModelPart(EntityModelLayersME.CAVE_TROLL_BANNER));
        this.spriteHolder = spriteHolder;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(!state.hasBanner) {
            return;
        }

        submit(matrices, queue, light, state, this.poles, ModelBaker.BANNER_BASE, this.poles::getLayer, -1, state.outlineColor);

        // Same layering as a vanilla banner, minus the plain base when see-through as it would stack up the opacity
        if(!state.fadeBanner) {
            submit(matrices, queue, light, state, this.flags, ModelBaker.BANNER_BASE, RenderLayers::entitySolid, -1, state.outlineColor);
        }
        submit(matrices, queue, light, state, this.flags, TexturedRenderLayers.BANNER_BASE, RenderLayers::entityNoOutline, state.bannerColor.getEntityColor(), 0);
        for(int i = 0; i < 16 && i < state.bannerPatterns.layers().size(); i++) {
            BannerPatternsComponent.Layer layer = state.bannerPatterns.layers().get(i);
            submit(matrices, queue, light, state, this.flags, TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()), RenderLayers::entityNoOutline, layer.color().getEntityColor(), 0);
        }
    }

    // Faded banners all go see-through with no outline, whatever layer they'd use otherwise
    private void submit(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, CaveTrollEntityRenderState state,
                        CaveTrollBannerModel model, SpriteIdentifier sprite, Function<Identifier, RenderLayer> layer, int color, int outlineColor) {
        boolean faded = state.fadeBanner;
        queue.submitModel(model, state, matrices, sprite.getRenderLayer(faded ? RenderLayers::entityTranslucent : layer), light, OverlayTexture.DEFAULT_UV,
                faded ? withOpacity(color, FADED_OPACITY) : color, this.spriteHolder.getSprite(sprite), faded ? 0 : outlineColor, null);
    }

    private static int withOpacity(int color, float opacity) {
        return ((int)(opacity * 255.0f) << 24) | (color & 0xFFFFFF);
    }
}
