package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.mutable.MutableLong;

public class StrollInWaterTask {
    public static Task<PathAwareEntity> create(int range, float speed) {
        MutableLong mutableLong = new MutableLong(0L);
        return TaskTriggerer.task(
                context -> context.group(
                                context.queryMemoryAbsent(MemoryModuleType.ATTACK_TARGET),
                                context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET),
                                context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET)
                        )
                        .apply(context, (attackTarget, walkTarget, lookTarget) -> (world, entity, time) -> {
                            if (time < mutableLong.getValue()) {
                                mutableLong.setValue(time + 20L + 2L);
                            } else {
                                BlockPos blockPos = null;
                                BlockPos entityPos = entity.getBlockPos();

                                for (BlockPos destinationPos : BlockPos.iterateOutwards(entityPos, range, range, range)) {
                                    if (destinationPos.getX() != entityPos.getX() || destinationPos.getZ() != entityPos.getZ()) {
                                        BlockState aboveBlock = entity.getWorld().getBlockState(destinationPos.up());
                                        BlockState waterBlock = entity.getWorld().getBlockState(destinationPos);
                                        if (waterBlock.isOf(Blocks.WATER)) {
                                            if (aboveBlock.isAir() && entity.getRandom().nextDouble() < 0.1) {
                                                blockPos = destinationPos.toImmutable();
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (blockPos != null) {
                                    lookTarget.remember(new BlockPosLookTarget(blockPos));
                                    walkTarget.remember(new WalkTarget(new BlockPosLookTarget(blockPos), speed, 0));
                                }

                                mutableLong.setValue(time + 40L);
                            }
                            return true;
                        })
        );
    }
}
