package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornArmorFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornSaddleFeatureRenderer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

import java.util.Map;

public class GreatHornRenderer extends AgeableMobEntityRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererFactory.Context context) {
        super(context, new GreatHornModel(context.getPart(ModEntityModelLayers.GREAT_HORN)),
                new GreatHornModel(context.getPart(ModEntityModelLayers.GREAT_HORN_BABY)), 0.95f);
        this.addFeature(new GreatHornSaddleFeatureRenderer(this,  context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new GreatHornArmorFeatureRenderer(this,  context.getEntityModels(), context.getEquipmentRenderer()));
    }

    protected float getShadowRadius(GreatHornEntityRenderState greatHornEntityRenderState) {
        float f = super.getShadowRadius(greatHornEntityRenderState);
        return greatHornEntityRenderState.baby ? f * 0.8F : f;
    }

    @Override
    public GreatHornEntityRenderState createRenderState() {
        return new GreatHornEntityRenderState();
    }

    public static final Map<GreatHornVariantDep, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GreatHornVariantDep.class), (map) -> {
                map.put(GreatHornVariantDep.BROWN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "brown_great_horn.png"));
                map.put(GreatHornVariantDep.TEMPERATE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "temperate_great_horn.png"));
                map.put(GreatHornVariantDep.COLD,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "cold_great_horn.png"));
                map.put(GreatHornVariantDep.WARM,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warm_great_horn.png"));
                map.put(GreatHornVariantDep.WHITE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "white_great_horn.png"));

            });


    @Override
    public Identifier getTexture(GreatHornEntityRenderState state) {
        return state.greatHornVariant.assetInfo().id().texturePath();
    }

    @Override
    public void updateRenderState(GreatHornEntity greatHornEntity, GreatHornEntityRenderState state, float f) {
        super.updateRenderState(greatHornEntity, state, f);

        state.idleAnimationState.copyFrom(greatHornEntity.idleAnimationState);
        state.earWiggleAnimationState.copyFrom(greatHornEntity.earWigglingAnimationState);
        state.gallopAnimationState.copyFrom(greatHornEntity.gallopAnimationState);
        state.bowAnimationState.copyFrom(greatHornEntity.bowAnimationState);
        state.attackAnimationState.copyFrom(greatHornEntity.attackAnimationState);
        state.saddle = greatHornEntity.getEquippedStack(EquipmentSlot.SADDLE);
        state.armor = greatHornEntity.getEquippedStack(EquipmentSlot.BODY);
        state.blueSaddle = greatHornEntity.hasBlueSaddle();
        state.hasRider = greatHornEntity.hasPlayerRider();
        state.greatHornVariant = greatHornEntity.getVariant();
    }
}
