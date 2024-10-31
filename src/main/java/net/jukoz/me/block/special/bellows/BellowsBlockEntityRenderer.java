package net.jukoz.me.block.special.bellows;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BellowsBlockEntityRenderer<T extends BellowsBlockEntity> implements BlockEntityRenderer<T>  {
    private final float BELLOW_MAX_ANGLE = 0.72f;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart cavity;

    public BellowsBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModEntityModelLayers.BELLOWS);
        this.bottom = modelPart.getChild("bottom");
        this.top = modelPart.getChild("top");
        this.cavity = modelPart.getChild("cavity");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 12)
                .cuboid(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(10, 12).cuboid(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F,
                        new Dilation(0.0F))
                .uv(1, 1).mirrored().cuboid(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new Dilation(0.0F)).mirrored(false)
                .uv(1, 1).mirrored().cuboid(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new Dilation(0.0F)).mirrored(false)
                .uv(0, 17).cuboid(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F,
                        new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -9.0F));

        modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new Dilation(0.0F))
                .uv(18, 12).cuboid(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F,
                        new Dilation(0.0F)), ModelTransform.of(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));

        modelPartData.addChild("cavity", ModelPartBuilder.create().uv(10, 17).mirrored()
                .cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F,
                        new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 48, 48);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : ModDecorativeBlocks.BELLOWS.getDefaultState().with(BellowsBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        float g = ((BellowsBlockEntity) entity).animationProgress;
        if(g > (float) BellowsBlockEntity.MAX_TICKS / 2) g = BellowsBlockEntity.MAX_TICKS - g;
        g /= BellowsBlockEntity.MAX_TICKS;

        int spriteState = (int) Math.max(0, Math.min(2, g * 7.5f));
        SpriteIdentifier idleBellowsTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                Identifier.of(MiddleEarth.MOD_ID, "model/bellows/bellows_" + spriteState));
        VertexConsumer vertexConsumer = idleBellowsTexture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        top.pitch = 0.37f + (g * -BELLOW_MAX_ANGLE);
        top.render(matrices, vertexConsumer, light, overlay);
        bottom.render(matrices, vertexConsumer, light, overlay);

        cavity.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
