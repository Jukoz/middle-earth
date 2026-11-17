package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.sevenstars.middleearth.client.renderer.rings.RingBand;
import net.sevenstars.middleearth.client.renderer.rings.RingBandRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateRingMixin implements RingBandRenderer.RingBandRenderState {
    @Unique
    private @Nullable RingBand middleearth$ringBand;

    @Override
    public @Nullable RingBand middleearth$getRingBand() {
        return middleearth$ringBand;
    }

    @Override
    public void middleearth$setRingBand(@Nullable RingBand band) {
        this.middleearth$ringBand = band;
    }
}
