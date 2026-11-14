package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.WorldEvents;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.registries.content.forage.ForageTables;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Sends an npc out to fish, hunt, forage or patrol, then back home with what it got
public class NpcRoutineGoal extends Goal {
    private enum Phase { NONE, GOING, WORKING, HUNTING, RETURNING, SLEEPING }

    // Delays are in checks, made every other tick
    private static final int IDLE_DELAY = 100;
    private static final int IDLE_DELAY_VARIATION = 60;
    private static final int WAITING_DELAY = 10;
    // Goal ticks between two looks at the world, 10 a second
    private static final int THINK_INTERVAL = 5;

    private static final int PLAYER_RANGE = 48;
    private static final int SPOT_RANGE = 64;
    private static final int WILD_HOME_RANGE = 64;
    // A settled npc further from home than this walks back before anything else
    private static final int STRAY_DISTANCE = 24;
    private static final double ARRIVAL_DISTANCE = 2.5;
    private static final int HOME_DISTANCE = 12;
    private static final int DRIFT_DISTANCE = 48;
    private static final double BED_REACH = 6;
    private static final double BED_HEIGHT_REACH = 5;
    private static final double STORE_REACH = 3.5;
    private static final double STORE_ARRIVAL_DISTANCE = 1.5;

    private static final int DIRECT_DISTANCE = 18;
    private static final int LEG_LENGTH = 14;
    // In degrees
    private static final float[] DETOURS = {0, 25, -25, 50, -50};
    private static final float[] AROUND_LEFT = {90, 60, 120};
    private static final float[] AROUND_RIGHT = {-90, -60, -120};
    private static final int AROUND_LENGTH = 8;
    private static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_LEGS = 30;
    private static final int MAXIMUM_FAILURES = 3;
    private static final int MINIMUM_PROGRESS = 2;
    // Looks at the world in a row, two seconds of them, without the npc getting any further along its path
    private static final int WATCH_THINKS = 8;
    private static final int MAXIMUM_BACK_OFF = 5;

    // In ticks
    private static final int ROUTINE_TIME_LIMIT = 20 * 60 * 5;
    private static final int REST = 20 * 20;
    private static final int REST_VARIATION = 20 * 40;
    private static final int NOTHING_TO_DO_REST = 20 * 120;
    private static final int FISHING_TIME = 20 * 30;
    private static final int FISHING_TIME_VARIATION = 20 * 30;
    private static final int FORAGING_TIME = 20 * 15;
    private static final int FORAGING_TIME_VARIATION = 20 * 10;
    // Long enough to look over the hunting ground for game
    private static final int STALKING_TIME = 20 * 60;
    private static final int STALKING_TIME_VARIATION = 20 * 60;
    private static final int HUNT_ROAM = 10;
    private static final int FARMING_TIME = 20 * 90;
    private static final int FARMING_TIME_VARIATION = 20 * 60;
    // From where the farmer stands to the plot it works
    private static final double FIELD_REACH = 3;
    private static final int CHASE_TIME = 20 * 30;
    private static final int WATCH_TIME = 20 * 3;
    private static final int WATCH_TIME_VARIATION = 20 * 4;
    private static final int MEETING_TIME = 20 * 20;
    private static final int MEETING_TIME_VARIATION = 20 * 20;
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;
    private static final int ESCORT_RANGE = 24;
    private static final int WORK_TIME = 20 * 40;
    private static final int WORK_TIME_VARIATION = 20 * 40;
    // From home. Nobody crosses the whole town to somebody else's forge
    private static final int WORK_RANGE = 48;
    private static final int VISIT_SEAT_RANGE = 10;
    private static final int TEA_TIME = 20 * 45;
    private static final int TEA_TIME_VARIATION = 20 * 45;
    // Time of day
    private static final int DUSK = 12500;
    private static final int DAWN = 23500;
    private static final int COMPANION_RANGE = 6;
    // Only those it can see: nobody talks to, or sets out with, somebody on the other side of a wall
    private static final TargetPredicate COMPANIONS = TargetPredicate.createNonAttackable();

    private static final int PREY_RANGE = 24;
    private static final int PREY_HEIGHT_RANGE = 6;
    // Leaves the last animal of a herd alone
    private static final int MINIMUM_HERD = 2;
    private static final int BYSTANDER_DISTANCE = 5;
    private static final List<Item> DEFAULT_FORAGE = List.of(Items.BROWN_MUSHROOM, Items.SWEET_BERRIES, Items.APPLE);

    private final NpcEntity npc;
    private final double speed;

    private int delay;
    private int thinkCooldown = 0;
    private Phase phase = Phase.NONE;
    private RoutineData.Activity activity = RoutineData.Activity.PATROL;
    private @Nullable RoutineSite site = null;
    private BlockPos spot = BlockPos.ORIGIN;
    private @Nullable Vec3d lookTarget = null;
    private @Nullable Item carried = null;
    private long phaseEnd = 0;
    private long deadline = 0;
    private long restUntil = 0;
    private int legs = 0;
    private int failures = 0;
    private double lastLegDistance = 0;
    private @Nullable Path watchedPath = null;
    private int watchedNode = 0;
    private double watchedDistance = 0;
    private int watchedThinks = 0;
    // Grows each time a routine has to be abandoned, so that an npc with no way out stops trying
    private int backOff = 0;
    private int ringIndex = 0;
    private int ringStep = 1;
    private int ringLeft = 0;
    private int laps = 0;
    private boolean ownsRod = false;
    private boolean toBed = false;
    private @Nullable NpcEntity companion = null;
    private @Nullable BlockPos meetSeat = null;
    private @Nullable BlockPos workStation = null;
    private @Nullable BlockPos fieldCell = null;
    private long farmingEnd = 0;
    // Plots seen to be behind a fence or a wall from where they were to be worked, passed over for the rest of the day
    private final Set<BlockPos> hiddenPlots = new HashSet<>();
    // Going back with no task behind it, after straying or at the end of the day
    private boolean homeward = false;
    private @Nullable BlockPos standOf = null;
    private @Nullable BlockPos storeStand = null;

