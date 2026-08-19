package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.utils.DamageablePlantsUtil;

public class ThornyGrowthBlock extends GlowLichenBlock {
    public ThornyGrowthBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            double xMovement = Math.abs(livingEntity.getX() - livingEntity.xOld);
            double zMovement = Math.abs(livingEntity.getZ() - livingEntity.zOld);
            if (xMovement >= 0.003000000026077032 || zMovement >= 0.003000000026077032) {
                DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.damageSources().sweetBerryBush());
            }
        }
    }
}
