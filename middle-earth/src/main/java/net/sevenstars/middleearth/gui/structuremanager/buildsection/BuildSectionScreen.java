package net.sevenstars.middleearth.gui.structuremanager.buildsection;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.client2server.PacketStructureBoundsRequest;

import java.util.Locale;

// Asked once two corners were clicked with the builder's tool: whether the box is the new bounds of the section it was
// started in, or a section of its own under a name
public class BuildSectionScreen extends Screen {
    private static final String KEY = "ui." + MiddleEarth.MOD_ID + ".build_section.";
    private static final int WIDTH = 240;
    private static final int ROW = 24;
    private static final int FIELD_HEIGHT = 20;
    private static final int MAXIMUM_NAME_LENGTH = 100;
    private static final int TEXT = 0xFFFFFFFF;
    private static final int HINT = 0xFFA0A0A0;

    private final BlockPos anchor;
    private final BlockPos first;
    private final BlockPos second;
    // Empty when the box was started outside every section
    private final String current;
    private TextFieldWidget name;
    private ButtonWidget create;

    public BuildSectionScreen(BlockPos anchor, BlockPos first, BlockPos second, String current) {
        super(Text.translatable(KEY + "title"));
        this.anchor = anchor;
        this.first = first;
        this.second = second;
        this.current = current;
    }

    @Override
    protected void init() {
        int left = (width - WIDTH) / 2;
        int top = height / 2 - ROW * 2;
        name = addDrawableChild(new TextFieldWidget(textRenderer, left, top + ROW, WIDTH, FIELD_HEIGHT, Text.translatable(KEY + "name")));
        name.setMaxLength(MAXIMUM_NAME_LENGTH);
        name.setText(shown(current));
        name.setChangedListener(typed -> update());
        create = addDrawableChild(ButtonWidget.builder(Text.translatable(KEY + "create"), button -> send(typed()))
                .dimensions(left, top + ROW * 2 + 4, WIDTH, FIELD_HEIGHT).build());
        if(!current.isEmpty())
            addDrawableChild(ButtonWidget.builder(Text.translatable(KEY + "resize", shown(current)), button -> send(""))
                    .dimensions(left, top + ROW * 3 + 4, WIDTH, FIELD_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.cancel"), button -> close())
                .dimensions(left, top + ROW * (current.isEmpty() ? 3 : 4) + 4, WIDTH, FIELD_HEIGHT).build());
        setInitialFocus(name);
        update();
    }

    // The mod's own names are shown and typed without its namespace
    private static String shown(String name) {
        Identifier id = Identifier.tryParse(name);
        return id != null && id.getNamespace().equals(MiddleEarth.MOD_ID) ? id.getPath() : name;
    }

    private String typed() {
        return name.getText().trim().toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    // A new section needs a name that is one, and not that of the section the box was started in
    private void update() {
        String typed = typed();
        create.active = !typed.isEmpty() && !typed.equals(shown(current)) && Identifier.tryParse(typed.contains(":") ? typed : MiddleEarth.MOD_ID + ":" + typed) != null;
    }

    private void send(String section) {
        ClientPlayNetworking.send(new PacketStructureBoundsRequest(anchor, first, second, section));
        close();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if((keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER || keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER) && create.active) {
            send(typed());
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int top = height / 2 - ROW * 2;
        BlockBox box = BlockBox.create(first, second);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, top - ROW, TEXT);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable(KEY + "size", box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ()), width / 2, top - ROW / 2, HINT);
        context.drawTextWithShadow(textRenderer, Text.translatable(KEY + "name"), (width - WIDTH) / 2, top + ROW / 2, HINT);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
