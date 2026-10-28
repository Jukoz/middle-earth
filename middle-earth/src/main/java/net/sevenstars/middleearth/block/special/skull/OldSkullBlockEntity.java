package net.sevenstars.middleearth.block.special.skull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

public class OldSkullBlockEntity extends BlockEntity {
    public OldSkullBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.OLD_SKULL, pos, state);
    }
}
