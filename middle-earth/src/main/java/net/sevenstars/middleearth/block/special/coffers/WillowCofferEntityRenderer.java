package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class WillowCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart body;
    private final ModelPart lid;

    public WillowCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.WILLOW_COFFER);
        this.body = modelPart.getChild(BODY);
        this.lid = modelPart.getChild(LID);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BODY, ModelPartBuilder.create()
                        .uv(0, 36).cuboid(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(16, 36).cuboid(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(32, 36).cuboid(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(0, 44).cuboid(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(1, 0).cuboid(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(-7.0F, 24.0F, -5.0F));

        modelPartData.addChild(LID, ModelPartBuilder.create()
                        .uv(1, 21).cuboid(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 11.0F, 6.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.of('/', "model", "willow_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        WillowCofferBlock chest = (WillowCofferBlock)block;

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
        float g = properties.apply(ReinforcedChestBlock.getAnimationProgressRetriever(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = blockState.get(ChestBlock.FACING).getPositiveHorizontalDegrees();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation - 180));

        lid.pitch = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        body.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
