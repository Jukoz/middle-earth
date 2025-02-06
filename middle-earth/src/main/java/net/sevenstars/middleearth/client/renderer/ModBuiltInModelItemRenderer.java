package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.item.ModelTransformationMode;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.MEModelLoader;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.item.ModWeaponItems;
import net.sevenstars.middleearth.item.items.HeldBannerItem;
import net.sevenstars.middleearth.item.items.shields.CustomBannerShieldItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ModBuiltInModelItemRenderer {

    private HeaterShieldEntityModel heaterShieldEntityModel;
    private KiteShieldEntityModel kiteShieldEntityModel;
    private RoundShieldEntityModel roundShieldEntityModel;

    private HeldBannerEntityModel heldBannerEntityModel;

    public ModBuiltInModelItemRenderer() {
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.heaterShieldEntityModel = new HeaterShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.HEATER_SHIELD_LAYER));
        this.kiteShieldEntityModel = new KiteShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.KITE_SHIELD_LAYER));
        this.roundShieldEntityModel = new RoundShieldEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.ROUND_SHIELD_LAYER));
        this.heldBannerEntityModel = new HeldBannerEntityModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(MiddleEarthClient.HELD_BANNER_LAYER));

        if (stack.getItem() instanceof CustomBannerShieldItem) {
            BannerPatternsComponent bannerPatternsComponent = (BannerPatternsComponent)stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
            DyeColor dyeColor2 = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heaterShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.heaterShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.heaterShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.KITE_SHIELD_BASE : MEModelLoader.KITE_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.kiteShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.kiteShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.kiteShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            } else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.roundShieldEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.roundShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.roundShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.roundShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            }
        }

        if (stack.getItem() instanceof HeldBannerItem){
            BannerPatternsComponent bannerPatternsComponent = (BannerPatternsComponent)stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
            DyeColor dyeColor2 = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.isOf(ModWeaponItems.HELD_BANNER)){
                SpriteIdentifier spriteIdentifier = ModelBaker.BANNER_BASE;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.heldBannerEntityModel.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.heldBannerEntityModel.getPole().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heldBannerEntityModel.getBanner(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    this.heldBannerEntityModel.getBanner().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.pop();
            }
        }
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, BannerPatternsComponent patterns, boolean glint, ItemStack stack, boolean solid) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, solid, glint), light, overlay);
        if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.HEATER_SHIELD_BASE, color);
        } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.KITE_SHIELD_BASE, color);
        }else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        }else if (stack.isOf(ModWeaponItems.HELD_BANNER)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, TexturedRenderLayers.BANNER_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = (BannerPatternsComponent.Layer)patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.isOf(ModWeaponItems.HEATER_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(ModWeaponItems.KITE_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.isOf(ModWeaponItems.ROUND_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(ModWeaponItems.HELD_BANNER)){
                spriteIdentifier = TexturedRenderLayers.getBannerPatternTextureId(layer.pattern());
            }
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }



    private static void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, i);
    }
}
