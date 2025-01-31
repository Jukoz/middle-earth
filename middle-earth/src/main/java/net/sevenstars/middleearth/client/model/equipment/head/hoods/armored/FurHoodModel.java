package net.sevenstars.middleearth.client.model.equipment.head.hoods.armored;

import net.sevenstars.middleearth.client.model.equipment.head.hoods.CloakHoodModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class FurHoodModel extends CloakHoodModel {

    public FurHoodModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hat.addChild("fur_hood", ModelPartBuilder.create()
                .uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.3F))
                .uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, 0.0F, -0.0F, -0.0F, 0.0F, 0.0F));

        ModelPartData hood = hat.addChild("hood", ModelPartBuilder.create(), ModelTransform.of(-0.14F, 2.65F, -0.0F, -0.0F, 0.0F, 0.0F));

        hood.addChild("top", ModelPartBuilder.create()
                .uv(112, 29).cuboid(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F))
                .uv(112, 26).cuboid(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.7F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hood.addChild("bottom", ModelPartBuilder.create()
                .uv(112, 20).cuboid(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F))
                .uv(112, 23).cuboid(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.7F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hood.addChild("left", ModelPartBuilder.create()
                .uv(100, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 10.0F, 2.0F, new Dilation(0.6F))
                .uv(94, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 10.0F, 2.0F, new Dilation(0.69F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hood.addChild("right", ModelPartBuilder.create()
                .uv(100, 20).mirrored().cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 10.0F, 2.0F, new Dilation(0.6F)).mirrored(false)
                .uv(106, 20).cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 10.0F, 2.0F, new Dilation(0.69F)), ModelTransform.pivot(-0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
