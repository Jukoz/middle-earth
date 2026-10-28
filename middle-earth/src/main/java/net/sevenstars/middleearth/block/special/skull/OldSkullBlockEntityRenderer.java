package net.sevenstars.middleearth.block.special.skull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity> {
    private final ModelPart skull;

    public OldSkullBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OLD_SKULL);
        this.skull = modelPart.getChild("skull");
    }

    @Nullable
    public static SkullBlockEntityModel getModels(LoadedEntityModels models) {
        return new SkullEntityModel(models.getModelPart(EntityModelLayers.SKELETON_SKULL));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData skull = modelPartData.addChild("skull", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 24.0F, -8.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(OldSkullBlockEntity skullBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, Vec3d vec3d) {
        BlockState blockState = skullBlockEntity.getCachedState();
        boolean bl = blockState.getBlock() instanceof WallSkullBlock;
        Direction direction = bl ? blockState.get(WallSkullBlock.FACING) : null;
        int k = bl ? RotationPropertyHelper.fromDirection(direction.getOpposite()) : (Integer)blockState.get(SkullBlock.ROTATION);
        float h = RotationPropertyHelper.toDegrees(k);

        VertexConsumer vertexConsumer = getSpriteIdentifier().getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);

        RenderLayer renderLayer = getRenderLayer();
        renderSkull(direction, h, 0, matrixStack, vertexConsumer, i, skull, renderLayer);
    }

    public static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, MatrixStack matrices,
                                   VertexConsumer vertexConsumers, int light, ModelPart model, RenderLayer renderLayer) {
        matrices.push();
        if (direction == null) {
            matrices.translate(0.5F, 0.0F, 0.5F);
        } else {
            float f = 0.25F;
            matrices.translate(0.5F - direction.getOffsetX() * 0.25F, 0.25F, 0.5F - direction.getOffsetZ() * 0.25F);
        }

        matrices.translate(0, 1.5f, 0);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        model.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    public static RenderLayer getRenderLayer() {
        return RenderLayer.getEntityCutoutNoCullZOffset(Identifier.of(MiddleEarth.MOD_ID, "model/old_skull"));
    }

    private SpriteIdentifier getSpriteIdentifier(){
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                Identifier.of(MiddleEarth.MOD_ID, "model/old_skull"));
    }
}
