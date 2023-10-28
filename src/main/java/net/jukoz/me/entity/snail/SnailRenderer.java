package net.jukoz.me.entity.snail;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailModel> {

    private static final Identifier TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/entities/snails/snail1");

    public SnailRenderer(EntityRendererFactory.Context context, SnailModel entityModel, float f) {
        super(context, new SnailModel(context.getPart(ModEntityModelLayers.SNAIL)), 0.3f);
    }

    @Override
    public Identifier getTexture(SnailEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SnailEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
