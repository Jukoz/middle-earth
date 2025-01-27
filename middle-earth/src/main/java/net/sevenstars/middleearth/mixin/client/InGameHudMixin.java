package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.utils.HallucinationData;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier HALLUCINATION_OUTLINE = Identifier.of(MiddleEarth.MOD_ID, "textures/misc/hallucination_outline.png");

    @Shadow @Final private MinecraftClient client;


    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderTickCounter;getLastFrameDuration()F", shift = At.Shift.AFTER))
    public void injected(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            this.renderOverlay(context, HALLUCINATION_OUTLINE, intensity);

        }
    }
}



