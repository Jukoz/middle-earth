package net.sevenstars.ofhallsandheralds.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import net.sevenstars.ofhallsandheralds.persistentdatas.playernbt.PlayerRaceData;
import net.sevenstars.ofhallsandheralds.persistentdatas.CustomPlayerDataHolderHH;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class CustomPlayerEntityMixin extends LivingEntity implements CustomPlayerDataHolderHH {
    private String key = "OfHallsAndHeralds";

    @Unique
    private final PlayerRaceData playerDataHH = new PlayerRaceData();

    @Override
    public PlayerRaceData getPlayerDataHH() {
        return playerDataHH;
    }


    protected CustomPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeCustomData(WriteView writeView, CallbackInfo ci) {
        writeView.put(key, PlayerRaceData.CODEC, playerDataHH);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readCustomData(ReadView readView, CallbackInfo ci) {
        readView.read(key, PlayerRaceData.CODEC)
                .ifPresent(playerDataHH::copyFrom);
    }
}
