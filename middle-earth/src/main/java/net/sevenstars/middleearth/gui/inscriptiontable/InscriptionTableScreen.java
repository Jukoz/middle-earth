package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.InscriptionConfirmationPacket;
import net.sevenstars.middleearth.network.packets.C2S.InscriptionWordUpdatePacket;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Environment(value= EnvType.CLIENT)
public class InscriptionTableScreen extends HandledScreen<InscriptionTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/inscription_table.png");

    private static final Identifier SCROLLER_TEXTURE = Identifier.ofVanilla("container/villager/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Identifier.ofVanilla("container/villager/scroller_disabled");

    private static final Identifier EMPTY_SLOT_EMERALD_TEXTURE = Identifier.ofVanilla("container/slot/emerald");
    private static final Identifier EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = Identifier.ofVanilla("container/slot/lapis_lazuli");
    private static final Identifier EMPTY_SLOT_ADAMANT_TEXTURE = MiddleEarth.of("container/slot/adamant");
    private static final Identifier EMPTY_SLOT_RUBY_TEXTURE = MiddleEarth.of("container/slot/ruby");
    private static final Identifier EMPTY_SLOT_SAPPHIRE_TEXTURE = MiddleEarth.of("container/slot/sapphire");

    private static final Identifier FONT_ID = Identifier.ofVanilla("alt");
    private static final Style STYLE = Style.EMPTY.withFont(FONT_ID);

    int indexStartOffset;
    private boolean scrolling;

    private String enchant;
    private int level;
    private int maxLevel;
    private Text enchantText;

    private final CyclingSlotIcon catalystSlotIcon = new CyclingSlotIcon(0);

    private WidgetArrowButtonPage confirmationButton;

    private final WidgetInscriptionButtonPage[] words = new WidgetInscriptionButtonPage[11];
    private final List<String> selectedWords = new ArrayList<>();
    private final List<Integer> selectedButtons = new ArrayList<>();

    public InscriptionTableScreen(InscriptionTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 275;
        this.backgroundHeight = 183;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        this.catalystSlotIcon.updateTexture(List.of(
                EMPTY_SLOT_LAPIS_LAZULI_TEXTURE,
                EMPTY_SLOT_ADAMANT_TEXTURE,
                EMPTY_SLOT_EMERALD_TEXTURE,
                EMPTY_SLOT_RUBY_TEXTURE,
                EMPTY_SLOT_SAPPHIRE_TEXTURE));

        for (WidgetInscriptionButtonPage button : this.words){
            button.setSelected(this.selectedButtons.contains(button.index + this.indexStartOffset));
            button.setSelectedIndex(this.selectedButtons.contains(button.index  + this.indexStartOffset) ? this.selectedButtons.indexOf(button.index + this.indexStartOffset) : -1);
        }

        if (!this.handler.hasAll()){
            this.selectedWords.clear();
            this.selectedButtons.clear();
            this.enchant = null;
            this.level = 0;
            this.maxLevel = 0;
        }
    }

    public void updateInfo(String enchant, int level, int maxLevel){
        this.enchant = enchant;
        this.level = level;
        this.maxLevel = maxLevel;
    }

    @Override
    protected void init() {
        super.init();
        titleX = 6;
        playerInventoryTitleX = 108;
        playerInventoryTitleY = 92;

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        int k = j + 22;

        for(int l = 0; l < 11; ++l) {
            this.words[l] = this.addDrawableChild(new WidgetInscriptionButtonPage(i + 5, k, l, button -> {
                if (button instanceof WidgetInscriptionButtonPage wordButton) {
                    if (button.isSelected() && !wordButton.hidden){
                        if (!((WidgetInscriptionButtonPage) button).selected){
                            if (this.selectedWords.size() == 3){
                                this.selectedWords.remove(this.selectedWords.getLast());
                                ClientPlayNetworking.send(new InscriptionWordUpdatePacket(false, this.selectedWords.getLast()));
                                this.selectedButtons.remove(this.selectedButtons.getLast());
                            }
                            this.selectedWords.add(handler.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset));
                            ClientPlayNetworking.send(new InscriptionWordUpdatePacket(true, handler.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset)));
                            this.selectedButtons.add(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset);
                        } else {
                            this.selectedWords.remove(handler.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset));
                            ClientPlayNetworking.send(new InscriptionWordUpdatePacket(false, handler.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset)));
                            Object buttonIndex = ((WidgetInscriptionButtonPage) button).index + this.indexStartOffset;
                            this.selectedButtons.remove(buttonIndex);
                        }
                    }
                    this.enchantText = Text.literal("awaiting runes").fillStyle(STYLE.withObfuscated(true));
                }
            }));
            k += 14;
        }

        this.confirmationButton = new WidgetArrowButtonPage(i + 204, j + 50, button -> {
            if (button instanceof WidgetArrowButtonPage){
                ClientPlayNetworking.send(new InscriptionConfirmationPacket());
                this.enchantText = Text.of(this.enchant);
            }
        });
        this.confirmationButton.active = false;

        this.addDrawableChild(this.confirmationButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 512, 256);

        if (this.selectedWords.isEmpty()){
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 104, j + 7, 347, 1, 164, 16, 512, 256);
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 104, j + 7, 347, 19, 164, 16, 512, 256);
        }

        if (!(enchant == null || enchant.isEmpty())){
            int m = 19;
            if (this.maxLevel == 1) m = 21;
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                    i + 188 - m * this.maxLevel /2, j + 81,
                    282, 33 * (this.maxLevel - 1) + 1,
                    m * this.maxLevel, 12,
                    512, 256);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                    i + 188 - m * this.maxLevel /2, j + 81,
                    282, 33 * (this.maxLevel - 1) + 21,
                    m * this.level, 12,
                    512, 256);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                    i + 188 + (m * (this.level - 1)) - m * this.maxLevel /2, j + 81,
                    282 + (m * (this.level  - 1)), 33 * (this.maxLevel - 1) + 14,
                    m, 6,
                    512, 256);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 123, j + 25, 347, 39, 130, 16, 512, 256);
            context.drawText(this.textRenderer, enchant, i + 188 - textRenderer.getWidth(enchant) / 2, j + 29, ColorHelper.fullAlpha(0xad6b3f), false);

            int k = this.handler.getLevelCost();
            int l = this.handler.getPlayerLevels();

            int color;
            Text text = Text.of(k + " Levels");

            if (this.client.player.isInCreativeMode() || (l >= k && k != 0)){
                color = -8323296;
                this.confirmationButton.active = true;
            } else{
                color = -40864;
                this.confirmationButton.active = false;
            }

            context.fill(i + 156, j + 71, i + 221, j + 79, 1325400064);

            context.drawText(this.textRenderer, text, i + 188 - textRenderer.getWidth(text) / 2, j + 71, color, true);
        } else {
            this.confirmationButton.active = false;
            if(!this.selectedWords.isEmpty()){
                context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 123, j + 25, 347, 39, 130, 16, 512, 256);
                StringVisitable stringVisitable = textRenderer.getTextHandler().trimToWidth(this.enchantText, 159, Style.EMPTY);
                context.drawWrappedText(this.textRenderer, stringVisitable, i + 188 - textRenderer.getWidth(stringVisitable.getString()) / 2, j + 29, 159, ColorHelper.fullAlpha(0xad6b3f),false);
            }
        }

        if (this.handler.hasGem()){
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 130, j + 43, 282, 166, 26, 26, 512, 256);
        }

        this.catalystSlotIcon.render(this.handler, context, delta, this.x, this.y);

        StringBuilder stringBuilder = new StringBuilder();

        int m = 0;
        for(String word : this.selectedWords){
            if (m != 0){
                stringBuilder.append("-");
            }
            stringBuilder.append(StringUtils.capitalize(word));
            m++;
        }

        StringVisitable stringVisitable = textRenderer.getTextHandler().trimToWidth(Text.literal(stringBuilder.toString()), 159, Style.EMPTY);
        context.drawCenteredTextWithShadow(this.textRenderer, stringVisitable.getString(), i + 186, j + 11, Colors.WHITE);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.renderScrollbar(context, i, j);

        Iterator<String> words = this.handler.getWords().iterator();

        byte[] enabledWords = this.handler.getAvailableWords();
        for (WidgetInscriptionButtonPage widgetButtonPage : this.words) {
            widgetButtonPage.visible = widgetButtonPage.index < this.handler.getWords().size();
            if(enabledWords != null && widgetButtonPage.index + indexStartOffset < enabledWords.length) {
                widgetButtonPage.setHidden(enabledWords[widgetButtonPage.index + indexStartOffset] != 1);
            }
        }

        String word;
        int index = 0;
        int m = 0;
        int n = j + 25;
        while(words.hasNext()) {
            word = words.next();
            if (!this.canScroll(this.handler.getWords().size()) || (m >= this.indexStartOffset && m < 11 + this.indexStartOffset)) {
                if(index - indexStartOffset >= 0 && index - indexStartOffset < this.words.length) {
                    WidgetInscriptionButtonPage widgetButtonPage = this.words[index - indexStartOffset];
                    if(widgetButtonPage.hidden) {
                        Text text = Text.literal(StringUtils.capitalize(word)).setStyle(Style.EMPTY.withStrikethrough(widgetButtonPage.hidden));
                        context.drawText(this.textRenderer, text, i + 11, n, Colors.LIGHT_GRAY, false);
                    } else {
                        context.drawText(this.textRenderer, StringUtils.capitalize(word), i + 11, n, Colors.WHITE, false);
                    }
                } else {
                    context.drawText(this.textRenderer, StringUtils.capitalize(word), i + 11, n, Colors.WHITE, false);
                }
                n += 14;
            }
            index++;
            ++m;
        }

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean canScroll(int listSize) {
        return listSize > 11;
    }

    private void renderScrollbar(DrawContext context, int x, int y) {
        int i = this.handler.getWords().size() + 1 - 11;
        if (i > 1) {
            int j = 153 - (27 + (i - 1) * 153 / i);
            int k = 1 + j / i + 153 / i;
            int m = Math.min(127, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 127;
            }
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_TEXTURE, x + 94, y + 22 + m, 6, 27);
        } else {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_DISABLED_TEXTURE, x + 94, y + 22, 6, 27);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            int i = this.handler.getWords().size();
            if (this.canScroll(i)) {
                int j = i - 11;
                this.indexStartOffset = MathHelper.clamp((int) ((double) this.indexStartOffset - verticalAmount), 0, j);
            }

        }
        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = this.handler.getWords().size();
        if (this.scrolling) {
            int j = this.y + 18;
            int k = j + 153;
            int l = i - 11;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.indexStartOffset = MathHelper.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        if (this.canScroll((this.handler).getWords().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 22) && mouseY <= (double)(j + 22 + 153 + 1)) {
            this.scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    static class WidgetInscriptionButtonPage extends ButtonWidget {
        final int index;
        boolean selected;
        int selectedIndex = -1;
        boolean hidden;

        private static final Identifier BUTTON_TEXTURE = MiddleEarth.of("word_button");
        private static final Identifier DISABLED_BUTTON_TEXTURE = MiddleEarth.of("word_button_disabled");
        private static final Identifier SELECTED_BUTTON_TEXTURE = MiddleEarth.of("word_button_selected");
        private static final Identifier HIGHLIGHTED_BUTTON_TEXTURE = MiddleEarth.of("word_button_highlighted");
        private static final Identifier BUTTON_MARKERS = MiddleEarth.of("inscription_table_markers");

        public WidgetInscriptionButtonPage(final int x, final int y, final int index, final ButtonWidget.PressAction onPress) {
            super(x, y, 86, 14, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = false;
        }

        public void setSelected(boolean selected){
            this.selected = selected;
        }

        public void setSelectedIndex(int selectedIndex){
            this.selectedIndex = selectedIndex;
        }

        public void setHidden(boolean hidden){
            this.hidden = hidden;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            final int x = getX();
            final int y = getY();
            final int width = getWidth();
            final int height = getHeight();
            if (this.hidden){
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, DISABLED_BUTTON_TEXTURE, x, y, width, height, ColorHelper.getWhite(this.alpha));
            } else if (this.selected){
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SELECTED_BUTTON_TEXTURE, x, y, width, height, ColorHelper.getWhite(this.alpha));
            } else if (this.isHovered()) {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HIGHLIGHTED_BUTTON_TEXTURE, x, y, width, height, ColorHelper.getWhite(this.alpha));
            } else {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BUTTON_TEXTURE, x, y, width, height, ColorHelper.getWhite(this.alpha));
            }

            if(selectedIndex >= 0 && selectedIndex < 3) {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BUTTON_MARKERS, 16, 4,
                        4 * selectedIndex, 0, x + width - 8, y + 4, 4, 4, ColorHelper.getWhite(this.alpha));
            }

            int i = ColorHelper.withAlpha(this.alpha, this.active ? -1 : -6250336);
            this.drawMessage(context, minecraftClient.textRenderer, i);
        }

        @Override
        public void playDownSound(SoundManager soundManager) {

        }
    }

    static class WidgetArrowButtonPage extends ButtonWidget {

        private static final Identifier BUTTON_TEXTURE = MiddleEarth.of("arrow_button");
        private static final Identifier BUTTON_UNAVAILABLE = MiddleEarth.of("arrow_button_unavailable");
        private static final Identifier HIGHLIGHTED_BUTTON_TEXTURE = MiddleEarth.of("arrow_button_highlighted");

        public WidgetArrowButtonPage(final int x, final int y, final ButtonWidget.PressAction onPress) {
            super(x, y, 16, 11, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (this.isHovered() && this.active){
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HIGHLIGHTED_BUTTON_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
            } else if (this.active) {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BUTTON_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
            } else {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BUTTON_UNAVAILABLE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
            }
            int i = ColorHelper.withAlpha(this.alpha, this.active ? -1 : -6250336);
            this.drawMessage(context, minecraftClient.textRenderer, i);
        }
    }
}