package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollDroolFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollHeldItemFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollSaddleFeatureRenderer;

import java.util.Map;

public class CaveTrollRenderer extends MobRenderer<CaveTrollEntity, CaveTrollEntityModel> {
    private static final String PATH = "textures/entities/trolls/cave/cave_troll_";
    private static final String TEXTURE_ANG = "textures/entities/trolls/cave/cave_troll_ang.png";
    private static final String TEXTURE_ANGRY_ANG = "textures/entities/trolls/cave/cave_troll_angry_ang.png";
    public CaveTrollRenderer(EntityRendererProvider.Context context) {
        super(context, new CaveTrollEntityModel(context.bakeLayer(EntityModelLayersME.CAVE_TROLL)), 1.1f);
        this.addLayer(new CaveTrollDroolFeatureRenderer(this));
        this.addLayer(new CaveTrollSaddleFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new CaveTrollHeldItemFeatureRenderer(this, context.getItemRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(CaveTrollEntity entity) {
        boolean isCalm = (entity.getTameness() > 25 || !entity.isTamed()) && !entity.isEnraged();
        if(entity.hasCustomName() && entity.getName().getString().equals("Angmarzku")) {
            return isCalm ?
                    MiddleEarth.of(TEXTURE_ANG) :
                    MiddleEarth.of(TEXTURE_ANGRY_ANG);
        }
        return isCalm ?
                MiddleEarth.of(PATH + LOCATION_BY_VARIANT.get(entity.getVariant()) + ".png") :
                MiddleEarth.of(PATH + LOCATION_BY_VARIANT.get(entity.getVariant()) + "_angry.png");
    }

    public static final Map<CaveTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaveTrollVariant.class), (map) -> {
                map.put(CaveTrollVariant.GREEN, "green");
                map.put(CaveTrollVariant.YELLOW, "yellow");
                map.put(CaveTrollVariant.BROWN, "brown");
                map.put(CaveTrollVariant.BLUE, "blue");
                map.put(CaveTrollVariant.GRAY, "gray");
                map.put(CaveTrollVariant.STONE, "stone");
                map.put(CaveTrollVariant.GREY_BLUE, "grey_blue");
            });

}
