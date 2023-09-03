package net.jesteur.me.entity.equipments;

import net.jesteur.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.text.Text;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class CloakCapeEntityModel<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart cape;

    public CloakCapeEntityModel(ModelPart root) {
        this.cape = root.getChild("cape");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.0F);
        modelPartData.addChild("cape", ModelPartBuilder.create()
                .cuboid(-6.0F, 0f, 2, 12.0F, 22.0F, 1.0F, dilation)
                .uv(0, 0)
                , ModelTransform.of(0, 0.5f, 1.5f, 0.0F, 0.0F, 0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.cape);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        Vec3d velocity = entity.getVelocity();
        Vec3d absVel = new Vec3d(Math.abs(velocity.x), Math.abs(velocity.y), Math.abs(velocity.z));
        double speed = Math.sqrt(absVel.getX() * absVel.getX() + absVel.getY() * absVel.getY() + absVel.getZ() * absVel.getZ());


        double degree = 90f * speed;
        degree = Math.max(5f, degree);
        degree = Math.min(90f, degree);

        this.cape.pitch = ToRad.ex(degree + (Math.sin(animationProgress * 0.2f) / 2));
    }


}
