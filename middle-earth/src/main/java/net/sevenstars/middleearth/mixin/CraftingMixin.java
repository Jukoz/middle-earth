package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CraftingScreenHandler.class)
public class CraftingMixin {


    /*@Inject(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 0),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void updateResultCancel(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory,
                                           CraftingResultInventory resultInventory, RecipeEntry<CraftingRecipe> recipe, CallbackInfo ci,
                                           CraftingRecipeInput craftingRecipeInput, ServerPlayerEntity serverPlayerEntity, ItemStack itemStack){
        if (world.isClient){
            return;
        }
        if (!ModServerConfigs.ENABLE_GOLDEN_FOOD_RECIPES && ModDimensions.isInMiddleEarth(world)
                && (itemStack.isOf(Items.GOLDEN_APPLE) || itemStack.isOf(Items.GOLDEN_CARROT))){
            itemStack = new ItemStack(Items.AIR);
        }
        resultInventory.setStack(0, itemStack);
        ci.cancel();
    }*/
}