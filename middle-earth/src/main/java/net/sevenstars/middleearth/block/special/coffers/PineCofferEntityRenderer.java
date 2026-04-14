package net.sevenstars.middleearth.block.special.coffers;

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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class PineCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final String BASE = "bottom";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart bottom;

    public PineCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.PINE_COFFER);
        this.bottom = modelPart.getChild(BASE);
        this.lid = modelPart.getChild(LID);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 23)
                .cuboid(-8.0F, -2.0F, -14.0F, 16.0F, 2.0F, 15.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 10.0F, 7.0F));

        ModelPartData bottom = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(48, 29).
                cuboid(7.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(3, 29).cuboid(7.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(3, 45).cuboid(-8.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(47, 45).cuboid(-8.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 41).cuboid(-8.0F, -14.0F, -6.0F, 16.0F, 10.0F, 13.0F, new Dilation(0.0F))
                .uv(14, 5).cuboid(8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .uv(30, 5).cuboid(-8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bottom.addChild("cube_r1", ModelPartBuilder.create().uv(14, 0)
                .mirrored().cuboid(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .mirrored(false), ModelTransform.of(-8.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData cube_r2 = bottom.addChild("cube_r2", ModelPartBuilder.create().uv(14, 0)
                .cuboid(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(8.0F, -12.0F, 1.0F, 0.0F, 0.0F, -0.3491F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.of('/', "model", "pine_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        PineCofferBlock chest = (PineCofferBlock)block;

        DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> properties;
        properties = chest.getBlockEntitySource(blockState, world, entity.getPos(), true);
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
        bottom.render(matrices, vertexConsumer, light, overlay);

        matrices.pop();
    }
}
