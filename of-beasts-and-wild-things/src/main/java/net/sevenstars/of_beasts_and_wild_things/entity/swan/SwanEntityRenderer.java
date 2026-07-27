package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class SwanEntityRenderer extends MobRenderer<SwanEntity, SwanEntityModel> {
    private static final String PATH = "textures/entity/swan/";
    private final SwanEntityModel adultModel;
    private final SwanEntityModel babyModel;

    public SwanEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SwanAdultModel(context.bakeLayer(EntityModelLayersWT.SWAN)), 0.5f);
        this.adultModel = this.model;
        this.babyModel = new SwanBabyModel(context.bakeLayer(EntityModelLayersWT.SWAN_BABY));
    }

    public static final Map<SwanEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SwanEntityVariant.class), (map) -> {
                map.put(SwanEntityVariant.WHITE,
                        PATH + "swan_white.png");
                map.put(SwanEntityVariant.BLACK,
                        PATH + "swan_black.png");
                map.put(SwanEntityVariant.TRUMPETER,
                        PATH + "swan_trumpeter.png");
                map.put(SwanEntityVariant.WHOOPER,
                        PATH + "swan_whooper.png");
            });

    @Override
    public ResourceLocation getTextureLocation(SwanEntity entity) {
        return entity.isBaby()
                ? OfBeastsAndWildThings.of(PATH + "swan_baby.png")
                : OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(SwanEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
