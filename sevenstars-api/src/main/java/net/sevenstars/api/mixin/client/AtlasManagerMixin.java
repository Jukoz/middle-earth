package net.sevenstars.api.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.texture.AtlasManager;
import net.sevenstars.api.registries.AtlasRegistryiesAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(AtlasManager.class)
public class AtlasManagerMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/texture/AtlasManager;ATLAS_METADATA:Ljava/util/List;"))
    private List<AtlasManager.Metadata> addNewAtlas(List<AtlasManager.Metadata> metadata) {
        List<AtlasManager.Metadata> withModAtlases = new ArrayList<>(metadata);
        AtlasRegistryiesAPI.getAtlases().forEach((textureId, definitionId) -> withModAtlases.add(new AtlasManager.Metadata(textureId, definitionId, false)));
        return withModAtlases;
    }
}
