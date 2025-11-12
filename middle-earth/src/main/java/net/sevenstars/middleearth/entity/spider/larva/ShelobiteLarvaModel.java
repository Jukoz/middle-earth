package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteLarvaModel extends EntityModel<ShelobiteScuttlerRenderState> {
    private final ModelPart root;

    private final Animation walkingAnimation;
    private final Animation biteAnimation;

    public ShelobiteLarvaModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");

        this.walkingAnimation = ShelobiteLarvaAnimations.SHELOBITE_LARVA_WALK.createAnimation(root);
        this.biteAnimation = ShelobiteLarvaAnimations.SHELOBITE_LARVA_BITE.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 23.5F, 5.0F));

        ModelPartData abdomen = root.addChild("abdomen", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -1.5F, -3.0F));

        ModelPartData cube_r1 = abdomen.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -1.5F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.2F, 0.5F, 0.1745F, 0.0F, 0.0F));

        ModelPartData core = root.addChild("core", ModelPartBuilder.create().uv(0, 11).cuboid(-1.5F, -0.95F, -1.2F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -1.05F, -5.8F));

        ModelPartData leg_left = core.addChild("leg_left", ModelPartBuilder.create(), ModelTransform.origin(1.5F, -0.45F, 0.3F));

        ModelPartData leg_left_front = leg_left.addChild("leg_left_front", ModelPartBuilder.create(), ModelTransform.of(-0.2617F, 0.65F, 0.0804F, -1.5708F, 1.0472F, 0.0F));

        ModelPartData cube_r2 = leg_left_front.addChild("cube_r2", ModelPartBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.2182F, 0.0F));

        ModelPartData leg_left_center_front = leg_left.addChild("leg_left_center_front", ModelPartBuilder.create(), ModelTransform.of(-0.2617F, 0.65F, 0.0804F, -1.6411F, 0.6474F, -0.2534F));

        ModelPartData cube_r3 = leg_left_center_front.addChild("cube_r3", ModelPartBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_left_center_back = leg_left.addChild("leg_left_center_back", ModelPartBuilder.create(), ModelTransform.of(-0.2617F, 0.65F, 0.0804F, -1.5327F, -0.1704F, -0.2214F));

        ModelPartData cube_r4 = leg_left_center_back.addChild("cube_r4", ModelPartBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_left_back = leg_left.addChild("leg_left_back", ModelPartBuilder.create(), ModelTransform.of(-0.2617F, 0.65F, 0.0804F, -1.4338F, -0.5522F, -0.257F));

        ModelPartData cube_r5 = leg_left_back.addChild("cube_r5", ModelPartBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right = core.addChild("leg_right", ModelPartBuilder.create(), ModelTransform.origin(-1.5F, -0.45F, 0.3F));

        ModelPartData leg_right_front = leg_right.addChild("leg_right_front", ModelPartBuilder.create(), ModelTransform.of(0.2617F, 0.65F, 0.0804F, -1.5708F, -1.0472F, 0.0F));

        ModelPartData cube_r6 = leg_right_front.addChild("cube_r6", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, -0.2182F, 0.0F));

        ModelPartData leg_right_center_front = leg_right.addChild("leg_right_center_front", ModelPartBuilder.create(), ModelTransform.of(0.2617F, 0.65F, 0.0804F, -1.6411F, -0.6474F, 0.2534F));

        ModelPartData cube_r7 = leg_right_center_front.addChild("cube_r7", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right_center_back = leg_right.addChild("leg_right_center_back", ModelPartBuilder.create(), ModelTransform.of(0.2617F, 0.65F, 0.0804F, -1.5327F, 0.1704F, 0.2214F));

        ModelPartData cube_r8 = leg_right_center_back.addChild("cube_r8", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg_right_back = leg_right.addChild("leg_right_back", ModelPartBuilder.create(), ModelTransform.of(0.2617F, 0.65F, 0.0804F, -1.4338F, 0.5522F, 0.257F));

        ModelPartData cube_r9 = leg_right_back.addChild("cube_r9", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ShelobiteScuttlerRenderState state) {
        super.setAngles(state);

        this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 3F, 2.5F);
        this.biteAnimation.apply(state.biteAnimationState, state.age, 1.25f);
    }
}