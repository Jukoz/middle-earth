package net.sevenstars.middleearth.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.MEModelLoader;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.HeldBannerItem;
import net.sevenstars.middleearth.item.items.shields.CustomBannerShieldItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.Objects;

public class ModBuiltInModelItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final HeaterShieldEntityModel heaterShieldEntityModel;
    private final KiteShieldEntityModel kiteShieldEntityModel;
    private final RoundShieldEntityModel roundShieldEntityModel;
    private final HeldBannerEntityModel heldBannerEntityModel;

    public ModBuiltInModelItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        EntityModelSet models = Minecraft.getInstance().getEntityModels();
        this.heaterShieldEntityModel = new HeaterShieldEntityModel(models.bakeLayer(MiddleEarthClient.HEATER_SHIELD_LAYER));
        this.kiteShieldEntityModel = new KiteShieldEntityModel(models.bakeLayer(MiddleEarthClient.KITE_SHIELD_LAYER));
        this.roundShieldEntityModel = new RoundShieldEntityModel(models.bakeLayer(MiddleEarthClient.ROUND_SHIELD_LAYER));
        this.heldBannerEntityModel = new HeldBannerEntityModel(models.bakeLayer(MiddleEarthClient.HELD_BANNER_LAYER));
    }

    public static void register(RegisterClientExtensionsEvent event, Item... items) {
        event.registerItem(new IClientItemExtensions() {
            private ModBuiltInModelItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new ModBuiltInModelItemRenderer();
                }
                return this.renderer;
            }
        }, items);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext mode, PoseStack matrices,
                             MultiBufferSource vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof CustomBannerShieldItem) {
            BannerPatternLayers bannerPatternsComponent = (BannerPatternLayers)stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            DyeColor dyeColor2 = (DyeColor)stack.get(DataComponents.BASE_COLOR);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.pushPose();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.is(WeaponItemsME.HEATER_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.sprite().wrap(ItemRenderer.getFoilBuffer(vertexConsumers, this.heaterShieldEntityModel.renderType(spriteIdentifier.atlasLocation()), true, stack.hasFoil()));
                this.heaterShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heaterShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasFoil(), stack);
                } else {
                    this.heaterShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.popPose();
            } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.KITE_SHIELD_BASE : MEModelLoader.KITE_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.sprite().wrap(ItemRenderer.getFoilBuffer(vertexConsumers, this.kiteShieldEntityModel.renderType(spriteIdentifier.atlasLocation()), true, stack.hasFoil()));
                this.kiteShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.kiteShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasFoil(), stack);
                } else {
                    this.kiteShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.popPose();
            } else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
                Material spriteIdentifier = bl ? MEModelLoader.ROUND_SHIELD_BASE : MEModelLoader.ROUND_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.sprite().wrap(ItemRenderer.getFoilBuffer(vertexConsumers, this.roundShieldEntityModel.renderType(spriteIdentifier.atlasLocation()), true, stack.hasFoil()));
                this.roundShieldEntityModel.getHandle().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.roundShieldEntityModel.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasFoil(), stack);
                } else {
                    this.roundShieldEntityModel.getPlate().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.popPose();
            }
        }

        if (stack.getItem() instanceof HeldBannerItem){
            BannerPatternLayers bannerPatternsComponent = (BannerPatternLayers)stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            DyeColor dyeColor2 = (DyeColor)stack.get(DataComponents.BASE_COLOR);
            boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
            matrices.pushPose();
            matrices.scale(1.0F, -1.0F, -1.0F);

            if (stack.is(WeaponItemsME.HELD_BANNER)){
                Material spriteIdentifier = ModelBakery.BANNER_BASE;
                VertexConsumer vertexConsumer = spriteIdentifier.sprite().wrap(ItemRenderer.getFoilBuffer(vertexConsumers, this.heldBannerEntityModel.renderType(spriteIdentifier.atlasLocation()), true, stack.hasFoil()));
                this.heldBannerEntityModel.getPole().render(matrices, vertexConsumer, light, overlay);
                if (bl) {
                    renderCanvas(matrices, vertexConsumers, light, overlay, this.heldBannerEntityModel.getBanner(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasFoil(), stack);
                } else {
                    this.heldBannerEntityModel.getBanner().render(matrices, vertexConsumer, light, overlay);
                }

                matrices.popPose();
            }
        }
    }

    public static void renderCanvas(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ModelPart canvas, Material baseSprite, boolean isBanner, DyeColor color, BannerPatternLayers patterns, boolean glint, ItemStack stack) {
        canvas.render(matrices, baseSprite.buffer(vertexConsumers, RenderType::entitySolid, glint), light, overlay);
        if (stack.is(WeaponItemsME.HEATER_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? Sheets.BANNER_BASE : ModTexturedRenderLayers.HEATER_SHIELD_BASE, color);
        } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? Sheets.BANNER_BASE : ModTexturedRenderLayers.KITE_SHIELD_BASE, color);
        }else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, isBanner ? Sheets.BANNER_BASE : ModTexturedRenderLayers.ROUND_SHIELD_BASE, color);
        }else if (stack.is(WeaponItemsME.HELD_BANNER)){
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, Sheets.BANNER_BASE, color);
        }
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternLayers.Layer layer = (BannerPatternLayers.Layer)patterns.layers().get(i);
            Material spriteIdentifier = isBanner ? Sheets.getBannerMaterial(layer.pattern()) : ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            if (stack.is(WeaponItemsME.HEATER_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getHeaterShieldPatternTextureId(layer.pattern());
            } else if (stack.is(WeaponItemsME.KITE_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getKiteShieldPatternTextureId(layer.pattern());
            }else if (stack.is(WeaponItemsME.ROUND_SHIELD)){
                spriteIdentifier = ModTexturedRenderLayers.getRoundShieldPatternTextureId(layer.pattern());
            } else if (stack.is(WeaponItemsME.HELD_BANNER)){
                spriteIdentifier = Sheets.getBannerMaterial(layer.pattern());
            }
            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }
    }



    private static void renderLayer(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ModelPart canvas, Material textureId, DyeColor color) {
        int i = color.getTextureDiffuseColor();
        canvas.render(matrices, textureId.buffer(vertexConsumers, RenderType::entityNoOutline), light, overlay, i);
    }
}
