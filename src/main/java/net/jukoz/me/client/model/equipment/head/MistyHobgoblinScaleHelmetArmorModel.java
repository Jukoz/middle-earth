package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MistyHobgoblinScaleHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinScaleHelmetArmorModel(ModelPart root) {
        super(root);
        wingRight = root.getChild("head").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("wing_left");

        HELMET_ADDON_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_scale_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("wing_right", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.5F, -2.5F, -1.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.5F, -11.5F, 0.0F, 0.0F, -0.2618F, 0.0F));
        head.addChild("wing_left", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.5F, -1.0F, 4.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, -11.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}