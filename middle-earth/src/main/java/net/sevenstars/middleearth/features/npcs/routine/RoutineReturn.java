package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.STORE_STAND_RADIUS;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;

class RoutineReturn {
    static final double STORE_REACH = 3.5;

    private static final float STORE_SOUND_VOLUME = 0.4f;

    private static final int SMOKE_PARTICLES = 6;
    private static final double SMOKE_HEIGHT = 0.8;
    private static final double SMOKE_SPREAD = 0.2;
    private static final double SMOKE_SPEED = 0.02;

    private final NpcRoutine routine;
    private final NpcEntity npc;

    private @Nullable BlockPos standOf = null;
    private @Nullable BlockPos storeStand = null;

    RoutineReturn(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    private void deliver(ServerWorld world, BlockPos store) {
        if(routine.carried == null)
            return;
        if(world.getBlockState(store).getBlock() instanceof CampfireBlock) {
            world.spawnParticles(ParticleTypes.SMOKE, store.getX() + 0.5, store.getY() + SMOKE_HEIGHT, store.getZ() + 0.5, SMOKE_PARTICLES, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPEED);
            routine.did("deliver", "put " + itemName(routine.carried) + " on the fire at " + store.toShortString());
            return;
        }
        Inventory inventory = RoutineStores.inventoryAt(world, store);
        if(inventory == null) {
            routine.abandoned("its store " + store.toShortString() + " cannot hold anything");
            return;
        }
        ItemStack left = HopperBlockEntity.transfer(null, inventory, new ItemStack(routine.carried, routine.carriedCount), null);
        routine.site.getStock().changed();
        // A full store still counts as the work done
        routine.did("deliver", left.isEmpty() ? "stored " + itemName(routine.carried) + " in the store at " + store.toShortString()
                : "found the store at " + store.toShortString() + " full, " + itemName(routine.carried) + " stays out");
    }

    void arriveHome(ServerWorld world, Random random) {
        routine.homeStrikes = 0;
        if(routine.toBed) {
            routine.beds.goToBed(world);
            return;
        }
        if(routine.carried != null) {
            BlockPos store = routine.dropOff();
            if(store == null || routine.canSee(world, store))
                npc.swingHand(Hand.MAIN_HAND);
            if(store != null && store.isWithinDistance(npc.getEntityPos(), STORE_REACH) && routine.canSee(world, store)) {
                world.playSound(null, store, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, STORE_SOUND_VOLUME, 1f);
                deliver(world, store);
            } else if(store != null)
                routine.abandoned("could not put away what it brought, its store " + store.toShortString() + " is out of reach or behind a wall");
        }
        routine.finish(world, REST + random.nextInt(REST_VARIATION));
    }

    @Nullable BlockPos returnTarget(ServerWorld world) {
        if(routine.toBed) {
            BlockPos bed = routine.beds.freeBed(world);
            if(bed != null)
                return npc.getBedStand() != null ? npc.getBedStand() : bed;
        } else if(routine.carried != null && routine.dropOff() != null) {
            return storeStand(world, routine.dropOff());
        }
        return npc.getRoutineHome();
    }

    // The pathfinder's closest point to a store may be the wrong side of a wall
    private BlockPos storeStand(ServerWorld world, BlockPos store) {
        if(!store.equals(standOf)) {
            standOf = store;
            BlockPos inside = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            storeStand = isLoaded(world, store) ? NestSurvey.standNextTo(world, store, inside) : null;
            // Reached across a barrel on a table or in a corner, from a floor that can see it
            if(storeStand == null && isLoaded(world, store))
                storeStand = NestSurvey.standInSight(world, store, inside, STORE_STAND_RADIUS);
        }
        return storeStand != null ? storeStand : store;
    }
}
