package net.sevenstars.middleearth.entity.projectile.spear;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {
    private static final float MIN_DISTANCE = 12.25F;
    private static final float SCALE = 1.0F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public SpearEntityRenderer(EntityRendererProvider.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = SCALE * scale;
        this.lit = lit;
    }

    public SpearEntityRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(SpearEntity entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLightLevel(entity, pos);

    }

    @Override
    public void render(SpearEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        float cosYaw = (float)Math.cos(Math.toRadians(entity.getYRot()));
        float sinYaw = (float)Math.sin(Math.toRadians(entity.getYRot()));
        matrices.translate(sinYaw * -1.3D, 1.55D * (entity.getXRot() / -90), cosYaw * -1.3D);
        matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(tickDelta, entity.yRotO, entity.getYRot()) - 90.0F));
        matrices.mulPose(Axis.ZP.rotationDegrees(-90.0F));
        matrices.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(tickDelta, entity.xRotO, entity.getXRot())));
        matrices.mulPose(Axis.YP.rotationDegrees(90.0F));
        matrices.scale(scale, scale, scale);

        ItemStack itemStack = entity.getTrackedItemStackData();
        if (itemStack == null) {
            itemStack = entity.getDefaultPickupItem();
        }
        this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light,
                OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.level(), entity.getId());
        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(SpearEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
