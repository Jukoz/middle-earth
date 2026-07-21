package net.sevenstars.middleearth.entity.npcs.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.feet.FeetFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.ItemUtil;
import org.jetbrains.annotations.Nullable;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final SpriteAtlasTexture characterTextureAtlas;

    public final static int HURT_COLOR = 0xff7e75;

    public final static int LIGHT_LEVEL_EMISSIVE_EYES = 8;
    public final static int BLINKING_INTERVAL = 80;
    public final static int BLINKING_DURATION = 3;


    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(EntityModelLayersME.NPC)), 0.7f);

        this.features.removeIf(x -> x.getClass() == ElytraFeatureRenderer.class);
        this.features.removeIf(x -> x.getClass() == HeadFeatureRenderer.class);

        this.addFeature(new ArmorFeatureRenderer<>(this, new ArmorEntityModel<>(context.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new ArmorEntityModel<>(context.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), context.getEquipmentRenderer()));
        this.addFeature(new HairFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new EarFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new NoseFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new FeetFeatureRenderer(this, context.getEntityModels()));

        characterTextureAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);

        this.shadowRadius = 0.5f;
    }

    // region [RenderState]
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    public static float getLOD(Vec3d entityPos) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = client.gameRenderer.getCamera();
        return (float)camera.getPos().distanceTo(entityPos);
        // Keep in case it's needed
        /*int currentFov = client.options.getFov().getValue();
        double fovRatio = currentFov / 90.0;
        return (float) (distance * fovRatio);*/
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float tickDelta) {
        if(!npcEntity.getWorld().isClient)
            return;

        npcEntityRenderState.aimingState = npcEntity.aimingState;

        super.updateRenderState(npcEntity, npcEntityRenderState, tickDelta);
        var npcTextureData = npcEntity.getNpcTextureData();
        float currentLightLevel = npcEntity.getWorld().getLightLevel(npcEntity.getBlockPos());

        npcEntityRenderState.pose = npcEntity.getPose();

        npcEntityRenderState.leftArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.OFF_HAND), Hand.OFF_HAND);
        npcEntityRenderState.rightArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.MAIN_HAND), Hand.MAIN_HAND);

        npcEntityRenderState.widthScale = npcEntity.getWidthScale();

        npcEntityRenderState.simplifiedSkinId = npcTextureData.getSimplifiedSkin();
        npcEntityRenderState.simplifiedEarId = npcTextureData.getSimplifiedEar();
        npcEntityRenderState.simplifiedFeetId = npcTextureData.getSimplifiedFeet();
        npcEntityRenderState.simplifiedHairAddonId = npcTextureData.getSimplifiedHair();
        npcEntityRenderState.simplifiedNoseId = npcTextureData.getSimplifiedNose();

        if(!ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING || npcEntityRenderState.simplifiedSkinId == null){
            npcEntityRenderState.skinId = npcTextureData.get(NpcRenderedPart.BODY);
            npcEntityRenderState.feetId = npcTextureData.get(NpcRenderedPart.FEET);
            npcEntityRenderState.headId = npcTextureData.get(NpcRenderedPart.HEAD);
            npcEntityRenderState.earId = npcTextureData.get(NpcRenderedPart.EAR);
            npcEntityRenderState.noseId = npcTextureData.get(NpcRenderedPart.NOSE);
            npcEntityRenderState.eyesId = npcTextureData.get(NpcRenderedPart.EYE);
            npcEntityRenderState.eyesEmissiveId = npcTextureData.get(NpcRenderedPart.EYE_EMISSIVE);
            npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive() && currentLightLevel <= LIGHT_LEVEL_EMISSIVE_EYES;
            npcEntityRenderState.eyebrowId = npcTextureData.get(NpcRenderedPart.EYEBROW);
            npcEntityRenderState.scarId = npcTextureData.get(NpcRenderedPart.SCAR);
            npcEntityRenderState.beardId = npcTextureData.get(NpcRenderedPart.BEARD);
            npcEntityRenderState.beardAddonId = npcTextureData.get(NpcRenderedPart.BEARD_ADDON);
            npcEntityRenderState.hairId = npcTextureData.get(NpcRenderedPart.HAIR);
            npcEntityRenderState.hairAddonId = npcTextureData.get(NpcRenderedPart.HAIR_ADDON);

            npcEntityRenderState.clothingBase = npcTextureData.get(NpcRenderedPart.CLOTHING_BASE);
            npcEntityRenderState.clothingOver = npcTextureData.get(NpcRenderedPart.CLOTHING_OVER);
            npcEntityRenderState.clothingExtra = npcTextureData.get(NpcRenderedPart.CLOTHING_EXTRA);

            long initializationTick = npcEntity.getInitializationTick();
            long age = npcEntity.age;
            npcEntityRenderState.blinking = (initializationTick + age) % BLINKING_INTERVAL >= BLINKING_INTERVAL - BLINKING_DURATION;
        }
        ItemStack currentHelmet = npcEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(currentHelmet == null || currentHelmet.isEmpty()){
            npcEntityRenderState.canShowBeard = true;
            npcEntityRenderState.canShowHair = true;
            npcEntityRenderState.canShowEars = true;
        } else {
            var hasAttachment = currentHelmet.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
            boolean hasHoodDown = hasAttachment == null || hasAttachment.down();

            npcEntityRenderState.canShowEars = currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_SHOW_EARS) && hasHoodDown;
            npcEntityRenderState.canShowBeard = !currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
            npcEntityRenderState.canShowHair = !currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hasHoodDown;
        }
        ItemStack currentShoes = npcEntity.getEquippedStack(EquipmentSlot.FEET);
        npcEntityRenderState.canShowFeet = currentShoes == null || currentShoes.isEmpty();
        npcEntityRenderState.LOD = getLOD(npcEntity.getPos());
    }
    // endregion

    // region [Layered Texture Renderer]
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
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;

        if(!simplified && (state.skinId == null || state.headId == null || state.eyesId == null))
            return;


        matrices.push();
        if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }
        else if (state.hasVehicle) {
            matrices.translate(0, -0.5F, 0);
        }

        float g = state.baseScale;
        float widthScale = state.widthScale;

        matrices.scale(g * widthScale, g, g * widthScale);
        this.setupTransforms(state, matrices, state.bodyYaw, g);
        matrices.scale(-widthScale, -1.0f, widthScale);
        this.scale(state, matrices);
        matrices.translate(0.0f, -1.501f, 0.0f);

        this.model.setAngles(state);
        int overlay = state.hurt ? getOverlay(state, this.getAnimationCounter(state)) : OverlayTexture.DEFAULT_UV;

        if(simplified){
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());
            renderTexture(matrices, vertexConsumer, state.simplifiedSkinId, light, overlay, false);
        } else {
            renderComplexVersion(matrices, vertexConsumers, light, overlay, state);
        }

        if (this.shouldRenderFeatures(state) && state.LOD < ModClientConfigs.LOD_NPC_ARMOR_DISTANCE) {
            for (FeatureRenderer<NpcEntityRenderState, NpcEntityModel> feature : this.features) {
                if (feature instanceof EarFeatureRenderer)
                    if ((state.simplifiedEarId == null && state.earId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof NoseFeatureRenderer)
                    if ((state.simplifiedNoseId == null && state.noseId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof HairFeatureRenderer)
                    if ((state.simplifiedHairAddonId == null && state.hairAddonId == null && state.beardAddonId == null)
                            || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if(feature instanceof FeetFeatureRenderer)
                    if((state.simplifiedFeetId == null && state.feetId == null) || state.LOD > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                feature.render(matrices, vertexConsumers, light, state, state.relativeHeadYaw, state.pitch);
            }
        }

        matrices.pop();

        if ((state).displayName != null) {
            this.renderLabelIfPresent(state, (state).displayName, matrices, vertexConsumers, light);
        }
    }

    private void renderComplexVersion(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, NpcEntityRenderState state) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        // Will always be shown
        renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.skinId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.headId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(!state.blinking){
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.eyesId, AtlasesME.EYE_PREFIX), light, overlay, false);
        }
        // Optionally shown, only if the value is present
        if(state.eyebrowId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.eyebrowId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.scarId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.scarId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(state.beardId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.beardId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.clothingBase == null && state.clothingOver == null && state.clothingExtra == null){
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(CharacterClothesRegistryME.Base.THONG_BROWN, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);
        }
        else {
            if(state.clothingBase != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingBase, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);

            if(state.clothingOver != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingOver, AtlasesME.CLOTHES_OVER_PREFIX), light, overlay, false);

            if(state.clothingExtra != null)
                renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.clothingExtra, AtlasesME.CLOTHES_EXTRA_PREFIX), light, overlay, false);
        }

        if(state.hairId != null)
            renderTexture(matrices, vertexConsumer, MiddleEarth.ofPrefix(state.hairId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(!state.blinking && state.haveEmissiveEyes){
            VertexConsumer vertexConsumerEmissive = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer());
            renderTexture(matrices, vertexConsumerEmissive, MiddleEarth.ofPrefix(state.eyesEmissiveId, AtlasesME.EYE_PREFIX), light, overlay, true);
        }
    }

    private void renderTexture(MatrixStack matrices, VertexConsumer vertexConsumer, Identifier textureId, int light, int overlay, boolean isEmissive){
        RenderUtil.renderAtlasTexture(characterTextureAtlas, model, matrices, vertexConsumer, textureId, light, overlay);
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
        if(npc.isAiming()){
            return BipedEntityModel.ArmPose.BOW_AND_ARROW;
        }

        if (stack.isEmpty()) {
            return BipedEntityModel.ArmPose.EMPTY;
        }
        if (!npc.handSwinging && (stack.isOf(Items.CROSSBOW) || stack.isIn(ItemTagsME.CROSSBOW))) {
            if(CrossbowItem.isCharged(stack)) {
                return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }
            else if(npc.isCharging()) {
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
        }
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
    // endregion
}
