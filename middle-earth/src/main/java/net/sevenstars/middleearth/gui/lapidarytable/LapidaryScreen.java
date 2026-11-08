package net.sevenstars.middleearth.gui.lapidarytable;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.gui.lapidarytable.LapidaryScreenHandler;
import net.sevenstars.middleearth.recipe.lapidary.RefractionReading;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;
import net.sevenstars.middleearth.recipe.lapidary.Faceting;
import net.sevenstars.middleearth.recipe.lapidary.GemQuality;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.item.items.gems.CutGemItem;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.client.texture.NativeImage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

/**
 * The Lapidary Table screen and the study minigame, Refraction Reading.
 * <p>
 * Left: the board, a stone disc. The gem in the middle, a candle (seen from above) on the inner groove, the
 * Cirth runes on the outer groove and the candle's light traced live through the gem. Right: the paper, gem and
 * notes slots, the Study button and the score on parchment.
 * <p>
 * Controls: drag the candle round its groove (or Left / Right arrow: one step, one move); turn the gem by
 * dragging it, scrolling, A / D or the arrow buttons (one move a step); click a rune while the light is on it
 * to lock it. One candle drag is one move however far it goes.
 * <p>
 * Effects are client-only: flame particles on the candle, a flash and sparks when a rune locks, a flash of the
 * gem and the grade stamped across the board when the study is done.
 * <p>
 * The table's second screen is Faceting: pick a cut of the next tier, press Cut (one water bucket), then grind
 * each facet: hold the mouse on the board or Space to push the grind marker along the bar, against its fall, and
 * keep it inside the drifting zone. The bar runs here; each facet's score goes to the server.
 */
@Environment(EnvType.CLIENT)
public class LapidaryScreen extends HandledScreen<LapidaryScreenHandler> {
    private static final Identifier BACKGROUND = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/lapidary_table.png");
    private static final Identifier BACKGROUND_FACETING = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/lapidary_faceting.png");
    /** The Faceting board with each tool set installed: its wheel, lenses, lantern and pump fitting on the table. */
    private static final Identifier BACKGROUND_FACETING_MASTERWORK = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/lapidary_faceting_masterwork.png");
    private static final Identifier BACKGROUND_FACETING_RELIC = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/lapidary_faceting_relic.png");
    private static final Identifier FLAME = Identifier.ofVanilla("textures/particle/flame.png");
    private static final Identifier SMOKE = Identifier.ofVanilla("textures/particle/generic_0.png");
    private static final Identifier GLINT = Identifier.ofVanilla("textures/particle/glint.png");
    private static final Identifier XP_ORB = Identifier.ofVanilla("textures/entity/experience_orb.png");

    private static final int WIDTH = 248;
    private static final int HEIGHT = 260;
    private static final int BOARD_X = 84;
    private static final int BOARD_Y = 90;
    private static final float SCALE = 26.0F;
    /** Where the rune glyphs are drawn: the middle of the brass band (radius 63 to 73 in the texture). */
    private static final double RUNE_DRAW_RADIUS = 68.0;
    private static final int PANEL_X = 164;

    private static final int COLOR_BEAM = 0xFFD27A;
    private static final int COLOR_BEAM_GLOW = 0xFF9A2E;
    private static final int COLOR_LABEL = 0xFF404040;
    private static final int INK = 0xFF3B2A1A;
    private static final int INK_DIM = 0xFF7A6448;
    private static final int INK_GOOD = 0xFF2F6B1F;
    private static final int INK_BAD = 0xFF9E2B25;

    /** Cirth-like staves, 5 x 7 pixels each, engraved round the brass rim. */
    private static final String[][] RUNES = {
        {"#....", "##...", "#.#..", "#..#.", "#....", "#....", "#...."},
        {"..#..", "..#..", ".###.", "#.#.#", "..#..", "..#..", "..#.."},
        {"#...#", "#..#.", "#.#..", "##...", "#.#..", "#..#.", "#...#"},
        {"..#..", ".###.", "#.#.#", "..#..", "..#..", "..#..", "..#.."},
        {"#...#", ".#.#.", "..#..", "..#..", "..#..", ".#.#.", "#...#"},
        {"..#..", ".#.#.", "#...#", ".#.#.", "..#..", "..#..", "..#.."},
        {"#...#", "##.##", "#.#.#", "#...#", "#...#", "#...#", "#...#"},
        {"...#.", "..#..", ".#...", "..#..", "...#.", "..#..", ".#..."},
    };

    private ButtonWidget studyButton;
    private ButtonWidget turnLeft;
    private ButtonWidget turnRight;
    private ButtonWidget readingButton;
    private ButtonWidget facetingButton;
    private ButtonWidget cutButton;
    private final IconButton[] cutChoices = new IconButton[GemCut.values().length];

    // ---- Faceting, client side ----
    private static final long READY_MILLIS = 1000;
    /**
     * The marker moves at a steady speed: right while held, back left when let go. It eases into its new
     * direction within about a tenth of a second (a very mild momentum), so it stops where the player expects.
     */
    private static final double GRIND_SPEED = 0.5;
    /** How quickly the marker takes up its new speed: lower makes a short tap a smaller nudge. */
    private static final double RESPONSE = 8.0;
    private static final int BAR_HALF = 50;
    /**
     * The board layout of the Faceting background, the table's top seen from above: the wheel's rim showing in
     * the slot (x RIM_X0..RIM_X1, y RIM_Y0..RIM_Y1), the gem on it, and the gauge plate centred on LAP_X.
     */
    private static final int LAP_X = 84;
    private static final int RIM_X0 = 78;
    private static final int RIM_X1 = 91;
    private static final int RIM_Y0 = 23;
    private static final int RIM_Y1 = 119;
    private static final int GEM_X = 85;
    private static final int GEM_Y = 71;
    /** The lens (between the gem and the player): particles passing under it are hidden by it. */
    private static final int LENS_X = 85;
    private static final int LENS_Y = 104;
    private static final int LENS_R = 19;
    private static final int PIPS_Y = 131;
    private static final int BAR_Y = 140;

    private enum Phase {
        /** Waiting for the player to grind the next facet. */
        IDLE,
        /** A short countdown before the facet starts. */
        READY,
        RUNNING,
        /** Score sent; waiting for the server to count it. */
        SENT
    }

    private Phase phase = Phase.IDLE;
    private long phaseStart;
    private int runFacet;
    private double runSeconds;
    private double inZoneSeconds;
    private double marker;
    private double markerSpeed;
    private boolean grindHeld;
    private int lastScore = -1;
    private long lastScoreShown;
    private @org.jetbrains.annotations.Nullable GemCut chosenCut;
    private boolean lastCutActive;
    /** The cut just finished in this screen: its result is shown until the gem is taken. */
    private boolean showResult;

    // ---- The gem being ground: its old and new sprites, pixel by pixel ----
    /** Each item sprite's 16 x 16 pixels (ARGB), read once from its texture. */
    private final Map<String, int[]> artCache = new HashMap<>();
    /** How far the gem has turned into its new shape, as last drawn (0-1), to see which pixels just changed. */
    private double lastMorph;
    /** The gem's turn on the dop, in degrees: each facet turns it on, eased during the countdown. */
    private float gemAngle;
    private float sparkSpawn;

    /** Where the candle and gem are drawn: the player's own moves show at once, before the server echoes them. */
    private int localLamp;
    private int localGem;
    private int pendingTicks;
    private boolean draggingLamp;
    private boolean draggingGem;
    private double gemDragAngle;
    private double gemDragAccumulated;
    private int lastState = -1;
    private int lastLocked;

    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();
    private final long[] lockFlash = new long[LapidaryScreenHandler.MAX_RUNES];
    private long finishFlash;
    /** The grade goal shown while studying, the one it replaced and when, for the crossfade. */
    private StudyGrade aimGrade;
    private StudyGrade previousAim;
    private long aimChanged;
    private long lastFrame;
    private float flameSpawn;
    private float smokeSpawn;
    /** How much the whole screen is shrunk to fit the window (1 = full size); see {@link #init()}. */
    private float fit = 1.0F;

    public LapidaryScreen(LapidaryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundWidth = WIDTH;
        backgroundHeight = HEIGHT;
        titleX = 8;
        titleY = 4;
        playerInventoryTitleX = LapidaryScreenHandler.INVENTORY_X;
        playerInventoryTitleY = LapidaryScreenHandler.INVENTORY_Y - 10;
    }

