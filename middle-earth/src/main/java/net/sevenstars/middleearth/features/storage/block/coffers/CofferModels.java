package net.sevenstars.middleearth.features.storage.block.coffers;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

// CofferModel opens the part named "lid"
public class CofferModels {
    public static TexturedModelData larch() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 21).cuboid(-8.0F, -2.0F, -10.0F, 16.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(49, 57).cuboid(-1.0F, -2.0F, -11.0F, 2.0F, 3.0F, 1.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, 14.0F, 5.0F));

        ModelPartData bottom = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 58).cuboid(-7.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(12, 58).cuboid(-7.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 58).cuboid(4.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 58).cuboid(4.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.0F, -10.0F, -5.0F, 14.0F, 8.0F, 10.0F,
                        new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData handle_r1 = bottom.addChild("handle_r1", ModelPartBuilder.create().uv(0, 0)
                .mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .mirrored(false), ModelTransform.of(7.0F, -6.1F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData handle_r2 = bottom.addChild("handle_r2", ModelPartBuilder.create().uv(0, 0)
                .cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-7.0F, -6.1F, 0.0F, 0.0F, 0.0F, 0.3927F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData pine() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 23)
                .cuboid(-8.0F, -2.0F, -14.0F, 16.0F, 2.0F, 15.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 10.0F, 7.0F));

        ModelPartData bottom = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(48, 29).
                cuboid(7.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(3, 29).cuboid(7.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(3, 45).cuboid(-8.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(47, 45).cuboid(-8.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 41).cuboid(-8.0F, -14.0F, -6.0F, 16.0F, 10.0F, 13.0F, new Dilation(0.0F))
                .uv(14, 5).cuboid(8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .uv(30, 5).cuboid(-8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bottom.addChild("cube_r1", ModelPartBuilder.create().uv(14, 0)
                .mirrored().cuboid(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .mirrored(false), ModelTransform.of(-8.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData cube_r2 = bottom.addChild("cube_r2", ModelPartBuilder.create().uv(14, 0)
                .cuboid(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.of(8.0F, -12.0F, 1.0F, 0.0F, 0.0F, -0.3491F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData spruce() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 45)
                .cuboid(0.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F,
                new Dilation(0.0F)), ModelTransform.origin(-8.0F, 16.0F, 0.0F));

        ModelPartData lock_r1 = lid.addChild("lock_r1", ModelPartBuilder.create().uv(0, 1)
                .cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(16.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 38)
                        .cuboid(5.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 38).cuboid(-8.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(24, 38).cuboid(-8.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(36, 38).cuboid(5.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 36).cuboid(-5.0F, -3.0F, -8.0F, 10.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(20, 36).cuboid(-5.0F, -3.0F, 8.0F, 10.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(20, 24).cuboid(-8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 3).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 5.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData fir() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("lid", ModelPartBuilder.create()
                        .uv(0, 24).cuboid(-5.0F, -2.0F, -8.0F, 10.0F, 2.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 18.0F, 4.0F));

        modelPartData.addChild("bottom", ModelPartBuilder.create()
                        .uv(0, 36).cuboid(-1.0F, -4.0F, -7.0F, 10.0F, 4.0F, 8.0F, new Dilation(0.0F))
                        .uv(40, 34).cuboid(-1.0F, 0.0F, -7.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                        .uv(40, 29).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                        .uv(40, 44).cuboid(7.0F, 0.0F, -7.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                        .uv(40, 39).cuboid(7.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(-4.0F, 22.0F, 3.0F));

        return TexturedModelData.of(modelData, 48, 48);
    }

    public static TexturedModelData beech() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(8, 50).cuboid(-12.0F, -1.0F, -3.0F, 12.0F, 6.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.origin(6.0F, 19.0F, 1.0F));

        body.addChild("cube_r1", ModelPartBuilder.create()
                        .uv(43, 48).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        body.addChild("cube_r2", ModelPartBuilder.create()
                        .uv(43, 48).mirrored().cuboid(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        modelPartData.addChild("lid", ModelPartBuilder.create()
                        .uv(12, 35).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 18.0F, 5.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData chestnut() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(2, 34).cuboid(-16.0F, 6.1F, -5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(19, 34).cuboid(-4.0F, 6.1F, -5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(35, 34).cuboid(-4.0F, 6.1F, 5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(2, 39).cuboid(-16.0F, 6.1F, 5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 13).cuboid(-16.0F, -1.9F, -5.0F, 16.0F, 8.0F, 11.0F, new Dilation(0.0F)),
                ModelTransform.origin(8.0F, 15.9F, 0.0F));

        body.addChild("cube_r1", ModelPartBuilder.create()
                        .uv(1, -1).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        body.addChild("cube_r2", ModelPartBuilder.create()
                        .uv(1, -1).mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 4.0F, 5.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        modelPartData.addChild("lid", ModelPartBuilder.create()
                        .uv(0, 49).cuboid(-8.0F, -2.0F, -12.0F, 16.0F, 2.0F, 13.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 14.0F, 6.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData oak() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 13.0F, 6.0F));

        lid.addChild("lid_r1", ModelPartBuilder.create()
                        .uv(0, 9).cuboid(-12.0F, -3.0F, -16.0F, 12.0F, 3.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.of(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(0, 56).cuboid(-11.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(26, 56).cuboid(-11.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(39, 56).cuboid(-2.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(13, 56).cuboid(-2.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(0, 32).cuboid(-11.0F, -7.0F, -14.0F, 12.0F, 7.0F, 14.0F, new Dilation(0.0F)),
                ModelTransform.of(-7.0F, 20.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        body.addChild("cube_r1", ModelPartBuilder.create()
                        .uv(0, -2).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.0F, -5.0F, -14.0F, -1.5708F, 1.2654F, -1.5708F));

        body.addChild("cube_r2", ModelPartBuilder.create()
                        .uv(0, -2).mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-5.0F, -5.0F, 0.0F, 1.5708F, 1.2654F, 1.5708F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData willow() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(0, 36).cuboid(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(16, 36).cuboid(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(32, 36).cuboid(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(0, 44).cuboid(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(1, 0).cuboid(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(-7.0F, 24.0F, -5.0F));

        modelPartData.addChild("lid", ModelPartBuilder.create()
                        .uv(1, 21).cuboid(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 11.0F, 6.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
