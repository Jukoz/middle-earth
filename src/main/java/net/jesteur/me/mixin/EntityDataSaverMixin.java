package net.jesteur.me.mixin;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.MiddleEarthClient;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class EntityDataSaverMixin implements IEntityDataSaver {
    private final String KEY = MiddleEarth.MOD_ID;
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(persistentData != null) {
            nbt.put(KEY, persistentData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    protected void readCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains(KEY, 10)) {
            persistentData = nbt.getCompound(KEY);
        }
    }
}
