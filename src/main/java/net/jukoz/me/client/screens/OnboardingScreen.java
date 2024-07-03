package net.jukoz.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.rei.api.client.overlay.ScreenOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.data.FactionNpcPreviewData;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.utils.Factions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OnboardingScreen extends Screen {
    private static final Identifier MAP_BACKGROUND = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text ONBOARDING_TEXT = Text.of("Onboarding");
    private static boolean debug = false;
    AbstractClientPlayerEntity player;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;

    private static List<FactionNpcPreviewData> NPC_PREVIEWS = new ArrayList<>();

    private ModelPart bannerField;

    private List<Float> guiScaleModifier = new ArrayList<Float>(){{
        add(1f); // Auto
        add(1.25f);
        add(1f);
        add(0.75f);
        add(0.5f);
    }};

    @Nullable
    private LongbeardDwarfEntity dwarfEntity;
    @Nullable
    private GondorHumanEntity gondorHumanEntity;
    @Nullable
    LivingEntity entity;

    public OnboardingScreen() {
        super(ONBOARDING_TEXT);
    }

    @Override
    protected void init() {
        setup();
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.player = abstractClientPlayerEntity;

                this.renderBackground(context, mouseX, mouseY, delta);
                this.drawWindow(context, this.client.options.getGuiScale().getValue());
            } else {
                this.player = null;
            }
        }
    }

    protected void setup() {
        ButtonWidget debugButton = ButtonWidget.builder(Text.literal("Debug"), button -> {
                    debug = !debug;
                })
                .dimensions(0, 0, 50, 50).build();
        addDrawableChild(debugButton);


        this.dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_ELITE, this.client.world);
        this.gondorHumanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_LEADER, this.client.world);

        FactionNpcPreviewData prevData = NPC_PREVIEWS.stream().filter(f -> f.FACTION == Factions.EREBOR).findFirst().get();
        this.entity = this.dwarfEntity;

        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, prevData.HEAD);
        this.entity.equipStack(EquipmentSlot.CHEST, prevData.CHEST);
        this.entity.equipStack(EquipmentSlot.LEGS, prevData.LEGS);
        this.entity.equipStack(EquipmentSlot.FEET, prevData.FEET);
        this.entity.equipStack(EquipmentSlot.MAINHAND, prevData.MAIN_HAND);
        this.entity.equipStack(EquipmentSlot.OFFHAND, prevData.OFF_HAND);
    }

    public void drawWindow(DrawContext context, int guiScale) {
        RenderSystem.enableBlend();
        int xCenter = (context.getScaledWindowWidth() / 2);
        int yCenter = (context.getScaledWindowHeight() / 2);

        drawMap(context, (float) xCenter / 2 * 3, yCenter, guiScale);

        drawNpcPreview(context, xCenter, yCenter);
        drawFactionBanner(context, xCenter, yCenter / 2f * 3f);

    }

    private void drawNpcPreview(DrawContext context, float anchorX, float anchorY){
        float size = 30f;
        float x = anchorX;
        float y = anchorY + (size / 2f);

        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
    }

    private void drawMap(DrawContext context, float anchorX, float anchorY, int guiScale){
        int size = (int) (400 * guiScaleModifier.get(guiScale));

        int margin = 4;

        context.drawTexture(MAP_BACKGROUND,
                (int) anchorX - ((size + margin) / 2),
                (int) anchorY - ((size + margin) / 2),
                0, 0,
                size + margin,
                size + margin,
                size + margin,
                size + margin
        );

        context.drawTexture(MAP_TEXTURE,
                (int) anchorX - (size / 2),
                (int) anchorY - (size / 2),
                0, 0,
                size,
                size,
                size,
                size
        );

        drawMapMarkers(context, (int) anchorX - ((float) size / 2) - margin, (int) anchorY - ((float) size / 2) - margin, size, guiScale);
    }

    private void drawMapMarkers(DrawContext context, float anchorX, float anchorY, int size, int guiScale) {
        int markerSize = (int) (8);
        int x = (int) anchorX;
        int y = (int) anchorY;

        int mapSize = 96000;
        Vector2f coord01 = new Vector2f(48000, 48000);
        coord01.x = ((float) size / mapSize * coord01.x);
        coord01.y = ((float) size / mapSize * coord01.y);

        Vector2f coord02 = new Vector2f(64700, 23000);
        coord02.x = ((float) size / mapSize * coord02.x);
        coord02.y = ((float) size / mapSize * coord02.y);

        Vector2f coord03 = new Vector2f(62000, 57000);
        coord03.x = ((float) size / mapSize * coord03.x);
        coord03.y = ((float) size / mapSize * coord03.y);

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord01.x)),
                (int) (y + (coord01.y)),
                54, 0,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord02.x)),
                (int) (y + (coord02.y)),
                54, 8,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord03.x)),
                (int) (y + (coord03.y)),
                54, 16,
                markerSize,
                markerSize
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 27){
            this.close();
            return true;
        }
        if (keyCode == 256) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    static {
        VECTOR = new Vector3f();
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);

        NPC_PREVIEWS.add(new FactionNpcPreviewData(
                Factions.EREBOR,
                ModEquipmentItems.EREBOR_PLATE_HELMET,
                ModEquipmentItems.EREBOR_PLATE_CHESTPLATE,
                ModEquipmentItems.EREBOR_PLATE_LEGGINGS,
                ModEquipmentItems.EREBOR_PLATE_BOOTS,
                ModWeaponItems.LONGBEARD_SWORD,
                ModEquipmentItems.LONGBEARD_SHIELD
        ));
        NPC_PREVIEWS.add(new FactionNpcPreviewData(
                Factions.GONDOR,
                ModEquipmentItems.GONDORIAN_PLATE_HELMET,
                ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE,
                ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS,
                ModEquipmentItems.GONDORIAN_PLATE_BOOTS,
                ModWeaponItems.GONDOR_LONGSWORD,
                ModEquipmentItems.GONDORIAN_SHIELD
        ));
    }
    private void drawFactionBanner(DrawContext context, float anchorX, float anchorY){
        // TODO : Make it so day cycle doesn't affect brightness
        DiffuseLighting.disableGuiDepthLighting();

        float size = 30f;
        float ratioX = 0f;
        float ratioY = 3f;

        float x = anchorX;
        float y = anchorY;


        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x - ((size * ratioX) / 2f), y - ((size * ratioY) / 2f), 0);
        matrixStack.push();
        matrixStack.scale(-size, size, 1.0F);
        this.bannerField.pitch = 0.0F;

        List<RegistryEntry<BannerPattern>> list = new ArrayList<>();

        var bannerPatternRegistry = this.client.world.getRegistryManager().get(RegistryKeys.BANNER_PATTERN);

        list.add(bannerPatternRegistry.getEntry(BannerPatterns.CURLY_BORDER).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.GRADIENT_UP).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.TRIANGLE_BOTTOM).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.TRIANGLES_BOTTOM).get());
        list.add(bannerPatternRegistry.getEntry(ModBannerPatterns.DRAGON_BANNER_PATTERN).get());

        BannerPatternsComponent bannerPatternsComponent = new BannerPatternsComponent.Builder()
        .add(list.get(0), DyeColor.WHITE)
        .add(list.get(1), DyeColor.BROWN)
        .add(list.get(2), DyeColor.BLACK)
        .add(list.get(3), DyeColor.GREEN)
        .add(list.get(4), DyeColor.RED)
        .build();

        BannerBlockEntityRenderer.renderCanvas(matrixStack, context.getVertexConsumers(), 16777215, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, DyeColor.BLUE, bannerPatternsComponent);
        matrixStack.pop();
        context.draw();
        DiffuseLighting.enableGuiDepthLighting();
        }

}