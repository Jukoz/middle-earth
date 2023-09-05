package net.jesteur.me.entity.equipments;

import com.google.common.collect.ImmutableList;
import net.jesteur.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

public class CloakHoodEntityModel <T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart hood;

    public CloakHoodEntityModel(ModelPart root) {
        this.hood = root.getChild("hood");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.0F);
        modelPartData
                .addChild("hood", ModelPartBuilder.create()
                                .uv(32, 0)
                                .cuboid(-3, -12, 2, 6, 6, 1.0F, dilation)
                , ModelTransform.of(0, 0, 0, 0, 0.0F, 0F))
                .addChild("back_hood", ModelPartBuilder.create().uv(32, 7)
                        .cuboid(-3, -13.435f, 2.08f, 6,6,2, dilation)
                , ModelTransform.of(0, 1, -1, -ToRad.ex(7.5), 0.0F, 0F));


        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.hood);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
