package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import org.jetbrains.annotations.Nullable;

// A door of the mod that npcs walk through. A block that implements this is known to their pathfinding and opened and shut on
// the way by NpcModDoorGoal, with nothing else to change. Doors they are to leave alone go in the LOCKED tag
public interface NpcDoor {
    TagKey<Block> LOCKED = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("npc_locked_doors"));
    // Doors of cells and corridors that those living in the place open, and prisoners and passers-by find shut
    TagKey<Block> KEYED = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("npc_keyed_doors"));

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

    // Locked for everybody, keyed unless the mob is one of those who hold the keys of the place. Vanilla doors as much as these
    static boolean opensFor(BlockState state, @Nullable MobEntity mob) {
        if(state.isIn(LOCKED))
            return false;
        return !state.isIn(KEYED) || mob instanceof NpcEntity npc && NpcJobs.holdsKeys(npc);
    }

    // The leaf of an open door leaves less room than an npc is wide, so it does not stop one
    static boolean isNpcPassing(ShapeContext context) {
        return context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof NpcEntity;
    }

    default boolean opensForNpcs(BlockState state) {
        return !state.isIn(LOCKED);
    }
}
