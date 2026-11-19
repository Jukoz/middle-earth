package net.sevenstars.middleearth.features.npcs;

import com.mojang.serialization.Codec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.entity.goals.FollowHerderGoal;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NpcParty {
    // In blocks
    private static final int PARTY_RANGE = 64;

    private final NpcEntity npc;
    private @Nullable UUID leader = null;
    private @Nullable Float travelHeading = null;
    private @Nullable BlockPos travelDestination = null;
    private @Nullable UUID escort = null;
    // Saved, or a reloaded escort would follow its ward forever
    private boolean escorting = false;

    NpcParty(NpcEntity npc) {
        this.npc = npc;
    }

    public @Nullable UUID getLeader() {
        return leader;
    }

    public void setLeader(@Nullable UUID leader) {
        this.leader = leader;
    }

    // In degrees
    public @Nullable Float getTravelHeading() {
        return travelHeading;
    }

    public void setTravelHeading(@Nullable Float travelHeading) {
        this.travelHeading = travelHeading;
    }

    public @Nullable BlockPos getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(@Nullable BlockPos travelDestination) {
        this.travelDestination = travelDestination;
    }

    public @Nullable UUID getEscort() {
        return escort;
    }

    public void clearEscort() {
        escort = null;
    }

    public void escort(NpcEntity ward) {
        ward.getParty().escort = npc.getUuid();
        escorting = true;
        leader = ward.getUuid();
    }

    public boolean isEscorting() {
        return escorting;
    }

    public void endEscort() {
        if(escorting)
            leader = null;
        escorting = false;
    }

    // A travelling leader's party carries on without it
    void onLeaderDeath(ServerWorld world) {
        if(leader == null && travelHeading != null)
            handOver(world);
    }

    private void handOver(ServerWorld world) {
        NpcEntity heir = null;
        for(NpcEntity follower : world.getEntitiesByClass(NpcEntity.class, npc.getBoundingBox().expand(PARTY_RANGE), other -> other.isAlive() && npc.getUuid().equals(other.getPartyLeader()))) {
            if(heir == null) {
                heir = follower;
                follower.setPartyLeader(null);
                follower.setTravelHeading(travelHeading);
                follower.setTravelDestination(travelDestination);
            } else {
                follower.setPartyLeader(heir.getUuid());
            }
        }
        if(heir != null)
            FollowHerderGoal.handOver(world, npc, heir, PARTY_RANGE);
    }

    void write(WriteView view) {
        view.putNullable(NpcEntity.KeyStrings.PARTY_LEADER, Uuids.INT_STREAM_CODEC, leader);
        view.putNullable(NpcEntity.KeyStrings.TRAVEL_HEADING, Codec.FLOAT, travelHeading);
        view.putNullable(NpcEntity.KeyStrings.TRAVEL_DESTINATION, BlockPos.CODEC, travelDestination);
        view.putBoolean(NpcEntity.KeyStrings.ESCORTING, escorting);
    }

    void read(ReadView view) {
        leader = view.read(NpcEntity.KeyStrings.PARTY_LEADER, Uuids.INT_STREAM_CODEC).orElse(null);
        travelHeading = view.read(NpcEntity.KeyStrings.TRAVEL_HEADING, Codec.FLOAT).orElse(null);
        travelDestination = view.read(NpcEntity.KeyStrings.TRAVEL_DESTINATION, BlockPos.CODEC).orElse(null);
        escorting = view.getBoolean(NpcEntity.KeyStrings.ESCORTING, false);
    }
}
