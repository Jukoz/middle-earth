package net.sevenstars.middleearth.resources;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IdentifierUtil;

/**
 * Middle-earth mod atlases<br>
 */
public class AtlasesME {
    public static final Identifier CHARACTER_SKINS = IdentifierUtil.create("character_skins");
    public static final Identifier CHARACTER_HAIRS = IdentifierUtil.create("character_hairs");
    public static final Identifier CHARACTER_EYES = IdentifierUtil.create("character_eyes");
    public static final Identifier CHARACTER_CLOTHINGS = IdentifierUtil.create("character_clothings");

    public static Identifier prefixAtlas(Identifier sprite, Identifier atlas) {
        return sprite.withPrefixedPath(String.format("%s/", atlas.getPath()));
    }

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return Identifier.of(MiddleEarth.MOD_ID, String.format("textures/atlas/%s.png", atlasIdentifier.getPath()));
    }
}
