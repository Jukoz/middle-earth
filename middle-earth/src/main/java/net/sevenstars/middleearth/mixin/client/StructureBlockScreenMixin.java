package net.sevenstars.middleearth.mixin.client;

import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// The structure block screen doesn't explain its buttons and fields, so each one gets a tooltip
@Mixin(StructureBlockScreen.class)
public abstract class StructureBlockScreenMixin extends Screen {
    @Shadow private StructureBlockMode mode;
    @Shadow private TextFieldWidget inputName;
    @Shadow private TextFieldWidget inputPosX;
    @Shadow private TextFieldWidget inputPosY;
    @Shadow private TextFieldWidget inputPosZ;
    @Shadow private TextFieldWidget inputSizeX;
    @Shadow private TextFieldWidget inputSizeY;
    @Shadow private TextFieldWidget inputSizeZ;
    @Shadow private TextFieldWidget inputIntegrity;
    @Shadow private TextFieldWidget inputSeed;
    @Shadow private TextFieldWidget inputMetadata;
    @Shadow private ButtonWidget buttonSave;
    @Shadow private ButtonWidget buttonLoad;
    @Shadow private ButtonWidget buttonRotate0;
    @Shadow private ButtonWidget buttonRotate90;
    @Shadow private ButtonWidget buttonRotate180;
    @Shadow private ButtonWidget buttonRotate270;
    @Shadow private ButtonWidget buttonDetect;
    @Shadow private CyclingButtonWidget<Boolean> ignoreEntitiesButton;
    @Shadow private CyclingButtonWidget<Boolean> strictButton;
    @Shadow private CyclingButtonWidget<BlockMirror> mirrorButton;
    @Shadow private CyclingButtonWidget<Boolean> showAirButton;
    @Shadow private CyclingButtonWidget<Boolean> showBoundingBoxButton;

    protected StructureBlockScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void middleEarth$explainWidgets(CallbackInfo ci) {
        middleEarth$explain(inputName, "name");
        for(ClickableWidget position : new ClickableWidget[]{inputPosX, inputPosY, inputPosZ})
            middleEarth$explain(position, "position");
        for(ClickableWidget size : new ClickableWidget[]{inputSizeX, inputSizeY, inputSizeZ})
            middleEarth$explain(size, "size");
        middleEarth$explain(inputIntegrity, "integrity");
        middleEarth$explain(inputSeed, "seed");
        middleEarth$explain(inputMetadata, "data");
        middleEarth$explain(buttonSave, "save");
        middleEarth$explain(buttonLoad, "load");
        for(ClickableWidget rotation : new ClickableWidget[]{buttonRotate0, buttonRotate90, buttonRotate180, buttonRotate270})
            middleEarth$explain(rotation, "rotation");
        middleEarth$explain(buttonDetect, "detect");
        middleEarth$explain(ignoreEntitiesButton, "entities");
        middleEarth$explain(strictButton, "strict");
        middleEarth$explain(mirrorButton, "mirror");
        middleEarth$explain(showAirButton, "show_air");
        middleEarth$explain(showBoundingBoxButton, "show_box");
        middleEarth$explainMode();
    }

    @Inject(method = "updateWidgets", at = @At("TAIL"))
    private void middleEarth$explainModeAgain(StructureBlockMode mode, CallbackInfo ci) {
        middleEarth$explainMode();
    }

    // The mode button isn't stored in a field of the screen, so find it among the children. Its tooltip explains the current mode
    private void middleEarth$explainMode() {
        for(Element child : children()) {
            if(child instanceof CyclingButtonWidget<?> button && button.getValue() instanceof StructureBlockMode)
                middleEarth$explain(button, "mode." + mode.asString());
        }
    }

    private static void middleEarth$explain(ClickableWidget widget, String key) {
        if(widget != null)
            widget.setTooltip(Tooltip.of(Text.translatable("ui.%s.structure_block.%s".formatted(MiddleEarth.MOD_ID, key))));
    }
}
