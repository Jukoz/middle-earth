package net.sevenstars.of_beasts_and_wild_things.mixin.farm;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Cow;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantData;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cow.class)
public abstract class CowFarmAnimalVariantMixin {
    @Unique
    private static final EntityDataAccessor<ResourceLocation> WILD_THINGS_VARIANT =
            FarmAnimalVariantData.register(
                    FarmAnimalKind.COW,
                    SynchedEntityData.defineId(Cow.class, FarmAnimalVariantData.serializer())
            );

    @Inject(
            method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/Cow;",
            at = @At("RETURN")
    )
    private void wildThings$inheritFarmAnimalVariant(
            ServerLevel level,
            AgeableMob mate,
            CallbackInfoReturnable<Cow> cir
    ) {
        Cow child = cir.getReturnValue();
        if (child == null || !(mate instanceof Cow)) {
            return;
        }
        Cow self = (Cow) (Object) this;
        FarmAnimalVariantHolder source = self.getRandom().nextBoolean()
                ? (FarmAnimalVariantHolder) self
                : (FarmAnimalVariantHolder) mate;
        ((FarmAnimalVariantHolder) child).wildThings$setFarmVariant(source.wildThings$getFarmVariant());
    }
}
