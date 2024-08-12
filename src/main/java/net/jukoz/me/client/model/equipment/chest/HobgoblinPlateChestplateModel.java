package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class HobgoblinPlateChestplateModel<T extends LivingEntity> extends ChestplateAddonModel<T> {

    public HobgoblinPlateChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData spears = body.addChild("spears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, -0.5F));

        ModelPartData rightspear = spears.addChild("rightspear", ModelPartBuilder.create(), ModelTransform.of(-1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, -0.3491F));

        ModelPartData Skull3 = rightspear.addChild("Skull3", ModelPartBuilder.create().uv(40, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -18.5F, -0.2F, 0.0F, 0.5236F, 0.0F));

        ModelPartData Skull7 = rightspear.addChild("Skull7", ModelPartBuilder.create().uv(40, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -27.5F, -0.2F, 0.0F, 0.5236F, 0.0F));

        ModelPartData cloth2 = rightspear.addChild("cloth2", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.3181F, -14.5411F, 1.0122F, 0.1745F, 0.0F, 0.3491F));

        ModelPartData spears2 = rightspear.addChild("spears2", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -0.5F, -3.7F));

        ModelPartData cube_r1 = spears2.addChild("cube_r1", ModelPartBuilder.create().uv(16, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData leftspear = spears.addChild("leftspear", ModelPartBuilder.create(), ModelTransform.of(1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, 0.3491F));

        ModelPartData Skull6 = leftspear.addChild("Skull6", ModelPartBuilder.create().uv(40, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -27.5F, -0.2F, 0.0F, 0.5236F, 0.0F));

        ModelPartData rib = leftspear.addChild("rib", ModelPartBuilder.create().uv(39, 51).cuboid(-4.0F, -5.4F, -2.0F, 8.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.5F, -0.2F));

        ModelPartData cloth5 = leftspear.addChild("cloth5", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.6819F, -19.5411F, 1.0122F, 0.1745F, 0.0F, -0.3491F));

        ModelPartData spears6 = leftspear.addChild("spears6", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -0.5F, -3.7F));

        ModelPartData cube_r2 = spears6.addChild("cube_r2", ModelPartBuilder.create().uv(8, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData centrespear = spears.addChild("centrespear", ModelPartBuilder.create(), ModelTransform.of(0.0F, 3.0F, 2.7F, -0.1309F, 0.0F, 0.0F));

        ModelPartData Skull8 = centrespear.addChild("Skull8", ModelPartBuilder.create().uv(40, 0).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 10.0F, -0.2F));

        ModelPartData Skull9 = centrespear.addChild("Skull9", ModelPartBuilder.create(), ModelTransform.of(0.0F, -18.5F, -0.2F, 0.0F, 0.5236F, 0.0F));

        ModelPartData rib3 = centrespear.addChild("rib3", ModelPartBuilder.create().uv(36, 51).cuboid(-5.0F, -5.4F, -1.0F, 10.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.5F, -0.2F));

        ModelPartData spears3 = centrespear.addChild("spears3", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -0.5F, -3.7F));

        ModelPartData cube_r3 = spears3.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));


        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_spike", ModelPartBuilder.create().uv(0, 45).cuboid(0.0F, 14.3F, 0.0F, 4.0F, 19.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -21.0F, 0.0F));
        ModelPartData left_arm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_spike", ModelPartBuilder.create().uv(8, 45).mirrored().cuboid(-4.0F, 14.3F, 0.0F, 4.0F, 19.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, -21.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}