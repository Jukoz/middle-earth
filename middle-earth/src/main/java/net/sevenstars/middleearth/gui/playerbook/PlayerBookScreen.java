package net.sevenstars.middleearth.gui.playerbook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.CommonColors;
import net.minecraft.world.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlayerBookScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/gui/player_book.png");
    private static final String PATH = "textures/gui/playerbook/";
    private static final int WIDTH = 320;
    private static final int HEIGHT = 220;
    private static HashMap<PlayerBookChapters, List<PlayerBookPageData>> chaptersPages;
    private List<Chapter> chapters;
    private PageButton nextPageButton;
    private PageButton previousPageButton;
    private PlayerBookChapters currentChapter = PlayerBookChapters.GETTING_STARTED;
    private int currentPage = 0;

    public PlayerBookScreen(Component title) {
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
        this.previousPageButton = this.addRenderableWidget(new PageButton(x - 27, y + 70, false, button -> this.openPreviousPage(), true));
        this.nextPageButton = this.addRenderableWidget(new PageButton(x + 210, y + 70, true, button -> this.openNextPage(), true));
        updatePageButtons();
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);

        int centerX = context.guiWidth() / 2;
        int startX = 5 + centerX - (WIDTH / 2);
        int startY = (context.guiHeight() / 2) - (HEIGHT / 2);

        if(currentPage == 0) {
            drawScaledText(font, context, Component.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.title").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD),
                    startX + (int)(WIDTH * 0.325), startY + (int)(HEIGHT * 0.11f), 1.5f, CommonColors.BLACK, true);

            context.drawWordWrap(font, Component.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.description"), startX + 38, startY + (int)(HEIGHT * 0.22f), 116, CommonColors.BLACK);

            drawScaledText(font, context, Component.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.chapters").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD),
                    startX + (int)(WIDTH * 0.75), startY + (int)(HEIGHT * 0.11f), 1.5f, CommonColors.BLACK, true);
            int index = 0;
            for(Chapter chapter : chapters) {
                MutableComponent text = Component.translatable("screen." + MiddleEarth.MOD_ID + "." + chapter.name);
                int startTooltipX = centerX + 30;
                int startTooltipY = (context.guiHeight() / 2) - (int)(HEIGHT * 0.295f) + (index * 18);
                if (mouseX >= startTooltipX && mouseX <= startTooltipX + (chapter.name.length() * 4.75) + 5 && mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                    context.renderTooltip(this.minecraft.font, Lists.transform(
                            List.of(Component.translatable("screen." + MiddleEarth.MOD_ID + ".playerbook.navigate_to")
                            ), Component::getVisualOrderText), mouseX, mouseY);
                    text.withStyle(ChatFormatting.UNDERLINE);
                }

                context.renderItem(chapter.icon.getDefaultInstance(), startX + (int)(WIDTH * 0.5f) + 12, startY + (int)(HEIGHT * 0.22f) - 4 + (index * 18));
                context.drawString(font, text, startX + (int)(WIDTH * 0.5f) + 32, startY + (int)(HEIGHT * 0.22f) + (index * 18), CommonColors.BLACK, false);
                index++;
            }
        } else {
            PlayerBookPageData pageData = chaptersPages.get(currentChapter).get(currentPage - 1);
            if(pageData != null) {
                drawScaledText(font, context, Component.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.leftPageTitle).withStyle(ChatFormatting.UNDERLINE),
                        startX + (int)(WIDTH * 0.3), startY + (int)(HEIGHT * 0.11f), 1.25f, CommonColors.BLACK, true);

                if(pageData.image != null) {
                    context.blit(pageData.image,
                            startX, startY, 0, 0,
                            WIDTH, HEIGHT, 320, 320);
                }

                context.drawWordWrap(font, Component.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.leftPageDescription),
                        startX + 38, startY + (int)(HEIGHT * 0.2f), 116, CommonColors.BLACK);
                context.drawWordWrap(font, Component.translatable("screen." + MiddleEarth.MOD_ID + "." + pageData.rightPageDescription),
                        startX + (int)(WIDTH * 0.5f) + 16, startY + (int)(HEIGHT * 0.16f), 114, CommonColors.BLACK);
            }
        }

        this.renderables.forEach(renderable -> renderable.render(context, mouseX, mouseY, deltaTicks));
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        super.renderBackground(context, mouseX, mouseY, deltaTicks);

        int centerX = context.guiWidth() / 2;
        int startX = 5 + centerX - (WIDTH / 2);
        int startY = (context.guiHeight() / 2) - (HEIGHT / 2);
        context.blit(TEXTURE,
                startX, startY, 0, 0,
                WIDTH, HEIGHT, 384, 384);
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
                    int textWidth = this.minecraft.font.width(chapter.name);

                    if (mouseX >= startTooltipX && mouseX <= startTooltipX + textWidth + 5 &&
                            mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                        this.currentChapter = chapter.chapter;
                        this.currentPage = 1;
                        updatePageButtons();
                        this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        return true;
                    }
                    index++;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderTooltip(GuiGraphics context, int mouseX, int mouseY) {
        int centerX = context.guiWidth() / 2;
    }

    public static void drawScaledText(Font textRenderer, GuiGraphics context, Component text, int x, int y, float scale, int color, boolean centered) {
        PoseStack matrices = context.pose();
        matrices.pushPose();
        matrices.scale(scale, scale, 1.0F);

        if (centered) {
            context.drawString(textRenderer, text, (int)(x / scale) - (int)((textRenderer.width(text) * Math.pow(scale, 0.5f)) / 2),
                    (int)(y / scale), color, false);
        } else {
            context.drawString(textRenderer, text, x, y, color, false);
        }

        matrices.popPose();
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
                new PlayerBookPageData().withTitle("playerbook.chapter_getting_started").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "getting_started1.png"))
                    .withLeftPageDesc("playerbook.getting_started_desc")
                    .withRightPageDesc("playerbook.getting_started_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.MINING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_mining").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mining1.png"))
                    .withLeftPageDesc("playerbook.mining_desc")
                    .withRightPageDesc("playerbook.mining_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.tools")
                    .withLeftPageDesc("playerbook.mining_tools_desc")
                    .withRightPageDesc("playerbook.mining_tools_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.cave_monsters").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mining3.png"))
                    .withLeftPageDesc("playerbook.mining_cave_monster_desc")
                    .withRightPageDesc("playerbook.mining_cave_monster_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.SMITHING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_smithing").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing1.png"))
                        .withLeftPageDesc("playerbook.smithing_desc")
                        .withRightPageDesc("playerbook.smithing_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.copper_alloys").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing2.png"))
                        .withLeftPageDesc("playerbook.smithing_copper_alloys_smithing_desc")
                        .withRightPageDesc("playerbook.smithing_copper_alloys_smithing_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.casting").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing3.png"))
                        .withLeftPageDesc("playerbook.smithing_casting_desc")
                        .withRightPageDesc("playerbook.smithing_casting_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.shaping_anvil").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing4.png"))
                        .withLeftPageDesc("playerbook.smithing_shaping_anvil_desc")
                        .withRightPageDesc("playerbook.smithing_shaping_anvil_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.shaping_anvil")
                        .withLeftPageDesc("playerbook.smithing_shaping_anvil_1_desc")
                        .withRightPageDesc("playerbook.smithing_shaping_anvil_1_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.quenching").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing5.png"))
                        .withLeftPageDesc("playerbook.smithing_quenching_desc")
                        .withRightPageDesc("playerbook.smithing_quenching_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.artisan_table").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing6.png"))
                        .withLeftPageDesc("playerbook.smithing_artisan_table_desc")
                        .withRightPageDesc("playerbook.smithing_artisan_table_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.artisan_table").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "smithing7.png"))
                        .withLeftPageDesc("playerbook.smithing_artisan_table_1_desc")
                        .withRightPageDesc("playerbook.smithing_artisan_table_1_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.ENCHANTING, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_enchanting").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "enchanting1.png"))
                        .withLeftPageDesc("playerbook.enchanting_desc")
                        .withRightPageDesc("playerbook.enchanting_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.inscription_table").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "enchanting2.png"))
                        .withLeftPageDesc("playerbook.inscription_table_desc")
                        .withRightPageDesc("playerbook.inscription_table_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.MOUNTS, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_mounts")
                        .withLeftPageDesc("playerbook.mounts_desc")
                        .withRightPageDesc("playerbook.mounts_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.broadhoof_goat").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mount_broadhoof_goat.png"))
                        .withLeftPageDesc("playerbook.mount_broadhoof_goat_desc")
                        .withRightPageDesc("playerbook.mount_broadhoof_goat_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.great_horn").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mount_great_horn.png"))
                        .withLeftPageDesc("playerbook.mount_great_horn_desc")
                        .withRightPageDesc("playerbook.mount_great_horn_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.warg").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mount_warg.png"))
                        .withLeftPageDesc("playerbook.mount_warg_desc")
                        .withRightPageDesc("playerbook.mount_warg_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.cave_troll").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "mount_cave_troll.png"))
                        .withLeftPageDesc("playerbook.mount_cave_troll_desc")
                        .withRightPageDesc("playerbook.mount_cave_troll_desc_right")
        ));

        chaptersPages.put(PlayerBookChapters.DUNGEONS, List.of(
                new PlayerBookPageData().withTitle("playerbook.chapter_dungeons")
                        .withLeftPageDesc("playerbook.dungeons_desc")
                        .withRightPageDesc("playerbook.dungeons_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.brigands").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "dungeon_brigands.png"))
                        .withLeftPageDesc("playerbook.dungeon_brigands_desc")
                        .withRightPageDesc("playerbook.dungeon_brigands_desc_right"),
                new PlayerBookPageData().withTitle("playerbook.spider_burrows").withImage(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "dungeon_spider_burrows.png"))
                        .withLeftPageDesc("playerbook.dungeon_spider_burrows_desc")
                        .withRightPageDesc("playerbook.dungeon_spider_burrows_desc_right")
        ));
    }
}
