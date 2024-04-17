package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Inject(method = "createToolComponent", at = @At(value = "HEAD"), cancellable = true)
    private static void createToolComponent(CallbackInfoReturnable<ToolComponent> cir) {
        cir.setReturnValue(new ToolComponent(List.of(ToolComponent.Rule.ofAlwaysDropping(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "cobwebs")), 15.0f),
                ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5f)), 1.0f, 2));
    }
}
