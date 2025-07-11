package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.GlobalPos;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModMemoryModules;

import java.util.List;
import java.util.Optional;

public class DefendHomeTask {
    public static SingleTickTask<LivingEntity> create(double radius) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.ATTACK_TARGET)).apply(context, (attackTarget) -> {
                return (world, entity, time) -> {
                    Optional<LivingEntity> optional = Optional.ofNullable(threatClose(entity, radius));

                    if(optional.isEmpty()) {
                        return false;
                    }
                    attackTarget.remember(optional);
                    return true;
                };
            });
        });
    }


    private static LivingEntity threatClose(LivingEntity entity, double radius) {
        Optional<GlobalPos> optionalHome = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        Optional<List<PlayerEntity>> optionalPlayers = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_PLAYERS);

        if(optionalHome != null && optionalHome.isPresent()) {
            if(optionalPlayers != null && optionalPlayers.isPresent()) {
                for(PlayerEntity player : optionalPlayers.get()) {
                    if(player.getPos().squaredDistanceTo(optionalHome.get().pos().toCenterPos()) < (radius*radius)) {
                        return player;
                    }
                }
            }
        }

        return null;
    }
}
