package net.jukoz.me.entity.nazguls;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class NazgulRenderer extends BipedEntityRenderer<NazgulEntity, NazgulModel<NazgulEntity>> {
    private static final String PATH = "textures/entities/nazgul/nazgul.png";

    public NazgulRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new NazgulModel<>(ctx.getPart(ModEntityModelLayers.NAZGUL)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new ZombieEntityModel(ctx.getPart(EntityModelLayers.ZOMBIE_INNER_ARMOR)), new ZombieEntityModel(ctx.getPart(EntityModelLayers.ZOMBIE_OUTER_ARMOR)), ctx.getModelManager()));
    }

    @Override
    public Identifier getTexture(NazgulEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, PATH);
    }

    @Override
    public void render(NazgulEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        if(entity.isFading()) {
            int fadingTicks = entity.getFadingTicks();
            float percentage = (float) fadingTicks / NazgulEntity.FADING_TIME;
            //poseStack.translate(Math.cos(fadingTicks * 0.3f) * percentage, Math.sin(fadingTicks * 0.35f) * percentage, Math.cos(fadingTicks * 0.4f) * percentage);
            entityYaw += (float)(Math.cos((double)(entity).age * 10 * percentage)) * Math.PI * ((double)1.35f + percentage);
            float entityPitch = (float)(Math.cos((double)(entity).age * (5 + percentage)) * Math.PI * (double)0.3f);
            poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - entityYaw));
            //poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60.0f + entityPitch));
        }

        float scale = 1 - (float)entity.getFadingTicks() / NazgulEntity.FADING_TIME;
        scale = (float) Math.pow(scale, 0.35f);
        poseStack.scale(1.05f * scale, 1.05f * scale, 1.05f * scale);
        if(entity.hasVehicle()) {
            poseStack.translate(0, -0.4f, 0);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
