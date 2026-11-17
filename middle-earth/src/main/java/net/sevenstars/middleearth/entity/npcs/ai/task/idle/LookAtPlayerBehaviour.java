package net.sevenstars.middleearth.entity.npcs.ai.task.idle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class LookAtPlayerBehaviour extends IdleBehaviour {
    private static final float CHANCE = 0.02F;
    private static final int LOOK_TIME = 40;
    private static final int LOOK_TIME_VARIATION = 40;

    private @Nullable TargetPredicate predicate = null;
    private @Nullable PlayerEntity target = null;
    // In goal ticks
    private int lookTime;

    public LookAtPlayerBehaviour() {
        super(7, false, true);
    }

    @Override
    public boolean canStart(NpcEntity npc) {
        if(npc.getRandom().nextFloat() >= CHANCE)
            return false;
        if(predicate == null) {
            Predicate<Entity> notRiding = EntityPredicates.rides(npc);
            predicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(NpcBrain.LOOK_RANGE).setPredicate((entity, world) -> notRiding.test(entity));
        }
        target = ((ServerWorld) npc.getEntityWorld()).getClosestPlayer(predicate, npc, npc.getX(), npc.getEyeY(), npc.getZ());
        return target != null;
    }

    @Override
    public boolean shouldContinue(NpcEntity npc) {
        if(!target.isAlive())
            return false;
        if(npc.squaredDistanceTo(target) > (double) (NpcBrain.LOOK_RANGE * NpcBrain.LOOK_RANGE))
            return false;
        return lookTime > 0;
    }

    @Override
    public void start(NpcEntity npc) {
        lookTime = MathHelper.ceilDiv(LOOK_TIME + npc.getRandom().nextInt(LOOK_TIME_VARIATION), 2);
    }

    @Override
    public void stop(NpcEntity npc) {
        target = null;
    }

    @Override
    public void tick(NpcEntity npc) {
        if(!target.isAlive())
            return;
        npc.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
        lookTime--;
    }
}
