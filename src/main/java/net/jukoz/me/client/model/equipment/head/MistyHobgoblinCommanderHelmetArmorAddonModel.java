package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MistyHobgoblinCommanderHelmetArmorAddonModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public final ModelPart wingMiddle;
    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinCommanderHelmetArmorAddonModel(ModelPart root) {
        super(root);
        wingMiddle = root.getChild("head").getChild("addon").getChild("wing_middle");
        wingRight = root.getChild("head").getChild("addon").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("addon").getChild("wing_left");

        HELMET_ADDON_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_commander_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create(), ModelTransform.of(-1.5F, -10.5976F, -3.7012F, 0.0F, -0.3491F, 0.0F));
        addon.addChild("wing_middle", ModelPartBuilder.create().uv(17, 2).mirrored().cuboid(-3.0F, -4.0F, -2.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.3312F, -0.9868F, -0.0188F, 0.0F, 0.1309F, 0.0F));
        addon.addChild("wing_right", ModelPartBuilder.create().uv(17, 2).cuboid(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.3312F, -0.9868F, -0.0188F, 0.0F, 0.5672F, 0.0F));
        addon.addChild("wing_left", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -7.0F, -0.5F, 4.0F, 14.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-0.5F, 3.1132F, -0.3666F, 0.0F, 0.3491F, 0.0F));

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