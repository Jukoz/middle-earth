package net.sevenstars.middleearth.enchantments.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record MiningEnchantmentEffect(EnchantmentLevelBasedValue value) implements EnchantmentEntityEffect {

    public static final MapCodec<MiningEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(MiningEnchantmentEffect::value)
            ).apply(instance, MiningEnchantmentEffect::new)
    );


    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        PlayerEntity player = (PlayerEntity) user;
        BlockPos blockPos = BlockPos.ofFloored(pos);
        world.getBlockState(blockPos);
        world.breakBlock(blockPos.up(), true, player);
        world.breakBlock(blockPos.down(), true, player);
    }

    @Override
    public MapCodec<? extends MiningEnchantmentEffect> getCodec() {
        return CODEC;
    }
}
