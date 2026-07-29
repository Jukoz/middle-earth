package net.sevenstars.middleearth.entity.npcs.renderer.features.hair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache.NpcPrefix;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.utils.ItemTagsME;

public class HairFeatureRenderer extends RenderLayer<NpcEntity, NpcEntityModel> {
    private final EntityModel<NpcEntity> hairModel;
    private final TextureAtlas atlas;

    public HairFeatureRenderer(RenderLayerParent<NpcEntity, NpcEntityModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.hairModel = new HairModel(modelSet.bakeLayer(EntityModelLayersME.NPC_ENTITY_HAIR));
        this.atlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, NpcEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        if (NpcEntityRenderer.getLOD(entity.position()) > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE) return;
        var data = entity.retrieveNpcTextureData();
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING
                && data.getSimplifiedSkin() != null;
        ResourceLocation rawHair = simplified ? data.getSimplifiedHair() : data.get(NpcRenderedPart.HAIR_ADDON);
        ResourceLocation rawBeard = simplified ? null : data.get(NpcRenderedPart.BEARD_ADDON);
        ResourceLocation hairId = simplified || rawHair == null
                ? rawHair : RenderResourceCache.npcTexture(rawHair, NpcPrefix.HAIR);
        ResourceLocation beardId = RenderResourceCache.npcTexture(rawBeard, NpcPrefix.HAIR);
        if (hairId == null && beardId == null) return;

        var helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        var attachment = helmet.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        boolean hoodDown = attachment == null || attachment.down();
        boolean showHair = helmet.isEmpty()
                || (!helmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hoodDown);
        boolean showBeard = helmet.isEmpty() || !helmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);

        this.hairModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer base = buffers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
        if (hairId != null && showHair) {
            this.hairModel.renderToBuffer(matrices, this.atlas.getSprite(hairId).wrap(base), light, overlay);
        }
        if (beardId != null && showBeard) {
            this.hairModel.renderToBuffer(matrices, this.atlas.getSprite(beardId).wrap(base), light, overlay);
        }
    }
}
