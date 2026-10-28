package net.sevenstars.middleearth.entity.wight;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.entity.wight.brain.BarrowWightEnchanterBrain;
import net.sevenstars.middleearth.item.WeaponItemsME;

public class BarrowWightEnchanterEntity extends BarrowWightEntity{
    public static final int HALLUCINATION_CAST_COOLDOWN = 67;
    private static final TrackedData<Integer> HALLUCINATION_CAST;

    private int hallucinationCastAnimationCooldown = 0;

    public BarrowWightEnchanterEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 30.0)
                .add(EntityAttributes.ARMOR, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.22)
                .add(EntityAttributes.FOLLOW_RANGE, 40.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HALLUCINATION_CAST, 0);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(random.nextBoolean()) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(DecorativeBlockRegistryME.GROUND_BOOK));
        } else if(random.nextBoolean()) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(WeaponItemsME.BARROW_BLADE));
            if(random.nextBoolean()) {
                this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(DecorativeBlockRegistryME.GROUND_BOOK));
            }
        }
    }

    @Override
    protected void setupAnimationStates() {
        super.setupAnimationStates();

        setTrackerState(HALLUCINATION_CAST, incantationAnimation);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        } else {
            if (hallucinationCastAnimationCooldown <= 1) {
                this.dataTracker.set(HALLUCINATION_CAST, -1);
            }
            hallucinationCastAnimationCooldown = Math.max(hallucinationCastAnimationCooldown - 1, 0);
        }
    }

    public void tryCastingAnimation() {
        if(hallucinationCastAnimationCooldown == 0) {
            hallucinationCastAnimationCooldown = HALLUCINATION_CAST_COOLDOWN;
            this.dataTracker.set(HALLUCINATION_CAST, 1);
        }
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return BarrowWightEnchanterBrain.create(this, dynamic);
    }

    @Override
    public Brain<BarrowWightEntity> getBrain() {
        return (Brain<BarrowWightEntity>)super.getBrain();
    }

    static {
        HALLUCINATION_CAST = DataTracker.registerData(BarrowWightEnchanterEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
