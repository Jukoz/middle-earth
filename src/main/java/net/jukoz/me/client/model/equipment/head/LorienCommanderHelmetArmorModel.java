package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class LorienCommanderHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart spike;

    public LorienCommanderHelmetArmorModel(ModelPart root) {
        super(root);
        spike = root.getChild("head").getChild("spike");

        HELMET_ADDON_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/lorien_commander_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("spike", ModelPartBuilder.create().uv(0, -10).mirrored().cuboid(-5.0F, 0.0F, 2.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, -3.0F, -8.0F, 1.5708F, 0.0F, 0.0F));
        head.addChild("mouth_piece", ModelPartBuilder.create().uv(32, 16).mirrored().cuboid(-4.0F, -8.0F, -4.4F, 8.0F, 8.0F, 2.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

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