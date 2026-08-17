package net.sevenstars.middleearth.entity.wight;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

public class BarrowWightEnchanterEntity extends BarrowWightEntity{

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
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(random.nextBoolean()) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModDecorativeBlocks.GROUND_BOOK));
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

}
