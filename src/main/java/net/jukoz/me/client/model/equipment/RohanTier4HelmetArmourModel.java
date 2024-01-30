package net.jukoz.me.client.model.equipment;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class RohanTier4HelmetArmourModel<T extends LivingEntity> extends BipedEntityModel<T> {
    private final ModelPart headornament;

    public RohanTier4HelmetArmourModel(ModelPart root) {
        super(root);
        headornament = head.getChild("headornament");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData head = data.getRoot().getChild(EntityModelPartNames.HEAD);
        ModelPartData body = data.getRoot().getChild(EntityModelPartNames.BODY);
        ModelPartData rightLeg = data.getRoot().getChild(EntityModelPartNames.RIGHT_LEG);
        ModelPartData leftLeg = data.getRoot().getChild(EntityModelPartNames.LEFT_LEG);
        ModelPartData rightArm = data.getRoot().getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData leftArm = data.getRoot().getChild(EntityModelPartNames.LEFT_ARM);

        ModelPartData headornament = head.addChild("headornament", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1.0F, -11.0F, -6.0F, 2.0F, 12.0F, 17.0F, new Dilation(-0.5F))
                        .uv(0, 0).cuboid(-1.0F, -11.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.25F)),
                ModelTransform.pivot(0.0F, -4.0F, -2.0F));


        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}