package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineGame;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineStores;
import net.sevenstars.middleearth.entity.npcs.routine.SiteStock;
import net.sevenstars.middleearth.entity.npcs.routine.TradeRecipes;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.DAY;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;

public class StationActivity extends RoutineActivity {
    // A station the npc couldn't stand at, or couldn't see from where it stood, is skipped for this long
    private static final int STATION_HOLD = 20 * 300;

    private static final int WORK_TIME = 20 * 40;
    private static final int WORK_TIME_VARIATION = 20 * 40;

    // Distance from home. Nobody crosses the whole town to use someone else's forge
    public static final int WORK_RANGE = 48;
    private static final int STABLE_RANGE = 12;
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
    // The animal a stable master is tending
    public @Nullable AnimalEntity workAnimal = null;
    // A trade that works from the place's stock first fetches its inputs from a store, works them at the station, then
    // takes what it made back
    public @Nullable TradeRecipes.Order order = null;
    private boolean woodNeeded = false;
    public @Nullable BlockPos fetchFrom = null;
    public @Nullable BlockPos stationStand = null;
    // An oven burns planks split from the logs the woodcutters bring in. A hearth is already a fire
    private int planks = 0;
    public @Nullable BlockPos fuelFrom = null;
    public @Nullable Item logInHand = null;

