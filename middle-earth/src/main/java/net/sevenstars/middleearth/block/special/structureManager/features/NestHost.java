package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Tracks who it spawned and when they're due, only while its chunk is loaded
public interface NestHost {
    String NEST_KEY = "MiddleEarthNest";

    @Nullable SpawnNestManager getNest();

    void setNest(@Nullable SpawnNestManager nest);

    // No position in the saved data means it came from a template, so the people it remembers are foreign
    static boolean isOfThisWorld(ReadView view) {
        return view.getOptionalInt("x").isPresent();
    }

    static @Nullable SpawnNestManager load(ReadView view) {
        return isOfThisWorld(view) ? view.read(NEST_KEY, SpawnNestManager.CODEC).orElse(null) : null;
    }

    static void save(WriteView view, @Nullable SpawnNestManager nest) {
        view.putNullable(NEST_KEY, SpawnNestManager.CODEC, nest);
    }

    static void signalGone(World world, @Nullable BlockPos host, LivingEntity entity) {
        if(world.isClient() || host == null || !world.isChunkLoaded(host.getX() >> 4, host.getZ() >> 4))
            return;
        BlockEntity blockEntity = world.getBlockEntity(host);
        if(blockEntity instanceof NestHost found && !blockEntity.isRemoved() && found.getNest() != null && found.getNest().removeEntity(entity))
            blockEntity.markDirty();
    }

    static boolean isHost(World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof NestHost host && host.getNest() != null;
    }

    static Set<UUID> residents(ServerWorld world, BlockPos host) {
        Set<UUID> residents = new HashSet<>();
        RoutineSite site = RoutineSites.forManager(world, host);
        if(site == null)
            return residents;
        for(StructureFeatureData.Bed bed : site.getBeds()) {
            BlockPos pos = bed.pos();
            if(bed.tag().isPresent() && world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4) && world.getBlockEntity(pos) instanceof NestHost found && found.getNest() != null)
                residents.addAll(found.getNest().getEntityUuids());
        }
        return residents;
    }
}
