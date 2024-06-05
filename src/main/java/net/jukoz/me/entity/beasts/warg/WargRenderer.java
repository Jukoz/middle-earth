package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WargRenderer extends MobEntityRenderer<WargEntity, WargModel> {
    private static final String PATH = "textures/entities/warg/";

    public WargRenderer(EntityRendererFactory.Context context) {
        super(context, new WargModel(context.getPart(ModEntityModelLayers.WARG)), 0.8f);
    }

    @Override
    public Identifier getTexture(WargEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, PATH + "warg1.png");
    }
}
