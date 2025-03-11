package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class MoveTowardsBlockTask {

    private static final int DEFAULT_HORIZONTAL_RADIUS = 12;
    private static final int DEFAULT_VERTICAL_RADIUS = 7;
    private boolean shouldRun;

    // create methods for Blocks or BlockTags
    public static SingleTickTask<LivingEntity> create(float speed, TagKey<Block> blockTag) {
        return create(speed, blockTag, null);
    }

    public static SingleTickTask<LivingEntity> create(float speed, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        return create(speed, null, null, blockTag, lowPrioBlockTag, LivingEntity::isOnGround);
    }

    public static SingleTickTask<LivingEntity> create(float speed, Block block) {
        return create(speed, block, null);
    }

    public static SingleTickTask<LivingEntity> create(float speed, Block block, Block lowPrioBlock) {
        return create(speed, block, lowPrioBlock, null, null, LivingEntity::isOnGround);
    }

    // main create method
    private static SingleTickTask<LivingEntity> create(float speed, Block block, Block lowPrioBlock, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag, Predicate<LivingEntity> shouldRun) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(context, (walkTarget) -> {
                return (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<Vec3d> optional;
                        if(block != null) {
                            optional = Optional.ofNullable(findTargetPos(entity, block, lowPrioBlock));
                        }
                        else if (blockTag != null) {
                            optional = Optional.ofNullable(findTargetPosFromTag(entity, blockTag, lowPrioBlockTag));
                        }
                        else {
                            return false;
                        }

                        walkTarget.remember(optional.map((pos) -> {
                            return new WalkTarget(pos, speed, 0);
                        }));
                        return true;
                    }
                };
            });
        });
    }

    private static boolean isValidBlock(BlockState blockState, Block block) {
        if(blockState.isOf(block)) {
            if(block == Blocks.SWEET_BERRY_BUSH) {
                return (Integer)blockState.get(SweetBerryBushBlock.AGE) >= 2;
            }
            return true;
        }

        return false;
    }

    private static Vec3d findTargetPos(LivingEntity entity, Block block, Block lowPrioBlock) {
        World world = entity.getWorld();
        int y = entity.getBlockY();;
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevel(entity, y, block, lowPrioBlock);
                if(pos != null && isValidBlock(world.getBlockState(pos), block))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock))
                    lowPrioPos = pos;

            }
            else {
                pos = scanYLevel(entity, y + i, block, lowPrioBlock);
                if(pos != null && isValidBlock(world.getBlockState(pos), block))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock) && lowPrioPos == null)
                    lowPrioPos = pos;

                pos = scanYLevel(entity, y - i, block, lowPrioBlock);
                if(pos != null && world.getBlockState(pos).isOf(block))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlock != null && pos != null && isValidBlock(world.getBlockState(pos), lowPrioBlock) && lowPrioPos == null)
                    lowPrioPos = pos;
            }
        }

        if(lowPrioPos != null) {
            return new Vec3d(lowPrioPos.getX() + 0.5, lowPrioPos.getY(), lowPrioPos.getZ() + 0.5);
        }
        return null;
    }

    private static Vec3d findTargetPosFromTag(LivingEntity entity, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        World world = entity.getWorld();
        int y = entity.getBlockY();;
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevelFromTag(entity, y, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).isIn(lowPrioBlockTag))
                    lowPrioPos = pos;

            }
            else {
                pos = scanYLevelFromTag(entity, y + i, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null)
                    lowPrioPos = pos;

                pos = scanYLevelFromTag(entity, y - i, blockTag, lowPrioBlockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                else if(lowPrioBlockTag != null && pos != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null)
                    lowPrioPos = pos;
            }
        }

        if(lowPrioPos != null) {
            return new Vec3d(lowPrioPos.getX(), lowPrioPos.getY(), lowPrioPos.getZ());
        }
        return null;
    }

    private static BlockPos scanYLevel(LivingEntity entity, int y, Block block, Block lowPrioBlock) {
        World world = entity.getWorld();
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
                if(world.getBlockState(pos).isOf(block))
                    return pos;
                else if (lowPrioBlock != null && isValidBlock(world.getBlockState(pos), lowPrioBlock)) {
                    lowPrioPos = pos;
                }
            }
        }

        return lowPrioPos;
    }

    private static BlockPos scanYLevelFromTag(LivingEntity entity, int y, TagKey<Block> blockTag, TagKey<Block> lowPrioBlockTag) {
        World world = entity.getWorld();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).isIn(blockTag))
                return pos;
            else if (lowPrioBlockTag != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null) {
                lowPrioPos = pos;
            }

            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
                else if (lowPrioBlockTag != null && world.getBlockState(pos).isIn(lowPrioBlockTag) && lowPrioPos == null) {
                    lowPrioPos = pos;
                }
            }
        }

        return lowPrioPos;
    }
}
