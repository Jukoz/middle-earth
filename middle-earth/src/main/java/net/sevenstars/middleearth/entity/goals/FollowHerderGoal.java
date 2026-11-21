package net.sevenstars.middleearth.entity.goals;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class FollowHerderGoal extends Goal {
    // Saved with the animal so the herd stays together after a reload
    private static final AttachmentType<UUID> HERDER = AttachmentRegistry.create(MiddleEarth.id("herder"), builder -> builder.persistent(Uuids.CODEC));
    // Herders used to be stored as a command tag
    private static final String LEGACY_TAG_PREFIX = "middle-earth.herder.";
    private static final double START_DISTANCE = 7;
    private static final double STOP_DISTANCE = 3;
    private static final double LOST_DISTANCE = 48;
    // In checks, run every other tick
    private static final int CHECK_INTERVAL = 20;
    private static final int ORPHAN_CHECKS = 30;
    private static final int ORPHAN_PLAYER_RANGE = 32;
    private static final int GOAL_PRIORITY = 2;
    private static final double HERD_SPEED = 1.1;

    private final MobEntity animal;
    private UUID herder;
    private final double speed;
    private int cooldown = 0;
    private int missingChecks = 0;
    private Entity target = null;
    private boolean released = false;

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
            migrateLegacyTag(mob);
            UUID herder = herderOf(mob);
            if(herder != null)
                mob.goalSelector.add(GOAL_PRIORITY, new FollowHerderGoal(mob, herder, HERD_SPEED));
        });
    }

    public static @Nullable UUID herderOf(Entity animal) {
        UUID herder = animal.getAttached(HERDER);
        return herder != null ? herder : legacyHerderOf(animal);
    }

    // The goal is added when the animal enters the world
    public static void assign(MobEntity animal, UUID herder) {
        animal.setAttached(HERDER, herder);
    }

    private static @Nullable UUID legacyHerderOf(Entity animal) {
        for(String tag : animal.getCommandTags()) {
            if(tag.startsWith(LEGACY_TAG_PREFIX)) {
                try {
                    return UUID.fromString(tag.substring(LEGACY_TAG_PREFIX.length()));
                } catch (IllegalArgumentException ignored) {}
            }
        }
        return null;
    }

    private static void removeLegacyTag(Entity animal) {
        UUID legacy = legacyHerderOf(animal);
        if(legacy != null)
            animal.removeCommandTag(LEGACY_TAG_PREFIX + legacy);
    }

    private static void migrateLegacyTag(Entity animal) {
        UUID legacy = legacyHerderOf(animal);
        if(legacy == null)
            return;
        if(animal.getAttached(HERDER) == null)
            animal.setAttached(HERDER, legacy);
        removeLegacyTag(animal);
    }

    private static void release(Entity animal) {
        animal.removeAttached(HERDER);
        removeLegacyTag(animal);
    }

    public static void handOver(ServerWorld world, Entity from, Entity to, double range) {
        UUID fromId = from.getUuid();
        UUID toId = to.getUuid();
        for(MobEntity animal : world.getEntitiesByClass(MobEntity.class, from.getBoundingBox().expand(range), mob -> fromId.equals(herderOf(mob)))) {
            removeLegacyTag(animal);
            animal.setAttached(HERDER, toId);
            for(PrioritizedGoal prioritized : animal.goalSelector.getGoals()) {
                if(prioritized.getGoal() instanceof FollowHerderGoal goal)
                    goal.herder = toId;
            }
        }
    }

    private static boolean isOwned(MobEntity animal) {
        return animal instanceof TameableEntity tameable && tameable.isTamed()
                || animal instanceof AbstractHorseEntity horse && horse.isTame()
                || animal instanceof AbstractDonkeyEntity donkey && donkey.hasChest()
                || !animal.getEquippedStack(EquipmentSlot.SADDLE).isEmpty();
    }

    @Override
    public boolean canStart() {
        if(released)
            return false;
        if(--cooldown > 0)
            return false;
        cooldown = CHECK_INTERVAL;
        if(isOwned(animal)) {
            release(animal);
            released = true;
            return false;
        }
        target = animal.getEntityWorld().getEntity(herder);
        if(target == null || !target.isAlive()) {
            // Herders come and go with wild spawns, so an animal whose herder is gone goes too
            missingChecks++;
            if(missingChecks >= ORPHAN_CHECKS && !animal.getEntityWorld().isPlayerInRange(animal.getX(), animal.getY(), animal.getZ(), ORPHAN_PLAYER_RANGE)
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
