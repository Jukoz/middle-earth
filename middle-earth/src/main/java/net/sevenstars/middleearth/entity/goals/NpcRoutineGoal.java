package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.WorldEvents;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
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
import net.sevenstars.middleearth.dev.NpcDebug;
import net.sevenstars.middleearth.dev.NpcFailureRecorder;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.pathing.NpcNavigation;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.entity.npcs.routine.FarmPlots;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineGame;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineStores;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

// Sends an npc out to fish, hunt, forage or patrol, then back home with what it got
public class NpcRoutineGoal extends Goal {
    private enum Phase { NONE, GOING, WORKING, HUNTING, RETURNING, SLEEPING }

    // Delays are in checks, made every other tick
    private static final int IDLE_DELAY = 100;
    private static final int IDLE_DELAY_VARIATION = 60;
    private static final int WAITING_DELAY = 10;
    private static final int SITE_NOT_READY_DELAY = WAITING_DELAY * 2;
    private static final int FIRST_CHECK_DELAY = 20;
    private static final int FIRST_CHECK_ID_FACTOR = 31;
    private static final int FIRST_CHECK_SPREAD = 300;
    // Goal ticks between two looks at the world, 10 a second
    private static final int THINK_INTERVAL = 5;

    private static final int PLAYER_RANGE = Integer.getInteger("me.routineRange", 48);
    private static final int SPOT_RANGE = 64;
    private static final int WILD_HOME_RANGE = 64;
    // A settled npc further from home than this walks back before anything else
    private static final int STRAY_DISTANCE = 24;
    private static final double ARRIVAL_DISTANCE = 2.5;
    private static final int HOME_DISTANCE = 12;
    private static final int DRIFT_DISTANCE = 48;
    private static final double BED_REACH = 6;
    private static final double BED_HEIGHT_REACH = 5;
    // Just over the top of a bed, where it is seen across a footboard
    private static final double BED_TOP = 0.6;
    // A table this near home is taken before one further off
    private static final int TABLE_RANGE = 40;
    // Times the nodes a path search may look at, on the way to bed
    private static final float BED_SEARCH = 4;
    private static final double STORE_REACH = 3.5;
    private static final double STORE_ARRIVAL_DISTANCE = 1.5;
    private static final double SEAT_ARRIVAL_DISTANCE = 2;
    private static final double SAT_FROM_RANGE = 5;
    // How far from a store with no floor beside it a floor that sees it is looked for
    private static final int STORE_STAND_RADIUS = 2;
    // Just inside the side of a block, so that a ray aimed there ends in the block
    private static final double FACE_INSET = 0.49;

    private static final int DIRECT_DISTANCE = 18;
    // Blocks over the feet, past which a place is indoors or underground
    private static final int COVER_HEIGHT = 2;
    // Tops of the shapes under and in the feet's block: a fence or a wall is over the first, a slab or a carpet under the second
    private static final double BLOCK_HEIGHT = 1;
    private static final double LOW_THING_HEIGHT = 0.5;
    // Near enough to the end of a stretch to have walked it
    private static final double LEG_END_REACH = 2;
    // Stretches walked to their end that came no nearer, before the walk counts as stuck
    private static final int IDLE_LEGS = 3;
    // Nodes of a planned way between the places walked to one stretch at a time, and how near one counts as come up to
    private static final int WAYPOINT_SPACING = 16;
    private static final double WAYPOINT_REACH = 2.5;
    // A door this near the place, once the nearest has been tried
    private static final int FAR_DOOR_REACH = 32;
    // A door this near a place is taken for its way in, and one this near the npc for the door it is already at
    private static final int DOOR_REACH = 16;
    private static final double AT_DOOR = 2.5;
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
    // A task that went wrong is put by this long, longer for each time in a row it went wrong, up to three times as long
    private static final int HOLD = 20 * 120;
    private static final int MAXIMUM_STRIKES = 3;
    // Another task is tried this many times when the one picked cannot be begun
    private static final int PICK_ATTEMPTS = 3;
    // A station there was no standing at, or no seeing from where the npc stood, is passed over this long
    private static final int STATION_HOLD = 20 * 300;
    // How far an npc stranded where no path starts looks for a floor to step to
    private static final int STEP_OFF_RADIUS = 3;
    private static final int STEP_OFF_DEPTH = 3;

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
    private static final int FARMING_TIME = 20 * 90;
    private static final int FARMING_TIME_VARIATION = 20 * 60;
    private static final int CHOPPING_TIME = 20 * 40;
    private static final int CHOPPING_TIME_VARIATION = 20 * 20;
    private static final int MINING_TIME = 20 * 40;
    private static final int MINING_TIME_VARIATION = 20 * 20;
    private static final int CHASE_TIME = 20 * 30;
    private static final int WATCH_TIME = 20 * 3;
    private static final int WATCH_TIME_VARIATION = 20 * 4;
    private static final int MEETING_TIME = 20 * 20;
    private static final int MEETING_TIME_VARIATION = 20 * 20;
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;
    private static final int COURT_TIME = 20 * 120;
    private static final int COURT_TIME_VARIATION = 20 * 60;
    private static final int INSPECT_TIME = 20 * 6;
    private static final int INSPECT_TIME_VARIATION = 20 * 6;
    private static final int LORD_SEARCH_DELAY = 20 * 5;
    private static final int WORK_TIME = 20 * 40;
    private static final int WORK_TIME_VARIATION = 20 * 40;
    // How long a household asked to have a visitor over keeps it in mind
    private static final int INVITATION_TIME = 20 * 60;
    private static final int TEA_TIME = 20 * 45;
    private static final int TEA_TIME_VARIATION = 20 * 45;
    private static final int DEBUG_NOTE_INTERVAL = 600;

    private static final int HUNT_ROAM = 10;
    private static final int FORAGE_ROAM = 3;
    private static final double ROAM_SPEED = 0.75;
    // From where the farmer stands to the plot it works
    private static final double FIELD_REACH = 3;
    // The last trees worked at are left alone for a while
    private static final int RECENT_TREES = 3;
    private static final int INSPECT_RING_STOPS = 4;
    private static final int MINIMUM_ROUTE_POINTS = 2;
    private static final int MINIMUM_RING_POINTS = 3;
    // From the marker of the lord's seat to the seat that is its own
    private static final int LORD_SEAT_REACH = 3;
    private static final double FOLLOW_NEAR = 3.5;
    private static final double FOLLOW_FAR = 5.5;
    private static final int[] FOLLOW_HEIGHTS = {0, -1, 1};
    // Squared distance under which two places are the same one, and there is no heading from one to the other
    private static final double NO_HEADING = 0.01;
    // Where a bodyguard stands while the lord sits, from the lord
    private static final int FOLLOW_POST_RANGE = 8;
    // Looks, of half a second, between two corrections of where a bodyguard walks to
    private static final int FOLLOW_REPATH = 6;
    private static final int LORD_SEARCH_RANGE = 32;
    private static final int LORD_HEIGHT_RANGE = 48;
    // A rider's round lies this far outside the one on foot
    private static final int RIDER_MARGIN = 18;
    private static final int ESCORT_RANGE = 24;
    // From home. Nobody crosses the whole town to somebody else's forge
    private static final int WORK_RANGE = 48;
    private static final int STABLE_RANGE = 12;
    // Plots this close to a field a farmer's bed is linked to are its own
    private static final int LINKED_FIELD_RANGE = 8;
    private static final int VISIT_SEAT_RANGE = 10;
    private static final int HOST_RANGE = 24;
    private static final int VISIT_TRIES = 4;
    // A hive fills over a day, and its honey is taken once it has stood full for another
    private static final long DAY = 24000;
    private static final int FULL_HONEY = 5;
    private static final long HIVE_READY_TIME = 2 * DAY;
    private static final double HORSE_REACH = 4;
    private static final int HORSE_HEIGHT = 8;
    private static final int PLANKS_PER_LOG = 4;
    // Past the edge of the buildings, so that the animals of the yards and the green are left alone
    private static final int SETTLEMENT_MARGIN = 12;
    // Time of day
    private static final int DUSK = 12500;
    private static final int DAWN = 23500;
    // In blocks, how far the place to stand by the lord has to move for the walk to it to start over
    private static final int FOLLOW_SPOT_SHIFT = 3;
    // In blocks from the place of its work, past which an npc that was interrupted has left it
    private static final double LEFT_WORK_DISTANCE = 4;
    private static final int COMPANION_RANGE = 6;
    private static final int COMPANION_HEIGHT_RANGE = 2;
    private static final int ESCORT_HEIGHT_RANGE = 8;
    // Only those it can see: nobody talks to, or sets out with, somebody on the other side of a wall
    private static final TargetPredicate COMPANIONS = TargetPredicate.createNonAttackable();

    private static final int PREY_RANGE = 24;
    private static final int PREY_HEIGHT_RANGE = 6;
    private static final int BYSTANDER_DISTANCE = 5;
    private static final int LOOK_DISTANCE = 5;
    private static final List<Item> DEFAULT_FORAGE = List.of(Items.BROWN_MUSHROOM, Items.SWEET_BERRIES, Items.APPLE);
    private static final Set<RoutineSite> DEBUG_LOGGED = new HashSet<>();

    // One in this many looks, while working
    private static final int FISH_ESCAPE_ONE_IN = 5;
    private static final int FISH_SPLASH_ONE_IN = 12;
    private static final int CHOP_SWING_ONE_IN = 3;
    private static final int MINE_SWING_ONE_IN = 3;
    private static final int STABLE_SWING_ONE_IN = 3;
    private static final int STABLE_SOUND_ONE_IN = 6;
    private static final int STABLE_HEART_ONE_IN = 12;
    private static final int STATION_SWING_ONE_IN = 4;
    private static final int STATION_SOUND_ONE_IN = 3;
    private static final int FORAGE_SWING_ONE_IN = 3;
    private static final int FORAGE_ROAM_ONE_IN = 4;
    private static final int PREY_SEARCH_ONE_IN = 3;
    private static final int HUNT_ROAM_ONE_IN = 4;
    private static final int GESTURE_ONE_IN = 10;
    private static final int FARM_ACT_ONE_IN = 2;

    private static final float STORE_SOUND_VOLUME = 0.4f;
    private static final float SPLIT_SOUND_VOLUME = 0.8f;
    private static final float SPLASH_SOUND_VOLUME = 0.2f;
    private static final float HORSE_SOUND_VOLUME = 0.6f;
    private static final float CHOP_SOUND_VOLUME = 0.8f;
    private static final float CHOP_SOUND_PITCH = 0.9f;
    private static final float CHOP_SOUND_PITCH_VARIATION = 0.2f;
    private static final float MINE_SOUND_VOLUME = 0.8f;
    private static final float MINE_SOUND_PITCH = 0.8f;
    private static final float MINE_SOUND_PITCH_VARIATION = 0.2f;
    private static final float WORK_SOUND_VOLUME = 0.5f;
    private static final float WORK_SOUND_PITCH = 0.9f;
    private static final float WORK_SOUND_PITCH_VARIATION = 0.2f;

