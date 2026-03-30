package net.sevenstars.middleearth.entity.beasts.experimental_mount;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

public class ExperimentalMountRenderer extends MobEntityRenderer<ExperimentalMountEntity, LivingEntityRenderState, ExperimentalMountModel> {
    private static final String PATH = "textures/entities/experimental_entity/experimental_entity_texture.png";

    public ExperimentalMountRenderer(EntityRendererFactory.Context context) {
        super(context, new ExperimentalMountModel(context.getPart(ModEntityModelLayers.EXPERIMENTAL_ENTITY)), 1.0f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
