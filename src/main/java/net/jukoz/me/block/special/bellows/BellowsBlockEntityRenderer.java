package net.jukoz.me.block.special.bellows;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlock;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
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
public class BellowsBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>  {
    private final ModelPart bottom;
    private final ModelPart top;

    public BellowsBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModEntityModelLayers.BELLOWS);
        this.bottom = modelPart.getChild("bottom");
        this.top = modelPart.getChild("top");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bottom = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 12).cuboid(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(10, 12).cuboid(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 1).mirrored().cuboid(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
                .uv(1, 1).mirrored().cuboid(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
                .uv(10, 17).mirrored().cuboid(-12.0F, -7.0F, 5.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 17).cuboid(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -9.0F));

        ModelPartData top = modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new Dilation(0.0F))
                .uv(18, 12).cuboid(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 48, 48);
    }

    @Override
    public void render(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SpriteIdentifier idleBellowsTexture = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                Identifier.of(MiddleEarth.MOD_ID, "model/bellows/bellows_0"));
        VertexConsumer vertexConsumer = idleBellowsTexture.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : ModDecorativeBlocks.BELLOWS.getDefaultState().with(BellowsBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        BellowsBlock bellows = (BellowsBlock)block;

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> properties;
        //properties = bellows.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g =  1; //properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        //top.pitch = top.pitch = + (g * 1.5707964f);
        top.render(matrices, vertexConsumer, light, overlay);
        bottom.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