    private static final int CRACK_PARTICLES = 4;
    private static final double CRACK_SPREAD = 0.25;
    private static final double CRACK_SPEED = 0.05;
    private static final int SMOKE_PARTICLES = 6;
    private static final double SMOKE_HEIGHT = 0.8;
    private static final double SMOKE_SPREAD = 0.2;
    private static final double SMOKE_SPEED = 0.02;
    private static final int SPLASH_PARTICLES = 6;
    private static final double SPLASH_HEIGHT = 0.5;
    private static final double SPLASH_SPREAD = 0.2;
    private static final double HEART_HEIGHT = 0.3;
    private static final double HEART_SPREAD = 0.2;
    private static final double HEART_SPREAD_HEIGHT = 0.1;

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
    // Where the path of the last stretch was to end, when it led all the way there
    private @Nullable BlockPos lastLegEnd = null;
    private double bestLegDistance = Double.MAX_VALUE;
    private int idleLegs = 0;
    private boolean lastLegMoved = true;
    // Doors already tried as the way in on this walk
    private final Set<BlockPos> triedDoors = new HashSet<>();
    // The whole way to the place of this walk, once planned
    private @Nullable Vec3d satFrom = null;
    // Where what is carried back goes, when not the household's store: a woodcutter's logs go on the wood pile
    private @Nullable BlockPos dropOff = null;
    // The seat at its own table a visitor asked it to, while it comes home for the visit
    private @Nullable BlockPos hostingAt = null;
    private String interruptedBy = "";
    // The household the pick found somebody in, for the visit to go to
    private @Nullable BlockPos visitHousehold = null;
    private boolean planned = false;
    private @Nullable List<BlockPos> waypoints = null;
    private int waypointIndex = 0;
    private @Nullable Path watchedPath = null;
    private int watchedNode = 0;
    private double watchedDistance = 0;
    private int watchedThinks = 0;
    // Grows each time a routine has to be abandoned, so that an npc with no way out stops trying
    private final Map<RoutineData.Activity, Long> heldUntil = new EnumMap<>(RoutineData.Activity.class);
    private final Map<RoutineData.Activity, Integer> strikes = new EnumMap<>(RoutineData.Activity.class);
    private final Map<BlockPos, Long> unusableStations = new HashMap<>();
    private final Set<String> notedFallbacks = new HashSet<>();
    // The way home and the way to bed are held back the same way once they fail
    private long homeHeldUntil = 0;
    private int homeStrikes = 0;
    private long bedHeldUntil = 0;
    private int bedStrikes = 0;
    private boolean steppedOff = false;
    // Set once the task under way has been given up, so that it is not counted as finished as well
    private boolean failed = false;
    private int ringIndex = 0;
    private int ringStep = 1;
    private int ringLeft = 0;
    private int laps = 0;
    private boolean ownsRod = false;
    private boolean toBed = false;
    private @Nullable NpcEntity companion = null;
    private @Nullable BlockPos meetSeat = null;
    private @Nullable BlockPos workStation = null;
    // The animal a stable master is seeing to
    private @Nullable AnimalEntity workAnimal = null;
    // A cook first fetches something raw from a store, then cooks it at the station and takes it back
    private @Nullable BlockPos fetchFrom = null;
    private @Nullable BlockPos cookStand = null;
    // An oven burns planks, split from the logs the woodcutters bring in. A hearth is a fire already
    private int planks = 0;
    private @Nullable BlockPos fuelFrom = null;
    private @Nullable Item logInHand = null;
    private @Nullable BlockPos fieldCell = null;
    private long farmingEnd = 0;
    // Plots seen to be behind a fence or a wall from where they were to be worked, passed over for the rest of the day
    private final Set<BlockPos> hiddenPlots = new HashSet<>();
    // Going back with no task behind it, after straying or at the end of the day
    private boolean homeward = false;
    private @Nullable BlockPos standOf = null;
    private @Nullable BlockPos storeStand = null;
    // The foot of the trunk being worked at, and the rock face being worked
    private @Nullable BlockPos tree = null;
    private @Nullable BlockPos rockFace = null;
    private final ArrayDeque<BlockPos> recentTrees = new ArrayDeque<>();
    // Which way the marker of the post, the seat or the stop being visited faces
    private @Nullable Direction facing = null;
    private final ArrayDeque<StructureFeatureData.Marker> stops = new ArrayDeque<>();
    private @Nullable UUID lordId = null;
    private long lordSearchAt = 0;
    private int followCheck = 0;
    private long debugNoteAt = 0;

    public String debugLabel() {
        debugLogRing();
        String what = switch (activity) {
            case FISH -> "fishing";
            case HUNT -> "hunting";
            case FORAGE -> "foraging";
            case PATROL -> "patrol";
            case VISIT -> npc.getVehicle() instanceof SeatEntity ? "visiting, seated" : "visiting";
            case WORK -> "at work";
            case POST -> "on guard";
            case ESCORT -> "escort";
            case TABLE -> npc.getVehicle() instanceof SeatEntity ? "sitting at a table" : "table";
            case FARM -> fieldCell == null ? "farming" : "farming at " + fieldCell.toShortString();
            case CHOP -> "chopping";
            case MINE -> "mining";
            case COURT -> npc.getVehicle() instanceof SeatEntity ? "holding court, seated" : "holding court";
            case INSPECT -> "inspecting the posts";
            case FOLLOW -> "following its lord";
        };
        String load = carried == null ? "" : " [carrying " + carried.getName().getString() + "]";
        return switch (phase) {
            case GOING -> what + " (walking there)";
            case WORKING -> what + (activity == RoutineData.Activity.PATROL ? " (on watch)" : activity == RoutineData.Activity.HUNT ? " (stalking)" : "") + load;
            case HUNTING -> "hunting (chasing prey)";
            case RETURNING -> (toBed ? "going to bed" : homeward ? "going home" : what + " (heading home)") + load;
            case SLEEPING -> "sleeping";
            case NONE -> "idle";
        };
    }
    public boolean debugActive() { return phase != Phase.NONE && phase != Phase.SLEEPING; }
    public boolean debugReturning() { return phase == Phase.RETURNING; }
    public boolean debugToBed() { return toBed; }
    public RoutineData.Activity debugActivity() { return activity; }
    public BlockPos debugSpot() { return spot; }
    public @Nullable Vec3d debugLookTarget() { return lookTarget; }
    public @Nullable NpcEntity debugCompanion() { return companion; }
    public @Nullable Item debugCarried() { return carried; }
    public List<BlockPos> debugRing() { return site == null ? List.of() : site.getRing(); }
    public int debugRingIndex() { return ringIndex; }
    public void debugLogRing() {
        if(NpcDebug.ENABLED && site != null && activity == RoutineData.Activity.PATROL && phase != Phase.NONE && DEBUG_LOGGED.add(site))
            System.out.println("RINGDEBUG center " + site.getCenterX() + "," + site.getCenterZ() + " extent " + site.getExtent() + " ring " + site.getRing().stream().map(p -> p.getX() + "," + p.getZ()).toList());
    }

    public NpcRoutineGoal(NpcEntity npc, double speed) {
        this.npc = npc;
        this.speed = speed;
        // Spreads the first check of each npc over half a minute
        this.delay = FIRST_CHECK_DELAY + Math.floorMod(npc.getId() * FIRST_CHECK_ID_FACTOR, FIRST_CHECK_SPREAD);
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
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
        if(!(npc.getWorld() instanceof ServerWorld world))
            return false;

        if(phase == Phase.HUNTING)
            return finishHunt(world);
        if(phase == Phase.SLEEPING)
            phase = Phase.NONE;
        boolean seated = npc.getVehicle() instanceof SeatEntity;
        // Saved at the table, and the meeting it sat down for is forgotten
        if(seated && phase == Phase.NONE) {
            standUp();
            meetSeat = null;
        }
        if(npc.getTarget() != null || (npc.hasVehicle() && !seated && !riding()))
            return false;
        if(phase != Phase.NONE) {
            if(world.getTime() > deadline && phase != Phase.RETURNING) {
                // At work when the time is up is the work done, a long shift at a post among it
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
        // Wandering takes settled npcs out of their village for good, seen or not. One that could not get home rests first,
        // as after any other walk given up, or it tries again and again from the same spot
        if(time >= restUntil && time >= homeHeldUntil && drifted(world)) {
            carried = null;
            homeward = true;
            failed = false;
            startReturn(world);
            return true;
        }
        RoutineData routine = npc.getRoutine();
        if(routine == null) debugWhyNot(world, "no routine");
        if(routine == null)
            return false;
        // Where a wild npc first stands is its camp, before wandering takes it elsewhere
        if(npc.getRoutineHome() == null && npc.getStructureManagerHostPos() == null)
            npc.setRoutineHome(npc.getBlockPos());
        // Keeps a village from setting out all at once when its chunks load
        if(restUntil == 0)
            restUntil = time + npc.getRandom().nextInt(REST + REST_VARIATION);
        if(time < restUntil) debugWhyNot(world, "resting for " + (restUntil - time));
        if(time < restUntil && !npc.hasInvitation(time))
            return false;
        // Bed time comes whether anyone is watching or not
        // A rider keeps to its mount through the night, and has no bed to lie down in
        BlockPos restingHome = npc.getStructureManagerHostPos() == null || riding() ? null : npc.getRoutineHome();
        if(restingHome != null && npc.isRestTime())
            return bedTime(world, time, restingHome);
        if(!isWatched(world)) debugWhyNot(world, "no player within " + PLAYER_RANGE);
        if(!isWatched(world))
            return false;
        if(!RoutineSites.takeStart(world)) {
            delay = WAITING_DELAY + npc.getRandom().nextInt(WAITING_DELAY);
            return false;
        }

        if(!findSite(world))
            return false;
        site.touch(time);
        site.claim(npc.getFactionIdentifier());
        if(!site.isReady()) debugWhyNot(world, "site not ready");
        if(!site.isReady()) {
            delay = SITE_NOT_READY_DELAY;
            return false;
        }

        BlockPos home = npc.getRoutineHome();
        // Somebody is calling on its household, and it comes home to sit with them
        hostingAt = npc.takeInvitation(time);
        if(hostingAt != null && !npc.isRestTime() && begin(world, RoutineData.Activity.VISIT, home))
            return true;
        hostingAt = null;
        RoutineData.Activity picked = pickActivity(world, routine, home, time);
        if(picked == RoutineData.Activity.ESCORT) {
            findWard(world);
            return false;
        }
        if(picked == null) debugWhyNot(world, "nothing picked, on duty " + routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift()));
        // A task that cannot be begun is put by, and another one tried, before the npc settles for doing nothing
        for(int attempt = 0; attempt < PICK_ATTEMPTS && picked != null && picked != RoutineData.Activity.ESCORT; attempt++) {
            if(begin(world, picked, home))
                return true;
            picked = pickActivity(world, routine, home, time);
        }

        restUntil = time + NOTHING_TO_DO_REST;
        return goHomeOffDuty(world, time, home);
    }

    private boolean isEscortOut(ServerWorld world) {
        // An escort stays with its ward until that one is home again, or lost
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
        // Without a bed, staying around the house is all the rest there is
        if(freeBed(world) == null && restingHome.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            restUntil = time + NOTHING_TO_DO_REST;
            return false;
        }
        if(time < bedHeldUntil) {
            restUntil = bedHeldUntil;
            return false;
        }
        carried = null;
        toBed = freeBed(world) != null;
        homeward = true;
        failed = false;
        startReturn(world);
        return true;
    }

    private boolean goHomeOffDuty(ServerWorld world, long time, BlockPos home) {
        // Off duty, a settled npc that drifted away goes home
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
        // Those living deep underground keep to their halls: the hunting grounds and the round of the place are up on the
        // surface, further off down the halls and up the stairs than any walk is looked for
        // A wild npc has no house over its head, and any roof over its camp is the roof of a cave
        boolean underground = npc.getStructureManagerHostPos() == null ? isUnderCover(world, home) : RoutineSite.isEnclosed(world, home.getX(), home.getZ(), home.getY());
        boolean onDuty = routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
        // Elves need little sleep, and keep company through the night
        if(!onDuty && (!npc.isElf() || npc.isRestTime()))
            return null;
        // Each task is judged once: whether there is somebody to visit is a matter of chance, and what was found possible
        // has to stay so while the pick is made
        visitHousehold = null;
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
            visitHousehold = householdToVisit(world, home);
            if(visitHousehold == null)
                return false;
        }
        if(!onDuty)
            return true;
        if(riding() && !mounted(activity) || underground && isOutdoors(activity))
            return false;
        return switch (activity) {
            case HUNT -> huntingGround(world, home, time) >= 0 && !leftToHunters(world);
            case FARM -> fieldsNeedWork(world, home);
            case WORK -> hasWork(world, routine, home, time);
            default -> true;
        };
    }

    // Done out on the ground around the place. Posts are inspected where they are marked, wherever that is
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

