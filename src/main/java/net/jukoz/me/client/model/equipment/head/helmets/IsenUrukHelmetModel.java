package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class IsenUrukHelmetModel<T extends LivingEntity> extends HelmetAddonModel<T> {


    public IsenUrukHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));


        ModelPartData Human2 = modelPartData.addChild("Human2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 25.0F, 0.0F));

        ModelPartData HumanRoot = Human2.addChild("HumanRoot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        ModelPartData Addon3 = head.addChild("Addon3", ModelPartBuilder.create().uv(-9, 30).cuboid(-7.5F, -0.7F, -0.5F, 15.0F, 0.0F, 9.0F, new Dilation(0.0F))
                .uv(-9, 39).cuboid(-6.5F, -1.95F, -6.5F, 13.0F, 0.0F, 9.0F, new Dilation(0.0F))
                .uv(0, -12).cuboid(0.0F, -11.5F, -6.0F, 0.0F, 10.0F, 12.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-4.0F, 1.8F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        ModelPartData spikes_r1 = Addon3.addChild("spikes_r1", ModelPartBuilder.create().uv(40, 50).mirrored().cuboid(-2.3F, 2.5F, -7.0F, 5.0F, 0.0F, 14.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-6.75F, -0.65F, -1.0F, 0.0F, 0.0F, -0.5672F));

        ModelPartData spikes_r2 = Addon3.addChild("spikes_r2", ModelPartBuilder.create().uv(40, 50).cuboid(-2.7F, 2.5F, -7.0F, 5.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(6.75F, -0.65F, -1.0F, 0.0F, 0.0F, 0.5672F));

        ModelPartData fan2_r1 = Addon3.addChild("fan2_r1", ModelPartBuilder.create().uv(0, 17).cuboid(-13.0F, -6.5F, 0.0F, 26.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.875F, 2.3F, -0.4363F, 0.0F, 0.0F));

        ModelPartData fan1_r1 = Addon3.addChild("fan1_r1", ModelPartBuilder.create().uv(36, 29).cuboid(-7.0F, -6.5F, 0.0F, 14.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 1.0F, -0.5672F, 0.0F, 0.0F));

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