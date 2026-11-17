package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.ModelLoaderME;
import net.sevenstars.middleearth.client.TexturedRenderLayersME;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.Objects;
import java.util.function.Consumer;

public class KiteShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {

    private final SpriteHolder spriteHolder;
    private final KiteShieldEntityModel model;

    public KiteShieldModelRenderer(SpriteHolder spriteHolder, KiteShieldEntityModel model) {
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
        SpriteIdentifier spriteIdentifier = bl2 ? ModelLoaderME.KITE_SHIELD_BASE : ModelLoaderME.KITE_SHIELD_BASE_NO_PATTERN;
        queue.submitModelPart(this.model.getHandle(), matrices, this.model.getLayer(spriteIdentifier.getAtlasId()), light, overlay, this.spriteHolder.getSprite(spriteIdentifier), false, false, -1, null, outlineColor);
        if (bl2) {
            renderCanvas(this.spriteHolder, matrices, queue, light, overlay, this.model.getPlate(), spriteIdentifier, (DyeColor)Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, glint, outlineColor);
        } else {
            queue.submitModelPart(this.model.getPlate(), matrices, this.model.getLayer(spriteIdentifier.getAtlasId()), light, overlay, this.spriteHolder.getSprite(spriteIdentifier), false, glint, -1, null, outlineColor);
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
        renderLayer(spriteHolder, matrices, queue, light, overlay, canvas, TexturedRenderLayersME.KITE_SHIELD_BASE, color);
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = TexturedRenderLayersME.getKiteShieldPatternTextureId(layer.pattern());

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
        public static final KiteShieldModelRenderer.Unbaked INSTANCE = new KiteShieldModelRenderer.Unbaked();
        public static final MapCodec<KiteShieldModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }

        public MapCodec<KiteShieldModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
            return new KiteShieldModelRenderer(context.spriteHolder(), new KiteShieldEntityModel(context.entityModelSet().getModelPart(MiddleEarthClient.KITE_SHIELD_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
