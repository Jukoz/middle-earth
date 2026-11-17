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

// A mod door that npcs walk through. A block that implements this works with npc pathfinding, and ModDoorBehaviour opens
// and closes it on the way through, with nothing else to set up. Npcs open every door, vanilla or mod, except the ones
// a builder marked off limits
public interface NpcDoor {

    boolean isOpen(BlockState state);

    // False when the door couldn't be moved
    boolean setOpen(World world, BlockPos pos, BlockState state, boolean open, @Nullable Entity opener);

    // False while something is blocking the space the door would swing into. A door that can't move counts as a wall
    default boolean canMove(BlockView world, BlockPos pos, BlockState state) {
        return true;
    }

    // The block that stays put while the door moves. That's the door itself unless it is made of several blocks
    default BlockPos getAnchor(BlockPos pos, BlockState state) {
        return pos.toImmutable();
    }

    // Applies to vanilla doors as well as these. A prisoner never opens any door, so it stays in its cell
    static boolean opensFor(World world, BlockPos pos, BlockState state, @Nullable MobEntity mob) {
        if(mob instanceof NpcEntity npc && npc.isPrisoner())
            return false;
        if(RoutineSites.isNoEntry(world, pos))
            return false;
        // A multi-block gate can be marked through any one of its blocks
        return !(state.getBlock() instanceof NpcDoor door) || !RoutineSites.isNoEntry(world, door.getAnchor(pos, state));
    }

    // An open leaf leaves less room than an npc is wide, so it doesn't block one
    static boolean isNpcPassing(ShapeContext context) {
        return context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof NpcEntity;
    }
}
