package net.sevenstars.middleearth.world.features.tree;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.world.features.tree.roots.MirkwoodRootPlacer;

public class ModRootPlacerType {
    public static final RootPlacerType<MirkwoodRootPlacer> MIRKWOOD_ROOT_PLACER = register(
            "mirkwood_root_placer", MirkwoodRootPlacer.CODEC);

    private static <P extends RootPlacer> RootPlacerType<P> register(String id, MapCodec<P> codec) {
        return RegistrationBridge.register(
                BuiltInRegistries.ROOT_PLACER_TYPE,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id),
                new RootPlacerType<>(codec)
        );
    }
}
