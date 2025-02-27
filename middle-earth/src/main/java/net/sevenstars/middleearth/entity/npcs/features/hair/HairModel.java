package net.sevenstars.middleearth.entity.npcs.features.hair;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class HairModel extends EntityModel<NpcEntityRenderState> {
    public static final String BEARD = "beard";
    public static final String BEARD2 = "beard2";
    public static final String BEARD_TIP = "beard_tip";
    public static final String NORI_BEARD_CENTER = "nori_beard_center";
    public static final String NORI_BEARD_LEFT = "nori_beard_left";
    public static final String NORI_BEARD_RIGHT = "nori_beard_right";
    public static final float BEARD_PITCH_ANGLE = -0.174f;
    public final ModelPart hair;
    public final ModelPart frontAddon;
    public final ModelPart backAddon;

    public HairModel(ModelPart modelPart) {
        super(modelPart);

        this.hair = modelPart.getChild("hair");
        this.frontAddon = hair.getChild("hair_front_addon");
        this.backAddon = hair.getChild("hair_back_addon");

    }


    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();


        /*
        head.addChild(NORI_BEARD_CENTER, ModelPartBuilder.create().uv(0, 16).cuboid(-1, 1.5f, -4.5f, 2.0f, 5.0f, 2.0f, dilation.add(0.15f)),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE - 0.2f, 0, 0));
        head.addChild(NORI_BEARD_LEFT, ModelPartBuilder.create().uv(0, 16).cuboid(1f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, -0.55f));
        head.addChild(NORI_BEARD_RIGHT, ModelPartBuilder.create().uv(0, 16).cuboid(-3f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                ModelTransform.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, 0.55f));
        */

        ModelPartData hairGroup = modelPartData.addChild("hair");
        ModelPartData frontAddon = hairGroup.addChild("hair_front_addon");
        ModelPartData backAddon = hairGroup.addChild("hair_back_addon");

        frontAddon.addChild("hair_addon_front_core", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, 0f, -4.0f, 8, 12, 2, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        frontAddon.addChild("hair_addon_front_hat", ModelPartBuilder.create().uv(20, 0).cuboid(-4.0f, 1.3f, -3.9f, 8, 12, 2, Dilation.NONE.add(0.35f)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));


        frontAddon.addChild("hair_addon_back_core", ModelPartBuilder.create().uv(0, 14).cuboid(-4.0f, 0f, 2.0f, 8, 12, 2, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.frontAddon.pitch = state.pitch * 0.017453292F;
        this.frontAddon.yaw = state.yawDegrees * 0.017453292F;

        this.backAddon.pitch = state.pitch * 0.017453292F;
        this.backAddon.yaw = state.yawDegrees * 0.017453292F;

        if (bl) {
            this.frontAddon.pitch = -0.7853982F;
            this.backAddon.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.frontAddon.pitch = MathHelper.lerpAngleRadians(f, this.frontAddon.pitch, -0.7853982F);
            this.backAddon.pitch = MathHelper.lerpAngleRadians(f, this.backAddon.pitch, -0.7853982F);
        }
    }
}
