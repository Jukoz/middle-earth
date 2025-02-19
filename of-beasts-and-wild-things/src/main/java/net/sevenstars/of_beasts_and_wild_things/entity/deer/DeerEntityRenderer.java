package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModelLayers;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityModel;

public class DeerEntityRenderer extends MobEntityRenderer<DeerEntity, LivingEntityRenderState, DeerEntityModel> {
    private static final String PATH = "textures/entity/deer/";

    public DeerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DeerEntityModel(context.getPart(ModEntityModelLayers.DEER)), 1.0f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(OfBeastsAndWildThings.MOD_ID, PATH + "deer.png");

    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
