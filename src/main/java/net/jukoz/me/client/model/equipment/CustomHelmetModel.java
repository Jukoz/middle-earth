package net.jukoz.me.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class CustomHelmetModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public final ModelPart head;

    public final ModelPart hat;

    public final ModelPart chestHeadExtension;
    public final ModelPart rightArmHeadExtension;
    public final ModelPart leftArmHeadExtension;

    public CustomHelmetModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);

        this.head = root.getChild("head");
        this.hat = root.getChild("hat");

        this.chestHeadExtension = root.getChild("body").getChild("chestHeadExtension");
        this.rightArmHeadExtension = root.getChild("right_arm").getChild("rightArmHeadExtension");
        this.leftArmHeadExtension = root.getChild("left_arm").getChild("leftArmHeadExtension");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.2F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F)), ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.2F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        body.addChild("chestHeadExtension",  ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F,
                new Dilation(0.82F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightArmHeadExtension = rightArm.addChild("rightArmHeadExtension",  ModelPartBuilder.create().uv(32, 48).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                new Dilation(0.92F)),  ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftArmHeadExtension = leftArm.addChild("leftArmHeadExtension",  ModelPartBuilder.create().uv(48, 48).mirrored().cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                new Dilation(0.92F)).mirrored(false),  ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightLeg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData leftLeg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
