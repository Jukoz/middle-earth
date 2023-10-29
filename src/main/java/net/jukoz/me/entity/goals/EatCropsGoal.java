package net.jukoz.me.entity.goals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EatCropsGoal extends Goal {

    private static final int MAX_TIMER = 40;
    private static final Predicate<BlockState> POTATO_PREDICATE = BlockStatePredicate.forBlock(Blocks.POTATOES);
    private static final List<Block> eatableCrops = new ArrayList<Block>() {
        {
            add(Blocks.POTATOES);
            add(Blocks.CARROTS);
            add(Blocks.BEETROOTS);
        }
    };
    private final MobEntity mob;
    private final World world;
    private int timer;


    public EatCropsGoal(MobEntity mob) {
        this.mob = mob;
        this.world = mob.getWorld();
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
    }

    @Override
    public boolean canStart() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        }
        BlockPos blockPos = this.mob.getBlockPos().up();
        for(Block b : eatableCrops) {
            if (this.world.getBlockState(blockPos).isOf(b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.timer = this.getTickCount(40);
        this.world.sendEntityStatus(this.mob, EntityStatuses.SET_SHEEP_EAT_GRASS_TIMER_OR_PRIME_TNT_MINECART);
        this.mob.getNavigation().stop();
    }
    @Override
    public void stop() {
        this.timer = 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer > 0;
    }

    public int getTimer() {
        return this.timer;
    }

    @Override
    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer != this.getTickCount(4)) {
            return;
        }
        BlockPos blockPos = this.mob.getBlockPos().up();
        for(Block b : eatableCrops) {
            if (this.world.getBlockState(blockPos).isOf(b)) {
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    this.world.breakBlock(blockPos, false);
                }
                this.mob.onEatingGrass();
            }
        }

    }
}
