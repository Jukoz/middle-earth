package net.sevenstars.api.mixin.client;

import net.minecraft.client.texture.AtlasManager;
import net.sevenstars.api.utils.AtlasUtil;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(AtlasManager.class)
public class AtlasManagerMixin {

    @Mutable
    @Shadow
    @Final
    private static List<AtlasManager.Metadata> ATLAS_METADATA;

    static {
        inject();
    }

    @Unique
    private static void inject() {
        ATLAS_METADATA = new ArrayList<>(ATLAS_METADATA);
        ATLAS_METADATA.addAll(AtlasUtil.getAtlases());
        ATLAS_METADATA = List.copyOf(ATLAS_METADATA);
    }
}
