package net.jukoz.me.client.screens;

import me.shedaniel.clothconfig2.api.Tooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.data.FactionNpcPreviewData;
import net.jukoz.me.datageneration.content.models.SimpleButtonModel;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.resource.data.faction.Faction;
import net.jukoz.me.resource.data.faction.ModFactions;
import net.jukoz.me.utils.Factions;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class OnboardingScreen extends Screen {
    private static final Identifier FACTION_SELECTION_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png");
    private static final Identifier FACTION_SELECTION_BANNER_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_banner.png");
    private static final Identifier FACTION_SELECTION_BUTTONS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");


    private static final Identifier MAP_BACKGROUND = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text ONBOARDING_TEXT = Text.of("Onboarding");
    private static boolean debug = false;
    AbstractClientPlayerEntity player;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;

    private static List<FactionNpcPreviewData> NPC_PREVIEWS = new ArrayList<>();

    private ModelPart bannerField;

    private int currentAlignement = 0;
    private int currentFaction = 0;
    private int currentSubFaction = 0;

    @Nullable
    private LongbeardDwarfEntity dwarfEntity;
    @Nullable
    private GondorHumanEntity gondorHumanEntity;
    @Nullable
    LivingEntity entity;
    Map<Alignment, List<Faction>> factions = new HashMap<>();

    public OnboardingScreen() {
        super(ONBOARDING_TEXT);
    }
    @Override
    protected void init() {
        setup();
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");

        // Create faction list
        factions.put(Alignment.Good, ModFactions.getFactions(Alignment.Good));
        factions.put(Alignment.Neutral, ModFactions.getFactions(Alignment.Neutral));
        factions.put(Alignment.Evil,  ModFactions.getFactions(Alignment.Evil));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.player = abstractClientPlayerEntity;

                this.renderBackground(context, mouseX, mouseY, delta);
                int guiScale = this.client.options.getGuiScale().getValue();
                this.drawPanels(context, guiScale);

                //this.drawWindow(context, this.client.options.getGuiScale().getValue());
            } else {
                this.player = null;
            }
        }
    }

    protected void drawPanels(DrawContext context, int guiScale){
        int mainPanelWidth = 169;
        int mainPanelHeight = 207;
        int minimalMargin = 8;

        drawFactionSelectionPanel(context, mainPanelWidth, mainPanelHeight, minimalMargin);
        drawInformationPanel(context, mainPanelWidth, mainPanelHeight);
        drawMapPanel(context, mainPanelWidth, mainPanelHeight, minimalMargin);
    }

    private void drawInformationPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f));
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        context.drawTexture(FACTION_SELECTION_UI,
                startX,
                startY,
                0, 0,
                mainPanelWidth,
                mainPanelHeight
        );

        drawFactionBanner(context, startX + mainPanelWidth - 60, startY + 3);
    }

    private void drawFactionSelectionPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int minimalMargin) {
        int endX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f) - minimalMargin);
        int startX = Math.max(minimalMargin, endX - mainPanelWidth);
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        int widthX = endX - Math.max(minimalMargin, startX);

        int centerX = startX + (int) ((widthX / 2f));

        // Draw alignment option
        Alignment alignment = Alignment.values()[currentAlignement];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFaction) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFaction) : null;
        int voidMargin = 23;

        startY += drawSelectionElement(context, centerX, startY, widthX,95, alignment.getLangKey());

        if(faction != null)
            startY += drawSelectionElement(context, centerX, startY, widthX, 114, faction.getLangKey());
        else
            startY += voidMargin;

        if(subFaction != null)
            startY += drawSelectionElement(context, centerX, startY, widthX, 133, subFaction.getLangKey());
        else
            startY += voidMargin;

        drawNpcPreview(context, centerX, startY + 75);
        drawFactionRandomizer(context, centerX, (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight);
    }

    protected int drawSelectionElement(DrawContext context, int centerX, int startY, int widthX, int uvY, String key){
        int sizeX = 102;
        int sizeY = 18;

        // Text panel
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX - (sizeX / 2f)),
                startY,
                0, uvY,
                sizeX,
                sizeY
        );

        // Dynamic text panel
        context.drawText(textRenderer, Text.translatable(key),
                (int)(centerX - textRenderer.getWidth(Text.translatable(key)) / 2f),
                startY + (int) ((sizeY / 2f) - (textRenderer.fontHeight / 2f)), 0, false);

        int arrowStartY = (int) (startY + (sizeY / 2f) - (13 / 2f));
        // Left arrow
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX - (sizeX / 2f) - 7) - 5,
                arrowStartY,
                192, 1,
                9,
                13
        );
        // Right arrow
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX + (sizeX / 2f)) + 5,
                arrowStartY,
                201, 1,
                9,
                13
        );

        return sizeY + 5;
    }

    protected void drawFactionRandomizer(DrawContext context, int centerX, int endY) {
        int sizeX = 52;
        int sizeY = 18;

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX - (sizeX / 2f)),
                endY - sizeY,
                103, 74,
                sizeX,
                sizeY
        );
    }


    private void drawMapPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int minimalMargin) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) + (mainPanelWidth / 2f)) + minimalMargin;
        int widthX = Math.min(context.getScaledWindowWidth() - startX, mainPanelWidth -minimalMargin);
        context.drawTexture(MAP_TEXTURE,
                startX,
                (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)),
                0, 0,
                widthX -minimalMargin,
                widthX
        );

        int sizeX = 52;
        int sizeY = 18;

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)) - (sizeX + 10),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 111,
                sizeX,
                sizeY
        );

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 19,
                sizeX,
                sizeY
        );
    }



    protected void setup() {
        DiffuseLighting.disableGuiDepthLighting();

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


    private void drawNpcPreview(DrawContext context, float anchorX, float anchorY){
        float size = 50f;
        float x = anchorX;
        float y = anchorY + (size / 2f);

        DiffuseLighting.disableGuiDepthLighting();
        DiffuseLighting.disableForLevel();
        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
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

    private void drawFactionBanner(DrawContext context, float startX, float startY){
        // TODO : Make it so day cycle doesn't affect brightness
        DiffuseLighting.disableGuiDepthLighting();

        float size = 32f;

        float x = startX;
        float y = startY;

        int borderMarginX = 2;
        int borderMarginY = 2;

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x + borderMarginX + (size / 2f) + 4, y + borderMarginY, 0f);
        matrixStack.push();
        matrixStack.scale(-size, size, 0.1f);
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

        BannerBlockEntityRenderer.renderCanvas(matrixStack, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, DyeColor.BLUE, bannerPatternsComponent);
        matrixStack.pop();
        context.draw();
        DiffuseLighting.enableGuiDepthLighting();

        context.drawTexture(FACTION_SELECTION_BANNER_UI,
                (int) x - 2,
                (int) y - 2,
                0, 0,
                48,
                112
        );
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

    /*
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
        // TODO : Clean this up to be data driven!
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

     */
}