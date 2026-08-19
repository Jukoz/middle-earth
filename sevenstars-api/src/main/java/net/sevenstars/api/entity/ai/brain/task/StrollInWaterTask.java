package net.sevenstars.api.entity.ai.brain.task;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableLong;

public class StrollInWaterTask {
    public static BehaviorControl<PathfinderMob> create(int range, float speed) {
        MutableLong mutableLong = new MutableLong(0L);
        return BehaviorBuilder.create(
                context -> context.group(
                                context.absent(MemoryModuleType.ATTACK_TARGET),
                                context.absent(MemoryModuleType.WALK_TARGET),
                                context.registered(MemoryModuleType.LOOK_TARGET)
                        )
                        .apply(context, (attackTarget, walkTarget, lookTarget) -> (world, entity, time) -> {
                            if (time < mutableLong.getValue()) {
                                mutableLong.setValue(time + 20L + 2L);
                            } else {
                                BlockPos blockPos = null;
                                BlockPos entityPos = entity.blockPosition();

                                for (BlockPos destinationPos : BlockPos.withinManhattan(entityPos, range, range, range)) {
                                    if (destinationPos.getX() != entityPos.getX() || destinationPos.getZ() != entityPos.getZ()) {
                                        BlockState aboveBlock = entity.level().getBlockState(destinationPos.above());
                                        BlockState waterBlock = entity.level().getBlockState(destinationPos);
                                        if (waterBlock.is(Blocks.WATER)) {
                                            if (aboveBlock.isAir() && entity.getRandom().nextDouble() < 0.1) {
                                                blockPos = destinationPos.immutable();
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (blockPos != null) {
                                    lookTarget.set(new BlockPosTracker(blockPos));
                                    walkTarget.set(new WalkTarget(new BlockPosTracker(blockPos), speed, 0));
                                }

                                mutableLong.setValue(time + 40L);
                            }
                            return true;
                        })
        );
    }
}
