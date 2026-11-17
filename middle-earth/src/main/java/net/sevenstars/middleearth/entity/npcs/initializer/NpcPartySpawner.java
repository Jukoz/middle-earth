package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.goals.FollowHerderGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.utils.SpawnUtil;

public class NpcPartySpawner {
    private static final int SPAWN_RADIUS = 3;
    private static final int SPAWN_ATTEMPTS = 6;
    private static final int MAXIMUM_HEIGHT_DIFFERENCE = 4;
    private static final float FULL_TURN = 360f;

    public static void spawn(ServerWorld world, NpcEntity leader, PartyData party) {
        Random random = world.getRandom();
        party.getLeaderJob().ifPresent(leader::takeJob);
        leader.setTravelHeading(random.nextFloat() * FULL_TURN);
        if(!leader.keepsBounds())
            leader.setTravelDestination(RoutineSites.findDestination(world, leader));

        for(PartyData.Member member : party.getMembers()) {
            int amount = member.minimumAmount() + random.nextInt(Math.max(1, member.maximumAmount() - member.minimumAmount() + 1));
            for(int i = 0; i < amount; i++) {
                BlockPos pos = findSpawnPos(world, leader, random);
                if(member.npcClass().isPresent()) {
                    NpcEntity follower = new NpcEntityBuilder(world, pos)
                            .withNpcClass(member.npcClass().get())
                            .build();
                    member.job().ifPresent(follower::takeJob);
                    follower.setPartyLeader(leader.getUuid());
                    world.spawnEntity(follower);
                } else if(member.entityType().isPresent() && Registries.ENTITY_TYPE.getOptionalValue(member.entityType().get()).map(type -> type.create(world, SpawnReason.EVENT)).orElse(null) instanceof MobEntity animal) {
                    animal.refreshPositionAndAngles(pos, random.nextFloat() * FULL_TURN, 0);
                    animal.initialize(world, world.getLocalDifficulty(pos), SpawnReason.EVENT, null);
                    // Pack animals carry a chest, and a dog is tamed but has no owner
                    if(animal instanceof AbstractDonkeyEntity donkey)
                        donkey.setHasChest(true);
                    if(animal instanceof WolfEntity dog)
                        dog.setTamed(true, false);
                    FollowHerderGoal.assign(animal, leader.getUuid());
                    world.spawnEntity(animal);
                }
            }
        }
    }

    private static BlockPos findSpawnPos(ServerWorld world, NpcEntity leader, Random random) {
        BlockPos leaderPos = leader.getBlockPos();
        for(int attempt = 0; attempt < SPAWN_ATTEMPTS; attempt++) {
            int x = leaderPos.getX() + random.nextBetween(-SPAWN_RADIUS, SPAWN_RADIUS);
            int z = leaderPos.getZ() + random.nextBetween(-SPAWN_RADIUS, SPAWN_RADIUS);
            BlockPos pos = new BlockPos(x, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            if(Math.abs(pos.getY() - leaderPos.getY()) > MAXIMUM_HEIGHT_DIFFERENCE)
                continue;
            boolean hasRoom = world.isSpaceEmpty(EntitiesME.NPC.getSpawnBox(x + 0.5, pos.getY(), z + 0.5)) && world.getFluidState(pos).isEmpty();
            if(hasRoom && SpawnUtil.canSpawn(pos, world, SpawnReason.EVENT))
                return pos;
        }
        return leaderPos;
    }
}
