package net.sevenstars.middleearth.entity.npcs.features.nose;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class NoseModel  extends EntityModel<NpcEntityRenderState>  {
    public final ModelPart cubeNose;

    public NoseModel(ModelPart modelPart) {
        super(modelPart);
        this.cubeNose = modelPart.getChild("nose_cube");
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData cubeNosePart = modelPartData.addChild("nose_cube");

        cubeNosePart.addChild("cube", ModelPartBuilder.create().uv(0, 2).cuboid(-1.0f, -4f, -5f, 2, 2, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.cubeNose.pitch = state.pitch * 0.017453292F;

        this.cubeNose.yaw = state.yawDegrees * 0.017453292F;

        if (bl) {
            this.cubeNose.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.cubeNose.pitch = MathHelper.lerpAngleRadians(f, this.cubeNose.pitch, -0.7853982F);
        }
    }
}
