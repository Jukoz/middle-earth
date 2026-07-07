package net.sevenstars.middleearth.entity.seat;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class SeatRenderer extends EntityRenderer<SeatEntity, SeatEntityRenderState> {

    public SeatRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public SeatEntityRenderState createRenderState() {
        return new SeatEntityRenderState();
    }
}
