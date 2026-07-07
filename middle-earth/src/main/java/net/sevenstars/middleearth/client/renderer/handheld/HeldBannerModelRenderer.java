package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
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
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public class HeldBannerModelRenderer implements SpecialModelRenderer<ComponentMap> {

    private final HeldBannerEntityModel model;

    public HeldBannerModelRenderer(HeldBannerEntityModel model) {
        this.model = model;
    }

    @Override
    public void render(@Nullable ComponentMap data, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        BannerPatternsComponent bannerPatternsComponent = data != null ? data.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT) : BannerPatternsComponent.DEFAULT;
        DyeColor dyeColor = data != null ? data.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = ModelBaker.BANNER_BASE;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(spriteIdentifier.getAtlasId()), displayContext == ItemDisplayContext.GUI, glint));
        this.model.getPole().render(matrices, vertexConsumer, light, overlay);
        if (bl2) {
            renderCanvas(matrices, vertexConsumers, light, overlay, this.model.getBanner(), spriteIdentifier, false, (DyeColor)Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, glint, false);
        } else {
            this.model.getBanner().render(matrices, vertexConsumer, light, overlay);
        }

        matrices.pop();
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.getRootPart().collectVertices(matrixStack, vertices);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, DyeColor color, BannerPatternsComponent patterns, boolean glint, boolean solid) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, solid, glint), light, overlay);
        renderLayer(matrices, vertexConsumers, light, overlay, canvas, TexturedRenderLayers.BANNER_BASE, color);
        for(int i = 0; i < 16 && i < patterns.layers().size(); ++i) {
            BannerPatternsComponent.Layer layer = patterns.layers().get(i);
            SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getBannerPatternTextureId(layer.pattern());

            renderLayer(matrices, vertexConsumers, light, overlay, canvas, spriteIdentifier, layer.color());
        }}

    private static void renderLayer(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier textureId, DyeColor color) {
        int i = color.getEntityColor();
        canvas.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, i);
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

        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new HeldBannerModelRenderer(new HeldBannerEntityModel(entityModels.getModelPart(MiddleEarthClient.HELD_BANNER_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
