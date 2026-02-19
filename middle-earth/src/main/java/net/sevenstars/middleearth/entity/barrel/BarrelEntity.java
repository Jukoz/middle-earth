package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BarrelEntity extends AbstractBoatEntity {
    public BarrelEntity(EntityType<? extends AbstractBoatEntity> type, World world, Supplier<Item> itemSupplier) {
        super(type, world, itemSupplier);
    }

    @Override
    protected double getPassengerAttachmentY(EntityDimensions dimensions) {
        return dimensions.height() / 1.75F;
    }

    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }

    protected int getMaxPassengers() {
        return 1;
    }
}
