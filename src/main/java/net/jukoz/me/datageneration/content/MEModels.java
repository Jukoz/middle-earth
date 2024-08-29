package net.jukoz.me.datageneration.content;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MEModels {
    public static final Model VERTICAL_SLAB;
    public static final Model VERTICAL_COLUMN_SLAB;
    public static final Model WOOD_STOOL;
    public static final Model WOOD_BENCH;
    public static final Model WOOD_TABLE;
    public static final Model WOOD_CHAIR;
    public static final Model STONE_STOOL;
    public static final Model STONE_TABLE;
    public static final Model STONE_CHAIR;

    public static final Model LARGE_DOOR_LEFT;
    public static final Model LARGE_DOOR_LEFT_OPEN;
    public static final Model LARGE_DOOR_RIGHT;
    public static final Model LARGE_DOOR_RIGHT_OPEN;

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of("me", "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    static {
        VERTICAL_SLAB = block("vertical_slab", TextureKey.ALL, TextureKey.PARTICLE);
        VERTICAL_COLUMN_SLAB = block("vertical_column_slab", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.PARTICLE);
        WOOD_STOOL = block("wood_stool_template", TextureKey.ALL, TextureKey.PARTICLE);
        WOOD_BENCH = block("wood_bench_template", TextureKey.ALL, TextureKey.PARTICLE);
        WOOD_TABLE = block("wood_table_template", TextureKey.ALL, TextureKey.PARTICLE);
        WOOD_CHAIR = block("wood_chair_template", TextureKey.ALL, TextureKey.PARTICLE);
        STONE_STOOL = block("stone_stool_template", TextureKey.ALL, TextureKey.PARTICLE);
        STONE_TABLE = block("stone_table_template", TextureKey.ALL, TextureKey.PARTICLE);
        STONE_CHAIR = block("stone_chair_template", TextureKey.ALL, TextureKey.PARTICLE);

        LARGE_DOOR_LEFT = block("large_door_left", TextureKey.ALL, TextureKey.PARTICLE);
        LARGE_DOOR_LEFT_OPEN = block("large_door_left_open", TextureKey.ALL, TextureKey.PARTICLE);
        LARGE_DOOR_RIGHT = block("large_door_right", TextureKey.ALL, TextureKey.PARTICLE);
        LARGE_DOOR_RIGHT_OPEN = block("large_door_right_open", TextureKey.ALL, TextureKey.PARTICLE);
    }
}
