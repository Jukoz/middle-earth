package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.Objects;
import java.util.function.Consumer;

public class HeldBannerModelRenderer implements SpecialModelRenderer<ComponentMap> {

    private final SpriteHolder spriteHolder;
    private final HeldBannerEntityModel model;

    public HeldBannerModelRenderer(SpriteHolder spriteHolder, HeldBannerEntityModel model) {
        this.spriteHolder = spriteHolder;
        this.model = model;
    }

    @Override
    public void render(@Nullable ComponentMap data, ItemDisplayContext displayContext, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, boolean glint, int outlineColor) {
        BannerPatternsComponent bannerPatternsComponent = data != null ? data.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT) : BannerPatternsComponent.DEFAULT;
        DyeColor dyeColor = data != null ? data.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = ModelBaker.BANNER_BASE;
        queue.submitModelPart(this.model.getPole(), matrices, this.model.getLayer(spriteIdentifier.getAtlasId()), light, overlay, this.spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, outlineColor);
        if (bl2) {
            renderCanvas(this.spriteHolder, matrices, queue, light, overlay, this.model.getBanner(), spriteIdentifier, (DyeColor)Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, glint, outlineColor);
        } else {
            queue.submitModelPart(this.model.getBanner(), matrices, this.model.getLayer(spriteIdentifier.getAtlasId()), light, overlay, this.spriteHolder.getSprite(spriteIdentifier), false, glint, -1, null, outlineColor);
        }

        matrices.pop();
    }

    @Override
    public void collectVertices(Consumer<Vector3fc> consumer) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.getRootPart().collectVertices(matrixStack, consumer);
    }

    public static void renderCanvas(SpriteHolder spriteHolder, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, DyeColor color, BannerPatternsComponent patterns, boolean glint, int outlineColor) {
        queue.submitModelPart(canvas, matrices, baseSprite.getRenderLayer(RenderLayers::entitySolid), light, overlay, spriteHolder.getSprite(baseSprite), false, glint, -1, null, outlineColor);
        renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, TexturedRenderLayers.BANNER_BASE, color);
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getBannerPatternTextureId(layer.pattern());

            renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, spriteIdentifier, layer.color());
        }}

    private static void renderLayer(SpriteHolder spriteHolder, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        queue.submitModelPart(canvas, matrices, textureId.getRenderLayer(RenderLayers::entityNoOutline), light, overlay, spriteHolder.getSprite(textureId), i, null);
    }

    @Nullable
    @Override
    public ComponentMap getData(ItemStack stack) {
        return stack.getImmutableComponents();
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final HeldBannerModelRenderer.Unbaked INSTANCE = new HeldBannerModelRenderer.Unbaked();
        public static final MapCodec<HeldBannerModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }

        public MapCodec<HeldBannerModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
            return new HeldBannerModelRenderer(context.spriteHolder(), new HeldBannerEntityModel(context.entityModelSet().getModelPart(MiddleEarthClient.HELD_BANNER_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
