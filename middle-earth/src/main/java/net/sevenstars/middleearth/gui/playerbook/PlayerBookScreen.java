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
            drawScaledText(textRenderer, context, Text.literal("Middle-earth").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                    startX + (int)(WIDTH * 0.325), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);

            String paragraph = "This mod is about the famous universe of Middle-earth.\n " +
                    "You will find a brand new dimension with custom blocks, items, entities, factions, structures and more!";
            context.drawWrappedText(textRenderer, Text.literal(paragraph), startX + 38, startY + (int)(HEIGHT * 0.22f), 116, Colors.BLACK, false);

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

                context.drawWrappedText(textRenderer, pageData.leftPageDescription, startX + 38, startY + (int)(HEIGHT * 0.2f), 116, Colors.BLACK, false);
                context.drawWrappedText(textRenderer, pageData.rightPageDescription, startX + (int)(WIDTH * 0.5f) + 16, startY + (int)(HEIGHT * 0.16f), 114, Colors.BLACK, false);
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
                for (Chapter chapter : chapters) {
                    int startTooltipX = centerX + 30;
                    int startTooltipY = (this.height / 2) - (int)(HEIGHT * 0.28f) + (i * 18);
                    int textWidth = this.client.textRenderer.getWidth(chapter.name);

                    if (mouseX >= startTooltipX && mouseX <= startTooltipX + textWidth + 5 &&
                            mouseY >= startTooltipY && mouseY <= startTooltipY + 9) {
                        this.currentChapter = chapter.chapter;
                        this.currentPage = 1;
                        updatePageButtons();
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
                new PlayerBookPageData().withTitle("Getting Started").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "getting_started1.png"))
                    .withLeftPageDesc(Text.of("To enter Middle-earth and thus begin your adventure, you shall first craft a starlight phial.\n " +
                            "Once the phial is crafted, fill it with water and then combine with a glowing item such as glow berries, glowstone or glow ink to finally craft the starlight phial."))
                    .withRightPageDesc(Text.of("Use it and you'll be brought to the onboarding process to Middle-earth."))
        ));

        chaptersPages.put(PlayerBookChapters.MINING, List.of(
                new PlayerBookPageData().withTitle("Mining").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mining1.png"))
                    .withLeftPageDesc(Text.of("Basic resources like coal, tin and copper can be found almost anywhere near the surface, " +
                        "but valuable ores and gems are only found at deeper levels.\n\n From shallowest to deepest, " +
                        "the strata of the world consists of layers of Stone, Deepslate, Núrgon, and Medgon."))
                    .withRightPageDesc(Text.of("")),
                new PlayerBookPageData().withTitle("Tools")
                        .withLeftPageDesc(Text.of("You must Smith stronger tools to extract resources from the rock in deeper layers.\n\n " +
                                "Deepslate minerals can be obtained using Stone Tools or better, Núrgon ores require at least Bronze Tools, and Steel Tools are needed to mine Medgon. "))
                        .withRightPageDesc(Text.of("Read the chapter about Smithing to learn how to get better tools.")),
                new PlayerBookPageData().withTitle("Cave Monsters").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mining3.png"))
                    .withLeftPageDesc(Text.of("Delving deep is not without danger —  miners tell of roving bands of Wild Goblins patrolling the dark reaches of Middle-earth, " +
                            "preying on the unwary. Some tales even say fouler and darker things stalk the roots of the world, lying in wait… go not into the deep unprepared."))
                    .withRightPageDesc(Text.of("The dark depths hide many a glimmering prize, but caution is to be exercised!\n"))
        ));

        chaptersPages.put(PlayerBookChapters.SMITHING, List.of(
                new PlayerBookPageData().withTitle("Smithing").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing1.png"))
                        .withLeftPageDesc(Text.of("Better equipment will be essential for survival in the dangerous wilds of the world.\n\n " +
                                "Smithing allows you to create new deadly weapons, more complex armour, and efficient tools to delve more deeply."))
                        .withRightPageDesc(Text.of("To work metals with more finesse than crafting, you’ll need a Forge.\n\n " +
                                "You can craft it with any cobbled stones, a furnace and bricks.\n To get clay for bricks, either find a lush cave or convert mud to clay")),
                new PlayerBookPageData().withTitle("Copper Alloys").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing2.png"))
                        .withLeftPageDesc(Text.of("Bronze and Crude are simple alloys, harder and more durable than stone and copper.\n\n " +
                                "Copper alloy tools will enable you to mine deeper than before, entering the Nurgon layer of the world and exploiting the riches found there.\n" +
                                "Make sure to toggle the forge to alloying mode."))
                        .withRightPageDesc(Text.of("Here's their recipes")),
                new PlayerBookPageData().withTitle("Casting").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing3.png"))
                        .withLeftPageDesc(Text.of("All molten metals can be cast into Nuggets, Ingots, Rods, Plates, and Large Rods. " +
                                "Different casted parts will require different amounts of molten metal to create.\n\n " +
                                "A forge’s crucible can hold up to 16 ingots-worth of molten liquid metal"))
                        .withRightPageDesc(Text.of("")),
                new PlayerBookPageData().withTitle("Shaping Anvil").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing4.png"))
                        .withLeftPageDesc(Text.of("Smithing Anvils are a handy solution to tackle the next step in smithing.\n " +
                                "The Stone Anvil is the most basic type of smithing anvil — create one by laying down a base of three logs of any type under six stones of any kind."))
                        .withRightPageDesc(Text.of("You’ll need a Smithing Hammer to work your casted parts over the Smithing Anvil")),
                new PlayerBookPageData().withTitle("Shaping Anvil")
                        .withLeftPageDesc(Text.of("Now you have all the tools needed for an amateur smith. Put a Hot Large Metal Rod onto the stone anvil.\n\n " +
                                "Next, shape it into a Pickaxe Head. Using the Smithing Hammer, hit the large rod at a stable tempo."))
                        .withRightPageDesc(Text.of("Casted parts can only be worked into new shapes on the smithing anvil while they are hot, " +
                                "and will also cool down over time while on the anvil.\n\n You can reheat cooled parts in a low-heat forge without resetting their progress.")),
                new PlayerBookPageData().withTitle("Quenching").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing5.png"))
                        .withLeftPageDesc(Text.of("Once the large rod is fully shaped into the pickaxe head, it must be cool to the touch before being worked further. " +
                                "You must quench it in a filled Cauldron; cooling it instantly."))
                        .withRightPageDesc(Text.of("Both the cauldron and bucket can be crafted using Tin, which can be found in caves near the surface.")),
                new PlayerBookPageData().withTitle("Artisan Table").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "smithing6.png"))
                        .withLeftPageDesc(Text.of("The Artisan Table is the workstation of choice for armourers, blacksmiths and craftspeople across many realms, " +
                                "and will be required to piece together smithing components into a final product.\n " +
                                "Its recipe is one cobbled stone over one log on either side of a tin ingot, in a helmet shape. "))
                        .withRightPageDesc(Text.of("")),
                new PlayerBookPageData().withTitle("Artisan Table")
                        .withLeftPageDesc(Text.of("Place your bronze pickaxe head and two sticks in the Artisan Table, " +
                                "and you are now ready to explore even farther underground using your new Bronze Pickaxe. "))
                        .withRightPageDesc(Text.of(""))
        ));

        chaptersPages.put(PlayerBookChapters.ENCHANTING, List.of(
                new PlayerBookPageData().withTitle("Enchanting").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "enchanting1.png"))
                        .withLeftPageDesc(Text.of("The greatest artisans of past ages were known to have honed their craft so wonderfully, " +
                                "resulting in storied blades and armour with extraordinary properties.\n\n" +
                                "First, craft the inscription table as follow."))
                        .withRightPageDesc(Text.of("")),
                new PlayerBookPageData().withTitle("Inscription Table").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "enchanting2.png"))
                        .withLeftPageDesc(Text.of("To use the inscription table, you will need gems, a chisel and the item that you wish to enchant. " +
                                "Each gem have unique words associated with it, you can explore each combination of words until you find a valid enchant."))
                        .withRightPageDesc(Text.of("Here's an example of the Unbreaking enchantment inscription;"))
        ));

        chaptersPages.put(PlayerBookChapters.MOUNTS, List.of(
                new PlayerBookPageData().withTitle("Mounts")
                        .withLeftPageDesc(Text.of("The roads of middle earth are long and winding so consider a mount to assist with your travels.\n\n " +
                                "Such companions come in all shapes and sizes, each having their own unique advantages and disadvantages."))
                        .withRightPageDesc(Text.of("Many of those beasts can only be tamed by certain races.")),
                new PlayerBookPageData().withTitle("Broadhoof Goat").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_broadhoof_goat.png"))
                        .withLeftPageDesc(Text.of("The Broadhoof Goat is a great mount for Dwarves that can easily climb mountains.\n" +
                                "You can find them in mountains where Dwarves live, but beware of these fluffy climbers; they can be aggressive if provoked. " +
                                "They may charge with their horns against enemies and jump very high."))
                        .withRightPageDesc(Text.of("To tame them, you must be a dwarf and offer them wheat.")),
                new PlayerBookPageData().withTitle("Great Horn").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_great_horn.png"))
                        .withLeftPageDesc(Text.of("The Great Horn lives in the forests of Anduin and northern Wastelands.\n" +
                                "It's a majestic creature that will flee everyone but elves. " +
                                "Indeed, as an elf, you may tame it with various items, such as clovers, sweet berries, mushrooms, and more."))
                        .withRightPageDesc(Text.of("Great Horns can leap\n        and pierce.")),
                new PlayerBookPageData().withTitle("Warg").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_warg.png"))
                        .withLeftPageDesc(Text.of("The wicked wolves are bred for war and hunting. They attack all good-aligned entities and can be ridden by " +
                                "orcs, snagas, goblins, and uruks.\n To tame this wicked beast, simply give some raw meat."))
                        .withRightPageDesc(Text.of("Wargs can leap and take down other riders from their mounts.")),
                new PlayerBookPageData().withTitle("Cave Troll").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "mount_cave_troll.png"))
                        .withLeftPageDesc(Text.of("The Cave Troll, a fearsome creature that dwells deep in caves. " +
                                "It attacks everyone when hungry, but if you're evil aligned, you may attempt to tame it with chains while it sleeps.\n " +
                                "The Cave Troll can bear up to three riders on his back and be commanded with a bone."))
                        .withRightPageDesc(Text.of("It can smash and charge."))
        ));

        chaptersPages.put(PlayerBookChapters.DUNGEONS, List.of(
                new PlayerBookPageData().withTitle("Dungeons")
                        .withLeftPageDesc(Text.of("Throughout your adventures in middle earth you may encounter a variety of dungeons with great riches to behold.\n " +
                                "Before entering, you should prepare yourself for whatever unfriendly creatures you may find inside, for such bounty is not left unguarded. "))
                        .withRightPageDesc(Text.of("In each dungeon, you shall find trial spawners, that give keys upon defeating the wave of enemies.\n " +
                                "The trial key is used for the treasure vaults that may be found deeper in the dungeon.")),
                new PlayerBookPageData().withTitle("Brigands").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "dungeon_brigands.png"))
                        .withLeftPageDesc(Text.of("Brigands' dungeons can be found all around Anduin’s Vale.\n " +
                                "They hold stolen riches from neighboring factions and hide below ruined towers."))
                        .withRightPageDesc(Text.of("")),
                new PlayerBookPageData().withTitle("Mirkwood Burrows").withImage(Identifier.of(MiddleEarth.MOD_ID, PATH + "dungeon_spider_burrows.png"))
                        .withLeftPageDesc(Text.of("The spiders of Shelob have infested the Mirkwood forest.\n " +
                                "Traveling in that corrupted forest is very dangerous, however you may find forgotten traveler's riches, if you ever return alive."))
                        .withRightPageDesc(Text.of(""))
        ));
    }
}
