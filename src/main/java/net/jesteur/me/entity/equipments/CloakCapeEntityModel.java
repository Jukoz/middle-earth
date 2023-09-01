package net.jesteur.me.entity.equipments;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.MathHelper;

public class CloakCapeEntityModel<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart cape;

    public CloakCapeEntityModel(ModelPart cape) {
        this.cape = cape;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.0F);
        modelPartData.addChild("cape", ModelPartBuilder.create().uv(32, 32)
                .cuboid(-6.0F, -1F, 3f, 12.0F, 22.0F, 1.0F, dilation)
                , ModelTransform.of(0, 0, 0, 0.0872665f, 0.0F, 0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.cape);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}
