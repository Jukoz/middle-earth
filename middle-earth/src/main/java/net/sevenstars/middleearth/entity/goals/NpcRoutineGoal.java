package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

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
    private static final int MAXIMUM_HEIGHT_CHANGE = 8;
    private static final int MAXIMUM_LEGS = 30;
    private static final int MAXIMUM_FAILURES = 3;
    private static final int MINIMUM_PROGRESS = 2;
    // Looks at the world, two a second, in which a walking npc has to move at least this far
    private static final int WATCH_THINKS = 8;
    private static final double WATCH_PROGRESS = 1;
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
    private static final int STALKING_TIME = 20 * 15;
    private static final int STALKING_TIME_VARIATION = 20 * 15;
    private static final int CHASE_TIME = 20 * 30;
    private static final int WATCH_TIME = 20 * 3;
    private static final int WATCH_TIME_VARIATION = 20 * 4;
    private static final int MEETING_TIME = 20 * 20;
    private static final int MEETING_TIME_VARIATION = 20 * 20;
    private static final int TEA_TIME = 20 * 45;
    private static final int TEA_TIME_VARIATION = 20 * 45;
    // Time of day
    private static final int DUSK = 12500;
    private static final int DAWN = 23500;
    private static final int COMPANION_RANGE = 6;
    private static final TargetPredicate COMPANIONS = TargetPredicate.createNonAttackable().ignoreVisibility();

    private static final int PREY_RANGE = 16;
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
    private Vec3d watchedPos = Vec3d.ZERO;
    private int watchedThinks = 0;
    // Grows each time a routine has to be abandoned, so that an npc with no way out stops trying
    private int backOff = 0;
    private int ringIndex = 0;
    private int ringStep = 1;
    private int ringLeft = 0;
    private boolean ownsRod = false;
    private boolean toBed = false;
    private @Nullable NpcEntity companion = null;
    private @Nullable BlockPos meetSeat = null;
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
            npc.stopRiding();
        if(npc.getTarget() != null || (npc.hasVehicle() && !seated))
            return false;
        if(phase != Phase.NONE) {
            if(world.getTime() > deadline && phase != Phase.RETURNING)
                startReturn(world);
            return true;
        }

        delay = IDLE_DELAY + npc.getRandom().nextInt(IDLE_DELAY_VARIATION);
        if(npc.getPartyLeader() != null || npc.getTravelHeading() != null)
            return false;
        long time = world.getTime();
        // Wandering takes settled npcs out of their village for good, seen or not
        if(drifted(world)) {
            carried = null;
            startReturn(world);
            return true;
        }
        NpcType type = npc.getNpcType();
        RoutineData routine = type == null ? null : type.getRoutine();
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
        if(routine.getPeriod().isActive(world.getTimeOfDay(), npc.isNightShift()))
            picked = routine.pick(npc.getRandom(), site::supports);
        else if(npc.isElf() && !npc.isRestTime())
            // Elves need little sleep, and keep company through the night
            picked = routine.pick(npc.getRandom(), activity -> activity == RoutineData.Activity.MEET && site.supports(activity));
        if(picked != null && begin(world, picked, home))
            return true;

        restUntil = time + NOTHING_TO_DO_REST;
        // Off duty, a settled npc that drifted away goes home
        if(npc.getStructureManagerHostPos() != null && !home.isWithinDistance(npc.getBlockPos(), STRAY_DISTANCE)) {
            carried = null;
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
                    return false;
                spot = site.getShoreStands().get(index);
                lookTarget = Vec3d.ofCenter(site.getShoreWater(index));
            }
            case HUNT, FORAGE -> {
                int index = pickNear(site.getGrounds(), home, SPOT_RANGE + site.getExtent(), random);
                if(index < 0)
                    return false;
                spot = site.getGrounds().get(index);
            }
            case MEET -> {
                meetSeat = site.pickSeat(world, random);
                if(meetSeat != null) {
                    BlockPos beside = NestSurvey.standNextTo(world, meetSeat, npc.getBlockPos());
                    spot = beside != null ? beside : meetSeat;
                } else if(site.getMeetingPoint() != null) {
                    spot = site.getMeetingPoint();
                } else {
                    return false;
                }
            }
            case PATROL -> {
                List<BlockPos> ring = site.getRing();
                ringIndex = 0;
                for(int i = 1; i < ring.size(); i++) {
                    if(ring.get(i).getSquaredDistance(npc.getBlockPos()) < ring.get(ringIndex).getSquaredDistance(npc.getBlockPos()))
                        ringIndex = i;
                }
                ringStep = (npc.getUuid().hashCode() & 1) == 0 ? 1 : -1;
                ringLeft = ring.size();
                spot = ring.get(ringIndex);
            }
        }
        activity = picked;
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
            finish(world, REST);
            return;
        }
        // The usual distance reaches through a wall, and a store on its far side was filled from the street
        boolean storing = phase == Phase.RETURNING && !toBed && carried != null && npc.getStore() != null;
        if(target.isWithinDistance(npc.getPos(), storing ? STORE_ARRIVAL_DISTANCE : ARRIVAL_DISTANCE)) {
            arrive(world);
            return;
        }
        // A path that cannot be followed keeps the navigation busy without the npc getting anywhere
        if(npc.getPos().squaredDistanceTo(watchedPos) > WATCH_PROGRESS * WATCH_PROGRESS) {
            watchedPos = npc.getPos();
            watchedThinks = 0;
        } else if(++watchedThinks >= WATCH_THINKS) {
            watchedThinks = 0;
            npc.getNavigation().stop();
            failures++;
            if(failures >= MAXIMUM_FAILURES) {
                giveUp(world);
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
            giveUp(world);
    }

    // Long walks are cut into short stretches, a path over the whole way would cost too much
    private @Nullable Vec3d nextLeg(ServerWorld world, BlockPos target) {
        double distanceX = target.getX() + 0.5 - npc.getX();
        double distanceZ = target.getZ() + 0.5 - npc.getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        if(distance <= DIRECT_DISTANCE)
            return Vec3d.ofBottomCenter(target);

        float heading = (float) (MathHelper.atan2(distanceZ, distanceX) * MathHelper.DEGREES_PER_RADIAN);
        for(float detour : DETOURS) {
            float radians = (heading + detour) * MathHelper.RADIANS_PER_DEGREE;
            int x = MathHelper.floor(npc.getX() + MathHelper.cos(radians) * LEG_LENGTH);
            int z = MathHelper.floor(npc.getZ() + MathHelper.sin(radians) * LEG_LENGTH);
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

    private void giveUp(ServerWorld world) {
        boolean walkedSome = activity == RoutineData.Activity.PATROL && ringLeft > 1 && ringLeft < site.getRing().size();
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
            }
            finish(world, REST + random.nextInt(REST_VARIATION));
            return;
        }

        phase = Phase.WORKING;
        backOff = 0;
        switch (activity) {
            case FISH -> phaseEnd = time + FISHING_TIME + random.nextInt(FISHING_TIME_VARIATION);
            case FORAGE -> phaseEnd = time + FORAGING_TIME + random.nextInt(FORAGING_TIME_VARIATION);
            case HUNT -> phaseEnd = time + STALKING_TIME + random.nextInt(STALKING_TIME_VARIATION);
            case MEET -> {
                phaseEnd = time + MEETING_TIME + random.nextInt(MEETING_TIME_VARIATION);
                companion = null;
                // Somebody quicker may have taken the seat, the table is still the place to be
                if(meetSeat != null && SeatBlock.seat(world, meetSeat, npc))
                    phaseEnd = time + TEA_TIME + random.nextInt(TEA_TIME_VARIATION);
                meetSeat = null;
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
                    startReturn(world);
                } else if(lookTarget != null && random.nextInt(12) == 0) {
                    npc.swingHand(Hand.MAIN_HAND);
                    world.spawnParticles(ParticleTypes.SPLASH, lookTarget.x, lookTarget.y + 0.5, lookTarget.z, 6, 0.2, 0, 0.2, 0);
                    world.playSound(null, lookTarget.x, lookTarget.y, lookTarget.z, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.NEUTRAL, 0.2f, 1f);
                }
            }
            case FORAGE -> {
                if(done) {
                    List<Item> forage = forage();
                    carried = forage.get(random.nextInt(forage.size()));
                    startReturn(world);
                } else if(random.nextInt(3) == 0) {
                    npc.swingHand(Hand.MAIN_HAND);
                } else if(random.nextInt(4) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
                    npc.getNavigation().startMovingTo(spot.getX() + random.nextBetween(-3, 3), spot.getY(), spot.getZ() + random.nextBetween(-3, 3), speed * 0.75);
                }
            }
            case HUNT -> {
                if(done) {
                    // Small game, caught out of sight
                    if(random.nextInt(3) == 0)
                        carried = Items.RABBIT;
                    startReturn(world);
                } else if(random.nextInt(3) == 0 && npc.getMainHandStack().getItem() instanceof RangedWeaponItem && RoutineSites.takePreySearch(world)) {
                    // Deer outrun anyone without a bow
                    LivingEntity prey = findPrey(world);
                    if(prey != null) {
                        npc.startHunt(prey, CHASE_TIME);
                        phase = Phase.HUNTING;
                    }
                }
            }
            case MEET -> {
                if(done) {
                    finish(world, REST + random.nextInt(REST_VARIATION));
                } else if(companion == null || !companion.isAlive() || npc.squaredDistanceTo(companion) > COMPANION_RANGE * COMPANION_RANGE) {
                    // Turns to whoever else is standing around
                    companion = RoutineSites.takePreySearch(world) ? world.getClosestEntity(NpcEntity.class, COMPANIONS, npc, npc.getX(), npc.getY(), npc.getZ(), npc.getBoundingBox().expand(COMPANION_RANGE, 2, COMPANION_RANGE)) : null;
                    lookTarget = null;
                } else {
                    lookTarget = companion.getEyePos();
                }
            }
            case PATROL -> {
                if(!done)
                    return;
                ringLeft--;
                // The watch walks its rounds until the shift is over. Going home after each one took it through the middle of the place
                if(!onDuty(world)) {
                    startReturn(world);
                } else {
                    if(ringLeft <= 0)
                        ringLeft = site.getRing().size();
                    deadline = world.getTime() + ROUTINE_TIME_LIMIT;
                    nextWaypoint();
                }
            }
        }
    }

    private boolean onDuty(ServerWorld world) {
        NpcType type = npc.getNpcType();
        RoutineData routine = type == null ? null : type.getRoutine();
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
    }

    private List<Item> forage() {
        NpcType type = npc.getNpcType();
        RoutineData routine = type == null ? null : type.getRoutine();
        return routine == null || routine.getForage().isEmpty() ? DEFAULT_FORAGE : routine.getForage();
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
        if(bed != null && isInReach(bed)) {
            npc.lieDown(bed);
            phase = Phase.SLEEPING;
        } else {
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

    private void standUp() {
        meetSeat = null;
        if(npc.getVehicle() instanceof SeatEntity)
            npc.stopRiding();
    }

    private void finish(ServerWorld world, int rest) {
        standUp();
        toBed = false;
        phase = Phase.NONE;
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
        else if(activity == RoutineData.Activity.PATROL && phase != Phase.NONE && isDark())
            prop = Items.TORCH;
        else if(activity == RoutineData.Activity.FISH && phase != Phase.NONE && !ownsRod)
            prop = Items.FISHING_ROD;

        if(prop == null)
            npc.clearRoutineProp();
        else
            npc.showRoutineProp(new ItemStack(prop));
    }
}
