package net.sevenstars.middleearth.resources.datas.npc_classes.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

import java.util.List;

// A place of work in a building, and the blocks that make one. Found in the structure templates when data is generated
public enum StationKind implements StringIdentifiable {
    FORGE("forge", "forge", "anvil"),
    OVEN("oven", "smoker", "oven"),
    HEARTH("hearth", "fat_pot", "cauldron", "campfire"),
    LOOM("loom", "loom"),
    WOOD_PILE("wood_pile", "wood_pile"),
    HIVE("hive", "beehive", "bee_nest"),
    LECTERN("lectern", "lectern"),
    HAY("hay", "hay_block"),
    GRINDSTONE("grindstone", "grindstone"),
    TARGET("target", "target"),
    // A plot of the fields, worked where it lies rather than at a bench
    FIELD("field", "farmland");

    public static final Codec<StationKind> CODEC = StringIdentifiable.createCodec(StationKind::values);
    private final String name;
    private final List<String> blocks;

    StationKind(String name, String... blocks) {
        this.name = name;
        this.blocks = List.of(blocks);
    }

    // By the path of the block's id, so that the wood or stone it is made of does not matter
    public static StationKind of(String blockPath) {
        for(StationKind kind : values()) {
            for(String block : kind.blocks) {
                if(blockPath.equals(block) || blockPath.endsWith("_" + block))
                    return kind;
            }
        }
        return null;
    }

    @Override
    public String asString() {
        return name;
    }
}
