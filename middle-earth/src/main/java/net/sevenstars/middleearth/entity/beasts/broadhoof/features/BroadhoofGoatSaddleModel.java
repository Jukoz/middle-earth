package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class BroadhoofGoatSaddleModel extends BroadhoofGoatModel {
    private final ModelPart reins;
    public BroadhoofGoatSaddleModel(ModelPart root) {
        super(root);

        this.reins = root.getChild("broadhoof_goat").getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD).getChild("saddle_things").getChild("reins");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData root = modelData.getRoot().getChild("broadhoof_goat").getChild(EntityModelPartNames.BODY);
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);


        ModelPartData saddle = root.addChild("saddle", ModelPartBuilder.create().uv(74, 19).cuboid(-7.0F, -23.0F, 13.0F, 14.0F, 14.0F, 13.0F, new Dilation(0.3F))
                .uv(92, 72).cuboid(-7.0F, -25.0F, 25.0F, 14.0F, 4.0F, 4.0F, new Dilation(0.5F)), ModelTransform.origin(0.0F, 12.0F, -21.0F));

        ModelPartData saddle_things = head.addChild("saddle_things", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData reins = saddle_things.addChild("reins", ModelPartBuilder.create().uv(81, -3).mirrored().cuboid(-4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new Dilation(0.0F)).mirrored(false)
                .uv(81, -3).cuboid(4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 1.0F, 0.48F, 0.0F, 0.0F));

        ModelPartData mouth_things = saddle_things.addChild("mouth_things", ModelPartBuilder.create().uv(62, 50).mirrored().cuboid(-2.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(62, 50).cuboid(5.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(66, 47).cuboid(-1.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.2F)), ModelTransform.of(-2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BroadhoofGoatEntityRenderState state) {
        super.setAngles(state);

        this.reins.visible = state.conrollingPassenger != null;
    }
}
