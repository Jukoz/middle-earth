package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
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
        ModelPart body = this.warg.getChild(EntityModelPartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        this.saddle = bodyNoLegs.getChild("saddle");
        this.bodyFur = bodyNoLegs.getChild("body_fur");
        this.mainBody = bodyNoLegs.getChild("main_body");
        this.head = bodyNoLegs.getChild(EntityModelPartNames.HEAD);
        this.waitTail = bodyNoLegs.getChild("wait_tail");
        this.rightFrontLeg = upperBody.getChild("right_front_leg");
        this.leftFrontLeg = upperBody.getChild("left_front_leg");
        this.backBody = body.getChild("back_body");

        // Only the saddle is drawn
        this.bodyFur.visible = false;
        this.mainBody.visible = false;
        this.head.visible = false;
        this.waitTail.visible = false;
        this.rightFrontLeg.visible = false;
        this.leftFrontLeg.visible = false;
        this.backBody.visible = false;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getEmptyModelData();
        ModelPartData bodyNoLegs = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild("body_no_legs");

        ModelPartData saddle = bodyNoLegs.addChild("saddle", ModelPartBuilder.create(), ModelTransform.origin(-9.5F, -3.75F, -4.5F));

        saddle.addChild("cube_r3", ModelPartBuilder.create().uv(26, 12).cuboid(-2.5F, -0.5F, -7.0F, 11.0F, 16.0F, 11.0F, new Dilation(0.4F)), ModelTransform.of(-1.0F, -2.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cloth = saddle.addChild("cloth", ModelPartBuilder.create(), ModelTransform.origin(-1.0F, -2.0F, 2.0F));
        cloth.addChild("cube_r4", ModelPartBuilder.create().uv(4, 44).cuboid(-2.5F, -0.5F, -9.0F, 11.0F, 16.0F, 12.0F, new Dilation(0.2F)), ModelTransform.of(-3.0F, 0.25F, 0.0F, 0.0F, -1.5708F, 0.0F));

        saddle.addChild("saddle_addons", ModelPartBuilder.create(), ModelTransform.origin(-6.0F, -2.0F, 2.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
