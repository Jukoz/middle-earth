package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnEventDataUtil;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnCensus;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BiomeEventData {
    public static class Fields {
        public static final String SPAWN_DEFAULT_WHEN_UNMET = "SpawnDefaultWhenUnmet";
        public static final String WILD_SPAWNS = "WildSpawns";
    }

    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf(Fields.SPAWN_DEFAULT_WHEN_UNMET).forGetter(BiomeEventData::getSpawnDefaultWhenUnmet),
            Codec.list(WildSpawnEventData.CODEC).fieldOf(Fields.WILD_SPAWNS).forGetter(BiomeEventData::getWildSpawnEventDatas)
    ).apply(instance, BiomeEventData::new));

    // In ticks
    private static final int CHUNK_DISABLE_TICKS = 20 * 5;

    private static final int DEFAULT_WEIGHT = 1;

    public record SpawnPick(@Nullable WildSpawnEventData data) {}

    private boolean shouldSpawnDefaultWhenUnmet;
    private List<WildSpawnEventData> wildSpawnEventDatas;

    public BiomeEventData(boolean shouldSpawnDefaultWhenUnmet, List<WildSpawnEventData> wildNpcs){
        this(wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public BiomeEventData(List<WildSpawnEventData> wildNpcs){
        this.wildSpawnEventDatas = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public BiomeEventData withMoreWildSpawns(List<WildSpawnEventData> wildNpcs){
        List<WildSpawnEventData> newWildSpawns = new ArrayList<>();
        if(wildSpawnEventDatas != null)
            newWildSpawns.addAll(wildSpawnEventDatas);
        newWildSpawns.addAll(wildNpcs);
        this.wildSpawnEventDatas = newWildSpawns;
        return this;
    }

    private List<WildSpawnEventData> getWildSpawnEventDatas() {
        return this.wildSpawnEventDatas;
    }

    public boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public ContextualizedBiomeData findNpcData(World world, NpcEntity entity) {
        // Use what the spawn check picked here
        WildSpawnEventData spawningData = SpawnGate.takeHeld(world, entity.getBlockPos(), wildSpawnEventDatas);
        if(spawningData == null)
            spawningData = pickWeighted(Registries.ENTITY_TYPE.getId(EntitiesME.NPC), world, entity.getBlockPos(), world.getRandom());
        if(spawningData == null)
            return null;

        Identifier npcClassId = spawningData.getNpcClass(null);
        NpcClass npcClass = npcClassId == null ? null : world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).get(npcClassId);
        return new ContextualizedBiomeData(npcClass, spawningData.getParty(), spawningData.getJob());
    }

    private @Nullable WildSpawnEventData pickWeighted(Identifier entityId, World world, BlockPos pos, Random random) {
        List<WildSpawnEventData> considered = new ArrayList<>();
        int total = 0;
        for(WildSpawnEventData data : wildSpawnEventDatas) {
            if(SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, pos)) {
                considered.add(data);
                total += Math.max(0, data.getWeight(DEFAULT_WEIGHT));
            }
        }
        if(total <= 0)
            return null;
        int roll = random.nextInt(total);
        for(WildSpawnEventData data : considered) {
            roll -= Math.max(0, data.getWeight(DEFAULT_WEIGHT));
            if(roll < 0)
                return data;
        }
        return null;
    }

    private boolean hasEntityType(Identifier entityId) {
        for(WildSpawnEventData data : wildSpawnEventDatas) {
            if(IdentifierUtil.compareId(data.getEntityType(), entityId))
                return true;
        }
        return false;
    }

    // Null refuses. No data means no event governs the type
    public @Nullable SpawnPick pickSpawn(EntityType<?> type, ServerWorld world, BlockPos pos, Random random) {
        Identifier entityId = Registries.ENTITY_TYPE.getId(type);
        if(!hasEntityType(entityId))
            return new SpawnPick(null);
        if(SpawnGate.isDisabled(world, pos, type))
            return null;

        WildSpawnEventData spawningData = pickWeighted(entityId, world, pos, random);
        if(spawningData == null || spawningData.isDiscarded(random)){
            SpawnGate.disable(world, pos, type, CHUNK_DISABLE_TICKS);
            return null;
        }
        return new SpawnPick(spawningData);
    }

    // Once nothing else of ours refuses the spawn
    public void commitSpawn(SpawnPick pick, EntityType<?> type, ServerWorld world, BlockPos pos) {
        WildSpawnEventData spawningData = pick.data();
        if(spawningData == null)
            return;
        spawningData.broadcastMessage(world, pos);
        if(type == EntitiesME.NPC) {
            SpawnGate.hold(world, pos, spawningData);
        }
        SpawnCensus.expect(world, type, type == EntitiesME.NPC ? spawningData.getNpcClass(null) : null, pos);
    }

    public record ContextualizedBiomeData(NpcClass npcClass, @Nullable PartyData party, @Nullable Identifier job) {}
}
