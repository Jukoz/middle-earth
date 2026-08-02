package net.sevenstars.middleearth.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;

public class EarModel extends EntityModel<NpcEntityRenderState> {
    public ModelPart head;

    public final ModelPart ears;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;

    public EarModel(ModelPart modelPart) {
        super(modelPart);

        this.ears = modelPart.getChild("ears");

        this.planeFlatLeft = this.ears.getChild("left_flat_ear");
        this.planeFlatRight = this.ears.getChild("right_flat_ear");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData ears = root.addChild("ears",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(0, 0f, 0.0F, 0, 0, 0.0F, Dilation.NONE),
                ModelTransform.rotation(0F, 0, 0.0F)
                        .moveOrigin(0f, -8f, 0.0F));

        ears.addChild("left_flat_ear",
                ModelPartBuilder.create()
                        .uv(0, 6)
                        .cuboid(0, 0, 0.0F, 6.0F, 7.0F, 0.0F, Dilation.NONE),
                ModelTransform.rotation(0.0F, -0.35F, 0.0F)
                        .moveOrigin(4F, 0f, 0.0F));
        ears.addChild("right_flat_ear",
                ModelPartBuilder.create()
                        .uv(0, 6)
                        .cuboid(0, 0, 0.0F, 6.0F, 7.0F, 0.0F, Dilation.NONE)
                        .mirrored(),
                ModelTransform.rotation(0.0F, 0.35F, 0.0F)
                        .moveOrigin(-4F, 0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        ears.pitch = head.pitch;
        ears.yaw = head.yaw;
        ears.roll = head.roll;

        this.planeFlatLeft.yaw = (float)Math.toRadians(-20);
        this.planeFlatRight.yaw = (float)Math.toRadians(-160);
    }

    public void setHead(ModelPart head) {
        this.head = head;
    }
}
