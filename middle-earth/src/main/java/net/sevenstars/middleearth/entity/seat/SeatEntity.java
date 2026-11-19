package net.sevenstars.middleearth.entity.seat;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class SeatEntity extends VehicleEntity {
    private static final TagKey<Block> SEATS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "seat"));

    public SeatEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected Item asItem() {
        return null;
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if(world.isClient) return;

        BlockState state = world.getBlockState(this.getBlockPos());
        if(!this.hasPassengers() || !state.isIn(SEATS)) {
            this.discard();
        } else if(!state.get(SeatBlock.OCCUPIED)) {
            // A rider who logged out on the seat comes back with it
            world.setBlockState(this.getBlockPos(), state.with(SeatBlock.OCCUPIED, true));
        }
    }

    // Also runs when the rider logs out, which removes the seat without another tick
    @Override
    public void onRemove(RemovalReason reason) {
        super.onRemove(reason);
        World world = this.getWorld();
        if(world.isClient || reason == RemovalReason.UNLOADED_TO_CHUNK) return;

        BlockPos pos = this.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if(state.isIn(SEATS) && state.get(SeatBlock.OCCUPIED)) {
            world.setBlockState(pos, state.with(SeatBlock.OCCUPIED, false));
        }
    }

    @Override
    protected void readCustomData(ReadView view) {

    }

    @Override
    protected void writeCustomData(WriteView view) {

    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Direction front = state.contains(Properties.HORIZONTAL_FACING) ? state.get(Properties.HORIZONTAL_FACING) : passenger.getHorizontalFacing();
        for(Direction direction : List.of(front, front.rotateYClockwise(), front.rotateYCounterclockwise(), front.getOpposite())) {
            Vec3d spot = Dismounting.findRespawnPos(passenger.getType(), world, pos.offset(direction), true);
            if(spot != null && isPathClear(world, passenger, spot, direction)) return spot;
        }
        return super.updatePassengerForDismount(passenger);
    }

    // The rider lands mid-block, so a closed door or trapdoor on the near edge of that block would be skipped past
    private static boolean isPathClear(World world, LivingEntity passenger, Vec3d spot, Direction direction) {
        Box body = passenger.getType().getDimensions().getBoxAt(spot);
        // 0.19 reaches back to just short of the seat block's face
        Box gap = body.stretch(-direction.getOffsetX() * 0.19, 0, -direction.getOffsetZ() * 0.19);
        return world.isSpaceEmpty(passenger, gap);
    }
}
