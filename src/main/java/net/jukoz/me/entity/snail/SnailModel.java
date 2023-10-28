package net.jukoz.me.entity.snail;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SnailModel<T extends SnailEntity> extends SinglePartEntityModel<T> {
	private final ModelPart snail;
	public SnailModel(ModelPart root) {
		this.snail = root.getChild("snail");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData snail = modelPartData.addChild("snail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, -2.0F));

		ModelPartData house = snail.addChild("house", ModelPartBuilder.create().uv(0, 9).cuboid(-1.5F, -5.5F, 1.0F, 3.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = snail.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

		ModelPartData eyes = body.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -3.0F));

		ModelPartData eye_r1 = eyes.addChild("eye_r1", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData eye_r2 = eyes.addChild("eye_r2", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(SnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		snail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return snail;
	}


}