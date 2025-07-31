package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerAnimations;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteLarvaModel extends EntityModel<ShelobiteScuttlerRenderState> {
    private final ModelPart root;
    
    public ShelobiteLarvaModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 23.5F, 5.0F));

        ModelPartData e1 = root.addChild("e1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 12.0F, -4.0F));

        ModelPartData abdomen = e1.addChild("abdomen", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -13.1007F, -0.5251F));

        ModelPartData thorns_left_r1 = abdomen.addChild("thorns_left_r1", ModelPartBuilder.create().uv(16, 4).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -0.8993F, 1.5251F, 0.0456F, 0.1685F, -1.3051F));

        ModelPartData thorns_left_r2 = abdomen.addChild("thorns_left_r2", ModelPartBuilder.create().uv(16, 4).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -0.8993F, 1.5251F, 0.0456F, -0.1685F, 1.3051F));

        ModelPartData cube_r1 = abdomen.addChild("cube_r1", ModelPartBuilder.create().uv(1, 1).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5993F, 1.0251F, 0.1745F, 0.0F, 0.0F));

        ModelPartData head = e1.addChild("head", ModelPartBuilder.create().uv(2, 6).cuboid(-1.0F, -0.95F, -1.2F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(13, 0).cuboid(-2.0F, -2.05F, -2.3F, 4.0F, 4.0F, 4.0F, new Dilation(-0.95F)), ModelTransform.origin(0.0F, -13.05F, -1.8F));

        ModelPartData leg_left = head.addChild("leg_left", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 13.05F, 2.4F));

        ModelPartData leg_left_front = leg_left.addChild("leg_left_front", ModelPartBuilder.create(), ModelTransform.of(0.7383F, -12.85F, -2.0196F, -1.5708F, 1.0472F, 0.0F));

        ModelPartData cube_r2 = leg_left_front.addChild("cube_r2", ModelPartBuilder.create().uv(5, 13).cuboid(-0.1608F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.2182F, 0.0F));

        ModelPartData leg_left_front2 = leg_left.addChild("leg_left_front2", ModelPartBuilder.create(), ModelTransform.of(0.7383F, -12.85F, -2.0196F, -1.6411F, 0.6474F, -0.2534F));

        ModelPartData cube_r3 = leg_left_front2.addChild("cube_r3", ModelPartBuilder.create().uv(5, 13).cuboid(-0.1608F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_left_front3 = leg_left.addChild("leg_left_front3", ModelPartBuilder.create(), ModelTransform.of(0.7383F, -12.85F, -2.0196F, -1.5327F, -0.1704F, -0.2214F));

        ModelPartData cube_r4 = leg_left_front3.addChild("cube_r4", ModelPartBuilder.create().uv(5, 13).cuboid(-0.1608F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_left_front4 = leg_left.addChild("leg_left_front4", ModelPartBuilder.create(), ModelTransform.of(0.7383F, -12.85F, -2.0196F, -1.4338F, -0.5522F, -0.257F));

        ModelPartData cube_r5 = leg_left_front4.addChild("cube_r5", ModelPartBuilder.create().uv(5, 13).cuboid(-0.1608F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right = head.addChild("leg_right", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 13.05F, 2.4F));

        ModelPartData leg_right_front = leg_right.addChild("leg_right_front", ModelPartBuilder.create(), ModelTransform.of(-0.7383F, -12.85F, -2.0196F, -1.5708F, -1.0472F, 0.0F));

        ModelPartData cube_r6 = leg_right_front.addChild("cube_r6", ModelPartBuilder.create().uv(5, 13).mirrored().cuboid(-1.8392F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, -0.2182F, 0.0F));

        ModelPartData leg_right_front2 = leg_right.addChild("leg_right_front2", ModelPartBuilder.create(), ModelTransform.of(-0.7383F, -12.85F, -2.0196F, -1.6411F, -0.6474F, 0.2534F));

        ModelPartData cube_r7 = leg_right_front2.addChild("cube_r7", ModelPartBuilder.create().uv(5, 13).mirrored().cuboid(-1.8392F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right_front3 = leg_right.addChild("leg_right_front3", ModelPartBuilder.create(), ModelTransform.of(-0.7383F, -12.85F, -2.0196F, -1.5327F, 0.1704F, 0.2214F));

        ModelPartData cube_r8 = leg_right_front3.addChild("cube_r8", ModelPartBuilder.create().uv(5, 13).mirrored().cuboid(-1.8392F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right_front4 = leg_right.addChild("leg_right_front4", ModelPartBuilder.create(), ModelTransform.of(-0.7383F, -12.85F, -2.0196F, -1.4338F, 0.5522F, 0.257F));

        ModelPartData cube_r9 = leg_right_front4.addChild("cube_r9", ModelPartBuilder.create().uv(5, 13).mirrored().cuboid(-1.8392F, -1.5F, -0.0804F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ShelobiteScuttlerRenderState state) {
        super.setAngles(state);

        //this.idleAnimation.apply(state.idleAnimationState, state.age, 0.75f);
        //this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2F, 2.5F);
        //this.biteAnimation.apply(state.walkAnimationState, state.age, 1.25f);
    }
}