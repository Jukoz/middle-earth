package net.jukoz.me.entity.deer;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
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
        return new Identifier(MiddleEarth.MOD_ID, PATH + "deer1.png");
    }


}
