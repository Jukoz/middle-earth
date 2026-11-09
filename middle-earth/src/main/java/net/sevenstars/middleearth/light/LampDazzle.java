package net.sevenstars.middleearth.light;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import org.jetbrains.annotations.Nullable;

/**
 * A focused lamp's beam in the eyes of someone in pitch darkness, underground or indoors: every NPC is blinded
 * for a moment and loses its quarry, and the creatures of the deep (orc-kind, trolls, Shelob's brood) for longer.
 * Under the open sky, or where a torch or the like already lights the place, the beam does nothing. Each is
 * dazzled at most once every five minutes; after that their eyes have grown used to it. Held and placed lamps alike.
 */
public final class LampDazzle {
    /** How often each lamp looks for faces in its beam, in ticks. */
    public static final int CHECK_TICKS = 4;
    private static final int BLIND_TICKS = 3 * 20;
    private static final int DEEP_BLIND_TICKS = 5 * 20;
    private static final long COOLDOWN_TICKS = 5 * 60 * 20;
    /** Below this light (not counting the lamps' own), a place is dark enough for the beam to dazzle. */
    private static final int DARK_BELOW = 7;
    /** The beam's radius at the lens and its widening per block, as drawn, and some slack for a face's size. */
    private static final double START_RADIUS = 0.07;
    private static final double SPREAD = 0.08;
    private static final double SLACK = 0.4;

    private static final Set<Identifier> ORC_KIND = Set.of(RaceRegistry.ORC.getValue(), RaceRegistry.URUK.getValue(),
        RaceRegistry.GOBLIN.getValue(), RaceRegistry.SNAGA.getValue());
    private static final Set<EntityType<?>> DEEP_CREATURES = Set.of(EntitiesME.CAVE_TROLL, EntitiesME.STONE_TROLL,
        EntitiesME.SNOW_TROLL, EntitiesME.SHELOBITE_LARVA, EntitiesME.SHELOBITE_SCUTTLER, EntitiesME.SPAWN_OF_SHELOB);

    /** When each one was last dazzled (world time), and until when those dazzled now stay lost. */
    private static final Map<UUID, Long> LAST = new HashMap<>();
    private static final Map<UUID, Long> DAZED = new HashMap<>();

    private LampDazzle() {
    }

    /** How long the beam blinds this one, in ticks; 0 if it does not. */
    public static int blindTicks(Entity entity) {
        if (DEEP_CREATURES.contains(entity.getType())) {
            return DEEP_BLIND_TICKS;
        }
        if (entity instanceof NpcEntity npc) {
            NpcType npcType = npc.getNpcType();
            return npcType != null && ORC_KIND.contains(npcType.getRace()) ? DEEP_BLIND_TICKS : BLIND_TICKS;
        }
        return 0;
    }

    /**
     * A beam from {@code from} along {@code direction}: dazzles the faces it finds. Call every
     * {@link #CHECK_TICKS} ticks per lamp. {@code holder} (the one carrying it, if anyone) is the cause.
     */
    public static void beam(ServerWorld world, @Nullable Entity holder, Vec3d from, Vec3d direction, double range) {
        long now = world.getTime();
        Box reach = new Box(from, from.add(direction.multiply(range))).expand(2);
        for (MobEntity mob : world.getEntitiesByClass(MobEntity.class, reach, mob -> mob.isAlive() && mob != holder)) {
            int ticks = blindTicks(mob);
            if (ticks <= 0) {
                continue;
            }
            Vec3d face = mob.getEyePos();
            Vec3d toFace = face.subtract(from);
            double along = toFace.dotProduct(direction);
            if (along <= 0 || along > range) {
                continue;
            }
            double off = toFace.subtract(direction.multiply(along)).length();
            if (off > START_RADIUS + SPREAD * along + SLACK + mob.getWidth() / 2) {
                continue;
            }
            Long last = LAST.get(mob.getUuid());
            if (last != null && now - last < COOLDOWN_TICKS && now >= last) {
                continue;
            }
            if (!inSight(world, from, face) || !inPitchDark(world, mob)) {
                continue;
            }
            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, ticks, 0, false, true), holder);
            LAST.put(mob.getUuid(), now);
            DAZED.put(mob.getUuid(), now + ticks);
            loseQuarry(mob);
        }
    }

    /** Every tick: the dazzled keep losing their quarry until they can see again; old records are dropped. */
    public static void tick(ServerWorld world) {
        long now = world.getTime();
        Iterator<Map.Entry<UUID, Long>> it = DAZED.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Long> dazed = it.next();
            if (now >= dazed.getValue() || now < dazed.getValue() - DEEP_BLIND_TICKS) {
                it.remove();
            } else if (world.getEntity(dazed.getKey()) instanceof MobEntity mob) {
                loseQuarry(mob);
            }
        }
        if (now % 1200 == 0) {
            LAST.values().removeIf(time -> now - time >= COOLDOWN_TICKS || now < time);
        }
    }

    private static void loseQuarry(MobEntity mob) {
        mob.setTarget(null);
        if (mob.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
            mob.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
        }
    }

    private static boolean inSight(ServerWorld world, Vec3d from, Vec3d face) {
        HitResult hit = world.raycast(new RaycastContext(from, face, RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE, ShapeContext.absent()));
        return hit.getType() == HitResult.Type.MISS;
    }

    /**
     * Whether it is pitch dark where the face is, but for the lamps: no open sky (even at night: underground or
     * indoors only), and no torch or the like brighter there than the lamps' light could make it (light only
     * takes the brightest source, so a level the lamps account for may be all theirs).
     */
    private static boolean inPitchDark(ServerWorld world, MobEntity mob) {
        BlockPos pos = BlockPos.ofFloored(mob.getEyePos());
        if (world.getLightLevel(LightType.SKY, pos) >= DARK_BELOW) {
            return false;
        }
        int block = world.getLightLevel(LightType.BLOCK, pos);
        return block < DARK_BELOW || block <= BeamLights.lampLightAt(world, pos);
    }
}
