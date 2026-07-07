package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class WoodlandRealmSoldierHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WoodlandRealmSoldierHelmetModel(ModelPart root) {
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

        ModelPartData Crest = addon.addChild("crest", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData crest_r1 = Crest.addChild("crest_left", ModelPartBuilder.create().uv(46, 51).
                cuboid(-3.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(2.654F, -14.0819F, -1.4803F, -0.4363F, -0.6109F, 0.2094F));

        ModelPartData crest_r2 = Crest.addChild("crest_right", ModelPartBuilder.create().uv(46, 51).mirrored()
                .cuboid(-5.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-3.404F, -13.8899F, -0.9163F, -0.4363F, 0.6109F, -0.2094F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}