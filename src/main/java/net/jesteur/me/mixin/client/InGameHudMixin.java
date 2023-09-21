package net.jesteur.me.mixin.client;

import joptsimple.OptionSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.language.LanguageAdapter;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.barrow_wights.BarrowWightEntity;
import net.jesteur.me.statusEffects.Hallucination;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.chrono.MinguoEra;
import java.util.Collection;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier HALLUCINATION_OUTLINE = new Identifier(MiddleEarth.MOD_ID, "textures/misc/hallucination_outline.png");

    @Shadow @Final private MinecraftClient client;

    LivingEntity lookAt;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getLastFrameDuration()F", shift = At.Shift.AFTER))
    public void injected(DrawContext context, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            float intensity = (float)HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            this.renderOverlay(context, HALLUCINATION_OUTLINE,  intensity);

            if( lookAt != null  && !lookAt.isDead()){
                double dX = lookAt.getX() - player.getX(); // d
                double dY = lookAt.getY() - player.getY(); // e
                double dZ = lookAt.getZ() - player.getZ(); // f
                double g = Math.sqrt(dX * dX + dZ * dZ);

                float destPitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(dY, g) * 57.2957763671875)));
                float destYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(dZ, dX) * 57.2957763671875) - 90.0f);

                destPitch = MathHelper.lerp(MinecraftClient.getInstance().getTickDelta()    , destPitch, player.getPitch());
                destYaw = MathHelper.lerp(MinecraftClient.getInstance().getTickDelta() , destYaw, player.getYaw());
                //player.changeLookDirection(1920/Math.sin(destYaw), 1080/Math.sin(destPitch)); cant figure this out.

                //destPitch = player.getPitch();
                //destYaw = player.getYaw();
                player.setPitch(destPitch);
                player.setYaw(destYaw);
                player.setHeadYaw(player.getYaw());
                player.prevPitch = player.getPitch();
                player.prevYaw = player.getYaw();

                //Vec3d currentLookAt = player.getCameraPosVec(0);
                //MinecraftClient.getInstance().player.sendMessage(Text.literal("Dest Yaw: " + destYaw + " while player: " + player.getYaw()));

            }
            else
                lookAt = player.getWorld().getClosestEntity(BarrowWightEntity.class, TargetPredicate.createNonAttackable(), null, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().expand(12));
        }
        else
            lookAt = null;
    }
}



