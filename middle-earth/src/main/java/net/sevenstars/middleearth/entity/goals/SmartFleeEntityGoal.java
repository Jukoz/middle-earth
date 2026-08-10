package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.sevenstars.middleearth.entity.goals.interfaces.Evader;

import java.util.function.Predicate;

public class SmartFleeEntityGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
    private Evader evader;

    public SmartFleeEntityGoal(PathAwareEntity mob, Evader evader, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        this.evader = evader;
    }

    public SmartFleeEntityGoal(PathAwareEntity mob, Evader evader, Class<T> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, float distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(mob, fleeFromType, extraInclusionSelector, distance, slowSpeed, fastSpeed, inclusionSelector);
        this.evader = evader;
    }

    public SmartFleeEntityGoal(PathAwareEntity fleeingEntity, Evader evader, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(fleeingEntity, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
        this.evader = evader;
    }

    @Override
    public void start() {
        super.start();
        evader.startFlee();
    }

    @Override
    public void stop() {
        super.stop();
        evader.stopFlee();
    }
}
