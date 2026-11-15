package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget.Choice;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Environment(value = EnvType.CLIENT)
public class BedTagScreen extends HandledScreen<BedTagScreenHandler> {
    private static final int LIST_WIDTH = 140;
    private static final int LIST_HEIGHT = 150;
    private static final int ROW_HEIGHT = 16;
    private static final int GAP = 10;
    private static final int LABEL_GAP = 4;
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 20;
    private static final int LABEL_COLOR = 0xFFA0A0A0;

    private ChoiceListWidget typeList;
    private ChoiceListWidget jobList;

    public BedTagScreen(BedTagScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        BedTagScreenData data = handler.getData();
        int left = width / 2 - LIST_WIDTH - GAP / 2;
        int top = height / 2 - LIST_HEIGHT / 2;

        List<Choice> types = new ArrayList<>();
        types.add(new Choice("", none()));
        for(String type : data.types())
            types.add(new Choice(type, readable(type)));
        typeList = newList(types, data.type(), left, top);

        List<Choice> jobs = new ArrayList<>();
        jobs.add(new Choice("", none()));
        jobs.addAll(jobChoices());
        jobList = newList(jobs, data.job(), left + LIST_WIDTH + GAP, top);

        int buttonTop = top + LIST_HEIGHT + GAP;
        addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.bed_tag.save".formatted(MiddleEarth.MOD_ID)), button -> save(false))
                .dimensions(width / 2 - BUTTON_WIDTH / 2 - GAP - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.bed_tag.reset_links".formatted(MiddleEarth.MOD_ID)), button -> save(true))
                .dimensions(width / 2 - BUTTON_WIDTH / 2, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(width / 2 + BUTTON_WIDTH / 2 + GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    private void save(boolean resetLinks) {
        String type = typeList.selectedValue();
        handler.save(type, type.isEmpty() ? "" : jobList.selectedValue(), resetLinks);
        close();
    }

    private ChoiceListWidget newList(List<Choice> choices, String current, int x, int y) {
        ChoiceListWidget list = new ChoiceListWidget(LIST_WIDTH, LIST_HEIGHT, x, y, ROW_HEIGHT);
        list.setChoices(choices, current);
        return addDrawableChild(list);
    }

    private List<Choice> jobChoices() {
        List<Choice> choices = new ArrayList<>();
        Registry<NpcJob> registry = MinecraftClient.getInstance().getNetworkHandler().getRegistryManager().getOptional(DynamicRegistriesME.NPC_JOB).orElse(null);
        if(registry == null)
            return choices;
        List<Identifier> ids = new ArrayList<>(registry.getIds());
        ids.sort(Comparator.naturalOrder());
        for(Identifier id : ids)
            choices.add(new Choice(id.toString(), readable(id.getPath())));
        return choices;
    }

    private static Text none() {
        return Text.translatable("ui.%s.bed_tag.none".formatted(MiddleEarth.MOD_ID));
    }

    // stables_master is shown as Stables Master
    private static Text readable(String name) {
        StringBuilder text = new StringBuilder();
        for(String word : name.split("_")) {
            if(word.isEmpty())
                continue;
            if(!text.isEmpty())
                text.append(' ');
            text.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1));
        }
        return Text.literal(text.toString());
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    // A container screen keeps the wheel to itself, and the lists never scrolled. It goes to the list under the mouse
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for(ChoiceListWidget list : new ChoiceListWidget[]{typeList, jobList}) {
            if(list != null && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        int labelY = typeList.getY() - textRenderer.fontHeight - LABEL_GAP;
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ui.%s.bed_tag.type".formatted(MiddleEarth.MOD_ID)),
                typeList.getX() + LIST_WIDTH / 2, labelY, LABEL_COLOR);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ui.%s.bed_tag.role".formatted(MiddleEarth.MOD_ID)),
                jobList.getX() + LIST_WIDTH / 2, labelY, LABEL_COLOR);
    }
}
