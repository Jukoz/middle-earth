package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class PheasantEntityModel extends EntityModel<PheasantEntityRenderState> {

    public PheasantEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData pheasant = modelPartData.addChild("pheasant", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 25.0F, 0.0F));

        ModelPartData upper_body = pheasant.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, -6.5F, 0.0F));

        ModelPartData body = upper_body.addChild("body", ModelPartBuilder.create().uv(0, 14).cuboid(-2.5F, -2.5F, -3.5F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.0F, -1.5F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.origin(2.5F, -2.5F, -3.0F));

        ModelPartData cube_r1 = left_wing.addChild("cube_r1", ModelPartBuilder.create().uv(29, 7).cuboid(0.0F, -1.5F, -1.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.513F, 1.4095F, 1.0F, 0.0F, 0.0F, -0.3491F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.origin(-2.5F, -2.5F, -3.0F));

        ModelPartData cube_r2 = right_wing.addChild("cube_r2", ModelPartBuilder.create().uv(29, 7).mirrored().cuboid(0.0F, -1.5F, -1.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.513F, 1.4095F, 1.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -1.5F, -0.1F, 5.0F, 3.0F, 11.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, -1.0F, 3.5F));

        ModelPartData head_and_neck = upper_body.addChild("head_and_neck", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -1.5F, -5.0F));

        ModelPartData neck = head_and_neck.addChild("neck", ModelPartBuilder.create().uv(21, 0).cuboid(-1.5F, -1.75F, -5.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -2.25F, 4.0F));

        ModelPartData head = head_and_neck.addChild("head", ModelPartBuilder.create().uv(4, 6).cuboid(-1.0F, -1.0F, -4.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(17, 14).cuboid(-1.5F, -2.0F, -3.5F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(4, 27).cuboid(1.5F, -4.0F, -3.5F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(4, 27).cuboid(-1.499F, -4.0F, -3.5F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(20, 22).cuboid(-1.5F, -3.0F, -3.5F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -4.0F, 0.5F));

        ModelPartData left_leg = pheasant.addChild("left_leg", ModelPartBuilder.create().uv(0, 14).cuboid(-0.1F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 3).cuboid(-1.1F, 4.0F, -2.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, -5.0F, -1.0F));

        ModelPartData right_leg = pheasant.addChild("right_leg", ModelPartBuilder.create().uv(0, 26).cuboid(-0.9F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.9F, 4.0F, -2.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, -5.0F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PheasantEntityRenderState state) {
        super.setAngles(state);

        animateWalking(PheasantEntityAnimations.WALK, state.limbSwingAnimationProgress, state.limbSwingAmplitude, 5.0F, 5.0F);
        animate(state.idleAnimationState, PheasantEntityAnimations.IDLE, state.age);
        animate(state.diggingAnimationState, PheasantEntityAnimations.DIGGING, state.age, 1.2F);
    }
}