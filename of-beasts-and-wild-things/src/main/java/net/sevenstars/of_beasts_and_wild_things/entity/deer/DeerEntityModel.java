package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class DeerEntityModel extends EntityModel<DeerEntityRenderState> {
    protected DeerEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData deer = modelPartData.addChild("deer", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 3.5F));

        ModelPartData upperBody = deer.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -15.5F, -3.5F));

        upperBody.addChild("chest", ModelPartBuilder.create().uv(35, 15).cuboid(-5.5F, -5.5F, -8.5F, 11.0F, 11.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(-5.0F, -4.5F, 0.5F, 10.0F, 10.0F, 11.0F, new Dilation(0.0F))
                .uv(72, 49).cuboid(-2.0F, -2.5F, 6.5F, 4.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.0F, -2.0F));

        ModelPartData head = upperBody.addChild("head", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.9137F, -8.8708F, -0.5672F, 0.0F, 0.0F));
        ModelPartData neck = head.addChild("neck", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.7641F, 0.6357F));
        neck.addChild("neck_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -3.3504F, -8.5F, 5.0F, 7.0F, 17.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, -5.0F, -3.0F, -1.0036F, 0.0F, 0.0F));

        ModelPartData right_ear = head.addChild("right_ear", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -5.0F, 1.0F));

        ModelPartData right_ear_r1 = right_ear.addChild("right_ear_r1", ModelPartBuilder.create().uv(32, 1).cuboid(-4.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        deer.addChild("leg_back_left", ModelPartBuilder.create().uv(0, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(3.49F, -11.0F, 4.0F));
        deer.addChild("leg_back_right", ModelPartBuilder.create().uv(38, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-3.49F, -11.0F, 4.0F));
        deer.addChild("leg_front_left", ModelPartBuilder.create().uv(12, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(3.5F, -11.0F, -11.0F));
        deer.addChild("leg_front_right", ModelPartBuilder.create().uv(24, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-3.5F, -11.0F, -11.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(DeerEntityRenderState state) {
        super.setAngles(state);

        if(state.isRunning) {
            this.animateWalking(DeerEntityAnimations.RUN, state.limbFrequency, state.limbAmplitudeMultiplier, 1.5F, 1.0F);
        }
        else {
            this.animateWalking(DeerEntityAnimations.WALK, state.limbFrequency, state.limbAmplitudeMultiplier, 1.0F, 1.0F);
        }
    }
}
