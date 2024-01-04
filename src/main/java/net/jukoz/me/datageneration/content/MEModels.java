package net.jukoz.me.datageneration.content;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MEModels {
    public static final Model VERTICAL_SLAB;
    public static final Model WOOD_STOOL;
    public static final Model WOOD_TABLE;

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("me", "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    static {
        VERTICAL_SLAB = block("vertical_slab", TextureKey.ALL, TextureKey.PARTICLE);
        WOOD_STOOL = block("wood_stool", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
        WOOD_TABLE= block("wood_table", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
    }
}
