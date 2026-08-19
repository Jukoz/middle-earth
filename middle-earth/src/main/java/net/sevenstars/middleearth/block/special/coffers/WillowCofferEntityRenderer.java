package net.sevenstars.middleearth.block.special.coffers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class WillowCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";
    private static final Material MATERIAL =
            new Material(Sheets.CHEST_SHEET, MiddleEarth.ofPath("model", "willow_coffer"));

    private final ModelPart body;
    private final ModelPart lid;

    public WillowCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.bakeLayer(EntityModelLayersME.WILLOW_COFFER);
        this.body = modelPart.getChild(BODY);
        this.lid = modelPart.getChild(LID);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(BODY, CubeListBuilder.create()
                        .texOffs(0, 36).addBox(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 36).addBox(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 36).addBox(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 44).addBox(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0).addBox(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-7.0F, 24.0F, -5.0F));

        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create()
                        .texOffs(1, 21).addBox(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.0F, 6.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        VertexConsumer vertexConsumer = MATERIAL.buffer(vertexConsumers, RenderType::entityCutout);

        Level world = entity.getLevel();
        BlockState blockState = world != null ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        WillowCofferBlock chest = (WillowCofferBlock)block;

        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> properties = chest.combine(blockState, world, entity.getBlockPos(), true);
        float g = properties.apply(ReinforcedChestBlock.opennessCombiner(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.pushPose();
        float rotation = blockState.getValue(ChestBlock.FACING).toYRot();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.mulPose(Axis.YP.rotationDegrees(rotation - 180));

        lid.xRot = g * -1.5707964f;
        lid.render(matrices, vertexConsumer, light, overlay);
        body.render(matrices, vertexConsumer, light, overlay);

        matrices.popPose();
    }
}
