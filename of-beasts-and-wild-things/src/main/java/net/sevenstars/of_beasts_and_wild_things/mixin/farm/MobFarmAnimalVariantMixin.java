package net.sevenstars.of_beasts_and_wild_things.mixin.farm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantData;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobFarmAnimalVariantMixin implements FarmAnimalVariantHolder {
    @Unique
    private static final String WILD_THINGS_VARIANT_NBT = "wild_things:variant";
    @Unique
    private static final String WILD_THINGS_LEGACY_VARIANT_NBT = "variant";

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void wildThings$defineFarmAnimalVariant(SynchedEntityData.Builder builder, CallbackInfo ci) {
        FarmAnimalKind kind = FarmAnimalKind.of((Mob) (Object) this);
        if (kind != null) {
            builder.define(
                    FarmAnimalVariantData.accessor(kind),
                    FarmAnimalVariants.TEMPERATE
            );
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void wildThings$saveFarmAnimalVariant(CompoundTag tag, CallbackInfo ci) {
        if (FarmAnimalKind.of((Mob) (Object) this) != null) {
            tag.putString(WILD_THINGS_VARIANT_NBT, this.wildThings$getFarmVariant().toString());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void wildThings$loadFarmAnimalVariant(CompoundTag tag, CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        FarmAnimalKind kind = FarmAnimalKind.of(self);
        if (kind == null) {
            return;
        }
        String key;
        if (tag.contains(WILD_THINGS_VARIANT_NBT, Tag.TAG_STRING)) {
            key = WILD_THINGS_VARIANT_NBT;
        } else if (tag.contains(WILD_THINGS_LEGACY_VARIANT_NBT, Tag.TAG_STRING)) {
            key = WILD_THINGS_LEGACY_VARIANT_NBT;
        } else {
            return;
        }
        ResourceLocation variant = ResourceLocation.tryParse(tag.getString(key));
        if (variant != null && FarmAnimalVariants.contains(kind, variant)) {
            this.wildThings$setFarmVariant(variant);
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    private void wildThings$selectFarmAnimalVariant(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType spawnType,
            SpawnGroupData spawnGroupData,
            CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        Mob self = (Mob) (Object) this;
        if (FarmAnimalKind.of(self) != null) {
            this.wildThings$setFarmVariant(
                    FarmAnimalVariants.select(self, level, self.blockPosition(), level.getRandom())
            );
        }
    }

    @Override
    public ResourceLocation wildThings$getFarmVariant() {
        Mob self = (Mob) (Object) this;
        FarmAnimalKind kind = FarmAnimalKind.of(self);
        if (kind == null) {
            return FarmAnimalVariants.TEMPERATE;
        }
        return self.getEntityData().get(FarmAnimalVariantData.accessor(kind));
    }

    @Override
    public void wildThings$setFarmVariant(ResourceLocation variant) {
        Mob self = (Mob) (Object) this;
        FarmAnimalKind kind = FarmAnimalKind.of(self);
        if (kind != null) {
            self.getEntityData().set(FarmAnimalVariantData.accessor(kind), variant);
        }
    }
}
