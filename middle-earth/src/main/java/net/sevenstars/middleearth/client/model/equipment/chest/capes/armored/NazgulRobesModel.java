package net.sevenstars.middleearth.client.model.equipment.chest.capes.armored;

import net.sevenstars.middleearth.client.model.equipment.chest.capes.CloakCapeModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class NazgulRobesModel<T extends LivingEntity>  extends CloakCapeModel<T> {

    public NazgulRobesModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("cape_shoulder", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -23.5F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.1F)),
                ModelTransform.pivot(0.0F, 23.0F, 0.016F));

        body.addChild("fur", ModelPartBuilder.create().uv(24, 66).cuboid(-9.5F, -2.0F, -3.75F, 19.0F, 7.0F, 7.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 0.0f, 0.0f));

        ModelPartData cape = body.addChild("cape", ModelPartBuilder.create()
                .uv(53, 32).mirrored().cuboid(-6.5F, -1.0F, -2F, 13.0F, 13.0F, 5.0F, new Dilation(0.2F)).mirrored(false)
                .uv(53, 94).mirrored().cuboid(-6.5F, -1.0F, -2F, 13.0F, 13.0F, 5.0F, new Dilation(0.19F)).mirrored(false)
                .uv(0, 32).mirrored().cuboid(-9F, -1.0F, -3F, 18.0F, 13.0F, 6.0F, new Dilation(0.4F)).mirrored(false)
                .uv(0, 94).mirrored().cuboid(-9F, -1.0F, -3F, 18.0F, 13.0F, 6.0F, new Dilation(0.39F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        cape.addChild("cape_low", ModelPartBuilder.create()
                .uv(53, 50).mirrored().cuboid(-6.5F, -0.8F, -2F, 13.0F, 9.0F, 5.0F, new Dilation(0.2F)).mirrored(false)
                .uv(53, 112).mirrored().cuboid(-6.5F, -0.8F, -2F, 13.0F, 9.0F, 5.0F, new Dilation(0.19F)).mirrored(false)
                .uv(0, 51).mirrored().cuboid(-9F, -0.8F, -3F, 18.0F, 9.0F, 6.0F, new Dilation(0.4F)).mirrored(false)
                .uv(0, 113).mirrored().cuboid(-9F, -0.8F, -3F, 18.0F, 9.0F, 6.0F, new Dilation(0.39F)).mirrored(false), ModelTransform.pivot(-0.0F, 13.1558F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_shoulder_cape", ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)), ModelTransform.pivot(-0.0F, -0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_shoulder_cape", ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(0.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)).mirrored(false), ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_leg.addChild("right_leg", ModelPartBuilder.create().uv(72, 16).cuboid(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        left_leg.addChild("left_leg", ModelPartBuilder.create().uv(56, 16).mirrored().cuboid(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.1F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
