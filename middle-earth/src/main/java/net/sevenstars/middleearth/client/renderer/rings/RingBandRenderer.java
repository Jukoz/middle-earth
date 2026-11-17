package net.sevenstars.middleearth.client.renderer.rings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

/**
 * Draws the worn ring as a small finger ring on the back of the main hand, with a tiny gem set in it.
 * It sits just outside the sleeve layer, or outside the armour when a chestplate is worn.
 * Parts are built in arm space: the arm's own transform is applied before rendering them.
 */
@Environment(EnvType.CLIENT)
public class RingBandRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entity/ring_band.png");
    private static final float BARE = 0.27F;
    private static final float ARMOURED = 1.02F;
    private static final ModelPart[] PARTS = new ModelPart[4];

    public RingBandRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context) {
        super(context);
    }

    public static void register() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, helper, context) -> {
            if (renderer instanceof PlayerEntityRenderer playerRenderer) {
                helper.register(new RingBandRenderer(playerRenderer));
            }
        });
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state,
                       float limbAngle, float limbDistance) {
        RingBand band = ((RingBandRenderState) state).middleearth$getRingBand();
        if (band == null || state.invisible) {
            return;
        }
        PlayerEntityModel model = this.getContextModel();
        ModelPart arm = state.mainArm == Arm.RIGHT ? model.rightArm : model.leftArm;
        boolean slim = state.skinTextures.model() == SkinTextures.Model.SLIM;
        renderOnArm(matrices, vertexConsumers, light, band, arm, state.mainArm, slim, !state.equippedChestStack.isEmpty());
    }

    /** Also used for the first-person arm. */
    public static void renderOnArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, @Nullable RingBand band,
                                   ModelPart arm, Arm side, boolean slim, boolean armoured) {
        if (band == null) {
            return;
        }
        ModelPart part = part(slim, armoured);
        ModelPart bandPart = part.getChild(side == Arm.RIGHT ? "right_band" : "left_band");
        ModelPart gemPart = part.getChild(side == Arm.RIGHT ? "right_gem" : "left_gem");
        int lit = band.glow() > 0 ? LightmapTextureManager.pack(
                Math.max(LightmapTextureManager.getBlockLightCoordinates(light), band.glow()),
                LightmapTextureManager.getSkyLightCoordinates(light)) : light;
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        matrices.push();
        arm.applyTransform(matrices);
        bandPart.render(matrices, consumer, lit, OverlayTexture.DEFAULT_UV, band.bandColor());
        if (band.hasGem()) {
            gemPart.render(matrices, consumer, lit, OverlayTexture.DEFAULT_UV, band.gemColor());
        }
        matrices.pop();
    }

    private static ModelPart part(boolean slim, boolean armoured) {
        int index = (slim ? 1 : 0) + (armoured ? 2 : 0);
        if (PARTS[index] == null) {
            PARTS[index] = texturedModelData(slim, armoured ? ARMOURED : BARE).createModel();
        }
        return PARTS[index];
    }

    /**
     * Arm-local coordinates match the player arm cuboids (arm ends at y 10). The ring is a finger-sized sliver on the
     * back of the hand: one pixel wide over the ring finger, 0.6 tall just above the fingertips, with a half-pixel gem.
     */
    private static TexturedModelData texturedModelData(boolean slim, float skin) {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        float rightFace = (slim ? -2.0F : -3.0F) - skin;
        float leftFace = (slim ? 2.0F : 3.0F) + skin;
        float thick = 0.3F;
        float top = 8.9F;
        float tall = 0.6F;
        float fingerZ = 0.25F;
        root.addChild("right_band", ModelPartBuilder.create().uv(0, 0)
                .cuboid(rightFace - thick, top, fingerZ, thick, tall, 1.0F), ModelTransform.NONE);
        root.addChild("left_band", ModelPartBuilder.create().uv(0, 0)
                .cuboid(leftFace, top, fingerZ, thick, tall, 1.0F), ModelTransform.NONE);
        float gem = 0.5F;
        float gemY = top + (tall - gem) / 2.0F;
        float gemZ = fingerZ + (1.0F - gem) / 2.0F;
        root.addChild("right_gem", ModelPartBuilder.create().uv(16, 0)
                .cuboid(rightFace - thick - gem * 0.6F, gemY, gemZ, gem * 0.6F, gem, gem), ModelTransform.NONE);
        root.addChild("left_gem", ModelPartBuilder.create().uv(16, 0)
                .cuboid(leftFace + thick, gemY, gemZ, gem * 0.6F, gem, gem), ModelTransform.NONE);
        return TexturedModelData.of(data, 32, 16);
    }

    /** Render-state access for the band, filled when the player's render state is updated. */
    public interface RingBandRenderState {
        @Nullable RingBand middleearth$getRingBand();

        void middleearth$setRingBand(@Nullable RingBand band);
    }
}
