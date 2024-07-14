package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RohirricHelmetArmorAddonModel<T extends LivingEntity> extends HelmetAddonModel<T> {
    private static final float MAX_ANGLE_HAIR = 75f;

    public final ModelPart hair;

    public RohirricHelmetArmorAddonModel(ModelPart root) {
        super(root);
        hair = root.getChild("head").getChild("hair");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(30, 17)
                .cuboid(0.0F, -14.5F, -9.5F, 0.0F, 11.0F, 17.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        head.addChild("hair", ModelPartBuilder.create().uv(0, 27)
                        .cuboid(-1.0F, -1.7F, 0.0F, 2.0F, 3.0F, 18.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -9.0F, 5.0F, -1.5708F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 48);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        Vec3d velocity = entity.getVelocity();
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(limbDistance)) * 0.4f;
        double degree;

        degree = 5 + (MAX_ANGLE_HAIR * speed);
        degree = Math.max(2.5f, degree);
        degree = Math.min(MAX_ANGLE_HAIR, degree);

        if(headPitch < -0.75){
            degree -= 45;
        }else if(headPitch < -1.4){
            degree -= 5;
        }else {
            degree -= 90;
        }

        this.hair.pitch = ToRad.ex(degree);
    }
}