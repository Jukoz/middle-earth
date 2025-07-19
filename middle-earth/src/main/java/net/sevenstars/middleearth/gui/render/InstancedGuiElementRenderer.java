package net.sevenstars.middleearth.gui.render;

import net.minecraft.client.gui.render.SpecialGuiElementRenderer;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.sevenstars.middleearth.gui.render.states.InstancedGuiElementRenderState;

public abstract class InstancedGuiElementRenderer<T extends InstancedGuiElementRenderState> extends SpecialGuiElementRenderer<T> {
    private boolean usedThisFrame;

    protected InstancedGuiElementRenderer(VertexConsumerProvider.Immediate vertexConsumers) {
        super(vertexConsumers);
    }

    public final boolean usedThisFrame() {
        return this.usedThisFrame;
    }

    public final void resetUsedThisFrame() {
        this.usedThisFrame = false;
    }

    @Override
    public void renderElement(T specialGuiElementRenderState, GuiRenderState guiRenderState) {
        super.renderElement(specialGuiElementRenderState, guiRenderState);
        this.usedThisFrame = true;
    }
}