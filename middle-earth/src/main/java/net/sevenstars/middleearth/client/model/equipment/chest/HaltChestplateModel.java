package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class HaltChestplateModel<T extends LivingEntity> extends ChestplateAddonModel<T> {

    public HaltChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        right_arm.addChild("right_pauldron", ModelPartBuilder.create().uv(0, 38).cuboid(-3.0F, -2.0F, -1.92F, 4.0F, 12.0F, 4.0F, new Dilation(1.3F)), ModelTransform.pivot(-0.58F, -1.0F, 0.0F));
        right_arm.addChild("right_halt", ModelPartBuilder.create().uv(0, 50).cuboid(-1.0F, -6.25F, -3.5F, 0.0F, 7.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        left_arm.addChild("left_pauldron", ModelPartBuilder.create().uv(16, 38).mirrored().cuboid(-1.0F, -2.0F, -1.92F, 4.0F, 12.0F, 4.0F, new Dilation(1.3F)).mirrored(false), ModelTransform.pivot(0.5F, -1.0F, 0.0F));
        left_arm.addChild("left_halt", ModelPartBuilder.create().uv(17, 50).cuboid(11.0F, -6.25F, -3.5F, 0.0F, 7.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-10.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}