package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.Bar;
import net.minecraft.client.gui.hud.bar.JumpBar;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.JumpingMount;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Vanilla shows a flat grey bar for the whole cooldown. Beast abilities recharge for several seconds,
// so the grey bar drains away as the cooldown runs out, leaving the empty jump bar when ready.
@Mixin(JumpBar.class)
public abstract class JumpBarMixin {
    @Unique
    private static final Identifier BACKGROUND = Identifier.ofVanilla("hud/jump_bar_background");
    @Unique
    private static final Identifier COOLDOWN = Identifier.ofVanilla("hud/jump_bar_cooldown");

    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private JumpingMount jumpingMount;

    @Inject(method = "renderBar", at = @At("HEAD"), cancellable = true)
    private void renderBeastCooldown(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if(!(this.jumpingMount instanceof AbstractBeastEntity beast) || beast.getJumpCooldown() <= 0) {
            return;
        }

        Bar bar = (Bar)(Object)this;
        int x = bar.getCenterX(this.client.getWindow());
        int y = bar.getCenterY(this.client.getWindow());
        float remaining = (beast.getJumpCooldown() - tickCounter.getTickProgress(true)) / beast.maxChargeCooldown();
        int width = MathHelper.ceil(MathHelper.clamp(remaining, 0.0f, 1.0f) * 182.0f);

        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, 182, 5);
        if(width > 0) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, COOLDOWN, 182, 5, 0, 0, x, y, width, 5);
        }
        ci.cancel();
    }
}
