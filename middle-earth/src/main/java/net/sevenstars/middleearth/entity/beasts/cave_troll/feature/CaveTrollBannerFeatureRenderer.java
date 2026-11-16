package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.renderer.handheld.HeldBannerModelRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

/** A banner on a pole standing on the rear of the saddle platform, where the rider sits. */
public class CaveTrollBannerFeatureRenderer extends FeatureRenderer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    // Pole base on the shelf at the rear of the saddle platform, in the body bone's model space (pixels)
    private static final float BASE_X = 0.5f;
    private static final float BASE_Y = -56.0f;
    private static final float BASE_Z = 13.5f;
    private static final float SCALE = 0.45f;

    private final HeldBannerEntityModel model;

    public CaveTrollBannerFeatureRenderer(FeatureRendererContext<CaveTrollEntityRenderState, CaveTrollEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.model = new HeldBannerEntityModel(loader.getModelPart(MiddleEarthClient.HELD_BANNER_LAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(!state.hasBanner) {
            return;
        }

        matrices.push();
        // Follow the animated body so the banner moves with the platform
        ModelPart root = this.getContextModel().getRootPart().getChild("root");
        ModelPart bodyNoLegs = root.getChild("body_no_legs");
        root.applyTransform(matrices);
        bodyNoLegs.applyTransform(matrices);
        bodyNoLegs.getChild("body_no_limbs").applyTransform(matrices);
        matrices.translate(BASE_X / 16.0f, BASE_Y / 16.0f, BASE_Z / 16.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f)); // Pattern faces the back
        matrices.scale(SCALE, SCALE, SCALE);

        SpriteIdentifier sprite = ModelBaker.BANNER_BASE;
        VertexConsumer consumer = sprite.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(sprite.getAtlasId()), false, false));
        this.model.getPole().render(matrices, consumer, light, OverlayTexture.DEFAULT_UV);
        HeldBannerModelRenderer.renderCanvas(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, this.model.getBanner(), sprite, false, state.bannerColor, state.bannerPatterns, false, false);
        matrices.pop();
    }
}
