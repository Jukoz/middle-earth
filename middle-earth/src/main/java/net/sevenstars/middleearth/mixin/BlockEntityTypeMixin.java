package net.sevenstars.middleearth.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Arrays;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @ModifyArgs(method = "<clinit>", at = @At( value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;"))
    private static void modifyLecternBlocks(Args args) {
        String id = args.get(0);
        if (id.equals("lectern")) {
            Block[] original = args.get(2);
            Block[] modified = Arrays.copyOf(original, original.length + 1);
            modified[modified.length - 1] = ModDecorativeBlocks.STONE_LECTERN;
            args.set(2, modified);
        } else if(id.equals("chiseled_bookshelf")) {
            Block[] original = args.get(2);
            Block[] modified = Arrays.copyOf(original, original.length + 1);
            modified[modified.length - 1] = ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF;
            args.set(2, modified);
        }
    }
}
