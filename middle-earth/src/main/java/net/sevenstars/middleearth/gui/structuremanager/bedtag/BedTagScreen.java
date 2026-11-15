package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget.Choice;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Environment(value = EnvType.CLIENT)
public class BedTagScreen extends HandledScreen<BedTagScreenHandler> {
    private static final int LIST_WIDTH = 120;
    private static final int LIST_HEIGHT = 150;
    private static final int ROW_HEIGHT = 16;
    private static final int GAP = 10;
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 20;
    private static final int LABEL_COLOR = 0xFFA0A0A0;

    private ChoiceListWidget nestKindList;
    private ChoiceListWidget jobList;
    private ChoiceListWidget classList;
    private @Nullable String shownNestKind = null;

    public BedTagScreen(BedTagScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        BedTagScreenData data = handler.getData();
        int left = width / 2 - (3 * LIST_WIDTH + 2 * GAP) / 2;
        int top = height / 2 - LIST_HEIGHT / 2;

        List<Choice> nestKinds = new ArrayList<>();
        nestKinds.add(new Choice("", none()));
        for(String nestKind : data.nestKinds())
            nestKinds.add(new Choice(nestKind, ChoiceListWidget.readable(nestKind)));
        nestKindList = newList(nestKinds, data.nestKind(), left, top);

        jobList = newList(List.of(), "", left + LIST_WIDTH + GAP, top);
        shownNestKind = null;
        refreshJobs(data.job());

        // Anybody can be named, for a cell holds whoever was caught. Left alone, the bed is for one of its nest
        List<Choice> classes = new ArrayList<>();
        classes.add(new Choice("", Text.translatable("ui.%s.bed_tag.from_nest".formatted(MiddleEarth.MOD_ID))));
        classes.addAll(choicesOf(DynamicRegistriesME.NPC_CLASS, id -> Text.translatable(id.toTranslationKey("npc_type"))));
        classList = newList(classes, data.npcClass(), left + 2 * (LIST_WIDTH + GAP), top);

        int buttonTop = top + LIST_HEIGHT + GAP;
        addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.bed_tag.save".formatted(MiddleEarth.MOD_ID)), button -> save(false))
                .dimensions(width / 2 - BUTTON_WIDTH / 2 - GAP - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.bed_tag.reset_links".formatted(MiddleEarth.MOD_ID)), button -> save(true))
                .dimensions(width / 2 - BUTTON_WIDTH / 2, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(width / 2 + BUTTON_WIDTH / 2 + GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    // Only the jobs that fit who lives there are offered: a trade for common folk, a duty for those who fight. Left alone,
    // the bed gives the job its nest does
    private void refreshJobs(String current) {
        String nestKind = nestKindList.selectedValue();
        if(nestKind.equals(shownNestKind))
            return;
        String picked = shownNestKind == null ? current : jobList.selectedValue();
        shownNestKind = nestKind;
        List<Choice> jobs = new ArrayList<>();
        jobs.add(new Choice("", Text.translatable("ui.%s.bed_tag.as_nest".formatted(MiddleEarth.MOD_ID))));
        if(!nestKind.isEmpty()) {
            BedTag.Folk folk = BedTag.folkOf(MinecraftClient.getInstance().getNetworkHandler().getRegistryManager(), nestKind);
            for(Choice job : choicesOf(DynamicRegistriesME.NPC_JOB, id -> ChoiceListWidget.readable(id.getPath()))) {
                Identifier id = Identifier.tryParse(job.value());
                if(id != null && NpcJobs.fits(id, folk, nestKind))
                    jobs.add(job);
            }
        }
        jobList.setChoices(jobs, picked);
    }

    private void save(boolean resetLinks) {
        String nestKind = nestKindList.selectedValue();
        handler.save(nestKind, nestKind.isEmpty() ? "" : jobList.selectedValue(), nestKind.isEmpty() ? "" : classList.selectedValue(), resetLinks);
        close();
    }

    private ChoiceListWidget newList(List<Choice> choices, String current, int x, int y) {
        ChoiceListWidget list = new ChoiceListWidget(LIST_WIDTH, LIST_HEIGHT, x, y, ROW_HEIGHT);
        list.setChoices(choices, current);
        addDrawableChild(list.newSearchField(textRenderer));
        return addDrawableChild(list);
    }

    private static <T> List<Choice> choicesOf(RegistryKey<Registry<T>> key, Function<Identifier, Text> label) {
        List<Choice> choices = new ArrayList<>();
        Registry<T> registry = MinecraftClient.getInstance().getNetworkHandler().getRegistryManager().getOptional(key).orElse(null);
        if(registry == null)
            return choices;
        List<Identifier> ids = new ArrayList<>(registry.getIds());
        ids.sort(Comparator.naturalOrder());
        for(Identifier id : ids)
            choices.add(new Choice(id.toString(), label.apply(id)));
        return choices;
    }

    private static Text none() {
        return Text.translatable("ui.%s.bed_tag.none".formatted(MiddleEarth.MOD_ID));
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    // A container screen closes on the inventory key, which is a letter somebody searching wants to type
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode != GLFW.GLFW_KEY_ESCAPE && getFocused() instanceof TextFieldWidget field && field.isFocused()) {
            field.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // A container screen keeps the wheel to itself, and the lists never scrolled. It goes to the list under the mouse
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for(ChoiceListWidget list : new ChoiceListWidget[]{nestKindList, jobList, classList}) {
            if(list != null && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        refreshJobs("");
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        label(context, nestKindList, "type");
        label(context, jobList, "role");
        label(context, classList, "class");
    }

    private void label(DrawContext context, ChoiceListWidget list, String key) {
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ui.%s.bed_tag.%s".formatted(MiddleEarth.MOD_ID, key)),
                list.getX() + LIST_WIDTH / 2, list.labelY(textRenderer), LABEL_COLOR);
    }
}
