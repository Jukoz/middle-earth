package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class SilvanLordHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public SilvanLordHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 1.75F, -0.25F));

        addon.addChild("central_spine", ModelPartBuilder.create().uv(0, 45).cuboid(-5.0F, 2.0F + Y_OFFSET, 0.0F, 1.0F, 10.0F, 9.0F,
                new Dilation(0.1F)), ModelTransform.of(4.5F, -3.25F, -7.5F, 1.5708F, 0.0F, 0.0F));

        ModelPartData crest = addon.addChild("crest", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -0.5F, 1.08F));

        ModelPartData crest_left = crest.addChild("crest_left", ModelPartBuilder.create().uv(46, 51)
                .cuboid(-3.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(2.654F, -15.0819F, -2.0603F, -0.4363F, -0.6109F, 0.2094F));

        ModelPartData crest_right = crest.addChild("crest_right", ModelPartBuilder.create().uv(46, 51)
                .mirrored().cuboid(-5.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-3.404F, -14.8899F, -1.4963F, -0.4363F, 0.6109F, -0.2094F));

        ModelPartData fin = addon.addChild("fin", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -0.5F, 1.08F));

        ModelPartData fin_1 = fin.addChild("fin_1", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-11.0F, -2.0F + Y_OFFSET, -3.75F, 21.0F, 0.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.of(0.5F, -7.25F, -0.968F, 0.6021F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}