package net.jukoz.me.client.model.equipment.chest;

import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.Vec3d;

public class CloakCapeModel<T extends LivingEntity> extends BipedEntityModel<T> {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;
    private final ModelPart cape_shoulder;
    private final ModelPart right_arm_shoulder_cape;
    private final ModelPart left_arm_shoulder_cape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
        this.cape_shoulder = root.getChild("body").getChild("cape_shoulder");
        this.right_arm_shoulder_cape = root.getChild("right_arm").getChild("right_arm_shoulder_cape");
        this.left_arm_shoulder_cape = root.getChild("left_arm").getChild("left_arm_shoulder_cape");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("cape", ModelPartBuilder.create()
                .uv(0, 26).mirrored().cuboid(-6.0F, 0.0F, 3.0F, 12.0F, 22.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0, 0.5f, 0, 0.0F, 0.0F, 0F));
        body.addChild("cape_shoulder", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.1F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_shoulder_cape", ModelPartBuilder.create()
                .uv(24, 0).cuboid(-3.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_shoulder_cape", ModelPartBuilder.create()
                .uv(24, 0).mirrored().cuboid(-0.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        Vec3d velocity = entity.getVelocity();
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(limbDistance)) * 0.4f;
        double degree;

        if (entity.isInSneakingPose()) {
            this.cape.pivotZ = 0.15f;
            this.cape.pivotY = 0.15f;
            degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
        } else {
            this.cape.pivotZ = 0;
            this.cape.pivotY = 0.5f;
            degree = 5 + (MAX_ANGLE_CLOAK * speed);
        }
        degree = Math.max(7.5f, degree);
        degree = Math.min(MAX_ANGLE_CLOAK, degree);

        this.cape.pitch = ToRad.ex(degree);
    }
}
