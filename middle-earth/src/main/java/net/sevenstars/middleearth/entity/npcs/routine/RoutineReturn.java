package net.sevenstars.middleearth.entity.npcs.routine;

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
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.STORE_STAND_RADIUS;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;

public class RoutineReturn {
    public static final double STORE_REACH = 3.5;

    private static final float STORE_SOUND_VOLUME = 0.4f;

    private static final int SMOKE_PARTICLES = 6;
    private static final double SMOKE_HEIGHT = 0.8;
    private static final double SMOKE_SPREAD = 0.2;
    private static final double SMOKE_SPEED = 0.02;

    private final NpcRoutine engine;
    private final NpcEntity npc;

    private @Nullable BlockPos standOf = null;
    private @Nullable BlockPos storeStand = null;

    public RoutineReturn(NpcRoutine engine) {
        this.engine = engine;
        this.npc = engine.npc;
    }

    private void deliver(ServerWorld world, BlockPos store) {
        if(engine.carried == null)
            return;
        // A camp keeps its things by the fire, so what's brought there goes on the fire
        if(world.getBlockState(store).getBlock() instanceof CampfireBlock) {
            world.spawnParticles(ParticleTypes.SMOKE, store.getX() + 0.5, store.getY() + SMOKE_HEIGHT, store.getZ() + 0.5, SMOKE_PARTICLES, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPEED);
            engine.did("deliver", "put " + itemName(engine.carried) + " on the fire at " + store.toShortString());
            return;
        }
        Inventory inventory = RoutineStores.inventoryAt(world, store);
        if(inventory == null) {
            engine.abandoned("its store " + store.toShortString() + " cannot hold anything");
            return;
        }
        ItemStack left = HopperBlockEntity.transfer(null, inventory, new ItemStack(engine.carried, engine.carriedCount), null);
        engine.site.getStock().changed();
        // A full store still counts as the work done
        engine.did("deliver", left.isEmpty() ? "stored " + itemName(engine.carried) + " in the store at " + store.toShortString()
                : "found the store at " + store.toShortString() + " full, " + itemName(engine.carried) + " stays out");
    }

    public void arriveHome(ServerWorld world, Random random) {
        engine.homeStrikes = 0;
        if(engine.toBed) {
            engine.beds.goToBed(world);
            return;
        }
        if(engine.carried != null) {
            BlockPos store = engine.dropOff();
            if(store == null || engine.canSee(world, store))
                npc.swingHand(Hand.MAIN_HAND);
            if(store != null && store.isWithinDistance(npc.getEntityPos(), STORE_REACH) && engine.canSee(world, store)) {
                world.playSound(null, store, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, STORE_SOUND_VOLUME, 1f);
                deliver(world, store);
            } else if(store != null)
                engine.abandoned("could not put away what it brought, its store " + store.toShortString() + " is out of reach or behind a wall");
        }
        engine.finish(world, REST + random.nextInt(REST_VARIATION));
    }

    // What's brought back goes to the barrel or the fire, and a tired npc goes to its bed
    public @Nullable BlockPos returnTarget(ServerWorld world) {
        if(engine.toBed) {
            BlockPos bed = engine.beds.freeBed(world);
            if(bed != null)
                return npc.getBedStand() != null ? npc.getBedStand() : bed;
        } else if(engine.carried != null && engine.dropOff() != null) {
            return storeStand(world, engine.dropOff());
        }
        return npc.getRoutineHome();
    }

    // The store itself is a block nobody can stand in, and the closest the pathfinder gets to it may be the wrong side
    // of a wall
    private BlockPos storeStand(ServerWorld world, BlockPos store) {
        if(!store.equals(standOf)) {
            standOf = store;
            BlockPos inside = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            storeStand = isLoaded(world, store) ? NestSurvey.standNextTo(world, store, inside) : null;
            // A barrel on a table or in a corner is reached across it, from a floor that can see it
            if(storeStand == null && isLoaded(world, store))
                storeStand = NestSurvey.standInSight(world, store, inside, STORE_STAND_RADIUS);
        }
        return storeStand != null ? storeStand : store;
    }
}
