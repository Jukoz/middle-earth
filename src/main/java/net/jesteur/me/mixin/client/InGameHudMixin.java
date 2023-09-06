package net.jesteur.me.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.statusEffects.Hallucination;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier HALLUCINATION_OUTLINE = new Identifier(MiddleEarth.MOD_ID, "textures/misc/hallucination_outline.png");

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        assert this.client.player != null;
        if(this.client.player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            StatusEffectInstance effect = this.client.player.getStatusEffect(ModStatusEffects.HALLUCINATION);

            float duration = (float)HallucinationData.readHallucination((IEntityDataSaver) this.client.player);
            this.client.player.sendMessage(Text.literal("Duration : " + duration));
            this.renderOverlay(context, HALLUCINATION_OUTLINE, 0.5f / 100);
            ci.cancel();
        }
    }
}



