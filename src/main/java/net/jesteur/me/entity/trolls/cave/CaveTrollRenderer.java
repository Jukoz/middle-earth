package net.jesteur.me.entity.trolls.cave;

import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.jesteur.me.entity.orcs.mordor.MordorOrcModel;
import net.jesteur.me.entity.orcs.mordor.MordorOrcVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class CaveTrollRenderer extends MobEntityRenderer<CaveTrollEntity, CaveTrollModel> {
    private static final String PATH = "textures/entities/trolls/cave/";

    public CaveTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CaveTrollModel(ctx.getPart(ModEntityModelLayers.TROLL)), 1.1f);
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
