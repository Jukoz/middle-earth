package net.jukoz.me.entity.beasts.trolls.petrified;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PetrifiedTrollRenderer extends MobEntityRenderer<PetrifiedTrollEntity, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.getPart(ModEntityModelLayers.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public Identifier getTexture(PetrifiedTrollEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
