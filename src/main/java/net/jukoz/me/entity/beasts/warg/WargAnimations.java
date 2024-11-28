package net.jukoz.me.entity.beasts.warg;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class WargAnimations {
	public static final Animation STAND_UP = Animation.Builder.create(1.5F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 18.1F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(-9.9621F, -2.654F, -53.0485F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(9.9621F, 2.654F, -53.0485F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.3F, -25.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0222F, 0.2534F, -16.2F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.68F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 3.99F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 1.7F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.68F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 3.25F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation SIT_DOWN = Animation.Builder.create(1.6667F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 17.2F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0.0F, 3.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0.0F, 3.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(-0.0131F, 0.2997F, -27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation SIT = Animation.Builder.create(4.75F).looping()
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.0131F, 0.2997F, -27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation GROOM = Animation.Builder.create(7.0F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(-35.0F, -22.5F, 42.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-36.3314F, -26.5633F, 45.7039F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.75F, AnimationHelper.createRotationalVector(-28.7991F, -34.6876F, 31.0148F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.25F, AnimationHelper.createRotationalVector(-22.0103F, -38.9941F, 19.7239F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-36.3314F, -26.5633F, 45.7039F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.0F, AnimationHelper.createRotationalVector(-28.7991F, -34.6876F, 31.0148F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.5F, AnimationHelper.createRotationalVector(-22.0103F, -38.9941F, 19.7239F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.0417F, AnimationHelper.createRotationalVector(-36.3314F, -26.5633F, 45.7039F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.2917F, AnimationHelper.createRotationalVector(-28.7991F, -34.6876F, 31.0148F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.7917F, AnimationHelper.createRotationalVector(-22.0103F, -38.9941F, 19.7239F), Transformation.Interpolations.CUBIC),
					new Keyframe(5.25F, AnimationHelper.createRotationalVector(-36.3314F, -26.5633F, 45.7039F), Transformation.Interpolations.CUBIC),
					new Keyframe(5.5F, AnimationHelper.createRotationalVector(-28.7991F, -34.6876F, 31.0148F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.0F, AnimationHelper.createRotationalVector(-22.0103F, -38.9941F, 19.7239F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.75F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.0417F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(5.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.0131F, 0.2997F, -27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(-0.0131F, 0.2997F, -27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(51.0241F, -61.8087F, -83.4484F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.0417F, AnimationHelper.createRotationalVector(51.0241F, -61.8087F, -83.4484F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(6.9583F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation LAY_DOWN = Animation.Builder.create(1.7083F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(4.5336F, -2.1109F, 28.0414F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-5.9551F, 4.2595F, 22.5542F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(-3.0F, -1.5F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-17.4844F, -0.7515F, -67.3844F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.0131F, 0.2997F, -27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -6.3F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(0.0F, -8.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(17.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0417F, AnimationHelper.createRotationalVector(17.7517F, -11.9313F, -16.3691F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(22.4065F, -10.3945F, -84.5049F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(1.0F, 3.73F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createTranslationalVector(1.5F, 1.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(-4.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0417F, AnimationHelper.createRotationalVector(10.9677F, 0.0F, 4.3548F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("body_armor", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-31.4557F, -5.4765F, -5.3022F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-24.0752F, 6.8432F, -87.0372F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(-0.39F, 4.81F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0417F, AnimationHelper.createTranslationalVector(1.5F, 2.49F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.7083F, AnimationHelper.createTranslationalVector(-4.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation LYING = Animation.Builder.create(1.5833F).looping()
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-3.0F, -1.5F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.4844F, -0.7515F, -67.3844F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -8.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(22.4065F, -10.3945F, -84.5049F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-4.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-24.0752F, 6.8432F, -87.0372F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-4.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation STAND_UP_FROM_LYING = Animation.Builder.create(2.0F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -22.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -15.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-3.0F, -1.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-17.4844F, -0.7515F, -67.3844F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-8.7418F, 0.3846F, -28.7503F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-6.4713F, 5.9023F, 10.9451F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(1.0F, 6.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(3.0F, 9.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(3.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(8.7418F, -0.3846F, -28.7503F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(6.4713F, -5.9023F, 10.9451F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(1.0F, 6.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(3.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -22.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -8.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(22.4065F, -10.3945F, -84.5049F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(23.7732F, -6.464F, -93.8027F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(21.9621F, -11.3386F, -82.1478F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-4.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 36.25F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 23.75F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(4.0F, 5.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-24.0752F, 6.8432F, -87.0372F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-24.9224F, 1.6426F, -98.4393F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-24.0752F, 6.8432F, -87.0372F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-4.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation WALK = Animation.Builder.create(1.25F).looping()
			.addBoneAnimation("root", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 25.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(-2.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, 2.42F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 35.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, 0.67F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-2.0F, 0.84F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -45.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -45.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.67F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(-2.0F, 0.84F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("front_armor", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 35.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.83F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 2.83F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.83F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-2.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.83F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation RUN = Animation.Builder.create(0.5F).looping()
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -12.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -60.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 27.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -60.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -7.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -7.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.BODY, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 67.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -50.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -25.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 67.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 67.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -50.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -25.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 67.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -60.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 25.36F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 30.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -60.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation BITE = Animation.Builder.create(1f)
			.addBoneAnimation("jaw",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 42.5f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("leftEar",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, -20f),
									Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightEar",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, -20f),
									Transformation.Interpolations.LINEAR))).build();

	public static final Animation EAT_FROM_HAND = Animation.Builder.create(1.7083433f)
			.addBoneAnimation(EntityModelPartNames.HEAD,
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, -7.5f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("jaw",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(0f, 0f, 0.83f),
									Transformation.Interpolations.LINEAR),
							new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, 0f, 20f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(0f, 0f, 0.83f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 20f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1.2083433f, AnimationHelper.createRotationalVector(0f, 0f, 0.83f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 20f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1.625f, AnimationHelper.createRotationalVector(0f, 0f, 0.83f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("leftEar",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 57.5f, 0f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("rightEar",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -47.5f, 0f),
									Transformation.Interpolations.CUBIC)))
			.addBoneAnimation("tail",
					new Transformation(Transformation.Targets.ROTATE,
							new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1.2916767f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
									Transformation.Interpolations.CUBIC),
							new Keyframe(1.7083433f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
									Transformation.Interpolations.CUBIC))).build();

	public static final Animation STRETCH = Animation.Builder.create(4.5F)
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -42.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -47.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.75F, AnimationHelper.createRotationalVector(-19.4789F, -12.2725F, -20.9105F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.9167F, AnimationHelper.createRotationalVector(27.3541F, 12.451F, -21.648F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.0833F, AnimationHelper.createRotationalVector(-19.4789F, -12.2725F, -20.9105F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.25F, AnimationHelper.createRotationalVector(27.3541F, 12.451F, -21.648F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.4167F, AnimationHelper.createRotationalVector(-19.4789F, -12.2725F, -20.9105F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.5833F, AnimationHelper.createRotationalVector(27.3541F, 12.451F, -21.648F), Transformation.Interpolations.CUBIC),
					new Keyframe(3.75F, AnimationHelper.createRotationalVector(-19.4789F, -12.2725F, -20.9105F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation(EntityModelPartNames.HEAD, new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("upper_body", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, -4.22F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(12.5F, 0.0F, -65.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(12.2127F, 2.6851F, -77.2127F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("left_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_back_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, -62.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.5F, AnimationHelper.createRotationalVector(-17.1097F, -3.7317F, -74.4384F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(4.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();
}