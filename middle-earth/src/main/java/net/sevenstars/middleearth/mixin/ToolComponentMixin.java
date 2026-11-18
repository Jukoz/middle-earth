package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToolComponent.class)
public class ToolComponentMixin {

    @WrapOperation(method = {"getSpeed", "isCorrectForDrops"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/entry/RegistryEntryList;)Z"))
    private boolean ignorePickaxeRule(BlockState state, RegistryEntryList<Block> blocks, Operation<Boolean> original) {
        if (blocks instanceof RegistryEntryList.Named<Block> named
                && named.getTag().equals(BlockTags.PICKAXE_MINEABLE)
                && state.isIn(BlockTagsME.NOT_MINEABLE_WITH_PICKAXE)) {
            return false;
        }
        return original.call(state, blocks);
    }
}
