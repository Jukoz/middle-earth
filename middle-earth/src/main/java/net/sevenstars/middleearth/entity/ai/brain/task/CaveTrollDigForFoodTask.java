package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.List;

public class CaveTrollDigForFoodTask extends MultiTickTask<CaveTrollEntity> {
    public CaveTrollDigForFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                100
        );
    }

    @Override
    protected void run(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setScavenging(true);
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setScavenging(false);
        entity.getBrain().remember(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 2400 + entity.getRandom().nextInt(1200));
        List<ItemStack> items = entity.scavengeLootTable.generateLoot(entity.lootWorldContext);
        if(!items.isEmpty()) {
            ItemStack itemStack = items.getFirst();
        }
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        return hasRequiredMemoryState(entity);
    }
}
