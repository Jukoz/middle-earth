package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;

public class WargArmorTopAddonsModel extends WargArmorModel {

    private final ModelPart warg;
    public WargArmorTopAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelDataFront() {
        ModelData modelData = getArmorModelData();
        ModelPartData body_armor = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild("body_armor");

        ModelPartData armor_addons = body_armor.addChild("armor_addons", ModelPartBuilder.create(), ModelTransform.origin(-5.0F, -5.0F, -4.0F));
        ModelPartData front_addons = armor_addons.addChild("front_addons", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        ModelPartData skull2 = front_addons.addChild("skull2", ModelPartBuilder.create().uv(6, 43).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.1F))
                .uv(79, 48).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.2F))
                .uv(6, 30).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(13.0F, -4.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }



    public static TexturedModelData getTexturedModelDataBack() {
        ModelData modelData = getArmorModelData();
        ModelPartData body_armor = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild("body_armor");

        ModelPartData armor_addons = body_armor.addChild("armor_addons", ModelPartBuilder.create(), ModelTransform.origin(-5.0F, -5.0F, -4.0F));

        ModelPartData back_addons = armor_addons.addChild("back_addons", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-1.0F, -9.0F, -1.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -23.0F, 2.0F, 2.0F, 23.0F, 2.0F, new Dilation(-0.1F))
                .uv(20, 63).cuboid(-1.0F, -27.0F, 3.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = back_addons.addChild("cube_r1", ModelPartBuilder.create().uv(4, 99).cuboid(-16.0F, -8.5F, 0.0F, 17.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.4399F));

        ModelPartData cube_r2 = back_addons.addChild("cube_r2", ModelPartBuilder.create().uv(19, 81).cuboid(-1.0F, -8.5F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(-0.3F)), ModelTransform.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData skull = back_addons.addChild("skull", ModelPartBuilder.create().uv(6, 43).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.1F))
                .uv(6, 30).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
}
