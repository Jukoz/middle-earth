package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
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
        return pos;
    }

    default boolean opensForNpcs(BlockState state) {
        return !state.isIn(LOCKED);
    }
}
