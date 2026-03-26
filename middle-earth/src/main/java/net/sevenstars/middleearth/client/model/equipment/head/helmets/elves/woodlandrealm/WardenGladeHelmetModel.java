package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class WardenGladeHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WardenGladeHelmetModel(ModelPart root) {
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

        crest.addChild("crest_left", ModelPartBuilder.create().uv(46, 51).cuboid(-3.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F,
                new Dilation(0.0F)), ModelTransform.of(2.654F, -15.0819F, -2.0603F, -0.4363F, -0.6109F, 0.2094F));

        crest.addChild("crest_right", ModelPartBuilder.create().uv(46, 51).mirrored().cuboid(-5.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F,
                new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.404F, -14.8899F, -1.4963F, -0.4363F, 0.6109F, -0.2094F));

        addon.addChild("lower_extension", ModelPartBuilder.create().uv(0, 11)
                .cuboid(-4.0F, -11.5F + Y_OFFSET, -3.8F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F))
                .uv(32, 11).cuboid(-4.0F, -10.0F + Y_OFFSET, -4.5F, 8.0F, 8.0F, 8.0F,
                        new Dilation(0.9F)), ModelTransform.origin(0.0F, 9.8F, 0.0F));

        addon.addChild("addon_eye", ModelPartBuilder.create()
                        .uv(46, 63).cuboid(-4.5F, -4.3F + Y_OFFSET, -4.475F, 9.0F, 1.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}