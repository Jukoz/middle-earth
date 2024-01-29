package net.jukoz.me.model.equipment;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class RohanTier4HelmetArmourModel<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart headornament;

    public RohanTier4HelmetArmourModel(ModelPart root) {
        this.headornament = root.getChild("headornament");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("headornament", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -11.0F, -6.0F, 2.0F, 12.0F, 17.0F, new Dilation(-0.5F))
                .uv(0, 0).cuboid(-1.0F, -11.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.25F)),
                ModelTransform.pivot(0.0F, -4.0F, -2.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.headornament);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}