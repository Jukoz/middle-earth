package net.sevenstars.middleearth.entity.spider.scuttler;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class ShelobiteScuttlerModel extends EntityModel<ShelobiteScuttlerRenderState> {
    private final ModelPart root;
    private final ModelPart legscore;
    private final ModelPart body;

    private final Animation idleAnimation;
    private final Animation walkingAnimation;
    private final Animation biteAnimation;
    private final Animation pounceAnimation;

    public ShelobiteScuttlerModel(ModelPart root) {
        super(root);

        this.root = root.getChild("root");
        this.legscore = this.root.getChild("legscore");
        this.body = this.root.getChild("body");

        this.idleAnimation = ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_IDLE.createAnimation(root);
        this.walkingAnimation = ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_WALK.createAnimation(root);
        this.biteAnimation = ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_BITE.createAnimation(root);
        this.pounceAnimation = ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_POUNCE.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 36.0F, 0.0F));

        ModelPartData legscore = root.addChild("legscore", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -18.0F, -5.0F));

        ModelPartData rights = legscore.addChild("rights", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rrotation = rights.addChild("rrotation", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 1.0F, -3.0F));

        ModelPartData rleg = rrotation.addChild("rleg", ModelPartBuilder.create().uv(146, 142).cuboid(-2.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg2 = rleg.addChild("rleg2", ModelPartBuilder.create().uv(56, 131).cuboid(-4.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, -3.0F, 0.0F));

        ModelPartData rleg3 = rleg2.addChild("rleg3", ModelPartBuilder.create().uv(158, 96).cuboid(-2.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(88, 83).cuboid(-11.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 1.0F, 0.0F));

        ModelPartData rrotation2 = rights.addChild("rrotation2", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 1.0F, 0.0F));

        ModelPartData rleg8 = rrotation2.addChild("rleg8", ModelPartBuilder.create().uv(146, 135).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, -1.0F, 0.0F));

        ModelPartData rleg9 = rleg8.addChild("rleg9", ModelPartBuilder.create().uv(53, 114).cuboid(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -4.0F, 0.0F));

        ModelPartData rleg10 = rleg9.addChild("rleg10", ModelPartBuilder.create().uv(152, 96).cuboid(-2.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-11.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 1.0F, 0.0F));

        ModelPartData rrotation3 = rights.addChild("rrotation3", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 1.0F, 3.0F));

        ModelPartData rleg11 = rrotation3.addChild("rleg11", ModelPartBuilder.create().uv(146, 135).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, -1.0F, 0.0F));

        ModelPartData rleg12 = rleg11.addChild("rleg12", ModelPartBuilder.create().uv(53, 114).cuboid(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -4.0F, 0.0F));

        ModelPartData rleg13 = rleg12.addChild("rleg13", ModelPartBuilder.create().uv(152, 96).cuboid(-2.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-11.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 1.0F, 0.0F));

        ModelPartData rrotation4 = rights.addChild("rrotation4", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, 1.0F, 6.0F));

        ModelPartData rleg4 = rrotation4.addChild("rleg4", ModelPartBuilder.create().uv(146, 142).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, -1.0F, 0.0F));

        ModelPartData rleg5 = rleg4.addChild("rleg5", ModelPartBuilder.create().uv(56, 131).cuboid(-4.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -2.0F, 0.0F));

        ModelPartData rleg6 = rleg5.addChild("rleg6", ModelPartBuilder.create().uv(158, 96).cuboid(-2.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(88, 83).cuboid(-11.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-3.0F, 1.0F, 0.0F));

        ModelPartData lefts = legscore.addChild("lefts", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rrotation5 = lefts.addChild("rrotation5", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 1.0F, -3.0F));

        ModelPartData rleg7 = rrotation5.addChild("rleg7", ModelPartBuilder.create().uv(146, 142).mirrored().cuboid(0.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg14 = rleg7.addChild("rleg14", ModelPartBuilder.create().uv(56, 131).mirrored().cuboid(0.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(1.0F, -3.0F, 0.0F));

        ModelPartData rleg15 = rleg14.addChild("rleg15", ModelPartBuilder.create().uv(158, 96).mirrored().cuboid(0.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(88, 83).mirrored().cuboid(-4.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(3.0F, 1.0F, 0.0F));

        ModelPartData rrotation6 = lefts.addChild("rrotation6", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 1.0F, 0.0F));

        ModelPartData rleg16 = rrotation6.addChild("rleg16", ModelPartBuilder.create().uv(146, 135).mirrored().cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(1.0F, -1.0F, 0.0F));

        ModelPartData rleg17 = rleg16.addChild("rleg17", ModelPartBuilder.create().uv(53, 114).mirrored().cuboid(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -4.0F, 0.0F));

        ModelPartData rleg18 = rleg17.addChild("rleg18", ModelPartBuilder.create().uv(152, 96).mirrored().cuboid(0.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 0).mirrored().cuboid(-5.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, 1.0F, 0.0F));

        ModelPartData rrotation7 = lefts.addChild("rrotation7", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 1.0F, 3.0F));

        ModelPartData rleg19 = rrotation7.addChild("rleg19", ModelPartBuilder.create().uv(146, 135).mirrored().cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(1.0F, -1.0F, 0.0F));

        ModelPartData rleg20 = rleg19.addChild("rleg20", ModelPartBuilder.create().uv(53, 114).mirrored().cuboid(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -4.0F, 0.0F));

        ModelPartData rleg21 = rleg20.addChild("rleg21", ModelPartBuilder.create().uv(152, 96).mirrored().cuboid(0.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 0).mirrored().cuboid(-5.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, 1.0F, 0.0F));

        ModelPartData rrotation8 = lefts.addChild("rrotation8", ModelPartBuilder.create(), ModelTransform.origin(2.0F, 1.0F, 6.0F));

        ModelPartData rleg22 = rrotation8.addChild("rleg22", ModelPartBuilder.create().uv(146, 142).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(1.0F, -1.0F, 0.0F));

        ModelPartData rleg23 = rleg22.addChild("rleg23", ModelPartBuilder.create().uv(56, 131).mirrored().cuboid(0.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -2.0F, 0.0F));

        ModelPartData rleg24 = rleg23.addChild("rleg24", ModelPartBuilder.create().uv(158, 96).mirrored().cuboid(0.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(88, 83).mirrored().cuboid(-4.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(3.0F, 1.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -18.0F, -4.0F));

        ModelPartData abdomen = body.addChild("abdomen", ModelPartBuilder.create().uv(19, 72).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -3.0F, 2.0F));

        ModelPartData stinger = abdomen.addChild("stinger", ModelPartBuilder.create().uv(156, 134).cuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(0.0F, -2.0F, -10.0F, 0.0F, 10.0F, 15.0F, new Dilation(0.0F))
                .uv(156, 142).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 3.0F, 11.0F));

        ModelPartData prosoma = body.addChild("prosoma", ModelPartBuilder.create().uv(96, 68).cuboid(-2.0F, -1.0F, -7.0F, 4.0F, 4.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -2.0F, 3.0F));

        ModelPartData chelicerae = prosoma.addChild("chelicerae", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 2.0F, -7.0F));

        ModelPartData leftchel = chelicerae.addChild("leftchel", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-0.5F, -0.5F, -3.5F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.5F, -0.5F, 0.5F));

        ModelPartData fang = leftchel.addChild("fang", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, -3.0F, -6.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-0.5F, 1.5F, -3.5F));

        ModelPartData rightchel = chelicerae.addChild("rightchel", ModelPartBuilder.create().uv(0, 22).cuboid(-0.5F, -0.5F, -3.5F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5F, -0.5F, 0.5F));

        ModelPartData fang2 = rightchel.addChild("fang2", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, -3.0F, -6.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, 1.5F, -3.5F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(ShelobiteScuttlerRenderState state) {
        super.setAngles(state);

        this.idleAnimation.apply(state.idleAnimationState, state.age, 0.75f);
        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.25F, 2.5F);
        this.biteAnimation.apply(state.walkAnimationState, state.age, 1.25f);
        this.pounceAnimation.apply(state.pounceAnimationState, state.age, 1.1f);
    }
}