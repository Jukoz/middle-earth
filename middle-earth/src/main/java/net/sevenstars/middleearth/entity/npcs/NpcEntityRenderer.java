package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.AtlasesME;
import net.sevenstars.middleearth.utils.ItemTagsME;
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

        skinAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_SKIN_ATLAS_TEXTURE);
        eyeAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_EYES_ATLAS_TEXTURE);
        hairAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_HAIRS_ATLAS_TEXTURE);
        clothingAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_CLOTHINGS_ATLAS_TEXTURE);

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
        npcEntityRenderState.pose = npcEntity.getPose();
        var npcTextureData = npcEntity.getNpcTextureData();

        npcEntityRenderState.leftArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.OFF_HAND), Hand.OFF_HAND);
        npcEntityRenderState.rightArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.MAIN_HAND), Hand.MAIN_HAND);

        float currentLightLevel = npcEntity.getWorld().getLightLevel(npcEntity.getBlockPos());

        npcEntityRenderState.skinId = npcTextureData.getBodyTexture();
        npcEntityRenderState.headId = npcTextureData.getHeadTexture();
        npcEntityRenderState.earId = npcTextureData.getEarTexture();
        npcEntityRenderState.noseId = npcTextureData.getNoseTexture();
        npcEntityRenderState.eyesId = npcTextureData.getEyeTexture();
        npcEntityRenderState.eyesEmissiveId = npcTextureData.getEyeEmissiveTexture();
        npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive() && currentLightLevel < 8;
        npcEntityRenderState.eyebrowId = npcTextureData.getEyebrowTexture();
        npcEntityRenderState.scarId = npcTextureData.getScarTexture();
        npcEntityRenderState.beardId = npcTextureData.getBeardTexture();
        npcEntityRenderState.beardAddonId = npcTextureData.getBeardAddonTexture();
        npcEntityRenderState.hairId = npcTextureData.getHairTexture();
        npcEntityRenderState.hairAddonId = npcTextureData.getHairAddonTexture();
        npcEntityRenderState.clothingId = npcTextureData.getClothingTexture();
        npcEntityRenderState.blinking = (npcEntity.getInitializationTick() + npcEntity.age) % 80 <= 2;

        ItemStack helmet = npcEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(helmet == null || helmet.isEmpty()){
            npcEntityRenderState.canShowBeard = true;
            npcEntityRenderState.canShowHair = true;
            npcEntityRenderState.canShowEars = true;
        } else {
            var hasAttachment = helmet.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
            boolean hasHoodDown = hasAttachment == null || hasAttachment.down();

            npcEntityRenderState.canShowEars = helmet.isIn(ItemTagsME.CHARACTER_HELMET_SHOW_EARS) && hasHoodDown;
            npcEntityRenderState.canShowBeard = !helmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
            npcEntityRenderState.canShowHair = !helmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hasHoodDown;
        }
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
        if(state.skinId == null || state.headId == null || state.eyesId == null){
            return;
        }
        matrices.push();
        if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }
        else if (state.hasVehicle) {
            float f = state.standingEyeHeight - 0.1F;
            matrices.translate(0, -0.5F, 0);
        }

        float g = ((NpcEntityRenderState)state).baseScale;
        matrices.scale(g, g, g);
        this.setupTransforms(state, matrices, ((NpcEntityRenderState)state).bodyYaw, g);
        matrices.scale(-1.0f, -1.0f, 1.0f);
        this.scale(state, matrices);
        matrices.translate(0.0f, -1.501f, 0.0f);
        ((EntityModel)this.model).setAngles(state);

        this.model.setAngles(state);
        boolean bl = this.isVisible(state);
        boolean bl2 = !bl && !state.invisibleToPlayer;
        int k = bl2 ? 654311423 : -1;
        int color = ColorHelper.mix(k, this.getMixColor(state));
        int overlay = state.hurt ? getOverlay(state, this.getAnimationCounter(state)) : OverlayTexture.DEFAULT_UV;

        // Will always be shown
        renderPart(matrices, vertexConsumers, skinAtlasTexture, ModTexturedRenderLayers.getCharacterSkinsRenderLayer(),
            AtlasesME.prefixAtlas(state.skinId, AtlasesME.CHARACTER_SKINS), light, overlay, color);

        renderPart(matrices, vertexConsumers, skinAtlasTexture, ModTexturedRenderLayers.getCharacterSkinsRenderLayer(),
            AtlasesME.prefixAtlas(state.headId, AtlasesME.CHARACTER_SKINS), light, overlay, color);

        if(!state.blinking){
            renderPart(matrices, vertexConsumers, eyeAtlasTexture, ModTexturedRenderLayers.getCharacterEyesTexturesRenderLayer(false),
                AtlasesME.prefixAtlas(state.eyesId, AtlasesME.CHARACTER_EYES), light, overlay, color);

            if(state.haveEmissiveEyes){
                renderPart(matrices, vertexConsumers, eyeAtlasTexture, ModTexturedRenderLayers.getCharacterEyesTexturesRenderLayer(true),
                    AtlasesME.prefixAtlas(state.eyesEmissiveId, AtlasesME.CHARACTER_EYES), light, overlay, color);
            }
        }
        // Optionally shown, only if the value is present
        if(state.eyebrowId != null){
            renderPart(matrices, vertexConsumers, hairAtlasTexture, ModTexturedRenderLayers.getCharacterHairsRenderLayer(),
                AtlasesME.prefixAtlas(state.eyebrowId, AtlasesME.CHARACTER_HAIRS), light, overlay, color);
        }

        if(state.scarId != null){
            renderPart(matrices, vertexConsumers, skinAtlasTexture, ModTexturedRenderLayers.getCharacterSkinsRenderLayer(),
                    AtlasesME.prefixAtlas(state.scarId, AtlasesME.CHARACTER_SKINS), light, overlay, color);
        }
        if(state.beardId != null){
            renderPart(matrices, vertexConsumers, hairAtlasTexture, ModTexturedRenderLayers.getCharacterHairsRenderLayer(),
                AtlasesME.prefixAtlas(state.beardId, AtlasesME.CHARACTER_HAIRS), light, overlay, color);
        }
        if(state.hairId != null){
            renderPart(matrices, vertexConsumers, hairAtlasTexture, ModTexturedRenderLayers.getCharacterHairsRenderLayer(),
                AtlasesME.prefixAtlas(state.hairId, AtlasesME.CHARACTER_HAIRS), light, overlay, color);
        }
        if(state.clothingId != null){
            renderPart(matrices, vertexConsumers, clothingAtlasTexture, ModTexturedRenderLayers.getCharacterClothingsRenderLayer(),
                AtlasesME.prefixAtlas(state.clothingId, AtlasesME.CHARACTER_CLOTHINGS), light, overlay, color);
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

        if (((EntityRenderState)state).displayName != null) {
            this.renderLabelIfPresent(state, ((EntityRenderState)state).displayName, matrices, vertexConsumers, light);
        }
    }

    private void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, SpriteAtlasTexture atlasTexture, RenderLayer renderLayer, Identifier textureId, int light, int overlay, int color){
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        Sprite sprite = atlasTexture.getSprite(textureId);
        renderModel(sprite, matrices, vertexConsumer, light, overlay, color);
    }

    private void renderModel(Sprite sprite, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            model.render(matrices, newLayerVertexConsumer, light, overlay, color);
        }
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

    private BipedEntityModel.ArmPose getArmPose(NpcEntity npc, ItemStack stack, Hand hand) {
        // TODO : Fix animation

        if (stack.isEmpty()) {
            return BipedEntityModel.ArmPose.EMPTY;
        }
        if (!npc.handSwinging && stack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(stack)) {
            return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
        }
        /*
        if ( stack.getUseAction() == UseAction.BLOCK || npc.isBlocking() && hand == Hand.OFF_HAND) {
            return BipedEntityModel.ArmPose.BLOCK;
        }
         */
        if (npc.getActiveHand() == hand && npc.getItemUseTimeLeft() > 0) {
            UseAction useAction = stack.getUseAction();
            if (useAction == UseAction.BLOCK || npc.isBlocking()) {
                return BipedEntityModel.ArmPose.BLOCK;
            }
            if (useAction == UseAction.BOW) {
                return BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == UseAction.SPEAR) {
                return BipedEntityModel.ArmPose.THROW_SPEAR;
            }
            if (useAction == UseAction.CROSSBOW) {
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
            if (useAction == UseAction.SPYGLASS) {
                return BipedEntityModel.ArmPose.SPYGLASS;
            }
            if (useAction == UseAction.TOOT_HORN) {
                return BipedEntityModel.ArmPose.TOOT_HORN;
            }
            if (useAction == UseAction.BRUSH) {
                return BipedEntityModel.ArmPose.BRUSH;
            }
        }
        return BipedEntityModel.ArmPose.ITEM;
    }

}
