package net.sevenstars.middleearth.entity.deer;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, DeerModel> {
    private static final String PATH = "textures/entities/deer/";

    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new DeerModel(context.getPart(ModEntityModelLayers.DEER)), 0.7f);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "deer1.png");
    }


}
