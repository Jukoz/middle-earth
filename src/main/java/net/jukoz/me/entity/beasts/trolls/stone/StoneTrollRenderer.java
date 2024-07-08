package net.jukoz.me.entity.beasts.trolls.stone;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class StoneTrollRenderer extends MobEntityRenderer<StoneTrollEntity, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public StoneTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneTrollModel(context.getPart(ModEntityModelLayers.STONE_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(StoneTrollEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "stone_troll1.png");
    }

    public void render(StoneTrollEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
