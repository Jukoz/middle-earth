package net.sevenstars.middleearth.entity.projectile.spear;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity, SpearEntityRenderState> {
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

    @Override
    public SpearEntityRenderState createRenderState() {
        return new SpearEntityRenderState();
    }

    @Override
    protected int getBlockLight(SpearEntity entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);

    }

    //TODO fix all this
    @Override
    public void render(SpearEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        /*
        matrices.push();
        float cosYaw = (float) Math.cos(Math.toRadians(state.getYaw()));
        float sinYaw = (float) Math.sin(Math.toRadians(state.getYaw()));
        matrices.translate(sinYaw * -1.3D, 1.55D * (state.getPitch() / -90), cosYaw * -1.3D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, state.prevYaw, state.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, state.prevPitch, state.getPitch())));

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));


        matrices.scale(scale, scale, scale);

        ItemStack itemStack = state.getTrackedItemStackData();
        if(itemStack == null) state.getDefaultItemStack();
        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();

        */
        super.render(state, matrices, vertexConsumers, light);
    }
}
