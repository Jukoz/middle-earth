package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.RaycastContext;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.dev.NpcDebug;
import net.sevenstars.middleearth.dev.NpcFailureRecorder;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NpcRoutine {
    enum Phase { NONE, GOING, WORKING, HUNTING, RETURNING, SLEEPING }

    // Delays are in think ticks
    private static final int IDLE_DELAY = 100;
    private static final int IDLE_DELAY_VARIATION = 60;
    private static final int WAITING_DELAY = 10;
    private static final int SITE_NOT_READY_DELAY = WAITING_DELAY * 2;
    private static final int FIRST_CHECK_DELAY = 20;
    private static final int FIRST_CHECK_ID_FACTOR = 31;
    private static final int FIRST_CHECK_SPREAD = 300;
    private static final int THINK_INTERVAL = 5;

    // Test runs widen it, so one player covers a whole village
    private static final int PLAYER_RANGE = Integer.getInteger("me.routineRange", 48);
    static final int SPOT_RANGE = 64;
    private static final int WILD_HOME_RANGE = 64;
    private static final int STRAY_DISTANCE = 24;
    private static final int DRIFT_DISTANCE = 48;
    static final int STORE_STAND_RADIUS = 2;
    // Just inside a block's face, so a ray aimed there ends inside the block
    private static final double FACE_INSET = 0.49;
    private static final double HALF_BLOCK = 0.5;

    private static final int COVER_HEIGHT = 2;
    // How long a failed task is held. Each failure in a row adds more, up to 3x
    static final int HOLD = 20 * 120;
    static final int MAXIMUM_STRIKES = 3;
    private static final int PICK_ATTEMPTS = 3;

    static final int ROUTINE_TIME_LIMIT = 20 * 60 * 5;
    static final int REST = 20 * 20;
    static final int REST_VARIATION = 20 * 40;
    static final int NOTHING_TO_DO_REST = 20 * 120;

    static final long DAY = 24000;
    // Tick of day
    private static final int DUSK = 12500;
    private static final int DAWN = 23500;
    private static final double LEFT_WORK_DISTANCE = 4;

    private static final int LOOK_DISTANCE = 5;

    final NpcEntity npc;
    final double speed;

    private int delay;
    private int thinkCooldown = 0;
    private Phase phase = Phase.NONE;
    RoutineData.Activity activity = RoutineData.Activity.PATROL;
    @Nullable RoutineSite site = null;
    private BlockPos spot = BlockPos.ORIGIN;
    private @Nullable Vec3d lookTarget = null;
    private @Nullable Item carried = null;
    private long phaseEnd = 0;
    private long deadline = 0;
    private long restUntil = 0;
    // Where carried goods go when it isn't the household's store, e.g. a woodcutter's logs go on the wood pile
    private @Nullable BlockPos dropOff = null;
    private String interruptedBy = "";
    // Grows with each give-up so a stuck npc stops trying
    private final Map<RoutineData.Activity, Long> heldUntil = new EnumMap<>(RoutineData.Activity.class);
    private final Map<RoutineData.Activity, Integer> strikes = new EnumMap<>(RoutineData.Activity.class);
    private final Set<String> notedFallbacks = new HashSet<>();
    private long homeHeldUntil = 0;
    int homeStrikes = 0;
    // Set once the task is given up, so it isn't also counted as finished
    private boolean failed = false;
    private boolean ownsRod = false;
    private boolean toBed = false;
    private @Nullable BlockPos meetSeat = null;
    private int carriedCount = 1;
    // Taken out of a store, so it's put back or dropped rather than lost
    private boolean carriedFromStore = false;
    private boolean homeward = false;
    private @Nullable Direction facing = null;

    final RoutineWalker walker;
    final RoutineBed beds;
    final RoutineDebug debug;
    private final RoutineReturn returnTrip;
    final RoutineSeat sitting;
    private final FishingActivity fishing;
    private final HuntingActivity hunting;
    private final ForagingActivity foraging;
    final TableActivity table;
    final StationActivity station;
    final FarmingActivity farming;
    private final ChoppingActivity chopping;
    private final MiningActivity mining;
    private final PostActivity post;
    final PatrolActivity patrol;
    private final InspectActivity inspect;
    private final FollowActivity follow;
    final EscortActivity escort;
    private final Map<RoutineData.Activity, RoutineActivity> activities = new EnumMap<>(RoutineData.Activity.class);

    public String debugLabel() {
        return debug.debugLabel();
    }

    public boolean debugActive() { return phase != Phase.NONE && phase != Phase.SLEEPING; }
    public boolean debugReturning() { return phase == Phase.RETURNING; }
    public boolean debugToBed() { return toBed; }
    public RoutineData.Activity debugActivity() { return activity; }
    public BlockPos debugSpot() { return spot; }
    public @Nullable Vec3d debugLookTarget() { return lookTarget; }
    public @Nullable NpcEntity debugCompanion() { return table.companion; }
    public @Nullable Item debugCarried() { return carried; }
    public List<BlockPos> debugRing() { return site == null ? List.of() : site.getRing(); }
    public int debugRingIndex() { return patrol.ringIndex; }

    public NpcRoutine(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        this.delay = FIRST_CHECK_DELAY + Math.floorMod(npc.getId() * FIRST_CHECK_ID_FACTOR, FIRST_CHECK_SPREAD);
        this.walker = new RoutineWalker(this);
        this.beds = new RoutineBed(this);
        this.debug = new RoutineDebug(this);
        this.returnTrip = new RoutineReturn(this);
        this.sitting = new RoutineSeat(this);
        this.fishing = new FishingActivity(this);
        this.hunting = new HuntingActivity(this);
        this.foraging = new ForagingActivity(this);
        this.table = new TableActivity(this);
        this.station = new StationActivity(this);
        this.farming = new FarmingActivity(this);
        this.chopping = new ChoppingActivity(this);
        this.mining = new MiningActivity(this);
        this.post = new PostActivity(this);
        this.patrol = new PatrolActivity(this);
        this.inspect = new InspectActivity(this);
        this.follow = new FollowActivity(this);
        this.escort = new EscortActivity(this);
        activities.put(RoutineData.Activity.FISH, fishing);
        activities.put(RoutineData.Activity.HUNT, hunting);
        activities.put(RoutineData.Activity.FORAGE, foraging);
        activities.put(RoutineData.Activity.VISIT, table);
        activities.put(RoutineData.Activity.TABLE, table);
        activities.put(RoutineData.Activity.COURT, table);
        activities.put(RoutineData.Activity.WORK, station);
        activities.put(RoutineData.Activity.FARM, farming);
        activities.put(RoutineData.Activity.CHOP, chopping);
        activities.put(RoutineData.Activity.MINE, mining);
        activities.put(RoutineData.Activity.POST, post);
        activities.put(RoutineData.Activity.PATROL, patrol);
        activities.put(RoutineData.Activity.INSPECT, inspect);
        activities.put(RoutineData.Activity.FOLLOW, follow);
        activities.put(RoutineData.Activity.ESCORT, escort);
    }

    public boolean canStart() {
        if(npc.isSleeping()) {
            phase = Phase.SLEEPING;
            return true;
        }
        if(npc.isConfined())
            return false;
        if(--delay > 0)
            return false;
        delay = WAITING_DELAY;
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
            return false;

        if(phase == Phase.HUNTING)
            return hunting.finishHunt(world);
        if(phase == Phase.SLEEPING)
            phase = Phase.NONE;
        boolean seated = npc.getVehicle() instanceof SeatEntity;
        // Saved at the table, so forget the meeting
        if(seated && phase == Phase.NONE) {
            sitting.standUp();
            meetSeat = null;
        }
        if(isBusyElsewhere(seated))
            return false;
        if(phase != Phase.NONE) {
            if(world.getTime() > deadline && phase != Phase.RETURNING)
                runOutOfTime(world);
            return true;
        }
        return startNewTask(world);
    }

    private boolean isBusyElsewhere(boolean seated) {
        return npc.getTarget() != null || (npc.hasVehicle() && !seated && !walker.riding());
    }

    // Still working when time is up counts as done, even a long shift at a post
    private void runOutOfTime(ServerWorld world) {
        if(phase != Phase.WORKING)
            abandoned("ran out of time");
        startReturn(world);
    }

    private boolean startNewTask(ServerWorld world) {
        delay = IDLE_DELAY + npc.getRandom().nextInt(IDLE_DELAY_VARIATION);
        if(isEscortOut(world))
            return false;
        if(npc.getPartyLeader() != null || npc.getTravelHeading() != null)
            return false;
        long time = world.getTime();
        // Settled npcs that wandered off walk home. One that failed rests first
        if(time >= restUntil && time >= homeHeldUntil && drifted(world))
            return headHome(world);
        RoutineData routine = npc.getRoutine();
        if(routine == null) {
            debug.debugWhyNot(world, "no routine");
            return false;
        }
        // A wild npc's camp is wherever it first stood
        if(npc.getRoutineHome() == null && npc.getHostPos() == null)
            npc.setRoutineHome(npc.getBlockPos());
        if(isResting(world, time))
            return false;
        // A rider stays with its mount through the night and has no bed to lie in
        BlockPos restingHome = npc.getHostPos() == null || walker.riding() ? null : npc.getRoutineHome();
        if(restingHome != null && npc.isRestTime())
            return bedTime(world, time, restingHome);
        if(!isWatched(world)) {
            debug.debugWhyNot(world, "no player within " + PLAYER_RANGE);
            return false;
        }
        BlockPos home = npc.getRoutineHome();
        if(home == null)
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
            debug.debugWhyNot(world, "site not ready");
            delay = SITE_NOT_READY_DELAY;
            return false;
        }
        return beginPicked(world, routine, home, time);
    }

    private boolean isResting(ServerWorld world, long time) {
        // Stops a village setting out all at once
        if(restUntil == 0)
            restUntil = time + npc.getRandom().nextInt(REST + REST_VARIATION);
        if(time < restUntil && !npc.hasInvitation(time)) {
            debug.debugWhyNot(world, "resting for " + (restUntil - time));
            return true;
        }
        return false;
    }

    private boolean beginPicked(ServerWorld world, RoutineData routine, BlockPos home, long time) {
        table.hostingAt = npc.takeInvitation(time);
        if(table.hostingAt != null && !npc.isRestTime() && begin(world, RoutineData.Activity.VISIT, home))
            return true;
        table.hostingAt = null;
        RoutineData.Activity picked = pickActivity(world, routine, home, time);
        if(NpcDebug.ENABLED && picked == null) debug.debugWhyNot(world, "nothing picked, on duty " + routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift()));
        for(int attempt = 0; attempt < PICK_ATTEMPTS && picked != null; attempt++) {
            if(activityFor(picked).searchesOnly(world))
                return false;
            if(begin(world, picked, home))
                return true;
            picked = pickActivity(world, routine, home, time);
        }

        restUntil = time + NOTHING_TO_DO_REST;
        return goHomeOffDuty(world, time, home);
    }

    private boolean isEscortOut(ServerWorld world) {
        if(npc.isEscorting()) {
            boolean stillOut = npc.getPartyLeader() != null && world.getEntity(npc.getPartyLeader()) instanceof NpcEntity ward && ward.isAlive() && ward.isOnOuting();
            if(stillOut)
                return true;
            npc.endEscort();
        }
        return false;
    }

    private boolean headHome(ServerWorld world) {
        loseCarried(world);
        homeward = true;
        failed = false;
        startReturn(world);
        return true;
    }

    private boolean bedTime(ServerWorld world, long time, BlockPos restingHome) {
        if(!RoutineSites.takeStart(world)) {
            delay = WAITING_DELAY + npc.getRandom().nextInt(WAITING_DELAY);
            return false;
        }
        if(beds.freeBed(world) == null && restingHome.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            restUntil = time + NOTHING_TO_DO_REST;
            return false;
        }
        if(time < beds.bedHeldUntil) {
            restUntil = beds.bedHeldUntil;
            return false;
        }
        loseCarried(world);
        toBed = beds.freeBed(world) != null;
        homeward = true;
        failed = false;
        startReturn(world);
        return true;
    }

    private boolean goHomeOffDuty(ServerWorld world, long time, BlockPos home) {
        if(npc.getHostPos() != null && time >= homeHeldUntil && !home.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE))
            return headHome(world);
        return false;
    }

    private @Nullable RoutineData.Activity pickActivity(ServerWorld world, RoutineData routine, BlockPos home, long time) {
        // Underground npcs stay in their halls. A wild npc has no house, so any roof is a cave
        boolean underground = npc.getHostPos() == null ? isUnderCover(world, home) : RoutineSite.isEnclosed(world, home.getX(), home.getZ(), home.getY());
        boolean onDuty = routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
        // Elves need little sleep and keep company through the night
        if(!onDuty && (!npc.isElf() || npc.isRestTime()))
            return null;
        // Each task is judged once so the answers stay the same during the pick
        table.visitHousehold = null;
        Set<RoutineData.Activity> possible = EnumSet.noneOf(RoutineData.Activity.class);
        for(RoutineData.Entry entry : routine.getEntries()) {
            RoutineData.Activity activity = entry.activity();
            if(!possible.contains(activity) && isPossible(world, routine, activity, home, time, onDuty, underground))
                possible.add(activity);
        }
        return routine.pick(npc.getRandom(), possible::contains);
    }

    private boolean isPossible(ServerWorld world, RoutineData routine, RoutineData.Activity activity, BlockPos home, long time, boolean onDuty, boolean underground) {
        RoutineActivity handler = activityFor(activity);
        if(!handler.isSupportedBy(site, activity) || isHeld(activity, time) || !onDuty && !handler.isSocial(activity))
            return false;
        if(!handler.canBeDone(world, activity, home))
            return false;
        if(!onDuty)
            return true;
        if(walker.riding() && !handler.canBeMounted() || underground && handler.isOutdoors())
            return false;
        return handler.isPossible(world, routine, home, time);
    }

    RoutineActivity activityFor(RoutineData.Activity kind) {
        return activities.get(kind);
    }

    private boolean isHeld(RoutineData.Activity activity, long time) {
        return heldUntil.getOrDefault(activity, 0L) > time;
    }

    void hold(RoutineData.Activity activity, long time) {
        int count = Math.min(strikes.getOrDefault(activity, 0) + 1, MAXIMUM_STRIKES);
        strikes.put(activity, count);
        heldUntil.put(activity, time + (long) HOLD * count);
    }

    private boolean drifted(ServerWorld world) {
        BlockPos managerPos = npc.getHostPos();
        if(managerPos == null)
            return false;
        BlockPos home = npc.getRoutineHome();
        if(home == null) {
            if(!isLoaded(world, managerPos))
                return false;
            home = managerPos;
            npc.setRoutineHome(home);
        }
        return !home.isWithinDistance(npc.getBlockPos(), DRIFT_DISTANCE);
    }

    boolean isWatched(ServerWorld world) {
        // isPlayerInRange ignores spectators, and the test harness watches as one
        if(NpcDebug.ENABLED && world.getPlayers().stream().anyMatch(player -> player.squaredDistanceTo(npc) < PLAYER_RANGE * PLAYER_RANGE))
            return true;
        return world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE);
    }

    private boolean findSite(ServerWorld world) {
        BlockPos managerPos = npc.getHostPos();
        if(managerPos != null) {
            // Fetched every start. A site forgotten meanwhile would differ from neighbours'
            RoutineSite found = RoutineSites.forManager(world, managerPos);
            if(found != null)
                site = found;
            if(site == null)
                debug.debugWhyNot(world, "no site for its manager at " + managerPos.toShortString());
            return site != null;
        }

        BlockPos home = npc.getRoutineHome();
        if(home == null || !home.isWithinDistance(npc.getBlockPos(), WILD_HOME_RANGE)) {
            home = npc.getBlockPos();
            npc.setRoutineHome(home);
            site = null;
        }
        if(site != null && !site.isDropped()) {
            site.touch(world.getTime());
            return true;
        }
        site = RoutineSites.forWild(world, home, npc.getFactionIdentifier());
        return site != null;
    }

    private boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        lookTarget = null;
        loseCarried(world);
        facing = null;
        picked = activityFor(picked).fallback(world, picked, home);
        RoutineActivity handler = activityFor(picked);
        if(!handler.begin(world, picked, home))
            return false;
        activity = picked;
        homeward = false;
        npc.setFarming(handler.isFarming());
        npc.setOnOuting(npc.getHostPos() != null && handler.isOuting());
        ownsRod = npc.getMainHandStack().getItem() instanceof FishingRodItem;
        phase = Phase.GOING;
        failed = false;
        deadline = world.getTime() + ROUTINE_TIME_LIMIT;
        return true;
    }

    boolean isSentry() {
        return NpcJobs.SENTRY.getValue().equals(npc.getJobId());
    }

    public boolean shouldContinue() {
        // Sleepers are still ticked, so keep it in bed
        if(phase == Phase.SLEEPING)
            return npc.isSleeping() && npc.getTarget() == null;
        return (phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING)
                && npc.getTarget() == null && (!npc.hasVehicle() || npc.getVehicle() instanceof SeatEntity || walker.riding());
    }

    public void start() {
        // Interrupted work goes home, it does not resume where it ended up
        if(phase == Phase.WORKING && hasLeftWork() && npc.getEntityWorld() instanceof ServerWorld world) {
            abandoned("left its work, " + interruptedBy);
            startReturn(world);
        }
        walker.resetWalk();
        thinkCooldown = 0;
        showProp();
    }

    private boolean hasLeftWork() {
        return activityFor(activity).isTiedToSpot() && !spot.isWithinDistance(npc.getEntityPos(), LEFT_WORK_DISTANCE);
    }

    public void stop() {
        if(phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING) {
            LivingEntity foe = npc.getTarget();
            // All npcs share one entity type, so tell them apart by npc class
            String foeName = foe == null ? "" : foe instanceof NpcEntity other && other.getNpcClass() != null ? other.getNpcClass().getId().getPath()
                    : Registries.ENTITY_TYPE.getId(foe.getType()).getPath();
            // A task resumed after a fight isn't given up, only if the npc has to leave it
            interruptedBy = foe != null ? "interrupted by a fight with " + foeName
                    : npc.hasVehicle() && !(npc.getVehicle() instanceof SeatEntity) && !walker.riding() ? "interrupted, it is riding" : "interrupted by something else, such as fleeing";
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s paused %s (%s) at %d,%d,%d: %s".formatted(who(), activity.asString(),
                    phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), interruptedBy));
        }
        walker.stopMoving();
        sitting.standUp();
        npc.clearRoutineProp();
        if(npc.isSleeping())
            npc.wakeUp();
        if(phase == Phase.SLEEPING)
            phase = Phase.NONE;
        delay = WAITING_DELAY;
    }

    public void tick() {
        if(phase == Phase.WORKING && lookTarget != null)
            npc.getLookControl().lookAt(lookTarget.x, lookTarget.y, lookTarget.z);
        if(--thinkCooldown > 0)
            return;
        thinkCooldown = THINK_INTERVAL;
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
            return;

        long time = world.getTime();
        if(phase == Phase.SLEEPING) {
            if(!npc.isRestTime())
                finish(world, 0);
            return;
        }
        if(time > deadline && phase != Phase.RETURNING) {
            runOutOfTime(world);
            return;
        }

        switch (phase) {
            case GOING -> {
                if(activity != RoutineData.Activity.FOLLOW || follow.following(world))
                    walker.travel(world, spot);
            }
            case RETURNING -> walker.travel(world, returnTrip.returnTarget(world));
            case WORKING -> work(world, time);
            default -> {}
        }
    }

    void abandoned(String why) {
        abandoned(toBed ? "bed" : homeward ? "home" : activity.asString(), why);
    }

    void abandoned(RoutineData.Activity what, String why) {
        abandoned(what.asString(), why);
    }

    void abandoned(String what, String why) {
        failed = true;
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s gave up %s (%s) at %d,%d,%d: %s%s".formatted(who(), what,
                phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), why, what.equals("bed") ? bedDistance() : ""));
        if(NpcDebug.ENABLED)
            NpcFailureRecorder.record(npc, who(), what, phase.name().toLowerCase(), why, phase == Phase.RETURNING ? (toBed && npc.getBedStand() != null ? npc.getBedStand() : toBed ? npc.getAssignedBed() : carried != null && npc.getStore() != null ? npc.getStore() : npc.getRoutineHome()) : spot, npc.getAssignedBed(), npc.getRoutineHome(), npc.getNavigation().getCurrentPath());
    }

    void fallsBack(RoutineData.Activity what, String why) {
        fallsBack(what.asString(), why);
    }

    void fallsBack(String what, String why) {
        if(!notedFallbacks.add(what + ": " + why))
            return;
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s falls back from %s at %d,%d,%d: %s".formatted(who(), what, pos.getX(), pos.getY(), pos.getZ(), why));
    }

    private String bedDistance() {
        BlockPos bed = npc.getAssignedBed();
        if(bed == null)
            return " (no bed assigned)";
        double across = Math.hypot(bed.getX() + 0.5 - npc.getX(), bed.getZ() + 0.5 - npc.getZ());
        int rise = bed.getY() - npc.getBlockY();
        return " (bed %.1f blocks away, %s)".formatted(across, rise == 0 ? "same floor" : rise > 0 ? rise + " up" : -rise + " down");
    }

    void did(String what) {
        did(activity.asString(), what);
    }

    void did(String task, String what) {
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s did %s at %d,%d,%d: %s".formatted(who(), task, pos.getX(), pos.getY(), pos.getZ(), what));
    }

    private String who() {
        NpcClass npcClass = npc.getNpcClass();
        return (npcClass == null ? "npc" : npcClass.getId().getPath()) + (npc.getJobId() == null ? "" : " [" + npc.getJobId().getPath() + "]");
    }

    static String itemName(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }

    boolean cannotStart(RoutineData.Activity what, String why) {
        abandoned(what, "could not start, " + why);
        hold(what, npc.getEntityWorld().getTime());
        return false;
    }

    static boolean isLoaded(ServerWorld world, BlockPos pos) {
        return world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }

    static boolean isUnderCover(ServerWorld world, BlockPos pos) {
        return isLoaded(world, pos) && world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) > pos.getY() + COVER_HEIGHT;
    }

    void giveUp(ServerWorld world, String why) {
        debug.debugWhy("GIVEUP", world, null, null);
        RoutineActivity handler = activityFor(activity);
        if(phase != Phase.RETURNING && handler.skipOnGiveUp(world, why))
            return;
        if(phase == Phase.RETURNING && toBed) {
            // Stopped short, but near enough to lie down
            BlockPos bed = beds.freeBed(world);
            if(bed == null || !beds.isInReach(bed) || !beds.canSeeBed(world, bed))
                abandoned("could not reach its bed, " + why);
            beds.goToBed(world);
            return;
        }
        boolean skipsStretch = phase != Phase.RETURNING && handler.canSkipStretch();
        abandoned((skipsStretch ? "skipped a stretch of its round, " : "") + why);
        if(skipsStretch) {
            // Skip a blocked stretch, keep walking the rest
            failed = false;
            handler.skipStretch();
            return;
        }
        if(phase == Phase.RETURNING) {
            homeStrikes = Math.min(homeStrikes + 1, MAXIMUM_STRIKES);
            homeHeldUntil = world.getTime() + (long) HOLD * homeStrikes;
            // A bed behind a wall can't be stood on, so the doorstep is home enough
            finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
        } else {
            handler.held(world.getTime());
            hold(activity, world.getTime());
            startReturn(world);
        }
    }

    void arrive(ServerWorld world) {
        Random random = npc.getRandom();
        long time = world.getTime();
        walker.stopMoving();
        if(phase == Phase.RETURNING) {
            returnTrip.arriveHome(world, random);
            return;
        }

        RoutineActivity handler = activities.get(activity);
        if(handler.beforeWork(world, random, time))
            return;
        phase = Phase.WORKING;
        handler.arrive(world, random, time);
    }

    private void work(ServerWorld world, long time) {
        Random random = npc.getRandom();
        boolean done = time >= phaseEnd;
        activities.get(activity).work(world, random, done, time);
    }

    void turnTo(NpcEntity speaker) {
        table.turnTo(speaker);
    }

    public boolean isInCompany() {
        return phase == Phase.WORKING && activityFor(activity).isInCompany();
    }

    boolean onDuty(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        return routine != null && routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
    }

    @Nullable Vec3d facingTarget() {
        return facing == null ? null : new Vec3d(npc.getX() + facing.getOffsetX() * LOOK_DISTANCE, npc.getEyeY(), npc.getZ() + facing.getOffsetZ() * LOOK_DISTANCE);
    }

    void startReturn(ServerWorld world) {
        sitting.standUp();
        meetSeat = null;
        if(site != null)
            site.releasePost(npc.getUuid());
        phase = Phase.RETURNING;
        walker.newWalk(world);
        showProp();
    }

    @Nullable BlockPos dropOff() {
        return dropOff != null ? dropOff : npc.getStore();
    }

    // Floor beside a block. A bench seat has none, so it's reached across the next seat
    BlockPos standAt(ServerWorld world, BlockPos block) {
        BlockPos stand = NestSurvey.standNextTo(world, block, npc.getBlockPos());
        if(stand == null)
            stand = NestSurvey.standInSight(world, block, npc.getBlockPos(), STORE_STAND_RADIUS);
        return stand != null ? stand : block;
    }

    // Ray hits the side facing the npc, from eye height the middle crosses the block above
    boolean canSee(ServerWorld world, BlockPos block) {
        Vec3d eye = npc.getEyePos();
        Vec3d middle = Vec3d.ofCenter(block);
        List<Direction> sides = new ArrayList<>(3);
        if(Math.abs(eye.x - middle.x) > HALF_BLOCK)
            sides.add(eye.x > middle.x ? Direction.EAST : Direction.WEST);
        if(Math.abs(eye.z - middle.z) > HALF_BLOCK)
            sides.add(eye.z > middle.z ? Direction.SOUTH : Direction.NORTH);
        if(Math.abs(eye.y - middle.y) > HALF_BLOCK)
            sides.add(eye.y > middle.y ? Direction.UP : Direction.DOWN);
        for(Direction side : sides) {
            Vec3d aim = middle.add(side.getOffsetX() * FACE_INSET, side.getOffsetY() * FACE_INSET, side.getOffsetZ() * FACE_INSET);
            BlockHitResult hit = world.raycast(new RaycastContext(eye, aim, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
            if(hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(block))
                return true;
        }
        return sides.isEmpty();
    }

    void finish(ServerWorld world, int rest) {
        if(phase != Phase.NONE && !failed) {
            // Only a finished task clears failures, so a hunter that found nothing stays away longer
            if(phase != Phase.SLEEPING && !toBed && !homeward)
                strikes.remove(activity);
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s finished %s at %d,%d,%d".formatted(who(), phase == Phase.SLEEPING ? "sleep" : toBed ? "bed" : homeward ? "home" : activity.asString(),
                    pos.getX(), pos.getY(), pos.getZ()));
        }
        failed = false;
        loseCarried(world);
        sitting.standUp();
        meetSeat = null;
        table.companion = null;
        if(site != null)
            site.releasePost(npc.getUuid());
        toBed = false;
        phase = Phase.NONE;
        dropOff = null;
        station.clear();
        npc.setOnOuting(false);
        npc.setFarming(false);
        lookTarget = null;
        restUntil = world.getTime() + rest;
        npc.clearRoutineProp();
        if(npc.isSleeping())
            npc.wakeUp();
    }

    void loseCarried(ServerWorld world) {
        if(carried != null && carriedFromStore) {
            ItemStack left = new ItemStack(carried, carriedCount);
            BlockPos source = dropOff();
            Inventory inventory = source != null && isLoaded(world, source) ? RoutineStores.inventoryAt(world, source) : null;
            if(inventory != null) {
                left = HopperBlockEntity.transfer(null, inventory, left, null);
                if(site != null)
                    site.getStock().changed();
            }
            if(!left.isEmpty())
                world.spawnEntity(new ItemEntity(world, npc.getX(), npc.getY(), npc.getZ(), left));
        }
        carried = null;
        carriedCount = 1;
        carriedFromStore = false;
    }

    boolean isDark() {
        long time = npc.getEntityWorld().getTimeOfDay() % DAY;
        return time >= DUSK && time < DAWN;
    }

    void showProp() {
        Item prop = null;
        if(phase == Phase.RETURNING)
            prop = carried;
        else if(phase == Phase.GOING || phase == Phase.WORKING)
            prop = activityFor(activity).prop(activity);

        if(prop == null)
            npc.clearRoutineProp();
        else
            npc.showRoutineProp(new ItemStack(prop));
    }

    boolean ownsRod() {
        return ownsRod;
    }

    Phase phase() {
        return phase;
    }

    BlockPos spot() {
        return spot;
    }

    void setSpot(BlockPos spot) {
        this.spot = spot;
    }

    @Nullable Vec3d lookTarget() {
        return lookTarget;
    }

    void setLookTarget(@Nullable Vec3d lookTarget) {
        this.lookTarget = lookTarget;
    }

    void setFacing(@Nullable Direction facing) {
        this.facing = facing;
    }

    void setPhaseEnd(long phaseEnd) {
        this.phaseEnd = phaseEnd;
    }

    @Nullable BlockPos meetSeat() {
        return meetSeat;
    }

    void setMeetSeat(@Nullable BlockPos meetSeat) {
        this.meetSeat = meetSeat;
    }

    boolean isToBed() {
        return toBed;
    }

    void clearToBed() {
        toBed = false;
    }

    boolean isHomeward() {
        return homeward;
    }

    boolean hasFailed() {
        return failed;
    }

    void clearFailure() {
        failed = false;
    }

    // Phase changes for the walking, hunting and sleeping steps
    void startChase() {
        phase = Phase.HUNTING;
    }

    void sleep() {
        phase = Phase.SLEEPING;
    }

    @Nullable Item carried() {
        return carried;
    }

    void setCarried(@Nullable Item carried) {
        this.carried = carried;
    }

    int carriedCount() {
        return carriedCount;
    }

    void setCarriedCount(int carriedCount) {
        this.carriedCount = carriedCount;
    }

    void setDropOff(@Nullable BlockPos dropOff) {
        this.dropOff = dropOff;
    }

    void carryFromStore(Item item, int count, BlockPos store) {
        carried = item;
        carriedCount = count;
        carriedFromStore = true;
        dropOff = store;
    }

    // Walks to a new spot from scratch
    void walkTo(BlockPos spot) {
        this.spot = spot;
        phase = Phase.GOING;
        walker.resetWalk();
    }

    // Next stop of a round: nothing to look at on the way, thinks again at once
    void walkOn(BlockPos spot) {
        lookTarget = null;
        walkTo(spot);
        thinkCooldown = 0;
    }

    void restartTimeLimit(long time) {
        deadline = time + ROUTINE_TIME_LIMIT;
    }

    void freshWalk(long time) {
        lookTarget = null;
        walker.resetWalk();
        thinkCooldown = 0;
        restartTimeLimit(time);
    }
}
