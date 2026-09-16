package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.util.Identifier;
import net.sevenstars.api.registries.AtlasRegistryiesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;

public class AtlasRegistryHH {
    public static final Identifier GENERIC_FACTIONS = OfHallsAndHeralds.id("generic_factions");

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return OfHallsAndHeralds.ofPath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
    }

    public static void registerAtlas(){
        AtlasRegistryiesAPI.injectAtlas(EntityModelLayersRegistryHH.GENERIC_FACTION_ATLAS_TEXTURE, GENERIC_FACTIONS);
    }
}
