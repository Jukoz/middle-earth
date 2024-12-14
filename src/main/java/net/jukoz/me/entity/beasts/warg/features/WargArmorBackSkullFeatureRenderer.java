package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.items.armor.CustomAnimalArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class WargArmorBackSkullFeatureRenderer extends FeatureRenderer<WargEntity, WargModel> {
    private final WargArmorTopAddonsModel model;

    public WargArmorBackSkullFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new WargArmorTopAddonsModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR_ADDONS_BACK));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntity wargEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = wargEntity.getBodyArmor();
        Item item = itemStack.getItem();
        if(item instanceof CustomAnimalArmorItem animalArmorItem) {
            if (itemStack.isOf(ModEquipmentItems.WARG_GUNDABAD_PLATE_ARMOR)) {
                if(itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA) != null && itemStack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA).topArmorAddon()) {
                    ((WargModel)this.getContextModel()).copyStateTo(this.model);

                    this.model.setAngles(wargEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

                    Identifier addonTexture = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_addons.png");

                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(addonTexture));
                    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
                }
            }
        }
    }
}
