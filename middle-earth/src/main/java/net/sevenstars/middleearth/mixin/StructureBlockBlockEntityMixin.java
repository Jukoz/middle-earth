package net.sevenstars.middleearth.mixin;

import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StructureBlockBlockEntity.class)
public abstract class StructureBlockBlockEntityMixin {
    // Reading clamps size and offset to 48, which cuts off the bounds of larger sections
    @ModifyConstant(method = "readData", constant = @Constant(intValue = 48))
    private int middleEarth$largerLimit(int limit) {
        return StructureEditing.MAXIMUM_SIZE;
    }

    @ModifyConstant(method = "readData", constant = @Constant(intValue = -48))
    private int middleEarth$largerLowerLimit(int limit) {
        return -StructureEditing.MAXIMUM_SIZE;
    }
}
