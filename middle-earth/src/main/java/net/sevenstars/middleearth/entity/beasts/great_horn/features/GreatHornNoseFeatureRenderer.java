package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

import java.time.LocalDate;
import java.time.Month;

@Environment(EnvType.CLIENT)
public class GreatHornNoseFeatureRenderer extends FeatureRenderer<GreatHornEntityRenderState, GreatHornModel> {
	private final GreatHornModel model;
	private final static Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_red_nose.png");

	public GreatHornNoseFeatureRenderer(FeatureRendererContext<GreatHornEntityRenderState, GreatHornModel> context, LoadedEntityModels loader) {
		super(context);
		this.model = new GreatHornModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN));
	}

	private boolean isChristmas() {
		LocalDate date = LocalDate.now();
		return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 24;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
		if(state.hasRedNose() || isChristmas()) {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
			this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
		}
	}
}
