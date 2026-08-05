package net.sevenstars.middleearth.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;

public class EarModel extends EntityModel<NpcEntityRenderState> {
    public final ModelPart ears;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;

    public EarModel(ModelPart modelPart) {
        super(modelPart);

        this.ears = modelPart.getChild("ears");

        this.planeFlatLeft = this.ears.getChild("ear_left");
        this.planeFlatRight = this.ears.getChild("ear_right");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData ears = root.addChild("ears", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ears.addChild("ear_right",
                ModelPartBuilder.create().uv(0, 6).cuboid(3.5F, -8.0F, 2F, 7.0F, 6.0F, 0.0F, Dilation.NONE),
                ModelTransform.NONE);

        ears.addChild("ear_left",
                ModelPartBuilder.create().uv(0, 6).cuboid(3.5F, -8.0F, -2F, 7.0F, 6.0F, 0.0F, Dilation.NONE),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.ears.pitch = state.pitch * 0.017453292F;
        this.ears.yaw = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.ears.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.ears.pitch = MathHelper.lerpAngleRadians(f, this.ears.pitch, -0.7853982F);
        }

        this.planeFlatLeft.yaw = (float)Math.toRadians(-20);
        this.planeFlatRight.yaw = (float)Math.toRadians(-160);
    }
}
