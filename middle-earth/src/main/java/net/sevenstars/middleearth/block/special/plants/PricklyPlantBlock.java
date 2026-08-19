package net.sevenstars.middleearth.block.special.plants;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.utils.DamageablePlantsUtil;

public class PricklyPlantBlock extends CustomPlantBlock {
    public PricklyPlantBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            double xMovement = Math.abs(entity.getX() - entity.xOld);
            double zMovement = Math.abs(entity.getZ() - entity.zOld);
            if (xMovement >= 0.003 || zMovement >= 0.003) {
                DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.damageSources().cactus());
            }
        }
    }
}
