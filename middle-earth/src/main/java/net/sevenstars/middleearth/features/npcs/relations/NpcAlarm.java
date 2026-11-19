package net.sevenstars.middleearth.features.npcs.relations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Who an npc holds a grudge against, and how it raises the alarm and answers one
public class NpcAlarm {
    private static final int GUARDS_TO_FLEE = 3;

    // In blocks
    private static final int ALARM_RANGE = 24;
    private static final int ALARM_HEIGHT_RANGE = 8;
    private static final int HELP_RANGE = 16;

    // Ticks. Vanilla forgets attackers after 5s, which would end fights with neutrals
    private static final int GRUDGE_TICKS = 20 * 60;

    private final NpcEntity npc;
    private @Nullable UUID threatFaced = null;
    private boolean fleesThreat = false;
    private @Nullable UUID guardsCalledFor = null;
    private long threatFacedUntil;
    private long guardsCalledUntil;
    // Grudges: offender -> expiry tick. Not saved
    private final Map<UUID, Long> grudges = new HashMap<>();

    public NpcAlarm(NpcEntity npc) {
        this.npc = npc;
    }

    // Only the wronged npc and those it called keep a grudge. A friendly npc's blow is a stray
    public void bearGrudge(LivingEntity offender) {
        if(offender == npc || npc.getEntityWorld().isClient())
            return;
        if(offender instanceof NpcEntity && NpcRelations.standing(npc, offender).isFriendly())
            return;
        long now = npc.getEntityWorld().getTime();
        grudges.values().removeIf(until -> until < now);
        grudges.put(offender.getUuid(), now + GRUDGE_TICKS);
    }

    public boolean hasGrudgeAgainst(Entity other) {
        Long until = grudges.get(other.getUuid());
        return until != null && until >= npc.getEntityWorld().getTime();
    }

    // Timid npcs commit to one response, or they'd run, turn and run again
    public boolean fleesFrom(LivingEntity threat) {
        long now = npc.getEntityWorld().getTime();
        if(!threat.getUuid().equals(threatFaced) || threatFacedUntil < now) {
            faceThreat(threat, now);
            fleesThreat = npc.getEntityWorld() instanceof ServerWorld world && countGuards(world) >= GUARDS_TO_FLEE;
            if(!fleesThreat && npc.getEntityWorld() instanceof ServerWorld world)
                callCommonFolk(world, threat);
        }
        return fleesThreat;
    }

    public void callGuardsOnce(LivingEntity threat) {
        long now = npc.getEntityWorld().getTime();
        if(threat.getUuid().equals(guardsCalledFor) && guardsCalledUntil >= now)
            return;
        guardsCalledFor = threat.getUuid();
        guardsCalledUntil = now + GRUDGE_TICKS;
        callGuards(threat);
    }

    public void callGuards(@Nullable LivingEntity threat) {
        if(threat == null || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        for(NpcEntity guard : neighbours(world)) {
            if(guard.isTimid() || guard.getTarget() != null || !NpcRelations.of(guard, npc).isFriendly())
                continue;
            guard.bearGrudge(threat);
            if(guard.canTarget(threat)) {
                if(guard.isSleeping())
                    guard.wakeUp();
                guard.setTarget(threat);
            }
        }
    }

    // Told by a neighbour that it is facing this threat, so it stands and fights
    private void warnedOf(LivingEntity threat, long now) {
        faceThreat(threat, now);
        fleesThreat = false;
    }

    private void faceThreat(LivingEntity threat, long now) {
        threatFaced = threat.getUuid();
        threatFacedUntil = now + GRUDGE_TICKS;
    }

    private int countGuards(ServerWorld world) {
        int count = 0;
        for(NpcEntity other : neighbours(world)) {
            if(!other.isTimid() && NpcRelations.of(other, npc).isFriendly())
                count++;
        }
        return count;
    }

    private void callCommonFolk(ServerWorld world, LivingEntity threat) {
        for(NpcEntity other : world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(HELP_RANGE, ALARM_HEIGHT_RANGE, HELP_RANGE),
                other -> other != npc && other.isAlive() && other.isTimid() && other.getTarget() == null)) {
            if(!NpcRelations.of(other, npc).isFriendly() || !other.canTarget(threat))
                continue;
            other.getAlarm().warnedOf(threat, world.getTime());
            if(other.isSleeping())
                other.wakeUp();
            other.setTarget(threat);
        }
    }

    private Set<NpcEntity> neighbours(ServerWorld world) {
        Set<NpcEntity> neighbours = new LinkedHashSet<>(world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(ALARM_RANGE, ALARM_HEIGHT_RANGE, ALARM_RANGE), other -> other != npc && other.isAlive()));
        BlockPos hostPos = npc.getHostPos();
        if(hostPos != null && world.isChunkLoaded(hostPos.getX() >> 4, hostPos.getZ() >> 4)) {
            for(UUID owner : NestHost.residents(world, hostPos)) {
                if(world.getEntity(owner) instanceof NpcEntity resident && resident != npc && resident.isAlive())
                    neighbours.add(resident);
            }
        }
        return neighbours;
    }
}
