package net.sevenstars.middleearth.entity.goals;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class FollowHerderGoal extends Goal {
    // Saved with the animal so the herd stays together after a reload
    private static final String TAG_PREFIX = "middle-earth.herder.";
    private static final double START_DISTANCE = 7;
    private static final double STOP_DISTANCE = 3;
    private static final double LOST_DISTANCE = 48;
    // In checks, which run every other tick
    private static final int CHECK_INTERVAL = 20;
    // About a minute without a herder
    private static final int ORPHAN_CHECKS = 30;
    private static final int ORPHAN_PLAYER_RANGE = 32;
    private static final int GOAL_PRIORITY = 2;
    // Multiple of normal speed
    private static final double HERD_SPEED = 1.1;

    private final MobEntity animal;
    private UUID herder;
    private final double speed;
    private int cooldown = 0;
    private int missingChecks = 0;
    private Entity target = null;

    public FollowHerderGoal(MobEntity animal, UUID herder, double speed) {
        this.animal = animal;
        this.herder = herder;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if(!(entity instanceof MobEntity mob))
                return;
            UUID herder = herderOf(mob);
            if(herder != null)
                mob.goalSelector.add(GOAL_PRIORITY, new FollowHerderGoal(mob, herder, HERD_SPEED));
        });
    }

    // The npc herding the animal, or null for a wild one
    public static @Nullable UUID herderOf(Entity animal) {
        for(String tag : animal.getCommandTags()) {
            if(tag.startsWith(TAG_PREFIX)) {
                try {
                    return UUID.fromString(tag.substring(TAG_PREFIX.length()));
                } catch (IllegalArgumentException ignored) {}
            }
        }
        return null;
    }

    // The goal itself is added when the animal enters the world
    public static void assign(MobEntity animal, UUID herder) {
        animal.addCommandTag(TAG_PREFIX + herder);
    }

    // Reassigns one herder's animals to another herder, e.g. when the herder dies
    public static void handOver(ServerWorld world, Entity from, Entity to, double range) {
        UUID fromId = from.getUuid();
        UUID toId = to.getUuid();
        for(MobEntity animal : world.getEntitiesByClass(MobEntity.class, from.getBoundingBox().expand(range), mob -> fromId.equals(herderOf(mob)))) {
            animal.removeCommandTag(TAG_PREFIX + fromId);
            animal.addCommandTag(TAG_PREFIX + toId);
            for(PrioritizedGoal prioritized : animal.goalSelector.getGoals()) {
                if(prioritized.getGoal() instanceof FollowHerderGoal goal)
                    goal.herder = toId;
            }
        }
    }

    @Override
    public boolean canStart() {
        if(--cooldown > 0)
            return false;
        cooldown = CHECK_INTERVAL;
        target = animal.getWorld().getEntity(herder);
        if(target == null || !target.isAlive()) {
            // Herders come and go with the wild spawns, so an animal whose herder is gone is removed too rather than
            // left behind forever
            missingChecks++;
            if(missingChecks >= ORPHAN_CHECKS && !animal.getWorld().isPlayerInRange(animal.getX(), animal.getY(), animal.getZ(), ORPHAN_PLAYER_RANGE)
                    && !animal.isLeashed() && !animal.hasPassengers() && !animal.hasCustomName())
                animal.discard();
            return false;
        }
        missingChecks = 0;
        double distance = animal.squaredDistanceTo(target);
        return distance > START_DISTANCE * START_DISTANCE && distance < LOST_DISTANCE * LOST_DISTANCE;
    }

    @Override
    public boolean shouldContinue() {
        return target != null && target.isAlive() && animal.squaredDistanceTo(target) > STOP_DISTANCE * STOP_DISTANCE && !animal.getNavigation().isIdle();
    }

    @Override
    public void start() {
        animal.getNavigation().startMovingTo(target, speed);
    }

    @Override
    public void stop() {
        animal.getNavigation().stop();
    }
}
