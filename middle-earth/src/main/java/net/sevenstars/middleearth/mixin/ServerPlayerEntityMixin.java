package net.sevenstars.middleearth.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin extends Player {
    @Shadow @Final public ServerPlayerGameMode gameMode;

    public ServerPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Override
    public boolean isSpectator() {
        return this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if(isCreative() || isSpectator()) {
            if(hasEffect(ModStatusEffects.ENSHROUDED) && getEffect(ModStatusEffects.ENSHROUDED).isInfiniteDuration()){
                forceAddEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 40), this);
            }
            return;
        }

        long currentTick = level().getGameTime();
        if(currentTick % 5 == 0){
            ServerPlayer player = (ServerPlayer) (Object) this;
            PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(player);

            int currentLightLevel = level().getMaxLocalRawBrightness(blockPosition());

            double delversFearStrenght = getAttributeValue(EntityAttributesME.DELVERS_FEAR_STRENGTH);

            if(delversFearStrenght > 0.0 && currentLightLevel < 3 && !level().canSeeSky(blockPosition())) {
                if(data == null) {
                    data = StateSaverAndLoader.getPlayerState(player);
                    if(data == null) return;
                }
                data.addToDelversFearCountInSeconds();

                if(data.getDelversFearCountInSeconds() > delversFearStrenght){
                    addEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, -1));
                    addEffect(new MobEffectInstance(MobEffects.DARKNESS, -1));
                    addEffect(new MobEffectInstance(MobEffects.WEAKNESS, -1));
                }
            } else {
                if(hasEffect(ModStatusEffects.ENSHROUDED) && getEffect(ModStatusEffects.ENSHROUDED).isInfiniteDuration()){
                    forceAddEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                    forceAddEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                    forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);

                }
                if(data != null) {
                    data.resetDelversFearCount();
                }
            }
        }
    }
}
