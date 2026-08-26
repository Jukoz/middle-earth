package net.sevenstars.api.entity.ai.brain.task;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;

public class PlaceBlockNearbyTask {
    public static SingleTickTask<PathAwareEntity> create(PlaceBlockNearbyTask.AlternativeCondition condition, MemoryModuleType<GlobalPos> memoryModuleType, Block block) {
        return TaskTriggerer.task(
                context -> context.group(
                                context.queryMemoryAbsent(memoryModuleType)
                        )
                        .apply(
                                context,
                                (memorizedPos) -> (world, entity, time) -> {
                                    if(condition.test()) {
                                        placeBlockAtClosePos(block, world, entity, 2, 5);
                                        return true;
                                    } else {
                                        return true;
                                    }
                                }
                        )
        );
    }

    public static void placeBlockAtClosePos(Block block, ServerWorld world, PathAwareEntity entity, int minRadius, int maxRadius) {
        for(int x = minRadius; x <= maxRadius; x++) {
            for(int z = -(minRadius - 1); z <= maxRadius; z++) {
                if(world.getBlockState(entity.getBlockPos().add(x, 0, z)).isOf(Blocks.AIR)
                        && world.getBlockState(entity.getBlockPos().add(x, -1, z)).isSolidBlock(world, entity.getBlockPos().add(x, -1, z))) {

                    world.setBlockState(entity.getBlockPos().add(x, 0, z), block.getDefaultState());
                    return;
                }
                else if(world.getBlockState(entity.getBlockPos().add(z, 0, x)).isOf(Blocks.AIR)
                        && world.getBlockState(entity.getBlockPos().add(z, -1, x)).isSolidBlock(world, entity.getBlockPos().add(z, -1, x))) {

                    world.setBlockState(entity.getBlockPos().add(z, 0, x), block.getDefaultState());
                    return;
                }
                else if(world.getBlockState(entity.getBlockPos().add(-x, 0, -z)).isOf(Blocks.AIR)
                        && world.getBlockState(entity.getBlockPos().add(-x, -1, -z)).isSolidBlock(world, entity.getBlockPos().add(-x, -1, -z))) {

                    world.setBlockState(entity.getBlockPos().add(-x, 0, -z), block.getDefaultState());
                    return;
                }
                else if(world.getBlockState(entity.getBlockPos().add(-z, 0, -x)).isOf(Blocks.AIR)
                        && world.getBlockState(entity.getBlockPos().add(-z, -1, -x)).isSolidBlock(world, entity.getBlockPos().add(-z, -1, -x))) {

                    world.setBlockState(entity.getBlockPos().add(-z, 0, -x), block.getDefaultState());
                    return;
                }
            }
        }
    }

    @FunctionalInterface
    public interface AlternativeCondition {
        boolean test();
    }
}
