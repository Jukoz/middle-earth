package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/// NpcSpawnNest is a specific area where entities can spawn with the different parameters.
public class StructureSpawnNest {
    public static final Codec<StructureSpawnNest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(StructureSpawnNest::getId),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(StructureSpawnNest::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("offset").forGetter(StructureSpawnNest::getBlockPosOffset),
            Codec.list(StructureSpawnNestPool.CODEC).fieldOf("npc_pool").forGetter(StructureSpawnNest::getNpcSpawnNestPool)
    ).apply(instance, StructureSpawnNest::new));

    private final Identifier id;
    private final int respawnTickDelay;
    private final List<StructureSpawnNestPool> structureSpawnNestPools;
    private final BlockPos blockPosOffset;


    public StructureSpawnNest(Identifier id, int respawnTickDelay, BlockPos blockPosOffset, List<StructureSpawnNestPool> npcPools) {
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.blockPosOffset = blockPosOffset;
        this.structureSpawnNestPools = npcPools;
    }

    public Identifier getId() {
        return this.id;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    private BlockPos getBlockPosOffset() {
        return this.blockPosOffset;
    }
    private List<StructureSpawnNestPool> getNpcSpawnNestPool() {
        return structureSpawnNestPools;
    }


}
