package net.sevenstars.middleearth.entity.projectile.spear;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer<T extends SpearEntity> extends EntityRenderer<T> {
    private static final float MIN_DISTANCE = 12.25F;
    private static final float SCALE = 1.0F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public SpearEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = SCALE * scale;
        this.lit = lit;
    }

    public SpearEntityRenderer(EntityRendererFactory.Context context) {
        this(context, 1.0F, false);
    }

    protected int getBlockLight(T entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);
    }

    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        float cosYaw = (float) Math.cos(Math.toRadians(entity.getYaw()));
        float sinYaw = (float) Math.sin(Math.toRadians(entity.getYaw()));
        matrices.translate(sinYaw * -1.3D, 1.55D * (entity.getPitch() / -90), cosYaw * -1.3D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));


        matrices.scale(scale, scale, scale);

        ItemStack itemStack = entity.getTrackedItemStackData();
        if(itemStack == null) entity.getDefaultItemStack();
        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
