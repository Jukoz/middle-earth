package net.sevenstars.of_beasts_and_wild_things.mixin.farm;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Chicken;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantData;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chicken.class)
public abstract class ChickenFarmAnimalVariantMixin {
    @Unique
    private static final EntityDataAccessor<String> WILD_THINGS_VARIANT =
            FarmAnimalVariantData.register(
                    FarmAnimalKind.CHICKEN,
                    SynchedEntityData.defineId(Chicken.class, EntityDataSerializers.STRING)
            );

    @Inject(
            method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/Chicken;",
            at = @At("RETURN")
    )
    private void wildThings$inheritFarmAnimalVariant(
            ServerLevel level,
            AgeableMob mate,
            CallbackInfoReturnable<Chicken> cir
    ) {
        Chicken child = cir.getReturnValue();
        if (child == null || !(mate instanceof Chicken)) {
            return;
        }
        Chicken self = (Chicken) (Object) this;
        FarmAnimalVariantHolder source = self.getRandom().nextBoolean()
                ? (FarmAnimalVariantHolder) self
                : (FarmAnimalVariantHolder) mate;
        ((FarmAnimalVariantHolder) child).wildThings$setFarmVariant(source.wildThings$getFarmVariant());
    }
}
