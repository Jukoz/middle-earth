package net.sevenstars.middleearth.block.special.reinforcedChest;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.*;
import net.minecraft.client.model.*;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ReinforcedChestEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private static final Material MATERIAL = new Material(
            Sheets.CHEST_SHEET,
            ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "model/reinforced_chest")
    );
    private final ModelPart chestLid;
    private final ModelPart chestBase;
    private final ModelPart chestLatch;

    public ReinforcedChestEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.bakeLayer(EntityModelLayersME.REINFORCED_CHEST);
        this.chestBase = modelPart.getChild(BASE);
        this.chestLid = modelPart.getChild(LID);
        this.chestLatch = modelPart.getChild(LATCH);
    }


    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, 0.0F, -6.0F, 16.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 1).addBox(-2.0F, -3.0F, -13.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 6.0F));
        modelPartData.addOrReplaceChild(LATCH, CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -12.0F, 16.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 6.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void render(T entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        VertexConsumer vertexConsumer = MATERIAL.buffer(vertexConsumers, RenderType::entityCutout);

        Level world = entity.getLevel();
        BlockState blockState = world != null ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();

        ReinforcedChestBlock chest = (ReinforcedChestBlock)block;

        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> properties;
        properties = chest.combine(blockState, world, entity.getBlockPos(), true);
        float g = properties.apply(ReinforcedChestBlock.opennessCombiner(entity)).get(tickProgress);
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.pushPose();
        float rotation = blockState.getValue(ChestBlock.FACING).toYRot();
        matrices.translate(0.5D, 0.0D, 0.5D);
        matrices.mulPose(Axis.YP.rotationDegrees(-rotation));

        chestLatch.xRot = chestLid.xRot = +(g * 1.5707964f);
        chestLid.render(matrices, vertexConsumer, light, overlay);
        chestLatch.render(matrices, vertexConsumer, light, overlay);
        chestBase.render(matrices, vertexConsumer, light, overlay);

        matrices.popPose();
    }
}
