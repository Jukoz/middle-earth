package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.MiddleEarth;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.sevenstars.middleearth.client.TrollRiderCamera;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier HALLUCINATION_OUTLINE = Identifier.of(MiddleEarth.MOD_ID, "textures/misc/hallucination_outline.png");

    @Unique
    private static final Identifier COOLDOWN_BACKGROUND = Identifier.ofVanilla("hud/crosshair_attack_indicator_background");
    @Unique
    private static final Identifier COOLDOWN_PROGRESS = Identifier.ofVanilla("hud/crosshair_attack_indicator_progress");

    @Shadow @Final private MinecraftClient client;


    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    //TODO broky
    /*
    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderTickCounter;getLastFrameDuration()F", shift = At.Shift.AFTER))
    public void injected(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            this.renderOverlay(context, HALLUCINATION_OUTLINE, intensity);

        }
    }*/

    // Shown in third person on a troll; TrollRiderCamera makes what it covers the real target
    @WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean showCrosshairOnTroll(Perspective perspective, Operation<Boolean> original) {
        return original.call(perspective) || TrollRiderCamera.isActive(MinecraftClient.getInstance());
    }

    // The vanilla indicator inverts the colours behind it and is a faint grey sliver, so a troll rider gets a bigger one in plain colours
    @Inject(method = "renderCrosshair", at = @At("RETURN"))
    private void drawTrollCooldown(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PlayerEntity player = this.client.player;
        if(player == null || !(player.getVehicle() instanceof CaveTrollEntity troll) || troll.getControllingPassenger() != player) {
            return;
        }

        float progress = player.getAttackCooldownProgress(0.0f);
        if(progress < 1.0f) {
            int x = context.getScaledWindowWidth() / 2 - 16;
            int y = context.getScaledWindowHeight() / 2 + 9;
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, COOLDOWN_BACKGROUND, 16, 4, 0, 0, x, y, 32, 8);
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, COOLDOWN_PROGRESS, 16, 4, 0, 0, x, y, (int)(progress * 34.0f), 8);
        }
    }
}
