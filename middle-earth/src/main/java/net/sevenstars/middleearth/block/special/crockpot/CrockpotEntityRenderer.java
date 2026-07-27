package net.sevenstars.middleearth.block.special.crockpot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CrockpotEntityRenderer implements BlockEntityRenderer<CrockpotBlockEntity> {
    private static final float SIZE_FACTOR = 0.25f;
    private static Material waterSpriteId = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/water_still"));

    public CrockpotEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrockpotBlockEntity entity, float tickProgress, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        int color = 4161734;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;

        matrices.pushPose();
        if(entity.hasOutput()) {
            float liquidTopLevel = entity.getLiquidTopLevel();
            matrices.translate(0, liquidTopLevel, 0); // 0.0625 per pixel

            VertexConsumer consumer = vertexConsumers.getBuffer(RenderType.translucentMovingBlock());
            //matrices.peek();

            TextureAtlasSprite waterSprite = waterSpriteId.sprite();

            PoseStack.Pose entry = matrices.last();

            float minU = waterSprite.getU0();
            float minV = waterSprite.getV0();
            float maxU = waterSprite.getU1();
            float maxV = waterSprite.getV1();

            float tempMin = minU;
            minU = Mth.lerp(SIZE_FACTOR, minU, maxU);
            maxU = Mth.lerp(1 - SIZE_FACTOR, tempMin, maxU);
            tempMin = minV;
            minV = Mth.lerp(SIZE_FACTOR, minV, maxV);
            maxV = Mth.lerp(1 - SIZE_FACTOR, tempMin, maxV);

            consumer.addVertex(entry, SIZE_FACTOR, 0, SIZE_FACTOR)               .setColor(50, 80, 240, 190).setUv(minU, minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
            consumer.addVertex(entry, SIZE_FACTOR, 0, 1 - SIZE_FACTOR)  .setColor(50, 80, 190, 190).setUv(minU, maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
            consumer.addVertex(entry, 1 - SIZE_FACTOR, 0, 1 - SIZE_FACTOR)        .setColor(50, 80, 240, 190).setUv(maxU, maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
            consumer.addVertex(entry, 1 - SIZE_FACTOR, 0, SIZE_FACTOR)               .setColor(50, 80, 240, 190).setUv(maxU, minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
        }

        matrices.popPose();
    }
}
