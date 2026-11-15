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

// A door of the mod that npcs walk through. A block that implements this is known to their pathfinding and opened and shut on
// the way by NpcModDoorGoal, with nothing else to change. Npcs open every door there is, of the game and of the mod, but
// the ones a builder marked with a no_entry marker
public interface NpcDoor {

    boolean isOpen(BlockState state);

    // False when it could not be moved
    boolean setOpen(World world, BlockPos pos, BlockState state, boolean open, @Nullable Entity opener);

    // False while something stands where the door would swing to. A door that cannot move is a wall
    default boolean canMove(BlockView world, BlockPos pos, BlockState state) {
        return true;
    }

    // The block that stays in place while the door moves, which is the door itself unless it is made of several
    default BlockPos getAnchor(BlockPos pos, BlockState state) {
        return pos.toImmutable();
    }

    // Vanilla doors as much as these. A prisoner opens none: it is held in its cell, whatever the door of it is
    static boolean opensFor(World world, BlockPos pos, BlockState state, @Nullable MobEntity mob) {
        if(mob instanceof NpcEntity npc && npc.isPrisoner())
            return false;
        if(RoutineSites.isNoEntry(world, pos))
            return false;
        // A gate of many blocks is marked by any one of them
        return !(state.getBlock() instanceof NpcDoor door) || !RoutineSites.isNoEntry(world, door.getAnchor(pos, state));
    }

    // The leaf of an open door leaves less room than an npc is wide, so it does not stop one
    static boolean isNpcPassing(ShapeContext context) {
        return context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof NpcEntity;
    }
}
