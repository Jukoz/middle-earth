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

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
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
                degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
            } else {
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
