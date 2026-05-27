package net.sevenstars.middleearth.block.special.sack;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class SackBlock extends BlockWithEntity {
    public static final Identifier CONTENTS_DYNAMIC_DROP_ID;
    public static final MapCodec<BarrelBlock> CODEC = createCodec(BarrelBlock::new);

    public SackBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4, 0, 4, 12, 9, 12);
    }

    static {
        CONTENTS_DYNAMIC_DROP_ID = Identifier.ofVanilla("contents");
    }
}
