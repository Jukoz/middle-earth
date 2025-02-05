package net.sevenstars.middleearth.client.model.equipment.head.hoods.armored;

import net.sevenstars.middleearth.client.model.equipment.head.hoods.CloakHoodModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class FurHoodDownModel extends CloakHoodModel {

    public FurHoodDownModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData hat = head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData hood = hat.addChild("hood", ModelPartBuilder.create().uv(0, 73).cuboid(-4.0F, -9.0F, -4.376F, 8.0F, 5.0F, 2.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        ModelPartData furHood = hood.addChild("fur_hood", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.5F, -3.5F, 1.5708F, 0.0F, 0.0F));

        ModelPartData down = furHood.addChild("down", ModelPartBuilder.create().uv(96, 0).cuboid(-4.0F, -6.0F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.3F))
                .uv(64, 0).cuboid(-4.0F, -5.91F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, 0.0F, -6.0F, -1.1345F, 0.0F, 0.0F));

        ModelPartData hoodDown = down.addChild("hood_down", ModelPartBuilder.create(), ModelTransform.of(-0.144F, 4.2857F, -1.716F, -1.5708F, 0.0F, 0.0F));

        hoodDown.addChild("top", ModelPartBuilder.create().uv(112, 29).cuboid(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 26).cuboid(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        hoodDown.addChild("bottom", ModelPartBuilder.create().uv(112, 20).cuboid(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 23).cuboid(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        hoodDown.addChild("left", ModelPartBuilder.create().uv(100, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F))
                .uv(94, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.25F, 0.0F, 0.0F));
        hoodDown.addChild("right", ModelPartBuilder.create().uv(100, 20).mirrored().cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F)).mirrored(false)
                .uv(106, 20).cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(-0.25F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
