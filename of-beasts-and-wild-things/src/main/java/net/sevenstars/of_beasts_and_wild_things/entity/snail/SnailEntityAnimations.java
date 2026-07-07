package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.minecraft.client.render.entity.animation.*;

public class SnailEntityAnimations {
    public static final AnimationDefinition CRAWL = AnimationDefinition.Builder.create(2f).looping()
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createScalingVector(1f, 1f, 1.2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.5f, AnimationHelper.createScalingVector(1f, 1f, 0.8f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC))).build();
}
