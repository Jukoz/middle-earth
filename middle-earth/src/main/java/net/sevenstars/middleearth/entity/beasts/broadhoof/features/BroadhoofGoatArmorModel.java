package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

// This class might be moved over to the main BroadhoofModel class
public class BroadhoofGoatArmorModel extends BroadhoofGoatModel {

    private final ModelPart broadhoofGoat;
    private final ModelPart head;

    public BroadhoofGoatArmorModel(ModelPart root) {
        super(root);

        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(EntityModelPartNames.BODY).getChild(EntityModelPartNames.HEAD);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData root = modelData.getRoot().getChild("broadhoof_goat").getChild(EntityModelPartNames.BODY);
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);

        ModelPartData head_armor = head.addChild("head_armor", ModelPartBuilder.create().uv(0, 1).cuboid(0.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 1).mirrored().cuboid(-7.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.origin(2.0F, 0.0F, -8.0F));

        ModelPartData neck_plate_r1 = head_armor.addChild("neck_plate_r1", ModelPartBuilder.create().uv(85, 5).cuboid(-5.0F, -7.0F, 4.0F, 6.0F, 14.0F, 6.0F, new Dilation(0.1F))
                .uv(44, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 6.0F, 11.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData side_hanging_plate_r1 = head_armor.addChild("side_hanging_plate_r1", ModelPartBuilder.create().uv(77, 53).cuboid(-5.0F, -4.0F, -1.0F, 6.0F, 5.0F, 11.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 2.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData body_armor = root.addChild("body_armor", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -3.0F, -20.0F, 12.0F, 11.0F, 20.0F, new Dilation(0.2F))
                .uv(0, 89).cuboid(-6.0F, 2.0F, -20.0F, 12.0F, 6.0F, 20.0F, new Dilation(0.1F))
                .uv(0, 31).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.2F))
                .uv(0, 60).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.1F))
                .uv(108, 113).cuboid(6.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new Dilation(0.0F))
                .uv(108, 98).cuboid(-9.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -6.0F, 9.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BroadhoofGoatEntityRenderState state) {
        super.setAngles(state);
    }
}
