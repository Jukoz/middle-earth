package net.sevenstars.middleearth.mixin;

import net.minecraft.registry.Registry;
import net.minecraft.server.Main;
import net.minecraft.server.SaveLoading;
import net.minecraft.server.dedicated.ServerPropertiesLoader;
import net.minecraft.util.Identifier;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.dimension.DimensionOptions;
import net.sevenstars.middleearth.features.generation.StartInMiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

// Only runs when a dedicated server creates a brand new world
@Mixin(Main.class)
public class ServerMainMixin {
    @Inject(method = "createWorld", at = @At("RETURN"))
    private static void middleEarth$applyStartInMiddleEarth(ServerPropertiesLoader propertiesLoader, SaveLoading.LoadContextSupplierContext context,
                                                            Registry<DimensionOptions> dimensionsRegistry, boolean demo, boolean bonusChest,
                                                            CallbackInfoReturnable<SaveLoading.LoadContext<SaveProperties>> cir) {
        String levelType = ((AbstractPropertiesHandlerAccessor) propertiesLoader.getPropertiesHandler()).getProperties().getProperty("level-type", "").toLowerCase(Locale.ROOT);
        StartInMiddleEarth.enableIfPreset(cir.getReturnValue().extraData().getGameRules(),
                StartInMiddleEarth.PRESET_ID.equals(Identifier.tryParse(levelType)));
    }
}
