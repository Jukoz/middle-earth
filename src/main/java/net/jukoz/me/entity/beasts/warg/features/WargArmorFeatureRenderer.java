package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.jukoz.me.item.items.CustomAnimalArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.ColorHelper;

public class WargArmorFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargArmorModel model;

    public WargArmorFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargArmorModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float p;
        float o;
        float n;
        CustomAnimalArmorItem animalArmorItem;
        ItemStack itemStack = wargEntity.getBodyArmor();
        Item item = itemStack.getItem();
        if (!(item instanceof CustomAnimalArmorItem) || (animalArmorItem = (CustomAnimalArmorItem)item).getArmorType() != CustomAnimalArmorItem.Type.WARG) {
            return;
        }

        this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        ((WargModel)this.getContextModel()).copyStateTo(this.model);
        if (itemStack.isIn(ItemTags.DYEABLE)) {
            int m = DyedColorComponent.getColor(itemStack, -6265536);
            n = (float) ColorHelper.Argb.getRed(m) / 255.0f;
            o = (float)ColorHelper.Argb.getGreen(m) / 255.0f;
            p = (float)ColorHelper.Argb.getBlue(m) / 255.0f;
        } else {
            n = 1.0f;
            o = 1.0f;
            p = 1.0f;
        }
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(animalArmorItem.getEntityTexture()));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, n, o, p, 1.0f);
    }
}
