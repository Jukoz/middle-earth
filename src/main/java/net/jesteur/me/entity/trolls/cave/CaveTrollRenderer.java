package net.jesteur.me.entity.trolls.cave;

import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class CaveTrollRenderer extends MobEntityRenderer<CaveTrollEntity, CaveTrollModel> {
    private static final String PATH = "textures/entities/trolls/cave/";

    public CaveTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CaveTrollModel(ctx.getPart(ModEntityModelLayers.CAVE_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(CaveTrollEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<CaveTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaveTrollVariant.class), (resourceLocation) -> {
                resourceLocation.put(CaveTrollVariant.GREY_YELLOW,
                        PATH + "troll1.png");
                resourceLocation.put(CaveTrollVariant.GREY_BLUE,
                        PATH + "troll2.png");
                resourceLocation.put(CaveTrollVariant.GREY_STONE,
                        PATH + "troll3.png");
            });
}
