package net.sevenstars.middleearth.entity.npcs.renderer.features.feet;

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
import net.sevenstars.middleearth.registries.AtlasesME;

public class FeetFeatureRenderer extends RenderLayer<NpcEntity, NpcEntityModel> {
    private final EntityModel<NpcEntity> feetModel;
    private final TextureAtlas atlas;

    public FeetFeatureRenderer(RenderLayerParent<NpcEntity, NpcEntityModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.feetModel = new FeetModel(modelSet.bakeLayer(EntityModelLayersME.NPC_ENTITY_FEET));
        this.atlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, NpcEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        if (!entity.getItemBySlot(EquipmentSlot.FEET).isEmpty()
                || NpcEntityRenderer.getLOD(entity.position()) > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE) return;
        var data = entity.retrieveNpcTextureData();
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING
                && data.getSimplifiedSkin() != null;
        ResourceLocation raw = simplified ? data.getSimplifiedFeet() : data.get(NpcRenderedPart.FEET);
        ResourceLocation id = simplified ? raw : RenderResourceCache.npcTexture(raw, NpcPrefix.SKIN);
        if (id == null) return;

        this.feetModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertices = this.atlas.getSprite(id).wrap(
                buffers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer()));
        this.feetModel.renderToBuffer(matrices, vertices, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
    }
}
