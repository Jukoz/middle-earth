package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import net.minecraft.client.model.*;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 8.0F, -10.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -8.5F, 12.0F));

        ModelPartData front_half = body.addChild("front_half", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -13.0F));

        ModelPartData front_body = front_half.addChild("front_body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.5F, 2.5F));

        ModelPartData saddle = front_body.addChild("saddle", ModelPartBuilder.create().uv(88, 50)
                .cuboid(-7.0F, -1.0F, 2.0F, 14.0F, 22.0F, 6.0F, new Dilation(0.3F))
                .uv(84, 18).cuboid(-7.0F, -2.0F, -6.6F, 14.0F, 23.0F, 8.0F,
                        new Dilation(0.3F)), ModelTransform.origin(0.0F, -6.5F, 6.5F));

        ModelPartData seat_back_r1 = saddle.addChild("seat_back_r1", ModelPartBuilder.create().uv(49, 78)
                .cuboid(-4.5F, -4.5F, -1.0F, 9.0F, 5.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(0.0F, 1.0F, 8.4F, -0.2182F, 0.0F, 0.0F));

        ModelPartData head_neck = front_half.addChild("head_neck", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, -3.0F));

        ModelPartData top_head = head_neck.addChild("top_head", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -7.5F, 0.0F));

        root.addChild("front_left_leg", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        root.addChild("front_right_leg", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        root.addChild("back_left_leg", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        root.addChild("back_right_leg", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData back_body = front_half.addChild("back_body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        back_body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        top_head.addChild("right_antler", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        top_head.addChild("left_antler", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        top_head.addChild("ear_left", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        top_head.addChild("ear_right", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        top_head.addChild("beard", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData reins_head = top_head.addChild("reins_head", ModelPartBuilder.create().uv(51, 35)
                .cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.3F))
                .uv(52, 26).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.3F))
                .uv(53, 53).cuboid(-3.5F, 0.0F, -4.0F, 7.0F, 18.0F, 7.0F,
                        new Dilation(0.3F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData reins = top_head.addChild("reins", ModelPartBuilder.create().uv(86, 99)
                .cuboid(3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F))
                .uv(86, 91).cuboid(-3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, -2.0F, -7.0F));

        return TexturedModelData.of(modelData, 128, 160);
    }

    @Override
    public void setAngles(GreatHornEntityRenderState state) {
        super.setAngles(state);

        boolean showSaddle = state.saddle != ItemStack.EMPTY;
        saddle.hidden = !showSaddle;
        reinsHead.hidden = !showSaddle;
        reins.hidden = !showSaddle;

        if(!state.hasRider) {
            reins.pitch = -12.5f * 0.017453292F;
        } else {
            reins.pitch = 0f;
        }
    }
}
