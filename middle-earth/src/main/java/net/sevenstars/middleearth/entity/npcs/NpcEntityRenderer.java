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
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.features.hair.NpcEntityHairFeatureRenderer;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexture;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final SpriteAtlasTexture skinAtlasTexture;
    private final SpriteAtlasTexture eyeAtlasTexture;
    private final SpriteAtlasTexture hairAtlasTexture;
    private final SpriteAtlasTexture clothingAtlasTexture;

    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(ModEntityModelLayers.NPC)), 0.7f);

        this.addFeature(new NpcEntityHairFeatureRenderer(this, context.getEntityModels()));

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
        super.updateRenderState(npcEntity, npcEntityRenderState, f);
        npcEntityRenderState.skinTextureIdentifier = npcEntity.getSkinTextureIdentifier();
        npcEntityRenderState.eyeTextureIdentifier = npcEntity.getEyeTextureIdentifier();
        npcEntityRenderState.haveEmissiveEyes = npcEntity.getEmissiveEyes();
        npcEntityRenderState.hairTextureIdentifier = npcEntity.getHairTextureIdentifier();
        npcEntityRenderState.hairAddonTextureIdentifier = npcEntity.getHairAddonTextureIdentifier();
        npcEntityRenderState.clothingTextureIdentifier = npcEntity.getClothingTextureIdentifier();
    }

    // endregion


    // region Layered Texture Renderer
    @Nullable
    @Override
    protected RenderLayer getRenderLayer(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(state);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Override
    public void render(NpcEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        int currentRenderStep = 0;
        int maximumRenderStep = 6;

        Text customName = state.customName;
        if(customName != null){
            String value = customName.getString();
            switch (value){
                case "Dev_0":
                    maximumRenderStep = 1;
                    break;
                case "Dev_1":
                    maximumRenderStep = 2;
                    break;
                case "Dev_2":
                    maximumRenderStep = 3;
                    break;
                case "Dev_3":
                    maximumRenderStep = 4;
                    break;
                default:
                    break;
            }
        }

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

        for(int run = 0; run < maximumRenderStep; run ++){
            //RenderLayer renderLayer = this.getRenderLayer(state, bl, bl2, state.hasOutline);
            int k = bl2 ? 654311423 : -1;
            int l = ColorHelper.mix(k, this.getMixColor(state));

            Identifier id;
            VertexConsumer vertexConsumer = null;
            Sprite sprite = null;

            switch (currentRenderStep) {
                case 0:
                    id = Identifier.of(state.skinTextureIdentifier.getNamespace(), "npc_skin_textures/" + state.skinTextureIdentifier.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());
                    sprite = skinAtlasTexture.getSprite(id);
                    break;
                case 1:
                    id = Identifier.of(state.eyeTextureIdentifier.getNamespace(), "npc_eye_textures/" + state.eyeTextureIdentifier.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcEyeTexturesRenderLayer(state.haveEmissiveEyes));
                    sprite = eyeAtlasTexture.getSprite(id);
                    break;
                case 2:
                    id = Identifier.of(state.hairTextureIdentifier.getNamespace(), "npc_hair_textures/" + state.hairTextureIdentifier.getPath());
                    vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());
                    sprite = hairAtlasTexture.getSprite(id);
                    break;
                case 3:
                    id = Identifier.of(state.clothingTextureIdentifier.getNamespace(), "npc_clothing_textures/" + state.clothingTextureIdentifier.getPath());
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

            currentRenderStep++;
        }


        if (this.shouldRenderFeatures(state)) {
            Iterator var15 = this.features.iterator();

            while(var15.hasNext()) {
                FeatureRenderer<NpcEntityRenderState, NpcEntityModel> featureRenderer = (FeatureRenderer)var15.next();
                featureRenderer.render(matrices, vertexConsumers, light, state, state.yawDegrees, state.pitch);
            }
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(NpcEntityRenderState state) {
        return Identifier.of("bleh");
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
