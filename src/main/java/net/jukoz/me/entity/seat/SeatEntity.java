package net.jukoz.me.entity.seat;

import net.jukoz.me.MiddleEarth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import javax.swing.text.html.HTML;

public class SeatEntity extends VehicleEntity {

    public SeatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected Item asItem() {
        return null;
    }

    @Override
    public void tick() {
        if(!this.getWorld().isClient){
            if(!this.hasPassengers() || !this.getWorld().getBlockState(this.getBlockPos()).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat")))){
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        passenger.requestTeleportAndDismount(this.getX(), this.getY(), this.getZ());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
