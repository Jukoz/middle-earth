package net.sevenstars.middleearth.gui.render.states;

import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.sevenstars.middleearth.gui.render.InstancedGuiElementRenderer;

public interface InstancedGuiElementRenderState extends SpecialGuiElementRenderState {
    InstancedGuiElementRenderer<? extends InstancedGuiElementRenderState> newRenderer(VertexConsumerProvider.Immediate vertexConsumers);
}
