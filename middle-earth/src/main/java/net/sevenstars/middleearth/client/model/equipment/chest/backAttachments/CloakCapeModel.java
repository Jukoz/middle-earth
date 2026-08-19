package net.sevenstars.middleearth.client.model.equipment.chest.backAttachments;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.utils.ToRad;

public class CloakCapeModel extends ChestplateAddonModel {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbAngle, float limbDistance,
                          float animationProgress, float headYaw, float headPitch) {
        this.cape.getAllParts().forEach(ModelPart::resetPose);
        Vec3 velocity = entity.getDeltaMovement();
        double speed = (velocity.length() * 0.65F) + Math.sqrt(Math.abs(limbDistance)) * 0.35F;
        double degree;

        if (entity.isCrouching()) {
            degree = 5.0F + (speed * (MAX_ANGLE_CLOAK / 2.0F));
        } else {
            degree = MAX_ANGLE_CLOAK * speed;
        }

        degree = Math.max(0.0F, Math.min(MAX_ANGLE_CLOAK, degree));
        if (entity.getLookAngle().dot(velocity) > 0.0D) {
            this.cape.xRot = ToRad.ex(degree);
        }
    }
}
