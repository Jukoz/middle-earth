package net.sevenstars.middleearth.gui.playerbook;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import org.joml.Matrix3x2fStack;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PlayerBookScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/player_book.png");
    private static final int WIDTH = 320;
    private static final int HEIGHT = 220;
    private static HashMap<PlayerBookChapters, List<PlayerBookPageData>> chaptersPages;
    private List<Chapter> chapters;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    private PlayerBookChapters currentChapter = PlayerBookChapters.GETTING_STARTED;
    private int currentPage = 0;

    public PlayerBookScreen(Text title) {
        super(title);
        chapters = List.of(new Chapter("Getting Started", PlayerBookChapters.GETTING_STARTED, ResourceItemsME.STARLIGHT_PHIAL),
                new Chapter("Mining", PlayerBookChapters.MINING, ToolItemsME.STEEL_PICKAXE),
                new Chapter("Smithing", PlayerBookChapters.SMITHING, ToolItemsME.SMITHING_HAMMER),
                new Chapter("Enchanting", PlayerBookChapters.ENCHANTING, DecorativeItemsME.INSCRIPTION_TABLE),
                new Chapter("Mounts", PlayerBookChapters.MOUNTS, EggItemsME.WARG_SPAWN_EGG),
                new Chapter("Dungeons", PlayerBookChapters.DUNGEONS, DecorativeItemsME.SPIDER_TRIAL_SPAWNER.asItem()));
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - 192) / 2;
        int j = this.height / 2;
        this.previousPageButton = this.addDrawableChild(new PageTurnWidget(i - 27, j + 70, false, button -> this.openPreviousPage(), true));
        this.nextPageButton = this.addDrawableChild(new PageTurnWidget(i + 210, j + 70, true, button -> this.openNextPage(), true));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int centerX = context.getScaledWindowWidth() / 2;
        int startX = 5 + centerX - (WIDTH / 2);
        int startY = (context.getScaledWindowHeight() / 2) - (HEIGHT / 2);

        this.nextPageButton.visible = true;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                startX, startY, 0, 0,
                WIDTH, HEIGHT, 384, 384);

        if(currentPage == 0) {
            drawScaledText(textRenderer, context, Text.literal("Middle-earth").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                    startX + (int)(WIDTH * 0.375), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);

            String paragraph = "This mod is about the famous universe of Middle-earth.\n " +
                    "You will find a brand new dimension with custom blocks, items, entities, factions, structures and more!";
            context.drawWrappedText(textRenderer, Text.literal(paragraph), startX + 36, startY + (int)(HEIGHT * 0.22f), 120, Colors.BLACK, false);

            drawScaledText(textRenderer, context, Text.literal("Chapters").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                    startX + (int)(WIDTH * 0.75), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);
            int i = 0;
            for(Chapter chapter : chapters) {
                MutableText text = Text.literal(chapter.name);
                int startTooltipX = centerX + 30;
                int startTooltipY = (context.getScaledWindowHeight() / 2) - (int)(HEIGHT * 0.295f) + (i * 18);
                if (mouseX >= startTooltipX && mouseX <= startTooltipX + (chapter.name.length() * 4.75) + 5 && mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                    context.drawOrderedTooltip(this.client.textRenderer, Lists.transform(
                            List.of(Text.of("Navigate to " + chapter.name)
                            ), Text::asOrderedText), mouseX, mouseY);
                    text.formatted(Formatting.UNDERLINE);
                }

                context.drawItem(chapter.icon.getDefaultStack(), startX + (int)(WIDTH * 0.5f) + 12, startY + (int)(HEIGHT * 0.22f) - 4 + (i * 18));
                context.drawText(textRenderer, text, startX + (int)(WIDTH * 0.5f) + 32, startY + (int)(HEIGHT * 0.22f) + (i * 18), Colors.BLACK, false);
                i++;
            }
        } else {
            PlayerBookPageData pageData = chaptersPages.get(currentChapter).get(currentPage - 1);
            if(pageData != null) {
                drawScaledText(textRenderer, context, Text.literal(pageData.leftPageTitle).formatted(Formatting.UNDERLINE),
                        startX + (int)(WIDTH * 0.3), startY + (int)(HEIGHT * 0.11f), 1.25f, Colors.BLACK, true);

                if(pageData.image != null) {
                    context.drawTexture(RenderPipelines.GUI_TEXTURED, pageData.image,
                            startX, startY, 0, 0,
                            WIDTH, HEIGHT, 320, 320);
                }

                context.drawWrappedText(textRenderer, pageData.leftPageDescription, startX + 36, startY + (int)(HEIGHT * 0.2f), 120, Colors.BLACK, false);
                context.drawWrappedText(textRenderer, pageData.rightPageDescription, startX + (int)(WIDTH * 0.5f) + 16, startY + (int)(HEIGHT * 0.125f), 115, Colors.BLACK, false);
            }
        }

        renderTooltip(context, mouseX, mouseY);
        super.render(context, mouseX, mouseY, deltaTicks);
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(currentPage == 0) { // Table of content
            if (button == 0) {
                int centerX = this.width / 2;
                int i = 0;
                System.out.println(mouseX + " : " + mouseY);

                for (Chapter chapter : chapters) {
                    int startTooltipX = centerX + 30;
                    int startTooltipY = (this.height / 2) - (int)(HEIGHT * 0.285f) + (i * 18);
                    int textWidth = this.client.textRenderer.getWidth(chapter.name);

                    if (mouseX >= startTooltipX && mouseX <= startTooltipX + textWidth + 5 &&
                            mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                        this.currentChapter = chapter.chapter;
                        this.currentPage = 1;
                        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        return true;
                    }
                    i++;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        int centerX = context.getScaledWindowWidth() / 2;
    }

    public static void drawScaledText(TextRenderer textRenderer, DrawContext context, Text text, int x, int y, float scale, int color, boolean centered) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.scale(scale);

        if (centered) {
            context.drawText(textRenderer, text, (int)(x / scale) - (int)((textRenderer.getWidth(text) / 2) * scale),
                    (int)(y / scale), color, false);
        } else {
            context.drawText(textRenderer, text, x, y, color, false);
        }

        matrices.popMatrix();
    }

    private void openPreviousPage() {
        if (this.currentPage > 0) {
            this.currentPage--;
        }
    }

    private void openNextPage() {
        this.currentPage++;
    }

    private class Chapter {
        public String name;
        public PlayerBookChapters chapter;
        public Item icon;

        public Chapter(String name, PlayerBookChapters chapter, Item icon) {
            this.name = name;
            this.chapter = chapter;
            this.icon = icon;
        }
    }

    static {
        chaptersPages = new HashMap<>();
        chaptersPages.put(PlayerBookChapters.MINING, List.of(
                new PlayerBookPageData().withTitle("Mining")
                    .withLeftPageDesc(Text.of("Basic resources like coal, tin and copper can be found almost anywhere near the surface, " +
                        "but valuable ores and gems are only found at deeper levels.\n\n From shallowest to deepest, " +
                        "the strata of the world consists of layers of Stone, Deepslate, Núrgon, and Medgon."))
                    .withRightPageDesc(Text.of("You must Smith stronger tools to extract resources from the rock in deeper layers.\n\n " +
                        "Deepslate minerals can be obtained using Stone Tools or better, Núrgon ores require at least " +
                        "Bronze Tools, and Steel Tools are needed to mine Medgon. "))

        ));

        chaptersPages.put(PlayerBookChapters.SMITHING, List.of(
                new PlayerBookPageData().withTitle("Smithing").withImage(Identifier.of(MiddleEarth.MOD_ID, "textures/gui/playerbook/mining1.png"))
                        .withLeftPageDesc(Text.of("Better equipment will be essential for survival in the dangerous wilds of the world.\n\n " +
                                "Smithing allows you to create new deadly weapons, more complex armour, and efficient tools to delve more deeply."))
                        .withRightPageDesc(Text.of("To work metals with more finesse than crafting, you’ll need a Forge.\n\n " +
                                "You can craft it with any cobbled stones, a furnace and bricks.\n To get clay for bricks, either find a lush cave or convert mud to clay"))
        ));

        chaptersPages.put(PlayerBookChapters.ENCHANTING, List.of(
                new PlayerBookPageData().withTitle("Enchanting")
                        .withLeftPageDesc(Text.of("The greatest artisans of past ages were known to have honed their craft so wonderfully, " +
                                "resulting in storied blades and armour with extraordinary properties. "))
                        .withRightPageDesc(Text.of(""))
        ));
    }
}
