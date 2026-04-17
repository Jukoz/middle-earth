package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

import static java.lang.Math.PI;

public class SnailEntityModel extends EntityModel<SnailEntityRenderState> {

    private final Animation walkingAnimation;

    protected SnailEntityModel(ModelPart root) {
        super(root);
        this.walkingAnimation = SnailEntityAnimations.CRAWL.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData snail = modelPartData.addChild("snail", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.4F)), ModelTransform.of(0.0F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData body = snail.addChild("body", ModelPartBuilder.create().uv(12, 22).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(0, -1).cuboid(-1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, -1).mirrored().cuboid(1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 3.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(SnailEntityRenderState state) {
        super.setAngles(state);

        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 20.0F, 30.0F);

        getRootPart().getChild("snail").pitch = -(float)PI/2 * ((float)state.climbingTicks / SnailEntity.CLIMBING_TIME_TRANSITION);
    }
}
