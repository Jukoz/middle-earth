package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ShearsItem.class)
public class ShearItemMixin {

    @Inject(method = "createToolComponent", at = @At(value = "HEAD"), cancellable = true)
    private static void createToolComponent(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(new ToolComponent(List.of(ToolComponent.Rule.ofAlwaysDropping(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "cobwebs")), 15.0F), ToolComponent.Rule.of(BlockTags.LEAVES, 15.0F), ToolComponent.Rule.of(BlockTags.WOOL, 5.0F), ToolComponent.Rule.of(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0F)), 1.0F, 1).defaultMiningSpeed());
    }

    @Inject(method = "postMine", at = @At(value = "HEAD"), cancellable = true)
    public void isSuitableFor(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.isIn(BlockTags.LEAVES) || state.isIn(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "cobwebs"))) || state.isOf(Blocks.SHORT_GRASS) || state.isOf(Blocks.FERN) || state.isOf(Blocks.DEAD_BUSH) || state.isOf(Blocks.HANGING_ROOTS) || state.isOf(Blocks.VINE) || state.isOf(Blocks.TRIPWIRE) || state.isIn(BlockTags.WOOL));
    }
}
