package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class WizardHatModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public WizardHatModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData wizard = head.addChild("wizard", ModelPartBuilder.create().uv(0, 47).cuboid(-8.0F, -6.134F, -8.0F, 16.0F, 1.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.4F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData bone = wizard.addChild("bone", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.6F))
                .uv(32, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 4.0F, 8.0F, new Dilation(1.0F)),
                ModelTransform.of(0.0F, -0.325F, 0.2F, 0.0436F, 0.0F, 0.0F));

        ModelPartData hat = bone.addChild("hat", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.6F, -0.4F, -0.0175F, 0.0F, 0.0F));

        hat.addChild("wizard_0", ModelPartBuilder.create()
                .uv(24, 1).mirrored().cuboid(2.0F, -10.4886F, -2.2615F, 0.0F, 10.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
                .uv(46, 12).cuboid(0.0F, -5.4886F, -2.2615F, 4.0F, 5.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.of(-2.0F, -13.0143F, 1.5539F, -0.5672F, 0.0F, 0.0F));
        hat.addChild("wizard_1", ModelPartBuilder.create()
                .uv(0, 12).cuboid(-3.0F, -5.5F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.5F)),
                ModelTransform.of(0.0F, -9.5F, 0.0F, -0.0873F, 0.0F, 0.0F));

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