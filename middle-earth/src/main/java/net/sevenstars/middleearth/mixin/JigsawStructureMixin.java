package net.sevenstars.middleearth.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.world.gen.structure.StructureWater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(JigsawStructure.class)
public abstract class JigsawStructureMixin {
    // Vanilla projects to the water's surface, so a village could spread over a lake
    @Inject(method = "getStructurePosition", at = @At("RETURN"), cancellable = true)
    private void middleEarth$keepOffWater(Structure.Context context, CallbackInfoReturnable<Optional<Structure.StructurePosition>> cir) {
        Optional<Structure.StructurePosition> planned = cir.getReturnValue();
        if(planned.isEmpty() || !StructureWater.applies((JigsawStructure)(Object)this))
            return;
        // Assembled once and handed on, so the checked pieces are the placed ones
        StructurePiecesCollector pieces = planned.get().generate();
        cir.setReturnValue(StructureWater.isFlooded(context, pieces) ? Optional.empty()
                : Optional.of(new Structure.StructurePosition(planned.get().position(), Either.right(pieces))));
    }
}
