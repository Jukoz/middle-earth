package net.jukoz.me.block.special.reinforcedChest;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class ReinforcedChestEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private final ModelPart chestLid;
    private final ModelPart chestBase;
    private final ModelPart chestLatch;

    public ReinforcedChestEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(ModEntityModelLayers.REINFORCED_CHEST);
        this.chestBase = modelPart.getChild(BASE);
        this.chestLid = modelPart.getChild(LID);
        this.chestLatch = modelPart.getChild(LATCH);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 18).cuboid(-8.0F, 0.0F, -6.0F, 16.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 1).cuboid(-2.0F, -3.0F, -13.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 6.0F));
        modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -12.0F, 16.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 6.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }


    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE,
                Identifier.of(MiddleEarth.MOD_ID, "model/reinforced_chest"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        ReinforcedChestBlock chest = (ReinforcedChestBlock)block;

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 0.0D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));

        chestLatch.pitch = chestLid.pitch = +(g * 1.5707964f);
        chestLid.render(matrices, vertexConsumer, light, overlay);
        chestLatch.render(matrices, vertexConsumer, light, overlay);
        chestBase.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
