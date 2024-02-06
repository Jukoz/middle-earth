package net.jukoz.me.entity.deer;

import net.jukoz.me.entity.beasts.trolls.TrollAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DeerModel extends SinglePartEntityModel<DeerEntity> {
    private final ModelPart deer;
    public DeerModel(ModelPart root) {
        this.deer = root.getChild("Deer");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData Deer = modelPartData.addChild("Deer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData Chest = Deer.addChild("Chest", ModelPartBuilder.create().uv(35, 15).cuboid(-5.5F, -5.5F, -8.5F, 11.0F, 11.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(-5.0F, -4.5F, 0.5F, 10.0F, 10.0F, 11.0F, new Dilation(0.0F))
                .uv(72, 49).cuboid(-2.0F, -2.5F, 6.5F, 4.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -16.5F, -5.5F));

        ModelPartData Head = Deer.addChild("Head", ModelPartBuilder.create(), ModelTransform.of(0.0F, -16.4137F, -12.3708F, -0.5672F, 0.0F, 0.0F));

        ModelPartData Neck = Head.addChild("Neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.7641F, 0.6357F));

        ModelPartData neck_r1 = Neck.addChild("neck_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -3.3504F, -8.5F, 5.0F, 7.0F, 17.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, -3.0F, -1.0036F, 0.0F, 0.0F));

        ModelPartData headnoneck = Neck.addChild("headnoneck", ModelPartBuilder.create(), ModelTransform.of(0.0F, -9.5365F, -6.0671F, 0.2182F, 0.0F, 0.0F));

        ModelPartData EarLeft_r1 = headnoneck.addChild("EarLeft_r1", ModelPartBuilder.create().uv(27, 0).cuboid(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.5889F, -0.3614F, 0.1678F, -0.2564F, 0.9819F));

        ModelPartData EarRight_r1 = headnoneck.addChild("EarRight_r1", ModelPartBuilder.create().uv(36, 45).cuboid(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -2.5889F, -0.3614F, 0.1678F, 0.2564F, -0.9819F));

        ModelPartData Head_r1 = headnoneck.addChild("Head_r1", ModelPartBuilder.create().uv(27, 0).cuboid(-3.0F, -3.0F, -5.8F, 6.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.7072F, -1.3409F, 0.4363F, 0.0F, 0.0F));

        ModelPartData Antlers = headnoneck.addChild("Antlers", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 25.4137F, 17.3708F));

        ModelPartData rightantler = Antlers.addChild("rightantler", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Right5_r1 = rightantler.addChild("Right5_r1", ModelPartBuilder.create().uv(36, 51).cuboid(-1.5F, -2.0F, 0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5608F, -30.4379F, -20.7981F, 0.9235F, 0.1104F, -0.1536F));

        ModelPartData Right4_r1 = rightantler.addChild("Right4_r1", ModelPartBuilder.create().uv(4, 12).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.3265F, -34.3883F, -21.0788F, 0.9235F, 0.1104F, -0.1536F));

        ModelPartData Right3_r1 = rightantler.addChild("Right3_r1", ModelPartBuilder.create().uv(49, 0).cuboid(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.3603F, -35.8932F, -19.3032F, -0.2983F, 0.1104F, -0.1536F));

        ModelPartData Right2_r1 = rightantler.addChild("Right2_r1", ModelPartBuilder.create().uv(4, 24).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.3603F, -35.8932F, -19.3032F, 0.1381F, 0.1104F, -0.1536F));

        ModelPartData Right1_r1 = rightantler.addChild("Right1_r1", ModelPartBuilder.create().uv(33, 0).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -32.2943F, -19.0853F, 0.057F, 0.1672F, -0.7237F));

        ModelPartData leftantler = Antlers.addChild("leftantler", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Left5_r1 = leftantler.addChild("Left5_r1", ModelPartBuilder.create().uv(8, 12).cuboid(0.5F, -2.0F, 0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5608F, -30.4379F, -20.7981F, 0.9235F, -0.1104F, 0.1536F));

        ModelPartData Left4_r1 = leftantler.addChild("Left4_r1", ModelPartBuilder.create().uv(0, 12).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.3265F, -34.3883F, -21.0788F, 0.9235F, -0.1104F, 0.1536F));

        ModelPartData Left3_r1 = leftantler.addChild("Left3_r1", ModelPartBuilder.create().uv(13, 11).cuboid(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.3603F, -35.8932F, -19.3032F, -0.2983F, -0.1104F, 0.1536F));

        ModelPartData Left2_r1 = leftantler.addChild("Left2_r1", ModelPartBuilder.create().uv(0, 24).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.3603F, -35.8932F, -19.3032F, 0.1381F, -0.1104F, 0.1536F));

        ModelPartData Left1_r1 = leftantler.addChild("Left1_r1", ModelPartBuilder.create().uv(31, 24).cuboid(1.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -32.2943F, -19.0853F, 0.057F, -0.1672F, 0.7237F));

        ModelPartData BackLegsLeft = Deer.addChild("BackLegsLeft", ModelPartBuilder.create().uv(0, 57).cuboid(-1.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.49F, -10.0F, 4.0F));

        ModelPartData BackLegsRight = Deer.addChild("BackLegsRight", ModelPartBuilder.create().uv(38, 57).cuboid(-1.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.49F, -10.0F, 4.0F));

        ModelPartData FrontLegsLeft = Deer.addChild("FrontLegsLeft", ModelPartBuilder.create().uv(12, 57).cuboid(-1.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -10.0F, -11.0F));

        ModelPartData FrontLegsRight = Deer.addChild("FrontLegsRight", ModelPartBuilder.create().uv(24, 57).cuboid(-1.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -10.0F, -11.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.deer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.deer;
    }

    @Override
    public void setAngles(DeerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.animateMovement(DeerAnimations.WALK, limbAngle, limbDistance, 4f, 5.5f);
        this.updateAnimation(entity.idleAnimationState, DeerAnimations.IDLE2, animationProgress, 1f);
    }
}