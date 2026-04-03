package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatBeads;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

public class BroadhoofGoatBeadsFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/beads/";
    private static final Identifier INVISIBLE_ID = Identifier.ofVanilla("invisible");

    private static final Map<BroadhoofGoatBeads, Identifier> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatBeads.NONE,
                    INVISIBLE_ID,
                    BroadhoofGoatBeads.LEATHER,
                    Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_leather_beads.png"),
                    BroadhoofGoatBeads.COAL,
                    Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_coal_beads.png"),
                    BroadhoofGoatBeads.COPPER,
                    Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_copper_beads.png"),
                    BroadhoofGoatBeads.GOLD,
                    Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gold_beads.png"),
                    BroadhoofGoatBeads.ALMANDINE,
                    Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_almandine_beads.png")
            )
    );

    public BroadhoofGoatBeadsFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        Identifier identifier = (Identifier)TEXTURES.get(state.beads);
        if (identifier != INVISIBLE_ID && !state.invisible) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(identifier));
            this.getContextModel().render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(state, 0.0F));
        }
    }
}
