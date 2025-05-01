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
import net.minecraft.util.math.Vec3d;

@Environment(value= EnvType.CLIENT)
public class CrockpotEntityRenderer implements BlockEntityRenderer<CrockpotBlockEntity> {
    private static final Identifier waterTexture = Identifier.ofVanilla("textures/block/water_still.png");
    private static SpriteIdentifier waterSpriteId = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/water_flow"));

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

        float epsilon = 0.001f;

        matrices.push();
        matrices.translate(0, 0.3f, 0);

        //vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(waterTexture, true));
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
        matrices.peek();

        float sizeFactor = 0.25f;

        Sprite waterSprite = waterSpriteId.getSprite();

        MatrixStack.Entry entry = matrices.peek();
        //consumer.quad(entry, consumer, color);
        //consumer.vertex(entry, 0, 0.3f, 0).color(color).texture(5, 5).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(entry, 0.0F, 1.0F, 0.0F);

        // Morgul Alchemy
        //consumer.vertex(entry, sizeFactor, 0, 1 - sizeFactor).color(255, 50, 80, 190).texture(0, 0).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, 1 - sizeFactor).color(255, 50, 80, 190).texture(0, 1).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, sizeFactor).color(255, 50, 80, 190).texture(1, 0).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, sizeFactor, 0, sizeFactor).color(255, 50, 80, 190).texture(1, 1).light(light).overlay(overlay).normal(1, 1, 1);

        // Horizontal
        //consumer.vertex(entry, sizeFactor, 0, 1 - sizeFactor).color(50, 80, 240, 190).texture(0, 1).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, 1 - sizeFactor).color(50, 80, 190, 190).texture(1, 1).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, sizeFactor).color(50, 80, 240, 190).texture(1, 0).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, sizeFactor, 0, sizeFactor).color(50, 80, 240, 190).texture(0, 0).light(light).overlay(overlay).normal(1, 1, 1);

        //consumer.vertex(entry, sizeFactor, 0, 1 - sizeFactor).color(50, 80, 240, 190).texture(1, 1).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, 1 - sizeFactor).color(50, 80, 190, 190).texture(0, 1).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, 1 - sizeFactor, 0, sizeFactor).color(50, 80, 240, 190).texture(0, 0).light(light).overlay(overlay).normal(1, 1, 1);
        //consumer.vertex(entry, sizeFactor, 0, sizeFactor).color(50, 80, 240, 190).texture(1, 0).light(light).overlay(overlay).normal(1, 1, 1);

        consumer.vertex(entry, sizeFactor, 0, sizeFactor)               .color(50, 80, 240, 190).texture(waterSprite.getMinU(), waterSprite.getMinV()).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, sizeFactor, 0, 1 - sizeFactor)  .color(50, 80, 190, 190).texture(waterSprite.getMinU(), waterSprite.getMaxV()).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, 1 - sizeFactor, 0, 1 - sizeFactor)        .color(50, 80, 240, 190).texture(waterSprite.getMaxU(), waterSprite.getMaxV()).light(light).overlay(overlay).normal(1, 1, 1);
        consumer.vertex(entry, 1 - sizeFactor, 0, sizeFactor)               .color(50, 80, 240, 190).texture(waterSprite.getMaxU(), waterSprite.getMinV()).light(light).overlay(overlay).normal(1, 1, 1);

        matrices.pop();
    }
}
