package net.sevenstars.middleearth.block.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import org.jetbrains.annotations.Nullable;

// Npcs open every door except ones a builder marked off limits
public interface NpcDoor {

    boolean isOpen(BlockState state);

    boolean setOpen(World world, BlockPos pos, BlockState state, boolean open, @Nullable Entity opener);

    default boolean canMove(BlockView world, BlockPos pos, BlockState state) {
        return true;
    }

    default BlockPos getAnchor(BlockPos pos, BlockState state) {
        return pos.toImmutable();
    }

    // A prisoner never opens any door, vanilla included
    static boolean opensFor(World world, BlockPos pos, BlockState state, @Nullable MobEntity mob) {
        if(mob instanceof NpcEntity npc && npc.isPrisoner())
            return false;
        if(RoutineSites.isNoEntry(world, pos))
            return false;
        // A multi-block gate can be marked through any one of its blocks
        return !(state.getBlock() instanceof NpcDoor door) || !RoutineSites.isNoEntry(world, door.getAnchor(pos, state));
    }

    // An open leaf is thinner than an npc
    static boolean isNpcPassing(ShapeContext context) {
        return context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof NpcEntity;
    }
}
