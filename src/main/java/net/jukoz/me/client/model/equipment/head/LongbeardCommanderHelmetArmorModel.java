package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class LongbeardCommanderHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public LongbeardCommanderHelmetArmorModel(ModelPart root) {
        super(root);

        HELMET_ADDON_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/longbeard_commander_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("wing_right", ModelPartBuilder.create().uv(32, 0).mirrored().cuboid(-0.2F, -10.8F, 0.0F, 15.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 2.0F, 5.0F, 1.2955F, -0.7926F, -1.1932F));
        head.addChild("wing_left", ModelPartBuilder.create().uv(32, 0).cuboid(-14.8F, -10.8F, 0.0F, 15.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 2.0F, 5.0F, 1.2955F, 0.7926F, 1.1932F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}