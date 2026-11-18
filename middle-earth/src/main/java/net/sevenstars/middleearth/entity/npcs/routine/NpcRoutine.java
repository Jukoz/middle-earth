package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
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
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.dev.NpcDebug;
import net.sevenstars.middleearth.dev.NpcFailureRecorder;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Runs an npc's day: out to its task, home with what it got, and to bed at night
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
    // In think ticks, so 10 looks at the world a second
    private static final int THINK_INTERVAL = 5;

    // Test runs widen it, so one player covers a whole village
    private static final int PLAYER_RANGE = Integer.getInteger("me.routineRange", 48);
    static final int SPOT_RANGE = 64;
    private static final int WILD_HOME_RANGE = 64;
    // A settled npc further from home than this walks back first
    private static final int STRAY_DISTANCE = 24;
    private static final int DRIFT_DISTANCE = 48;
    // How far to look for a floor with a line of sight to a store that has no floor beside it
    static final int STORE_STAND_RADIUS = 2;
    // Just inside the face of a block, so a ray aimed there ends inside the block
    private static final double FACE_INSET = 0.49;
    private static final double HALF_BLOCK = 0.5;

    // Blocks above the feet, beyond which a place counts as indoors or underground
    private static final int COVER_HEIGHT = 2;
    // How long a failed task is put on hold. Each failure in a row holds it longer, up to three times as long
    static final int HOLD = 20 * 120;
    static final int MAXIMUM_STRIKES = 3;
    // How many times another task is tried when the one picked can't be started
    private static final int PICK_ATTEMPTS = 3;

    // In ticks
    static final int ROUTINE_TIME_LIMIT = 20 * 60 * 5;
    static final int REST = 20 * 20;
    static final int REST_VARIATION = 20 * 40;
    static final int NOTHING_TO_DO_REST = 20 * 120;

    // In ticks
    static final long DAY = 24000;
    // Time of day
    private static final int DUSK = 12500;
    private static final int DAWN = 23500;
    // In blocks from the place of its work. An interrupted npc further away than this has left it
    private static final double LEFT_WORK_DISTANCE = 4;

    private static final int LOOK_DISTANCE = 5;

    final NpcEntity npc;
    final double speed;

    private int delay;
    int thinkCooldown = 0;
    Phase phase = Phase.NONE;
    RoutineData.Activity activity = RoutineData.Activity.PATROL;
    @Nullable RoutineSite site = null;
    BlockPos spot = BlockPos.ORIGIN;
    @Nullable Vec3d lookTarget = null;
    @Nullable Item carried = null;
    long phaseEnd = 0;
    long deadline = 0;
    private long restUntil = 0;
    // Where what is carried goes when it isn't the household's store, e.g. a woodcutter's logs go on the wood pile
    @Nullable BlockPos dropOff = null;
    private String interruptedBy = "";
    // Grows each time a routine has to be abandoned, so an npc with no way out stops trying
    private final Map<RoutineData.Activity, Long> heldUntil = new EnumMap<>(RoutineData.Activity.class);
    private final Map<RoutineData.Activity, Integer> strikes = new EnumMap<>(RoutineData.Activity.class);
    private final Set<String> notedFallbacks = new HashSet<>();
    // The way home and the way to bed are held back the same way once they fail
    private long homeHeldUntil = 0;
    int homeStrikes = 0;
    // Set once the current task has been given up, so it isn't also counted as finished
    boolean failed = false;
    private boolean ownsRod = false;
    boolean toBed = false;
    @Nullable BlockPos meetSeat = null;
    int carriedCount = 1;
    // Heading back with no task, after straying or at the end of the day
    boolean homeward = false;
    // The direction the marker of the post, seat or stop being visited faces
    @Nullable Direction facing = null;

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
        // Spreads the first check of each npc over half a minute
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
    }

    public boolean canStart() {
        // Also picks up an npc that was saved asleep
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
        // It was saved at the table, so forget the meeting it sat down for
        if(seated && phase == Phase.NONE) {
            sitting.standUp();
            meetSeat = null;
        }
        if(npc.getTarget() != null || (npc.hasVehicle() && !seated && !walker.riding()))
            return false;
        if(phase != Phase.NONE) {
            if(world.getTime() > deadline && phase != Phase.RETURNING) {
                // An npc still at work when time is up counts as done, including a long shift at a post
                if(phase != Phase.WORKING)
                    abandoned("ran out of time");
                startReturn(world);
            }
            return true;
        }

        delay = IDLE_DELAY + npc.getRandom().nextInt(IDLE_DELAY_VARIATION);
        if(isEscortOut(world))
            return false;
        if(npc.getPartyLeader() != null || npc.getTravelHeading() != null)
            return false;
        long time = world.getTime();
        // Wandering takes settled npcs out of their village for good, seen or not. One that couldn't get home rests
        // first, as after any other walk given up, or it would try again and again from the same spot
        if(time >= restUntil && time >= homeHeldUntil && drifted(world)) {
            carried = null;
            homeward = true;
            failed = false;
            startReturn(world);
            return true;
        }
        RoutineData routine = npc.getRoutine();
        if(routine == null) {
            debug.debugWhyNot(world, "no routine");
            return false;
        }
        // Where a wild npc first stands becomes its camp, before wandering takes it elsewhere
        if(npc.getRoutineHome() == null && npc.getStructureManagerHostPos() == null)
            npc.setRoutineHome(npc.getBlockPos());
        // Keeps a village from setting out all at once when its chunks load
        if(restUntil == 0)
            restUntil = time + npc.getRandom().nextInt(REST + REST_VARIATION);
        if(time < restUntil && !npc.hasInvitation(time)) {
            debug.debugWhyNot(world, "resting for " + (restUntil - time));
            return false;
        }
        // Bed time applies whether or not anyone is watching
        // A rider stays with its mount through the night and has no bed to lie in
        BlockPos restingHome = npc.getStructureManagerHostPos() == null || walker.riding() ? null : npc.getRoutineHome();
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

        // Someone is calling on its household, so it comes home to sit with them
        table.hostingAt = npc.takeInvitation(time);
        if(table.hostingAt != null && !npc.isRestTime() && begin(world, RoutineData.Activity.VISIT, home))
            return true;
        table.hostingAt = null;
        RoutineData.Activity picked = pickActivity(world, routine, home, time);
        if(NpcDebug.ENABLED && picked == null) debug.debugWhyNot(world, "nothing picked, on duty " + routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift()));
        // A task that can't be started is put on hold and another is tried, before the npc settles for doing nothing
        for(int attempt = 0; attempt < PICK_ATTEMPTS && picked != null; attempt++) {
            if(picked == RoutineData.Activity.ESCORT) {
                escort.findWard(world);
                return false;
            }
            if(begin(world, picked, home))
                return true;
            picked = pickActivity(world, routine, home, time);
        }

        restUntil = time + NOTHING_TO_DO_REST;
        return goHomeOffDuty(world, time, home);
    }

    private boolean isEscortOut(ServerWorld world) {
        // An escort stays with its ward until the ward is home again or lost
        if(npc.isEscorting()) {
            boolean stillOut = npc.getPartyLeader() != null && world.getEntity(npc.getPartyLeader()) instanceof NpcEntity ward && ward.isAlive() && ward.isOnOuting();
            if(stillOut)
                return true;
            npc.endEscort();
        }
        return false;
    }

    private boolean bedTime(ServerWorld world, long time, BlockPos restingHome) {
        if(!RoutineSites.takeStart(world)) {
            delay = WAITING_DELAY + npc.getRandom().nextInt(WAITING_DELAY);
            return false;
        }
        // Without a bed, staying around the house is all the rest it gets
        if(beds.freeBed(world) == null && restingHome.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            restUntil = time + NOTHING_TO_DO_REST;
            return false;
        }
        if(time < beds.bedHeldUntil) {
            restUntil = beds.bedHeldUntil;
            return false;
        }
        carried = null;
        toBed = beds.freeBed(world) != null;
        homeward = true;
        failed = false;
        startReturn(world);
        return true;
    }

    private boolean goHomeOffDuty(ServerWorld world, long time, BlockPos home) {
        // Off duty, a settled npc that has drifted away goes home
        if(npc.getStructureManagerHostPos() != null && time >= homeHeldUntil && !home.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            carried = null;
            homeward = true;
            failed = false;
            startReturn(world);
            return true;
        }
        return false;
    }

    private @Nullable RoutineData.Activity pickActivity(ServerWorld world, RoutineData routine, BlockPos home, long time) {
        // Npcs living deep underground stay in their halls. The hunting grounds and the round of the place are up on
        // the surface, further off down the halls and up the stairs than any walk is looked for
        // A wild npc has no house over its head, so any roof over its camp is the roof of a cave
        boolean underground = npc.getStructureManagerHostPos() == null ? isUnderCover(world, home) : RoutineSite.isEnclosed(world, home.getX(), home.getZ(), home.getY());
        boolean onDuty = routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
        // Elves need little sleep and keep company through the night
        if(!onDuty && (!npc.isElf() || npc.isRestTime()))
            return null;
        // Each task is judged only once, because whether there is somebody to visit is random, and what was found
        // possible has to stay possible while the pick is made
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
        if(!site.supports(activity) || isHeld(activity, time) || !onDuty && !isSocial(activity))
            return false;
        if(activity == RoutineData.Activity.VISIT) {
            table.visitHousehold = table.householdToVisit(world, home);
            if(table.visitHousehold == null)
                return false;
        }
        if(!onDuty)
            return true;
        if(walker.riding() && !mounted(activity) || underground && isOutdoors(activity))
            return false;
        return switch (activity) {
            case HUNT -> hunting.huntingGround(world, home, time) >= 0 && !hunting.leftToHunters(world);
            case FARM -> farming.fieldsNeedWork(world, home);
            case WORK -> station.hasWork(world, routine, home, time);
            default -> true;
        };
    }

    // Done outside, on the ground around the place. Posts are inspected where they're marked, wherever that is
    private boolean isOutdoors(RoutineData.Activity activity) {
        return switch (activity) {
            case HUNT, FORAGE, FISH, CHOP, PATROL -> true;
            case INSPECT -> site.getMarkers(MarkerKind.GUARD_POST).isEmpty() && site.getMarkers(MarkerKind.ENTRANCE).isEmpty();
            default -> false;
        };
    }

    private boolean isHeld(RoutineData.Activity activity, long time) {
        return heldUntil.getOrDefault(activity, 0L) > time;
    }

    // Each failure in a row holds the task longer, so the npc spends the time on its other tasks
    void hold(RoutineData.Activity activity, long time) {
        int count = Math.min(strikes.getOrDefault(activity, 0) + 1, MAXIMUM_STRIKES);
        strikes.put(activity, count);
        heldUntil.put(activity, time + (long) HOLD * count);
    }

    private boolean drifted(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
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

    // Routines only run where a player is near enough to see them
    boolean isWatched(ServerWorld world) {
        // isPlayerInRange ignores spectators, and the test harness watches as one
        if(NpcDebug.ENABLED && world.getPlayers().stream().anyMatch(player -> player.squaredDistanceTo(npc) < PLAYER_RANGE * PLAYER_RANGE))
            return true;
        return world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE);
    }

    private boolean findSite(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(managerPos != null) {
            // Asked for again at every start. A place nobody was near for a while is forgotten, and the one kept here
            // would no longer be the one its manager and its neighbours use
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
        if(site == null)
            site = RoutineSites.forWild(world, home, npc.getFactionIdentifier());
        return site != null;
    }

    private boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        lookTarget = null;
        carried = null;
        carriedCount = 1;
        facing = null;
        // Falls back to another duty when the build lacks what this one needs
        if(picked == RoutineData.Activity.POST) {
            String noPost = post.claimPost(world, home);
            if(noPost != null) {
                fallsBack(picked, noPost + ", patrols instead");
                picked = RoutineData.Activity.PATROL;
            }
        }
        if(picked == RoutineData.Activity.FOLLOW && follow.findLord(world) == null) {
            fallsBack(picked, "no lord to follow in this place, patrols instead");
            picked = RoutineData.Activity.PATROL;
        }
        if(picked == RoutineData.Activity.COURT && site.getMarkers(MarkerKind.LORD_SEAT).isEmpty()) {
            fallsBack(picked, "no lord seat marked in this build, sits at a table instead");
            picked = RoutineData.Activity.TABLE;
        }
        if(!activities.get(picked).begin(world, picked, home))
            return false;
        activity = picked;
        homeward = false;
        npc.setFarming(picked == RoutineData.Activity.FARM);
        // Only npcs with a home behind walls are worth escorting out and back
        npc.setOnOuting(npc.getStructureManagerHostPos() != null
                && (picked == RoutineData.Activity.FORAGE || picked == RoutineData.Activity.HUNT || picked == RoutineData.Activity.FISH || picked == RoutineData.Activity.CHOP));
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
        // A sleeping npc is still ticked, so the routine has to be what keeps it in bed
        if(phase == Phase.SLEEPING)
            return npc.isSleeping() && npc.getTarget() == null;
        return (phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING)
                && npc.getTarget() == null && (!npc.hasVehicle() || npc.getVehicle() instanceof SeatEntity || walker.riding());
    }

    public void start() {
        // Work that a fight or a fright interrupted isn't resumed from wherever the npc ended up. It goes home instead
        if(phase == Phase.WORKING && hasLeftWork() && npc.getEntityWorld() instanceof ServerWorld world) {
            abandoned("left its work, " + interruptedBy);
            startReturn(world);
        }
        walker.resetWalk();
        thinkCooldown = 0;
        showProp();
    }

    // Hunting, foraging and farming continue wherever the npc is, and a bodyguard looks for its lord again
    private boolean hasLeftWork() {
        return switch (activity) {
            case HUNT, FORAGE, FARM, FOLLOW -> false;
            default -> !spot.isWithinDistance(npc.getEntityPos(), LEFT_WORK_DISTANCE);
        };
    }

    public void stop() {
        if(phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING) {
            LivingEntity foe = npc.getTarget();
            // Every npc has the same entity type, so they're told apart by npc class
            String foeName = foe == null ? "" : foe instanceof NpcEntity other && other.getNpcClass() != null ? other.getNpcClass().getId().getPath()
                    : Registries.ENTITY_TYPE.getId(foe.getType()).getPath();
            // A task resumed after the fight isn't one given up. It only counts as given up if the npc has to leave it
            interruptedBy = foe != null ? "interrupted by a fight with " + foeName
                    : npc.hasVehicle() && !(npc.getVehicle() instanceof SeatEntity) && !walker.riding() ? "interrupted, it is riding" : "interrupted by something else, such as fleeing";
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s paused %s (%s) at %d,%d,%d: %s".formatted(who(), activity.asString(),
                    phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), interruptedBy));
        }
        walker.stopMoving();
        sitting.standUp();
        // The npc needs its hands back when a fight interrupts
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
            if(phase != Phase.WORKING)
                abandoned("ran out of time");
            startReturn(world);
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

    // Every task given up before it was done goes in the debug log with its reason, to find where npcs' days go wrong
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

    // What the build lacks, so the npc does something else in its place. Not a task given up, and noted once
    void fallsBack(RoutineData.Activity what, String why) {
        fallsBack(what.asString(), why);
    }

    void fallsBack(String what, String why) {
        if(!notedFallbacks.add(what + ": " + why))
            return;
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s falls back from %s at %d,%d,%d: %s".formatted(who(), what, pos.getX(), pos.getY(), pos.getZ(), why));
    }

    // How far off the bed was when the npc gave up on it, across the floor and up or down
    private String bedDistance() {
        BlockPos bed = npc.getAssignedBed();
        if(bed == null)
            return " (no bed assigned)";
        double across = Math.hypot(bed.getX() + 0.5 - npc.getX(), bed.getZ() + 0.5 - npc.getZ());
        int rise = bed.getY() - npc.getBlockY();
        return " (bed %.1f blocks away, %s)".formatted(across, rise == 0 ? "same floor" : rise > 0 ? rise + " up" : -rise + " down");
    }

    // Every task done is in the log too, to count what the npcs get done
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
        if(phase != Phase.RETURNING && activity == RoutineData.Activity.INSPECT && inspect.skipStop(world, why))
            return;
        if(phase == Phase.RETURNING && toBed) {
            // Stopped short, but near enough to lie down after all
            BlockPos bed = beds.freeBed(world);
            if(bed == null || !beds.isInReach(bed) || !beds.canSeeBed(world, bed))
                abandoned("could not reach its bed, " + why);
            beds.goToBed(world);
            return;
        }
        boolean skipsStretch = phase != Phase.RETURNING && activity == RoutineData.Activity.PATROL && patrol.canSkipStretch();
        abandoned((skipsStretch ? "skipped a stretch of its round, " : "") + why);
        if(skipsStretch) {
            // A blocked stretch of the round is skipped, and the rest of it still runs
            failed = false;
            patrol.skipStretch();
            return;
        }
        if(phase == Phase.RETURNING) {
            homeStrikes = Math.min(homeStrikes + 1, MAXIMUM_STRIKES);
            homeHeldUntil = world.getTime() + (long) HOLD * homeStrikes;
            // A bed behind a wall can't be stood on, so the doorstep is home enough
            finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
        } else {
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
        return phase == Phase.WORKING && (isSocial(activity) || activity == RoutineData.Activity.COURT);
    }

    boolean onDuty(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        return routine != null && routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
    }

    @Nullable Vec3d facingTarget() {
        return facing == null ? null : new Vec3d(npc.getX() + facing.getOffsetX() * LOOK_DISTANCE, npc.getEyeY(), npc.getZ() + facing.getOffsetZ() * LOOK_DISTANCE);
    }

    // Riders keep to the watch. The other work is done on foot
    private static boolean mounted(RoutineData.Activity activity) {
        return activity == RoutineData.Activity.PATROL || activity == RoutineData.Activity.POST || activity == RoutineData.Activity.INSPECT;
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

    // The floor beside a block. A seat on a bench between the table, a wall and the next seat has none, and is reached
    // across the next seat, from a floor a step further off that can see it
    BlockPos standAt(ServerWorld world, BlockPos block) {
        BlockPos stand = NestSurvey.standNextTo(world, block, npc.getBlockPos());
        if(stand == null)
            stand = NestSurvey.standInSight(world, block, npc.getBlockPos(), STORE_STAND_RADIUS);
        return stand != null ? stand : block;
    }

    // Aimed at the side of the block facing the npc. From eye height, a ray to the middle of a block a step away
    // crosses the block above it, which hides a forge under a stone hood
    boolean canSee(ServerWorld world, BlockPos block) {
        Vec3d eye = npc.getEyePos();
        Vec3d middle = Vec3d.ofCenter(block);
        // Every side facing the npc is tried, because from a corner one of them is behind the next block
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

    private static boolean isSocial(RoutineData.Activity activity) {
        return activity == RoutineData.Activity.VISIT || activity == RoutineData.Activity.TABLE;
    }

    void finish(ServerWorld world, int rest) {
        // Logs the tasks seen through to the end, to set against the ones given up
        if(phase != Phase.NONE && !failed) {
            // Only a task seen through clears what went wrong before. A hunter that found nothing again keeps away
            // longer
            if(phase != Phase.SLEEPING && !toBed && !homeward)
                strikes.remove(activity);
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s finished %s at %d,%d,%d".formatted(who(), phase == Phase.SLEEPING ? "sleep" : toBed ? "bed" : homeward ? "home" : activity.asString(),
                    pos.getX(), pos.getY(), pos.getZ()));
        }
        failed = false;
        sitting.standUp();
        meetSeat = null;
        table.companion = null;
        if(site != null)
            site.releasePost(npc.getUuid());
        toBed = false;
        phase = Phase.NONE;
        dropOff = null;
        carriedCount = 1;
        station.clear();
        npc.setOnOuting(false);
        npc.setFarming(false);
        carried = null;
        lookTarget = null;
        restUntil = world.getTime() + rest;
        npc.clearRoutineProp();
        if(npc.isSleeping())
            npc.wakeUp();
    }

    private boolean isDark() {
        long time = npc.getEntityWorld().getTimeOfDay() % DAY;
        return time >= DUSK && time < DAWN;
    }

    void showProp() {
        Item prop = null;
        if(phase == Phase.RETURNING)
            prop = carried;
        else if(phase == Phase.GOING || phase == Phase.WORKING) {
            if((activity == RoutineData.Activity.PATROL || activity == RoutineData.Activity.POST || activity == RoutineData.Activity.FOLLOW || activity == RoutineData.Activity.INSPECT) && isDark())
                prop = Items.TORCH;
            else if(activity == RoutineData.Activity.FISH && !ownsRod)
                prop = Items.FISHING_ROD;
            else if(activity == RoutineData.Activity.FARM && !(npc.getMainHandStack().getItem() instanceof HoeItem))
                prop = Items.IRON_HOE;
            else if(activity == RoutineData.Activity.CHOP && !npc.getMainHandStack().isIn(ItemTags.AXES))
                prop = Items.IRON_AXE;
            else if(activity == RoutineData.Activity.MINE && !npc.getMainHandStack().isIn(ItemTags.PICKAXES))
                prop = Items.IRON_PICKAXE;
        }

        if(prop == null)
            npc.clearRoutineProp();
        else
            npc.showRoutineProp(new ItemStack(prop));
    }
}
