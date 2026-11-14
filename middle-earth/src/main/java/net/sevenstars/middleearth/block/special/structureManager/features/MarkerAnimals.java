package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// The animals one spawn_animal marker put in a place. The entry exists once they were spawned, and is all that keeps them from
// being spawned again
public class MarkerAnimals {
    public static final Codec<MarkerAnimals> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos").forGetter(MarkerAnimals::getPos),
            Codec.list(Uuids.CODEC).fieldOf("animals").forGetter(markerAnimals -> markerAnimals.animals),
            Codec.LONG.optionalFieldOf("missing_since", -1L).forGetter(markerAnimals -> markerAnimals.missingSince)
    ).apply(instance, MarkerAnimals::new));

    // The same wait as the nests, in ticks
    private static final long RESPAWN_DELAY = 24000 * 3;

    private final BlockPos pos;
    private final List<UUID> animals;
    private long missingSince;

    public MarkerAnimals(BlockPos pos, List<UUID> animals, long missingSince) {
        this.pos = pos;
        this.animals = new ArrayList<>(animals);
        this.missingSince = missingSince;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void spawn(ServerWorld world, EntityType<?> type, int amount) {
        for(int i = 0; i < amount; i++) {
            Entity entity = type.create(world, SpawnReason.STRUCTURE);
            if(entity == null)
                continue;
            if(entity instanceof MobEntity mob) {
                mob.initialize(world, world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
                mob.setPersistent();
            }
            entity.setPosition(Vec3d.ofBottomCenter(pos));
            world.spawnEntity(entity);
            animals.add(entity.getUuid());
        }
    }

    // Brings them back to the count after the wait, where the marker says they respawn
    public boolean topUp(ServerWorld world, EntityType<?> type, int count) {
        // Animals out of the loaded ground are not gone
        if(!world.shouldTickEntityAt(pos))
            return false;
        int before = animals.size();
        animals.removeIf(uuid -> !(world.getEntity(uuid) instanceof Entity animal) || !animal.isAlive());
        boolean changed = animals.size() != before;
        if(animals.size() >= count) {
            changed |= missingSince >= 0;
            missingSince = -1;
            return changed;
        }
        if(missingSince < 0) {
            missingSince = world.getTime();
            return true;
        }
        if(world.getTime() - missingSince >= RESPAWN_DELAY) {
            spawn(world, type, count - animals.size());
            missingSince = -1;
            return true;
        }
        return changed;
    }
}
