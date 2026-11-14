package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.ArrayList;
import java.util.List;

public class StructureNestList {
    ModLogger logger = MiddleEarth.LOGGER;
    public static final Codec<StructureNestList> CODEC;
    public static final PacketCodec<ByteBuf, StructureNestList> PACKET_CODEC;
    private static final String ID = "spawn_nest_list";

    private List<SpawnNestManager> managers;

    public StructureNestList() {
        this.managers = new ArrayList<>();
    }

    public StructureNestList(List<SpawnNestManager> managers) {
        this.managers = managers;
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
        PACKET_CODEC = PacketCodecs.codec(CODEC);
    }

    // Nests that a manager keeps from before its data was what it is, or that came with a template of another one
    public int removeUnknown(StructureManagerData data) {
        int before = managers.size();
        managers = new ArrayList<>(managers.stream().filter(nest -> data.findSpawnNest(nest.getId()) != null).toList());
        return before - managers.size();
    }

    public void addNest(SpawnNestManager spawnNestManager) {
        this.managers = new ArrayList<>(managers);
        this.managers.add(spawnNestManager);
    }
}
