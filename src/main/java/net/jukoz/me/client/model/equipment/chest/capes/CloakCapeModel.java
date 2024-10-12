package net.jukoz.me.client.model.equipment.chest.capes;

import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class CloakCapeModel<T extends LivingEntity>  extends ChestplateAddonModel<T> {
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

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.cape.traverse().forEach(ModelPart::resetTransform);
        Vec3d velocity = entity.getVelocity();
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(limbDistance)) * 0.4f;
        double degree;

        if (entity.isInSneakingPose()) {
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

        double result = entity.getRotationVector().dotProduct(velocity);

        if(result > 0) {
            this.cape.pitch = ToRad.ex(degree);
        }
    }

}
