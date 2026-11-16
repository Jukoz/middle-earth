package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.item.ItemDisplayContext;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.ModelLoaderME;
import net.sevenstars.middleearth.client.TexturedRenderLayersME;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.HeldBannerItem;
import net.sevenstars.middleearth.item.items.shields.CustomBannerShieldItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CustomBuiltInModelItemRenderer {

    private HeaterShieldEntityModel heaterShieldEntityModel;
    private KiteShieldEntityModel kiteShieldEntityModel;
    private RoundShieldEntityModel roundShieldEntityModel;

    private HeldBannerEntityModel heldBannerEntityModel;

    public CustomBuiltInModelItemRenderer() {
    }

    public void render(ItemStack stack, ItemDisplayContext mode, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay) {
        SpriteHolder spriteHolder = MinecraftClient.getInstance().getAtlasManager();
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

            if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? ModelLoaderME.HEATER_SHIELD_BASE : ModelLoaderME.HEATER_SHIELD_BASE_NO_PATTERN;
                RenderLayer layer = this.heaterShieldEntityModel.getLayer(spriteIdentifier.getAtlasId());
                queue.submitModelPart(this.heaterShieldEntityModel.getHandle(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, 0);
                if (bl) {
                    renderCanvas(spriteHolder, matrices, queue, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    queue.submitModelPart(this.heaterShieldEntityModel.getPlate(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, stack.hasGlint(), -1, null, 0);
                }

                matrices.pop();
            } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? ModelLoaderME.KITE_SHIELD_BASE : ModelLoaderME.KITE_SHIELD_BASE_NO_PATTERN;
                RenderLayer layer = this.kiteShieldEntityModel.getLayer(spriteIdentifier.getAtlasId());
                queue.submitModelPart(this.kiteShieldEntityModel.getHandle(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, 0);
                if (bl) {
                    renderCanvas(spriteHolder, matrices, queue, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    queue.submitModelPart(this.kiteShieldEntityModel.getPlate(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, stack.hasGlint(), -1, null, 0);
                }

                matrices.pop();
            } else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
                SpriteIdentifier spriteIdentifier = bl ? ModelLoaderME.ROUND_SHIELD_BASE : ModelLoaderME.ROUND_SHIELD_BASE_NO_PATTERN;
                RenderLayer layer = this.roundShieldEntityModel.getLayer(spriteIdentifier.getAtlasId());
                queue.submitModelPart(this.roundShieldEntityModel.getHandle(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, 0);
                if (bl) {
                    renderCanvas(spriteHolder, matrices, queue, light, overlay, this.roundShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    queue.submitModelPart(this.roundShieldEntityModel.getPlate(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, stack.hasGlint(), -1, null, 0);
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

            if (stack.isOf(WeaponItemsME.HELD_BANNER)){
                SpriteIdentifier spriteIdentifier = ModelBaker.BANNER_BASE;
                RenderLayer layer = this.heldBannerEntityModel.getLayer(spriteIdentifier.getAtlasId());
                queue.submitModelPart(this.heldBannerEntityModel.getPole(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, 0);
                if (bl) {
                    renderCanvas(spriteHolder, matrices, queue, light, overlay, this.heldBannerEntityModel.getBanner(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint(), stack, false);
                } else {
                    queue.submitModelPart(this.heldBannerEntityModel.getBanner(), matrices, layer, light, overlay, spriteHolder.getSprite(spriteIdentifier), false, stack.hasGlint(), -1, null, 0);
                }

                matrices.pop();
            }
        }
    }

    public static void renderCanvas(SpriteHolder spriteHolder, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, BannerPatternsComponent patterns, boolean glint, ItemStack stack, boolean solid) {
        queue.submitModelPart(canvas, matrices, baseSprite.getRenderLayer(RenderLayers::entitySolid), light, overlay, spriteHolder.getSprite(baseSprite), false, glint, -1, null, 0);
        if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : TexturedRenderLayersME.HEATER_SHIELD_BASE, color);
        } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : TexturedRenderLayersME.KITE_SHIELD_BASE, color);
        }else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, isBanner ? TexturedRenderLayers.BANNER_BASE : TexturedRenderLayersME.ROUND_SHIELD_BASE, color);
        }else if (stack.isOf(WeaponItemsME.HELD_BANNER)){
            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, TexturedRenderLayers.BANNER_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = (BannerPatternsComponent.Layer)patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = isBanner ? TexturedRenderLayers.getBannerPatternTextureId(layer.pattern()) : TexturedRenderLayersME.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.isOf(WeaponItemsME.HEATER_SHIELD)){
                spriteIdentifier = TexturedRenderLayersME.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(WeaponItemsME.KITE_SHIELD)){
                spriteIdentifier = TexturedRenderLayersME.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.isOf(WeaponItemsME.ROUND_SHIELD)){
                spriteIdentifier = TexturedRenderLayersME.getRoundShieldPatternTextureId(layer.pattern());
            } else if (stack.isOf(WeaponItemsME.HELD_BANNER)){
                spriteIdentifier = TexturedRenderLayers.getBannerPatternTextureId(layer.pattern());
            }
            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }



    private static void renderLayer(SpriteHolder spriteHolder, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        queue.submitModelPart(canvas, matrices, textureId.getRenderLayer(RenderLayers::entityNoOutline), light, overlay, spriteHolder.getSprite(textureId), i, null);
    }
}
