package net.sevenstars.middleearth.entity.npcs.features.nose;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class NoseModel  extends EntityModel<NpcEntityRenderState>  {
    public final ModelPart cubeNose;
    public final ModelPart largeCubeNose;

    public NoseModel(ModelPart modelPart) {
        super(modelPart);
        this.cubeNose = modelPart.getChild("nose_cube");
        this.largeCubeNose = modelPart.getChild("nose_large_cube");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData cubeNosePart = modelPartData.addChild("nose_cube", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        cubeNosePart.addChild("cube", ModelPartBuilder.create().uv(0, 2).cuboid(-1.0f, -4f, -5f, 2, 2, 1, Dilation.NONE), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData largeCubeNosePart = modelPartData.addChild("nose_large_cube", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        largeCubeNosePart.addChild("cube", ModelPartBuilder.create().uv(0, 6).cuboid(-1.0f, -3f, -6f, 2, 4, 2, Dilation.NONE), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.cubeNose.pitch = state.pitch * 0.017453292F;
        this.largeCubeNose.pitch = state.pitch * 0.017453292F;

        this.cubeNose.yaw = state.relativeHeadYaw * 0.017453292F;
        this.largeCubeNose.yaw = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.cubeNose.pitch = -0.7853982F;
            this.largeCubeNose.pitch = -0.7853982F;

        } else if (f > 0.0F) {
            this.cubeNose.pitch = MathHelper.lerpAngleRadians(f, this.cubeNose.pitch, -0.7853982F);
            this.largeCubeNose.pitch = MathHelper.lerpAngleRadians(f, this.largeCubeNose.pitch, -0.7853982F);
        }
    }
}
