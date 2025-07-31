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
            .addBoneAnimation("leg_left_front2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_front3",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_front4",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-12.23f, -9.24f, -30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, -15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-12.23f, -9.24f, -30.47f),
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
            .addBoneAnimation("leg_right_front2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_front3",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.29167f, AnimationHelper.createRotationalVector(0f, -2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_front4",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.33333f, AnimationHelper.createRotationalVector(0f, 15f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.70833f, AnimationHelper.createRotationalVector(2.77f, 9.24f, 30.47f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 2.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("abdomen",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(4.98f, -0.44f, 4.98f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.75f, AnimationHelper.createRotationalVector(4.98f, 0.44f, -4.98f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    // endregion

    // region IDLE
    public static final AnimationDefinition SHELOBITE_LARVA_IDLE = AnimationDefinition.Builder.create(2f).looping()
            .addBoneAnimation("abdomen",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    // endregion

    // region BITE
    public static final AnimationDefinition SHELOBITE_LARVA_BITE = AnimationDefinition.Builder.create(0.66667f)
            .addBoneAnimation("head",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.66667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg_left_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-50f, -30f, -50f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.66667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg_right_front",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-56.76f, 33.01f, 52.97f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.66667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    // endregion
}