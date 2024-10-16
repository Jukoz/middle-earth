package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class HobgoblinPlateHelmetModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public HobgoblinPlateHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Addon = head.addChild("Addon", ModelPartBuilder.create().uv(28, 0).cuboid(-9.0F, -12.0F, -5.05F, 18.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData headthing2 = Addon.addChild("headthing2", ModelPartBuilder.create().uv(54, 49).cuboid(-0.5F, -3.8868F, -0.8666F, 4.0F, 14.0F, 1.0F, new Dilation(0.1F)), ModelTransform.pivot(-1.5F, -10.5976F, -4.4512F));
        headthing2.addChild("cube_r1", ModelPartBuilder.create().uv(38, 53).mirrored().cuboid(-4.0F, -5.5F, 0.0F, 8.0F, 11.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.2366F, 0.5132F, 0.3928F, 0.0F, -0.3491F, 0.0F));
        headthing2.addChild("cube_r2", ModelPartBuilder.create().uv(38, 53).cuboid(-5.0F, -4.0F, 0.0F, 8.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.3312F, -0.9868F, 0.0562F, 0.0F, 0.3491F, 0.0F));
        headthing2.addChild("cube_r3", ModelPartBuilder.create().uv(24, 10).cuboid(1.0F, -11.0F, -0.5F, 0.0F, 14.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 3.1132F, -0.3666F, 0.3054F, 0.0F, 0.0F));
        headthing2.addChild("bone", ModelPartBuilder.create().uv(32, 52).cuboid(0.3F, -4.5F, -2.0F, 2.0F, 11.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.6416F, 0.5132F, 1.3314F, 0.0F, -0.3491F, 0.0F));
        headthing2.addChild("bone2", ModelPartBuilder.create().uv(32, 52).mirrored().cuboid(1.675F, -4.5F, -1.925F, 2.0F, 11.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.3584F, 0.5132F, 2.6314F, 0.0F, 0.3491F, 0.0F));
        Addon.addChild("headthing3", ModelPartBuilder.create().uv(20, 12).mirrored().cuboid(-11.0F, -21.0F, 0.0F, 22.0F, 18.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(28, 53).cuboid(-0.5F, -18.0F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 44).cuboid(-9.5F, -8.0F, -0.5F, 19.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

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