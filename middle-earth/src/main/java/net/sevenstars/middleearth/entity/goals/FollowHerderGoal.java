package net.sevenstars.middleearth.entity.goals;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

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

    private final MobEntity animal;
    private final UUID herder;
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
                        mob.goalSelector.add(2, new FollowHerderGoal(mob, UUID.fromString(tag.substring(TAG_PREFIX.length())), 1.1));
                    } catch (IllegalArgumentException ignored) {}
                    return;
                }
            }
        });
    }

    // The goal itself is added when the animal enters the world
    public static void assign(MobEntity animal, UUID herder) {
        animal.addCommandTag(TAG_PREFIX + herder);
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
