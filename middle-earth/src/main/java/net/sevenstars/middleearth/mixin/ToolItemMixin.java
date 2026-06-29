package net.sevenstars.middleearth.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ToolMaterial.class)
public class ToolItemMixin {

    @Inject(method = "applySwordSettings", at = @At(value = "RETURN"), cancellable = true)
    private void applySwordSettings(Item.Settings settings, float attackDamage, float attackSpeed, CallbackInfoReturnable<Item.Settings> cir) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        Item.Settings result = cir.getReturnValue();

        result = result.component(
                DataComponentTypes.TOOL,
                new ToolComponent(
                        List.of(
                                ToolComponent.Rule.ofAlwaysDropping(registryEntryLookup.getOrThrow(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "cobwebs"))), 15.0F),
                                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                        ),
                        1.0F,
                        2,
                        false
                )
        );
        cir.setReturnValue(result);
    }
}
