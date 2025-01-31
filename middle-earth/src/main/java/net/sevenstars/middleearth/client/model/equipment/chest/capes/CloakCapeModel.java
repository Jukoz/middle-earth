package net.sevenstars.middleearth.client.model.equipment.chest.capes;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.utils.ToRad;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class CloakCapeModel  extends ChestplateAddonModel {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;
    private final ModelPart capeLow;
    private final ModelPart capeShoulder;
    private final ModelPart rightArmShoulderCape;
    private final ModelPart leftArmShoulderCape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
        this.capeLow = root.getChild("body").getChild("cape").getChild("cape_low");
        this.capeShoulder = root.getChild("body").getChild("cape_shoulder");
        this.rightArmShoulderCape = root.getChild("right_arm").getChild("right_arm_shoulder_cape");
        this.leftArmShoulderCape = root.getChild("left_arm").getChild("left_arm_shoulder_cape");
    }

    //TODO make this work cause this shit will not work
    @Override
    public void setAngles(BipedEntityRenderState bipedEntityRenderState) {
        super.setAngles(bipedEntityRenderState);
        this.cape.traverse().forEach(ModelPart::resetTransform);
        Vec3d velocity = bipedEntityRenderState.positionOffset;
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(bipedEntityRenderState.limbFrequency)) * 0.4f;
        double degree;

        if (bipedEntityRenderState.isInSneakingPose) {
            this.cape.pivotZ = 0.0f;
            this.cape.pivotY = 0.0f;
            degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
        } else {
            this.cape.pivotZ = 0;
            this.cape.pivotY = 0.0f;
            degree = (MAX_ANGLE_CLOAK * speed);
        }
        degree = Math.max(0.0F, degree);
        degree = Math.min(MAX_ANGLE_CLOAK, degree);

        double result = bipedEntityRenderState.positionOffset.dotProduct(velocity);

        if(result > 0) {
            this.cape.pitch = ToRad.ex(degree);
        }
    }
}
