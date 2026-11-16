package net.sevenstars.middleearth.resources.datas.npc_classes.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

import java.util.List;

// A place of work in a building, and the blocks that make one. Found in the structure templates when data is generated
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
    // A plot of a field, worked where it lies rather than at a bench
    FIELD("field", "farmland");

    public static final Codec<StationKind> CODEC = StringIdentifiable.createCodec(StationKind::values);
    private final String name;
    private final List<String> blocks;

    StationKind(String name, String... blocks) {
        this.name = name;
        this.blocks = List.of(blocks);
    }

    // Goes by the path of the block's id, so the wood or stone it's made of doesn't matter
    public static StationKind of(String blockPath) {
        for(StationKind kind : values()) {
            for(String block : kind.blocks) {
                if(blockPath.equals(block) || blockPath.endsWith("_" + block))
                    return kind;
            }
        }
        return null;
    }

    // The blocks that make this kind of station, best known first, so builders can be told what to place
    public List<String> blocks() {
        return blocks;
    }

    @Override
    public String asString() {
        return name;
    }
}
