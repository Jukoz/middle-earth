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
    
    // region BITE
    public static final AnimationDefinition SHELOBITE_LARVA_BITE = AnimationDefinition.Builder.create(0.65f)
            .addBoneAnimation("root",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("abdomen",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("core",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg_left",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("leg_right",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    // endregion
}