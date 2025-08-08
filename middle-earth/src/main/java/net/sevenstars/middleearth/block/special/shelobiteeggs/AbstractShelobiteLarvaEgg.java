package net.sevenstars.middleearth.block.special.shelobiteeggs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderEntity;

import java.util.Random;

public abstract class AbstractShelobiteLarvaEgg extends Block {
    public AbstractShelobiteLarvaEgg(Settings settings) {
        super(settings);
    }

    public static void breakEgg(World world, BlockPos pos, BlockState state) {
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    private static void SpawnSpider(BlockPos pos, World world){
        MirkwoodSpiderEntity entity = new MirkwoodSpiderEntity(ModEntities.MIRKWOOD_SPIDER, world);
        entity.age = 0;
        entity.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
        world.spawnEntity(entity);
    }
}
