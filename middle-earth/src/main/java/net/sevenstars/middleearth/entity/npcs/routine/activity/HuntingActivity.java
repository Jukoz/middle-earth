package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineGame;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.goals.FollowHerderGoal;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.List;
import java.util.UUID;

import static net.sevenstars.middleearth.entity.npcs.routine.activity.ForagingActivity.ROAM_SPEED;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;

public class HuntingActivity extends RoutineActivity {
    // Long enough to look over the hunting ground for game
    private static final int STALKING_TIME = 20 * 60;
    private static final int STALKING_TIME_VARIATION = 20 * 60;
    private static final int CHASE_TIME = 20 * 30;

    private static final int HUNT_ROAM = 10;
    // Past the edge of the buildings, so the animals of the yards and the green are left alone
    private static final int SETTLEMENT_MARGIN = 12;

    private static final int PREY_RANGE = 24;
    private static final int PREY_HEIGHT_RANGE = 6;
    private static final int BYSTANDER_DISTANCE = 5;

    private static final int PREY_SEARCH_ONE_IN = 3;
    private static final int HUNT_ROAM_ONE_IN = 4;

    public HuntingActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        int index = huntingGround(world, home, world.getTime());
        if(index < 0)
            return engine.cannotStart(picked, "no game seen about the place");
        engine.spot = engine.site.getGrounds().get(index);
        return true;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + STALKING_TIME + random.nextInt(STALKING_TIME_VARIATION);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        // Brings home only what it hunted
        if(done) {
            engine.abandoned("found no game");
            // The game may come back, but not right away
            engine.hold(RoutineData.Activity.HUNT, time);
            engine.startReturn(world);
        } else if(random.nextInt(PREY_SEARCH_ONE_IN) == 0 && RoutineSites.takeEntitySearch(world)) {
            LivingEntity prey = findPrey(world);
            if(prey != null) {
                npc.startHunt(prey, CHASE_TIME);
                engine.phase = Phase.HUNTING;
            }
        } else if(random.nextInt(HUNT_ROAM_ONE_IN) == 0 && npc.getNavigation().isIdle() && RoutineSites.takePath(world)) {
            // Looks over the hunting ground
            npc.getNavigation().startMovingTo(engine.spot.getX() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), engine.spot.getY(),
                    engine.spot.getZ() + random.nextBetween(-HUNT_ROAM, HUNT_ROAM), engine.speed * ROAM_SPEED);
        }
    }

    // Where the place has its own hunter, the hunting is left to the hunter and the rest keep to their other work
    public boolean leftToHunters(ServerWorld world) {
        BlockPos managerPos = npc.getStructureManagerHostPos();
        if(NpcJobs.HUNTER.getValue().equals(npc.getJobId()) || managerPos == null || !isLoaded(world, managerPos))
            return false;
        for(UUID resident : NestHost.residents(world, managerPos)) {
            if(world.getEntity(resident) instanceof NpcEntity other && other.isAlive() && NpcJobs.HUNTER.getValue().equals(other.getJobId()))
                return true;
        }
        return false;
    }

    // Of the open grounds within reach of home, the one with the most game about lately. Game is rare in many lands,
    // and a hunter doesn't go out where none has been seen. -1 when no ground has a herd near it
    public int huntingGround(ServerWorld world, BlockPos home, long time) {
        List<BlockPos> game = engine.site.getGame(world, time, animal -> isPrey(animal) && FollowHerderGoal.herderOf(animal) == null);
        if(game.isEmpty())
            return -1;
        List<BlockPos> grounds = engine.site.getGrounds();
        int reach = SPOT_RANGE + engine.site.getExtent();
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

    // A beast driven by a herder of a friendly people counts as theirs. Whether the herder is a friend or foe is only
    // known while it's about, and until then the beast is left be
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

    // A farm animal counts as wild out beyond the place, but not where it's fenced in
    private boolean isLoose(AnimalEntity animal) {
        if(engine.site != null && Math.hypot(animal.getX() - engine.site.getCenterX(), animal.getZ() - engine.site.getCenterZ()) < engine.site.getExtent() + SETTLEMENT_MARGIN)
            return false;
        return !RoutineGame.isPenned(animal);
    }

    // The fight does the chase, and the npc itself ends it
    public boolean finishHunt(ServerWorld world) {
        if(npc.getHuntTarget() != null)
            return false;
        EntityType<?> kill = npc.takeHuntKill();
        if(kill == null)
            engine.abandoned("the prey got away");
        engine.carried = kill == null ? null : RoutineGame.meatOf(kill);
        if(engine.carried != null)
            engine.did("killed " + Registries.ENTITY_TYPE.getId(kill).getPath() + ", carries " + itemName(engine.carried));
        engine.startReturn(world);
        return npc.getTarget() == null;
    }
}
