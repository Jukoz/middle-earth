package net.sevenstars.ofhallsandheralds.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import net.sevenstars.ofhallsandheralds.dtos.playerdata.PlayerDataHH;
import net.sevenstars.ofhallsandheralds.dtos.playerdata.PlayerDataHolderHH;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerDataHolderHH {
    private String key = "OfHallsAndHeralds";


    @Unique
    private final PlayerDataHH playerDataHH = new PlayerDataHH();

    @Override
    public PlayerDataHH getPlayerDataHH() {
        return playerDataHH;
    }


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeCustomData(WriteView writeView, CallbackInfo ci) {
        writeView.put(key, PlayerDataHH.CODEC, playerDataHH);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readCustomData(ReadView readView, CallbackInfo ci) {
        readView.read(key, PlayerDataHH.CODEC)
                .ifPresent(playerDataHH::copyFrom);
    }
}
