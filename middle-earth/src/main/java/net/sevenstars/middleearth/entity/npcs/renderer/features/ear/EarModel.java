package net.sevenstars.middleearth.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
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
        this.planeFlatLeft = this.ears.getChild("leftFlatEarPlane");
        this.planeFlatRight = this.ears.getChild("rightFlatEarPlane");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData planeFlatSides = modelPartData.addChild("ears", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3f, -1.0f));
        planeFlatSides.addChild("leftFlatEarPlane",
                ModelPartBuilder.create()
                        .uv(0, 6)
                        .cuboid(0, 0, 0.0F, 6.0F, 7.0F, 0.0F, Dilation.NONE),
                ModelTransform.rotation(0.0F, -0.35F, 0.0F)
                        .moveOrigin(4F, -5F, 0.0F));
        planeFlatSides.addChild("rightFlatEarPlane",
                ModelPartBuilder.create()
                        .uv(0, 6)
                        .cuboid(0, 0, 0.0F, 6.0F, 7.0F, 0.0F, Dilation.NONE)
                        .mirrored(),
                ModelTransform.rotation(0.0F, 0.35F, 0.0F)
                        .moveOrigin(-4F, -5F, 0.0F));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;

        this.ears.pitch = state.pitch * ((float)Math.PI / 180);
        this.ears.yaw = state.relativeHeadYaw * ((float)Math.PI / 180);
        if (bl) {
            this.ears.pitch = -0.7853982f;
        } else if (f > 0.0f) {
            this.ears.pitch = MathHelper.lerpAngleRadians(f, this.ears.pitch, -0.7853982f);
        }

        this.ears.yaw = state.relativeHeadYaw * 0.017453292F;
        this.ears.pitch = state.pitch * 0.017453292F;

        this.planeFlatLeft.yaw = (float)Math.toRadians(-20);
        this.planeFlatRight.yaw = (float)Math.toRadians(-160);
    }
}
