package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;

public class SpawnOfShelobModel extends HierarchicalModel<SpawnOfShelobEntity> {
    private final ModelPart root;

    public SpawnOfShelobModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition legscore = root.addOrReplaceChild("legscore", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -5.0F));

        PartDefinition rights = legscore.addOrReplaceChild("rights", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rrotation = rights.addOrReplaceChild("rrotation", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -4.5F));

        PartDefinition rleg = rrotation.addOrReplaceChild("rleg", CubeListBuilder.create().texOffs(150, 129).addBox(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(146, 135).addBox(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rleg2 = rleg.addOrReplaceChild("rleg2", CubeListBuilder.create().texOffs(50, 130).addBox(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -13.0F, 0.0F));

        PartDefinition rleg3 = rleg2.addOrReplaceChild("rleg3", CubeListBuilder.create().texOffs(147, 96).addBox(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 83).addBox(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 1.0F, 0.0F));

        PartDefinition rrotation1 = rights.addOrReplaceChild("rrotation1", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -1.0F));

        PartDefinition rleg4 = rrotation1.addOrReplaceChild("rleg4", CubeListBuilder.create().texOffs(52, 99).addBox(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rleg5 = rleg4.addOrReplaceChild("rleg5", CubeListBuilder.create().texOffs(144, 110).addBox(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 0.0F));

        PartDefinition rleg6 = rleg5.addOrReplaceChild("rleg6", CubeListBuilder.create().texOffs(84, 133).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -0.5F));

        PartDefinition rleg7 = rleg6.addOrReplaceChild("rleg7", CubeListBuilder.create().texOffs(0, 167).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 25).addBox(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 11.0F, 0.0F));

        PartDefinition rrotation2 = rights.addOrReplaceChild("rrotation2", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 3.0F));

        PartDefinition rleg8 = rrotation2.addOrReplaceChild("rleg8", CubeListBuilder.create().texOffs(52, 99).addBox(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rleg9 = rleg8.addOrReplaceChild("rleg9", CubeListBuilder.create().texOffs(144, 110).addBox(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 0.0F));

        PartDefinition rleg10 = rleg9.addOrReplaceChild("rleg10", CubeListBuilder.create().texOffs(84, 133).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -0.5F));

        PartDefinition rleg11 = rleg10.addOrReplaceChild("rleg11", CubeListBuilder.create().texOffs(0, 167).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 25).addBox(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 11.0F, 0.0F));

        PartDefinition rrotation3 = rights.addOrReplaceChild("rrotation3", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 6.5F));

        PartDefinition rleg12 = rrotation3.addOrReplaceChild("rleg12", CubeListBuilder.create().texOffs(150, 129).addBox(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(146, 135).addBox(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rleg13 = rleg12.addOrReplaceChild("rleg13", CubeListBuilder.create().texOffs(50, 130).addBox(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -13.0F, 0.0F));

        PartDefinition rleg14 = rleg13.addOrReplaceChild("rleg14", CubeListBuilder.create().texOffs(147, 96).addBox(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(84, 83).addBox(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 1.0F, 0.0F));

        PartDefinition lefts = legscore.addOrReplaceChild("lefts", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lrotation = lefts.addOrReplaceChild("lrotation", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -4.5F));

        PartDefinition lleg = lrotation.addOrReplaceChild("lleg", CubeListBuilder.create().texOffs(150, 129).mirror().addBox(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(146, 135).mirror().addBox(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lleg2 = lleg.addOrReplaceChild("lleg2", CubeListBuilder.create().texOffs(50, 130).mirror().addBox(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -13.0F, 0.0F));

        PartDefinition lleg3 = lleg2.addOrReplaceChild("lleg3", CubeListBuilder.create().texOffs(147, 96).mirror().addBox(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(84, 83).mirror().addBox(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 1.0F, 0.0F));

        PartDefinition lrotation2 = lefts.addOrReplaceChild("lrotation2", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -1.0F));

        PartDefinition lleg4 = lrotation2.addOrReplaceChild("lleg4", CubeListBuilder.create().texOffs(52, 99).mirror().addBox(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lleg5 = lleg4.addOrReplaceChild("lleg5", CubeListBuilder.create().texOffs(144, 110).mirror().addBox(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 0.0F, 0.0F));

        PartDefinition lleg6 = lleg5.addOrReplaceChild("lleg6", CubeListBuilder.create().texOffs(84, 133).mirror().addBox(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -15.0F, -0.5F));

        PartDefinition lleg7 = lleg6.addOrReplaceChild("lleg7", CubeListBuilder.create().texOffs(0, 167).mirror().addBox(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 25).mirror().addBox(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 11.0F, 0.0F));

        PartDefinition lrotation3 = lefts.addOrReplaceChild("lrotation3", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 3.0F));

        PartDefinition lleg8 = lrotation3.addOrReplaceChild("lleg8", CubeListBuilder.create().texOffs(52, 99).mirror().addBox(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lleg9 = lleg8.addOrReplaceChild("lleg9", CubeListBuilder.create().texOffs(144, 110).mirror().addBox(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 0.0F, 0.0F));

        PartDefinition lleg10 = lleg9.addOrReplaceChild("lleg10", CubeListBuilder.create().texOffs(84, 133).mirror().addBox(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -15.0F, -0.5F));

        PartDefinition lleg11 = lleg10.addOrReplaceChild("lleg11", CubeListBuilder.create().texOffs(0, 167).mirror().addBox(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 25).mirror().addBox(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 11.0F, 0.0F));

        PartDefinition lrotation4 = lefts.addOrReplaceChild("lrotation4", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 6.5F));

        PartDefinition lleg12 = lrotation4.addOrReplaceChild("lleg12", CubeListBuilder.create().texOffs(150, 129).mirror().addBox(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(146, 135).mirror().addBox(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lleg13 = lleg12.addOrReplaceChild("lleg13", CubeListBuilder.create().texOffs(50, 130).mirror().addBox(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -13.0F, 0.0F));

        PartDefinition lleg14 = lleg13.addOrReplaceChild("lleg14", CubeListBuilder.create().texOffs(147, 96).mirror().addBox(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(84, 83).mirror().addBox(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 1.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -1.0F));

        PartDefinition abdomen = body.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(6, 63).addBox(-8.0F, -6.0F, 0.0F, 16.0F, 9.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(5, 96).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 1.0F));

        PartDefinition stinger = abdomen.addOrReplaceChild("stinger", CubeListBuilder.create().texOffs(156, 134).addBox(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(0.0F, -2.0F, -10.0F, 0.0F, 10.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(156, 142).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 19.0F));

        PartDefinition thorns = abdomen.addOrReplaceChild("thorns", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = thorns.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(21, 36).mirror().addBox(-6.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -8.0F, 8.0F, 0.0F, 0.0F, 0.7854F));
        PartDefinition cube_r2 = thorns.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(21, 36).addBox(-3.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 8.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition cube_r3 = thorns.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-16, 217).mirror().addBox(-14.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -6.0F, 16.5F, 0.0F, 0.0F, 0.2618F));
        PartDefinition cube_r4 = thorns.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-16, 217).addBox(-9.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -6.0F, 16.5F, 0.0F, 0.0F, -0.2618F));

        PartDefinition prosoma = body.addOrReplaceChild("prosoma", CubeListBuilder.create().texOffs(88, 62).addBox(-4.0F, -2.0F, -15.0F, 8.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 3.0F));

        PartDefinition chelicerae = prosoma.addOrReplaceChild("chelicerae", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -13.0F));

        PartDefinition leftchel = chelicerae.addOrReplaceChild("leftchel", CubeListBuilder.create().texOffs(140, 80).mirror().addBox(-2.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -1.0F, 1.0F));

        PartDefinition leftooth = leftchel.addOrReplaceChild("leftooth", CubeListBuilder.create().texOffs(9, 130).addBox(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 154).mirror().addBox(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, -1.0F, -7.0F));

        PartDefinition leftjaw = leftchel.addOrReplaceChild("leftjaw", CubeListBuilder.create().texOffs(0, 132).mirror().addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 132).mirror().addBox(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 1.0F, -8.0F));

        PartDefinition rightchel = chelicerae.addOrReplaceChild("rightchel", CubeListBuilder.create().texOffs(140, 80).addBox(-1.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 1.0F));

        PartDefinition rightooth = rightchel.addOrReplaceChild("rightooth", CubeListBuilder.create().texOffs(9, 130).mirror().addBox(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 154).addBox(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, -7.0F));

        PartDefinition rightjaw = rightchel.addOrReplaceChild("rightjaw", CubeListBuilder.create().texOffs(0, 132).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(26, 132).addBox(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, -8.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(SpawnOfShelobEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        int climbingTicks = entity.getClimbingTicks();
        int leapingTicks = entity.getLeapingTicks();
        int croppedClimbingTicks = Math.min(ShelobiteScuttlerEntity.CLIMBING_TIME_TRANSITION, climbingTicks);
        float climbingPercentage = (float) croppedClimbingTicks / SpawnOfShelobEntity.CLIMBING_TIME_TRANSITION;

        int croppedLeapingTicks = Math.min(ShelobiteScuttlerEntity.LEAPING_TIME_TRANSITION, leapingTicks);
        float leapingPercentage = (float) croppedLeapingTicks / SpawnOfShelobEntity.LEAPING_TIME_TRANSITION;

        if(climbingTicks > 0 && climbingPercentage > leapingPercentage) {
            this.root.xRot = -1.5f * climbingPercentage;
            this.animateWalk(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK, ageInTicks, 0.4f, 1.75F, 2F);
            return;
        } else if(leapingTicks > 0 && leapingPercentage > climbingPercentage) {
            this.root.xRot = -0.8f * leapingPercentage;
            this.animateWalk(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK,
                    (float)leapingTicks / 3.1f, 0.75f, 2.2F, 2.5F);
            return;
        }

        if(limbSwingAmount <= 0.4) {
            this.animate(entity.idleAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_IDLE, ageInTicks, 0.75f);
        } else if(entity.blockAnimation.isStarted()) {
            this.animateWalk(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BLOCK,
                    limbSwing, limbSwingAmount, 2.2F, 2.25F);
        } else {
            this.animateWalk(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK,
                    limbSwing, limbSwingAmount, 2.25F, 2.5F);
        }

        if(entity.biteAnimation.isStarted()) {
            this.animate(entity.biteAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BITE, ageInTicks, 1.3f);
        }
        if(entity.pounceAnimation.isStarted()) {
            this.animate(entity.pounceAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_POUNCE, ageInTicks, 1.0f);
        }
    }
}
