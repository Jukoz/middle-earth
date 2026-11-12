package net.sevenstars.middleearth.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DoorBlock.class)
public class DoorBlockMixin extends Block {
    public DoorBlockMixin(Settings settings) {
        super(settings);
    }

    // An open door leaves a gap of 13 pixels to one side, and an npc is 0.8 blocks wide walking down the middle
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(DoorBlock.OPEN) && context instanceof EntityShapeContext entityContext && entityContext.getEntity() instanceof NpcEntity)
            return VoxelShapes.empty();
        return super.getCollisionShape(state, world, pos, context);
    }
}
