package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Random;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {

    @ModifyArg(method = "performBonemeal(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
    public int grow(
            int index,
            @Local(ordinal = 0) List<ConfiguredFeature<?, ?>> flowers
    ) {
        Random random = new Random();
        return random.nextInt(flowers.size());
    }
}
