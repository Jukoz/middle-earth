package net.sevenstars.middleearth.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.sevenstars.middleearth.utils.LootModifiers;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingHook.class)
public abstract class FishingBobberEntityMixin extends Projectile {

    public FishingBobberEntityMixin(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyArg(
            method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ReloadableServerRegistries$Holder;getLootTable(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/storage/loot/LootTable;"
            )
    )
    public ResourceKey<LootTable> onUseFishingRod(ResourceKey<LootTable> key) {
        if(!level().isClientSide) {
            if(ModDimensions.isInMiddleEarth(level())) {
                return LootModifiers.FISHING_LOOT_TABLE;
            }
        }
        return key;
    }
}