    @Override
    protected void init() {
        // The table is bigger than vanilla screens. When it does not fit the window at the current GUI scale,
        // lay it out in a larger virtual screen and draw that shrunk down, so it always fits whole.
        int realWidth = client.getWindow().getScaledWidth();
        int realHeight = client.getWindow().getScaledHeight();
        fit = Math.min(1.0F, Math.min((float) realWidth / WIDTH, (float) realHeight / HEIGHT));
        width = (int) Math.ceil(realWidth / fit);
        height = (int) Math.ceil(realHeight / fit);
        super.init();
        studyButton = addDrawableChild(ButtonWidget.builder(Text.translatable("gui.middle-earth.lapidary.study"),
                button -> click(LapidaryScreenHandler.START))
            .dimensions(x + PANEL_X + 4, y + 44, 72, 18).build());
        turnLeft = addDrawableChild(new ArrowButton(x + PANEL_X + 4, y + 66, 35, 18, true, () -> turnGem(1)));
        turnRight = addDrawableChild(new ArrowButton(x + PANEL_X + 41, y + 66, 35, 18, false, () -> turnGem(-1)));
        // The two screens of the table, centred in the space left of the inventory.
        int modeX = x + (3 + LapidaryScreenHandler.INVENTORY_X - 1) / 2 - 10;
        readingButton = addDrawableChild(new IconButton(modeX, y + LapidaryScreenHandler.INVENTORY_Y,
            new ItemStack(Items.CANDLE), 0, Text.translatable("gui.middle-earth.lapidary.mode.reading"),
            () -> setMode(false)));
        facetingButton = addDrawableChild(new IconButton(modeX, y + LapidaryScreenHandler.INVENTORY_Y + 22,
            ResourceItemsME.cutGem(GemCut.BRILLIANT, GemTypeRegistry.RUBY), 1,
            // The brilliant cut's art fills rows 2-15 of its frame, a pixel below centre.
            Text.translatable("gui.middle-earth.lapidary.mode.faceting"), () -> setMode(true)));
        cutButton = addDrawableChild(ButtonWidget.builder(Text.translatable("gui.middle-earth.lapidary.faceting.cut"),
                button -> pressCut())
            .dimensions(x + PANEL_X + 4, y + 44, 72, 18).build());
        for (GemCut cut : GemCut.values()) {
            cutChoices[cut.ordinal()] = addDrawableChild(new IconButton(0, 0, 18,
                ResourceItemsME.cutGem(cut, GemTypeRegistry.RUBY), 0, Text.translatable(cut.translationKey()),
                () -> chosenCut = cut));
        }
        lastCutActive = handler.isCutActive();
        updateModeButtons();
        localLamp = handler.getLamp();
        localGem = handler.getGemStep();
        lastState = handler.getState();
        lastLocked = lockedMask();
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        int state = handler.getState();
        if (state != lastState) {
            pendingTicks = 0;
            if (state == LapidaryScreenHandler.STATE_DONE) {
                finishFlash = Util.getMeasuringTimeMs();
            }
            if (state == LapidaryScreenHandler.STATE_STUDYING) {
                // A new study starts its goal fresh, without fading from the last one.
                aimGrade = null;
                previousAim = null;
            }
            lastState = state;
        }
        int locked = lockedMask();
        for (int rune = 0; rune < LapidaryScreenHandler.MAX_RUNES; rune++) {
            if ((locked & ~lastLocked & (1 << rune)) != 0) {
                lockFlash[rune] = Util.getMeasuringTimeMs();
                burst(runeOffset(rune), 10, 0xFFFFD060);
            }
        }
        lastLocked = locked;
        if (pendingTicks > 0) {
            pendingTicks--;
        } else if (!draggingLamp && !draggingGem) {
            localLamp = handler.getLamp();
            localGem = handler.getGemStep();
        }
        LapidaryScreenHandler.Setup setup = setup();
        boolean studying = state == LapidaryScreenHandler.STATE_STUDYING;
        studyButton.active = !studying && setup != null && !handler.hasNotesWaiting()
            && handler.hasPaper()
            && (client.player.isCreative() || client.player.experienceLevel >= setup.band().xpLevels());
        studyButton.setMessage(Text.translatable(state == LapidaryScreenHandler.STATE_DONE
            ? "gui.middle-earth.lapidary.study_again" : "gui.middle-earth.lapidary.study"));
        turnLeft.active = studying;
        turnRight.active = studying;
        facetingTick();
        updateModeButtons();
    }

    /** Which of the table's two screens shows: the server keeps it, so slots and moves follow. */
    private boolean reading() {
        return !handler.isFaceting();
    }

    private void setMode(boolean faceting) {
        draggingLamp = false;
        draggingGem = false;
        grindHeld = false;
        click(faceting ? LapidaryScreenHandler.MODE_FACETING : LapidaryScreenHandler.MODE_READING);
    }

    /**
     * The current screen's button is greyed out; the reading controls only show while reading. The screens
     * cannot be switched while a game is in progress.
     */
    private void updateModeButtons() {
        boolean busy = handler.getState() == LapidaryScreenHandler.STATE_STUDYING || handler.isCutActive()
            || phase != Phase.IDLE;
        readingButton.active = !busy && !reading();
        facetingButton.active = !busy && reading();
        studyButton.visible = reading();
        turnLeft.visible = reading();
        turnRight.visible = reading();
        cutButton.visible = !reading();
        layoutCutChoices();
    }

    // ---- Faceting ----

    private @org.jetbrains.annotations.Nullable RegistryKey<GemType> cutGemType() {
        return handler.getCutGem().get(DataComponentTypesME.GEM_TYPE);
    }

    /** The cut choices of the next tier, in a centred row under the Cut button; hidden during a cut. */
    private void layoutCutChoices() {
        int tier = Faceting.nextTier(handler.getCutGem());
        boolean choosing = !reading() && !handler.isCutActive() && tier > 0;
        List<GemCut> cuts = choosing ? Faceting.cutsOfTier(tier) : List.of();
        if (chosenCut != null && !cuts.contains(chosenCut)) {
            chosenCut = null;
        }
        RegistryKey<GemType> type = cutGemType();
        // Buttons side by side (18 px each), centred on the parchment so four still fit inside its border.
        int rowX = x + PANEL_X + 39 - cuts.size() * 18 / 2;
        for (IconButton button : cutChoices) {
            button.visible = false;
        }
        for (int i = 0; i < cuts.size(); i++) {
            GemCut cut = cuts.get(i);
            IconButton button = cutChoices[cut.ordinal()];
            button.visible = true;
            button.active = cut != chosenCut;
            button.setX(rowX + i * 18);
            button.setY(y + 66);
            if (type != null) {
                button.setIcon(ResourceItemsME.cutGem(cut, type));
            }
        }
    }

    private void pressCut() {
        if (!handler.isCutActive()) {
            if (chosenCut != null) {
                click(LapidaryScreenHandler.START_CUT + chosenCut.ordinal());
            }
        } else if (phase == Phase.IDLE && !handler.isFacetRunning()) {
            phase = Phase.READY;
            phaseStart = Util.getMeasuringTimeMs();
            runFacet = handler.getFacet();
            marker = 0.5;
            markerSpeed = 0;
            runSeconds = 0;
            inZoneSeconds = 0;
        }
    }

    private void facetingTick() {
        boolean active = handler.isCutActive();
        if (lastCutActive && !active) {
            // The finished gem catches the light.
            for (int i = 0; i < 16; i++) {
                Particle p = new Particle();
                float angle = random.nextFloat() * MathHelper.TAU;
                float speed = 15 + random.nextFloat() * 25;
                p.px = GEM_X - BOARD_X;
                p.py = GEM_Y - BOARD_Y;
                p.vx = MathHelper.cos(angle) * speed;
                p.vy = MathHelper.sin(angle) * speed;
                p.life = 0.4F + random.nextFloat() * 0.3F;
                p.size0 = 2;
                p.size1 = 1;
                p.color = 0xFFFFFFFF;
                particles.add(p);
            }
            showResult = true;
            phase = Phase.IDLE;
            chosenCut = null;
        }
        lastCutActive = active;
        if (handler.getCutGem().isEmpty()) {
            showResult = false;
        }
        if (phase == Phase.SENT && (handler.getFacet() != runFacet || !active
            || Util.getMeasuringTimeMs() - phaseStart > 3000)) {
            phase = Phase.IDLE;
        }
        if (active) {
            cutButton.setMessage(Text.translatable("gui.middle-earth.lapidary.faceting.grind"));
            cutButton.active = phase == Phase.IDLE && !handler.isFacetRunning();
        } else {
            cutButton.setMessage(Text.translatable("gui.middle-earth.lapidary.faceting.cut"));
            cutButton.active = chosenCut != null && handler.hasWater();
        }
    }

    private @org.jetbrains.annotations.Nullable StudyGrade cutGrade() {
        return handler.getCutGem().get(DataComponentTypesME.STUDY_GRADE);
    }

    /** Moves the bar on by {@code seconds} of play: the countdown, the marker and the facet's score. */
    private void updateFacet(float seconds) {
        long now = Util.getMeasuringTimeMs();
        if (phase == Phase.READY && now - phaseStart >= READY_MILLIS) {
            phase = Phase.RUNNING;
            phaseStart = now;
            click(LapidaryScreenHandler.FACET_BEGIN);
        }
        if (phase != Phase.RUNNING) {
            return;
        }
        double dt = Math.min(seconds, Faceting.FACET_SECONDS - runSeconds);
        double target = grindHeld ? GRIND_SPEED : -GRIND_SPEED;
        markerSpeed += (target - markerSpeed) * (1 - Math.exp(-RESPONSE * dt));
        marker += markerSpeed * dt;
        if (marker <= 0 || marker >= 1) {
            marker = MathHelper.clamp(marker, 0, 1);
            markerSpeed = 0;
        }
        runSeconds += dt;
        if (Faceting.zone(handler.getCut(), runFacet, cutGrade(), runSeconds).contains(marker)) {
            inZoneSeconds += dt;
        }
        if (runSeconds >= Faceting.FACET_SECONDS) {
            lastScore = (int) Math.round(100 * inZoneSeconds / runSeconds);
            lastScoreShown = now;
            phase = Phase.SENT;
            phaseStart = now;
            grindHeld = false;
            click(LapidaryScreenHandler.FACET_RESULT + MathHelper.clamp(lastScore, 0, 100));
        }
    }

