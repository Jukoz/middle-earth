package net.sevenstars.middleearth.mixin.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockElement.Deserializer.class)
public class BlockElementDeserializerMixin {
    @Inject(
            method = "getAngle(Lcom/google/gson/JsonObject;)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private void allowPost121ElementRotations(
            JsonObject json,
            CallbackInfoReturnable<Float> cir
    ) {
        float angle = GsonHelper.getAsFloat(json, "angle");
        if (Mth.abs(angle) > 45.0F) {
            throw new JsonParseException(
                    "Invalid rotation " + angle + " found, only -45 to 45 allowed"
            );
        }
        cir.setReturnValue(angle);
    }
}
