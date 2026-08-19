package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.item.WeaponItemsME;

import java.util.List;

public class CaveTrollDigForFoodTask extends Behavior<CaveTrollEntity> {
    public CaveTrollDigForFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),
                100
        );
    }

    @Override
    protected void start(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setScavenging(true);
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void stop(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setScavenging(false);
        entity.getBrain().setMemory(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 2400 + entity.getRandom().nextInt(1200));
        entity.getBrain().setMemory(MemoryModulesME.ACTION_TIMEOUT, 200);

        List<ItemStack> items = entity.scavengeLootTable.getRandomItems(entity.lootWorldContext);

        if(!items.isEmpty()) {
            ItemStack itemStack = items.getFirst();
            entity.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
        }

    }

    @Override
    protected boolean canStillUse(ServerLevel world, CaveTrollEntity entity, long time) {
        return hasRequiredMemories(entity);
    }
}
