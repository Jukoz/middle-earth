package net.sevenstars.middleearth.entity.seat;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
            World world = this.getWorld();
            BlockPos pos = this.getBlockPos();
            if (!this.hasPassengers() && this.getWorld().getBlockState(pos).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat")))){
                world.setBlockState(this.getBlockPos(), world.getBlockState(pos).with(SeatBlock.OCCUPIED, false));
                this.remove(RemovalReason.DISCARDED);
            } else if(!this.hasPassengers() || !this.getWorld().getBlockState(pos).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat")))){
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        World world = passenger.getWorld();
        if (!world.isClient){
            BlockPos pos = this.getBlockPos();
            passenger.requestTeleport(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            super.removePassenger(passenger);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
