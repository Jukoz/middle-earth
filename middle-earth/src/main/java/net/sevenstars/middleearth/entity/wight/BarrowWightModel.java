package net.sevenstars.middleearth.entity.wight;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class BarrowWightModel extends EntityModel<BarrowWightRenderState> implements ModelWithArms, ModelWithHead {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    private final Animation idleAnimation;
    private final Animation walkingAnimation;
    private final Animation attackAnimation;
    private final Animation screamAnimation;
    private final Animation incantationAnimation;

    public BarrowWightModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        ModelPart torso = this.root.getChild("torso");
        this.head = torso.getChild("Head");
        this.leftArm = torso.getChild("ArmLeft");
        this.rightArm = torso.getChild("ArmRight");

        this.idleAnimation = BarrowWightAnimations.IDLE.createAnimation(root);
        this.walkingAnimation = BarrowWightAnimations.WALKING_2.createAnimation(root);
        this.attackAnimation = BarrowWightAnimations.ATTACK.createAnimation(root);
        this.screamAnimation = BarrowWightAnimations.SCREAM.createAnimation(root);
        this.incantationAnimation = BarrowWightAnimations.INCANTATION.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.5F, 12.0F, 0.0F));

        ModelPartData torso = root.addChild("torso", ModelPartBuilder.create().uv(0, 21)
                .cuboid(-4.0714F, -12.35F, -1.9643F, 8.0F, 13.0F, 4.0F,
                        new Dilation(0.0F)), ModelTransform.origin(-0.4286F, -1.65F, -0.0357F));

        ModelPartData Head = torso.addChild("Head", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-4.5F, -7.25F, -3.75F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-4.5F, -7.25F, -3.75F, 8.0F, 15.0F, 8.0F,
                        new Dilation(0.25F)), ModelTransform.origin(0.4286F, -12.1F, -0.2143F));

        ModelPartData Jaw = Head.addChild("Jaw", ModelPartBuilder.create().uv(0, 16)
                .cuboid(-4.0F, -0.5F, -4.1F, 8.0F, 1.0F, 4.0F, new Dilation(0.2F))
                .uv(24, 0).cuboid(-2.0F, -1.5F, -4.4F, 4.0F, 7.0F, 1.0F,
                        new Dilation(0.0F)), ModelTransform.origin(-0.5F, 0.25F, 0.35F));

        ModelPartData equipment = Head.addChild("equipment", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, -0.25F, 1.25F));

        ModelPartData hat = equipment.addChild("hat", ModelPartBuilder.create().uv(64, 0)
                .cuboid(-4.0F, -9.5F, -5.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.25F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData crown = equipment.addChild("crown", ModelPartBuilder.create().uv(64, 10)
                .cuboid(-4.0F, -12.5F, -5.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.25F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData crest = equipment.addChild("crest", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, -3.0F, 0.0F));

        ModelPartData cube_r1 = crest.addChild("cube_r1", ModelPartBuilder.create().uv(67, 23)
                .cuboid(-5.0F, 2.0F, 2.0F, 2.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 3.75F, -8.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData wings = equipment.addChild("wings", ModelPartBuilder.create().uv(114, 0).mirrored()
                .cuboid(2.0F, -16.3F, -1.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(114, 16).cuboid(-9.0F, -16.3F, -1.0F, 7.0F, 16.0F, 0.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData ArmRight = torso.addChild("ArmRight", ModelPartBuilder.create().uv(24, 23)
                .cuboid(-4.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(24, 39).cuboid(-4.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                        new Dilation(0.5F)), ModelTransform.origin(-4.0714F, -11.35F, 0.0357F));

        /*ModelPartData Weapon = ArmRight.addChild("Weapon", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 10.0F, 0.0F));

        ModelPartData Spear = Weapon.addChild("Spear", ModelPartBuilder.create().uv(124, 127).cuboid(0.0F, -1.0F, -26.0F, 1.0F, 1.0F, 46.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData spear_head_r1 = Spear.addChild("spear_head_r1", ModelPartBuilder.create().uv(127, 74).cuboid(-0.45F, -4.75F, -4.75F, 0.9F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(125, 75).cuboid(-0.45F, -3.75F, -3.75F, 0.9F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(123, 76).cuboid(-0.45F, -2.75F, -1.75F, 0.9F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(122, 77).cuboid(-0.45F, -1.75F, 0.25F, 0.9F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(125, 77).cuboid(-0.45F, -1.75F, -2.75F, 0.9F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(121, 78).cuboid(-0.45F, -0.75F, 1.25F, 0.9F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(120, 79).cuboid(-0.45F, 0.25F, 2.25F, 0.9F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(119, 80).cuboid(-0.45F, 1.25F, 3.25F, 0.9F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(123, 80).cuboid(-0.45F, 1.25F, -0.75F, 0.9F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(118, 81).cuboid(-0.45F, 2.25F, 4.25F, 0.9F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(119, 83).cuboid(-0.45F, 4.25F, 2.25F, 0.9F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -0.7035F, -32.0F, 0.7854F, 0.0F, 0.0F));
        */

        ModelPartData Cape = torso.addChild("Cape", ModelPartBuilder.create(), ModelTransform.origin(-0.0714F, -9.65F, 0.0357F));

        ModelPartData neck_cape = Cape.addChild("neck_cape", ModelPartBuilder.create().uv(30, 88)
                .cuboid(-5.0F, -2.0F, -3.0F, 10.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.0F, 0.0F));

        ModelPartData Cape2 = Cape.addChild("Cape2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -2.5F, 3.0F));

        ModelPartData Left_cape = Cape2.addChild("Left_cape", ModelPartBuilder.create().uv(30, 98)
                .cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 28.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(3.0F, 0.0F, 0.0F));

        ModelPartData Right_Cape = Cape2.addChild("Right_Cape", ModelPartBuilder.create().uv(38, 98)
                .cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 28.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 0.0F, 0.0F));

        ModelPartData ArmLeft = torso.addChild("ArmLeft", ModelPartBuilder.create().uv(40, 23)
                .cuboid(0.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(40, 39).cuboid(0.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                        new Dilation(0.5F)), ModelTransform.origin(3.9286F, -11.35F, 0.0357F));
        
        ModelPartData SkirtBack = torso.addChild("SkirtBack", ModelPartBuilder.create().uv(16, 74).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-0.0714F, 0.65F, 2.0357F));

        ModelPartData SkirtFront = torso.addChild("SkirtFront", ModelPartBuilder.create().uv(0, 74).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-0.0714F, 0.65F, -1.9643F));

        ModelPartData LowerBody = root.addChild("LowerBody", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, -1.0F, 0.0F));

        ModelPartData LegLeft = LowerBody.addChild("LegLeft", ModelPartBuilder.create().uv(15, 97)
                .cuboid(-1.6667F, 4.1667F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(13, 89).cuboid(-2.1667F, -0.8333F, -1.5F, 3.0F, 5.0F, 3.0F,
                        new Dilation(0.0F)), ModelTransform.origin(2.6667F, 0.8333F, 0.0F));

        ModelPartData SkirtLeft = LegLeft.addChild("SkirtLeft", ModelPartBuilder.create().uv(0, 55)
                .cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.origin(1.3333F, -0.8333F, 0.0F));

        ModelPartData LegRight = LowerBody.addChild("LegRight", ModelPartBuilder.create().uv(2, 97)
                .cuboid(-0.3333F, 4.1667F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 89).cuboid(-0.8333F, -0.8333F, -1.5F, 3.0F, 5.0F, 3.0F,
                        new Dilation(0.0F)), ModelTransform.origin(-2.6667F, 0.8333F, 0.0F));

        ModelPartData SkirtRight = LegRight.addChild("SkirtRight", ModelPartBuilder.create().uv(16, 55)
                .cuboid(0.0F, 0.0F, -2.0F, 4.0F, 15.0F, 4.0F,
                        new Dilation(0.0F)), ModelTransform.origin(-1.3333F, -0.8333F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BarrowWightRenderState state) {
        super.setAngles(state);

        boolean isAttacking = false;
        if(state.attackAnimationState.isRunning()) {
            isAttacking = true;
            this.attackAnimation.apply(state.attackAnimationState, state.age, 2.0f);
        }

        if(!isAttacking && state.limbSwingAmplitude <= 0.1) {
            this.idleAnimation.apply(state.idleAnimationState, state.age, 0.75f);
        } else {
            this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 3.75F, 3.25F);
        }
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.root.applyTransform(matrices);
        this.getArm(arm).applyTransform(matrices);
    }

    protected ModelPart getArm(Arm arm) {
        return arm == Arm.LEFT ? this.leftArm : this.rightArm;
    }

    @Override
    public ModelPart getHead() {
        return null;
    }
}