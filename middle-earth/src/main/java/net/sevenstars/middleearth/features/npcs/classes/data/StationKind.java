package net.sevenstars.middleearth.features.npcs.classes.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum StationKind implements StringIdentifiable {
    FORGE("forge", "forge", "anvil"),
    OVEN("oven", "smoker", "oven"),
    HEARTH("hearth", "campfire", "cauldron", "fat_pot"),
    LOOM("loom", "loom"),
    WOOD_PILE("wood_pile", "wood_pile"),
    HIVE("hive", "beehive", "bee_nest"),
    LECTERN("lectern", "lectern"),
    HAY("hay", "hay_block"),
    GRINDSTONE("grindstone", "grindstone"),
    TARGET("target", "target"),
    // Worked where it lies, not at a bench
    FIELD("field", "farmland");

    public static final Codec<StationKind> CODEC = StringIdentifiable.createCodec(StationKind::values);
    private final String name;
    private final List<String> blocks;

    StationKind(String name, String... blocks) {
        this.name = name;
        this.blocks = List.of(blocks);
    }

    // By the path of the block's id, so wood or stone doesn't matter
    public static @Nullable StationKind of(String blockPath) {
        for(StationKind kind : values()) {
            for(String block : kind.blocks) {
                if(blockPath.equals(block) || blockPath.endsWith("_" + block))
                    return kind;
            }
        }
        return null;
    }

    public List<String> blocks() {
        return blocks;
    }

    @Override
    public String asString() {
        return name;
    }
}
