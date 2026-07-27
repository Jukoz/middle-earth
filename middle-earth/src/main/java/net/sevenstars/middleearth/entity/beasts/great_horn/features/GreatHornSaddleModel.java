package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleModel extends GreatHornModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart frontBody;
    private final ModelPart saddle;
    private final ModelPart headNeck;
    private final ModelPart topHead;
    private final ModelPart reinsHead;
    private final ModelPart reins;

    public GreatHornSaddleModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.frontBody = this.frontHalf.getChild("front_body");
        this.saddle = this.frontBody.getChild("saddle");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.topHead = headNeck.getChild("top_head");
        this.reinsHead = this.topHead.getChild("reins_head");
        this.reins = this.topHead.getChild("reins");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -10.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 12.0F));

        PartDefinition front_half = body.addOrReplaceChild("front_half", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -13.0F));

        PartDefinition front_body = front_half.addOrReplaceChild("front_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 2.5F));

        PartDefinition saddle = front_body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(88, 50)
                .addBox(-7.0F, -1.0F, 2.0F, 14.0F, 22.0F, 6.0F, new CubeDeformation(0.3F))
                .texOffs(84, 18).addBox(-7.0F, -2.0F, -6.6F, 14.0F, 23.0F, 8.0F,
                        new CubeDeformation(0.3F)), PartPose.offset(0.0F, -6.5F, 6.5F));

        PartDefinition seat_back_r1 = saddle.addOrReplaceChild("seat_back_r1", CubeListBuilder.create().texOffs(49, 78)
                .addBox(-4.5F, -4.5F, -1.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 1.0F, 8.4F, -0.2182F, 0.0F, 0.0F));

        PartDefinition head_neck = front_half.addOrReplaceChild("head_neck", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -3.0F));

        PartDefinition top_head = head_neck.addOrReplaceChild("top_head", CubeListBuilder.create(), PartPose.offset(0.0F, -7.5F, 0.0F));

        root.addOrReplaceChild("front_left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("front_right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("back_left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("back_right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_body = front_half.addOrReplaceChild("back_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        back_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        top_head.addOrReplaceChild("right_antler", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        top_head.addOrReplaceChild("left_antler", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        top_head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        top_head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        top_head.addOrReplaceChild("beard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition reins_head = top_head.addOrReplaceChild("reins_head", CubeListBuilder.create().texOffs(51, 35)
                .addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.3F))
                .texOffs(52, 26).addBox(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.3F))
                .texOffs(53, 53).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 18.0F, 7.0F,
                        new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition reins = top_head.addOrReplaceChild("reins", CubeListBuilder.create().texOffs(86, 99)
                .addBox(3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(86, 91).addBox(-3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -7.0F));

        return LayerDefinition.create(modelData, 128, 160);
    }

    @Override
    public void setupAnim(GreatHornEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        boolean showSaddle = entity.isSaddled();
        saddle.skipDraw = !showSaddle;
        reinsHead.skipDraw = !showSaddle;
        reins.skipDraw = !showSaddle;

        if(!entity.hasExactlyOnePlayerPassenger()) {
            reins.xRot = -12.5f * 0.017453292F;
        } else {
            reins.xRot = 0f;
        }
    }
}
