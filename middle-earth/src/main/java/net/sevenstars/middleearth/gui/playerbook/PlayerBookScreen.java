package net.sevenstars.middleearth.gui.playerbook;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
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
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
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
    private static final String PATH = "textures/gui/playerbook/";
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
        chapters = List.of(new Chapter("playerbook.chapter_getting_started", PlayerBookChapters.GETTING_STARTED, ResourceItemsME.STARLIGHT_PHIAL),
                new Chapter("playerbook.chapter_mining", PlayerBookChapters.MINING, ToolItemsME.STEEL_PICKAXE),
                new Chapter("playerbook.chapter_smithing", PlayerBookChapters.SMITHING, ToolItemsME.SMITHING_HAMMER),
                new Chapter("playerbook.chapter_enchanting", PlayerBookChapters.ENCHANTING, DecorativeItemsME.INSCRIPTION_TABLE),
                new Chapter("playerbook.chapter_mounts", PlayerBookChapters.MOUNTS, EggItemsME.WARG_SPAWN_EGG),
                new Chapter("playerbook.chapter_dungeons", PlayerBookChapters.DUNGEONS, DecorativeItemsME.SPIDER_TRIAL_SPAWNER.asItem()));
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - 192) / 2;
        int y = this.height / 2;
        this.previousPageButton = this.addDrawableChild(new PageTurnWidget(x - 27, y + 70, false, button -> this.openPreviousPage(), true));
        this.nextPageButton = this.addDrawableChild(new PageTurnWidget(x + 210, y + 70, true, button -> this.openNextPage(), true));
        updatePageButtons();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int centerX = context.getScaledWindowWidth() / 2;
        int startX = 5 + centerX - (WIDTH / 2);
        int startY = (context.getScaledWindowHeight() / 2) - (HEIGHT / 2);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                startX, startY, 0, 0,
                WIDTH, HEIGHT, 384, 384);

        if(currentPage == 0) {
            drawScaledText(textRenderer, context, Text.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.title").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                    startX + (int)(WIDTH * 0.325), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);

            context.drawWrappedText(textRenderer, Text.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.description"), startX + 38, startY + (int)(HEIGHT * 0.22f), 116, Colors.BLACK, false);

            drawScaledText(textRenderer, context, Text.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.chapters").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                    startX + (int)(WIDTH * 0.75), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);
            int index = 0;
            for(Chapter chapter : chapters) {
                MutableText text = Text.translatable("screen." + MiddleEarth.MOD_ID + "." + chapter.name);
                int startTooltipX = centerX + 30;
                int startTooltipY = (context.getScaledWindowHeight() / 2) - (int)(HEIGHT * 0.295f) + (index * 18);
                if (mouseX >= startTooltipX && mouseX <= startTooltipX + (chapter.name.length() * 4.75) + 5 && mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                    context.drawOrderedTooltip(this.client.textRenderer, Lists.transform(
                            List.of(Text.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.navigate_to")
                            ), Text::asOrderedText), mouseX, mouseY);
                    text.formatted(Formatting.UNDERLINE);
                }

                context.drawItem(chapter.icon.getDefaultStack(), startX + (int)(WIDTH * 0.5f) + 12, startY + (int)(HEIGHT * 0.22f) - 4 + (index * 18));
                context.drawText(textRenderer, text, startX + (int)(WIDTH * 0.5f) + 32, startY + (int)(HEIGHT * 0.22f) + (index * 18), Colors.BLACK, false);
                index++;
            }
        } else {
            PlayerBookPageData pageData = chaptersPages.get(currentChapter).get(currentPage - 1);
            if(pageData != null) {
                drawScaledText(textRenderer, context, Text.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.leftPageTitle).formatted(Formatting.UNDERLINE),
                        startX + (int)(WIDTH * 0.3), startY + (int)(HEIGHT * 0.11f), 1.25f, Colors.BLACK, true);

                if(pageData.image != null) {
                    context.drawTexture(RenderPipelines.GUI_TEXTURED, pageData.image,
                            startX, startY, 0, 0,
                            WIDTH, HEIGHT, 320, 320);
                }

                context.drawWrappedText(textRenderer, Text.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.leftPageDescription),
                        startX + 38, startY + (int)(HEIGHT * 0.2f), 116, Colors.BLACK, false);
                context.drawWrappedText(textRenderer, Text.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.rightPageDescription),
                        startX + (int)(WIDTH * 0.5f) + 16, startY + (int)(HEIGHT * 0.16f), 114, Colors.BLACK, false);
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
                int index = 0;
                for (Chapter chapter : chapters) {
                    int startTooltipX = centerX + 30;
                    int startTooltipY = (this.height / 2) - (int)(HEIGHT * 0.28f) + (index * 18);
                    int textWidth = this.client.textRenderer.getWidth(chapter.name);

                    if (mouseX >= startTooltipX && mouseX <= startTooltipX + textWidth + 5 &&
                            mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                        this.currentChapter = chapter.chapter;
                        this.currentPage = 1;
                        updatePageButtons();
                        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        return true;
                    }
                    index++;
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
            context.drawText(textRenderer, text, (int)(x / scale) - (int)((textRenderer.getWidth(text) * Math.pow(scale, 0.5f)) / 2),
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
        updatePageButtons();
    }

    private void openNextPage() {
        if(currentPage < chaptersPages.get(currentChapter).size()) {
            this.currentPage++;
        }
        updatePageButtons();
    }

    private void updatePageButtons() {
        this.previousPageButton.visible = (currentPage != 0);
        this.previousPageButton.active = (currentPage != 0);

        boolean canTurnNextPage = currentPage != 0 && (currentPage < chaptersPages.get(currentChapter).size());
        this.nextPageButton.visible = canTurnNextPage;
        this.nextPageButton.active = canTurnNextPage;
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
        chaptersPages.put(PlayerBookChapters.GETTING_STARTED, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_getting_started").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "getting_started1.png"))
                    .withLeftPageDesc("playerbook.getting_started_desc")
                    .withRightPageDesc("playerbook.getting_started_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.MINING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_mining").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mining1.png"))
                    .withLeftPageDesc("playerbook.mining_desc")
                    .withRightPageDesc("playerbook.mining_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.tools")
                    .withLeftPageDesc("playerbook.mining_tools_desc")
                    .withRightPageDesc("playerbook.mining_tools_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.cave_monsters").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mining3.png"))
                    .withLeftPageDesc("playerbook.mining_cave_monster_desc")
                    .withRightPageDesc("playerbook.mining_cave_monster_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.SMITHING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_smithing").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing1.png"))
                        .withLeftPageDesc("playerbook.smithing_desc")
                        .withRightPageDesc("playerbook.smithing_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.copper_alloys").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing2.png"))
                        .withLeftPageDesc("playerbook.smithing_copper_alloys_smithing_desc")
                        .withRightPageDesc("playerbook.smithing_copper_alloys_smithing_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.casting").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing3.png"))
                        .withLeftPageDesc("playerbook.smithing_casting_desc")
                        .withRightPageDesc("playerbook.smithing_casting_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.shaping_anvil").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing4.png"))
                        .withLeftPageDesc("playerbook.smithing_shaping_anvil_desc")
                        .withRightPageDesc("playerbook.smithing_shaping_anvil_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.shaping_anvil")
                        .withLeftPageDesc("playerbook.smithing_shaping_anvil_1_desc")
                        .withRightPageDesc("playerbook.smithing_shaping_anvil_1_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.quenching").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing5.png"))
                        .withLeftPageDesc("playerbook.smithing_quenching_desc")
                        .withRightPageDesc("playerbook.smithing_quenching_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.artisan_table").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing6.png"))
                        .withLeftPageDesc("playerbook.smithing_artisan_table_desc")
                        .withRightPageDesc("playerbook.smithing_artisan_table_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.artisan_table").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing7.png"))
                        .withLeftPageDesc("playerbook.smithing_artisan_table_1_desc")
                        .withRightPageDesc("playerbook.smithing_artisan_table_1_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.ENCHANTING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_enchanting").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "enchanting1.png"))
                        .withLeftPageDesc("playerbook.enchanting_desc")
                        .withRightPageDesc("playerbook.enchanting_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.inscription_table").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "enchanting2.png"))
                        .withLeftPageDesc("playerbook.inscription_table_desc")
                        .withRightPageDesc("playerbook.inscription_table_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.MOUNTS, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_mounts")
                        .withLeftPageDesc("playerbook.mounts_desc")
                        .withRightPageDesc("playerbook.mounts_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.broadhoof_goat").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_broadhoof_goat.png"))
                        .withLeftPageDesc("playerbook.mount_broadhoof_goat_desc")
                        .withRightPageDesc("playerbook.mount_broadhoof_goat_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.great_horn").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_great_horn.png"))
                        .withLeftPageDesc("playerbook.mount_great_horn_desc")
                        .withRightPageDesc("playerbook.mount_great_horn_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.warg").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_warg.png"))
                        .withLeftPageDesc("playerbook.mount_warg_desc")
                        .withRightPageDesc("playerbook.mount_warg_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.cave_troll").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_cave_troll.png"))
                        .withLeftPageDesc("playerbook.mount_cave_troll_desc")
                        .withRightPageDesc("playerbook.mount_cave_troll_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.DUNGEONS, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_dungeons")
                        .withLeftPageDesc("playerbook.dungeons_desc")
                        .withRightPageDesc("playerbook.dungeons_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.brigands").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "dungeon_brigands.png"))
                        .withLeftPageDesc("playerbook.dungeon_brigands_desc")
                        .withRightPageDesc("playerbook.dungeon_brigands_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.spider_burrows").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "dungeon_spider_burrows.png"))
                        .withLeftPageDesc("playerbook.dungeon_spider_burrows_desc")
                        .withRightPageDesc("playerbook.dungeon_spider_burrows_desc_right")
        ));
    }
}
