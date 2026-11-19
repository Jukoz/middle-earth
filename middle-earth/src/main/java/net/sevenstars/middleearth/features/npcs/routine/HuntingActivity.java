package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.entity.goals.FollowHerderGoal;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static net.sevenstars.middleearth.features.npcs.routine.ForagingActivity.ROAM_SPEED;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;

class HuntingActivity extends RoutineActivity {
    private static final int STALKING_TIME = 20 * 60;
    private static final int STALKING_TIME_VARIATION = 20 * 60;
    private static final int CHASE_TIME = 20 * 30;

    private static final int HUNT_ROAM = 10;
    // Past the buildings, so yard and green animals are left alone
    private static final int SETTLEMENT_MARGIN = 12;

    private static final int PREY_RANGE = 24;
    private static final int PREY_HEIGHT_RANGE = 6;
    private static final int BYSTANDER_DISTANCE = 5;

    private static final int PREY_SEARCH_ONE_IN = 3;
    private static final int HUNT_ROAM_ONE_IN = 4;

    HuntingActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return !site.getGrounds().isEmpty();
    }

    @Override
    boolean isPossible(ServerWorld world, RoutineData routineData, BlockPos home, long time) {
        return huntingGround(world, home, time) >= 0 && !leftToHunters(world);
    }

    @Override
    boolean isOutdoors() {
        return true;
    }

    @Override
    boolean isOuting() {
        return true;
    }

    @Override
    boolean isTiedToSpot() {
        return false;
    }

    @Override
    String label(RoutineData.Activity kind) {
        return "hunting";
    }

    @Override
    String workingNote() {
        return " (stalking)";
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        int index = huntingGround(world, home, world.getTime());
        if(index < 0)
            return routine.cannotStart(picked, "no game seen about the place");
        routine.setSpot(routine.site.getGrounds().get(index));
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.setPhaseEnd(time + STALKING_TIME + random.nextInt(STALKING_TIME_VARIATION));
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            routine.abandoned("found no game");
            routine.hold(RoutineData.Activity.HUNT, time);
            routine.startReturn(world);
        } else if(random.nextInt(PREY_SEARCH_ONE_IN) == 0 && RoutineSites.takeEntitySearch(world)) {
            LivingEntity prey = findPrey(world);
            if(prey != null) {
                npc.startHunt(prey, CHASE_TIME);
                routine.startChase();
            }
        } else if(random.nextInt(HUNT_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            npc.getNavigation().startMovingTo(routine.spot().getX() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), routine.spot().getY(),
                    routine.spot().getZ() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), routine.speed * ROAM_SPEED);
        }
    }

    boolean leftToHunters(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(NpcJobs.HUNTER.getValue().equals(npc.getJobId()) || managerPos == null || !isLoaded(world, managerPos))
            return false;
        for(UUID resident : NestHost.residents(world, managerPos)) {
            if(world.getEntity(resident) instanceof NpcEntity other && other.isAlive() && NpcJobs.HUNTER.getValue().equals(other.getJobId()))
                return true;
        }
        return false;
    }

    // Ground with the most game lately, skipping ones with none seen. -1 if none
    int huntingGround(ServerWorld world, BlockPos home, long time) {
        List<BlockPos> game = routine.site.getGame(world, time, animal -> isPrey(animal) && FollowHerderGoal.herderOf(animal) == null);
        if(game.isEmpty())
            return -1;
        List<BlockPos> grounds = routine.site.getGrounds();
        int reach = SPOT_RANGE + routine.site.getExtent();
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
        Box shot = new Box(npc.getEyePos(), nearest.getEntityPos()).expand(BYSTANDER_DISTANCE);
        boolean clear = world.getEntitiesByClass(LivingEntity.class, shot, entity -> entity != npc && (entity instanceof NpcEntity || entity instanceof PlayerEntity)).isEmpty();
        return clear ? nearest : null;
    }

    // Friend or foe is only known while the herder is about, so until then leave the beast alone
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

    private boolean isLoose(AnimalEntity animal) {
        if(routine.site != null && Math.hypot(animal.getX() - routine.site.getCenterX(), animal.getZ() - routine.site.getCenterZ()) < routine.site.getExtent() + SETTLEMENT_MARGIN)
            return false;
        return !RoutineGame.isPenned(animal);
    }

    boolean finishHunt(ServerWorld world) {
        if(npc.getHuntTarget() != null)
            return false;
        EntityType<?> kill = npc.takeHuntKill();
        if(kill == null)
            routine.abandoned("the prey got away");
        routine.setCarried(kill == null ? null : RoutineGame.meatOf(kill));
        if(routine.carried() != null)
            routine.did("killed " + Registries.ENTITY_TYPE.getId(kill).getPath() + ", carries " + itemName(routine.carried()));
        routine.startReturn(world);
        return npc.getTarget() == null;
    }
}
