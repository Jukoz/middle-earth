package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class GondorianHelmetModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public final ModelPart addons;

    public GondorianHelmetModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addons = head.addChild("addons", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -11.0F, -3.5F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.5F, -12.5F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(29, 0).cuboid(-3.0F, -15.6F, -4.35F, 6.0F, 12.0F, 0.0F, new Dilation(0.5F))
                .uv(29, 12).cuboid(-3.0F, -15.6F, 5.15F, 6.0F, 12.0F, 0.0F, new Dilation(0.5F))
                .uv(0, 15).mirrored().cuboid(-4.0F, -8.0F, -4.314F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(50, 0).mirrored().cuboid(2.0F, -19.8F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(50, 16).cuboid(-9.0F, -19.8F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        addons.addChild("side_right_0", ModelPartBuilder.create().uv(40, 49).cuboid(-6.0F, -9.5F, 0.528F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -6.5F, -0.5F, 0.0F, 1.309F, 0.0F));
        addons.addChild("side_right_1", ModelPartBuilder.create().uv(52, 32).cuboid(-6.0F, -9.5F, 0.502F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -6.5F, -2.5F, 0.0F, 1.3963F, 0.0F));

        addons.addChild("side_left_0", ModelPartBuilder.create().uv(52, 49).cuboid(0.0F, -9.5F, 0.492F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -6.5F, -0.5F, 0.0F, -1.309F, 0.0F));
        addons.addChild("side_left_1", ModelPartBuilder.create().uv(40, 32).cuboid(0.0F, -9.5F, 0.534F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -6.5F, -2.5F, 0.0F, -1.3963F, 0.0F));


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