package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;

public class WargSaddleModel extends WargModel {
    private final ModelPart warg;
    private final ModelPart saddle;
    private final ModelPart bodyFur;
    private final ModelPart mainBody;
    private final ModelPart head;
    private final ModelPart waitTail;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart backBody;

    public WargSaddleModel(ModelPart root) {
        super(root);

        this.warg = root.getChild("root");
        ModelPart body = this.warg.getChild(PartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        this.saddle = bodyNoLegs.getChild("saddle");
        this.bodyFur = bodyNoLegs.getChild("body_fur");
        this.mainBody = bodyNoLegs.getChild("main_body");
        this.head = bodyNoLegs.getChild(PartNames.HEAD);
        this.waitTail = bodyNoLegs.getChild("wait_tail");
        this.rightFrontLeg = upperBody.getChild("right_front_leg");
        this.leftFrontLeg = upperBody.getChild("left_front_leg");
        this.backBody = body.getChild("back_body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getEmptyModelData();
        PartDefinition bodyNoLegs = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs");

        PartDefinition saddle = bodyNoLegs.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.offset(-9.5F, -3.75F, -4.5F));

        saddle.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 12).addBox(-2.5F, -0.5F, -7.0F, 11.0F, 16.0F, 11.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cloth = saddle.addOrReplaceChild("cloth", CubeListBuilder.create(), PartPose.offset(-1.0F, -2.0F, 2.0F));
        cloth.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 44).addBox(-2.5F, -0.5F, -9.0F, 11.0F, 16.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.0F, 0.25F, 0.0F, 0.0F, -1.5708F, 0.0F));

        saddle.addOrReplaceChild("saddle_addons", CubeListBuilder.create(), PartPose.offset(-6.0F, -2.0F, 2.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }

    public void renderSaddle(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
        boolean bodyFurVisible = this.bodyFur.visible;
        boolean mainBodyVisible = this.mainBody.visible;
        boolean headVisible = this.head.visible;
        boolean waitTailVisible = this.waitTail.visible;
        boolean rightFrontLegVisible = this.rightFrontLeg.visible;
        boolean leftFrontLegVisible = this.leftFrontLeg.visible;
        boolean backBodyVisible = this.backBody.visible;

        this.bodyFur.visible = false;
        this.mainBody.visible = false;
        this.head.visible = false;
        this.waitTail.visible = false;
        this.rightFrontLeg.visible = false;
        this.leftFrontLeg.visible = false;
        this.backBody.visible = false;

        try {
            super.renderToBuffer(matrices, vertices, light, overlay);
        }
        finally {
            this.bodyFur.visible = bodyFurVisible;
            this.mainBody.visible = mainBodyVisible;
            this.head.visible = headVisible;
            this.waitTail.visible = waitTailVisible;
            this.rightFrontLeg.visible = rightFrontLegVisible;
            this.leftFrontLeg.visible = leftFrontLegVisible;
            this.backBody.visible = backBodyVisible;
        }
    }
}
