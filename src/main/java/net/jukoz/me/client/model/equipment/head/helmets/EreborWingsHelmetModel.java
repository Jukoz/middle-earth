package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class EreborWingsHelmetModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public EreborWingsHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("eye_addon", ModelPartBuilder.create().uv(46, 63).cuboid(-4.5F, -3.5F, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData wings = head.addChild("wings", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.5F, 0.5F));

        wings.addChild("rightwing_r1", ModelPartBuilder.create().uv(32, 0).cuboid(-9.0585F, -4.0F, -6.3165F, 15.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.2624F, 4.1083F, 1.1849F, 0.9521F, 1.1082F));

        wings.addChild("leftwing_r1", ModelPartBuilder.create().uv(32, 0).mirrored().cuboid(-5.9415F, -4.0F, -6.3165F, 15.0F, 8.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -7.2624F, 4.1083F, 1.1849F, -0.9521F, -1.1082F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}