    // Each failure in a row holds the task longer, so that the npc spends the time on its other tasks
    private void hold(RoutineData.Activity activity, long time) {
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
            BlockPos nest = world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager ? manager.findNestOrigin(npc.getUuid()) : null;
            home = nest != null ? nest : managerPos;
            npc.setRoutineHome(home);
        }
        return !home.isWithinDistance(npc.getBlockPos(), DRIFT_DISTANCE);
    }

    // Routines are only run where a player is near to see them
    private boolean isWatched(ServerWorld world) {
        // isPlayerInRange leaves out spectators, and the test harness watches as one
        if(NpcDebug.ENABLED && world.getPlayers().stream().anyMatch(player -> player.squaredDistanceTo(npc) < PLAYER_RANGE * PLAYER_RANGE))
            return true;
        return world.isPlayerInRange(npc.getX(), npc.getY(), npc.getZ(), PLAYER_RANGE);
    }

    private boolean findSite(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(managerPos != null) {
            // Asked for again at every start: a place nobody was near for a while is forgotten, and the one kept here would
            // no longer be the one its manager and its neighbours use
            RoutineSite found = RoutineSites.forManager(world, managerPos);
            if(found != null)
                site = found;
            if(site == null)
                debugWhyNot(world, "no site for its manager at " + managerPos.toShortString());
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
        facing = null;
        boolean sentry = NpcJobs.SENTRY.getValue().equals(npc.getJobId());
        // What the build lacks, the duty falls back from
        if(picked == RoutineData.Activity.POST && sentry && site.getMarkers(MarkerKind.LOOKOUT).isEmpty()) {
            fallsBack(picked, "no lookout marked in this build, patrols instead");
            picked = RoutineData.Activity.PATROL;
        }
        if(picked == RoutineData.Activity.FOLLOW && findLord(world) == null) {
            fallsBack(picked, "no lord to follow in this place, patrols instead");
            picked = RoutineData.Activity.PATROL;
        }
        if(picked == RoutineData.Activity.COURT && site.getMarkers(MarkerKind.LORD_SEAT).isEmpty()) {
            fallsBack(picked, "no lord seat marked in this build, sits at a table instead");
            picked = RoutineData.Activity.TABLE;
        }
        switch (picked) {
            case FISH -> { if(!beginFish(picked, home, random)) return false; }
            case HUNT -> { if(!beginHunt(world, picked, home)) return false; }
            case FORAGE -> { if(!beginForage(picked, home, random)) return false; }
            case VISIT -> { if(!beginVisit(world, picked, home)) return false; }
            case WORK -> { if(!beginWork(world, picked, home)) return false; }
            case TABLE -> { if(!beginTable(world, picked, home, random)) return false; }
            case FARM -> { if(!beginFarm(world, picked, home, random)) return false; }
            case CHOP -> { if(!beginChop(world, picked, home)) return false; }
            case MINE -> { if(!beginMine(world, picked, home)) return false; }
            case POST -> beginPost(world, picked, home, sentry);
            case PATROL -> { if(!beginPatrol(world, picked)) return false; }
            case COURT -> beginCourt(world);
            case INSPECT -> { if(!beginInspect(picked, random)) return false; }
            case FOLLOW -> beginFollow(world);
        }
        activity = picked;
        homeward = false;
        // Only those with a home behind walls are worth seeing out and back
        npc.setFarming(picked == RoutineData.Activity.FARM);
        npc.setOnOuting(npc.getStructureManagerHostPos() != null
                && (picked == RoutineData.Activity.FORAGE || picked == RoutineData.Activity.HUNT || picked == RoutineData.Activity.FISH || picked == RoutineData.Activity.CHOP));
        ownsRod = npc.getMainHandStack().getItem() instanceof FishingRodItem;
        phase = Phase.GOING;
        failed = false;
        deadline = world.getTime() + ROUTINE_TIME_LIMIT;
        return true;
    }

    private boolean beginFish(RoutineData.Activity picked, BlockPos home, Random random) {
        int index = pickNear(site.getShoreStands(), home, SPOT_RANGE + site.getExtent(), random);
        if(index < 0)
            return cannotStart(picked, "no shore within reach of home");
        spot = site.getShoreStands().get(index);
        lookTarget = Vec3d.ofCenter(site.getShoreWater(index));
        return true;
    }

    private boolean beginHunt(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        int index = huntingGround(world, home, world.getTime());
        if(index < 0)
            return cannotStart(picked, "no game seen about the place");
        spot = site.getGrounds().get(index);
        return true;
    }

    private boolean beginForage(RoutineData.Activity picked, BlockPos home, Random random) {
        int index = pickNear(site.getGrounds(), home, SPOT_RANGE + site.getExtent(), random);
        if(index < 0)
            return cannotStart(picked, "no open ground within reach of home");
        spot = site.getGrounds().get(index);
        return true;
    }

    private boolean beginVisit(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(hostingAt != null) {
            meetSeat = isLoaded(world, hostingAt) && SeatBlock.isFree(world, hostingAt) ? hostingAt : null;
            BlockPos beside = meetSeat == null ? NestSurvey.standNextTo(world, hostingAt, npc.getBlockPos()) : null;
            spot = meetSeat != null ? standAt(world, meetSeat) : beside != null ? beside : hostingAt;
            did("visit", "comes home to have a visitor over at " + spot.toShortString());
            hostingAt = null;
            return true;
        }
        BlockPos household = visitHousehold != null ? visitHousehold : householdToVisit(world, home);
        visitHousehold = null;
        if(household == null)
            return cannotStart(picked, "no other household to call on");
        // A visit is to somebody: the household is asked over to its own table
        List<NpcEntity> hosts = hostsAt(world, household);
        // To the table if there is room at it, or else just in at the door
        meetSeat = site.pickSeatNear(world, household, VISIT_SEAT_RANGE);
        BlockPos hostSeat = meetSeat == null ? null : site.pickSeatNear(world, household, VISIT_SEAT_RANGE, meetSeat);
        for(NpcEntity host : hosts)
            host.invite(hostSeat != null ? hostSeat : household, world.getTime() + INVITATION_TIME);
        if(meetSeat != null) {
            spot = standAt(world, meetSeat);
        } else {
            BlockPos beside = NestSurvey.standNextTo(world, household, npc.getBlockPos());
            spot = beside != null ? beside : household;
        }
        return true;
    }

    private boolean beginWork(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        RoutineData routine = npc.getRoutine();
        long time = world.getTime();
        unusableStations.values().removeIf(until -> until <= time);
        workStation = null;
        workAnimal = null;
        // A stable master's work is the animals of its spawn_animal marker
        if(isStableWork()) {
            workAnimal = findAnimal(world, home);
            if(workAnimal == null)
                return cannotStart(picked, "no animal to see to");
            BlockPos byHorse = NestSurvey.standNextTo(world, workAnimal.getBlockPos(), npc.getBlockPos());
            spot = byHorse != null ? byHorse : workAnimal.getBlockPos();
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
            if(isCookWork() && RoutineStores.isOven(world, station) && planks == 0 && woodSource(world, home) == null)
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
            return cannotStart(picked, stations.isEmpty() ? "no station of its trade nearby" : held ? "every station of its trade failed it lately"
                    : "no room to stand at any station of its trade");
        spot = beside;
        if(isCookWork()) {
            BlockPos pantry = storeWithRawFood(world, home);
            if(pantry == null)
                return cannotStart(picked, "nothing raw in any store to cook");
            fetchFrom = pantry;
            cookStand = beside;
            fuelFrom = RoutineStores.isOven(world, workStation) && planks == 0 ? woodSource(world, home) : null;
            spot = standAt(world, pantry);
        }
        return true;
    }

    private boolean beginTable(ServerWorld world, RoutineData.Activity picked, BlockPos home, Random random) {
        meetSeat = site.pickSeat(world, random, home, TABLE_RANGE);
        if(meetSeat == null)
            return cannotStart(picked, "no free seat at any table");
        spot = standAt(world, meetSeat);
        return true;
    }

    private boolean beginFarm(ServerWorld world, RoutineData.Activity picked, BlockPos home, Random random) {
        hiddenPlots.clear();
        fieldCell = nextFieldCell(world, home, npc.getBlockPos());
        if(fieldCell == null)
            return cannotStart(picked, "no field near home needs work");
        spot = fieldCell.up();
        farmingEnd = world.getTime() + FARMING_TIME + random.nextInt(FARMING_TIME_VARIATION);
        return true;
    }

    private boolean beginChop(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        tree = nextTree(world, home);
        if(tree == null)
            return cannotStart(picked, "no tree within reach of home");
        spot = standAt(world, tree);
        lookTarget = Vec3d.ofCenter(tree.up());
        return true;
    }

    private boolean beginMine(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        // The marker stands where the miner does, and faces the rock
        StructureFeatureData.Marker nearest = null;
        for(StructureFeatureData.Marker marker : site.getMarkers(MarkerKind.MINE_FACE)) {
            if(marker.pos().isWithinDistance(home, WORK_RANGE) && (nearest == null || marker.pos().getSquaredDistance(npc.getBlockPos()) < nearest.pos().getSquaredDistance(npc.getBlockPos())))
                nearest = marker;
        }
        if(nearest == null)
            return cannotStart(picked, site.supports(picked) ? "no mine face within reach of home" : "no mine face marked in this build");
        rockFace = nearest.pos().offset(nearest.facing());
        if(!isLoaded(world, rockFace) || world.getBlockState(rockFace).getBlock().asItem() == Items.AIR)
            return cannotStart(picked, "nothing to mine at the face " + rockFace.toShortString());
        spot = nearest.pos();
        lookTarget = Vec3d.ofCenter(rockFace);
        return true;
    }

    private void beginPost(ServerWorld world, RoutineData.Activity picked, BlockPos home, boolean sentry) {
        spot = home;
        MarkerKind[] kinds = sentry ? new MarkerKind[]{MarkerKind.LOOKOUT} : new MarkerKind[]{MarkerKind.ENTRANCE, MarkerKind.GUARD_POST};
        StructureFeatureData.Marker post = site.claimPost(world, npc.getUuid(), home, kinds);
        if(post != null) {
            spot = post.pos();
            facing = post.facing();
        } else {
            boolean marked = false;
            for(MarkerKind kind : kinds)
                marked |= !site.getMarkers(kind).isEmpty();
            fallsBack(picked, marked ? "every post is taken, stands at its nest" : "no post marked in this build, stands at its nest");
        }
    }

    private boolean beginPatrol(ServerWorld world, RoutineData.Activity picked) {
        List<BlockPos> route = site.getPatrolRoute();
        if(route.size() < MINIMUM_ROUTE_POINTS)
            return cannotStart(picked, "no round to walk in this build");
        ringIndex = 0;
        for(int i = 1; i < route.size(); i++) {
            if(route.get(i).getSquaredDistance(npc.getBlockPos()) < route.get(ringIndex).getSquaredDistance(npc.getBlockPos()))
                ringIndex = i;
        }
        laps = 0;
        ringStep = (npc.getUuid().hashCode() & 1) == 0 ? 1 : -1;
        ringLeft = route.size();
        spot = routePoint(world, route.get(ringIndex));
        return true;
    }

    private void beginCourt(ServerWorld world) {
        StructureFeatureData.Marker seatMarker = null;
        for(StructureFeatureData.Marker marker : site.getMarkers(MarkerKind.LORD_SEAT)) {
            if(seatMarker == null || marker.pos().getSquaredDistance(npc.getBlockPos()) < seatMarker.pos().getSquaredDistance(npc.getBlockPos()))
                seatMarker = marker;
        }
        // The marker stands beside the seat
        BlockPos seat = null;
        for(BlockPos candidate : site.getSeats()) {
            if(candidate.isWithinDistance(seatMarker.pos(), LORD_SEAT_REACH) && isLoaded(world, candidate) && SeatBlock.isFree(world, candidate)
                    && (seat == null || candidate.getSquaredDistance(seatMarker.pos()) < seat.getSquaredDistance(seatMarker.pos())))
                seat = candidate;
        }
        facing = seatMarker.facing();
        if(seat != null) {
            meetSeat = seat;
            spot = standAt(world, seat);
        } else {
            spot = seatMarker.pos();
        }
    }

    private boolean beginInspect(RoutineData.Activity picked, Random random) {
        stops.clear();
        List<StructureFeatureData.Marker> posts = new ArrayList<>(site.getMarkers(MarkerKind.GUARD_POST));
        posts.addAll(site.getMarkers(MarkerKind.ENTRANCE));
        if(posts.isEmpty()) {
            // Nothing marked, so a walk around the place will do
            List<BlockPos> ring = site.getRing();
            if(ring.size() < MINIMUM_RING_POINTS)
                return cannotStart(picked, "no post marked and no round to walk in this build");
            int step = Math.max(1, ring.size() / INSPECT_RING_STOPS);
            int first = random.nextInt(ring.size());
            for(int i = 0; i < Math.min(INSPECT_RING_STOPS, ring.size()); i++)
                stops.add(new StructureFeatureData.Marker(MarkerKind.PATROL_POINT, ring.get((first + i * step) % ring.size()), null));
        } else {
            BlockPos at = npc.getBlockPos();
            while(!posts.isEmpty()) {
                StructureFeatureData.Marker nearest = posts.getFirst();
                for(StructureFeatureData.Marker post : posts) {
                    if(post.pos().getSquaredDistance(at) < nearest.pos().getSquaredDistance(at))
                        nearest = post;
                }
                posts.remove(nearest);
                stops.add(nearest);
                at = nearest.pos();
            }
        }
        StructureFeatureData.Marker first = stops.poll();
        spot = first.pos();
        facing = first.facing();
        return true;
    }

    private void beginFollow(ServerWorld world) {
        NpcEntity lord = findLord(world);
        BlockPos stand = lord == null ? null : followSpot(world, lord);
        spot = stand != null ? stand : npc.getBlockPos();
        followCheck = FOLLOW_REPATH;
    }

    // A tree near home that still stands and was not worked at lately
    // The trees stand around the place, as far out as foraging goes. One felled since the scan is passed over
    private @Nullable BlockPos nextTree(ServerWorld world, BlockPos home) {
        List<BlockPos> trees = site.getTrees();
        int range = SPOT_RANGE + site.getExtent();
        int start = npc.getRandom().nextInt(Math.max(1, trees.size()));
        for(int pass = 0; pass < 2; pass++) {
            for(int i = 0; i < trees.size(); i++) {
                BlockPos candidate = trees.get((start + i) % trees.size());
                if(!candidate.isWithinDistance(home, range) || recentTrees.contains(candidate)
                        || !isLoaded(world, candidate) || !world.getBlockState(candidate).isIn(BlockTags.LOGS))
                    continue;
                // A bush is a log in leaves on every side, with no room to stand and swing at it
                if(NestSurvey.standNextTo(world, candidate, home) == null)
                    continue;
                recentTrees.addLast(candidate);
                if(recentTrees.size() > RECENT_TREES)
                    recentTrees.removeFirst();
                return candidate;
            }
            // With few trees about, one worked lately is better than none
            recentTrees.clear();
        }
        return null;
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
                && npc.getTarget() == null && (!npc.hasVehicle() || npc.getVehicle() instanceof SeatEntity || riding());
    }

    @Override
    public void start() {
        // Work a fight or a fright took the npc away from is not carried on with from wherever that ended: it goes home
        if(phase == Phase.WORKING && hasLeftWork() && npc.getWorld() instanceof ServerWorld world) {
            abandoned("left its work, " + interruptedBy);
            startReturn(world);
        }
        resetWalk();
        thinkCooldown = 0;
        showProp();
    }

    // Hunting, foraging and farming go on wherever the npc is, and a bodyguard finds its lord again
    private boolean hasLeftWork() {
        return switch (activity) {
            case HUNT, FORAGE, FARM, FOLLOW -> false;
            default -> !spot.isWithinDistance(npc.getPos(), LEFT_WORK_DISTANCE);
        };
    }

    @Override
    public void stop() {
        if(phase == Phase.GOING || phase == Phase.WORKING || phase == Phase.RETURNING) {
            LivingEntity foe = npc.getTarget();
            // Every npc is of the one entity kind, and is told apart by its own class
            String foeName = foe == null ? "" : foe instanceof NpcEntity other && other.getNpcClass() != null ? other.getNpcClass().getId().getPath()
                    : Registries.ENTITY_TYPE.getId(foe.getType()).getPath();
            // A task taken up again after the fight is not one given up. It only counts as that if the npc has to leave it
            interruptedBy = foe != null ? "interrupted by a fight with " + foeName
                    : npc.hasVehicle() && !(npc.getVehicle() instanceof SeatEntity) && !riding() ? "interrupted, it is riding" : "interrupted by another goal, such as fleeing";
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s paused %s (%s) at %d,%d,%d: %s".formatted(who(), activity.asString(),
                    phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), interruptedBy));
        }
        stopMoving();
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
            if(phase != Phase.WORKING)
                abandoned("ran out of time");
            startReturn(world);
            return;
        }

        switch (phase) {
            case GOING -> {
                if(activity != RoutineData.Activity.FOLLOW || following(world))
                    travel(world, spot);
            }
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
        boolean storing = phase == Phase.RETURNING && !toBed && carried != null && dropOff() != null;
        boolean sitting = phase == Phase.GOING && meetSeat != null;
        boolean working = phase == Phase.GOING && activity == RoutineData.Activity.WORK;
        // Close enough to a bed is where the bed can be seen from, not a step from it through the wall
        BlockPos bed = phase == Phase.RETURNING && toBed ? freeBed(world) : null;
        boolean bedding = bed != null;
        BlockPos store = dropOff();
        boolean atStore = !storing || store != null && store.isWithinDistance(npc.getPos(), STORE_REACH) && canSee(world, store);
        // A seat boxed in by others and a table is only got to from a corner, a little further off
        double arrival = sitting ? SEAT_ARRIVAL_DISTANCE : storing || working || bedding ? STORE_ARRIVAL_DISTANCE : ARRIVAL_DISTANCE;
        if(target.isWithinDistance(mover().getPos(), arrival) && (!bedding || canSeeBed(world, bed)) && (!working || canSee(world, workTarget()))
                && atStore && (!sitting || canSee(world, meetSeat))) {
            arrive(world);
            return;
        }
        // A path that cannot be followed keeps the navigation busy without the npc getting anywhere
        if(isGettingAlong()) {
            watchedThinks = 0;
        } else if(++watchedThinks >= WATCH_THINKS) {
            watchedThinks = 0;
            debugWhy("STALL", world, target, null);
            String heldUp = heldUpAt(world);
            mover().getNavigation().stop();
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                giveUp(world, "stuck on the way to " + target.toShortString() + heldUp);
                return;
            }
        }
        if(!mover().getNavigation().isIdle() || !RoutineSites.takePath(world))
            return;

        legs++;
        // A stretch that ended no closer was a path that led nowhere, unless it was walked to its end: the way to a loft can
        // lead off from it first
        double distance = target.getSquaredDistance(mover().getPos());
        boolean walkedLeg = lastLegEnd != null && lastLegEnd.isWithinDistance(mover().getPos(), LEG_END_REACH);
        // Nearer than ever before on this walk. Stretches that go back and forth between two places get no nearer, and
        // only a few of those are let pass
        // On a planned way, coming up to the next waypoint is getting along, though the way may first lead off
        boolean atWaypoint = waypoints != null && waypoints.get(waypointIndex).isWithinDistance(mover().getPos(), WAYPOINT_REACH);
        boolean improved = legs == 1 || distance < bestLegDistance - MINIMUM_PROGRESS * MINIMUM_PROGRESS || atWaypoint;
        if(improved) {
            bestLegDistance = distance;
            idleLegs = 0;
        }
        boolean stalled = legs > 1 && !improved && lastLegMoved && (!walkedLeg || ++idleLegs > IDLE_LEGS);
        if(stalled && phase == Phase.RETURNING && distance < HOME_DISTANCE * HOME_DISTANCE && (!bedding || isInReach(bed) && canSeeBed(world, bed)) && atStore) {
            arrive(world);
            return;
        }
        // A walk under cover that has gone wrong once gets its whole way planned, and walks it by stretches. With no way at all,
        // there is no use trying on. Out in the open the stretches and the ways round mostly do, but not round the far side of
        // a hill, and the way there is planned before the last try
        boolean covered = isUnderCover(world, mover().getBlockPos()) || isUnderCover(world, target);
        if(planWay(world, target, covered))
            return;
        Vec3d destination = legs > MAXIMUM_LEGS ? null : waypoints != null ? nextPlannedWaypoint() : nextLeg(world, target);
        // The way to a bed or a station ends on the floor beside it, not a step off where a table may hide it
        // A bed up a stair round the back of the house is a long way round, longer than the usual search looks
        if(bedding)
            mover().getNavigation().setRangeMultiplier(BED_SEARCH);
        boolean moving = destination != null && mover().getNavigation().startMovingTo(destination.x, destination.y, destination.z, bedding || working || storing ? 0 : 1, speed);
        mover().getNavigation().resetRangeMultiplier();
        Path leg = mover().getNavigation().getCurrentPath();
        lastLegEnd = moving && leg != null && leg.reachesTarget() && leg.getEnd() != null ? leg.getEnd().getBlockPos() : null;
        lastLegMoved = moving;
        if(!moving && stepOff(world))
            return;
        if(!moving || stalled) debugWhy(moving ? "NOPROGRESS" : "NOPATH", world, target, destination);
        if(moving && !stalled) {
            failures = 0;
            return;
        }
        failures++;
        if(legs > MAXIMUM_LEGS || failures >= MAXIMUM_FAILURES)
            giveUp(world, (legs > MAXIMUM_LEGS ? "too far, " + legs + " stretches without arriving at " : moving ? "got no nearer to " : "no path to ") + target.toShortString());
    }

    private boolean planWay(ServerWorld world, BlockPos target, boolean covered) {
        if(failures > 0 && !planned && (covered || failures >= MAXIMUM_FAILURES - 1)
                && mover().getNavigation() instanceof NpcNavigation navigation) {
            planned = true;
            Path whole = navigation.plan(target);
            if(whole == null) {
                // Up on a railing no way starts at all, and from the floor beside it there may well be one
                if(stepOff(world)) {
                    planned = false;
                    return true;
                }
                giveUp(world, "no way at all to " + target.toShortString());
                return true;
            }
            waypoints = new ArrayList<>();
            for(int i = WAYPOINT_SPACING; i < whole.getLength(); i += WAYPOINT_SPACING)
                waypoints.add(whole.getNodePos(i));
            waypoints.add(target);
            failures = 0;
        }
        return false;
    }

    // What the walk of a trade ends in sight of: the store a cook fetches from, the wood pile, and then the station
    private @Nullable BlockPos workTarget() {
        return fetchFrom != null ? fetchFrom : fuelFrom != null ? fuelFrom : workStation;
    }

    // Further along its path than at the last look, by however little: past another node of it, or nearer the next one.
    // Wading through water or climbing a ladder is slow and still counts. Without a path the stretches see to it
    private boolean isGettingAlong() {
        MobEntity mover = mover();
        Path path = mover.getNavigation().getCurrentPath();
        if(path == null || path.isFinished()) {
            watchedPath = null;
            return true;
        }
        int node = path.getCurrentNodeIndex();
        Vec3d next = path.getNodePosition(mover);
        // Across the ground only, as bobbing in the water goes up and down without getting anywhere
        double distance = Math.hypot(next.x - mover.getX(), next.z - mover.getZ());
        boolean further = path != watchedPath || node > watchedNode || distance < watchedDistance;
        watchedPath = path;
        watchedNode = node;
        watchedDistance = distance;
        return further;
    }

    // Standing on the ground, or on a low thing lying in its block such as a slab or a carpet
    // On roof stairs at the top of a ladder, or on a piece of furniture, no path starts at all. The npc steps down to the
    // nearest floor once a walk, rather than give up every walk from there
    private boolean stepOff(ServerWorld world) {
        if(steppedOff || riding() || npc.isClimbing() || isOnFloor(world))
            return false;
        steppedOff = true;
        BlockPos floor = NestSurvey.freeFloorNear(world, npc.getBlockPos(), STEP_OFF_RADIUS, STEP_OFF_DEPTH);
        if(floor == null)
            return false;
        did(activity.asString(), "stepped off a spot no path starts from, to " + floor.toShortString());
        npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
        return true;
    }

    private boolean isOnFloor(ServerWorld world) {
        BlockPos feet = npc.getBlockPos();
        // The top of a fence or a wall is higher than its block, and no path starts up there
        VoxelShape under = world.getBlockState(feet.down()).getCollisionShape(world, feet.down());
        if(!under.isEmpty() && under.getMax(Direction.Axis.Y) > BLOCK_HEIGHT)
            return false;
        VoxelShape inside = world.getBlockState(feet).getCollisionShape(world, feet);
        return npc.isOnGround() && (inside.isEmpty() || inside.getMax(Direction.Axis.Y) <= LOW_THING_HEIGHT) && !(world.getBlockState(feet).getBlock() instanceof StairsBlock);
    }

    // The block the walk was held up at, from the path it was following
    private String heldUpAt(ServerWorld world) {
        Path path = mover().getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return "";
        PathNode next = path.getCurrentNode();
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
        failed = true;
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s gave up %s (%s) at %d,%d,%d: %s%s".formatted(who(), what,
                phase.name().toLowerCase(), pos.getX(), pos.getY(), pos.getZ(), why, what.equals("bed") ? bedDistance() : ""));
        if(NpcDebug.ENABLED)
            NpcFailureRecorder.record(npc, who(), what, phase.name().toLowerCase(), why, phase == Phase.RETURNING ? (toBed && npc.getBedStand() != null ? npc.getBedStand() : toBed ? npc.getAssignedBed() : carried != null && npc.getStore() != null ? npc.getStore() : npc.getRoutineHome()) : spot, npc.getAssignedBed(), npc.getRoutineHome(), npc.getNavigation().getCurrentPath());
    }

    // What the build lacks, so that the npc does something else in its place. Not a task given up, and noted once
    private void fallsBack(RoutineData.Activity what, String why) {
        fallsBack(what.asString(), why);
    }

    private void fallsBack(String what, String why) {
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
    private void did(String what) {
        did(activity.asString(), what);
    }

    private void did(String task, String what) {
        BlockPos pos = npc.getBlockPos();
        MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s did %s at %d,%d,%d: %s".formatted(who(), task, pos.getX(), pos.getY(), pos.getZ(), what));
    }

    private String who() {
        NpcClass npcClass = npc.getNpcClass();
        return (npcClass == null ? "npc" : npcClass.getId().getPath()) + (npc.getJobId() == null ? "" : " [" + npc.getJobId().getPath() + "]");
    }

    private static String itemName(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }

    private void cracks(ServerWorld world, BlockPos block) {
        BlockState state = world.getBlockState(block);
        if(!state.isAir())
            world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, CRACK_PARTICLES, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPEED);
    }

    // One of what was carried goes into the store, if there is room
    private void deliver(ServerWorld world, BlockPos store) {
        if(carried == null)
            return;
        // A camp keeps its things by the fire, and what is brought there goes on it
        if(world.getBlockState(store).getBlock() instanceof CampfireBlock) {
            world.spawnParticles(ParticleTypes.SMOKE, store.getX() + 0.5, store.getY() + SMOKE_HEIGHT, store.getZ() + 0.5, SMOKE_PARTICLES, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPREAD, SMOKE_SPEED);
            did("deliver", "put " + itemName(carried) + " on the fire at " + store.toShortString());
            return;
        }
        Inventory inventory = RoutineStores.inventoryAt(world, store);
        if(inventory == null) {
            abandoned("its store " + store.toShortString() + " cannot hold anything");
            return;
        }
        ItemStack left = HopperBlockEntity.transfer(null, inventory, new ItemStack(carried), null);
        // A full store is the work done all the same
        did("deliver", left.isEmpty() ? "stored " + itemName(carried) + " in the store at " + store.toShortString()
                : "found the store at " + store.toShortString() + " full, " + itemName(carried) + " stays out");
    }

    private boolean cannotStart(RoutineData.Activity what, String why) {
        abandoned(what, "could not start, " + why);
        hold(what, npc.getWorld().getTime());
        return false;
    }

    // The door nearest the place not tried yet on this walk. Once one has been, those further off are looked at as well: the
    // nearest door to a room upstairs may be inside the house, and the way into the house round the far side
    private @Nullable BlockPos doorTowards(BlockPos target, MobEntity mover) {
        if(site == null)
            return null;
        int reach = triedDoors.isEmpty() ? DOOR_REACH : FAR_DOOR_REACH;
        BlockPos best = null;
        for(BlockPos door : site.getDoors()) {
            if(triedDoors.contains(door) || !door.isWithinDistance(target, reach) || door.isWithinDistance(mover.getPos(), AT_DOOR))
                continue;
            if(best == null || door.getSquaredDistance(target) < best.getSquaredDistance(target))
                best = door;
        }
        if(best != null)
            triedDoors.add(best);
        return best;
    }

    // The next of the planned waypoints the npc has not yet come up to
    private Vec3d nextPlannedWaypoint() {
        MobEntity mover = mover();
        while(waypointIndex < waypoints.size() - 1 && waypoints.get(waypointIndex).isWithinDistance(mover.getPos(), WAYPOINT_REACH))
            waypointIndex++;
        return Vec3d.ofBottomCenter(waypoints.get(waypointIndex));
    }

    private static boolean isLoaded(ServerWorld world, BlockPos pos) {
        return world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }

    private static boolean isUnderCover(ServerWorld world, BlockPos pos) {
        return isLoaded(world, pos) && world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) > pos.getY() + COVER_HEIGHT;
    }

    // Long walks are cut into short stretches, a path over the whole way would cost too much
    private @Nullable Vec3d nextLeg(ServerWorld world, BlockPos target) {
        MobEntity mover = mover();
        double distanceX = target.getX() + 0.5 - mover.getX();
        double distanceZ = target.getZ() + 0.5 - mover.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        boolean close = distance <= DIRECT_DISTANCE;
        if(close && failures == 0)
            return Vec3d.ofBottomCenter(target);
        // No way found straight to a place indoors: the way in is by the door nearest it, and from there it is a short walk.
        // A search from far off gives out on the way round to the far side of a house
        BlockPos door = failures > 0 ? doorTowards(target, mover) : null;
        if(door != null)
            return Vec3d.ofBottomCenter(door);
        // Under a roof or underground, the stretches over open ground below are no way at all: each search goes straight for
        // the place, and the walk gets as near as it found
        if(isUnderCover(world, mover.getBlockPos()) || isUnderCover(world, target))
            return Vec3d.ofBottomCenter(target);

        // No path to a place close by means a wall in between, and a search aimed straight at the place gives up at the wall.
        // The way in is around the building: first to one hand, and to the other if that leads nowhere
        float[] detours = !close ? DETOURS : failures % 2 == 1 ? AROUND_LEFT : AROUND_RIGHT;
        int length = close ? AROUND_LENGTH : LEG_LENGTH;
        float heading = (float) (MathHelper.atan2(distanceZ, distanceX) * MathHelper.DEGREES_PER_RADIAN);
        for(float detour : detours) {
            float radians = (heading + detour) * MathHelper.RADIANS_PER_DEGREE;
            int x = MathHelper.floor(mover.getX() + MathHelper.cos(radians) * length);
            int z = MathHelper.floor(mover.getZ() + MathHelper.sin(radians) * length);
            if(!world.isChunkLoaded(x >> 4, z >> 4))
                continue;
            BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            if(Math.abs(ground.getY() - mover.getY()) > MAXIMUM_HEIGHT_CHANGE || (site != null && site.isBuilding(x, ground.getY(), z)))
                continue;
            if(world.getFluidState(ground.down()).isEmpty() && SpawnUtil.canSpawn(ground, world, SpawnReason.EVENT))
                return Vec3d.ofBottomCenter(ground);
        }
        // Boxed in, a path straight at the target at least gets closer
        return Vec3d.ofBottomCenter(target);
    }

    private void debugWhy(String what, ServerWorld world, @Nullable BlockPos target, @Nullable Vec3d destination) {
        if(!NpcDebug.ENABLED)
            return;
        Path path = mover().getNavigation().getCurrentPath();
        String route = "none";
        if(path != null) {
            PathNode end = path.getEnd();
            PathNode next = path.isFinished() ? null : path.getCurrentNode();
            route = "len " + path.getLength() + " at " + path.getCurrentNodeIndex() + " reaches " + path.reachesTarget() + " end " + (end == null ? null : end.x + "," + end.y + "," + end.z)
                    + " next " + (next == null ? null : next.x + "," + next.y + "," + next.z + " " + world.getBlockState(new BlockPos(next.x, next.y, next.z)).getBlock().getName().getString());
        }
        System.out.println("ROUTEDIAG " + what + " " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + " phase " + phase + " toBed " + toBed
                + " at " + String.format("%.1f,%.1f,%.1f", npc.getX(), npc.getY(), npc.getZ()) + " target " + (target == null ? null : target.toShortString())
                + " leg " + (destination == null ? null : String.format("%.0f,%.0f,%.0f", destination.x, destination.y, destination.z)) + " legs " + legs + " failures " + failures + " path " + route);
    }

    private void debugWhyNot(ServerWorld world, String why) {
        if(!NpcDebug.ENABLED || world.getTime() < debugNoteAt) return;
        debugNoteAt = world.getTime() + DEBUG_NOTE_INTERVAL;
        System.out.println("WHYNOT " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + ": " + why);
    }

    private void giveUp(ServerWorld world, String why) {
        debugWhy("GIVEUP", world, null, null);
        if(phase != Phase.RETURNING && activity == RoutineData.Activity.INSPECT && !stops.isEmpty()) {
            abandoned("skipped a post, " + why);
            // The skip is in the log. The rest of the round is still seen through
            failed = false;
            nextStop(world.getTime());
            return;
        }
        if(phase == Phase.RETURNING && toBed) {
            // Stopped short, but near enough to lie down after all
            BlockPos bed = freeBed(world);
            if(bed == null || !isInReach(bed) || !canSeeBed(world, bed))
                abandoned("could not reach its bed, " + why);
            goToBed(world);
            return;
        }
        boolean walkedSome = activity == RoutineData.Activity.PATROL && ringLeft > 1 && (laps > 0 || ringLeft < site.getPatrolRoute().size());
        abandoned((phase != Phase.RETURNING && walkedSome ? "skipped a stretch of its round, " : "") + why);
        if(phase != Phase.RETURNING && walkedSome) {
            // A blocked stretch of the round is skipped, and the rest of it is still seen through
            failed = false;
            ringLeft--;
            nextWaypoint();
            return;
        }
        if(phase == Phase.RETURNING) {
            homeStrikes = Math.min(homeStrikes + 1, MAXIMUM_STRIKES);
            homeHeldUntil = world.getTime() + (long) HOLD * homeStrikes;
            // A bed behind a wall cannot be stood on, the doorstep is home enough
            finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
        } else {
            hold(activity, world.getTime());
            startReturn(world);
        }
    }

    private void arrive(ServerWorld world) {
        Random random = npc.getRandom();
        long time = world.getTime();
        stopMoving();
        if(phase == Phase.RETURNING) {
            arriveHome(world, random);
            return;
        }

        // The cook has fetched what it is to cook, and goes on to its station with it
        if(activity == RoutineData.Activity.WORK && fetchFrom != null) {
            arriveAtStore(world);
            return;
        }
        // And a log for the oven, from the wood pile
        if(activity == RoutineData.Activity.WORK && fuelFrom != null) {
            arriveAtWoodPile(world);
            return;
        }
        // Nothing is worked on the far side of a wall
        if(activity == RoutineData.Activity.WORK && workStation != null && !canSee(world, workStation)) {
            abandoned("its station " + workStation.toShortString() + " is behind a wall");
            unusableStations.put(workStation, time + STATION_HOLD);
            finish(world, REST + random.nextInt(REST_VARIATION));
            return;
        }
        BlockPos worked = activity == RoutineData.Activity.CHOP ? tree : activity == RoutineData.Activity.MINE ? rockFace : null;
        if(worked != null && !canSee(world, worked)) {
            abandoned("what it was to work at, " + worked.toShortString() + ", is behind a wall");
            finish(world, REST + random.nextInt(REST_VARIATION));
            return;
        }
        phase = Phase.WORKING;
        switch (activity) {
            case FISH -> phaseEnd = time + FISHING_TIME + random.nextInt(FISHING_TIME_VARIATION);
            case FORAGE -> phaseEnd = time + FORAGING_TIME + random.nextInt(FORAGING_TIME_VARIATION);
            case HUNT -> phaseEnd = time + STALKING_TIME + random.nextInt(STALKING_TIME_VARIATION);
            // Walking on to the next plot is still the same day in the fields
            case FARM -> phaseEnd = farmingEnd;

            case CHOP -> phaseEnd = time + CHOPPING_TIME + random.nextInt(CHOPPING_TIME_VARIATION);
            case MINE -> phaseEnd = time + MINING_TIME + random.nextInt(MINING_TIME_VARIATION);
            case WORK -> arriveWork(world, random, time);
            case VISIT, TABLE -> arriveVisitOrTable(world, random, time);
            case POST -> arrivePost(random, time);
            case COURT -> arriveCourt(world, random, time);
            case INSPECT -> {
                phaseEnd = time + INSPECT_TIME + random.nextInt(INSPECT_TIME_VARIATION);
                lookTarget = facingTarget();
            }
            case FOLLOW -> phaseEnd = time;
            case PATROL -> arrivePatrol(random, time);
        }
    }

    private void arriveHome(ServerWorld world, Random random) {
        homeStrikes = 0;
        if(toBed) {
            goToBed(world);
            return;
        }
        if(carried != null) {
            BlockPos store = dropOff();
            if(store == null || canSee(world, store))
                npc.swingHand(Hand.MAIN_HAND);
            if(store != null && store.isWithinDistance(npc.getPos(), STORE_REACH) && canSee(world, store)) {
                world.playSound(null, store, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, STORE_SOUND_VOLUME, 1f);
                deliver(world, store);
            } else if(store != null)
                abandoned("could not put away what it brought, its store " + store.toShortString() + " is out of reach or behind a wall");
        }
        finish(world, REST + random.nextInt(REST_VARIATION));
    }

    private void arriveAtStore(ServerWorld world) {
        Item raw = RoutineStores.takeRawFood(world, fetchFrom);
        if(raw == null) {
            abandoned("what there was to cook in the store at " + fetchFrom.toShortString() + " is gone");
            finish(world, REST);
            return;
        }
        carried = raw;
        did("work", "took " + itemName(raw) + " from the store at " + fetchFrom.toShortString() + " to cook");
        dropOff = fetchFrom;
        fetchFrom = null;
        spot = fuelFrom != null ? standAt(world, fuelFrom) : cookStand;
        newWalk(world);
        showProp();
    }

    private void arriveAtWoodPile(ServerWorld world) {
        logInHand = RoutineStores.takeFrom(world, fuelFrom, stack -> stack.isIn(ItemTags.LOGS));
        if(logInHand != null)
            did("work", "took " + itemName(logInHand) + " from " + fuelFrom.toShortString() + " for the oven");
        fuelFrom = null;
        spot = cookStand;
        newWalk(world);
    }

    private void arriveWork(ServerWorld world, Random random, long time) {
        phaseEnd = time + WORK_TIME + random.nextInt(WORK_TIME_VARIATION);
        lookTarget = workStation == null ? null : Vec3d.ofCenter(workStation);
        if(logInHand != null) {
            planks += PLANKS_PER_LOG;
            world.playSound(null, npc.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, SPLIT_SOUND_VOLUME, 1f);
            did("work", "split " + itemName(logInHand) + " into " + PLANKS_PER_LOG + " planks");
            logInHand = null;
        }
        if(isCookWork() && workStation != null && RoutineStores.isOven(world, workStation) && planks == 0) {
            abandoned("has no wood for the oven at " + workStation.toShortString());
            // What was fetched to be cooked goes back to its store
            if(carried != null)
                startReturn(world);
            else
                finish(world, REST);
            return;
        }
        if(workAnimal != null && (!workAnimal.isAlive() || workAnimal.squaredDistanceTo(npc) > HORSE_REACH * HORSE_REACH)) {
            abandoned("the horse it came to see to wandered off");
            finish(world, REST);
            return;
        }
    }

    private void arriveVisitOrTable(ServerWorld world, Random random, long time) {
        // Somebody quicker may have taken the seat, the table is still the place to be
        boolean seated = sitDown(world);
        phaseEnd = seated ? time + TEA_TIME + random.nextInt(TEA_TIME_VARIATION) : time + MEETING_TIME + random.nextInt(MEETING_TIME_VARIATION);
        companion = null;
        meetSeat = null;
    }

    private void arrivePost(Random random, long time) {
        phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        // A marked post says where to look, the rest watch the land outside
        lookTarget = facingTarget();
        if(lookTarget == null) {
            double awayX = npc.getX() - site.getCenterX();
            double awayZ = npc.getZ() - site.getCenterZ();
            lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
        }
    }

    private void arriveCourt(ServerWorld world, Random random, long time) {
        boolean seated = sitDown(world);
        phaseEnd = time + COURT_TIME + random.nextInt(COURT_TIME_VARIATION);
        did("court", seated ? "sat down at the lord's seat" : "stands at the lord's seat");
        companion = null;
        meetSeat = null;
    }

    private void arrivePatrol(Random random, long time) {
        phaseEnd = time + WATCH_TIME + random.nextInt(WATCH_TIME_VARIATION);
        // Watches the land outside, the place for a rider being further out than the ring
        double awayX = npc.getX() - site.getCenterX();
        double awayZ = npc.getZ() - site.getCenterZ();
        lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
    }

    private void work(ServerWorld world, long time) {
        Random random = npc.getRandom();
        boolean done = time >= phaseEnd;
        switch (activity) {
            case FISH -> workFish(world, random, done);
            case CHOP -> workChop(world, random, done);
            case MINE -> workMine(world, random, done);
            case WORK -> workAtStation(world, random, done, time);
            case FORAGE -> workForage(world, random, done);
            case HUNT -> workHunt(world, random, done, time);
            case FARM -> farm(world, done);
            case VISIT, TABLE, COURT -> workAtTable(world, random, done);
            case POST -> workPost(world, random, done, time);
            case INSPECT -> workInspect(world, done, time);
            case FOLLOW -> follow(world, time);
            case PATROL -> workPatrol(world, done);
        }
    }

    private void workFish(ServerWorld world, Random random, boolean done) {
        if(done) {
            if(random.nextInt(FISH_ESCAPE_ONE_IN) != 0) {
                carried = random.nextBoolean() ? Items.COD : Items.SALMON;
                did("caught " + itemName(carried));
            } else
                abandoned("caught nothing");
            startReturn(world);
        } else if(lookTarget != null && random.nextInt(FISH_SPLASH_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.spawnParticles(ParticleTypes.SPLASH, lookTarget.x, lookTarget.y + SPLASH_HEIGHT, lookTarget.z, SPLASH_PARTICLES, SPLASH_SPREAD, 0, SPLASH_SPREAD, 0);
            world.playSound(null, lookTarget.x, lookTarget.y, lookTarget.z, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.NEUTRAL, SPLASH_SOUND_VOLUME, 1f);
        }
    }

    private void workChop(ServerWorld world, Random random, boolean done) {
        if(done) {
            carried = tree != null && world.getBlockState(tree).isIn(BlockTags.LOGS) ? world.getBlockState(tree).getBlock().asItem() : null;
            if(carried == null)
                abandoned("the tree was gone");
            else
                did("carries " + itemName(carried) + " from the tree at " + tree.toShortString());
            // Stacked on the wood pile of the place, where there is one
            BlockPos home = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            dropOff = carried == null ? null : site.pickStation(List.of(StationKind.WOOD_PILE), home, WORK_RANGE);
            startReturn(world);
        } else if(tree != null && random.nextInt(CHOP_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, tree, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, CHOP_SOUND_VOLUME, CHOP_SOUND_PITCH + random.nextFloat() * CHOP_SOUND_PITCH_VARIATION);
            cracks(world, tree.up());
        }
    }

    private void workMine(ServerWorld world, Random random, boolean done) {
        if(done) {
            BlockState rock = rockFace == null ? null : world.getBlockState(rockFace);
            carried = rock == null || rock.isAir() ? null : rock.getBlock().asItem();
            if(carried == Items.AIR)
                carried = null;
            if(carried == null)
                abandoned("the rock face was gone");
            else
                did("carries " + itemName(carried) + " from the face at " + rockFace.toShortString());
            startReturn(world);
        } else if(rockFace != null && random.nextInt(MINE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, rockFace, world.getBlockState(rockFace).getSoundGroup().getHitSound(), SoundCategory.BLOCKS, MINE_SOUND_VOLUME, MINE_SOUND_PITCH + random.nextFloat() * MINE_SOUND_PITCH_VARIATION);
            cracks(world, rockFace);
        }
    }

    private void workAtStation(ServerWorld world, Random random, boolean done, long time) {
        if(done && workStation != null && RoutineStores.isHive(world, workStation)) {
            BlockState hive = world.getBlockState(workStation);
            world.setBlockState(workStation, hive.with(BeehiveBlock.HONEY_LEVEL, 0));
            site.emptyHive(workStation, time);
            world.playSound(null, workStation, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1f, 1f);
            carried = Items.HONEYCOMB;
            did("work", "took the honey from the hive at " + workStation.toShortString());
            startReturn(world);
        } else if(done && isCookWork() && carried != null) {
            Item raw = carried;
            carried = RoutineStores.cookedOf(world, raw);
            if(workStation != null && RoutineStores.isOven(world, workStation))
                planks--;
            did("work", "cooked " + itemName(raw) + " into " + itemName(carried) + " at " + (workStation == null ? "its station" : workStation.toShortString()));
            startReturn(world);
        } else if(done) {
            if(workAnimal != null)
                did("work", "tended the animal at " + workAnimal.getBlockPos().toShortString());
            finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(workAnimal != null) {
            // Brushed and fed where it stands
            workAnimal.getNavigation().stop();
            lookTarget = workAnimal.getEyePos();
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

    private void workForage(ServerWorld world, Random random, boolean done) {
        if(done) {
            List<Item> forage = forage(world);
            carried = forage.get(random.nextInt(forage.size()));
            startReturn(world);
        } else if(random.nextInt(FORAGE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
        } else if(random.nextInt(FORAGE_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            npc.getNavigation().startMovingTo(spot.getX() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), spot.getY(), spot.getZ() + random.nextBetween(-FORAGE_ROAM, FORAGE_ROAM), speed * ROAM_SPEED);
        }
    }

    private void workHunt(ServerWorld world, Random random, boolean done, long time) {
        // Nothing is brought home but what was hunted down
        if(done) {
            abandoned("found no game");
            // The game may come back, but not at once
            hold(RoutineData.Activity.HUNT, time);
            startReturn(world);
        } else if(random.nextInt(PREY_SEARCH_ONE_IN) == 0 && RoutineSites.takeEntitySearch(world)) {
            LivingEntity prey = findPrey(world);
            if(prey != null) {
                npc.startHunt(prey, CHASE_TIME);
                phase = Phase.HUNTING;
            }
        } else if(random.nextInt(HUNT_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            // Looks over the hunting ground
            npc.getNavigation().startMovingTo(spot.getX() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), spot.getY(),
                    spot.getZ() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), speed * ROAM_SPEED);
        }
    }

    private void workAtTable(ServerWorld world, Random random, boolean done) {
        if(done) {
            finish(world, REST + random.nextInt(REST_VARIATION));
        } else if(companion == null || !companion.isAlive() || npc.squaredDistanceTo(companion) > COMPANION_RANGE * COMPANION_RANGE) {
            // Turns to whoever else is standing around
            companion = RoutineSites.takeEntitySearch(world) ? world.getClosestEntity(NpcEntity.class, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ(), npc.getBoundingBox().expand(COMPANION_RANGE, COMPANION_HEIGHT_RANGE, COMPANION_RANGE)) : null;
            lookTarget = activity == RoutineData.Activity.COURT && !(npc.getVehicle() instanceof SeatEntity) ? facingTarget() : null;
        } else {
            lookTarget = companion.getEyePos();
            // Talking with the hands
            if(random.nextInt(GESTURE_ONE_IN) == 0)
                npc.swingHand(Hand.MAIN_HAND);
        }
    }

    private void workPost(ServerWorld world, Random random, boolean done, long time) {
        if(!done) {
            showProp();
            return;
        }
        // The guard is not relieved while its shift lasts and there is somebody to see it stand there
        if(onDuty(world) && isWatched(world))
            phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        else
            finish(world, REST + random.nextInt(REST_VARIATION));
    }

    private void workInspect(ServerWorld world, boolean done, long time) {
        if(!done)
            return;
        did("inspect", "looked over the post at " + spot.toShortString());
        if(stops.isEmpty() || !onDuty(world))
            startReturn(world);
        else
            nextStop(time);
    }

    private void workPatrol(ServerWorld world, boolean done) {
        if(!done)
            return;
        ringLeft--;
        // The watch walks its rounds until the shift is over. Going home after each one took it through the middle of the place
        // With nobody about to see it the round ends, as every other routine does
        if(!onDuty(world) || !isWatched(world)) {
            if(onDuty(world))
                abandoned("cut its round short, no player near");
            startReturn(world);
        } else {
            if(ringLeft <= 0) {
                ringLeft = site.getPatrolRoute().size();
                laps++;
            }
            deadline = world.getTime() + ROUTINE_TIME_LIMIT;
            nextWaypoint();
        }
    }

    private boolean onDuty(ServerWorld world) {
        RoutineData routine = npc.getRoutine();
        return routine != null && routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift());
    }

    private void nextStop(long time) {
        StructureFeatureData.Marker next = stops.poll();
        spot = next.pos();
        facing = next.facing();
        lookTarget = null;
        phase = Phase.GOING;
        resetWalk();
        thinkCooldown = 0;
        deadline = time + ROUTINE_TIME_LIMIT;
    }

    // The lord of the place is the one of its people who holds the job, found once and known by its id after
    private @Nullable NpcEntity findLord(ServerWorld world) {
        if(lordId != null && world.getEntity(lordId) instanceof NpcEntity known && known.isAlive())
            return known;
        lordId = null;
        if(world.getTime() < lordSearchAt)
            return null;
        lordSearchAt = world.getTime() + LORD_SEARCH_DELAY;
        int reach = (site == null ? 0 : site.getExtent()) + LORD_SEARCH_RANGE;
        NpcEntity nearest = null;
        for(NpcEntity other : world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(reach, LORD_HEIGHT_RANGE, reach),
                other -> other != npc && other.isAlive() && NpcJobs.isLord(other.getJobId()) && Objects.equals(npc.getFactionIdentifier(), other.getFactionIdentifier()))) {
            if(nearest == null || npc.squaredDistanceTo(other) < npc.squaredDistanceTo(nearest))
                nearest = other;
        }
        if(nearest != null)
            lordId = nearest.getUuid();
        return nearest;
    }

    // Where a bodyguard should be: at a post while the lord sits at court, otherwise a few blocks off the lord. Null when it is there
    private @Nullable BlockPos followSpot(ServerWorld world, NpcEntity lord) {
        facing = null;
        if(lord.getVehicle() instanceof SeatEntity && site != null) {
            StructureFeatureData.Marker post = null;
            for(StructureFeatureData.Marker marker : site.getMarkers(MarkerKind.GUARD_POST)) {
                if(marker.pos().isWithinDistance(lord.getBlockPos(), FOLLOW_POST_RANGE)
                        && (post == null || marker.pos().getSquaredDistance(lord.getBlockPos()) < post.pos().getSquaredDistance(lord.getBlockPos())))
                    post = marker;
            }
            if(post != null) {
                facing = post.facing();
                return npc.getBlockPos().isWithinDistance(post.pos(), ARRIVAL_DISTANCE) ? null : post.pos();
            }
        }
        if(npc.squaredDistanceTo(lord) <= FOLLOW_FAR * FOLLOW_FAR)
            return null;
        Vec3d away = npc.getPos().subtract(lord.getPos()).multiply(1, 0, 1);
        away = away.lengthSquared() < NO_HEADING ? new Vec3d(1, 0, 0) : away.normalize();
        BlockPos lordAt = lord.getBlockPos();
        for(int dy : FOLLOW_HEIGHTS) {
            BlockPos candidate = BlockPos.ofFloored(lord.getX() + away.x * FOLLOW_NEAR, lordAt.getY() + dy, lord.getZ() + away.z * FOLLOW_NEAR);
            if(isLoaded(world, candidate) && world.getBlockState(candidate).getCollisionShape(world, candidate).isEmpty()
                    && world.getBlockState(candidate.up()).getCollisionShape(world, candidate.up()).isEmpty() && world.getBlockState(candidate.down()).isSolidBlock(world, candidate.down()))
                return candidate;
        }
        BlockPos beside = NestSurvey.standNextTo(world, lordAt, npc.getBlockPos());
        return beside != null ? beside : lordAt;
    }

    // While walking after the lord, which has moved on since the walk began. False once there is nowhere left to walk to
    private boolean following(ServerWorld world) {
        if(--followCheck > 0)
            return true;
        followCheck = FOLLOW_REPATH;
        NpcEntity lord = findLord(world);
        if(lord == null) {
            abandoned("lost its lord");
            finish(world, REST);
            return false;
        }
        BlockPos stand = followSpot(world, lord);
        if(stand == null) {
            arrive(world);
            return false;
        }
        if(!stand.isWithinDistance(spot, FOLLOW_SPOT_SHIFT))
            resetLegs();
        steppedOff = false;
        spot = stand;
        return true;
    }

    private void follow(ServerWorld world, long time) {
        deadline = time + ROUTINE_TIME_LIMIT;
        if(!onDuty(world) || !isWatched(world)) {
            startReturn(world);
            return;
        }
        NpcEntity lord = findLord(world);
        if(lord == null) {
            abandoned("lost its lord");
            finish(world, REST);
            return;
        }
        BlockPos stand = followSpot(world, lord);
        if(stand != null) {
            spot = stand;
            phase = Phase.GOING;
            resetWalk();
            followCheck = FOLLOW_REPATH;
            return;
        }
        // Looks the way the post does, and otherwise outwards from the lord
        lookTarget = facingTarget();
        if(lookTarget == null) {
            Vec3d away = npc.getPos().subtract(lord.getPos()).multiply(1, 0, 1);
            lookTarget = away.lengthSquared() < NO_HEADING ? null : new Vec3d(npc.getX() + away.x, npc.getEyeY(), npc.getZ() + away.z);
        }
        showProp();
    }

    private @Nullable Vec3d facingTarget() {
        return facing == null ? null : new Vec3d(npc.getX() + facing.getOffsetX() * LOOK_DISTANCE, npc.getEyeY(), npc.getZ() + facing.getOffsetZ() * LOOK_DISTANCE);
    }

    // The npc on its own mount, which it steers with the mount's own legs
    private boolean riding() {
        NpcClass npcClass = npc.getNpcClass();
        return npcClass != null && npcClass.hasMount() && npc.getVehicle() instanceof MobEntity mount && mount.getFirstPassenger() == npc;
    }

    // What the one that does the walking is: the mount of a rider
    private MobEntity mover() {
        return riding() && npc.getVehicle() instanceof MobEntity mount ? mount : npc;
    }

    private void stopMoving() {
        npc.getNavigation().stop();
        if(npc.getVehicle() instanceof MobEntity mount)
            mount.getNavigation().stop();
    }

    // Riders keep to the watch: the other work is done on foot
    private static boolean mounted(RoutineData.Activity activity) {
        return activity == RoutineData.Activity.PATROL || activity == RoutineData.Activity.POST || activity == RoutineData.Activity.INSPECT;
    }

    // Riders go round further out, where a horse has the room to go
    private BlockPos routePoint(ServerWorld world, BlockPos point) {
        if(!riding() || site.hasMarkedRoute())
            return point;
        double outX = point.getX() - site.getCenterX();
        double outZ = point.getZ() - site.getCenterZ();
        double length = Math.hypot(outX, outZ);
        if(length < 1)
            return point;
        int x = MathHelper.floor(point.getX() + outX / length * RIDER_MARGIN);
        int z = MathHelper.floor(point.getZ() + outZ / length * RIDER_MARGIN);
        if(!world.isChunkLoaded(x >> 4, z >> 4))
            return point;
        BlockPos ground = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
        boolean open = Math.abs(ground.getY() - point.getY()) <= MAXIMUM_HEIGHT_CHANGE && world.getFluidState(ground.down()).isEmpty() && !site.isBuilding(x, ground.getY(), z);
        return open ? ground : point;
    }

    private void nextWaypoint() {
        List<BlockPos> ring = site.getPatrolRoute();
        int next = ringIndex + ringStep;
        // The ends of a round broken by a lake or a cliff are turned back from
        if(site.isPatrolArc() && (next < 0 || next >= ring.size())) {
            ringStep = -ringStep;
            next = ringIndex + ringStep;
        }
        ringIndex = Math.floorMod(next, ring.size());
        spot = npc.getWorld() instanceof ServerWorld world ? routePoint(world, ring.get(ringIndex)) : ring.get(ringIndex);
        lookTarget = null;
        phase = Phase.GOING;
        resetWalk();
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

    // Where the place has a hunter of its own, the hunting is the hunter's, and the rest keep to their other work
    private boolean leftToHunters(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(NpcJobs.HUNTER.getValue().equals(npc.getJobId()) || managerPos == null || !isLoaded(world, managerPos)
                || !(world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager))
            return false;
        for(UUID resident : manager.residents()) {
            if(world.getEntity(resident) instanceof NpcEntity other && other.isAlive() && NpcJobs.HUNTER.getValue().equals(other.getJobId()))
                return true;
        }
        return false;
    }

    // Of the open grounds within reach of home, the one with the most game about it lately. Game is rare in many lands,
    // and a hunter does not go out where none has been seen. -1 when no ground has a herd near it
    private int huntingGround(ServerWorld world, BlockPos home, long time) {
        List<BlockPos> game = site.getGame(world, time, animal -> isPrey(animal) && FollowHerderGoal.herderOf(animal) == null);
        if(game.isEmpty())
            return -1;
        List<BlockPos> grounds = site.getGrounds();
        int reach = SPOT_RANGE + site.getExtent();
        int best = -1;
        int most = 0;
        for(int i = 0; i < grounds.size(); i++) {
            BlockPos ground = grounds.get(i);
            if(!ground.isWithinDistance(home, reach))
                continue;
            int near = 0;
            for(BlockPos animal : game) {
                if(animal.isWithinDistance(ground, PREY_RANGE + HUNT_ROAM))
                    near++;
            }
            if(near > most) {
                most = near;
                best = i;
            }
        }
        return best;
    }

    private @Nullable LivingEntity findPrey(ServerWorld world) {
        List<AnimalEntity> herd = world.getEntitiesByClass(AnimalEntity.class, npc.getBoundingBox().expand(PREY_RANGE, PREY_HEIGHT_RANGE, PREY_RANGE),
                animal -> isPrey(animal) && !isKeptByFriend(world, animal));
        if(herd.isEmpty())
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
        if(npc.getRandom().nextInt(FARM_ACT_ONE_IN) != 0)
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
            resetWalk();
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
        boolean bestLinked = false;
        for(BlockPos cell : site.getFields(home, WORK_RANGE)) {
            if(hiddenPlots.contains(cell) || !isLoaded(world, cell) || !FarmPlots.needsWork(world, cell))
                continue;
            boolean linked = isByLinkedField(cell);
            if(best == null || linked && !bestLinked || linked == bestLinked && cell.getSquaredDistance(from) < best.getSquaredDistance(from)) {
                best = cell;
                bestLinked = linked;
            }
        }
        return best;
    }

    // Plots near a field the bed was linked to come first
    private boolean isByLinkedField(BlockPos cell) {
        for(BlockPos link : npc.getLinks()) {
            if(link.isWithinDistance(cell, LINKED_FIELD_RANGE))
                return true;
        }
        return false;
    }

    // While the crops are growing, the fields need nobody
    private boolean fieldsNeedWork(ServerWorld world, BlockPos home) {
        for(BlockPos cell : site.getFields(home, WORK_RANGE)) {
            if(isLoaded(world, cell) && FarmPlots.needsWork(world, cell))
                return true;
        }
        return false;
    }

    // Tills it, sows it or brings in its crop and sows again. False when the plot needs nothing
    private boolean tend(ServerWorld world, BlockPos cell) {
        BlockState ground = world.getBlockState(cell);
        BlockPos up = cell.up();
        BlockState above = world.getBlockState(up);
        if(!(ground.getBlock() instanceof FarmlandBlock)) {
            if(!FarmPlots.isTillable(ground) || !(above.isAir() || above.isReplaceable()))
                return false;
            if(!above.isAir())
                world.breakBlock(up, false);
            world.setBlockState(cell, Blocks.FARMLAND.getDefaultState());
            world.playSound(null, cell, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1f, 1f);
            did("tilled the plot at " + cell.toShortString());
        } else if(above.isAir()) {
            Block sown = FarmPlots.cropFor(world, cell);
            world.setBlockState(up, sown.getDefaultState());
            world.playSound(null, up, SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1f, 1f);
            did("sowed " + Registries.BLOCK.getId(sown).getPath() + " at " + cell.toShortString());
        } else if(above.getBlock() instanceof CropBlock crop && crop.isMature(above)) {
            carried = FarmPlots.harvestOf(world, up, above);
            did("harvested " + itemName(carried) + " at " + cell.toShortString());
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, up, Block.getRawIdFromState(above));
            world.setBlockState(up, crop.withAge(0));
        } else {
            return false;
        }
        npc.getLookControl().lookAt(cell.getX() + 0.5, cell.getY() + 1, cell.getZ() + 0.5);
        npc.swingHand(Hand.MAIN_HAND);
        return true;
    }

    // A beast driven by a herder of a friendly people is theirs. Whether the herder is friend or foe is only known while it
    // is about, and until then the beast is let be
    private boolean isKeptByFriend(ServerWorld world, AnimalEntity animal) {
        UUID herder = FollowHerderGoal.herderOf(animal);
        if(herder == null)
            return false;
        return !(world.getEntity(herder) instanceof NpcEntity keeper) || keeper.getFactionIdentifier() == null
                || !npc.isHostileToFaction(keeper.getFactionIdentifier());
    }

    private boolean isPrey(AnimalEntity animal) {
        if(!animal.isAlive() || animal.isBaby() || animal.hasPassengers() || animal.hasVehicle() || animal.isLeashed())
            return false;
        if(animal instanceof TameableEntity tameable && tameable.isTamed())
            return false;
        if(animal.getType().isIn(EntityTypeTagsME.GAME))
            return true;
        return animal.getType().isIn(EntityTypeTagsME.WILD_LIVESTOCK) && isLoose(animal);
    }

    // A beast of the farm counts as wild out beyond the place, and not where it is fenced in
    private boolean isLoose(AnimalEntity animal) {
        if(site != null && Math.hypot(animal.getX() - site.getCenterX(), animal.getZ() - site.getCenterZ()) < site.getExtent() + SETTLEMENT_MARGIN)
            return false;
        return !RoutineGame.isPenned(animal);
    }

    // The fighting goals do the chase, the npc itself ends it
    private boolean finishHunt(ServerWorld world) {
        if(npc.getHuntTarget() != null)
            return false;
        EntityType<?> kill = npc.takeHuntKill();
        if(kill == null)
            abandoned("the prey got away");
        carried = kill == null ? null : RoutineGame.meatOf(kill);
        if(carried != null)
            did("killed " + Registries.ENTITY_TYPE.getId(kill).getPath() + ", carries " + itemName(carried));
        startReturn(world);
        return npc.getTarget() == null;
    }

    private void startReturn(ServerWorld world) {
        standUp();
        meetSeat = null;
        if(site != null)
            site.releasePost(npc.getUuid());
        phase = Phase.RETURNING;
        newWalk(world);
        showProp();
    }

    private void resetLegs() {
        legs = 0;
        lastLegEnd = null;
        bestLegDistance = Double.MAX_VALUE;
        idleLegs = 0;
        lastLegMoved = true;
        triedDoors.clear();
        planned = false;
        waypoints = null;
        waypointIndex = 0;
        watchedPath = null;
        watchedThinks = 0;
    }

    private void resetWalk() {
        resetLegs();
        steppedOff = false;
        failures = 0;
    }

    // Each walk is judged by itself: its stretches, the doors tried, the way planned
    private void newWalk(ServerWorld world) {
        lookTarget = null;
        resetWalk();
        thinkCooldown = 0;
        deadline = world.getTime() + ROUTINE_TIME_LIMIT;
    }

    // What is brought back goes to the barrel or the fire, and a tired npc to its bed
    private @Nullable BlockPos returnTarget(ServerWorld world) {
        if(toBed) {
            BlockPos bed = freeBed(world);
            if(bed != null)
                return npc.getBedStand() != null ? npc.getBedStand() : bed;
        } else if(carried != null && dropOff() != null) {
            return storeStand(world, dropOff());
        }
        return npc.getRoutineHome();
    }

    // Of a few households tried, one with somebody in it to call on. Nobody goes to an empty house
    private @Nullable BlockPos householdToVisit(ServerWorld world, BlockPos home) {
        for(int attempt = 0; attempt < VISIT_TRIES; attempt++) {
            BlockPos household = site.pickHousehold(world, home, npc.getRandom());
            if(household == null)
                return null;
            if(!hostsAt(world, household).isEmpty())
                return household;
        }
        return null;
    }

    private List<NpcEntity> hostsAt(ServerWorld world, BlockPos household) {
        return world.getEntitiesByClass(NpcEntity.class, new Box(household).expand(HOST_RANGE),
                other -> other != npc && household.equals(other.getRoutineHome()) && !other.isSleeping());
    }

    private @Nullable BlockPos dropOff() {
        return dropOff != null ? dropOff : npc.getStore();
    }

    // Fills the hive as the day goes since its honey was last taken, which is due once it has stood full for a whole day.
    // The hives of a settlement have no bees to fill them. Where the honey in one is not what was left in it, somebody else
    // took some or bees brought some, and it is reckoned again from what is there
    private boolean hiveReady(ServerWorld world, BlockPos hive, long time) {
        BlockState state = world.getBlockState(hive);
        int found = state.get(BeehiveBlock.HONEY_LEVEL);
        long since = time - site.hiveEmptiedAt(hive, time, found, found * DAY / FULL_HONEY);
        int level = (int) Math.min(FULL_HONEY, since * FULL_HONEY / DAY);
        if(found != level)
            world.setBlockState(hive, state.with(BeehiveBlock.HONEY_LEVEL, level));
        site.noteHiveLevel(hive, level);
        return since >= HIVE_READY_TIME;
    }

    private boolean isCookWork() {
        return NpcJobs.COOK.getValue().equals(npc.getJobId());
    }

    // The nearest store of the place with something in it to cook
    private @Nullable BlockPos storeWithRawFood(ServerWorld world, BlockPos home) {
        BlockPos best = null;
        for(BlockPos store : site.getStores()) {
            if(!store.isWithinDistance(home, WORK_RANGE) || !isLoaded(world, store))
                continue;
            if(best != null && store.getSquaredDistance(home) >= best.getSquaredDistance(home))
                continue;
            Inventory inventory = RoutineStores.inventoryAt(world, store);
            if(inventory == null)
                continue;
            for(int slot = 0; slot < inventory.size(); slot++) {
                if(RoutineStores.isRawFood(world, inventory.getStack(slot))) {
                    best = store;
                    break;
                }
            }
        }
        return best;
    }

    // The nearest wood pile or store of the place with a log in it
    private @Nullable BlockPos woodSource(ServerWorld world, BlockPos home) {
        List<BlockPos> places = new ArrayList<>(site.stationsNear(List.of(StationKind.WOOD_PILE), home, WORK_RANGE));
        places.addAll(site.getStores());
        BlockPos best = null;
        for(BlockPos place : places) {
            if(!place.isWithinDistance(home, WORK_RANGE) || !isLoaded(world, place)
                    || best != null && place.getSquaredDistance(home) >= best.getSquaredDistance(home))
                continue;
            Inventory inventory = RoutineStores.inventoryAt(world, place);
            if(inventory == null)
                continue;
            for(int slot = 0; slot < inventory.size(); slot++) {
                if(inventory.getStack(slot).isIn(ItemTags.LOGS)) {
                    best = place;
                    break;
                }
            }
        }
        return best;
    }

    // The floor beside a block. A seat on a bench between the table, a wall and the next seat has none, and is got to across
    // the next seat, from a floor a step further off that sees it
    private BlockPos standAt(ServerWorld world, BlockPos block) {
        BlockPos stand = NestSurvey.standNextTo(world, block, npc.getBlockPos());
        if(stand == null)
            stand = NestSurvey.standInSight(world, block, npc.getBlockPos(), STORE_STAND_RADIUS);
        return stand != null ? stand : block;
    }

    private boolean isStableWork() {
        return NpcJobs.STABLES_MASTER.getValue().equals(npc.getJobId());
    }

    // The spawn_animal marker a stable master tends: one it is linked to, or else the nearest to home
    private @Nullable StructureFeatureData.Marker animalMarker(BlockPos home) {
        List<StructureFeatureData.Marker> markers = site.getMarkers(MarkerKind.SPAWN_ANIMAL);
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

    // The nearest animal of the marker's kind by it that nobody is riding. A marker of any horse stands for all of them
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

    // The links of the bed that are stations of the trade, to be gone to before any other, and then the stations near home
    private List<BlockPos> stationsToWork(RoutineData routine, BlockPos home) {
        List<BlockPos> stations = new ArrayList<>();
        for(BlockPos link : npc.getLinks()) {
            StationKind kind = site.stationKindAt(link);
            if(kind != null && routine.getStations().contains(kind))
                stations.add(link);
        }
        for(BlockPos station : site.stationsNear(routine.getStations(), home, WORK_RANGE)) {
            if(!stations.contains(station))
                stations.add(station);
        }
        return stations;
    }

    // A station of its trade to work at, a hive only once its honey is due, and for a stable master an animal
    private boolean hasWork(ServerWorld world, RoutineData routine, BlockPos home, long time) {
        if(isCookWork() && (storeWithRawFood(world, home) == null
                || planks == 0 && site.stationsNear(List.of(StationKind.HEARTH), home, WORK_RANGE).isEmpty() && woodSource(world, home) == null))
            return false;
        if(isStableWork())
            return findAnimal(world, home) != null;
        for(BlockPos station : site.stationsNear(routine.getStations(), home, WORK_RANGE)) {
            if(!isLoaded(world, station))
                continue;
            if(!RoutineStores.isHive(world, station) || hiveReady(world, station, time))
                return true;
        }
        return false;
    }

    // The store itself is a block nobody can stand in, and the closest the pathfinder gets to it may be the wrong side of a wall
    private BlockPos storeStand(ServerWorld world, BlockPos store) {
        if(!store.equals(standOf)) {
            standOf = store;
            BlockPos inside = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
            storeStand = isLoaded(world, store) ? NestSurvey.standNextTo(world, store, inside) : null;
            // A barrel on a table or in a corner is reached across it, from a floor that sees it
            if(storeStand == null && isLoaded(world, store))
                storeStand = NestSurvey.standInSight(world, store, inside, STORE_STAND_RADIUS);
        }
        return storeStand != null ? storeStand : store;
    }

    // A bed is two blocks, and the near half is as good as the other
    // Its middle, or else the top of it: a trapdoor stood open at the foot of a bed hides the middle from the floor beside it
    private boolean canSeeBed(ServerWorld world, BlockPos bed) {
        for(Vec3d aim : new Vec3d[]{Vec3d.ofCenter(bed), Vec3d.ofBottomCenter(bed).add(0, BED_TOP, 0)}) {
            BlockHitResult hit = world.raycast(new RaycastContext(npc.getEyePos(), aim, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
            if(hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(bed)
                    || world.getBlockState(hit.getBlockPos()).getBlock() instanceof BedBlock && hit.getBlockPos().getManhattanDistance(bed) == 1)
                return true;
        }
        return false;
    }

    // Aimed at the side of the block turned towards the npc. From eye height, a ray to the middle of a block a step away
    // crosses the block over it, and a forge under a stone hood was never seen
    private boolean canSee(ServerWorld world, BlockPos block) {
        Vec3d eye = npc.getEyePos();
        Vec3d middle = Vec3d.ofCenter(block);
        // Every side turned towards the npc is tried: from a corner, one of them is behind the next block
        List<Direction> sides = new ArrayList<>(3);
        if(Math.abs(eye.x - middle.x) > 0.5)
            sides.add(eye.x > middle.x ? Direction.EAST : Direction.WEST);
        if(Math.abs(eye.z - middle.z) > 0.5)
            sides.add(eye.z > middle.z ? Direction.SOUTH : Direction.NORTH);
        if(Math.abs(eye.y - middle.y) > 0.5)
            sides.add(eye.y > middle.y ? Direction.UP : Direction.DOWN);
        for(Direction side : sides) {
            Vec3d aim = middle.add(side.getOffsetX() * FACE_INSET, side.getOffsetY() * FACE_INSET, side.getOffsetZ() * FACE_INSET);
            BlockHitResult hit = world.raycast(new RaycastContext(eye, aim, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, npc));
            if(hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(block))
                return true;
        }
        return sides.isEmpty();
    }

    // Its own bed, unless that is gone or somebody is lying in it
    private @Nullable BlockPos freeBed(ServerWorld world) {
        BlockPos bed = npc.getAssignedBed();
        if(bed == null || !isLoaded(world, bed))
            return null;
        BlockState state = world.getBlockState(bed);
        if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED))
            return null;
        // A bed with no floor anywhere beside it, such as one in a tent shut at both ends by its poles, is got into by
        // nobody, and its owner rests at home. Looked for again in case the bed's chunk was not loaded when it was given out
        if(npc.getBedStand() == null) {
            BlockPos stand = NestSurvey.standFor(world, bed);
            if(stand == null) {
                fallsBack("bed", "no floor beside its bed at " + bed.toShortString() + ", rests at home instead");
                return null;
            }
            npc.setHousehold(bed, stand, npc.getStore(), npc.getLinks());
        }
        return bed;
    }

    private void goToBed(ServerWorld world) {
        lookTarget = null;
        npc.getNavigation().stop();
        npc.clearRoutineProp();
        BlockPos bed = freeBed(world);
        if(bed != null && isInReach(bed) && canSeeBed(world, bed)) {
            toBed = false;
            npc.lieDown(bed);
            phase = Phase.SLEEPING;
            bedStrikes = 0;
            failed = false;
        } else {
            // A walk to it that was given up is already in the log
            if(!failed)
                abandoned("bed", bed == null ? "its bed is gone or somebody lies in it" : !isInReach(bed) ? "could not get near its bed " + bed.toShortString()
                        : "its bed " + bed.toShortString() + " is behind a wall");
            // Left set until the failure is logged, which records the way to the bed rather than home
            toBed = false;
            bedStrikes = Math.min(bedStrikes + 1, MAXIMUM_STRIKES);
            bedHeldUntil = world.getTime() + (long) HOLD * bedStrikes;
            finish(world, NOTHING_TO_DO_REST);
        }
    }

    // Lofts, ladders and odd corners keep many npcs from walking the last steps to their bed. Inside the house is near enough
    private boolean isInReach(BlockPos bed) {
        double distanceX = bed.getX() + 0.5 - npc.getX();
        double distanceZ = bed.getZ() + 0.5 - npc.getZ();
        return distanceX * distanceX + distanceZ * distanceZ <= BED_REACH * BED_REACH && Math.abs(bed.getY() - npc.getY()) <= BED_HEIGHT_REACH;
    }

    private static boolean isSocial(RoutineData.Activity activity) {
        return activity == RoutineData.Activity.VISIT || activity == RoutineData.Activity.TABLE;
    }

    // The nearest of the place's own who is setting out and has nobody with it yet
    private void findWard(ServerWorld world) {
        if(!RoutineSites.takeEntitySearch(world))
            return;
        List<NpcEntity> setting = world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(ESCORT_RANGE, ESCORT_HEIGHT_RANGE, ESCORT_RANGE),
                other -> other != npc && other.isAlive() && other.isOnOuting() && other.getEscort() == null
                        && Objects.equals(npc.getFactionIdentifier(), other.getFactionIdentifier()));
        NpcEntity ward = world.getClosestEntity(setting, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ());
        if(ward != null)
            npc.escort(ward);
    }

    // Of the floor around a seat, the side between it and the next one is shut in, and getting up is back to where it sat
    // down from
    private boolean sitDown(ServerWorld world) {
        Vec3d from = npc.getPos();
        if(meetSeat == null || !canSee(world, meetSeat) || !SeatBlock.seat(world, meetSeat, npc))
            return false;
        satFrom = from;
        return true;
    }

    private void standUp() {
        if(!(npc.getVehicle() instanceof SeatEntity seat))
            return;
        BlockPos seatPos = seat.getBlockPos();
        npc.stopRiding();
        // Getting up leaves the npc standing on the seat, and there is no path off a piece of furniture.
        // It is put on the floor it sat down from
        Vec3d back = satFrom;
        satFrom = null;
        if(back != null && back.isInRange(Vec3d.ofBottomCenter(seatPos), SAT_FROM_RANGE)) {
            npc.refreshPositionAndAngles(back.x, back.y, back.z, npc.getYaw(), npc.getPitch());
            return;
        }
        BlockPos floor = npc.getWorld() instanceof ServerWorld world ? standAt(world, seatPos) : null;
        if(floor != null && !floor.equals(seatPos))
            npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }

    private void finish(ServerWorld world, int rest) {
        // Against the tasks given up, the ones seen through to the end
        if(phase != Phase.NONE && !failed) {
            // Only a task seen through clears what went wrong before. A hunter that found nothing again keeps away longer
            if(phase != Phase.SLEEPING && !toBed && !homeward)
                strikes.remove(activity);
            BlockPos pos = npc.getBlockPos();
            MiddleEarth.LOGGER.logDebugMsg("NpcRoutine :: %s finished %s at %d,%d,%d".formatted(who(), phase == Phase.SLEEPING ? "sleep" : toBed ? "bed" : homeward ? "home" : activity.asString(),
                    pos.getX(), pos.getY(), pos.getZ()));
        }
        failed = false;
        standUp();
        meetSeat = null;
        companion = null;
        if(site != null)
            site.releasePost(npc.getUuid());
        toBed = false;
        phase = Phase.NONE;
        dropOff = null;
        fetchFrom = null;
        cookStand = null;
        fuelFrom = null;
        logInHand = null;
        workAnimal = null;
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
        long time = npc.getWorld().getTimeOfDay() % DAY;
        return time >= DUSK && time < DAWN;
    }

    private void showProp() {
        Item prop = null;
        if(phase == Phase.RETURNING)
            prop = carried;
        else if(phase != Phase.GOING && phase != Phase.WORKING)
            prop = null;
        else if((activity == RoutineData.Activity.PATROL || activity == RoutineData.Activity.POST || activity == RoutineData.Activity.FOLLOW || activity == RoutineData.Activity.INSPECT) && isDark())
            prop = Items.TORCH;
        else if(activity == RoutineData.Activity.FISH && !ownsRod)
            prop = Items.FISHING_ROD;
        else if(activity == RoutineData.Activity.FARM && !(npc.getMainHandStack().getItem() instanceof HoeItem))
            prop = Items.IRON_HOE;
        else if(activity == RoutineData.Activity.CHOP && !npc.getMainHandStack().isIn(ItemTags.AXES))
            prop = Items.IRON_AXE;
        else if(activity == RoutineData.Activity.MINE && !npc.getMainHandStack().isIn(ItemTags.PICKAXES))
            prop = Items.IRON_PICKAXE;

        if(prop == null)
            npc.clearRoutineProp();
        else
            npc.showRoutineProp(new ItemStack(prop));
    }
}
