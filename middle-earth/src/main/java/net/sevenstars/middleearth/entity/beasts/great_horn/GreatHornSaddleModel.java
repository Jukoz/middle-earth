package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class GreatHornSaddleModel extends GreatHornModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart frontBody;
    private final ModelPart saddle;
    private final ModelPart headNeck;
    private final ModelPart neck;
    private final ModelPart topHead;
    private final ModelPart headStall;
    private final ModelPart reins;

    public GreatHornSaddleModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.frontBody = this.frontHalf.getChild("front_body");
        this.saddle = this.frontHalf.getChild("saddle");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.neck = headNeck.getChild("neck");
        this.topHead = headNeck.getChild("top_head");
        this.headStall = this.topHead.getChild("head_stall");
        this.reins = this.topHead.getChild("reins");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 8.0F, -10.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -8.5F, 12.0F));

        ModelPartData front_half = body.addChild("front_half", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, -13.0F));
        ModelPartData front_body = front_half.addChild("front_body", ModelPartBuilder.create().uv(0, 15).cuboid(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.5F, 2.5F));

        ModelPartData saddle = front_half.addChild("saddle", ModelPartBuilder.create()
                .uv(88, 50).cuboid(-7.0F, -1.0F, 1.0F, 14.0F, 22.0F, 6.0F, new Dilation(0.0F))
                .uv(84, 18).cuboid(-7.0F, -2.0F, -7.0F, 14.0F, 23.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, -6.0F, 9.0F));

        ModelPartData head_neck = front_half.addChild("head_neck", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, -3.0F));

        ModelPartData neck = head_neck.addChild("neck", ModelPartBuilder.create().uv(53, 53)
                .cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F,
                        new Dilation(0.2F)), ModelTransform.origin(0.0F, -1.5F, -2.0F));

        ModelPartData top_head = head_neck.addChild("top_head", ModelPartBuilder.create().uv(51, 35)
                .cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.2F))
                .uv(52, 26).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.2F))
                .uv(86, 99).cuboid(3.8F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F))
                .uv(86, 91).cuboid(-3.8F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, -7.5F, 0.0F));

        ModelPartData head_stall = top_head.addChild("head_stall", ModelPartBuilder.create().uv(52, 26).cuboid(-2.5F, -11.5F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.2F))
                .uv(51, 35).cuboid(-3.5F, -14.5F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.2F))
                .uv(53, 53).cuboid(-3.5F, -7.5F, -4.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.2F)), ModelTransform.origin(0.0F, 7.5F, 0.0F));

        ModelPartData reins = top_head.addChild("reins", ModelPartBuilder.create().uv(86, 99).cuboid(3.8F, -1.0F, -1.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F))
                .uv(86, 91).cuboid(-3.8F, -1.0F, -1.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.0F, -6.0F));


        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(GreatHornEntityRenderState state) {
        super.setAngles(state);

        boolean showSaddle = state.saddle != ItemStack.EMPTY;
        saddle.hidden = !showSaddle;
        headStall.hidden = !showSaddle;
        reins.hidden = !showSaddle;

        if(!state.hasRider) {
            reins.pitch = -12.5f * 0.017453292F;
        } else {
            reins.pitch = 0f;
        }
    }
}
