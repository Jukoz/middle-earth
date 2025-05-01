package net.sevenstars.middleearth.block.special.crockpot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.system.MathUtil;

@Environment(value= EnvType.CLIENT)
public class CrockpotEntityRenderer implements BlockEntityRenderer<CrockpotBlockEntity> {
    private static final Identifier waterTexture = Identifier.ofVanilla("textures/block/water_still.png");
    private static SpriteIdentifier waterSpriteId = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/water_still"));

    public CrockpotEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CrockpotBlockEntity entity, float tickProgress, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        int color = 4161734;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        int alpha = 200;

        matrices.push();
        matrices.translate(0, 0.3f, 0);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
        matrices.peek();

        float sizeFactor = 0.25f;

        Sprite waterSprite = waterSpriteId.getSprite();

        MatrixStack.Entry entry = matrices.peek();

        float minU = waterSprite.getMinU();
        float minV = waterSprite.getMinV();
        float maxU = waterSprite.getMaxU();
        float maxV = waterSprite.getMaxV();

        float tempMin = minU;
        minU = MathHelper.lerp(sizeFactor, minU, maxU);
        maxU = MathHelper.lerp(1 - sizeFactor, tempMin, maxU);
        tempMin = minV;
        minV = MathHelper.lerp(sizeFactor, minV, maxV);
        maxV = MathHelper.lerp(1 - sizeFactor, tempMin, maxV);

        consumer.vertex(entry, sizeFactor, 0, sizeFactor)               .color(50, 80, 240, 190).texture(minU, minV).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, sizeFactor, 0, 1 - sizeFactor)  .color(50, 80, 190, 190).texture(minU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, 1 - sizeFactor, 0, 1 - sizeFactor)        .color(50, 80, 240, 190).texture(maxU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, 1 - sizeFactor, 0, sizeFactor)               .color(50, 80, 240, 190).texture(maxU, minV).light(light).overlay(overlay).normal(1, 1, 1);

        matrices.pop();
    }
}
