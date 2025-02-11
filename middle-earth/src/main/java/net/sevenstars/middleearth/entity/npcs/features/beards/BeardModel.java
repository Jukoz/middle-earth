package net.sevenstars.middleearth.entity.npcs.features.beards;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class BeardModel extends EntityModel<NpcEntityRenderState> {
    public static final String BEARD = "beard";
    public static final String BEARD2 = "beard2";
    public static final String BEARD_TIP = "beard_tip";
    public static final String NORI_BEARD_CENTER = "nori_beard_center";
    public static final String NORI_BEARD_LEFT = "nori_beard_left";
    public static final String NORI_BEARD_RIGHT = "nori_beard_right";
    public static final float BEARD_PITCH_ANGLE = -0.174f;
    public final ModelPart head;
    public BeardModel(ModelPart modelPart) {
        super(modelPart);
        this.head = modelPart.getChild("head");
    }


    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.1f)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.5F).add(0.1f)), ModelTransform.NONE);

        head.addChild(NORI_BEARD_CENTER, ModelPartBuilder.create().uv(0, 16).cuboid(-1, 1.5f, -4.5f, 2.0f, 5.0f, 2.0f, dilation.add(0.15f)),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE - 0.2f, 0, 0));
        head.addChild(NORI_BEARD_LEFT, ModelPartBuilder.create().uv(0, 16).cuboid(1f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, -0.55f));
        head.addChild(NORI_BEARD_RIGHT, ModelPartBuilder.create().uv(0, 16).cuboid(-3f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, 0.55f));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.head.pitch = state.pitch * 0.017453292F;
        this.head.yaw = state.yawDegrees * 0.017453292F;
        if (bl) {
            this.head.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.head.pitch = MathHelper.lerpAngleRadians(f, this.head.pitch, -0.7853982F);
        }
    }
}
