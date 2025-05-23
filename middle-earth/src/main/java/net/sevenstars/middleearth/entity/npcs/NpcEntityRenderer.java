package net.sevenstars.middleearth.entity.npcs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexture;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final SpriteAtlasTexture skinAtlasTexture;
    private final SpriteAtlasTexture eyeAtlasTexture;
    private final SpriteAtlasTexture hairAtlasTexture;
    private final SpriteAtlasTexture clothingAtlasTexture;
    public final static int HURT_COLOR = 0xff7e75;

    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(ModEntityModelLayers.NPC)), 0.7f);

        this.features.removeIf(x -> x.getClass() == ElytraFeatureRenderer.class);
        this.features.removeIf(x -> x.getClass() == HeadFeatureRenderer.class);


        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), context.getEquipmentRenderer()));

        this.addFeature(new HairFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new EarFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new NoseFeatureRenderer(this, context.getEntityModels()));

        MinecraftClient client = MinecraftClient.getInstance();
        skinAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_SKIN_TEXTURES_ATLAS_TEXTURE);
        eyeAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_EYE_TEXTURES_ATLAS_TEXTURE);
        hairAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_HAIR_TEXTURES_ATLAS_TEXTURE);
        clothingAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_CLOTHING_TEXTURES_ATLAS_TEXTURE);

        this.shadowRadius = 0.5f;
    }

    // region RenderState
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    @Override
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float f) {
        if(!npcEntity.getWorld().isClient)
            return;
        super.updateRenderState(npcEntity, npcEntityRenderState, f);

        var npcTextureData = npcEntity.getNpcTextureData();

        npcEntityRenderState.skinId = npcTextureData.getBodyTexture();
        npcEntityRenderState.headId = npcTextureData.getHeadTexture();
        npcEntityRenderState.earId = npcTextureData.getEarTexture();
        npcEntityRenderState.noseId = npcTextureData.getNoseTexture();
        npcEntityRenderState.eyesId = npcTextureData.getEyeTexture();
        npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive();
        npcEntityRenderState.eyebrowId = npcTextureData.getEyebrowTexture();
        npcEntityRenderState.scarId = npcTextureData.getScarTexture();
        npcEntityRenderState.beardId = npcTextureData.getBeardTexture();
        npcEntityRenderState.beardAddonId = npcTextureData.getBeardAddonTexture();
        npcEntityRenderState.hairId = npcTextureData.getHairTexture();
        npcEntityRenderState.hairAddonId = npcTextureData.getHairAddonTexture();
        npcEntityRenderState.clothingId = npcTextureData.getClothingTexture();
    }



    // endregion


    // region Layered Texture Renderer
    @Nullable
    @Override
    protected RenderLayer getRenderLayer(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(state);
        if (translucent) {
            return RenderLayer.getEntityTranslucent(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Override
    public void render(NpcEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }


        float g = state.baseScale;
        matrices.scale(g, g, g);
        this.setupTransforms(state, matrices, state.bodyYaw, g);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(state, matrices);
        matrices.translate(0.0F, -1.501F, 0.0F);

        this.model.setAngles(state);
        boolean bl = this.isVisible(state);
        boolean bl2 = !bl && !state.invisibleToPlayer;
        int k = bl2 ? 654311423 : -1;
        int l = ColorHelper.mix(k, this.getMixColor(state));

        for(int run = 0; run < 8; run ++){
            //RenderLayer renderLayer = this.getRenderLayer(state, bl, bl2, state.hasOutline);
            Identifier id;
            VertexConsumer vertexConsumer = null;
            Sprite sprite = null;


            if(state.skinId == null)
                return;
            switch (run) {
                case 0:
                    id = Identifier.of(state.skinId.getNamespace(), "npc_skin_textures/" + state.skinId.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());
                    sprite = skinAtlasTexture.getSprite(id);
                    break;
                case 1:
                    id = Identifier.of(state.headId.getNamespace(), "npc_skin_textures/" + state.headId.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());
                    sprite = skinAtlasTexture.getSprite(id);
                    break;
                case 2:
                    id = Identifier.of(state.eyesId.getNamespace(), "npc_eye_textures/" + state.eyesId.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcEyeTexturesRenderLayer(state.haveEmissiveEyes));
                    sprite = eyeAtlasTexture.getSprite(id);
                    break;
                case 3:
                    if(state.eyebrowId != null) {
                        id = Identifier.of(state.eyebrowId.getNamespace(), "npc_hair_textures/" + state.eyebrowId.getPath());
                        vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());
                        sprite = hairAtlasTexture.getSprite(id);
                    }
                    break;
                case 4:
                    if(state.scarId != null) {
                        id = Identifier.of(state.scarId.getNamespace(), "npc_skin_textures/" + state.scarId.getPath());
                        vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());
                        sprite = skinAtlasTexture.getSprite(id);
                    }
                    break;
                case 5:
                    if(state.hairId != null){
                        id = Identifier.of(state.hairId.getNamespace(), "npc_hair_textures/" + state.hairId.getPath());
                        vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());
                        sprite = hairAtlasTexture.getSprite(id);
                        break;
                    }
                case 6:
                    if(state.beardId != null){
                        id = Identifier.of(state.beardId.getNamespace(), "npc_hair_textures/" + state.beardId.getPath());
                        vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());
                        sprite = hairAtlasTexture.getSprite(id);
                    }
                    break;
                case 7:
                    id = Identifier.of(state.clothingId.getNamespace(), "npc_clothing_textures/" + state.clothingId.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcClothingTexturesRenderLayer());
                    sprite = clothingAtlasTexture.getSprite(id);
                    break;
                default:
                    break;
            }
            if(sprite != null){
                VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
                model.render(matrices, newLayerVertexConsumer, light, OverlayTexture.DEFAULT_UV, l);
            }
        }


        if (this.shouldRenderFeatures(state)) {

            for (FeatureRenderer<NpcEntityRenderState, NpcEntityModel> feature : this.features) {
                if (feature instanceof EarFeatureRenderer) {
                    if (state.earId == null) continue;
                }
                if (feature instanceof NoseFeatureRenderer) {
                    if (state.noseId == null) continue;
                }
                if (feature instanceof HairFeatureRenderer) {
                    if (state.hairAddonId == null && state.beardAddonId == null) continue;
                }
                feature.render(matrices, vertexConsumers, light, state, state.relativeHeadYaw, state.pitch);
            }
        }

        matrices.pop();
    }

    @Override
    protected int getMixColor(NpcEntityRenderState state) {
        if(state.hurt)
            return HURT_COLOR;
        return super.getMixColor(state);
    }

    @Override
    public Identifier getTexture(NpcEntityRenderState state) {
        // Made custom in the render method
        return null;
    }


    @Environment(EnvType.CLIENT)
    static record NpcTextureKeys(NpcTexture npcTexture, NpcTextureType type) {
        NpcTextureKeys(NpcTexture npcTexture, NpcTextureType type) {
            this.npcTexture = npcTexture;
            this.type = type;
        }

        public Identifier getTexture() {
            Identifier patternIdentifier = (this.npcTexture.pattern().value()).getIdentifier();
            String assetName = ((this.npcTexture.material().value())).getIdentifier().getPath();
            return patternIdentifier.withPath((path) -> {
                //return "npc_skin_textures/entity/" + this.type.asString() + "/" + path + "_" + string;
                return "npc_skin_textures/" + path + "_" + assetName;
            });
        }

        public NpcTexture npcTexture() {
            return this.npcTexture;
        }

        public NpcTextureType type() {
            return this.type;
        }
    }
}
