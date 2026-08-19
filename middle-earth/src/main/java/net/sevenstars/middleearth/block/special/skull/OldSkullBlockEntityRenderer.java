package net.sevenstars.middleearth.block.special.skull;

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
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@OnlyIn(Dist.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity> {
    private static final Material MATERIAL = new Material(
            TextureAtlas.LOCATION_BLOCKS,
            MiddleEarth.of("model/old_skull"));

    private final ModelPart skull;

    public OldSkullBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        skull = context.bakeLayer(EntityModelLayersME.OLD_SKULL).getChild("skull");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(8.0F, 24.0F, -8.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void render(OldSkullBlockEntity entity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffers, int packedLight, int packedOverlay) {
        float yaw = RotationSegment.convertToDegrees(entity.getBlockState().getValue(OldSkullBlock.ROTATION));
        VertexConsumer consumer = MATERIAL.buffer(buffers, RenderType::entityCutout);

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        skull.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
