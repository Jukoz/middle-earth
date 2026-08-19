package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

final class BackportedCowModel<T extends Entity> extends CowModel<T> {
    private static final float BABY_HEAD_Y_OFFSET = 8.0F / 16.0F;
    private static final float BABY_HEAD_Z_OFFSET = 6.0F / 16.0F;
    private static final float BABY_BODY_SCALE = 0.5F;
    private static final float BABY_BODY_Y_OFFSET = 24.0F / 16.0F;

    BackportedCowModel(ModelPart root) {
        super(root);
    }

    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer consumer,
            int packedLight,
            int packedOverlay,
            int color
    ) {
        if (!this.young) {
            super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, color);
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.0F, BABY_HEAD_Y_OFFSET, BABY_HEAD_Z_OFFSET);
        this.head.render(poseStack, consumer, packedLight, packedOverlay, color);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.scale(BABY_BODY_SCALE, BABY_BODY_SCALE, BABY_BODY_SCALE);
        poseStack.translate(0.0F, BABY_BODY_Y_OFFSET, 0.0F);
        this.body.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.rightHindLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.leftHindLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.rightFrontLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.leftFrontLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }
}
