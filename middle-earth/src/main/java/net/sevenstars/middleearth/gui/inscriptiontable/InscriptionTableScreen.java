package net.sevenstars.middleearth.gui.inscriptiontable;

import net.neoforged.neoforge.network.PacketDistributor;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.InscriptionConfirmationPacket;
import net.sevenstars.middleearth.network.packets.C2S.InscriptionWordUpdatePacket;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableScreenHandler> {
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "inscription_table.png");

    private static final ResourceLocation SCROLLER_TEXTURE = ResourceLocation.withDefaultNamespace("container/villager/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_TEXTURE = ResourceLocation.withDefaultNamespace("container/villager/scroller_disabled");

    private static final ResourceLocation EMPTY_SLOT_EMERALD_TEXTURE = ResourceLocation.withDefaultNamespace("container/slot/emerald");
    private static final ResourceLocation EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = ResourceLocation.withDefaultNamespace("container/slot/lapis_lazuli");
    private static final ResourceLocation EMPTY_SLOT_ADAMANT_TEXTURE = MiddleEarth.ofPath( "container", "slot", "adamant");
    private static final ResourceLocation EMPTY_SLOT_RUBY_TEXTURE = MiddleEarth.ofPath( "container", "slot", "ruby");
    private static final ResourceLocation EMPTY_SLOT_SAPPHIRE_TEXTURE = MiddleEarth.ofPath( "container", "slot", "sapphire");

    private static final ResourceLocation FONT_ID = ResourceLocation.withDefaultNamespace("alt");
    private static final Style STYLE = Style.EMPTY.withFont(FONT_ID);

    private static int withAlpha(float alpha, int color) {
        return Mth.ceil(alpha * 255.0F) << 24 | color & 0xFFFFFF;
    }

    int indexStartOffset;
    private boolean scrolling;

    private String enchant;
    private int level;
    private int maxLevel;
    private Component enchantText;

    private final CyclingSlotBackground catalystSlotIcon = new CyclingSlotBackground(0);

    private WidgetArrowButtonPage confirmationButton;

    private final WidgetInscriptionButtonPage[] words = new WidgetInscriptionButtonPage[11];
    private final List<String> selectedWords = new ArrayList<>();
    private final List<Integer> selectedButtons = new ArrayList<>();

    public InscriptionTableScreen(InscriptionTableScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 275;
        this.imageHeight = 183;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.catalystSlotIcon.tick(List.of(
                EMPTY_SLOT_LAPIS_LAZULI_TEXTURE,
                EMPTY_SLOT_ADAMANT_TEXTURE,
                EMPTY_SLOT_EMERALD_TEXTURE,
                EMPTY_SLOT_RUBY_TEXTURE,
                EMPTY_SLOT_SAPPHIRE_TEXTURE));

        for (WidgetInscriptionButtonPage button : this.words){
            button.setSelected(this.selectedButtons.contains(button.index + this.indexStartOffset));
            button.setSelectedIndex(this.selectedButtons.contains(button.index  + this.indexStartOffset) ? this.selectedButtons.indexOf(button.index + this.indexStartOffset) : -1);
        }

        if (!this.menu.hasAll()){
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
        titleLabelX = 6;
        inventoryLabelX = 108;
        inventoryLabelY = 92;

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 22;

        for(int l = 0; l < 11; ++l) {
            this.words[l] = this.addRenderableWidget(new WidgetInscriptionButtonPage(i + 5, k, l, button -> {
                if (button instanceof WidgetInscriptionButtonPage wordButton) {
                    if (button.isHoveredOrFocused() && !wordButton.hidden){
                        if (!((WidgetInscriptionButtonPage) button).selected){
                            if (this.selectedWords.size() == 3){
                                this.selectedWords.remove(this.selectedWords.getLast());
                                PacketDistributor.sendToServer(new InscriptionWordUpdatePacket(false, this.selectedWords.getLast()));
                                this.selectedButtons.remove(this.selectedButtons.getLast());
                            }
                            this.selectedWords.add(menu.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset));
                            PacketDistributor.sendToServer(new InscriptionWordUpdatePacket(true, menu.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset)));
                            this.selectedButtons.add(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset);
                        } else {
                            this.selectedWords.remove(menu.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset));
                            PacketDistributor.sendToServer(new InscriptionWordUpdatePacket(false, menu.getWords().get(((WidgetInscriptionButtonPage) button).index + this.indexStartOffset)));
                            Object buttonIndex = ((WidgetInscriptionButtonPage) button).index + this.indexStartOffset;
                            this.selectedButtons.remove(buttonIndex);
                        }
                    }
                    this.enchantText = Component.literal("awaiting runes").withStyle(STYLE.withObfuscated(true));
                }
            }));
            k += 14;
        }

        this.confirmationButton = new WidgetArrowButtonPage(i + 204, j + 50, button -> {
            if (button instanceof WidgetArrowButtonPage){
                PacketDistributor.sendToServer(new InscriptionConfirmationPacket());
                this.enchantText = Component.nullToEmpty(this.enchant);
            }
        });
        this.confirmationButton.active = false;

        this.addRenderableWidget(this.confirmationButton);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 512, 256);

        if (this.selectedWords.isEmpty()){
            context.blit(TEXTURE, i + 104, j + 7, 347, 1, 164, 16, 512, 256);
        } else {
            context.blit(TEXTURE, i + 104, j + 7, 347, 19, 164, 16, 512, 256);
        }

        if (!(enchant == null || enchant.isEmpty())){
            int m = 19;
            if (this.maxLevel == 1) m = 21;
            context.blit(TEXTURE,
                    i + 188 - m * this.maxLevel /2, j + 81,
                    282, 33 * (this.maxLevel - 1) + 1,
                    m * this.maxLevel, 12,
                    512, 256);
            context.blit(TEXTURE,
                    i + 188 - m * this.maxLevel /2, j + 81,
                    282, 33 * (this.maxLevel - 1) + 21,
                    m * this.level, 12,
                    512, 256);
            context.blit(TEXTURE,
                    i + 188 + (m * (this.level - 1)) - m * this.maxLevel /2, j + 81,
                    282 + (m * (this.level  - 1)), 33 * (this.maxLevel - 1) + 14,
                    m, 6,
                    512, 256);

            context.blit(TEXTURE, i + 123, j + 25, 347, 39, 130, 16, 512, 256);
            context.drawString(this.font, enchant, i + 188 - font.width(enchant) / 2, j + 29, 0xFFAD6B3F, false);

            int k = this.menu.getLevelCost();
            int l = this.menu.getPlayerLevels();

            int color;
            Component text = Component.nullToEmpty(k + " Levels");

            if (this.minecraft.player.hasInfiniteMaterials() || (l >= k && k != 0)){
                color = -8323296;
                this.confirmationButton.active = true;
            } else{
                color = -40864;
                this.confirmationButton.active = false;
            }

            context.fill(i + 156, j + 71, i + 221, j + 79, 1325400064);

            context.drawString(this.font, text, i + 188 - font.width(text) / 2, j + 71, color, true);
        } else {
            this.confirmationButton.active = false;
            if(!this.selectedWords.isEmpty()){
                context.blit(TEXTURE, i + 123, j + 25, 347, 39, 130, 16, 512, 256);
                FormattedText stringVisitable = font.getSplitter().headByWidth(this.enchantText, 159, Style.EMPTY);
                context.drawWordWrap(this.font, stringVisitable, i + 188 - font.width(stringVisitable.getString()) / 2, j + 29, 159, 0xFFAD6B3F);
            }
        }

        if (this.menu.hasGem()){
            context.blit(TEXTURE, i + 130, j + 43, 282, 166, 26, 26, 512, 256);
        }

        this.catalystSlotIcon.render(this.menu, context, delta, this.leftPos, this.topPos);

        StringBuilder stringBuilder = new StringBuilder();

        int m = 0;
        for(String word : this.selectedWords){
            if (m != 0){
                stringBuilder.append("-");
            }
            stringBuilder.append(StringUtils.capitalize(word));
            m++;
        }

        FormattedText stringVisitable = font.getSplitter().headByWidth(Component.literal(stringBuilder.toString()), 159, Style.EMPTY);
        context.drawCenteredString(this.font, stringVisitable.getString(), i + 186, j + 11, CommonColors.WHITE);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.renderScrollbar(context, i, j);

        Iterator<String> words = this.menu.getWords().iterator();

        byte[] enabledWords = this.menu.getAvailableWords();
        for (WidgetInscriptionButtonPage widgetButtonPage : this.words) {
            widgetButtonPage.visible = widgetButtonPage.index < this.menu.getWords().size();
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
            if (!this.canScroll(this.menu.getWords().size()) || (m >= this.indexStartOffset && m < 11 + this.indexStartOffset)) {
                if(index - indexStartOffset >= 0 && index - indexStartOffset < this.words.length) {
                    WidgetInscriptionButtonPage widgetButtonPage = this.words[index - indexStartOffset];
                    if(widgetButtonPage.hidden) {
                        Component text = Component.literal(StringUtils.capitalize(word)).setStyle(Style.EMPTY.withStrikethrough(widgetButtonPage.hidden));
                        context.drawString(this.font, text, i + 11, n, CommonColors.LIGHT_GRAY, false);
                    } else {
                        context.drawString(this.font, StringUtils.capitalize(word), i + 11, n, CommonColors.WHITE, false);
                    }
                } else {
                    context.drawString(this.font, StringUtils.capitalize(word), i + 11, n, CommonColors.WHITE, false);
                }
                n += 14;
            }
            index++;
            ++m;
        }

        renderTooltip(context, mouseX, mouseY);
    }

    private boolean canScroll(int listSize) {
        return listSize > 11;
    }

    private void renderScrollbar(GuiGraphics context, int x, int y) {
        int i = this.menu.getWords().size() + 1 - 11;
        if (i > 1) {
            int j = 153 - (27 + (i - 1) * 153 / i);
            int k = 1 + j / i + 153 / i;
            int m = Math.min(127, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 127;
            }
            context.blitSprite(SCROLLER_TEXTURE, x + 94, y + 22 + m, 6, 27);
        } else {
            context.blitSprite(SCROLLER_DISABLED_TEXTURE, x + 94, y + 22, 6, 27);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            int i = this.menu.getWords().size();
            if (this.canScroll(i)) {
                int j = i - 11;
                this.indexStartOffset = Mth.clamp((int) ((double) this.indexStartOffset - verticalAmount), 0, j);
            }

        }
        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = this.menu.getWords().size();
        if (this.scrolling) {
            int j = this.topPos + 18;
            int k = j + 153;
            int l = i - 11;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.indexStartOffset = Mth.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.canScroll((this.menu).getWords().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 22) && mouseY <= (double)(j + 22 + 153 + 1)) {
            this.scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    static class WidgetInscriptionButtonPage extends Button {
        final int index;
        boolean selected;
        int selectedIndex = -1;
        boolean hidden;

        private static final ResourceLocation BUTTON_TEXTURE = MiddleEarth.of("word_button");
        private static final ResourceLocation DISABLED_BUTTON_TEXTURE = MiddleEarth.of("word_button_disabled");
        private static final ResourceLocation SELECTED_BUTTON_TEXTURE = MiddleEarth.of("word_button_selected");
        private static final ResourceLocation HIGHLIGHTED_BUTTON_TEXTURE = MiddleEarth.of("word_button_highlighted");
        private static final ResourceLocation BUTTON_MARKERS = MiddleEarth.of("inscription_table_markers");

        public WidgetInscriptionButtonPage(final int x, final int y, final int index, final Button.OnPress onPress) {
            super(x, y, 86, 14, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
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
        protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
            Minecraft minecraftClient = Minecraft.getInstance();
            final int x = getX();
            final int y = getY();
            final int width = getWidth();
            final int height = getHeight();
            context.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            if (this.hidden){
                context.blitSprite(DISABLED_BUTTON_TEXTURE, x, y, width, height);
            } else if (this.selected){
                context.blitSprite(SELECTED_BUTTON_TEXTURE, x, y, width, height);
            } else if (this.isHovered()) {
                context.blitSprite(HIGHLIGHTED_BUTTON_TEXTURE, x, y, width, height);
            } else {
                context.blitSprite(BUTTON_TEXTURE, x, y, width, height);
            }

            if(selectedIndex >= 0 && selectedIndex < 3) {
                context.blitSprite(BUTTON_MARKERS, 16, 4,
                        4 * selectedIndex, 0, x + width - 8, y + 5, 0, 4, 4);
            }
            context.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            int i = withAlpha(this.alpha, this.active ? -1 : -6250336);
            this.renderString(context, minecraftClient.font, i);
        }

        @Override
        public void playDownSound(SoundManager soundManager) {

        }
    }

    static class WidgetArrowButtonPage extends Button {

        private static final ResourceLocation BUTTON_TEXTURE = MiddleEarth.of("arrow_button");
        private static final ResourceLocation BUTTON_UNAVAILABLE = MiddleEarth.of("arrow_button_unavailable");
        private static final ResourceLocation HIGHLIGHTED_BUTTON_TEXTURE = MiddleEarth.of("arrow_button_highlighted");

        public WidgetArrowButtonPage(final int x, final int y, final Button.OnPress onPress) {
            super(x, y, 16, 11, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        }

        @Override
        protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
            Minecraft minecraftClient = Minecraft.getInstance();
            context.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            if (this.isHovered() && this.active){
                context.blitSprite(HIGHLIGHTED_BUTTON_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
            } else if (this.active) {
                context.blitSprite(BUTTON_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
            } else {
                context.blitSprite(BUTTON_UNAVAILABLE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }
            context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = withAlpha(this.alpha, this.active ? -1 : -6250336);
            this.renderString(context, minecraftClient.font, i);
        }
    }
}
