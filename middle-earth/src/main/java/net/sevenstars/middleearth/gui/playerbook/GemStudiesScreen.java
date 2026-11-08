package net.sevenstars.middleearth.gui.playerbook;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.List;
import java.util.Map;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.recipe.lapidary.GemStudies;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;
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
            addDrawableChild(new PageTurnWidget(left() + 266, top() + 190, true, button -> turnTo(ARTS), true));
        } else {
            addDrawableChild(new PageTurnWidget(left() + 30, top() + 190, false, button -> turnTo(GEMS), true));
        }
    }

    private void turnTo(int to) {
        page = to;
        clearAndInit();
    }

    /** The four parts of a tool set, in the order the book lists them. */
    private static List<Item> parts(LapidaryTools tools) {
        return tools == LapidaryTools.MASTERWORK
            ? List.of(ResourceItemsME.REFINED_FOCUSING_LENSES, ResourceItemsME.ENHANCED_GRINDING_WHEEL,
                ResourceItemsME.REFRACTION_LAMP, ResourceItemsME.WATER_PUMP)
            : List.of(ResourceItemsME.EXQUISITE_FOCUSING_LENSES, ResourceItemsME.KHAZAD_GRINDING_WHEEL,
                ResourceItemsME.EDHEL_BEAM_LAMP, ResourceItemsME.HOBBIT_WORK_PUMP);
    }

    private static Item toolsItem(LapidaryTools tools) {
        return tools == LapidaryTools.MASTERWORK ? ResourceItemsME.MASTERWORK_LAPIDARY_TOOLS : ResourceItemsME.RELIC_LAPIDARY_TOOLS;
    }

    private static final int ARTS_SET_Y = 40;
    private static final int ARTS_PART_Y = 88;

    /**
     * The Lapidary Arts pages: left, the two tool sets and the parts of the chosen one; right, how the chosen
     * set or part is made. A set's page is there only once its scroll is bound into the book.
     */
    private void renderArts(DrawContext context, ItemStack book, int left, int top) {
        List<String> pages = book.getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of());
        drawScaled(context, Text.translatable("screen.middle-earth.gem_studies.arts").formatted(Formatting.BOLD),
            left + 44, top + 20, 1.25F, BLACK);
        if (book.isEmpty()) {
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.no_book"), left + 44, top + 44, 104, FADED);
            return;
        }
        for (int i = 0; i < LapidaryTools.values().length; i++) {
            LapidaryTools tools = LapidaryTools.values()[i];
            int rowY = top + ARTS_SET_Y + i * 18;
            context.drawItem(new ItemStack(toolsItem(tools)), left + 44, rowY);
            Text name = Text.translatable(tools.translationKey());
            if (tools == selectedTools && selectedPart == null) {
                name = name.copy().formatted(Formatting.UNDERLINE);
            }
            context.drawText(textRenderer, name, left + 64, rowY + 4, pages.contains(tools.id()) ? BLACK : FADED, false);
        }
        boolean bound = pages.contains(selectedTools.id());
        int rightX = left + 172;
        if (!bound) {
            drawScaled(context, Text.translatable(selectedTools.translationKey()).formatted(Formatting.BOLD),
                rightX, top + 20, 1.0F, BLACK);
            drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.arts.missing"), rightX, top + 38, 116, FADED);
            return;
        }
        context.drawText(textRenderer, Text.translatable("screen.middle-earth.gem_studies.arts.parts"),
            left + 44, top + ARTS_PART_Y - 12, FADED, false);
        List<Item> parts = parts(selectedTools);
        for (int i = 0; i < parts.size(); i++) {
            int rowY = top + ARTS_PART_Y + i * 18;
            context.drawItem(new ItemStack(parts.get(i)), left + 44, rowY);
            Text name = parts.get(i).getName();
            if (parts.get(i) == selectedPart) {
                name = name.copy().formatted(Formatting.UNDERLINE);
            }
            List<OrderedText> lines = textRenderer.wrapLines(name, 88);
            int y = lines.size() > 1 ? rowY : rowY + 4;
            for (OrderedText line : lines) {
                context.drawText(textRenderer, line, left + 64, y, BLACK, false);
                y += 9;
            }
        }
        Item shown = selectedPart != null ? selectedPart : toolsItem(selectedTools);
        String id = net.minecraft.registry.Registries.ITEM.getId(shown).getPath();
        int y = drawWrapped(context, shown.getName().copy().formatted(Formatting.BOLD), rightX, top + 20, 116, BLACK) + 4;
        drawWrapped(context, Text.translatable("screen.middle-earth.gem_studies.arts." + id), rightX, y, 116, BLACK);
    }

    private boolean clickArts(double mouseX, double mouseY) {
        for (int i = 0; i < LapidaryTools.values().length; i++) {
            int rowY = top() + ARTS_SET_Y + i * 18;
            if (mouseX >= left() + 42 && mouseX <= left() + 150 && mouseY >= rowY && mouseY < rowY + 18) {
                selectedTools = LapidaryTools.values()[i];
                selectedPart = null;
                return true;
            }
        }
        if (!heldBook().getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, List.of()).contains(selectedTools.id())) {
            return false;
        }
        List<Item> parts = parts(selectedTools);
        for (int i = 0; i < parts.size(); i++) {
            int rowY = top() + ARTS_PART_Y + i * 18;
            if (mouseX >= left() + 42 && mouseX <= left() + 150 && mouseY >= rowY && mouseY < rowY + 18) {
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
            renderArts(context, book, left, top);
            super.render(context, mouseX, mouseY, deltaTicks);
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