    public NpcRoutineGoal(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        // Spreads the first check of each npc over half a minute
        this.delay = 20 + Math.floorMod(npc.getId() * 31, 300);
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        // Also picks up an npc that was saved asleep
        if(npc.isSleeping()) {
            phase = Phase.SLEEPING;
            return true;
        }
        if(--delay > 0)
            return false;
        delay = WAITING_DELAY;
        if(!(npc.getWorld() instanceof ServerWorld world))
            return false;

        if(phase == Phase.HUNTING)
            return finishHunt(world);
        if(phase == Phase.SLEEPING)
            phase = Phase.NONE;
        boolean seated = npc.getVehicle() instanceof SeatEntity;
        // Saved at the table, and the meeting it sat down for is forgotten
        if(seated && phase == Phase.NONE)
            standUp();
        if(npc.getTarget() != null || (npc.hasVehicle() && !seated))
            return false;
        if(phase != Phase.NONE) {
            if(world.getTime() > deadline && phase != Phase.RETURNING) {
                abandoned("ran out of time");
                startReturn(world);
            }
            return true;
        }

        delay = IDLE_DELAY + npc.getRandom().nextInt(IDLE_DELAY_VARIATION);
        // An escort stays with its ward until that one is home again, or lost
        if(npc.isEscorting()) {
            boolean stillOut = npc.getPartyLeader() != null && world.getEntity(npc.getPartyLeader()) instanceof NpcEntity ward && ward.isAlive() && ward.isOnOuting();
            if(stillOut)
                return false;
            npc.endEscort();
        }
        if(npc.getPartyLeader() != null || npc.getTravelHeading() != null)
            return false;
        long time = world.getTime();
        // Wandering takes settled npcs out of their village for good, seen or not
        if(drifted(world)) {
            carried = null;
            homeward = true;
            startReturn(world);
            return true;
        }
        RoutineData routine = npc.getRoutine();
        if(routine == null)
            return false;
        // Where a wild npc first stands is its camp, before wandering takes it elsewhere
        if(npc.getRoutineHome() == null && npc.getStructureManagerHostPos() == null)
            npc.setRoutineHome(npc.getBlockPos());
        // Keeps a village from setting out all at once when its chunks load
        if(restUntil == 0)
            restUntil = time + npc.getRandom().nextInt(REST + REST_VARIATION);
        if(time < restUntil)
            return false;
        // Bed time comes whether anyone is watching or not
        BlockPos restingHome = npc.getStructureManagerHostPos() == null ? null : npc.getRoutineHome();
        if(restingHome != null && npc.isRestTime()) {
            if(!RoutineSites.takeStart(world)) {
                delay = WAITING_DELAY + npc.getRandom().nextInt(WAITING_DELAY);
                return false;
            }
            askHousehold(world);
            // Without a bed, staying around the house is all the rest there is
            if(freeBed(world) == null && restingHome.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
                restUntil = time + NOTHING_TO_DO_REST;
                return false;
            }
            carried = null;
            toBed = true;
            homeward = true;
            startReturn(world);
            return true;
        }
        if(!world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE))
            return false;
        if(!RoutineSites.takeStart(world)) {
            delay = WAITING_DELAY + npc.getRandom().nextInt(WAITING_DELAY);
            return false;
        }

        if(!findSite(world))
            return false;
        site.touch(time);
        site.claim(npc.getFactionIdentifier());
        if(!site.isReady()) {
            delay = WAITING_DELAY * 2;
            return false;
        }

