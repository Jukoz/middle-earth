package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.DAY;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;

class StationActivity extends RoutineActivity {
    // A station the npc couldn't stand at or see from where it stood is skipped this long
    private static final int STATION_HOLD = 20 * 300;

    private static final int WORK_TIME = 20 * 40;
    private static final int WORK_TIME_VARIATION = 20 * 40;

    // From home. Nobody crosses town to use someone else's forge
    static final int WORK_RANGE = 48;
    private static final int STABLE_RANGE = 12;
    // A hive fills over a day, and its honey is taken once it's been full another
    private static final int FULL_HONEY = 5;
    private static final long HIVE_READY_TIME = 2 * DAY;
    private static final double HORSE_REACH = 4;
    private static final int HORSE_HEIGHT = 8;
    private static final int PLANKS_PER_LOG = 4;

    private static final int STABLE_SWING_ONE_IN = 3;
    private static final int STABLE_SOUND_ONE_IN = 6;
    private static final int STABLE_HEART_ONE_IN = 12;
    private static final int STATION_SWING_ONE_IN = 4;
    private static final int STATION_SOUND_ONE_IN = 3;

    private static final float SPLIT_SOUND_VOLUME = 0.8f;
    private static final float HORSE_SOUND_VOLUME = 0.6f;
    private static final float WORK_SOUND_VOLUME = 0.5f;
    private static final float WORK_SOUND_PITCH = 0.9f;
    private static final float WORK_SOUND_PITCH_VARIATION = 0.2f;

    private static final double HEART_HEIGHT = 0.3;
    private static final double HEART_SPREAD = 0.2;
    private static final double HEART_SPREAD_HEIGHT = 0.1;

    private final Map<BlockPos, Long> unusableStations = new HashMap<>();
    private @Nullable BlockPos workStation = null;
    @Nullable AnimalEntity workAnimal = null;
    @Nullable TradeRecipes.Order order = null;
    private boolean woodNeeded = false;
    @Nullable BlockPos fetchFrom = null;
    @Nullable BlockPos stationStand = null;
    // An oven burns planks split from the woodcutters' logs. A hearth is already a fire
    private int planks = 0;
    @Nullable BlockPos fuelFrom = null;
    @Nullable Item logInHand = null;

    StationActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return site.hasWorkStation();
    }

    @Override
    boolean isPossible(ServerWorld world, RoutineData routineData, BlockPos home, long time) {
        return hasWork(world, routineData, home, time);
    }

    @Override
    void held(long time) {
        holdStation(time);
    }

    @Override
    String label(RoutineData.Activity kind) {
        return "at work";
    }

    void holdStation(long time) {
        if(workStation != null)
            unusableStations.put(workStation, time + STATION_HOLD);
    }

    void clear() {
        fetchFrom = null;
        order = null;
        stationStand = null;
        fuelFrom = null;
        logInHand = null;
        workAnimal = null;
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        RoutineData data = npc.getRoutine();
        long time = world.getTime();
        unusableStations.values().removeIf(until -> until <= time);
        workStation = null;
        workAnimal = null;
        order = TradeRecipes.order(world, routine.site.getStock(), npc.getJobId(), home, WORK_RANGE, npc.getRandom());
        woodNeeded = false;
        if(order == null && needsStock())
            return routine.cannotStart(picked, "nothing in any store for its trade to work with");
        if(isStableWork()) {
            workAnimal = findAnimal(world, home);
            if(workAnimal == null)
                return routine.cannotStart(picked, "no animal to see to");
            BlockPos byHorse = NestSurvey.standNextTo(world, workAnimal.getBlockPos(), npc.getBlockPos());
            routine.setSpot(byHorse != null ? byHorse : workAnimal.getBlockPos());
            return true;
        }
        BlockPos beside = null;
        List<BlockPos> stations = data == null ? List.of() : stationsToWork(data, home);
        boolean held = false;
        for(BlockPos station : stations) {
            if(unusableStations.containsKey(station)) {
                held = true;
                continue;
            }
            if(!isLoaded(world, station))
                continue;
            if(RoutineStores.isHive(world, station) && !hiveReady(world, station, time))
                continue;
            if(order != null && lacksWood(world, station, home))
                continue;
            beside = NestSurvey.standNextTo(world, station, npc.getBlockPos());
            // A forge walled in on every side is for show
            if(beside != null) {
                workStation = station;
                if(RoutineStores.isHive(world, station))
                    syncHive(world, station, time);
                break;
            }
            unusableStations.put(station, time + STATION_HOLD);
        }
        if(beside == null)
            return routine.cannotStart(picked, stations.isEmpty() ? "no station of its trade nearby" : held ? "every station of its trade failed it lately"
                    : "no room to stand at any station of its trade");
        routine.setSpot(beside);
        if(order != null) {
            fetchFrom = order.from();
            stationStand = beside;
            fuelFrom = RoutineStores.isOven(world, workStation) && planks == 0 ? woodSource(world, home) : null;
            woodNeeded = fuelFrom != null;
            routine.setSpot(routine.standAt(world, fetchFrom));
        }
        return true;
    }

    // What the walk ends in sight of: the store for inputs, then the wood pile, then the station.
    @Nullable BlockPos workTarget() {
        return fetchFrom != null ? fetchFrom : fuelFrom != null ? fuelFrom : workStation;
    }

    @Override
    boolean beforeWork(ServerWorld world, Random random, long time) {
        if(fetchFrom != null) {
            arriveAtStore(world);
            return true;
        }
        if(fuelFrom != null) {
            arriveAtWoodPile(world);
            return true;
        }
        if(workStation != null && !routine.canSee(world, workStation)) {
            routine.abandoned("its station " + workStation.toShortString() + " is behind a wall");
            unusableStations.put(workStation, time + STATION_HOLD);
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
            return true;
        }
        return false;
    }

    private void arriveAtStore(ServerWorld world) {
        if(order == null || !RoutineStores.take(world, fetchFrom, order.input(), order.count())) {
            routine.abandoned("what it came for in the store at " + fetchFrom.toShortString() + " is gone");
            routine.site.getStock().changed();
            routine.finish(world, REST);
            return;
        }
        routine.site.getStock().changed();
        routine.carryFromStore(order.input(), order.count(), fetchFrom);
        routine.did("work", "took " + routine.carriedCount() + " " + itemName(routine.carried()) + " from the store at " + fetchFrom.toShortString() + " to make " + itemName(order.output()));
        fetchFrom = null;
        routine.setSpot(fuelFrom != null ? routine.standAt(world, fuelFrom) : stationStand);
        routine.walker.newWalk(world);
        routine.showProp();
    }

    private void arriveAtWoodPile(ServerWorld world) {
        logInHand = RoutineStores.takeFrom(world, fuelFrom, stack -> stack.isIn(ItemTags.LOGS));
        if(logInHand != null)
            routine.did("work", "took " + itemName(logInHand) + " from " + fuelFrom.toShortString() + " for the oven");
        fuelFrom = null;
        routine.setSpot(stationStand);
        routine.walker.newWalk(world);
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.setPhaseEnd(time + WORK_TIME + random.nextInt(WORK_TIME_VARIATION));
        routine.setLookTarget(workStation == null ? null : Vec3d.ofCenter(workStation));
        if(logInHand != null) {
            planks += PLANKS_PER_LOG;
            world.playSound(null, npc.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, SPLIT_SOUND_VOLUME, 1f);
            routine.did("work", "split " + itemName(logInHand) + " into " + PLANKS_PER_LOG + " planks");
            logInHand = null;
        }
        if(woodNeeded && planks == 0) {
            routine.abandoned("has no wood for the oven at " + workStation.toShortString());
            if(routine.carried() != null)
                routine.startReturn(world);
            else
                routine.finish(world, REST);
            return;
        }
        if(workAnimal != null && (!workAnimal.isAlive() || workAnimal.squaredDistanceTo(npc) > HORSE_REACH * HORSE_REACH)) {
            routine.abandoned("the horse it came to see to wandered off");
            routine.finish(world, REST);
        }
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done && workStation != null && RoutineStores.isHive(world, workStation)) {
            BlockState hive = world.getBlockState(workStation);
            world.setBlockState(workStation, hive.with(BeehiveBlock.HONEY_LEVEL, 0));
            routine.site.emptyHive(workStation, time);
            world.playSound(null, workStation, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1f, 1f);
            routine.setCarried(Items.HONEYCOMB);
            routine.did("work", "took the honey from the hive at " + workStation.toShortString());
            routine.startReturn(world);
        } else if(done && order != null && routine.carried() != null) {
            Item used = routine.carried();
            routine.setCarried(order.output());
            if(planks > 0 && workStation != null && RoutineStores.isOven(world, workStation))
                planks--;
            routine.did("work", "made " + itemName(routine.carried()) + " from " + routine.carriedCount() + " " + itemName(used) + " at " + (workStation == null ? "its station" : workStation.toShortString()));
            routine.setCarriedCount(1);
            order = null;
            routine.startReturn(world);
        } else if(done) {
            if(workAnimal != null)
                routine.did("work", "tended the animal at " + workAnimal.getBlockPos().toShortString());
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(workAnimal != null) {
            workAnimal.getNavigation().stop();
            routine.setLookTarget(workAnimal.getEyePos());
            if(random.nextInt(STABLE_SWING_ONE_IN) == 0)
                npc.swingHand(Hand.MAIN_HAND);
            if(random.nextInt(STABLE_SOUND_ONE_IN) == 0)
                world.playSound(null, workAnimal.getBlockPos(), random.nextBoolean() ? SoundEvents.ENTITY_HORSE_EAT : SoundEvents.ENTITY_HORSE_BREATHE, SoundCategory.NEUTRAL, HORSE_SOUND_VOLUME, 1f);
            if(random.nextInt(STABLE_HEART_ONE_IN) == 0)
                world.spawnParticles(ParticleTypes.HEART, workAnimal.getX(), workAnimal.getEyeY() + HEART_HEIGHT, workAnimal.getZ(), 1, HEART_SPREAD, HEART_SPREAD_HEIGHT, HEART_SPREAD, 0);
        } else if(random.nextInt(STATION_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            if(workStation != null && random.nextInt(STATION_SOUND_ONE_IN) == 0)
                world.playSound(null, workStation, RoutineStores.workSound(world.getBlockState(workStation)), SoundCategory.BLOCKS, WORK_SOUND_VOLUME, WORK_SOUND_PITCH + random.nextFloat() * WORK_SOUND_PITCH_VARIATION);
        }
    }

    // Settlement hives have no bees. If the honey isn't what was left, someone changed it, so reckon again
    private boolean hiveReady(ServerWorld world, BlockPos hive, long time) {
        int found = world.getBlockState(hive).get(BeehiveBlock.HONEY_LEVEL);
        long since = time - routine.site.hiveEmptiedAt(hive, time, found, found * DAY / FULL_HONEY);
        routine.site.noteHiveLevel(hive, found);
        return since >= HIVE_READY_TIME;
    }

    // Shows the honey it has grown by the time the npc sets out for it
    private void syncHive(ServerWorld world, BlockPos hive, long time) {
        BlockState state = world.getBlockState(hive);
        int found = state.get(BeehiveBlock.HONEY_LEVEL);
        long since = time - routine.site.hiveEmptiedAt(hive, time, found, found * DAY / FULL_HONEY);
        int level = (int) Math.min(FULL_HONEY, since * FULL_HONEY / DAY);
        if(found != level)
            world.setBlockState(hive, state.with(BeehiveBlock.HONEY_LEVEL, level));
        routine.site.noteHiveLevel(hive, level);
    }

    private boolean needsStock() {
        return TradeRecipes.needsStock(npc.getJobId()) && !routine.site.getStores().isEmpty();
    }

    private @Nullable BlockPos woodSource(ServerWorld world, BlockPos home) {
        SiteStock.Holding logs = routine.site.getStock().nearest(world, home, WORK_RANGE, item -> item.getDefaultStack().isIn(ItemTags.LOGS), 1);
        return logs == null ? null : logs.place();
    }

    // With no wood pile anywhere, the oven counts as already fired
    private boolean lacksWood(ServerWorld world, BlockPos station, BlockPos home) {
        return RoutineStores.isOven(world, station) && planks == 0 && woodSource(world, home) == null
                && !routine.site.stationsNear(List.of(StationKind.WOOD_PILE), home, WORK_RANGE).isEmpty();
    }

    private boolean isStableWork() {
        return NpcJobs.STABLES_MASTER.getValue().equals(npc.getJobId());
    }

    private @Nullable StructureFeatureData.Marker animalMarker(BlockPos home) {
        List<StructureFeatureData.Marker> markers = routine.site.getMarkers(MarkerKind.SPAWN_ANIMAL);
        for(BlockPos link : npc.getLinks()) {
            for(StructureFeatureData.Marker marker : markers) {
                if(marker.pos().equals(link))
                    return marker;
            }
        }
        StructureFeatureData.Marker nearest = null;
        for(StructureFeatureData.Marker marker : markers) {
            if(nearest == null || marker.pos().getSquaredDistance(home) < nearest.pos().getSquaredDistance(home))
                nearest = marker;
        }
        return nearest;
    }

    // Nearest unridden animal of the marker's kind. A marker of any horse covers all of them
    private @Nullable AnimalEntity findAnimal(ServerWorld world, BlockPos home) {
        StructureFeatureData.Marker marker = animalMarker(home);
        Identifier typeId = marker == null ? null : Identifier.tryParse(marker.value());
        EntityType<?> type = typeId == null ? null : Registries.ENTITY_TYPE.getOptionalValue(typeId).orElse(null);
        if(type == null)
            return null;
        boolean horseLike = RoutineGame.isHorseLike(type);
        Vec3d centre = Vec3d.ofCenter(marker.pos());
        List<AnimalEntity> animals = world.getEntitiesByClass(AnimalEntity.class, new Box(marker.pos()).expand(STABLE_RANGE, HORSE_HEIGHT, STABLE_RANGE),
                animal -> animal.isAlive() && !animal.hasPassengers() && !animal.isLeashed() && (animal.getType() == type || horseLike && animal instanceof AbstractHorseEntity));
        AnimalEntity nearest = null;
        for(AnimalEntity animal : animals) {
            if(nearest == null || animal.squaredDistanceTo(centre) < nearest.squaredDistanceTo(centre))
                nearest = animal;
        }
        return nearest;
    }

    private List<BlockPos> stationsToWork(RoutineData data, BlockPos home) {
        List<BlockPos> stations = new ArrayList<>();
        for(BlockPos link : npc.getLinks()) {
            StationKind kind = routine.site.stationKindAt(link);
            if(kind != null && data.getStations().contains(kind))
                stations.add(link);
        }
        for(BlockPos station : routine.site.stationsNear(data.getStations(), home, WORK_RANGE)) {
            if(!stations.contains(station))
                stations.add(station);
        }
        return stations;
    }

    boolean hasWork(ServerWorld world, RoutineData data, BlockPos home, long time) {
        boolean ready = TradeRecipes.order(world, routine.site.getStock(), npc.getJobId(), home, WORK_RANGE, npc.getRandom()) != null;
        if(!ready && needsStock())
            return false;
        if(isStableWork())
            return findAnimal(world, home) != null;
        for(BlockPos station : routine.site.stationsNear(data.getStations(), home, WORK_RANGE)) {
            if(!isLoaded(world, station))
                continue;
            if(ready && lacksWood(world, station, home))
                continue;
            if(!RoutineStores.isHive(world, station) || hiveReady(world, station, time))
                return true;
        }
        return false;
    }
}
