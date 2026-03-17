package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BarrelEntity extends AbstractChestBoatEntity {
    public BarrelEntity(EntityType<? extends AbstractChestBoatEntity> type, World world, Supplier<Item> itemSupplier) {
        super(type, world, itemSupplier);
    }

    @Override
    protected double getPassengerAttachmentY(EntityDimensions dimensions) {
        return dimensions.height() / 1.75F;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return (new Vec3d(0.0, this.getPassengerAttachmentY(dimensions), -0.2f)).rotateY(-this.getYaw() * 0.017453292F);
    }
}
