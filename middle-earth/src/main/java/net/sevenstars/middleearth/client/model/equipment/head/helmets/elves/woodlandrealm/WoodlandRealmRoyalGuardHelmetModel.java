package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class WoodlandRealmRoyalGuardHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WoodlandRealmRoyalGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData centralSpine = addon.addChild("central_spine", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = centralSpine.addChild("cube_r1", ModelPartBuilder.create().uv(0, 45)
                .cuboid(-5.0F, 2.0F, 0.0F, 1.0F, 10.0F, 9.0F, new Dilation(0.1F)),
                ModelTransform.of(4.5F, -2.25F, -7.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData fin = addon.addChild("fin", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData fin1 = fin.addChild("fin_1", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-11.0F, -2.0F + Y_OFFSET, -4.0F, 21.0F, 0.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.of(0.5F, -6.25F, -0.388F, 0.6021F, 0.0F, 0.0F));

        //ModelPartData lowerExtension = addon.addChild("lower_extension", ModelPartBuilder.create().uv(0, 11)
        //        .cuboid(-4.0F, -7.8F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)).uv(32, 11)
        //        .cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)), ModelTransform.origin(0.0F, 10.8F, 0.0F));
        ModelPartData lowerExtension = addon.addChild("lower_extension", ModelPartBuilder.create().uv(0, 11)
                .cuboid(-4.0F, -13.8F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)).uv(32, 11)
                .cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)), ModelTransform.origin(0.0F, 10.8F, 0.0F));


        head.addChild("addon_eye", ModelPartBuilder.create()
                        .uv(46, 63).cuboid(-4.5F, -3.55F, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}