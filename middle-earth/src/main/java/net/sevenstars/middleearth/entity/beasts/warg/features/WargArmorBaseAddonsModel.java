package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;

public class WargArmorBaseAddonsModel extends EntityModel<WargEntityRenderState> {
    private final ModelPart warg;
    public WargArmorBaseAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelDataSpine() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-2.6142F, 2.1138F, 1.5F));

        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create(), ModelTransform.pivot(-0.3858F, -1.1138F, -1.5F));

        ModelPartData body_armor = upper_body.addChild("body_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 2.0F));

        ModelPartData front_armor = body_armor.addChild("front_armor", ModelPartBuilder.create(), ModelTransform.pivot(7.8F, 1.5F, -1.0F));

        ModelPartData addons_front_armor = front_armor.addChild("addons_front_armor", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData spine = addons_front_armor.addChild("spine", ModelPartBuilder.create().uv(8, 0).cuboid(2.0F, -2.4F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 0).cuboid(-1.0F, -2.1F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 0.0F));

        ModelPartData spine_04_r1 = spine.addChild("spine_04_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -0.7F, 0.0F, 0.0F, 0.0F, 0.0873F));

        ModelPartData spine_01_r1 = spine.addChild("spine_01_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -1.2F, 0.0F, 0.0F, 0.0F, 0.1309F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
