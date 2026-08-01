package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class SearchForHomeTask {
    private static final int DEFAULT_HORIZONTAL_RADIUS = 12;
    private static final int DEFAULT_VERTICAL_RADIUS = 7;

    public static SingleTickTask<LivingEntity> create(TagKey<Block> blockTag) {
        return create(null, blockTag, LivingEntity::isOnGround);
    }

    public static SingleTickTask<LivingEntity> create(Block block) {
        return create(block, null, LivingEntity::isOnGround);
    }

    private static SingleTickTask<LivingEntity> create(Block block, TagKey<Block> blockTag, Predicate<LivingEntity> shouldRun) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.HOME)).apply(context, (home) -> {
                return (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<BlockPos> optional;
                        if(block != null) {
                            optional = Optional.ofNullable(findTargetPos(entity, block));
                        }
                        else if (blockTag != null) {
                            optional = Optional.ofNullable(findTargetPosFromTag(entity, blockTag));
                        }
                        else {
                            return false;
                        }

                        home.remember(optional.map((pos) -> {
                            return new GlobalPos(world.getRegistryKey(), new BlockPos(pos));
                        }));
                        return true;
                    }
                };
            });
        });
    }



    private static BlockPos findTargetPos(LivingEntity entity, Block block) {
        World world = entity.getWorld();
        int y = entity.getBlockY();
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevel(entity, y, block);
                if(pos != null && world.getBlockState(pos).isOf(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
            else {
                pos = scanYLevel(entity, y + i, block);
                if(pos != null && world.getBlockState(pos).isOf(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

                pos = scanYLevel(entity, y - i, block);
                if(pos != null && world.getBlockState(pos).isOf(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return null;
    }

    private static BlockPos scanYLevel(LivingEntity entity, int y, Block block) {
        World world = entity.getWorld();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).isOf(block))
                return pos;

            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).isOf(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).isOf(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).isOf(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).isOf(block))
                    return pos;
            }
        }

        return null;
    }

    private static BlockPos findTargetPosFromTag(LivingEntity entity, TagKey<Block> blockTag) {
        World world = entity.getWorld();
        int y = entity.getBlockY();
        BlockPos pos;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevelFromTag(entity, y, blockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

            }
            else {
                pos = scanYLevelFromTag(entity, y + i, blockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

                pos = scanYLevelFromTag(entity, y - i, blockTag);
                if(pos != null && world.getBlockState(pos).isIn(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return null;
    }

    private static BlockPos scanYLevelFromTag(LivingEntity entity, int y, TagKey<Block> blockTag) {
        World world = entity.getWorld();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).isIn(blockTag)) {
                return pos;
            }


            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).isIn(blockTag))
                    return pos;
            }
        }

        return null;
    }
}
