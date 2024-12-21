package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class OrthancCommanderHelmetModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public final ModelPart addons;

    public OrthancCommanderHelmetModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("addons", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -12.0F, -3.5F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.5F, -13.5F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(46, 63).cuboid(-4.5F, -4.8F, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(1, 16).cuboid(-11.0F, -19.875F, -0.7F, 22.0F, 12.0F, 1.0F, new Dilation(0.0F))
                .uv(-9, 30).cuboid(-7.5F, -3.1F, -1.0F, 15.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.1F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}