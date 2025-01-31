package net.sevenstars.middleearth.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class MordorBNHelmetModel extends HelmetAddonModel {

    public final ModelPart addons;

    public MordorBNHelmetModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addons = head.addChild("addons", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -13.0F, -3.5F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.5F, -14.5F, -0.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(40, 3).cuboid(0.0F, -18.35F, -6.1F, 0.0F, 17.0F, 12.0F, new Dilation(0.0F))
                .uv(46, 63).cuboid(-4.5F, -4.8F, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(-4.0F, -10.5F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 1.1F, 0.0F));

        addons.addChild("left_wing_cube", ModelPartBuilder.create().uv(40, 0).mirrored().cuboid(-0.5F, -10.5F, 0.284F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.814F, -7.5F, -1.5F, 0.0F, -0.6545F, 0.0F));
        addons.addChild("right_wing_cube", ModelPartBuilder.create().uv(52, 0).cuboid(-5.25F, -10.5F, 0.252F, 6.0F, 15.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -7.5F, -1.25F, 0.0F, 0.6545F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}