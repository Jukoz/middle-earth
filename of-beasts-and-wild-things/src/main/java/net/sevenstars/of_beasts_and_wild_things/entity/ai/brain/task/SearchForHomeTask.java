package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SearchForHomeTask {
    private static final int DEFAULT_HORIZONTAL_RADIUS = 12;
    private static final int DEFAULT_VERTICAL_RADIUS = 7;

    public static OneShot<LivingEntity> create(TagKey<Block> blockTag) {
        return create(null, blockTag, LivingEntity::onGround);
    }

    public static OneShot<LivingEntity> create(Block block) {
        return create(block, null, LivingEntity::onGround);
    }

    private static OneShot<LivingEntity> create(Block block, TagKey<Block> blockTag, Predicate<LivingEntity> shouldRun) {
        return BehaviorBuilder.create((context) -> {
            return context.group(context.absent(MemoryModuleType.HOME)).apply(context, (home) -> {
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

                        home.setOrErase(optional.map((pos) -> {
                            return new GlobalPos(world.dimension(), new BlockPos(pos));
                        }));
                        return true;
                    }
                };
            });
        });
    }



    private static BlockPos findTargetPos(LivingEntity entity, Block block) {
        Level world = entity.level();
        int y = entity.getBlockY();
        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevel(entity, y, block);
                if(pos != null && world.getBlockState(pos).is(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
            else {
                pos = scanYLevel(entity, y + i, block);
                if(pos != null && world.getBlockState(pos).is(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

                pos = scanYLevel(entity, y - i, block);
                if(pos != null && world.getBlockState(pos).is(block))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return null;
    }

    private static BlockPos scanYLevel(LivingEntity entity, int y, Block block) {
        Level world = entity.level();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;
        BlockPos lowPrioPos = null;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).is(block))
                return pos;

            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).is(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).is(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).is(block))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).is(block))
                    return pos;
            }
        }

        return null;
    }

    private static BlockPos findTargetPosFromTag(LivingEntity entity, TagKey<Block> blockTag) {
        Level world = entity.level();
        int y = entity.getBlockY();
        BlockPos pos;

        // This for-loop calls the scan method with y-levels alternating between above and below, starting from the entities y-level and moving away
        for(int i = 0; i <= DEFAULT_VERTICAL_RADIUS; i++) {
            if(i == 0) {
                pos = scanYLevelFromTag(entity, y, blockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

            }
            else {
                pos = scanYLevelFromTag(entity, y + i, blockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());

                pos = scanYLevelFromTag(entity, y - i, blockTag);
                if(pos != null && world.getBlockState(pos).is(blockTag))
                    return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return null;
    }

    private static BlockPos scanYLevelFromTag(LivingEntity entity, int y, TagKey<Block> blockTag) {
        Level world = entity.level();
        int x = entity.getBlockX();
        int z = entity.getBlockZ();

        BlockPos pos;

        // This for-loop looks for a matching block by going round in a spiral shape
        for(int i = 0; i <= DEFAULT_HORIZONTAL_RADIUS; i++) {
            pos = new BlockPos(x++, y, z);
            if(world.getBlockState(pos).is(blockTag)) {
                return pos;
            }


            for(int j = 0; j < i; j++) {
                pos = new BlockPos(x++, y, z++);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z++);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x--, y, z--);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
            }

            for(int j = 0; j <= i; j++) {
                pos = new BlockPos(x++, y, z--);
                if(world.getBlockState(pos).is(blockTag))
                    return pos;
            }
        }

        return null;
    }
}
