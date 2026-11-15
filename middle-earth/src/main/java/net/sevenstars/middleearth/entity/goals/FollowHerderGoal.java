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

// Keeps an animal of a herd near the npc that drives it
public class FollowHerderGoal extends Goal {
    // Saved with the animal, so that the herd holds together after a reload
    private static final String TAG_PREFIX = "middle-earth.herder.";
    private static final double START_DISTANCE = 7;
    private static final double STOP_DISTANCE = 3;
    private static final double LOST_DISTANCE = 48;
    // In checks, made every other tick
    private static final int CHECK_INTERVAL = 20;
    // About a minute without a herder
    private static final int ORPHAN_CHECKS = 30;
    private static final int ORPHAN_PLAYER_RANGE = 32;
    private static final int GOAL_PRIORITY = 2;
    // Times the normal speed
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
            for(String tag : mob.getCommandTags()) {
                if(tag.startsWith(TAG_PREFIX)) {
                    try {
                        mob.goalSelector.add(GOAL_PRIORITY, new FollowHerderGoal(mob, UUID.fromString(tag.substring(TAG_PREFIX.length())), HERD_SPEED));
                    } catch (IllegalArgumentException ignored) {}
                    return;
                }
            }
        });
    }

    // Who drives the animal, null for one that runs wild
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

    // Re-ties the animals of one herder to another, as when the herder dies
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
            // The herder comes and goes with the wild spawns, and its animals must not stay behind for ever
            missingChecks++;
            if(missingChecks >= ORPHAN_CHECKS && !animal.getWorld().isPlayerInRange(animal.getX(), animal.getY(), animal.getZ(), ORPHAN_PLAYER_RANGE))
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
