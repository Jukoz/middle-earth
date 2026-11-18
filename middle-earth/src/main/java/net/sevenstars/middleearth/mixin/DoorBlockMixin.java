package net.sevenstars.middleearth.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.block.special.NpcDoor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DoorBlock.class)
public class DoorBlockMixin extends Block {
    public DoorBlockMixin(Settings settings) {
        super(settings);
    }

    // On purpose for every mod's doors. An open leaf leaves a 13 pixel gap; an npc is 0.8 wide
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(DoorBlock.OPEN) && NpcDoor.isNpcPassing(context))
            return VoxelShapes.empty();
        return super.getCollisionShape(state, world, pos, context);
    }
}
