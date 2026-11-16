package net.sevenstars.middleearth.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

// Lets a builder add a piece to a pool in the currently loaded world
@Mixin(StructurePool.class)
public interface StructurePoolAccessor {
    @Accessor("elements")
    ObjectArrayList<StructurePoolElement> middleEarth$getElements();

    @Mutable
    @Accessor("elementWeights")
    void middleEarth$setElementWeights(List<Pair<StructurePoolElement, Integer>> elementWeights);
}
