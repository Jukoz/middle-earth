package net.sevenstars.api.entity.ai.brain.task;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.GlobalPos;

public class ForgetMemorizedPosTask {
    public static SingleTickTask<PathAwareEntity> create(ForgetMemorizedPosTask.AlternativeCondition condition, MemoryModuleType<GlobalPos> memoryModuleType) {
        return TaskTriggerer.task(
                context -> context.group(
                                context.queryMemoryValue(memoryModuleType)
                        )
                        .apply(
                                context,
                                (memorizedPos) -> (world, entity, time) -> {
                                    if(condition.test()) {
                                        memorizedPos.forget();
                                        return true;
                                    } else {
                                        return true;
                                    }
                                }
                        )
        );
    }

    @FunctionalInterface
    public interface AlternativeCondition {
        boolean test();
    }
}
