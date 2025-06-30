package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.utils.HallucinationData;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Shadow @Final public ClientWorld clientWorld;
    @Inject(method = "getFovMultiplier", at =@At("TAIL"), cancellable = true)
    private void injected(CallbackInfoReturnable<Float> cir) {
    PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            cir.setReturnValue(cir.getReturnValue() * (1 - intensity/4));
        }
    }

    @Redirect(method = "getFovMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean init(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof BowItem;
    }
}
