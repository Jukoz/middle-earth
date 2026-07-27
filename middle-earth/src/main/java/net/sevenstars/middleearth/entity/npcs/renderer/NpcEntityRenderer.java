package net.sevenstars.middleearth.entity.npcs.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache.NpcPrefix;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.npcs.renderer.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.feet.FeetFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.utils.ItemTagsME;

public class NpcEntityRenderer extends HumanoidMobRenderer<NpcEntity, NpcEntityModel> {
    public static final int LIGHT_LEVEL_EMISSIVE_EYES = 8;
    public static final int BLINKING_INTERVAL = 80;
    public static final int BLINKING_DURATION = 3;
    private static final ResourceLocation EMPTY_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/misc/white.png");

    public NpcEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new NpcEntityModel(context.bakeLayer(EntityModelLayersME.NPC)), 0.7F);
        this.layers.removeIf(layer -> layer.getClass() == ElytraLayer.class
                || layer.getClass() == CustomHeadLayer.class
                || layer.getClass() == ItemInHandLayer.class);
        this.addLayer(new LodItemInHandLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new SkinLayer(this));
        this.addLayer(new LodHumanoidArmorLayer(
                this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new HairFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new EarFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new NoseFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new FeetFeatureRenderer(this, context.getModelSet()));
        this.shadowRadius = 0.5F;
    }

    public static float getLOD(Vec3 entityPos) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        return (float)camera.getPosition().distanceTo(entityPos);
    }

    @Override
    public void render(NpcEntity entity, float entityYaw, float partialTick, PoseStack matrices,
                       MultiBufferSource buffers, int light) {
        NpcTextureData data = entity.retrieveNpcTextureData();
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING
                && data.getSimplifiedSkin() != null;
        if (!simplified && (data.get(NpcRenderedPart.BODY) == null
                || data.get(NpcRenderedPart.HEAD) == null
                || data.get(NpcRenderedPart.EYE) == null)) {
            return;
        }

        this.model.setAllVisible(false);
        if (entity.isPassenger()) {
            matrices.pushPose();
            matrices.translate(0.0F, -0.5F, 0.0F);
            super.render(entity, entityYaw, partialTick, matrices, buffers, light);
            matrices.popPose();
        } else {
            super.render(entity, entityYaw, partialTick, matrices, buffers, light);
        }
    }

    @Override
    protected void scale(NpcEntity entity, PoseStack matrices, float partialTick) {
        float widthScale = entity.getWidthScale();
        matrices.scale(widthScale, 1.0F, widthScale);
    }

    @Override
    public ResourceLocation getTextureLocation(NpcEntity entity) {
        return EMPTY_TEXTURE;
    }

    public static HumanoidModel.ArmPose getArmPose(NpcEntity npc, ItemStack stack, InteractionHand hand) {
        if (npc.isAiming()) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
        if (stack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        if (!npc.swinging && (stack.is(Items.CROSSBOW) || stack.is(ItemTagsME.CROSSBOW))) {
            if (CrossbowItem.isCharged(stack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            if (npc.isCharging()) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        }
        if (npc.getUsedItemHand() == hand && npc.getUseItemRemainingTicks() > 0) {
            UseAnim useAnimation = stack.getUseAnimation();
            if (useAnimation == UseAnim.BLOCK || npc.isBlocking()) return HumanoidModel.ArmPose.BLOCK;
            if (useAnimation == UseAnim.BOW) return HumanoidModel.ArmPose.BOW_AND_ARROW;
            if (useAnimation == UseAnim.SPEAR) return HumanoidModel.ArmPose.THROW_SPEAR;
            if (useAnimation == UseAnim.CROSSBOW) return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            if (useAnimation == UseAnim.SPYGLASS) return HumanoidModel.ArmPose.SPYGLASS;
            if (useAnimation == UseAnim.TOOT_HORN) return HumanoidModel.ArmPose.TOOT_HORN;
            if (useAnimation == UseAnim.BRUSH) return HumanoidModel.ArmPose.BRUSH;
        }
        return HumanoidModel.ArmPose.ITEM;
    }

    private static boolean shouldRenderEquipment(NpcEntity entity) {
        return getLOD(entity.position()) < ModClientConfigs.LOD_NPC_ARMOR_DISTANCE;
    }

    private static final class LodItemInHandLayer extends ItemInHandLayer<NpcEntity, NpcEntityModel> {
        private LodItemInHandLayer(RenderLayerParent<NpcEntity, NpcEntityModel> parent,
                                   ItemInHandRenderer itemInHandRenderer) {
            super(parent, itemInHandRenderer);
        }

        @Override
        public void render(PoseStack matrices, MultiBufferSource buffers, int light, NpcEntity entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                           float netHeadYaw, float headPitch) {
            if (shouldRenderEquipment(entity)) {
                super.render(matrices, buffers, light, entity, limbSwing, limbSwingAmount, partialTick,
                        ageInTicks, netHeadYaw, headPitch);
            }
        }
    }

    private static final class LodHumanoidArmorLayer
            extends HumanoidArmorLayer<NpcEntity, NpcEntityModel, HumanoidArmorModel<NpcEntity>> {
        private LodHumanoidArmorLayer(RenderLayerParent<NpcEntity, NpcEntityModel> parent,
                                      HumanoidArmorModel<NpcEntity> innerModel,
                                      HumanoidArmorModel<NpcEntity> outerModel,
                                      ModelManager modelManager) {
            super(parent, innerModel, outerModel, modelManager);
        }

        @Override
        public void render(PoseStack matrices, MultiBufferSource buffers, int light, NpcEntity entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                           float netHeadYaw, float headPitch) {
            if (shouldRenderEquipment(entity)) {
                super.render(matrices, buffers, light, entity, limbSwing, limbSwingAmount, partialTick,
                        ageInTicks, netHeadYaw, headPitch);
            }
        }
    }

    private static final class SkinLayer extends RenderLayer<NpcEntity, NpcEntityModel> {
        private final TextureAtlas atlas;

        private SkinLayer(RenderLayerParent<NpcEntity, NpcEntityModel> parent) {
            super(parent);
            this.atlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
        }

        @Override
        public void render(PoseStack matrices, MultiBufferSource buffers, int light, NpcEntity entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                           float netHeadYaw, float headPitch) {
            NpcTextureData data = entity.retrieveNpcTextureData();
            boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING
                    && data.getSimplifiedSkin() != null;
            if (!simplified && (data.get(NpcRenderedPart.BODY) == null
                    || data.get(NpcRenderedPart.HEAD) == null
                    || data.get(NpcRenderedPart.EYE) == null)) {
                return;
            }

            NpcEntityModel model = this.getParentModel();
            model.setAllVisible(true);
            int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
            VertexConsumer vertices = buffers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());
            if (simplified) {
                render(model, this.atlas, matrices, vertices, data.getSimplifiedSkin(), light, overlay);
                return;
            }

            render(model, this.atlas, matrices, vertices,
                    RenderResourceCache.npcTexture(data.get(NpcRenderedPart.BODY), NpcPrefix.SKIN), light, overlay);
            render(model, this.atlas, matrices, vertices,
                    RenderResourceCache.npcTexture(data.get(NpcRenderedPart.HEAD), NpcPrefix.SKIN), light, overlay);

            boolean blinking = entity.tickCount % BLINKING_INTERVAL >= BLINKING_INTERVAL - BLINKING_DURATION;
            if (!blinking) {
                render(model, this.atlas, matrices, vertices,
                        RenderResourceCache.npcTexture(data.get(NpcRenderedPart.EYE), NpcPrefix.EYE), light, overlay);
            }
            renderOptional(model, this.atlas, matrices, vertices,
                    data.get(NpcRenderedPart.EYEBROW), NpcPrefix.HAIR, light, overlay);
            renderOptional(model, this.atlas, matrices, vertices,
                    data.get(NpcRenderedPart.SCAR), NpcPrefix.SKIN, light, overlay);
            renderOptional(model, this.atlas, matrices, vertices,
                    data.get(NpcRenderedPart.BEARD), NpcPrefix.HAIR, light, overlay);

            ResourceLocation clothingBase = data.get(NpcRenderedPart.CLOTHING_BASE);
            ResourceLocation clothingOver = data.get(NpcRenderedPart.CLOTHING_OVER);
            ResourceLocation clothingExtra = data.get(NpcRenderedPart.CLOTHING_EXTRA);
            if (clothingBase == null && clothingOver == null && clothingExtra == null) {
                render(model, this.atlas, matrices, vertices,
                        RenderResourceCache.npcTexture(
                                CharacterClothesRegistryME.Base.THONG_BROWN, NpcPrefix.CLOTHES_BASE),
                        light, overlay);
            } else {
                renderOptional(model, this.atlas, matrices, vertices,
                        clothingBase, NpcPrefix.CLOTHES_BASE, light, overlay);
                renderOptional(model, this.atlas, matrices, vertices,
                        clothingOver, NpcPrefix.CLOTHES_OVER, light, overlay);
                renderOptional(model, this.atlas, matrices, vertices,
                        clothingExtra, NpcPrefix.CLOTHES_EXTRA, light, overlay);
            }
            renderOptional(model, this.atlas, matrices, vertices,
                    data.get(NpcRenderedPart.HAIR), NpcPrefix.HAIR, light, overlay);

            if (!blinking && data.isEyeEmissive()
                    && entity.level().getMaxLocalRawBrightness(entity.blockPosition()) <= LIGHT_LEVEL_EMISSIVE_EYES) {
                ResourceLocation emissive = data.get(NpcRenderedPart.EYE_EMISSIVE);
                if (emissive != null) {
                    VertexConsumer emissiveVertices =
                            buffers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer());
                    render(model, this.atlas, matrices, emissiveVertices,
                            RenderResourceCache.npcTexture(emissive, NpcPrefix.EYE), light, overlay);
                }
            }
        }

        private static void renderOptional(NpcEntityModel model, TextureAtlas atlas, PoseStack matrices,
                                           VertexConsumer vertices, ResourceLocation id, NpcPrefix prefix,
                                           int light, int overlay) {
            if (id != null) {
                render(model, atlas, matrices, vertices,
                        RenderResourceCache.npcTexture(id, prefix), light, overlay);
            }
        }

        private static void render(NpcEntityModel model, TextureAtlas atlas, PoseStack matrices,
                                   VertexConsumer vertices, ResourceLocation id, int light, int overlay) {
            RenderUtil.renderAtlasTexture(atlas, model, matrices, vertices, id, light, overlay);
        }
    }
}
