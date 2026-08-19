package net.sevenstars.middleearth.entity.barrel;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BarrelEntity extends ChestBoat {
    private final Supplier<Item> itemSupplier;

    public BarrelEntity(EntityType<? extends Boat> type, Level world, Supplier<Item> itemSupplier) {
        super(type, world);
        this.itemSupplier = itemSupplier;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vec3(0.0, dimensions.height() / 1.75F, -0.2F)
                .yRot(-this.getYRot() * ((float)Math.PI / 180.0F));
    }

    @Override
    public Item getDropItem() {
        return this.itemSupplier.get();
    }
}