    /** The Faceting board: the gem large in the middle, its facets as pips, and the grinding bar. */
    private void drawFacetingBoard(DrawContext context) {
        int cx = x + LAP_X;
        long now = Util.getMeasuringTimeMs();
        if (phase == Phase.RUNNING) {
            drawSpinningRim(context, now);
        }
        ItemStack gem = handler.getCutGem();
        RegistryKey<GemType> type = cutGemType();
        boolean active = handler.isCutActive();
        // The gem keeps its old shape until it is ground: mid-cut it turns into the cut being made.
        ItemStack shown = gem;
        if (type != null && active) {
            shown = ResourceItemsME.cutGem(handler.getCut(), type);
        }
        if (!active) {
            lastMorph = 0;
            gemAngle = 0;
            if (!shown.isEmpty()) {
                // Item size, so its pixels match the board's.
                context.drawItem(shown, x + GEM_X - 8, y + GEM_Y - 8);
            }
            drawParticles(context, x + BOARD_X, y + BOARD_Y);
            return;
        }
        drawGrindingGem(context, gem, shown);
        drawParticles(context, x + BOARD_X, y + BOARD_Y);
        GemCut cut = handler.getCut();
        int facets = Faceting.facets(cut.tier());
        int done = handler.getFacet();
        int pipsX = cx - (facets * 11 - 5) / 2 + 3;
        for (int i = 0; i < facets; i++) {
            boolean current = i == done && phase != Phase.IDLE;
            drawPip(context, pipsX + i * 11, y + PIPS_Y, i < done ? 0xFFC98A10 : current ? 0xFFFFF2B0 : 0, 0xFF2A1E12);
        }

        // The bar: dark groove, the zone, the marker and how much of the facet is left.
        int left = cx - BAR_HALF;
        int right = cx + BAR_HALF;
        int top = y + BAR_Y;
        context.fill(left - 1, top - 1, right + 1, top + 9, 0xFF000000);
        context.fill(left, top, right, top + 8, 0xFF3A3028);
        double seconds = phase == Phase.RUNNING ? runSeconds : 0;
        int facet = phase == Phase.IDLE ? done : runFacet;
        Faceting.Zone zone = Faceting.zone(cut, facet, cutGrade(), seconds);
        int zoneLeft = left + (int) Math.round((zone.center() - zone.halfWidth()) * BAR_HALF * 2);
        int zoneRight = left + (int) Math.round((zone.center() + zone.halfWidth()) * BAR_HALF * 2);
        boolean inZone = zone.contains(marker);
        context.fill(zoneLeft, top, zoneRight, top + 8, phase == Phase.RUNNING && inZone ? 0xFF6FCF5A : 0xFF3F8A34);
        if (phase == Phase.READY || phase == Phase.RUNNING) {
            int mx = left + (int) Math.round(marker * BAR_HALF * 2);
            context.fill(mx - 1, top - 3, mx + 1, top + 11, inZone ? 0xFFFFE070 : 0xFFFFFFFF);
        }
        if (phase == Phase.RUNNING) {
            int length = (int) Math.round((1 - runSeconds / Faceting.FACET_SECONDS) * BAR_HALF * 2);
            context.fill(left, top + 10, left + length, top + 11, 0xFFFFD27A);
        }

        Text status = null;
        if (phase == Phase.READY) {
            status = Text.translatable("gui.middle-earth.lapidary.faceting.ready");
        } else if (lastScore >= 0 && now - lastScoreShown < 1500) {
            status = Text.literal(lastScore + "%");
        }
        if (status != null) {
            context.drawCenteredTextWithShadow(textRenderer, status, cx, top + 14, 0xFFFFFFFF);
        }
    }

    /** The lowest y a line of parchment text may start at: the parchment ends at 165. */
    private static final int TEXT_BOTTOM = 156;

    /** Draws wrapped parchment text and returns the y below it; lines past the parchment are left out. */
    private int wrapped(DrawContext context, Text text, int tx, int ty, int color) {
        for (var line : textRenderer.wrapLines(text, 70)) {
            if (ty <= TEXT_BOTTOM) {
                context.drawText(textRenderer, line, tx, ty, color, false);
            }
            ty += 10;
        }
        return ty;
    }


    // ---- The gem being ground ----

    /** Grit on the wheel's rim scrolling down the slot while the wheel turns; fainter where it curves away. */
    private void drawSpinningRim(DrawContext context, long now) {
        int length = RIM_Y1 - RIM_Y0;
        // The rim runs up the slot: the grit moves up.
        int offset = (int) (length - now * 60 / 1000 % length);
        for (int k = 0; k < 18; k++) {
            int ry = (k * 37 + offset) % length;
            int rx = RIM_X0 + 1 + (k * 7) % (RIM_X1 - RIM_X0 - 2);
            double t = (ry + 0.5) / length * 2 - 1;
            int alpha = (int) (Math.sqrt(Math.max(0, 1 - t * t)) * (k % 2 == 0 ? 150 : 90));
            int color = k % 2 == 0 ? 0x161616 : 0xC8C4C0;
            context.fill(x + rx, y + RIM_Y0 + ry, x + rx + 1, y + RIM_Y0 + ry + 2, (alpha << 24) | color);
        }
    }

    /** How far the cut has come, 0-1: finished facets plus the one being ground. */
    private double cutProgress() {
        int facets = Faceting.facets(handler.getCut().tier());
        double current = switch (phase) {
            case RUNNING -> runSeconds / Faceting.FACET_SECONDS;
            case SENT -> 1;
            default -> 0;
        };
        return MathHelper.clamp((handler.getFacet() + current) / facets, 0, 1);
    }

    /** When a pixel of the sprite turns into the new shape: the outside goes first, with some scatter. */
    private static double pixelTurn(int px, int py) {
        double hash = MathHelper.fractionalPart(Math.sin(px * 12.9898 + py * 78.233) * 43758.5453);
        double outside = Math.hypot(px - 7.5, py - 7.5) / 10.61;
        return 0.02 + 0.96 * (0.4 * hash + 0.6 * (1 - outside));
    }

    /**
     * The 16 x 16 ARGB pixels of the sprite an item stack shows, or null if they cannot be read. Found the way
     * the game does: the item's definition (picking the case for the stack's gem type), its model, the model's
     * layer0 texture.
     */
    private int[] art(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Identifier itemId = net.minecraft.registry.Registries.ITEM.getId(stack.getItem());
        RegistryKey<GemType> type = stack.get(DataComponentTypesME.GEM_TYPE);
        String key = itemId + "|" + type;
        if (artCache.containsKey(key)) {
            return artCache.get(key);
        }
        int[] pixels = null;
        try {
            JsonObject definition = readJson(itemId.withPath(path -> "items/" + path + ".json"));
            String model = findModel(definition.getAsJsonObject("model"),
                type == null ? null : type.getValue().toString());
            if (model != null) {
                JsonObject modelJson = readJson(Identifier.of(model).withPath(path -> "models/" + path + ".json"));
                String layer = modelJson.getAsJsonObject("textures").get("layer0").getAsString();
                pixels = readSprite(Identifier.of(layer));
            }
        } catch (IOException | RuntimeException e) {
            MiddleEarth.LOGGER.logError("Lapidary Table: could not read the sprite of " + key + ": " + e);
        }
        artCache.put(key, pixels);
        return pixels;
    }

