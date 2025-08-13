package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class ShelobiteLarvaAnimations {

    // region WALK

    public static final AnimationDefinition SHELOBITE_LARVA_WALK = AnimationDefinition.Builder.create(1f).looping()
            .addBoneAnimation("leg_left_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_center_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_center_back",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_back",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, -2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_center_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_center_back",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, -2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_back",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
    // endregion
}