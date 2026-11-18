package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.WorldPreset;
import net.sevenstars.middleearth.features.generation.StartInMiddleEarth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Shadow @Final WorldCreator worldCreator;

    @Inject(method = "createLevel", at = @At("HEAD"))
    private void middleEarth$applyStartInMiddleEarth(CallbackInfo ci) {
        // null for custom world types
        RegistryEntry<WorldPreset> preset = worldCreator.getWorldType().preset();
        StartInMiddleEarth.enableIfPreset(worldCreator.getGameRules(),
                preset != null && preset.matchesKey(StartInMiddleEarth.PRESET_KEY));
    }
}