    private JsonObject readJson(Identifier file) throws IOException {
        try (InputStream in = client.getResourceManager().open(file)) {
            return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    /** The model an item definition shows: a plain model, or the case of a select matching {@code when}. */
    private static String findModel(JsonObject node, String when) {
        if (node == null || !node.has("type")) {
            return null;
        }
        String kind = node.get("type").getAsString();
        if (kind.endsWith("model") && node.has("model") && node.get("model").isJsonPrimitive()) {
            return node.get("model").getAsString();
        }
        if (kind.endsWith("select")) {
            for (JsonElement entry : node.getAsJsonArray("cases")) {
                JsonObject option = entry.getAsJsonObject();
                JsonElement cases = option.get("when");
                boolean match = cases.isJsonArray()
                    ? cases.getAsJsonArray().contains(new com.google.gson.JsonPrimitive(String.valueOf(when)))
                    : cases.getAsString().equals(when);
                if (match) {
                    return findModel(option.getAsJsonObject("model"), when);
                }
            }
            return node.has("fallback") ? findModel(node.getAsJsonObject("fallback"), when) : null;
        }
        return null;
    }

    /**
     * A sprite's pixels. Gem sprites have no file of their own: the block atlas makes them from a grey base and
     * a palette swap (paletted permutations: "cut_gem/oval" + "_ruby" swaps the colours of gem_palettes/key for
     * those of gem_palettes/ruby). So a missing sprite is rebuilt the same way.
     */
    private int[] readSprite(Identifier sprite) throws IOException {
        Identifier file = sprite.withPath(path -> "textures/" + path + ".png");
        if (client.getResourceManager().getResource(file).isPresent()) {
            return readPixels(file);
        }
        String path = sprite.getPath();
        int split = path.lastIndexOf('_');
        if (split < 0) {
            throw new java.io.FileNotFoundException(file.toString());
        }
        int[] base = readPixels(sprite.withPath("textures/" + path.substring(0, split) + ".png"));
        int[] key = readRow(Identifier.of(sprite.getNamespace(), "textures/gem_palettes/key.png"));
        int[] palette = readRow(Identifier.of(sprite.getNamespace(),
            "textures/gem_palettes/" + path.substring(split + 1) + ".png"));
        Map<Integer, Integer> swap = new HashMap<>();
        for (int i = 0; i < Math.min(key.length, palette.length); i++) {
            swap.put(key[i] | 0xFF000000, palette[i]);
        }
        for (int i = 0; i < base.length; i++) {
            int alpha = base[i] >>> 24;
            Integer to = alpha == 0 ? null : swap.get(base[i] | 0xFF000000);
            if (to != null) {
                base[i] = (alpha << 24) | (to & 0xFFFFFF);
            }
        }
        return base;
    }

    /** Every pixel of a small texture, row by row (palettes are one row). */
    private int[] readRow(Identifier file) throws IOException {
        try (InputStream in = client.getResourceManager().open(file); NativeImage image = NativeImage.read(in)) {
            int[] colors = new int[image.getWidth() * image.getHeight()];
            for (int py = 0; py < image.getHeight(); py++) {
                for (int px = 0; px < image.getWidth(); px++) {
                    colors[py * image.getWidth() + px] = image.getColorArgb(px, py);
                }
            }
            return colors;
        }
    }

    private int[] readPixels(Identifier file) throws IOException {
        try (InputStream in = client.getResourceManager().open(file); NativeImage image = NativeImage.read(in)) {
            int[] pixels = new int[256];
            // Only the first frame of animated sprites (they are as wide as one frame).
            int size = image.getWidth();
            for (int py = 0; py < 16; py++) {
                for (int px = 0; px < 16; px++) {
                    pixels[py * 16 + px] = image.getColorArgb(px * size / 16, py * size / 16);
                }
            }
            return pixels;
        }
    }

    /**
     * The gem on the lap mid-cut: the old sprite turning pixel by pixel into the new cut as the facets are
     * ground, turned on the dop for each facet. Pixels ground away fly off as dust.
     */
    private void drawGrindingGem(DrawContext context, ItemStack from, ItemStack to) {
        int[] oldArt = art(from);
        int[] newArt = art(to);
        if (oldArt == null || newArt == null) {
            context.drawItem(cutProgress() >= 1 ? to : from, x + GEM_X - 8, y + GEM_Y - 8);
            return;
        }
        double progress = cutProgress();
        double radians = Math.toRadians(gemAngle + (phase == Phase.RUNNING ? Math.sin(runSeconds * 18) * 2.5 : 0));
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x + GEM_X, y + GEM_Y);
        context.getMatrices().rotate((float) radians);
        for (int py = 0; py < 16; py++) {
            for (int px = 0; px < 16; px++) {
                int i = py * 16 + px;
                double turn = pixelTurn(px, py);
                boolean turned = progress >= turn;
                int color = turned ? newArt[i] : oldArt[i];
                if (turned && lastMorph < turn && progress > lastMorph && (oldArt[i] >>> 24) > 16
                    && oldArt[i] != newArt[i]) {
                    // This pixel was just ground away: it flies off as dust.
                    float ox = px - 7.5F;
                    float oy = py - 7.5F;
                    dust(GEM_X - BOARD_X + ox * cos - oy * sin, GEM_Y - BOARD_Y + ox * sin + oy * cos, oldArt[i]);
                }
                if ((color >>> 24) > 16) {
                    context.fill(px - 8, py - 8, px - 7, py - 7, color);
                }
            }
        }
        context.getMatrices().popMatrix();
        lastMorph = progress;
    }

    /** A ground-away pixel of the gem, thrown off along the spinning lap. */
    private void dust(float bx, float by, int color) {
        Particle p = new Particle();
        p.px = bx;
        p.py = by;
        // Seen from above: thrown down the slot the way the rim turns, spreading a little sideways.
        p.vx = (random.nextFloat() * 2 - 1) * 12;
        p.vy = -15 - random.nextFloat() * 30;
        p.life = 0.5F + random.nextFloat() * 0.4F;
        p.size0 = 1;
        p.size1 = 1;
        p.color = 0xFF000000 | color;
        particles.add(p);
    }

    /**
     * While a facet is ground: the gem turns to its facet during the countdown, and sparks in the gem's colour
     * and water drops spray from where it meets the lap; more and brighter while the marker is in the zone.
     */
    private void updateGrinding(float seconds) {
        // No candle on this screen: its flames must not pile up for the reading board.
        flameSpawn = 0;
        smokeSpawn = 0;
        if (!handler.isCutActive()) {
            return;
        }
        int facets = Faceting.facets(handler.getCut().tier());
        int facet = phase == Phase.IDLE ? handler.getFacet() : runFacet;
        float target = facet * 360.0F / facets;
        gemAngle += (target - gemAngle) * (1 - (float) Math.exp(-6 * seconds));
        if (phase != Phase.RUNNING) {
            return;
        }
        boolean inZone = Faceting.zone(handler.getCut(), runFacet, cutGrade(), runSeconds).contains(marker);
        if (!handler.getTools().isEmpty()) {
            pumpWater(seconds);
        }
        sparkSpawn += seconds * (inZone ? 40 : 10);
        int[][] palette = gemPalette(art(handler.getCutGem()));
        while (sparkSpawn >= 1) {
            sparkSpawn -= 1;
            Particle p = new Particle();
            boolean water = random.nextFloat() < 0.35F;
            p.px = GEM_X - BOARD_X + random.nextFloat() * 6 - 3;
            p.py = GEM_Y - BOARD_Y - 5;
            // The rim runs up the slot under the gem, so the spray fans out up the slot, away from the lens.
            p.vx = (random.nextFloat() * 2 - 1) * 18;
            p.vy = -20 - random.nextFloat() * 45;
            p.life = 0.3F + random.nextFloat() * 0.35F;
            if (water) {
                p.size0 = 1;
                p.size1 = 1;
                p.color = 0xC0BFD8EA;
            } else {
                // In the zone the wheel bites cleanly: sparkles in the gem's lightest colours among its grit.
                boolean sparkle = inZone && random.nextBoolean();
                int[] colors = sparkle ? palette[0] : palette[1];
                p.size0 = sparkle ? 2 : 1;
                p.size1 = 1;
                p.color = 0xFF000000 | colors[random.nextInt(colors.length)];
                if (sparkle && random.nextInt(4) == 0) {
                    p.sprite = GLINT;
                    p.size0 = 4;
                    p.life *= 0.8F;
                }
            }
            particles.add(p);
        }
    }

    /** Where the tool set's pump fitting pours onto the wheel (Faceting board texture pixels). */
    private static final int SPOUT_X = 90;
    /** The slot in the table the wheel turns in (board texture pixels, end exclusive). */
    private static final int SLOT_X0 = 74;
    private static final int SLOT_X1 = 95;
    private static final int SLOT_Y0 = 22;
    private static final int SLOT_Y1 = 120;
    private static final int SPOUT_Y = 34;
    private float waterSpawn;

    /** The pump wetting the wheel while it grinds: water from the spout onto the rim, carried up the slot by it. */
    private void pumpWater(float seconds) {
        waterSpawn += seconds * 45;
        while (waterSpawn >= 1) {
            waterSpawn -= 1;
            Particle p = new Particle();
            p.px = SPOUT_X - BOARD_X;
            p.py = SPOUT_Y - BOARD_Y + random.nextFloat() * 2 - 1;
            // Out of the spout to the left, falling onto the rim, which carries it up the slot.
            p.vx = -40 - random.nextFloat() * 25;
            p.vy = random.nextFloat() * 4 - 2;
            p.gravity = -120;
            p.life = 0.25F + random.nextFloat() * 0.2F;
            p.inSlot = true;
            p.size0 = random.nextInt(3) == 0 ? 2 : 1;
            p.size1 = 1;
            p.color = random.nextBoolean() ? 0xD08CC4EC : 0xC0D6ECFA;
            particles.add(p);
        }
    }

    private final Map<int[], int[][]> paletteCache = new java.util.IdentityHashMap<>();

    /**
     * The gem's own colours, from its sprite: {lightest third (sparkles), the rest (grit)}. Grey stand-ins if the
     * sprite could not be read.
     */
    private int[][] gemPalette(int[] art) {
        if (art == null) {
            return new int[][] {{0xFFFFFF, 0xE0E0E0}, {0x9A9A9A, 0x6A6A6A}};
        }
        return paletteCache.computeIfAbsent(art, pixels -> {
            List<Integer> colors = new ArrayList<>();
            for (int pixel : pixels) {
                if ((pixel >>> 24) > 16 && !colors.contains(pixel & 0xFFFFFF)) {
                    colors.add(pixel & 0xFFFFFF);
                }
            }
            if (colors.isEmpty()) {
                return new int[][] {{0xFFFFFF}, {0x9A9A9A}};
            }
            colors.sort(java.util.Comparator.comparingInt(c -> -((c >> 16 & 0xFF) * 3 + (c >> 8 & 0xFF) * 6 + (c & 0xFF))));
            int light = Math.max(1, colors.size() / 3);
            int[] sparkles = colors.subList(0, light).stream().mapToInt(Integer::intValue).toArray();
            // The grit leaves out the darkest outline colour unless that is all there is.
            List<Integer> rest = colors.subList(Math.min(light, colors.size() - 1),
                Math.max(Math.min(light, colors.size() - 1) + 1, colors.size() - 1));
            return new int[][] {sparkles, rest.stream().mapToInt(Integer::intValue).toArray()};
        });
    }

    /** The Faceting side of the parchment: what the gem can become, the cost and the run so far. */
    private void drawFacetingPanel(DrawContext context) {
        int px = PANEL_X + 5;
        int line = 90;
        ItemStack gem = handler.getCutGem();
        if (gem.isEmpty()) {
            wrapped(context, Text.translatable("gui.middle-earth.lapidary.faceting.place_gem"), px, line, INK_DIM);
            return;
        }
        if (handler.isCutActive()) {
            // No cut choices during a cut: the text starts right under the button.
            line = 68;
            GemCut cut = handler.getCut();
            int facets = Faceting.facets(cut.tier());
            int done = handler.getFacet();
            line = wrapped(context, Text.translatable(cut.translationKey()), px, line, INK);
            context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.faceting.facet",
                Math.min(done + 1, facets), facets), px, line, INK, false);
            line += 10;
            if (done > 0) {
                int average = Math.round(handler.getScoreSum() / (float) done);
                GemQuality quality = GemQuality.of(Math.min(average, handler.getBenchCap()));
                context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.faceting.score", average),
                    px, line, INK, false);
                line += 10;
                context.drawText(textRenderer, Text.translatable(quality.translationKey()), px, line,
                    qualityInk(quality), false);
                line += 10;
            }
            wrapped(context, Text.translatable("gui.middle-earth.lapidary.faceting.hold"), px, line + 3, INK_DIM);
            return;
        }
        if (showResult && gem.contains(DataComponentTypesME.GEM_QUALITY)) {
            GemQuality quality = GemQuality.of(Faceting.quality(gem));
            wrapped(context, Text.translatable("gui.middle-earth.lapidary.faceting.done",
                Text.translatable(quality.translationKey())), px, line, qualityInk(quality));
            return;
        }
        int tier = Faceting.nextTier(gem);
        if (tier == 0) {
            boolean raw = !(gem.getItem() instanceof CutGemItem);
            wrapped(context, Text.translatable(raw ? "gui.middle-earth.lapidary.faceting.unstudied"
                : "gui.middle-earth.lapidary.faceting.final"), px, line, INK_BAD);
            return;
        }
        line = wrapped(context, chosenCut != null ? Text.translatable(chosenCut.translationKey())
            : Text.translatable("gui.middle-earth.lapidary.faceting.choose", tier),
            px, line, chosenCut != null ? INK : INK_DIM);
        context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.faceting.facets",
            Faceting.facets(tier)), px, line, INK_DIM, false);
        line += 12;
        if (!handler.hasWater()) {
            context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.faceting.water"),
                px, line, INK_BAD, false);
            line += 12;
        }
        // The bench's limit: a label, then the best quality it allows.
        GemQuality cap = GemQuality.of(handler.getBenchCap());
        context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.faceting.cap"), px, line,
            INK_DIM, false);
        context.drawText(textRenderer, Text.translatable(cap.translationKey()), px, line + 10, qualityInk(cap), false);
    }

    private Identifier facetingBackground() {
        net.sevenstars.middleearth.recipe.lapidary.LapidaryTools tools =
            net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.of(handler.getTools());
        if (tools == null) {
            return BACKGROUND_FACETING;
        }
        return switch (tools) {
            case MASTERWORK -> BACKGROUND_FACETING_MASTERWORK;
            case RELIC -> BACKGROUND_FACETING_RELIC;
        };
    }

    /** Quality colours dark enough to read on parchment. */
    private static int qualityInk(GemQuality quality) {
        return switch (quality) {
            case MUNDANE -> 0xFF5E5E5E;
            case FINE -> 0xFF2F7A2F;
            case EXCEPTIONAL -> 0xFF1F6F7A;
            case MASTERWORK -> 0xFF7A2F8A;
            case RELIC -> 0xFFA86E08;
        };
    }

    private boolean overBoard(double mouseX, double mouseY) {
        return mouseX >= x + 4 && mouseX < x + 162 && mouseY >= y + 15 && mouseY < y + 166;
    }

    private int lockedMask() {
        int mask = 0;
        for (int rune = 0; rune < handler.getRuneCount(); rune++) {
            mask |= handler.isLocked(rune) ? 1 << rune : 0;
        }
        return mask;
    }

    private LapidaryScreenHandler.Setup setup() {
        return LapidaryScreenHandler.setup(handler.getGem(),
            client.world == null ? null : client.world.getRegistryManager());
    }

    private boolean studying() {
        return handler.getState() == LapidaryScreenHandler.STATE_STUDYING;
    }

    private void click(int id) {
        client.interactionManager.clickButton(handler.syncId, id);
    }

    private void turnGem(int steps) {
        if (!studying()) {
            return;
        }
        localGem = Math.floorMod(localGem + steps, RefractionReading.STEPS);
        pendingTicks = 10;
        click(steps > 0 ? LapidaryScreenHandler.GEM_LEFT : LapidaryScreenHandler.GEM_RIGHT);
        playClick(1.4F);
    }

    /** One candle step from the keyboard: a drag of its own, so one move. */
    private void nudgeLamp(int steps) {
        if (!studying()) {
            return;
        }
        localLamp = Math.floorMod(localLamp + steps, RefractionReading.STEPS);
        pendingTicks = 10;
        click(LapidaryScreenHandler.GRAB_LAMP);
        click(LapidaryScreenHandler.SET_LAMP + localLamp);
        playClick(1.8F);
    }

    private void playClick(float pitch) {
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, pitch, 0.35F));
    }

    // ---- Drawing ----

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.getMatrices().pushMatrix();
        context.getMatrices().scale(fit);
        super.renderBackground(context, (int) (mouseX / fit), (int) (mouseY / fit), deltaTicks);
        context.getMatrices().popMatrix();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.getMatrices().pushMatrix();
        context.getMatrices().scale(fit);
        renderFitted(context, (int) (mouseX / fit), (int) (mouseY / fit), deltaTicks);
        // Tooltips are drawn after the screen; draw them now, at the same scale, so they line up.
        context.renderTooltip();
        context.getMatrices().popMatrix();
    }

    private void renderFitted(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        long now = Util.getMeasuringTimeNano();
        float seconds = lastFrame == 0 ? 0 : Math.min(0.1F, (now - lastFrame) / 1.0E9F);
        lastFrame = now;
        updateParticles(seconds);
        if (!reading()) {
            updateFacet(seconds);
            updateGrinding(seconds);
        }

        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
        if (reading() && isOverHelp(mouseX, mouseY)) {
            List<Text> help = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                help.add(Text.translatable("gui.middle-earth.lapidary.help." + i));
            }
            context.drawTooltip(textRenderer, help, mouseX, mouseY);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, reading() ? BACKGROUND : facetingBackground(), x, y, 0, 0,
            WIDTH, HEIGHT, WIDTH, HEIGHT);
        if (!reading() && handler.getSlot(3).getStack().isEmpty()) {
            // A faint water bucket in the empty water slot.
            context.drawItem(new ItemStack(Items.WATER_BUCKET), x + LapidaryScreenHandler.FACET_WATER_X,
                y + LapidaryScreenHandler.GEM_SLOT_Y);
            context.fill(x + LapidaryScreenHandler.FACET_WATER_X, y + LapidaryScreenHandler.GEM_SLOT_Y,
                x + LapidaryScreenHandler.FACET_WATER_X + 16, y + LapidaryScreenHandler.GEM_SLOT_Y + 16, 0xAA8B8B8B);
        }
        if (!reading() && handler.getTools().isEmpty()) {
            // Faint tools in the empty tools slot.
            context.drawItem(new ItemStack(ResourceItemsME.MASTERWORK_LAPIDARY_TOOLS),
                x + LapidaryScreenHandler.FACET_TOOLS_X, y + LapidaryScreenHandler.GEM_SLOT_Y);
            context.fill(x + LapidaryScreenHandler.FACET_TOOLS_X, y + LapidaryScreenHandler.GEM_SLOT_Y,
                x + LapidaryScreenHandler.FACET_TOOLS_X + 16, y + LapidaryScreenHandler.GEM_SLOT_Y + 16, 0xAA8B8B8B);
        }
        if (reading() && handler.getSlot(1).getStack().isEmpty()) {
            // A faint paper in the empty paper slot.
            context.drawItem(new ItemStack(Items.PAPER), x + LapidaryScreenHandler.PAPER_SLOT_X,
                y + LapidaryScreenHandler.GEM_SLOT_Y);
            context.fill(x + LapidaryScreenHandler.PAPER_SLOT_X, y + LapidaryScreenHandler.GEM_SLOT_Y,
                x + LapidaryScreenHandler.PAPER_SLOT_X + 16, y + LapidaryScreenHandler.GEM_SLOT_Y + 16, 0xAA8B8B8B);
        }
        if (reading()) {
            drawBoard(context, mouseX, mouseY);
        } else {
            drawFacetingBoard(context);
        }
    }

    private void drawBoard(DrawContext context, int mouseX, int mouseY) {
        int cx = x + BOARD_X;
        int cy = y + BOARD_Y;
        long now = Util.getMeasuringTimeMs();
        float time = now / 50.0F;

        drawRimRunes(context);
        if (studying() && !draggingGem && overLampRing(mouseX, mouseY)) {
            drawCircle(context, cx, cy, RefractionReading.LAMP_RING * SCALE, 0x90FFD27A);
        }

        LapidaryScreenHandler.Setup setup = setup();
        RefractionReading.Beam beam = null;
        if (setup != null) {
            int gemColor = GemTypeRegistry.get(client.world.getRegistryManager(), setup.gem())
                .map(type -> type.color().getRgb()).orElse(0xFFFFFF);
            double[][] poly = RefractionReading.rotate(setup.outline(), localGem);
            drawGem(context, cx, cy, poly, gemColor);
            float flash = 1.0F - (now - finishFlash) / 700.0F;
            if (finishFlash > 0 && flash > 0) {
                fillPolygon(context, cx, cy, poly, 1.0, ((int) (flash * 220) << 24) | 0xFFFFFF);
            }
            beam = RefractionReading.trace(setup.outline(), setup.band().index(), localLamp, localGem);
            drawBeam(context, cx, cy, beam, time);
        }

        if (handler.getState() != LapidaryScreenHandler.STATE_IDLE) {
            float pulse = 0.5F + 0.5F * MathHelper.sin(time * 0.4F);
            for (int rune = 0; rune < handler.getRuneCount(); rune++) {
                drawRune(context, rune, beam, pulse, now);
            }
        }

        double lampAngle = Math.toRadians(localLamp * RefractionReading.STEP_DEGREES);
        int lx = (int) Math.round(cx + Math.cos(lampAngle) * RefractionReading.LAMP_RING * SCALE);
        int ly = (int) Math.round(cy - Math.sin(lampAngle) * RefractionReading.LAMP_RING * SCALE);
        drawCandle(context, lx, ly, time);
        spawnFlame(lx - cx, ly - cy);
        drawParticles(context, cx, cy);

        StudyGrade grade = handler.getLastGrade();
        if (handler.getState() == LapidaryScreenHandler.STATE_DONE && grade != null) {
            drawStamp(context, cx, cy, grade, now);
        }
    }

    /** The gem: body, a table facet in the middle, crown facet lines to it and the girdle outline. */
    private static void drawGem(DrawContext context, int cx, int cy, double[][] poly, int color) {
        fillPolygon(context, cx, cy, poly, 1.0, 0xA0000000 | color);
        fillPolygon(context, cx, cy, poly, 0.45, 0x70000000 | lerpColor(color, 0xFFFFFF, 0.45F));
        int line = 0xC0000000 | lerpColor(color, 0x000000, 0.35F);
        int light = 0xC0000000 | lerpColor(color, 0xFFFFFF, 0.55F);
        for (int i = 0; i < poly.length; i++) {
            double[] a = poly[i];
            double[] b = poly[(i + 1) % poly.length];
            drawLine(context, cx + a[0] * SCALE, cy - a[1] * SCALE, cx + a[0] * SCALE * 0.45, cy - a[1] * SCALE * 0.45,
                line, 1);
            drawLine(context, cx + a[0] * SCALE * 0.45, cy - a[1] * SCALE * 0.45,
                cx + b[0] * SCALE * 0.45, cy - b[1] * SCALE * 0.45, light, 1);
            drawLine(context, cx + a[0] * SCALE, cy - a[1] * SCALE, cx + b[0] * SCALE, cy - b[1] * SCALE,
                0xFF000000 | color, 1);
        }
    }

    /** The light: full strength from the candle, a little dimmer after every bounce, a spark where it lands. */
    private void drawBeam(DrawContext context, int cx, int cy, RefractionReading.Beam beam, float time) {
        double[] points = beam.points();
        int segment = 0;
        for (int i = 0; i + 3 < points.length; i += 2, segment++) {
            float strength = Math.max(0.35F, 1.0F - 0.15F * Math.max(0, segment - 1));
            double x1 = cx + points[i] * SCALE;
            double y1 = cy - points[i + 1] * SCALE;
            double x2 = cx + points[i + 2] * SCALE;
            double y2 = cy - points[i + 3] * SCALE;
            drawLine(context, x1, y1, x2, y2, ((int) (strength * 90) << 24) | COLOR_BEAM_GLOW, 3);
            drawLine(context, x1, y1, x2, y2, ((int) (strength * 255) << 24) | COLOR_BEAM, 1);
        }
        if (beam.escaped()) {
            int ex = (int) Math.round(cx + points[points.length - 2] * SCALE);
            int ey = (int) Math.round(cy - points[points.length - 1] * SCALE);
            int size = 6 + (int) (MathHelper.sin(time * 0.7F) * 1.5F + 1.5F);
            drawSprite(context, GLINT, ex - size / 2, ey - size / 2, size, 8, 0xE0FFE8A0);
        }
    }

    /** The runes engraved all round the brass rim, one per slot; always there, lit or not. */
    private void drawRimRunes(DrawContext context) {
        for (int slot = 0; slot < RefractionReading.RUNE_SLOTS; slot++) {
            int[] pos = slotPosition(slot * RefractionReading.RUNE_SLOT_DEGREES);
            drawGlyph(context, glyphFor(slot * RefractionReading.RUNE_SLOT_DEGREES), pos[0], pos[1], 0xFF4A3418, 0x50FFE6A8);
        }
    }

    /**
     * A rune the study needs lit: glowing amber so it stands out on the rim, brighter while the light is on it,
     * gold with a ring once locked.
     */
    private void drawRune(DrawContext context, int rune, RefractionReading.Beam beam, float pulse, long now) {
        int[] pos = runePosition(rune);
        boolean locked = handler.isLocked(rune);
        boolean lit = beam != null && RefractionReading.lights(beam.exitDegrees(), handler.getRune(rune));
        if (locked) {
            fillCircle(context, pos[0], pos[1], 6, 0x60FFB000);
            drawCircle(context, pos[0], pos[1], 6.5, 0xD0FFC94A);
        } else if (lit) {
            fillCircle(context, pos[0], pos[1], 6, ((int) (90 + pulse * 90) << 24) | 0xFFF4C8);
        } else {
            fillCircle(context, pos[0], pos[1], 5, ((int) (35 + pulse * 45) << 24) | 0xFF9A2E);
        }
        float flash = (now - lockFlash[rune]) / 500.0F;
        if (lockFlash[rune] > 0 && flash < 1) {
            drawCircle(context, pos[0], pos[1], 5 + flash * 9, ((int) ((1 - flash) * 255) << 24) | 0xFFE080);
        }
        int color = locked ? 0xFFFFD45A : lit ? 0xFF000000 | lerpColor(0xFFF2B0, 0xFFFFFF, pulse)
            : 0xFF000000 | lerpColor(0xE07A1E, 0xFFB347, pulse);
        drawGlyph(context, glyphFor(handler.getRune(rune)), pos[0], pos[1], color, 0x80000000);
    }

    private static String[] glyphFor(int degrees) {
        int slot = Math.floorMod(degrees / RefractionReading.RUNE_SLOT_DEGREES, RefractionReading.RUNE_SLOTS);
        return RUNES[(slot * 5 + slot / 4) % RUNES.length];
    }

    /** A glyph centred on (cx, cy), with a one-pixel shadow (or highlight, for engraving) down and right. */
    private static void drawGlyph(DrawContext context, String[] glyph, int cx, int cy, int color, int shadow) {
        for (int row = 0; row < glyph.length; row++) {
            for (int col = 0; col < glyph[row].length(); col++) {
                if (glyph[row].charAt(col) == '#') {
                    int gx = cx - 2 + col;
                    int gy = cy - 3 + row;
                    context.fill(gx + 1, gy + 1, gx + 2, gy + 2, shadow);
                }
            }
        }
        for (int row = 0; row < glyph.length; row++) {
            for (int col = 0; col < glyph[row].length(); col++) {
                if (glyph[row].charAt(col) == '#') {
                    int gx = cx - 2 + col;
                    int gy = cy - 3 + row;
                    context.fill(gx, gy, gx + 1, gy + 1, color);
                }
            }
        }
    }

    /**
     * The light source: a candle seen from above. A brass holder, the wax top with its melted pool, and the
     * flame: Minecraft's own flame particle sprite, flickering in size and place, with embers and smoke
     * drifting off it (see {@link #spawnFlame}).
     */
    private void drawCandle(DrawContext context, int fx, int fy, float time) {
        fillCircle(context, fx + 1, fy + 1, 8, 0x60000000);
        fillCircle(context, fx, fy, 8, 0xFF6B4A22);
        fillCircle(context, fx, fy, 7, 0xFFB08A4A);
        fillCircle(context, fx - 1, fy - 1, 5, 0xFFC9A35E);
        fillCircle(context, fx, fy, 5, 0xFFD8CCA8);
        fillCircle(context, fx, fy, 4, 0xFFEFE6C8);
        fillCircle(context, fx, fy, 2, 0xFFFFF6DC);
        float breathe = MathHelper.sin(time * 0.6F) * 0.5F + MathHelper.sin(time * 1.7F) * 0.3F;
        fillCircle(context, fx, fy, 11 + (int) (breathe * 2), 0x14FFB347);
        fillCircle(context, fx, fy, 7 + (int) breathe, 0x28FFC870);
        int size = 8 + Math.round(breathe * 2);
        int jx = random.nextFloat() < 0.15F ? random.nextInt(3) - 1 : 0;
        int jy = random.nextFloat() < 0.15F ? random.nextInt(3) - 1 : 0;
        drawSprite(context, FLAME, fx - size / 2 + jx, fy - size / 2 + jy, size, 8, 0xFFFFFFFF);
        context.fill(fx, fy, fx + 1, fy + 1, 0xFF2A1E14);
    }

    /** The grade, stamped across the board at a slant once the study is done. */
    private void drawStamp(DrawContext context, int cx, int cy, StudyGrade grade, long now) {
        float appear = Math.min(1.0F, (now - finishFlash - 250) / 300.0F);
        if (appear <= 0) {
            return;
        }
        float scale = 2.6F - appear * 0.6F;
        int alpha = (int) (appear * 235);
        Integer rgb = grade.formatting().getColorValue();
        int color = (alpha << 24) | (rgb == null ? 0xFFFFFF : rgb);
        Text text = Text.translatable(grade.translationKey()).formatted(Formatting.BOLD);
        int width = textRenderer.getWidth(text);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(cx, cy + 30);
        context.getMatrices().rotate(-0.18F);
        context.getMatrices().scale(scale);
        context.fill(-width / 2 - 4, -6, width / 2 + 4, 10, (alpha / 2 << 24) | 0x1A1210);
        context.drawText(textRenderer, text, -width / 2, -2, color, true);
        context.getMatrices().popMatrix();
    }

    // ---- Particles: flame, smoke and sparks, positions relative to the board centre ----

    private static final class Particle {
        float px;
        float py;
        float vx;
        float vy;
        float age;
        float life;
        float size0;
        float size1;
        int color;
        /** Null for a solid square of {@link #color} (grinding dust, water drops). */
        Identifier sprite;
        float gravity;
        /** Water on the wheel: only shown inside the slot the wheel turns in. */
        boolean inSlot;
    }

    private void spawnFlame(int ox, int oy) {
        while (flameSpawn >= 1) {
            flameSpawn -= 1;
            Particle p = new Particle();
            float angle = random.nextFloat() * MathHelper.TAU;
            float speed = 3 + random.nextFloat() * 7;
            p.px = ox + random.nextFloat() * 2 - 1;
            p.py = oy + random.nextFloat() * 2 - 1;
            p.vx = MathHelper.cos(angle) * speed;
            p.vy = MathHelper.sin(angle) * speed;
            p.life = 0.25F + random.nextFloat() * 0.3F;
            p.size0 = 4 + random.nextInt(3);
            p.size1 = 1;
            p.color = 0xFFFFFFFF;
            p.sprite = FLAME;
            particles.add(p);
        }
        while (smokeSpawn >= 1) {
            smokeSpawn -= 1;
            Particle p = new Particle();
            float angle = random.nextFloat() * MathHelper.TAU;
            float speed = 5 + random.nextFloat() * 6;
            p.px = ox;
            p.py = oy;
            p.vx = MathHelper.cos(angle) * speed;
            p.vy = MathHelper.sin(angle) * speed;
            p.life = 0.9F + random.nextFloat() * 0.6F;
            p.size0 = 2;
            p.size1 = 6;
            p.color = 0x70404040;
            p.sprite = SMOKE;
            particles.add(p);
        }
    }

    /** Sparks flying out from a point, e.g. a rune as it locks. */
    private void burst(int[] at, int count, int color) {
        for (int i = 0; i < count; i++) {
            Particle p = new Particle();
            float angle = random.nextFloat() * MathHelper.TAU;
            float speed = 15 + random.nextFloat() * 25;
            p.px = at[0];
            p.py = at[1];
            p.vx = MathHelper.cos(angle) * speed;
            p.vy = MathHelper.sin(angle) * speed;
            p.life = 0.35F + random.nextFloat() * 0.3F;
            p.size0 = 5;
            p.size1 = 1;
            p.color = color;
            p.sprite = GLINT;
            particles.add(p);
        }
    }

    private void updateParticles(float seconds) {
        flameSpawn += seconds * 28;
        smokeSpawn += seconds * 3;
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.age += seconds;
            // Pump water rides the rim up the slot and goes down into it with the wheel just before the slot's end.
            if (p.age >= p.life || p.inSlot && p.py + BOARD_Y < SLOT_Y0 + 3) {
                it.remove();
                continue;
            }
            p.px += p.vx * seconds;
            p.py += p.vy * seconds;
            p.vx *= 0.9F;
            p.vy *= 0.9F;
            p.vy += p.gravity * seconds;
        }
    }

    private void drawParticles(DrawContext context, int cx, int cy) {
        for (Particle p : particles) {
            float t = p.age / p.life;
            int size = Math.max(1, Math.round(MathHelper.lerp(t, p.size0, p.size1)));
            int alpha = (int) (((p.color >>> 24) & 0xFF) * (1 - t));
            int rgb = p.sprite == FLAME ? lerpColor(0xFFFFFF, 0xFF6020, t) : p.color & 0xFFFFFF;
            if (!reading() && Math.hypot(cx + p.px - (x + LENS_X), cy + p.py - (y + LENS_Y)) <= LENS_R) {
                continue;
            }
            if (p.inSlot) {
                int sx = Math.round(cx + p.px) - x;
                int sy = Math.round(cy + p.py) - y;
                if (sx < SLOT_X0 || sx + size > SLOT_X1 || sy < SLOT_Y0 || sy + size > SLOT_Y1) {
                    continue;
                }
            }
            if (p.sprite == null) {
                int sx = Math.round(cx + p.px) - size / 2;
                int sy = Math.round(cy + p.py) - size / 2;
                context.fill(sx, sy, sx + size, sy + size, (alpha << 24) | rgb);
                continue;
            }
            drawSprite(context, p.sprite, Math.round(cx + p.px) - size / 2, Math.round(cy + p.py) - size / 2,
                size, 8, (alpha << 24) | rgb);
        }
    }

    private static void drawSprite(DrawContext context, Identifier sprite, int sx, int sy, int size, int textureSize,
                                   int color) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, sprite, sx, sy, 0, 0, size, size,
            textureSize, textureSize, textureSize, textureSize, color);
    }

    private int[] slotPosition(int degrees) {
        double angle = Math.toRadians(degrees);
        return new int[] {
            x + BOARD_X + glyphOffset(Math.cos(angle)),
            y + BOARD_Y + glyphOffset(-Math.sin(angle))};
    }

    private int[] runeOffset(int rune) {
        double angle = Math.toRadians(handler.getRune(rune));
        return new int[] {
            glyphOffset(Math.cos(angle)),
            glyphOffset(-Math.sin(angle))};
    }

    /**
     * Pixel offset from the board centre for a rune glyph along one axis. The board centre lies between pixels
     * while a glyph is centred on a pixel, so take half a pixel off before flooring: this centres the glyphs in
     * the brass band on every side instead of rounding them half a pixel inward at the top and outward at the
     * bottom.
     */
    private static int glyphOffset(double direction) {
        return (int) Math.floor(direction * RUNE_DRAW_RADIUS - 0.5);
    }

    private int[] runePosition(int rune) {
        int[] offset = runeOffset(rune);
        return new int[] {x + BOARD_X + offset[0], y + BOARD_Y + offset[1]};
    }

    // ---- The parchment panel ----

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, title, titleX, titleY, COLOR_LABEL, false);
        context.drawText(textRenderer, playerInventoryTitle, playerInventoryTitleX, playerInventoryTitleY, COLOR_LABEL, false);
        if (!reading()) {
            drawFacetingPanel(context);
            return;
        }
        context.drawText(textRenderer, "?", PANEL_X + 68, 4, isOverHelp(mouseX, mouseY) ? 0xFFFFFFFF : COLOR_LABEL,
            isOverHelp(mouseX, mouseY));

        int px = PANEL_X + 5;
        int line = 90;
        LapidaryScreenHandler.Setup setup = setup();
        if (setup == null) {
            drawWrapped(context, Text.translatable("gui.middle-earth.lapidary.place_gem"), px, line, INK_DIM);
            return;
        }
        boolean creative = client.player.isCreative();
        int cost = setup.band().xpLevels();
        boolean affordable = creative || client.player.experienceLevel >= cost;
        // Cost, like the enchanting table: an experience orb and the levels, then the paper.
        context.drawTexture(RenderPipelines.GUI_TEXTURED, XP_ORB, px - 1, line - 2, 0, 0, 11, 11, 16, 16, 64, 64,
            0xFF000000 | (affordable ? 0x80FF20 : 0xB04040));
        context.drawText(textRenderer, String.valueOf(cost), px + 12, line, affordable ? INK_GOOD : INK_BAD, false);
        boolean paper = handler.hasPaper();
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(px + 30, line - 2);
        context.getMatrices().scale(0.6875F);
        context.drawItem(new ItemStack(Items.PAPER), 0, 0);
        context.getMatrices().popMatrix();
        context.drawText(textRenderer, "1", px + 43, line, paper ? INK_GOOD : INK_BAD, false);
        line += 13;

        // Rune pips: hollow, lit by the light, or locked.
        int state = handler.getState();
        int runes = state == LapidaryScreenHandler.STATE_IDLE ? setup.band().runes() : handler.getRuneCount();
        RefractionReading.Beam beam = state == LapidaryScreenHandler.STATE_STUDYING
            ? RefractionReading.trace(setup.outline(), setup.band().index(), localLamp, localGem) : null;
        for (int rune = 0; rune < runes; rune++) {
            boolean locked = state != LapidaryScreenHandler.STATE_IDLE && handler.isLocked(rune);
            boolean lit = beam != null && RefractionReading.lights(beam.exitDegrees(), handler.getRune(rune));
            drawPip(context, px + 3 + rune * 11, line + 3, locked ? 0xFFC98A10 : lit ? 0xFFFFF2B0 : 0, INK);
        }
        line += 12;

        if (state != LapidaryScreenHandler.STATE_IDLE) {
            context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.moves", handler.getMoves()),
                px, line, INK, false);
            line += 10;
            if (state == LapidaryScreenHandler.STATE_STUDYING) {
                // One goal at a time: the best grade still in reach. When the moves pass its limit it fades
                // out and the next grade down fades in.
                StudyGrade goal = StudyGrade.fromMovesOverPar(handler.getMoves() - handler.getPar(),
                    handler.getRuneCount());
                long now = Util.getMeasuringTimeMs();
                if (goal != aimGrade) {
                    previousAim = aimGrade;
                    aimGrade = goal;
                    aimChanged = now;
                }
                float fade = Math.min(1.0F, (now - aimChanged) / 450.0F);
                if (previousAim != null && fade < 1) {
                    drawGoal(context, previousAim, px, line, 1 - fade);
                }
                drawGoal(context, aimGrade, px, line, previousAim == null ? 1 : fade);
                line += 22;
            } else {
                // Par stays hidden: the grade says how close the player came.
                line += 2;
                StudyGrade grade = handler.getLastGrade();
                if (grade != null) {
                    context.drawText(textRenderer, Text.translatable(grade.translationKey()).formatted(Formatting.BOLD),
                        px, line, gradeInk(grade), false);
                    line += 12;
                }
            }
        }
        StudyGrade held = handler.getGem().get(DataComponentTypesME.STUDY_GRADE);
        if (handler.hasNotesWaiting() && state != LapidaryScreenHandler.STATE_STUDYING) {
            drawWrapped(context, Text.translatable("gui.middle-earth.lapidary.take_notes"), px, line, INK_BAD);
        } else if (held != null && state == LapidaryScreenHandler.STATE_IDLE) {
            // Two lines, so long grade names fit the parchment.
            context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.studied"), px, line, INK_DIM, false);
            context.drawText(textRenderer, Text.translatable(held.translationKey()), px, line + 10, gradeInk(held), false);
        }
    }

    /** A grade goal: its name, and below it the most moves that still earn it. */
    private void drawGoal(DrawContext context, StudyGrade grade, int gx, int gy, float alpha) {
        int a = (int) (alpha * 255);
        if (a < 8) {
            return;
        }
        context.drawText(textRenderer, Text.translatable(grade.translationKey()), gx, gy,
            (a << 24) | (gradeInk(grade) & 0xFFFFFF), false);
        if (grade != StudyGrade.ROUGH_NOTES) {
            int limit = handler.getPar() + grade.maxOverPar(handler.getRuneCount());
            context.drawText(textRenderer, Text.translatable("gui.middle-earth.lapidary.aim", limit), gx, gy + 10,
                (a << 24) | (INK_DIM & 0xFFFFFF), false);
        }
    }

    /** A small diamond: filled when lit or locked, hollow otherwise. */
    private static void drawPip(DrawContext context, int cx, int cy, int fill, int outline) {
        for (int dy = -3; dy <= 3; dy++) {
            int half = 3 - Math.abs(dy);
            context.fill(cx - half, cy + dy, cx - half + 1, cy + dy + 1, outline);
            context.fill(cx + half, cy + dy, cx + half + 1, cy + dy + 1, outline);
            if (fill != 0 && half > 0) {
                context.fill(cx - half + 1, cy + dy, cx + half, cy + dy + 1, fill);
            }
        }
    }

    /** Grade colours dark enough to read on parchment. */
    private static int gradeInk(StudyGrade grade) {
        return switch (grade) {
            case ROUGH_NOTES -> 0xFF5E5E5E;
            case CAREFUL -> 0xFF2F7A2F;
            case THOROUGH -> 0xFF1F6F7A;
            case MASTERFUL -> 0xFFA86E08;
        };
    }

    private void drawWrapped(DrawContext context, Text text, int tx, int ty, int color) {
        wrapped(context, text, tx, ty, color);
    }

    private boolean isOverHelp(double mouseX, double mouseY) {
        return mouseX >= x + PANEL_X + 64 && mouseX < x + PANEL_X + 76 && mouseY >= y + 1 && mouseY < y + 14;
    }

    // ---- Input ----

    private double boardDistance(double mouseX, double mouseY) {
        return Math.hypot(mouseX - (x + BOARD_X), mouseY - (y + BOARD_Y));
    }

    private double boardAngle(double mouseX, double mouseY) {
        return Math.toDegrees(Math.atan2(-(mouseY - (y + BOARD_Y)), mouseX - (x + BOARD_X)));
    }

    private boolean overLampRing(double mouseX, double mouseY) {
        return Math.abs(boardDistance(mouseX, mouseY) - RefractionReading.LAMP_RING * SCALE) <= 9;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX /= fit;
        mouseY /= fit;
        if (button == 0 && !reading() && (phase == Phase.READY || phase == Phase.RUNNING)
            && overBoard(mouseX, mouseY)) {
            grindHeld = true;
            return true;
        }
        if (button == 0 && reading() && studying()) {
            for (int rune = 0; rune < handler.getRuneCount(); rune++) {
                int[] pos = runePosition(rune);
                if (Math.abs(mouseX - pos[0]) <= 6 && Math.abs(mouseY - pos[1]) <= 6) {
                    if (!handler.isLocked(rune)) {
                        click(LapidaryScreenHandler.LOCK_RUNE + rune);
                    }
                    return true;
                }
            }
            if (boardDistance(mouseX, mouseY) <= SCALE * 1.05) {
                draggingGem = true;
                gemDragAngle = boardAngle(mouseX, mouseY);
                gemDragAccumulated = 0;
                return true;
            }
            if (overLampRing(mouseX, mouseY)) {
                draggingLamp = true;
                click(LapidaryScreenHandler.GRAB_LAMP);
                dragLamp(mouseX, mouseY);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mouseX /= fit;
        mouseY /= fit;
        deltaX /= fit;
        deltaY /= fit;
        if (draggingLamp) {
            dragLamp(mouseX, mouseY);
            return true;
        }
        if (draggingGem) {
            double angle = boardAngle(mouseX, mouseY);
            double delta = angle - gemDragAngle;
            delta -= Math.floor((delta + 180) / 360) * 360;
            gemDragAngle = angle;
            gemDragAccumulated += delta;
            while (gemDragAccumulated >= RefractionReading.STEP_DEGREES) {
                gemDragAccumulated -= RefractionReading.STEP_DEGREES;
                turnGem(1);
            }
            while (gemDragAccumulated <= -RefractionReading.STEP_DEGREES) {
                gemDragAccumulated += RefractionReading.STEP_DEGREES;
                turnGem(-1);
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseX /= fit;
        mouseY /= fit;
        if (button == 0 && grindHeld) {
            grindHeld = false;
            return true;
        }
        if (draggingLamp || draggingGem) {
            draggingLamp = false;
            draggingGem = false;
            pendingTicks = 10;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        mouseX /= fit;
        mouseY /= fit;
        if (verticalAmount != 0 && reading() && boardDistance(mouseX, mouseY) <= RefractionReading.RUNE_RING * SCALE + 8) {
            turnGem(verticalAmount > 0 ? 1 : -1);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!reading() && keyCode == GLFW.GLFW_KEY_SPACE) {
            if (phase == Phase.READY || phase == Phase.RUNNING) {
                grindHeld = true;
            }
            return true;
        }
        if (reading() && studying()) {
            switch (keyCode) {
                case GLFW.GLFW_KEY_A -> {
                    turnGem(1);
                    return true;
                }
                case GLFW.GLFW_KEY_D -> {
                    turnGem(-1);
                    return true;
                }
                case GLFW.GLFW_KEY_LEFT -> {
                    nudgeLamp(1);
                    return true;
                }
                case GLFW.GLFW_KEY_RIGHT -> {
                    nudgeLamp(-1);
                    return true;
                }
                default -> {
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_SPACE && grindHeld) {
            grindHeld = false;
            return true;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    /** Snaps the candle to the groove step nearest the mouse; the whole drag counts as one move. */
    private void dragLamp(double mouseX, double mouseY) {
        if (!studying()) {
            draggingLamp = false;
            return;
        }
        int step = Math.floorMod((int) Math.round(boardAngle(mouseX, mouseY) / RefractionReading.STEP_DEGREES),
            RefractionReading.STEPS);
        if (step != localLamp) {
            localLamp = step;
            pendingTicks = 10;
            click(LapidaryScreenHandler.SET_LAMP + step);
            playClick(1.8F);
        }
    }

    // ---- Simple shapes with fills ----

    private static void fillCircle(DrawContext context, int cx, int cy, int radius, int color) {
        for (int dy = -radius; dy <= radius; dy++) {
            int half = (int) Math.sqrt(radius * radius - dy * dy);
            context.fill(cx - half, cy + dy, cx + half + 1, cy + dy + 1, color);
        }
    }

    private static void drawCircle(DrawContext context, int cx, int cy, double radius, int color) {
        int segments = Math.max(24, (int) (radius * 2));
        for (int i = 0; i < segments; i++) {
            double a = 2 * Math.PI * i / segments;
            double b = 2 * Math.PI * (i + 1) / segments;
            drawLine(context, cx + Math.cos(a) * radius, cy - Math.sin(a) * radius,
                cx + Math.cos(b) * radius, cy - Math.sin(b) * radius, color, 1);
        }
    }

    /** Fills a convex polygon given in board units, scaled by {@code factor}, row by row. */
    private static void fillPolygon(DrawContext context, int cx, int cy, double[][] poly, double factor, int color) {
        double scale = SCALE * factor;
        double top = Double.MAX_VALUE;
        double bottom = -Double.MAX_VALUE;
        for (double[] p : poly) {
            top = Math.min(top, cy - p[1] * scale);
            bottom = Math.max(bottom, cy - p[1] * scale);
        }
        for (int row = (int) Math.ceil(top); row <= (int) Math.floor(bottom); row++) {
            double left = Double.MAX_VALUE;
            double right = -Double.MAX_VALUE;
            double sy = row + 0.5;
            for (int i = 0; i < poly.length; i++) {
                double ax = cx + poly[i][0] * scale;
                double ay = cy - poly[i][1] * scale;
                double bx = cx + poly[(i + 1) % poly.length][0] * scale;
                double by = cy - poly[(i + 1) % poly.length][1] * scale;
                if ((ay <= sy && by > sy) || (by <= sy && ay > sy)) {
                    double ix = ax + (sy - ay) / (by - ay) * (bx - ax);
                    left = Math.min(left, ix);
                    right = Math.max(right, ix);
                }
            }
            if (left < right) {
                context.fill((int) Math.round(left), row, (int) Math.round(right), row + 1, color);
            }
        }
    }

    private static void drawLine(DrawContext context, double x1, double y1, double x2, double y2, int color, int width) {
        int steps = (int) Math.ceil(Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1)));
        int half = width / 2;
        for (int i = 0; i <= steps; i++) {
            double t = steps == 0 ? 0 : (double) i / steps;
            int px = (int) Math.floor(x1 + (x2 - x1) * t);
            int py = (int) Math.floor(y1 + (y2 - y1) * t);
            context.fill(px - half, py - half, px - half + width, py - half + width, color);
        }
    }

    /** Mixes two RGB colours (alpha ignored, result has none). */
    private static int lerpColor(int a, int b, float t) {
        int r = (int) MathHelper.lerp(t, (a >> 16) & 0xFF, (b >> 16) & 0xFF);
        int g = (int) MathHelper.lerp(t, (a >> 8) & 0xFF, (b >> 8) & 0xFF);
        int bl = (int) MathHelper.lerp(t, a & 0xFF, b & 0xFF);
        return (r << 16) | (g << 8) | bl;
    }

    /** A 20x20 button showing an item as its icon; its name shows as a tooltip and is read by the narrator. */
    private static final class IconButton extends ButtonWidget {
        private ItemStack icon;
        /** Nudges the icon up for item art that does not sit centred in its 16x16 frame. */
        private final int raise;

        IconButton(int x, int y, ItemStack icon, int raise, Text name, Runnable onPress) {
            this(x, y, 20, icon, raise, name, onPress);
        }

        IconButton(int x, int y, int size, ItemStack icon, int raise, Text name, Runnable onPress) {
            super(x, y, size, size, name, button -> onPress.run(), DEFAULT_NARRATION_SUPPLIER);
            this.icon = icon;
            this.raise = raise;
            setTooltip(net.minecraft.client.gui.tooltip.Tooltip.of(name));
        }

        void setIcon(ItemStack icon) {
            this.icon = icon;
        }

        @Override
        public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
            int inset = (getWidth() - 16) / 2;
            context.drawItem(icon, getX() + inset, getY() + inset - raise);
        }
    }

    /** A gem turn button: a filled arrow sized to the button instead of a text label. */
    private static final class ArrowButton extends ButtonWidget {
        private static final int HEAD = 5;
        private static final int SHAFT = 8;
        private final boolean left;

        ArrowButton(int x, int y, int width, int height, boolean left, Runnable onPress) {
            super(x, y, width, height, Text.translatable(left ? "gui.middle-earth.lapidary.turn_left"
                : "gui.middle-earth.lapidary.turn_right"), button -> onPress.run(), DEFAULT_NARRATION_SUPPLIER);
            this.left = left;
        }

        /** Drawn in the colour the button would give its text, so it greys out the same way. */
        @Override
        public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
            int length = HEAD + SHAFT;
            int x0 = getX() + (getWidth() - length) / 2;
            int cy = getY() + getHeight() / 2;
            for (int i = 0; i < length; i++) {
                // Column i from the tip: the head widens by a pixel each way per column, the shaft is 3 tall.
                int half = i < HEAD ? i : 1;
                int column = left ? x0 + i : x0 + length - 1 - i;
                context.fill(column, cy - half, column + 1, cy + half + 1, color);
            }
        }
    }
}
