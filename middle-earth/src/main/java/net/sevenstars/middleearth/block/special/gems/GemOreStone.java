package net.sevenstars.middleearth.block.special.gems;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

/** The stones a gem ore can look like; picks the model and mining speed. */
public enum GemOreStone implements StringIdentifiable {
    STONE("minecraft", "stone"),
    DEEPSLATE("minecraft", "deepslate"),
    GRANITE("minecraft", "granite"),
    ANDESITE("minecraft", "andesite"),
    DIORITE("minecraft", "diorite"),
    CALCITE("minecraft", "calcite"),
    TUFF("minecraft", "tuff"),
    MEDGON("middle-earth", "medgon"),
    NURGON("middle-earth", "nurgon"),
    ASHENSTONE("middle-earth", "ashenstone"),
    LIMESTONE("middle-earth", "limestone"),
    CHALK("middle-earth", "chalk"),
    TRAVERTINE("middle-earth", "travertine"),
    GABBRO("middle-earth", "gabbro"),
    SLATE("middle-earth", "slate"),
    KHAGALABAN("middle-earth", "khagalaban"),
    IRONSTONE("middle-earth", "ironstone"),
    HEMATITE("middle-earth", "hematite"),
    GALONN("middle-earth", "galonn"),
    IZHERABAN("middle-earth", "izheraban"),
    DOLOMITE("middle-earth", "dolomite"),
    GREEN_TUFF("middle-earth", "green_tuff"),
    BLUE_TUFF("middle-earth", "blue_tuff");

    private static volatile Map<Block, GemOreStone> byBlock;

    private final Identifier blockId;

    GemOreStone(String namespace, String path) {
        this.blockId = Identifier.of(namespace, path);
    }

    @Override
    public String asString() {
        return blockId.getPath();
    }

    public Identifier blockId() {
        return blockId;
    }

    public Block block() {
        return Registries.BLOCK.getOptionalValue(blockId).orElse(Blocks.STONE);
    }

    public static GemOreStone of(Block block) {
        Map<Block, GemOreStone> map = byBlock;
        if (map == null) {
            Map<Block, GemOreStone> built = new HashMap<>();
            for (GemOreStone stone : values()) {
                Registries.BLOCK.getOptionalValue(stone.blockId).ifPresent(b -> built.put(b, stone));
            }
            map = Map.copyOf(built);
            byBlock = map;
        }
        return map.get(block);
    }

    public static GemOreStone forDepth(int y) {
        if (y < -32) {
            return MEDGON;
        }
        if (y < 0) {
            return NURGON;
        }
        if (y < 32) {
            return DEEPSLATE;
        }
        return STONE;
    }
}
