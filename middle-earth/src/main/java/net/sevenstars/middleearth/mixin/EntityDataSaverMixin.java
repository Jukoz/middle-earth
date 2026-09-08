package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class EntityDataSaverMixin implements IEntityDataSaver {
    private final String KEY = MiddleEarth.getModId();  // FIXME: might be a better way
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    /*@Inject(method = "writeCustomData", at = @At("HEAD"))
    protected void writeCustomDataToNbt(WriteView view, CallbackInfo ci) {
        if(persistentData != null) {
            view.putString(KEY, persistentData);
        }
    }

    @Inject(method = "readCustomData", at = @At("HEAD"))
    protected void readCustomDataToNbt(ReadView view, CallbackInfo ci) {
        if(view.contains(KEY)) {
            persistentData = view.getString(KEY).get();
        }
    }*/

}
