package net.sevenstars.middleearth.client.model.equipment.chest.backAttachments;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.minecraft.client.model.ModelPart;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.BipedEntityRenderStateAccess;
import net.sevenstars.middleearth.utils.ToRad;

public class CloakCapeModel extends ChestplateAddonModel {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;
    private final ModelPart capeLow;
    private final ModelPart capeShoulder;
    private final ModelPart rightArmShoulderCape;
    private final ModelPart leftArmShoulderCape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("backAttachment");
        this.capeLow = root.getChild("body").getChild("backAttachment").getChild("cape_low");
        this.capeShoulder = root.getChild("body").getChild("cape_shoulder");
        this.rightArmShoulderCape = root.getChild("right_arm").getChild("right_arm_shoulder_cape");
        this.leftArmShoulderCape = root.getChild("left_arm").getChild("left_arm_shoulder_cape");
    }

    @Override
    public void setAngles(BipedEntityRenderState bipedEntityRenderState) {
        BipedEntityRenderStateAccess renderStateAccess = ((BipedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess != null) {
            this.cape.traverse().forEach(ModelPart::resetTransform);
            Vec3d currentVelocity = renderStateAccess.getVelocity();
            if(currentVelocity == null) currentVelocity = new Vec3d(0, 0, 0);

            double sqrVel = currentVelocity.length();
            double speed = (sqrVel * 0.65f) + Math.sqrt(Math.abs(bipedEntityRenderState.limbSwingAmplitude)) * 0.35f;
            double degree;

            if (bipedEntityRenderState.isInSneakingPose) {
                //this.cape.originY = 0.0f;
                //this.cape.originZ = 0.0f;
                degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
            } else {
                //this.cape.originY = 0;
                //this.cape.originZ = 0.0f;
                degree = (MAX_ANGLE_CLOAK * speed);
            }
            degree = Math.max(0.0F, degree);
            degree = Math.min(MAX_ANGLE_CLOAK, degree);

            double result = renderStateAccess.getVelocity().dotProduct(currentVelocity);

            if(result > 0) {
                this.cape.pitch = ToRad.ex(degree);
            }
        }
    }
}
