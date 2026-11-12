package net.sevenstars.middleearth.gui.playerbook;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.recipe.lapidary.GemStudies;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryRecipeLayouts;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.registry.Registries;
import net.minecraft.item.Item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * The Player Book's Gem Studies section: what the notes bound into the held Player Book say about each gem.
 * Drawn on Middle-earth's own book texture so it reads as part of the book. Left page: the gems; right page:
 * the chosen gem's notes. The back arrow returns to the book's contents; the forward arrow turns to the Lapidary
 * Arts, the tool pages bound into the book from lapidary scrolls.
 */
@Environment(EnvType.CLIENT)
public class GemStudiesScreen extends Screen {
    private static final Identifier BOOK_TEXTURE = Identifier.of("middle-earth", "textures/gui/player_book.png");
    private static final int BOOK_WIDTH = 320;
    private static final int BOOK_HEIGHT = 220;
    private static final int BLACK = 0xFF000000;
    private static final int FADED = 0xFF6B5A48;

    private static final int GEMS = 0;
    private static final int ARTS = 1;

    private final Screen parent;
    private RegistryKey<GemType> selected;
    /** GEMS: the gems' notes; ARTS: the lapidary tool pages bound from scrolls. */
    private int page = GEMS;
    private LapidaryTools selectedTools = LapidaryTools.MASTERWORK;
    /** The part of the chosen tool set being read, or null for the set itself. */
    private Item selectedPart;

    public GemStudiesScreen(Screen parent) {
        super(Text.translatable("screen.middle-earth.gem_studies"));
        this.parent = parent;
    }

    /** The icon on the book's contents page: a raw ruby. */
    public static ItemStack icon() {
        return new ItemStack(ResourceItemsME.RAW_GEMS.get(GemTypeRegistry.RUBY));
    }

    private int left() {
        return width / 2 + 5 - BOOK_WIDTH / 2;
    }

    private int top() {
        return height / 2 - BOOK_HEIGHT / 2;
    }

    @Override
    protected void init() {
        if (page == GEMS) {
            addDrawableChild(new PageTurnWidget(left() + 30, top() + 190, false, button -> close(), true));
            if (artsOpen()) {
                addDrawableChild(new PageTurnWidget(left() + 266, top() + 190, true, button -> turnTo(ARTS), true));
            }
        } else {
            addDrawableChild(new PageTurnWidget(left() + 30, top() + 190, false, button -> turnTo(GEMS), true));
        }
    }

