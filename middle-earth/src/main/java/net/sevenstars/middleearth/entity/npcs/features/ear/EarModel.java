package net.sevenstars.middleearth.entity.npcs.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class EarModel  extends EntityModel<NpcEntityRenderState> {
    public final ModelPart cubeEarGroup;
    public final ModelPart pointyEarGroup;
    public final ModelPart pointyEarLeft;
    public final ModelPart pointyEarRight;

    public EarModel(ModelPart modelPart) {
        super(modelPart);

        this.cubeEarGroup = modelPart.getChild("cube_ears");
        this.pointyEarGroup = modelPart.getChild("pointy_ears");
        this.pointyEarLeft = pointyEarGroup.getChild("ears_pointy_left");
        this.pointyEarRight = pointyEarGroup.getChild("ears_pointy_right");
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData cube_ears = modelPartData.addChild("cube_ears");

        cube_ears.addChild("ears_cube_left", ModelPartBuilder.create().uv(12, 0).cuboid(-5.0f, -4f, -1f, 1, 2, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        cube_ears.addChild("ears_cube_right", ModelPartBuilder.create().uv(12, 0).cuboid(4.0f, -4f, -1f, 1, 2, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData pointy_ears = modelPartData.addChild("pointy_ears");

        pointy_ears.addChild("ears_pointy_right", ModelPartBuilder.create().uv(0, -3).cuboid(0f,0f,0f, 0, 2, 6, Dilation.NONE), ModelTransform.pivot(-3.75f, -3f, -2f));
        pointy_ears.addChild("ears_pointy_left", ModelPartBuilder.create().uv(0, -3).cuboid(0f,0f,0f, 0, 2, 6, Dilation.NONE), ModelTransform.pivot(3.75f, -3f, -2f));


        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.cubeEarGroup.yaw = state.yawDegrees * 0.017453292F;
        this.cubeEarGroup.pitch = state.pitch * 0.017453292F;

        this.pointyEarGroup.yaw = state.yawDegrees * 0.017453292F;
        this.pointyEarGroup.pitch = state.pitch * 0.017453292F;

        this.pointyEarLeft.yaw = 0.261799f;
        this.pointyEarRight.yaw = -0.261799f;

        this.pointyEarLeft.pitch = 1.0472f;
        this.pointyEarRight.pitch = 1.0472f;

        this.pointyEarLeft.roll = 0.523599f;
        this.pointyEarRight.roll = -0.523599f;

        if (bl) {
            this.cubeEarGroup.pitch = -0.7853982F;
            this.pointyEarGroup.pitch = -0.7853982F;

        } else if (f > 0.0F) {
            this.cubeEarGroup.pitch = MathHelper.lerpAngleRadians(f, this.cubeEarGroup.pitch, -0.7853982F);
            this.pointyEarGroup.pitch = MathHelper.lerpAngleRadians(f, this.pointyEarGroup.pitch, -0.7853982F);
        }
    }
}