        BlockPos home = npc.getRoutineHome();
        askHousehold(world);
        RoutineData.Activity picked = null;
        if(routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift())) {
            picked = routine.pick(npc.getRandom(), activity -> site.supports(activity)
                        && (activity != RoutineData.Activity.WORK || site.pickStation(routine.getStations(), home, WORK_RANGE) != null));
        }
        else if(npc.isElf() && !npc.isRestTime())
            // Elves need little sleep, and keep company through the night
            picked = routine.pick(npc.getRandom(), activity -> isSocial(activity) && site.supports(activity));
        if(picked == RoutineData.Activity.ESCORT) {
            findWard(world);
            return false;
        }
        if(picked != null && begin(world, picked, home))
            return true;

        restUntil = time + NOTHING_TO_DO_REST;
        // Off duty, a settled npc that drifted away goes home
        if(npc.getStructureManagerHostPos() != null && !home.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            carried = null;
            homeward = true;
            startReturn(world);
            return true;
        }
        return false;
    }

    private boolean drifted(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(managerPos == null)
            return false;
        BlockPos home = npc.getRoutineHome();
        if(home == null) {
            if(!world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4))
                return false;
            BlockPos nest = world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager ? manager.findNestOrigin(npc.getUuid()) : null;
            home = nest != null ? nest : managerPos;
            npc.setRoutineHome(home);
        }
        return !home.isWithinDistance(npc.getBlockPos(), DRIFT_DISTANCE);
    }

    private boolean findSite(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(managerPos != null) {
            if(site == null)
                site = RoutineSites.forManager(world, managerPos);
            return site != null;
        }

        BlockPos home = npc.getRoutineHome();
        if(home == null || !home.isWithinDistance(npc.getBlockPos(), WILD_HOME_RANGE)) {
            home = npc.getBlockPos();
            npc.setRoutineHome(home);
            site = null;
        }
        if(site == null)
            site = RoutineSites.forWild(world, home, npc.getFactionIdentifier());
        return site != null;
    }

    private boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        lookTarget = null;
        carried = null;
        switch (picked) {
            case FISH -> {
                int index = pickNear(site.getShoreStands(), home, SPOT_RANGE + site.getExtent(), random);
                if(index < 0)
                    return cannotStart(picked, "no shore within reach of home");
                spot = site.getShoreStands().get(index);
                lookTarget = Vec3d.ofCenter(site.getShoreWater(index));
            }
            case HUNT, FORAGE -> {
                int index = pickNear(site.getGrounds(), home, SPOT_RANGE + site.getExtent(), random);
                if(index < 0)
                    return cannotStart(picked, "no open ground within reach of home");
                spot = site.getGrounds().get(index);
            }
            case VISIT -> {
                BlockPos household = site.pickHousehold(home, random);
                if(household == null)
                    return cannotStart(picked, "no other household to call on");
                // To the table if there is room at it, or else just in at the door
                meetSeat = site.pickSeatNear(world, household, VISIT_SEAT_RANGE);
                BlockPos beside = NestSurvey.standNextTo(world, meetSeat != null ? meetSeat : household, npc.getBlockPos());
                spot = beside != null ? beside : household;
            }
            case WORK -> {
                RoutineData routine = npc.getRoutine();
                workStation = routine == null ? null : site.pickStation(routine.getStations(), home, WORK_RANGE);
                BlockPos beside = workStation == null ? null : NestSurvey.standNextTo(world, workStation, npc.getBlockPos());
                // A forge walled in on every side is for show
                if(beside == null)
                    return cannotStart(picked, workStation == null ? "no station of its trade nearby" : "no room to stand at its station " + workStation.toShortString());
                spot = beside;
            }
            case TABLE -> {
                meetSeat = site.pickSeat(world, random);
                if(meetSeat == null)
                    return cannotStart(picked, "no free seat at any table");
                BlockPos beside = NestSurvey.standNextTo(world, meetSeat, npc.getBlockPos());
                spot = beside != null ? beside : meetSeat;
            }
            case FARM -> {
                hiddenPlots.clear();
                fieldCell = nextFieldCell(world, home, npc.getBlockPos());
                if(fieldCell == null)
                    return cannotStart(picked, "no field near home needs work");
                spot = fieldCell.up();
                farmingEnd = world.getTime() + FARMING_TIME + random.nextInt(FARMING_TIME_VARIATION);
            }
            case POST -> spot = home;
            case PATROL -> {
                List<BlockPos> ring = site.getRing();
                ringIndex = 0;
                for(int i = 1; i < ring.size(); i++) {
                    if(ring.get(i).getSquaredDistance(npc.getBlockPos()) < ring.get(ringIndex).getSquaredDistance(npc.getBlockPos()))
                        ringIndex = i;
                }
                laps = 0;
                ringStep = (npc.getUuid().hashCode() & 1) == 0 ? 1 : -1;
                ringLeft = ring.size();
                spot = ring.get(ringIndex);
            }
        }
        activity = picked;
        homeward = false;
        // Only those with a home behind walls are worth seeing out and back
        npc.setOnOuting(npc.getStructureManagerHostPos() != null
                && (picked == RoutineData.Activity.FORAGE || picked == RoutineData.Activity.HUNT || picked == RoutineData.Activity.FISH));
        ownsRod = npc.getMainHandStack().getItem() instanceof FishingRodItem;
        phase = Phase.GOING;
        deadline = world.getTime() + ROUTINE_TIME_LIMIT;
        return true;
    }

    private static int pickNear(List<BlockPos> spots, BlockPos home, int range, Random random) {
        int start = random.nextInt(Math.max(1, spots.size()));
        for(int i = 0; i < spots.size(); i++) {
            int index = (start + i) % spots.size();
            if(spots.get(index).isWithinDistance(home, range))
                return index;
        }
        return -1;
    }

    @Override
    public boolean shouldContinue() {
        // Goals keep running for a sleeping mob, so this one has to hold it in bed
        if(phase == Phase.SLEEPING)
            return npc.isSleeping() && npc.getTarget() == null;
        return (phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING)
                && npc.getTarget() == null && (!npc.hasVehicle() || npc.getVehicle() instanceof SeatEntity);
    }

    @Override
    public void start() {
        legs = 0;
        failures = 0;
        thinkCooldown = 0;
        showProp();
    }

    @Override
    public void stop() {
        if(phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING) {
            LivingEntity foe = npc.getTarget();
            abandoned(foe != null ? "interrupted by a fight with " + foe.getType().getName().getString()
                    : npc.hasVehicle() && !(npc.getVehicle() instanceof SeatEntity) ? "interrupted, it is riding" : "interrupted by another goal, such as fleeing");
        }
        npc.getNavigation().stop();
        standUp();
        // Hands are needed back when a fight interrupts
        npc.clearRoutineProp();
        if(npc.isSleeping())
            npc.wakeUp();
        if(phase == Phase.SLEEPING)
            phase = Phase.NONE;
        delay = WAITING_DELAY;
    }

    @Override
    public void tick() {
        if(phase == Phase.WORKING && lookTarget != null)
            npc.getLookControl().lookAt(lookTarget.x, lookTarget.y, lookTarget.z);
        if(--thinkCooldown > 0)
            return;
        thinkCooldown = THINK_INTERVAL;
        if(!(npc.getWorld() instanceof ServerWorld world))
            return;

        long time = world.getTime();
        if(phase == Phase.SLEEPING) {
            if(!npc.isRestTime())
                finish(world, 0);
            return;
        }
        if(time > deadline && phase != Phase.RETURNING) {
            abandoned("ran out of time");
            startReturn(world);
            return;
        }

        switch (phase) {
            case GOING -> travel(world, spot);
            case RETURNING -> travel(world, returnTarget(world));
            case WORKING -> work(world, time);
            default -> {}
        }
    }

    private void travel(ServerWorld world, @Nullable BlockPos target) {
        if(target == null) {
            abandoned("has no home to go back to");
            finish(world, REST);
            return;
        }
        // The usual distance reaches through a wall, and a store on its far side was filled from the street
        boolean storing = phase == Phase.RETURNING && !toBed && carried != null && npc.getStore() != null;
        boolean sitting = phase == Phase.GOING && meetSeat != null;
        boolean working = phase == Phase.GOING && activity == RoutineData.Activity.WORK;
        // Close enough to a bed is where the bed can be seen from, not a step from it through the wall
        BlockPos bed = phase == Phase.RETURNING && toBed ? freeBed(world) : null;
        boolean bedding = bed != null;
        if(target.isWithinDistance(npc.getPos(), storing || sitting || working || bedding ? STORE_ARRIVAL_DISTANCE : ARRIVAL_DISTANCE)
                && (!bedding || canSeeBed(world, bed))) {
            arrive(world);
            return;
        }
        // A path that cannot be followed keeps the navigation busy without the npc getting anywhere
        if(isGettingAlong()) {
            watchedThinks = 0;
        } else if(++watchedThinks >= WATCH_THINKS) {
            watchedThinks = 0;
            String heldUp = heldUpAt(world);
            npc.getNavigation().stop();
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                giveUp(world, "stuck on the way to " + target.toShortString() + heldUp);
                return;
            }
        }
        if(!npc.getNavigation().isIdle() || !RoutineSites.takePath(world))
            return;

        legs++;
        // A stretch that ended no closer was a path that led nowhere
        double distance = target.getSquaredDistance(npc.getPos());
        boolean stalled = legs > 1 && distance > lastLegDistance - MINIMUM_PROGRESS * MINIMUM_PROGRESS;
        lastLegDistance = distance;
        if(stalled && phase == Phase.RETURNING && distance < HOME_DISTANCE * HOME_DISTANCE) {
            arrive(world);
            return;
        }
        Vec3d destination = legs > MAXIMUM_LEGS ? null : nextLeg(world, target);
        boolean moving = destination != null && npc.getNavigation().startMovingTo(destination.x, destination.y, destination.z, speed);
        if(moving && !stalled) {
            failures = 0;
            return;
        }
        failures++;
        if(legs > MAXIMUM_LEGS || failures >= MAXIMUM_FAILURES)
            giveUp(world, (legs > MAXIMUM_LEGS ? "too far, " + legs + " stretches without arriving at " : "no path to ") + target.toShortString());
    }

    // Further along its path than at the last look, by however little: past another node of it, or nearer the next one.
    // Wading through water or climbing a ladder is slow and still counts. Without a path the stretches see to it
    private boolean isGettingAlong() {
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished()) {
            watchedPath = null;
            return true;
        }
        int node = path.getCurrentNodeIndex();
        Vec3d next = path.getNodePosition(npc);
        // Across the ground only, as bobbing in the water goes up and down without getting anywhere
        double distance = Math.hypot(next.x - npc.getX(), next.z - npc.getZ());
        boolean further = path != watchedPath || node > watchedNode || distance < watchedDistance;
        watchedPath = path;
        watchedNode = node;
        watchedDistance = distance;
        return further;
    }

    // The block the walk was held up at, from the path it was following
    private String heldUpAt(ServerWorld world) {
        net.minecraft.entity.ai.pathing.Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return "";
        net.minecraft.entity.ai.pathing.PathNode next = path.getCurrentNode();
        BlockPos at = new BlockPos(next.x, next.y, next.z);
        return ", held up at " + at.toShortString() + " (" + world.getBlockState(at).getBlock().getName().getString() + ")";
    }

    // Every task given up before it was done goes in the debug log with its reason, to find where the days of npcs go wrong
    private void abandoned(String why) {
        abandoned(toBed ? "bed" : homeward ? "home" : activity.asString(), why);
    }

    private void abandoned(RoutineData.Activity what, String why) {
        abandoned(what.asString(), why);
    }

    private void abandoned(String what, String why) {
        BlockPos pos = npc.getBlockPos();
        NpcType type = npc.getNpcType();
        String who = (type == null ? "npc" : type.getId().getPath()) + (npc.getRoleId() == null ? "" : " [" + npc.getRoleId().getPath() + "]");
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s gave up %s (%s) at %d,%d,%d: %s".formatted(who, what,
                phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), why));
    }

    private boolean cannotStart(RoutineData.Activity what, String why) {
        abandoned(what, "could not start, " + why);
        return false;
    }

    // Long walks are cut into short stretches, a path over the whole way would cost too much
    private @Nullable Vec3d nextLeg(ServerWorld world, BlockPos target) {
        double distanceX = target.getX() + 0.5 - npc.getX();
        double distanceZ = target.getZ() + 0.5 - npc.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        boolean close = distance <= DIRECT_DISTANCE;
        if(close && failures == 0)
            return Vec3d.ofBottomCenter(target);

        // No path to a place close by means a wall in between, and a search aimed straight at the place gives up at the wall.
        // The way in is around the building: first to one hand, and to the other if that leads nowhere
        float[] detours = !close ? DETOURS : failures % 2 == 1 ? AROUND_LEFT : AROUND_RIGHT;
        int length = close ? AROUND_LENGTH : LEG_LENGTH;
        float heading = (float) (MathHelper.atan2(distanceZ, distanceX) * MathHelper.DEGREES_PER_RADIAN);
        for(float detour : detours) {
            float radians = (heading + detour) * MathHelper.RADIANS_PER_DEGREE;
            int x = MathHelper.floor(npc.getX() + MathHelper.cos(radians) * length);
            int z = MathHelper.floor(npc.getZ() + MathHelper.sin(radians) * length);
            if(!world.isChunkLoaded(x >> 4, z >> 4))
                continue;
            BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            if(Math.abs(ground.getY() - npc.getY()) > MAXIMUM_HEIGHT_CHANGE || (site != null && site.isBuilding(x, ground.getY(), z)))
                continue;
            if(world.getFluidState(ground.down()).isEmpty() && SpawnUtil.canSpawn(ground, world, SpawnReason.EVENT))
                return Vec3d.ofBottomCenter(ground);
        }
        // Boxed in, a path straight at the target at least gets closer
        return Vec3d.ofBottomCenter(target);
    }

    private void giveUp(ServerWorld world, String why) {
        boolean walkedSome = activity == RoutineData.Activity.PATROL && ringLeft > 1 && (laps > 0 || ringLeft < site.getRing().size());
        abandoned((phase != Phase.RETURNING && walkedSome ? "skipped a stretch of its round, " : toBed ? "could not reach its bed, " : "") + why);
        if(phase != Phase.RETURNING && walkedSome) {
            // A blocked stretch of the round is skipped
            ringLeft--;
            nextWaypoint();
            return;
        }
        if(phase == Phase.RETURNING && toBed) {
            goToBed(world);
        } else if(phase == Phase.RETURNING) {
            // A bed behind a wall cannot be stood on, the doorstep is home enough
            BlockPos home = npc.getRoutineHome();
            if(home == null || !home.isWithinDistance(npc.getBlockPos(), HOME_DISTANCE))
                backOff = Math.min(backOff + 1, MAXIMUM_BACK_OFF);
            finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
        } else {
            backOff = Math.min(backOff + 1, MAXIMUM_BACK_OFF);
            startReturn(world);
        }
    }

    private void arrive(ServerWorld world) {
        Random random = npc.getRandom();
        long time = world.getTime();
        npc.getNavigation().stop();
        if(phase == Phase.RETURNING) {
            if(toBed) {
                goToBed(world);
                return;
            }
            if(carried != null) {
                BlockPos store = npc.getStore();
                if(store == null || canSee(world, store))
                    npc.swingHand(Hand.MAIN_HAND);
                if(store != null && store.isWithinDistance(npc.getPos(), STORE_REACH) && canSee(world, store))
                    world.playSound(null, store, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 0.4f, 1f);
                else if(store != null)
                    abandoned("could not put away what it brought, its store " + store.toShortString() + " is out of reach or behind a wall");
            }
            finish(world, REST + random.nextInt(REST_VARIATION));
            return;
        }

        // Nothing is worked on the far side of a wall
        if(activity == RoutineData.Activity.WORK && workStation != null && !canSee(world, workStation)) {
            abandoned("its station " + workStation.toShortString() + " is behind a wall");
            finish(world, REST + random.nextInt(REST_VARIATION));
            return;
        }
        phase = Phase.WORKING;
        backOff = 0;
        switch (activity) {
            case FISH -> phaseEnd = time + FISHING_TIME + random.nextInt(FISHING_TIME_VARIATION);
            case FORAGE -> phaseEnd = time + FORAGING_TIME + random.nextInt(FORAGING_TIME_VARIATION);
            case HUNT -> phaseEnd = time + STALKING_TIME + random.nextInt(STALKING_TIME_VARIATION);
            // Walking on to the next plot is still the same day in the fields
            case FARM -> phaseEnd = farmingEnd;

            case WORK -> {
                phaseEnd = time + WORK_TIME + random.nextInt(WORK_TIME_VARIATION);
                lookTarget = workStation == null ? null : Vec3d.ofCenter(workStation);
            }
            case VISIT, TABLE -> {
                // Somebody quicker may have taken the seat, the table is still the place to be
                boolean seated = meetSeat != null && canSee(world, meetSeat) && SeatBlock.seat(world, meetSeat, npc);
                phaseEnd = seated ? time + TEA_TIME + random.nextInt(TEA_TIME_VARIATION) : time + MEETING_TIME + random.nextInt(MEETING_TIME_VARIATION);
                companion = null;
                meetSeat = null;
            }
            case POST -> {
                phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
                double awayX = npc.getX() - site.getCenterX();
                double awayZ = npc.getZ() - site.getCenterZ();
                lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
            }
            case PATROL -> {
                phaseEnd = time + WATCH_TIME + random.nextInt(WATCH_TIME_VARIATION);
                // Watches the land outside
                double awayX = npc.getX() - site.getCenterX();
                double awayZ = npc.getZ() - site.getCenterZ();
                lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
            }
        }
    }

    private void work(ServerWorld world, long time) {
        Random random = npc.getRandom();
        boolean done = time >= phaseEnd;
        switch (activity) {
            case FISH -> {
                if(done) {
                    if(random.nextInt(5) != 0)
                        carried = random.nextBoolean() ? Items.COD : Items.SALMON;
                    else
                        abandoned("caught nothing");
                    startReturn(world);
                } else if(lookTarget != null && random.nextInt(12) == 0) {
                    npc.swingHand(Hand.MAIN_HAND);
                    world.spawnParticles(ParticleTypes.SPLASH, lookTarget.x, lookTarget.y + 0.5, lookTarget.z, 6, 0.2, 0, 0.2, 0);
                    world.playSound(null, lookTarget.x, lookTarget.y, lookTarget.z, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.NEUTRAL, 0.2f, 1f);
                }
            }
            case WORK -> {
                if(done) {
                    finish(world, REST + random.nextInt(REST_VARIATION));
                } else if(random.nextInt(4) == 0) {
                    npc.swingHand(Hand.MAIN_HAND);
                    if(workStation != null && random.nextInt(3) == 0)
                        world.playSound(null, workStation, workSound(world.getBlockState(workStation)), SoundCategory.BLOCKS, 0.5f, 0.9f + random.nextFloat() * 0.2f);
                }
            }
            case FORAGE -> {
                if(done) {
                    List<Item> forage = forage(world);
                    carried = forage.get(random.nextInt(forage.size()));
                    startReturn(world);
                } else if(random.nextInt(3) == 0) {
                    npc.swingHand(Hand.MAIN_HAND);
                } else if(random.nextInt(4) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
                    npc.getNavigation().startMovingTo(spot.getX() + random.nextBetween(-3, 3), spot.getY(), spot.getZ() + random.nextBetween(-3, 3), speed * 0.75);
                }
            }
            case HUNT -> {
                // Nothing is brought home but what was hunted down
                if(done) {
                    abandoned("found no game");
                    startReturn(world);
                } else if(random.nextInt(3) == 0 && RoutineSites.takePreySearch(world)) {
                    LivingEntity prey = findPrey(world);
                    if(prey != null) {
                        npc.startHunt(prey, CHASE_TIME);
                        phase = Phase.HUNTING;
                    }
                } else if(random.nextInt(4) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
                    // Looks over the hunting ground
                    npc.getNavigation().startMovingTo(spot.getX() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), spot.getY(),
                            spot.getZ() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), speed * 0.75);
                }
            }
            case FARM -> farm(world, done);
            case VISIT, TABLE -> {
                if(done) {
                    finish(world, REST + random.nextInt(REST_VARIATION));
                } else if(companion == null || !companion.isAlive() || npc.squaredDistanceTo(companion) > COMPANION_RANGE * COMPANION_RANGE) {
                    // Turns to whoever else is standing around
                    companion = RoutineSites.takePreySearch(world) ? world.getClosestEntity(NpcEntity.class, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ(), npc.getBoundingBox().expand(COMPANION_RANGE, 2, COMPANION_RANGE)) : null;
                    lookTarget = null;
                } else {
                    lookTarget = companion.getEyePos();
                    // Talking with the hands
                    if(random.nextInt(10) == 0)
                        npc.swingHand(Hand.MAIN_HAND);
                }
            }
            case POST -> {
                if(!done) {
                    showProp();
                    return;
                }
                // The guard is not relieved while its shift lasts and there is somebody to see it stand there
                if(onDuty(world) && world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE))
                    phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
                else
                    finish(world, REST + random.nextInt(REST_VARIATION));
            }
            case PATROL -> {
                if(!done)
                    return;
                ringLeft--;
                // The watch walks its rounds until the shift is over. Going home after each one took it through the middle of the place
                // With nobody about to see it the round ends, as every other routine does
                if(!onDuty(world) || !world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE)) {
                    if(onDuty(world))
                        abandoned("cut its round short, no player near");
                    startReturn(world);
                } else {
                    if(ringLeft <= 0) {
                        ringLeft = site.getRing().size();
                        laps++;
                    }
                    deadline = world.getTime() + ROUTINE_TIME_LIMIT;
                    nextWaypoint();
                }
            }
        }
    }

    private boolean onDuty(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        return routine != null && routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
    }

    private void nextWaypoint() {
        List<BlockPos> ring = site.getRing();
        ringIndex = Math.floorMod(ringIndex + ringStep, ring.size());
        spot = ring.get(ringIndex);
        lookTarget = null;
        phase = Phase.GOING;
        legs = 0;
        failures = 0;
        thinkCooldown = 0;
        // Dusk and dawn come in the middle of a round
        showProp();
    }

    // What its routine sends the npc out for, where that is something particular. Otherwise whatever grows in the land it is in
    private List<Item> forage(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        if(routine != null && !routine.getForage().isEmpty())
            return routine.getForage();
        List<Item> found = ForageTables.itemsAt(world, spot);
        return found.isEmpty() ? DEFAULT_FORAGE : found;
    }

    private @Nullable LivingEntity findPrey(ServerWorld world) {
        List<AnimalEntity> herd = world.getEntitiesByClass(AnimalEntity.class, npc.getBoundingBox().expand(PREY_RANGE, PREY_HEIGHT_RANGE, PREY_RANGE), NpcRoutineGoal::isPrey);
        if(herd.size() < MINIMUM_HERD)
            return null;
        AnimalEntity nearest = herd.getFirst();
        for(AnimalEntity animal : herd) {
            if(npc.squaredDistanceTo(animal) < npc.squaredDistanceTo(nearest))
                nearest = animal;
        }
        // No shooting towards people
        Box shot = new Box(npc.getEyePos(), nearest.getPos()).expand(BYSTANDER_DISTANCE);
        boolean clear = world.getEntitiesByClass(LivingEntity.class, shot, entity -> entity != npc && (entity instanceof NpcEntity || entity instanceof PlayerEntity)).isEmpty();
        return clear ? nearest : null;
    }

    // One plot at a time: whatever needs doing to the one in reach, then on to the next that needs anything
    private void farm(ServerWorld world, boolean done) {
        if(done) {
            endFarming(world);
            return;
        }
        if(npc.getRandom().nextInt(2) != 0)
            return;
        if(fieldCell != null && fieldCell.isWithinDistance(npc.getPos(), FIELD_REACH)) {
            if(!canSee(world, fieldCell.up()))
                hiddenPlots.add(fieldCell);
            else if(tend(world, fieldCell))
                return;
        }
        BlockPos home = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
        fieldCell = nextFieldCell(world, home, npc.getBlockPos());
        if(fieldCell == null) {
            endFarming(world);
        } else if(!fieldCell.isWithinDistance(npc.getPos(), FIELD_REACH)) {
            spot = fieldCell.up();
            phase = Phase.GOING;
            legs = 0;
            failures = 0;
        }
    }

    // The harvest goes home to the store
    private void endFarming(ServerWorld world) {
        fieldCell = null;
        if(carried != null)
            startReturn(world);
        else
            finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
    }

    // The plots of the place's fields are known from its buildings. The nearest one that needs anything done
    private @Nullable BlockPos nextFieldCell(ServerWorld world, BlockPos home, BlockPos from) {
        BlockPos best = null;
        for(BlockPos cell : site.getFields(home, WORK_RANGE)) {
            if(hiddenPlots.contains(cell) || !world.isChunkLoaded(cell.getX() >> 4, cell.getZ() >> 4) || !needsWork(world, cell))
                continue;
            if(best == null || cell.getSquaredDistance(from) < best.getSquaredDistance(from))
                best = cell;
        }
        return best;
    }

    private static boolean needsWork(ServerWorld world, BlockPos cell) {
        BlockState ground = world.getBlockState(cell);
        BlockState above = world.getBlockState(cell.up());
        if(ground.getBlock() instanceof FarmlandBlock)
            return above.isAir() || (above.getBlock() instanceof CropBlock crop && crop.isMature(above));
        return isTillable(ground) && (above.isAir() || above.isReplaceable());
    }

    // Ground that was a plot and has been trodden or grown over
    private static boolean isTillable(BlockState ground) {
        return ground.isIn(BlockTags.DIRT) && !ground.isOf(Blocks.MUD) && !ground.isOf(Blocks.MOSS_BLOCK);
    }

    // Tills it, sows it or brings in its crop and sows again. False when the plot needs nothing
    private boolean tend(ServerWorld world, BlockPos cell) {
        BlockState ground = world.getBlockState(cell);
        BlockPos up = cell.up();
        BlockState above = world.getBlockState(up);
        if(!(ground.getBlock() instanceof FarmlandBlock)) {
            if(!isTillable(ground) || !(above.isAir() || above.isReplaceable()))
                return false;
            if(!above.isAir())
                world.breakBlock(up, false);
            world.setBlockState(cell, Blocks.FARMLAND.getDefaultState());
            world.playSound(null, cell, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1f, 1f);
        } else if(above.isAir()) {
            world.setBlockState(up, cropFor(world, cell).getDefaultState());
            world.playSound(null, up, SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1f, 1f);
        } else if(above.getBlock() instanceof CropBlock crop && crop.isMature(above)) {
            carried = harvestOf(world, up, above);
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, up, Block.getRawIdFromState(above));
            world.setBlockState(up, crop.withAge(0));
        } else {
            return false;
        }
        npc.getLookControl().lookAt(cell.getX() + 0.5, cell.getY() + 1, cell.getZ() + 0.5);
        npc.swingHand(Hand.MAIN_HAND);
        return true;
    }

    // Sown with what grows in the next rows, or wheat where nothing does
    private static Block cropFor(ServerWorld world, BlockPos cell) {
        for(BlockPos near : BlockPos.iterateOutwards(cell.up(), 2, 0, 2)) {
            if(world.getBlockState(near).getBlock() instanceof CropBlock crop)
                return crop;
        }
        return Blocks.WHEAT;
    }

    // What the crop is grown for, not its seed: the block's own item is the seed of wheat and beetroot, the crop itself for carrots
    private static Item harvestOf(ServerWorld world, BlockPos pos, BlockState crop) {
        Item seed = crop.getBlock().asItem();
        for(ItemStack drop : Block.getDroppedStacks(crop, world, pos, null)) {
            if(!drop.isEmpty() && drop.getItem() != seed)
                return drop.getItem();
        }
        return seed;
    }

    private static boolean isPrey(AnimalEntity animal) {
        if(!animal.isAlive() || animal.isBaby() || animal.hasPassengers() || animal.hasVehicle() || animal.isLeashed())
            return false;
        if(animal instanceof TameableEntity tameable && tameable.isTamed())
            return false;
        return animal.getType().isIn(EntityTypeTagsME.GAME);
    }

    // The fighting goals do the chase, the npc itself ends it
    private boolean finishHunt(ServerWorld world) {
        if(npc.getHuntTarget() != null)
            return false;
        EntityType<?> kill = npc.takeHuntKill();
        if(kill == null)
            abandoned("the prey got away");
        carried = kill == null ? null : meatOf(kill);
        startReturn(world);
        return npc.getTarget() == null;
    }

    private static Item meatOf(EntityType<?> type) {
        if(type == EntitiesWT.DEER)
            return ItemsWT.RAW_VENISON;
        if(type == EntitiesWT.PHEASANT)
            return ItemsWT.RAW_POULTRY;
        return Items.RABBIT;
    }

    private void startReturn(ServerWorld world) {
        standUp();
        phase = Phase.RETURNING;
        lookTarget = null;
        legs = 0;
        failures = 0;
        thinkCooldown = 0;
        deadline = world.getTime() + ROUTINE_TIME_LIMIT;
        showProp();
    }

    // What is brought back goes to the barrel or the fire, and a tired npc to its bed
    private @Nullable BlockPos returnTarget(ServerWorld world) {
        if(toBed) {
            BlockPos bed = freeBed(world);
            if(bed != null)
                return npc.getBedStand() != null ? npc.getBedStand() : bed;
        } else if(carried != null && npc.getStore() != null) {
            return storeStand(world, npc.getStore());
        }
        return npc.getRoutineHome();
    }

    // The store itself is a block nobody can stand in, and the closest the pathfinder gets to it may be the wrong side of a wall
    private BlockPos storeStand(ServerWorld world, BlockPos store) {
        if(!store.equals(standOf)) {
            standOf = store;
            BlockPos inside = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            storeStand = world.isChunkLoaded(store.getX() >> 4, store.getZ() >> 4) ? NestSurvey.standNextTo(world, store, inside) : null;
        }
        return storeStand != null ? storeStand : store;
    }

    // A bed is two blocks, and the near half is as good as the other
    private boolean canSeeBed(ServerWorld world, BlockPos bed) {
        BlockHitResult hit = world.raycast(new RaycastContext(npc.getEyePos(), Vec3d.ofCenter(bed), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
        if(hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(bed))
            return true;
        return world.getBlockState(hit.getBlockPos()).getBlock() instanceof BedBlock && hit.getBlockPos().getManhattanDistance(bed) == 1;
    }

    private boolean canSee(ServerWorld world, BlockPos block) {
        BlockHitResult hit = world.raycast(new RaycastContext(npc.getEyePos(), Vec3d.ofCenter(block), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(block);
    }

    // Its own bed, unless that is gone or somebody is lying in it
    private @Nullable BlockPos freeBed(ServerWorld world) {
        BlockPos bed = npc.getAssignedBed();
        if(bed == null || !world.isChunkLoaded(bed.getX() >> 4, bed.getZ() >> 4))
            return null;
        BlockState state = world.getBlockState(bed);
        return state.getBlock() instanceof BedBlock && !state.get(BedBlock.OCCUPIED) ? bed : null;
    }

    private void goToBed(ServerWorld world) {
        toBed = false;
        lookTarget = null;
        npc.getNavigation().stop();
        npc.clearRoutineProp();
        BlockPos bed = freeBed(world);
        if(bed != null && isInReach(bed) && canSeeBed(world, bed)) {
            npc.lieDown(bed);
            phase = Phase.SLEEPING;
        } else {
            abandoned("bed", bed == null ? "its bed is gone or somebody lies in it" : !isInReach(bed) ? "could not get near its bed " + bed.toShortString()
                    : "its bed " + bed.toShortString() + " is behind a wall");
            finish(world, NOTHING_TO_DO_REST);
        }
    }

    // Lofts, ladders and odd corners keep many npcs from walking the last steps to their bed. Inside the house is near enough
    private boolean isInReach(BlockPos bed) {
        double distanceX = bed.getX() + 0.5 - npc.getX();
        double distanceZ = bed.getZ() + 0.5 - npc.getZ();
        return distanceX * distanceX + distanceZ * distanceZ <= BED_REACH * BED_REACH && Math.abs(bed.getY() - npc.getY()) <= BED_HEIGHT_REACH;
    }

    // Npcs that were alive before nests kept beds ask for theirs once
    private void askHousehold(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(npc.isHouseholdKnown() || managerPos == null || !world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4))
            return;
        if(world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager)
            manager.assignBed(npc);
    }

    // The sound of the block itself being worked: hammer on iron at a forge, hay rustling in a stable
    private static SoundEvent workSound(BlockState station) {
        StationKind kind = StationKind.of(Registries.BLOCK.getId(station.getBlock()).getPath());
        if(kind == null)
            return station.getSoundGroup().getHitSound();
        return switch (kind) {
            case FORGE -> SoundEvents.BLOCK_ANVIL_USE;
            case OVEN -> SoundEvents.BLOCK_SMOKER_SMOKE;
            case HEARTH -> SoundEvents.BLOCK_CAMPFIRE_CRACKLE;
            case LOOM -> SoundEvents.UI_LOOM_TAKE_RESULT;
            case WOOD_PILE -> SoundEvents.ITEM_AXE_STRIP;
            case HIVE -> SoundEvents.BLOCK_BEEHIVE_WORK;
            case LECTERN -> SoundEvents.ITEM_BOOK_PAGE_TURN;
            case GRINDSTONE -> SoundEvents.BLOCK_GRINDSTONE_USE;
            case HAY, TARGET, FIELD -> station.getSoundGroup().getHitSound();
        };
    }

    private static boolean isSocial(RoutineData.Activity activity) {
        return activity == RoutineData.Activity.VISIT || activity == RoutineData.Activity.TABLE;
    }

    // The nearest of the place's own who is setting out and has nobody with it yet
    private void findWard(ServerWorld world) {
        if(!RoutineSites.takePreySearch(world))
            return;
        NpcEntity ward = world.getClosestEntity(NpcEntity.class, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ(), npc.getBoundingBox().expand(ESCORT_RANGE, 8, ESCORT_RANGE));
        if(ward != null && ward.isOnOuting() && ward.getEscort() == null && java.util.Objects.equals(npc.getFactionIdentifier(), ward.getFactionIdentifier()))
            npc.escort(ward);
    }

    private void standUp() {
        meetSeat = null;
        if(!(npc.getVehicle() instanceof SeatEntity seat))
            return;
        BlockPos seatPos = seat.getBlockPos();
        npc.stopRiding();
        // Getting up leaves the npc standing on the seat, and there is no path off a piece of furniture.
        // It is put on the floor it sat down from
        BlockPos floor = npc.getWorld() instanceof ServerWorld world ? NestSurvey.standNextTo(world, seatPos, npc.getBlockPos()) : null;
        if(floor != null)
            npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }

    private void finish(ServerWorld world, int rest) {
        standUp();
        toBed = false;
        phase = Phase.NONE;
        npc.setOnOuting(false);
        carried = null;
        lookTarget = null;
        restUntil = world.getTime() + (long) rest * (1 + backOff * 2);
        npc.clearRoutineProp();
        if(npc.isSleeping())
            npc.wakeUp();
    }

    private boolean isDark() {
        long time = npc.getWorld().getTimeOfDay() % 24000;
        return time >= DUSK && time < DAWN;
    }

    private void showProp() {
        Item prop = null;
        if(phase == Phase.RETURNING)
            prop = carried;
        else if((activity == RoutineData.Activity.PATROL || activity == RoutineData.Activity.POST) && phase != Phase.NONE && isDark())
            prop = Items.TORCH;
        else if(activity == RoutineData.Activity.FISH && phase != Phase.NONE && !ownsRod)
            prop = Items.FISHING_ROD;
        else if(activity == RoutineData.Activity.FARM && phase != Phase.NONE && !(npc.getMainHandStack().getItem() instanceof HoeItem))
            prop = Items.IRON_HOE;

        if(prop == null)
            npc.clearRoutineProp();
        else
            npc.showRoutineProp(new ItemStack(prop));
    }
}
