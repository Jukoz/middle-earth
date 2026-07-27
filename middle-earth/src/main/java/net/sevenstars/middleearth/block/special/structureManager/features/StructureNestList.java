package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

public class StructureNestList {
    ModLogger logger = MiddleEarth.LOGGER;
    public static final Codec<StructureNestList> CODEC;
    public static final StreamCodec<ByteBuf, StructureNestList> PACKET_CODEC;
    private static final String ID = "spawn_nest_list";

    private List<SpawnNestManager> managers;

    public StructureNestList() {
        this.managers = new ArrayList<>();
    }

    public StructureNestList(List<SpawnNestManager> managers) {
        this.managers = new ArrayList<>(managers);
    }

    public void computeDeath(LivingEntity entity) {
        for (SpawnNestManager nest : managers) {
            if(nest.computeDeath(entity))
                return;
        }
    }

    public List<SpawnNestManager> getManagers() {
        return managers;
    }


    public List<SpawnNestManager> content() {
        return managers;
    }

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(SpawnNestManager.CODEC).fieldOf("Managers").forGetter(StructureNestList::content)
        ).apply(instance, StructureNestList::new));
        PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);
    }

    public void addNest(SpawnNestManager spawnNestManager) {
        this.managers.add(spawnNestManager);
    }
}
