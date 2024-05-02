package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MistyHobgoblinPlateHelmetArmorAddonModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinPlateHelmetArmorAddonModel(ModelPart root) {
        super(root);
        wingRight = root.getChild("head").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("wing_left");

        HELMET_ADDON_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_plate_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData wing_right = head.addChild("wing_right", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -10.5976F, -0.7012F));
        ModelPartData wing_left = head.addChild("wing_left", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -10.5976F, -0.7012F));
        wing_right.addChild("wing_right1", ModelPartBuilder.create().uv(6, 0).cuboid(-3.5F, 8.5F, 8.1426F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.6462F, -13.4868F, -7.8413F, 0.0F, 0.3491F, 0.0F));
        wing_right.addChild("wing_right2", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -7.0F, -0.5F, 2.0F, 14.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-0.5F, 3.1132F, -0.3666F, 0.0F, 0.3491F, 0.0F));
        wing_left.addChild("wing_left1", ModelPartBuilder.create().uv(6, 0).mirrored().cuboid(-2.5F, 8.5F, 8.1426F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(8.6462F, -13.4868F, -7.8413F, 0.0F, -0.3491F, 0.0F));
        wing_left.addChild("wing_left2", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.0F, -7.0F, -0.5F, 2.0F, 14.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(5.5F, 3.1132F, -0.3666F, 0.0F, -0.3491F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}