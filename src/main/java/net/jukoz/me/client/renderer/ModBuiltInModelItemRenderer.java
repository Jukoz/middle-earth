package net.jukoz.me.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.MEModelLoader;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.client.model.shields.HeaterShieldEntityModel;
import net.jukoz.me.client.model.shields.KiteShieldEntityModel;
import net.jukoz.me.client.model.shields.RoundShieldEntityModel;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.items.shields.CustomBannerShieldItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ModBuiltInModelItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private HeaterShieldEntityModel heaterShieldEntityModel;
    private KiteShieldEntityModel kiteShieldEntityModel;
    private RoundShieldEntityModel roundShieldEntityModel;
    
    public ModBuiltInModelItemRenderer() {
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.heaterShieldEntityModel = new HeaterShieldEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HEATER_SHIELD_LAYER));
        this.kiteShieldEntityModel = new KiteShieldEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.KITE_SHIELD_LAYER));
        this.roundShieldEntityModel = new RoundShieldEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.ROUND_SHIELD_LAYER));

        if (stack.getItem() instanceof CustomBannerShieldItem) {
            BannerPatternsComponent bannerPatternsComponent = (BannerPatternsComponent)stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
            DyeColor dyeColor2 = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.heaterShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.heaterShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack);
                } else {
                    this.heaterShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.KITE_SHIELD_BASE : MEModelLoader.KITE_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.kiteShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.kiteShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack);
                } else {
                    this.kiteShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.roundShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.roundShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.roundShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack);
                } else {
                    this.roundShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            }
        }
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, BannerPatternsComponent patterns, boolean glint, ItemStack stack) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, glint), light, overlay);
        if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.HEATER_SHIELD_BASE, color);
        } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.KITE_SHIELD_BASE, color);
        }else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        }else {
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : TexturedRenderLayers.SHIELD_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = (BannerPatternsComponent.Layer)patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
                spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
                spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
                spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            }
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }



    private static void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, i);
    }
}
