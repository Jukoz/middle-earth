package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.sevenstars.middleearth.entity.goals.interfaces.Evader;

import java.util.function.Predicate;

public class SmartFleeEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private Evader evader;

    public SmartFleeEntityGoal(PathfinderMob mob, Evader evader, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        this.evader = evader;
    }

    public SmartFleeEntityGoal(PathfinderMob mob, Evader evader, Class<T> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, float distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(mob, fleeFromType, extraInclusionSelector, distance, slowSpeed, fastSpeed, inclusionSelector);
        this.evader = evader;
    }

    public SmartFleeEntityGoal(PathfinderMob fleeingEntity, Evader evader, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
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
