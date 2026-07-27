package net.sevenstars.api.entity.ai.brain.task;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MoveTowardsBlockTask {

    private static final int DEFAULT_HORIZONTAL_RADIUS = 12;
    private static final int DEFAULT_VERTICAL_RADIUS = 7;
    private boolean shouldRun;


    public static OneShot<LivingEntity> create(float speed, TagKey<Block> blockTag) {
        return create(speed, blockTag, null);
    }

    public static OneShot<LivingEntity> create(float speed, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        return create(speed, null, null, blockTag, lowPrioBlockTag, LivingEntity::onGround);
    }

    public static OneShot<LivingEntity> create(float speed, Block block) {
        return create(speed, block, null);
    }

    public static OneShot<LivingEntity> create(float speed, Block block, Block lowPrioBlock) {
        return create(speed, block, lowPrioBlock, null, null, LivingEntity::onGround);
    }

    // main create method
    private static OneShot<LivingEntity> create(float speed, Block block, Block lowPrioBlock, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag, Predicate<LivingEntity> shouldRun) {
        return BehaviorBuilder.create((context) -> {
            return context.group(context.absent(MemoryModuleType.WALK_TARGET)).apply(context, (walkTarget) -> {
                return (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<Vec3> optional;
                        if(block != null) {
                            optional = Optional.ofNullable(findTargetPos(entity, block, lowPrioBlock));
                        }
                        else if (blockTag != null) {
                            optional = Optional.ofNullable(findTargetPosFromTag(entity, blockTag, lowPrioBlockTag));
                        }
                        else {
                            return false;
                        }

                        walkTarget.setOrErase(optional.map((pos) -> {
                            return new WalkTarget(pos, speed, 0);
                        }));
                        return true;
                    }
                };
            });
        });
    }

    private static boolean isValidBlock(BlockState blockState, Block block) {
        if(blockState.is(block)) {
            if(block == Blocks.SWEET_BERRY_BUSH) {
                return (Integer)blockState.getValue(SweetBerryBushBlock.AGE) >= 2;
            }
            return true;
        }

        return false;
    }

    private static Vec3 findTargetPos(LivingEntity entity, Block block, Block lowPrioBlock) {
        Level world = entity.level();
        int y = entity.getBlockY();
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevel(entity, y, block, lowPrioBlock);
                if(pos != null && isValidBlock(world.getBlockState(pos), block))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock))
                    lowPrioPos = pos;

            }
            else {
                pos = scanYLevel(entity, y + i, block, lowPrioBlock);
                if(pos != null && isValidBlock(world.getBlockState(pos), block))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock) && lowPrioPos == null)
                    lowPrioPos = pos;

                pos = scanYLevel(entity, y - i, block, lowPrioBlock);
                if(pos != null && world.getBlockState(pos).is(block))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock) && lowPrioPos == null)
                    lowPrioPos = pos;
            }
        }

        if(lowPrioPos != null) {
            return new Vec3(lowPrioPos.getX() + 0.5, lowPrioPos.getY(), lowPrioPos.getZ() + 0.5);
        }
        return null;
    }

    private static Vec3 findTargetPosFromTag(LivingEntity entity, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        Level world = entity.level();
        int y = entity.getBlockY();;
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevelFromTag(entity, y, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).is(lowPrioBlockTag))
                    lowPrioPos = pos;

            }
            else {
                pos = scanYLevelFromTag(entity, y + i, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null)
                    lowPrioPos = pos;

                pos = scanYLevelFromTag(entity, y - i, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null)
                    lowPrioPos = pos;
            }
        }

        if(lowPrioPos != null) {
            return new Vec3(lowPrioPos.getX(), lowPrioPos.getY(), lowPrioPos.getZ());
        }
        return null;
    }

    private static BlockPos scanYLevel(LivingEntity entity, int y, Block block, Block lowPrioBlock) {
        Level world = entity.level();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(isValidBlock(world.getBlockState(pos), block))
                return pos;
            else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                lowPrioPos = pos;
            }

            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(isValidBlock(world.getBlockState(pos), block))
                    return pos;
                else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(isValidBlock(world.getBlockState(pos), block))
                    return pos;
                else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(isValidBlock(world.getBlockState(pos), block))
                    return pos;
                else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).is(block))
                    return pos;
                else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                    lowPrioPos = pos;
                }
            }
        }

        return lowPrioPos;
    }

    private static BlockPos scanYLevelFromTag(LivingEntity entity, int y, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        Level world = entity.level();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).is(blockTag))
                return pos;
            else if (lowPrioBlockTag != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null) {
                lowPrioPos = pos;
            }

            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).is(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }
        }

        return lowPrioPos;
    }
}
