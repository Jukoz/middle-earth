package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;

// This class might be moved over to the main BroadhoofModel class
public class BroadhoofGoatSaddleModel extends EntityModel<BroadhoofGoatEntityRenderState> {

    private final ModelPart broadhoofGoat;
    private final ModelPart head;
    private final ModelPart reins;
    public BroadhoofGoatSaddleModel(ModelPart root) {
        super(root);

        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD);
        this.reins = head.getChild("saddle_things").getChild("reins");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData broadhoof_goat = modelPartData.addChild("broadhoof_goat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = broadhoof_goat.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 2.0F));

        ModelPartData saddle = body.addChild("saddle", ModelPartBuilder.create().uv(74, 19).cuboid(-7.0F, -23.0F, 13.0F, 14.0F, 14.0F, 13.0F, new Dilation(0.3F))
                .uv(92, 72).cuboid(-7.0F, -25.0F, 25.0F, 14.0F, 4.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 12.0F, -21.0F));

        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData saddle_things = head.addChild("saddle_things", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData reins = saddle_things.addChild("reins", ModelPartBuilder.create().uv(81, -3).mirrored().cuboid(-4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
                .uv(81, -3).cuboid(4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 1.0F, 0.48F, 0.0F, 0.0F));

        ModelPartData mouth_things = saddle_things.addChild("mouth_things", ModelPartBuilder.create().uv(62, 50).mirrored().cuboid(-2.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(62, 50).cuboid(5.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(66, 47).cuboid(-1.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.2F)), ModelTransform.of(-2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
