package net.jukoz.me.datageneration.content;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MEModels {
    public static final Model VERTICAL_SLAB;
    public static final Model VERTICAL_COLUMN_SLAB;
    public static final Model WOOD_STOOL;
    public static final Model WOOD_TABLE;
    public static final Model WOOD_CHAIR;
    public static final Model STONE_STOOL;
    public static final Model STONE_TABLE;
    public static final Model STONE_CHAIR;

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of("me", "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    static {
        VERTICAL_SLAB = block("vertical_slab", TextureKey.ALL, TextureKey.PARTICLE);
        VERTICAL_COLUMN_SLAB = block("vertical_column_slab", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.PARTICLE);
        WOOD_STOOL = block("wood_stool", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
        WOOD_TABLE = block("wood_table", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
        WOOD_CHAIR = block("wood_chair", TextureKey.TOP, TextureKey.SIDE, TextureKey.PARTICLE);
        STONE_STOOL = block("stone_stool_template", TextureKey.ALL, TextureKey.PARTICLE);
        STONE_TABLE = block("stone_table_template", TextureKey.ALL, TextureKey.PARTICLE);
        STONE_CHAIR = block("stone_chair_template", TextureKey.ALL, TextureKey.PARTICLE);
    }
}
