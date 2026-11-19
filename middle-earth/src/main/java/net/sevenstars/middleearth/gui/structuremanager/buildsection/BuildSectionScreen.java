package net.sevenstars.middleearth.gui.structuremanager.buildsection;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.sevenstars.middleearth.gui.structuremanager.EditorPanel;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.client2server.PacketStructureBoundsRequest;
import net.sevenstars.middleearth.network.packets.client2server.PacketSectionEditRequest;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;


// Opens after two corners are clicked (new box or section). On a section's structure block, rename or remove
public class BuildSectionScreen extends Screen {
    private static final String KEY = "ui." + MiddleEarth.MOD_ID + ".build_section.";
    private static final int WIDTH = 270;
    private static final int FIELD_HEIGHT = 18;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 64;
    private static final int WIDE_BUTTON_WIDTH = 84;
    private static final int GAP = 6;
    private static final int LINE_GAP = 3;

    private final BlockPos anchor;
    private final BlockPos first;
    private final BlockPos second;
    private final String current;
    private final @Nullable BlockPos block;
    private TextFieldWidget name;
    private ButtonWidget create;
    private int windowX;
    private int windowY;
    private int windowHeight;
    private Text said = Text.empty();
    private boolean wrong;

    public BuildSectionScreen(BlockPos anchor, BlockPos first, BlockPos second, String current) {
        super(Text.translatable(KEY + "title"));
        this.anchor = anchor;
        this.first = first;
        this.second = second;
        this.current = current;
        this.block = null;
    }

    public BuildSectionScreen(BuildSections.Section section) {
        super(Text.translatable(KEY + "title"));
        this.anchor = section.block();
        this.first = new BlockPos(section.box().getMinX(), section.box().getMinY(), section.box().getMinZ());
        this.second = new BlockPos(section.box().getMaxX(), section.box().getMaxY(), section.box().getMaxZ());
        this.current = section.name();
        this.block = section.block();
    }

    @Override
    protected void init() {
        windowHeight = EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + textRenderer.fontHeight + LINE_GAP + FIELD_HEIGHT + LINE_GAP + textRenderer.fontHeight
                + EditorPanel.PADDING + EditorPanel.FOOTER_HEIGHT;
        windowX = (width - WIDTH) / 2;
        windowY = (height - windowHeight) / 2;
        int left = windowX + EditorPanel.PADDING;
        int right = windowX + WIDTH - EditorPanel.PADDING;
        int fieldTop = windowY + EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + textRenderer.fontHeight + LINE_GAP;
        name = addDrawableChild(new TextFieldWidget(textRenderer, left, fieldTop, WIDTH - 2 * EditorPanel.PADDING, FIELD_HEIGHT, Text.translatable(KEY + "name")));
        name.setMaxLength(BuildSections.MAXIMUM_NAME_LENGTH);
        name.setText(BuildSections.shortName(current));
        name.setChangedListener(typed -> update());
        int buttonTop = windowY + windowHeight - EditorPanel.FOOTER_HEIGHT + (EditorPanel.FOOTER_HEIGHT - BUTTON_HEIGHT) / 2 + 1;
        create = addDrawableChild(ButtonWidget.builder(Text.translatable(KEY + (block == null ? "create" : "rename")), button -> send(typed()))
                .tooltip(Tooltip.of(Text.translatable(KEY + (block == null ? "create_tip" : "rename_tip"))))
                .dimensions(right - WIDE_BUTTON_WIDTH, buttonTop, WIDE_BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(right - WIDE_BUTTON_WIDTH - GAP - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        if(block != null)
            addDrawableChild(ButtonWidget.builder(Text.translatable(KEY + "remove"), button -> {
                ClientPlayNetworking.send(new PacketSectionEditRequest(block, PacketSectionEditRequest.Edit.REMOVE, ""));
                close();
            }).tooltip(Tooltip.of(Text.translatable(KEY + "remove_tip"))).dimensions(left, buttonTop, WIDE_BUTTON_WIDTH, BUTTON_HEIGHT).build());
        else if(!current.isEmpty())
            addDrawableChild(ButtonWidget.builder(Text.translatable(KEY + "resize"), button -> send(""))
                    .tooltip(Tooltip.of(Text.translatable(KEY + "resize_tip", BuildSections.shortName(current)))).dimensions(left, buttonTop, WIDE_BUTTON_WIDTH, BUTTON_HEIGHT).build());
        setInitialFocus(name);
        update();
    }

    private String typed() {
        return BuildSections.normalised(name.getText());
    }

    // A new section needs a valid name no other section uses, different from the one the box started in.
    private void update() {
        String typed = typed();
        Identifier id = BuildSections.named(typed);
        String why = null;
        if(typed.isEmpty())
            why = "say_empty";
        else if(typed.equals(BuildSections.shortName(current)))
            why = block == null ? "say_same" : "say_unchanged";
        else if(id == null)
            why = "bad_name";
        else if(client != null && client.world != null && BuildSections.near(client.world, anchor).stream().anyMatch(section -> section.name().equals(id.toString())))
            why = "say_taken";
        // Empty or still pre-filled isn't an error, they just haven't typed yet
        wrong = why != null && !why.equals("say_empty") && !why.equals("say_same") && !why.equals("say_unchanged");
        said = why != null ? Text.translatable(KEY + why, BuildSections.shortName(current)) : Text.translatable(KEY + (block == null ? "say_create" : "say_rename"), id.toString());
        create.active = why == null;
        name.setEditableColor(wrong ? EditorPanel.WARNING : EditorPanel.TEXT);
    }

    private void send(String section) {
        if(block != null)
            ClientPlayNetworking.send(new PacketSectionEditRequest(block, PacketSectionEditRequest.Edit.RENAME, section));
        else
            ClientPlayNetworking.send(new PacketStructureBoundsRequest(anchor, first, second, section));
        close();
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        int keyCode = input.key();
        if((keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) && create.active) {
            send(typed());
            return true;
        }
        return super.keyPressed(input);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        BlockBox box = BlockBox.create(first, second);
        EditorPanel.window(context, textRenderer, windowX, windowY, WIDTH, windowHeight, Text.translatable(KEY + (block == null ? "title" : "title_edit")),
                Text.translatable(KEY + "size", box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()));
        EditorPanel.footer(context, windowX, windowY, WIDTH, windowHeight);
        super.render(context, mouseX, mouseY, delta);
        EditorPanel.label(context, textRenderer, Text.translatable(KEY + "name"), name.getX(), name.getY() - LINE_GAP - textRenderer.fontHeight);
        context.drawTextWithShadow(textRenderer, said, name.getX(), name.getBottom() + LINE_GAP, wrong ? EditorPanel.WARNING : EditorPanel.MUTED);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
