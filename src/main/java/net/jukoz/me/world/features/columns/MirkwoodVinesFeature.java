package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MirkwoodVinesFeature  extends Feature<DefaultFeatureConfig> {
    public MirkwoodVinesFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        if (!structureWorldAccess.isAir(blockPos)) {
            return false;
        } else {
            BlockState blockState = structureWorldAccess.getBlockState(blockPos.up());
            if (!blockState.isOf(WoodBlockSets.MIRKWOOD.leaves()) && !blockState.isSolidBlock(context.getWorld(), blockPos.up())) {
                return false;
            } else {
                this.generateVinesInArea(structureWorldAccess, random, blockPos);
                return true;
            }
        }
    }

    private void generateVinesInArea(WorldAccess world, Random random, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; i < 32; ++i) {
            mutable.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));

            if (world.isAir(mutable) && validateRoot(world, mutable)) {
                int length = MathHelper.nextInt(random, 1, 8);

                if (random.nextInt(6) == 0) { // very long vine
                    length *= 2;
                }

                if (random.nextInt(5) == 0) { // short vine
                    length = 1;
                }

                generateVineColumn(world, random, mutable, length, 3, 24);
            }
        }
    }

    public static void generateVineColumn(WorldAccess world, Random random, BlockPos.Mutable pos, int length, int minAge, int maxAge) {
        for(int i = 0; i <= length; ++i) {
            if (world.isAir(pos)) {
                BlockState blockStateAbove = world.getBlockState(pos.up());

                if(blockStateAbove.isOf(ModNatureBlocks.MIRKWOOD_VINES))
                    break;
                if(blockStateAbove.isAir())
                    break;

                if(blockStateAbove.isOf(WoodBlockSets.MIRKWOOD.leaves())){
                    world.setBlockState(pos.up(), WoodBlockSets.MIRKWOOD.leaves().getDefaultState().with(LeavesBlock.PERSISTENT, true), 2);
                }

                if (i == length || !world.getBlockState(pos.down()).isAir()) {
                    world.setBlockState(pos, ModNatureBlocks.MIRKWOOD_VINES.getDefaultState().with(AbstractPlantStemBlock.AGE, MathHelper.nextInt(random, minAge, maxAge)), 2);
                    break;
                }

                world.setBlockState(pos, ModNatureBlocks.MIRKWOOD_VINES_PLANT.getDefaultState(), 2);
            }

            pos.move(Direction.DOWN);
        }
    }

    private static boolean validateRoot(WorldAccess world, BlockPos.Mutable mutable) {
        BlockState blockState = world.getBlockState(mutable.up());
        return (blockState.isOf(WoodBlockSets.MIRKWOOD.log()));
        // WoodBlockSets.MIRKWOOD.leaves()) ||
    }
}
