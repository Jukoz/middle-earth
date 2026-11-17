package net.sevenstars.middleearth.features.cooking.block.crockpot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class CrockpotEntityRenderer implements BlockEntityRenderer<CrockpotBlockEntity, CrockpotEntityRenderer.State> {
    private static final float SIZE_FACTOR = 0.25f;
    private static final SpriteIdentifier WATER_SPRITE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/water_still"));

    private final SpriteHolder spriteHolder;

    public CrockpotEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.spriteHolder = context.spriteHolder();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void updateRenderState(CrockpotBlockEntity entity, State state, float tickProgress, Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(entity, state, tickProgress, cameraPos, crumblingOverlay);
        state.hasOutput = entity.hasOutput();
        state.liquidTopLevel = entity.getLiquidTopLevel();
    }

    @Override
    public void render(State state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (!state.hasOutput) return;

        matrices.push();
        matrices.translate(0, state.liquidTopLevel, 0); // 0.0625 per pixel

        Sprite waterSprite = this.spriteHolder.getSprite(WATER_SPRITE_ID);
        float minU = MathHelper.lerp(SIZE_FACTOR, waterSprite.getMinU(), waterSprite.getMaxU());
        float maxU = MathHelper.lerp(1 - SIZE_FACTOR, waterSprite.getMinU(), waterSprite.getMaxU());
        float minV = MathHelper.lerp(SIZE_FACTOR, waterSprite.getMinV(), waterSprite.getMaxV());
        float maxV = MathHelper.lerp(1 - SIZE_FACTOR, waterSprite.getMinV(), waterSprite.getMaxV());
        int light = state.lightmapCoordinates;
        int overlay = OverlayTexture.DEFAULT_UV;

        queue.submitCustom(matrices, RenderLayers.translucentMovingBlock(), (entry, consumer) -> {
            consumer.vertex(entry, SIZE_FACTOR, 0, SIZE_FACTOR).color(50, 80, 240, 190).texture(minU, minV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry, SIZE_FACTOR, 0, 1 - SIZE_FACTOR).color(50, 80, 190, 190).texture(minU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry, 1 - SIZE_FACTOR, 0, 1 - SIZE_FACTOR).color(50, 80, 240, 190).texture(maxU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry, 1 - SIZE_FACTOR, 0, SIZE_FACTOR).color(50, 80, 240, 190).texture(maxU, minV).light(light).overlay(overlay).normal(1, 1, 1);
        });

        matrices.pop();
    }

    public static class State extends BlockEntityRenderState {
        public boolean hasOutput;
        public float liquidTopLevel;
    }
}
