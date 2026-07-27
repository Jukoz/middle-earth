package net.sevenstars.middleearth.block.special.bellows;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity>  {
    private static final Material[] MATERIALS = {
            new Material(
                    TextureAtlas.LOCATION_BLOCKS,
                    ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "model/bellows/bellows_0")
            ),
            new Material(
                    TextureAtlas.LOCATION_BLOCKS,
                    ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "model/bellows/bellows_1")
            ),
            new Material(
                    TextureAtlas.LOCATION_BLOCKS,
                    ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "model/bellows/bellows_2")
            )
    };
    private final float BELLOW_MAX_ANGLE = 0.72f;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart cavity;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart modelPart = ctx.bakeLayer(EntityModelLayersME.BELLOWS);
        this.bottom = modelPart.getChild("bottom");
        this.top = modelPart.getChild("top");
        this.cavity = modelPart.getChild("cavity");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 12)
                .addBox(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F,
                        new CubeDeformation(0.0F))
                .texOffs(1, 1).mirror().addBox(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 1).mirror().addBox(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 17).addBox(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -9.0F));

        modelPartData.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(18, 12).addBox(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild("cavity", CubeListBuilder.create().texOffs(10, 17).mirror()
                .addBox(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F,
                        new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(modelData, 48, 48);
    }

    @Override
    public void render(BellowsBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Level world = entity.getLevel();
        BlockState blockState = world != null
                ? entity.getBlockState()
                : ModDecorativeBlocks.BELLOWS.defaultBlockState().setValue(BellowsBlock.FACING, Direction.SOUTH);

        float animationProgress = getAnimationProgress(entity);

        float rotation = blockState.getValue(ChestBlock.FACING).toYRot();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.mulPose(Axis.YP.rotationDegrees(-rotation));
        matrices.mulPose(Axis.XP.rotationDegrees(180));

        VertexConsumer vertexConsumer = getSpriteIdentifier(animationProgress).buffer(vertexConsumers, RenderType::entityCutout);

        this.top.xRot = 0.37f + (animationProgress * -BELLOW_MAX_ANGLE);
        this.top.render(matrices, vertexConsumer, light, overlay);
        this.bottom.render(matrices, vertexConsumer, light, overlay);
        this.cavity.render(matrices, vertexConsumer, light, overlay);
    }


    private float getAnimationProgress(BellowsBlockEntity bellowsBlockEntity){
        float animationProgress = 0;
        if (bellowsBlockEntity.pumping){
            animationProgress = bellowsBlockEntity.animationProgress;
            if(animationProgress > (float) BellowsBlockEntity.MAX_TICKS / 2) animationProgress = BellowsBlockEntity.MAX_TICKS - animationProgress;
            animationProgress /= BellowsBlockEntity.MAX_TICKS;
        }
        return animationProgress;
    }

    private Material getSpriteIdentifier(float animationProgress){
        int spriteState = (int) Math.max(0, Math.min(2, animationProgress * 7.5f));
        return MATERIALS[spriteState];
    }
}
