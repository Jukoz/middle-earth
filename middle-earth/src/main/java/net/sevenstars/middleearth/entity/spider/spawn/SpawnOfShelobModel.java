package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class SpawnOfShelobModel extends EntityModel<SpawnOfShelobRenderState> {
    private final ModelPart root;

    private final Animation idleAnimation;
    private final Animation walkingAnimation;
    private final Animation biteAnimation;
    private final Animation walkingBlockAnimation;
    private final Animation pounceAnimation;

    public SpawnOfShelobModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");

        this.idleAnimation = SpawnOfShelobAnimations.SPAWN_OF_SHELOB_IDLE.createAnimation(root);
        this.walkingAnimation = SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK.createAnimation(root);
        this.biteAnimation = SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BITE.createAnimation(root);
        this.walkingBlockAnimation = SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BLOCK.createAnimation(root);
        this.pounceAnimation = SpawnOfShelobAnimations.SPAWN_OF_SHELOB_POUNCE.createAnimation(root);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 36.0F, 0.0F));

        ModelPartData legscore = root.addChild("legscore", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -18.0F, -5.0F));

        ModelPartData rights = legscore.addChild("rights", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rrotation = rights.addChild("rrotation", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, 0.0F, -4.5F));

        ModelPartData rleg = rrotation.addChild("rleg", ModelPartBuilder.create().uv(150, 129).cuboid(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(146, 135).cuboid(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg2 = rleg.addChild("rleg2", ModelPartBuilder.create().uv(50, 130).cuboid(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, -13.0F, 0.0F));

        ModelPartData rleg3 = rleg2.addChild("rleg3", ModelPartBuilder.create().uv(147, 96).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(84, 83).cuboid(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-9.0F, 1.0F, 0.0F));

        ModelPartData rrotation1 = rights.addChild("rrotation1", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, 0.0F, -1.0F));

        ModelPartData rleg4 = rrotation1.addChild("rleg4", ModelPartBuilder.create().uv(52, 99).cuboid(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg5 = rleg4.addChild("rleg5", ModelPartBuilder.create().uv(144, 110).cuboid(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 0.0F, 0.0F));

        ModelPartData rleg6 = rleg5.addChild("rleg6", ModelPartBuilder.create().uv(84, 133).cuboid(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -15.0F, -0.5F));

        ModelPartData rleg7 = rleg6.addChild("rleg7", ModelPartBuilder.create().uv(0, 167).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(112, 25).cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, 11.0F, 0.0F));

        ModelPartData rrotation2 = rights.addChild("rrotation2", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, 0.0F, 3.0F));

        ModelPartData rleg8 = rrotation2.addChild("rleg8", ModelPartBuilder.create().uv(52, 99).cuboid(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new Dilation(0.01F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg9 = rleg8.addChild("rleg9", ModelPartBuilder.create().uv(144, 110).cuboid(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 0.0F, 0.0F));

        ModelPartData rleg10 = rleg9.addChild("rleg10", ModelPartBuilder.create().uv(84, 133).cuboid(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -15.0F, -0.5F));

        ModelPartData rleg11 = rleg10.addChild("rleg11", ModelPartBuilder.create().uv(0, 167).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(112, 25).cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, 11.0F, 0.0F));

        ModelPartData rrotation3 = rights.addChild("rrotation3", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, 0.0F, 6.5F));

        ModelPartData rleg12 = rrotation3.addChild("rleg12", ModelPartBuilder.create().uv(150, 129).cuboid(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(146, 135).cuboid(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rleg13 = rleg12.addChild("rleg13", ModelPartBuilder.create().uv(50, 130).cuboid(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, -13.0F, 0.0F));

        ModelPartData rleg14 = rleg13.addChild("rleg14", ModelPartBuilder.create().uv(147, 96).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(84, 83).cuboid(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-9.0F, 1.0F, 0.0F));

        ModelPartData lefts = legscore.addChild("lefts", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData lrotation = lefts.addChild("lrotation", ModelPartBuilder.create(), ModelTransform.origin(3.0F, 0.0F, -4.5F));

        ModelPartData lleg = lrotation.addChild("lleg", ModelPartBuilder.create().uv(150, 129).mirrored().cuboid(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(146, 135).mirrored().cuboid(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData lleg2 = lleg.addChild("lleg2", ModelPartBuilder.create().uv(50, 130).mirrored().cuboid(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, -13.0F, 0.0F));

        ModelPartData lleg3 = lleg2.addChild("lleg3", ModelPartBuilder.create().uv(147, 96).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(84, 83).mirrored().cuboid(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(9.0F, 1.0F, 0.0F));

        ModelPartData lrotation2 = lefts.addChild("lrotation2", ModelPartBuilder.create(), ModelTransform.origin(3.0F, 0.0F, -1.0F));

        ModelPartData lleg4 = lrotation2.addChild("lleg4", ModelPartBuilder.create().uv(52, 99).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData lleg5 = lleg4.addChild("lleg5", ModelPartBuilder.create().uv(144, 110).mirrored().cuboid(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, 0.0F, 0.0F));

        ModelPartData lleg6 = lleg5.addChild("lleg6", ModelPartBuilder.create().uv(84, 133).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -15.0F, -0.5F));

        ModelPartData lleg7 = lleg6.addChild("lleg7", ModelPartBuilder.create().uv(0, 167).mirrored().cuboid(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(112, 25).mirrored().cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-1.0F, 11.0F, 0.0F));

        ModelPartData lrotation3 = lefts.addChild("lrotation3", ModelPartBuilder.create(), ModelTransform.origin(3.0F, 0.0F, 3.0F));

        ModelPartData lleg8 = lrotation3.addChild("lleg8", ModelPartBuilder.create().uv(52, 99).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData lleg9 = lleg8.addChild("lleg9", ModelPartBuilder.create().uv(144, 110).mirrored().cuboid(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, 0.0F, 0.0F));

        ModelPartData lleg10 = lleg9.addChild("lleg10", ModelPartBuilder.create().uv(84, 133).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -15.0F, -0.5F));

        ModelPartData lleg11 = lleg10.addChild("lleg11", ModelPartBuilder.create().uv(0, 167).mirrored().cuboid(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(112, 25).mirrored().cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-1.0F, 11.0F, 0.0F));

        ModelPartData lrotation4 = lefts.addChild("lrotation4", ModelPartBuilder.create(), ModelTransform.origin(3.0F, 0.0F, 6.5F));

        ModelPartData lleg12 = lrotation4.addChild("lleg12", ModelPartBuilder.create().uv(150, 129).mirrored().cuboid(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(146, 135).mirrored().cuboid(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData lleg13 = lleg12.addChild("lleg13", ModelPartBuilder.create().uv(50, 130).mirrored().cuboid(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, -13.0F, 0.0F));

        ModelPartData lleg14 = lleg13.addChild("lleg14", ModelPartBuilder.create().uv(147, 96).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(84, 83).mirrored().cuboid(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(9.0F, 1.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -18.0F, -1.0F));

        ModelPartData abdomen = body.addChild("abdomen", ModelPartBuilder.create().uv(6, 63).cuboid(-8.0F, -6.0F, 0.0F, 16.0F, 9.0F, 19.0F, new Dilation(0.0F))
                .uv(5, 96).cuboid(-3.0F, -8.0F, -2.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -4.0F, 1.0F));

        ModelPartData stinger = abdomen.addChild("stinger", ModelPartBuilder.create().uv(156, 134).cuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(0.0F, -2.0F, -10.0F, 0.0F, 10.0F, 15.0F, new Dilation(0.0F))
                .uv(156, 142).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 3.0F, 19.0F));

        ModelPartData thorns = abdomen.addChild("thorns", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = thorns.addChild("cube_r1", ModelPartBuilder.create().uv(21, 36).mirrored().cuboid(-6.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.0F, -8.0F, 8.0F, 0.0F, 0.0F, 0.7854F));
        ModelPartData cube_r2 = thorns.addChild("cube_r2", ModelPartBuilder.create().uv(21, 36).cuboid(-3.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -8.0F, 8.0F, 0.0F, 0.0F, -0.7854F));
        ModelPartData cube_r3 = thorns.addChild("cube_r3", ModelPartBuilder.create().uv(-16, 217).mirrored().cuboid(-14.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.0F, -6.0F, 16.5F, 0.0F, 0.0F, 0.2618F));
        ModelPartData cube_r4 = thorns.addChild("cube_r4", ModelPartBuilder.create().uv(-16, 217).cuboid(-9.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -6.0F, 16.5F, 0.0F, 0.0F, -0.2618F));

        ModelPartData prosoma = body.addChild("prosoma", ModelPartBuilder.create().uv(88, 62).cuboid(-4.0F, -2.0F, -15.0F, 8.0F, 6.0F, 15.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -4.0F, 3.0F));

        ModelPartData chelicerae = prosoma.addChild("chelicerae", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 1.0F, -13.0F));

        ModelPartData leftchel = chelicerae.addChild("leftchel", ModelPartBuilder.create().uv(140, 80).mirrored().cuboid(-2.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(2.0F, -1.0F, 1.0F));

        ModelPartData leftooth = leftchel.addChild("leftooth", ModelPartBuilder.create().uv(9, 130).cuboid(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new Dilation(0.0F))
                .uv(0, 154).mirrored().cuboid(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-0.5F, -1.0F, -7.0F));

        ModelPartData leftjaw = leftchel.addChild("leftjaw", ModelPartBuilder.create().uv(0, 132).mirrored().cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
                .uv(26, 132).mirrored().cuboid(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-0.5F, 1.0F, -8.0F));

        ModelPartData rightchel = chelicerae.addChild("rightchel", ModelPartBuilder.create().uv(140, 80).cuboid(-1.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, -1.0F, 1.0F));

        ModelPartData rightooth = rightchel.addChild("rightooth", ModelPartBuilder.create().uv(9, 130).mirrored().cuboid(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 154).cuboid(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -1.0F, -7.0F));

        ModelPartData rightjaw = rightchel.addChild("rightjaw", ModelPartBuilder.create().uv(0, 132).cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F))
                .uv(26, 132).cuboid(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, 1.0F, -8.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(SpawnOfShelobRenderState state) {
        super.setAngles(state);

        if(state.limbSwingAmplitude <= 0.4) {
            this.idleAnimation.apply(state.idleAnimationState, state.age, 0.75f);
        }
        if(state.blockAnimationState.isRunning()) {
            this.walkingBlockAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.2F, 2.25F);
        } else {
            this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.25F, 2.5F);
        }
        this.biteAnimation.apply(state.walkAnimationState, state.age, 1.25f);
        this.pounceAnimation.apply(state.pounceAnimationState, state.age, 1.0f);
    }
}