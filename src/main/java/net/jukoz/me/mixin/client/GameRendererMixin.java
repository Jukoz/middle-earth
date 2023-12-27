package net.jukoz.me.mixin.client;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyConstant(method="updateTargetedEntity", constant = @Constant(doubleValue = 3.0))
    public double injectedConstant(double constant) {
        return 6.5f;
    }
}
