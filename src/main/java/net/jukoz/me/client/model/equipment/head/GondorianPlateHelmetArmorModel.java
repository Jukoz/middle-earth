package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class GondorianPlateHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart addons;

    public GondorianPlateHelmetArmorModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");

        HELMET_ADDON_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/models/armor/gondorian_plate_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("addons", ModelPartBuilder.create()
                .uv(0, 10).cuboid(-3.0F, -13.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.0F, -11.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 0).mirrored().cuboid(-3.0F, -15.6F, -4.6F, 6.0F, 12.0F, 0.0F, new Dilation(0.5F)).mirrored(false)
                .uv(32, 16).mirrored().cuboid(-4.0F, -8.0F, -4.4F, 8.0F, 8.0F, 2.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("wing_right",  ModelPartBuilder.create().uv(49, 0).cuboid(-6.0F, -9.5F, -0.1F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -7.5F, -0.5F, 0.0F, 1.309F, 0.0F));
        head.addChild("wing_left",  ModelPartBuilder.create().uv(43, 0).cuboid(0.0F, -9.5F, -0.1F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -7.5F, -0.5F, 0.0F, -1.309F, 0.0F));

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