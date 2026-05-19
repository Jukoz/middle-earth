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

public class OakCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart body;

    public OakCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OAK_COFFER);
        this.lid = modelPart.getChild(LID);
        this.body = modelPart.getChild(BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild(LID, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 13.0F, 6.0F));

        lid.addChild("lid_r1", ModelPartBuilder.create()
                        .uv(0, 9).cuboid(-12.0F, -3.0F, -16.0F, 12.0F, 3.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.of(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = modelPartData.addChild(BODY, ModelPartBuilder.create()
                        .uv(0, 56).cuboid(-11.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(26, 56).cuboid(-11.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(39, 56).cuboid(-2.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(13, 56).cuboid(-2.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(0, 32).cuboid(-11.0F, -7.0F, -14.0F, 12.0F, 7.0F, 14.0F, new Dilation(0.0F)),
                ModelTransform.of(-7.0F, 20.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        body.addChild("cube_r1", ModelPartBuilder.create()
                        .uv(0, -2).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.0F, -5.0F, -14.0F, -1.5708F, 1.2654F, -1.5708F));

        body.addChild("cube_r2", ModelPartBuilder.create()
                        .uv(0, -2).mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-5.0F, -5.0F, 0.0F, 1.5708F, 1.2654F, 1.5708F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.ofPath( "model", "oak_coffer"));
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        World world = entity.getWorld();
        BlockState blockState = world != null ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        OakCofferBlock chest = (OakCofferBlock)block;

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
