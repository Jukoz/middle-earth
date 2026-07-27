package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BroadhoofGoatModel extends HierarchicalModel<BroadhoofGoatEntity> {
    private final ModelPart broadhoofGoat;
    private final ModelPart head;
    private final ModelPart hair;
    private final ModelPart horns;
    private final ModelPart wildBeard;
    private final ModelPart brushedBeard;
    private final ModelPart[] leftHorns = new ModelPart[BroadhoofGoatHorns.values().length];
    private final ModelPart[] rightHorns = new ModelPart[BroadhoofGoatHorns.values().length];

    public BroadhoofGoatModel(ModelPart root) {
        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(PartNames.BODY).getChild(PartNames.HEAD);

        this.wildBeard = this.head.getChild("wild_beard");
        this.brushedBeard = this.head.getChild("brushed_beard");

        this.hair = this.head.getChild("hair");
        this.horns = this.head.getChild("horns");

        this.leftHorns[0] = horns.getChild("tiny_left_horn");
        this.rightHorns[0] = horns.getChild("tiny_right_horn");

        this.leftHorns[1] = horns.getChild("normal_left_horn");
        this.rightHorns[1] = horns.getChild("normal_right_horn");

        this.leftHorns[2] = horns.getChild("long_left_horn");
        this.rightHorns[2] = horns.getChild("long_right_horn");

        this.leftHorns[3] = horns.getChild("curly_left_horn");
        this.rightHorns[3] = horns.getChild("curly_right_horn");

        this.leftHorns[4] = horns.getChild("swirly_left_horn");
        this.rightHorns[4] = horns.getChild("swirly_right_horn");

        this.leftHorns[5] = horns.getChild("wide_left_horn");
        this.rightHorns[5] = horns.getChild("wide_right_horn");

        this.leftHorns[6] = horns.getChild("huge_left_horn");
        this.rightHorns[6] = horns.getChild("huge_right_horn");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot();
        PartDefinition broadhoof_goat = root.getChild("broadhoof_goat");
        PartDefinition body = broadhoof_goat.getChild(PartNames.BODY);
        PartDefinition head = body.getChild(PartNames.HEAD);

        PartDefinition head_cube = head.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(2.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(-5.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));


        PartDefinition body_cube = body.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(2, 48).addBox(-6.0F, -9.0F, -11.0F, 12.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-7.0F, -11.0F, -12.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_r1 = body.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(86, 1).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 9.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition wild_beard = head.getChild("wild_beard");
        PartDefinition wild_beard_cube = wild_beard.addOrReplaceChild("wild_beard_cube", CubeListBuilder.create().texOffs(3, 47).mirror().addBox(0.0F, -5.0F, -2.0F, 0.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.9232F, 0.0F, 3.1321F));

        PartDefinition brushed_beard = head.getChild("brushed_beard");
        PartDefinition brushed_beard_cube = brushed_beard.addOrReplaceChild("brushed_beard_cube", CubeListBuilder.create().texOffs(54, 31).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 10.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition neck_r1 = head.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(36, 2).addBox(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.1213F, -1.636F, 0.7854F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition horns = head.getChild("horns");

        PartDefinition long_left_horn = horns.getChild("long_left_horn");
        PartDefinition long_right_horn = horns.getChild("long_right_horn");
        PartDefinition long_left_horn_cube = long_left_horn.addOrReplaceChild("long_left_horn_cube", CubeListBuilder.create().texOffs(0, 113).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.0F, 2.0F, 1.4443F, 0.0338F, 0.2597F));
        PartDefinition long_right_horn_cube = long_right_horn.addOrReplaceChild("long_right_horn_cube", CubeListBuilder.create().texOffs(0, 113).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, -1.0F, 2.0F, 1.4443F, -0.0338F, -0.2597F));

        PartDefinition normal_left_horn = horns.getChild("normal_left_horn");
        PartDefinition normal_right_horn = horns.getChild("normal_right_horn");
        PartDefinition normal_left_horn_cube = normal_left_horn.addOrReplaceChild("normal_left_horn_cube", CubeListBuilder.create().texOffs(23, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition normal_right_horn_cube = normal_right_horn.addOrReplaceChild("normal_right_horn_cube", CubeListBuilder.create().texOffs(23, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition tiny_left_horn = horns.getChild("tiny_left_horn");
        PartDefinition tiny_right_horn = horns.getChild("tiny_right_horn");
        PartDefinition tiny_left_horn_cube = tiny_left_horn.addOrReplaceChild("tiny_left_horn_cube", CubeListBuilder.create().texOffs(28, 5).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition tiny_right_horn_cube = tiny_right_horn.addOrReplaceChild("tiny_right_horn_cube", CubeListBuilder.create().texOffs(28, 5).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition curly_left_horn = horns.getChild("curly_left_horn");
        PartDefinition curly_right_horn = horns.getChild("curly_right_horn");
        PartDefinition curly_left_horn5_r1 = curly_left_horn.addOrReplaceChild("curly_left_horn5_r1", CubeListBuilder.create().texOffs(58, 107).addBox(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 102).addBox(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(59, 95).addBox(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 90).addBox(1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 82).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition curly_right_horn5_r1 = curly_right_horn.addOrReplaceChild("curly_right_horn5_r1", CubeListBuilder.create().texOffs(37, 108).addBox(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 102).addBox(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(37, 95).addBox(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(35, 90).addBox(3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(37, 82).addBox(7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition swirly_left_horn = horns.getChild("swirly_left_horn");
        PartDefinition swirly_right_horn = horns.getChild("swirly_right_horn");
        PartDefinition swirly_left_horn_tip_r1 = swirly_left_horn.addOrReplaceChild("swirly_left_horn_tip_r1", CubeListBuilder.create().texOffs(21, 80).addBox(0.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(1, 81).addBox(0.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition swirly_right_horn_tip_r1 = swirly_right_horn.addOrReplaceChild("swirly_right_horn_tip_r1", CubeListBuilder.create().texOffs(21, 94).addBox(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(1, 95).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition wide_left_horn = horns.getChild("wide_left_horn");
        PartDefinition wide_right_horn = horns.getChild("wide_right_horn");
        PartDefinition wide_left_horn_cube = wide_left_horn.addOrReplaceChild("wide_left_horn_cube", CubeListBuilder.create(), PartPose.offsetAndRotation(13.0F, 0.6F, -0.1F, 0.2012F, -0.2955F, 0.2578F));
        PartDefinition wide_right_horn_cube = wide_right_horn.addOrReplaceChild("wide_right_horn_cube", CubeListBuilder.create(), PartPose.offsetAndRotation(9.0F, 0.6F, -0.1F, 0.2012F, 0.2955F, -0.2578F));
        PartDefinition wide_left_horn_tip_r1 = wide_left_horn_cube.addOrReplaceChild("wide_left_horn_tip_r1", CubeListBuilder.create().texOffs(59, 117).mirror().addBox(-11.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(35, 119).mirror().addBox(-21.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 115).mirror().addBox(-21.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition wide_right_horn_tip_r1 = wide_right_horn_cube.addOrReplaceChild("wide_right_horn_tip_r1", CubeListBuilder.create().texOffs(59, 117).addBox(9.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(35, 119).addBox(11.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 115).addBox(18.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition huge_left_horn = horns.getChild("huge_left_horn");
        PartDefinition huge_right_horn = horns.getChild("huge_right_horn");
        PartDefinition huge_left_horn_cube = huge_left_horn.addOrReplaceChild("huge_left_horn_cube", CubeListBuilder.create(), PartPose.offsetAndRotation(12.6F, 1.0F, -0.5F, 0.0576F, -0.2575F, 0.287F));
        PartDefinition huge_right_horn_cube = huge_right_horn.addOrReplaceChild("huge_right_horn_cube", CubeListBuilder.create(), PartPose.offsetAndRotation(9.4F, 1.0F, -0.5F, 0.0576F, 0.2575F, -0.287F));
        PartDefinition huge_left_horn4_r1 = huge_left_horn_cube.addOrReplaceChild("huge_left_horn4_r1", CubeListBuilder.create().texOffs(85, 88).addBox(-1.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(86, 95).addBox(-1.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(67, 94).addBox(-1.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(73, 109).addBox(-1.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition huge_right_horn5_r1 = huge_right_horn_cube.addOrReplaceChild("huge_right_horn5_r1", CubeListBuilder.create().texOffs(85, 88).mirror().addBox(-2.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 95).mirror().addBox(-2.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(67, 94).mirror().addBox(-2.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(73, 109).mirror().addBox(-2.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));


        PartDefinition left_front_leg = broadhoof_goat.getChild("left_front_leg");
        PartDefinition left_hind_leg = broadhoof_goat.getChild("left_hind_leg");
        PartDefinition right_front_leg = broadhoof_goat.getChild("right_front_leg");
        PartDefinition right_hind_leg = broadhoof_goat.getChild("right_hind_leg");

        PartDefinition left_front_leg_cube = left_front_leg.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(43, 17).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_hind_leg_cube = left_hind_leg.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(61, 17).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_front_leg_cube = right_front_leg.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(43, 17).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_hind_leg_cube = right_hind_leg.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(61, 17).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }



    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition broadhoof_goat = modelPartData.addOrReplaceChild("broadhoof_goat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = broadhoof_goat.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 2.0F));

        PartDefinition head = body.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition wild_beard = head.addOrReplaceChild("wild_beard", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -4.0F));

        PartDefinition brushed_beard = head.addOrReplaceChild("brushed_beard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(100, 91).addBox(-4.0F, -2.0F, -1.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -9.0F, -9.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.offset(-11.0F, -11.0208F, -7.4645F));

        PartDefinition long_left_horn = horns.addOrReplaceChild("long_left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0f));
        PartDefinition long_right_horn = horns.addOrReplaceChild("long_right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0f));

        PartDefinition normal_left_horn = horns.addOrReplaceChild("normal_left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.0F, 0.0F));
        PartDefinition normal_right_horn = horns.addOrReplaceChild("normal_right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        PartDefinition tiny_left_horn = horns.addOrReplaceChild("tiny_left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -0F, 0F, 0F, 0.0F, 0.0F));
        PartDefinition tiny_right_horn = horns.addOrReplaceChild("tiny_right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        PartDefinition curly_left_horn = horns.addOrReplaceChild("curly_left_horn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition curly_right_horn = horns.addOrReplaceChild("curly_right_horn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition swirly_left_horn = horns.addOrReplaceChild("swirly_left_horn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition swirly_right_horn = horns.addOrReplaceChild("swirly_right_horn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wide_left_horn = horns.addOrReplaceChild("wide_left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, -0F, 0F, 0F, 0F));
        PartDefinition wide_right_horn = horns.addOrReplaceChild("wide_right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition huge_left_horn = horns.addOrReplaceChild("huge_left_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        PartDefinition huge_right_horn = horns.addOrReplaceChild("huge_right_horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition left_front_leg = broadhoof_goat.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.0F, -9.0F, -6.0F));
        PartDefinition left_hind_leg = broadhoof_goat.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(3.0F, -9.0F, 7.0F));
        PartDefinition right_front_leg = broadhoof_goat.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, -9.0F, -6.0F));
        PartDefinition right_hind_leg = broadhoof_goat.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, -9.0F, 7.0F));

        return modelData;
    }

    @Override
    public void setupAnim(BroadhoofGoatEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        for(int i = 0 ; i < BroadhoofGoatHorns.values().length; i++) {
            this.leftHorns[i].visible = entity.getHorns().getId() == i && entity.hasLeftHorn() && !entity.isBaby();
            this.rightHorns[i].visible = entity.getHorns().getId() == i && entity.hasRightHorn() && !entity.isBaby();
        }

        this.hair.visible = entity.hasHair();

        this.wildBeard.visible = !entity.hasBrushedBeard();
        this.brushedBeard.visible = entity.hasBrushedBeard();

        if(!entity.isSprinting()
                && !(entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting())
                && !entity.isCharging()) {
            this.animateWalk(BroadhoofGoatAnimations.WALK, limbSwing, limbSwingAmount, 4.0F, 4.0F);
        }
        else {
            this.animateWalk(BroadhoofGoatAnimations.RUN, limbSwing, limbSwingAmount, 1.2F, 1.2F);
        }

        this.animate(entity.idleAnimationState, BroadhoofGoatAnimations.EAT, ageInTicks);
        this.animate(entity.attackAnimationState, BroadhoofGoatAnimations.RAM_ATTACK, ageInTicks);
        this.animate(entity.startSittingAnimationState, BroadhoofGoatAnimations.LAY_DOWN, ageInTicks);
        this.animate(entity.stopSittingAnimationState, BroadhoofGoatAnimations.STAND_UP, ageInTicks);
        this.animate(entity.sittingAnimationState, BroadhoofGoatAnimations.LYING, ageInTicks);
        this.animate(entity.chargeAnimationState, BroadhoofGoatAnimations.CHARGE_ATTACK, ageInTicks);
        this.animate(entity.jumpAnimationState, BroadhoofGoatAnimations.JUMP, ageInTicks);

    }

    @Override
    public ModelPart root() {
        return this.broadhoofGoat;
    }
}