    public StationActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        RoutineData routine = npc.getRoutine();
        long time = world.getTime();
        unusableStations.values().removeIf(until -> until <= time);
        workStation = null;
        workAnimal = null;
        order = TradeRecipes.order(world, engine.site.getStock(), npc.getJobId(), home, WORK_RANGE, npc.getRandom());
        woodNeeded = false;
        if(order == null && needsStock())
            return engine.cannotStart(picked, "nothing in any store for its trade to work with");
        // A stable master works on the animals at its spawn_animal marker
        if(isStableWork()) {
            workAnimal = findAnimal(world, home);
            if(workAnimal == null)
                return engine.cannotStart(picked, "no animal to see to");
            BlockPos byHorse = NestSurvey.standNextTo(world, workAnimal.getBlockPos(), npc.getBlockPos());
            engine.spot = byHorse != null ? byHorse : workAnimal.getBlockPos();
            return true;
        }
        BlockPos beside = null;
        List<BlockPos> stations = routine == null ? List.of() : stationsToWork(routine, home);
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
                break;
            }
            unusableStations.put(station, time + STATION_HOLD);
        }
        if(beside == null)
            return engine.cannotStart(picked, stations.isEmpty() ? "no station of its trade nearby" : held ? "every station of its trade failed it lately"
                    : "no room to stand at any station of its trade");
        engine.spot = beside;
        if(order != null) {
            fetchFrom = order.from();
            stationStand = beside;
            fuelFrom = RoutineStores.isOven(world, workStation) && planks == 0 ? woodSource(world, home) : null;
            woodNeeded = fuelFrom != null;
            engine.spot = engine.standAt(world, fetchFrom);
        }
        return true;
    }

    // What the walk of a trade ends in sight of. That's the store its inputs are fetched from, then the wood pile, then
    // the station. None for a stable master, who walks up to an animal
    public @Nullable BlockPos workTarget() {
        return fetchFrom != null ? fetchFrom : fuelFrom != null ? fuelFrom : workStation;
    }

    @Override
    public boolean beforeWork(ServerWorld world, Random random, long time) {
        // What the order needs has been fetched, and goes on to the station
        if(fetchFrom != null) {
            arriveAtStore(world);
            return true;
        }
        // And a log for the oven, from the wood pile
        if(fuelFrom != null) {
            arriveAtWoodPile(world);
            return true;
        }
        // Nothing is worked on the far side of a wall
        if(workStation != null && !engine.canSee(world, workStation)) {
            engine.abandoned("its station " + workStation.toShortString() + " is behind a wall");
            unusableStations.put(workStation, time + STATION_HOLD);
            engine.finish(world, REST + random.nextInt(REST_VARIATION));
            return true;
        }
        return false;
    }

    private void arriveAtStore(ServerWorld world) {
        if(order == null || !RoutineStores.take(world, fetchFrom, order.input(), order.count())) {
            engine.abandoned("what it came for in the store at " + fetchFrom.toShortString() + " is gone");
            engine.site.getStock().changed();
            engine.finish(world, REST);
            return;
        }
        engine.site.getStock().changed();
        engine.carried = order.input();
        engine.carriedCount = order.count();
        engine.did("work", "took " + engine.carriedCount + " " + itemName(engine.carried) + " from the store at " + fetchFrom.toShortString() + " to make " + itemName(order.output()));
        engine.dropOff = fetchFrom;
        fetchFrom = null;
        engine.spot = fuelFrom != null ? engine.standAt(world, fuelFrom) : stationStand;
        engine.walker.newWalk(world);
        engine.showProp();
    }

    private void arriveAtWoodPile(ServerWorld world) {
        logInHand = RoutineStores.takeFrom(world, fuelFrom, stack -> stack.isIn(ItemTags.LOGS));
        if(logInHand != null)
            engine.did("work", "took " + itemName(logInHand) + " from " + fuelFrom.toShortString() + " for the oven");
        fuelFrom = null;
        engine.spot = stationStand;
        engine.walker.newWalk(world);
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + WORK_TIME + random.nextInt(WORK_TIME_VARIATION);
        engine.lookTarget = workStation == null ? null : Vec3d.ofCenter(workStation);
        if(logInHand != null) {
            planks += PLANKS_PER_LOG;
            world.playSound(null, npc.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, SPLIT_SOUND_VOLUME, 1f);
            engine.did("work", "split " + itemName(logInHand) + " into " + PLANKS_PER_LOG + " planks");
            logInHand = null;
        }
        if(woodNeeded && planks == 0) {
            engine.abandoned("has no wood for the oven at " + workStation.toShortString());
            // What was fetched to be cooked goes back to its store
            if(engine.carried != null)
                engine.startReturn(world);
            else
                engine.finish(world, REST);
            return;
        }
        if(workAnimal != null && (!workAnimal.isAlive() || workAnimal.squaredDistanceTo(npc) > HORSE_REACH * HORSE_REACH)) {
            engine.abandoned("the horse it came to see to wandered off");
            engine.finish(world, REST);
        }
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(done && workStation != null && RoutineStores.isHive(world, workStation)) {
            BlockState hive = world.getBlockState(workStation);
            world.setBlockState(workStation, hive.with(BeehiveBlock.HONEY_LEVEL, 0));
            engine.site.emptyHive(workStation, time);
            world.playSound(null, workStation, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1f, 1f);
            engine.carried = Items.HONEYCOMB;
            engine.did("work", "took the honey from the hive at " + workStation.toShortString());
            engine.startReturn(world);
        } else if(done && order != null && engine.carried != null) {
            Item used = engine.carried;
            engine.carried = order.output();
            if(planks > 0 && workStation != null && RoutineStores.isOven(world, workStation))
                planks--;
            engine.did("work", "made " + itemName(engine.carried) + " from " + engine.carriedCount + " " + itemName(used) + " at " + (workStation == null ? "its station" : workStation.toShortString()));
            engine.carriedCount = 1;
            order = null;
            engine.startReturn(world);
        } else if(done) {
            if(workAnimal != null)
                engine.did("work", "tended the animal at " + workAnimal.getBlockPos().toShortString());
            engine.finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(workAnimal != null) {
            // Brushed and fed where it stands
            workAnimal.getNavigation().stop();
            engine.lookTarget = workAnimal.getEyePos();
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

    // Fills the hive as time passes since its honey was last taken, which is due once the hive has been full for a
    // whole day. The hives of a settlement have no bees to fill them. Where the honey in one isn't what was left in it,
    // somebody else took some or bees brought some, and it's reckoned again from what is there
    private boolean hiveReady(ServerWorld world, BlockPos hive, long time) {
        BlockState state = world.getBlockState(hive);
        int found = state.get(BeehiveBlock.HONEY_LEVEL);
        long since = time - engine.site.hiveEmptiedAt(hive, time, found, found * DAY / FULL_HONEY);
        int level = (int) Math.min(FULL_HONEY, since * FULL_HONEY / DAY);
        if(found != level)
            world.setBlockState(hive, state.with(BeehiveBlock.HONEY_LEVEL, level));
        engine.site.noteHiveLevel(hive, level);
        return since >= HIVE_READY_TIME;
    }

    // A trade that lives off the place's stock can't work without it. Where a place has no stores at all there is
    // nothing to share, and its people go through the motions at their stations
    private boolean needsStock() {
        return TradeRecipes.needsStock(npc.getJobId()) && !engine.site.getStores().isEmpty();
    }

    // The nearest wood pile or store of the place with a log in it
    private @Nullable BlockPos woodSource(ServerWorld world, BlockPos home) {
        SiteStock.Holding logs = engine.site.getStock().nearest(world, home, WORK_RANGE, item -> item.getDefaultStack().isIn(ItemTags.LOGS), 1);
        return logs == null ? null : logs.place();
    }

    // An oven burns the wood the woodcutters bring in. Where nobody keeps a wood pile it's taken to be fired already
    private boolean lacksWood(ServerWorld world, BlockPos station, BlockPos home) {
        return RoutineStores.isOven(world, station) && planks == 0 && woodSource(world, home) == null
                && !engine.site.stationsNear(List.of(StationKind.WOOD_PILE), home, WORK_RANGE).isEmpty();
    }

    private boolean isStableWork() {
        return NpcJobs.STABLES_MASTER.getValue().equals(npc.getJobId());
    }

    // The spawn_animal marker a stable master tends. That's one it's linked to, or else the nearest to home
    private @Nullable StructureFeatureData.Marker animalMarker(BlockPos home) {
        List<StructureFeatureData.Marker> markers = engine.site.getMarkers(MarkerKind.SPAWN_ANIMAL);
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

    // The nearest animal of the marker's kind near it that nobody is riding. A marker of any horse covers all of them
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

    // The links of the bed that are stations of the trade, visited before any other, and then the stations near home
    private List<BlockPos> stationsToWork(RoutineData routine, BlockPos home) {
        List<BlockPos> stations = new ArrayList<>();
        for(BlockPos link : npc.getLinks()) {
            StationKind kind = engine.site.stationKindAt(link);
            if(kind != null && routine.getStations().contains(kind))
                stations.add(link);
        }
        for(BlockPos station : engine.site.stationsNear(routine.getStations(), home, WORK_RANGE)) {
            if(!stations.contains(station))
                stations.add(station);
        }
        return stations;
    }

    // A station of its trade to work at, a hive only once its honey is due, and for a stable master an animal
    public boolean hasWork(ServerWorld world, RoutineData routine, BlockPos home, long time) {
        boolean ready = TradeRecipes.order(world, engine.site.getStock(), npc.getJobId(), home, WORK_RANGE, npc.getRandom()) != null;
        if(!ready && needsStock())
            return false;
        if(isStableWork())
            return findAnimal(world, home) != null;
        for(BlockPos station : engine.site.stationsNear(routine.getStations(), home, WORK_RANGE)) {
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
