package net.jukoz.me.entity.beasts.trolls.hill;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollEntity;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HillTrollRenderer extends MobEntityRenderer<HillTrollEntity, HillTrollModel> {
    private static final String PATH = "textures/entities/trolls/hill/";

    public HillTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new HillTrollModel(context.getPart(ModEntityModelLayers.HILL_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(HillTrollEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, PATH + "troll1.png");
    }

    public void render(HillTrollEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