    private void turnTo(int to) {
        page = to;
        if (to == ARTS) {
            // Open on a set whose page is bound, if any is.
            List<String> pages = heldBook().getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of());
            if (!pages.contains(selectedTools.id())) {
                for (LapidaryTools tools : LapidaryTools.values()) {
                    if (pages.contains(tools.id())) {
                        selectedTools = tools;
                        selectedPart = null;
                        break;
                    }
                }
            }
        }
        clearAndInit();
    }

    /** The four parts of a tool set, in the order the book lists them. */
    private static List<Item> parts(LapidaryTools tools) {
        return tools == LapidaryTools.MASTERWORK
            ? List.of(ResourceItemsME.REFINED_FOCUSING_LENSES, ResourceItemsME.ENHANCED_GRINDING_WHEEL,
                ResourceItemsME.CITRINE_BULLSEYE_LAMP, ResourceItemsME.STANDARD_PUMP)
            : List.of(ResourceItemsME.EXQUISITE_FOCUSING_LENSES, ResourceItemsME.KHAZAD_GRINDING_WHEEL,
                ResourceItemsME.MIRDAIN_BEAM_LAMP, ResourceItemsME.NUMENOREAN_PUMP);
    }

    /** A tier's icon in the book: its lenses. */
    private static Item tierIcon(LapidaryTools tools) {
        return tools.upgrade(LapidaryTools.Slot.LENSES);
    }

    private static final int ARTS_SET_Y = 36;
    /** Space between rows of the Lapidary Arts lists (each row as tall as its wrapped name, at least an icon). */
    private static final int ARTS_GAP = 4;
    /** Below the tool sets: space before the "Parts" heading, and from the heading to the first part. */
    private static final int ARTS_PARTS_GAP = 5, ARTS_HEADING = 11;
    /** Left page: items at ICON_X, their names at NAME_X wrapped to NAME_W so they stay off the spine. */
    private static final int ICON_X = 42;
    private static final int NAME_X = 60;
    private static final int NAME_W = 86;
    /** Right page text column. */
    private static final int RIGHT_X = 174;
    private static final int RIGHT_W = 110;
    /** A recipe diagram: a 3x3 grid, an arrow and the result, centred on the right page. */
    private static final int SLOT = 18;
    private static final int GRID_X = 180;
    /** How long each variant of a diagram (a gem glass, a kind of planks) shows before the next. */
    private static final long CYCLE_MS = 1500;
    private static final int SLOT_EDGE_DARK = 0xFF7A6248;
    private static final int SLOT_EDGE_LIGHT = 0xFFE9D7AE;
    private static final int SLOT_FILL = 0xFFC7AC80;

    /** The stack under the mouse in a recipe diagram this frame, for its tooltip. */
    private ItemStack hoveredStack = ItemStack.EMPTY;

    /** Whether the Lapidary Arts pages can be opened: the player knows a tool set or the book holds one's page. */
    private boolean artsOpen() {
        if (client == null || client.player == null) {
            return false;
        }
        return !LapidaryKnowledge.learned(client.player).isEmpty()
            || !heldBook().getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of()).isEmpty();
    }

    /** Draws a list name wrapped to the left page's width, centred on its 18 px row. */
    /** A row's name, wrapped, from the top of its row. */
    private void drawRowName(DrawContext context, Text name, int x, int rowY, int color) {
        int y = rowY;
        for (OrderedText line : textRenderer.wrapLines(name, NAME_W)) {
            context.drawText(textRenderer, line, x, y, color, false);
            y += 9;
        }
    }

    /** A row's height: its wrapped name's (with the underline below the last line), at least an icon's. */
    private int rowHeight(Text name) {
        return Math.max(16, textRenderer.wrapLines(name, NAME_W).size() * 9 + 1);
    }

    /** The rows of a list from {@code y} (page-relative): each {y, height}, a gap apart. */
    private int[][] rows(List<Text> names, int y) {
        int[][] rows = new int[names.size()][];
        for (int i = 0; i < names.size(); i++) {
            int h = rowHeight(names.get(i));
            rows[i] = new int[]{y, h};
            y += h + ARTS_GAP;
        }
        return rows;
    }

    private int[][] setRows() {
        List<Text> names = new java.util.ArrayList<>();
        for (LapidaryTools tools : LapidaryTools.values()) {
            names.add(Text.translatable(tools.translationKey()));
        }
        return rows(names, ARTS_SET_Y);
    }

    /** Where the "Parts" heading goes: below the last tool set. */
    private int partsHeadingY() {
        int[][] sets = setRows();
        int[] last = sets[sets.length - 1];
        return last[0] + last[1] + ARTS_PARTS_GAP;
    }

    private int[][] partRows(List<Item> parts) {
        List<Text> names = new java.util.ArrayList<>();
        for (Item part : parts) {
            names.add(part.getName());
        }
        return rows(names, partsHeadingY() + ARTS_HEADING);
    }

    /** An icon centred on the height of its row's name. */
    private static int iconY(int[] row) {
        return row[0] + (row[1] - 16) / 2;
    }

    /** Whether the mouse is on a row (its icon and name, and half the gap either side). */
    private boolean inRow(double mouseX, double mouseY, int[] row) {
        int y = top() + row[0] - ARTS_GAP / 2;
        return mouseX >= left() + ICON_X - 2 && mouseX <= left() + NAME_X + NAME_W
            && mouseY >= y && mouseY < y + row[1] + ARTS_GAP;
    }

    /**
     * The Lapidary Arts pages: left, the two tool sets and the parts of the chosen one; right, how the chosen
     * set or part is made, with its crafting diagram. A set's page is there only once its scroll is bound into
     * the book.
     */
    private void renderArts(DrawContext context, ItemStack book, int left, int top, int mouseX, int mouseY) {
        List<String> pages = book.getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of());
        drawScaled(context, Text.translatable("screen.middle-earth.gem_studies.arts").formatted(Formatting.BOLD),
            left + ICON_X, top + 20, 1.25F, BLACK);
        if (book.isEmpty()) {
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.no_book"), left + ICON_X, top + 44, NAME_W + 18, FADED);
            return;
        }
        int[][] setRows = setRows();
        for (int i = 0; i < LapidaryTools.values().length; i++) {
            LapidaryTools tools = LapidaryTools.values()[i];
            context.drawItem(new ItemStack(tierIcon(tools)), left + ICON_X, top + iconY(setRows[i]));
            Text name = Text.translatable(tools.translationKey());
            if (tools == selectedTools && selectedPart == null) {
                name = name.copy().formatted(Formatting.UNDERLINE);
            }
            drawRowName(context, name, left + NAME_X, top + setRows[i][0], pages.contains(tools.id()) ? BLACK : FADED);
        }
        boolean bound = pages.contains(selectedTools.id());
        int rightX = left + RIGHT_X;
        if (!bound) {
            int y = drawWrapped(context, Text.translatable(selectedTools.translationKey()).formatted(Formatting.BOLD),
                rightX, top + 20, RIGHT_W, BLACK) + 4;
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.arts.missing"), rightX, y, RIGHT_W, FADED);
            return;
        }
        context.drawText(textRenderer, Text.translatable("screen.middle-earth.gem_studies.arts.parts"),
            left + ICON_X, top + partsHeadingY(), FADED, false);
        List<Item> parts = parts(selectedTools);
        int[][] partRows = partRows(parts);
        for (int i = 0; i < parts.size(); i++) {
            context.drawItem(new ItemStack(parts.get(i)), left + ICON_X, top + iconY(partRows[i]));
            Text name = parts.get(i).getName();
            if (parts.get(i) == selectedPart) {
                name = name.copy().formatted(Formatting.UNDERLINE);
            }
            drawRowName(context, name, left + NAME_X, top + partRows[i][0], BLACK);
        }
        if (selectedPart == null) {
            // The tier itself: what its upgrades do on a Lapidary Table.
            int y = drawWrapped(context, Text.translatable(selectedTools.translationKey()).formatted(Formatting.BOLD),
                rightX, top + 20, RIGHT_W, BLACK) + 3;
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.arts." + selectedTools.id() + "_lapidary_tools"),
                rightX, y, RIGHT_W, BLACK);
            return;
        }
        Item shown = selectedPart;
        String id = net.minecraft.registry.Registries.ITEM.getId(shown).getPath();
        // Kept tight: the lenses' page holds a two-line name, four lines of text and two recipes above the page's foot.
        int y = drawWrapped(context, shown.getName().copy().formatted(Formatting.BOLD), rightX, top + 20, RIGHT_W, BLACK) + 2;
        y = drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.arts." + id), rightX, y, RIGHT_W, BLACK) + 3;
        LapidaryRecipeLayouts.Layout layout = LapidaryRecipeLayouts.of(shown);
        if (layout != null) {
            y = drawRecipe(context, layout, left + GRID_X, y, mouseX, mouseY) + 3;
        }
        if (shown == ResourceItemsME.REFINED_FOCUSING_LENSES || shown == ResourceItemsME.EXQUISITE_FOCUSING_LENSES) {
            // The lenses need gem glass first: one diagram that turns through each gem's glass.
            List<LapidaryRecipeLayouts.Layout> glass = LapidaryRecipeLayouts.gemGlass(selectedTools);
            drawRecipe(context, glass.get(cycle(glass.size())), left + GRID_X, y, mouseX, mouseY);
        }
    }

    /** Which of {@code count} variants shows now: they take turns every CYCLE_MS. */
    private static int cycle(int count) {
        return (int) (System.currentTimeMillis() / CYCLE_MS % count);
    }

    /** The stack a recipe square shows now: a metal rod or plate carries its trim; a tag turns through its items. */
    private ItemStack display(LapidaryRecipeLayouts.Part part) {
        return switch (part) {
            case LapidaryRecipeLayouts.OfItem of -> new ItemStack(of.item());
            case LapidaryRecipeLayouts.OfTag of -> {
                List<Item> items = new ArrayList<>();
                Registries.ITEM.iterateEntries(of.tag()).forEach(entry -> items.add(entry.value()));
                yield items.isEmpty() ? ItemStack.EMPTY : new ItemStack(items.get(cycle(items.size())));
            }
            case LapidaryRecipeLayouts.Metal of -> {
                ItemStack stack = new ItemStack(of.shape());
                if (client != null && client.world != null) {
                    try {
                        stack.set(DataComponentTypes.TRIM, LapidaryRecipeLayouts.trim(client.world.getRegistryManager(), of.metal()));
                    } catch (IllegalStateException missing) {
                        // An unknown metal: the bare shape still shows what goes there.
                    }
                }
                yield stack;
            }
        };
    }

    /** Draws one recipe as a crafting grid, an arrow and the result at (x, y); returns the y below it. */
    private int drawRecipe(DrawContext context, LapidaryRecipeLayouts.Layout layout, int x, int y, int mouseX, int mouseY) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int sx = x + col * SLOT;
                int sy = y + row * SLOT;
                drawSlot(context, sx, sy);
                if (row < layout.pattern().size() && col < layout.pattern().get(row).length()) {
                    LapidaryRecipeLayouts.Part part = layout.key().get(layout.pattern().get(row).charAt(col));
                    if (part != null) {
                        drawSlotItem(context, display(part), sx, sy, mouseX, mouseY);
                    }
                }
            }
        }
        // The arrow, then the result.
        int ax = x + 3 * SLOT + 5;
        int ay = y + SLOT + 5;
        context.fill(ax, ay + 3, ax + 11, ay + 5, SLOT_EDGE_DARK);
        for (int i = 0; i < 4; i++) {
            context.fill(ax + 11 + i, ay + i, ax + 12 + i, ay + 8 - i, SLOT_EDGE_DARK);
        }
        int ox = ax + 21;
        int oy = y + SLOT;
        drawSlot(context, ox, oy);
        drawSlotItem(context, new ItemStack(layout.output(), layout.count()), ox, oy, mouseX, mouseY);
        return y + 3 * SLOT;
    }

    /** A slot pressed into the page: dark on the top and left, light on the bottom and right. */
    private static void drawSlot(DrawContext context, int x, int y) {
        context.fill(x, y, x + SLOT, y + SLOT, SLOT_EDGE_LIGHT);
        context.fill(x, y, x + SLOT - 1, y + SLOT - 1, SLOT_EDGE_DARK);
        context.fill(x + 1, y + 1, x + SLOT - 1, y + SLOT - 1, SLOT_FILL);
    }

    private void drawSlotItem(DrawContext context, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        if (stack.isEmpty()) {
            return;
        }
        context.drawItem(stack, x + 1, y + 1);
        context.drawStackOverlay(textRenderer, stack, x + 1, y + 1);
        if (mouseX >= x && mouseX < x + SLOT && mouseY >= y && mouseY < y + SLOT) {
            context.fill(x + 1, y + 1, x + SLOT - 1, y + SLOT - 1, 0x40FFFFFF);
            hoveredStack = stack;
        }
    }

    private boolean clickArts(double mouseX, double mouseY) {
        int[][] setRows = setRows();
        for (int i = 0; i < LapidaryTools.values().length; i++) {
            if (inRow(mouseX, mouseY, setRows[i])) {
                selectedTools = LapidaryTools.values()[i];
                selectedPart = null;
                return true;
            }
        }
        if (!heldBook().getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of()).contains(selectedTools.id())) {
            return false;
        }
        List<Item> parts = parts(selectedTools);
        int[][] partRows = partRows(parts);
        for (int i = 0; i < parts.size(); i++) {
            if (inRow(mouseX, mouseY, partRows[i])) {
                selectedPart = parts.get(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    /** The Player Book in hand, main hand first; empty if the player is not holding one. */
    private ItemStack heldBook() {
        if (client.player == null) {
            return ItemStack.EMPTY;
        }
        if (GemStudies.isPlayerBook(client.player.getMainHandStack())) {
            return client.player.getMainHandStack();
        }
        return GemStudies.isPlayerBook(client.player.getOffHandStack()) ? client.player.getOffHandStack() : ItemStack.EMPTY;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int left = left();
        int top = top();
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BOOK_TEXTURE, left, top, 0, 0, BOOK_WIDTH, BOOK_HEIGHT, 384, 384);

        ItemStack book = heldBook();
        if (page == ARTS) {
            hoveredStack = ItemStack.EMPTY;
            renderArts(context, book, left, top, mouseX, mouseY);
            super.render(context, mouseX, mouseY, deltaTicks);
            if (!hoveredStack.isEmpty()) {
                context.drawItemTooltip(textRenderer, hoveredStack, mouseX, mouseY);
            }
            return;
        }
        Map<Identifier, StudyGrade> studies = book.getOrDefault(DataComponentTypesME.GEM_STUDIES, Map.of());

        // Left page: title and the gems.
        drawScaled(context, title.copy().formatted(Formatting.BOLD), left + 44, top + 20, 1.25F, BLACK);
        if (book.isEmpty()) {
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.no_book"), left + 44, top + 44, 104, FADED);
        } else {
            for (int i = 0; i < GemTypeRegistry.ALL.size(); i++) {
                RegistryKey<GemType> gem = GemTypeRegistry.ALL.get(i);
                StudyGrade grade = studies.get(gem.getValue());
                int rowY = top + 40 + i * 18;
                context.drawItem(new ItemStack(ResourceItemsME.RAW_GEMS.get(gem)), left + 44, rowY);
                Text name = Text.translatable(GemTypeRegistry.translationKey(gem));
                if (gem.equals(selected)) {
                    name = name.copy().formatted(Formatting.UNDERLINE);
                }
                context.drawText(textRenderer, name, left + 64, rowY + 4, grade != null ? BLACK : FADED, false);
            }
        }

        // Right page: the chosen gem's notes.
        int rightX = left + 172;
        if (book.isEmpty()) {
            // nothing to show
        } else if (selected == null) {
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.pick"), rightX, top + 24, 116, FADED);
        } else {
            drawScaled(context, Text.translatable(GemTypeRegistry.translationKey(selected)).formatted(Formatting.BOLD),
                rightX, top + 20, 1.25F, BLACK);
            StudyGrade grade = studies.get(selected.getValue());
            int y = top + 38;
            if (grade == null) {
                drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.none"), rightX, y, 116, FADED);
            } else {
                context.drawText(textRenderer, Text.translatable("screen.middle-earth.gem_studies.notes",
                    Text.translatable(grade.translationKey())), rightX, y, FADED, false);
                y += 14;
                for (Text line : GemStudies.infoLines(selected, grade)) {
                    // Book ink: drop the tooltip colours, keep italics for lore.
                    Text ink = line.getStyle().isItalic()
                        ? Text.literal(line.getString()).formatted(Formatting.ITALIC) : Text.literal(line.getString());
                    y = drawWrapped(context, ink, rightX, y, 116, BLACK) + 6;
                }
            }
        }
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && page == ARTS && !heldBook().isEmpty()) {
            if (clickArts(mouseX, mouseY)) {
                client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.2F));
                return true;
            }
        } else if (button == 0 && !heldBook().isEmpty()) {
            for (int i = 0; i < GemTypeRegistry.ALL.size(); i++) {
                int rowY = top() + 40 + i * 18;
                if (mouseX >= left() + 42 && mouseX <= left() + 150 && mouseY >= rowY && mouseY < rowY + 18) {
                    selected = GemTypeRegistry.ALL.get(i);
                    client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.2F));
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int drawWrapped(DrawContext context, Text text, int x, int y, int width, int color) {
        List<OrderedText> lines = textRenderer.wrapLines(text, width);
        for (OrderedText line : lines) {
            context.drawText(textRenderer, line, x, y, color, false);
            y += 10;
        }
        return y;
    }

    private void drawScaled(DrawContext context, Text text, int x, int y, float scale, int color) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x, y);
        context.getMatrices().scale(scale);
        context.drawText(textRenderer, text, 0, 0, color, false);
        context.getMatrices().popMatrix();
    }

}
