package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBrain;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

import java.util.List;

public class CaveTrollEntity extends AbstractBeastEntity {
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState chaseAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 120.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.ATTACK_SPEED, 0.65)
                .add(EntityAttributes.FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.STEP_HEIGHT, 1.25)
                .add(EntityAttributes.FOLLOW_RANGE, 15.0);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("caveTrollBrain");
        this.getBrain().tick(world, this);
        profiler.swap("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        super.mobTick(world);
    }

    @Override
    public void tickMovement() {
        if(this.getWorld().isClient) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetInBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetInBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }
        }

        super.tickMovement();
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    public Brain<CaveTrollEntity> getBrain() {
        return (Brain<CaveTrollEntity>)super.getBrain();
    }

    @Override
    protected void setupAnimationStates() {
    }

    @Override
    protected Disposition getDisposition() {
        return null;
    }

    @Override
    protected List<RaceType> getRaceType() {
        return null;
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return false;
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof PlayerEntity || target instanceof PigEntity;
    }
}
