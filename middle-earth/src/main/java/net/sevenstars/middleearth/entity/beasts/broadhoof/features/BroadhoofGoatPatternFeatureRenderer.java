package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class BroadhoofGoatPatternFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/patterns/";
    private static final Identifier INVISIBLE_ID = Identifier.ofVanilla("invisible");

    private static final Map<BroadhoofGoatPattern, Identifier> TEXTURES = Maps.newEnumMap(
            Map.ofEntries(
                    Map.entry(BroadhoofGoatPattern.NONE, INVISIBLE_ID),

                    Map.entry(BroadhoofGoatPattern.BLACK_MASK,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_PATCHES,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SIDE_PATCH,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SPOTS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_STRIPS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_strip.png")),

                    Map.entry(BroadhoofGoatPattern.BROWN_MASK,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_PATCHES,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SIDE_PATCH,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SPOTS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_STRIPS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown_strip.png")),

                    Map.entry(BroadhoofGoatPattern.PALE_MASK,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_pale_mask.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_PATCHES,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_pale_patches.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SIDE_PATCH,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_pale_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SPOTS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_pale_spots.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_STRIPS,
                            Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_pale_strip.png"))


            )
    );

    public BroadhoofGoatPatternFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        Identifier identifier = (Identifier)TEXTURES.get(state.pattern);
        if (identifier != INVISIBLE_ID && !state.invisible) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(identifier));
            this.getContextModel().render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(state, 0.0F));
        }
    }
}
