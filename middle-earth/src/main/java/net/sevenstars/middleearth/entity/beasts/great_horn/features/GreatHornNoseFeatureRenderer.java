package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.time.LocalDate;
import java.time.Month;

public class GreatHornNoseFeatureRenderer extends RenderLayer<GreatHornEntity, GreatHornModel> {
	private final GreatHornModel model;
	private final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_red_nose.png");

	public GreatHornNoseFeatureRenderer(RenderLayerParent<GreatHornEntity, GreatHornModel> context, EntityModelSet loader) {
		super(context);
		this.model = new GreatHornModel(loader.bakeLayer(EntityModelLayersME.GREAT_HORN));
	}

	private boolean isChristmas() {
		LocalDate date = LocalDate.now();
		return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 24;
	}

	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, GreatHornEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
		String name = entity.hasCustomName() ? entity.getName().getString() : "";
		if("rudolph".equalsIgnoreCase(name) || "rudolf".equalsIgnoreCase(name) || isChristmas()) {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE));
			this.getParentModel().renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
		}
	}
}
