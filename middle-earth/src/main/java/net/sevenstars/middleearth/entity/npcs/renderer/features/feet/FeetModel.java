package net.sevenstars.middleearth.entity.npcs.renderer.features.feet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;

@Environment(EnvType.CLIENT)
public class FeetModel extends EntityModel<NpcEntityRenderState> {
    public final ModelPart toeCubeLeft;
    public final ModelPart toeCubeRight;

    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public FeetModel(ModelPart modelPart) {
        super(modelPart);

        this.leftLeg = modelPart.getChild("left_leg");
        this.rightLeg = modelPart.getChild("right_leg");

        this.toeCubeLeft = leftLeg.getChild("feet");
        this.toeCubeRight = rightLeg.getChild("feet");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        var rightLeg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(16,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE), ModelTransform.origin(-1.9F, 12.0F, 0.0F));
        var leftLeg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16,16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,  Dilation.NONE), ModelTransform.origin(1.9F, 12.0F, 0.0F));

        rightLeg.addChild("feet",
                                ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(0,0,0,4,2,2, Dilation.NONE),
                                ModelTransform.origin(-2f,10f,-4f));

        leftLeg.addChild("feet",
                                ModelPartBuilder.create().uv(0, 4).cuboid(0,0,0,4,2,2, Dilation.NONE),
                                ModelTransform.origin(-2f,10f,-4f));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        float g = state.limbSwingAnimationProgress;
        float h = state.limbSwingAmplitude;

        this.rightLeg.pitch = MathHelper.cos(g * 0.6662F) * 1.4F * h / state.limbAmplitudeInverse;
        this.leftLeg.pitch = MathHelper.cos(g * 0.6662F + (float)Math.PI) * 1.4F * h / state.limbAmplitudeInverse;
        this.rightLeg.yaw = 0.005F;
        this.leftLeg.yaw = -0.005F;
        this.rightLeg.roll = 0.005F;
        this.leftLeg.roll = -0.005F;
        if (state.hasVehicle) {
            this.rightLeg.pitch = -1.4137167F;
            this.rightLeg.yaw = ((float)Math.PI / 10F);
            this.rightLeg.roll = 0.07853982F;
            this.leftLeg.pitch = -1.4137167F;
            this.leftLeg.yaw = (-(float)Math.PI / 10F);
            this.leftLeg.roll = -0.07853982F;
        }
    }
}
