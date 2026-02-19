package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.item.Item;
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
}
