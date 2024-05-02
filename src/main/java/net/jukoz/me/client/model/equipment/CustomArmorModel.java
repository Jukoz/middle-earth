package net.jukoz.me.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class CustomArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public final ModelPart head;

    public final ModelPart hat;

    public final ModelPart chestHeadExtension;
    public final ModelPart rightArmHeadExtension;
    public final ModelPart leftArmHeadExtension;

    public final ModelPart body;

    public final ModelPart rightArm;

    public final ModelPart leftArm;

    public final ModelPart rightLeg;

    public final ModelPart leftLeg;


    public CustomArmorModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
        this.head = root.getChild("head");
        this.hat = root.getChild("hat");

        this.chestHeadExtension = root.getChild("body").getChild("chestHeadExtension");
        this.rightArmHeadExtension = root.getChild("right_arm").getChild("rightArmHeadExtension");
        this.leftArmHeadExtension = root.getChild("left_arm").getChild("leftArmHeadExtension");

        this.body = root.getChild("body");

        this.rightArm = root.getChild("right_arm");

        this.leftArm = root.getChild("left_arm");

        this.rightLeg = root.getChild("right_leg");

        this.leftLeg = root.getChild("left_leg");

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.8F)),
                ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        ModelPartData innerChest = body.addChild("innerChest", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, -1.0F, -2.068F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        ModelPartData waist = body.addChild("waist", ModelPartBuilder.create().uv(16, 16).cuboid(-3.9F, -1.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        ModelPartData chestHeadExtension = body.addChild("chestHeadExtension",  ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F,
                new Dilation(0.82F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F))
                .uv(32, 48).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.92F))
                .uv(40, 16).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.pivot(-5.0F, -21.0F, 0.0F));

        ModelPartData rightArmHeadExtension = rightArm.addChild("rightArmHeadExtension",  ModelPartBuilder.create().uv(32, 48).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                new Dilation(0.92F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(48, 48).mirrored().cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)).mirrored(false)
                .uv(40, 32).mirrored().cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false), ModelTransform.pivot(5.0F, -21.0F, 0.0F));

        ModelPartData leftArmHeadExtension = leftArm.addChild("leftArmHeadExtension",  ModelPartBuilder.create().uv(48, 48).mirrored().cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F,
                new Dilation(0.92F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightLeg = modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F))
                .uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(-1.9F, -11.0F, 0.0F));

        ModelPartData rightBoot = rightLeg.addChild("rightBoot", ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rightInnerBoot = rightLeg.addChild("rightInnerBoot", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftLeg = modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(0, 32).mirrored().cuboid(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(1.9F, -11.0F, 0.0F));

        ModelPartData leftBoot = leftLeg.addChild("leftBoot", ModelPartBuilder.create().uv(16, 48).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData leftInnerBoot = leftLeg.addChild("leftInnerBoot", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        
        return TexturedModelData.of(modelData, 64, 64);
    }
}