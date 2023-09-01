package net.jesteur.me.entity.barrow_wights;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class BarrowWightAnimations {
    public static final Animation anim_scream = Animation.Builder.create(1.5f)
            .addBoneAnimation("head",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-1.73f, 8.41f, 2.72f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(1.73f, -8.41f, -2.72f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("head",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("right_arm2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-20f, 0f, 10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-20f, 0f, 20f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(-20f, 0f, 10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-20f, 0f, 20f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-20f, 0f, 10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("left_arm2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-20f, 0f, -10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-20f, 0f, -20f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(-20f, 0f, -10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-20f, 0f, -20f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-20f, 0f, -10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("bottom_jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(32f, 6f, 12.5f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("bottom_jaw",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.LINEAR))).build();
}
