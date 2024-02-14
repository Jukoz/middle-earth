package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollEntity;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class HillTrollRenderer extends MobEntityRenderer<HillTrollEntity, HillTrollModel> {
    private static final String PATH = "textures/entities/trolls/";

    public HillTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new HillTrollModel(context.getPart(ModEntityModelLayers.HILL_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(HillTrollEntity entity) {
        return entity.isStoned() ?
                new Identifier(MiddleEarth.MOD_ID, PATH + "statue/hill_troll_statue.png") : new Identifier(MiddleEarth.MOD_ID, PATH + "hill/troll1.png");
    }

    public void render(HillTrollEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
