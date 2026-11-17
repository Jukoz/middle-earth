package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

final class LookAtPlayerBehaviour extends Behaviour {
    private static final float RANGE = 8.0F;
    private static final int LOOK_TIME = 40;
    private static final int LOOK_TIME_VARIATION = 40;

    private @Nullable TargetPredicate predicate = null;
    private @Nullable PlayerEntity target = null;
    // In think ticks
    private int lookTime;

    LookAtPlayerBehaviour() {
        super(LOOK_PRIORITY, LOOK);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        if(npc.getRandom().nextFloat() >= LOOK_CHANCE)
            return false;
        if(predicate == null) {
            Predicate<Entity> notRiding = EntityPredicates.rides(npc);
            predicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(RANGE).setPredicate((entity, world) -> notRiding.test(entity));
        }
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
            return false;
        target = world.getClosestPlayer(predicate, npc, npc.getX(), npc.getEyeY(), npc.getZ());
        return target != null;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        if(target == null || !target.isAlive())
            return false;
        if(npc.squaredDistanceTo(target) > (double) (RANGE * RANGE))
            return false;
        return lookTime > 0;
    }

    @Override
    void start(NpcEntity npc) {
        lookTime = MathHelper.ceilDiv(LOOK_TIME + npc.getRandom().nextInt(LOOK_TIME_VARIATION), 2);
    }

    @Override
    void stop(NpcEntity npc) {
        target = null;
    }

    @Override
    void tick(NpcEntity npc) {
        if(target == null || !target.isAlive())
            return;
        npc.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
        lookTime--;
    }
}
